package biz.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import biz.enumerate.Sexe;
import biz.enumerate.Titre;
import biz.enumerate.TypeUser;

public interface UserDto {

  public int getId();

  public String getPseudo();

  public void setPseudo(String pseudo);

  public String getMdp();

  public void setMdp(String mdp);

  public String getNom();

  public void setNom(String nom);

  public String getPrenom();

  public void setPrenom(String prenom);

  public DepartementDto getDepartement();

  public void setDepartement(DepartementDto departement);

  public LocalDate getDateNaissance();

  public void setDateNaissance(LocalDate dateNaissance);

  public String getAdresse();

  public void setAdresse(String adresse);

  public String getTelephone();

  public void setTelephone(String telephone);

  public String getEmail();

  public void setEmail(String email);

  public String getNationalite();

  public void setNationalite(String nationalite);

  public Titre getTitre();

  public void setTitre(Titre titre);

  public Sexe getSexe();

  public void setSexe(Sexe sexe);

  public int getnbAnneeEns();

  public void setnbAnneeEns(int nbAnneeEns);

  public String getTitulaireCompte();

  public void setTitulaireCompte(String titulaireCompte);

  public String getNumCompte();

  public void setNumCompte(String numCompte);

  public String getNomBanque();

  public void setNomBanque(String nomBanque);

  public String getCodeBic();

  public void setCodeBic(String codeBic);

  public LocalDateTime getDateInscription();

  public void setDateInscription(LocalDateTime dateInscription);

  public TypeUser getTypeUser();

  public void setTypeUser(TypeUser typeUser);



}
