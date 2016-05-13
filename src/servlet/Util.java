package servlet;

import biz.dto.PartenaireDto;
import biz.dto.PaysDto;

import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.SortedMap;


public class Util {
  private static Genson jsonBuilder;

  static {
    jsonBuilder = new GensonBuilder().useConstructorWithArguments(true).exclude("mdp")
        .useDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).create();

  }



  /**
   * Méthode pour renvoyer en json la hashmap d'un enum par la méthode getAll().
   * 
   * @param map la hashmap que l'on veut convertir
   * @return le json souhaité
   */
  public static String getAll(HashMap<String, String> map) {
    return jsonBuilder.serialize(map);
  }

  // TODO je dois changer cela
  public static String getAllObject(SortedMap<String, PaysDto> sortedMap) {
    return jsonBuilder.serialize(sortedMap);
  }

  /**
   * Format JSON pour la sélection.
   * 
   * @param map les Partenaires agrées.
   * @return le JSON
   */
  public static String getAllPartenairesAgree(HashMap<String, PartenaireDto> map) {

    JSONArray json = new JSONArray();
    json.put(map);

    return json.toString();
  }

  /**
   * Méthode pour convertir les données d'un formulaire en une HashMap.
   * 
   * @param json Un json
   * @return Une HashMap contenant les données
   * @throws exception.BizException erreur System
   */
  public static HashMap<String, Object> jsonToHashMap(String json) throws exception.BizException {

    JSONObject test;
    HashMap<String, Object> lesDonnees;

    try {
      test = new JSONObject(json);
      lesDonnees = new HashMap<String, Object>(test.length());

      Iterator<?> it = test.keys();
      while (it.hasNext()) {
        String key = (String) it.next();
        if (test.get(key).equals(JSONObject.NULL)) {
          lesDonnees.put(key, null);
        } else {
          lesDonnees.put(key, test.get(key));
        }
      }

      return lesDonnees;
    } catch (JSONException err) {
      throw new exception.BizException("JSON INVALIDE");
    }


  }

  /**
   * Méthode pour sérialiser un dto (WORKING).
   * 
   * @param dto un dto
   * @return le dto en JSON
   */
  public static String infosDto(Object dto) {
    return jsonBuilder.serialize(dto);
  }

  /**
   * Fournit les infos au format JSON de DataTables.
   * 
   * @param recordsTotal total
   * @param donnees l'arrayList
   * @return String JSON
   */
  public static String dataTable(int recordsTotal, ArrayList<?> donnees) {
    JSONObject result = new JSONObject();

    try {

      String dataString = jsonBuilder.serialize(donnees);
      JSONArray data = new JSONArray(dataString);

      if (donnees.size() != 0) {

        result.put("data", data);
      } else {
        result.put("data", new JSONObject());
      }
      return result.toString();
    } catch (JSONException err) {
      err.printStackTrace();
    }
    return null;
  }

}
