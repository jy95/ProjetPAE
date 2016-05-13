package biz.ucc;

import static org.junit.Assert.assertTrue;

import biz.dto.PaysDto;
import biz.factory.DtoFactory;
import biz.factory.DtoFactoryImpl;
import exception.BizException;
import persistance.dal.DalBackendServices;
import persistance.dal.MockDalServices;
import persistance.dao.MockPays;
import persistance.dao.PaysDao;

import org.junit.Before;
import org.junit.Test;

public class PaysTest {

  DtoFactory dtoFact;
  PaysDto paysDto;
  PaysDto paysDtoIncorrect;
  PaysDto demDtoRempli;
  PaysUcc paysUcc;


  {
    dtoFact = new DtoFactoryImpl();
    DalBackendServices dalb = new MockDalServices();
    PaysDao paysDao = new MockPays(dtoFact);
    paysUcc = new PaysUccImpl(dtoFact, paysDao, dalb);

  }

  @Before
  public void setUp() {
    paysDto = dtoFact.getPaysDto();
    paysDtoIncorrect = dtoFact.getPaysDto();
    paysDto.setNom("Afghanistan");
    paysDtoIncorrect.setNom("af");
    // paysDto.setTypeMoblite();
  }

  @Test
  public void testRechercher() {
    paysUcc.rechercherPays(paysDto);
  }

  @Test(expected = BizException.class)
  public void testRechercher1() {
    paysDtoIncorrect = dtoFact.getPaysDto();
    paysDtoIncorrect.setNom("af");
    paysUcc.rechercherPays(paysDtoIncorrect);
  }

  @Test
  public void testRechercher2() {

    PaysDto p = dtoFact.getPaysDto();
    p = paysUcc.rechercherPays(paysDto);
    assertTrue(p.getNom().equals(paysDto.getNom()));
  }


}
