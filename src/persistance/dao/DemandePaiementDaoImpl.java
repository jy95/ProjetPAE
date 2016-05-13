package persistance.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import biz.biz.AnnulationBiz;
import biz.biz.DemandeBiz;
import biz.biz.DemandePaiementBiz;
import biz.biz.MobiliteBiz;
import biz.biz.PartenaireBiz;
import biz.biz.PaysBiz;
import biz.biz.UserBiz;
import biz.dto.DemandePaiementDto;
import biz.dto.MobiliteDto;
import biz.enumerate.EtatMobilite;
import biz.enumerate.Programme;
import biz.enumerate.TypeStage;
import biz.factory.DtoFactory;
import exception.BizException;
import exception.FatalException;
import persistance.dal.DalBackendServices;
import persistance.util.Util;

public class DemandePaiementDaoImpl implements DemandePaiementDao {
  DtoFactory dtoFact;
  DalBackendServices dalb;


  public DemandePaiementDaoImpl(DalBackendServices dalb, DtoFactory dtoFact) {
    this.dalb = dalb;
    this.dtoFact = dtoFact;
  }

  @Override
  public void ecrireDemandePaiement(DemandePaiementDto demPaiement) throws FatalException {

    PreparedStatement ps = dalb.getPreparedStatement(DalBackendServices.ECRIREDEMANDEPAIEMENT);

    try {
      ps.setTimestamp(1, Util.localDatetimeToTimestamp(demPaiement.getDateExcution()));

      UserBiz prof = (UserBiz) demPaiement.getProfesseur();
      UserBiz etud = (UserBiz) demPaiement.getEtudiant();
      MobiliteBiz mobi = (MobiliteBiz) demPaiement.getMoblite();

      ps.setInt(2, prof.getId());
      ps.setInt(3, etud.getId());
      ps.setInt(4, mobi.getId());
      ps.setInt(5, ((DemandePaiementBiz) demPaiement).getNumVersion());

      int nbLigne;
      nbLigne = ps.executeUpdate();
      if (nbLigne == 0) {
        throw new FatalException("L'insert n'a pas pu être effectuée.");
      }

    } catch (SQLException err) {

      throw new FatalException();
    }

  }

  @Override
  public DemandePaiementDto lireDemandePaiementPk(DemandePaiementDto demandePaiement) {
    DemandePaiementDto paiement = dtoFact.getDemandePaiementDto();
    PreparedStatement ps = dalb.getPreparedStatement(DalBackendServices.LIREDEMANDEPAIEMENT);
    ResultSet rs;

    try {
      ps.setInt(1, ((DemandePaiementBiz) demandePaiement).getId());

      rs = ps.executeQuery();

      if (!rs.next()) {
        rs.close();
        throw new BizException("DocDepart introuvable");
      }

      paiement = remplirDemandePaiement(rs);
      rs.close();
    } catch (SQLException err) {
      throw new FatalException(err.getMessage());

    }

    return paiement;
  }

  @Override
  public ArrayList<DemandePaiementDto> lireDemandePaiementMobilite(MobiliteDto mobilite) {
    PreparedStatement ps = dalb.getPreparedStatement(DalBackendServices.LIREDEMANDEPAIMENTMOBILITE);
    ResultSet rs;
    ArrayList<DemandePaiementDto> lesDemande = new ArrayList<DemandePaiementDto>();

    try {
      ps.setInt(1, mobilite.getId());
      rs = ps.executeQuery();

      while (rs.next()) {
        DemandePaiementDto demande = dtoFact.getDemandePaiementDto();
        demande = remplirDemandePaiement(rs);
        lesDemande.add(demande);
      }
      rs.close();
    } catch (SQLException err) {
      throw new FatalException(err.getMessage());
    }

    return lesDemande;
  }


  @Override
  public ArrayList<DemandePaiementDto> lireAllDemandePaiement() {
    PreparedStatement ps = dalb.getPreparedStatement(DalBackendServices.LIREALLDEMANDEPAIMENT);
    ResultSet rs;
    ArrayList<DemandePaiementDto> lesDemande = new ArrayList<DemandePaiementDto>();

    try {
      rs = ps.executeQuery();

      while (rs.next()) {
        DemandePaiementDto demande = dtoFact.getDemandePaiementDto();
        demande = remplirDemandePaiement(rs);
        lesDemande.add(demande);
      }
      rs.close();
    } catch (SQLException err) {
      throw new FatalException(err.getMessage());
    }

    return lesDemande;
  }

