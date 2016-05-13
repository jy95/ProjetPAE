package biz.ucc;

import biz.biz.DocRetourBiz;
import biz.biz.MobiliteBiz;
import biz.biz.UserBiz;
import biz.dto.DocRetourDto;
import biz.dto.MobiliteDto;
import biz.dto.UserDto;
import biz.enumerate.EtatMobilite;
import biz.factory.DtoFactory;
import biz.factory.DtoFactoryImpl;
import exception.BizException;
import exception.FatalException;
import persistance.dal.DalBackendServices;
import persistance.dal.MockDalServices;
import persistance.dao.DepartementDao;
import persistance.dao.DocRetourDao;
import persistance.dao.MobiliteDao;
import persistance.dao.MockDepartement;
import persistance.dao.MockDocRetour;
import persistance.dao.MockMobilite;
import persistance.dao.MockPays;
import persistance.dao.MockUser;
import persistance.dao.PaysDao;
import persistance.dao.UserDao;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

public class DocRetourTest {
  DtoFactory dtoFact;
  DocRetourUcc docRetourUcc;
  DocRetourDto docRetourDto;

  {
      dtoFact = new DtoFactoryImpl();
      DalBackendServices dalb = new MockDalServices();
      DocRetourDao docRetourDao = new MockDocRetour(dtoFact);
      MobiliteDao mobiliteDao = new MockMobilite(dtoFact);
      UserDao userDao = new MockUser(dtoFact);
      DepartementDao departementDao = new MockDepartement();
      PaysDao paysDao = new MockPays(dtoFact);
      docRetourUcc  = new DocRetourUccImpl(dalb, docRetourDao, userDao, mobiliteDao, departementDao, paysDao);

    }

  @Before
    public void setUp() {
      docRetourDto = dtoFact.getDocRetourDto();
    }

  @Test(expected = BizException.class)
  public void testEcrire1() { // ko dto vide
    docRetourUcc.ecrireDocRetour(docRetourDto);
  }

  @Test(expected = BizException.class)
  public void testEcrire2() { // ko Etudiantdto null
    MobiliteDto mobilite = dtoFact.getMobiliteDto();
    ((MobiliteBiz) mobilite).setPkMobilite(2000);
    docRetourDto.setMobilite(mobilite);
    docRetourUcc.ecrireDocRetour(docRetourDto);
  }

  @Test(expected = BizException.class)
  public void testEcrire3() { // ko Etudiantdto pk a 0
    MobiliteDto mobilite = dtoFact.getMobiliteDto();
    ((MobiliteBiz) mobilite).setPkMobilite(2000);
    docRetourDto.setMobilite(mobilite);
    UserDto user = dtoFact.getUserDto();
    ((UserBiz) user).setPkUser(0);
    docRetourDto.setEtudiant(user);
    docRetourUcc.ecrireDocRetour(docRetourDto);
  }

  @Test(expected = BizException.class)
  public void testEcrire4() { // ko MobiliteDto null
    UserDto user = dtoFact.getUserDto();
    ((UserBiz) user).setPkUser(2000);
    docRetourDto.setEtudiant(user);
    docRetourUcc.ecrireDocRetour(docRetourDto);
  }

  @Test(expected = BizException.class)
  public void testEcrire5() { // ko MobiliteDto pk négative
    UserDto user = dtoFact.getUserDto();
    ((UserBiz) user).setPkUser(2000);
    docRetourDto.setEtudiant(user);
    MobiliteDto mobilite = dtoFact.getMobiliteDto();
    ((MobiliteBiz) mobilite).setPkMobilite(-1);
    docRetourDto.setMobilite(mobilite);
    docRetourUcc.ecrireDocRetour(docRetourDto);
  }

  @Test
  public void testEcrire6() { // OK
    UserDto user = dtoFact.getUserDto();
    ((UserBiz) user).setPkUser(2000);
    docRetourDto.setEtudiant(user);
    MobiliteDto mobilite = dtoFact.getMobiliteDto();
    ((MobiliteBiz) mobilite).setPkMobilite(2000);
    docRetourDto.setMobilite(mobilite);
    docRetourDto.setDateRetour(LocalDateTime.now());
    docRetourUcc.ecrireDocRetour(docRetourDto);
  }

  @Test
  public void testEcrire8() { // OK sans Date
    UserDto user = dtoFact.getUserDto();
    ((UserBiz) user).setPkUser(2000);
    docRetourDto.setEtudiant(user);
    MobiliteDto mobilite = dtoFact.getMobiliteDto();
    ((MobiliteBiz) mobilite).setPkMobilite(2000);
    docRetourDto.setMobilite(mobilite);
    docRetourUcc.ecrireDocRetour(docRetourDto);
  }

  @Test(expected = BizException.class)
  public void testEcrire9() { // Pk non-positive
    UserDto user = dtoFact.getUserDto();
    ((UserBiz) user).setPkUser(2000);
    docRetourDto.setEtudiant(user);
    MobiliteDto mobilite = dtoFact.getMobiliteDto();
    ((MobiliteBiz) mobilite).setPkMobilite(2000);
    docRetourDto.setMobilite(mobilite);
    ((DocRetourBiz) docRetourDto).setPkDocRetour(-1);
    docRetourUcc.ecrireDocRetour(docRetourDto);
  }

