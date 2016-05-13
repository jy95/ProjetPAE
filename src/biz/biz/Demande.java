package biz.biz;

import java.time.LocalDateTime;

import biz.dto.PartenaireDto;
import biz.dto.PaysDto;
import biz.dto.UserDto;
import biz.enumerate.Programme;
import biz.enumerate.TypeStage;
import biz.util.Util;
import exception.BizException;

public class Demande implements DemandeBiz {
  private int pkDemande;
  private int preference;
  private String ville;
  private TypeStage typeStage;
  private int quardi;
  private PartenaireDto partenaire;
  private UserDto etudiant;
  private PaysDto pays;
  private boolean validee;
  private LocalDateTime dateIntroduction;
  private Programme programme;
  private int numVersion;
  private String anneeAcademique;

  @Override
  public int getId() {

    return pkDemande;
  }

  @Override
  public void setPkDemande(int pkDemande) {

    this.pkDemande = pkDemande;
  }

  @Override
  public int getPreference() {

    return preference;
  }

  @Override
  public void setPreference(int num) {

    this.preference = num;
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
  public TypeStage getTypeStage() {

    return typeStage;
  }

  @Override
  public void setTypeStage(TypeStage typeStage) {

    this.typeStage = typeStage;
  }

  @Override
  public int getQuadri() {

    return quardi;
  }

  @Override
  public void setQuadri(int quadri) {
    this.quardi = quadri;

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
  public UserDto getEtudiant() {

    return etudiant;
  }

  @Override
  public void setEtudiant(UserDto etudiant) {
    this.etudiant = etudiant;

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
  public boolean getValidee() {

    return validee;
  }

  @Override
  public void setValidee(boolean validee) {
    this.validee = validee;

  }

  @Override
  public LocalDateTime getDateIntroduction() {

    return dateIntroduction;
  }

  @Override
  public void setDateIntroduction(LocalDateTime date) {
    this.dateIntroduction = date;

  }

  @Override
  public Programme getProgramme() {

    return programme;
  }

  @Override
  public void setProgramme(Programme programme) {
    this.programme = programme;
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
  public String getAnneeAcademique() {

    return anneeAcademique;
  }

  @Override
  public void setAnneeAcademique(String anneeAcademique) {
    this.anneeAcademique = anneeAcademique;

  }


  @Override
  public void checkDemande() {
    if (!Util.checkValPositive(pkDemande)) {
      throw new BizException("pkDemande non-positive");
    }
    if (!Util.checkObject(typeStage)) {
      throw new BizException("typeStage non-instancié");
    }
    if (!Util.checkValPositive(quardi)) {
      throw new BizException("pkDemande non-positive");
    }
    if (!Util.checkObject(etudiant)) {
      throw new BizException("etudiant non-instancié");
    } else {
      int xo = ((UserBiz) etudiant).getId();
      if (!Util.checkValPositive(xo) || xo == 0) {
        throw new BizException("pkUser non positive ou égale à 0");
      }
    }
    if (dateIntroduction.isAfter(LocalDateTime.now())) {
      throw new BizException("La date de la demande de paiement est invalide.");
    }
    if (!Util.checkObject(programme)) {
      throw new BizException("programme non-instancié");
    }
    if (!Util.checkValPositive(preference)) {
      throw new BizException("preference non-positive");
    }

  }



}
