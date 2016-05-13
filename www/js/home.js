//js propre à la HOME page
function HomePage() {

	getAllDepartement();

	// affichage dynamique
	$('#login-form-link').click(function(e) {
		$("#login-form").delay(100).fadeIn(100);
		$("#register-form").fadeOut(100);
		$('#register-form-link').removeClass('active');
		$(this).addClass('active');
		e.preventDefault();
		$("#messageConnexion").css("display", "none");
	});
	$('#register-form-link').click(function(e) {
		$("#register-form").delay(100).fadeIn(100);
		$("#login-form").fadeOut(100);
		$('#login-form-link').removeClass('active');
		$(this).addClass('active');
		e.preventDefault();
		$("#messageConnexion").css("display", "none");
	});

	// Se connecter
	$("#login-form").on("submit", function(event) {
		event.preventDefault();
		var json = JSON.stringify(formToJson($(this)));

		$.ajax({
			url : linkUtil + 'connexion',
			type : 'POST',
			data : {
				donnee : json
			},
			success : function(reponse) {
				isConnected();
			},
			error : function(e) {
				
			}

		});

	});
	
	/**
	 * appel ajax qui permet l'inscription d'un user
	 */
		$("#register-form").on('submit',function(event) {
		    event.stopPropagation();
			event.preventDefault();
		}).validate({
			rules : {
				nom:{
					required:true,
					nowhitespace:true
				} ,
                prenom:{
                	required:true,
                	nowhitespace:true
                	},
                pseudo:{
                	nowhitespace:true,
                	required:true
                	},
				password1 :{
					required:true,
                	nowhitespace:true

					},
				password2 : {
					required:true,
					equalTo : "#password1"
				},
				email:{
					required:true,
					email:true
				}
			},
			 messages: {
				nom:{
					required:"vous devez metre un nom",
					nowhitespace:"pas d'espace"
				},
				prenom: {
					required:"vous devez metre un prenom",
					nowhitespace:"pas d'espace"

				},
	            pseudo:{
	            	required:"indiquez un pseudo",
					nowhitespace:"pas d'espace"
	            },
	            password1:{
	            	nowhitespace:"pas d'espace",
	            	required:"entrer mdp"
	            },
	            password2 : {
	  					required:"entrez un mdp",
	  					equalTo : "entrez un mdp identique"
	  			},
	  			email:{
	  					required:"champ vide",
	  					email:"entrez un email valid"
	  				}
			 },
			 
			submitHandler : function(form) {
				var json = JSON.stringify(formToJson($(form)));
				$.ajax({
					url : linkUtil + 'inscrire',
					type : 'POST',
					data : {
						donnee : json
					},
					success : function(reponse) {
						isConnected();
					},
					error : function(e) {
						console.log(e);
						$("#messageConnexion").addClass("alert-danger");
						$("#messageConnexion").val("Un Message d'erreur");
						$("#messageConnexion").css("display", "block");
					}

				});
				
			},
			invalidHandler : function(event, validator) {
				console.log(validator);
			},
			success:function(element){
				 element.remove();
			 }
	})
};