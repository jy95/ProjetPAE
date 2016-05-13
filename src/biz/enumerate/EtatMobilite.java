package biz.enumerate;

import biz.util.Util;

public enum EtatMobilite {
  CREEE("créée"), APAYER("à payer"), ENPREPA("en préparation"), AENVOYERDEM(
      "à envoyer demande"), APAYERSOLDE("à payer solde"), ENATTENTE("en attente 2ème paiement");

  String etat;

  private EtatMobilite(String etat) {

    this.etat = etat;

  }

  /**
   * Renvoie l'enum qui correspond à la string en paramètre.
   * 
   * @param etat String
   * @return EtatMobilite l'enum correspondant
   */
  public static EtatMobilite getValEtat(String etat) {

    switch (etat) {
      case "créée":
        return CREEE;
      case "à payer":
        return APAYER;

      case "en préparation":
        return ENPREPA;
      case "à envoyer demande":
        return AENVOYERDEM;
      case "à payer solde":
        return APAYERSOLDE;
      case "en attente 2ème paiement":
        return ENATTENTE;
      default:
        break;
    }
    return null;

  }

  /**
   * Renvoit l'enum qui correspond à la string en paramètre.
   * 
   * @param string chaine EtatMobilite stockée en DB
   * @return EtatMobilite enum
   */
  public static EtatMobilite stringToEtatMobilite(String string) {
    if (!Util.checkString(string)) {
      return null;
    }
    return EtatMobilite.valueOf(string);
  }

  /**
   * Renvoit le string qui correspond à l'enum en paramètre.
   * 
   * @param etatMobilite EtatMobilite enum
   * @return String
   */
  public static String etatMobiliteToString(EtatMobilite etatMobilite) {
    if (!Util.checkObject(etatMobilite)) {
      return null;
    }
    return etatMobilite.name();
  }

  public String getEtat() {
    return etat;
  }

}
