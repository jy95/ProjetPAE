function listeDocDepart() {
	if (table instanceof Object) {
		table.destroy();
		$("#tableauPersonnalise").empty();
	}
	ColomnsGenerator("listeDocDepart");
	table = $("#tableauPersonnalise")
			.DataTable(
					{
						language : {
							url : '/js/French.json'
						},

						"ajax" : {
							"url" : linkTeacher + "listeDocDepart",
							"type" : "POST"
						},
						"aoColumns" : [
								{
									"mData" : "etudiant.nom",
									"title" : "Nom :",
									"bSortable" : true
								},
								{
									"mData" : "etudiant.prenom",
									"title" : "Prenom :",
									"bSortable" : true
								},
								{
									"mData" : "etudiant.departement.id",
									"title" : "Departement :",
									"bSortable" : true
								},
								{
									"mData" : function(o, type) {
										return '<button type="button" class="btn btn-link infoMobStud" id="mobiliteN'
												+ o.id + '" >Infos</button>';
									},
									"title" : "Détail mobilité:"
								},
								{
									"mData" : function(o, type) {
										return '<div class="checkbox"><input name="contratBourse" type="checkbox" '
												+ (o.contratBourse ? 'checked'
														: '') + '></div>';
									},
									"title" : "Contrat de bourse :"
								},
								{
									"mData" : function(o, type) {
										return '<div class="checkbox"><input name="conventionStageOuEtude" type="checkbox" '
												+ (o.conventionStageOuEtude ? 'checked'
														: '') + '></div>';
									},
									"title" : "Convention de stage d\'études :"
								},
								{
									"mData" : function(o, type) {
										return '<div class="checkbox"><input name="charteEtudiant" type="checkbox" '
												+ (o.charteEtudiant ? 'checked'
														: '') + '></div>';
									},
									"title" : "Charte de l'étudiant :"
								},
								{
									"mData" : function(o, type) {
										return '<div class="checkbox"><input name="docEngagement" type="checkbox" '
												+ (o.docEngagement ? 'checked'
														: '') + '></div>';
									},
									"title" : "Document d’engagement :"
								},
								{
									"mData" : function(o, type) {
										return '<div class="checkbox"><input name="preuveTestLangue" type="checkbox" '
												+ (o.preuveTestLangue ? 'checked'
														: '') + '></div>';
									},
									"title" : "Preuve du passage des tests linguistiques :"
								},
								{
									"render" : function(data, type, full, meta) {

										return '<button type="button" class="btn btn-primary updateDocDepart" data-version="'
												+ full["numVersion"]
												+ '" id="MobEncodageID'
												+ full["id"]
												+ '" data-numLigne="'
												+ (meta.row + 1)
												+ '">Valider</button>';
									},
									"title" : "Valider"

								} ]
					});
};
function listeDocDepartStudent() {
	if (table instanceof Object) {
		table.destroy();
		$("#tableauPersonnalise").empty();
	}
	ColomnsGenerator("listeDocDepartStudent");
	table = $("#tableauPersonnalise")
			.DataTable(
					{
						language : {
							url : '/js/French.json'
						},

						"ajax" : {
							"url" : linkTeacher + "listeDocDepart",
							"type" : "POST"
						},
						"aoColumns" : [

								{
									"mData" : "mobilite",
									"title" : "Mobilité :",
									"bSortable" : true
								},
								{
									"mData" : function(o, type) {
										return '<div class="checkbox"><input name="contratBourse" type="checkbox" '
												+ (o.contratBourse ? 'checked'
														: '') + '></div>';
									},
									"title" : "Contrat de bourse :"
								},
								{
									"mData" : function(o, type) {
										return '<div class="checkbox"><input name="conventionStageOuEtude" type="checkbox" '
												+ (o.conventionStageOuEtude ? 'checked'
														: '') + '></div>';
									},
									"title" : "Convention de stage d\'études :"
								},
								{
									"mData" : function(o, type) {
										return '<div class="checkbox"><input name="charteEtudiant" type="checkbox" '
												+ (o.charteEtudiant ? 'checked'
														: '') + '></div>';
									},
									"title" : "Charte de l'étudiant :"
								},
								{
									"mData" : function(o, type) {
										return '<div class="checkbox"><input name="docEngagement" type="checkbox" '
												+ (o.docEngagement ? 'checked'
														: '') + '></div>';
									},
									"title" : "Document d’engagement :"
								},
								{
									"mData" : function(o, type) {
										return '<div class="checkbox"><input name="preuveTestLangue" type="checkbox" '
												+ (o.preuveTestLangue ? 'checked'
														: '') + '></div>';
									},
									"title" : "Preuve du passage des tests linguistiques :"
								},
								{
									"mData" : function(o, type) {
										return '<button type="button" class="btn btn-link infoPart" id="mobiId'
												+ o.id + '" >Infos</button>';
									},
									"title" : "Valider"

								} ]
					});
};
function listeDocRetour() {
	if (table instanceof Object) {
		table.destroy();
		$("#tableauPersonnalise").empty();
	}
	ColomnsGenerator("listeDocRetour");
	table = $("#tableauPersonnalise")
			.DataTable(
					{
						language : {
							url : '/js/French.json'
						},

						"ajax" : {
							"url" : linkTeacher + "listeDocRetour",
							"type" : "POST"
						},
						"aoColumns" : [
								{
									"mData" : "etudiant.nom",
									"title" : "Nom :"
								},
								{
									"mData" : "etudiant.prenom",
									"title" : "Prenom :"
								},
								{
									"mData" : "etudiant.departement.id",
									"title" : "Departement :"
								},
								{
									"mData" : function(o, type) {
										return '<button type="button" class="btn btn-link infoMobStud" id="mobiliteN'
												+ o.id + '" >Infos</button>';
									},
									"title" : "Détail mobilité:"
								},
								{
									"mData" : function(o, type) {
										return '<div class="checkbox"><input name="attestationSejour" type="checkbox" '
												+ (o.attestationSejour ? 'checked'
														: '') + '></div>';
									},
									"title" : "Attestation sejour:"
								},
								{
									"mData" : function(o, type) {
										return '<div class="checkbox"><input name="releveNoteOuCertifStage" type="checkbox" '
												+ (o.releveNoteOuCertifStage ? 'checked'
														: '') + '></div>';
									},
									"title" : "Releve note ou certif stage / d\'études :"
								},
								{
									"mData" : function(o, type) {
										return '<div class="checkbox"><input name="preuvePassageTest" type="checkbox" '
												+ (o.preuvePassageTest ? 'checked'
														: '') + '></div>';
									},
									"title" : "Preuve du passage de test linguistique :"
								},
								{
									"mData" : function(o, type) {
										return '<div class="checkbox"><input name="rapportFinal" type="checkbox" '
												+ (o.rapportFinal ? 'checked'
														: '') + '></div>';
									},
									"title" : "Rapport final :"
								},
								{
									"render" : function(data, type, full, meta) {

										return '<button type="button" class="btn btn-primary updateDocRetour" data-version="'
												+ full["numVersion"]
												+ '" id="MobEncodageID'
												+ full["id"]
												+ '" data-numLigne="'
												+ (meta.row + 1)
												+ '">Valider</button>';
									},
									"title" : "Valider"
								} ]
					});
};

