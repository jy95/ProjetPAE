package persistance.dao;

import biz.biz.PaysBiz;
import biz.dto.PaysDto;
import biz.factory.DtoFactory;
import exception.BizException;
import exception.FatalException;
import persistance.dal.DalBackendServices;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.SortedMap;
import java.util.TreeMap;

public class PaysDaoImpl implements PaysDao {

  DalBackendServices dalb;
  DtoFactory dtoFact;

  public PaysDaoImpl(DalBackendServices dalb, DtoFactory dtoFact) {
    this.dalb = dalb;
    this.dtoFact = dtoFact;
  }

  @Override
  public SortedMap<String, PaysDto> lireTousPays() {
    PreparedStatement ps = dalb.getPreparedStatement(DalBackendServices.LIRETOUSPAYS);
    ResultSet rs;
    SortedMap<String, PaysDto> hm = new TreeMap<String, PaysDto>();
    try {
      rs = ps.executeQuery();
      if (!rs.next()) {
        throw new BizException("Pas de pays");
      }

      do {
        PaysDto paysDto = dtoFact.getPaysDto();
        ((PaysBiz) paysDto).setPkPays(rs.getString(1));
        paysDto.setTypeMoblite(rs.getString(2));
        paysDto.setNom(rs.getString(3));
        hm.put(rs.getString(1), paysDto);
      } while (rs.next());
      rs.close();

    } catch (SQLException err) {
      throw new FatalException(err.getMessage());
    }
    return hm;
  }

  @Override
  public PaysDto lirePaysByPk(PaysDto pays) {
    PreparedStatement ps = dalb.getPreparedStatement(DalBackendServices.LIREPAYSPK);
    ResultSet rs;
    PaysDto paysDto = dtoFact.getPaysDto();
    try {
      ps.setString(1, ((PaysBiz) pays).getId());
      rs = ps.executeQuery();

      if (rs.next()) {
        ((PaysBiz) paysDto).setPkPays(rs.getString(1));
        paysDto.setNom(rs.getString(3));
        paysDto.setTypeMoblite(rs.getString(2));
      }
      rs.close();
    } catch (SQLException err) {
      throw new FatalException(err.getMessage());
    }

    return paysDto;
  }

  @Override
  public PaysDto rechercherPays(PaysDto pays) {
    PreparedStatement ps = dalb.getPreparedStatement(DalBackendServices.RECHERCHERPAYS);
    ResultSet rs;
    PaysDto pays2 = dtoFact.getPaysDto();
    try {

      ps.setString(1, ((PaysBiz) pays).getId());
      ps.setString(2, pays.getNom());

      rs = ps.executeQuery();


      if (rs.next()) {

        ((PaysBiz) pays2).setPkPays(rs.getString(1));
        pays2.setNom(rs.getString(3));
        pays2.setTypeMoblite(rs.getString(2));
      } else {
        throw new BizException("pas de resultat pour la recherche des pays");
      }
      rs.close();


    } catch (SQLException err) {
      throw new FatalException(err.getMessage());
    }
    return pays2;
  }



}