  private DemandePaiementDto remplirDemandePaiement(ResultSet rs) {
    DemandePaiementDto paiement = dtoFact.getDemandePaiementDto();

    try {
      ((DemandePaiementBiz) paiement).setPkDemandePaiement(rs.getInt(1));

      paiement.setDateExecution(Util.timestampToLocalDatetime(rs.getTimestamp(2)));

      UserBiz etud = (UserBiz) dtoFact.getUserDto();
      etud.setPkUser(rs.getInt(3));
      paiement.setEtudiant(etud);

      UserBiz prof = (UserBiz) dtoFact.getUserDto();
      prof.setPkUser(rs.getInt(4));
      paiement.setProfesseur(prof);

      MobiliteBiz mobi = (MobiliteBiz) dtoFact.getMobiliteDto();
      mobi.setPkMobilite(rs.getInt(5));
      paiement.setMobilite(mobi);

      ((DemandePaiementBiz) paiement).setNumVersion(rs.getInt(6));
    } catch (SQLException err) {
      throw new FatalException(err.getMessage());
    }

    return paiement;
  }

  @Override
  public ArrayList<MobiliteDto> listerTouteMobiliteAvecDemandePaiement() {
    PreparedStatement ps = dalb.getPreparedStatement(
        DalBackendServices.LIRETOUTEMOBILITEAVECDEMANDEPAIEMENT);
    ResultSet rs;
    ArrayList<MobiliteDto> lesMobilites = new ArrayList<MobiliteDto>();
    Set<Integer> ensemble = new HashSet<>();
    try {
      rs = ps.executeQuery();

      while (rs.next()) {
        int pkMobilite = rs.getInt(1);
        if (!ensemble.contains(pkMobilite)) {
          ensemble.add(pkMobilite);
          int pkAnnulation = rs.getInt(8);
          if (pkAnnulation == 0) {

            // 0=>16 mobilite

            MobiliteBiz mobi = (MobiliteBiz) dtoFact.getMobiliteDto();
            mobi.setPkMobilite(pkMobilite);
            UserBiz user = (UserBiz) dtoFact.getUserDto();
            user.setPkUser(rs.getInt(2));
            mobi.setEtudiant(user);

            DemandeBiz demande = (DemandeBiz) dtoFact.getDemandeDto();
            demande.setPkDemande(rs.getInt(3));
            mobi.setDemande(demande);

            PaysBiz pays = (PaysBiz) dtoFact.getPaysDto();
            pays.setPkPays(rs.getString(4));
            mobi.setPays(pays);

            mobi.setVille(rs.getString(5));

            PartenaireBiz part = (PartenaireBiz) dtoFact.getPartenaireDto();
            part.setPkPartenaire(rs.getInt(6));
            mobi.setPartenaire(part);
            mobi.setEtat(EtatMobilite.stringToEtatMobilite(rs.getString(7)));

            AnnulationBiz annulation = (AnnulationBiz) dtoFact.getAnnulationDto();
            annulation.setPkAnnulation(pkAnnulation);
            mobi.setAnnulation(annulation);

            mobi.setAnneeAcademique(rs.getString(9));
            mobi.setProEco(rs.getBoolean(10));
            mobi.setMobilityTool(rs.getBoolean(11));
            mobi.setMobi(rs.getBoolean(12));
            mobi.setStage(TypeStage.stringToTypeStage(rs.getString(13)));
            mobi.setQuadri(rs.getInt(14));
            mobi.setProgramme(Programme.getValType(rs.getString(15)));
            ((MobiliteBiz) mobi).setNumVersion(rs.getInt(16));

            // 17=>22 demande1
            DemandePaiementDto demandePaiement1 = dtoFact.getDemandePaiementDto();
            ((DemandePaiementBiz) demandePaiement1).setPkDemandePaiement(rs.getInt(17));

            demandePaiement1.setDateExecution(Util.timestampToLocalDatetime(rs.getTimestamp(18)));

            // 19
            demandePaiement1.setEtudiant(mobi.getEtudiant());

            UserBiz prof = (UserBiz) dtoFact.getUserDto();
            prof.setPkUser(rs.getInt(20));
            demandePaiement1.setProfesseur(prof);

            // 21
            demandePaiement1.setMobilite(mobi);

            ((DemandePaiementBiz) demandePaiement1).setNumVersion(rs.getInt(22));



            // 23=>28 demande2
            DemandePaiementDto demandePaiement2 = dtoFact.getDemandePaiementDto();
            ((DemandePaiementBiz) demandePaiement2).setPkDemandePaiement(rs.getInt(23));

            demandePaiement2.setDateExecution(Util.timestampToLocalDatetime(rs.getTimestamp(24)));

            // 25
            demandePaiement2.setEtudiant(mobi.getEtudiant());

            prof = (UserBiz) dtoFact.getUserDto();
            prof.setPkUser(rs.getInt(26));
            demandePaiement2.setProfesseur(prof);

            // 27
            demandePaiement2.setMobilite(mobi);

            ((DemandePaiementBiz) demandePaiement2).setNumVersion(rs.getInt(28));

            mobi.setDemandePaiement1(demandePaiement1);
            mobi.setDemandePaiement2(demandePaiement2);
            lesMobilites.add(mobi);
          }
        }

      }
      rs.close();
    } catch (SQLException err) {
      throw new FatalException(err.getMessage());
    }
    return lesMobilites;
  }



}
