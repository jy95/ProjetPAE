package persistance.dao;

import biz.dto.DocRetourDto;
import biz.dto.MobiliteDto;
import biz.dto.UserDto;

import java.util.ArrayList;

public interface DocRetourDao {
  public void ecrireDocRetour(DocRetourDto docRetour);

  public DocRetourDto lireDocRetour(DocRetourDto docRetour);

  public ArrayList<DocRetourDto> lireToutDocRetour();

  public DocRetourDto modifierDocRetour(DocRetourDto docRetour);

  public ArrayList<DocRetourDto> lireDocRetourUser(UserDto userDto);

  public DocRetourDto lireDocRetourMobilite(MobiliteDto mobiliteDto);
}
