//js propre aux Student
function StudentPage() {
	
	$("body").on('click','#boutonCreationPartenaire',function(event){
		$("#CreationPartenaire :input").prop("disabled",false);
	});
	
	$("#profil-edit").on('submit', function(event) {
		event.stopPropagation();
		event.preventDefault();
	}).validate({
		rules : {
			nom : {
				required : true,
				nowhitespace : true
			},
			prenom : {
				required : true,
				nowhitespace : true
			},
			emailProfil : {
				required : true,
				email : true
			},
			DateNaissance:{
				nowhitespace : true
			},
			nbAnneeSup : {
				min : 0
			},
		},
		messages : {
			nom : {
				required : "vous devez metre un nom",
				nowhitespace : "pas d'espace"
			},
			prenom : {
				required : "vous devez metre un prenom",
				nowhitespace : "pas d'espace"

			},
			nbAnneeSup : {
				min : "min sup a 0"
			},
			emailProfil : {
				required : "pas vide",
				email : "entre un email valide"
			}
		},
		submitHandler : function(form) {
			var json = JSON.stringify(formToJson($(form)));
			setProfil(json,linkStudent,"profil-edit");
		},
		invalidHandler : function(event, validator) {
			console.log(validator);
		},
		success : function(element) {
			element.remove();
		}
	});

	$("#CreationPartenaire").on('submit', function(event) {
		event.stopPropagation();
		event.preventDefault();
	}).validate({
		rules : {
			nomLegal : {
				required : true
			},
			nomAffaire : {
				required : true
			},
			nomComplet : {
				required : true
			},
			nombreEmp : {
				required : true,
				min : 1
			},
			typeEntreprise : {
				required : true
			},
			typeStage : {
				required : true
			},
			adressePartenaire : {
				required : true
			},
			paysPartenaire : {
				required : true
			},
			regionPartenaire : {
				required : true
			},
			codePostalPartenaire : {
				required : true
			},
			villePartenaire : {
				required : true
			},
			emailPartenaire : {
				required : true,
				nowhitespace : true,
				email : true
			},
			siteWebPartenaire : {
				required : true,
				nowhitespace : true
			},
			telPartenaire : {
				required : true
			}
		},
		messages : {
			nomLegal : {
				required : "pas vide"
			},
			nomAffaire : {
				required : "pas vide"
			},
			nomComplet : {
				required : "pas vide"
			},
			nombreEmp : {
				required : "pas vide"
			},
			typeEntreprise : {
				required : "pas vide"
			},
			typeStage : {
				required : "pas vide"
			},
			adressePartenaire : {
				required : "pas vide"
			},
			paysPartenaire : {
				required : "pas vide"
			},
			regionPartenaire : {
				required : "pas vide"
			},
			codePostalPartenaire : {
				required : "pas vide"
			},
			villePartenaire : {
				required : "pas vide"
			},
			emailPartenaire : {
				required : "pas vide",
				email : "entre un email correcte"
			},
			siteWebPartenaire : {
				required : "pas vide"
			},
			telPartenaire : {
				required : "pas vide"
			}

		},
		submitHandler : function(form) {
			var json = JSON.stringify(formToJson($(form)));
			// peut etre info déjà dans json , à tester...
			var dts = JSON.stringify($(form).find("#partDepartement").val());
			checkPartenaireExistant(json, dts,linkStudent);

		},
		invalidHandler : function(event, validator) {
			console.log(validator);
		},
		success : function(element) {
			element.remove();
		}
	});

	$("#enregistrerDemande")
			.on('submit', function(event) {
				event.stopPropagation();
				event.preventDefault();
			})
			.validate(
					{
						rules : {
							typeStage : {
								required : true
							},
							preference : {
								required : true,
								min : 1
							},
							programme : {
								required : true,
							}
						},
						messages : {
							typeStage : {
								required : "pas vide"
							},
							programme : {
								required : "pas vide"
							},
							preference : {
								required : "donne ta preference",
								min : "entre un nombre sup a 0"
							}
						},
						submitHandler : function(form) {
							var validee = $(
									'#enregistrerDemande select[name="partenaire"] option:selected')
									.attr("data-valide");
							var json = JSON.stringify(formToJson($(form)));
							enregistrerDemande(json, validee, false);
						},
						invalidHandler : function(event, validator) {
							console.log(validator);
						},
						success : function(element) {
							element.remove();
						}
					});

	$("#pays").on("change", function() {
		var typeProg = $("#pays :selected").attr("data-mobilite");
		typeProg = typeProg.toUpperCase();
		$('#programme').val(typeProg).trigger("change");
		$("#programme").prop("disabled", true);
	});

	$('body').on('click', ".lien", function(event) {
		event.stopPropagation();
		event.preventDefault();
		StudentLoadPage(event.target.id);
	});

	$(".infoPartInfoDemande").on("click", function(event) {
		getAllDepartement();
		getTypeEntreprise();
		var whoPart = event.target.id.replace('partN', '');
		getPartenaire(whoPart);
		$("#partenaireModal :input").prop("disabled", true);
		$("#partenaireModal select").prop("disabled", true);
		$(".modalClose .close").prop("disabled", false);
		$(".modalClose").prop("disabled", false);
		$("#partenaireModal .modal-title").text("Informations Partenaire");
		$("#partenaireModal").modal('show');
	});

	$("#tableauPersonnalise").on("click", ".infoPart", function(event) {
		getAllDepartement();
		getTypeEntreprise();
		var whoPart = event.target.id.replace('partN', '');
		getPartenaire(whoPart);
		$("#partenaireModal :input").prop("disabled", true);
		$("#partenaireModal select").prop("disabled", true);
		$(".modalClose .close").prop("disabled", false);
		$(".modalClose").prop("disabled", false);
		$("#partenaireModal .modal-title").text("Informations Partenaire");
		$("#partenaireModal").modal('show');
	})
	
	$("a.infoPart").on("click", function(event) {
		event.stopPropagation();
		event.preventDefault();
		$("#infoMobilite").modal('hide');
		getAllDepartement();
		getTypeEntreprise();
		var whoPart = $(event.target).attr('data-id')
		getPartenaire(whoPart);
		$("#partenaireModal :input").prop("disabled", true);
		$("#partenaireModal select").prop("disabled", true);
		$(".modalClose .close").prop("disabled", false);
		$(".modalClose").prop("disabled", false);
		$("#partenaireModal .modal-title").text("Informations Partenaire");
		$("#partenaireModal").modal('show');
	});

	$("#tableauPersonnalise").on("click", ".infoDemandeStud", function(event) {
		getAllDepartement();
		var whoPart = event.target.id.replace('IdDemande', '');
		detailDemande(whoPart);
		StudentLoadPage("infoDemande");
	});
	
	$("a.infoDemandeStud").on("click", function(event) {
		getAllDepartement();
		var whoPart = $(event.target).attr('data-id');
		detailDemande(whoPart);
		StudentLoadPage("infoDemande");
	});
	

	$("#tableauPersonnalise").on("click", ".infoMobStud", function(event) {
		var whoPart = event.target.id.replace('mobiliteN', '');
		detailMobilite(whoPart,linkStudent);
		$("#infoMobilite").modal('show');
		//callDatePicker();
	});
	
	$("#tableauPersonnalise").on("click", ".delMob", function(event) {
		var whoPart = event.target.id.replace('Mob', '');
		var numVersion = $(event.target).attr('data-numVersion');
		$("#abandonModal").find('input[name="numVersion"]').val(numVersion);
		$("#abandonModal").find('input[name="mobAnnul"]').val(whoPart);
		$("#abandonModal").modal('show');
	});
	
	
	$("#creationAnnulation").on('submit',function(event){
		event.stopPropagation();
		event.preventDefault();
		var json = formToJson("#creationAnnulation");
		enregistrerAbandon(json,linkStudent);
		$("#abandonModal").modal('hide');
	});
	
	
};