function getAllTitre() {
	$.ajax({
		url : linkUtil + 'getAllTitre',
		type : 'POST',
		data : {},
		success : function(reponse) {

			var options = "";
			$.each(reponse, function(index, value) {
				options += " <option value=\"" + index + "\">" + reponse[index]
						+ "</option>";
			});

			$(".titre").html(options);
			$('.titre').select2();

		},
		error : function(er) {
			console.log(er);

		}
	});
};

function getSexe() {
	$.ajax({
		url : linkUtil + 'getSexe',
		type : 'POST',
		data : {},
		success : function(reponse) {

			var options = "";
			$.each(reponse, function(index, value) {
				options += " <option value=\"" + index + "\">" + reponse[index]
						+ "</option>";
			});

			$(".sexe").html(options);
			$('.sexe').select2();
		},
		error : function(er) {
			console.log(er);

		}
	});
};

function getTypeEntreprise() {
	$.ajax({
		url : linkUtil + 'getTypeEntreprises',
		type : 'POST',
		data : {},
		success : function(reponse) {

			var options = "";
			$.each(reponse, function(index, value) {
				options += " <option value=\"" + index + "\">" + reponse[index]
						+ "</option>";
			});

			$("#typeEntreprise").html(options);
			$('#typeEntreprise').select2();
		},
		error : function(er) {
			console.log(er);

		}
	});
};

function getTypeStage() {
	$
			.ajax({
				url : linkUtil + 'getTypeStage',
				type : 'POST',
				data : {},
				success : function(reponse) {

					var options = "";
					$
							.each(
									reponse,
									function(index, value) {
										options += "<label class=\"radio-inline\"><input type=\"radio\" name=\"typeStage\" value=\""
												+ index
												+ "\">"
												+ value
												+ "</input></label>";
									});

					$(".typeStage").html(options);
				},
				error : function(er) {
					console.log(er);

				}
			});
};

function getProgramme() {
	$.ajax({
		url : linkUtil + 'getProgramme',
		type : 'POST',
		data : {},
		success : function(reponse) {
			var options = "";
			$.each(reponse, function(index, value) {
				options += " <option value=\"" + index + "\" >"
						+ reponse[index] + "</option>";
			});

			$(".programme").html(options);
			$('.programme').select2();
			$('.programme').val('ERASMUS').trigger('change');
		},
		error : function(er) {
			console.log(er);

		}
	});
};
/**
 * liste de tout les départements
 */
function getAllDepartement() {
	$.ajax({
		url : linkUtil + 'getAllDepartements',
		type : 'POST',
		data : {},
		success : function(reponse) {
			var options = "";
			$.each(reponse, function(index, value) {
				options += " <option value=\"" + index + "\">" + reponse[index]
						+ "</option>";
			});

			$(".departement").html(options);
			$('.departement').select2();
		},
		error : function(err) {
			console.log(err);

		}
	});
};

