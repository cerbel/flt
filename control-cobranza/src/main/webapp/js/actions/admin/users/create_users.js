$(document).ready(function(){
	
	$('#btnBack').on('click', function() {
		
		window.location.replace("usersAdmin.action");
	});
	
	
	
	$('#idRut').on('blur', function(){
		
		var rutProveedor = this.value;
		var params       = "rutProveedor="+rutProveedor;
		
		ajaxInvokeMethods.normalAjaxCallback("obtieneProveedor.action", params, function(data){
			
		$("#idNombre").val(data.nombreProveedor)
		
		});
	});
	
	
	$('#idNombre').on('blur',function(){
		
		var nombreProveedor = this.value;
		var params			= "nombreProveedor="+nombreProveedor;
		
		ajaxInvokeMethods.normalAjaxCallback("obtieneRutProveedor.action", params, function(data){
			
			var listProveedor = data.rutProveedor2;
			
	if(listProveedor.length>0){
			
			$("#select-proveedor").empty();
			for (var j=0; j<listProveedor.length; j++){
				var row = "<option value='"+listProveedor[j].rutProveedor+"+"+listProveedor[j].nombreProveedor+"'>"+listProveedor[j].nombreProveedor+"</option>";
				$("#select-proveedor").append(row);
			}
			$('#modal-seleccion-proveedor').modal('show'); 
			
			$('#btn-seleccion-proveedor').click(function(){
				var unido		= $("#select-proveedor").val();
				var cortado		= unido.split("+");
				var rut 		= cortado[0];
				var nombre 		= cortado[1];

				$('#idRut').val(rut);
				$('#idNombre').val(nombre);
			});
		
	}
//		}else{
//			$('#idRut').val(listProveedor[0].rutProveedor);
//			$('#idNombre').val(listProveedor[0].nombreProveedor);
//		}	
			
			
		});	
	});
	
	
	
	$('#btnSave').on('click', function() {
		
		var idRut   		= $( "#idRut" ).val();
		var idNombre    	= $( "#idNombre" ).val();
		var idFecha  		= $( "#idFecha" ).val();
		var idMonto     	= $( "#idMonto" ).val();
		var idFactura       = $( "#idFactura").val();
		var idObservacion	= $("#idObservacion").val();
				
		
    	var params ="pagoCobranzaVO.rut="+idRut+"&pagoCobranzaVO.nombre="+idNombre+"&pagoCobranzaVO.fechaV="+idFecha+"&pagoCobranzaVO.monto="+idMonto+"&pagoCobranzaVO.factura="+idFactura+"&pagoCobranzaVO.observacion="+idObservacion;
   		
    	if(idRut == "" || idNombre == "" || idFecha == "" || idMonto == "" || idFactura == "" ){ //  || idObservacion == ""
    		$('#alert-factura').modal('show'); 
    		
		}else{
			
			$( "#idRut" ).val('');
			$( "#idNombre" ).val('');
			$( "#idFecha" ).val('');
			$( "#idMonto" ).val('');
			$( "#idFactura").val('');
			$("#idObservacion").val('');
			
			ajaxInvokeMethods.normalAjaxCallback("saveUser.action", params, null);
		}
    	
	});
	
	function ValidarNumeros(object){
		numero = object.value;
		if (!/^([0-9])*$/.test(numero))
		object.value = numero.substring(0,numero.length-1);
	}
	
	
	//*************************CALLBACK FUNCTIONS
	
//	function callbackGuardarUsuario(data){
//		window.location.replace("usersAdmin.action");
//	}	
	
	
});