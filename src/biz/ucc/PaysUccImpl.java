package biz.ucc;

import biz.dto.PaysDto;
import biz.factory.DtoFactory;
import persistance.dal.DalBackendServices;
import persistance.dal.DalServices;
import persistance.dao.PaysDao;

public class PaysUccImpl implements PaysUcc {

  private PaysDao paysDao;
  private DtoFactory dtoFact;
  private DalBackendServices dalb;

  /**
   * Constructeur.
   * 
   * @param dtoFact dtoFactory
   * @param paysDao paysDao
   * @param dalb dalb
   */
  public PaysUccImpl(DtoFactory dtoFact, PaysDao paysDao, DalBackendServices dalb) {
    this.dtoFact = dtoFact;
    this.paysDao = paysDao;
    this.dalb = dalb;
  }

  @Override
  public PaysDto rechercherPays(PaysDto pays) {
    try {
      ((DalServices) dalb).openConnection();
      PaysDto paysDto = dtoFact.getPaysDto();
      paysDto = paysDao.rechercherPays(pays);
      return paysDto;
    } finally {
      ((DalServices) dalb).closeConnection();
    }
  }

}
