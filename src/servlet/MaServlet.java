package servlet;


import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.eclipse.jetty.http.HttpStatus;

import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWTVerifyException;

import biz.biz.DemandeBiz;
import biz.biz.DepartementBiz;
import biz.biz.DocDepartBiz;
import biz.biz.DocRetourBiz;
import biz.biz.MobiliteBiz;
import biz.biz.PartenaireBiz;
import biz.biz.PaysBiz;
import biz.biz.UserBiz;
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
import biz.enumerate.EtatMobilite;
import biz.enumerate.Programme;
import biz.enumerate.Sexe;
import biz.enumerate.Titre;
import biz.enumerate.TypeEntreprise;
import biz.enumerate.TypeStage;
import biz.enumerate.TypeUser;
import biz.factory.DtoFactory;
import biz.ucc.AnnulationUcc;
import biz.ucc.DemandePaiementUcc;
import biz.ucc.DemandeUcc;
import biz.ucc.DocDepartUcc;
import biz.ucc.DocRetourUcc;
import biz.ucc.MobiliteUcc;
import biz.ucc.PartenaireUcc;
import biz.ucc.UserUcc;
import exception.BizException;
import exception.FatalException;

public class MaServlet extends HttpServlet {

  private String cleSecret;

  private DtoFactory dtoFact;


  private UserUcc userUcc;
  private PartenaireUcc partenaireUcc;
  private MobiliteUcc mobiliteUcc;
  private DocDepartUcc docDepartUcc;
  private DocRetourUcc docRetourUcc;
  private DemandeUcc demandeUcc;
  private DemandePaiementUcc demandePaiementUcc;
  private AnnulationUcc annulationUcc;
  private static Logger logger;

  /**
   * Constructeur.
   * 
   * @param dtoFact dtoFact
   * @param userUcc userUcc
   * @param partenaireUcc partenaireUcc
   * @param mobiliteUcc mobiliteUcc
   * @param demandeUcc demandeUcc
   * @param demandePaiementUcc demandePaiementUcc
   * @param annulationUcc annulationUcc
   */
  public MaServlet(DtoFactory dtoFact, UserUcc userUcc, PartenaireUcc partenaireUcc,
      MobiliteUcc mobiliteUcc, DemandeUcc demandeUcc, DemandePaiementUcc demandePaiementUcc,
      AnnulationUcc annulationUcc, DocDepartUcc docDepartUcc, DocRetourUcc docRetourUcc,
      String cleSecret, Logger logger) {

    this.dtoFact = dtoFact;


    this.userUcc = userUcc;
    this.partenaireUcc = partenaireUcc;
    this.mobiliteUcc = mobiliteUcc;
    this.docDepartUcc = docDepartUcc;
    this.docRetourUcc = docRetourUcc;
    this.demandeUcc = demandeUcc;
    this.demandePaiementUcc = demandePaiementUcc;
    this.annulationUcc = annulationUcc;
    this.cleSecret = cleSecret;
    MaServlet.logger = logger;
  }


  /**
   * Appels GET au serveur.
   * 
   * @param req HttpServletRequest
   * 
   * 
   */
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    resp.setCharacterEncoding("UTF-8");

    PrintWriter writter = resp.getWriter();
    final String folder = "www//partsHTML//";
    String content;
    String chemin = req.getRequestURI();

    String[] param = chemin.substring(4).split("/");

