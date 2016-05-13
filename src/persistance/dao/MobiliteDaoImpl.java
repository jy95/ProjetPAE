package persistance.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import biz.biz.AnnulationBiz;
import biz.biz.DemandeBiz;
import biz.biz.MobiliteBiz;
import biz.biz.PartenaireBiz;
import biz.biz.PaysBiz;
import biz.biz.UserBiz;
import biz.dto.MobiliteDto;
import biz.dto.UserDto;
import biz.enumerate.EtatMobilite;
import biz.enumerate.TypeStage;
import biz.factory.DtoFactory;
import exception.BizException;
import exception.FatalException;
import persistance.dal.DalBackendServices;
import persistance.dal.DalServices;

public class MobiliteDaoImpl implements MobiliteDao {

  DalBackendServices dalb;
  DtoFactory dtoFact;

  public MobiliteDaoImpl(DalBackendServices dalb, DtoFactory dtoFact) {
    this.dalb = dalb;
    this.dtoFact = dtoFact;
  }

  @Override
  public MobiliteDto lireMobilitePk(MobiliteDto mobiliteDto) {
    PreparedStatement ps = dalb.getPreparedStatement(DalBackendServices.LIREMOBILITEPK);
    MobiliteDto mobi;
    try {
      ps.setInt(1, mobiliteDto.getId());
      ResultSet rs = ps.executeQuery();
      if (!rs.next()) {
        throw new BizException("aucune mobilite trouvee");
      }
      mobi = remplirMobilite(rs);
      rs.close();
    } catch (SQLException err) {
      throw new FatalException(err.getMessage());
    }
    return mobi;
  }

  @Override
  public int ecrireMobilite(MobiliteDto mobiliteDto) throws FatalException {
    PreparedStatement ps = dalb.getPreparedStatement(DalBackendServices.ECRIREMOBILITE);
    int pk = 0;
    try {

      int pkUs = mobiliteDto.getEtudiant().getId();
      ps.setInt(1, pkUs);
      int pkDem = mobiliteDto.getDemande().getId();
      ps.setInt(2, pkDem);
      String codeP = mobiliteDto.getPays().getId();
      ps.setString(3, codeP);
      ps.setString(4, mobiliteDto.getVille());
      int pkPart = mobiliteDto.getPartenaire().getId();
      ps.setInt(5, pkPart);
      String etatMb = EtatMobilite.etatMobiliteToString(mobiliteDto.getEtat()); // qui a fait etat ?
      ps.setString(6, etatMb);
      if (mobiliteDto.getAnnulation() != null && mobiliteDto.getAnnulation().getId() != 0) {
        int numAnu = mobiliteDto.getAnnulation().getId();
        ps.setInt(7, numAnu);
      } else {
        ps.setNull(7, java.sql.Types.INTEGER);
      }
      ps.setString(8, mobiliteDto.getAnneeAcademique());
      ps.setBoolean(9, mobiliteDto.getProEco());
      ps.setBoolean(10, mobiliteDto.getMobilityTool());
      ps.setString(11, mobiliteDto.getStage().name());
      ps.setBoolean(12, mobiliteDto.getMobi());
      ps.setInt(13, mobiliteDto.getQuadri());
      ps.setInt(14, ((MobiliteBiz) mobiliteDto).getNumVersion());
      ResultSet rs;
      rs = ps.executeQuery();
      if (rs.next()) {
        pk = rs.getInt(1);
        rs.close();
      } else {
        ((DalServices) dalb).rollBackTransaction();
        rs.close();
        throw new FatalException("L'insert n'a pas pu être effectuée.");
      }
      ps.close();
      ((MobiliteBiz) mobiliteDto).setPkMobilite(pk);
    } catch (SQLException err) {
      throw new FatalException(err.getMessage());
    }
    return pk;
  }

  @Override
  public MobiliteDto modifierMobilite(MobiliteDto mobiliteDto) throws FatalException {
    PreparedStatement ps;
    ps = dalb.getPreparedStatement(DalBackendServices.MODIFIERMOBILITE);

    int pkUs = ((UserBiz) mobiliteDto.getEtudiant()).getId();
    try {
      ps.setInt(1, pkUs);
      int pkDem = ((DemandeBiz) mobiliteDto.getDemande()).getId();
      ps.setInt(2, pkDem);
      String codeP = ((PaysBiz) mobiliteDto.getPays()).getId();
      ps.setString(3, codeP);
      ps.setString(4, mobiliteDto.getVille());
      int pkPart = ((PartenaireBiz) mobiliteDto.getPartenaire()).getId();
      ps.setInt(5, pkPart);
      String etatMb = EtatMobilite.etatMobiliteToString(mobiliteDto.getEtat());
      ps.setString(6, etatMb);
      if (mobiliteDto.getAnnulation() != null && mobiliteDto.getAnnulation().getId() != 0) {
        int numAnu = mobiliteDto.getAnnulation().getId();
        ps.setInt(7, numAnu);
      } else {
        ps.setNull(7, java.sql.Types.INTEGER);
      }
      ps.setString(8, mobiliteDto.getAnneeAcademique());
      ps.setBoolean(9, mobiliteDto.getProEco());
      ps.setBoolean(10, mobiliteDto.getMobilityTool());
      ps.setBoolean(11, mobiliteDto.getMobi());
      ps.setString(12, TypeStage.typeStageToString(mobiliteDto.getStage()));
      ps.setInt(13, mobiliteDto.getQuadri());
      ps.setInt(14, ((MobiliteBiz) mobiliteDto).getNumVersion() + 1);
      ps.setInt(15, mobiliteDto.getId());
      int nbLigne;
      nbLigne = ps.executeUpdate();
      if (nbLigne == 0) {

        throw new FatalException("L'update n'a pas pu être effectuée.");
      }
    } catch (SQLException err) {

      throw new FatalException(err.getMessage());
    }

    return mobiliteDto;
  }

