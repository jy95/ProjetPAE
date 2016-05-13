package main;

import biz.factory.DtoFactory;
import biz.ucc.AnnulationUcc;
import biz.ucc.DemandePaiementUcc;
import biz.ucc.DemandeUcc;
import biz.ucc.DocDepartUcc;
import biz.ucc.DocRetourUcc;
import biz.ucc.MobiliteUcc;
import biz.ucc.PartenaireUcc;
import biz.ucc.PaysUcc;
import biz.ucc.UserUcc;
import persistance.dal.DalBackendServices;
import persistance.dao.AnnulationDao;
import persistance.dao.DemandeDao;
import persistance.dao.DemandePaiementDao;
import persistance.dao.DepartementDao;
import persistance.dao.DocDepartDao;
import persistance.dao.DocRetourDao;
import persistance.dao.MobiliteDao;
import persistance.dao.PartenaireDao;
import persistance.dao.PaysDao;
import persistance.dao.UserDao;
import servlet.MaServlet;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;

import java.lang.reflect.InvocationTargetException;

public class Main {
  private static Logger logger = Logger.getLogger(Main.class);

  /**
   * Classe principal.
   * 
   * @param args arguments fournis en paramètre
   * @throws IllegalArgumentException erreur système
   * @throws InvocationTargetException erreur système
   * @throws NoSuchMethodException erreur système
   * @throws SecurityException erreur système
   */
  public static void main(String[] args) throws IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {



    String file;

    if (args.length == 0) {
      file = "properties.props";
    } else {
      file = args[1];
    }

    Config config = new Config(file);
    String url = config.getConfig("UrlConnection");
    String user = config.getConfig("UserConnection");
    String mdp = config.getConfig("PasswordConnection");
    MaServlet maServlet = null;

    try {
      DalBackendServices dalb = (DalBackendServices) Class.forName(config.getConfig("DalServices"))
          .getConstructor(String.class, String.class, String.class).newInstance(url, user, mdp);
      DtoFactory dtoFact =
          (DtoFactory) Class.forName(config.getConfig("DtoFactory")).getConstructor().newInstance();



      // Departement
      DepartementDao departementDao = (DepartementDao) Class
          .forName(config.getConfig("DepartementDao"))
          .getConstructor(DalBackendServices.class).newInstance(dalb);


      // User
      UserDao userDao = (UserDao) Class.forName(config.getConfig("UserDao"))
          .getConstructor(DalBackendServices.class, DtoFactory.class).newInstance(dalb, dtoFact);
      UserUcc userUcc = (UserUcc) Class
          .forName(config.getConfig("UserUcc")).getConstructor(UserDao.class, DtoFactory.class,
              DepartementDao.class, DalBackendServices.class)
          .newInstance(userDao, dtoFact, departementDao, dalb);
      // Annulation
      AnnulationDao annulationDao = (AnnulationDao) Class.forName(config.getConfig("AnnulationDao"))
          .getConstructor(DalBackendServices.class, DtoFactory.class).newInstance(dalb, dtoFact);
      AnnulationUcc annulationUcc = (AnnulationUcc) Class.forName(config.getConfig("AnnulationUcc"))
          .getConstructor(AnnulationDao.class, DalBackendServices.class)
          .newInstance(annulationDao, dalb);

      // Pays
      PaysDao paysDao = (PaysDao) Class.forName(config.getConfig("PaysDao"))
          .getConstructor(DalBackendServices.class, DtoFactory.class).newInstance(dalb, dtoFact);

     

      // Partenaire
      PartenaireDao partenaireDao = (PartenaireDao) Class.forName(config.getConfig("PartenaireDao"))
          .getConstructor(DalBackendServices.class, DtoFactory.class).newInstance(dalb, dtoFact);

      PartenaireUcc partenaireUcc = (PartenaireUcc) Class.forName(config.getConfig("PartenaireUcc"))
          .getConstructor(DalBackendServices.class, PartenaireDao.class,
              PaysDao.class, DepartementDao.class, UserDao.class)
          .newInstance(dalb, partenaireDao, paysDao, departementDao, userDao);

      // Demande
      DemandeDao demandeDao = (DemandeDao) Class.forName(config.getConfig("DemandeDao"))
          .getConstructor(DalBackendServices.class, DtoFactory.class).newInstance(dalb, dtoFact);
      DemandeUcc demandeUcc = (DemandeUcc) Class.forName(config.getConfig("DemandeUcc"))
          .getConstructor(DalBackendServices.class, DemandeDao.class, UserDao.class, 
              PartenaireDao.class, PaysDao.class, DepartementDao.class)
          .newInstance(dalb, demandeDao, userDao, partenaireDao, paysDao, departementDao);

      // Mobilité
      MobiliteDao mobiliteDao = (MobiliteDao) Class.forName(config.getConfig("MobiliteDao"))
          .getConstructor(DalBackendServices.class, DtoFactory.class).newInstance(dalb, dtoFact);


      // DocDepart
      DocDepartDao docDepartDao = (DocDepartDao) Class.forName(config.getConfig("DocDepartDao"))
          .getConstructor(DalBackendServices.class, DtoFactory.class).newInstance(dalb, dtoFact);
      DocDepartUcc docDepartUcc = (DocDepartUcc) Class.forName(config.getConfig("DocDepartUcc"))
          .getConstructor(DalBackendServices.class, DocDepartDao.class,
              UserDao.class, MobiliteDao.class, DepartementDao.class, PaysDao.class)
          .newInstance(dalb, docDepartDao, userDao, mobiliteDao, departementDao, paysDao);

      // DocRetour
      DocRetourDao docRetourDao = (DocRetourDao) Class.forName(config.getConfig("DocRetourDao"))
          .getConstructor(DalBackendServices.class, DtoFactory.class).newInstance(dalb, dtoFact);
      DocRetourUcc docRetourUcc = (DocRetourUcc) Class.forName(config.getConfig("DocRetourUcc"))
          .getConstructor(DalBackendServices.class, DocRetourDao.class,
              UserDao.class, MobiliteDao.class, DepartementDao.class, PaysDao.class)
          .newInstance(dalb, docRetourDao, userDao, mobiliteDao, departementDao, paysDao);


      // DemandePaiement
      DemandePaiementDao demandePaiementDao = (DemandePaiementDao) Class
          .forName(config.getConfig("DemandePaiementDao"))
          .getConstructor(DalBackendServices.class, DtoFactory.class).newInstance(dalb, dtoFact);
      DemandePaiementUcc demandePaiementUcc =
          (DemandePaiementUcc) Class.forName(config.getConfig("DemandePaiementUcc"))
              .getConstructor(DalBackendServices.class, DtoFactory.class, DemandePaiementDao.class,
                  MobiliteDao.class, UserDao.class)
          .newInstance(dalb, dtoFact, demandePaiementDao, mobiliteDao, userDao);


      // Mobilite
      MobiliteUcc mobiliteUcc = (MobiliteUcc) Class.forName(config.getConfig("MobiliteUcc"))
          .getConstructor(DalBackendServices.class, DtoFactory.class, MobiliteDao.class,
              UserDao.class, DemandeDao.class, PartenaireDao.class, AnnulationDao.class,
              PaysDao.class, DepartementDao.class, DocDepartDao.class, DocRetourDao.class,
              DemandePaiementDao.class)
          .newInstance(dalb, dtoFact, mobiliteDao, userDao, demandeDao, partenaireDao,
              annulationDao, paysDao, departementDao, docDepartDao, docRetourDao,
              demandePaiementDao);



      // Cle Cryptage
      String cleSecret = config.getConfig("cleCryptageJWT");

      maServlet = new MaServlet(dtoFact, userUcc,
          partenaireUcc, mobiliteUcc, demandeUcc, demandePaiementUcc, annulationUcc, 
          docDepartUcc, docRetourUcc, cleSecret, logger);


    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
        | InvocationTargetException | NoSuchMethodException | SecurityException
        | ClassNotFoundException err) {
      err.printStackTrace();
    }


    WebAppContext context = new WebAppContext();
    context.setResourceBase("www");
    context.setContextPath("/");
    context.addServlet(new ServletHolder(maServlet), "/v1/*");
    context.addServlet(new ServletHolder(new DefaultServlet()), "/");
    context.setInitParameter("cacheControl", "no-store,no-cache,must-revalidate");
    context.setClassLoader(Thread.currentThread().getContextClassLoader());
    context.setWelcomeFiles(new String[] {"index.html"});
    Server server = new Server(8080);
    server.setHandler(context);

    try {
      server.start();
    } catch (Exception err) {

      err.printStackTrace();
    }


  }
}
