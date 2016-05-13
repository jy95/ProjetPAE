package biz.dto;


import java.time.LocalDateTime;

public interface DocDepartDto {
  
  public int getId();

  public boolean getContratBourse();

  public void setContratBourse(boolean contratBourse);

  public boolean getCharteEtudiant();

  public void setCharteEtudiant(boolean chartEetudiant);

  public boolean getConventionStageOuEtude();

  public void setConventionStageOuEtude(boolean conventionStageOuEtude);

  public boolean getPreuveTestLangue();

  public void setPreuveTestLangue(boolean preuveTestLangue);

  public boolean getDocEngagement();

  public void setDocEngagement(boolean docEngagement);

  public LocalDateTime getDateDepart();

  public void setDateDepart(LocalDateTime dateDepart);

  public UserDto getEtudiant();

  public void setEtudiant(UserDto etudiant);

  public MobiliteDto getMobilite();

  public void setMobilite(MobiliteDto mobilite);
}