    switch (param[0]) {

      case "student":

        resp.setContentType("text/html;charset=UTF-8");
        content = new String(Files.readAllBytes(Paths.get(folder + "header.html")),
            StandardCharsets.UTF_8);
        writter.println(content);

        content = new String(Files.readAllBytes(Paths.get(folder + "navStud.html")),
            StandardCharsets.UTF_8);
        writter.println(content);
        content = new String(Files.readAllBytes(Paths.get(folder + "creationDemandeStud.html")),
            StandardCharsets.UTF_8);
        writter.println(content);
        content = new String(Files.readAllBytes(Paths.get(folder + "partenaireModale.html")),
            StandardCharsets.UTF_8);
        writter.println(content);
        content = new String(Files.readAllBytes(Paths.get(folder + "tableau1.html")),
            StandardCharsets.UTF_8);
        writter.println(content);
        content = new String(Files.readAllBytes(Paths.get(folder + "infoMobStud.html")),
            StandardCharsets.UTF_8);
        writter.println(content);
        content = new String(Files.readAllBytes(Paths.get(folder + "profilStud.html")),
            StandardCharsets.UTF_8);
        writter.println(content);
        content = new String(Files.readAllBytes(Paths.get(folder + "abandonStudentModal.html")),
            StandardCharsets.UTF_8);
        writter.println(content);
        content = new String(Files.readAllBytes(Paths.get(folder + "infoDemandeStud.html")),
            StandardCharsets.UTF_8);
        writter.println(content);
        content = new String(Files.readAllBytes(Paths.get(folder + "accueilStud.html")),
            StandardCharsets.UTF_8);
        writter.println(content);
        content = new String(Files.readAllBytes(Paths.get(folder + "footer.html")),
            StandardCharsets.UTF_8);
        writter.println(content);
        content = new String(Files.readAllBytes(Paths.get(folder + "footerStud.html")),
            StandardCharsets.UTF_8);
        writter.println(content);
        break;

      case "teacher":

        resp.setContentType("text/html;charset=UTF-8");
        content = new String(Files.readAllBytes(Paths.get(folder + "header.html")),
            StandardCharsets.UTF_8);
        writter.println(content);

        content = new String(Files.readAllBytes(Paths.get(folder + "navTeacher.html")),
            StandardCharsets.UTF_8);
        writter.println(content);
        
        // COUCOU
        content = new String(Files.readAllBytes(Paths.get(folder + "easteregg.html")),
            StandardCharsets.UTF_8);
        writter.println(content);
        
        content = new String(Files.readAllBytes(Paths.get(folder + "selectUser.html")),
            StandardCharsets.UTF_8);
        writter.println(content);
        content = new String(Files.readAllBytes(Paths.get(folder + "tableau1.html")),
            StandardCharsets.UTF_8);
        writter.println(content);
        content = new String(Files.readAllBytes(Paths.get(folder + "partenaireModale.html")),
            StandardCharsets.UTF_8);
        writter.println(content);
        content = new String(Files.readAllBytes(Paths.get(folder + "infoMobStud.html")),
            StandardCharsets.UTF_8);
        writter.println(content);
        content = new String(Files.readAllBytes(Paths.get(folder + "infoDemandeStud.html")),
            StandardCharsets.UTF_8);
        writter.println(content);
        content = new String(Files.readAllBytes(Paths.get(folder + "valideeDemande.html")),
            StandardCharsets.UTF_8);
        writter.println(content);
        content = new String(Files.readAllBytes(Paths.get(folder + "abandonStudentModal.html")),
            StandardCharsets.UTF_8);
        writter.println(content);
        content = new String(Files.readAllBytes(Paths.get(folder + "infoProfilStud.html")),
            StandardCharsets.UTF_8);
        writter.println(content);
        content = new String(Files.readAllBytes(Paths.get(folder + "boutonValiderEncodage.html")),
            StandardCharsets.UTF_8);
        writter.println(content);
        content = new String(Files.readAllBytes(Paths.get(folder + "accueilTeacher.html")),
            StandardCharsets.UTF_8);
        writter.println(content);
        content = new String(Files.readAllBytes(Paths.get(folder + "abandonStudentModal.html")),
            StandardCharsets.UTF_8);
        writter.println(content);
        content = new String(Files.readAllBytes(Paths.get(folder + "footer.html")),
            StandardCharsets.UTF_8);
        writter.println(content);
        content = new String(Files.readAllBytes(Paths.get(folder + "footerTeacher.html")),
            StandardCharsets.UTF_8);
        writter.println(content);
        break;

      case "genererCsv":

        if (param.length != 2) {
          break;
        }

        // exemple : DemandeMobilite_BIN_BIM.csv

        Pattern pattern = Pattern.compile("(_.{3})");
        Matcher matcher = pattern.matcher(param[1]);
        HashMap<String, String> lesDepartementsvoulus = new HashMap<String, String>();

        while (matcher.find()) {
          String group = matcher.group().substring(1);
          lesDepartementsvoulus.put(group, group);
        }

        if (param[1].matches("DemandeMobilite(_.*){1,5}.csv")) {

          resp.setHeader("Content-Type", "text/csv");
          resp.setHeader("Content-Disposition", "attachment; filename=\"" + param[1] + "\"");
          PrintWriter writer = resp.getWriter();
          ArrayList<DemandeDto> demandes = tableDeToutLesDemandes(req);

          Predicate<DemandeDto> pre = a -> a.getAnneeAcademique().equals(Year.now().toString() + "-"
              + String.valueOf((Integer.parseInt(Year.now().toString()) + 1)));
          Predicate<DemandeDto> pre2 =
              a -> lesDepartementsvoulus.containsKey(a.getEtudiant().getDepartement().getId());

          List<DemandeDto> demandeList =
              demandes.stream().filter(pre).filter(pre2).collect(toList());
          demandes = new ArrayList<>(demandeList);
          genererDemandeCsv(param[1], demandes, writer);

        }

        break;

      case "genererCsv2":

        String nomfichier = null;

        resp.setHeader("Content-Type", "text/csv");

        ArrayList<MobiliteDto> mobis = mobiliteUcc.listerTouteMobiliteAvecDemandePaiement();
        Predicate<MobiliteDto> pre;

        if (param.length != 2) {
          pre = a -> a.getAnneeAcademique().equals(Year.now().toString() + "-"
              + String.valueOf((Integer.parseInt(Year.now().toString()) + 1)));
          nomfichier = "Mobilites" + Year.now().toString() + "-"
              + String.valueOf((Integer.parseInt(Year.now().toString()) + 1)) + ".csv";
        } else {
          pre = a -> a.getAnneeAcademique().equals(param[1]);
          nomfichier = "Mobilites" + param[1] + ".csv";
        }
        resp.setHeader("Content-Disposition", "attachment; filename=\"" + nomfichier + "\"");

        mobis = new ArrayList<MobiliteDto>(mobis.stream().filter(pre).collect(toList()));

        ArrayList docs = new ArrayList<>();
        for (MobiliteDto mobi : mobis) {
          DocDepartDto depart = docDepartUcc.lireDocDepartMobilite(mobi);
          docs.add(depart);
          DocRetourDto retour = docRetourUcc.lireDocRetourMobilite(mobi);
          docs.add(retour);
        }

        // prépare filtrer

        PrintWriter writer = resp.getWriter();
        genererDocCsv(docs, writer);

        break;

      default:
        content =
            new String(Files.readAllBytes(Paths.get(folder + "home.html")), StandardCharsets.UTF_8);
        writter.println(content);
        content = new String(Files.readAllBytes(Paths.get(folder + "footer.html")),
            StandardCharsets.UTF_8);
        writter.println(content);
        break;
    }
    writter.flush();
    writter.close();
    resp.setStatus(HttpStatus.OK_200);

  }


  /**
   * Appels POST au serveur.
   * 
   * @param req HttpServletRequest
   * 
   */
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String chemin = req.getRequestURI();
    String page = "";
    Object result = null;
    String[] param = chemin.substring(4).split("/");
    if (param.length == 2) {
      page = param[1];
    }
    resp.setCharacterEncoding("UTF-8");
    try {

      switch (param[0]) {
        case "util":
          switch (page) {
            case "isConnected":
              result = isConnected(req);
              break;

            case "seDeconnecter":
              Cookie cook = new Cookie("HermesAuth", "");
              cook.setMaxAge(0);
              cook.setPath("/");
              resp.addCookie(cook);
              HttpSession session = req.getSession(false);
              if (session != null) {
                session.invalidate();
              }
              result = true;
              break;

            case "connexion":
              result = seConnecter(req, resp);
              break;

            case "inscrire":
              result = inscription(req, resp);
              break;

            case "getAllTitre":
              result = getTitre(req);
              break;
            case "getSexe":
              result = getSexe(req);
              break;
            case "getTypeStage":
              result = getTypeStage(req);
              break;

            case "getTypeEntreprises":
              result = getTypeEntreprises(req);
              break;

            case "getProgramme":
              result = getProgramme(req);
              break;

            case "getAllDepartements":
              result = getDepartements(req);
              break;

            case "getAllPays":
              result = getAllPays(req);
              break;

            case "getRaisonAbandonGenerique":
              result = getRaisonAbandonGenerique(req);
              break;
            case "getAllPartenairesAgree":
              result = getAllPartenairesAgree(req);
              break;

            case "getInfoPartenaire":
              result = infoPartenaire(req);
              break;

            case "enregistrerAbandon":
              result = enregistrerAbandon(req);
              break;

            case "PartenairesList":
              result = rechercherPartenaire(req);
              break;

            case "checkPartenaire":
              result = checkPartenaire(req);
              break;

            default:
              break;
          }
          break;

        case "teacher":
          checkPermission(req, TypeUser.PROF);
          switch (page) {
            case "mobiliteConfirmees":
              result = mobilitesConfirmees(req);
              break;

            case "listeMobilitee":
              result = listeMobilitee(req);
              break;


            case "tableDeToutLesDemandes":
              ArrayList<DemandeDto> demandes = tableDeToutLesDemandes(req);
              result = Util.dataTable(demandes.size(), demandes);
              break;

            case "listeDemandePaiement":
              result = listeDemandePaiement(req);
              break;

            case "confirmerEncodage":
              result = confirmerEncodage(req);
              break;

            case "listeDesProgrammesExterne":
              result = listeMobilitee(req);
              break;
            case "enregistrerAbandon":
              result = enregistrerAbandon(req);
              break;

            case "listeDesAbandons":
              result = listeDesAbandons(req);
              break;

            case "changerTypeUser":
              result = changerTypeUser(req);
              break;

            case "getTypesUser":
              result = getTypesUser();
              break;

            case "enregistrerPartenaire":
              result = enregistrerPartenaire(req);
              break;

            case "allUsers":
              result = allUsers(req);
              break;

            case "getProfilStudent":
              UserDto user = getProfilForTeacher(req);
              String json = Util.infosDto(user);
              result = json;
              break;

            case "setProfil":
              result = setProfil(req);
              break;

            case "infoMobilitepk":
              result = infoMobilitepk(req);
              break;

            case "DocRetour":
              result = getDocRetourForAMobility(req);
              break;

            case "validerDemande":
              result = validerDemande(req);
              break;

            case "DocDepartStud":
              result = docDepartstudent(req);
              break;

            case "detailDemande":
              result = detailDemande(req);
              break;

            case "listeDocDepart":
              result = listeDocDepart(req);
              break;

            case "listeDocRetour":
              result = listeDocRetour(req);
              break;

            case "updateMobility":
              result = updateMobility(req);
              break;

            case "PartenairesSupprimesList":
              result = partenairesSupprimesList(req);
              break;

            case "UpdatePartenaireVisibility":
              result = updatePartenaireVisibility(req);
              break;

            case "infoDemandepk":
              result = infoDemandepk(req);
              break;

            default:
              break;
          }
          break;

        case "student":
          checkPermission(req, TypeUser.ETUD);
          switch (page) {
            case "DemandesMobilitesStud":
              result = demandesMobilitesStud(req);
              break;
            case "enregistrerDemande":
              result = enregistrerDemande(req);
              break;

            case "infoDemandepk":
              result = infoDemandepk(req);
              break;

            case "MobilitesStud":
              result = mobilitesStud(req);
              break;

            case "infoMobilitepk":
              result = infoMobilitepk(req);
              break;

            case "DocDepartStud":
              result = docDepartstudent(req);
              break;

            case "detailDemande":
              result = detailDemande(req);
              break;

            case "enregistrerAbandon":
              result = enregistrerAbandon(req);
              break;

            case "enregistrerPartenaire":
              result = enregistrerPartenaire(req);
              break;

            case "getProfil":
              UserDto user = getProfil(req);
              String json = Util.infosDto(user);
              result = json;
              break;

            case "setProfil":
              result = setProfil(req);
              break;

            case "DocRetour":
              result = getDocRetourForAMobility(req);
              break;

            case "UpdatePartenaireVisibility":
              result = updatePartenaireVisibility(req);
              break;

            default:
              break;
          }
          break;
        default:
          resp.setStatus(404);
          break;
      }

      if (Objects.nonNull(result)) {
        resp.setStatus(HttpStatus.OK_200);
        resp.setContentType("application/json");
        resp.getWriter().print(result);
      }
    } catch (FatalException | BizException | IOException | ServletException err) {
      resp.setStatus(HttpStatus.BAD_REQUEST_400);
      resp.getWriter().print(err.getMessage());


      LocalDateTime dateTime = LocalDateTime.now();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("_yyyy_MM_dd-HH_mm");
      String dateLog = dateTime.format(formatter);

      String pattern = "logs/LogFile" + dateLog + ".log";
      FileAppender appender = new FileAppender();

      appender = new FileAppender();
      appender.setLayout(new SimpleLayout());
      appender.setFile(pattern);
      appender.activateOptions();
      logger.addAppender(appender);


      formatter = DateTimeFormatter.ofPattern("_yyyy_MM_dd-HH_mm_ss");
      dateLog = dateTime.format(formatter);
      //

      logger.log(org.apache.log4j.Level.ERROR,
          dateTime + " : " + err.getMessage() + " " + String.valueOf(err.getStackTrace()));

    }

  }

  /**
   * Renvoie true si l'utilisateur est connecté.
   * 
   * @param req HttpServletRequest
   * @return boolean un boolean
   * @throws ServletException erreur système
   * @throws BizException erreur système
   * @throws FatalException erreur système
   */
  private String isConnected(HttpServletRequest req)
      throws FatalException, BizException, ServletException {

    HttpSession test = req.getSession(false);

    if (test == null) {

      Map<String, Object> map = getInfosCookie(req);

      if (Objects.isNull(map)) {
        return "false";
      }

      if (Objects.isNull(map.get("TypeUtilisateur"))) {
        return "false";
      }
      test = req.getSession();
      test.setAttribute("TypeUtilisateur", map.get("TypeUtilisateur"));
    }

    String type = (String) test.getAttribute("TypeUtilisateur");

    if (Objects.isNull(type)) {
      return "false";
    }

    TypeUser typeUser = TypeUser.valueOf(type);
    String urlRedirection = getRedirectedUrl(typeUser);
    return urlRedirection;
  }

  /**
   * Crée un cookie.
   * 
   * 
   * @param user UserDto d'un user connecté
   */
  private boolean createCookieAndSession(HttpServletResponse resp, UserDto user) {

    HashMap<String, Object> infosCookie = new HashMap<String, Object>();
    infosCookie.put("Utilisateur", user.getPrenom() + " " + user.getNom());
    infosCookie.put("TypeUtilisateur", user.getTypeUser());
    infosCookie.put("Pseudo", user.getPseudo());
    String departementPk = ((biz.biz.DepartementBiz) user.getDepartement()).getId();
    infosCookie.put("Departement", departementPk);
    infosCookie.put("ID", user.getId());

    String token = new JWTSigner(cleSecret).sign(infosCookie);

    Cookie cookie = new Cookie("HermesAuth", token);
    cookie.setPath("/");

    cookie.setMaxAge(60 * 60 * 24 * 365);
    resp.addCookie(cookie);
    resp.setStatus(HttpStatus.OK_200);
    return true;

  }

  /**
   * Fait la redicrection url en fonction du type de user.
   * 
   * @param type TypeUser (ETUD ou PROF)
   * @return String URL de redirection
   */
  private static String getRedirectedUrl(TypeUser type) {

    String urlRedirection;

    if (type.equals(TypeUser.PROF)) {
      urlRedirection = "PROF";
    } else {
      urlRedirection = "ETUD";
    }

    return urlRedirection;
  }

  /**
   * Methode pour vérifier les doits d'un User à faire une action.
   * 
   * @param req HttpServletRequest
   * @param typeUser L'user
   * @return boolean return true si autorisé
   * @throws ServletException non authentifié
   * @throws BizException non authentifié
   * @throws FatalException non authentifié
   */
  private boolean checkPermission(HttpServletRequest req, TypeUser typeUser)
      throws FatalException, BizException, ServletException {


    HttpSession session = req.getSession(false);

    if (session == null) {

      session = req.getSession();

      Map<String, Object> map = getInfosCookie(req);

      if (Objects.isNull(map)) {
        throw new exception.BizException("Vous n'êtes pas connecté");
      }

      if (Objects.isNull(map.get("TypeUtilisateur"))) {
        throw new exception.BizException("Vous n'êtes pas connecté");
      }

      session.setAttribute("TypeUtilisateur", map.get("TypeUtilisateur"));
    }

    String type = (String) session.getAttribute("TypeUtilisateur");

    if (!type.equals(typeUser.name())) {
      throw new exception.BizException("Vous n'êtes pas autorisé à accomplir cette action");
    }

    return true;
  }


  /**
   * Permet à l'utilisateur de s'inscrire.
   * 
   * @param req HttpServletRequest
   * 
   * @throws FatalException erreur système
   * @throws BizException erreur Biz
   */
  private boolean inscription(HttpServletRequest req, HttpServletResponse resp)
      throws FatalException, BizException {

    String json = req.getParameter("donnee");
    HashMap<String, Object> donnees = Util.jsonToHashMap(json);
    UserDto userAInscrire = dtoFact.getUserDto();
    userAInscrire.setPseudo((String) donnees.get("pseudo"));
    userAInscrire.setMdp((String) donnees.get("password1"));
    userAInscrire.setDateInscription(LocalDateTime.now());
    String departement = (String) donnees.get("departement");
    DepartementDto departementDto = dtoFact.getDepartementDto();
    ((DepartementBiz) departementDto).setPkDepartement(departement);
    userAInscrire.setDepartement(departementDto);
    userAInscrire.setNom((String) donnees.get("nom"));
    userAInscrire.setPrenom((String) donnees.get("prenom"));
    userAInscrire.setEmail((String) donnees.get("email"));
    userAInscrire.setTypeUser(TypeUser.ETUD);
    userAInscrire.setDateNaissance(null);
    userUcc.creerUtilisateur(userAInscrire);
    userAInscrire = userUcc.rechercherUserPseudo(userAInscrire);
    createCookieAndSession(resp, userAInscrire);
    HttpSession session = req.getSession();
    session.setAttribute("TypeUtilisateur", TypeUser.typeUserToString(userAInscrire.getTypeUser()));
    return true;
  }

  /**
   * Permet à l'utilisateur de se connecter.
   * 
   * @param req HttpServletRequest
   * 
   * @throws BizException erreur Biz
   */
  private boolean seConnecter(HttpServletRequest req, HttpServletResponse resp)
      throws BizException {
    String json = req.getParameter("donnee");
    HashMap<String, Object> donnees = Util.jsonToHashMap(json);
    String pseudo = (String) donnees.get("pseudo");
    String mdp = (String) donnees.get("password");
    UserDto user = dtoFact.getUserDto();
    user.setMdp(mdp);
    user.setPseudo(pseudo);
    user = userUcc.seConnecter(user);
    HttpSession session = req.getSession();
    session.setAttribute("TypeUtilisateur", TypeUser.typeUserToString(user.getTypeUser()));
    createCookieAndSession(resp, user);
    return true;
  }

  /**
   * Renvoit dans une HashMap les données du cookie (si présent).
   * 
   * @param req HttpServletRequest
   * @return HashMap contenant les informations du cookie
   * @throws FatalException cookie invalide
   * @throws BizException invalide
   * @throws ServletException invalide
   */
  private Map<String, Object> getInfosCookie(HttpServletRequest req)
      throws FatalException, BizException, ServletException {
    Cookie[] cook = req.getCookies();

    if (cook != null) {

      for (Cookie c : cook) {
        // todo
        if (c.getName().equals("HermesAuth")) {
          try {
            return new JWTVerifier(cleSecret).verify(c.getValue());
          } catch (InvalidKeyException | NoSuchAlgorithmException | IllegalStateException
              | SignatureException | IOException | JWTVerifyException err) {
            throw new ServletException(err.getMessage());
          }
        }
      }
    }
    throw new BizException("PROBLEME DE COOKIE");
  }


  // Ton code en commentaire vu que getInfosCookie existe
  /**
   * Obtenir le pseudo de l'utilisateur connecté.
   * 
   * @param req HttpServletRequest
   * 
   * @return le pseudo en String
   * @throws FatalException erreur systeme
   * @throws BizException erreur systeme
   * @throws ServletException erreur systeme
   */


  private String getPseudo(HttpServletRequest req)
      throws FatalException, BizException, ServletException {
    HashMap<String, Object> mesInfos = (HashMap<String, Object>) getInfosCookie(req);

    return (String) mesInfos.get("Pseudo");
  }


  /**
   * Obtenir tous les départements (Méthode à bannir bientot).
   * 
   * @param req HttpServletRequest
   * 
   * @throws IOException exeception
   */
  private String getDepartements(HttpServletRequest req) throws IOException {

    String json = Util.getAll(partenaireUcc.getAllDepartement());
    return json;
  }

  private String getTypesUser() {
    String json = Util.getAll(TypeUser.getAll());
    return json;
  }

  /**
   * Obtenir tous les pays.
   * 
   * @param req HttpServletRequest
   * 
   * @throws IOException exeception
   */
  private String getAllPays(HttpServletRequest req) throws IOException {
    String json = Util.getAllObject(partenaireUcc.getAllPays());
    return json;

  }

  /**
   * Obtenir tous les titres.
   * 
   * @param req HttpServletRequest
   * 
   * @throws IOException exeception
   */
  private String getTitre(HttpServletRequest req) throws IOException {
    String json = Util.getAll(Titre.getAll());
    return json;

  }

  /**
   * Obtenir tous les sexes.
   * 
   * @param req HttpServletRequest
   * 
   * @throws IOException exeception
   */
  private String getSexe(HttpServletRequest req) throws IOException {
    String json = Util.getAll(Sexe.getAll());
    return json;

  }

  /**
   * Obtenir tous les types d'entreprise.
   * 
   * @param req HttpServletRequest
   * 
   * @throws IOException exeception
   */
  private String getTypeEntreprises(HttpServletRequest req) throws IOException {
    String json = Util.getAll(TypeEntreprise.getAll());
    return json;

  }

  /**
   * Obtenir tous les programmes.
   * 
   * @param req HttpServletRequest
   * 
   * @throws IOException exeception
   */
  private String getProgramme(HttpServletRequest req) throws IOException {
    String json = Util.getAll(Programme.getAll());
    return json;

  }

  /**
   * Obtenir tous les types de stage.
   * 
   * @param req HttpServletRequest
   * 
   * @throws IOException exeception
   */
  private String getTypeStage(HttpServletRequest req) throws IOException {
    String json = Util.getAll(TypeStage.getAll());
    return json;

  }

  // Partenaire
  /**
   * Enregistrer un nouveau partenaire.
   * 
   * @param req req
   * @param resp resp
   * @throws FatalException erreur systeme
   * @throws BizException erreur systeme
   * @throws ServletException erreur systeme
   * @throws IOException erreur systeme
   */
  private String enregistrerPartenaire(HttpServletRequest req)
      throws FatalException, BizException, ServletException, IOException {
    String json = req.getParameter("donnee");
    HashMap<String, Object> donnees = Util.jsonToHashMap(json);
    PartenaireDto partenaire = dtoFact.getPartenaireDto();
    Map<String, Object> infosCookie = getInfosCookie(req);
    partenaire.setNomLegal((String) donnees.get("nomLegal"));
    partenaire.setNomAffaire((String) donnees.get("nomAffaire"));
    partenaire.setNomComplet((String) donnees.get("nomComplet"));
    String departement = req.getParameter("departements");

    HttpSession session = req.getSession();
    String type = (String) session.getAttribute("TypeUtilisateur");
    TypeUser typeUser = TypeUser.stringToTypeUser(type);
    boolean agree = false;
    if (TypeUser.PROF.equals(typeUser)) {
      agree = true;
    } else {
      agree = false;
    }
    partenaire.setAgree(agree);

    if (TypeUser.PROF.equals(typeUser) && departement != null) {
      departement = departement.replaceAll("\\[", "").replaceAll("\\]", "");
      departement = departement.replaceAll("\"", "");
      String[] lesDepartements = departement.split(",");

      for (String test : lesDepartements) {
        DepartementDto departementDto = dtoFact.getDepartementDto();
        ((DepartementBiz) departementDto).setPkDepartement(test);
        partenaire.setDepartement(departementDto);
      }
    } else {
      departement = (String) infosCookie.get("Departement");
      DepartementDto departementDto = dtoFact.getDepartementDto();
      ((DepartementBiz) departementDto).setPkDepartement(departement);
      partenaire.setDepartement(departementDto);
    }
    String typeEntreprise = (String) donnees.get("typeOrganisation");
    partenaire.setTypeEntreprise(TypeEntreprise.stringToTypeEntreprise(typeEntreprise));
    partenaire.setNbrEmploye(Integer.parseInt((String) donnees.get("nombreEmp")));
    partenaire.setAdresse((String) donnees.get("adressePartenaire"));
    String pays = (String) donnees.get("paysPartenaire");
    PaysDto paysDto = dtoFact.getPaysDto();
    ((PaysBiz) paysDto).setPkPays(pays);

    partenaire.setPays(paysDto);
    partenaire.setRegion((String) donnees.get("regionPartenaire"));
    partenaire.setCodePostal((String) donnees.get("codePostalPartenaire"));
    partenaire.setVille((String) donnees.get("villePartenaire"));
    partenaire.setEmail((String) donnees.get("emailPartenaire"));
    partenaire.setSiteWeb((String) donnees.get("siteWebPartenaire"));
    partenaire.setTelephone((String) donnees.get("telPartenaire"));
    UserDto user = dtoFact.getUserDto();
    int pkUser = Integer.parseInt(infosCookie.get("ID").toString());
    ((UserBiz) user).setPkUser(pkUser);
    partenaire.setCreateur(user);
    TypeStage typeStage = TypeStage.stringToTypeStage((String) donnees.get("typeStage"));
    partenaire.setTypeStage(typeStage);

    partenaire.setAgree(agree);
    ((PartenaireBiz) partenaire).setNumVersion(1);
    partenaire.setSupprime(false);

    partenaire = partenaireUcc.creePartenaire(partenaire);
    String toReturn = Util.infosDto(partenaire);
    return toReturn;


  }

  /**
   * Recherche les informations d'un partenaire.
   * 
   * @param req req
   * @param resp resp
   * @throws FatalException erreur systeme
   * @throws BizException erreur systeme
   * @throws ServletException erreur systeme
   * @throws IOException erreur systeme
   */
  private String infoPartenaire(HttpServletRequest req)
      throws FatalException, BizException, ServletException, IOException {

    String pkString = req.getParameter("pkPart");
    Integer pk = Integer.valueOf(pkString);
    PartenaireDto partenaire = dtoFact.getPartenaireDto();
    ((PartenaireBiz) partenaire).setPkPartenaire(pk.intValue());
    partenaire = partenaireUcc.rechercherPartenairePk(partenaire);
    String json = Util.infosDto(partenaire);
    return json;

  }

  /**
   * Obtenir tous les nom des partenaires valides.
   * 
   * @param req HttpServletRequest
   * 
   * @throws IOException exeception
   * @throws ServletException execption
   * @throws BizException execption
   * @throws FatalException execption
   */
  private String getAllPartenairesAgree(HttpServletRequest req)
      throws IOException, FatalException, BizException, ServletException {
    HashMap<String, Object> mesInfos = (HashMap<String, Object>) getInfosCookie(req);
    DepartementDto departement = dtoFact.getDepartementDto();

    String pk = Objects.nonNull(req.getParameter("pkDep")) ? req.getParameter("pkDep")
        : (String) mesInfos.get("Departement");
    ((DepartementBiz) departement).setPkDepartement(pk);
    // ((DepartementBiz) departement).setPkDepartement((String) mesInfos.get("Departement"));
    // TDOO CEDRIC
    HashMap<String, PartenaireDto> test =
        partenaireUcc.rechercherPartenaireAgreeParDepartement(departement);

    String json = Util.getAllPartenairesAgree(test);
    return json;
  }

  /**
   * Effectue la recherche de partenaire demandée par la dataTable.
   * 
   * @param req HttpServletRequest
   * 
   * @throws IOException erreur systeme
   */
  private String rechercherPartenaire(HttpServletRequest req) throws IOException {



    ArrayList<PartenaireDto> partenaireResult = partenaireUcc.rechercherPartenaire("");
    int recordsTotal = partenaireResult.size();

    String json = Util.dataTable(recordsTotal, partenaireResult);
    return json;

  }


  // user

  /**
   * Obtenir les informations propres à un utilisateur.
   * 
   * @param req HttpServletRequest
   * 
   * @throws FatalException user invalide
   * @throws BizException user invalide
   * @throws ServletException erreur systeme
   * @throws IOException erreur systeme
   */

  private UserDto getProfil(HttpServletRequest req)
      throws FatalException, BizException, ServletException, IOException {

    String pseudo = getPseudo(req);

    UserDto user = dtoFact.getUserDto();
    user.setPseudo(pseudo);
    user = userUcc.rechercherUserPseudo(user);

    return user;

  }

  /**
   * Setter les informations propres à un utilisateur.
   * 
   * @param req HttpServletRequest
   * 
   * @throws FatalException user invalide
   * @throws BizException user invalide
   * @throws ServletException user invalide
   * @throws IOException erreur systeme
   */
  private boolean setProfil(HttpServletRequest req)
      throws FatalException, BizException, ServletException, IOException {

    UserDto user = setteurProfil(req);
    userUcc.modifierUser(user);
    return true;
  }

  /**
   * Obtenir la liste des users.
   * 
   * @param req HttpServletRequest
   * 
   * @throws FatalException erreur systeme
   * @throws BizException erreur systeme
   * @throws ServletException erreur systeme
   * @throws IOException erreur systeme
   */
  private String allUsers(HttpServletRequest req)
      throws FatalException, BizException, ServletException, IOException {



    ArrayList<UserDto> lesUsers = userUcc.rechercherUser("");
    int recordsTotal = lesUsers.size();

    String json = Util.dataTable(recordsTotal, lesUsers);
    return json;

  }

  /**
   * Remplir User Set Profil (pour les 2 types d'users).
   * 
   * @param req HttpServletRequest
   * @param req HttpServletRequest
   * @return UserDto rempli
   * @throws FatalException erreur
   * @throws BizException erreur
   * @throws ServletException erreur
   * @throws IOException erreur
   */
  private UserDto setteurProfil(HttpServletRequest req)
      throws FatalException, BizException, ServletException, IOException {

    String json = req.getParameter("donnee");
    HashMap<String, Object> donnees = Util.jsonToHashMap(json);
    UserDto user = dtoFact.getUserDto();

    HttpSession session = req.getSession();

    String type = (String) session.getAttribute("TypeUtilisateur");
    TypeUser typeUser = TypeUser.stringToTypeUser(type);

    if (TypeUser.PROF.equals(typeUser)) {
      user = getProfilForTeacher(req);
    } else {
      user = getProfil(req);
    }

    String titreString = (String) donnees.get("titre");
    Titre titre = Titre.stringToTitre(titreString);
    user.setTitre(titre);
    String sexeString = (String) donnees.get("sexe");
    Sexe sexe = Sexe.stringToSexe(sexeString);
    user.setSexe(sexe);
    user.setNom((String) donnees.get("nom"));
    user.setPrenom((String) donnees.get("prenom"));
    String chaineDate = (String) donnees.get("dateNaissance");
    LocalDate maDate = null;

    if (Objects.nonNull(chaineDate) && !chaineDate.trim().equals("")) {
      try {
        DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE;
        maDate = LocalDate.parse(chaineDate, dtf);
      } catch (DateTimeParseException err) {
        throw new BizException("DATE INVALIDE");
      }
    }
    user.setDateNaissance(maDate);
    user.setNationalite((String) donnees.get("nationalite"));
    user.setEmail((String) donnees.get("email"));
    String nombreString = (String) donnees.get("nbAnneeSup");
    Integer nombre = Integer.valueOf(nombreString);
    user.setnbAnneeEns(nombre.intValue());
    user.setNumCompte((String) donnees.get("CptBancaire"));
    String nomBanque = (String) donnees.get("NomBanque");
    user.setNomBanque(nomBanque);
    user.setCodeBic((String) donnees.get("CodeBic"));
    user.setTitulaireCompte((String) donnees.get("TituCpt"));
    user.setAdresse((String) donnees.get("adresse"));
    String telephone = (String) donnees.get("telephone");
    user.setTelephone(telephone);
    String mdp = (String) donnees.get("pwd");
    if (Objects.nonNull(mdp) && !mdp.isEmpty()) {
      mdp = biz.util.Cryptage.hash(mdp, biz.util.Cryptage.newSalt());
      user.setMdp(mdp);
    }
    String numversionString = (String) donnees.get("numVersion");
    Integer numVersion = Integer.valueOf(numversionString);
    ((UserBiz) user).setNumVersion(numVersion.intValue());

    return user;

  }


  /**
   * Changer le type d'un ou plusieurs user.
   * 
   * @param req HttpServletRequest
   * @return true or false
   */
  private boolean changerTypeUser(HttpServletRequest req) {

    TypeUser type = TypeUser.stringToTypeUser(req.getParameter("typeUser"));
    HashMap<String, Object> ti = Util.jsonToHashMap(req.getParameter("users"));

    UserDto[] users = new UserDto[ti.size()];
    int indice = 0;

    if (ti.size() == 0) {
      throw new BizException("Aucun user A modifier");
    }

    for (Entry<String, Object> element : ti.entrySet()) {
      UserDto dto = dtoFact.getUserDto();
      int pk = Integer.parseInt(element.getKey());
      ((UserBiz) dto).setPkUser(pk);
      int numVersion = Integer.parseInt((String) element.getValue());
      ((UserBiz) dto).setNumVersion(numVersion);
      users[indice++] = dto;
    }

    return userUcc.changePermission(users, type);
  }

  /**
   * Obtenir les informations propres à un utilisateur.
   * 
   * @param req HttpServletRequest
   * 
   * @throws FatalException user invalide
   * @throws BizException user invalide
   * @throws ServletException erreur systeme
   * @throws IOException erreur systeme
   */

  private UserDto getProfilForTeacher(HttpServletRequest req)
      throws FatalException, BizException, ServletException, IOException {

    String pseudo = req.getParameter("pseudoUser");
    if (Objects.isNull(pseudo)) {
      String json = req.getParameter("donnee");
      HashMap<String, Object> donnees = Util.jsonToHashMap(json);
      pseudo = Objects.nonNull(pseudo) ? pseudo : (String) donnees.get("pseudoUser");
    }

    UserDto user = dtoFact.getUserDto();
    user.setPseudo(pseudo);
    user = userUcc.rechercherUserPseudo(user);

    return user;

  }

  // demande
  /**
   * Enregistrer une nouvelle demande.
   * 
   * @param req req
   * @param resp resp
   * @throws FatalException erreur systeme
   * @throws BizException erreur systeme
   * @throws ServletException erreur systeme
   * @throws IOException erreur systeme
   */
  private boolean enregistrerDemande(HttpServletRequest req)
      throws FatalException, BizException, ServletException, IOException {
    String json = req.getParameter("donnee");
    HashMap<String, Object> donnees = Util.jsonToHashMap(json);
    DemandeDto demandeDto = dtoFact.getDemandeDto();
    String preferenceString = (String) donnees.get("preference");
    Integer preference = Integer.valueOf(preferenceString);
    demandeDto.setPreference(preference.intValue());
    String ville = (String) donnees.get("ville");
    demandeDto.setVille(ville);
    TypeStage stage = TypeStage.stringToTypeStage((String) donnees.get("stage"));
    demandeDto.setTypeStage(stage);
    String quadrimestreString = (String) donnees.get("quadrimestre");
    Integer quadrimestre = Integer.valueOf(quadrimestreString);
    demandeDto.setQuadri(quadrimestre.intValue());
    Programme programme = Programme.stringToProgramme(((String) donnees.get("programme")));
    demandeDto.setProgramme(programme);
    demandeDto.setTypeStage(TypeStage.stringToTypeStage((String) donnees.get("typeStage")));
    demandeDto.setDateIntroduction(LocalDateTime.now());
    String anneeAcademique = (String) donnees.get("AnneeAcademique");
    demandeDto.setAnneeAcademique(anneeAcademique);
    String valid = req.getParameter("validation");
    boolean validee = false;
    if (valid == "" || valid == null || valid.equals("false")) {
      validee = false;
    } else {
      validee = true;
    }

    String teacherFlagString = req.getParameter("teacher");
    boolean teacherFlag = false;
    if (Objects.isNull(teacherFlagString) || teacherFlagString.equals("")
        || teacherFlagString.equals("false")) {
      teacherFlag = false;
    } else {
      teacherFlag = true;
      validee = true;
    }

    demandeDto.setValidee(validee);
    String pays = Objects.nonNull(donnees.get("pays")) ? (String) donnees.get("pays") : "";

    PaysDto paysDto = dtoFact.getPaysDto();
    if (!pays.isEmpty()) {
      ((PaysBiz) paysDto).setPkPays(pays);
      demandeDto.setPays(paysDto);
    } else {
      demandeDto.setPays(null);
    }

    String partenaire =
        Objects.nonNull(donnees.get("partenaire")) ? (String) donnees.get("partenaire") : "";

    PartenaireDto partenaireDto = dtoFact.getPartenaireDto();
    if (!partenaire.isEmpty()) {
      ((PartenaireBiz) partenaireDto).setPkPartenaire(Integer.parseInt((partenaire)));
      demandeDto.setPartenaire(partenaireDto);
    } else {
      demandeDto.setPartenaire(null);
    }

    UserDto userDto = dtoFact.getUserDto();
    Map<String, Object> infosCookie = getInfosCookie(req);
    int pkUser = Integer.parseInt(infosCookie.get("ID").toString());
    ((UserBiz) userDto).setPkUser(pkUser);
    demandeDto.setEtudiant(userDto);
    demandeUcc.creerDemande(demandeDto);
    if (validee) {
      MobiliteDto mobiliteDto = dtoFact.getMobiliteDto();
      mobiliteDto.setAnneeAcademique(anneeAcademique);
      mobiliteDto.setDemande(demandeDto);
      mobiliteDto.setPartenaire(demandeDto.getPartenaire());
      mobiliteDto.setEtudiant(demandeDto.getEtudiant());
      mobiliteDto.setPays(demandeDto.getPays());
      mobiliteDto.setStage(demandeDto.getTypeStage());
      mobiliteDto.setVille(demandeDto.getVille());
      mobiliteDto.setQuadri(demandeDto.getQuadri());
      mobiliteDto.setProgramme(demandeDto.getProgramme());
      mobiliteDto.setEtat(EtatMobilite.CREEE);
      mobiliteUcc.ecrireMobilite(mobiliteDto);
    }
    return true;
  }


  /**
   * Recherche les informations d'une demande.
   * 
   * @param req req
   * @param resp resp
   * @throws FatalException erreur systeme
   * @throws BizException erreur systeme
   * @throws ServletException erreur systeme
   * @throws IOException erreur systeme
   */
  private String infoDemandepk(HttpServletRequest req)
      throws FatalException, BizException, ServletException, IOException {
    String pkDem = req.getParameter("pkDemande");
    Integer pk = Integer.valueOf(pkDem);
    DemandeDto demande = dtoFact.getDemandeDto();
    ((DemandeBiz) demande).setPkDemande(pk);
    String json = Util.infosDto(demande);
    return json;

  }

  /**
   * Obtenir la liste des demandes de mobilités d'un étudiant.
   * 
   * @param req HttpServletRequest
   * 
   * @throws ServletException erreur systeme
   * @throws BizException erreur systeme
   * @throws FatalException erreur systeme
   * @throws IOException erreur systeme
   */
  private String demandesMobilitesStud(HttpServletRequest req)
      throws FatalException, BizException, ServletException, IOException {



    UserDto user = dtoFact.getUserDto();
    int pkUser = (Integer) getInfosCookie(req).get("ID");
    ((UserBiz) user).setPkUser(pkUser);
    ArrayList<DemandeDto> demandesResult = demandeUcc.rechercherDemandes(user);
    int recordsTotal = demandesResult.size();

    String json = Util.dataTable(recordsTotal, demandesResult);
    return json;

  }

  /**
   * Obtenir la liste de toutes les demandes.
   * 
   * @param req HttpServletRequest
   * 
   * @throws FatalException erreur systeme
   * @throws BizException erreur systeme
   * @throws ServletException erreur systeme
   * @throws IOException erreur systeme
   */
  private ArrayList<DemandeDto> tableDeToutLesDemandes(HttpServletRequest req)
      throws FatalException, BizException, ServletException, IOException {


    ArrayList<DemandeDto> listeDesDemande = demandeUcc.listerToutesDemandes();
    return listeDesDemande;

  }

  /**
   * Fournit les infos sur une demande de Mobilité.
   * 
   * @param req HttpServletRequest
   * @return Les infos de la demande en JSON
   */
  private String detailDemande(HttpServletRequest req) {
    int pk = Integer.parseInt(req.getParameter("pkDemande"));
    DemandeDto demande = dtoFact.getDemandeDto();
    ((DemandeBiz) demande).setPkDemande(pk);
    demande = demandeUcc.infoDemande(demande);
    return Util.infosDto(demande);
  }

  // mobilite

  /**
   * Recherche les informations d'une mobilite.
   * 
   * @param req req
   * @param resp resp
   * @throws FatalException erreur systeme
   * @throws BizException erreur systeme
   * @throws ServletException erreur systeme
   * @throws IOException erreur systeme
   */
  private String infoMobilitepk(HttpServletRequest req)
      throws FatalException, BizException, ServletException, IOException {
    String pkDem = req.getParameter("pkMobilite");
    Integer pk = Integer.valueOf(pkDem);
    MobiliteDto mobilite = dtoFact.getMobiliteDto();
    ((MobiliteBiz) mobilite).setPkMobilite(pk);
    mobilite = mobiliteUcc.rechercherMobilitePk(mobilite);
    String json = Util.infosDto(mobilite);
    return json;

  }



  /**
   * Obtenir la liste des mobilités d'un étudiant.
   * 
   * @param req HttpServletRequest
   * 
   * @throws FatalException erreur systeme
   * @throws BizException erreur systeme
   * @throws ServletException erreur systeme
   * @throws IOException erreur systeme
   */
  private String mobilitesStud(HttpServletRequest req)
      throws FatalException, BizException, ServletException, IOException {



    UserDto user = dtoFact.getUserDto();
    int pkUser = (Integer) getInfosCookie(req).get("ID");
    ((UserBiz) user).setPkUser(pkUser);
    ArrayList<MobiliteDto> demandesResult = mobiliteUcc.rechercherMobilitesStud(user);
    int recordsTotal = demandesResult.size();

    String json = Util.dataTable(recordsTotal, demandesResult);
    return json;

  }

  /**
   * Obtenir la liste de toutes les mobilites.
   * 
   * @param req HttpServletRequest
   * 
   * @throws IOException erreur systeme
   * @throws ServletException erreur systeme
   * @throws BizException erreur systeme
   * @throws FatalException erreur systeme
   */
  private String mobilitesConfirmees(HttpServletRequest req)
      throws IOException, FatalException, BizException, ServletException {


    ArrayList<MobiliteDto> lesMobilites = mobiliteUcc.listerToutesMobilite();
    List<MobiliteDto> mobSansannulations =
        lesMobilites.stream().filter(a -> a.getAnnulation().getId() == 0).collect(toList());
    lesMobilites = new ArrayList<>(mobSansannulations);
    int recordsTotal = lesMobilites.size();

    String json = Util.dataTable(recordsTotal, lesMobilites);
    return json;

  }

  /**
   * Obtenir la liste de touts les Mobilites.
   * 
   * @param req HttpServletRequest
   * 
   * @throws IOException erreur systeme
   */
  private String listeMobilitee(HttpServletRequest req) throws IOException {


    ArrayList<MobiliteDto> listMoblilite = mobiliteUcc.listerToutesMobilite();
    int recordsTotal = listMoblilite.size();

    String json = Util.dataTable(recordsTotal, listMoblilite);
    return json;

  }

  // programme externe
  /**
   * Confirmation de l'encodage d'une ou de plusieurs mobilités dans prog externes.
   * 
   * @param req HttpServletRequest
   * @return true si effectué
   */
  private boolean confirmerEncodage(HttpServletRequest req) {
    HashMap<String, Object> te = Util.jsonToHashMap(req.getParameter("encodage"));
    if (te.size() == 0) {
      throw new BizException("Aucun Encodage à confirmer");
    }

    for (Entry<String, Object> element : te.entrySet()) {
      int pk = Integer.parseInt(element.getKey());
      MobiliteDto dto = dtoFact.getMobiliteDto();
      ((MobiliteBiz) dto).setPkMobilite(pk);
      dto = mobiliteUcc.rechercherMobilitePk(dto);
      HashMap<String, Object> booleans = Util.jsonToHashMap(element.getValue().toString());

      dto.setProEco((boolean) booleans.get("proEco"));
      dto.setMobi((boolean) booleans.get("mobi"));
      dto.setMobilityTool((boolean) booleans.get("mobilityTool"));
      int numVersion = Integer.parseInt((String) booleans.get("numVersion"));
      ((MobiliteBiz) dto).setNumVersion(numVersion);
      mobiliteUcc.modifierMobilite(dto);
    }

    return true;
  }

  // doc depart

  /**
   * Obtenir la liste de touts les DocDepart.
   * 
   * @param req HttpServletRequest
   * 
   * @throws IOException erreur systeme
   */
  private String listeDocDepart(HttpServletRequest req) throws IOException {

    ArrayList<DocDepartDto> listDocdepart = docDepartUcc.listerTousDocDepart();
    listDocdepart = new ArrayList<>(listDocdepart.stream()
        .filter(a -> a.getMobilite().getAnnulation().getId() == 0).collect(toList()));
    int recordsTotal = listDocdepart.size();

    String json = Util.dataTable(recordsTotal, listDocdepart);
    return json;

  }

  /**
   * Fournir le document de départ propre à la mobilité d'un étudiant.
   * 
   * @param req HttpServletRequest
   * @return infos en JSON
   */
  private String docDepartstudent(HttpServletRequest req) {
    Integer pk = Integer.parseInt(req.getParameter("identifiant"));
    MobiliteDto dto = dtoFact.getMobiliteDto();
    ((MobiliteBiz) dto).setPkMobilite(pk.intValue());
    DocDepartDto dto2 = docDepartUcc.lireDocDepartMobilite(dto);
    return Util.infosDto(dto2);
  }


  // doc retour


  // demande payement

  /**
   * Obtenir la liste de toutes les DemandePaiement.
   * 
   * @param req HttpServletRequest
   * 
   * @throws IOException erreur systeme
   */
  private String listeDemandePaiement(HttpServletRequest req) throws IOException {

    ArrayList<MobiliteDto> listDemandepaiement =
        mobiliteUcc.listerTouteMobiliteAvecDemandePaiement();
    int recordsTotal = listDemandepaiement.size();
    String json = Util.dataTable(recordsTotal, listDemandepaiement);
    return json;

  }


  /**
   * Enregistrer un abandon.
   * 
   * @param req HttpServletRequest
   * 
   * @return {@code String } string
   */
  private String enregistrerAbandon(HttpServletRequest req) {
    String json = req.getParameter("donnee");
    HashMap<String, Object> donnees = Util.jsonToHashMap(json);
    AnnulationDto annulationDto = dtoFact.getAnnulationDto();
    annulationDto.setDescription((String) donnees.get("descriptionAnnulation"));
    HttpSession sess = req.getSession(false);
    TypeUser type = TypeUser.stringToTypeUser((String) sess.getAttribute("TypeUtilisateur"));
    if (type.equals(TypeUser.PROF)) {
      annulationDto.setGenerique(true);
    } else {
      annulationDto.setGenerique(false);
    }
    String pkMobbilite = (String) donnees.get("mobAnnul");
    Integer pk = Integer.valueOf(pkMobbilite);
    MobiliteDto mobiDto = dtoFact.getMobiliteDto();
    ((MobiliteBiz) mobiDto).setPkMobilite(pk);
    ((MobiliteBiz) mobiDto).setNumVersion(Integer.parseInt((String) donnees.get("numVersion")));
    mobiliteUcc.annulerMobilite(mobiDto, annulationDto);
    return json;
  }


  /**
   * Retourne la liste des Abandons.
   * 
   * @param req HttpServletRequest
   * @return les données en JSON
   */
  private String listeDesAbandons(HttpServletRequest req) {
    ArrayList<MobiliteDto> donnees = mobiliteUcc.listerToutesMobiliteAnnulees();
    List<MobiliteDto> te =
        donnees.parallelStream().filter(a -> a.getAnnulation().getId() != 0).collect(toList());
    donnees = new ArrayList<>(te);
    return Util.dataTable(donnees.size(), donnees);
  }

  /**
   * Renvoit la liste des raisons d'annulation générique.
   * 
   * @param req HttpServletRequest
   **/
  private Object getRaisonAbandonGenerique(HttpServletRequest req) {
    ArrayList<AnnulationDto> listeAbandon = annulationUcc.listerAnnulationsGenerique();
    String json = Util.infosDto(listeAbandon);
    return json;
  }


  /**
   * Générer le fichier CSV des demandes de mobilités.
   * 
   * @param filename nom du fichier
   * @param listDemande les demandes
   * @param fw PrintWriter
   */
  private void genererDemandeCsv(String filename, ArrayList<DemandeDto> listDemande,
      PrintWriter fw) {

    fw.append("Nom");
    fw.append(';');
    fw.append("Prénom");
    fw.append(';');
    fw.append("Département");
    fw.append(';');
    fw.append("N° ordre de préférence");
    fw.append(';');
    fw.append("Type mobilité");
    fw.append(';');
    fw.append("Stage/académique");
    fw.append(';');
    fw.append("Quadrimestre");
    fw.append(';');
    fw.append("Partenaire");

    fw.append('\n');

    for (DemandeDto demandeDto : listDemande) {

      List<String> dem = new ArrayList<>();
      dem.add(demandeDto.getEtudiant().getNom());
      dem.add(demandeDto.getEtudiant().getPrenom());
      dem.add(demandeDto.getEtudiant().getDepartement().getNom());
      dem.add(demandeDto.getPreference() + "");
      dem.add(demandeDto.getProgramme().getType());
      dem.add(demandeDto.getTypeStage().getTypeStage());
      dem.add(demandeDto.getQuadri() + "");
      dem.add(demandeDto.getPartenaire().getNomComplet());
      dem.add("\n");
      String teste = dem.stream().reduce((ta, uc) -> ta + ";" + uc).get();
      fw.append(teste);


    }

  }

  private void genererDocCsv(ArrayList docs, PrintWriter fw) {
    fw.append("Prenom");
    fw.append(';');
    fw.append("Nom");
    fw.append(';');
    fw.append("Departement");
    fw.append(';');
    fw.append("Programme");
    fw.append(';');
    fw.append("Type de stage");
    fw.append(';');
    fw.append("Ville");
    fw.append(';');
    fw.append("Mobi");
    fw.append(';');
    fw.append("MobilityTool");
    fw.append(';');
    fw.append("ProEco");
    fw.append(';');

    fw.append("Date de Depart");
    fw.append(';');
    fw.append("Contrat Bourse");
    fw.append(';');
    fw.append("Charte Etudiant");
    fw.append(';');
    fw.append("Convention Stage ou Etude");
    fw.append(';');
    fw.append("Preuve Test Langue Depart");
    fw.append(';');
    fw.append("Document Engagement");
    fw.append(';');

    fw.append("Date Retour");
    fw.append(';');
    fw.append("Attestation Sejour");
    fw.append(';');
    fw.append("Releve de Note ou Certifiact de Stage");
    fw.append(';');
    fw.append("Preuve Test Langue Retour");
    fw.append(';');
    fw.append("Rapport Final");
    fw.append('\n');
    boolean depart = true;
    for (Object docMobi : docs) {
      if (depart) {
        DocDepartDto doc = (DocDepartDto) docMobi;

        String dateDepart =
            Objects.isNull(doc.getDateDepart()) ? "" : doc.getDateDepart().toString();
        UserDto etudiant = doc.getEtudiant();
        MobiliteDto mobilite = doc.getMobilite();
        fw.append(etudiant.getPrenom());
        fw.append(';');
        fw.append(etudiant.getNom());
        fw.append(';');
        fw.append(etudiant.getDepartement().getNom());
        fw.append(';');
        fw.append(mobilite.getPays().getTypeMobilite());
        fw.append(';');
        fw.append(TypeStage.typeStageToString(mobilite.getStage()));
        fw.append(';');
        fw.append(mobilite.getVille());
        fw.append(';');
        fw.append(mobilite.getMobi() ? "X" : "");
        fw.append(';');
        fw.append(mobilite.getMobilityTool() ? "X" : "");
        fw.append(';');
        fw.append(mobilite.getProEco() ? "X" : "");
        fw.append(';');


        fw.append(dateDepart);
        fw.append(';');
        fw.append(doc.getContratBourse() ? "X" : "");
        fw.append(';');
        fw.append(doc.getCharteEtudiant() ? "X" : "");
        fw.append(';');
        fw.append(doc.getConventionStageOuEtude() ? "X" : "");
        fw.append(';');
        fw.append(doc.getPreuveTestLangue() ? "X" : "");
        fw.append(';');
        fw.append(doc.getDocEngagement() ? "X" : "");
        fw.append(';');


      } else {
        DocRetourDto doc = (DocRetourDto) docMobi;

        String dateRetour =
            Objects.isNull(doc.getDateRetour()) ? "" : doc.getDateRetour().toString();


        fw.append(dateRetour);
        fw.append(';');
        fw.append(doc.getAttestationSejour() ? "X" : "");
        fw.append(';');
        fw.append(doc.getReleveNoteOuCertifStage() ? "X" : "");
        fw.append(';');
        fw.append(doc.getPreuvePassageTest() ? "X" : "");
        fw.append(';');
        fw.append(doc.getRapportFinal() ? "X" : "");
        fw.append(';');
        fw.append('\n');

      }
      depart = !depart;
    }

  }

  /**
   * Renvoit le docRetour pour la mobilité.
   * 
   * @param req HttpServletRequest
   * @return json dtoDepart
   */
  private String getDocRetourForAMobility(HttpServletRequest req) {
    MobiliteDto mobiliteDto = dtoFact.getMobiliteDto();
    ((MobiliteBiz) mobiliteDto).setPkMobilite(Integer.parseInt(req.getParameter("identifiant")));
    DocRetourDto dto = docRetourUcc.lireDocRetourMobilite(mobiliteDto);
    return Util.infosDto(dto);
  }

  /**
   * Change l'état de la mobilité selon les cas.
   * 
   * @param req HttpServletRequest
   * @return json
   * @throws ServletException erreur
   * @throws BizException erreur
   * @throws FatalException erreur
   */
  private String updateMobility(HttpServletRequest req)
      throws FatalException, BizException, ServletException {

    HashMap<String, Object> infos = Util.jsonToHashMap(req.getParameter("infos"));
    String typeModification = req.getParameter("action");

    switch (typeModification) {


      // modification de doc Depart
      case "updateDocDepart":

        // remplissage du DTO
        DocDepartDto docDepartDto = dtoFact.getDocDepartDto();
        ((DocDepartBiz) docDepartDto).setPkDocDepart(Integer.parseInt(infos.get("id").toString()));
        docDepartDto = docDepartUcc.lireDocDepartPk(docDepartDto);
        ((DocDepartBiz) docDepartDto)
            .setNumVersion(Integer.parseInt(((String) infos.get("numVersion"))));
        docDepartDto.setCharteEtudiant(Boolean.valueOf(infos.get("charteEtudiant").toString()));
        docDepartDto.setContratBourse(Boolean.valueOf(infos.get("contratBourse").toString()));
        docDepartDto.setConventionStageOuEtude(
            Boolean.valueOf(infos.get("conventionStageOuEtude").toString()));
        docDepartDto.setDocEngagement(Boolean.valueOf(infos.get("docEngagement").toString()));
        docDepartDto.setPreuveTestLangue(Boolean.valueOf(infos.get("preuveTestLangue").toString()));

        // Appel à sa fonction dédiée
        docDepartUcc.modifierDocDepart(docDepartDto);
        break;

      // modification de doc Retour
      case "updateDocRetour":

        // remplissage du DTO
        DocRetourDto docRetourDto = dtoFact.getDocRetourDto();
        ((DocRetourBiz) docRetourDto).setPkDocRetour(Integer.parseInt(infos.get("id").toString()));
        docRetourDto = docRetourUcc.lireDocRetourPk(docRetourDto);
        ((DocRetourBiz) docRetourDto)
            .setNumVersion(Integer.parseInt(((String) infos.get("numVersion"))));
        docRetourDto
            .setAttestationSejour(Boolean.valueOf(infos.get("attestationSejour").toString()));
        docRetourDto
            .setPreuvePassageTest(Boolean.valueOf(infos.get("preuvePassageTest").toString()));
        docRetourDto.setRapportFinal(Boolean.valueOf(infos.get("rapportFinal").toString()));
        docRetourDto.setReleveNoteOuCertifStage(
            Boolean.valueOf(infos.get("releveNoteOuCertifStage").toString()));

        // Appel à sa fonction dédiée
        docRetourUcc.modifierDocRetour(docRetourDto);
        break;

      // modification de demande Paiement
      case "updateDocPaiement":

        // Dto d'infos
        Map<String, Object> mesDonnees = getInfosCookie(req);
        UserDto professeur = dtoFact.getUserDto();
        ((UserBiz) professeur).setPseudo(mesDonnees.get("Pseudo").toString());
        professeur = userUcc.rechercherUserPseudo(professeur);
        MobiliteDto mobilite = dtoFact.getMobiliteDto();
        int pkMobilite = Integer.parseInt(infos.get("pkMobilite").toString());
        ((MobiliteBiz) mobilite).setPkMobilite(pkMobilite);
        mobilite = mobiliteUcc.rechercherMobilitePk(mobilite);

        // Dto du doc

        DemandePaiementDto demandePaiement = dtoFact.getDemandePaiementDto();
        demandePaiement.setDateExecution(LocalDateTime.now());
        demandePaiement.setProfesseur(professeur);
        demandePaiement.setEtudiant(mobilite.getEtudiant());
        demandePaiement.setMobilite(mobilite);
        demandePaiementUcc.ecrireDemandePaiement(demandePaiement);

        break;

      default:
        break;
    }


    // UPDATE DE DOC DEPART
    // docDepartUcc.modifierDocDepart(docDepartDto);

    // UPDATE DE DOC RETOUR
    // docRetourUcc.modifierDocRetour(docRetourDto);

    // UPDATE DE DOC PAIEMENT
    // demandePaiementUcc.ecrireDemandePaiement(demandePaiement);

    return "true";
  }

  /**
   * Valider Demande par un prof.
   * 
   * @param req HttpServletRequest
   * @return true si fonctionne
   * @throws ServletException erreur
   * @throws BizException erreur
   * @throws FatalException erreur
   */
  private String validerDemande(HttpServletRequest req)
      throws FatalException, BizException, ServletException {

    HashMap<String, Object> donnees = Util.jsonToHashMap(req.getParameter("donnees"));

    DemandeDto dto = dtoFact.getDemandeDto();
    int pk = Integer.parseInt(donnees.get("pkDemande").toString());
    ((DemandeBiz) dto).setPkDemande(pk);
    dto = demandeUcc.infoDemande(dto);
    int numVersion = Integer.parseInt(donnees.get("numVersion").toString());
    ((DemandeBiz) dto).setNumVersion(numVersion);

    // UPDATE demande

    dto.setAnneeAcademique(donnees.get("anneeAcademique").toString());
    dto.setTypeStage(TypeStage.stringToTypeStage(donnees.get("typeStage").toString()));
    dto.setQuadri(Integer.parseInt(donnees.get("quadrimestre").toString()));
    dto.setProgramme(Programme.stringToProgramme(donnees.get("programme").toString()));
    dto.setVille(donnees.get("ville").toString());

    if (Objects.isNull(donnees.get("preference"))) {
      throw new BizException("Pas d'ordre de préference pour cette demande");
    }
    dto.setPreference(Integer.parseInt(donnees.get("preference").toString()));
    PartenaireDto part = dtoFact.getPartenaireDto();

    if (Objects.isNull(donnees.get("partenaire"))) {
      throw new BizException("Aucun partenaire pour cette demande");
    }

    ((PartenaireBiz) part).setPkPartenaire(Integer.parseInt(donnees.get("partenaire").toString()));
    dto.setPartenaire(part);
    PaysDto pays = dtoFact.getPaysDto();

    if (Objects.isNull(donnees.get("pays"))) {
      throw new BizException("Aucun pays pour cette demande");
    }
    ((PaysBiz) pays).setPkPays(donnees.get("pays").toString());
    dto.setPays(pays);


    // demandeUcc.validerDemande(dto);

    // CREER LA MOBILITE
    /*
     * MobiliteDto mobiliteDto = dtoFact.getMobiliteDto();
     * 
     * mobiliteDto.setAnneeAcademique(dto.getAnneeAcademique()); mobiliteDto.setDemande(dto);
     * mobiliteDto.setPartenaire(dto.getPartenaire()); mobiliteDto.setEtudiant(dto.getEtudiant());
     * mobiliteDto.setVille(dto.getVille()); mobiliteDto.setEtat(EtatMobilite.CREEE);
     * mobiliteDto.setPays(dto.getPays()); mobiliteDto.setStage(dto.getTypeStage());
     * mobiliteDto.setQuadri(dto.getQuadri()); mobiliteDto.setProgramme(dto.getProgramme());
     * 
     * mobiliteUcc.ecrireMobilite(mobiliteDto);
     */
    mobiliteUcc.ecrireMobiliteParDemande(dto);

    return "true";
  }

  /**
   * Lister tous les documents de Retour.
   * 
   * @param req HttpServletRequest
   * @return json des docRetour
   */
  private String listeDocRetour(HttpServletRequest req) {

    ArrayList<DocRetourDto> listDocdepart = docRetourUcc.listerTousDocRetour();
    listDocdepart = new ArrayList<>(listDocdepart.stream()
        .filter(a -> a.getMoblite().getAnnulation().getId() == 0).collect(toList()));
    int recordsTotal = listDocdepart.size();

    String json = Util.dataTable(recordsTotal, listDocdepart);
    return json;

  }

  /**
   * Vérifie si le partenaire existe déjà dans le système.
   * 
   * @param req HttpServletRequest
   * @return le partenaire si existant
   */
  private String checkPartenaire(HttpServletRequest req) {

    PartenaireDto partenaireDto = dtoFact.getPartenaireDto();
    partenaireDto.setNomComplet(req.getParameter("partenaireAVerifier"));
    if (partenaireUcc.verifierPartenaireExistant(partenaireDto)) {
      partenaireDto = partenaireUcc.rechercherPartenairePk(partenaireDto);
    }
    return Util.infosDto(partenaireDto);
  }

  /**
   * Renvoit la liste des partenaires Supprimées.
   * 
   * @param req HttpServletRequest
   * @return JSON du resultat
   */
  private String partenairesSupprimesList(HttpServletRequest req) {

    ArrayList<PartenaireDto> partenairesSupprimes = partenaireUcc.listerPartenairessupprimes();
    return Util.dataTable(partenairesSupprimes.size(), partenairesSupprimes);
  }

  /**
   * Mettre à jour la visiblité d'un partenaire.
   * 
   * @param req HttpServletRequest
   * @return true si accompli
   */
  private String updatePartenaireVisibility(HttpServletRequest req) {
    HashMap<String, Object> infos = Util.jsonToHashMap(req.getParameter("infos"));
    PartenaireDto partenaireDto = dtoFact.getPartenaireDto();
    int pk =
        (Objects.isNull(infos.get("pkPartenaire"))) ? Integer.parseInt(infos.get("id").toString())
            : Integer.parseInt(infos.get("pkPartenaire").toString());

    ((PartenaireBiz) partenaireDto).setPkPartenaire(pk);
    ((PartenaireBiz) partenaireDto)
        .setNumVersion(Integer.parseInt(infos.get("numVersion").toString()));
    partenaireDto.setSupprime(Boolean.valueOf(infos.get("supprime").toString()));

    if (partenaireDto.getSupprime() == true && mobiliteUcc.listerToutesMobilite().stream()
        .anyMatch(a -> a.getPartenaire().getId() == partenaireDto.getId())) {
      throw new BizException("Partenaire avec au moins une mobilité ");
    }

    partenaireUcc.supprimerPartenaire(partenaireDto);
    return "true";
  }

}
