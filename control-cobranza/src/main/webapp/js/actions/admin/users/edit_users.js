$(document).ready(function(){
	
	$('#btnBack').on('click', function() {
		
		window.location.replace("usersAdmin.action");
		
	});
	
	$('#btnSave').on('click', function() {
		
		var errors_rows = '';
		
		var isidUser   		= $( "#isidUser" ).val();
		var nameUser    	= $( "#nameUser" ).val();
		var idProfileUser  	= $( "#idProfileUser" ).val();
		var emailUser     	= $( "#emailUser" ).val();
		var cont			= 0;
		var arraySite = new Array();

		
		if (validationsAttributes.isNullOrBlack(nameUser))
			errors_rows = errors_rows.concat("- Enter User Name<br>");
		
		if (validationsAttributes.isNullOrBlack(emailUser))
			errors_rows = errors_rows.concat("- Enter E-Mail<br>");
		else
			if (!validationsAttributes.isEmailAddress(emailUser))
				errors_rows = errors_rows.concat("- Enter valid E-Mail <br>");
		
		if (validationsAttributes.isNullOrBlack(idProfileUser))
			errors_rows = errors_rows.concat("- Select Profile User<br>");

		$(".select").each(function(){
			if ( $(this).is(':checked') ) {
				arraySite.push($(this).val());
				cont = cont + 1;
			} 
		});

		if (cont == 0)
			errors_rows = errors_rows.concat("- Select Sities<br>");
		
		if (errors_rows != ''){
			$('#alert-errors').modal('show');
			$('#alert-errors-content').html(errors_rows);		
			return false;
		}
		
		var params = "userVO.nameUser="+nameUser+"&userVO.idProfileUser="+idProfileUser+"&userVO.emailUser="+emailUser+"&userVO.isidUser="+isidUser+"&userVO.listSite="+arraySite;
		

		ajaxInvokeMethods.normalAjaxCallback("modifyUser.action", params, callbackUpdateUser);
		
	});
		
	function callbackUpdateUser(data){
		window.location.replace("usersAdmin.action");
	}
	
});