package biz.biz;

import biz.util.Util;
import exception.BizException;


public class Annulation implements AnnulationBiz {

  private int pkAnnulation;
  private String description;
  private boolean generique;
  private int numVersion;

  @Override
  public int getId() {
    return pkAnnulation;
  }

  @Override
  public void setPkAnnulation(int pkAnnulation) {
    this.pkAnnulation = pkAnnulation;
  }

  @Override
  public String getDescription() {

    return description;
  }

  @Override
  public void setDescription(String description) {

    this.description = description;
  }

  @Override
  public boolean getGenerique() {

    return generique;
  }

  @Override
  public void setGenerique(boolean generique) {

    this.generique = generique;
  }

  @Override
  public int getNumVersion() {
    return numVersion;
  }

  @Override
  public void setNumVersion(int numVersion) {
    this.numVersion = numVersion;
  }

  @Override
  public void checkAnnulation() {
    if (!Util.checkString(this.description)) {
      throw new BizException("description vide ou null");
    }
    if (!Util.checkValPositive(this.pkAnnulation)) {
      throw new BizException("pkAnnulation non positive");
    }
  }


}
