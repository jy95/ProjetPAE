package persistance.dao;

import biz.biz.DepartementBiz;
import biz.dto.DepartementDto;
import exception.BizException;
import exception.FatalException;
import persistance.dal.DalBackendServices;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class DepartementDaoImpl implements DepartementDao {
  DalBackendServices dalb;

  public DepartementDaoImpl(DalBackendServices dalb) {
    this.dalb = dalb;
  }


  @Override
  public DepartementDto lireDepartementPk(DepartementDto departementDto) {
    PreparedStatement ps = dalb.getPreparedStatement(DalBackendServices.LIREDEPARTEMENTPK);
    ResultSet rs;
    try {
      ps.setString(1, ((DepartementBiz) departementDto).getId());
      rs = ps.executeQuery();
      if (!rs.next()) {
        throw new BizException("Aucun resultat trouvé ");
      }

      ((DepartementBiz) departementDto).setPkDepartement((rs.getString(1)));
      departementDto.setNom(rs.getString(2));
      ((DepartementBiz) departementDto).setNumVersion((rs.getInt(3)));
      rs.close();
    } catch (SQLException err) {
      throw new FatalException(err.getMessage());
    }
    return departementDto;
  }

  @Override
  public HashMap<String, String> lireTousDepartements() {
    PreparedStatement ps = dalb.getPreparedStatement(DalBackendServices.LIRETOUSDEPARTEMENTS);
    ResultSet rs;
    HashMap<String, String> hm = new HashMap<String, String>();
    try {
      rs = ps.executeQuery();
      if (!rs.next()) {
        throw new BizException("Pas de departement");
      }
      do {
        hm.put(rs.getString(1), rs.getString(2));
      } while (rs.next());
      rs.close();

    } catch (SQLException err) {
      throw new FatalException(err.getMessage());
    }
    return hm;

  }



}
