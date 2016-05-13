package biz.dto;

import java.time.LocalDateTime;

public interface DocRetourDto {
  
  public int getId();

  public boolean getAttestationSejour();

  public void setAttestationSejour(boolean attestationSejour);

  public boolean getReleveNoteOuCertifStage();

  public void setReleveNoteOuCertifStage(boolean releveNoteOuCertifStage);

  public boolean getPreuvePassageTest();

  public void setPreuvePassageTest(boolean preuvePassageTest);

  public boolean getRapportFinal();

  public void setRapportFinal(boolean rapportFinal);


  public LocalDateTime getDateRetour();

  public void setDateRetour(LocalDateTime dateRetour);

  public UserDto getEtudiant();

  public void setEtudiant(UserDto etudiant);

  public MobiliteDto getMoblite();

  public void setMobilite(MobiliteDto mobilite);

}

