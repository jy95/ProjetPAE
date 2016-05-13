package persistance.dao;
import java.util.ArrayList;
import java.util.HashMap;

import biz.biz.PartenaireBiz;
import biz.biz.UserBiz;
import biz.dto.DepartementDto;
import biz.dto.PartenaireDto;
import biz.dto.PaysDto;
import biz.dto.UserDto;
import biz.enumerate.TypeEntreprise;
import biz.factory.DtoFactory;
import exception.FatalException;

public class MockPartennaire implements PartenaireDao{
  DtoFactory dtoFact; 
  
  public MockPartennaire(DtoFactory dtoFact) {
	  this.dtoFact = dtoFact;
  }
  
  @Override
  public PartenaireDto lirePartenairePk(PartenaireDto partenaireDto) {
    if(partenaireDto == null)partenaireDto = dtoFact.getPartenaireDto();
    if(partenaireDto.getSupprime()){

		PaysDto paysDto = dtoFact.getPaysDto();
		UserDto userDto = dtoFact.getUserDto();
		((UserBiz)userDto).setPkUser(1);
		PartenaireDto partenaireDto2 = dtoFact.getPartenaireDto();
		partenaireDto2.setNomAffaire("nom");
		partenaireDto2.setNomLegal("nom");
		partenaireDto2.setNomComplet("nom");
		partenaireDto2.setTypeEntreprise(TypeEntreprise.ETI);
		partenaireDto2.setNbrEmploye(1);
		partenaireDto2.setPays(paysDto);
		partenaireDto2.setAdresse("adresse");
		partenaireDto2.setCodePostal("1234");
		partenaireDto2.setVille("ville");
		partenaireDto2.setRegion("region");
		partenaireDto2.setSiteWeb("site");
		partenaireDto2.setTelephone("tel");
		partenaireDto2.setCreateur(userDto);
		((PartenaireBiz)partenaireDto2).setNumVersion(1);
		partenaireDto2.setSupprime(false);
	    return partenaireDto2;
    }
    return partenaireDto;
  }

  @Override
  public PartenaireDto ecrirePartenaire(PartenaireDto partenaireDto) {
    if(partenaireDto.getId() == 2)throw new FatalException();
    return null;
  }



  @Override
  public ArrayList<PartenaireDto> rechercherPartenaire(String champRecherche) {
	  if(champRecherche.equals("cedric"))throw new FatalException();
    ArrayList<PartenaireDto> arr = new ArrayList<PartenaireDto>();
    PartenaireDto partenaire = dtoFact.getPartenaireDto();
    arr.add(partenaire);
    return arr;
  }

  @Override
  public PartenaireDto lireAllDepartementsPartenaire(PartenaireDto partenaireDto) {
    return partenaireDto;
  }

  @Override
  public HashMap<String, PartenaireDto> getPartenaireAgreeParDepartement(
      DepartementDto departement) {
	  if(departement.getId() == "brenda")throw new FatalException();
	  return null;
  }

  @Override
  public boolean mettreAjourPartenaire(PartenaireDto partenaireDto) {
    if(partenaireDto.getId() == 2)throw new FatalException();
    return false;
  }

  @Override
  public ArrayList<PartenaireDto> listerPartenairessupprimes() {
    ArrayList<PartenaireDto> arr = new ArrayList<>();
    PartenaireDto p = dtoFact.getPartenaireDto();
    ((PartenaireBiz)p).setPkPartenaire(1);
    p.setPays(dtoFact.getPaysDto());
    arr.add(p);
    return arr;
  }

  @Override
  public boolean verifierPartenaireExistant(PartenaireDto partenaireDto) {
    // TODO Auto-generated method stub
    return false;
  }
 

}
