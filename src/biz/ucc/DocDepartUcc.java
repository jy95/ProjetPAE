package biz.ucc;

import biz.dto.DocDepartDto;
import biz.dto.MobiliteDto;
import biz.dto.UserDto;

import java.util.ArrayList;

public interface DocDepartUcc {

  public void ecrireDocDepart(DocDepartDto docDepartDto);

  public void modifierDocDepart(DocDepartDto docDepartDto);

  public ArrayList<DocDepartDto> lireDocDepartUser(UserDto userDto);

  public ArrayList<DocDepartDto> listerTousDocDepart();

  public DocDepartDto lireDocDepartMobilite(MobiliteDto mobiliteDto);

  public DocDepartDto lireDocDepartPk(DocDepartDto docDepartDto);

}
