package persistance.dao;

import exception.BizException;
import exception.FatalException;
import biz.dto.PaysDto;
import biz.factory.DtoFactory;
import biz.ucc.PartenaireTest;

import java.util.SortedMap;
import java.util.TreeMap;

public class MockPays implements PaysDao {
	DtoFactory dtoFact;

	public MockPays(DtoFactory dtoFact) {
		this.dtoFact = dtoFact;
	}

	@Override
  public SortedMap<String, PaysDto> lireTousPays() {
	if(PartenaireTest.fatal)throw new FatalException();
	SortedMap<String, PaysDto> hm = new TreeMap<String, PaysDto>();
    PaysDto paysDto = dtoFact.getPaysDto();
	hm.put("pays", paysDto);
	return hm;
  }

	@Override
	public PaysDto lirePaysByPk(PaysDto pays) {

		return pays;
	}

	@Override
	public PaysDto rechercherPays(PaysDto pays) {
		if (pays.getNom().equals("af"))
			throw new BizException();
		return pays;
	}

}
