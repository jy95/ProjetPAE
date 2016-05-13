package persistance.dao;

import biz.biz.AnnulationBiz;
import biz.biz.DemandeBiz;
import biz.biz.MobiliteBiz;
import biz.biz.PartenaireBiz;
import biz.biz.UserBiz;
import biz.dto.AnnulationDto;
import biz.dto.DemandeDto;
import biz.dto.MobiliteDto;
import biz.dto.PartenaireDto;
import biz.dto.UserDto;
import biz.enumerate.EtatMobilite;
import biz.enumerate.TypeStage;
import biz.factory.DtoFactory;
import exception.FatalException;

import java.util.ArrayList;

public class MockMobilite implements MobiliteDao {
	DtoFactory dtoFact;

	public MockMobilite(DtoFactory dtoFact) {
		this.dtoFact = dtoFact;
	}

	@Override
	public MobiliteDto lireMobilitePk(MobiliteDto mobiliteDto) {
	  if (mobiliteDto == null)
        mobiliteDto = dtoFact.getMobiliteDto();
      if(mobiliteDto.getId() == 4)throw new FatalException();
		UserDto u = dtoFact.getUserDto();
		((UserBiz) u).setPkUser(1);
		mobiliteDto.setEtudiant(u);
		DemandeDto d = dtoFact.getDemandeDto();
		((DemandeBiz) d).setPkDemande(1);
		mobiliteDto.setDemande(d);
		PartenaireDto p = dtoFact.getPartenaireDto();
		((PartenaireBiz) p).setPkPartenaire(1);
		mobiliteDto.setPartenaire(p);
		mobiliteDto.setPays(dtoFact.getPaysDto());
		if (mobiliteDto.getId() == 3) {
			MobiliteDto mobi = dtoFact.getMobiliteDto();
			((MobiliteBiz) mobi).setNumVersion(1);
			mobi.setEtudiant(u);
			mobi.setDemande(d);
			mobi.setPartenaire(p);
			mobi.setAnneeAcademique("2015-2016");
			mobi.setVille("ville");
			mobi.setStage(TypeStage.SMP);
			mobi.setEtat(EtatMobilite.APAYER);
			mobi.setPays(dtoFact.getPaysDto());
			return mobi;
		}
		  return mobiliteDto;
    }
	
  @Override
  public int ecrireMobilite(MobiliteDto mobiliteDto) {
	  if(mobiliteDto.getId() == 2)throw new FatalException();
	  return 0;
  }

	

	

	@Override
	public MobiliteDto modifierMobilite(MobiliteDto mobiliteDto) {
		if (mobiliteDto.getId() == 2)
			throw new FatalException();
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<MobiliteDto> rechercherMobilite(EtatMobilite etat, String anneeAcademique) {
		if(anneeAcademique.equals("brenda"))throw new FatalException();
		return new ArrayList<>();
	}

	@Override
	public ArrayList<MobiliteDto> rechercherMobiliteStud(UserDto user) {
		MobiliteDto mobiliteDto = dtoFact.getMobiliteDto();
		AnnulationDto annu = dtoFact.getAnnulationDto();
		((AnnulationBiz)annu).setPkAnnulation(1);
		if(user.getId() == 3)mobiliteDto.setAnnulation(annu);
		ArrayList<MobiliteDto> arr = new ArrayList<MobiliteDto>();
		arr.add(mobiliteDto);
		return arr;
	}

	@Override
	public ArrayList<MobiliteDto> lireToutMobilite() {
		// TODO Auto-generated method stub
		return new ArrayList<MobiliteDto>();
	}

	@Override
	public ArrayList<MobiliteDto> lireMobilitesAnnulees() {
		return new ArrayList<MobiliteDto>();
	}

}
