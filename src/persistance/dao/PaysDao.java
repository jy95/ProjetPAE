package persistance.dao;

import biz.dto.PaysDto;

import java.util.SortedMap;

public interface PaysDao {
  public SortedMap<String, PaysDto> lireTousPays();
  
  public PaysDto lirePaysByPk(PaysDto pays);
  
  public PaysDto rechercherPays(PaysDto pays);
}
