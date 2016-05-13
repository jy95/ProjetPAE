package biz.ucc;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import biz.biz.AnnulationBiz;
import biz.biz.DemandeBiz;
import biz.biz.MobiliteBiz;
import biz.biz.PartenaireBiz;
import biz.biz.UserBiz;
import biz.dto.AnnulationDto;
import biz.dto.DemandeDto;
import biz.dto.MobiliteDto;
import biz.dto.PartenaireDto;
import biz.dto.PaysDto;
import biz.dto.UserDto;
import biz.enumerate.EtatMobilite;
import biz.enumerate.TypeStage;
import biz.factory.DtoFactory;
import biz.factory.DtoFactoryImpl;
import exception.BizException;
import exception.FatalException;
import persistance.dal.DalBackendServices;
import persistance.dal.MockDalServices;
import persistance.dao.AnnulationDao;
import persistance.dao.DemandeDao;
import persistance.dao.DemandePaiementDao;
import persistance.dao.DepartementDao;
import persistance.dao.DocDepartDao;
import persistance.dao.DocRetourDao;
import persistance.dao.MobiliteDao;
import persistance.dao.MockAnnulation;
import persistance.dao.MockDemande;
import persistance.dao.MockDemandePaiement;
import persistance.dao.MockDepartement;
import persistance.dao.MockDocDepart;
import persistance.dao.MockDocRetour;
import persistance.dao.MockMobilite;
import persistance.dao.MockPartennaire;
import persistance.dao.MockPays;
import persistance.dao.MockUser;
import persistance.dao.PartenaireDao;
import persistance.dao.PaysDao;
import persistance.dao.UserDao;

public class MobiliteTest {
	DtoFactory dtoFact;
	MobiliteDto uDto;
	MobiliteUcc mobiliteUcc;
	MobiliteDao mobiliteDao;
	UserDao userDao;
	UserDto userDto;
	DemandeDao demandeDao;
	PartenaireDao partenaireDao;
	AnnulationDao annulationDao;
	PaysDao paysDao;
	MobiliteDto mobiliteDto;
	DepartementDao departementDao;
	AnnulationDto annulationDto;
	DocDepartDao docDepartDao;
	DocRetourDao docRetourDao;
	DemandeDto d;
	PartenaireDto p;

	{
		dtoFact = new DtoFactoryImpl();
		departementDao = new MockDepartement();
		DalBackendServices dalb = new MockDalServices();
		mobiliteDao = new MockMobilite(dtoFact);
		userDao = new MockUser(dtoFact);
		demandeDao = new MockDemande(dtoFact);
		partenaireDao = new MockPartennaire(dtoFact);
		annulationDao = new MockAnnulation();
		paysDao = new MockPays(dtoFact);
		docRetourDao = new MockDocRetour(dtoFact);
		docDepartDao = new MockDocDepart(dtoFact);
		DemandePaiementDao demandePaiementDao = new MockDemandePaiement(dtoFact);
		mobiliteUcc = new MobiliteUccImpl(dalb, dtoFact, mobiliteDao, userDao, demandeDao, partenaireDao, annulationDao,
				paysDao, departementDao, docDepartDao, docRetourDao, demandePaiementDao);
	}

	@Before
	public void setUp() {
		mobiliteDto = dtoFact.getMobiliteDto();
		((MobiliteBiz) mobiliteDto).setPkMobilite(1);
		mobiliteDto.setAnneeAcademique("2015-2016");
		mobiliteDto.setQuadri(1);
		userDto = dtoFact.getUserDto();
		((UserBiz) userDto).setPkUser(1);
		mobiliteDto.setEtudiant(userDto);
		d = dtoFact.getDemandeDto();
		((DemandeBiz) d).setPkDemande(1);
		mobiliteDto.setDemande(d);
		mobiliteDto.setPays(dtoFact.getPaysDto());
		mobiliteDto.setVille("ville");
		p = dtoFact.getPartenaireDto();
		((PartenaireBiz) p).setPkPartenaire(1);
		mobiliteDto.setPartenaire(p);
		mobiliteDto.setStage(TypeStage.SMP);
		mobiliteDto.setEtat(EtatMobilite.APAYER);
		annulationDto = dtoFact.getAnnulationDto();
		((AnnulationBiz) annulationDto).setPkAnnulation(1);
		annulationDto.setDescription("brenda");
	}

	@Test
	public void testEcrire1() { // ok
		mobiliteUcc.ecrireMobilite(mobiliteDto);
	}

	@Test(expected = BizException.class)
	public void testEcrire2() { // pk ko
		((MobiliteBiz) mobiliteDto).setPkMobilite(-1);
		mobiliteUcc.ecrireMobilite(mobiliteDto);
	}

	@Test(expected = BizException.class)
	public void testEcrire3() { // annee ko
		mobiliteDto.setAnneeAcademique(null);
		mobiliteUcc.ecrireMobilite(mobiliteDto);
	}

	@Test(expected = BizException.class)
	public void testEcrire4() { // quadri ko
		mobiliteDto.setQuadri(-1);
		mobiliteUcc.ecrireMobilite(mobiliteDto);
	}

