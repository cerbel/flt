$(document).ready(function(){
	
	$('#btnBack').on('click', function() {
		
		window.location.replace("listSites.action");
		
	});
	
	$('#btnSave').on('click', function() {
		
		var errors_rows = '';
		
		
		var idSite   		= $( "#idSite" ).val();
		var nameSite    	= $( "#nameSite" ).val();
		var idCurrencySite  = $( "#idCurrencySite" ).val();
		var flagSite        = $('input[name=flag]:checked', '#formSite').val();
		
		
		if (validationsAttributes.isNullOrBlack(nameSite))
			errors_rows = errors_rows.concat("- Enter valid Name<br>");
		
		
		if (errors_rows != ''){
			$('#alert-errors').modal('show');
			$('#alert-errors-content').html(errors_rows);		
			return false;
		}
		
		
		var params = "siteVO.idSite="+idSite+"&siteVO.nameSite="+nameSite+"&siteVO.localCurrency.idCurrency="+idCurrencySite+"&siteVO.flagSite="+flagSite;
		
		ajaxInvokeMethods.normalAjaxCallback("registerSite.action", params, callbackUpdateSite);
	});
		
	function callbackUpdateSite(data){
		window.location.replace("listSites.action");
	}
	
});