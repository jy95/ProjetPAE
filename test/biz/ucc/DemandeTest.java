package biz.ucc;


import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import biz.biz.DemandeBiz;
import biz.biz.PartenaireBiz;
import biz.biz.UserBiz;
import biz.dto.DemandeDto;
import biz.dto.PartenaireDto;
import biz.dto.UserDto;
import biz.enumerate.Programme;
import biz.enumerate.TypeStage;
import biz.factory.DtoFactory;
import biz.factory.DtoFactoryImpl;
import exception.BizException;
import exception.FatalException;
import persistance.dal.DalBackendServices;
import persistance.dal.MockDalServices;
import persistance.dao.DemandeDao;
import persistance.dao.DepartementDao;
import persistance.dao.MobiliteDao;
import persistance.dao.MockDemande;
import persistance.dao.MockDepartement;
import persistance.dao.MockMobilite;
import persistance.dao.MockPartennaire;
import persistance.dao.MockPays;
import persistance.dao.MockUser;
import persistance.dao.PartenaireDao;
import persistance.dao.PaysDao;
import persistance.dao.UserDao;

public class DemandeTest {

	DtoFactory dtoFact;
	DalBackendServices dalb;
	DemandeDao demandeDao;
	UserDao userDao;
	MobiliteDao mobiliteDao;
	PartenaireDao partenaireDao;
	PaysDao paysDao;
	DepartementDao departementDao;
	DemandeUcc demandeUcc;

	DemandeDto demandeDto;
	UserDto userDto;

	{
		dtoFact = new DtoFactoryImpl();
		dalb = new MockDalServices();
		demandeDao = new MockDemande(dtoFact);
		userDao = new MockUser(dtoFact);
		mobiliteDao = new MockMobilite(dtoFact);
		partenaireDao = new MockPartennaire(dtoFact);
		paysDao = new MockPays(dtoFact);
		departementDao = new MockDepartement();
		demandeUcc = new DemandeUccImpl(dalb, demandeDao, userDao, partenaireDao, paysDao, departementDao);
	}

	@Before
	public void setUp() {
		demandeDto = dtoFact.getDemandeDto();
		((DemandeBiz) demandeDto).setPkDemande(1);
		demandeDto.setTypeStage(TypeStage.SMP);
		demandeDto.setQuadri(1);
		userDto = dtoFact.getUserDto();
		((UserBiz) userDto).setPkUser(1);
		demandeDto.setEtudiant(userDto);
		demandeDto.setDateIntroduction(LocalDateTime.of(2013, 12, 13, 12, 47));
		demandeDto.setProgramme(Programme.ERABEL);
		demandeDto.setPreference(1);
	}

	@Test
	public void creerDemande1() { // ok
		demandeUcc.creerDemande(demandeDto);
	}

	@Test(expected = BizException.class)
	public void creerDemande2() { // pk ko
		((DemandeBiz) demandeDto).setPkDemande(-1);
		demandeUcc.creerDemande(demandeDto);
	}

	@Test(expected = BizException.class)
	public void creerDemande3() { // type ko
		demandeDto.setTypeStage(null);
		demandeUcc.creerDemande(demandeDto);
	}

	@Test(expected = BizException.class)
	public void creerDemande4() { // quadri ko
		demandeDto.setQuadri(-1);
		demandeUcc.creerDemande(demandeDto);
	}

	@Test(expected = BizException.class)
	public void creerDemande5() { // etudiant ko
		demandeDto.setEtudiant(null);
		demandeUcc.creerDemande(demandeDto);
	}

	@Test(expected = BizException.class)
	public void creerDemande6() { // etudiant pk ko
		((UserBiz) userDto).setPkUser(-1);
		demandeDto.setEtudiant(userDto);
		demandeUcc.creerDemande(demandeDto);
	}

	@Test(expected = BizException.class)
	public void creerDemande7() { // programme ko
		demandeDto.setProgramme(null);
		demandeUcc.creerDemande(demandeDto);
	}

	@Test(expected = BizException.class)
	public void creerDemande8() { // preference ko
		demandeDto.setPreference(-1);
		demandeUcc.creerDemande(demandeDto);
	}

	@Test(expected = FatalException.class)
	public void creerDemande9() { // Fatal
		((DemandeBiz) demandeDto).setPkDemande(2);
		demandeUcc.creerDemande(demandeDto);
	}

	@Test
	public void validerDemande1() {
		demandeUcc.validerDemande(demandeDto);
	}

	@Test(expected = BizException.class)
	public void validerDemande2() { // numVersion ko
		((DemandeBiz) demandeDto).setPkDemande(3);
		demandeUcc.validerDemande(demandeDto);
	}

	@Test(expected = FatalException.class)
	public void validerDemande3() { // fatal ko
		((DemandeBiz) demandeDto).setPkDemande(4);
		demandeUcc.validerDemande(demandeDto);
	}

	@Test
	public void changerPreferenceDemande1() { // ok
		demandeUcc.changerPreferenceDemande(demandeDto, 1);
	}

	@Test(expected = BizException.class)
	public void changerPreferenceDemande2() { // numversion ko
		((DemandeBiz) demandeDto).setPkDemande(3);
		demandeUcc.changerPreferenceDemande(demandeDto, 1);
	}

	@Test(expected = FatalException.class)
	public void changerPreferenceDemande3() { // fatal ko
		((DemandeBiz) demandeDto).setPkDemande(4);
		demandeUcc.changerPreferenceDemande(demandeDto, 1);
	}

	@Test
	public void listerToutesDemandes() {
		demandeUcc.listerToutesDemandes();
	}

	@Test
	public void rechercherDemandes() {
		demandeUcc.rechercherDemandes(userDto);
	}

	@Test
	public void remplirAllDtoDemande1() {
		ArrayList<DemandeDto> arr = new ArrayList<DemandeDto>();
		DemandeDto demande = dtoFact.getDemandeDto();
		PartenaireDto part = dtoFact.getPartenaireDto();
		((PartenaireBiz) part).setPkPartenaire(1);
		demande.setPartenaire(part);
		arr.add(demande);
		((DemandeUccImpl) demandeUcc).remplirAllDtoDemande(arr);
	}

	@Test
	public void remplirAllDtoDemande2() {
		ArrayList<DemandeDto> arr = new ArrayList<DemandeDto>();
		DemandeDto demande = dtoFact.getDemandeDto();
		PartenaireDto part = dtoFact.getPartenaireDto();
		((PartenaireBiz) part).setPkPartenaire(1);
		demande.setPartenaire(part);
		demande.setPays(dtoFact.getPaysDto());
		arr.add(demande);
		((DemandeUccImpl) demandeUcc).remplirAllDtoDemande(arr);
	}

	@Test
	public void infoDemande() {
		demandeUcc.infoDemande(demandeDto);
	}
}
