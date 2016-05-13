package biz.ucc;

import biz.biz.DocDepartBiz;
import biz.biz.MobiliteBiz;
import biz.biz.UserBiz;
import biz.dto.DocDepartDto;
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
import persistance.dao.DocDepartDao;
import persistance.dao.MobiliteDao;
import persistance.dao.MockDepartement;
import persistance.dao.MockDocDepart;
import persistance.dao.MockMobilite;
import persistance.dao.MockPays;
import persistance.dao.MockUser;
import persistance.dao.PaysDao;
import persistance.dao.UserDao;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

public class DocDepartTest {
  DtoFactory dtoFact;
  DocDepartUcc docDepartUcc;
  DocDepartDto docDepartDto;
  {
    dtoFact = new DtoFactoryImpl();
    DalBackendServices dalb = new MockDalServices();
    DocDepartDao docDepartDao = new MockDocDepart(dtoFact);
    MobiliteDao mobiliteDao = new MockMobilite(dtoFact);
    UserDao userDao = new MockUser(dtoFact);
    DepartementDao departementDao = new MockDepartement();
    PaysDao paysDao = new MockPays(dtoFact);
    docDepartUcc = new DocDepartUccImpl(dalb, docDepartDao, userDao, mobiliteDao, departementDao, paysDao);
  }

  @Before
  public void setUp() {
    docDepartDto = dtoFact.getDocDepartDto();
  }

  @Test(expected = BizException.class)
  public void testEcrire1() { // ko dto vide
    docDepartUcc.ecrireDocDepart(docDepartDto);
  }

  @Test(expected = BizException.class)
  public void testEcrire2() { // ko Etudiantdto null
    MobiliteDto mobilite = dtoFact.getMobiliteDto();
    ((MobiliteBiz) mobilite).setPkMobilite(2000);
    docDepartDto.setMobilite(mobilite);
    docDepartUcc.ecrireDocDepart(docDepartDto);
  }

  @Test(expected = BizException.class)
  public void testEcrire3() { // ko Etudiantdto pk a 0
    MobiliteDto mobilite = dtoFact.getMobiliteDto();
    ((MobiliteBiz) mobilite).setPkMobilite(2000);
    docDepartDto.setMobilite(mobilite);
    UserDto user = dtoFact.getUserDto();
    ((UserBiz) user).setPkUser(0);
    docDepartDto.setEtudiant(user);
    docDepartUcc.ecrireDocDepart(docDepartDto);
  }

  @Test(expected = BizException.class)
  public void testEcrire4() { // ko MobiliteDto null
    UserDto user = dtoFact.getUserDto();
    ((UserBiz) user).setPkUser(2000);
    docDepartDto.setEtudiant(user);
    docDepartUcc.ecrireDocDepart(docDepartDto);
  }

  @Test(expected = BizException.class)
  public void testEcrire5() { // ko MobiliteDto pk négative
    UserDto user = dtoFact.getUserDto();
    ((UserBiz) user).setPkUser(2000);
    docDepartDto.setEtudiant(user);
    MobiliteDto mobilite = dtoFact.getMobiliteDto();
    ((MobiliteBiz) mobilite).setPkMobilite(-1);
    docDepartDto.setMobilite(mobilite);
    docDepartUcc.ecrireDocDepart(docDepartDto);
  }

  @Test
  public void testEcrire6() { // OK
    UserDto user = dtoFact.getUserDto();
    ((UserBiz) user).setPkUser(2000);
    docDepartDto.setEtudiant(user);
    MobiliteDto mobilite = dtoFact.getMobiliteDto();
    ((MobiliteBiz) mobilite).setPkMobilite(2000);
    docDepartDto.setMobilite(mobilite);
    docDepartDto.setDateDepart(LocalDateTime.now());
    docDepartUcc.ecrireDocDepart(docDepartDto);
  }

  @Test
  public void testEcrire8() { // OK sans Date
    UserDto user = dtoFact.getUserDto();
    ((UserBiz) user).setPkUser(2000);
    docDepartDto.setEtudiant(user);
    MobiliteDto mobilite = dtoFact.getMobiliteDto();
    ((MobiliteBiz) mobilite).setPkMobilite(2000);
    docDepartDto.setMobilite(mobilite);
    docDepartUcc.ecrireDocDepart(docDepartDto);
  }

  @Test(expected = BizException.class)
  public void testEcrire9() { // Pk non-positive
    UserDto user = dtoFact.getUserDto();
    ((UserBiz) user).setPkUser(2000);
    docDepartDto.setEtudiant(user);
    MobiliteDto mobilite = dtoFact.getMobiliteDto();
    ((MobiliteBiz) mobilite).setPkMobilite(2000);
    docDepartDto.setMobilite(mobilite);
    ((DocDepartBiz) docDepartDto).setPkDocDepart(-1);
    docDepartUcc.ecrireDocDepart(docDepartDto);
  }

  @Test(expected = FatalException.class)
  public void testEcrire10() { // ko fatal
    UserDto user = dtoFact.getUserDto();
    ((UserBiz) user).setPkUser(2000);
    docDepartDto.setEtudiant(user);
    MobiliteDto mobilite = dtoFact.getMobiliteDto();
    ((MobiliteBiz) mobilite).setPkMobilite(2000);
    docDepartDto.setMobilite(mobilite);
    ((DocDepartBiz) docDepartDto).setPkDocDepart(2);
    docDepartUcc.ecrireDocDepart(docDepartDto);
  }

