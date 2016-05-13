package biz.enumerate;

import biz.util.Util;

import java.util.HashMap;

public enum TypeStage {
  SMS("sms"), SMP("smp");

  String typeStage;

  private TypeStage(String typeStage) {
    this.typeStage = typeStage;
  }

  /**
   * Renvoit l'enum qui correspond à la string en paramètre.
   * 
   * @param string chaine TypeStage stockée en DB
   * @return l'enum correspondant
   */
  public static TypeStage stringToTypeStage(String string) {
    if (!Util.checkString(string)) {
      return null;
    }
    return TypeStage.valueOf(string);
  }

  /**
   * Renvoit la string qui correspond de l'enum en paramètre.
   * 
   * @param typeStage TypseStage
   * @return String la valeur  en String du typeStage en param
   */
  public static String typeStageToString(TypeStage typeStage) {
    if (!Util.checkObject(typeStage)) {
      return null;
    }
    return typeStage.name();
  }

  /**
   * Renvoit une HashMap contenant les différentes TypeMobilite.
   * 
   * @return la HashMap demandée
   */
  public static HashMap<String, String> getAll() {
    TypeStage[] lesTypes = TypeStage.values();

    HashMap<String, String> resultat = new HashMap<>(lesTypes.length);
    for (TypeStage c : lesTypes) {
      resultat.put(c.name(), c.getTypeStage());
    }
    return resultat;
  }

  public String getTypeStage() {
    return typeStage;
  }

}