	@Test(expected = BizException.class)
	public void testEcrire5() { // etudiant ko
		mobiliteDto.setEtudiant(null);
		mobiliteUcc.ecrireMobilite(mobiliteDto);
	}

	@Test(expected = BizException.class)
	public void testEcrire6() { // etudiant pk ko
		UserDto user = mobiliteDto.getEtudiant();
		((UserBiz) user).setPkUser(-1);
		mobiliteDto.setEtudiant(user);
		mobiliteUcc.ecrireMobilite(mobiliteDto);
	}

	@Test(expected = BizException.class)
	public void testEcrire7() { // demande ko
		mobiliteDto.setDemande(null);
		mobiliteUcc.ecrireMobilite(mobiliteDto);
	}

	@Test(expected = BizException.class)
	public void testEcrire8() { // demandee pk ko
		DemandeDto demande = mobiliteDto.getDemande();
		((DemandeBiz) demande).setPkDemande(-1);
		mobiliteDto.setDemande(demande);
		mobiliteUcc.ecrireMobilite(mobiliteDto);
	}

	@Test(expected = BizException.class)
	public void testEcrire9() { // pays ko
		mobiliteDto.setPays(null);
		mobiliteUcc.ecrireMobilite(mobiliteDto);
	}

	@Test(expected = BizException.class)
	public void testEcrire10() { // ville ko
		mobiliteDto.setVille(null);
		mobiliteUcc.ecrireMobilite(mobiliteDto);
	}

	@Test(expected = BizException.class)
	public void testEcrire11() { // partenaire ko
		mobiliteDto.setPartenaire(null);
		mobiliteUcc.ecrireMobilite(mobiliteDto);
	}

	@Test(expected = BizException.class)
	public void testEcrire12() { // partenaire pk ko
		PartenaireDto partenaire = mobiliteDto.getPartenaire();
		((PartenaireBiz) partenaire).setPkPartenaire(-1);
		mobiliteUcc.ecrireMobilite(mobiliteDto);
	}

	@Test(expected = BizException.class)
	public void testEcrire13() { // stage ko
		mobiliteDto.setStage(null);
		mobiliteUcc.ecrireMobilite(mobiliteDto);
	}

	@Test(expected = FatalException.class)
	public void testEcrire14() { // stage ko
		((MobiliteBiz) mobiliteDto).setPkMobilite(2);
		mobiliteUcc.ecrireMobilite(mobiliteDto);
	}

	@Test(expected = BizException.class)
	public void testEcrire15() { // ville  ko
		mobiliteDto.setVille(null);
		mobiliteUcc.ecrireMobilite(mobiliteDto);
	}

	@Test
	public void testRechercher1() { // ok
		mobiliteUcc.rechercherMobilite(EtatMobilite.APAYER, "");
	}

	@Test(expected = FatalException.class)
	public void testRechercher2() { // ko
		mobiliteUcc.rechercherMobilite(EtatMobilite.APAYER, "brenda");
	}

	@Test
	public void testRechercherPk1() { // ok
		mobiliteUcc.rechercherMobilitePk(mobiliteDto);
	}

	@Test(expected = FatalException.class)
	public void testRechercherPk2() { // Fatal
		((MobiliteBiz) mobiliteDto).setPkMobilite(4);
		mobiliteUcc.rechercherMobilitePk(mobiliteDto);
	}

	@Test
	public void testRechercher() { // ok
		mobiliteUcc.rechercherMobilite(EtatMobilite.APAYER, "");
	}

	@Test
	public void testRechercherPk() { // ok
		mobiliteUcc.rechercherMobilitePk(mobiliteDto);
	}

	@Test
	public void testListerToutesMobilite() { // ok
		mobiliteUcc.listerToutesMobilite();
	}

	@Test
	public void testListerToutesMobiliteAnnulees() { // ok
		mobiliteUcc.listerToutesMobiliteAnnulees();
	}

	@Test
	public void modifierMobilite1() { // ok
		mobiliteUcc.modifierMobilite(mobiliteDto);
	}

	@Test(expected = BizException.class)
	public void modifierMobilite2() { // etat ko
		mobiliteDto.setEtat(null);
		mobiliteUcc.modifierMobilite(mobiliteDto);
	}

	@Test(expected = FatalException.class)
	public void modifierMobilite3() { // fatal
		((MobiliteBiz) mobiliteDto).setPkMobilite(2);
		mobiliteUcc.modifierMobilite(mobiliteDto);
	}

	@Test(expected = BizException.class)
	public void modifierMobilite4() { // numVersion ko
		((MobiliteBiz) mobiliteDto).setPkMobilite(3);
		mobiliteUcc.modifierMobilite(mobiliteDto);
	}

	@Test
	public void annulerMobilite1() { // ok
		mobiliteUcc.annulerMobilite(mobiliteDto, annulationDto);
	}

	@Test(expected = BizException.class)
	public void annulerMobilite2() { // annulation ko
		annulationDto = null;
		mobiliteUcc.annulerMobilite(mobiliteDto, annulationDto);
	}

