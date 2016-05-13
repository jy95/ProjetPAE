package persistance.dao;

import biz.dto.UserDto;
import biz.enumerate.TypeUser;

import java.util.ArrayList;

public interface UserDao {

  public boolean checkNoUser();

  /*
   * Cette methode renvoie true si le pseudo n'est pas présant dans le db pour un user. Si non il
   * renvoie false.
   * 
   * @param UserDto
   * 
   * @return boolean
   */
  public boolean checkPseudoUnique(UserDto userDto);

  public void ecrireUser(UserDto userDto);

  public UserDto lireUserPk(UserDto userDto);

  public UserDto lireUserPseudo(UserDto userDto);

  public UserDto modifierUser(UserDto userDto);

  public ArrayList<UserDto> rechercherUser(String champRecherche);

  public ArrayList<UserDto> lireAllUser();

  public boolean changeUsersRights(UserDto[] users , TypeUser type);

}
