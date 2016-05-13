package persistance.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import biz.biz.DemandeBiz;
import biz.biz.PartenaireBiz;
import biz.biz.PaysBiz;
import biz.biz.UserBiz;
import biz.dto.DemandeDto;
import biz.dto.PartenaireDto;
import biz.dto.PaysDto;
import biz.dto.UserDto;
import biz.enumerate.Programme;
import biz.enumerate.TypeStage;
import biz.factory.DtoFactory;
import exception.FatalException;
import persistance.dal.DalBackendServices;
import persistance.util.Util;

public class DemandeDaoImpl implements DemandeDao {
  private DalBackendServices dalb;
  private DtoFactory dtoFact;


  public DemandeDaoImpl(DalBackendServices dalb, DtoFactory dtoFact) {
    this.dalb = dalb;
    this.dtoFact = dtoFact;
  }

  @Override
  public void ecrireDemande(DemandeDto demande) throws FatalException {

    PreparedStatement ps = dalb.getPreparedStatement(DalBackendServices.ECRIREDEMANDE);
    try {
      ps.setInt(1, demande.getPreference());
      ps.setString(2, demande.getVille());
      ps.setString(3, TypeStage.typeStageToString(demande.getTypeStage()));
      ps.setInt(4, demande.getQuadri());
      if (demande.getPartenaire() != null && demande.getPartenaire().getId() != 0) {
        int numPartenaire = demande.getPartenaire().getId();
        ps.setInt(5, numPartenaire);
      } else {
        // ps.setString(5, "NULL");
        ps.setNull(5, java.sql.Types.INTEGER);
      }
      ps.setInt(6, demande.getEtudiant().getId());
      if (demande.getPays() != null && !demande.getPays().getId().equals("")) {
        String idPays = demande.getPays().getId();
        ps.setString(7, idPays);
      } else {
        ps.setNull(7, java.sql.Types.CHAR);
      }
      ps.setBoolean(8, demande.getValidee());
      ps.setString(9, Programme.programmeToString(demande.getProgramme()));
      ps.setTimestamp(10, Util.localDatetimeToTimestamp(demande.getDateIntroduction()));
      ps.setInt(11, ((DemandeBiz) demande).getNumVersion());
      ps.setString(12, demande.getAnneeAcademique());

      ResultSet rs = ps.executeQuery();
      if (!rs.next()) {
        rs.close();
        throw new FatalException("L'insert n'a pas pu être effectuée.");
      }

      // IMPORTANT
      // Il faut récupérer l'ID de la demande pour créer l'eventuelle mobilité
      ((DemandeBiz) demande).setPkDemande(rs.getInt(1));
      rs.close();
    } catch (SQLException err) {

      throw new FatalException(err.getMessage());
    }

  }

  @Override
  public DemandeDto lireDemande(DemandeDto demande) {
    PreparedStatement ps = dalb.getPreparedStatement(DalBackendServices.LIREDEMANDEPK);
    DemandeDto demandeDto = dtoFact.getDemandeDto();
    try {
      ps.setInt(1, ((DemandeBiz) demande).getId());
      ResultSet rs = ps.executeQuery();

      if (rs.next()) {
        demandeDto = (DemandeBiz) remplirDemande(rs);
      }
      rs.close();
    } catch (SQLException err) {
      throw new FatalException(err.getMessage());
    }
    return demandeDto;
  }

