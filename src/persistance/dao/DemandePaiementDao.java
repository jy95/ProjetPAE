package persistance.dao;

import java.util.ArrayList;

import biz.dto.DemandePaiementDto;
import biz.dto.MobiliteDto;

public interface DemandePaiementDao {
  public void ecrireDemandePaiement(DemandePaiementDto demPaiement);

  public DemandePaiementDto lireDemandePaiementPk(DemandePaiementDto demandePaiement);

  public ArrayList<DemandePaiementDto> lireAllDemandePaiement();

  ArrayList<DemandePaiementDto> lireDemandePaiementMobilite(MobiliteDto mobilite);

  ArrayList<MobiliteDto> listerTouteMobiliteAvecDemandePaiement();
}
