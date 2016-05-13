package persistance.dao;

import biz.dto.AnnulationDto;
import exception.BizException;
import exception.FatalException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MockAnnulation implements AnnulationDao {

  private Set<AnnulationDto> donnees;

  public MockAnnulation() {
    donnees = new HashSet<AnnulationDto>();
  }


  @Override
  public int ecrireAnnulation(AnnulationDto annulationdto) {
    if (annulationdto.getId() == 3)
      throw new FatalException();
    if (donnees.contains(annulationdto)) {
      throw new FatalException("L'insert n'a pas pu être effectuée.");
    }
    donnees.add(annulationdto);
    return annulationdto.getId();
  }

  @Override
  public AnnulationDto lireAnnulationPk(AnnulationDto annulationDto) {
    annulationDto.setDescription("blablabla");
    annulationDto.setGenerique(true);
    return annulationDto;
  }

  @Override
  public ArrayList<AnnulationDto> lireToutAnnulationsGenerique() {
    ArrayList<AnnulationDto> x = new ArrayList<>();
    for (AnnulationDto annulationDto : donnees) {
      if (annulationDto.getGenerique()) {
        x.add(annulationDto);
      }
    }
    if (x.isEmpty()) {
      throw new BizException("Aucune Annulations génériques");
    }
    return x;
  }

}
