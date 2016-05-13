package biz.dto;

import com.owlike.genson.annotation.JsonIgnore;

import java.time.LocalDateTime;

public interface DemandePaiementDto {

  public int getId();

  public LocalDateTime getDateExcution();

  public void setDateExecution(LocalDateTime date);

  public UserDto getEtudiant();

  public void setEtudiant(UserDto etudiant);

  public UserDto getProfesseur();

  public void setProfesseur(UserDto professeur);
  
  @JsonIgnore
  public MobiliteDto getMoblite();

  public void setMobilite(MobiliteDto mobilite);

}
