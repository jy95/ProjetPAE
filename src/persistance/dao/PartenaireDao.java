package persistance.dao;

import biz.dto.DepartementDto;
import biz.dto.PartenaireDto;

import java.util.ArrayList;
import java.util.HashMap;

public interface PartenaireDao {

  public PartenaireDto lirePartenairePk(PartenaireDto partenaireDto);

  public PartenaireDto ecrirePartenaire(PartenaireDto partenaireDto);

  public ArrayList<PartenaireDto> rechercherPartenaire(String champRecherche);

  public PartenaireDto lireAllDepartementsPartenaire(PartenaireDto partenaireDto);

  public HashMap<String, PartenaireDto> getPartenaireAgreeParDepartement(
      DepartementDto departement);

  public boolean mettreAjourPartenaire(PartenaireDto partenaireDto);
  
  public ArrayList<PartenaireDto> listerPartenairessupprimes();

  public boolean verifierPartenaireExistant(PartenaireDto partenaireDto);

}
