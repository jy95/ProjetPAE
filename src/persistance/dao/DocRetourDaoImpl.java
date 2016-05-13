package persistance.dao;

import biz.biz.DocRetourBiz;
import biz.biz.MobiliteBiz;
import biz.biz.UserBiz;
import biz.dto.DocRetourDto;
import biz.dto.MobiliteDto;
import biz.dto.UserDto;
import biz.factory.DtoFactory;
import exception.BizException;
import exception.FatalException;
import persistance.dal.DalBackendServices;
import persistance.dal.DalServices;
import persistance.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DocRetourDaoImpl implements DocRetourDao {

  private DalBackendServices dalb;
  private DtoFactory dtoFact;

  public DocRetourDaoImpl(DalBackendServices dalb, DtoFactory dtoFact) {
    this.dalb = dalb;
    this.dtoFact = dtoFact;
  }

  @Override
  public void ecrireDocRetour(DocRetourDto docRetour) throws FatalException {

    PreparedStatement ps = dalb.getPreparedStatement(DalBackendServices.ECRIREDOCRETOUR);
    try {
      ps.setBoolean(1, false);
      ps.setBoolean(2, false);
      ps.setBoolean(3, false);
      ps.setBoolean(4, false);
      ps.setNull(5, java.sql.Types.TIMESTAMP);
      ps.setInt(6, ((UserBiz) docRetour.getEtudiant()).getId());
      ps.setInt(7, ((MobiliteBiz) docRetour.getMoblite()).getId());
      ps.setInt(8, ((DocRetourBiz) docRetour).getNumVersion());

      int nbLigne;
      nbLigne = ps.executeUpdate();
      if (nbLigne == 0) {
        ((DalServices) dalb).rollBackTransaction();
        throw new FatalException("L'insert n'a pas pu être effectuée.");
      }

    } catch (SQLException err) {

      throw new FatalException(err.getMessage());
    }

  }

  @Override
  public DocRetourDto lireDocRetour(DocRetourDto docRetour) {
    PreparedStatement ps = dalb.getPreparedStatement(DalBackendServices.LIREDOCRETOUR);
    ResultSet rs;
    try {
      ps.setInt(1, ((DocRetourBiz) docRetour).getId());
      rs = ps.executeQuery();
      if (!rs.next()) {
        rs.close();
        throw new BizException("DocRetour introuvable");
      }
      docRetour = remplirDocRetourDto(rs);
      rs.close();
    } catch (SQLException err) {
      throw new FatalException(err.getMessage());
    }
    return docRetour;
  }

  @Override
  public DocRetourDto modifierDocRetour(DocRetourDto docRetour) {
    ((DalServices) dalb).startTransaction();
    PreparedStatement ps = dalb.getPreparedStatement(DalBackendServices.MODIFIERDOCRETOUR);
    try {
      ps.setBoolean(1, docRetour.getAttestationSejour());
      ps.setBoolean(2, docRetour.getReleveNoteOuCertifStage());
      ps.setBoolean(3, docRetour.getPreuvePassageTest());
      ps.setBoolean(4, docRetour.getRapportFinal());
      if (docRetour.getDateRetour() == null) {
        ps.setNull(5, java.sql.Types.TIMESTAMP);
      } else {
        ps.setTimestamp(5, Util.localDatetimeToTimestamp(docRetour.getDateRetour()));
      }
      ps.setInt(6, ((DocRetourBiz) docRetour).getNumVersion() + 1);
      ps.setInt(7, docRetour.getId());
      int nbLigne;
      nbLigne = ps.executeUpdate();
      if (nbLigne == 0) {
        ((DalServices) dalb).rollBackTransaction();
        throw new FatalException("L'update n'a pas pu être effectuée.");
      }
    } catch (SQLException err) {
      ((DalServices) dalb).rollBackTransaction();
      throw new FatalException(err.getMessage());
    }
    ((DalServices) dalb).commitTransaction();
    return docRetour;
  }

  @Override
  public ArrayList<DocRetourDto> lireDocRetourUser(UserDto userDto) {
    PreparedStatement ps = dalb.getPreparedStatement(DalBackendServices.LIREDOCRETOURUSER);
    ArrayList<DocRetourDto> docs = new ArrayList<DocRetourDto>();
    ResultSet rs;
    try {
      ps.setInt(1, userDto.getId());
      rs = ps.executeQuery();
      while (rs.next()) {
        DocRetourDto docRetourDto = dtoFact.getDocRetourDto();
        docRetourDto = remplirDocRetourDto(rs);
        docs.add(docRetourDto);
      }
      rs.close();
    } catch (SQLException err) {
      throw new FatalException(err.getMessage());
    }
    return docs;
  }

  @Override
  public DocRetourDto lireDocRetourMobilite(MobiliteDto mobiliteDto) {
    PreparedStatement ps = dalb.getPreparedStatement(DalBackendServices.LIREDOCRETOURMOBILITE);
    DocRetourDto docRetourDto;
    ResultSet rs;
    try {
      ps.setInt(1, mobiliteDto.getId());
      rs = ps.executeQuery();
      if (!rs.next()) {
        rs.close();
        throw new BizException("DocRetour introuvable");
      }
      docRetourDto = remplirDocRetourDto(rs);
      rs.close();
    } catch (SQLException err) {
      throw new FatalException(err.getMessage());
    }
    return docRetourDto;
  }

  private DocRetourDto remplirDocRetourDto(ResultSet rs) throws SQLException {
    DocRetourDto docRetour = dtoFact.getDocRetourDto();
    ((DocRetourBiz) docRetour).setPkDocRetour(rs.getInt(1));
    docRetour.setAttestationSejour(rs.getBoolean(2));
    docRetour.setReleveNoteOuCertifStage(rs.getBoolean(3));
    docRetour.setPreuvePassageTest(rs.getBoolean(4));
    docRetour.setRapportFinal(rs.getBoolean(5));
    docRetour.setDateRetour(Util.timestampToLocalDatetime(rs.getTimestamp(6)));
    UserDto userDto = dtoFact.getUserDto();
    ((UserBiz) userDto).setPkUser(rs.getInt(7));
    docRetour.setEtudiant(userDto);
    MobiliteDto mobiliteDto = dtoFact.getMobiliteDto();
    ((MobiliteBiz) mobiliteDto).setPkMobilite(rs.getInt(8));
    ((DocRetourBiz) docRetour).setNumVersion(rs.getInt(9));
    docRetour.setMobilite(mobiliteDto);
    return docRetour;
  }

  @Override
  public ArrayList<DocRetourDto> lireToutDocRetour() {
    PreparedStatement ps = dalb.getPreparedStatement(DalBackendServices.LIRETOUTDOCRETOUR);
    ArrayList<DocRetourDto> docs = new ArrayList<DocRetourDto>();
    ResultSet rs;
    try {
      rs = ps.executeQuery();
      while (rs.next()) {
        DocRetourDto docRetourDto = dtoFact.getDocRetourDto();
        docRetourDto = remplirDocRetourDto(rs);
        docs.add(docRetourDto);
      }
      rs.close();
    } catch (SQLException err) {
      throw new FatalException(err.getMessage());
    }
    return docs;
  }
}
