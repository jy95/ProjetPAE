package biz.ucc;

import biz.biz.DocDepartBiz;
import biz.dto.DocDepartDto;
import biz.dto.MobiliteDto;
import biz.dto.UserDto;
import biz.enumerate.EtatMobilite;
import exception.BizException;
import exception.FatalException;
import persistance.dal.DalBackendServices;
import persistance.dal.DalServices;
import persistance.dao.DepartementDao;
import persistance.dao.DocDepartDao;
import persistance.dao.MobiliteDao;
import persistance.dao.PaysDao;
import persistance.dao.UserDao;

import java.util.ArrayList;

public class DocDepartUccImpl implements DocDepartUcc {
  private DocDepartDao docDepartDao;
  private UserDao userDao;
  private MobiliteDao mobiliteDao;
  private DepartementDao departementDao;
  private PaysDao paysDao;
  private DalBackendServices dalb;

  /**
   * Constructeur.
   * 
   * @param docDepartDao docDepartDao
   * @param userDao userDao
   */
  public DocDepartUccImpl(DalBackendServices dalb, DocDepartDao docDepartDao,
      UserDao userDao, MobiliteDao mobiliteDao, DepartementDao departementDao, PaysDao paysDao) {
    this.docDepartDao = docDepartDao;
    this.userDao = userDao;
    this.departementDao = departementDao;
    this.mobiliteDao = mobiliteDao;
    this.paysDao = paysDao;
    this.dalb = dalb;
  }


  @Override
  public void ecrireDocDepart(DocDepartDto docDepartDto) {

    ((DocDepartBiz) docDepartDto).checkDocDepart();
    try {
      ((DalServices) dalb).openConnection();
      ((DalServices) dalb).startTransaction();

      docDepartDao.ecrireDocDepart(docDepartDto);

      ((DalServices) dalb).commitTransaction();
    } catch (FatalException err) {
      ((DalServices) dalb).rollBackTransaction();
      throw err;
    } finally {
      ((DalServices) dalb).closeConnection();
    }

  }


  @Override
  public void modifierDocDepart(DocDepartDto docDepartDto) {
    ((DocDepartBiz) docDepartDto).checkDocDepart();

    try {
      ((DalServices) dalb).openConnection();

      int numVersionDb =
          ((DocDepartBiz) docDepartDao.lireDocDepartPk(docDepartDto)).getNumVersion();
      if (numVersionDb != ((DocDepartBiz) docDepartDto).getNumVersion()) {
        throw new BizException("NumVersion differents");
      }

      ((DalServices) dalb).startTransaction();


      docDepartDao.modifierDocDepart(docDepartDto);
      MobiliteDto mobilite = mobiliteDao.lireMobilitePk(docDepartDto.getMobilite());
      if (mobilite.getEtat() == EtatMobilite.CREEE || mobilite.getEtat() == EtatMobilite.ENPREPA) {
        if (docDepartDto.getCharteEtudiant() && docDepartDto.getContratBourse()
            && docDepartDto.getConventionStageOuEtude() && docDepartDto.getDocEngagement()
            && docDepartDto.getPreuveTestLangue()) {
          mobilite.setEtat(EtatMobilite.APAYER);
        } else {
          if (docDepartDto.getCharteEtudiant() || docDepartDto.getContratBourse()
              || docDepartDto.getConventionStageOuEtude() || docDepartDto.getDocEngagement()
              || docDepartDto.getPreuveTestLangue()) {
            mobilite.setEtat(EtatMobilite.ENPREPA);
          }
        }
        mobiliteDao.modifierMobilite(mobilite);
      }
      remplirDtoDocDepart(docDepartDto);
      ((DalServices) dalb).commitTransaction();
    } catch (FatalException err) {
      ((DalServices) dalb).rollBackTransaction();
      throw err;
    } finally {
      ((DalServices) dalb).closeConnection();
    }

  }

  @Override
  public ArrayList<DocDepartDto> lireDocDepartUser(UserDto userDto) {
    ArrayList<DocDepartDto> tmp;
    try {
      ((DalServices) dalb).openConnection();
      tmp = docDepartDao.lireDocDepartUser(userDto);
      userDto = UtilUcc.remplirUser(userDto, userDao);
      for (DocDepartDto docDepartDto : tmp) {
        docDepartDto.setEtudiant(userDto);
        docDepartDto.setMobilite(UtilUcc.remplirMobilite(docDepartDto.getMobilite(), mobiliteDao));
      }
    } finally {
      ((DalServices) dalb).closeConnection();
    }
    return tmp;
  }

  @Override
  public ArrayList<DocDepartDto> listerTousDocDepart() {
    ArrayList<DocDepartDto> tmp;
    try {
      ((DalServices) dalb).openConnection();
      tmp = docDepartDao.lireToutDocDepart();
      for (DocDepartDto docDepartDto : tmp) {
        docDepartDto = remplirDtoDocDepart(docDepartDto);
      }
    } finally {
      ((DalServices) dalb).closeConnection();
    }
    return tmp;
  }

  @Override
  public DocDepartDto lireDocDepartMobilite(MobiliteDto mobiliteDto) {
    DocDepartDto docDepartDto;
    try {
      ((DalServices) dalb).openConnection();
      docDepartDto = docDepartDao.lireDocDepartMobilite(mobiliteDto);
      UserDto etudiant = UtilUcc.remplirUser(docDepartDto.getEtudiant(), userDao);
      etudiant.setDepartement(UtilUcc.remplirDepartement(
          etudiant.getDepartement(), departementDao));
      docDepartDto.setEtudiant(etudiant);
      mobiliteDto = UtilUcc.remplirMobilite(mobiliteDto, mobiliteDao);
      mobiliteDto.setPays(UtilUcc.remplirPays(mobiliteDto.getPays(), paysDao));
      docDepartDto.setMobilite(mobiliteDto);
    } finally {
      ((DalServices) dalb).closeConnection();
    }
    return docDepartDto;
  }

  @Override
  public DocDepartDto lireDocDepartPk(DocDepartDto docDepartDto) {
    try {
      ((DalServices) dalb).openConnection();
      docDepartDto = remplirDtoDocDepart(docDepartDao.lireDocDepartPk(docDepartDto));
    } finally {
      ((DalServices) dalb).closeConnection();
    }
    return docDepartDto;
  }

  private DocDepartDto remplirDtoDocDepart(DocDepartDto docDepartDto) {
    docDepartDto.setEtudiant(UtilUcc.remplirUser(docDepartDto.getEtudiant(), userDao));
    docDepartDto.setMobilite(UtilUcc.remplirMobilite(docDepartDto.getMobilite(), mobiliteDao));
    return docDepartDto;
  }

}
