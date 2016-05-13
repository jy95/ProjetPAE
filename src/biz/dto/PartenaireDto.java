package biz.dto;

import biz.enumerate.TypeEntreprise;
import biz.enumerate.TypeStage;

public interface PartenaireDto {
  
  public int getId();

  public String getNomAffaire();

  public void setNomAffaire(String nomAffaire);

  public String getNomLegal();

  public void setNomLegal(String nomComplet);

  public String getNomComplet();

  public void setNomComplet(String nomComplet);

  public DepartementDto getDepartement();

  public void setDepartement(DepartementDto departement);

  public TypeEntreprise getTypeEntreprise();

  public void setTypeEntreprise(TypeEntreprise type);

  public int getNbrEmploye();

  public void setNbrEmploye(int nbEmploye);

  public String getAdresse();

  public void setAdresse(String adresse);

  public UserDto getCreateur();

  public void setCreateur(UserDto createur);

  public PaysDto getPays();

  public void setPays(PaysDto pays);

  public String getRegion();

  public void setRegion(String region);

  public String getCodePostal();

  public void setCodePostal(String codePostal);

  public String getVille();

  public void setVille(String ville);

  public String getEmail();

  public void setEmail(String email);

  public String getSiteWeb();

  public void setSiteWeb(String siteWeb);

  public String getTelephone();

  public void setTelephone(String telephone);

  public TypeStage getTypeStage();

  public void setTypeStage(TypeStage typeStage);

  public boolean getAgree();

  public void setAgree(boolean agree);
  
  public DepartementDto[] getAllDepartements();
  
  public boolean getSupprime();
  
  public void setSupprime(boolean supprime);
}
