package biz.ucc;

import java.util.ArrayList;

import biz.dto.AnnulationDto;
import biz.dto.DemandeDto;
import biz.dto.MobiliteDto;
import biz.dto.UserDto;
import biz.enumerate.EtatMobilite;

public interface MobiliteUcc {
  public ArrayList<MobiliteDto> rechercherMobilite(EtatMobilite etat, String anneeAcademique);

  /**
   * Ecrire la mobiliter envoyé en paramètre en db.
   * 
   * @param mobiliteDto MobiliteDto
   * 
   */
  public void ecrireMobilite(MobiliteDto mobiliteDto);

  /**
   * Recherche une moblite via la pk de la dto en paramètre.
   * 
   * @param mobiliteDto MobiliteDto
   * @return {@code MobiliteDto } une mobilité
   */
  public MobiliteDto rechercherMobilitePk(MobiliteDto mobiliteDto);

  /**
   * Modifie une moblite via la pk de la dto en paramètre.
   * 
   * @param mobiliteDto MobiliteDto
   */
  public void modifierMobilite(MobiliteDto mobiliteDto);

  /**
   * Modifie l'état d'une moblite via la pk de la dto en paramètre.
   * 
   * @param mobiliteDto MobiliteDto
   * @param etat EtatMobilite
   */
  public void changerEtatMobilite(MobiliteDto mobiliteDto, EtatMobilite etat);

  /**
   * Annule la mobilité en paramètre, set son etat à annuler, l'update. Si la raison d'annulation
   * passer en paramètre n'est pas générique, la méthode va l'inserer dans la db.
   * 
   * @param mobiliteDto MobiliteDto
   * @param annulationDto AnnulationDto
   */
  public void annulerMobilite(MobiliteDto mobiliteDto, AnnulationDto annulationDto);

  /**
   * Donne la Liste des mobilité de l'étudiant passé en paramètre.
   * 
   * @param userDto UserDto
   * @return {@code ArrayList<MobiliteDto> } les mobilités
   */
  public ArrayList<MobiliteDto> rechercherMobilitesStud(UserDto userDto);

  /**
   * Donne la Liste de tout les mobilités existante.
   * 
   * @return {@code ArrayList<MobiliteDto> } les mobilités
   */
  public ArrayList<MobiliteDto> listerToutesMobilite();


  /**
   * Donne la Liste de tout les mobilités annulees.
   * 
   * @return {@code ArrayList<MobiliteDto> } les mobilités
   */
  public ArrayList<MobiliteDto> listerToutesMobiliteAnnulees();

  void ecrireMobiliteParDemande(DemandeDto demandeDto);

  ArrayList<MobiliteDto> listerTouteMobiliteAvecDemandePaiement();
}
