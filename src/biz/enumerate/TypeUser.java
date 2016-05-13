package biz.enumerate;

import biz.util.Util;

import java.util.HashMap;

public enum TypeUser {
  PROF("Professeur"), ETUD("Etudiant");

  String valeur;

  private TypeUser(String valeur) {
    this.valeur = valeur;
  }

  public String getType() {
    return this.valeur;
  }

  /**
   * Renvoit l'enum qui correspond à la string en paramètre.
   * 
   * @param string chaine TypeStage stockée en DB
   * @return l'enum correspondant
   */
  public static TypeUser stringToTypeUser(String string) {
    if (!Util.checkString(string)) {
      return null;
    }
    return TypeUser.valueOf(string);
  }

  /**
   * Renvoit le string qui correspond à l'enum en paramètre.
   * 
   * @param typeUser TypeUser
   * @return String
   */
  public static String typeUserToString(TypeUser typeUser) {
    if (!Util.checkObject(typeUser)) {
      return null;
    }
    return typeUser.name();
  }

  /**
   * Renvoit une HashMap contenant les différentes TypeMobilite.
   * 
   * @return la HashMap demandée
   */
  public static HashMap<String, String> getAll() {
    TypeUser[] lesTypes = TypeUser.values();
    HashMap<String, String> resultat = new HashMap<>(lesTypes.length);
    for (biz.enumerate.TypeUser c : lesTypes) {
      resultat.put(c.name(), c.getType());
    }
    return resultat;
  }

}
