package persistance.dal;

import java.sql.PreparedStatement;

public interface DalBackendServices {

  public static final String ECRIREANNULATION = "INSERT INTO projet.annulations(descriptif_raison,"
      + " generique, num_version) VALUES (?,?,?) RETURNING num_annulation";

  public static final String LIREANNULATION =
      "SELECT num_annulation, descriptif_raison, generique  , num_version FROM projet.annulations "
          + "WHERE num_annulation = ?";

  public static final String LIRETOUTANNULATIONGENERIQUE =
      "SELECT num_annulation, descriptif_raison , generique , num_version FROM projet.annulations "
          + "WHERE generique = ?";

  // Demande


  static final String SELECTCOLONNESDEMANDE =
      "num_demande, preference, ville, stage, num_quadri, num_partenaire,"
          + " num_etudiant, code_pays, validee, programme, date_introduction,"
          + " num_version, annee_academique ";

  public static final String ECRIREDEMANDE = "INSERT INTO projet.demandes(" + "preference,"
      + "ville," + "stage," + "num_quadri," + "num_partenaire," + "num_etudiant," + "code_pays,"
      + "validee," + "programme," + "date_introduction, num_version, annee_academique) VALUES"
      + "(?,?,?,?,?,?,?,?,?,?,?,?) RETURNING num_demande";

  public static final String MODIFIERPREFERENCEDEMANDE =
      "UPDATE projet.demandes set preference = ?, num_version = ? WHERE num_demande = ?";

  public static final String MODIFIERVALIDEEDEMANDE =
      "UPDATE projet.demandes SET preference = ? , ville = ? , stage = ?, num_quadri = ?,"
          + " num_partenaire = ? , code_pays = ?, validee = TRUE, programme = ? , "
          + " num_version = ? , annee_academique = ? WHERE num_demande = ?";

  public static final String LIRETOUTDEMANDE = "SELECT " + SELECTCOLONNESDEMANDE
      + " FROM projet.demandes " + "ORDER BY num_etudiant, preference";

  public static final String LIREDEMANDEPK =
      "SELECT " + SELECTCOLONNESDEMANDE + " FROM projet.demandes WHERE num_demande = ?";

  public static final String LIRETOUTDEMANDESUSER = "SELECT " + SELECTCOLONNESDEMANDE
      + " FROM projet.demandes WHERE num_etudiant=? " + "ORDER BY preference";


  // DemandePaiement

  public static final String ECRIREDEMANDEPAIEMENT =
      "INSERT INTO projet.demande_paiements (date_execution, "
          + "num_etudiant, num_professeur, num_mobilite, num_version) VALUES (?,?,?,?,?)";

  public static final String LIREDEMANDEPAIEMENT = "SELECT num_paiement,date_execution,"
      + " num_etudiant, num_professeur, num_mobilite, num_version "
      + "FROM projet.demande_paiements WHERE num_paiement = ? ";

  public static final String LIREALLDEMANDEPAIMENT = "SELECT num_paiement,date_execution,"
      + " num_etudiant, num_professeur, num_mobilite, num_version "
      + "FROM projet.demande_paiements";

  public static final String LIREDEMANDEPAIMENTMOBILITE = "SELECT num_paiement,date_execution,"
      + " num_etudiant, num_professeur, num_mobilite, num_version "
      + "FROM projet.demande_paiements WHERE num_mobilite = ? ";

  public static final String LIRETOUTEMOBILITEAVECDEMANDEPAIEMENT =
      "SELECT m.num_mobilite, m.num_etudiant, "
          + "m.num_demande, m.code_pays, m.ville, m.num_partenaire, m.etat, "
          + "m.num_annulation, m.annee_academique,"
          + " m.proEco, m.mobility_tool, m.mobi, m.stage,num_quadri, m.mobi, m.num_version, "
          + "d1.num_paiement, d1.date_execution, d1.num_etudiant, "
          + "d1.num_professeur, d1.num_mobilite, d1.num_version,"
          + "d2.num_paiement, d2.date_execution, d2.num_etudiant, "
          + "d2.num_professeur, d2.num_mobilite, d2.num_version "
          + "FROM ((projet.mobilites m LEFT OUTER JOIN projet.demande_paiements d1"
          + " ON d1.num_mobilite = m.num_mobilite)"
          + "LEFT OUTER JOIN projet.demande_paiements d2 ON d2.num_mobilite = m.num_mobilite AND "
          + "d2.num_mobilite = d1.num_mobilite AND d1.date_execution < d2.date_execution)"
          + "ORDER BY 23,17,1";

