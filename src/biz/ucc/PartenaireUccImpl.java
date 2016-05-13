package biz.ucc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

import biz.biz.PartenaireBiz;
import biz.dto.DepartementDto;
import biz.dto.PartenaireDto;
import biz.dto.PaysDto;
import exception.BizException;
import exception.FatalException;
import persistance.dal.DalBackendServices;
import persistance.dal.DalServices;
import persistance.dao.DepartementDao;
import persistance.dao.PartenaireDao;
import persistance.dao.PaysDao;
import persistance.dao.UserDao;

public class PartenaireUccImpl implements PartenaireUcc {
  private PartenaireDao partDao;
  private PaysDao paysDao;
  private DepartementDao departDao;
  private UserDao userDao;
  private DalBackendServices dalb;

  /**
   * constructeur PartenaireUccImpl.
   * 
   * @param partDao partDao
   * @param paysDao paysDao
   * @param departDao departDao
   * @param userDao userDao
   */
  public PartenaireUccImpl(DalBackendServices dalb, PartenaireDao partDao,
      PaysDao paysDao, DepartementDao departDao, UserDao userDao) {
    this.partDao = partDao;
    this.departDao = departDao;
    this.paysDao = paysDao;
    this.userDao = userDao;
    this.dalb = dalb;
  }

  @Override
  public ArrayList<PartenaireDto> rechercherPartenaire(String champRecherche) {
    ArrayList<PartenaireDto> partList = null;

    try {
      ((DalServices) dalb).openConnection();
      partList = partDao.rechercherPartenaire(champRecherche);
      for (PartenaireDto part : partList) {
        PartenaireDto partDto = partDao.lirePartenairePk(part);
        partDto.setCreateur(UtilUcc.remplirUser(partDto.getCreateur(), userDao));
        partDto = partDao.lireAllDepartementsPartenaire(part);
        partDto.setPays(UtilUcc.remplirPays(partDto.getPays(), paysDao));
      }
      return partList;
    } catch (FatalException err) {

      throw err;

    } finally {
      ((DalServices) dalb).closeConnection();
    }

  }



  @Override
  public HashMap<String, PartenaireDto> rechercherPartenaireAgreeParDepartement(
      DepartementDto departement) {
    HashMap<String, PartenaireDto> partMap = null;
    try {
      ((DalServices) dalb).openConnection();
      partMap = partDao.getPartenaireAgreeParDepartement(departement);
      return partMap;
    } catch (FatalException err) {
      throw err;
    } finally {
      ((DalServices) dalb).closeConnection();
    }
  }

  @Override
  public HashMap<String, String> getAllDepartement() {
    HashMap<String, String> depMap = null;
    try {
      ((DalServices) dalb).openConnection();
      depMap = departDao.lireTousDepartements();
      return depMap;
    } catch (FatalException err) {
      throw err;
    } finally {
      ((DalServices) dalb).closeConnection();
    }
  }

  @Override
  public SortedMap<String, PaysDto> getAllPays() {
    SortedMap<String, PaysDto> paysMap = null;
    try {
      ((DalServices) dalb).openConnection();
      paysMap = paysDao.lireTousPays();
      return paysMap;
    } catch (FatalException err) {
      throw err;
    } finally {
      ((DalServices) dalb).closeConnection();
    }

  }

  @Override
  public PartenaireDto creePartenaire(PartenaireDto part) {
    ((PartenaireBiz) part).checkPartenaire();

    try {
      ((DalServices) dalb).openConnection();
      ((DalServices) dalb).startTransaction();


      part = partDao.ecrirePartenaire(part);
      ((DalServices) dalb).commitTransaction();
    } catch (FatalException err) {
      ((DalServices) dalb).rollBackTransaction();
      throw err;
    } finally {
      ((DalServices) dalb).closeConnection();
    }

    return part;

  }

  @Override
  public PartenaireDto rechercherPartenairePk(PartenaireDto part) {
    PartenaireDto partDto;
    try {
      ((DalServices) dalb).openConnection();
      partDto = partDao.lirePartenairePk(part);
      partDto.setCreateur(UtilUcc.remplirUser(partDto.getCreateur(), userDao));
      partDto = partDao.lireAllDepartementsPartenaire(partDto);
      partDto.setPays(UtilUcc.remplirPays(partDto.getPays(), paysDao));
      ((PartenaireBiz) partDto).checkPartenaire();
    } finally {
      ((DalServices) dalb).closeConnection();
    }
    return partDto;
  }

  /**
   * remplis toutes les dto de partenaires.
   * 
   * @param partenaires liste partenaire
   * 
   * @return {@code ArrayList<PartenaireDto> } liste de partenaire
   */
  public ArrayList<PartenaireDto> remplirAllPart(ArrayList<PartenaireDto> partenaires) {
    for (PartenaireDto partenaireDto : partenaires) {
      partenaireDto = remplirPart(partenaireDto);
    }
    return partenaires;
  }

  private PartenaireDto remplirPart(PartenaireDto partenaireDto) {
    partenaireDto.setCreateur(UtilUcc.remplirUser(partenaireDto.getCreateur(), userDao));
    partenaireDto.setPays(UtilUcc.remplirPays(partenaireDto.getPays(), paysDao));

    return partenaireDto;
  }

  @Override
  public boolean supprimerPartenaire(PartenaireDto partenaireDto) {

    try {
      ((DalServices) dalb).openConnection();

      // check num Version
      int numVersionDb = ((PartenaireBiz) partDao.lirePartenairePk(partenaireDto)).getNumVersion();
      if (numVersionDb != ((PartenaireBiz) partenaireDto).getNumVersion()) {
        throw new BizException("NumVersion differents");
      }

      // check inutilité
      boolean flagDb = partDao.lirePartenairePk(partenaireDto).getSupprime();
      if (partenaireDto.getSupprime() == flagDb) {
        throw new BizException("Partenaire déjà réhabilité/supprimé");
      }

      ((DalServices) dalb).startTransaction();

      partDao.mettreAjourPartenaire(partenaireDto);
      ((DalServices) dalb).commitTransaction();
      return true;
    } catch (FatalException err) {
      ((DalServices) dalb).rollBackTransaction();
      throw err;
    } finally {
      ((DalServices) dalb).closeConnection();
    }

  }

  @Override
  public ArrayList<PartenaireDto> listerPartenairessupprimes() {

    ArrayList<PartenaireDto> partList = null;

    try {
      ((DalServices) dalb).openConnection();
      partList = partDao.listerPartenairessupprimes();
      for (PartenaireDto part : partList) {
        PartenaireDto partDto = partDao.lirePartenairePk(part);
        partDto.setCreateur(UtilUcc.remplirUser(partDto.getCreateur(), userDao));
        partDto = partDao.lireAllDepartementsPartenaire(part);
        partDto.setPays(UtilUcc.remplirPays(partDto.getPays(), paysDao));
      }
      return partList;
    } finally {
      ((DalServices) dalb).closeConnection();
    }

  }

  @Override
  public boolean verifierPartenaireExistant(PartenaireDto partenaireDto) {

    try {
      ((DalServices) dalb).openConnection();
      return partDao.verifierPartenaireExistant(partenaireDto);
    } finally {
      ((DalServices) dalb).closeConnection();
    }

  }

}
