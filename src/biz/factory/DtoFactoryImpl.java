package biz.factory;

import biz.biz.Annulation;
import biz.biz.Demande;
import biz.biz.DemandePaiement;
import biz.biz.Departement;
import biz.biz.DocDepart;
import biz.biz.DocRetour;
import biz.biz.Mobilite;
import biz.biz.Partenaire;
import biz.biz.Pays;
import biz.biz.User;
import biz.dto.AnnulationDto;
import biz.dto.DemandeDto;
import biz.dto.DemandePaiementDto;
import biz.dto.DepartementDto;
import biz.dto.DocDepartDto;
import biz.dto.DocRetourDto;
import biz.dto.MobiliteDto;
import biz.dto.PartenaireDto;
import biz.dto.PaysDto;
import biz.dto.UserDto;

public class DtoFactoryImpl implements DtoFactory {

  @Override
  public UserDto getUserDto() {
    return new User();
  }

  @Override
  public PartenaireDto getPartenaireDto() {
    return new Partenaire();
  }

  @Override
  public AnnulationDto getAnnulationDto() {
    return new Annulation();
  }

  @Override
  public DemandeDto getDemandeDto() {
    return new Demande();
  }

  @Override
  public DemandePaiementDto getDemandePaiementDto() {
    return new DemandePaiement();
  }

  @Override
  public DocDepartDto getDocDepartDto() {
    return new DocDepart();
  }

  @Override
  public DocRetourDto getDocRetourDto() {
    return new DocRetour();
  }

  @Override
  public MobiliteDto getMobiliteDto() {
    return new Mobilite();
  }

  @Override
  public PaysDto getPaysDto() {
    return new Pays();
  }

  @Override
  public DepartementDto getDepartementDto() {
    return new Departement();
  }



}
