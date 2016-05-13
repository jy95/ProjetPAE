package persistance.dao;
import biz.dto.DepartementDto;
import biz.ucc.PartenaireTest;
import exception.FatalException;

import java.util.HashMap;

public class MockDepartement implements DepartementDao{

  @Override
  public DepartementDto lireDepartementPk(DepartementDto departementDto) {
    
    return departementDto;
  }

  @Override
  public HashMap<String, String> lireTousDepartements() {
   if(PartenaireTest.fatal)throw new FatalException();
    return null;
  }

}
