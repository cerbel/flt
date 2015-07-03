var ajaxInvokeMethods = {};

ajaxInvokeMethods.normalAjaxCallback = function (url, params, callback){	
	$.ajax({
		url: url,
		cache: false,
		data: params,
		dataType: "json", 
		success: function(data) {
			callback(data);
		}
	});
};



ajaxInvokeMethods.postAjaxCallback = function (url, params, callback){
	$.ajax({
		  type: "POST",
		  url: url,
		  data: params,
		  success: callback,
		  dataType: 'json'
		});
};

