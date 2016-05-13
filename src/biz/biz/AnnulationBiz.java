package biz.biz;

import biz.dto.AnnulationDto;

public interface AnnulationBiz extends AnnulationDto {
  public void setPkAnnulation(int pkAnnulation);
  
  // pour verifier la validité d'un contact
  public void checkAnnulation();

  public int getNumVersion();
  
  public void setNumVersion(int numVersion);
  

}
