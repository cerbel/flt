<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
<jsp:directive.include file="/pages/templates/header_html.jsp" />
</head>

<body>
	<script src="js/actions/stages/find_stages.js"></script>
	<script type="text/javascript">
		function cambiarEstadoFactura(idEstadoFactura,rutFactura){
			
			var factura	= idEstadoFactura.toString();
			var rut	= rutFactura.toString();
			
			
		/* 	if(this.id.split('-')[4]!=null){
				rut		= this.id.split('-')[3]+"-"+this.id.split('-')[4];
			}else{
				rut		= this.id.split('-')[3];
			}  */
			alert(factura+"    "+rut)
			
/* 			$('#Modal').modal('show'); 
			
					
			$('#btnEstado-factura').click(function(idEstadoFactura,rutFactura){
				
				$('#btnEstado-factura').unbind();
				$('#Modal').modal('hide');
				var params 			= "idEstadoFacturaVO.factura="+idEstadoFactura+"&idEstadoFacturaVO.rut="+rutFactura;
				ajaxInvokeMethods.postAjaxCallback("cambiaEstado.action", params, function(){
					var idSiteStage  = $('#idSiteStage').val();//numero factura
					var idTypeStage  = $('#idTypeStage').val();//estado SI o NO
					
					var params = "filtroPagoCobranzaVO.factura="+idSiteStage+"&filtroPagoCobranzaVO.estado="+idTypeStage;
					ajaxInvokeMethods.postAjaxCallback("listStages.action", params, null);
				});
			});	 */
			
		};
	</script>
	<div id="wrapper">

		<jsp:directive.include file="/pages/templates/head_body.jsp" />

		<div class="body_page">
			<div class="body_content">

				<form action="home.action" class="form_general">
					<fieldset>
						<legend>Facturas</legend>

						<div class="column_form">

							<label class="control-label">N° Factura:</label> <input
								id="idSiteStage" type="text" class="input-block-level">
							<%-- <select id="idSiteStage">
								<option value="">All Sites</option>
							  
								<s:iterator value="#session.userSessionVO.listSiteVO">
									<option value="<s:property value="idSite"/>"><s:property value="nameSite"/></option>
								</s:iterator>
							</select>  --%>

						</div>


						<div class="column_form">

							<label class="control-label">Estado Factura:</label> <select
								id="idTypeStage">
								<option value="">TODAS</option>
								<option value="NO PAGADO">NO PAGADO</option>
								<option value="PAGADO">PAGADO</option>
								<%-- 	<s:iterator value="#session.listTypeStageSession">
									<option value="<s:property value="idGeneralCombo"/>"><s:property value="nameGeneralCombo"/></option>
								</s:iterator> --%>
							</select>

						</div>



						<div style="clear: both;"></div>
					</fieldset>


				</form>

				<div class="well">
					<div class="buttoms">
						<button class="btn btn-success" type="button" id="idFilter">Filtrar</button>
					</div>
				</div>

				<div id="content-table">
					<!-- <div id="content-table" style="display: none;"> -->
					<!-- 					<div class="head_table_icons"> -->
					<!-- 						<a class="btn_table" href="createUser.action" title="Download Report Excel"><i class="icon-download-alt"></i></a> -->
					<!-- 					</div> -->

					<table class="table table-hover t-f-h-stages">
						<thead class="header">
							<tr>
								<!-- 								<th>Select</th> -->
								<!-- <th>Stage ID</th>
								<th>Stage Name</th>
								<th>Site</th>
								<th>Stage Type</th>
								<th>Status</th>
								<th>Period</th>
								<th>Date</th>
								<th>User ID</th>
								<th style='text-align:right;'>Units</th>
								<th style='text-align:right;'>Trn. Price</th>
								<th style='text-align:right;'>Duties</th>
								<th style='text-align:right;'>Freight & Others</th>
								<th>Action</th> -->

								<th>Rut Proveedor</th>
								<th>Nombre Proveedor</th>
								<th>Fecha Vencimiento Proveedor</th>
								<th>Observaci&oacute;n</th>
								<th>Monto de Pago Proveedor</th>
								<th>N° Factura Proveedor</th>
								<th>Estado</th>
								<th>Acción</th>


							</tr>
						</thead>

						<tbody id="table-stages-body"></tbody>
					</table>

				</div>

			</div>

		</div>

		<jsp:directive.include file="/pages/templates/footer_body.jsp" />

	</div>


	<div id="Modal" class="modal hide fade" tabindex="-1"
		style="left: 50%">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h3 id="myModalLabel">Mensaje</h3>
		</div>
		<div class="modal-body">
			<p id="alert-message-conten">
				<strong>Desea Cambiar el estado de la factura ?</strong>
			</p>
			<p id="alert-message-content"></p>
		</div>
		<div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
        	<button id="btnEstado-factura" type="button" class="btn btn-primary">Aceptar</button>
			<!-- <button id="alert-message-acept" class="btn btn-success" data-dismiss="modal">OK</button> -->
		</div>
	</div>
	


	<!-- Dialog Confirm -->
	<!-- <div id="content-table" class="modal hide fade" tabindex="-1"
		style="left: 50%">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h3 id="myModalLabel">Action Confirm</h3>
		</div>
		<div class="modal-body">
			<p id="alert-confirm-content"></p>
		</div>
		<div class="modal-footer">
			<button class="btn" data-dismiss="modal">Cancelar</button>
			<button id="alert-confirm-acept" class="btn btn-success">Confirmar</button>
		</div>
	</div> -->



</body>
</html>