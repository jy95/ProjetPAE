package biz.biz;

import biz.dto.PaysDto;

public interface PaysBiz extends PaysDto {
  public void setPkPays(String pkPays);

  // pour verifier la validité d'un contact
  public void checkPays();

  public boolean checkPays(String pays);
}
