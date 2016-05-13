package biz.biz;

public class Pays implements PaysBiz {
  private String pkPays;

  private String nom;
  private String typeMoblite;

  @Override
  public String getId() {
    return pkPays;
  }

  @Override
  public void setPkPays(String pkPays) {
    this.pkPays = pkPays;
  }

  @Override
  public String getNom() {
    return nom;
  }

  @Override
  public void setNom(String nom) {
    this.nom = nom;
  }

  @Override
  public String getTypeMobilite() {
    return typeMoblite;
  }

  @Override
  public void setTypeMoblite(String typeMobilite) {
    this.typeMoblite = typeMobilite;

  }

  @Override
  public void checkPays() {

  }

  @Override
  public boolean checkPays(String pays) {
    return false;
  }

}
