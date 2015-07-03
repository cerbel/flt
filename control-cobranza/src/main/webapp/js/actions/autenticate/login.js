$(document)
	.ready(
		function() {
			
			

			$('#btnLogin')
					.on(
							'click',
							function() {

								var isid = $('#isidUser').val()
								var passwd = $('#passwdUser').val()

								// $('#alert-confirm-remove').modal('show');
								// $('#alert-confirm-remove-content').html('Are
								// you sure to remove the row sdfs dfsd
								// sdfdsf asdasd?');
								// $('#alert-confirm-remove-acept').on('click',
								// function() {
								// window.location.replace("usersAdmin.action");
								// });
								//		
								// return false;

								var errors_rows = '';

								if (validationsAttributes
										.isNullOrBlack(isid))
									errors_rows = errors_rows
											.concat("<li>Must enter a username (ISID)</li>");

								if (validationsAttributes
										.isNullOrBlack(passwd))
									errors_rows = errors_rows
											.concat("<li>Must enter a password</li>");

								if (errors_rows != '') {
									$('#alert-errors').modal('show');
									$('#alert-errors-content').html(
											errors_rows);
								} else {

									var params = "userVO.isidUser="
											+ isid
											+ "&userVO.passwdUser="
											+ passwd;
									ajaxInvokeMethods.postAjaxCallback(
											"autenticate.action",
											params, callbackSuccess);
								}
							});

			// *************************CALLBACK FUNCTIONS
			function callbackSuccess(data) {

				var statusMessageError = data.statusMessageError;

				var errors_rows = '';

				if (statusMessageError != '') {
					errors_rows = errors_rows.concat("<li>"
							+ statusMessageError + "</li>");

					$('#alert-errors').modal('show');
					$('#alert-errors-content').html(errors_rows);
				} else {

					window.location.replace("sites.action");
				}
			}
			
			
			$('.body_page').css('height', 'auto');
			$('.body_page').height($( document ).height() - 140); 
		});