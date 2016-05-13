package biz.util;

import org.mindrot.jbcrypt.BCrypt;

public class Cryptage {

  public static String hash(String password, String salt) {
    String hash = BCrypt.hashpw(password, salt);
    return hash;
  }

  public static boolean check(String password, String hash) {
    return BCrypt.checkpw(password, hash);
  }

  public static String newSalt() {
    String salt = BCrypt.gensalt();
    return salt;
  }
}