  // Departement
  public static final String LIREDEPARTEMENTPK =
      "SELECT code_departement, nom, num_version FROM projet.departements "
          + "WHERE code_departement = ?";

  public static final String LIRETOUSDEPARTEMENTS =
      "SELECT code_departement, nom, num_version FROM projet.departements "
          + "ORDER BY nom,code_departement";

  public static final String ECRIREDEPARTEMENTPARTENAIRE =
      "INSERT INTO projet.partenairespardepartement" + "(" + "partenaire, "
          + " code_departement, num_version) VALUES" + "(?,?,?)";


  // Doc Depart

  static final String SELECTCOLONNESDOCDEPART =
      "num_depart, contrat_bourse, charte_etudiant, convention_stage_ou_etude,"
          + " preuve_test_langue, doc_engagement, date_depart,"
          + " num_etudiant, num_mobilite, num_version ";



  public static final String ECRIREDOCDEPART =
      "INSERT INTO projet.docs_departs(contrat_bourse, charte_etudiant"
          + ", convention_stage_ou_etude, preuve_test_langue, doc_engagement, "
          + "date_depart, num_etudiant, num_mobilite, num_version) VALUES(?,?,?,?,?,?,?,?,?)";

  public static final String LIREDOCDEPART =
      "SELECT " + SELECTCOLONNESDOCDEPART + " FROM projet.docs_departs" + " WHERE num_depart = ?";

  public static final String LIREDOCDEPARTUSER =
      "SELECT " + SELECTCOLONNESDOCDEPART + " FROM projet.docs_departs" + " WHERE num_etudiant = ?";

  public static final String LIREDOCDEPARTMOBILITE =
      "SELECT " + SELECTCOLONNESDOCDEPART + " FROM projet.docs_departs" + " WHERE num_mobilite = ?";

  public static final String LIRETOUTDOCDEPART =
      "SELECT " + SELECTCOLONNESDOCDEPART + " FROM projet.docs_departs";

  public static final String MODIFIERDOCDEPART =
      "UPDATE projet.docs_departs SET contrat_bourse = ?, charte_etudiant = ?, "
          + "convention_stage_ou_etude = ?, "
          + "preuve_test_langue = ?, doc_engagement = ?, date_depart = ?,"
          + " num_version = ? WHERE num_depart = ?";

  // Doc Retour



  static final String SELECTCOLONNESDOCRETOUR =
      "num_retour, attestation_sejour, releve_note_ou_certif_stage, "
          + "preuve_passage_test,rapport_final, date_retour, num_etudiant, "
          + "num_mobilite, num_version";



  public static final String ECRIREDOCRETOUR =
      "INSERT INTO projet.docs_retours(attestation_sejour, releve_note_ou_certif_stage,"
          + " preuve_passage_test,rapport_final, date_retour, "
          + "num_etudiant, num_mobilite, num_version) VALUES(?,?,?,?,?,?,?,?)";

  public static final String LIREDOCRETOUR =
      "SELECT " + SELECTCOLONNESDOCRETOUR + " FROM projet.docs_retours WHERE num_retour = ?";

  public static final String LIREDOCRETOURUSER =
      "SELECT " + SELECTCOLONNESDOCRETOUR + " FROM projet.docs_retours WHERE num_etudiant = ?";

  public static final String MODIFIERDOCRETOUR =
      "UPDATE projet.docs_retours SET attestation_sejour = ?, releve_note_ou_certif_stage = ?, "
          + "preuve_passage_test = ?, rapport_final = ?, date_retour = ?, num_version = ?"
          + " WHERE num_retour = ?";

