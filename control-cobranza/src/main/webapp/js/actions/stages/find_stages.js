$(document).ready(function(){
	

	
	$('#idFilter').on('click', function() {
		
		var idSiteStage  = $('#idSiteStage').val();//numero factura
		var idTypeStage  = $('#idTypeStage').val();//estado SI o NO
		
//		var params = "stageVO.idSiteStage="+idSiteStage+"&stageVO.idTypeStage="+idTypeStage;
//		ajaxInvokeMethods.postAjaxCallback("listStages.action", params, callbackListStages);
		
		var params = "filtroPagoCobranzaVO.factura="+idSiteStage+"&filtroPagoCobranzaVO.estado="+idTypeStage;
//		alert(params);
		ajaxInvokeMethods.postAjaxCallback("listStages.action", params, callbackListStages);
	});
	
	
	/*CALL BACK FUNCTIONS*/
	
	
//	function callbackDeleteStage(data){
//		
//		$('#alert-confirm').modal('hide');
//		
//		$("#idFilter").trigger("click");
//	}

	
	
	function callbackListStages(data){
		
		
		var statusMessageError = data.statusMessageError;
//		var listStagesVO       = data.listStagesVO;
		var listPagoVO		   = data.listPagoVO;
		
		$("#table-stages-body").empty();
		for (var i=0; i<listPagoVO.length; i++){
			
			var row = "";
			
//			row = row.concat("<tr>");
//			row = row.concat("<td>" + listPagoVO[i].rut + "</td>");
//			row = row.concat("<td>" + listPagoVO[i].nombre + "</td>");
//			row = row.concat("<td>" + listPagoVO[i].fechaV + "</td>");
//			row = row.concat("<td>" + listPagoVO[i].observacion + "</td>");
//			row = row.concat("<td>" + listPagoVO[i].monto + "</td>");
//			row = row.concat("<td>" + listPagoVO[i].factura + "</td>");
//			row = row.concat("<td>" + listPagoVO[i].estado + "</td>");

				
			if(listPagoVO[i].estado!="PAGADO"){
				row = row.concat("<tr>");
				row = row.concat("<td>" + listPagoVO[i].rut + "</td>");
				row = row.concat("<td>" + listPagoVO[i].nombre + "</td>");
				row = row.concat("<td>" + listPagoVO[i].fechaV + "</td>");
				row = row.concat("<td>" + listPagoVO[i].observacion + "</td>");
				row = row.concat("<td>" + listPagoVO[i].monto + "</td>");
				row = row.concat("<td>" + listPagoVO[i].factura + "</td>");
				row = row.concat("<td>" + listPagoVO[i].estado + "</td>");
				row = row.concat("<td>" + "<a class='btn_table' title='View Stage'  onClick='cambiarEstadoFactura("+listPagoVO[i].factura+","+listPagoVO[i].rut+")' id='btnEstado-"+listPagoVO[i].estado+"-"+listPagoVO[i].factura+"-"+listPagoVO[i].rut+"'  ><i class='icon-list'></i></a>" + "</td>");
				row = row.concat("</tr>");
			}else{
				row = row.concat("<tr bgcolor='#C0C0C0'>");
				row = row.concat("<td>" + listPagoVO[i].rut + "</td>");
				row = row.concat("<td>" + listPagoVO[i].nombre + "</td>");
				row = row.concat("<td>" + listPagoVO[i].fechaV + "</td>");
				row = row.concat("<td>" + listPagoVO[i].observacion + "</td>");
				row = row.concat("<td>" + listPagoVO[i].monto + "</td>");
				row = row.concat("<td>" + listPagoVO[i].factura + "</td>");
				row = row.concat("<td>" + listPagoVO[i].estado + "</td>");
				row = row.concat("<td>" + "<a class='btn_table' title='View Stage' id='btnEstado-"+listPagoVO[i].estado+"-"+listPagoVO[i].factura+"-"+listPagoVO[i].rut+"' disabled><i class='icon-list'></i></a>" + "</td>");
				row = row.concat("</tr>");
			};
			
//			row = row.concat("<td>" +
//								"<a class='btn_table' title='View Stage' id='btnEstado-"+listPagoVO[i].factura+"' ><i class='icon-list'></i></a>" + 
//								"<a class='btn_table' href='viewStageHistory.action?idEstadoFactura.factura="+listPagoVO[i].factura+"' title='View Stage' id='btnEstado-"+listPagoVO[i].factura+"' value='"+listPagoVO[i].factura+"' ><i class='icon-list'></i></a>" +           
//					 			"<a class='btn_table' href='viewStageHistory.action?stageVO.idStage="+listPagoVO[i].factura+"' title='View Stage'><i class='icon-list'></i></a>" +
//							"</td>");
			
//			row = row.concat("</tr>");
			
			
			$("#table-stages-body").append(row);
			
			
			
			
//			$('#'+listStagesVO[i].idStage).unbind( "click" );
//			$('#'+listStagesVO[i].idStage).on('click', function() {
//
//				var idStage = this.id;
//				
//				$('#alert-confirm-acept').unbind( "click" );
//				$('#alert-confirm').modal('show');
//				$('#alert-confirm-content').html('This action delete the stage, are you sure to continue?');
//				$('#alert-confirm-acept').on('click', function() {
//					
//					var params = "stageVO.idStage="+idStage;
//					ajaxInvokeMethods.postAjaxCallback("deleteStage.action", params, callbackDeleteStage);
//				});
//				
//			});
		
		 

//		$('#btnEstado-'+listPagoVO[i].estado+'-'+listPagoVO[i].factura+'-'+listPagoVO[i].rut).on('click',function(){
//			var estado	= this.id.split('-')[1];
//			var factura	= this.id.split('-')[2];
//			var rut;
//			
//			if(this.id.split('-')[4]!=null){
//				rut		= this.id.split('-')[3]+"-"+this.id.split('-')[4];
//			}else{
//				rut		= this.id.split('-')[3];
//			}
//			alert(estado+"\n "+factura+"\n "+rut)
//			if(estado!='PAGADO')
//				cambiarEstadoFactura(factura,rut);
//			
//			var params	= "idEstadoFacturaVO.factura="+factura+"&idEstadoFacturaVO.rut="+rut;
//			ajaxInvokeMethods.postAjaxCallback("cambiaEstado.action", params,function(){
//				
//				var idSiteStage  = $('#idSiteStage').val();//numero factura
//				var idTypeStage  = $('#idTypeStage').val();//estado SI o NO
//				
//				var params = "filtroPagoCobranzaVO.factura="+idSiteStage+"&filtroPagoCobranzaVO.estado="+idTypeStage;
//				ajaxInvokeMethods.postAjaxCallback("listStages.action", params, callbackListStages);
//				
//			});		
//		});//esta cierra
			
		function llama(){
			var idSiteStage  = $('#idSiteStage').val();//numero factura
			var idTypeStage  = $('#idTypeStage').val();//estado SI o NO
			
			var params = "filtroPagoCobranzaVO.factura="+idSiteStage+"&filtroPagoCobranzaVO.estado="+idTypeStage;
			ajaxInvokeMethods.postAjaxCallback("listStages.action", params, callbackListStages);
		}	
			
//		$('#btnEstado-'+listPagoVO[i].factura).on('click',function(){
//
//				var idEstadoFactura = this.id.split('-')[1];
//				var rutFactura		= listPagoVO[idEstadoFactura].rut;
//				var estado			= listPagoVO[idEstadoFactura].estado;
//				
//				
//				cambiarEstadoFactura(idEstadoFactura,rutFacura);
//			
//				var params 			= "idEstadoFacturaVO.factura="+idEstadoFactura+"&idEstadoFacturaVO.rut="+rutFactura;
//
//				
//				ajaxInvokeMethods.postAjaxCallback("cambiaEstado.action", params, function(){
//					
//					var idSiteStage  = $('#idSiteStage').val();//numero factura
//					var idTypeStage  = $('#idTypeStage').val();//estado SI o NO
//					
//					var params = "filtroPagoCobranzaVO.factura="+idSiteStage+"&filtroPagoCobranzaVO.estado="+idTypeStage;
//					ajaxInvokeMethods.postAjaxCallback("listStages.action", params, callbackListStages);
//					
//				});
//				
//			});
		
		 }
		
		
		
	}//termino callback
	
	
	
//	$('#btnEstado-factura').click(function(){
//		$('#btnEstado-factura').unbind();
//		$('#Modal').modal('hide');
//		var params 			= "idEstadoFacturaVO.factura="+idEstadoFactura+"&idEstadoFacturaVO.rut="+rutFactura;
//		ajaxInvokeMethods.postAjaxCallback("cambiaEstado.action", params, function(){
//			var idSiteStage  = $('#idSiteStage').val();//numero factura
//			var idTypeStage  = $('#idTypeStage').val();//estado SI o NO
//			
//			var params = "filtroPagoCobranzaVO.factura="+idSiteStage+"&filtroPagoCobranzaVO.estado="+idTypeStage;
//			ajaxInvokeMethods.postAjaxCallback("listStages.action", params, callbackListStages);
//		});
//	});	
	
	
//	function cambiarEstadoFactura(idEstadoFactura,rutFactura){
//		$('#Modal').modal('show'); 
//		$('#btnEstado-factura').unbind();
//		$('#btnEstado-factura').click(function(){
//			
//			$('#Modal').modal('hide');
//			var params 			= "idEstadoFacturaVO.factura="+idEstadoFactura+"&idEstadoFacturaVO.rut="+rutFactura;
//			ajaxInvokeMethods.postAjaxCallback("cambiaEstado.action", params, function(){
//				var idSiteStage  = $('#idSiteStage').val();//numero factura
//				var idTypeStage  = $('#idTypeStage').val();//estado SI o NO
//				
//				var params = "filtroPagoCobranzaVO.factura="+idSiteStage+"&filtroPagoCobranzaVO.estado="+idTypeStage;
//				ajaxInvokeMethods.postAjaxCallback("listStages.action", params, callbackListStages);
//			});
//		});
//	}
	
	
	
});