  @Test(expected = FatalException.class)
  public void testLireParMobi1() { // KO fatal
    MobiliteDto mobilite = dtoFact.getMobiliteDto();
    ((MobiliteBiz) mobilite).setPkMobilite(3);
    docDepartUcc.lireDocDepartMobilite(mobilite);
  }

  @Test(expected = BizException.class)
  public void testLireParMobi2() { // KO biz
    MobiliteDto mobilite = dtoFact.getMobiliteDto();
    ((MobiliteBiz) mobilite).setPkMobilite(2);
    docDepartUcc.lireDocDepartMobilite(mobilite);
  }

  @Test
  public void testLireParMobi3() { // OK biz
    MobiliteDto mobilite = dtoFact.getMobiliteDto();
    ((MobiliteBiz) mobilite).setPkMobilite(6);
    docDepartUcc.lireDocDepartMobilite(mobilite);
  }

  @Test(expected = FatalException.class)
  public void testLireParPk1() { // KO fatal
    ((DocDepartBiz) docDepartDto).setPkDocDepart(3);
    docDepartUcc.lireDocDepartPk(docDepartDto);
  }

  @Test(expected = BizException.class)
  public void testLireParPk2() { // KO biz
    ((DocDepartBiz) docDepartDto).setPkDocDepart(2);
    docDepartUcc.lireDocDepartPk(docDepartDto);
  }

  @Test
  public void testLireParPk3() { // OK biz
    ((DocDepartBiz) docDepartDto).setPkDocDepart(1);
    docDepartUcc.lireDocDepartPk(docDepartDto);
  }

  @Test
  public void modifierDocDepart1(){
    UserDto user = dtoFact.getUserDto();
    ((UserBiz) user).setPkUser(2000);
    docDepartDto.setEtudiant(user);
    MobiliteDto mobilite = dtoFact.getMobiliteDto();
    ((MobiliteBiz) mobilite).setPkMobilite(2000);
    docDepartDto.setMobilite(mobilite);
    docDepartDto.setDateDepart(LocalDateTime.now());
    
    docDepartUcc.modifierDocDepart(docDepartDto);
  }
  @Test(expected = BizException.class)
  public void modifierDocDepart2(){
    UserDto user = dtoFact.getUserDto();
    ((UserBiz) user).setPkUser(2000);
    docDepartDto.setEtudiant(user);
    MobiliteDto mobilite = dtoFact.getMobiliteDto();
    ((MobiliteBiz) mobilite).setPkMobilite(2000);
    docDepartDto.setMobilite(mobilite);
    docDepartDto.setDateDepart(LocalDateTime.now());
    ((DocDepartBiz)docDepartDto).setNumVersion(2);
    
    docDepartUcc.modifierDocDepart(docDepartDto);
  }
  @Test
  public void modifierDocDepart3(){
    UserDto user = dtoFact.getUserDto();
    ((UserBiz) user).setPkUser(2000);
    docDepartDto.setEtudiant(user);
    MobiliteDto mobilite = dtoFact.getMobiliteDto();
    ((MobiliteBiz) mobilite).setPkMobilite(2000);
    mobilite.setEtat(EtatMobilite.CREEE);
    docDepartDto.setMobilite(mobilite);
    docDepartDto.setDateDepart(LocalDateTime.now());

    docDepartDto.setCharteEtudiant(true);
    docDepartDto.setContratBourse(true);
    docDepartDto.setConventionStageOuEtude(true);
    docDepartDto.setDocEngagement(true);
    docDepartDto.setPreuveTestLangue(true);
    
    docDepartUcc.modifierDocDepart(docDepartDto);
  }
  @Test
  public void modifierDocDepart4(){
    UserDto user = dtoFact.getUserDto();
    ((UserBiz) user).setPkUser(2000);
    docDepartDto.setEtudiant(user);
    MobiliteDto mobilite = dtoFact.getMobiliteDto();
    ((MobiliteBiz) mobilite).setPkMobilite(2000);
    mobilite.setEtat(EtatMobilite.CREEE);
    docDepartDto.setMobilite(mobilite);
    docDepartDto.setDateDepart(LocalDateTime.now());

    docDepartDto.setPreuveTestLangue(true);
    
    docDepartUcc.modifierDocDepart(docDepartDto);
  }
  @Test(expected = FatalException.class)
  public void modifierDocDepart5(){
    UserDto user = dtoFact.getUserDto();
    ((UserBiz) user).setPkUser(2000);
    docDepartDto.setEtudiant(user);
    MobiliteDto mobilite = dtoFact.getMobiliteDto();
    ((MobiliteBiz) mobilite).setPkMobilite(2000);
    mobilite.setEtat(EtatMobilite.CREEE);
    docDepartDto.setMobilite(mobilite);
    docDepartDto.setDateDepart(LocalDateTime.now());

    ((DocDepartBiz)docDepartDto).setPkDocDepart(4);
    
    docDepartUcc.modifierDocDepart(docDepartDto);
  }
  
  @Test
  public void lireDocDepartUser(){
    UserDto user = dtoFact.getUserDto();
    ((UserBiz) user).setPkUser(2000);
    docDepartUcc.lireDocDepartUser(user);
  }
  
  @Test
  public void listerTousDocDepart(){
    docDepartUcc.listerTousDocDepart();
  }
}
