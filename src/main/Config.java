package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Config {

  private Properties props;

  /**
   * fichier de config.
   * 
   * @param path String
   */
  public Config(String path) {
    props = new Properties();
    FileInputStream fis = null;

    try {
      fis = new FileInputStream(path);
      props.load(fis);
    } catch (FileNotFoundException err) {
      throw new InternalError("fichier inconnu ");
    } catch (IOException err) {
      throw new InternalError("erreur d'ouverture de fichier");
    } finally {
      if (fis != null) {
        try {
          fis.close();
        } catch (IOException err) {
          throw new InternalError("erreur de fermeture de fichier");
        }

      }
    }

  }

  public String getConfig(String key) {
    return props.getProperty(key);
  }

}
