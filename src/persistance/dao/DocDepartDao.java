package persistance.dao;

import biz.dto.DocDepartDto;
import biz.dto.MobiliteDto;
import biz.dto.UserDto;

import java.util.ArrayList;

public interface DocDepartDao {
  public void ecrireDocDepart(DocDepartDto docDepart);

  public DocDepartDto lireDocDepartPk(DocDepartDto docDepart);

  public ArrayList<DocDepartDto> lireToutDocDepart();

  public ArrayList<DocDepartDto> lireDocDepartUser(UserDto userDto);

  public DocDepartDto lireDocDepartMobilite(MobiliteDto mobiliteDto);

  public DocDepartDto modifierDocDepart(DocDepartDto docDepart);
}
