package biz.ucc;

import java.util.ArrayList;

import biz.biz.AnnulationBiz;
import biz.biz.DocDepartBiz;
import biz.biz.DocRetourBiz;
import biz.biz.MobiliteBiz;
import biz.dto.AnnulationDto;
import biz.dto.DemandeDto;
import biz.dto.DocDepartDto;
import biz.dto.DocRetourDto;
import biz.dto.MobiliteDto;
import biz.dto.PartenaireDto;
import biz.dto.UserDto;
import biz.enumerate.EtatMobilite;
import biz.factory.DtoFactory;
import biz.util.Util;
import exception.BizException;
import exception.FatalException;
import persistance.dal.DalBackendServices;
import persistance.dal.DalServices;
import persistance.dao.AnnulationDao;
import persistance.dao.DemandeDao;
import persistance.dao.DemandePaiementDao;
import persistance.dao.DepartementDao;
import persistance.dao.DocDepartDao;
import persistance.dao.DocRetourDao;
import persistance.dao.MobiliteDao;
import persistance.dao.PartenaireDao;
import persistance.dao.PaysDao;
import persistance.dao.UserDao;

public class MobiliteUccImpl implements MobiliteUcc {

  private DtoFactory dtoFact;
  private MobiliteDao mobiliteDao;
  private UserDao userDao;
  private DemandeDao demandeDao;
  private PartenaireDao partenaireDao;
  private AnnulationDao annulationDao;
  private PaysDao paysDao;
  private DepartementDao departementDao;
  private DalBackendServices dalb;
  private DocRetourDao docRetourDao;
  private DocDepartDao docDepartDao;
  private DemandePaiementDao demandePaiementDao;

  /**
   * constructeur MobiliteUccImpl.
   * 
   * @param dtoFact dtoFact
   * @param mobiliteDao mobiDao
   * @param userDao userDao
   */
  public MobiliteUccImpl(DalBackendServices dalb, DtoFactory dtoFact, MobiliteDao mobiliteDao,
      UserDao userDao, DemandeDao demandeDao, PartenaireDao partenaireDao,
      AnnulationDao annulationDao, PaysDao paysDao, DepartementDao departementDao,
      DocDepartDao docDepartDao, DocRetourDao docRetourDao, DemandePaiementDao demandePaiementDao) {
    this.dtoFact = dtoFact;
    this.mobiliteDao = mobiliteDao;
    this.demandeDao = demandeDao;
    this.userDao = userDao;
    this.partenaireDao = partenaireDao;
    this.annulationDao = annulationDao;
    this.paysDao = paysDao;
    this.dalb = dalb;
    this.departementDao = departementDao;
    this.docDepartDao = docDepartDao;
    this.docRetourDao = docRetourDao;
    this.demandePaiementDao = demandePaiementDao;
  }


  @Override
  public ArrayList<MobiliteDto> rechercherMobilite(EtatMobilite etat, String anneeAcademique) {
    ArrayList<MobiliteDto> listMobi;
    try {
      ((DalServices) dalb).openConnection();
      listMobi = remplirAllMobi(mobiliteDao.rechercherMobilite(etat, anneeAcademique));
    } finally {
      ((DalServices) dalb).closeConnection();
    }
    return listMobi;
  }


  @Override
  public void ecrireMobilite(MobiliteDto mobiliteDto) {

    mobiliteDto.setEtat(EtatMobilite.CREEE);
    ((MobiliteBiz) mobiliteDto).checkMobilite();
    try {
      ((DalServices) dalb).openConnection();
      ((DalServices) dalb).startTransaction();

      mobiliteDao.ecrireMobilite(mobiliteDto);
      DocDepartDto docDepart = dtoFact.getDocDepartDto();
      DocRetourDto docRetour = dtoFact.getDocRetourDto();
      docDepart.setMobilite(mobiliteDto);
      docDepart.setEtudiant(mobiliteDto.getEtudiant());
      docRetour.setMobilite(mobiliteDto);
      docRetour.setEtudiant(mobiliteDto.getEtudiant());
      ((DocRetourBiz) docRetour).setNumVersion(0);
      ((DocDepartBiz) docDepart).setNumVersion(0);
      docDepartDao.ecrireDocDepart(docDepart);
      docRetourDao.ecrireDocRetour(docRetour);
      ((DalServices) dalb).commitTransaction();
    } catch (FatalException err) {
      ((DalServices) dalb).rollBackTransaction();
      throw err;
    } finally {
      ((DalServices) dalb).closeConnection();
    }

  }

