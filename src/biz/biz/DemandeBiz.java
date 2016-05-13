package biz.biz;

import biz.dto.DemandeDto;

public interface DemandeBiz extends DemandeDto {
  public void setPkDemande(int pkDemande);

  // pour verifier la validité d'un contact
  public void checkDemande();

  public int getNumVersion();

  public void setNumVersion(int numVersion);
}
