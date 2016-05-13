function setProfil(json, link, identificant) {
	$.ajax({
		url : link + 'setProfil',
		type : 'POST',
		data : {
			donnee : json
		},
		success : function(reponse) {
			if (link === linkStudent ) {
				getProfil(link, identificant)
			} 
		},
		error : function(reponse) {
			console.log(reponse);
		}
	})
};
function enregistrerAbandon(json,link){
	$.ajax({
		url : link + 'enregistrerAbandon',
		type : 'POST',
		data : {
			donnee : JSON.stringify(json)
		},
		success : function(reponse) {
		//je dois bloquer les champs 
			table.ajax.reload();
			console.log(reponse);

		},
		error : function(reponse) {
			console.log(reponse);
		}
	})
};

function updateDocMobility(whoCallthis,flags) {
	//SUPER FONCTION
	$.ajax({
		url : linkTeacher + 'updateMobility',
		type : 'POST',
		data : {
			infos : JSON.stringify(flags),
			action: whoCallthis
		},
		success : function(reponse) {
		//je dois bloquer les champs 
			table.ajax.reload();
			console.log(reponse);
		},
		error : function(reponse) {
			console.log(reponse);
		}
	})
};

function enregistrerPartenaire(json,dts,link) {

	$.ajax({
		url : link + 'enregistrerPartenaire',
		type : 'POST',
		data : {
			donnee : json,
			departements : dts
		},
		success : function(reponse) {
			$("#partenaireModal").modal('hide');
			
			if ($("#enregistrerDemande").length === 1 ) {

			var option = " <option value=\"" + reponse["id"] + "\" data-valide=\"true\">"
			+ reponse["nomAffaire"] + "</option>";
			$('#enregistrerDemande select[name="partenaire"]').append(option);
			$('#enregistrerDemande select[name="partenaire"]').val(reponse["id"]).trigger("change");
			var form = $("#enregistrerDemande");
			var idPays = reponse["pays"]["id"];
			form.find('select[name="pays"]').val(idPays).trigger("change");
			form.find("select").prop("disabled", true);
			form.find('select[name="quadrimestre"]').prop("disabled", false);
			form.find('select[name="AnneeAcademique"]').prop("disabled", false);
			form.find('input[name="ville"]').val(reponse["ville"]);
			$("#boutonCreationPartenaire").prop("disabled", true);
			form.find('input[name="typeStage"][value="' + JSON.parse(json)["typeStage"] + '"]').prop('checked',true);
			form.find('input[name="typeStage"]').prop('disabled',true);
			
			}
		},
		error : function(reponse) {
			console.log(reponse);
		}
	})
};
function enregistrerDemande(json,validee,flag){
	var link;
	if ( flag === false ){
		link = linkStudent;
	} else {
		link = linkTeacher;
	}
	
	$.ajax({
		url : link + 'enregistrerDemande',
		type : 'POST',
		data : {
			donnee : json,
			validation:validee,
			teacher: flag
		},
		success : function(reponse) {
			
		},
		error : function(err) {
			console.log(err);

		}
})
};


function changeUserRight(map,type) {
	$.ajax({
		url : linkTeacher + 'changerTypeUser',
		type : 'POST',
		data : {
			users : JSON.stringify(map),
			typeUser : type
		},
		success : function(reponse) {
			table.ajax.reload();
		},
		error : function(err) {
			console.log(err);

		}
})
};

function enregistrerEncodage(map) {
	$.ajax({
		url : linkTeacher + 'confirmerEncodage',
		type : 'POST',
		data : {
			encodage : JSON.stringify(map)
		},
		success : function(reponse) {
			table.ajax.reload();
		},
		error : function(err) {
			console.log(err);

		}
})
};

function updateMobility(json,link) {
	$.ajax({
		url : link + 'confirmerEncodage',
		type : 'POST',
		data : {
			donnees : JSON.stringify(json)
		},
		success : function(reponse) {
			table.ajax.reload();
		},
		error : function(err) {
			console.log(err);

		}
})
};

function ValidateDemande(map) {
	
	$.ajax({
		url : linkTeacher + 'validerDemande',
		type : 'POST',
		data : {
			donnees : map
		},
		success : function(reponse) {
			table.ajax.reload();
		},
		error : function(err) {
			console.log(err);

		}
	})
};

function checkPartenaireExistant(json,dts,link) {
	
	
	$.ajax({
		url : linkUtil + 'checkPartenaire',
		type : 'POST',
		data : {
			partenaireAVerifier : JSON.parse(json)["nomComplet"]
		},
		success : function(reponse) {
			
			var save = reponse;
			var link = (laBonneSection === TeacherHome) ? linkTeacher : linkStudent; 
			
			if (reponse["id"] !== 0 ){
				// si un partenaire a été trouvé avec le nom complet
				Lobibox.confirm({
					title : "Confirmation",
				    msg: "Ce partenaire a été déclaré 'supprimé' du système. Voulez-vous l'utiliser et le valider ? ",
				    callback: function ($this, type, ev) {
				    	if (type === "yes") {
				    		utiliserPartenaireSupprime(link, reponse);
				    	} else {
				    		return false;
				    	}
				    }
				});
			} else {
				enregistrerPartenaire(json,dts,link);
			}
			
			
		},
		error : function(err) {

		}
	})
	
};

function utiliserPartenaireSupprime(link,partenaire) {
	partenaire["supprime"] = false;
	$.ajax({
		url : link + 'UpdatePartenaireVisibility',
		type : 'POST',
		data : {
			infos : JSON.stringify(partenaire)
		},
		success : function(reponse) {
			//table.ajax.reload();
			/*Mettre le reste du code dans la page de création demande (ou valider demande) */
			$("#partenaireModal").modal('hide');
			
			if ($("#enregistrerDemande").length === 1  ) {

			var option = " <option value=\"" + partenaire["id"] + "\" data-valide=\"true\">"
			+ partenaire["nomAffaire"] + "</option>";
			$('#enregistrerDemande select[name="partenaire"]').append(option);
			$('#enregistrerDemande select[name="partenaire"]').val(partenaire["id"]).trigger("change");
			var form = $("#enregistrerDemande");
			var idPays = partenaire["pays"]["id"];
			form.find('select[name="pays"]').val(idPays).trigger("change");
			form.find("select").prop("disabled", true);
			form.find('select[name="quadrimestre"]').prop("disabled", false);
			form.find('select[name="AnneeAcademique"]').prop("disabled", false);
			form.find('input[name="ville"]').val(partenaire["ville"]);
			$("#boutonCreationPartenaire").prop("disabled", true);
			form.find('input[name="typeStage"][value="' + partenaire["typeStage"] + '"]').prop('checked',true);
			form.find('input[name="typeStage"]').prop('disabled',true);
			
			}
		},
		error : function(err) {
			console.log(err);

		}
	})
};

function utiliserPartenaireSupprime2(link,partenaire) {
	
	$.ajax({
		url : link + 'UpdatePartenaireVisibility',
		type : 'POST',
		data : {
			infos : JSON.stringify(partenaire)
		},
		success : function(reponse) {
			table.ajax.reload();
		},
		error : function(err) {
			console.log(err);

		}
	})
};