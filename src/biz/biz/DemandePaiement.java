package biz.biz;

import biz.dto.MobiliteDto;
import biz.dto.UserDto;
import biz.util.Util;
import exception.BizException;

import java.time.LocalDateTime;

public class DemandePaiement implements DemandePaiementBiz {

  private int pkDemandePaiement;
  private LocalDateTime dateExecution;
  private UserDto professeur;
  private UserDto etudiant;
  private MobiliteDto mobilite;
  private int numVersion;

  @Override
  public int getId() {

    return pkDemandePaiement;
  }

  @Override
  public void setPkDemandePaiement(int pkDemandePaiement) {
    this.pkDemandePaiement = pkDemandePaiement;
  }

  @Override
  public LocalDateTime getDateExcution() {
    return dateExecution;
  }

  @Override
  public void setDateExecution(LocalDateTime date) {
    this.dateExecution = date;

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
  public UserDto getProfesseur() {
    return professeur;
  }

  @Override
  public void setProfesseur(UserDto professeur) {
    this.professeur = professeur;

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
  public void checkDemandePaiement() {
    if (!Util.checkValPositive(pkDemandePaiement)) {
      throw new BizException("pkDemandePaiement non-positive");
    }
    if (dateExecution.isAfter(LocalDateTime.now())) {
      throw new BizException("La date de la demande de paiement est invalide.");
    }
    if (!Util.checkObject(professeur)) {
      throw new BizException("professeur non-instancié");
    }
    if (!Util.checkObject(etudiant)) {
      throw new BizException("etudiant non-instancié");
    }
    if (!Util.checkObject(mobilite)) {
      throw new BizException("mobilite non-instancié");
    }

  }


}
