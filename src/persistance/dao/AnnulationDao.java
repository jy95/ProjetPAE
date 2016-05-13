package persistance.dao;

import biz.dto.AnnulationDto;

import java.util.ArrayList;

public interface AnnulationDao {

  /**
   * Ecrire une annulation en db, et renvoie cette pk.
   * 
   * @Return int
   * @Param AnnulationDto
   */
  public int ecrireAnnulation(AnnulationDto annulationdto);

  public AnnulationDto lireAnnulationPk(AnnulationDto annulationDto);

  public ArrayList<AnnulationDto> lireToutAnnulationsGenerique();
}