function listeDemandePaiement() {
	if (table instanceof Object) {
		table.destroy();
		$("#tableauPersonnalise").empty();
	}
	ColomnsGenerator("listeDemandePaiement");

	table = $("#tableauPersonnalise")
			.DataTable(
					{
						language : {
							url : '/js/French.json'
						},

						"ajax" : {
							"url" : linkTeacher + "listeDemandePaiement",
							"type" : "POST"
						},
						"aoColumns" : [
								{
									"mData" : "anneeAcademique",
									"title" : "Annee academique :"
								},
								{
									"mData" : "etudiant.departement.nom",
									"title" : "Departement :"
								},
								{
									"mData" : function(o, type) {
										return '<button type="button" class="btn btn-link infoMobStud" id="mobiliteN'
												+ o.id + '" >Infos</button>';
									},
									"title" : "Détail mobilité:"
								},
								{
									"mData" : "etudiant.nom",
									"title" : "Nom :"
								},
								{
									"mData" : "etudiant.prenom",
									"title" : "Prenom :"
								},
								{
									"title" : "Demande Paiment 1",
									"mData" : function(data, type) {
										if (data.demandePaiement1.id == 0) {
											return '<button type="button" class="btn btn-info ValiderDemandePaimentItem"'
													+ 'data-mobilite="'
													+ data.id
													+ '">Confirmer demande</button>';
										} else {
											return 'Demande Envoyé';
										}
									}
								},
								{
									"title" : "Demande Paiment 2",
									"mData" : function(data, type) {
										if (data.demandePaiement2.id == 0) {
											return '<button type="button" class="btn btn-info ValiderDemandePaimentItem"'
													+ 'data-mobilite="'
													+ data.id
													+ '">Confirmer demande</button>';
										} else {
											return 'Demande Envoyé';
										}
									}
								} ]
					});
	// tri sur colonnes
	yadcf.init(table, [ {
		column_number : 0,
		filter_type : "select",
		select_type : 'select2'
	} ]);
};
function listeDesProgrammesExterne() {
	if (table instanceof Object) {
		table.destroy();
		$("#tableauPersonnalise").empty();
	}
	ColomnsGenerator("listeDesProgrammesExterne");
	table = $("#tableauPersonnalise")
			.DataTable(
					{
						language : {
							url : '/js/French.json'
						},

						"ajax" : {
							"url" : linkTeacher + "listeDesProgrammesExterne",
							"type" : "POST"
						},
						"aoColumns" : [
								{
									"title" : "Sélectionner :",
									"data" : "active",
									"render" : function(data, type, full, meta) {
										// Vieux code à garder
										return '<input type="checkbox" class="changeEncodageItem" data-version="'
												+ full["numVersion"]
												+ '" id="MobEncodageID'
												+ full["id"]
												+ '" data-numLigne="'
												+ (meta.row + 1) + '">';
									}
								},
								{
									"mData" : "etudiant.nom",
									"title" : "Nom :"
								},
								{
									"mData" : "etudiant.prenom",
									"title" : "Prenom :"
								},
								{
									"mData" : "etudiant.departement.nom",
									"title" : "Departement :"
								},
								{
									"mData" : function(o, type) {
										return '<button type="button" class="btn btn-link infoMobStud" id="mobiliteN'
												+ o.id + '" >Infos</button>';
									},
									"title" : "Détail mobilité:"
								},
								{
									"mData" : function(o, type) {
										return '<div class="checkbox"><input name="proEco" type="checkbox" '
												+ (o.proEco === true ? 'checked'
														: '') + '></div>';
									},
									"title" : "ProEco:"
								},
								{
									"mData" : function(o, type) {
										return '<div class="checkbox"><input name="mobility_Tool" type="checkbox" '
												+ (o.mobilityTool === true ? 'checked'
														: '') + '></div>';
									},
									"title" : "Mobility Tool :"
								},
								{
									"mData" : function(o, type) {
										return '<div class="checkbox"><input name="mobi" type="checkbox" '
												+ (o.mobi === true ? 'checked'
														: '') + '></div>';
									},
									"title" : "Mobi :"
								} ]
					});
	// tri sur colonnes
	yadcf.init(table, [ {
		column_number : 3,
		filter_type : "multi_select",
		select_type : 'select2'
	} ]);
}
function listeDesAbandons() {
	if (table instanceof Object) {
		table.destroy();
		$("#tableauPersonnalise").empty();
	}
	ColomnsGenerator("listeDesAbandons");
	table = $("#tableauPersonnalise")
			.DataTable(
					{
						language : {
							url : '/js/French.json'
						},

						"ajax" : {
							"url" : linkTeacher + "listeDesAbandons",
							"type" : "POST"
						},

						"aoColumns" : [
								{
									"mData" : "etudiant.nom",
									"title" : "Nom :"
								},
								{
									"mData" : "etudiant.prenom",
									"title" : "Prenom :"
								},
								{
									"mData" : "etudiant.departement.nom",
									"title" : "Departement :"
								},
								{
									"mData" : "annulation.description",
									"title" : "Raison d\'abandon :"
								},
								{
									"mData" : "etat",
									"title" : "Etat de l\'abandon avant l\'annulation"
								},
								{
									"mData" : function(o, type) {
										return '<button type="button" class="btn btn-link infoMobStud" id="mobiliteN'
												+ o.id + '" >Infos</button>';
									},
									"title" : "Détails"
								} ]
					});

};
function tableDeToutLesDemandes() {
	if (table instanceof Object) {
		table.destroy();
		$("#tableauPersonnalise").empty();
	}

	ColomnsGenerator("listeDemande");
	table = $("#tableauPersonnalise")
			.DataTable(
					{
						language : {
							url : '/js/French.json'
						},

						"ajax" : {
							"url" : linkTeacher + "tableDeToutLesDemandes",
							"type" : "POST"
						},

						"aoColumns" : [
								{
									"mData" : "etudiant.nom",
									"title" : "Nom :"
								},
								{
									"mData" : "etudiant.prenom",
									"title" : "Prenom :"
								},
								{
									"mData" : "etudiant.departement.nom",
									"title" : "Departement :",
									"defaultContent" : "/"

								},
								{
									"mData" : function(data, type) {
										if (data.validee === true) {
											return "oui";
										} else {
											return '<button type="button" class="btn btn-info ValidationMobItem"'
													+ 'data-id="'
													+ data.id
													+ '" data-validee="'
													+ data.validee
													+ '" data-numVersion="'
													+ data.numVersion
													+ '"'
													+ 'data-dep="'
													+ data.etudiant.departement.id
													+ '">Confirmer</button>';
										}
									},
									"title" : "Validée :",
									"bSortable" : true
								},
								{
									"mData" : "preference",
									"title" : "Priorité :"
								},
								{
									"mData" : "programme",
									"title" : "Type mobilité :"
								},
								{
									"mData" : "typeStage",
									"title" : "SMS/SMP :"
								},
								{
									"mData" : function(o, type) {
										return '<button type="button" class="btn btn-link infoDemandeStud" id="IdDemande'
												+ o.id + '" >Infos</button>';
									},
									"title" : "Détails"
								} ]
					});
};

