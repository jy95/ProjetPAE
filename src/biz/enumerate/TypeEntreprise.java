package biz.enumerate;

import biz.util.Util;

import java.util.HashMap;

public enum TypeEntreprise {

  TPE("Très petite entreprise"), PME("Petite/Moyenne entreprise"), ETI(
      "Entreprise taille intermédiaire"), TGE("Très grande entreprise");

  String typeEntreprise;

  private TypeEntreprise(String typeEntreprise) {
    this.typeEntreprise = typeEntreprise;
  }

  /**
   * Renvoit l'enum qui correspond à la string en paramètre.
   * 
   * @param string chaine typeEntreprise stockée en DB
   * @return l'enum correspondant
   */
  public static TypeEntreprise stringToTypeEntreprise(String string) {
    if (!Util.checkString(string)) {
      return null;
    }
    return TypeEntreprise.valueOf(string);
  }

  /**
   * Renvoit le string qui correspond à l'enum en paramètre.
   * 
   * @param typeEntreprise typeEntreprise
   * @return String la valeur de l'instance enum passé en param
   */

  public static String typeEntrepriseToString(TypeEntreprise typeEntreprise) {
    if (!Util.checkObject(typeEntreprise)) {
      return null;
    }
    return typeEntreprise.name();
  }

  public String valeur() {
    return typeEntreprise;
  }

  /**
   * Renvoit une HashMap contenant les différentes typeEntreprise.
   * 
   * @return la HashMap demandée
   */
  public static HashMap<String, String> getAll() {
    TypeEntreprise[] lesTypes = TypeEntreprise.values();
    HashMap<String, String> resultat = new HashMap<>(lesTypes.length);
    for (TypeEntreprise c : lesTypes) {
      resultat.put(c.name(), c.valeur());
    }
    return resultat;

  }

}