  @Override
  public ArrayList<MobiliteDto> rechercherMobilite(EtatMobilite etat, String anneeAcademique) {

    ArrayList<MobiliteDto> listMobi = new ArrayList<MobiliteDto>();
    PreparedStatement ps = dalb.getPreparedStatement(DalBackendServices.RECHERCHERMOBILITE);
    ResultSet rs;

    try {

      ps.setString(1, etat.getEtat());
      ps.setString(2, "%" + anneeAcademique + "%");

      rs = ps.executeQuery();

      while (rs.next()) {

        MobiliteDto mobiDto = dtoFact.getMobiliteDto();
        mobiDto = remplirMobilite(rs);
        listMobi.add(mobiDto);

      }
      rs.close();
    } catch (Exception err) {
      throw new FatalException("erreur dans le DaoImpl ");
    }

    return listMobi;
  }

  /**
   * remplis une mobilite.
   * 
   * @param rs Resultset
   * @return MobiliteDto
   */
  public MobiliteDto remplirMobilite(ResultSet rs) {

    MobiliteBiz mobi = (MobiliteBiz) dtoFact.getMobiliteDto();

    try {
      mobi.setPkMobilite(rs.getInt(1));
      UserBiz user = (UserBiz) dtoFact.getUserDto();
      user.setPkUser(rs.getInt(2));
      mobi.setEtudiant(user);

      DemandeBiz demande = (DemandeBiz) dtoFact.getDemandeDto();
      demande.setPkDemande(rs.getInt(3));
      mobi.setDemande(demande);

      PaysBiz pays = (PaysBiz) dtoFact.getPaysDto();
      pays.setPkPays(rs.getString(4));
      mobi.setPays(pays);

      mobi.setVille(rs.getString(5));

      PartenaireBiz part = (PartenaireBiz) dtoFact.getPartenaireDto();
      part.setPkPartenaire(rs.getInt(6));
      mobi.setPartenaire(part);
      mobi.setEtat(EtatMobilite.stringToEtatMobilite(rs.getString(7)));

      AnnulationBiz annulation = (AnnulationBiz) dtoFact.getAnnulationDto();
      annulation.setPkAnnulation(rs.getInt(8));
      mobi.setAnnulation(annulation);

      mobi.setAnneeAcademique(rs.getString(9));
      mobi.setProEco(rs.getBoolean(10));
      mobi.setMobilityTool(rs.getBoolean(11));
      mobi.setMobi(rs.getBoolean(12));
      mobi.setStage(TypeStage.stringToTypeStage(rs.getString(13)));
      mobi.setQuadri(rs.getInt(14));
      ((MobiliteBiz) mobi).setNumVersion(rs.getInt(15));
    } catch (SQLException err) {
      throw new FatalException(err.getMessage() + "erreur dans mobilite Dao");
    }


    return mobi;

  }

  @Override
  public ArrayList<MobiliteDto> rechercherMobiliteStud(UserDto user) {

    ArrayList<MobiliteDto> listMobi = new ArrayList<MobiliteDto>();
    PreparedStatement ps = dalb.getPreparedStatement(DalBackendServices.LIREALLMOBSTUD);
    ResultSet rs;

    try {
      ps.setInt(1, ((UserBiz) user).getId());
      rs = ps.executeQuery();


      while (rs.next()) {

        MobiliteDto mobiDto = dtoFact.getMobiliteDto();
        mobiDto = remplirMobilite(rs);
        listMobi.add(mobiDto);

      }
      rs.close();
    } catch (Exception err) {
      throw new FatalException("erreur dans le DaoImpl ");
    }

    return listMobi;
  }

  @Override
  public ArrayList<MobiliteDto> lireToutMobilite() {
    ArrayList<MobiliteDto> listMobi = new ArrayList<MobiliteDto>();
    PreparedStatement ps = dalb.getPreparedStatement(DalBackendServices.LIRETOUTMOBILITE);
    ResultSet rs;

    try {
      rs = ps.executeQuery();

      while (rs.next()) {
        MobiliteDto mobiDto = dtoFact.getMobiliteDto();
        mobiDto = remplirMobilite(rs);
        listMobi.add(mobiDto);
      }
      rs.close();
    } catch (Exception err) {
      throw new FatalException("erreur dans le DaoImpl ");
    }
    return listMobi;
  }

  @Override
  public ArrayList<MobiliteDto> lireMobilitesAnnulees() {
    ArrayList<MobiliteDto> listMobi = new ArrayList<MobiliteDto>();
    PreparedStatement ps = dalb.getPreparedStatement(DalBackendServices.LIREMOBILITESANNULEES);
    ResultSet rs;

    try {
      rs = ps.executeQuery();

      while (rs.next()) {
        MobiliteDto mobiDto = dtoFact.getMobiliteDto();
        mobiDto = remplirMobilite(rs);
        listMobi.add(mobiDto);
      }
      rs.close();
    } catch (Exception err) {
      throw new FatalException("erreur dans le DaoImpl ");
    }
    return listMobi;
  }
}
