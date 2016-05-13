package biz.biz;

import biz.dto.DemandePaiementDto;

public interface DemandePaiementBiz extends DemandePaiementDto {
  public void setPkDemandePaiement(int pkDemandePaiement);

  // pour verifier la validité d'un contact
  public void checkDemandePaiement();

  public int getNumVersion();

  public void setNumVersion(int numVersion);
}