function getAllPays() {
	$
			.ajax({
				url : linkUtil + 'getAllPays',
				type : 'POST',
				data : {},
				success : function(reponse) {
					var options = "";
					options += '<option class="null" data-mobilite="ERASMUS" value="" disabled selected>Aucun</option>';
					$.each(reponse, function(index, value) {
						options += " <option value=\"" + index
								+ "\" data-mobilite=\""
								+ reponse[index]["typeMobilite"] + "\">"
								+ reponse[index]["nom"] + "</option>";
					});
					$(".pays").html(options);
					$(".pays").select2();
				},
				error : function(er) {
					console.log(er);

				}
			});
};
function getAllPartenairesAgree2() {
	$
			.ajax({
				url : linkUtil + 'getAllPartenairesAgree',
				type : 'POST',
				data : {},
				success : function(reponse) {
					var options = "";
					$.each(reponse, function(index, value) {
						$.map(reponse[index], function(n, i) {
							options += " <option value=\"" + n["id"]
									+ "\" data-valide=\"" + !n["agree"] + "\">"
									+ n["nomAffaire"] + "</option>";
						});
					});
					options += '<option data-valide="false" value="" disabled selected>Aucun</option>';
					$("#partenaire").html(options);
					$("#partenaire").select2();
					if (laBonneSection === TeacherHome) {
						$("#partenaireProf").html(options);
						$("#partenaireProf").select2();
					}
				},
				error : function(err) {
					var options = '<option data-valide="false" value="" disabled selected>Aucun</option>';
					$("#partenaire").html(options);
					$("#partenaire").select2();
					if (laBonneSection === TeacherHome) {
						$("#partenaireProf").html(options);
						$("#partenaireProf").select2();
					}
				}
			});
};
function getAnnulationGenerique(){
	$.ajax({
		url : linkUtil + 'getRaisonAbandonGenerique',
		type : 'POST',
		data : {},
		success : function(reponse) {
			var options = "";
			console.log(reponse)
			$.each(reponse, function(index, value) {
				options += " <option value=\"" + index + "\">" + reponse[index]["description"]
						+ "</option>";
			});
			$(".raisonAbandon").html(options);
			$('.raisonAbandon').select2();
		},
		error : function(err) {
			var options = " <option value=\"" + "Aucun" + "\">" + "Aucun"
			+ "</option>";
			$(".raisonAbandon").html(options);
			$('.raisonAbandon').select2();
		}
	});
};
function getProfil(link, identificant) {
	$.ajax({
		url : link + 'getProfil',
		type : 'POST',
		data : {},
		success : function(reponse) {

			var form = $("#" + identificant);
			var date;

			if (reponse["titre"] != null) {
				form.find('#titre').val(reponse["titre"]);
			} else {
				form.find("#titre option[value='M']").prop('selected', true);
			}
			form.find('#titre').trigger("change");

			if (reponse["sexe"] != null) {
				form.find('#sexe').val(reponse["sexe"]);
			} else {
				form.find("#sexe option[value='M']").prop('selected', true);
			}
			form.find('#sexe').trigger("change");
			form.find('input[name="nom"]').val(reponse["nom"]);
			form.find('input[name="prenom"]').val(reponse["prenom"]);

			date = DatetoString(reponse["dateNaissance"]);
			if (date !== null ) {
				form.find('input[name="dateNaissance"]').val(date);
			}
			form.find('input[name="nationalite"]').val(reponse["nationalite"]);
			form.find('input[name="email"]').val(reponse["email"]);
			form.find('input[name="nbAnneeSup"]').val(reponse["nbAnneeEns"]);
			form.find('input[name="CptBancaire"]').val(reponse["numCompte"]);
			form.find('input[name="NomBanque"]').val(reponse["NomBanque"]);
			form.find('input[name="CodeBic"]').val(reponse["codeBic"]);
			form.find('input[name="TituCpt"]').val(reponse["titulaireCompte"]);
			form.find('input[name="NomBanque"]').val(reponse["nomBanque"]);
			form.find('textarea[name="adresse"]').val(reponse["adresse"]);
			form.find('input[name="telephone"]').val(reponse["telephone"]);
			form.find('input[name="numVersion"]').val(reponse["numVersion"]);
			callDatePicker();
		},
		error : function(er) {
			console.log(er);

		}
	});
};

function getPartenaire(pk) {
	if (pk === undefined) {
		return false;
	}
	$.ajax({
		url : linkUtil + 'getInfoPartenaire',
		type : 'POST',
		data : {
			pkPart : pk
		},
		success : function(reponse) {
			
			var form = $("#partenaireModal");
			
			form.find('input[name="nomLegal"]').val(reponse["nomLegal"]);
			form.find('input[name="nomAffaire"]').val(reponse["nomAffaire"]);
			form.find('input[name="nomComplet"]').val(reponse["nomComplet"]);

			form.find('input[name="nombreEmp"]').val(reponse["nbrEmploye"]);

			form.find('select[name="typeOrganisation"]').val(
					reponse["typeEntreprise"]).trigger("change");

			form.find(
					'input[name="typeStage"][value="' + reponse["typeStage"]
							+ '"]').prop("checked", true);
			form.find('textarea[name="adressePartenaire"]').val(
					reponse["adresse"]);

			var nomPays = reponse["pays"]["nom"];
			var idPays = form.find(
					'select[name="paysPartenaire"]  > option:contains("'
							+ nomPays + '")').attr('value');
			form.find('select[name="paysPartenaire"]').val(idPays).trigger(
					"change");

			form.find('input[name="regionPartenaire"]').val(reponse["region"]);
			form.find('input[name="codePostalPartenaire"]').val(
					reponse["codePostal"]);
			form.find('input[name="villePartenaire"]').val(reponse["ville"]);
			form.find('input[name="emailPartenaire"]').val(reponse["email"]);
			form.find('input[name="siteWebPartenaire"]')
					.val(reponse["siteWeb"]);
			form.find('input[name="telPartenaire"]').val(reponse["telephone"]);
			
			var selectedValues = new Array();
			
			$.each(reponse["allDepartements"], function(index, value) {
				if (value !== null) {
					selectedValues.push(value["id"]);
				} ;	
			});
			
			if (selectedValues.length !== 0) {
				form.find('#partDepartement').select2().val(selectedValues).trigger("change");
			}
			
			if (laBonneSection === 'StudentHome') {
				form.find('#partDepartement').prop("disabled", true);
			}
			
		},
		error : function(er) {
			console.log(er);

		}
	});

};

