package biz.biz;

import biz.dto.MobiliteDto;
import biz.dto.UserDto;
import biz.util.Util;
import exception.BizException;

import java.time.LocalDateTime;

public class DocRetour implements DocRetourBiz {
  private boolean atestationSejour;
  private boolean releveNoteOuCertifStage;
  private boolean preuveTestLangue;
  private boolean rapportFinal;
  private LocalDateTime dateRetour;
  private UserDto etudiant;
  private MobiliteDto mobilite;
  private int pkDocRetour;
  private int numVersion;


  @Override
  public int getId() {

    return pkDocRetour;
  }

  @Override
  public void setPkDocRetour(int pkDocRetour) {

    this.pkDocRetour = pkDocRetour;

  }

  @Override
  public boolean getAttestationSejour() {

    return atestationSejour;
  }

  @Override
  public void setAttestationSejour(boolean attestationSejour) {

    this.atestationSejour = attestationSejour;
  }

  @Override
  public boolean getReleveNoteOuCertifStage() {

    return releveNoteOuCertifStage;
  }

  @Override
  public void setReleveNoteOuCertifStage(boolean releveNoteOuCertifStage) {

    this.releveNoteOuCertifStage = releveNoteOuCertifStage;
  }

  @Override
  public boolean getPreuvePassageTest() {

    return preuveTestLangue;
  }

  @Override
  public void setPreuvePassageTest(boolean preuvePassageTest) {

    this.preuveTestLangue = preuvePassageTest;
  }

  @Override
  public boolean getRapportFinal() {

    return rapportFinal;
  }

  @Override
  public void setRapportFinal(boolean rapportFinal) {

    this.rapportFinal = rapportFinal;
  }

  @Override
  public LocalDateTime getDateRetour() {

    return dateRetour;
  }

  @Override
  public void setDateRetour(LocalDateTime dateRetour) {

    this.dateRetour = dateRetour;
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
  public MobiliteDto getMoblite() {

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
  public void checkDocRetour() {
    if (!Util.checkValPositive(pkDocRetour)) {
      throw new BizException("pkDocDepart non-positive");
    }
    /*
    if (!atestationSejour) {
      throw new BizException("l'atestation de sejour n'est pas presente");
    }
    if (!rapportFinal) {
      throw new BizException("le rapportFinal ou d'etude n'est pas presente");
    }
    if (!releveNoteOuCertifStage) {
      throw new BizException("ledoc Engagement n'est pas present ");
    }
    if (!preuveTestLangue) {
      throw new BizException("la preuve du test de Langue n'est pas presente");
    }
    if (dateRetour.isAfter(LocalDateTime.now())) {
      throw new BizException("La date de la demande de paiement est invalide.");
    }
    
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
