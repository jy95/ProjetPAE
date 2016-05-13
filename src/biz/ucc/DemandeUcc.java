package biz.ucc;

import java.util.ArrayList;

import biz.dto.DemandeDto;
import biz.dto.UserDto;

public interface DemandeUcc {

  public void creerDemande(DemandeDto demande);

  public void validerDemande(DemandeDto demande);

  public void changerPreferenceDemande(DemandeDto demande, int preference);

  /**
   * liste de toutes les demandes.
   * 
   * @return {@code ArrayList<DemandeDto> } les Demandes
   */
  public ArrayList<DemandeDto> listerToutesDemandes();

  /**
   * liste de toutes les demandes d'un utilisateur.
   * 
   * @return {@code ArrayList<DemandeDto> } les Demandes
   */
  public ArrayList<DemandeDto> rechercherDemandes(UserDto user);

  /**
   * Renvoit les infos d'une demande.
   * 
   * @param demande la demande concernée.
   * @return {@code DemandeDto } la demande
   */
  public DemandeDto infoDemande(DemandeDto demande);


}
