//js propre aux Teacher
function TeacherPage() {
	getAllDepartement();
	IntervalDate();
	$("body").on('click','#ExportDemande',function(event){
		event.stopPropagation();
		event.preventDefault();
		var param = $('#SelectExportDemande').val();
		var paramString = "";
		$.each(param,function (index,val) {
			paramString += "_" + val;
		});
		
		window.location.href = '/v1/genererCsv/DemandeMobilite'+ paramString +'.csv';
	});
	
	$("body").on('click','#ExportMobilite',function(event){
		event.stopPropagation();
		event.preventDefault();
		var param = $('#SelectExportMobilite').val();
		var paramString = param;
		
		window.location.href = '/v1/genererCsv2/'+ paramString ;
	});
	
	$("body").on('click','#boutonCreationPartenaire',function(event){
		$("#CreationPartenaire :input").prop("disabled",false);
	});
	
	$('body').on('click','.modificationVisibilitePart',function(event){
		var infos = {};
		var pk = $(event.target).attr('id');
		pk = pk.replace('partN','');
		infos["pkPartenaire"] = pk;
		infos["numVersion"] = $(event.target).attr('data-numVersion');
		infos["supprime"] =	$(event.target).attr('data-action');
		utiliserPartenaireSupprime2(linkTeacher,infos);
	});
	
	$('body').on('click','.ValidationMobItem',function(event){
		event.stopPropagation();
		event.preventDefault();
		var form = $("#enregistrerDemandeProf");
		TeacherLoadPage('valideeDemande');
		var pk = $(event.target).attr('data-dep');
		var pkDemande = $(event.target).attr('data-id');
		getAllPartenairesAgree(pk);
		detailDemandeForAValidation(pkDemande);
		form.find('input[name="pkDemande"]').val(pkDemande);
	});
	
	$('body').on('click','.ValiderDemandePaimentItem',function(event){
		event.stopPropagation();
		event.preventDefault();
		var infos = {};
		infos["pkMobilite"] = $(event.target).attr('data-mobilite');
		updateDocMobility("updateDocPaiement",infos);
	});
	
	$("#creationAnnulation").on('submit',function(event){
		event.stopPropagation();
		event.preventDefault();
		var json = formToJson("#creationAnnulation");
		enregistrerAbandon(json,linkTeacher);
		$("#abandonModal").modal('hide');
	});
	
	
	$("#tableauPersonnalise").on("click", ".infoMobStud", function(event) {
		var whoPart = event.target.id.replace('mobiliteN', '');
		detailMobilite(whoPart,linkTeacher);
		$("#infoMobilite").modal('show');
		//callDatePicker();
	});
	
	$(".infoStud").on('click',function(event) {
		getAllTitre();
		getSexe();
		var whoPart = event.target.id.replace('stud', '');
		getProfilStudentForTeacher(whoPart);
		$("#infoStudModal").find('input[type="hidden"][name="pseudoUser"]').val(whoPart);
		$("#infoStudModal").modal('show');
	});
	
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
	
	/* Affichage infos */
	$("#tableauPersonnalise").on("click", ".infoPart", function(event) {
		getAllDepartement();
		var whoPart = event.target.id.replace('partN', '');
		getPartenaire(whoPart);
		$("#partenaireModal :input").prop("disabled", true);
		$("#partenaireModal select").prop("disabled", true);
		$(".modalClose .close").prop("disabled", false);
		$(":button.close").prop("disabled", false);
		$(".modalClose").prop("disabled", false);
		$("#partenaireModal .modal-title").text("Informations Partenaire");
		$("#partenaireModal").modal('show');
	});

	$("#tableauPersonnalise").on("click", ".infoStud", function(event) {
		getAllTitre();
		getSexe();
		var whoPart = event.target.id.replace('stud', '');
		getProfilStudentForTeacher(whoPart);
		$("#infoStudModal").find('input[type="hidden"][name="pseudoUser"]').val(whoPart);
		$("#infoStudModal").modal('show');
	});

	$("#tableauPersonnalise").on("click", ".delMob", function(event) {
		var whoPart = event.target.id.replace('Mob', '');
		var numVersion = $(event.target).attr('data-numVersion');
		$("#abandonModal").find('input[name="numVersion"]').val(numVersion);
		$("#abandonModal").find('input[name="mobAnnul"]').val(whoPart);
		$("#abandonModal").modal('show');
	});

	$('body').on('click', ".lien", function(event) {
		event.stopPropagation();
		event.preventDefault();
		TeacherLoadPage(event.target.id);
	});
	
	$('body').on('click','#CreerPartenaireSpecial',function(event){
		event.stopPropagation();
		event.preventDefault();
		//$("#partenaireModal").find('select[name="departements"]').show();
		$("#programme").prop("disabled", false);
		$("#pays").prop("disabled", false);
		$("#partenaire").prop("disabled", false);
		$('#CreationPartenaire')[0].reset();
		$('#boutonCreationPartenaire').prop("disabled", false);
		getAllDepartement();
		$("#partenaireModal").modal('show');
	});
	
	$("#tableauPersonnalise").on ('click','input[type="checkbox"]',function() {
		$(this).attr('checked', ($(this).is(':checked')));
	});
	
	$('body').on('click','.updateDocDepart,.updateDocRetour',function(event) {
				event.stopPropagation();
				event.preventDefault();
				var flags = {};
				var whoCallthis = $(event.target).attr('class');
				whoCallthis = whoCallthis.replace('btn btn-primary ','');
				
				var id = $(this).attr('id');
				var tableau = $("#"+id).closest('tr');
				id = parseInt(id.replace('MobEncodageID', ''));
				flags["id"] = id;
				var numVersion = $(this).attr('data-version');
				flags["numVersion"] = numVersion;
				var numLigne = ($(this).attr('data-numLigne'));
				var tableauBox = tableau.find('input[type="checkbox"]');
				tableauBox.each(function(index, val) {
					flags[this.name] = val.checked;
				});

				console.log(flags);
				// requete pour update la mobilité
				updateDocMobility(whoCallthis,flags);
	});
	
	$('body').on('click','#changeEncodageInput',function(event) {
		event.stopPropagation();
		event.preventDefault();
		var map = {};
		// Iterate over all selected checkboxes
		var test = $("#tableauPersonnalise").find(".changeEncodageItem:checked");
		test.each(function (index,val) {
			var id = $(this).attr('id');
			id = parseInt(id.replace('MobEncodageID', ''));
			var numVersion = $(this).attr('data-version');
			var numLigne = ($(this).attr('data-numLigne'));
			var tableau = $("#tableauPersonnalise tr:nth-child(" + (numLigne)+ ")");
			var tableauBox = tableau.find('input[type="checkbox"]');
			var proEco = tableauBox[1].checked;
			var mobilityTool = tableauBox[2].checked;
			var mobi =  tableauBox[3].checked;
			var flags = {};
			flags["numVersion"] = numVersion;
			flags["proEco"] = proEco;
			flags["mobi"] = mobi;
			flags["mobilityTool"] = mobilityTool;
			map[id] = flags;
		});
		enregistrerEncodage(map);
	});
	
	$('body').on('click','#changeRightsInput',function(event) {
				event.stopPropagation();
				event.preventDefault();
				var type = $("#changeRights :selected").val();
				var map = {};
				// Iterate over all selected checkboxes
				var test = $("#tableauPersonnalise").find(".changeUserGrant:checked");
				test.each(function () {
					var id = $(this).attr('id');
					id = id.replace('user', '');
					var numVersion = $(this).attr('data-version');
					map[id] = numVersion;
				});
				changeUserRight(map,type);
	});
	
	$("#profil-view-teacher").on('submit',function(event) {
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
			setProfil(json,linkTeacher,"profil-view-teacher");
			$("#infoStudModal").modal('hide');
			table.draw();
		},
		invalidHandler : function(event, validator) {
			console.log(validator);
		},
		success : function(element) {
			element.remove();
		}
	});
	
	$("#tableauPersonnalise").on("click", ".infoDemandeStud", function(event) {
		getAllDepartement();
		var whoPart = event.target.id.replace('IdDemande', '');
		detailDemande2(whoPart);
		StudentLoadPage("infoDemande");
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
			enregistrerPartenaire(json, dts,linkTeacher);
			table.draw();
		},
		invalidHandler : function(event, validator) {
			console.log(validator);
		},
		success : function(element) {
			element.remove();
		}
	});
	$("#enregistrerDemandeProf").on('submit', function(event) {
		event.stopPropagation();
		event.preventDefault();
	})
	.validate(
			{
				rules : {
					paysProf:{required : true},
					villeProf:{required : true},
					partenaireProf:{required : true},
					programmeProf:{required : true},
					quadriProf:{required : true},
					anneeAcademiqueProf:{required : true}
					
				},
				messages : {
					paysProf:{required : "pas vide"},
					villeProf:{required : "pas vide"},
					partenaireProf:{required : "pas vide"},
					programmeProf:{required : "pas vide"},
					quadriProf:{required : "pas vide"},
					anneeAcademiqueProf:{required : "pas vide"}
				},
				submitHandler : function(form) {
					var json = JSON.stringify(formToJson($(form)));
					ValidateDemande(json);
				},
				invalidHandler : function(event, validator) {
					console.log(validator);
				},
				success : function(element) {
					element.remove();
				}
			});

};

//Vieux code pour valider plusieurs mobilités d'un coup
// Plus valide
/*
$('body').on('click','#buttonValiderDemande',function(event){
	event.stopPropagation();
	event.preventDefault();

	var map = {};
	
	debugger;
	var test = $("#tableauPersonnalise").find(".ValidationMobItem:checked");
	// Iterate over all selected checkboxes
	test.each(function (index,val) {
		var id = $(this).attr('data-id');
		var numVersion = $(this).attr("data-numversion");
		var flags = {};
		flags["numVersion"] = numVersion;
		map[id] = flags;
	});
	ValidateDemande(map);
});
*/