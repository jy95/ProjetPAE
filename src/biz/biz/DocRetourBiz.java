package biz.biz;

import biz.dto.DocRetourDto;

public interface DocRetourBiz extends DocRetourDto {
  public void setPkDocRetour(int pkDocRetour);

  public int getNumVersion();

  public void setNumVersion(int numVersion);

  // pour verifier la validité d'un contact
  public void checkDocRetour();

}
