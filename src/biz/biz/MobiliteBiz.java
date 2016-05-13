package biz.biz;

import biz.dto.MobiliteDto;

public interface MobiliteBiz extends MobiliteDto {
  public void setPkMobilite(int pkMobilite);

  public int getNumVersion();

  public void setNumVersion(int numVersion);

  // pour verifier la validité d'un contact
  public void checkMobilite();


}
