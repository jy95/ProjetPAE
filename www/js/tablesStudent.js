function TableDemandeMobiliteStudent() {

	if (table instanceof Object) {
		table.destroy();
		$("#tableauPersonnalise").empty();
	}
	ColomnsGenerator("demandesStud");
	table = $("#tableauPersonnalise").DataTable({
		language: {
			url: '/js/French.json'
		},
		
		
		"searching": false,
		"ajax": {
			"url": linkStudent + "DemandesMobilitesStud",
			"type": "POST"
		},
		"aoColumns": [{
			"mData": "preference",
			"title": "Priorité :",
			"bSortable": true
		},{
			"mData": "programme",
			"title": "Type Mobilité :",
			"bSortable": true
		},{
			"mData": "pays.nom",
			"title": "Pays :",
			"bSortable": true,
			"defaultContent":"/"
		},{
			"mData": "typeStage",
			"title": "SMS/SMP :",
			"bSortable": true
		},{
			"mData": function(o, type) {
				return '<button type="button" class="btn btn-link infoDemandeStud" id="IdDemande'+o.id+'" >Infos</button>';
			},
			"title" : "Détails",
			"bSortable": false
		}]
	});
};


function TableMobiliteStudent() {

	if (table instanceof Object) {
		table.destroy();
		$("#tableauPersonnalise").empty();
	}
	ColomnsGenerator("mobilitesStud");
	table = $("#tableauPersonnalise").DataTable({
		language: {
			url: '/js/French.json'
		},
		
		
		"ajax": {
			"url": linkStudent + "MobilitesStud",
			"type": "POST"
		},
		"aoColumns": [{
			"mData": "demande.programme",
			"title": "Type Mobilité :",
			"bSortable": true
		},{
			"mData": "demande.typeStage",
			"title": "SMS/SMP :",
			"bSortable": true
		},{
			"mData": "anneeAcademique",
			"title": "Année académique :",
			"bSortable": true
		},{
			"mData": function(o, type) {
				var text = (o.annulation.description === null ? o.etat : 'ANNULEE' );
				return '' + text;
				
			},
			"title": "Etat :",
			"bSortable": true
		},{
			"mData": function(o, type) {
				return '<button type="button" class="btn btn-link infoMobStud" id="mobiliteN'+o.id+'" >Infos</button>';
			},
			"title" : "Détails",
			"bSortable": false
		},{
			"mData" : function(o, type) {
				return '<button type="button" class="btn btn-link glyphicon glyphicon-remove-sign delMob" data-numVersion="'
						+ o.numVersion
						+ '" id="Mob'
						+ o.id + '" >Annuler</button>';
			},
			"title" : "Abandonner :"
		}]
	});
};

// Code à supprimer dans x temps
function TableDocDepartStud(pk) {
	
	if (pk === undefined ){
		return false;
	}
	
	if (table2 instanceof Object) {
		table2.destroy();
		$("#tableauPersonnalise").empty();
	}

	table2 = $("#tableauDepart").DataTable({
		language: {
			url: '/js/French.json'
		},
		
		
		"ajax": {
			"url": linkStudent + "DocDepartStud",
			"type": "POST",
			"data"   : function( d ) {
		          d.identifiant = pk;
		    }
		},
		"aoColumns": [{
			"mData": function(o, type) {
				return '<div class="checkbox"><input name="contratBourse" type="checkbox" ' + (o.contratBourse ? 'checked' : '') + '></div>';
			},
			"title": "Contrat de bourse :"
		},{
			"mData": function(o, type) {
				return '<div class="checkbox"><input name="conventionStageOuEtude" type="checkbox" ' + (o.conventionStageOuEtude ? 'checked' : '') + '></div>';
			},
			"title": "Convention de stage / d\'études :"
		},{
			"mData": function(o, type) {
				return '<div class="checkbox"><input name="charteEtudiant" type="checkbox" ' + (o.charteEtudiant ? 'checked' : '') + '></div>';
			},
			"title": "Charte de l\'étudiant :"
		},{
			"mData": function(o, type) {
				return '<div class="checkbox"><input name="docEngagement" type="checkbox" ' + (o.docEngagement ? 'checked' : '') + '></div>';
			},
			"title": "Document d’engagement :"
		},{
			"mData": function(o, type) {
				return '<div class="checkbox"><input name="preuveTestLangue" type="checkbox" ' + (o.preuveTestLangue ? 'checked' : '') + '></div>';
			},
			"title": "Preuve du passage des tests linguistiques :"
		}]
	});
} ;

function TablePartenaires() {

	if (table instanceof Object) {
		table.destroy();
		$("#tableauPersonnalise").empty();
	}
	ColomnsGenerator("partenaires");
	table = $("#tableauPersonnalise").DataTable({
		language: {
			url: '/js/French.json'
		},
		"ajax": {
			"url": linkUtil + "PartenairesList",
			"type": "POST"
		},
		"aoColumns": [{
			"mData": "nomComplet",
			"title": "Nom Complet :",
			"bSortable": true
		}, {
			"mData": "pays.nom",
			"title": "Pays :",
			"bSortable": true
		}, {
			"mData": "ville",
			"title": "Ville :",
			"bSortable": true
		},{
			"mData": function(data, type) {
				if(data.agree===true){
					return "oui";}
				else{
					return"non";
				}
			},
			"title": "Agrée :",
			"bSortable": true
		},{
			"mData": function(o, type) {
				return '<button type="button" class="btn btn-link infoPart" id="partN'+o.id +'" >Infos</button>';
			},
			"title" : "Infos",
			"bSortable": false
		}]
	});
};
