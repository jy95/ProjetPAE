package biz.biz;

import biz.dto.DocDepartDto;

public interface DocDepartBiz extends DocDepartDto {
  public void setPkDocDepart(int pkDocDepart);

  // pour verifier la validité d'un contact
  public int getNumVersion();
  
  public void setNumVersion(int numVersion);

  public void checkDocDepart();

}