function listeMobilitee() {
	if (table instanceof Object) {
		table.destroy();
		$("#tableauPersonnalise").empty();
	}
	ColomnsGenerator("listeMobilitee");
	table = $("#tableauPersonnalise")
			.DataTable(
					{
						language : {
							url : '/js/French.json'
						},

						"ajax" : {
							"url" : linkTeacher + "listeMobilitee",
							"type" : "POST"
						},
						"aoColumns" : [
								{
									"mData" : "etudiant.nom",
									"title" : "Nom :"
								},
								{
									"mData" : "etudiant.prenom",
									"title" : "Prenom :"
								},
								{
									"mData" : "etudiant.departement.nom",
									"title" : "Departement :"
								},
								{
									"mData" : "programme",
									"title" : "Type Mobilité :"
								},
								{
									"mData" : "typeStage",
									"title" : "SMS/SMP :"
								},
								{
									"mData" : "pays.nom",
									"title" : "Pays :"
								},
								{
									"mData" : "quadrimestre",
									"title" : "Quadrimestre :"
								},
								{
									"mData" : function(o, type) {
										return '<button type="button" class="btn btn-link infoMob" id="infoMob'
												+ o.id + '" >Infos</button>';
									},
									"title" : "Infos"
								}, {
									"title" : "annulation"
								// annulation
								} ]
					});
};

