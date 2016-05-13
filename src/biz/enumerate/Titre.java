package biz.enumerate;

import biz.util.Util;

import java.util.HashMap;

public enum Titre {

  M("Monsieur"), Mme("Madame"), Mlle("Mademoiselle");

  String sexe;

  private Titre(String sexe) {
    this.sexe = sexe;
  }

  /**
   * Renvoit l'enum qui correspond à la string en paramètre.
   * 
   * @param string chaine titre stockée en DB
   * @return l'enum correspondant
   */
  public static Titre stringToTitre(String string) {
    if (!Util.checkString(string)) {
      return null;
    }
    return Titre.valueOf(string);
  }

  /**
   * Renvoit le string qui correspond à l'enum en paramètre.
   * 
   * @param titre chaine titre stockée en DB
   * @return String la valeur de l'instance enum passé en param
   */
  public static String titreToString(Titre titre) {
    if (!Util.checkObject(titre)) {
      return null;
    }
    return titre.name();
  }

  public String valeur() {
    return sexe;
  }

  /**
   * Renvoit une HashMap contenant les différents titres.
   * 
   * @return la HashMap demandée
   */
  public static HashMap<String, String> getAll() {
    Titre[] lesTitre = Titre.values();
    HashMap<String, String> resultat = new HashMap<>(lesTitre.length);
    for (Titre c : lesTitre) {
      resultat.put(c.name(), c.valeur());
    }
    return resultat;
  }

}
