package biz.ucc;

import java.util.ArrayList;

import biz.dto.DemandePaiementDto;
import biz.dto.MobiliteDto;

public interface DemandePaiementUcc {
  public void ecrireDemandePaiement(DemandePaiementDto demandePaiement);

  public DemandePaiementDto lireDemandePaiementPk(DemandePaiementDto demandePaiement);

  public ArrayList<DemandePaiementDto> lireAllDemandePaiement();

  ArrayList<DemandePaiementDto> lireDemandePaiementMobilite(MobiliteDto mobilite);

}