  @Override
  public ArrayList<DemandeDto> lireToutesDemandes() {
    PreparedStatement ps = dalb.getPreparedStatement(DalBackendServices.LIRETOUTDEMANDE);
    DemandeBiz demande;
    ArrayList<DemandeDto> demandes = new ArrayList<DemandeDto>();
    try {
      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        demande = (DemandeBiz) remplirDemande(rs);
        demandes.add(demande);
      }
      rs.close();
    } catch (SQLException err) {
      throw new FatalException(err.getMessage());
    }
    return demandes;
  }

  @Override
  public ArrayList<DemandeDto> rechercherDemandes(UserDto user) {
    PreparedStatement ps = dalb.getPreparedStatement(DalBackendServices.LIRETOUTDEMANDESUSER);
    DemandeBiz demande;
    ArrayList<DemandeDto> demandes = new ArrayList<DemandeDto>();
    try {
      ps.setInt(1, user.getId());
      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        demande = (DemandeBiz) remplirDemande(rs);
        demandes.add(demande);
      }
      rs.close();
    } catch (SQLException err) {
      throw new FatalException(err.getMessage());
    }
    return demandes;
  }

  private DemandeBiz remplirDemande(ResultSet rs) {
    DemandeBiz demande = (DemandeBiz) dtoFact.getDemandeDto();
    try {
      demande.setPkDemande(rs.getInt(1));
      demande.setPreference(rs.getInt(2));
      demande.setVille(rs.getString(3));
      TypeStage typeStage = TypeStage.stringToTypeStage(rs.getString(4));
      demande.setTypeStage(typeStage);
      demande.setQuadri(rs.getInt(5));
      PartenaireDto partenaire = dtoFact.getPartenaireDto();
      ((PartenaireBiz) partenaire).setPkPartenaire(rs.getInt(6));
      demande.setPartenaire(partenaire);
      UserDto etudiant = dtoFact.getUserDto();
      ((UserBiz) etudiant).setPkUser(rs.getInt(7));
      PaysDto pays = dtoFact.getPaysDto();
      ((PaysBiz) pays).setPkPays(rs.getString(8));
      demande.setPays(pays);
      demande.setEtudiant(etudiant);
      demande.setValidee(rs.getBoolean(9));
      demande.setDateIntroduction(Util.dateToLocalDateTime(rs.getDate(11)));
      ((DemandeBiz) demande).setNumVersion(rs.getInt(12));
      demande.setAnneeAcademique(rs.getString(13));
      Programme programme = Programme.stringToProgramme(rs.getString(10));
      demande.setProgramme(programme);

    } catch (SQLException err) {
      throw new FatalException(err.getMessage());
    }
    return demande;
  }

  @Override
  public void validerDemande(DemandeDto demande) {

    PreparedStatement ps = dalb.getPreparedStatement(DalBackendServices.MODIFIERVALIDEEDEMANDE);
    try {
      
      ps.setInt(1, demande.getPreference());
      ps.setString(2, demande.getVille());
      ps.setString(3, TypeStage.typeStageToString(demande.getTypeStage()));
      ps.setInt(4, demande.getQuadri());
      ps.setInt(5, demande.getPartenaire().getId());
      ps.setString(6, demande.getPays().getId());
      ps.setString(7, Programme.programmeToString(demande.getProgramme()));
      ps.setInt(8, ((DemandeBiz) demande).getNumVersion() + 1);
      ps.setString(9, demande.getAnneeAcademique());
      ps.setInt(10, ((DemandeBiz) demande).getId());
      
      int nbLigne = ps.executeUpdate();
      if (nbLigne == 0) {
        throw new FatalException("L'update n'a pas pu être effectuée.");
      }

    } catch (SQLException err) {

      throw new FatalException(err.getMessage());
    }

  }


  @Override
  public void modifierPreferenceDemande(DemandeDto demande) {

    PreparedStatement ps = dalb.getPreparedStatement(DalBackendServices.MODIFIERPREFERENCEDEMANDE);
    try {
      ps.setInt(1, demande.getPreference());
      ps.setInt(1, ((DemandeBiz) demande).getNumVersion() + 1);

      int nbLigne;
      nbLigne = ps.executeUpdate();
      if (nbLigne == 0) {

        throw new FatalException("L'update n'a pas pu être effectuée.");
      }

    } catch (SQLException err) {

      throw new FatalException(err.getMessage());
    }

  }


}
