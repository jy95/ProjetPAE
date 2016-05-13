package persistance.dao;

import biz.biz.UserBiz;
import biz.dto.UserDto;
import biz.enumerate.TypeUser;
import biz.factory.DtoFactory;
import exception.FatalException;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class MockUser implements UserDao {
	private DtoFactory dtoFact;
	private static boolean prof = false;

	public MockUser(DtoFactory dtoFact) {
		this.dtoFact = dtoFact;
	}

	@Override
	public boolean checkNoUser() {
		MockUser.prof = !MockUser.prof;
		return MockUser.prof;
	}

	@Override
	public boolean checkPseudoUnique(UserDto userDto) {
		return !"cedric".equals(userDto.getPseudo());
	}

	@Override
	public void ecrireUser(UserDto userDto) {
		if (userDto.getId() == 2)
			throw new FatalException();
	}

	@Override
	public UserDto lireUserPk(UserDto userDto) {

		if (userDto == null || ((UserBiz)userDto).getNumVersion() == 2)
			userDto = dtoFact.getUserDto();
		if (userDto.getId() == 2)
			throw new FatalException();
		((UserBiz) userDto).setNumVersion(1);
		return userDto;

	}

	@Override
	public UserDto lireUserPseudo(UserDto userDto) {
		if (userDto == null)
			userDto = dtoFact.getUserDto();
		if (userDto.getPseudo() == "clara") {
			UserDto uDto = dtoFact.getUserDto();
			uDto.setPseudo("pseudo1");
			uDto.setMdp("brenda");
			((UserBiz) uDto).setPkUser(1);
			uDto.setEmail("pocpoc@maxime.com");
			uDto.setNom("yakoub");
			uDto.setPrenom("jacques");
			uDto.setDepartement(dtoFact.getDepartementDto());
			((UserBiz) uDto).setNumVersion(1);
			uDto.setDateInscription(LocalDateTime.now());
			return uDto;
		}
		userDto.setMdp("$2a$10$SnWsrHvVBl83pmxenkUOq.7nrcSkVj.pbtI7ihRSZbVet/88flioO");
		return userDto;

	}

	@Override
	public UserDto modifierUser(UserDto userDto) {
		if (userDto.getId() == 2)
			throw new FatalException();

		return userDto;
	}

	@Override
	public ArrayList<UserDto> rechercherUser(String champRecherche) {
		UserDto uDto = dtoFact.getUserDto();
		ArrayList<UserDto> users = new ArrayList<UserDto>();
		((UserBiz) uDto).setPkUser(1);
		users.add(uDto);
		return users;

	}

	@Override
	public ArrayList<UserDto> lireAllUser() {
		UserDto uDto = dtoFact.getUserDto();
		UserDto uDto2 = dtoFact.getUserDto();

		ArrayList<UserDto> users = new ArrayList<UserDto>();
		((UserBiz) uDto).setPkUser(1);
		((UserBiz) uDto2).setPkUser(2);
		users.add(uDto);
		return users;
	}

	@Override
	public boolean changeUsersRights(UserDto[] users, TypeUser type) {
		if (type == TypeUser.PROF)
			throw new FatalException();
		return false;
	}

}
