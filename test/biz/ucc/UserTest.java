package biz.ucc;


import java.time.LocalDateTime;

import biz.biz.UserBiz;
import biz.dto.UserDto;
import biz.enumerate.TypeUser;
import biz.factory.DtoFactory;
import biz.factory.DtoFactoryImpl;
import exception.BizException;
import exception.FatalException;
import persistance.dal.DalBackendServices;
import persistance.dal.MockDalServices;
import persistance.dao.DepartementDao;
import persistance.dao.MockDepartement;
import persistance.dao.MockUser;
import persistance.dao.UserDao;

import org.junit.Before;
import org.junit.Test;

public class UserTest {

  DtoFactory dtoFact;
  UserDto uDto;
  UserUcc userUcc;
  UserDao userDao;

  {
    dtoFact = new DtoFactoryImpl();
    DepartementDao departementDao = new MockDepartement();
    DalBackendServices dalb = new MockDalServices();
    userDao = new MockUser(dtoFact);
    userUcc = new UserUccImpl(userDao, dtoFact, departementDao, dalb);
  }

  @Before
  public void setUp() {
    uDto = dtoFact.getUserDto();
    uDto.setPseudo("pseudo1");
    uDto.setMdp("brenda");
    ((UserBiz) uDto).setPkUser(1);
    uDto.setEmail("pocpoc@maxime.com");
    uDto.setNom("yakoub");
    uDto.setPrenom("jacques");
    uDto.setDepartement(dtoFact.getDepartementDto());
    ((UserBiz) uDto).setNumVersion(1);
    uDto.setDateInscription(LocalDateTime.now());
    this.userUcc.creerUtilisateur(uDto);
  }

  @Test
  public void testConnection1() { // ok
    uDto.setMdp("brenda");
    userUcc.seConnecter(uDto);
  }

  @Test(expected = BizException.class)
  public void testConnection2() { // mdp ko
    uDto.setMdp("cedric");
    userUcc.seConnecter(uDto);
  }


  @Test
  public void testInscription0() { // ok
    userUcc.creerUtilisateur(uDto);
  }

  @Test(expected = BizException.class)
  public void testInscription1() { // pk ko
    ((UserBiz) uDto).setPkUser(-1);
    userUcc.creerUtilisateur(uDto);
  }

  @Test(expected = BizException.class)
  public void testInscription2() { // pseudo ko
    uDto.setPseudo(null);
    userUcc.creerUtilisateur(uDto);
  }

  @Test(expected = BizException.class)
  public void testInscription3() { // mdp ko
    uDto.setMdp(null);
    userUcc.creerUtilisateur(uDto);
  }

  @Test(expected = BizException.class)
  public void testInscription4() { // nom ko
    uDto.setNom(null);
    userUcc.creerUtilisateur(uDto);
  }

  @Test(expected = BizException.class)
  public void testInscription5() { // prenom ko
    uDto.setPrenom(null);
    userUcc.creerUtilisateur(uDto);
  }

  @Test(expected = BizException.class)
  public void testInscription6() { // departement ko
    uDto.setDepartement(null);
    userUcc.creerUtilisateur(uDto);
  }

  @Test(expected = BizException.class)
  public void testInscription7() { // mail ko
    uDto.setEmail(null);
    userUcc.creerUtilisateur(uDto);
  }

  @Test(expected = FatalException.class)
  public void testInscription8() { // fatal
    ((UserBiz) uDto).setPkUser(2);
    userUcc.creerUtilisateur(uDto);
  }

  @Test(expected = BizException.class)
  public void testInscription9() { // no unique
    uDto.setPseudo("cedric");
    userUcc.creerUtilisateur(uDto);
  }

  @Test(expected = BizException.class)
  public void testModifier1() { // date ko
    uDto.setDateInscription(null);
    this.userUcc.modifierUser(uDto);
  }

  @Test(expected = BizException.class)
  public void testModifier2() { // numversion ko
    ((UserBiz) uDto).setNumVersion(2);
    this.userUcc.modifierUser(uDto);
  }

  @Test(expected = FatalException.class)
  public void testModifier3() { // fatal
    ((UserBiz) uDto).setPkUser(2);
    this.userUcc.modifierUser(uDto);
  }

  @Test
  public void testModifier4() { // ok
    this.userUcc.modifierUser(uDto);
  }

  @Test
  public void rechercherUser1() {
    userUcc.rechercherUser("brenda");
  }

  @Test
  public void rechercherUser2() {
    userUcc.rechercherUser("");
  }

  @Test
  public void rechercherUser3() {
    userUcc.rechercherUser(null);
  }

  @Test
  public void rechercherUserPseudo() {
    uDto.setPseudo("clara");
    userUcc.rechercherUserPseudo(uDto);
  }

  @Test
  public void changePermission1() {     //ok
    UserDto[] users = {uDto};
    userUcc.changePermission(users, TypeUser.ETUD);
  }
  @Test(expected = BizException.class)
  public void changePermission2() {     //numversion ko
    ((UserBiz)uDto).setNumVersion(2);
    UserDto[] users = {uDto};
    userUcc.changePermission(users, TypeUser.ETUD);
  }
  @Test(expected = FatalException.class)
  public void changePermission3() {     //fatal
	  ((UserBiz)uDto).setPkUser(2);
	  UserDto[] users = {uDto};
    userUcc.changePermission(users, TypeUser.PROF);
  }
}
