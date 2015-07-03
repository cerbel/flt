$(document).ready(function(){
	
	$("#saveData").click(function(){
		
		var sitID   		= $( "#sitID" ).val();
		var errors_rows = '';
		
		if (sitID == ''){
			errors_rows = "Please, select site for upload the file plan";
		}
		
		if (errors_rows != ''){
			$('#alert-errors').modal('show');
			$('#alert-errors-content').html(errors_rows);		
			return false;
		}
		
		$('#modal-loading').modal("show");
//		event.preventDefault();
		
		var url ='uploadExcel.action?sitID='+sitID+'&iphType=P';
		var options = { 
			url: url,
			dataType:"json",
			success: function (data) {
				
				var statusMessageError = data.statusMessageError;
				if (statusMessageError != ""){
					$('#alert-errors').modal('show');
					$('#alert-errors-content').html(statusMessageError);		
					$('#modal-loading').modal("hide");
					return false;
				}
				
				
				var listErrorFields = data.listErrorFields;
				var statusMessage = data.statusMessage;
				
				

				if (data.detailLastUpdate == "")
					$('#lastUploadP').text("--");
				else
					$('#lastUploadP').text(data.detailLastUpdate);
				
				if (listErrorFields.length>0){
					$('#modal-search-products').modal('show');
					$("#table-item-master-tbody").empty();
					for (var i=0; i<listErrorFields.length; i++){
						var row = "";
						row = row.concat("<tr>");
						row = row.concat("<td>"+listErrorFields[i].line+"</td>");
						row = row.concat("<td>"+listErrorFields[i].error+"</td>");
						row = row.concat("</tr>");
		
						$("#table-item-master").append(row);
					}
					$('#modal-loading').modal("hide");
				}else{
					$('#modal-loading').modal("hide");
					
					if (statusMessage == ""){
						$('#alert-errors').modal('show');
						$('#alert-errors-content').html(statusMessageError);		
					}else{
						$('#alert-message').modal('show');
						$('#alert-message-content').html(statusMessage);		
						
						$('#alert-message-acept').on('click', function() {
							window.location.replace("home.action");
						});
					}
				}
			}
		}; 
		
	
		$('#data').ajaxSubmit(options);
	    
		$('#data').submit(); //aqui es el id del formulario del jsp cambiar
	
		
		
	});
	
	$("#saveDataF").click(function(){

		var sitID   		= $( "#siteIDF" ).val();
		var errors_rows = '';
		
		if (sitID == ''){
			errors_rows = "Please, select site for upload the file forecast";
		}
		
		if (errors_rows != ''){
			$('#alert-errors').modal('show');
			$('#alert-errors-content').html(errors_rows);		
			return false;
		}
		
		$('#modal-loading').modal("show");
//		event.preventDefault();

		
		var url ='uploadExcel.action?sitID='+sitID+'&iphType=F';
		var options = { 
			url: url,
			dataType:"json",
			success: function (data) {
				
				var statusMessageError = data.statusMessageError;
				if (statusMessageError != ""){
					$('#alert-errors').modal('show');
					$('#alert-errors-content').html(statusMessageError);	
					$('#modal-loading').modal("hide");
					return false;
				}
				
				
				var listErrorFields = data.listErrorFields;
				
				var statusMessage = data.statusMessage;
				
				
				if (data.detailLastUpdate == "")
					$('#lastUploadF').text("--");
				else
					$('#lastUploadF').text(data.detailLastUpdate);
				
				if (listErrorFields.length>0){
					$('#modal-search-products').modal('show');
					$("#table-item-master-tbody").empty();
					for (var i=0; i<listErrorFields.length; i++){
						var row = "";
						row = row.concat("<tr>");
						row = row.concat("<td>"+listErrorFields[i].line+"</td>");
						row = row.concat("<td>"+listErrorFields[i].error+"</td>");
						row = row.concat("</tr>");
		
						$("#table-item-master").append(row);
					}
					$('#modal-loading').modal("hide");
				}else{
					$('#modal-loading').modal("hide");
					if (statusMessage == ""){
						$('#alert-errors').modal('show');
						$('#alert-errors-content').html(statusMessageError);		
					}else{
						$('#alert-message').modal('show');
						$('#alert-message-content').html(statusMessage);	
						
						
						$('#alert-message-acept').on('click', function() {
							window.location.replace("home.action");
						});
					}

				}

			}
		}; 
		
	
		$('#dataF').ajaxSubmit(options);
	    
		$('#dataF').submit(); //aqui es el id del formulario del jsp cambiar		
		
			
		
		return false;
		
	});
	
	$("#hrDownP").on('click', function() {
		var sitID = $( "#sitID" ).val();
		var errors_rows = '';
		
		if (sitID == ''){
			errors_rows = "Please, select site for download the file plan";
		}

		if (errors_rows != ''){
			$('#alert-errors').modal('show');
			$('#alert-errors-content').html(errors_rows);		
			return false;
		}
		
		var params = "sitID="+sitID + "&iphType=P";

		window.location.replace("downloadExcel.action?"+params);
		
	});

	$("#hrDownF").on('click', function() {
		var sitID = $( "#siteIDF" ).val();
		var errors_rows = '';
		
		if (sitID == ''){
			errors_rows = "Please, select site for download the file forecast";
		}
		
		if (errors_rows != ''){
			$('#alert-errors').modal('show');
			$('#alert-errors-content').html(errors_rows);		
			return false;
		}
		
		var params = "sitID="+sitID + "&iphType=F";

		window.location.replace("downloadExcel.action?"+params);
		
	});
	
	$("#sitID").on('change', function(){
	
		
		var sitID = $( "#sitID" ).val();
		var params = "sitID="+sitID + "&iphType=P";

		ajaxInvokeMethods.normalAjaxCallback("getLastUpload.action", params, callbackDownload);

		
	});
	
	$("#siteIDF").on('change', function(){
		
		var siteIDF = $( "#siteIDF" ).val();
		var params = "sitID="+siteIDF + "&iphType=F";

		ajaxInvokeMethods.normalAjaxCallback("getLastUpload.action", params, callbackDownload);

	});

	//*************************CALLBACK FUNCTIONS
	function callbackDownload(data){

		if (data.detailLastUpdate == "")
			$('#lastUpload'+data.iphType).text("--");
		else
			$('#lastUpload'+data.iphType).text(data.detailLastUpdate);


	}
});