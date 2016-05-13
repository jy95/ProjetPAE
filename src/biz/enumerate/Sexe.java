package biz.enumerate;

import biz.util.Util;

import java.util.HashMap;

public enum Sexe {
  M("masculin"), F("féminin");

  String sexe;

  private Sexe(String sexe) {
    this.sexe = sexe;
  }

  /**
   * Renvoie l'enum qui correspond à la string en paramètre.
   * 
   * @param string chaine sexe stockée en DB
   * @return l'enum correspondant
   */
  public static Sexe stringToSexe(String string) {
    if (!Util.checkString(string)) {
      return null;
    }
    return Sexe.valueOf(string);
  }

  public String valeur() {
    return sexe;
  }

  /**
   * Renvoit la chaine de caractères correspondant à l'instance de l'enum sexe.
   * @param sexe Sexe
   * @return String la valeur de l'instance enum passé en param
   */
  public static String sexeToString(Sexe sexe) {
    if (!Util.checkObject(sexe)) {
      return null;
    }
    return sexe.name();
  }

  /**
   * Renvoit une HashMap contenant les différents sexes.
   * 
   * @return la HashMap demandée
   */
  public static HashMap<String, String> getAll() {
    Sexe[] lesSexes = Sexe.values();
    HashMap<String, String> resultat = new HashMap<>(lesSexes.length);
    for (Sexe c : lesSexes) {
      resultat.put(c.name(), c.valeur());
    }
    return resultat;
  }
}