	@Test(expected = BizException.class)
	public void annulerMobilite3() { // numVersion ko
		((MobiliteBiz) mobiliteDto).setNumVersion(2);
		((MobiliteBiz) mobiliteDto).setPkMobilite(3);
		mobiliteUcc.annulerMobilite(mobiliteDto, annulationDto);
	}

	@Test
	public void annulerMobilite4() { // demande pk 0
		((AnnulationBiz) annulationDto).setPkAnnulation(0);
		mobiliteUcc.annulerMobilite(mobiliteDto, annulationDto);
	}

	@Test(expected = BizException.class)
	public void annulerMobilite5() { // demande pk ko
		((MobiliteBiz) mobiliteDto).setPkMobilite(0);
		mobiliteUcc.annulerMobilite(mobiliteDto, annulationDto);
	}

	@Test(expected = FatalException.class)
	public void annulerMobilite6() { // fatal
		((MobiliteBiz) mobiliteDto).setPkMobilite(2);
		mobiliteUcc.annulerMobilite(mobiliteDto, annulationDto);
	}

	@Test
	public void remplirAllDto1() {
		mobiliteDto.setAnnulation(annulationDto);
		ArrayList<MobiliteDto> arr = new ArrayList<MobiliteDto>();
		arr.add(mobiliteDto);
		((MobiliteUccImpl) mobiliteUcc).remplirAllMobi(arr);
	}

	@Test
	public void changerEtatMobilite1() { // ok
		mobiliteUcc.changerEtatMobilite(mobiliteDto, EtatMobilite.APAYER);
	}

	@Test(expected = BizException.class)
	public void changerEtatMobilite2() { // ko
		mobiliteUcc.changerEtatMobilite(mobiliteDto, EtatMobilite.AENVOYERDEM);
	}

	@Test
	public void rechercherMobiliteStud1() { // ok
		mobiliteUcc.rechercherMobilitesStud(userDto);
	}

	@Test
	public void rechercherMobiliteStud2() { // ok
		((UserBiz) userDto).setPkUser(3);
		mobiliteUcc.rechercherMobilitesStud(userDto);
	}

	@Test
	public void ecrireMobiliteParDemande1() { // ok
		DemandeDto demandeDto = dtoFact.getDemandeDto();
		((DemandeBiz) demandeDto).setPkDemande(1);
		demandeDto.setAnneeAcademique("2015-2016");
		demandeDto.setQuadri(1);
		userDto = dtoFact.getUserDto();
		((UserBiz) userDto).setPkUser(1);
		demandeDto.setEtudiant(userDto);
		demandeDto.setPays(dtoFact.getPaysDto());
		p = dtoFact.getPartenaireDto();
		((PartenaireBiz) p).setPkPartenaire(1);
		PaysDto pays = dtoFact.getPaysDto();
		p.setPays(pays);
		p.setVille("ville");
		demandeDto.setVille("ville");
		p.setTypeStage(TypeStage.SMP);
		demandeDto.setTypeStage(TypeStage.SMP);
		demandeDto.setPartenaire(p);
		annulationDto = dtoFact.getAnnulationDto();
		mobiliteUcc.ecrireMobiliteParDemande(demandeDto);
	}

	@Test(expected = BizException.class)
	public void ecrireMobiliteParDemande2() { // partenaire ko
		DemandeDto demandeDto = dtoFact.getDemandeDto();
		((DemandeBiz) demandeDto).setPkDemande(1);
		demandeDto.setAnneeAcademique("2015-2016");
		demandeDto.setQuadri(1);
		userDto = dtoFact.getUserDto();
		((UserBiz) userDto).setPkUser(1);
		demandeDto.setEtudiant(userDto);
		d = dtoFact.getDemandeDto();

		annulationDto = dtoFact.getAnnulationDto();
		mobiliteUcc.ecrireMobiliteParDemande(demandeDto);
	}

	@Test(expected = FatalException.class)
	public void ecrireMobiliteParDemande3() { // fatal
		DemandeDto demandeDto = dtoFact.getDemandeDto();
		((DemandeBiz) demandeDto).setPkDemande(2);
		demandeDto.setAnneeAcademique("2015-2016");
		demandeDto.setQuadri(1);
		userDto = dtoFact.getUserDto();
		((UserBiz) userDto).setPkUser(1);
		demandeDto.setEtudiant(userDto);
		d = dtoFact.getDemandeDto();
		demandeDto.setPays(dtoFact.getPaysDto());
		p = dtoFact.getPartenaireDto();
		((PartenaireBiz) p).setPkPartenaire(1);
		PaysDto pays = dtoFact.getPaysDto();
		p.setPays(pays);
		p.setVille("ville");
		demandeDto.setVille("ville");
		demandeDto.setTypeStage(TypeStage.SMP);
		demandeDto.setPartenaire(p);
		annulationDto = dtoFact.getAnnulationDto();
		mobiliteUcc.ecrireMobiliteParDemande(demandeDto);
	}
	
	@Test
	public void listerTouteMobiliteAvecDemandePaiement(){
		mobiliteUcc.listerTouteMobiliteAvecDemandePaiement();
	}
}
