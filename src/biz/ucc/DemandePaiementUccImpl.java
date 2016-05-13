package biz.ucc;


import java.util.ArrayList;

import biz.biz.DemandePaiementBiz;
import biz.dto.DemandePaiementDto;
import biz.dto.MobiliteDto;
import biz.dto.UserDto;
import biz.enumerate.EtatMobilite;
import biz.factory.DtoFactory;
import biz.util.Util;
import exception.BizException;
import exception.FatalException;
import persistance.dal.DalBackendServices;
import persistance.dal.DalServices;
import persistance.dao.DemandePaiementDao;
import persistance.dao.MobiliteDao;
import persistance.dao.UserDao;

public class DemandePaiementUccImpl implements DemandePaiementUcc {

  private DtoFactory dtoFact;
  private DemandePaiementDao demandePaiementDao;
  private UserDao userDao;
  private MobiliteDao mobiliteDao;
  private DalBackendServices dalb;

  /**
   * constructeur DemandePaiementUccImpl.
   * 
   * @param dtoFact dtoFact
   * @param demandePaiementDao demandePaiementDao
   */
  public DemandePaiementUccImpl(DalBackendServices dalb, DtoFactory dtoFact,
      DemandePaiementDao demandePaiementDao, MobiliteDao mobiliteDao,
      UserDao userDao) {
    this.dtoFact = dtoFact;
    this.demandePaiementDao = demandePaiementDao;
    this.userDao = userDao;
    this.mobiliteDao = mobiliteDao;
    this.dalb = dalb;
  }


  @Override
  public void ecrireDemandePaiement(DemandePaiementDto demande) {
    ((DemandePaiementBiz) demande).checkDemandePaiement();
    try {
      ((DalServices) dalb).openConnection();
      ((DalServices) dalb).startTransaction();
      MobiliteDto mobilite = mobiliteDao.lireMobilitePk(demande.getMoblite());
      UserDto us = userDao.lireUserPk(demande.getEtudiant());
      if (!Util.checkString(us.getCodeBic())) {
        ((DalServices) dalb).rollBackTransaction();
        throw new BizException("l'utilisateur n'a pas de code bic ou de compte bancaire associé");
      }
      int nbDemandeDejaIntroduitedemandesDejaIntroduites =
          demandePaiementDao.lireDemandePaiementMobilite(demande.getMoblite()).size();
      if (nbDemandeDejaIntroduitedemandesDejaIntroduites >= 2) {
        ((DalServices) dalb).rollBackTransaction();
        throw new BizException("il existe déjà deux demande pour cette mobilite");
      }
      if (nbDemandeDejaIntroduitedemandesDejaIntroduites <= 0) {
        mobilite.setEtat(EtatMobilite.AENVOYERDEM);
      } else {
        mobilite.setEtat(EtatMobilite.ENATTENTE);
      }
      mobiliteDao.modifierMobilite(mobilite);
      demandePaiementDao.ecrireDemandePaiement(demande);
      ((DalServices) dalb).commitTransaction();
    } catch (FatalException err) {
      ((DalServices) dalb).rollBackTransaction();
      throw err;
    } finally {
      ((DalServices) dalb).closeConnection();
    }
  }


  @Override
  public DemandePaiementDto lireDemandePaiementPk(DemandePaiementDto demandePaiement) {
    DemandePaiementDto paiement;
    try {
      ((DalServices) dalb).openConnection();

      paiement = dtoFact.getDemandePaiementDto();
      paiement = demandePaiementDao.lireDemandePaiementPk(demandePaiement);
      UtilUcc.remplirUser(paiement.getEtudiant(), userDao);
      UtilUcc.remplirUser(paiement.getProfesseur(), userDao);
      UtilUcc.remplirMobilite(paiement.getMoblite(), mobiliteDao);
    } finally {
      ((DalServices) dalb).closeConnection();
    }
    return paiement;
  }

  @Override
  public ArrayList<DemandePaiementDto> lireDemandePaiementMobilite(MobiliteDto mobilite) {
    ArrayList<DemandePaiementDto> alldemandePaiement;
    try {
      ((DalServices) dalb).openConnection();
      alldemandePaiement = demandePaiementDao.lireDemandePaiementMobilite(mobilite);
    } finally {
      ((DalServices) dalb).closeConnection();
    }
    return alldemandePaiement;
  }

  @Override
  public ArrayList<DemandePaiementDto> lireAllDemandePaiement() {
    ArrayList<DemandePaiementDto> alldemandePaiement;
    try {
      ((DalServices) dalb).openConnection();
      alldemandePaiement = demandePaiementDao.lireAllDemandePaiement();
    } finally {
      ((DalServices) dalb).closeConnection();
    }
    return alldemandePaiement;
  }

}