  @Override
  public void ecrireMobiliteParDemande(DemandeDto demandeDto) {
    MobiliteDto mobiliteDto = dtoFact.getMobiliteDto();

    try {
      ((DalServices) dalb).openConnection();
      ((DalServices) dalb).startTransaction();
      // demandeDto = demandeDao.lireDemande(demandeDto);
      if (demandeDto.getPartenaire() == null) {
        throw new BizException("Pas de partenaire pour la demande selectionné");
      }
      PartenaireDto patenaireDto = partenaireDao.lirePartenairePk(demandeDto.getPartenaire());
      mobiliteDto.setAnneeAcademique(demandeDto.getAnneeAcademique());
      mobiliteDto.setDemande(demandeDto);
      mobiliteDto.setEtat(EtatMobilite.CREEE);
      mobiliteDto.setEtudiant(demandeDto.getEtudiant());
      mobiliteDto.setMobi(false);
      mobiliteDto.setMobilityTool(false);
      mobiliteDto.setPartenaire(patenaireDto);
      mobiliteDto.setPays(demandeDto.getPays());
      mobiliteDto.setProEco(false);
      mobiliteDto.setProgramme(demandeDto.getProgramme());
      mobiliteDto.setQuadri(demandeDto.getQuadri());
      mobiliteDto.setStage(demandeDto.getTypeStage());
      mobiliteDto.setVille(demandeDto.getVille());
      demandeDao.validerDemande(demandeDto);
      ((MobiliteBiz) mobiliteDto).checkMobilite();
      mobiliteDao.ecrireMobilite(mobiliteDto);
      DocDepartDto docDepart = dtoFact.getDocDepartDto();
      DocRetourDto docRetour = dtoFact.getDocRetourDto();
      docDepart.setMobilite(mobiliteDto);
      docDepart.setEtudiant(mobiliteDto.getEtudiant());
      docRetour.setMobilite(mobiliteDto);
      docRetour.setEtudiant(mobiliteDto.getEtudiant());
      ((DocRetourBiz) docRetour).setNumVersion(0);
      ((DocDepartBiz) docDepart).setNumVersion(0);
      docDepartDao.ecrireDocDepart(docDepart);
      docRetourDao.ecrireDocRetour(docRetour);
      ((DalServices) dalb).commitTransaction();
    } catch (FatalException err) {
      ((DalServices) dalb).rollBackTransaction();
      throw err;
    } finally {
      ((DalServices) dalb).closeConnection();
    }

  }


  @Override
  public MobiliteDto rechercherMobilitePk(MobiliteDto mobiliteDto) {
    MobiliteDto mobiliteDto2 = null;
    try {
      ((DalServices) dalb).openConnection();
      mobiliteDto2 = mobiliteDao.lireMobilitePk(mobiliteDto);
      mobiliteDto2 = remplirAllDtoByMobi(mobiliteDto2);
    } finally {
      ((DalServices) dalb).closeConnection();
    }
    return mobiliteDto2;
  }


  @Override
  public ArrayList<MobiliteDto> listerToutesMobilite() {

    ArrayList<MobiliteDto> allDto;
    try {
      ((DalServices) dalb).openConnection();
      allDto = remplirAllMobi(mobiliteDao.lireToutMobilite());
    } finally {
      ((DalServices) dalb).closeConnection();
    }
    return allDto;

  }

  @Override
  public ArrayList<MobiliteDto> listerToutesMobiliteAnnulees() {
    ArrayList<MobiliteDto> allDto;
    try {
      ((DalServices) dalb).openConnection();
      allDto = remplirAllMobi(mobiliteDao.lireToutMobilite());
    } finally {
      ((DalServices) dalb).closeConnection();
    }
    return allDto;
  }

  @Override
  public void modifierMobilite(MobiliteDto mobiliteDto) {
    ((MobiliteBiz) mobiliteDto).checkMobilite();

    try {
      ((DalServices) dalb).openConnection();

      int numVersionDb = ((MobiliteBiz) mobiliteDao.lireMobilitePk(mobiliteDto)).getNumVersion();
      if (numVersionDb != ((MobiliteBiz) mobiliteDto).getNumVersion()) {
        throw new BizException("NumVersion differents");
      }

      ((DalServices) dalb).startTransaction();

      mobiliteDao.modifierMobilite(mobiliteDto);
      ((DalServices) dalb).commitTransaction();
    } catch (FatalException err) {
      ((DalServices) dalb).rollBackTransaction();
      throw err;
    } finally {
      ((DalServices) dalb).closeConnection();
    }

  }

  @Override
  public void annulerMobilite(MobiliteDto mobiliteDto, AnnulationDto annulationDto) {
    int pkAnnulation;
    if (!Util.checkObject(annulationDto) || mobiliteDto.getId() <= 0) {
      throw new BizException("object param null");
    }

    try {
      ((DalServices) dalb).openConnection();
      MobiliteDto mobiDick = dtoFact.getMobiliteDto();
      mobiDick = mobiliteDao.lireMobilitePk(mobiliteDto);

      ((MobiliteBiz) mobiDick).checkMobilite();


      ((DalServices) dalb).startTransaction();

      if (((AnnulationBiz) annulationDto).getId() == 0) {
        ((AnnulationBiz) annulationDto).checkAnnulation();
        pkAnnulation = annulationDao.ecrireAnnulation(annulationDto);
        ((AnnulationBiz) annulationDto).setPkAnnulation(pkAnnulation);
      }

      mobiDick.setAnnulation(annulationDto);

      int numVersionDb2 = ((MobiliteBiz) mobiDick).getNumVersion();
      if (numVersionDb2 != ((MobiliteBiz) mobiliteDto).getNumVersion()) {
        throw new BizException("NumVersion differents");
      }

      mobiliteDao.modifierMobilite(mobiDick);
      ((DalServices) dalb).commitTransaction();
    } catch (FatalException err) {
      ((DalServices) dalb).rollBackTransaction();
      throw err;
    } finally {
      ((DalServices) dalb).closeConnection();
    }

  }


