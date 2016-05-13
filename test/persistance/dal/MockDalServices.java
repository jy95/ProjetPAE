package persistance.dal;

import java.sql.PreparedStatement;

public class MockDalServices implements DalBackendServices, DalServices {

  @Override
  public void startTransaction() {}

  @Override
  public void commitTransaction() {}

  @Override
  public void rollBackTransaction() {}

  @Override
  public PreparedStatement getPreparedStatement(String s) {
    return null;
  }

  @Override
  public void openConnection() {
   
  }

  @Override
  public void closeConnection() {
 
  }


}
