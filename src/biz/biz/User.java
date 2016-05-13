package biz.biz;

import java.time.LocalDate;
import java.time.LocalDateTime;

import biz.dto.DepartementDto;
import biz.enumerate.Sexe;
import biz.enumerate.Titre;
import biz.enumerate.TypeUser;
import biz.util.Cryptage;
import biz.util.Util;
import exception.BizException;

public class User implements UserBiz {
  private int pkUser;
  private String pseudo;
  private String mdp;
  private String nom;
  private String prenom;
  private DepartementDto departement;
  private LocalDate dateNaissance;
  private String adresse;
  private String telephone;
  private String email;
  private String nationalite;
  private Titre titre;
  private Sexe sexe;
  private int nbAnneeEns;
  private String titulaireCompte;
  private String numCompte;
  private String nomBanque;
  private String codeBic;
  private LocalDateTime dateInscription;
  private TypeUser type;

  private int numVersion;



  @Override
  public int getId() {
    return pkUser;
  }

  @Override
  public void setPkUser(int pkUser) {
    this.pkUser = pkUser;
  }

  @Override
  public String getPseudo() {
    return pseudo;
  }

  @Override
  public void setPseudo(String pseudo) {
    this.pseudo = pseudo;
  }

  @Override
  public String getMdp() {
    return mdp;
  }

  @Override
  public void setMdp(String mdp) {
    this.mdp = mdp;
  }

  @Override
  public String getNom() {
    return nom;
  }

  @Override
  public void setNom(String nom) {
    this.nom = nom;
  }

  @Override
  public String getPrenom() {
    return prenom;
  }

  @Override
  public void setPrenom(String prenom) {
    this.prenom = prenom;
  }

  @Override
  public DepartementDto getDepartement() {
    return departement;
  }

  @Override
  public void setDepartement(DepartementDto departement) {
    this.departement = departement;
  }


  @Override
  public LocalDate getDateNaissance() {
    return dateNaissance;
  }

  @Override
  public void setDateNaissance(LocalDate dateNaissance) {
    this.dateNaissance = dateNaissance;
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
  public String getTelephone() {
    return telephone;
  }

  @Override
  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }



  @Override
  public String getNationalite() {
    return nationalite;
  }

  @Override
  public void setNationalite(String nationalite) {
    this.nationalite = nationalite;
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
  public Titre getTitre() {
    return titre;
  }

  @Override
  public void setTitre(Titre titre) {
    this.titre = titre;

  }

  @Override
  public Sexe getSexe() {
    return sexe;
  }

  @Override
  public void setSexe(Sexe sexe) {
    this.sexe = sexe;
  }

  @Override
  public int getnbAnneeEns() {
    return nbAnneeEns;
  }

  @Override
  public void setnbAnneeEns(int nbAnneeEns) {
    this.nbAnneeEns = nbAnneeEns;
  }

  @Override
  public String getTitulaireCompte() {
    return titulaireCompte;
  }

  @Override
  public void setTitulaireCompte(String titulaireCompte) {
    this.titulaireCompte = titulaireCompte;
  }

  @Override
  public String getNumCompte() {
    return numCompte;
  }

  @Override
  public void setNumCompte(String numCompte) {
    this.numCompte = numCompte;
  }

  @Override
  public String getNomBanque() {
    return nomBanque;
  }

  @Override
  public void setNomBanque(String nomBanque) {
    this.nomBanque = nomBanque;
  }

  @Override
  public String getCodeBic() {
    return codeBic;
  }

  @Override
  public void setCodeBic(String codeBic) {
    this.codeBic = codeBic;
  }

  @Override
  public LocalDateTime getDateInscription() {
    return dateInscription;
  }

  @Override
  public void setDateInscription(LocalDateTime dateInscription) {
    this.dateInscription = dateInscription;
  }



  @Override
  public boolean checkValidePassword(String pwd) {
    return Cryptage.check(pwd, this.mdp);
  }



  @Override
  public TypeUser getTypeUser() {

    return type;
  }

  @Override
  public void setTypeUser(TypeUser typeUser) {
    this.type = typeUser;
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
  public void checkUser() {
    if (!Util.checkValPositive(pkUser)) {
      throw new BizException("pkUser non positive");
    }
    if (!Util.checkString(pseudo)) {
      throw new BizException("pseudo invalide");
    }
    if (!Util.checkString(mdp)) {
      throw new BizException("mdp Invalide");
    }
    if (!Util.checkString(nom)) {
      throw new BizException("nom invalide");
    }
    if (!Util.checkString(prenom)) {
      throw new BizException("prenom invalide");
    }
    if (!Util.checkObject(departement)) {
      throw new BizException("departement invalide");
    }
    if (!Util.checkEmail(email)) {
      throw new BizException("email Invalide");
    }
    if (!Util.checkObject(dateInscription)) {
      throw new BizException("dateInscription invalide");
    }
    if (Util.checkString(numCompte)) {
      if (!Util.checkCompteBancaire(numCompte)) {
        throw new BizException("numero de compte invalide ");
      }
    }
    if (Util.checkString(codeBic)) {
      if (!Util.checkBic(codeBic)) {
        throw new BizException("code bic invalide ");
      }
    }

    // 1 - vérifie si vide

    /*
     * if (Util.checkObject(numCompte) && Util.checkString(numCompte) &&
     * !Util.checkCompteBancaire(numCompte)) { throw new BizException("numero de compte invalide ");
     * } TODO
     */
  }



}