  public static final String LIRETOUTDOCRETOUR =
      "SELECT " + SELECTCOLONNESDOCRETOUR + " FROM projet.docs_retours";

  public static final String LIREDOCRETOURMOBILITE =
      "SELECT " + SELECTCOLONNESDOCRETOUR + " FROM projet.docs_retours" + " WHERE num_mobilite = ?";

  // Mobilite.

  public static final String SELECTCOLONNESMOBILITE = "num_mobilite, num_etudiant, "
      + "num_demande, code_pays, ville, num_partenaire, etat, num_annulation, annee_academique,"
      + " proEco, mobility_tool, mobi, stage,num_quadri, num_version ";


  public static final String LIREMOBILITEPK =
      "SELECT " + SELECTCOLONNESMOBILITE + " FROM projet.mobilites " + "WHERE num_mobilite = ?";

  public static final String LIRETOUTMOBILITE =
      "SELECT " + SELECTCOLONNESMOBILITE + " FROM projet.mobilites ";

  public static final String LIREMOBILITESANNULEES = "SELECT " + SELECTCOLONNESMOBILITE
      + " FROM projet.mobilites WHERE num_annulation IS NOT NULL";

  public static final String RECHERCHERMOBILITE = "SELECT " + SELECTCOLONNESMOBILITE
      + " FROM projet.mobilites " + "WHERE etat = ?, annee_academique = ?, num_version = ? ";

