package persistance.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import biz.biz.UserBiz;
import biz.dto.DemandePaiementDto;
import biz.dto.MobiliteDto;
import biz.dto.UserDto;
import biz.factory.DtoFactory;
import exception.BizException;
import exception.FatalException;

public class MockDemandePaiement implements DemandePaiementDao {

  DtoFactory dtoFact;
  Set donnees;

  public MockDemandePaiement(DtoFactory dtoFact) {
    this.dtoFact = dtoFact;
    donnees = new HashSet();
  }

  @Override
  public void ecrireDemandePaiement(DemandePaiementDto demPaiement) {
    if (demPaiement.getId() == 2)
      throw new FatalException("erreur DB");
  }

  @Override
  public DemandePaiementDto lireDemandePaiementPk(DemandePaiementDto demandePaiement) {
    if (demandePaiement.getId() == 2) {
      throw new BizException("introuvable");
    }
    demandePaiement.setMobilite(dtoFact.getMobiliteDto());

    return demandePaiement;
  }

  @Override
  public ArrayList<DemandePaiementDto> lireAllDemandePaiement() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ArrayList<DemandePaiementDto> lireDemandePaiementMobilite(MobiliteDto mobilite) {
    ArrayList<DemandePaiementDto> arr = new ArrayList<>();
    if(mobilite.getId() == 6){
    	return arr;
    }
    arr.add(mobilite.getDemandePaiement1());
    if(mobilite.getId() == 5){
    	arr.add(mobilite.getDemandePaiement1());
    	arr.add(mobilite.getDemandePaiement1());
    	arr.add(mobilite.getDemandePaiement1());
    }
    return arr;
  }

  @Override
  public ArrayList<MobiliteDto> listerTouteMobiliteAvecDemandePaiement() {
    ArrayList<MobiliteDto> arr = new ArrayList<>();
    MobiliteDto m = dtoFact.getMobiliteDto();
    DemandePaiementDto dp = dtoFact.getDemandePaiementDto();
    UserDto u = dtoFact.getUserDto();
    ((UserBiz)u).setPkUser(1);
    dp.setProfesseur(u);
    m.setDemandePaiement1(dp);
    m.setDemandePaiement2(dp);
    arr.add(m);
    return arr;
  }

}
