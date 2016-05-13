package persistance.dao;

import biz.dto.DemandeDto;
import biz.dto.UserDto;

import java.util.ArrayList;

public interface DemandeDao {
  public void ecrireDemande(DemandeDto demande);

  /**
   * valide une demande.
   * 
   * @param demande DemandeDto
   */
  public void validerDemande(DemandeDto demande);

  /**
   * modifie la preference d'une demande.
   * 
   * @param demande DemandeDto
   */
  public void modifierPreferenceDemande(DemandeDto demande);

  /**
   * liste de toutes les demandes.
   * 
   * @return {@code ArrayList<DemandeDto> } les Demandes
   */
  public ArrayList<DemandeDto> lireToutesDemandes();

  /**
   * liste de toutes les demandes d'un utilisateur.
   * 
   * @return {@code ArrayList<DemandeDto> } les Demandes
   */
  public ArrayList<DemandeDto> rechercherDemandes(UserDto user);

  /**
   * Renvoie la demandeDto via la pk dans demande.
   * 
   * @return DemandeDto
   */
  public DemandeDto lireDemande(DemandeDto demande);

}
