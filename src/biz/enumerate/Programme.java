package biz.enumerate;

import biz.util.Util;

import java.util.HashMap;

public enum Programme {
  ERASMUS("Erasmus+"), ERABEL("Erabel"), FAME("FAME");

  private String type;

  private Programme(String type) {
    this.type = type;
  }

  /**
   * Renvoit le programme correspondant à la String.
   * 
   * @param type chaine TypeMobilite stockée en DB
   * @return l'enum correspondant
   */
  public static Programme getValType(String type) {
    switch (type) {
      case "Erasmus":
      case "Erasmus+":
        return ERASMUS;
      case "Erabel":
        return ERABEL;
      case "FAME":
        return FAME;
      default:
        break;
    }
    return null;
  }

  /**
   * Renvoit l'enum qui correspond à la string en paramètre.
   * 
   * @param string chaine Programme stockée en DB
   * @return l'enum correspondant
   */

  public static Programme stringToProgramme(String string) {
    if (!Util.checkString(string)) {
      return null;
    }
    return Programme.valueOf(string);
  }

  /**
   * Renvoit le string qui correspond à l'enum en paramètre.
   * 
   * @param programme Programme
   * @return String nom
   */
  public static String programmeToString(Programme programme) {
    if (!Util.checkObject(programme)) {
      return null;
    }
    return programme.name();
  }

  public String getType() {
    return type;
  }

  /**
   * Renvoit une HashMap contenant les différentes TypeMobilite.
   * 
   * @return la HashMap demandée
   */
  public static HashMap<String, String> getAll() {
    Programme[] lesTypes = Programme.values();
    HashMap<String, String> resultat = new HashMap<>(lesTypes.length);
    for (Programme c : lesTypes) {
      resultat.put(c.name(), c.getType());
    }
    return resultat;

  }
}
