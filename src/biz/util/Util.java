package biz.util;

public class Util {
  /**
   * Renvoie true si l'objet est instancié.
   * 
   * @param element Object
   * @return Boolean
   */
  public static boolean checkObject(Object element) {
    return element != null;
  }

  /**
   * Renvoie true si l'entier envoyer en paramètre est superieur à 0.
   * 
   * @param elem int
   * @return Boolean
   */
  public static boolean checkValPositive(int elem) {
    return elem >= 0;
  }

  /**
   * Renvoie true si le String est un email.
   * 
   * @param email String
   * @return Boolean
   */
  public static boolean checkEmail(String email) {
    return checkString(email)
        && email.matches("^(\\w|\\d|\\.|_)+@(\\w|\\d)+(\\.(\\w|\\d)+)*\\.\\w+$");
  }

  /**
   * Renvoie true si le String est une année accadémique.
   * 
   * @param annee (String à tester)
   * @return Boolean
   */
  public static boolean checkAnneeAcademique(String annee) {
    // NE fonctionne pas
    // return checkString(annee) && annee.matches("^\\d+-\\d+$");
    return checkString(annee) && annee.matches("^([0-9]+)-([0-9]+)$");
  }

  /**
   * Renvoie true si le nombre de caractère du String est égale au int.
   * 
   * @param element String
   * @param nbre int
   * @return Boolean
   */
  public static boolean checkNombreCaractere(String element, int nbre) {

    return checkObject(element) && element.length() == nbre;

  }

  /**
   * Renvoie true si la chaine de caractères est instancié et non-vide.
   * 
   * @param st String
   * @return Boolean
   */
  public static boolean checkString(String st) {
    if (!checkObject(st) || st.equals("")) {
      return false;
    }
    return true;

  }

  /**
   * Renvoie true si la chaine de caractères est une date.
   * 
   * @param date String
   * @return Boolean
   */
  public static boolean isDate(String date) {
    return checkString(date) && date.matches("^[0-9]{2}/[0-9]{2}/[0-9]{4} [0-9]{2}:[0-9]{2}$");
  }

  /**
   * Renvoie true si la chaine de caractères correspont à un code IBAN.
   * 
   * @param compte String
   * @return Boolean
   */
  public static boolean checkCompteBancaire(String compte) {
    return checkString(compte)
        && compte.matches("^[a-zA-Z]{2}[0-9]{2}[a-zA-Z0-9]{4}[0-9]{7}([a-zA-Z0-9]?){0,16}$");
  }

  /**
   * Renvoie true si la chaine de caractères correspont à un code BIC.
   * 
   * @param compte String
   * @return Boolean
   */
  public static boolean checkBic(String compte) {
    return checkString(compte)
        && compte.matches("^[a-zA-Z]{4}[a-zA-Z]{2}[a-zA-Z0-9]{2}([a-zA-Z0-9]{3})?$");
  }

}