function mobiliteValidees() {
	if (table instanceof Object) {
		table.destroy();
		$("#tableauPersonnalise").empty();
	}
	ColomnsGenerator("mobilitesConfirmees");
	table = $("#tableauPersonnalise")
			.DataTable(
					{
						language : {
							url : '/js/French.json'
						},

						"ajax" : {
							"url" : linkTeacher + "mobiliteConfirmees",
							"type" : "POST"
						},
						"aoColumns" : [
								{
									"mData" : "etudiant.nom",
									"title" : "Nom :"
								},
								{
									"mData" : "etudiant.prenom",
									"title" : "Prenom :"
								},
								{
									"mData" : "etudiant.departement.nom",
									"title" : "Departement :"
								},
								{
									"mData" : function(o, type) {
										var text = (o.annulation.description === null ? o.etat
												: 'ANNULEE');
										return '' + text;

									},
									"title" : "Etat :",
									"bSortable" : true
								},
								{
									"mData" : "demande.programme",
									"title" : "Type Mobilité :"
								},
								{
									"mData" : "demande.typeStage",
									"title" : "SMS/SMP :"
								},
								{
									"mData" : "pays.nom",
									"title" : "Pays :"
								},
								{
									"mData" : "demande.quadri",
									"title" : "Quadrimestre :"
								},
								{
									"mData" : function(o, type) {
										return '<button type="button" class="btn btn-link infoMobStud" id="mobiliteN'
												+ o.id + '" >Infos</button>';
									},
									"title" : "Détails :"
								},
								{
									"mData" : function(o, type) {
										return '<button type="button" class="btn btn-link glyphicon glyphicon-remove-sign delMob" data-numVersion="'
												+ o.numVersion
												+ '" id="Mob'
												+ o.id + '" >Annuler</button>';
									},
									"title" : "Abandonner :"
								} ]
					});
	// tri sur colonnes
	yadcf.init(table, [ {
		column_number : 2,
		filter_type : "multi_select",
		select_type : 'select2'
	}, {
		column_number : 3,
		filter_type : "multi_select",
		select_type : 'select2'
	}, {
		column_number : 4,
		filter_type : "multi_select",
		select_type : 'select2'
	} ]);
};

