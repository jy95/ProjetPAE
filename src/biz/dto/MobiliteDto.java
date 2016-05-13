package biz.dto;

import biz.enumerate.EtatMobilite;
import biz.enumerate.Programme;
import biz.enumerate.TypeStage;

public interface MobiliteDto {

  public int getId();

  public UserDto getEtudiant();

  public void setEtudiant(UserDto etudiant);

  public DemandeDto getDemande();

  public void setDemande(DemandeDto demande);

  public PaysDto getPays();

  public void setPays(PaysDto pays);

  public String getVille();

  public void setVille(String ville);

  public PartenaireDto getPartenaire();

  public void setPartenaire(PartenaireDto partenaire);

  public EtatMobilite getEtat();

  public void setEtat(EtatMobilite etat);

  public AnnulationDto getAnnulation();

  public void setAnnulation(AnnulationDto annulation);

  public String getAnneeAcademique();

  public void setAnneeAcademique(String anneeAcademique);

  public boolean getProEco();

  public void setProEco(boolean proEco);

  public boolean getMobilityTool();

  public void setMobilityTool(boolean mobilityTool);

  public boolean getMobi();

  public void setMobi(boolean mobi);

  public TypeStage getStage();

  public void setStage(TypeStage stage);

  public int getQuadri();

  public void setQuadri(int nbQuadri);

  public Programme getProgramme();

  public void setProgramme(Programme typeMobi);

  public DemandePaiementDto getDemandePaiement1();

  public void setDemandePaiement1(DemandePaiementDto demandePaiement1);

  public DemandePaiementDto getDemandePaiement2();

  public void setDemandePaiement2(DemandePaiementDto demandePaiement2);

}
