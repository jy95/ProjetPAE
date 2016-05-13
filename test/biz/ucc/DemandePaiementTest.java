package biz.ucc;


import biz.biz.DemandePaiementBiz;
import biz.biz.MobiliteBiz;
import biz.biz.UserBiz;
import biz.dto.DemandePaiementDto;
import biz.dto.MobiliteDto;
import biz.dto.UserDto;
import biz.factory.DtoFactory;
import biz.factory.DtoFactoryImpl;
import exception.BizException;
import exception.FatalException;
import persistance.dal.DalBackendServices;
import persistance.dal.MockDalServices;
import persistance.dao.DemandePaiementDao;
import persistance.dao.MobiliteDao;
import persistance.dao.MockDemandePaiement;
import persistance.dao.MockMobilite;
import persistance.dao.MockUser;
import persistance.dao.UserDao;

import org.junit.*;

import java.time.LocalDateTime;

public class DemandePaiementTest {

	DtoFactory dtoFact;
	DemandePaiementDto demDto;
	DemandePaiementDto demDtoRempli;
	DemandePaiementUcc demandePaiementUcc;
	MobiliteDto m;
	{
		dtoFact = new DtoFactoryImpl();
		DalBackendServices dalb = new MockDalServices();
		DemandePaiementDao demandePaimentDao = new MockDemandePaiement(dtoFact);
		UserDao userDao = new MockUser(dtoFact);
		MobiliteDao mobiliteDao = new MockMobilite(dtoFact);

		demandePaiementUcc = new DemandePaiementUccImpl(dalb, dtoFact, demandePaimentDao, mobiliteDao,
				userDao);
	}

	@Before
	public void setUp() {
		demDto = dtoFact.getDemandePaiementDto();
		((DemandePaiementBiz) demDto).setPkDemandePaiement(1);
		demDtoRempli = dtoFact.getDemandePaiementDto();
		((DemandePaiementBiz) demDtoRempli).setPkDemandePaiement(1);
		demDtoRempli.setDateExecution(LocalDateTime.of(2013, 12, 13, 12, 47));
		UserDto u = dtoFact.getUserDto();
		((UserBiz) u).setPkUser(1);
		u.setCodeBic("brenda");
		demDtoRempli.setEtudiant(u);
		demDtoRempli.setProfesseur(u);
		m = dtoFact.getMobiliteDto();
		demDtoRempli.setMobilite(m);
	}

	@Test
	public void ecrireDemandePaiement1() {
		demandePaiementUcc.ecrireDemandePaiement(demDtoRempli);
	}

	@Test(expected = FatalException.class)
	public void ecrireDemandePaiement2() {
		((DemandePaiementBiz) demDtoRempli).setPkDemandePaiement(2);
		demandePaiementUcc.ecrireDemandePaiement(demDtoRempli);
	}

	@Test(expected = BizException.class)
	public void ecrireDemandePaiement3() {
		UserDto u = dtoFact.getUserDto();
		((UserBiz) u).setPkUser(1);
		u.setCodeBic(null);
		demDtoRempli.setEtudiant(u);
		demandePaiementUcc.ecrireDemandePaiement(demDtoRempli);
	}

	@Test(expected = BizException.class)
	public void ecrireDemandePaiement4() {
		((MobiliteBiz)m).setPkMobilite(5);
		demDtoRempli.setMobilite(m);
		demandePaiementUcc.ecrireDemandePaiement(demDtoRempli);
	}

	@Test
	public void ecrireDemandePaiement5() {
		((MobiliteBiz)m).setPkMobilite(6);
		demDtoRempli.setMobilite(m);
		demandePaiementUcc.ecrireDemandePaiement(demDtoRempli);
	}

	@Test
	public void lireDemandePaiement1() {
		demDto = demandePaiementUcc.lireDemandePaiementPk(demDto);
	}

	@Test(expected = BizException.class)
	public void lireDemandePaiement2() {
		((DemandePaiementBiz) demDto).setPkDemandePaiement(2);
		demDto = demandePaiementUcc.lireDemandePaiementPk(demDto);
	}
	
	@Test
	public void lireAllDemandePaiement(){
	  demandePaiementUcc.lireAllDemandePaiement();
	}
	
	@Test
	public void lireDemandePaiementMobilite(){
		demandePaiementUcc.lireDemandePaiementMobilite(m);
		
	}
}
