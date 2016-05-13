package persistance.dao;

import biz.biz.DocDepartBiz;
import biz.biz.MobiliteBiz;
import biz.biz.UserBiz;
import biz.dto.DocDepartDto;
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

public class DocDepartDaoImpl implements DocDepartDao {

  private DalBackendServices dalb;
  private DtoFactory dtoFact;

  public DocDepartDaoImpl(DalBackendServices dalb, DtoFactory dtoFact) {
    this.dalb = dalb;
    this.dtoFact = dtoFact;
  }

  @Override
  public void ecrireDocDepart(DocDepartDto docDepart) throws FatalException {
    PreparedStatement ps = dalb.getPreparedStatement(DalBackendServices.ECRIREDOCDEPART);

    try {
      ps.setBoolean(1, false);
      ps.setBoolean(2, false);
      ps.setBoolean(3, false);
      ps.setBoolean(4, false);
      ps.setBoolean(5, false);
      ps.setNull(6, java.sql.Types.TIMESTAMP);
      ps.setInt(7, ((UserBiz) docDepart.getEtudiant()).getId());
      ps.setInt(8, ((MobiliteBiz) docDepart.getMobilite()).getId());
      ps.setInt(9, ((DocDepartBiz) docDepart).getNumVersion());

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
  public DocDepartDto lireDocDepartPk(DocDepartDto docDepart) {
    PreparedStatement ps = dalb.getPreparedStatement(DalBackendServices.LIREDOCDEPART);
    ResultSet rs;
    try {
      ps.setInt(1, ((DocDepartBiz) docDepart).getId());
      rs = ps.executeQuery();
      if (!rs.next()) {
        throw new BizException("DocDepart introuvable");
      }
      docDepart = remplirDocDepartDto(rs);
      rs.close();


    } catch (SQLException err) {
      throw new FatalException("L'update n'a pas pu être effectuée.");
    }
    return docDepart;
  }

  @Override
  public DocDepartDto modifierDocDepart(DocDepartDto docDepart) {
    ((DalServices) dalb).startTransaction();
    PreparedStatement ps = dalb.getPreparedStatement(DalBackendServices.MODIFIERDOCDEPART);

    try {
      ps.setBoolean(1, docDepart.getContratBourse());
      ps.setBoolean(2, docDepart.getCharteEtudiant());
      ps.setBoolean(3, docDepart.getConventionStageOuEtude());
      ps.setBoolean(4, docDepart.getPreuveTestLangue());
      ps.setBoolean(5, docDepart.getDocEngagement());
      if (docDepart.getDateDepart() == null) {
        ps.setNull(6, java.sql.Types.TIMESTAMP);
      } else {
        ps.setTimestamp(6, Util.localDatetimeToTimestamp(docDepart.getDateDepart()));
      }
      ps.setInt(7, ((DocDepartBiz) docDepart).getNumVersion());
      ps.setInt(8, docDepart.getId());
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
    return docDepart;
  }

  private DocDepartDto remplirDocDepartDto(ResultSet rs) throws SQLException {
    DocDepartDto docDepart = dtoFact.getDocDepartDto();
    ((DocDepartBiz) docDepart).setPkDocDepart(rs.getInt(1));
    docDepart.setContratBourse(rs.getBoolean(2));
    docDepart.setCharteEtudiant(rs.getBoolean(3));
    docDepart.setConventionStageOuEtude(rs.getBoolean(4));
    docDepart.setPreuveTestLangue(rs.getBoolean(5));
    docDepart.setDocEngagement(rs.getBoolean(6));
    docDepart.setDateDepart(Util.timestampToLocalDatetime(rs.getTimestamp(7)));
    UserDto userDto = dtoFact.getUserDto();
    ((UserBiz) userDto).setPkUser(rs.getInt(8));
    docDepart.setEtudiant(userDto);
    MobiliteDto mobiliteDto = dtoFact.getMobiliteDto();
    ((MobiliteBiz) mobiliteDto).setPkMobilite(rs.getInt(9));
    ((DocDepartBiz) docDepart).setNumVersion(rs.getInt(10));

    docDepart.setMobilite(mobiliteDto);
    return docDepart;
  }

  @Override
  public ArrayList<DocDepartDto> lireToutDocDepart() {
    PreparedStatement ps = dalb.getPreparedStatement(DalBackendServices.LIRETOUTDOCDEPART);
    ArrayList<DocDepartDto> docs = new ArrayList<DocDepartDto>();
    ResultSet rs;
    try {
      rs = ps.executeQuery();
      while (rs.next()) {
        DocDepartDto docDepartDto = dtoFact.getDocDepartDto();
        docDepartDto = remplirDocDepartDto(rs);
        docs.add(docDepartDto);
      }
      rs.close();
    } catch (SQLException err) {
      throw new FatalException(err.getMessage());
    }
    return docs;
  }

  @Override
  public ArrayList<DocDepartDto> lireDocDepartUser(UserDto userDto) {
    PreparedStatement ps = dalb.getPreparedStatement(DalBackendServices.LIREDOCDEPARTUSER);
    ArrayList<DocDepartDto> docs = new ArrayList<DocDepartDto>();
    ResultSet rs;
    try {
      ps.setInt(1, userDto.getId());
      rs = ps.executeQuery();
      while (rs.next()) {
        DocDepartDto docDepartDto = dtoFact.getDocDepartDto();
        docDepartDto = remplirDocDepartDto(rs);
        docs.add(docDepartDto);
      }
      rs.close();
    } catch (SQLException err) {
      throw new FatalException(err.getMessage());
    }
    return docs;
  }


  @Override
  public DocDepartDto lireDocDepartMobilite(MobiliteDto mobiliteDto) {
    PreparedStatement ps = dalb.getPreparedStatement(DalBackendServices.LIREDOCDEPARTMOBILITE);
    ResultSet rs;
    DocDepartDto docDepart;
    try {
      ps.setInt(1, mobiliteDto.getId());
      rs = ps.executeQuery();
      if (!rs.next()) {
        throw new BizException("DocDepart introuvable");
      }
      docDepart = remplirDocDepartDto(rs);
      rs.close();

    } catch (SQLException err) {
      throw new FatalException(err.getMessage());
    }
    return docDepart;
  }

}
