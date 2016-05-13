package biz.ucc;

import biz.biz.UserBiz;
import biz.dto.UserDto;
import biz.enumerate.TypeUser;
import biz.factory.DtoFactory;
import biz.util.Cryptage;
import biz.util.Util;
import exception.BizException;
import exception.FatalException;
import persistance.dal.DalBackendServices;
import persistance.dal.DalServices;
import persistance.dao.DepartementDao;
import persistance.dao.UserDao;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class UserUccImpl implements UserUcc {
  private UserDao userDao;
  private DepartementDao departementDao;
  private DtoFactory dtoFact;
  private DalBackendServices dalb;

  /**
   * constructeur UserUccImpl.
   * 
   * @param userDao userDao
   * @param dtoFact dtoFact
   * @param departementDao departementDao
   */
  public UserUccImpl(UserDao userDao, DtoFactory dtoFact, DepartementDao departementDao,
      DalBackendServices dalb) {
    this.userDao = userDao;
    this.departementDao = departementDao;
    this.dtoFact = dtoFact;
    this.dalb = dalb;
  }

  @Override
  public UserDto seConnecter(UserDto user) throws BizException {

    UserDto user2 = dtoFact.getUserDto();
    user2.setPseudo(user.getPseudo());

    try {
      ((DalServices) dalb).openConnection();

      user2 = userDao.lireUserPseudo(user2);
      if (!((UserBiz) user2).checkValidePassword(user.getMdp())) {
        throw new BizException("Combinaison login, mdp incorrecte.");
      }

    } finally {
      ((DalServices) dalb).closeConnection();
    }
    return user2;

  }

  @Override
  public void creerUtilisateur(UserDto user) {
    user.setDateInscription(LocalDateTime.now());
    try {
      ((DalServices) dalb).openConnection();
      ((UserBiz) user).checkUser();
      // si 0 etudiant d'office prof
      if (userDao.checkNoUser()) {
        user.setTypeUser(TypeUser.PROF);
      }
      // Si le pseudo est déjà dans la db, throw
      if (!userDao.checkPseudoUnique(user)) {
        throw new BizException("Pseudo déjà utilisé!");
      }

      ((DalServices) dalb).startTransaction();
      String salt = Cryptage.newSalt();
      String crypt = Cryptage.hash(user.getMdp(), salt);
      user.setMdp(crypt);
      userDao.ecrireUser(user);
      ((DalServices) dalb).commitTransaction();
    } catch (FatalException err) {
      ((DalServices) dalb).rollBackTransaction();
      throw err;

    } finally {
      ((DalServices) dalb).closeConnection();
    }

  }

  @Override
  public UserDto modifierUser(UserDto user) {
    ((UserBiz) user).checkUser();

    try {
      ((DalServices) dalb).openConnection();

      int numVersionDb = ((UserBiz) userDao.lireUserPk(user)).getNumVersion();
      if (numVersionDb != ((UserBiz) user).getNumVersion()) {
        throw new BizException("NumVersion differents");
      }

      ((DalServices) dalb).startTransaction();
      user = userDao.modifierUser(user);
      ((DalServices) dalb).commitTransaction();
    } catch (FatalException err) {
      ((DalServices) dalb).rollBackTransaction();
      throw err;

    } finally {
      ((DalServices) dalb).closeConnection();
    }

    return user;

  }

  @Override
  public ArrayList<UserDto> rechercherUser(String champRecherche) {
    ArrayList<UserDto> listUser;

    try {
      ((DalServices) dalb).openConnection();
      if ("".equals(champRecherche)) {
        listUser = userDao.lireAllUser();

      } else {
        boolean check = Util.checkString(champRecherche);

        if (check) {
          listUser = userDao.rechercherUser(champRecherche);
        } else {
          listUser = userDao.lireAllUser();
        }
      }

      for (UserDto userDto : listUser) {
        userDto
            .setDepartement(UtilUcc.remplirDepartement(userDto.getDepartement(), departementDao));
      }

    } finally {
      ((DalServices) dalb).closeConnection();
    }

    return listUser;
  }

  @Override
  public UserDto rechercherUserPseudo(UserDto user) {

    UserDto user2 = dtoFact.getUserDto();
    user2.setPseudo(user.getPseudo());
    try {
      ((DalServices) dalb).openConnection();
      user2 = userDao.lireUserPseudo(user2);
      ((UserBiz) user2).checkUser();
      user2.setDepartement(UtilUcc.remplirDepartement(user2.getDepartement(), departementDao));
    } finally {
      ((DalServices) dalb).closeConnection();
    }
    return user2;
  }

  @Override
  public boolean changePermission(UserDto[] users, TypeUser type) {

    // virer si mauvaise num Version
    Boolean bool = false;
    try {
      ((DalServices) dalb).openConnection();
      ((DalServices) dalb).startTransaction();
      for (UserDto user : users) {
        int numVersionDb = ((UserBiz) userDao.lireUserPk(user)).getNumVersion();

        if (numVersionDb != ((UserBiz) user).getNumVersion()) {

          throw new BizException("NumVersion differents");
        }
      }

      bool = userDao.changeUsersRights(users, type);

      ((DalServices) dalb).commitTransaction();
    } catch (FatalException err) {
      ((DalServices) dalb).rollBackTransaction();
      throw err;
    } finally {
      ((DalServices) dalb).closeConnection();
    }

    return bool;
  }

}
