package biz.dto;

import biz.enumerate.Programme;
import biz.enumerate.TypeStage;

import java.time.LocalDateTime;

public interface DemandeDto {

  public int getId();

  public int getPreference();

  public void setPreference(int num);

  public String getVille();

  public void setVille(String ville);

  public TypeStage getTypeStage();

  public void setTypeStage(TypeStage typeStage);

  public int getQuadri();

  public void setQuadri(int quadri);

  public PartenaireDto getPartenaire();

  public void setPartenaire(PartenaireDto partenaire);

  public UserDto getEtudiant();

  public void setEtudiant(UserDto etudiant);

  public PaysDto getPays();

  public void setPays(PaysDto pays);

  public boolean getValidee();

  public void setValidee(boolean validee);

  public LocalDateTime getDateIntroduction();

  public void setDateIntroduction(LocalDateTime date);

  public Programme getProgramme();

  public void setProgramme(Programme programme);
  
  public String getAnneeAcademique();

  public void setAnneeAcademique(String anneeAcademique);

}
