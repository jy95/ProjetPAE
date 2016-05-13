package persistance.dao;

import biz.biz.DepartementBiz;
import biz.biz.PartenaireBiz;
import biz.biz.PaysBiz;
import biz.biz.UserBiz;
import biz.dto.DepartementDto;
import biz.dto.PartenaireDto;
import biz.enumerate.TypeEntreprise;
import biz.enumerate.TypeStage;
import biz.factory.DtoFactory;
import exception.BizException;
import exception.FatalException;
import persistance.dal.DalBackendServices;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class PartenaireDaoImpl implements PartenaireDao {
  DalBackendServices dalb;
  DtoFactory dtoFact;

  public PartenaireDaoImpl(DalBackendServices dalb, DtoFactory dtoFact) {
    this.dalb = dalb;
    this.dtoFact = dtoFact;
  }

  @Override
  public PartenaireDto lirePartenairePk(PartenaireDto partenaireDto) {
    PreparedStatement ps = dalb.getPreparedStatement(DalBackendServices.LIREPARTENAIREPK);
    ResultSet rs;
    try {
      ps.setInt(1, ((PartenaireBiz) partenaireDto).getId());
      rs = ps.executeQuery();
      if (!rs.next()) {
        rs.close();
        throw new BizException("Partenaire introuvable");
      }
      partenaireDto = remplirPartenaire(rs);
      rs.close();

    } catch (SQLException err) {

      throw new FatalException(err.getMessage() + " erreur de lecture dans la lirePartenairePk ");
    }
    return partenaireDto;
  }

  @Override
  public PartenaireDto ecrirePartenaire(PartenaireDto partenaireDto) throws FatalException {


    PreparedStatement ps = dalb.getPreparedStatement(DalBackendServices.ECRIREPARTENAIRE);
    ResultSet rs;
    try {
      ps.setString(1, partenaireDto.getNomAffaire());
      ps.setString(2, partenaireDto.getNomLegal());
      ps.setInt(3, partenaireDto.getNbrEmploye());
      ps.setString(4, partenaireDto.getAdresse());
      ps.setInt(5, ((UserBiz) partenaireDto.getCreateur()).getId());
      ps.setString(6, ((PaysBiz) partenaireDto.getPays()).getId());
      ps.setString(7, partenaireDto.getRegion());
      ps.setString(8, partenaireDto.getCodePostal());
      ps.setString(9, partenaireDto.getVille());
      ps.setString(10, partenaireDto.getEmail());
      ps.setString(11, partenaireDto.getSiteWeb());
      ps.setString(12, partenaireDto.getTelephone());
      ps.setString(13, TypeStage.typeStageToString(partenaireDto.getTypeStage()));
      ps.setBoolean(14, partenaireDto.getAgree());
      ps.setString(15, partenaireDto.getNomComplet());
      ps.setString(16, TypeEntreprise.typeEntrepriseToString(partenaireDto.getTypeEntreprise()));
      ps.setInt(17, ((PartenaireBiz) partenaireDto).getNumVersion());
      ps.setBoolean(18, partenaireDto.getSupprime());

      int pkPart;
      rs = ps.executeQuery();
      if (!rs.next()) {
        rs.close();
        throw new FatalException("L'insert n'a pas pu être effectué.");
      }
      pkPart = rs.getInt(1);
      rs.close();
      ((PartenaireBiz) partenaireDto).setPkPartenaire(pkPart);
      PreparedStatement ps2 = dalb.getPreparedStatement(
          DalBackendServices.ECRIREDEPARTEMENTPARTENAIRE);

      for (DepartementDto departementDto : partenaireDto.getAllDepartements()) {
        if (departementDto != null) {
          ps2.setInt(1, pkPart);
          ps2.setString(2, ((DepartementBiz) departementDto).getId());
          ps2.setInt(3, 1);

          int nbLigne = 0;
          nbLigne = ps2.executeUpdate();
          if (nbLigne == 0) {
            throw new FatalException("erreur dans getEcrireDepartementPartenaire");
          }
          // ps2.executeQuery() -> pas besoin d'avoir un resultset (il faut juste savoir si cela a
          // fonctionné point.
        }
      }

    } catch (SQLException err) {

      throw new FatalException(err.getMessage());
    }

    return partenaireDto;
  }

  @Override
  public ArrayList<PartenaireDto> rechercherPartenaire(String champRecherche) {
    PreparedStatement ps = dalb.getPreparedStatement(DalBackendServices.RECHERCHERPARTENAIRE);
    ArrayList<PartenaireDto> partlist = new ArrayList<PartenaireDto>();

    try {
      ps.setString(1, "%" + champRecherche + "%");
      ps.setString(2, "%" + champRecherche + "%");
      ps.setString(3, "%" + champRecherche + "%");
      ps.setString(4, "%" + champRecherche + "%");

      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        PartenaireDto part = dtoFact.getPartenaireDto();
        part = remplirPartenaire(rs);
        partlist.add(part);
      }
      rs.close();

    } catch (SQLException err) {
      throw new FatalException("erreur survenue dans PartDao");
    }

    return partlist;
  }

  /**
   * remplis les informtions d'un partenaire.
   * 
   * @param rs Resultset
   */
  public PartenaireDto remplirPartenaire(ResultSet rs) {
    PartenaireBiz partenaire = (PartenaireBiz) dtoFact.getPartenaireDto();

    try {
      partenaire.setPkPartenaire(rs.getInt(1));
      partenaire.setNomAffaire(rs.getString(2));
      partenaire.setNomLegal(rs.getString(3));
      partenaire.setNomComplet(rs.getString(4));

      partenaire.setNbrEmploye(rs.getInt(5));
      partenaire.setAdresse(rs.getString(6));

      UserBiz user = (UserBiz) dtoFact.getUserDto();
      user.setPkUser(rs.getInt(7));
      partenaire.setCreateur(user);
      PaysBiz pays = (PaysBiz) dtoFact.getPaysDto();
      pays.setPkPays(rs.getString(8));
      partenaire.setPays(pays);
      partenaire.setRegion(rs.getString(9));
      partenaire.setCodePostal(rs.getString(10));
      partenaire.setVille(rs.getString(11));
      partenaire.setEmail(rs.getString(12));
      partenaire.setSiteWeb(rs.getString(13));
      partenaire.setTelephone(rs.getString(14));
      partenaire.setTypeStage(TypeStage.stringToTypeStage(rs.getString(15)));
      partenaire.setAgree(rs.getBoolean(16));
      partenaire.setTypeEntreprise(TypeEntreprise.stringToTypeEntreprise(rs.getString(17)));
      ((PartenaireBiz) partenaire).setNumVersion(rs.getInt(18));
      partenaire.setSupprime(rs.getBoolean(19));

    } catch (SQLException err) {

      throw new FatalException("erreur survenue dans PartDao");
    }



    return partenaire;

  }

  @Override
  public PartenaireDto lireAllDepartementsPartenaire(PartenaireDto partenaireDto) {
    PreparedStatement ps = dalb.getPreparedStatement(
        DalBackendServices.LIREALLDEPARTEMENTPARTENAIRE);
    try {
      ps.setInt(1, ((PartenaireBiz) partenaireDto).getId());
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        DepartementDto empty = dtoFact.getDepartementDto();
        ((DepartementBiz) empty).setPkDepartement(rs.getString(1));
        empty.setNom(rs.getString(2));
        partenaireDto.setDepartement(empty);
      }
      rs.close();
    } catch (SQLException err) {
      throw new FatalException(err.getMessage());
    }
    return partenaireDto;
  }

  @Override
  public HashMap<String, PartenaireDto> getPartenaireAgreeParDepartement(
      DepartementDto departement) {
    PreparedStatement ps = dalb.getPreparedStatement(
        DalBackendServices.PARTENAIREAGREEPARDEPARTEMENT);
    HashMap<String, PartenaireDto> map = new HashMap<>();
    ResultSet rs;
    try {
      ps.setString(1, ((DepartementBiz) departement).getId());
      rs = ps.executeQuery();
      if (!rs.next()) {
        throw new BizException("pas de partenaire agrée");
      }
      do {
        PartenaireDto partenaireDto = dtoFact.getPartenaireDto();
        ((PartenaireBiz) partenaireDto).setPkPartenaire(rs.getInt(1));
        partenaireDto.setNomAffaire(rs.getString(2));
        partenaireDto.setAgree(true);
        map.put(String.valueOf(rs.getInt(1)), partenaireDto);
      } while (rs.next());
      rs.close();
    } catch (SQLException err) {
      throw new FatalException();
    }
    return map;
  }

  @Override
  public boolean mettreAjourPartenaire(PartenaireDto partenaireDto) {
    PreparedStatement ps = dalb.getPreparedStatement(
        DalBackendServices.MODIFIERDRAPEAUSUPPRIMEPARTENAIRE);

    try {
      ps.setBoolean(1, partenaireDto.getSupprime());
      ps.setInt(2, ((PartenaireBiz) partenaireDto).getNumVersion() + 1);
      ps.setInt(3, ((PartenaireBiz) partenaireDto).getId());
      int nbLigne;
      nbLigne = ps.executeUpdate();
      if (nbLigne == 0) {

        throw new FatalException("L'update n'a pas pu être effectuée.");
      }
      return true;
    } catch (SQLException err) {

      throw new FatalException(err.getMessage());
    }

  }

  @Override
  public ArrayList<PartenaireDto> listerPartenairessupprimes() {

    PreparedStatement ps = dalb.getPreparedStatement(DalBackendServices.LIREPARTENAIRESSUPPRIME);
    ArrayList<PartenaireDto> partlist = new ArrayList<PartenaireDto>();

    try {

      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        PartenaireDto part = dtoFact.getPartenaireDto();
        part = remplirPartenaire(rs);
        partlist.add(part);
      }
      rs.close();

    } catch (SQLException err) {
      throw new FatalException("erreur survenue dans PartDao");
    }

    return partlist;

  }

  @Override
  public boolean verifierPartenaireExistant(PartenaireDto partenaireDto) {
    PreparedStatement ps = dalb.getPreparedStatement(DalBackendServices.CHECKPARTENAIREEXISTANT);
    boolean check = false;

    try {
      ps.setString(1, partenaireDto.getNomComplet());
      ResultSet rs = ps.executeQuery();

      if (rs.next()) {
        ((PartenaireBiz) partenaireDto).setPkPartenaire(rs.getInt(1));
        check = true;
      }
      rs.close();
      return check;
    } catch (SQLException err) {
      throw new FatalException("erreur survenue dans PartDao");
    }

  }



}
