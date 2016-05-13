package biz.biz;

import biz.dto.AnnulationDto;
import biz.dto.DemandeDto;
import biz.dto.DemandePaiementDto;
import biz.dto.PartenaireDto;
import biz.dto.PaysDto;
import biz.dto.UserDto;
import biz.enumerate.EtatMobilite;
import biz.enumerate.Programme;
import biz.enumerate.TypeStage;
import biz.util.Util;
import exception.BizException;

public class Mobilite implements MobiliteBiz {

  private int pkMobilite;

  private UserDto etudiant;

  private DemandeDto demande;

  private PaysDto pays;
  private String ville;

  private PartenaireDto partenaire;
  private EtatMobilite etat;
  private AnnulationDto annulation;

  private String anneeAcademique;
  private boolean proEco;

  private boolean mobilityTool;
  private boolean mobi;

  private TypeStage typeStage;

  private int quadri;
  private Programme typeMobi;
  private int numVersion;


  private DemandePaiementDto demandePaiement1;
  private DemandePaiementDto demandePaiement2;

  @Override
  public int getId() {
    return pkMobilite;
  }

  @Override
  public void setPkMobilite(int pkMobilite) {
    this.pkMobilite = pkMobilite;
  }

  @Override
  public UserDto getEtudiant() {
    return etudiant;
  }

  @Override
  public void setEtudiant(UserDto etudiant) {
    this.etudiant = etudiant;
  }

  @Override
  public DemandeDto getDemande() {
    return demande;
  }

  @Override
  public void setDemande(DemandeDto demande) {
    this.demande = demande;
  }

  @Override
  public PaysDto getPays() {
    return pays;
  }

  @Override
  public void setPays(PaysDto pays) {
    this.pays = pays;
  }

  @Override
  public String getVille() {
    return ville;
  }

  @Override
  public void setVille(String ville) {
    this.ville = ville;
  }

  @Override
  public PartenaireDto getPartenaire() {
    return partenaire;
  }

  @Override
  public void setPartenaire(PartenaireDto partenaire) {
    this.partenaire = partenaire;
  }

  @Override
  public EtatMobilite getEtat() {
    return etat;
  }

  @Override
  public void setEtat(EtatMobilite etat) {
    this.etat = etat;

  }

  @Override
  public AnnulationDto getAnnulation() {
    return annulation;
  }

  @Override
  public void setAnnulation(AnnulationDto annulation) {
    this.annulation = annulation;
  }

  @Override
  public String getAnneeAcademique() {
    return anneeAcademique;
  }

  @Override
  public void setAnneeAcademique(String anneeAcademique) {
    this.anneeAcademique = anneeAcademique;
  }

  @Override
  public boolean getProEco() {
    return proEco;
  }

  @Override
  public void setProEco(boolean proEco) {
    this.proEco = proEco;
  }

  @Override
  public boolean getMobilityTool() {
    return mobilityTool;
  }

  @Override
  public void setMobilityTool(boolean mobilityTool) {
    this.mobilityTool = mobilityTool;
  }

  @Override
  public boolean getMobi() {
    return mobi;
  }

  @Override
  public void setMobi(boolean mobi) {
    this.mobi = mobi;
  }

  @Override
  public TypeStage getStage() {
    return typeStage;
  }

  @Override
  public void setStage(TypeStage stage) {
    this.typeStage = stage;
  }

  @Override
  public void checkMobilite() {
    if (!Util.checkValPositive(pkMobilite)) {
      throw new BizException("pkDemande non-positive");
    }
    if (!Util.checkAnneeAcademique(anneeAcademique)) {
      throw new BizException("Année academique incorecte");
    }
    if (!Util.checkValPositive(this.quadri)) {
      throw new BizException("quadri non-positive");
    }
    if (!Util.checkObject(etudiant)) {
      throw new BizException("etudiant non-instancié");
    } else {
      int pkEtudiant = ((UserBiz) etudiant).getId();
      if (!Util.checkValPositive(pkEtudiant) || pkEtudiant == 0) {
        throw new BizException("la fk vers users non positive ou égale à 0");
      }
    }
    if (!Util.checkObject(demande)) {
      throw new BizException("etudiant non-instancié");
    } else {
      int pkDemande = ((DemandeBiz) demande).getId();
      if (!Util.checkValPositive(pkDemande) || pkDemande == 0) {
        throw new BizException("la fk vers demandes non positive ou égale à 0");
      }
    }
    if (!Util.checkObject(pays)) {
      throw new BizException("pays non-instancié");
    }
    if (!Util.checkString(ville)) {
      throw new BizException("ville non-instancié ou vide");
    }
    if (!Util.checkObject(partenaire)) {
      throw new BizException("partenaire non-instancié");
    } else {
      int xo = ((PartenaireBiz) partenaire).getId();
      if (!Util.checkValPositive(xo) || xo == 0) {
        throw new BizException("la fk vers partenaire non positive ou égale à 0");
      }
    }
    if (!Util.checkObject(typeStage)) {
      throw new BizException("typeStage non-instancié");
    }
    if (!Util.checkObject(etat)) {
      throw new BizException("etat non-instancié");
    }

  }

  @Override
  public int getQuadri() {
    /*
     * switch (this.quadri) { case 1: return "premier quadri";
     * 
     * case 2: return "second quadri";
     * 
     * default: return ""; }
     */
    return this.quadri;

  }

  @Override
  public void setQuadri(int nbQuadri) {
    this.quadri = nbQuadri;

  }

  @Override
  public Programme getProgramme() {
    return this.typeMobi;
  }

  @Override
  public void setProgramme(Programme typeMobi) {
    this.typeMobi = typeMobi;

  }


  @Override
  public int getNumVersion() {
    return numVersion;
  }

  @Override
  public void setNumVersion(int numVersion) {
    this.numVersion = numVersion;
  }

  @Override
  public DemandePaiementDto getDemandePaiement1() {
    return this.demandePaiement1;
  }

  @Override
  public void setDemandePaiement1(DemandePaiementDto demandePaiement1) {
    this.demandePaiement1 = demandePaiement1;

  }

  @Override
  public DemandePaiementDto getDemandePaiement2() {
    return this.demandePaiement2;
  }

  @Override
  public void setDemandePaiement2(DemandePaiementDto demandePaiement2) {
    this.demandePaiement2 = demandePaiement2;

  }

}
