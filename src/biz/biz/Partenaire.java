package biz.biz;

import exception.BizException;
import biz.dto.DepartementDto;
import biz.dto.PaysDto;
import biz.dto.UserDto;
import biz.enumerate.TypeEntreprise;
import biz.enumerate.TypeStage;
import biz.util.Util;

public class Partenaire implements PartenaireBiz {
  private int pkPartenaire;
  private String nomAffaire;
  private String nomLegal;
  private String nomComplet;
  private DepartementDto[] departements = new DepartementDto[5];
  private TypeEntreprise typeEntreprise;
  private int nbrEmploye;
  private String adresse;
  private String codePostal;
  private String ville;
  private String region;
  private String email;
  private String siteWeb;
  private String telephone;
  private TypeStage typeS;
  private boolean agree;
  private UserDto createur;
  private PaysDto pays;
  private int nombreDepartement = 0;
  private int numVersion;
  private boolean supprime;

  @Override
  public int getId() {

    return pkPartenaire;
  }

  @Override
  public void setPkPartenaire(int pkPartenaire) {

    this.pkPartenaire = pkPartenaire;
  }

  @Override
  public String getNomAffaire() {

    return nomAffaire;
  }

  @Override
  public void setNomAffaire(String nomAffaire) {

    this.nomAffaire = nomAffaire;
  }

  @Override
  public String getNomLegal() {
    return nomLegal;
  }

  @Override
  public void setNomLegal(String nomLegal) {
    this.nomLegal = nomLegal;

  }

  @Override
  public String getNomComplet() {

    return nomComplet;
  }

  @Override
  public void setNomComplet(String nomComplet) {

    this.nomComplet = nomComplet;
  }

  @Override
  public DepartementDto getDepartement() {
    return departements[0];
  }

  @Override
  public void setDepartement(DepartementDto departement) {
    if (nombreDepartement < 5) {
      this.departements[nombreDepartement++] = departement;
    }
  }

  @Override
  public TypeEntreprise getTypeEntreprise() {
    return typeEntreprise;
  }

  @Override
  public void setTypeEntreprise(TypeEntreprise type) {
    this.typeEntreprise = type;
  }

  @Override
  public int getNbrEmploye() {
    return nbrEmploye;
  }

  @Override
  public void setNbrEmploye(int nbEmploye) {
    this.nbrEmploye = nbEmploye;
  }

  @Override
  public String getAdresse() {
    return adresse;
  }

  @Override
  public void setAdresse(String adresse) {
    this.adresse = adresse;
  }

  @Override
  public UserDto getCreateur() {

    return createur;
  }

  @Override
  public void setCreateur(UserDto createur) {

    this.createur = createur;
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
  public String getRegion() {
    return region;
  }

  @Override
  public void setRegion(String region) {
    this.region = region;
  }

  @Override
  public String getCodePostal() {
    return codePostal;
  }

  @Override
  public void setCodePostal(String codePostal) {
    this.codePostal = codePostal;
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
  public String getEmail() {
    return email;
  }

  @Override
  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String getSiteWeb() {
    return siteWeb;
  }

  @Override
  public void setSiteWeb(String siteWeb) {
    this.siteWeb = siteWeb;
  }

  @Override
  public String getTelephone() {
    return telephone;
  }

  @Override
  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }

  @Override
  public TypeStage getTypeStage() {
    return typeS;
  }

  @Override
  public void setTypeStage(TypeStage typeStage) {
    this.typeS = typeStage;
  }


  @Override
  public boolean getAgree() {
    return agree;
  }

  @Override
  public void setAgree(boolean agree) {
    this.agree = agree;
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
  public void checkPartenaire() {
    if (!Util.checkValPositive(pkPartenaire)) {
      throw new BizException("pkParteniare non-positive");
    }
    if (!Util.checkString(nomAffaire)) {
      throw new BizException("nomAffaire invalide");
    }
    if (!Util.checkString(nomComplet)) {
      throw new BizException("nomComplet invalide ");
    }
    if (!Util.checkString(nomLegal)) {
      throw new BizException("nomComplet invalide");
    }
    if (!Util.checkObject(typeEntreprise)) {
      throw new BizException("typeEntreprise non rempli");
    }
    if (!Util.checkValPositive(nbrEmploye)) {
      throw new BizException("le nbrEmploye non positive ou égale à 0");
    }
    if (!Util.checkObject(pays)) {
      throw new BizException("pays non-instancié");
    }
    if (!Util.checkString(adresse)) {
      throw new BizException("adresse vide");
    }
    if (!Util.checkString(codePostal)) {
      throw new BizException("codePostal vide");
    }
    if (!Util.checkString(ville)) {
      throw new BizException("ville vide");
    }
    if (!Util.checkString(region)) {
      throw new BizException("region vide");
    }
    if (!Util.checkString(siteWeb)) {
      throw new BizException("siteWeb vide");
    }
    if (!Util.checkString(telephone)) {
      throw new BizException("telephone vide");
    }
    if (!Util.checkObject(createur)) {
      throw new BizException("etudiant non-instancié");
    } else {
      int pkCreateur = ((UserBiz) createur).getId();
      if (!Util.checkValPositive(pkCreateur) || pkCreateur == 0) {
        throw new BizException("la fk vers users non positive ou égale à 0");
      }
    }
    if (numVersion < 0) {
      throw new BizException("num version negatif");
    }
  }

  @Override
  public DepartementDto[] getAllDepartements() {
    // TODO Auto-generated method stub
    return departements.clone();
  }

  @Override
  public boolean getSupprime() {
    // TODO Auto-generated method stub
    return supprime;
  }

  @Override
  public void setSupprime(boolean supprime) {
    this.supprime = supprime;
  }

}
