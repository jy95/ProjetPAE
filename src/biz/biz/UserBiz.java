package biz.biz;

import biz.dto.UserDto;

public interface UserBiz extends UserDto {

  public void setPkUser(int pkUser);

  // pour verifier la validité d'un contact
  public void checkUser();

  public boolean checkValidePassword(String pwd);

  public int getNumVersion();

  public void setNumVersion(int numVersion);

}
