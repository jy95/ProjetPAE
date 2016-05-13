package biz.ucc;

import biz.dto.AnnulationDto;
import biz.dto.DemandeDto;
import biz.dto.DepartementDto;
import biz.dto.MobiliteDto;
import biz.dto.PartenaireDto;
import biz.dto.PaysDto;
import biz.dto.UserDto;
import persistance.dao.AnnulationDao;
import persistance.dao.DemandeDao;
import persistance.dao.DepartementDao;
import persistance.dao.MobiliteDao;
import persistance.dao.PartenaireDao;
import persistance.dao.PaysDao;
import persistance.dao.UserDao;

public class UtilUcc {
  /**
   * Methode Static qui remplit renvoie un userDto remplit sur base de l'id dans celui envoyer en
   * paramètre.
   * 
   * @param userDto UserDto
   * @param userDao UserDao
   * @return UserDto un user
   */
  protected static UserDto remplirUser(UserDto userDto, UserDao userDao) {
    UserDto out = userDao.lireUserPk(userDto);
    return out;
  }

  /**
   * Methode Static qui remplit renvoie un PartenaireDto remplit sur base de l'id dans celui envoyer
   * en paramètre.
   * 
   * @param partenaireDto PartenaireDto
   * @param partenaireDao PartenaireDao
   * @return PartenaireDto un partenaire
   */
  protected static PartenaireDto remplirPartenaire(PartenaireDto partenaireDto,
      PartenaireDao partenaireDao) {
    return partenaireDao.lirePartenairePk(partenaireDto);
  }

  /**
   * Methode Static qui remplit renvoie un PaysDto remplit sur base de l'id dans celui envoyer en
   * paramètre.
   * 
   * @param paysDto PaysDto
   * @param paysDao PaysDao
   * @return PaysDto un pays
   */
  protected static PaysDto remplirPays(PaysDto paysDto, PaysDao paysDao) {
    return paysDao.lirePaysByPk(paysDto);
  }

  /**
   * Methode Static qui remplit renvoie un DemandeDto remplit sur base de l'id dans celui envoyer en
   * paramètre.
   * 
   * @param demandeDto DemandeDto
   * @param demandeDao DemandeDao
   * @return DemandeDto une demande
   */
  protected static DemandeDto remplirDemande(DemandeDto demandeDto, DemandeDao demandeDao) {
    return demandeDao.lireDemande(demandeDto);
  }

  /**
   * Methode Static qui remplit renvoie un MobiliteDto remplit sur base de l'id dans celui envoyer
   * en paramètre.
   * 
   * @param mobiliteDto MobiliteDto
   * @param mobiliteDao MobiliteDao
   * @return MobiliteDto une mobilité
   */
  protected static MobiliteDto remplirMobilite(MobiliteDto mobiliteDto, MobiliteDao mobiliteDao) {
    return mobiliteDao.lireMobilitePk(mobiliteDto);
  }

  /**
   * Methode Static qui remplit renvoie un AnnulationDto remplit sur base de l'id dans celui envoyer
   * en paramètre.
   * 
   * @param annulationDto AnnulationDto
   * @param annulationDao AnnulationDao
   * @return AnnulationDto une annulation 
   */
  protected static AnnulationDto remplirAnnulation(AnnulationDto annulationDto,
      AnnulationDao annulationDao) {
    return annulationDao.lireAnnulationPk(annulationDto);
  }

  /**
   * Methode Static qui remplit renvoie un DepartementDto remplit sur base de l'id dans celui
   * envoyer en paramètre.
   * 
   * @param dep DepartementDto
   * @param departementDao DepartementDao
   * @return DepartementDto un département
   */
  protected static DepartementDto remplirDepartement(DepartementDto dep,
      DepartementDao departementDao) {
    return departementDao.lireDepartementPk(dep);
  
  } 
}
