package persistance.dao;

import biz.dto.DepartementDto;

import java.util.HashMap;

public interface DepartementDao {
  public DepartementDto lireDepartementPk(DepartementDto departementDto);

  public HashMap<String, String> lireTousDepartements();
  
}