function TablePartenaires() {

	if (table instanceof Object) {
		table.destroy();
		$("#tableauPersonnalise").empty();
	}
	ColomnsGenerator("partenaires");
	table = $("#tableauPersonnalise")
			.DataTable(
					{
						language : {
							url : '/js/French.json'
						},

						"ajax" : {
							"url" : linkUtil + "PartenairesList",
							"type" : "POST"
						},
						"aoColumns" : [
								{
									"mData" : "nomComplet",
									"title" : "Nom Complet :",
									"bSortable" : true
								},
								{
									"mData" : "pays.nom",
									"title" : "Pays :",
									"bSortable" : true
								},
								{
									"mData" : "ville",
									"title" : "Ville :",
									"bSortable" : true
								},
								{
									"mData" : function(data, type) {
										if (data.agree === true) {
											return "oui";
										} else {
											return "non";
										}
									},
									"title" : "Agrée :",
									"bSortable" : true
								},
								{
									"mData" : function(o, type) {
										return '<button type="button" class="btn btn-info infoPart" id="partN'
												+ o.id + '" >Infos</button>';
									},
									"title" : "Infos"
								}, {
									"title" : "Suppression",
									"mData": function(data,type) {
										return '<button type="button" class="btn btn-danger modificationVisibilitePart" id="partN'
										+ data.id + '" data-action = "true" data-numVersion="' + data.numVersion + '" >Supprimer</button>';
									}
								} ]
					});
	// tri sur colonnes
	yadcf.init(table, [ {
		column_number : 1,
		filter_type : "multi_select",
		select_type : 'select2'
	} ]);

};
function ListeUser() {
	if (table instanceof Object) {
		table.destroy();
		$("#tableauPersonnalise").empty();
	}
	ColomnsGenerator("allUsers");
	table = $("#tableauPersonnalise")
			.DataTable(
					{
						language : {
							url : '/js/French.json'
						},

						"ajax" : {
							"url" : linkTeacher + "allUsers",
							"type" : "POST"
						},
						"aoColumns" : [
								{
									"title" : "Sélectionner :",
									"data" : "active",
									"mData" : function(o, type) {
										return '<input type="checkbox" class="changeUserGrant" id="user'
												+ o.id
												+ '" data-version="'
												+ o.numVersion + '">';
									}
								},
								{
									"mData" : "nom",
									"title" : "Nom :"
								},
								{
									"mData" : "prenom",
									"title" : "Prenom :"
								},
								{
									"mData" : "departement.nom",
									"title" : "Departement :"
								},
								{
									"mData" : function(o, type) {
										return (o.typeUser === 'ETUD') ? 'Etudiant'
												: 'Professeur';
									},
									"title" : "Type Utilisateur :"
								},
								{
									"mData" : function(o, type) {
										return '<button type="button" class="btn btn-link infoStud" id="stud'
												+ o.pseudo
												+ '" >Infos</button>';
									},
									"title" : "Infos"
								}]
					});
	yadcf.init(table, [ {
		column_number : 3,
		filter_type : "multi_select",
		select_type : 'select2'
	}, {
		column_number : 4,
		select_type : 'select2'
	} ]);
};

function TablePartenairesAnnules() {

	if (table instanceof Object) {
		table.destroy();
		$("#tableauPersonnalise").empty();
	}
	ColomnsGenerator("partenaires");
	table = $("#tableauPersonnalise")
			.DataTable(
					{
						language : {
							url : '/js/French.json'
						},

						"ajax" : {
							"url" : linkTeacher + "PartenairesSupprimesList",
							"type" : "POST"
						},
						"aoColumns" : [
								{
									"mData" : "nomComplet",
									"title" : "Nom Complet :",
									"bSortable" : true
								},
								{
									"mData" : "pays.nom",
									"title" : "Pays :",
									"bSortable" : true
								},
								{
									"mData" : "ville",
									"title" : "Ville :",
									"bSortable" : true
								},
								{
									"mData" : function(data, type) {
										if (data.agree === true) {
											return "oui";
										} else {
											return "non";
										}
									},
									"title" : "Agrée :",
									"bSortable" : true
								},
								{
									"mData" : function(o, type) {
										return '<button type="button" class="btn btn-info infoPart" id="partN'
												+ o.id + '" >Infos</button>';
									},
									"title" : "Infos",
									"bSortable" : false
								},{
									"title" : "Réhabilitation",
									"mData": function(data,type) {
										return '<button type="button" class="btn btn-warning modificationVisibilitePart" id="partN'
										+ data.id + '" data-action = "false" data-numVersion="' + data.numVersion + '" >Réhabiliter</button>';
									}
								}]
					});
	// tri sur colonnes
	yadcf.init(table, [ {
		column_number : 1,
		filter_type : "multi_select",
		select_type : 'select2'
	} ]);

};