package persistance.dao;

import biz.biz.MobiliteBiz;
import biz.biz.UserBiz;
import biz.dto.DocRetourDto;
import biz.dto.MobiliteDto;
import biz.dto.UserDto;
import biz.factory.DtoFactory;
import exception.BizException;
import exception.FatalException;

import java.util.ArrayList;

public class MockDocRetour implements DocRetourDao {
  DtoFactory dtoFact;

  public MockDocRetour(DtoFactory dtoFactory) {
    dtoFact = dtoFactory;
  }

  @Override
  public void ecrireDocRetour(DocRetourDto docRetour) {
    if (docRetour.getId() == 2)
      throw new FatalException();
  }

  @Override
  public DocRetourDto lireDocRetour(DocRetourDto docRetour) {
    if (docRetour.getId() == 2)
      throw new BizException("DocRetour introuvable");
    if (docRetour.getId() == 3)
      throw new FatalException("Boudin SQL!");
    UserDto user = dtoFact.getUserDto();
    ((UserBiz) user).setPkUser(1);
    DocRetourDto docRetourDto = dtoFact.getDocRetourDto();
    docRetourDto.setEtudiant(user);
    MobiliteDto mobiliteDto = dtoFact.getMobiliteDto();
    ((MobiliteBiz) mobiliteDto).setPkMobilite(1);
    docRetourDto.setMobilite(mobiliteDto);
    return docRetourDto;
  }

  @Override
  public ArrayList<DocRetourDto> lireToutDocRetour() {
    ArrayList<DocRetourDto> arr = new ArrayList<>();
    DocRetourDto doc = dtoFact.getDocRetourDto();
    arr.add(doc);
    return arr;
  }

  @Override
  public ArrayList<DocRetourDto> lireDocRetourUser(UserDto userDto) {
    ArrayList<DocRetourDto> arr = new ArrayList<>();
    DocRetourDto doc = dtoFact.getDocRetourDto();
    arr.add(doc);
    return arr;
  }

  @Override
  public DocRetourDto lireDocRetourMobilite(MobiliteDto mobiliteDto) {
    if (mobiliteDto.getId() == 2)
      throw new BizException("DocRetour introuvable");
    if (mobiliteDto.getId() == 3)
      throw new FatalException("Boudin SQL!");

    UserDto user = dtoFact.getUserDto();
    ((UserBiz) user).setPkUser(1);
    DocRetourDto docRetourDto = dtoFact.getDocRetourDto();
    docRetourDto.setEtudiant(user);
    return docRetourDto;
  }

  @Override
  public DocRetourDto modifierDocRetour(DocRetourDto docRetour) {
    if(docRetour.getId() == 4)throw new FatalException();
    return null;
  }

}
