package biz.biz;

import biz.dto.MobiliteDto;
import biz.dto.UserDto;
import biz.util.Util;
import exception.BizException;

import java.time.LocalDateTime;

public class DocDepart implements DocDepartBiz {
  private boolean contratBourse;
  private boolean charteEtudiant;
  private boolean conventionStageOuEtude;
  private boolean preuveTestLangue;
  private boolean docEngagement;
  private LocalDateTime dateDepart;
  private UserDto etudiant;
  private MobiliteDto mobilite;
  private int pkDocDepart;
  private int numVersion;


  @Override
  public int getId() {
    return pkDocDepart;
  }

  @Override
  public void setPkDocDepart(int pkDocDepart) {
    this.pkDocDepart = pkDocDepart;
  }

  @Override
  public void setDateDepart(LocalDateTime dateDepart) {
    this.dateDepart = dateDepart;
  }

  @Override
  public LocalDateTime getDateDepart() {
    return dateDepart;
  }

  @Override
  public boolean getContratBourse() {
    return contratBourse;
  }

  @Override
  public void setContratBourse(boolean contratBourse) {
    this.contratBourse = contratBourse;
  }

  @Override
  public boolean getCharteEtudiant() {
    return charteEtudiant;
  }

  @Override
  public void setCharteEtudiant(boolean charteEtudiant) {
    this.charteEtudiant = charteEtudiant;
  }

  @Override
  public boolean getConventionStageOuEtude() {
    return conventionStageOuEtude;
  }

  @Override
  public void setConventionStageOuEtude(boolean conventionStageOuEtude) {
    this.conventionStageOuEtude = conventionStageOuEtude;
  }

  @Override
  public boolean getPreuveTestLangue() {
    return preuveTestLangue;
  }

  @Override
  public void setPreuveTestLangue(boolean preuveTestLangue) {
    this.preuveTestLangue = preuveTestLangue;
  }

  @Override
  public boolean getDocEngagement() {
    return docEngagement;
  }

  @Override
  public void setDocEngagement(boolean docEngagement) {
    this.docEngagement = docEngagement;
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
  public MobiliteDto getMobilite() {
    return mobilite;
  }

  @Override
  public void setMobilite(MobiliteDto mobilite) {
    this.mobilite = mobilite;
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
  public void checkDocDepart() {
    if (!Util.checkValPositive(pkDocDepart)) {
      throw new BizException("pkDocDepart non-positive");
    }
    /*
     * if (!charteEtudiant) { throw new BizException("la charteEtudiant n'est pas presente"); } if
     * (!contratBourse) { throw new BizException("le contrat de bourse n'est pas present"); } if
     * (!conventionStageOuEtude) { throw new BizException(
     * "la convention de stage ou d'etude n'est pas presente"); } if (!docEngagement) { throw new
     * BizException("ledoc Engagement n'est pas present "); } if (!preuveTestLangue) { throw new
     * BizException("la preuve du test de Langue n'est pas presente"); } if (dateDepart != null) {
     * throw new BizException("La date de la demande de paiement est invalide."); }
     */

    if (!Util.checkObject(etudiant)) {
      throw new BizException("etudiant non-instancié");
    }
    if (etudiant.getId() <= 0) {
      throw new BizException("fk etudiant échèque");
    }
    if (!Util.checkObject(mobilite)) {
      throw new BizException("mobilite non-instancié");
    }
    if (mobilite.getId() <= 0) {
      throw new BizException("fk mobilite non-instancié");
    }

  }


}
