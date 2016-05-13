/**
 * 
 * appel ajax qui verife que l'ulisateur est encore connecter
 */
function isConnected() {

	$.ajax({
		url : linkUtil + 'isConnected',
		type : 'POST',
		data : {},
		dataType : "text",
		success : function(response) {
			if (response === 'PROF' && laBonneSection != 'TeacherHome') {
				window.location.href = '/v1/teacher/';
			}
			if (response === 'ETUD' && laBonneSection != 'StudentHome') {
				window.location.href = '/v1/student/';

			}
			if (response !== 'ETUD' && response !== 'PROF'
					&& laBonneSection !== 'HomeHermes') {
				window.location.href = '/';
			}
			return;
		},
		error : function(e) {
			if ( response !== 'ETUD' && response !== 'PROF'
				&& laBonneSection !== 'HomeHermes' ) {
				window.location.href = '/';
			}
		}

	});
};

function seDeconnecter() {
	$.ajax({
		url : linkUtil + 'seDeconnecter',
		type : 'POST',
		data : {},
		success : function(response) {
			window.location.href = '/';
		},
		error : function(e) {
		}

	});

};

// Générateur de colonnes pour les datatables
function ColomnsGenerator(test) {
	var colonnes;
	var selector;
	var thead = "";

	switch (test) {
	case "docDepart":
		colonnes = [ 'Contrat de bourse :',
				'Convention de stage / d\'études :', 'Charte de l\'étudiant :',
				'Document d’engagement :',
				'Preuve du passage des tests linguistiques :' ];
		break;
	case "listeDocDepart":
		colonnes = [ 'Prenom:', 'Nom:', 'Departement :', 'Mobilité',
				'Contrat de bourse :', 'Convention de stage d\'études :',
				'Charte de l\'étudiant :', 'Document d’engagement:',
				'Preuve du passage des tests linguistiques :', 'Valider' ];
		break;
	case "listeDocDepartStudent":
		colonnes = [ 'Mobilité', 'Contrat de bourse :',
				'Convention de stage d\'études :', 'Charte de l\'étudiant :',
				'Document d’engagement:',
				'Preuve du passage des tests linguistiques :', 'Valider' ];
		break;
	case "listeDocRetour":
		colonnes = [ 'Prenom:', 'Nom:', 'Departement :',
				' Attestation sejour:',
				'Releve note ou certif stage / d\'études :',
				'Preuve du passage de test linguistique :', 'Rapport final :',
				'valider' ];
		break;
	case "docRetour":
		colonnes = [ 'Attestation séjour :',
				'relevé de notes/certificat de stage :',
				'Rapport final complété en ligne :', 'Document d’engagement :',
				'Preuve du passage des tests linguistiques :' ];
		break;

	case "listeDemandePaiement":
		colonnes = [ 'Annee academique :', 'Departement :', 'Mobilité:', 
				'Prenom:', 'Nom:', 'Demande Paiment 1', 'Demande Paiment 2' ];
		break;
	case "listeDesProgrammesExterne":
		colonnes = [ 'Prenom:', 'Nom:', 'Departement :', 'Détail mobilité:',
				'ProEco:', 'Mobility Tool:', 'Mobi:', 'Valider'

		];
		break;
	case "listeDesAbandons":
		colonnes = [ 'Prenom:', 'Nom:', 'Departement :', 'Détail mobilité:',
				'Raison d\'abandons:',
				'Etat de l\'abandons avans l\'annulation:' ];
		break;
	case "listeDemande":
		colonnes = [ 'Prenom:', 'Nom:', 'Departement :', 'Validée :',
				'Priorité :', 'Type mobilité:', 'SMS/SMP', 'Détails' ];
		break;
	case "demandesStud":
		colonnes = [ 'Priorité :', 'Type mobilité:', 'pays', 'SMS/SMP',
				'détails' ];
		break;

	case "mobilitesStud":
		colonnes = [ 'Type Mobilité :', 'SMS/SMP :', 'Année académique :',
				'Etat :', 'Détails', 'Abandonner' ];
		break;
	case "listeMobilitee":
		colonnes = [ 'Prenom:', 'Nom:', 'Departement :', 'Type Mobilité :',
				'SMS/SMP :', 'Année académique :', 'Etat :', 'Détails',
				'Abandonner' ];
		break;

	case "mobilitesConfirmees":
		colonnes = [ 'Nom :', 'Prenom', 'Departement :', 'Etat :',
				'Type Mobilité :', 'SMS/SMP :', 'Pays :', 'Année académique :',
				'Quadrimestre :', 'Détails :', 'Abandonner :' ];
		break;
	case "partenaires":
		colonnes = [ 'Nom Complet :', 'Pays :', 'Ville :', 'agrée :', 'Infos' ];
		break;

	case "allUsers":
		colonnes = [ 'Sélectionner :', 'Nom :', 'Prenom :', 'Departement :',
				'Type Utilisateur :', 'Infos' ];
		break;
	default:
		colonnes = [];
		break;
	}

	$.each(colonnes, function(index, value) {
		thead += "<th>" + value + "</th>";
	});

	selector = "#tableauPersonnalise";
	$(selector + " thead tr").val(colonnes);

};

function formToJson(formulaire) {

	var tableJSON = {};
	$(formulaire)
			.find(
					'input[type=text],input[type=password],input[type=number],input[type=email],input[type=hidden],input[type="tel"]')
			.each(function(i, el) {
				tableJSON[$(el).attr('name')] = $(el).val();
			});

	$(formulaire).find('input[type=radio]:checked').each(function(i, el) {
		tableJSON[$(el).attr('name')] = $(el).val();
	});

	$(formulaire).find('input[type=checkbox]:checked').each(function(i, el) {
		tableJSON[$(el).attr('name')] = $(el).val();
	});
	$(formulaire).find('textarea').each(function(i, el) {
		tableJSON[$(el).attr('name')] = $(el).val();
	});
	$(formulaire).find('select').each(function(i, el) {
		var selected = $(el).find('option:selected');
		tableJSON[$(el).attr('name')] = $(el).val();
	});

	return tableJSON;
};

function searchInfosTables(selectTable, value) {

	var callOption;

	switch (laBonneSection) {

	case "StudentHome":
		switch (selectTable) {

		case "mob":
			callOption = "mobConfirmes";
			break;

		case "par":
			callOption = "partenaires";
			break;
		}
		StudentLoadPage(callOption);
		break;
	case "TeacherHome":
		switch (selectTable) {

		case "mob":
			callOption = "Mobilites";
			break;

		case "par":
			callOption = "InfosPartenaires";
			break;

		case "etu":
			callOption = "InfosStudent";
			break;
		}
		TeacherLoadPage(callOption);
		break;
	}
	// rechercher l'info dans la table concernée (Recherche Globale car moins
	// *** à programmer)
	table.search(value).draw();

};

function ValiderDemande(classItem, whichTableId) {
	var map = {};
	// Iterate over all selected checkboxes
	debugger;
	var test = $("#" + whichTableId).find("." + classItem + ":checked");
	test.each(function(index, val) {
		var flags = [];
		$(this).each(function() {
			$.each(this.attributes, function() {

				if (this.specified) {
					flags[this.name] = this.value;
				}

			});
		});
		map[id] = flags;
	});
	return map;
};