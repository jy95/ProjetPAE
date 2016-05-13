package persistance.dao;

import biz.biz.MobiliteBiz;
import biz.biz.UserBiz;
import biz.dto.DocDepartDto;
import biz.dto.MobiliteDto;
import biz.dto.UserDto;
import biz.factory.DtoFactory;
import exception.BizException;
import exception.FatalException;

import java.util.ArrayList;

public class MockDocDepart implements DocDepartDao {
  DtoFactory dtoFact;

  public MockDocDepart(DtoFactory dtoFactory) {
    dtoFact = dtoFactory;
  }

  @Override
  public void ecrireDocDepart(DocDepartDto docDepart) {
    if (docDepart.getId() == 2)
      throw new FatalException();
  }

  @Override
  public DocDepartDto lireDocDepartPk(DocDepartDto docDepart) {
    if (docDepart.getId() == 2)
      throw new BizException("DocDepart introuvable");
    if (docDepart.getId() == 3)
      throw new FatalException("Boudin SQL!");
    UserDto user = dtoFact.getUserDto();
    ((UserBiz) user).setPkUser(1);
    DocDepartDto docDepartDto = dtoFact.getDocDepartDto();
    docDepartDto.setEtudiant(user);
    MobiliteDto mobiliteDto = dtoFact.getMobiliteDto();
    ((MobiliteBiz) mobiliteDto).setPkMobilite(1);
    docDepartDto.setMobilite(mobiliteDto);
    return docDepartDto;
  }

  @Override
  public ArrayList<DocDepartDto> lireToutDocDepart() {
    ArrayList<DocDepartDto> arr = new ArrayList<>();
    DocDepartDto doc = dtoFact.getDocDepartDto();
    arr.add(doc);
    return arr;
  }

  @Override
  public ArrayList<DocDepartDto> lireDocDepartUser(UserDto userDto) {
    ArrayList<DocDepartDto> arr = new ArrayList<>();
    DocDepartDto doc = dtoFact.getDocDepartDto();
    arr.add(doc);
    return arr;
  }

  @Override
  public DocDepartDto lireDocDepartMobilite(MobiliteDto mobiliteDto) {
    if (mobiliteDto.getId() == 2)
      throw new BizException("DocDepart introuvable");
    if (mobiliteDto.getId() == 3)
      throw new FatalException("Boudin SQL!");

    UserDto user = dtoFact.getUserDto();
    ((UserBiz) user).setPkUser(1);
    DocDepartDto docDepartDto = dtoFact.getDocDepartDto();
    docDepartDto.setEtudiant(user);
    return docDepartDto;
  }

  @Override
  public DocDepartDto modifierDocDepart(DocDepartDto docDepart) {
    if(docDepart.getId() == 4)throw new FatalException();
    return null;
  }

}
