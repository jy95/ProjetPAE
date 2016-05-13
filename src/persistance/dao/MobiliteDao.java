package persistance.dao;

import biz.dto.MobiliteDto;
import biz.dto.UserDto;
import biz.enumerate.EtatMobilite;

import java.util.ArrayList;

public interface MobiliteDao {

  public MobiliteDto lireMobilitePk(MobiliteDto mobiliteDto);

  public int ecrireMobilite(MobiliteDto mobiliteDto);

  public MobiliteDto modifierMobilite(MobiliteDto mobiliteDto);

  public ArrayList<MobiliteDto> rechercherMobilite(EtatMobilite etat, String anneeAcademique);

  public ArrayList<MobiliteDto> rechercherMobiliteStud(UserDto user);

  /**
   * Renvoit toutes les mobilités.
   * 
   * @return {@code ArrayList<MobiliteDto> } les mobilités
   */
  public ArrayList<MobiliteDto> lireToutMobilite();

  public ArrayList<MobiliteDto> lireMobilitesAnnulees();

}
