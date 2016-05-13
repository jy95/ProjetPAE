package persistance.dao;

import biz.biz.DepartementBiz;
import biz.biz.UserBiz;
import biz.dto.DepartementDto;
import biz.dto.UserDto;
import biz.enumerate.Sexe;
import biz.enumerate.Titre;
import biz.enumerate.TypeUser;
import biz.factory.DtoFactory;
import exception.BizException;
import exception.FatalException;
import persistance.dal.DalBackendServices;
import persistance.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDaoImpl implements UserDao {

  private DalBackendServices dalb;
  private DtoFactory dtoFact;

  public UserDaoImpl(DalBackendServices dalb, DtoFactory dtoFact) {
    this.dalb = dalb;
    this.dtoFact = dtoFact;
  }

  @Override
  public boolean checkNoUser() {

    PreparedStatement ps = dalb.getPreparedStatement(DalBackendServices.CHECKNOUSER);

    try {
      ResultSet rs = ps.executeQuery();
      if (!rs.next()) {
        rs.close();
        return true;
      }
      rs.close();

    } catch (SQLException err) {
      throw new FatalException(err.getMessage());
    }
    return false;
  }

  @Override
  public boolean checkPseudoUnique(UserDto userDto) {
    PreparedStatement ps = dalb.getPreparedStatement(DalBackendServices.CHECKPSEUDOUNIQUE);
    try {
      ps.setString(1, userDto.getPseudo());
      ResultSet rs = ps.executeQuery();
      if (!rs.next()) {
        rs.close();
        return true;
      }
      rs.close();

    } catch (SQLException err) {
      throw new FatalException(err.getMessage());
    }
    return false;
  }

  @Override
  public UserDto lireUserPk(UserDto userDto) {
    PreparedStatement ps = dalb.getPreparedStatement(DalBackendServices.LIREUSERPK);
    ResultSet rs;
    UserBiz user;
    try {
      ps.setInt(1, ((UserBiz) userDto).getId());
      rs = ps.executeQuery();
      if (!rs.next()) {
        throw new BizException("Aucun resultat trouvé ");
      }
      user = (UserBiz) remplirUser(rs);
      rs.close();

    } catch (SQLException err) {
      throw new FatalException(err.getMessage());
    }

    return user;
  }

  @Override
  public UserDto lireUserPseudo(UserDto userDto) {
    PreparedStatement ps = dalb.getPreparedStatement(DalBackendServices.LIREUSERPSEUDO);
    ResultSet rs;
    UserDto user;
    try {
      ps.setString(1, userDto.getPseudo());
      rs = ps.executeQuery();
      if (!rs.next()) {
        throw new BizException("Aucun resultat trouvé ");
      }
      user = remplirUser(rs);
      rs.close();

    } catch (SQLException err) {
      throw new FatalException(err.getMessage());
    }

    return user;
  }

  @Override
  public void ecrireUser(UserDto userDto) throws FatalException {

    PreparedStatement ps = dalb.getPreparedStatement(DalBackendServices.ECRIREUSER);

    try {
      ps.setString(1, userDto.getPseudo());
      ps.setString(2, userDto.getMdp());
      ps.setString(3, userDto.getNom());
      ps.setString(4, userDto.getPrenom());
      ps.setString(5, ((DepartementBiz) userDto.getDepartement()).getId());
      ps.setDate(6, Util.localDateToDate(userDto.getDateNaissance()));
      ps.setString(7, userDto.getAdresse());
      ps.setString(8, userDto.getTelephone());
      ps.setString(9, userDto.getEmail());
      ps.setString(10, userDto.getNationalite());
      ps.setString(11, Titre.titreToString(userDto.getTitre()));
      ps.setString(12, Sexe.sexeToString(userDto.getSexe()));
      ps.setInt(13, userDto.getnbAnneeEns());
      ps.setString(14, userDto.getTitulaireCompte());
      ps.setString(15, userDto.getNumCompte());
      ps.setString(16, userDto.getNomBanque());
      ps.setString(17, userDto.getCodeBic());
      ps.setTimestamp(18, Util.localDatetimeToTimestamp(userDto.getDateInscription()));
      ps.setString(19, TypeUser.typeUserToString(userDto.getTypeUser()));
      ps.setInt(20, ((UserBiz) userDto).getNumVersion());
      int nbLigne;
      nbLigne = ps.executeUpdate();
      if (nbLigne == 0) {

        throw new FatalException("L'insert n'a pas pu être effectuée.");
      }

    } catch (SQLException err) {

      throw new FatalException(err.getMessage());
    }


  }

  @Override
  public UserDto modifierUser(UserDto userDto) throws FatalException {
    PreparedStatement ps = dalb.getPreparedStatement(DalBackendServices.MODIFIERUSER);

    try {
      ps.setString(1, userDto.getMdp());
      ps.setString(2, userDto.getNom());
      ps.setString(3, userDto.getPrenom());
      ps.setString(4, ((DepartementBiz) userDto.getDepartement()).getId());
      ps.setDate(5, Util.localDateToDate(userDto.getDateNaissance()));
      ps.setString(6, userDto.getAdresse());
      ps.setString(7, userDto.getTelephone());
      ps.setString(8, userDto.getEmail());
      ps.setString(9, userDto.getNationalite());
      ps.setString(10, Titre.titreToString(userDto.getTitre()));
      ps.setString(11, Sexe.sexeToString(userDto.getSexe()));
      ps.setInt(12, userDto.getnbAnneeEns());
      ps.setString(13, userDto.getTitulaireCompte());
      ps.setString(14, userDto.getNumCompte());
      ps.setString(15, userDto.getNomBanque());
      ps.setString(16, userDto.getCodeBic());
      ps.setString(17, TypeUser.typeUserToString(userDto.getTypeUser()));
      ps.setInt(18, ((UserBiz) userDto).getNumVersion() + 1);
      ps.setInt(19, ((UserBiz) userDto).getId());

      int nbLigne;
      nbLigne = ps.executeUpdate();
      if (nbLigne == 0) {

        throw new FatalException("La modification n'a pas pu être effectuée.");
      }

    } catch (SQLException err) {

      throw new FatalException(err.getMessage());
    }

    return userDto;
  }

  @Override
  public ArrayList<UserDto> rechercherUser(String champRecherche) {
    PreparedStatement ps = dalb.getPreparedStatement(DalBackendServices.RECHERCHERUSER);
    ResultSet rs;
    UserDto userDto;
    ArrayList<UserDto> listUser = new ArrayList<UserDto>();
    try {
      ps.setString(1, "%" + champRecherche + "%");
      ps.setString(2, "%" + champRecherche + "%");

      rs = ps.executeQuery();

      while (rs.next()) {
        userDto = remplirUser(rs);
        listUser.add(userDto);
      }
      rs.close();

    } catch (SQLException err) {
      throw new FatalException("la recherche n'a pas put être effectuée");
    }
    return listUser;
  }

  @Override
  public ArrayList<UserDto> lireAllUser() {
    PreparedStatement ps = dalb.getPreparedStatement(DalBackendServices.LIREALLUSER);
    UserBiz user;
    ArrayList<UserDto> listUser = new ArrayList<UserDto>();
    try {
      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        user = (UserBiz) remplirUser(rs);
        listUser.add(user);
      }
      rs.close();
    } catch (SQLException err) {
      throw new FatalException(err.getMessage());
    }
    return listUser;
  }

  /**
   * remplir information utilisateur.
   * 
   * @param rs Resultset
   * @return UserDto l'UserDTO
   */
  public UserDto remplirUser(ResultSet rs) {
    UserBiz user = (UserBiz) dtoFact.getUserDto();
    try {

      user.setPkUser(rs.getInt(1));
      user.setPseudo(rs.getString(2));
      user.setMdp(rs.getString(3));
      user.setNom(rs.getString(4));
      user.setPrenom(rs.getString(5));
      DepartementDto departementDto = dtoFact.getDepartementDto();
      ((DepartementBiz) departementDto).setPkDepartement(rs.getString(6));
      user.setDepartement(departementDto);
      user.setDateNaissance(Util.dateToLocalDate(rs.getDate(7)));
      user.setAdresse(rs.getString(8));
      user.setTelephone(rs.getString(9));
      user.setEmail(rs.getString(10));
      user.setNationalite(rs.getString(11));
      user.setTitre(Titre.stringToTitre(rs.getString(12)));
      user.setSexe(Sexe.stringToSexe(rs.getString(13)));
      user.setnbAnneeEns(rs.getInt(14));
      user.setTitulaireCompte(rs.getString(15));
      user.setNumCompte(rs.getString(16));
      user.setNomBanque(rs.getString(17));
      user.setCodeBic(rs.getString(18));
      user.setDateInscription(Util.timestampToLocalDatetime(rs.getTimestamp((19))));
      user.setTypeUser(TypeUser.stringToTypeUser(rs.getString(20)));
      user.setNumVersion(rs.getInt(21));
    } catch (SQLException err) {
      throw new FatalException(err.getMessage());
    }

    return user;
  }

  @Override
  public boolean changeUsersRights(UserDto[] users, TypeUser type) throws FatalException {
    PreparedStatement ps = dalb.getPreparedStatement(DalBackendServices.CHANGEUSERSRIGHTS);
    try {
      for (UserDto user : users) {

        ps.setString(1, type.name());
        ps.setInt(2, ((UserBiz) user).getNumVersion());
        ps.setInt(3, ((UserBiz) user).getId());
        int check = ps.executeUpdate();
        if (check == 0) {
          throw new BizException("Mise à jour ratée");
        }
      }

    } catch (SQLException err) {
      throw new FatalException(err.getMessage());
    }
    return true;
  }



}
