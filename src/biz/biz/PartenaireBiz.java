package biz.biz;

import biz.dto.PartenaireDto;

public interface PartenaireBiz extends PartenaireDto {
  public void setPkPartenaire(int pkPartenaire);

  public int getNumVersion();

  public void setNumVersion(int numVersion);

  // pour verifier la validité d'un contact
  public void checkPartenaire();


}