function detailDemandeForAValidation(pk) {
	if (pk === undefined) {
		return false;
	}
	$.ajax({
		url : linkTeacher + 'detailDemande',
		type : 'POST',
		data : {
			pkDemande : pk
		},
		success : function(reponse) {
			var form = $("#enregistrerDemandeProf");
			test = reponse;
			form.find('input[name="preference"]').val(reponse["preference"]);
			form.find('input[name="ville"]').val(reponse["ville"]);
			form.find('input[name="numVersion"]').val(reponse["numVersion"]);
			var nomPays = reponse["pays"]["nom"];
			var idPays = form
					.find(
							'select[name="pays"]  > option:contains("'
									+ nomPays + '")').attr('value');
			form.find('select[name="pays"]').val(idPays).trigger("change");
			var partenaire = reponse["partenaire"]["nomAffaire"];
			var idPartenaire = form.find(
					'select[name="partenaire"]  > option:contains("'
							+ partenaire + '")').attr('value');
			form.find('select[name="partenaire"]').val(idPartenaire).trigger(
					"change");
			
			var programme = reponse["programme"];
			form.find('select[name="programme"]').val(programme).trigger(
					"change");
			form.find(
					'input[name="typeStage"][value="' + reponse["typeStage"]
							+ '"]').prop("checked", true);

			var quadri = reponse["quadri"];
			var idQuadri = form.find(
					'select[name="quadri"]  > option:contains("' + quadri
							+ '")').attr('value');
			form.find('select[name="quadri"]').val(idQuadri).trigger("change");
			form.find(
					':radio[name="typeStage"][value="' + reponse["typeStage"]
							+ '"]').prop("checked", true);
			form.find('select[name="anneeAcademique"]').val(test["anneeAcademique"]).trigger("change");
		},
		error : function(er) {
			console.log(er);

		}
	});
};	

function detailDemande(pk) {
	if (pk === undefined) {
		return false;
	}
	$.ajax({
		url : linkStudent + 'detailDemande',
		type : 'POST',
		data : {
			pkDemande : pk
		},
		success : function(reponse) {
			var form = $(".infoDemandeSection");

			form.find('input[name="preference"]').val(reponse["preference"]);
			form.find('input[name="ville"]').val(reponse["ville"]);
			var nomPays = reponse["pays"]["nom"];
			var idPays = form
					.find(
							'select[name="pays"]  > option:contains("'
									+ nomPays + '")').attr('value');
			form.find('select[name="pays"]').val(idPays).trigger("change");

			var partenaire = reponse["partenaire"]["nomAffaire"];
			var idPartenaire = form.find(
					'select[name="partenaire"]  > option:contains("'
							+ partenaire + '")').attr('value');
			form.find('select[name="partenaire"]').val(idPartenaire).trigger(
					"change");
			var programme = reponse["programme"];
			form.find('select[name="programme"]').val(programme).trigger(
					"change");
			form.find(
					'input[name="typeStage"][value="' + reponse["typeStage"]
							+ '"]').prop("checked", true);

			var quadri = reponse["quadri"];
			var idQuadri = form.find(
					'select[name="quadri"]  > option:contains("' + quadri
							+ '")').attr('value');
			form.find('select[name="quadri"]').val(idQuadri).trigger("change");
			form.find(
					':radio[name="typeStage"][value="' + reponse["typeStage"]
							+ '"]').prop("checked", true);
			var message = (reponse["validee"] == true) ? "DEMANDE VALIDEE"
					: "DEMANDE NON VALIDEE";
			form.find("#EtatDemande").text(message);
			form.find(".infoPartInfoDemande").prop("id",
					"partN" + reponse["partenaire"]["id"]);

		},
		error : function(er) {
			console.log(er);

		}
	});
};

