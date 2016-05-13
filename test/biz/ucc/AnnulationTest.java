package biz.ucc;

import static org.junit.Assert.assertEquals;

import biz.biz.AnnulationBiz;
import biz.dto.AnnulationDto;
import biz.factory.DtoFactory;
import biz.factory.DtoFactoryImpl;
import exception.BizException;
import exception.FatalException;
import persistance.dal.DalBackendServices;
import persistance.dal.MockDalServices;
import persistance.dao.AnnulationDao;
import persistance.dao.MockAnnulation;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class AnnulationTest {
  DtoFactory dtoFact;
  AnnulationDto annulationDto;
  AnnulationDto annulationDtoRempli;
  AnnulationUcc annulationUcc;
  AnnulationDao annulationDao;

  {
    dtoFact = new DtoFactoryImpl();
    DalBackendServices dalb = new MockDalServices();
    annulationDao = new MockAnnulation();
    annulationUcc = new AnnulationUccImpl(annulationDao, dalb);
  }

  @Before
  public void setUp() {
    annulationDao = new MockAnnulation();
    annulationDto = dtoFact.getAnnulationDto();
    ((AnnulationBiz) annulationDto).setPkAnnulation(1);
    annulationDtoRempli = dtoFact.getAnnulationDto();
    ((AnnulationBiz) annulationDtoRempli).setPkAnnulation(2);
    annulationDtoRempli.setDescription("super description");
    annulationDtoRempli.setGenerique(true);
  }

  @Test
  public void testLireAnnulation1() {
    annulationDto = annulationUcc.lireAnnulationPk(annulationDto);
  }

  @Test(expected = BizException.class)
  public void testCreeAnnulation1() {
    annulationUcc.creerAnnulation(annulationDto);
  }

  @Test
  public void testCreeAnnulation2() {
    annulationUcc.creerAnnulation(annulationDtoRempli);
  }

  @Test(expected = FatalException.class)
  public void testCreeAnnulation3() {
    ((AnnulationBiz) annulationDtoRempli).setPkAnnulation(3);
    annulationUcc.creerAnnulation(annulationDtoRempli);
  }



  @Test
  public void testLireAnnulation2() {
    annulationUcc.creerAnnulation(annulationDtoRempli);
    annulationDto = annulationUcc.lireAnnulationPk(annulationDtoRempli);
  }

  @Test(expected = BizException.class)
  public void testListerAnnulationGenerique1() {
    annulationUcc.listerAnnulationsGenerique();
  }

  @Test(expected = BizException.class)
  public void testListerAnnulationGenerique2() {
    annulationDtoRempli.setGenerique(false);
    annulationUcc.creerAnnulation(annulationDtoRempli);
    annulationUcc.listerAnnulationsGenerique();
  }

  @Test
  public void testListerAnnulationGenerique3() {
    annulationUcc.creerAnnulation(annulationDtoRempli);
    ArrayList<AnnulationDto> actual = annulationUcc.listerAnnulationsGenerique();
    assertEquals(1, actual.size());
  }

  @Test
  public void testListerAnnulationGenerique4() {
    annulationDto.setDescription("Kek");
    annulationDto.setGenerique(true);
    annulationUcc.creerAnnulation(annulationDto);
    annulationUcc.creerAnnulation(annulationDtoRempli);
    ArrayList<AnnulationDto> actual = annulationUcc.listerAnnulationsGenerique();
    assertEquals(2, actual.size());
  }

  public void testListerAnnulationGenerique5() {
    annulationDto.setDescription("Kek");
    annulationDto.setGenerique(false);
    annulationUcc.creerAnnulation(annulationDto);
    annulationUcc.creerAnnulation(annulationDtoRempli);
    ArrayList<AnnulationDto> actual = annulationUcc.listerAnnulationsGenerique();
    assertEquals(1, actual.size());
  }

}
