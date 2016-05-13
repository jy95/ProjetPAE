package persistance.util;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Util {
  /**
   * Methode qui transforme une chaine de caractere en localDateTime. La chaine doit respecter une
   * format jj/mm/aaaa hh:mm.
   */
  public static LocalDateTime stringToDate(String st) {
    if (!biz.util.Util.checkString(st)) {
      return null;
    }
    int jour = Integer.parseInt(st.substring(0, 2));
    int mois = Integer.parseInt(st.substring(3, 5));
    int annee = Integer.parseInt(st.substring(6, 10));
    int heure = Integer.parseInt(st.substring(11, 13));
    int minute = Integer.parseInt(st.substring(14));
    return LocalDateTime.of(annee, mois, jour, heure, minute);
  }

  /**
   * transforme un localDatetiem en timestamp.
   */
  public static Timestamp localDatetimeToTimestamp(LocalDateTime ldt) {
    if (!biz.util.Util.checkObject(ldt)) {
      return null;
    }
    return Timestamp.valueOf(ldt);
  }

  /**
   * transforme un timestamp en localDatetime.
   */
  public static LocalDateTime timestampToLocalDatetime(Timestamp ts) {
    if (!biz.util.Util.checkObject(ts)) {
      return null;
    }
    return ts.toLocalDateTime();
  }

  /**
   * transforme un localDate en date.
   */
  public static Date localDateToDate(LocalDate ld) {
    if (!biz.util.Util.checkObject(ld)) {
      return null;
    }
    return java.sql.Date.valueOf(ld);
  }

  /**
   * transforme une date en localDate.
   */
  public static LocalDate dateToLocalDate(Date date) {
    if (!biz.util.Util.checkObject(date)) {
      return null;
    }
    return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
  }

  /**
   * transforme une date en localDatetime.
   */
  public static LocalDateTime dateToLocalDateTime(Date date) {
    if (!biz.util.Util.checkObject(date)) {
      return null;
    }
    return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
  }

}