function detailMobilite(pk,link) {
	if (pk === undefined) {
		return false;
	}
	$.ajax({
		url : link + 'infoMobilitepk',
		type : 'POST',
		data : {
			pkMobilite : pk
		},
		success : function(reponse) {
			console.log(reponse);
			var form = $("#infoMobilite");
			form.find(".infoPart").attr('data-id', reponse["partenaire"]["id"]);
			form.find(".infoPart").attr('data-numVersion', reponse["numVersion"]);
			form.find(".infoPart").text(reponse["partenaire"]["nomAffaire"]);
			if (laBonneSection === 'StudentHome') {
				form.find(".infoStud").attr('data-id', reponse["id"]);
				form.find(".infoStud").text("moi");
				getDocDepartForAMobility(pk,linkStudent);
				getDocRetourForAMobility(pk,linkStudent);
			} else {
				form.find(".infoStud").attr('id', reponse["etudiant"]["pseudo"]);
				form.find(".infoStud").text(reponse["etudiant"]["prenom"] + '' + reponse["etudiant"]["nom"]);
				getDocDepartForAMobility(pk,linkTeacher);
				getDocRetourForAMobility(pk,linkTeacher);
			}
			form.find('input[name="idMobilite"]').attr('value', reponse["id"]);
		}
	});
};

function getProfilStudentForTeacher(pseudo) {
	if (pseudo === undefined) {
		return false;
	}
	$.ajax({
		url : linkTeacher + 'getProfilStudent',
		type : 'POST',
		data : {
			pseudoUser : pseudo
		},
		success : function(reponse) {

			var form = $("#infoStudModal");
			var date;

			if (reponse["titre"] != null) {
				form.find('#titre').val(reponse["titre"]);
			} else {
				form.find("#titre option[value='M']").prop('selected', true);
			}
			form.find('#titre').trigger("change");

			if (reponse["sexe"] != null) {
				form.find('#sexe').val(reponse["sexe"]);
			} else {
				form.find("#sexe option[value='M']").prop('selected', true);
			}
			form.find('#sexe').trigger("change");
			form.find('input[name="nom"]').val(reponse["nom"]);
			form.find('input[name="prenom"]').val(reponse["prenom"]);
			
			date = DatetoString(reponse["dateNaissance"]);
			if (date !== null ) {
				form.find('input[name="dateNaissance"]').val(date);
			}

			form.find('input[name="nationalite"]').val(reponse["nationalite"]);
			form.find('input[name="email"]').val(reponse["email"]);
			form.find('input[name="nbAnneeSup"]').val(reponse["nbAnneeEns"]);
			form.find('input[name="CptBancaire"]').val(reponse["numCompte"]);
			form.find('input[name="NomBanque"]').val(reponse["NomBanque"]);
			form.find('input[name="CodeBic"]').val(reponse["codeBic"]);
			form.find('input[name="TituCpt"]').val(reponse["titulaireCompte"]);
			form.find('input[name="NomBanque"]').val(reponse["nomBanque"]);
			form.find('textarea[name="adresse"]').val(reponse["adresse"]);
			form.find('input[name="telephone"]').val(reponse["telephone"]);
			form.find('input[name="numVersion"]').val(reponse["numVersion"]);
			callDatePicker();
		},
		error : function(er) {
			console.log(er);

		}
	});
};

function getTypesUser() {
	$.ajax({
		url : linkTeacher + 'getTypesUser',
		type : 'POST',
		data : {},
		success : function(reponse) {
			var options = "";

			$.each(reponse, function(index, value) {
				options += " <option value=\"" + index + "\">" + reponse[index]
						+ "</option>";
			});

			$(".TypesUser").html(options);
			$(".TypesUser").select2();
		},
		error : function(err) {

		}
	});
};

function getDocDepartForAMobility(pk,link) {
	if (pk === undefined){
		return false;
	}
	$.ajax({
		url : link + "DocDepartStud",
		type : 'POST',
		data : {
			identifiant : pk
		},
		success : function(reponse) {
			var options = "";
			
			var list = { "contratBourse" : "Contrat de bourse :",
							"conventionStageOuEtude" :"Convention de stage / d\'études :",
							"charteEtudiant" : "Charte de l\'étudiant :", "docEngagement" : "Document d’engagement :" ,
							"preuveTestLangue" : "Preuve du passage des tests linguistiques :" };
			$.each(reponse, function(index, value) {
				
				if (jQuery.inArray(index, Object.keys(list)) !== -1) {
					
				options += '<label class="checkbox-inline"><input name="' + index
						+ '" type="checkbox" '
						+ ((value === true) ? 'checked' : '') + '>' +  list[index] + '</label>';
				}

			});

			var form = $("#change_mob");
			form.find('input[name="NumVersionDocDepart"]').attr('value',reponse["numVersion"]);
			form.find('input[name="idDocDepart"]').attr('value',reponse["id"]);
			form.find("#infoDocDepartCheckbox").html("");
			form.find("#infoDocDepartCheckbox").html(options);
			form.find("#infoDocDepartCheckbox").prepend("<h3>Documents de départ</h3>");
		},
		error : function(err) {

		}
	});
};

