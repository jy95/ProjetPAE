package biz.biz;

import biz.util.Util;
import exception.BizException;

public class Departement implements DepartementBiz {

  // private TypeDepartement pkDepartement;
  private String pkDepartement;
  private String nom;
  private int numVersion;

  @Override
  public String getNom() {
    return nom;
  }

  @Override
  public void setNom(String nom) {
    this.nom = nom;

  }

  @Override
  public String getId() {
    return this.pkDepartement;
    // return pkDepartement.getDepartement();
  }

  @Override
  public void setPkDepartement(String pkDepartement) {
    // this.pkDepartement = new TypeDepartement(pkDepartement);
    this.pkDepartement = pkDepartement;

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
  public void checkDepartement() {
    if (!Util.checkString(pkDepartement)) {
      throw new BizException("pkDepartement");
    }
    if (!Util.checkString(nom)) {
      throw new BizException("nom non-intensié ou vide");
    }

  }

}
