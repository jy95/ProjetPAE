package biz.ucc;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import biz.biz.DepartementBiz;
import biz.biz.PartenaireBiz;
import biz.biz.UserBiz;
import biz.dto.DepartementDto;
import biz.dto.PartenaireDto;
import biz.dto.PaysDto;
import biz.dto.UserDto;
import biz.enumerate.TypeEntreprise;
import biz.factory.DtoFactory;
import biz.factory.DtoFactoryImpl;
import exception.BizException;
import exception.FatalException;
import persistance.dal.DalBackendServices;
import persistance.dal.MockDalServices;
import persistance.dao.DepartementDao;
import persistance.dao.DocDepartDao;
import persistance.dao.MockDepartement;
import persistance.dao.MockPartennaire;
import persistance.dao.MockPays;
import persistance.dao.MockUser;
import persistance.dao.PartenaireDao;
import persistance.dao.PaysDao;
import persistance.dao.UserDao;

public class PartenaireTest {
	DtoFactory dtoFact;
	PartenaireDao partenaireDao;
	PartenaireUcc partenaireUcc;
	PaysDao paysDao;
	DocDepartDao docDepartDao;
	UserDao userDao;
	PartenaireDto partenaireDto;
	PaysDto paysDto;
	UserDto userDto;
	public static boolean fatal = false;

	{
		dtoFact = new DtoFactoryImpl();
		DepartementDao departementDao = new MockDepartement();
		DalBackendServices dalb = new MockDalServices();
		partenaireDao = new MockPartennaire(dtoFact);
		paysDao = new MockPays(dtoFact);
		userDao = new MockUser(dtoFact);
		
		partenaireUcc = new PartenaireUccImpl(dalb, partenaireDao, paysDao, departementDao, userDao);
	}

	@Before
	public void setUp() {
		partenaireDto = dtoFact.getPartenaireDto();
		paysDto = dtoFact.getPaysDto();
		fatal = false;
		userDto = dtoFact.getUserDto();
		((UserBiz)userDto).setPkUser(1);
		
		partenaireDto.setNomAffaire("nom");
		partenaireDto.setNomLegal("nom");
		partenaireDto.setNomComplet("nom");
		partenaireDto.setTypeEntreprise(TypeEntreprise.ETI);
		partenaireDto.setNbrEmploye(1);
		partenaireDto.setPays(paysDto);
		partenaireDto.setAdresse("adresse");
		partenaireDto.setCodePostal("1234");
		partenaireDto.setVille("ville");
		partenaireDto.setRegion("region");
		partenaireDto.setSiteWeb("site");
		partenaireDto.setTelephone("tel");
		partenaireDto.setCreateur(userDto);
		((PartenaireBiz)partenaireDto).setNumVersion(1);
		partenaireDto.setSupprime(false);
	}

	@Test
	public void creerPartenaire1() {		//ok
		partenaireUcc.creePartenaire(partenaireDto);
	}
	@Test(expected = BizException.class)
	public void creerPartenaire2() {		//pk ko
		((PartenaireBiz)partenaireDto).setPkPartenaire(-1);
		partenaireUcc.creePartenaire(partenaireDto);
	}
	@Test(expected = BizException.class)
	public void creerPartenaire3() {		//affaire ko
		partenaireDto.setNomAffaire(null);
		partenaireUcc.creePartenaire(partenaireDto);
	}
	@Test(expected = BizException.class)
	public void creerPartenaire4() {		//complet ko
		partenaireDto.setNomComplet(null);
		partenaireUcc.creePartenaire(partenaireDto);
	}
	@Test(expected = BizException.class)
	public void creerPartenaire5() {		//legal ko
		partenaireDto.setNomLegal(null);
		partenaireUcc.creePartenaire(partenaireDto);
	}
	@Test(expected = BizException.class)
	public void creerPartenaire6() {		//typeentre ko
		partenaireDto.setTypeEntreprise(null);
		partenaireUcc.creePartenaire(partenaireDto);
	}
	@Test(expected = BizException.class)
	public void creerPartenaire7() {		//nbre ko
		partenaireDto.setNbrEmploye(-1);
		partenaireUcc.creePartenaire(partenaireDto);
	}
	@Test(expected = BizException.class)
	public void creerPartenaire8() {		//pays ko
		partenaireDto.setPays(null);
		partenaireUcc.creePartenaire(partenaireDto);
	}
	@Test(expected = BizException.class)
	public void creerPartenaire9() {		//adress ko
		partenaireDto.setAdresse(null);
		partenaireUcc.creePartenaire(partenaireDto);
	}
	@Test(expected = BizException.class)
	public void creerPartenaire10() {		//postal ko
		partenaireDto.setCodePostal(null);
		partenaireUcc.creePartenaire(partenaireDto);
	}
	@Test(expected = BizException.class)
	public void creerPartenaire11() {		//ville ko
		partenaireDto.setVille(null);
		partenaireUcc.creePartenaire(partenaireDto);
	}
	@Test(expected = BizException.class)
	public void creerPartenaire12() {		//region ko
		partenaireDto.setRegion(null);
		partenaireUcc.creePartenaire(partenaireDto);
	}
	@Test(expected = BizException.class)
	public void creerPartenaire13() {		//web ko
		partenaireDto.setSiteWeb(null);
		partenaireUcc.creePartenaire(partenaireDto);
	}
	@Test(expected = BizException.class)
	public void creerPartenaire14() {		//tel ko
		partenaireDto.setTelephone(null);
		partenaireUcc.creePartenaire(partenaireDto);
	}
	@Test(expected = BizException.class)
	public void creerPartenaire15() {		//createur ko
		partenaireDto.setCreateur(null);
		partenaireUcc.creePartenaire(partenaireDto);
	}
	@Test(expected = BizException.class)
	public void creerPartenaire16() {		//createur pk ko
		((UserBiz)userDto).setPkUser(-1);
		partenaireDto.setCreateur(userDto);
		partenaireUcc.creePartenaire(partenaireDto);
	}
	@Test(expected = BizException.class)
	public void creerPartenaire17() {		//numversion ko
		((PartenaireBiz)partenaireDto).setNumVersion(-1);
		partenaireUcc.creePartenaire(partenaireDto);
	}

