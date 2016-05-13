function StudentLoadPage(WhoCallMe) {

	// récupérer l'id du appelant pour montrer sa section
	$(".section").css("display", "none");
	$("#partenaireModal").find('#partDepartement').prop("disabled", false);

	switch (WhoCallMe) {

	case "sectionProfil":
		$("#sectionProfilSection").css("display", "block");
		getAllTitre();
		getSexe();
		getProfil(linkStudent, 'profil-edit');
		break;

	case "creationDemande":
		$("#programme").prop("disabled", false);
		$("#pays").prop("disabled", false);
		$("#partenaire").prop("disabled", false);
		$('#CreationPartenaire')[0].reset();
		$(".creationDemande").css("display", "block");
		$('#boutonCreationPartenaire').prop("disabled", false);
		$("#anneeAcademique").prop("disabled", false);
		getTypeEntreprise();
		getTypeStage();
		getProgramme();
		getAllDepartement();
		getAllPays();
		getAllPartenairesAgree2();
		IntervalDate();

		$("#partenaireModal :input").prop("disabled", false);
		$("#partenaireModal select").prop("disabled", false);
		$(".page-header:visible").text(
				"Enregistrement d'une demande de mobilité");
		break;

	case "demandes":
		$("#partenaireModal modal-title").text("Création d'un partenaire");
		$("#headerTableau1").text("Mes demandes de mobilités");
		TableDemandeMobiliteStudent();
		$("#tableau1").css("display", "block");
		break;

	case "mobConfirmes":
		getAnnulationGenerique();
		$("#headerTableau1").text("Mes mobilités confirmées");
		TableMobiliteStudent();
		$("#tableau1").css("display", "block");
		break;

	case "partenaires":
		getTypeEntreprise();
		getTypeStage();
		getProgramme();
		getAllDepartement();
		getAllPays();
		$("#headerTableau1").text("Les partenaires");
		TablePartenaires();
		$("#tableau1").css("display", "block");
		break;

	case "infoDemande":
		getTypeStage();
		getAllPays();
		getProgramme();
		$(".infoDemandeSection").css("display", "block");
		break;

	default:
		$(".accueilStud").css("display", "block");
		break;
	}

};

function TeacherLoadPage(WhoCallMe) {

	$(".section").css("display", "none");
	$("#SelectSpecial").css("display", "none");
	$("#ValidSpecial").css("display", "none");
	$("#CreerPartenaireSpecial").css("display", "none");
	$("#buttonValiderDemande").css("display", "none");
	$("#boutonCreationPartenaireProf").css("display", "none");
	IntervalDate();
	
	switch (WhoCallMe) {

	case "listeDemande":
		$("#headerTableau1").text("Les demandes de mobilité");
		tableDeToutLesDemandes();
		$("#tableau1").css("display", "block");
		// $("#tableau1")
		// .prepend(
		// '<div class="col-sm-3"><button type="button" class="btn btn-info
		// btn-lg" id="buttonValiderDemande">Valider les demandes
		// sélectionnées</button></div>');
		break;

	case "Mobilites":
		getAnnulationGenerique();
		$("#headerTableau1").text("Les mobilités confirmées");
		mobiliteValidees();
		$("#tableau1").css("display", "block");
		break;

	case "MobilitesAnnulees":
		$("#headerTableau1").text("Les mobilités annulées");
		listeDesAbandons();
		$("#tableau1").css("display", "block");
		break;

	// case "EtatDocuments":
	// $("#headerTableau1").text("Etat des documents de mobilités");
	// EtatDocuments();
	// $("#tableau1").css("display", "block");
	// break;

	case "DemandesPaiement":
		$("#headerTableau1").text("Demandes de Paiement");
		listeDemandePaiement();
		$("#tableau1").css("display", "block");
		break;

	case "ConfirmationEncodage":
		$("#ValidSpecial").css("display", "block");
		$("#headerTableau1").text("Confirmations des Encodages");
		listeDesProgrammesExterne();
		$("#tableau1").css("display", "block");
		break;

	case "InfosStudent":
		getAllDepartement();
		getTypesUser();
		if ($("#messageCustom").length === 0) {
			$("#SelectSpecial")
					.prepend(
							'<p id="messageCustom">Sélectionner les users dont vous voulez changer les droits</p>');
		}
		$("#SelectSpecial").css("display", "block");
		$("#headerTableau1").text("Liste des étudiants");
		ListeUser();
		$("#tableau1").css("display", "block");
		break;

	case "InfosPartenaires":
		getTypeEntreprise();
		getTypeStage();
		getProgramme();
		getAllDepartement();
		getAllPays();
		$("#headerTableau1").text("Les partenaires");
		TablePartenaires();
		$("#tableau1").css("display", "block");
		$("#tableau1")
				.prepend(
						'<div class="col-sm-3" id="CreerPartenaireSpecial"><button type="button" class="btn btn-info btn-lg" id="boutonCreationPartenaire" data-toggle="modal" data-target="#partenaireModal">Ajouter un partenaire</button></div>');
		break;

	case "infoForAStudent":
		getAllTitre();
		getSexe();
		getProfil(linkTeacher, 'infosStudMod');
		break;

	case "valideeDemande":
		getTypeEntreprise();
		getTypeStage();
		getProgramme();
		getAllDepartement();
		getAllPays();
		IntervalDate();
		$('.valideeDemande').show();
		break;

	case "DemandesRetour":
		$("#headerTableau1").text("Les documents de Retour");
		listeDocRetour();
		$("#tableau1").css("display", "block");
		break;

	case "DemandesDepart":
		$("#headerTableau1").text("Les documents de Depart");
		listeDocDepart();
		$("#tableau1").css("display", "block");
		break;

	case "InfosPartenairesSupprimes":
		$("#headerTableau1").text("Les Partenaires Supprimés");
		TablePartenairesAnnules();
		$("#tableau1").css("display", "block");
		break;

	case "infoDemande":
		getTypeStage();
		getAllPays();
		getProgramme();
		$(".infoDemandeSection").css("display", "block");
		break;

	default:
		$(".accueilTeacher").css("display", "block");
		getAllDepartement();
		IntervalDate();
		break;
	}

};