  @Override
  public void changerEtatMobilite(MobiliteDto mobiliteDto, EtatMobilite etat) throws BizException {
    if ((etat == EtatMobilite.AENVOYERDEM || etat == EtatMobilite.APAYERSOLDE
        || etat == EtatMobilite.ENATTENTE)
        && !Util.checkString(mobiliteDto.getEtudiant().getNumCompte())
        && !Util.checkString(mobiliteDto.getEtudiant().getCodeBic())) {
      throw new BizException("Pas de compte banquaire ou de code BIC pour l'étudiant");
    }
    mobiliteDto.setEtat(etat);
    modifierMobilite(mobiliteDto);
  }

  @Override
  public ArrayList<MobiliteDto> rechercherMobilitesStud(UserDto user) {
    ArrayList<MobiliteDto> tmp;
    try {
      ((DalServices) dalb).openConnection();
      tmp = mobiliteDao.rechercherMobiliteStud(user);
      UserDto etudiant = UtilUcc.remplirUser(user, userDao);
      for (MobiliteDto mobiliteDto : tmp) {
        mobiliteDto.setEtudiant(etudiant);
        mobiliteDto.setDemande(UtilUcc.remplirDemande(mobiliteDto.getDemande(), demandeDao));
        mobiliteDto
            .setPartenaire(UtilUcc.remplirPartenaire(mobiliteDto.getPartenaire(), partenaireDao));
        mobiliteDto.setPays(UtilUcc.remplirPays(mobiliteDto.getPays(), paysDao));
        if (mobiliteDto.getAnnulation() != null && mobiliteDto.getAnnulation().getId() != 0) {
          mobiliteDto
              .setAnnulation(UtilUcc.remplirAnnulation(mobiliteDto.getAnnulation(), annulationDao));
        }
      }
    } finally {
      ((DalServices) dalb).closeConnection();
    }
    return tmp;
  }

  /**
   * rempli toutes les Dto concernées dans l'ArrayList.
   * 
   * @param mobilites liste de mobiliteDto
   * @return {@code ArrayList<MobiliteDto> } les mobilités remplis
   */
  public ArrayList<MobiliteDto> remplirAllMobi(ArrayList<MobiliteDto> mobilites) {

    for (MobiliteDto mobiliteDto : mobilites) {
      mobiliteDto = remplirAllDtoByMobi(mobiliteDto);
    }
    return mobilites;
  }

  /**
   * rempli toutes les Dto concernées par le MobiliteDto.
   * 
   * @param mobiliteDto MobiliteDto
   * @return MobiliteDto MobiliteDto
   */
  public MobiliteDto remplirAllDtoByMobi(MobiliteDto mobiliteDto) {

    UserDto userDto = UtilUcc.remplirUser(mobiliteDto.getEtudiant(), userDao);
    userDto.setDepartement(UtilUcc.remplirDepartement(userDto.getDepartement(), departementDao));
    mobiliteDto.setEtudiant(userDto);
    mobiliteDto.setDemande(UtilUcc.remplirDemande(mobiliteDto.getDemande(), demandeDao));
    mobiliteDto
        .setPartenaire(UtilUcc.remplirPartenaire(mobiliteDto.getPartenaire(), partenaireDao));
    mobiliteDto.setPays(UtilUcc.remplirPays(mobiliteDto.getPays(), paysDao));
    if (mobiliteDto.getAnnulation() != null && mobiliteDto.getAnnulation().getId() != 0) {
      mobiliteDto
          .setAnnulation(UtilUcc.remplirAnnulation(mobiliteDto.getAnnulation(), annulationDao));
    }


    return mobiliteDto;
  }

  @Override
  public ArrayList<MobiliteDto> listerTouteMobiliteAvecDemandePaiement() {
    ArrayList<MobiliteDto> mobilites;
    try {
      ((DalServices) dalb).openConnection();
      mobilites = demandePaiementDao.listerTouteMobiliteAvecDemandePaiement();
      for (MobiliteDto mobiliteDto : mobilites) {

        mobiliteDto = remplirAllDtoByMobi(mobiliteDto);
        if (mobiliteDto.getDemandePaiement1().getProfesseur().getId() != 0) {
          mobiliteDto.getDemandePaiement1().setProfesseur(
              UtilUcc.remplirUser(mobiliteDto.getDemandePaiement1().getProfesseur(), userDao));
        }
        if (mobiliteDto.getDemandePaiement2().getProfesseur().getId() != 0) {
          mobiliteDto.getDemandePaiement2().setProfesseur(
              UtilUcc.remplirUser(mobiliteDto.getDemandePaiement2().getProfesseur(), userDao));
        }

      }
    } finally {
      ((DalServices) dalb).closeConnection();
    }
    return mobilites;
  }
}
