var validationsAttributes = {};

/**
 * deprecated, use isNullOrBlack
 */
validationsAttributes.isNullOrSpaceValidatos = function (value){	
	
	if (value == null)
		return true;
	
	if (value == '')
		return true;
	
	if (value == undefined)
		return true;
	
	return false;
}


validationsAttributes.isNullOrBlack = function (value){	
	
	if (value == null)
		return true;
	
	if (value == '')
		return true;
	
	if (value == undefined)
		return true;
	
	return false;
}


validationsAttributes.isNullOrBlackAndMaxSize = function (value, maxlength){	
	
	if (value == null)
		return true;
	
	if (value == '')
		return true;
	
	if (value == undefined)
		return true;
	
	
	if (value.length > maxlength)
		return false;
	
	return false;
}

validationsAttributes.isNumber = function (value){	
	return !isNaN(parseFloat(value)) && isFinite(value);
}

validationsAttributes.isPercentValue = function (value){	
	
	if (!validationsAttributes.isNumber(value)){
		return false
	}else{
		
		if (value > 100 || value < 0)
			return false
	}
	return true;
}


validationsAttributes.hasBlacksChars = function (value){
	return value.indexOf(' ') >= 0;
}


validationsAttributes.isEmailAddress = function (value){
	var pattern = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/; 
	return pattern.test(value);
}



//Number.prototype.format = function(){
//
//	var number = new String(this);
//
//	var result = '';
//
//	while( number.length > 3 )
//
//	{
//
//	 result = '.' + number.substr(number.length - 3) + result;
//
//	 number = number.substring(0, number.length - 3);
//
//	}
//
//	result = number + result;
//
//	return result;
//
//	};
	
	