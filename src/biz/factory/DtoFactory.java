package biz.factory;

import biz.dto.AnnulationDto;
import biz.dto.DemandeDto;
import biz.dto.DemandePaiementDto;
import biz.dto.DepartementDto;
import biz.dto.DocDepartDto;
import biz.dto.DocRetourDto;
import biz.dto.MobiliteDto;
import biz.dto.PartenaireDto;
import biz.dto.PaysDto;
import biz.dto.UserDto;

public interface DtoFactory {

  /*
   * Methode permettant de recuperer une AnnulationDto.
   * 
   * @return AnnulationDto
   */
  public AnnulationDto getAnnulationDto();

  /*
   * Methode permettant de recuperer un DemandeDto.
   * 
   * @return DemandeDto
   */
  public DemandeDto getDemandeDto();

  /*
   * Methode permettant de recuperer un DemandePaiementDto.
   * 
   * @return PartenaireDto
   */
  public DemandePaiementDto getDemandePaiementDto();

  /*
   * Methode permettant de recuperer une DocDepartDto.
   * 
   * @return DocDepartDto
   */
  public DocDepartDto getDocDepartDto();

  /*
   * Methode permettant de recuperer un DocRetourDto.
   * 
   * @return DocRetourDto
   */
  public DocRetourDto getDocRetourDto();

  /*
   * Methode permettant de recuperer un MobiliteDto.
   * 
   * @return MobiliteDto
   */
  public MobiliteDto getMobiliteDto();


  /*
   * Methode permettant de recuperer un PartenaireDto.
   * 
   * @return PartenaireDto
   */
  public PartenaireDto getPartenaireDto();

  /*
   * Methode permettant de recuperer un PaysDto.
   * 
   * @return PaysDto
   */
  public PaysDto getPaysDto();

  /*
   * Methode permettant de recuperer un UserDto.
   * 
   * @return UserDto
   */
  public UserDto getUserDto();


  /*
   * Methode permettant de recuperer un DepartementDto.
   * 
   * @return DepartementDto
   */
  public DepartementDto getDepartementDto();
}
