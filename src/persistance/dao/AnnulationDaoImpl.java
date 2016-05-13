package persistance.dao;

import biz.biz.AnnulationBiz;
import biz.dto.AnnulationDto;
import biz.factory.DtoFactory;
import exception.BizException;
import exception.FatalException;
import persistance.dal.DalBackendServices;
import persistance.dal.DalServices;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AnnulationDaoImpl implements AnnulationDao {

  private DalBackendServices dalb;

  private DtoFactory dtofact;



  public AnnulationDaoImpl(DalBackendServices dalb, DtoFactory dtofact) {
    this.dalb = dalb;
    this.dtofact = dtofact;
  }



  @Override
  public int ecrireAnnulation(AnnulationDto annulationDto) throws FatalException {

    PreparedStatement ps = dalb.getPreparedStatement(DalBackendServices.ECRIREANNULATION);
    ResultSet rs;
    int pk = 0;
    try {
      ps.setString(1, annulationDto.getDescription());
      ps.setBoolean(2, annulationDto.getGenerique());
      ps.setInt(3, ((AnnulationBiz) annulationDto).getNumVersion());
      rs = ps.executeQuery();
      if (rs.next()) {
        pk = rs.getInt(1);
        rs.close();
      } else {
        ((DalServices) dalb).rollBackTransaction();
        rs.close();
        throw new FatalException("L'insert n'a pas pu être effectuée.");
      }
    } catch (SQLException err) {

      throw new FatalException(err.getMessage());
    }

    return pk;
  }


  @Override
  public AnnulationDto lireAnnulationPk(AnnulationDto annulationDto) {
    PreparedStatement ps = dalb.getPreparedStatement(DalBackendServices.LIREANNULATION);
    ResultSet rs;

    try {
      ps.setInt(1, ((AnnulationBiz) annulationDto).getId());
      rs = ps.executeQuery();

      if (!rs.next()) {
        throw new BizException("Annulation introuvable");
      }
      annulationDto.setDescription(rs.getString(2));
      annulationDto.setGenerique(rs.getBoolean(3));
      ((AnnulationBiz) annulationDto).setNumVersion(rs.getInt(4));
      rs.close();
    } catch (SQLException err) {
      throw new FatalException(err.getMessage());
    }
    return annulationDto;

  }

  @Override
  public ArrayList<AnnulationDto> lireToutAnnulationsGenerique() {
    PreparedStatement ps = dalb.getPreparedStatement(
        DalBackendServices.LIRETOUTANNULATIONGENERIQUE);
    ResultSet rs;
    ArrayList<AnnulationDto> annualtions = new ArrayList<>();
    try {
      ps.setBoolean(1, true);
      rs = ps.executeQuery();

      if (!rs.next()) {
        throw new BizException("Pas d'annulations générique");
      }
      do {
        AnnulationDto annulationDto = dtofact.getAnnulationDto();
        annulationDto.setDescription(rs.getString(2));
        annulationDto.setGenerique(rs.getBoolean(3));
        ((AnnulationBiz) annulationDto).setNumVersion(rs.getInt(4));
        annualtions.add(annulationDto);

      } while (rs.next());

      rs.close();
    } catch (SQLException err) {
      throw new FatalException(err.getMessage());
    }
    return annualtions;

  }

}
