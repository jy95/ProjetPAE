var table; // pour les autres tables
var laBonneSection; // pour savoir sur quelle page on est
var linkUtil = "/v1/util/"; // chemin pour méthodes génétiques
var linkTeacher = "/v1/teacher/"; // chemin pour méthodes génétiques
var linkStudent = "/v1/student/"; // chemin pour méthodes génétiques
var TeacherHome = "TeacherHome"; // constante pour la page PROF
var StudentHome = "StudentHome"; // constante pour la page ETUD
var autorizedSuccessNotif = [ linkStudent + 'setProfil',
		linkStudent + 'enregistrerPartenaire',
		linkStudent + 'enregistrerDemande', linkUtil + 'seDeconnecter',
		linkTeacher + 'confirmerEncodage', linkTeacher + 'changerTypeUser',
		linkTeacher + 'setProfil', linkTeacher + 'enregistrerPartenaire',
		linkStudent + "enregistrerAbandon", linkTeacher + "enregistrerAbandon",
		linkTeacher + "updateMobility",
		linkTeacher + "UpdatePartenaireVisibility",
		linkStudent + "UpdatePartenaireVisibility" + linkTeacher + "validerDemande" , linkTeacher + 'validerDemande' ];
var notificationWaitting;

$(function() {

	Lobibox.notify.DEFAULTS = $.extend({}, Lobibox.notify.DEFAULTS, {
		// modifications pour les default des notifications
		soundPath : "/bower_components/lobibox/dist/sounds/"
	});

	$(document).ajaxError(function(event, xhr, options, ex) {
		Lobibox.notify('error', {
			title : "Une erreur s'est produite : ",
			msg : xhr.responseText,
		});
	});

	$(document).ajaxStart(function() {
		notificationWaitting = Lobibox.notify('info', {
			title : "Traittement en cours: ",
			msg : "Veuillez patienter",
			delay : false,
			position : 'center top',
			size : 'mini',
			sound : false
		});
	}).ajaxStop(function() {
		notificationWaitting.remove();
	});

	$(document).ajaxSuccess(function(event, xhr, options, ex) {

		if (jQuery.inArray(options.url, autorizedSuccessNotif) !== -1) {
			Lobibox.notify('success', {
				title : "Succès",
				msg : "Tâche accomplie avec succès"
			});
		}

	});

	laBonneSection = $("body > div:visible").first().attr("id");
	isConnected();
	useGoodJS(laBonneSection);

	// Barre de Recherche
	$(".navbar-form.navbar-left").on('submit', function(event) {
		event.stopPropagation();
		event.preventDefault();
		var searchSelect = $(this).find('select[name="SearchOption"]').val();
		var searchWord = $(this).find('input[name="SearchWord"]').val();
		if ( laBonneSection === TeacherHome && searchWord === 'cochon'  ){
			$(".section").hide();
			$("#easterEgg").show();
		} else {
			searchInfosTables(searchSelect, searchWord);
		}
	});

});

// fonction pour charger le bon js en fonction de la page
function useGoodJS(laBonneSection) {

	switch (laBonneSection) {

	case "StudentHome":
		$("#raisonAbandon").hide();
		StudentPage();
		break;
	case "TeacherHome":
		$("#raisonAbandon").show();
		TeacherPage();
		break;
	default:
		HomePage();
		break;
	}
};

function cacherOuMontrer(divID) {
	$("#" + divID).toggle();
};

function callDatePicker() {
	$(".datepicker").datepicker({
		format : "yyyy-mm-dd",
		defaultViewDate : {},
		onSelect : function(dateText, inst) {
			$('#' + inst.id).attr('value', dateText);
		}
	});
};

function DatetoString(date) {

	var dateString;
	if (date === undefined || date === null) {
		dateString = null;
	} else {
		var mois = date.monthValue;
		var jour = date.dayOfMonth;
		dateString = date.year + "-" + twoDigits(mois) + "-" + twoDigits(jour);
	}
	return dateString;
};

function twoDigits(n) {
	return n > 9 ? "" + n : "0" + n;
};
jQuery.validator.setDefaults({
	debug : true,
	success : "valid",
	errorElement : 'span',
	errorClass : 'help-block',
	highlight : function(element) {
		$(element).closest('.form-group').addClass('has-error');
	},
	unhighlight : function(element) {
		$(element).closest('.form-group').removeClass('has-error');
	},
	errorPlacement : function(error, element) {
		if (element.prop('type') === 'checkbox'
				|| element.prop('type') === 'radio') {
			error.appendTo(element.parent().parent());

		} else {
			error.insertAfter(element);
		}
	}
});

function IntervalDate() {
	var currentYear = parseInt(new Date().getFullYear());
	var options = "";
	var liste = [];

	for (var i = 0; i < 5; i++) {
		liste.push(currentYear.toString() + "-" + (++currentYear).toString());
	}

	$.each(liste, function(index, value) {
		options += " <option value=\"" + value + "\">" + liste[index]
				+ "</option>";
	});
	$(".dateRangeAcademie").html(options);
	$('.dateRangeAcademie').select2();
};

function fixTailleSelect2() {
	// raison obscure, il ne fonctionne pas sur les datatables
	$("select").select2({
		dropdownCssClass : 'bigdrop'
	});
}