  public static final String ECRIREMOBILITE = "INSERT INTO projet.mobilites(num_etudiant, "
      + "num_demande, code_pays, ville, num_partenaire, etat, num_annulation, annee_academique,"
      + " proEco, mobility_TOOL, stage, mobi, num_quadri, num_version)"
      + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?) RETURNING num_mobilite";

  public static final String MODIFIERMOBILITE = "UPDATE projet.mobilites SET num_etudiant = ?,"
      + " num_demande = ?, code_pays = ?, ville = ?,"
      + " num_partenaire = ?, etat = ?, num_annulation = ?,"
      + " annee_academique = ?, proEco = ?, mobility_TOOL = ?,"
      + " mobi = ?, stage = ?, num_quadri = ?, num_version = ? " + "WHERE num_mobilite = ?";

  public static final String LIREALLMOBSTUD =
      "SELECT " + SELECTCOLONNESMOBILITE + " FROM projet.mobilites WHERE num_etudiant = ? ";

  // Partenaire.

  public static final String SELECTCOLONNESPARTENAIRE =
      " num_partenaire, nom_affaire, nom_legal, nom_complet, "
          + "nombre_employes, adresse, createur, code_pays, region,"
          + " code_postal, ville, email, site_web, telephone, stage,"
          + " agree, type_entreprise, num_version , supprime";

  public static final String RECHERCHERPARTENAIRE = "SELECT " + SELECTCOLONNESPARTENAIRE
      + " FROM projet.partenaires WHERE supprime = FALSE AND (nom_legal LIKE ? "
      + "OR nom_affaire LIKE ? OR ville LIKE ? OR code_pays LIKE ?)";

  public static final String ECRIREPARTENAIRE =
      "INSERT INTO projet.partenaires (nom_affaire, nom_legal, "
          + "nombre_employes, adresse, createur, code_pays, region, code_postal, "
          + "ville, email, site_web, telephone, stage, agree, "
          + "nom_complet, type_entreprise, num_version , supprime)"
          + " VALUES(?,?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ?,?, ?)"
          + " RETURNING num_partenaire";

  public static final String LIREPARTENAIRESSUPPRIME =
      "SELECT " + SELECTCOLONNESPARTENAIRE + " FROM projet.partenaires WHERE supprime = TRUE";

  public static final String LIREPARTENAIREPK =
      "SELECT " + SELECTCOLONNESPARTENAIRE + " FROM projet.partenaires WHERE num_partenaire = ?";

  public static final String PARTENAIREAGREEPARDEPARTEMENT =
      "SELECT p.num_partenaire, p.nom_affaire, p.num_version "
          + "from projet.partenaires p, projet.partenairespardepartement pd "
          + "WHERE p.agree = true and p.supprime = FALSE and pd.partenaire = p.num_partenaire and "
          + "pd.code_departement = ? order by p.nom_affaire";

  public static final String LIREALLDEPARTEMENTPARTENAIRE =
      "SELECT p.code_departement , d.nom, d.num_version "
          + "FROM projet.partenairespardepartement p , projet.departements d "
          + "WHERE p.code_departement = d.code_departement AND p.partenaire = ? ;";

  public static final String CHECKPARTENAIREEXISTANT =
      "SELECT num_partenaire FROM projet.partenaires WHERE nom_complet = ? AND supprime = TRUE";

  // Pays


  public static final String SELECTCOLONNESPAYS = " code_pays, programme, nom ";

  public static final String LIRETOUSPAYS = "SELECT " + SELECTCOLONNESPAYS + " FROM projet.pays";

  public static final String LIREPAYSPK =
      "SELECT " + SELECTCOLONNESPAYS + " FROM projet.pays WHERE code_pays = ?";


  public static final String RECHERCHERPAYS =
      "SELECT " + SELECTCOLONNESPAYS + " FROM projet.pays WHERE code_pays = ? OR nom = ? ";

  // Utilisateur.


  public static final String SELECTCOLONNESUSER =
      " num_utilisateur, pseudo, mdp, nom, prenom, departement,"
          + " date_naissance, adresse, telephone, email, nationalite,"
          + " titre, sexe, nbr_annee_ens_sup, titulaire_compte, num_compte,"
          + " nom_banque, code_bic, date_inscription, type_user, num_version ";


  public static final String LIREUSERPSEUDO =
      "SELECT " + SELECTCOLONNESUSER + "FROM projet.utilisateurs WHERE pseudo = ?";

  public static final String LIREUSERPK =
      "SELECT " + SELECTCOLONNESUSER + " FROM projet.utilisateurs WHERE num_utilisateur = ?";

  public static final String ECRIREUSER = "INSERT INTO projet.utilisateurs(" + "pseudo," + "mdp,"
      + "nom," + "prenom," + " departement " + ",date_naissance," + "adresse," + "telephone,"
      + "email," + "nationalite," + "titre," + "sexe," + "nbr_annee_ens_sup," + "titulaire_compte,"
      + "num_compte," + "nom_banque," + "code_bic," + "date_inscription,"
      + "type_user , num_version ) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

  public static final String MODIFIERUSER = "UPDATE projet.utilisateurs SET" + " mdp = ?,"
      + "nom = ?," + "prenom = ?," + "departement = ?," + "date_naissance = ?," + "adresse = ?,"
      + "telephone = ?," + "email = ?," + "nationalite = ?," + "titre = ?," + "sexe = ?,"
      + "nbr_annee_ens_sup = ?," + "titulaire_compte = ?," + "num_compte = ?," + "nom_banque = ?,"
      + "code_bic = ?," + "type_user = ?, num_version = ? WHERE num_utilisateur = ?";

  public static final String RECHERCHERUSER = "SELECT " + SELECTCOLONNESUSER
      + "FROM projet.utilisateurs WHERE nom LIKE ? OR prenom  LIKE ?";

  public static final String LIREALLUSER =
      "SELECT " + SELECTCOLONNESUSER + " FROM projet.utilisateurs ";

  public static final String CHECKNOUSER =
      "SELECT utilisateurs.pseudo FROM projet.utilisateurs LIMIT 1";

  public static final String CHECKPSEUDOUNIQUE =
      "SELECT u.pseudo FROM projet.utilisateurs u WHERE u.pseudo = ?";

  public static final String CHANGEUSERSRIGHTS = "UPDATE projet.utilisateurs SET type_user = ? ,"
      + " num_version = ? WHERE num_utilisateur = ?";

  public static final String MODIFIERDRAPEAUSUPPRIMEPARTENAIRE =
      "UPDATE projet.partenaires SET supprime = ? , num_version = ? WHERE num_partenaire = ?";

  public PreparedStatement getPreparedStatement(String prepare);

}
