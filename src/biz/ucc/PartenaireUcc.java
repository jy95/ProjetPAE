package biz.ucc;

import biz.dto.DepartementDto;
import biz.dto.PartenaireDto;
import biz.dto.PaysDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

public interface PartenaireUcc {

  public ArrayList<PartenaireDto> rechercherPartenaire(String champRecherche);

  public HashMap<String, String> getAllDepartement();

  public SortedMap<String, PaysDto> getAllPays();

  public PartenaireDto creePartenaire(PartenaireDto partDto);

  public PartenaireDto rechercherPartenairePk(PartenaireDto part);

  public HashMap<String, PartenaireDto> rechercherPartenaireAgreeParDepartement(
      DepartementDto departement);
  
  public boolean supprimerPartenaire(PartenaireDto partenaireDto);
  
  public ArrayList<PartenaireDto> listerPartenairessupprimes();

  public boolean verifierPartenaireExistant(PartenaireDto partenaireDto);
}
