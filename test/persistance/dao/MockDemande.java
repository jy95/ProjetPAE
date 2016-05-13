package persistance.dao;

import biz.biz.DemandeBiz;
import biz.biz.UserBiz;
import biz.dto.DemandeDto;
import biz.dto.UserDto;
import biz.enumerate.Programme;
import biz.enumerate.TypeStage;
import biz.factory.DtoFactory;
import exception.FatalException;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class MockDemande implements DemandeDao {

	private DtoFactory dtoFact;

	public MockDemande(DtoFactory dtoFact) {
		this.dtoFact = dtoFact;
	}

	@Override
	public void ecrireDemande(DemandeDto demande) {
		if (demande.getId() == 2)
			throw new FatalException();
	}

	@Override
	public void validerDemande(DemandeDto demande) {
	  
	  if(demande.getId() == 2)throw new FatalException();
	}

	@Override
	public void modifierPreferenceDemande(DemandeDto demande) {

	}

	@Override
	public ArrayList<DemandeDto> lireToutesDemandes() {
		ArrayList<DemandeDto> arr = new ArrayList<DemandeDto>();
		DemandeDto demandeDto = dtoFact.getDemandeDto();
		arr.add(demandeDto);
		return arr;
	}

	@Override
	public DemandeDto lireDemande(DemandeDto demande) {
		if (demande == null)
			demande = dtoFact.getDemandeDto();
		if (demande.getId() == 4)
			throw new FatalException();
		if (demande.getId() == 3) {
			DemandeDto demandeDto = dtoFact.getDemandeDto();
			((DemandeBiz) demandeDto).setPkDemande(1);
			demandeDto.setTypeStage(TypeStage.SMP);
			demandeDto.setQuadri(1);
			UserDto userDto = dtoFact.getUserDto();
			((UserBiz) userDto).setPkUser(1);
			demandeDto.setEtudiant(userDto);
			demandeDto.setDateIntroduction(LocalDateTime.of(2013, 12, 13, 12, 47));
			demandeDto.setProgramme(Programme.ERABEL);
			demandeDto.setPreference(1);
			((DemandeBiz) demandeDto).setNumVersion(2);
			return demandeDto;
		}
		return demande;
	}

	@Override
	public ArrayList<DemandeDto> rechercherDemandes(UserDto user) {
		ArrayList<DemandeDto> arr = new ArrayList<DemandeDto>();
		DemandeDto demandeDto = dtoFact.getDemandeDto();
		arr.add(demandeDto);
		return arr;
	}

}
