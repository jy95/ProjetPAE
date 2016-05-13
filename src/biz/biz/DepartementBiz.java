package biz.biz;

import biz.dto.DepartementDto;

public interface DepartementBiz extends DepartementDto {

  public void setPkDepartement(String pkDepartement);
  
  public int getNumVersion();
  
  public void setNumVersion(int numVersion);

  public void checkDepartement();
}
