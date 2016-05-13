package biz.ucc;

import biz.biz.DocRetourBiz;
import biz.dto.DocRetourDto;
import biz.dto.MobiliteDto;
import biz.dto.UserDto;
import biz.enumerate.EtatMobilite;
import exception.BizException;
import exception.FatalException;
import persistance.dal.DalBackendServices;
import persistance.dal.DalServices;
import persistance.dao.DepartementDao;
import persistance.dao.DocRetourDao;
import persistance.dao.MobiliteDao;
import persistance.dao.PaysDao;
import persistance.dao.UserDao;

import java.util.ArrayList;

public class DocRetourUccImpl implements DocRetourUcc {
  private DocRetourDao docRetourDao;
  private UserDao userDao;
  private MobiliteDao mobiliteDao;
  private DepartementDao departementDao;
  private PaysDao paysDao;
  private DalBackendServices dalb;

  /**
   * Constructeur.
   * 
   * @param docRetourDao docRetourDao
   * @param userDao userDao
   */
  public DocRetourUccImpl(DalBackendServices dalb, DocRetourDao docRetourDao,
      UserDao userDao, MobiliteDao mobiliteDao, DepartementDao departementDao, PaysDao paysDao) {
    this.docRetourDao = docRetourDao;
    this.userDao = userDao;
    this.departementDao = departementDao;
    this.mobiliteDao = mobiliteDao;
    this.paysDao = paysDao;
    this.dalb = dalb;
  }

  @Override
  public void ecrireDocRetour(DocRetourDto docRetourDto) {
    ((DocRetourBiz) docRetourDto).checkDocRetour();
    try {
      ((DalServices) dalb).openConnection();
      ((DalServices) dalb).startTransaction();

      docRetourDao.ecrireDocRetour(docRetourDto);
      docRetourDto.setEtudiant(UtilUcc.remplirUser(docRetourDto.getEtudiant(), userDao));
      ((DalServices) dalb).commitTransaction();
    } catch (FatalException err) {
      ((DalServices) dalb).rollBackTransaction();
      throw err;

    } finally {
      ((DalServices) dalb).closeConnection();
    }
  }

  @Override
  public void modifierDocRetour(DocRetourDto docRetourDto) {

    ((DocRetourBiz) docRetourDto).checkDocRetour();

    try {
      ((DalServices) dalb).openConnection();
      
      int numVersionDb = ((DocRetourBiz) docRetourDao.lireDocRetour(docRetourDto)).getNumVersion();
      if (numVersionDb != ((DocRetourBiz) docRetourDto).getNumVersion()) {
        throw new BizException("NumVersion differents");
      }
      
      ((DalServices) dalb).startTransaction();
      docRetourDao.modifierDocRetour(docRetourDto);
      MobiliteDto mobilite = mobiliteDao.lireMobilitePk(docRetourDto.getMoblite());
      if (mobilite.getEtat() == EtatMobilite.AENVOYERDEM) {
        if (docRetourDto.getPreuvePassageTest() && docRetourDto.getRapportFinal()
            && docRetourDto.getReleveNoteOuCertifStage() && docRetourDto.getAttestationSejour()) {
          mobilite.setEtat(EtatMobilite.APAYERSOLDE);
        }
        mobiliteDao.modifierMobilite(mobilite);
      }
      docRetourDto.setEtudiant(UtilUcc.remplirUser(docRetourDto.getEtudiant(), userDao));
      ((DalServices) dalb).commitTransaction();
    } catch (FatalException err) {
      ((DalServices) dalb).rollBackTransaction();
      throw err;

    } finally {
      ((DalServices) dalb).closeConnection();
    }
  }

  @Override
  public ArrayList<DocRetourDto> listerTousDocRetour() {
    ArrayList<DocRetourDto> listDocRet;
    try {
      ((DalServices) dalb).openConnection();
      listDocRet = remplirAllDocRetour(docRetourDao.lireToutDocRetour());
    } finally {
      ((DalServices) dalb).closeConnection();
    }
    return listDocRet;
  }

  @Override
  public DocRetourDto lireDocRetourPk(DocRetourDto docRetourDto) {
    DocRetourDto docRet;
    try {
      ((DalServices) dalb).openConnection();
      docRet = remplirDocRetour(docRetourDao.lireDocRetour(docRetourDto));
    } finally {
      ((DalServices) dalb).closeConnection();
    }
    return docRet;

  }

  @Override
  public ArrayList<DocRetourDto> lireDocRetourUser(UserDto userDto) {
    ArrayList<DocRetourDto> tmp;
    try {
      ((DalServices) dalb).openConnection();
      tmp = docRetourDao.lireDocRetourUser(userDto);
      userDto = UtilUcc.remplirUser(userDto, userDao);

      for (DocRetourDto docRetourDto : tmp) {
        docRetourDto.setEtudiant(userDto);
        docRetourDto.setMobilite(UtilUcc.remplirMobilite(docRetourDto.getMoblite(), mobiliteDao));
      }
    } finally {
      ((DalServices) dalb).closeConnection();
    }
    return tmp;
  }

  @Override
  public DocRetourDto lireDocRetourMobilite(MobiliteDto mobiliteDto) {
    DocRetourDto docRetourDto;
    try {
      ((DalServices) dalb).openConnection();
      docRetourDto = docRetourDao.lireDocRetourMobilite(mobiliteDto);
      mobiliteDto = UtilUcc.remplirMobilite(mobiliteDto, mobiliteDao);
      docRetourDto.setEtudiant(UtilUcc.remplirUser(
          docRetourDto.getEtudiant(), userDao));
      mobiliteDto.setPays(UtilUcc.remplirPays(mobiliteDto.getPays(), paysDao));
      docRetourDto.setMobilite(mobiliteDto);
    } finally {
      ((DalServices) dalb).closeConnection();
    }

    return docRetourDto;
  }

  private DocRetourDto remplirDocRetour(DocRetourDto docRetourDto) {

    try {
      ((DalServices) dalb).openConnection();
      UserDto etudiant = UtilUcc.remplirUser(docRetourDto.getEtudiant(), userDao);
      etudiant.setDepartement(UtilUcc.remplirDepartement(
          etudiant.getDepartement(), departementDao));
      docRetourDto.setEtudiant(etudiant);
      docRetourDto.setMobilite(UtilUcc.remplirMobilite(docRetourDto.getMoblite(), mobiliteDao));
    } finally {
      ((DalServices) dalb).closeConnection();
    }
    return docRetourDto;
  }

  private ArrayList<DocRetourDto> remplirAllDocRetour(ArrayList<DocRetourDto> docs) {

    for (DocRetourDto docRetourDto : docs) {
      docRetourDto = remplirDocRetour(docRetourDto);
    }

    return docs;
  }



}
