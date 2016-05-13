package biz.ucc;

import biz.biz.AnnulationBiz;
import biz.dto.AnnulationDto;
import exception.FatalException;
import persistance.dal.DalBackendServices;
import persistance.dal.DalServices;
import persistance.dao.AnnulationDao;

import java.util.ArrayList;

public class AnnulationUccImpl implements AnnulationUcc {
  private AnnulationDao annulationDao;
  private DalBackendServices dalb;

  /**
   * constructeur AnnulationUccImpl.
   * 
   * @param annulationDao annulationDao
   */
  public AnnulationUccImpl(AnnulationDao annulationDao,
      DalBackendServices dalb) {
    this.annulationDao = annulationDao;
    this.dalb = dalb;
  }

  @Override
  public AnnulationDto lireAnnulationPk(AnnulationDto annulationDto) {
    AnnulationDto ann;
    try {
      ((DalServices) dalb).openConnection();
      ann = annulationDao.lireAnnulationPk(annulationDto);
    } finally {
      ((DalServices) dalb).closeConnection();
    }
    return ann;
  }

  @Override
  public void creerAnnulation(AnnulationDto annulationDto) {


    ((AnnulationBiz) annulationDto).checkAnnulation();
    try {
      ((DalServices) dalb).openConnection();
      ((DalServices) dalb).startTransaction();

      annulationDao.ecrireAnnulation(annulationDto);
      ((DalServices) dalb).commitTransaction();
    } catch (FatalException err) {
      ((DalServices) dalb).rollBackTransaction();
      throw err;
    } finally {
      ((DalServices) dalb).closeConnection();
    }



  }

  @Override
  public ArrayList<AnnulationDto> listerAnnulationsGenerique() {
    ArrayList<AnnulationDto> listAnn;
    try {
      ((DalServices) dalb).openConnection();
      listAnn = annulationDao.lireToutAnnulationsGenerique();
    } finally {
      ((DalServices) dalb).closeConnection();
    }
    return listAnn;
  }

}