function getDocRetourForAMobility(pk,link) {
	if (pk === undefined){
		return false;
	}
	$.ajax({
		url : link + "DocRetour",
		type : 'POST',
		data : {
			identifiant : pk
		},
		success : function(reponse) {
			var options = "";

			var list = {'attestationSejour' : ' Attestation sejour:' ,
			        'releveNoteOuCertifStage' : 'Releve note ou certif stage / d\'études :' ,
			        'preuvePassageTest' : 'Preuve du passage de test linguistique :' ,
			        'rapportFinal' : 'Rapport final :'};
			$.each(reponse, function(index, value) {

				if (jQuery.inArray(index, Object.keys(list)) !== -1) {
					
				options += '<label class="checkbox-inline"><input name="' + index
						+ '" type="checkbox" '
						+ ((value === true) ? 'checked' : '') + '>' +  list[index] + '</label>';
				}

			});
			var form = $("#change_mob");
			form.find('input[name="NumVersionDocRetour"]').attr('value',reponse["numVersion"]);
			form.find('input[name="idDocRetour"]').attr('value',reponse["id"]);
			form.find("#infoDocRetourCheckbox").html("");
			form.find("#infoDocRetourCheckbox").html(options);
			form.find("#infoDocRetourCheckbox").prepend("<h3>Documents de retour</h3>");
		},
		error : function(err) {

		}
	});
};

// DUPLICATATE

function getAllPartenairesAgree(pk) {
	if (pk === undefined){
		return false;
	}
	$
			.ajax({
				url : linkUtil + 'getAllPartenairesAgree',
				type : 'POST',
				data : {pkDep : pk},
				success : function(reponse) {
					var options = "";
					$.each(reponse, function(index, value) {
						$.map(reponse[index], function(n, i) {
							options += " <option value=\"" + n["id"]
									+ "\" data-valide=\"" + !n["agree"] + "\">"
									+ n["nomAffaire"] + "</option>";
						});
					});
					options += '<option data-valide="false" value="" disabled selected>Aucun</option>';
					
					$("#partenaire").html(options);
					$("#partenaire").select2();
					if (laBonneSection === "TeacherHome") {
						$("#partenaireProf").html(options);
						$("#partenaireProf").select2();
						$("#partenaireProf").select2().trigger('change');
					}
				},
				error : function(err) {
					var options = '<option data-valide="false" value="" disabled selected>Aucun</option>';
					$("#partenaire").html(options);
					$("#partenaire").select2();
					if (laBonneSection === "TeacherHome") {
						$("#partenaireProf").html(options);
						$("#partenaireProf").select2();
						$("#partenaireProf").select2().trigger('change');
					}
				}
			});
};

// Cela aurait du être une seule méthode mais plus le temps

function detailDemande2(pk) {
	if (pk === undefined) {
		return false;
	}
	$.ajax({
		url : linkTeacher + 'detailDemande',
		type : 'POST',
		data : {
			pkDemande : pk
		},
		success : function(reponse) {
			var form = $(".infoDemandeSection");

			form.find('input[name="preference"]').val(reponse["preference"]);
			form.find('input[name="ville"]').val(reponse["ville"]);
			var nomPays = reponse["pays"]["nom"];
			var idPays = form
					.find(
							'select[name="pays"]  > option:contains("'
									+ nomPays + '")').attr('value');
			form.find('select[name="pays"]').val(idPays).trigger("change");

			var partenaire = reponse["partenaire"]["nomAffaire"];
			var idPartenaire = form.find(
					'select[name="partenaire"]  > option:contains("'
							+ partenaire + '")').attr('value');
			form.find('select[name="partenaire"]').val(idPartenaire).trigger(
					"change");
			var programme = reponse["programme"];
			form.find('select[name="programme"]').val(programme).trigger(
					"change");
			form.find(
					'input[name="typeStage"][value="' + reponse["typeStage"]
							+ '"]').prop("checked", true);

			var quadri = reponse["quadri"];
			var idQuadri = form.find(
					'select[name="quadri"]  > option:contains("' + quadri
							+ '")').attr('value');
			form.find('select[name="quadri"]').val(idQuadri).trigger("change");
			form.find(
					':radio[name="typeStage"][value="' + reponse["typeStage"]
							+ '"]').prop("checked", true);
			var message = (reponse["validee"] == true) ? "DEMANDE VALIDEE"
					: "DEMANDE NON VALIDEE";
			form.find("#EtatDemande").text(message);
			form.find(".infoPartInfoDemande").prop("id",
					"partN" + reponse["partenaire"]["id"]);

		},
		error : function(er) {
			console.log(er);

		}
	});
};
