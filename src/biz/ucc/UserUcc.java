package biz.ucc;

import biz.dto.UserDto;
import biz.enumerate.TypeUser;

import java.util.ArrayList;

public interface UserUcc {
  public UserDto seConnecter(UserDto user);

  public void creerUtilisateur(UserDto user);

  public UserDto modifierUser(UserDto user);

  public ArrayList<UserDto> rechercherUser(String champRecherche);

  public UserDto rechercherUserPseudo(UserDto user);
  
  public boolean changePermission(UserDto[] users, TypeUser type);
}