  @Test(expected = FatalException.class)
  public void testEcrire10() { // ko fatal
    UserDto user = dtoFact.getUserDto();
    ((UserBiz) user).setPkUser(2000);
    docRetourDto.setEtudiant(user);
    MobiliteDto mobilite = dtoFact.getMobiliteDto();
    ((MobiliteBiz) mobilite).setPkMobilite(2000);
    docRetourDto.setMobilite(mobilite);
    ((DocRetourBiz) docRetourDto).setPkDocRetour(2);
    docRetourUcc.ecrireDocRetour(docRetourDto);
  }

  @Test(expected = FatalException.class)
  public void testLireParMobi1() { // KO fatal
    MobiliteDto mobilite = dtoFact.getMobiliteDto();
    ((MobiliteBiz) mobilite).setPkMobilite(3);
    docRetourUcc.lireDocRetourMobilite(mobilite);
  }

  @Test(expected = BizException.class)
  public void testLireParMobi2() { // KO biz
    MobiliteDto mobilite = dtoFact.getMobiliteDto();
    ((MobiliteBiz) mobilite).setPkMobilite(2);
    docRetourUcc.lireDocRetourMobilite(mobilite);
  }

  @Test
  public void testLireParMobi3() { // OK biz
    MobiliteDto mobilite = dtoFact.getMobiliteDto();
    ((MobiliteBiz) mobilite).setPkMobilite(6);
    docRetourUcc.lireDocRetourMobilite(mobilite);
  }

  @Test(expected = FatalException.class)
  public void testLireParPk1() { // KO fatal
    ((DocRetourBiz) docRetourDto).setPkDocRetour(3);
    docRetourUcc.lireDocRetourPk(docRetourDto);
  }

  @Test(expected = BizException.class)
  public void testLireParPk2() { // KO biz
    ((DocRetourBiz) docRetourDto).setPkDocRetour(2);
    docRetourUcc.lireDocRetourPk(docRetourDto);
  }

  @Test
  public void testLireParPk3() { // OK biz
    ((DocRetourBiz) docRetourDto).setPkDocRetour(1);
    docRetourUcc.lireDocRetourPk(docRetourDto);
  }

  @Test
  public void modifierDocRetour1() {
    UserDto user = dtoFact.getUserDto();
    ((UserBiz) user).setPkUser(2000);
    docRetourDto.setEtudiant(user);
    MobiliteDto mobilite = dtoFact.getMobiliteDto();
    ((MobiliteBiz) mobilite).setPkMobilite(2000);
    docRetourDto.setMobilite(mobilite);
    docRetourDto.setDateRetour(LocalDateTime.now());

    docRetourUcc.modifierDocRetour(docRetourDto);
  }

  @Test(expected = BizException.class)
  public void modifierDocRetour2() {
    UserDto user = dtoFact.getUserDto();
    ((UserBiz) user).setPkUser(2000);
    docRetourDto.setEtudiant(user);
    MobiliteDto mobilite = dtoFact.getMobiliteDto();
    ((MobiliteBiz) mobilite).setPkMobilite(2000);
    docRetourDto.setMobilite(mobilite);
    docRetourDto.setDateRetour(LocalDateTime.now());
    ((DocRetourBiz) docRetourDto).setNumVersion(2);

    docRetourUcc.modifierDocRetour(docRetourDto);
  }

  @Test
  public void modifierDocRetour3() {
    UserDto user = dtoFact.getUserDto();
    ((UserBiz) user).setPkUser(2000);
    docRetourDto.setEtudiant(user);
    MobiliteDto mobilite = dtoFact.getMobiliteDto();
    ((MobiliteBiz) mobilite).setPkMobilite(2000);
    mobilite.setEtat(EtatMobilite.AENVOYERDEM);
    docRetourDto.setMobilite(mobilite);
    docRetourDto.setDateRetour(LocalDateTime.now());
    docRetourDto.setAttestationSejour(true);
    docRetourDto.setRapportFinal(true);
    docRetourDto.setPreuvePassageTest(true);
    docRetourDto.setReleveNoteOuCertifStage(true);
    docRetourUcc.modifierDocRetour(docRetourDto);
  }

  @Test
  public void modifierDocRetour4() {
    UserDto user = dtoFact.getUserDto();
    ((UserBiz) user).setPkUser(2000);
    docRetourDto.setEtudiant(user);
    MobiliteDto mobilite = dtoFact.getMobiliteDto();
    ((MobiliteBiz) mobilite).setPkMobilite(2000);
    mobilite.setEtat(EtatMobilite.CREEE);
    docRetourDto.setMobilite(mobilite);
    docRetourDto.setDateRetour(LocalDateTime.now());

    docRetourDto.setPreuvePassageTest(true);

    docRetourUcc.modifierDocRetour(docRetourDto);
  }

  @Test(expected = FatalException.class)
  public void modifierDocRetour5() {
    UserDto user = dtoFact.getUserDto();
    ((UserBiz) user).setPkUser(2000);
    docRetourDto.setEtudiant(user);
    MobiliteDto mobilite = dtoFact.getMobiliteDto();
    ((MobiliteBiz) mobilite).setPkMobilite(2000);
    mobilite.setEtat(EtatMobilite.CREEE);
    docRetourDto.setMobilite(mobilite);
    docRetourDto.setDateRetour(LocalDateTime.now());

    ((DocRetourBiz) docRetourDto).setPkDocRetour(4);

    docRetourUcc.modifierDocRetour(docRetourDto);
  }

  @Test
  public void lireDocRetourUser() {
    UserDto user = dtoFact.getUserDto();
    ((UserBiz) user).setPkUser(2000);
    docRetourUcc.lireDocRetourUser(user);
  }

  @Test
  public void listerTousDocRetour() {
    docRetourUcc.listerTousDocRetour();
  }



}
