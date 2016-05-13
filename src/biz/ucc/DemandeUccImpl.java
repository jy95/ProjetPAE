package biz.ucc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

import biz.biz.DemandeBiz;
import biz.dto.DemandeDto;
import biz.dto.UserDto;
import exception.BizException;
import exception.FatalException;
import persistance.dal.DalBackendServices;
import persistance.dal.DalServices;
import persistance.dao.DemandeDao;
import persistance.dao.DepartementDao;
import persistance.dao.PartenaireDao;
import persistance.dao.PaysDao;
import persistance.dao.UserDao;

public class DemandeUccImpl implements DemandeUcc {
  private DemandeDao demandeDao;
  private UserDao userDao;
  private PartenaireDao partenaireDao;
  private PaysDao paysDao;
  private DepartementDao departementDao;
  private DalBackendServices dalb;

  /**
   * constructeur DemandeUccImpl.
   * 
   * @param demandeDao demandeDao
   */
  public DemandeUccImpl(DalBackendServices dalb, DemandeDao demandeDao, 
      UserDao userDao, PartenaireDao partenaireDao, PaysDao paysDao,
      DepartementDao departementDao) {
    this.demandeDao = demandeDao;
    this.userDao = userDao;
    this.partenaireDao = partenaireDao;
    this.paysDao = paysDao;
    this.dalb = dalb;
    this.departementDao = departementDao;
  }

  @Override
  public void creerDemande(DemandeDto demande) {
    demande.setDateIntroduction(LocalDateTime.now());
    ((DemandeBiz) demande).checkDemande();
    try {

      ((DalServices) dalb).openConnection();
      ((DalServices) dalb).startTransaction();
      demandeDao.ecrireDemande(demande);
      ((DalServices) dalb).commitTransaction();

    } catch (FatalException err) {
      ((DalServices) dalb).rollBackTransaction();
      throw err;
    } finally {
      ((DalServices) dalb).closeConnection();
    }


  }

  @Override
  public void validerDemande(DemandeDto demandeDto) {
    ((DemandeBiz) demandeDto).checkDemande();
    try {
      ((DalServices) dalb).openConnection();
      int numVersionDb = ((DemandeBiz) demandeDao.lireDemande(demandeDto)).getNumVersion();
      if (numVersionDb != ((DemandeBiz) demandeDto).getNumVersion()) {
        throw new BizException("NumVersion differents");
      }

      ((DalServices) dalb).startTransaction();
      demandeDao.validerDemande(demandeDto);
      ((DalServices) dalb).commitTransaction();

    } catch (FatalException err) {
      ((DalServices) dalb).rollBackTransaction();
      throw err;
    } finally {
      ((DalServices) dalb).closeConnection();
    }

  }

  @Override
  public void changerPreferenceDemande(DemandeDto demandeDto, int preference) {
    try {
      ((DemandeBiz) demandeDto).checkDemande();
      ((DalServices) dalb).openConnection();
      demandeDto.setPreference(preference);

      int numVersionDb = ((DemandeBiz) demandeDao.lireDemande(demandeDto)).getNumVersion();
      int numVersiondto = ((DemandeBiz) demandeDto).getNumVersion();
      if (numVersionDb != numVersiondto) {
        throw new BizException("NumVersion differents");
      }

      ((DalServices) dalb).startTransaction();
      demandeDao.modifierPreferenceDemande(demandeDto);

      ((DalServices) dalb).commitTransaction();
    } catch (FatalException err) {
      ((DalServices) dalb).rollBackTransaction();
      throw err;

    } finally {
      ((DalServices) dalb).closeConnection();
    }


  }

  @Override
  public ArrayList<DemandeDto> listerToutesDemandes() {
    ArrayList<DemandeDto> listDem;
    try {
      ((DalServices) dalb).openConnection();
      listDem = remplirAllDtoDemande(demandeDao.lireToutesDemandes());
    } finally {
      ((DalServices) dalb).closeConnection();
    }
    return listDem;
  }

  @Override
  public ArrayList<DemandeDto> rechercherDemandes(UserDto user) {
    ArrayList<DemandeDto> listDem;
    try {
      ((DalServices) dalb).openConnection();
      listDem = remplirAllDtoDemande(demandeDao.rechercherDemandes(user));
    } finally {
      ((DalServices) dalb).closeConnection();
    }
    return listDem;

  }

  /**
   * remplis toutes les Dto demande qu'il y a dans remplir demande.
   * 
   * @param demandes liste de demande
   * 
   * @return {@code ArrayList<DemandeDto> } demande
   */
  public ArrayList<DemandeDto> remplirAllDtoDemande(ArrayList<DemandeDto> demandes) {
    try {
      ((DalServices) dalb).openConnection();
      for (DemandeDto demandeDto : demandes) {
        UserDto userDto = UtilUcc.remplirUser(demandeDto.getEtudiant(), userDao);
        userDto
            .setDepartement(UtilUcc.remplirDepartement(userDto.getDepartement(), departementDao));
        demandeDto.setEtudiant(userDto);
        if (demandeDto.getPartenaire() != null && demandeDto.getPartenaire().getId() != 0) {
          demandeDto
              .setPartenaire(UtilUcc.remplirPartenaire(demandeDto.getPartenaire(), partenaireDao));
        }
        if (!Objects.equals(demandeDto.getPays(), null)) {
          demandeDto.setPays(UtilUcc.remplirPays(demandeDto.getPays(), paysDao));
        }
      }
    } finally {
      ((DalServices) dalb).closeConnection();
    }
    return demandes;
  }

  @Override
  public DemandeDto infoDemande(DemandeDto demande) {
    DemandeDto demandeDto;
    try {
      ((DalServices) dalb).openConnection();
      demandeDto = demandeDao.lireDemande(demande);
      ArrayList<DemandeDto> demandeList = new ArrayList<DemandeDto>(1);
      demandeList.add(demandeDto);
      remplirAllDtoDemande(demandeList);
      demandeDto = demandeList.get(0);
    } finally {
      ((DalServices) dalb).closeConnection();
    }
    return demandeDto;
  }

}
