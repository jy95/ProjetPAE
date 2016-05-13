package persistance.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.dbcp2.BasicDataSource;

import exception.FatalException;



public class DalServicesImpl implements DalServices, DalBackendServices {
  // private Connection connection;
  private BasicDataSource dataSource;
  private ThreadLocal<Connection> threadLocal;
  private ThreadLocal<AtomicInteger> semaphoreConnexion;
  private ThreadLocal<AtomicInteger> semaphoreTransaction;

  /**
   * fichier Dal.
   * 
   * @param url String
   * @param user String
   * @param mdp String
   */
  public DalServicesImpl(String url, String user, String mdp) {
    threadLocal = new ThreadLocal<Connection>();

    dataSource = new BasicDataSource();
    dataSource.setUrl(url);
    dataSource.setUsername(user);
    dataSource.setPassword(mdp);
    // TODO serveur de l ecole demande cela
    dataSource.setInitialSize(2);
    semaphoreConnexion = new ThreadLocal<AtomicInteger>().withInitial(AtomicInteger::new);
    semaphoreTransaction = new ThreadLocal<AtomicInteger>().withInitial(AtomicInteger::new);

  }

  /**
   * Ouvre une connection.
   */
  public void openConnection() {

    try {
      if (semaphoreConnexion.get().incrementAndGet() == 1) {
        Connection con = dataSource.getConnection();
        threadLocal.set(con);
      }

    } catch (SQLException err) {
      throw new InternalError();
    }

  }

  /**
   * Ferme une connection.
   */
  public void closeConnection() {


    try {
      if (semaphoreConnexion.get().updateAndGet(i -> i > 0 ? i - 1 : i) == 0) {
        Connection con = threadLocal.get();
        threadLocal.remove();
        con.close();
      }

    } catch (SQLException err) {
      throw new InternalError();
    }

  }

  @Override
  public void startTransaction() {
    try {
      
      if (semaphoreTransaction.get().incrementAndGet() == 1) {
        threadLocal.get().setAutoCommit(false);
      }
      
    } catch (SQLException err) {
      throw new InternalError();
    }

  }

  @Override
  public void commitTransaction() {

    try {
      
      if (semaphoreTransaction.get().updateAndGet(i -> i > 0 ? i - 1 : i) == 0) {
        Connection con = threadLocal.get();
        con.commit();
        con.setAutoCommit(true);
      }
      
    } catch (SQLException err) {
      throw new InternalError();
    }

  }

  @Override
  public void rollBackTransaction() {
    try {
      threadLocal.get().rollback();
      threadLocal.get().setAutoCommit(true);
      semaphoreTransaction.get().set(0);
    } catch (SQLException err) {
      throw new InternalError();
    }

  }


  @Override
  public PreparedStatement getPreparedStatement(String prepare) {
    PreparedStatement ps = null;
    try {
      Connection connection = threadLocal.get();
      ps = connection.prepareStatement(prepare);
    } catch (SQLException err) {

      throw new FatalException(err.getMessage());
    }
    return ps;
  }


}
