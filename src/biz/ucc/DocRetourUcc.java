package biz.ucc;

import biz.dto.DocRetourDto;
import biz.dto.MobiliteDto;
import biz.dto.UserDto;

import java.util.ArrayList;

public interface DocRetourUcc {

  public void ecrireDocRetour(DocRetourDto docRetourDto);

  public void modifierDocRetour(DocRetourDto docRetourDto);

  public ArrayList<DocRetourDto> lireDocRetourUser(UserDto userDto);

  public ArrayList<DocRetourDto> listerTousDocRetour();

  public DocRetourDto lireDocRetourMobilite(MobiliteDto mobiliteDto);

  public DocRetourDto lireDocRetourPk(DocRetourDto docRetourDto);

}