	@Test(expected = FatalException.class)
	public void creerPartenaire18() {			//fatal
		((PartenaireBiz) partenaireDto).setPkPartenaire(2);
		partenaireUcc.creePartenaire(partenaireDto);
	}

	@Test
	public void rechercherPartenairePk1() {
		partenaireUcc.rechercherPartenairePk(partenaireDto);
	}

	@Test(expected = FatalException.class)
	public void getAllPays1() {
		fatal = true;
		partenaireUcc.getAllPays();
	}

	@Test
	public void getAllPays2() {
		partenaireUcc.getAllPays();
	}

	@Test
	public void rechercherPartenaire1() {
		partenaireUcc.rechercherPartenaire("brenda");
	}

	@Test(expected = FatalException.class)
	public void rechercherPartenaire2() {
		partenaireUcc.rechercherPartenaire("cedric");
	}

	@Test
	public void rechercherPartenaireAgreeParDepartement1() {
		DepartementDto departement = dtoFact.getDepartementDto();
		partenaireUcc.rechercherPartenaireAgreeParDepartement(departement);
	}

	@Test(expected = FatalException.class)
	public void rechercherPartenaireAgreeParDepartement2() {
		DepartementDto departement = dtoFact.getDepartementDto();
		((DepartementBiz) departement).setPkDepartement("brenda");
		partenaireUcc.rechercherPartenaireAgreeParDepartement(departement);
	}

	@Test
	public void getAllDepartement1() {
		partenaireUcc.getAllDepartement();
	}

	@Test(expected = FatalException.class)
	public void getAllDepartement2() {
		fatal = true;
		partenaireUcc.getAllDepartement();
	}

	@Test
	public void remplirAllPart(){
		ArrayList<PartenaireDto> partenaires = new ArrayList<PartenaireDto>();
		PartenaireDto partenaire = dtoFact.getPartenaireDto();
		UserDto createur = dtoFact.getUserDto();
		PaysDto pays = dtoFact.getPaysDto();
		partenaire.setPays(pays);
		partenaire.setCreateur(createur);
		partenaires.add(partenaire);
		
		((PartenaireUccImpl)partenaireUcc).remplirAllPart(partenaires);
	}

	@Test
	public void supprimerPartenaire1(){		//ok

		partenaireDto.setSupprime(true);
		partenaireUcc.supprimerPartenaire(partenaireDto);
	}
	@Test(expected = FatalException.class)
	public void supprimerPartenaire2(){		//Fatal
		partenaireDto.setSupprime(true);
		((PartenaireBiz)partenaireDto).setPkPartenaire(2);
		partenaireUcc.supprimerPartenaire(partenaireDto);
	}
	@Test(expected = BizException.class)
	public void supprimerPartenaire3(){		//deja supprime
		partenaireUcc.supprimerPartenaire(partenaireDto);
	}
	@Test(expected = BizException.class)
	public void supprimerPartenaire4(){		//ko numVersion
		partenaireDto.setSupprime(true);
		((PartenaireBiz)partenaireDto).setNumVersion(2);
		partenaireUcc.supprimerPartenaire(partenaireDto);
	}
	
	@Test
	public void listerPartenairessupprimes(){
		partenaireUcc.listerPartenairessupprimes();
		
	}
	
	@Test
	public void verifierPartenaireExistant(){
		partenaireUcc.verifierPartenaireExistant(partenaireDto);
	}
}
