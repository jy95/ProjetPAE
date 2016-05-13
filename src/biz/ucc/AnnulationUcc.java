package biz.ucc;

import biz.dto.AnnulationDto;

import java.util.ArrayList;

public interface AnnulationUcc {
  public void creerAnnulation(AnnulationDto annulationDto);

  public AnnulationDto lireAnnulationPk(AnnulationDto annulationDto);

  public ArrayList<AnnulationDto> listerAnnulationsGenerique();
}
