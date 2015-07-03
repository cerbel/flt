$(document).ready(function(){
	
	
	$('#btnFindProduts').on('click', function() {
		$('#modal-search-products').modal('show');
	});
	
	
	
	
	$('#nav-setup-exchange-currency a[href="#nav-setup-exchange-cur"]').click(function (e) {
		e.preventDefault();
		$('#nav-setup-exchange-currency a[href="#nav-setup-exchange-cur"]').tab('show');
	});
	
	$('#nav-setup-exchange-currency a[href="#nav-setup-exchange-macro-vars"]').click(function (e) {
		e.preventDefault();
		$('#nav-setup-exchange-currency a[href="#nav-setup-exchange-macro-vars"]').tab('show');
	});
	
	
	//Warehouse Temporal 
	$('#nav-warehousing a[href="#tab-warehouse-concepts"]').click(function (e) {
		e.preventDefault();
		
		var selIdSites       = $('#selIdSites').val();
		var params = "stageVO.idSiteStage="+selIdSites;
		ajaxInvokeMethods.postAjaxCallback("listConceptCostWarehouse.action", params, callBackListConceptCostWarehouse);
		
		$('#nav-warehousing a[href="#tab-warehouse-concepts"]').tab('show');
	});
	
	
	$('#nav-warehousing a[href="#tab-warehouse-ranges"]').click(function (e) {
		e.preventDefault();
		
		var selIdSites  = $('#selIdSites').val();
		var params = "stageVO.idSiteStage="+selIdSites;
		ajaxInvokeMethods.postAjaxCallback("listRangeWarehouse.action", params, callbackListRangeWarehouse);
		
		$('#nav-warehousing a[href="#tab-warehouse-ranges"]').tab('show');
	});
	
	$('#nav-warehousing a[href="#tab-warehouse-rates"]').click(function (e) {
		e.preventDefault();
		
		var params = "";
		ajaxInvokeMethods.postAjaxCallback("listRateWarehouse.action", params, callbackListRateWarehouse);
		
		$('#nav-warehousing a[href="#tab-warehouse-rates"]').tab('show');
	});
	
	
	//Local Transport
	$('#nav-localtransport a[href="#tab-localtransport-concepts"]').click(function (e) {
		e.preventDefault();
		
		var selIdSites       = $('#selIdSites').val();
		var params = "stageVO.idSiteStage="+selIdSites;
		ajaxInvokeMethods.postAjaxCallback("listConceptCostLocalTransport.action", params, callbackListConceptCostLocalTransport);
		
		$('#nav-localtransport a[href="#tab-localtransport-concepts"]').tab('show');
	});
	
	
	$('#nav-localtransport a[href="#tab-localtransport-ranges"]').click(function (e) {
		e.preventDefault();
		
		var selIdSites  = $('#selIdSites').val();
		var params = "stageVO.idSiteStage="+selIdSites;
		ajaxInvokeMethods.postAjaxCallback("listRangesLocalTransport.action", params, callbackListRangesLocalTransport);
		
		
		$('#nav-localtransport a[href="#tab-localtransport-ranges"]').tab('show');
	});
	
	$('#nav-localtransport a[href="#tab-localtransport-routes"]').click(function (e) {
		e.preventDefault();
		
		var params = "";
		ajaxInvokeMethods.postAjaxCallback("listRoutesLocalTransport.action", params, callbackListRoutesLocalTransport);
		
		$('#nav-localtransport a[href="#tab-localtransport-routes"]').tab('show');
	});
	
	
	
	
	
	
	
	$('#btnDeleteFilter').on('click', function() {
		ajaxInvokeMethods.postAjaxCallback("deleteFilterCustomDuty.action", null, callbackUpdateFilter);
		$("#tab-move-cost a[href='#tab-import-units']").trigger("click");
	});
	
	
	$('#btnSimulate').on('click', function() {
		
		
		var errors_rows = '';
		
		var selIdSites       = $('#selIdSites').val();
		var nameStage        = $('#nameStage').val();
		var descriptionStage = $('#descriptionStage').val();
		
		
		
		if (validationsAttributes.isNullOrBlack(selIdSites))
			errors_rows = errors_rows.concat("<li>Select a valid site</li>");
		
		
		if (validationsAttributes.isNullOrBlack(nameStage))
			errors_rows = errors_rows.concat("<li>Select a valid name</li>");
		
		if (validationsAttributes.isNullOrBlack(descriptionStage))
			errors_rows = errors_rows.concat("<li>Select a valid description</li>");
		
		
		if (errors_rows != ''){
			$('#alert-errors').modal('show');
			$('#alert-errors-content').html(errors_rows);		
			return false;
		}
		$('#btnDeleteFilter').trigger("click");
		
		$('#modal-loading').modal({shown:true,  keyboard: false });
		
		var params = "stageVO.idSiteStage="+selIdSites+"&stageVO.nameStage="+nameStage+"&stageVO.descriptionStage="+descriptionStage;
		ajaxInvokeMethods.postAjaxCallback("simulateStage.action", params, callbackSimulateStage);
	});
	
	
	
	$('#selIdSites').on('change', function() {
		
		var selIdSites       = $('#selIdSites').val();
		var nameStage        = $('#nameStage').val();
		var descriptionStage = $('#descriptionStage').val();

		//S�lo validaremos como obligatorio el id del site
		if (selIdSites == '')
			return false;
		
		var errors_rows = '';
		if (validationsAttributes.isNullOrBlack(nameStage))
			errors_rows = errors_rows.concat("<li>Select a valid name</li>");
		
		if (validationsAttributes.isNullOrBlack(descriptionStage))
			errors_rows = errors_rows.concat("<li>Select a valid description</li>");
		
		
		if (errors_rows != ''){
			$('#alert-errors').modal('show');
			$('#alert-errors-content').html(errors_rows);	
			
			 $('#selIdSites').val('');
			return false;
		}
		
		
		
		//comenzando loading, retirar en callback
		$('#modal-loading').modal({shown:true,  keyboard: false });
		
		var params = "stageVO.idSiteStage="+selIdSites+"&stageVO.nameStage="+nameStage+"&stageVO.descriptionStage="+descriptionStage;
		
		ajaxInvokeMethods.postAjaxCallback("initSimulate.action", params, callbackInitSimulate);
		
	});
	
	
	$('#btnNewStage').on('click', function() {
		
		$('#alert-confirm').modal('show');
		$('#alert-confirm-content').html('This action create a new stage, are you sure?');
		$('#alert-confirm-acept').on('click', function() {
			window.location.replace("moveCost.action");
		});
	});
	
	

	$('#tab-move-cost a[href="#tab-import-units"]').click(function (e) {
		e.preventDefault();
		
		$('#loading-local').show();
		
		ajaxInvokeMethods.postAjaxCallback("initImportUnit.action", "", callbackInitImportUnit);
		
		$('#tab-move-cost a[href="#tab-import-units"]').tab('show'); 
	})
	
	
	
	$('#tab-move-cost a[href="#tab-item-master"]').click(function (e) {
		e.preventDefault();
		
		$('#loading-local').show();
		
		var selIdSites       = $('#selIdSites').val();
		
		var params = "stageVO.idSiteStage="+selIdSites;
		ajaxInvokeMethods.postAjaxCallback("initItemMaster.action", params, callbackInitItemMaster);
		
		$('#tab-move-cost a[href="#tab-item-master"]').tab('show'); 
	});
	
	$('#tab-move-cost a[href="#tab-setup"]').click(function (e) {
		e.preventDefault();
		
		
		var selIdSites       = $('#selIdSites').val();
		
		var params = "stageVO.idSiteStage="+selIdSites;
		ajaxInvokeMethods.postAjaxCallback("initSetupMacroExchange.action", params, callbackInitSetupMacroExchange);		
		
		
		
		$('#tab-move-cost a[href="#tab-setup"]').tab('show'); 
	});
	$('#tab-move-cost a[href="#tab-freight"]').click(function (e) {
		e.preventDefault();
		
		var selIdSites       = $('#selIdSites').val();
		
		var params = "stageVO.idSiteStage="+selIdSites;
		ajaxInvokeMethods.postAjaxCallback("initFreightAir.action", params, callbackInitFreightAir);
		
		$('#tab-move-cost a[href="#tab-freight"]').tab('show'); 
	});
	
	
	$('#freights-tab a[href="#freight-aerial"]').click(function (e) {
		e.preventDefault();
		
		$('#freights-tab a[href="#freight-aerial"]').tab('show'); 
	});
	
	$('#freights-tab a[href="#freight-APO"]').click(function (e) {
		e.preventDefault();
		
		
		var params = "";
		ajaxInvokeMethods.postAjaxCallback("listAPOFreights.action", params, callBackListAPOFreights);
		
		$('#freights-tab a[href="#freight-APO"]').tab('show'); 
	});
	
	
	$('#concept-other-freight-ok').click(function (e) {
		
		$('#freights-tab a[href="#freight-marine"]').trigger("click");
	});
	
	
	
	
	
	
	$('#freights-tab a[href="#freight-marine"]').click(function (e) {
		e.preventDefault();
		
		//limpiamos los eventos
//		$('#freight-others-container').unbind( "blur" );
//		$('#freight-others-internal-freight').unbind( "blur" );
//		$('#freight-others-others-cost').unbind( "blur" );
//		$('#freight-others-destination').unbind( "blur" );
//		$('#freight-others-palettes').unbind( "blur" );
		
		

//		$('#freight-others-container').on('blur', function() {
//	
//			var freightOthersAttributes = "FRO_CONTAINER_RFER-" + this.value + "-"+	$('#freight-others-id-site-source').val();
//			var params = "freightOthersAttributes="+freightOthersAttributes;
//			if (!validationsAttributes.isNumber(this.value)){
//				$('#alert-errors').modal('show');
//				$('#alert-errors-content').html("Only numbers are allowed");
//				this.focus();
//			}else{
//				ajaxInvokeMethods.postAjaxCallback("freightOthersUpdateAttributes.action", params, null);
//			}
//		});
//		
//		$('#freight-others-internal-freight').on('blur', function() {
//			
//			var freightOthersAttributes = "FRO_INTER_FREIGHT-" + this.value + "-"+	$('#freight-others-id-site-source').val();
//			var params = "freightOthersAttributes="+freightOthersAttributes;
//
//			  			if (!validationsAttributes.isNumber(this.value)){
//							$('#alert-errors').modal('show');
//							$('#alert-errors-content').html("Only numbers are allowed");
//							this.focus();
//						}else{
//							ajaxInvokeMethods.postAjaxCallback("freightOthersUpdateAttributes.action", params, null);
//						}
//		});
//		
//		$('#freight-others-others-cost').on('blur', function() {
//			
//			var freightOthersAttributes = "FRO_OTHERS-" + this.value + "-"+	$('#freight-others-id-site-source').val();
//			var params = "freightOthersAttributes="+freightOthersAttributes;
//			
//			if (!validationsAttributes.isNumber(this.value)){
//				$('#alert-errors').modal('show');
//				$('#alert-errors-content').html("Only numbers are allowed");
//				this.focus();
//			}else{
//				ajaxInvokeMethods.postAjaxCallback("freightOthersUpdateAttributes.action", params, null);
//			}
//		});
//		
//		$('#freight-others-destination').on('blur', function() {
//			
//			var freightOthersAttributes = "FRO_DESTINATION_CHARGES-" + this.value + "-"+	$('#freight-others-id-site-source').val();
//			var params = "freightOthersAttributes="+freightOthersAttributes;
//			
//			if (!validationsAttributes.isNumber(this.value)){
//				$('#alert-errors').modal('show');
//				$('#alert-errors-content').html("Only numbers are allowed");
//				this.focus();
//			}else{
//				ajaxInvokeMethods.postAjaxCallback("freightOthersUpdateAttributes.action", params, null);
//			}
//		});
//		
//		
//		$('#freight-others-palettes').on('blur', function() {
//			
//			var freightOthersAttributes = "FRO_PALETTES_CHARGES-" + this.value + "-"+	$('#freight-others-id-site-source').val();
//			var params = "freightOthersAttributes="+freightOthersAttributes;
//			
//			if (!validationsAttributes.isNumber(this.value)){
//				$('#alert-errors').modal('show');
//				$('#alert-errors-content').html("Only numbers are allowed");
//				this.focus();
//			}else{
////				alert(params)
//				ajaxInvokeMethods.postAjaxCallback("freightOthersUpdateAttributes.action", params, null);
//			}
//		});
		
		
		
		var selIdSites       = $('#selIdSites').val();
		
		var params = "stageVO.idSiteStage="+selIdSites;
		ajaxInvokeMethods.postAjaxCallback("initFreightOthers.action", params, callbackInitFreightOthers);
		
		
		$('#freights-tab a[href="#freight-marine"]').tab('show');
		
		//Limpiando antiguos eventos para change y generando listener selector
//		$('#freight-others-id-site-source').unbind( "change" );
//		
//		$('#freight-others-id-site-source').on('change', function() {
//
//			var idSiteSource  = $('#freight-others-id-site-source').val();
//			
//			//Se env�a vacio para que no realice la b�squeda por este atributo
//			var idModeFreight = ""; 
//			
//			var params = "freightOthersVO.idSiteSourceFreight="+idSiteSource+"&freightOthersVO.idModeFreight="+idModeFreight;
//			ajaxInvokeMethods.postAjaxCallback("obtieneFreightOthers.action", params, callbackGetFreightOthers);
//		});
		
		
		
		//Limpiando eventos onchange y generando listener selector
//		$('#freight-others-mode-transport').unbind( "change" );
//		$('#freight-others-mode-transport').on('change', function() {
//
//			var idSiteSource  = $('#freight-others-id-site-source').val();
//			var idModeFreight = $('#freight-others-mode-transport').val();
//			
//			var params = "freightOthersVO.idSiteSourceFreight="+idSiteSource+"&freightOthersVO.idModeFreight="+idModeFreight;
//			ajaxInvokeMethods.postAjaxCallback("obtieneFreightOthers.action", params, callbackGetFreightOthers);
//		});
		
	});
	
	
	//SETUP
	
	$('#nav-setup a[href="#nav-setup-exchange"]').click(function (e) {
		e.preventDefault();
		$('#tab-move-cost a[href="#tab-setup"]').trigger("click");
		$('#nav-setup a[href="#nav-setup-exchange"]').tab('show'); 
	});
	
	
	
	
	$('#nav-setup a[href="#nav-setup-local-custom-duties"]').click(function (e) {
		e.preventDefault();
		
		
		var params = "";
		ajaxInvokeMethods.postAjaxCallback("initSetupCustomDuties.action", params, callbackInitSetupCustomDuties);
		
		$('#nav-setup a[href="#nav-setup-local-custom-duties"]').tab('show'); 
	})
	
	
	$('#nav-setup a[href="#nav-setup-operative-management"]').click(function (e) {
		e.preventDefault();
		
//		var params = "";
		
		//TODO: cambiar los objetos
//		$('#setup-ope-consolidate-site-source').unbind( "change" );
//		$('#setup-ope-consolidate-site-source').on('change', function() {
//
//			var idSiteSource  = $('#setup-ope-consolidate-site-source').val();
//			var idModeFreight = ""; 
//			
//			var params = "freightOthersVO.idSiteSourceFreight="+idSiteSource+"&freightOthersVO.idModeFreight="+idModeFreight;
//			ajaxInvokeMethods.postAjaxCallback("obtieneOpeConsolidation.action", params, callbackGetCountryOrders);
//		});
		
		
		
		//Limpiando eventos onchange y generando listener selector
//		$('#setup-ope-consolidate-site-modes').unbind( "change" );
//		$('#setup-ope-consolidate-site-modes').on('change', function() {
//
//			var idSiteSource  = $('#setup-ope-consolidate-site-source').val();
//			var idModeFreight = $('#setup-ope-consolidate-site-modest').val();
//			
//			var params = "freightOthersVO.idSiteSourceFreight="+idSiteSource+"&freightOthersVO.idModeFreight="+idModeFreight;
//			ajaxInvokeMethods.postAjaxCallback("obtieneOpeConsolidation.action", params, callbackGetCountryOrders);
//		});
		
		
		ajaxInvokeMethods.postAjaxCallback("initSetupOpConsolidation.action", "freightOthersVO.idSiteSourceFreight="+$('#selIdSites').val(), callbackInitSetupOpConsolidation);
		
		$('#nav-setup a[href="#nav-setup-operative-management"]').tab('show'); 
	});
	
	
	$('#nav-setup a[href="#nav-setup-temp-storage"]').click(function (e) {
		
		
		
		e.preventDefault();
//		var params="freightOthersVO.idSiteSourceFreight="+$('#selIdSites').val();
//
//		ajaxInvokeMethods.postAjaxCallback("initSetupTempStorage.action", params, callbackInitSetupTempStorage);

		
		var params = "";
		ajaxInvokeMethods.postAjaxCallback("listRateWarehouse.action", params, callbackListRateWarehouse);
		
		$('#nav-setup a[href="#nav-setup-temp-storage"]').tab('show'); 
	});
	
	$('#nav-setup a[href="#nav-setup-local-transport"]').click(function (e) {
		e.preventDefault();
//		var params="freightOthersVO.idSiteSourceFreight="+$('#selIdSites').val();
//		ajaxInvokeMethods.postAjaxCallback("initSetupLocalTransport.action", params, callbackInitSetupLocalTransport);
		
		
		var params = "";
		ajaxInvokeMethods.postAjaxCallback("listRoutesLocalTransport.action", params, callbackListRoutesLocalTransport);
		
		$('#nav-setup a[href="#nav-setup-local-transport"]').tab('show'); 
	});
	
	
	
	
	$('#nav-setup a[href="#nav-setup-custody"]').click(function (e) {
		e.preventDefault();

		$('#nav-setup a[href="#nav-setup-custody"]').tab('show'); 
		
		var selIdSites       = $('#selIdSites').val();
//		$('#modal-loading').modal({shown:true,  keyboard: false });
		
		var params = "stageVO.idSiteStage="+selIdSites;
		ajaxInvokeMethods.postAjaxCallback("initCustodySetup.action", params, callbackInitCustodySetup);
	});
	
	$('#btnImportUnitFilter').on('click', function() {
		var params = "";
		var filterTransport 	= $('#filterTransport').val();
		var filterSiteSource 	= $('#filterSiteSource').val();
		var filterPresentation 	= $('#filterPresentation').val();
		var filterTrade 		= $('#filterTrade').val();
		var filterFamily 		= $('#filterFamily').val();
		var filterType 			= $('#filterType').val();
		var filterTypeLoad 		= $('#filterTypeLoad').val();
		var filterCustomDuty 	= $('#filterCustomDuty').val();
		var filterProduct = $('#filterProduct').val();
		
		params = "filtrosVO.filterTransport="+filterTransport +"&filtrosVO.filterSiteSource=" + filterSiteSource + "&filtrosVO.filterPresentation=" + filterPresentation + "&filtrosVO.filterTrade=" + filterTrade + "&filtrosVO.filterFamily=" + filterFamily + "&filtrosVO.filterType=" + filterType + "&filtrosVO.filterTypeLoad=" + filterTypeLoad + "&filtrosVO.filterCustomDuty=" + filterCustomDuty + "&filtrosVO.filterProduct="+filterProduct;

		ajaxInvokeMethods.postAjaxCallback("filtros.action", params, callbackImportUnits);
	});
	
	
	/*CALL BACK FUNCTIONS*/
	function callbackInitSimulate(data){
		
		var statusMessageError = data.statusMessageError;
		var idStage            = data.stageVO.idStage
		var listCustomDuty = data.listCustomDuty;
		$( "#filterCustomDuty" ).empty();
		var optionRow = "<option value='0'>All Custom Duty</option>";
		$("#filterCustomDuty").append(optionRow);
		for (var j=0; j<listCustomDuty.length; j++){
			optionRow = "<option value='"+listCustomDuty[j].idGeneralCombo+"'>"+listCustomDuty[j].nameGeneralCombo+"</option>";
			$("#filterCustomDuty").append(optionRow);
		}

		$('#idStage').val(idStage);
		
		//Se desactiva selector de paises
		$('#selIdSites').prop("disabled", true); 
		
		//quitando loading
		$('#modal-loading').modal("hide");
		
		$("#form-content-inputs").show();
		
//		$("#div-summary-cost").show();
		$("#div-buttom-stage").show();
		
		$("#tab-move-cost a[href='#tab-import-units']").trigger("click");

	}
	
	function callbackInitSetupOpConsolidation(data){
//		var statusMessageError = data.statusMessageError;	
		var listSiteSource     = data.listSiteSource;
		//var listFreightModes   = data.listFreightModes;
		var listOpeManageVO    = data.listOpeManageVO;
		var listCurrencyVO	   = data.listCurrency;
		var optionCurID		   = "";
		
		$( "#setup-ope-consolidate-site-source" ).empty();
		for (var j=0; j<listSiteSource.length; j++){
			var optionRow = "<option value='"+listSiteSource[j].idGeneralCombo+"'>"+listSiteSource[j].nameGeneralCombo+"</option>";
			$("#setup-ope-consolidate-site-source").append(optionRow);
		}
		$( "#setup-ope-consolidate-site-source" ).trigger("change");
		
//		$( "#setup-ope-consolidate-site-modes" ).empty();
//		for (var j=0; j<listFreightModes.length; j++){
//			var optionRow = "<option value='"+listFreightModes[j].idGeneralCombo+"'>"+listFreightModes[j].nameGeneralCombo+"</option>"
//			$("#setup-ope-consolidate-site-modes").append(optionRow);
//		}
		
		
		
		for (var i=0; i < listCurrencyVO.length; i++){
			optionCurID = optionCurID.concat("<option value='"+listCurrencyVO[i].idGeneralCombo+"'>"+listCurrencyVO[i].nameGeneralCombo+"</option>");
		}
		
		
		$( "#table-setup-ope-manage-tbody" ).empty();
		for (var j=0; j<listOpeManageVO.length; j++){
			
			
			var row = "";
			row = row.concat("<tr>");
			row = row.concat("<td><input type='text' id='SOM_PROCESSING_ENTRY-"+listOpeManageVO[j].somID+"' class='input-block-level' style='text-align: right; padding-right: 2px;'></td>");
			row = row.concat("<td>");
			row = row.concat("	<select id='CUR_ID-"+listOpeManageVO[j].somID+"'>" + optionCurID + "	</select>");
			row = row.concat("</td>");
			row = row.concat("<td><input type='text' id='SOM_PROCESSING_FEE-"+listOpeManageVO[j].somID+"' class='input-block-level' style='text-align: right; padding-right: 2px;'></td>");			
			row = row.concat("<td><input type='text' id='SOM_RATE_KG-"+listOpeManageVO[j].somID+"' class='input-block-level' style='text-align: right; padding-right: 2px;'></td>");
			row = row.concat("<td><input type='text' id='SOM_PERCENT_TP-"+listOpeManageVO[j].somID+"' class='input-block-level' style='text-align: right; padding-right: 2px;'></td>");
			
			row = row.concat("<td>" +
					"<a class='btn_table' href='#' title='Replicate Operative Activity' id='replicateOperativeAct-"+listOpeManageVO[j].somID+"'><i class='icon-retweet'></i></a>" +
					"<a class='btn_table' href='#' title='Delete Operative Activity'    id='deleteOperativeAct-"+listOpeManageVO[j].somID+"'><i class='icon-trash'></i></a>" +
				"</td>");
			
			
			row = row.concat("</tr>");
			
			$("#table-setup-ope-manage-tbody").append(row);
			
			
			$("#SOM_PERCENT_TP-"+listOpeManageVO[j].somID).val(Number(listOpeManageVO[j].percentEntry).toFixed(2));
			$("#SOM_PROCESSING_ENTRY-"+listOpeManageVO[j].somID).val(listOpeManageVO[j].somEntry);
			$("#SOM_PROCESSING_FEE-"+listOpeManageVO[j].somID).val(Number(listOpeManageVO[j].somProcessingFee).toFixed(2));
			$("#SOM_RATE_KG-"+listOpeManageVO[j].somID).val(Number(listOpeManageVO[j].somRateKg).toFixed(2));			
			$("#CUR_ID-"+listOpeManageVO[j].somID).val(listOpeManageVO[j].curId);
			
			
			
			$('#replicateOperativeAct-'+listOpeManageVO[j].somID).on('click', function() {
				
				var params = "idOperativeActivity="+this.id.split("-")[1];
				ajaxInvokeMethods.postAjaxCallback("replicateOperativeActivities.action", params, function(){
					
					ajaxInvokeMethods.postAjaxCallback("initSetupOpConsolidation.action", "freightOthersVO.idSiteSourceFreight="+$('#selIdSites').val(), callbackInitSetupOpConsolidation);
				});
			});
			
			
			$('#deleteOperativeAct-'+listOpeManageVO[j].somID).on('click', function() {
				
				var params = "idOperativeActivity="+this.id.split("-")[1];
				ajaxInvokeMethods.postAjaxCallback("deleteOperativeActivities.action", params, function(data){
					
					var statusMessageError = data.statusMessageError;
					if(statusMessageError != ''){
						$('#alert-errors').modal('show');
						$('#alert-errors-content').html(statusMessageError);						
					}else{
						
						var params = "";
						ajaxInvokeMethods.postAjaxCallback("initSetupOpConsolidation.action", "freightOthersVO.idSiteSourceFreight="+$('#selIdSites').val(), callbackInitSetupOpConsolidation);
					}	
				});
			});
			
			
			$('#SOM_PERCENT_TP-'+listOpeManageVO[j].somID).on('blur', function() {
				
				var setupOpeManage = "SOM_PERCENT_TP-"+this.value + "-" + this.id.split("-")[1];
				var params = "setupOpeManage="+setupOpeManage;
				if (!validationsAttributes.isNumber(this.value)){
					$('#alert-errors').modal('show');
					$('#alert-errors-content').html("Only numbers are allowed");
				}else{
					ajaxInvokeMethods.postAjaxCallback("setupOpeManageUpdateAttributes.action", params, null);
				}
			});
			
			
			$('#SOM_PROCESSING_ENTRY-'+listOpeManageVO[j].somID).on('blur', function() {
				
				var setupOpeManage = "SOM_PROCESSING_ENTRY-"+this.value + "-" + this.id.split("-")[1];
				var params = "setupOpeManage="+setupOpeManage;
				ajaxInvokeMethods.postAjaxCallback("setupOpeManageUpdateAttributes.action", params, null);
			});
			
			$('#SOM_PROCESSING_FEE-'+listOpeManageVO[j].somID).on('blur', function() {
				
				var setupOpeManage = "SOM_PROCESSING_FEE-"+this.value + "-" + this.id.split("-")[1];
				var params = "setupOpeManage="+setupOpeManage;
				if (!validationsAttributes.isNumber(this.value)){
					$('#alert-errors').modal('show');
					$('#alert-errors-content').html("Only numbers are allowed");
					this.focus();
				}else{
					ajaxInvokeMethods.postAjaxCallback("setupOpeManageUpdateAttributes.action", params, null);
				}
			});
			
			$('#SOM_RATE_KG-'+listOpeManageVO[j].somID).on('blur', function() {
				
				var setupOpeManage = "SOM_RATE_KG-"+this.value + "-" + this.id.split("-")[1];
				var params = "setupOpeManage="+setupOpeManage;
				if (!validationsAttributes.isNumber(this.value)){
					$('#alert-errors').modal('show');
					$('#alert-errors-content').html("Only numbers are allowed");
					this.focus();
				}else{
					ajaxInvokeMethods.postAjaxCallback("setupOpeManageUpdateAttributes.action", params, null);
				}
			});
			
			$('#CUR_ID-'+listOpeManageVO[j].somID).on('change', function() {
				
				var setupOpeManage = "CUR_ID-"+this.value + "-" + this.id.split("-")[1];
				var params = "setupOpeManage="+setupOpeManage;

				ajaxInvokeMethods.postAjaxCallback("setupOpeManageUpdateAttributes.action", params, callBackUpdateSomProcessinfFee);
				
			});
		}
		
		
		$('.body_page').css('height', 'auto');
		$('.body_page').height($( document ).height() - 172);
		$('.t-f-h-operative-management').fixedHeader();
	}
	
	function callBackUpdateSomProcessinfFee(data){
		var valor = data.valor;
		$('#SOM_PROCESSING_FEE-'+valor.split("-")[1]).val(valor.split("-")[0]);
	}
	
	function callbackInitSetupCustomDuties(data){
		
		var statusMessageError = data.statusMessageError;
		var setupCustomDutiesHeadVO   = data.setupCustomDutiesHeadVO;
		
		
		$('#custom-duties-other-taxes').val(setupCustomDutiesHeadVO.otherTaxesDutyHead);
		$('#custom-duties-vat-import').val(setupCustomDutiesHeadVO.vatImportDutyHead);
		$('#custom-duties-sal-capitales').val(setupCustomDutiesHeadVO.otherTaxesImpDutyHead);

		$('#custom-duties-other-taxes').unbind( "blur" );
		$('#custom-duties-vat-import').unbind( "blur" );
		$('#custom-duties-sal-capitales').unbind( "blur" );

		
		$('#custom-duties-other-taxes').on('blur', function() {
			var setupCustomDuties = "SDH_OTHER_TAXES_FODINFA-"+this.value;
			var params = "setupCustomDuties="+setupCustomDuties;
			if (!validationsAttributes.isNumber(this.value)){
				$('#alert-errors').modal('show');
				$('#alert-errors-content').html("Only numbers are allowed");
				this.focus();
			}else{
				ajaxInvokeMethods.postAjaxCallback("setupCustomDutiesHeadUpdateAttributes.action", params, null);
			}
		});
		
		$('#custom-duties-vat-import').on('blur', function() {
			var setupCustomDuties = "SDH_VAT_IMPORTS-"+this.value;
			var params = "setupCustomDuties="+setupCustomDuties;
			if (!validationsAttributes.isNumber(this.value)){
				$('#alert-errors').modal('show');
				$('#alert-errors-content').html("Only numbers are allowed");
				this.focus();
			}else{
				ajaxInvokeMethods.postAjaxCallback("setupCustomDutiesHeadUpdateAttributes.action", params, null);
			}
		});
		
		$('#custom-duties-sal-capitales').on('blur', function() {
			var setupCustomDuties = "SDH_OTHER_TAXES_IMP_CAP-"+this.value;
			var params = "setupCustomDuties="+setupCustomDuties;
			if (!validationsAttributes.isNumber(this.value)){
				$('#alert-errors').modal('show');
				$('#alert-errors-content').html("Only numbers are allowed");
				this.focus();
			}else{
				ajaxInvokeMethods.postAjaxCallback("setupCustomDutiesHeadUpdateAttributes.action", params, null);
			}
		});
		
		
		var listSetupCustomDutiesDetailVO = setupCustomDutiesHeadVO.listSetupCustomDutiesDetailVO;
		$( "#table-setup-custom-duties-tbody" ).empty();
		for (var j=0; j<listSetupCustomDutiesDetailVO.length; j++){
			
			var row = "";
			row = row.concat("<tr>");
			
			row = row.concat("<td>" + "<input type='text' class='input-block-level' id='SETUP_CUSTOM_CODE-"+listSetupCustomDutiesDetailVO[j].idDutyDetail+"' value='"+listSetupCustomDutiesDetailVO[j].codeDuty+"' >" + "</td>");
			row = row.concat("<td>" + "<input type='text' class='input-block-level' id='SETUP_CUSTOM_TYPE-"+listSetupCustomDutiesDetailVO[j].idDutyDetail+"' value='"+listSetupCustomDutiesDetailVO[j].nameDutyDetail+"' style='text-align: left; padding-right: 2px;'>" + "</td>");
			row = row.concat("<td>" + "<input type='text' class='input-block-level' id='SETUP_CUSTOM_TARIF-"+listSetupCustomDutiesDetailVO[j].idDutyDetail+"' value='"+listSetupCustomDutiesDetailVO[j].tariffDutyDetail+"' style='text-align: right; padding-right: 2px;'>" + "</td>");
			row = row.concat("<td>" + "<input type='text' class='input-block-level' id='SETUP_CUSTOM_TAXES-"+listSetupCustomDutiesDetailVO[j].idDutyDetail+"' value='"+listSetupCustomDutiesDetailVO[j].otherTaxesDutyDetail+"' style='text-align: right; padding-right: 2px;'>" + "</td>");
			
			row = row.concat("<td>" +
					"<a class='btn_table' href='#' title='Add new Duty Type' id='replicateLocalDuty-"+listSetupCustomDutiesDetailVO[j].idDutyDetail+"'><i class='icon-retweet'></i></a>" +
					"<a class='btn_table' href='#' title='Delete Duty Type'  id='deleteLocalDuty-"+listSetupCustomDutiesDetailVO[j].idDutyDetail+"'><i class='icon-trash'></i></a>" +
				"</td>");
			
			row = row.concat("</tr>");
			
			$("#table-setup-custom-duties-tbody").append(row);
			
			
			
			$('#replicateLocalDuty-'+listSetupCustomDutiesDetailVO[j].idDutyDetail).on('click', function() {
				
				var params = "idLocalDuty="+this.id.split("-") [1];
				ajaxInvokeMethods.postAjaxCallback("replicateLocalDuty.action", params, function(){
					
				var params = "";
				ajaxInvokeMethods.postAjaxCallback("initSetupCustomDuties.action", params, callbackInitSetupCustomDuties);
				});
			});
			
			$('#deleteLocalDuty-'+listSetupCustomDutiesDetailVO[j].idDutyDetail).on('click', function() {
				
				var params = "idLocalDuty="+this.id.split("-") [1];
				ajaxInvokeMethods.postAjaxCallback("deleteLocalDuty.action", params, function(data){
					
					
					var statusMessageError = data.statusMessageError;
					if(statusMessageError != ''){
						$('#alert-errors').modal('show');
						$('#alert-errors-content').html(statusMessageError);						
					}else{
						
						var params = "";
						ajaxInvokeMethods.postAjaxCallback("initSetupCustomDuties.action", params, callbackInitSetupCustomDuties);						
					}

				});
			});
			
			$('#SETUP_CUSTOM_CODE-'+listSetupCustomDutiesDetailVO[j].idDutyDetail).on('blur', function() {
				var setupCustomDuties = "SDD_DUTY_CODE-"+this.value + "-" + this.id.split("-")[1];
				var params = "setupCustomDuties="+setupCustomDuties;
				ajaxInvokeMethods.postAjaxCallback("setupCustomDutiesUpdateAttributes.action", params, function(data){
					
					var statusMessageError = data.statusMessageError;
					
					if(statusMessageError != ''){
						$('#alert-errors').modal('show');
						$('#alert-errors-content').html(statusMessageError);						
					}

					
				});
			});
			
			$('#SETUP_CUSTOM_TYPE-'+listSetupCustomDutiesDetailVO[j].idDutyDetail).on('blur', function() {
				var setupCustomDuties = "SDD_DUTY_NAME-"+this.value + "-" + this.id.split("-")[1];
				var params = "setupCustomDuties="+setupCustomDuties;
				ajaxInvokeMethods.postAjaxCallback("setupCustomDutiesUpdateAttributes.action", params, null);
			});
			
			$('#SETUP_CUSTOM_TARIF-'+listSetupCustomDutiesDetailVO[j].idDutyDetail).on('blur', function() {
				var setupCustomDuties = "SDD_DUTY_TARIFF-"+this.value + "-" + this.id.split("-")[1];
				var params = "setupCustomDuties="+setupCustomDuties;
				if (!validationsAttributes.isNumber(this.value)){
					$('#alert-errors').modal('show');
					$('#alert-errors-content').html("Only numbers are allowed");
					this.focus();
				}else{
					ajaxInvokeMethods.postAjaxCallback("setupCustomDutiesUpdateAttributes.action", params, null);
				}
			});
			
			$('#SETUP_CUSTOM_TAXES-'+listSetupCustomDutiesDetailVO[j].idDutyDetail).on('blur', function() {
				
				var setupCustomDuties = "SDD_OTHER_TAXES-"+this.value + "-" + this.id.split("-")[1];
				var params = "setupCustomDuties="+setupCustomDuties;
				if (!validationsAttributes.isNumber(this.value)){
					$('#alert-errors').modal('show');
					$('#alert-errors-content').html("Only numbers are allowed");
					this.focus();
				}else{
					ajaxInvokeMethods.postAjaxCallback("setupCustomDutiesUpdateAttributes.action", params, null);
				}
			});
		}
		
		
		$('.body_page').css('height', 'auto');
		$('.body_page').height($( document ).height() - 172);
		$('.t-f-h-custom-duties').fixedHeader();
		
	}
	
	
	function callbackInitSetupMacroExchange(data){
		
//		var statusMessageError  = data.statusMessageError;
		
		var listSetupExchangeVO = data.listSetupExchangeVO;
		var listSetupMacroValVO = data.listSetupMacroValVO;
		
		
		
		$( "#table-setup-exchange-column-tbody" ).empty();
		for (var j=0; j<listSetupExchangeVO.length; j++){
			
			var row = "";
			row = row.concat("<tr>");
			
			row = row.concat("<td>" + listSetupExchangeVO[j].idExchange + "</td>");
			row = row.concat("<td>" + "<input type='text' class='input-block-level' id='SFE_NAME-"+listSetupExchangeVO[j].idExchange+"'  value='"+listSetupExchangeVO[j].nameExchange+"' style='text-align: left; padding-right: 2px;'>" + "</td>");
			row = row.concat("<td>" + "<input type='text' class='input-block-level' id='SFE_ACRONYM-"+listSetupExchangeVO[j].idExchange+"' value='"+listSetupExchangeVO[j].acronymExchage+"' style='text-align: right; padding-right: 2px;'>" + "</td>");
			row = row.concat("<td>" + "<input type='text' class='input-block-level' id='SFE_RATE-"+listSetupExchangeVO[j].idExchange+"' value='"+listSetupExchangeVO[j].amountExchange+"' style='text-align: right; padding-right: 2px;'>" + "</td>");
			
			row = row.concat("<td>" +
					"<a class='btn_table' href='#' title='Add new Duty Type'  id='replicateExchange-"+listSetupExchangeVO[j].idExchange+"'><i class='icon-retweet'></i></a>" +
					"<a class='btn_table' href='#' title='Delete Duty Type'   id='deleteExchange-"+listSetupExchangeVO[j].idExchange+"'><i class='icon-trash'></i></a>" +
				"</td>");
			
			
			row = row.concat("</tr>");
			
			$("#table-setup-exchange-column-tbody").append(row);
			
			$('#deleteExchange-'+listSetupExchangeVO[j].idExchange).on('click', function() {
				
				var params = "idExchangeCurrency="+this.id.split("-") [1];
				ajaxInvokeMethods.postAjaxCallback("deleteExchangeCurrency.action", params, function(data){
					var statusMessageError = data.statusMessageError;
					if (statusMessageError != ''){
						$('#alert-errors').modal('show');
						$('#alert-errors-content').html(statusMessageError);
						
						return false;
					}
					var selIdSites = $('#selIdSites').val();
					var params     = "stageVO.idSiteStage="+selIdSites;
					ajaxInvokeMethods.postAjaxCallback("initSetupMacroExchange.action", params, callbackInitSetupMacroExchange);
				});
			});
			
			$('#replicateExchange-'+listSetupExchangeVO[j].idExchange).on('click', function() {
				
				var params = "idExchangeCurrency="+this.id.split("-") [1];
				ajaxInvokeMethods.postAjaxCallback("replicateExchangeCurrency.action", params, function(){
					
					var selIdSites = $('#selIdSites').val();
					var params     = "stageVO.idSiteStage="+selIdSites;
					ajaxInvokeMethods.postAjaxCallback("initSetupMacroExchange.action", params, callbackInitSetupMacroExchange);
				});
			});
			
			
			$('#SFE_RATE-'+listSetupExchangeVO[j].idExchange).on('blur', function() {
			
				var setupExchangeAttributes = this.value + "-" + this.id.split("-")[0] + "-" + this.id.split("-")[1];
				var params = "setupExchangeAttributes="+setupExchangeAttributes;
				if (!validationsAttributes.isNumber(this.value)){
					$('#alert-errors').modal('show');
					$('#alert-errors-content').html("Only numbers are allowed");
				}else{
					ajaxInvokeMethods.postAjaxCallback("setupExchangeUpdateAttributes.action", params, null);
				}
			});
			
			
			$('#SFE_NAME-'+listSetupExchangeVO[j].idExchange).on('blur', function() {
				
				var setupExchangeAttributes = this.value + "-" + this.id.split("-")[0] + "-" + this.id.split("-")[1];
				var params = "setupExchangeAttributes="+setupExchangeAttributes;
				
				ajaxInvokeMethods.postAjaxCallback("setupExchangeUpdateAttributes.action", params, null);
				
			});
			
			$('#SFE_ACRONYM-'+listSetupExchangeVO[j].idExchange).on('blur', function() {
				
				var setupExchangeAttributes = this.value + "-" + this.id.split("-")[0] + "-" + this.id.split("-")[1];
				var params = "setupExchangeAttributes="+setupExchangeAttributes;
				
				ajaxInvokeMethods.postAjaxCallback("setupExchangeUpdateAttributes.action", params, null);
			});
		}
		
		
		$( "#setup-macrovar-column" ).empty();
		for (var j=0; j<listSetupMacroValVO.length; j++){
			var rowExchange = "<label class='control-label'>"+listSetupMacroValVO[j].nameMacroVar+":</label>"+"<input type='text' id='SETUP_MACROVAR-"+listSetupMacroValVO[j].idMacroVar+"' class='input-block-level' style='text-align: right; padding-right: 2px;' value="+listSetupMacroValVO[j].amountMacroVar+">";
			$("#setup-macrovar-column").append(rowExchange);

			$('#SETUP_MACROVAR-'+listSetupMacroValVO[j].idMacroVar).on('blur', function() {
				
				var setupMacrovarAttributes = this.value + "-" + this.id.split("-")[1];
				var params = "setupMacrovarAttributes="+setupMacrovarAttributes;
				if (!validationsAttributes.isNumber(this.value)){
					$('#alert-errors').modal('show');
					$('#alert-errors-content').html("Only numbers are allowed");
					this.focus();
				}else{
					ajaxInvokeMethods.postAjaxCallback("setupMacrovarUpdateAttributes.action", params, null);
				}
			});
		}
		
		$('.body_page').css('height', 'auto');
		$('.body_page').height($( document ).height() - 172);
		$('.t-f-h-exchange-currency').fixedHeader();
		
		
	}
	
	
	function callbackInitFreightOthers(data){
		
		
		var listOtherTransports = data.listOtherTransports;
		var listOtherFreightVO  = data.listOtherFreightVO;
		var listCurrencyFreight = data.listCurrencyFreight;
		
		
		
		var optionCurrencyFreight = "";
		for (var j=0; j<listCurrencyFreight.length; j++){
			optionCurrencyFreight = optionCurrencyFreight + "<option value='"+listCurrencyFreight[j].idGeneralCombo+"'>"+listCurrencyFreight[j].nameGeneralCombo+"</option>";
		}
		
		var optionOtherTransports = "";
		for (var j=0; j<listOtherTransports.length; j++){
			optionOtherTransports = optionOtherTransports.concat("<option value='"+listOtherTransports[j].idGeneralCombo+"'>"+listOtherTransports[j].nameGeneralCombo+"</option>");
		}
		
		
		$("#table-freight-others-body").empty();
		for (var j=0; j<listOtherFreightVO.length; j++){
			
			var row = "";
			row = row.concat("<tr>");
			row = row.concat("<td>" + listOtherFreightVO[j].idOtherFreight + "</td>");
			row = row.concat("<td>" + "<select id='FOT_ID_TANSPORT-"+listOtherFreightVO[j].idOtherFreight+"'>"+optionOtherTransports+"</select>" + "</td>");
			row = row.concat("<td>" + "<input  id='FOT_INCREASE-" +listOtherFreightVO[j].idOtherFreight+"' type='text' class='input-block-level' value='"+listOtherFreightVO[j].increaseOtherFreight+"' >" + "</td>");
			row = row.concat("<td>" + "<input  id='FOT_NAME_TANSPORT-" +listOtherFreightVO[j].idOtherFreight+"' type='text' class='input-block-level' value='"+listOtherFreightVO[j].nameOtherFreight+"' >" + "</td>");
			row = row.concat("<td>" + "<select id='FOT_ID_CURRENCY-"+listOtherFreightVO[j].idOtherFreight+"'>"+optionCurrencyFreight+"</select>" + "</td>");
			row = row.concat("<td>" + "<input  id='FOT_PALETTE_FREIGHT-" +listOtherFreightVO[j].idOtherFreight+"' type='text' class='input-block-level' value='"+listOtherFreightVO[j].palettesOtherFreight+"' style='text-align: right; padding-right: 2px;'>" + "</td>");
			row = row.concat("<td>" + "<input  id='FOT_ORDERS_NUMBER-" +listOtherFreightVO[j].idOtherFreight+"' type='text' class='input-block-level' value='"+listOtherFreightVO[j].ordersNumberOtherFreight+"' style='text-align: right; padding-right: 2px;'>" + "</td>");
			row = row.concat("<td style='text-align:right;'>" + listOtherFreightVO[j].totalConceptsFreight.formatMoney(2) + "</td>");
			
			row = row.concat("<td>" +
								"<a class='btn_table' href='#' title='Replicate Route' id='replicateOtherFreight-"+listOtherFreightVO[j].idOtherFreight+"'><i class='icon-retweet'></i></a>" +
								"<a class='btn_table' href='#' title='Delete Route'    id='deleteOtherFreight-"+listOtherFreightVO[j].idOtherFreight+"'><i class='icon-trash'></i></a>" +
								"<a class='btn_table' href='#' title='Edit Concepts'   id='initConceptsFreight-"+listOtherFreightVO[j].idOtherFreight+"'><i class='icon-plus'></i></a>" +
							"</td>");
			row = row.concat("</tr>");
			
			
			$("#table-freight-others-body").append(row);
			
			//Todos los setting se deben realizar despu�s que el objeto exista
			$("#FOT_ID_TANSPORT-"+listOtherFreightVO[j].idOtherFreight).val(listOtherFreightVO[j].idTransportOtherFreight);
			$("#FOT_ID_CURRENCY-"+listOtherFreightVO[j].idOtherFreight).val(listOtherFreightVO[j].idCrrencyOtherFreight);
			
			
			//Generando eventos para objetos ya creados
			$('#initConceptsFreight-'+listOtherFreightVO[j].idOtherFreight).on('click', function() {
				
				var params = "idOtherFreight="+this.id.split("-") [1];
				ajaxInvokeMethods.postAjaxCallback("listConceptOtherFreight.action", params, callbackListConceptOtherFreight);
				
			});
			
			
			$('#replicateOtherFreight-'+listOtherFreightVO[j].idOtherFreight).on('click', function() {
				
				var params = "idOtherFreight="+this.id.split("-") [1];
				ajaxInvokeMethods.postAjaxCallback("replicateOtherFreight.action", params, function(){
					
					var selIdSites       = $('#selIdSites').val();
					var params = "stageVO.idSiteStage="+selIdSites;
					ajaxInvokeMethods.postAjaxCallback("initFreightOthers.action", params, callbackInitFreightOthers);
				});
			});
			
			
			$('#deleteOtherFreight-'+listOtherFreightVO[j].idOtherFreight).on('click', function() {
				
				var params = "idOtherFreight="+this.id.split("-") [1];
				ajaxInvokeMethods.postAjaxCallback("deleteOtherFreight.action", params, function(data){
					
					var statusMessageError = data.statusMessageError;
					if (statusMessageError != ''){
						$('#alert-errors').modal('show');
						$('#alert-errors-content').html(statusMessageError);
						
						return false;
					}
					
					var selIdSites       = $('#selIdSites').val();
					var params = "stageVO.idSiteStage="+selIdSites;
					ajaxInvokeMethods.postAjaxCallback("initFreightOthers.action", params, callbackInitFreightOthers);
				});
			});
			
			
			
			
			//Eventos para los objetos ya creados
			
			
			$('#FOT_INCREASE-'+listOtherFreightVO[j].idOtherFreight).on('blur', function() {
				var freightOthersAttributes = this.id.split("-") [0]+"-" + this.value + "-" + this.id.split("-") [1];
				var params = "freightOthersAttributes="+freightOthersAttributes;
				if (!validationsAttributes.isNumber(this.value)){
					$('#alert-errors').modal('show');
					$('#alert-errors-content').html("Only numbers are allowed");
					this.focus();
				}else{
					ajaxInvokeMethods.postAjaxCallback("freightOthersUpdateAttributes.action", params, null);
				}
			});
			
			$('#FOT_ORDERS_NUMBER-'+listOtherFreightVO[j].idOtherFreight).on('blur', function() {
				var freightOthersAttributes = this.id.split("-") [0]+"-" + this.value + "-" + this.id.split("-") [1];
				var params = "freightOthersAttributes="+freightOthersAttributes;
				if (!validationsAttributes.isNumber(this.value)){
					$('#alert-errors').modal('show');
					$('#alert-errors-content').html("Only numbers are allowed");
					this.focus();
				}else{
					ajaxInvokeMethods.postAjaxCallback("freightOthersUpdateAttributes.action", params, null);
				}
			});
			
			
			$('#FOT_ID_TANSPORT-'+listOtherFreightVO[j].idOtherFreight).on('change', function() {
				var freightOthersAttributes = this.id.split("-") [0]+"-" + this.value + "-" + this.id.split("-") [1];
				var params = "freightOthersAttributes="+freightOthersAttributes;
				
				ajaxInvokeMethods.postAjaxCallback("freightOthersUpdateAttributes.action", params, null);
			});
			
			
			$('#FOT_NAME_TANSPORT-'+listOtherFreightVO[j].idOtherFreight).on('blur', function() {
				var freightOthersAttributes = this.id.split("-") [0]+"-" + this.value + "-" + this.id.split("-") [1];
				var params = "freightOthersAttributes="+freightOthersAttributes;
				
				ajaxInvokeMethods.postAjaxCallback("freightOthersUpdateAttributes.action", params, null);
			});
			
			
			$('#FOT_ID_CURRENCY-'+listOtherFreightVO[j].idOtherFreight).on('change', function() {
				var freightOthersAttributes = this.id.split("-") [0]+"-" + this.value + "-" + this.id.split("-") [1];
				var params = "freightOthersAttributes="+freightOthersAttributes;
				
				ajaxInvokeMethods.postAjaxCallback("freightOthersUpdateAttributes.action", params, null);
			});
			
			
			$('#FOT_PALETTE_FREIGHT-'+listOtherFreightVO[j].idOtherFreight).on('blur', function() {
				var freightOthersAttributes = this.id.split("-") [0]+"-" + this.value + "-" + this.id.split("-") [1];
				var params = "freightOthersAttributes="+freightOthersAttributes;
				if (!validationsAttributes.isNumber(this.value)){
					$('#alert-errors').modal('show');
					$('#alert-errors-content').html("Only numbers are allowed");
					this.focus();
				}else{
					ajaxInvokeMethods.postAjaxCallback("freightOthersUpdateAttributes.action", params, null);
				}
			});
			
			
		}$('.t-f-h-freight-marine').fixedHeader();
		
	}
	
//	function callbackGetFreightOthers(data){
//		
////		var statusMessageError = data.statusMessageError;
//		var freightOthersVO    = data.freightOthersVO;
//		
//		$('#freight-others-container').val(freightOthersVO.containerRferFreight);
//		$('#freight-others-internal-freight').val(freightOthersVO.interFreight);
//		$('#freight-others-others-cost').val(freightOthersVO.otherCostFreight);
//		$('#freight-others-destination').val(freightOthersVO.destinationChargesFreight);
//		
//		$('#freight-others-palettes').val(freightOthersVO.paletteChargesFreight);
//		
//		$('#freight-others-mode-transport').val(freightOthersVO.idModeFreight);
//		
//		
//		$('.body_page').css('height', 'auto');
//		$('.body_page').height($( document ).height() - 172);
//	
//	}
	

	
	function callbackGetCountryOrders(data){

		
		$('#setup-ope-consolidate-country-orders').val(data.socOrders);
		
		$('#setup-ope-consolidate-country-orders').unbind('change');
		
		$('#setup-ope-consolidate-country-orders').on('change', function() {
			
			var setupOpeManage = "SOC_ORDERS-" + this.value + "-" + $('#setup-ope-consolidate-site-source').val() ;
			var params = "setupOpeManage="+setupOpeManage;

			if (!validationsAttributes.isNumber(this.value)){
				$('#alert-errors').modal('show');
				$('#alert-errors-content').html("Only numbers are allowed");
				this.focus();
			}else{
				
				ajaxInvokeMethods.postAjaxCallback("setupOpeConsolidationUpdateAttributes.action", params, null);
			}
		});
		
		$('.body_page').css('height', 'auto');
		$('.body_page').height($( document ).height() - 172);
	}

	
	function callbackReplicateFreight(data){
		
		var statusMessageError = data.statusMessageError;
		
		if (statusMessageError != ''){
			$('#alert-errors').modal('show');
			$('#alert-errors-content').html(statusMessageError);
		}else{
			$("#tab-move-cost a[href='#tab-freight']").trigger("click");
		}
	}
	
	function callbackInitFreightAir(data){
		
		var listCurrencyFreight = data.listCurrencyFreight;
		var listFreightAirVO    = data.listFreightAirVO;
		
		var optionRow = "";
		for (var j=0; j<listCurrencyFreight.length; j++){
			optionRow = optionRow + "<option value='"+listCurrencyFreight[j].idGeneralCombo+"'>"+listCurrencyFreight[j].nameGeneralCombo+"</option>";
		}
		
		
		$("#table-freight-air-body").empty();
		for (var i=0; i<listFreightAirVO.length; i++){
			
			var idSiteSourceFreight = listFreightAirVO[i].idSiteSourceFreight;
			
			var clave = idSiteSourceFreight + "-" + listFreightAirVO[i].idFreight;
			
			var row = "";
			row = row.concat("<tr>");
			row = row.concat("<td><select id='FAR_CURRENCY_ID-"+clave+"'>"+optionRow+"</select>" + "</td>");
			
			var listFreighAirRangeVO = listFreightAirVO[i].listFreighAirRangeVO;
			
			
			row = row.concat("<td>" + "<input  id='FAR_ROUTE_DESCRIPTION-" +clave+"' type='text' class='input-block-level' value='"+listFreightAirVO[i].fromSiteSourceFreight+"' style='text-align: right; padding-right: 2px; width:200px;'>" + "</td>");
			row = row.concat("<td>" + "<input  id='ingreaseFreight-"+clave+"' type='text' class='input-block-level' value='"+listFreightAirVO[i].ingreaseFreight+"'       style='text-align: right; padding-right: 2px;'>" + "</td>");
			row = row.concat("<td>" + "<input  id='awbRatesFreight-"+clave+"' type='text' class='input-block-level' value='"+listFreightAirVO[i].awbRatesFreight+"'       style='text-align: right; padding-right: 2px;'>" + "</td>");
			row = row.concat("<td>" + "<input  id='increaseAwbRatesFreight-"+clave+"' type='text' class='input-block-level' value='"+listFreightAirVO[i].ingreaseAWBFreight+"'       style='text-align: right; padding-right: 2px;'>" + "</td>");
			row = row.concat("<td>" + "<input  id='otherFFWFeesFreight-"+clave+"' type='text' class='input-block-level' value='"+listFreightAirVO[i].otherFFWFeesFreight+"'   style='text-align: right; padding-right: 2px;'>" + "</td>");
			row = row.concat("<td>" + "<input  id='increaseOtherFFWFeesFreight-"+clave+"' type='text' class='input-block-level' value='"+listFreightAirVO[i].ingreaseFFWFreight+"'   style='text-align: right; padding-right: 2px;'>" + "</td>");
			row = row.concat("<td>" + "<input  id='fuelSurchargeFreight-"+clave+"' type='text' class='input-block-level' value='"+listFreightAirVO[i].fuelSurchargeFreight+"'  style='text-align: right; padding-right: 2px;'>" + "</td>");
			row = row.concat("<td>" + "<input  id='increaseFuelSurchargeFreight-"+clave+"' type='text' class='input-block-level' value='"+listFreightAirVO[i].ingreaseFSCFreight+"'  style='text-align: right; padding-right: 2px;'>" + "</td>");
			row = row.concat("<td>" + "<input  id='securityChargeFreight-"+clave+"' type='text' class='input-block-level' value='"+listFreightAirVO[i].securityChargeFreight+"' style='text-align: right; padding-right: 2px;'>" + "</td>");
			row = row.concat("<td>" + "<input  id='increaseSecurityChargeFreight-"+clave+"' type='text' class='input-block-level' value='"+listFreightAirVO[i].ingreaseSCCFreight+"' style='text-align: right; padding-right: 2px;'>" + "</td>");
			row = row.concat("<td>" + "<input  id='orderNumberFreight-"+clave+"' type='text' class='input-block-level' value='"+listFreightAirVO[i].ordersFreight+"' style='text-align: right; padding-right: 2px;'>" + "</td>");
			
			
//			row = row.concat("<td>" +
//								"<a class='btn_table' href='#' title='Replicate Route' value='importUnitAttribute="+idSiteSourceFreight+"' id='replicateFreight-"+clave+"'><i class='icon-retweet'></i></a>" +
//								"<a class='btn_table' href='#' title='Delete Route'    value='importUnitAttribute="+idSiteSourceFreight+"' id='deleteFreight-"+clave+"'><i class='icon-trash'></i></a>" +
//								"<a class='btn_table' href='#' title='Range Route'     id='rangeRoute-"+listFreightAirVO[i].idFreight+"'><i class='icon-plus'></i></a>" +
//							"</td>");
			
			row = row.concat("<td><table class=''>");
			row = row.concat("<td style='padding:0px; border:0px;'><a class='btn_table' href='#' title='Replicate Route' value='importUnitAttribute="+idSiteSourceFreight+"' id='replicateFreight-"+clave+"'><i class='icon-retweet'></i></a></td>");
			row = row.concat("<td style='padding:0px; border:0px;'><a class='btn_table' href='#' title='Delete Route'    value='importUnitAttribute="+idSiteSourceFreight+"' id='deleteFreight-"+clave+"'><i class='icon-trash'></i></a></td>");
			row = row.concat("<td style='padding:0px; border:0px;'><a class='btn_table' href='#' title='Range Route'     id='rangeRoute-"+listFreightAirVO[i].idFreight+"'><i class='icon-plus'></i></a></td>");
			row = row.concat("</tr></table></td>"); 
			
			
			row = row.concat("</tr>");
			
			$("#table-freight-air").append(row);
			
			
			//Setting variables
			$("#FAR_CURRENCY_ID-"+clave).val(listFreightAirVO[i].idCurrencyFreight);
			
			
			//Declare Events to objects
			
//			public static final String FAR_INCREASE_AWB					= "FAR_INCREASE_AWB";
//			public static final String FAR_INCREASE_FFW					= "FAR_INCREASE_FFW";
//			public static final String FAR_INCREASE_FSC					= "FAR_INCREASE_FSC";
//			public static final String FAR_INCREASE_SCC					= "FAR_INCREASE_SCC";
			
			
			$('#increaseAwbRatesFreight-'+clave).on('blur', function() {
				var freightFlyAttributes = "FAR_INCREASE_AWB-" + this.value + "-" + this.id.split("-") [1] + "-" + this.id.split("-") [2];
				var params = "freightFlyAttributes="+freightFlyAttributes;
				if (!validationsAttributes.isNumber(this.value)){
					$('#alert-errors').modal('show');
					$('#alert-errors-content').html("Only numbers are allowed");
					this.focus();
				}else{
					ajaxInvokeMethods.postAjaxCallback("freightFlyUpdateAttributes.action", params, null);
				}
			});
			
			$('#increaseOtherFFWFeesFreight-'+clave).on('blur', function() {
				var freightFlyAttributes = "FAR_INCREASE_FFW-" + this.value + "-" + this.id.split("-") [1] + "-" + this.id.split("-") [2];
				var params = "freightFlyAttributes="+freightFlyAttributes;
				if (!validationsAttributes.isNumber(this.value)){
					$('#alert-errors').modal('show');
					$('#alert-errors-content').html("Only numbers are allowed");
					this.focus();
				}else{
					ajaxInvokeMethods.postAjaxCallback("freightFlyUpdateAttributes.action", params, null);
				}
			});			
			
			$('#increaseFuelSurchargeFreight-'+clave).on('blur', function() {
				var freightFlyAttributes = "FAR_INCREASE_FSC-" + this.value + "-" + this.id.split("-") [1] + "-" + this.id.split("-") [2];
				var params = "freightFlyAttributes="+freightFlyAttributes;
				if (!validationsAttributes.isNumber(this.value)){
					$('#alert-errors').modal('show');
					$('#alert-errors-content').html("Only numbers are allowed");
					this.focus();
				}else{
					ajaxInvokeMethods.postAjaxCallback("freightFlyUpdateAttributes.action", params, null);
				}
			});		
			
			$('#increaseSecurityChargeFreight-'+clave).on('blur', function() {
				var freightFlyAttributes = "FAR_INCREASE_SCC-" + this.value + "-" + this.id.split("-") [1] + "-" + this.id.split("-") [2];
				var params = "freightFlyAttributes="+freightFlyAttributes;
				if (!validationsAttributes.isNumber(this.value)){
					$('#alert-errors').modal('show');
					$('#alert-errors-content').html("Only numbers are allowed");
					this.focus();
				}else{
					ajaxInvokeMethods.postAjaxCallback("freightFlyUpdateAttributes.action", params, null);
				}
			});	
			
			
			
			$('#rangeRoute-'+listFreightAirVO[i].idFreight).on('click', function() {
				
				var params = "idFreightAir="+this.id.split("-") [1];
				ajaxInvokeMethods.postAjaxCallback("listRangeFreight.action", params, callbacklistRangeFreight);
				
				$('#modal-other-freight-air-ranges').modal('show'); 
			});
			
			
			$('#replicateFreight-'+clave).on('click', function() {
				var freightFlyAttributes = this.id.split("-") [1];
				var params = "freightFlyAttributes="+freightFlyAttributes;
				
				ajaxInvokeMethods.postAjaxCallback("replicateFreight.action", params, callbackReplicateFreight);
			});
			
			
			$('#deleteFreight-'+clave).on('click', function() {
				var freightFlyAttributes = this.id.split("-") [1];
				var params = "freightFlyAttributes="+freightFlyAttributes;
				
				ajaxInvokeMethods.postAjaxCallback("deleteFreightRoute.action", params, callbackReplicateFreight);
			});
			
			
			$('#idFreightRange-'+clave).on('change', function() {
				
				var selectorFreighRange = this.value.split("-");
				
				$("#fromFreightRange-"+this.id.split("-") [1]+"-"+this.id.split("-") [2] ).val(selectorFreighRange[0]);
				$("#toFreightRange-"+this.id.split("-") [1]+"-"+this.id.split("-") [2]).val(selectorFreighRange[1]);
				$("#rateFreightRange-"+this.id.split("-") [1]+"-"+this.id.split("-") [2]).val(selectorFreighRange[2]);
				
			});
			
			
			
			$('#FAR_CURRENCY_ID-'+clave).on('blur', function() {
				
				var freightFlyAttributes = "FAR_CURRENCY_ID-" + this.value + "-" + this.id.split("-") [1] + "-" + this.id.split("-") [2];
				var params = "freightFlyAttributes="+freightFlyAttributes;
				
				ajaxInvokeMethods.postAjaxCallback("freightFlyUpdateAttributes.action", params, null);
			});
			
			$('#FAR_ROUTE_DESCRIPTION-'+clave).on('blur', function() {
				
				var freightFlyAttributes = "FAR_ROUTE_DESCRIPTION-" + this.value + "-" + this.id.split("-") [1] + "-" + this.id.split("-") [2];
				var params = "freightFlyAttributes="+freightFlyAttributes;
				
				ajaxInvokeMethods.postAjaxCallback("freightFlyUpdateAttributes.action", params, null);
			});
			
			

			$('#ingreaseFreight-'+clave).on('blur', function() {
				var freightFlyAttributes = "FAR_INCREASE-" + this.value + "-" + this.id.split("-") [1] + "-" + this.id.split("-") [2];
				var params = "freightFlyAttributes="+freightFlyAttributes;
				if (!validationsAttributes.isNumber(this.value)){
					$('#alert-errors').modal('show');
					$('#alert-errors-content').html("Only numbers are allowed");
					this.focus();
				}else{
					ajaxInvokeMethods.postAjaxCallback("freightFlyUpdateAttributes.action", params, null);
				}
			});

			$('#awbRatesFreight-'+clave).on('blur', function() {
				
				var freightFlyAttributes = "FAR_AWB_KG_RATES-" + this.value + "-" + this.id.split("-") [1] + "-" + this.id.split("-") [2];
				var params = "freightFlyAttributes="+freightFlyAttributes;
				if (!validationsAttributes.isNumber(this.value)){
					$('#alert-errors').modal('show');
					$('#alert-errors-content').html("Only numbers are allowed");
					this.focus();
				}else{
					ajaxInvokeMethods.postAjaxCallback("freightFlyUpdateAttributes.action", params, null);
				}
			});

			$('#otherFFWFeesFreight-'+clave).on('blur', function() {
				
				var freightFlyAttributes = "FAR_OTHER_FFW_FEES-" + this.value + "-" + this.id.split("-") [1] + "-" + this.id.split("-") [2];
				var params = "freightFlyAttributes="+freightFlyAttributes;
				if (!validationsAttributes.isNumber(this.value)){
					$('#alert-errors').modal('show');
					$('#alert-errors-content').html("Only numbers are allowed");
					this.focus();
				}else{
					ajaxInvokeMethods.postAjaxCallback("freightFlyUpdateAttributes.action", params, null);
				}
			});

			$('#fuelSurchargeFreight-'+clave).on('blur', function() {
				
				var freightFlyAttributes = "FAR_FUEL_SURCHARGE-" + this.value + "-" + this.id.split("-") [1] + "-" + this.id.split("-") [2];
				var params = "freightFlyAttributes="+freightFlyAttributes;
				if (!validationsAttributes.isNumber(this.value)){
					$('#alert-errors').modal('show');
					$('#alert-errors-content').html("Only numbers are allowed");
					this.focus();
				}else{
					ajaxInvokeMethods.postAjaxCallback("freightFlyUpdateAttributes.action", params, null);
				}
			});

			$('#securityChargeFreight-'+clave).on('blur', function() {
				
				var freightFlyAttributes = "FAR_SECURITY_CHARGE-" + this.value + "-" + this.id.split("-") [1] + "-" + this.id.split("-") [2];
				var params = "freightFlyAttributes="+freightFlyAttributes;
				if (!validationsAttributes.isNumber(this.value)){
					$('#alert-errors').modal('show');
					$('#alert-errors-content').html("Only numbers are allowed");
					this.focus();
				}else{
					ajaxInvokeMethods.postAjaxCallback("freightFlyUpdateAttributes.action", params, null);
				}
			});
			
			$('#orderNumberFreight-'+clave).on('blur', function() {
				
				var freightFlyAttributes = "FAR_ORDERS_NUMBER-" + this.value + "-" + this.id.split("-") [1] + "-" + this.id.split("-") [2];
				var params = "freightFlyAttributes="+freightFlyAttributes;
				if (!validationsAttributes.isNumber(this.value)){
					$('#alert-errors').modal('show');
					$('#alert-errors-content').html("Only numbers are allowed");
					this.focus();
				}else{
					ajaxInvokeMethods.postAjaxCallback("freightFlyUpdateAttributes.action", params, null);
				}
			});
		}
		
		$('.body_page').css('height', 'auto');
		$('.body_page').height($( document ).height() - 172);
		$('.t-f-h-freight-aerial').fixedHeader();
	}
	
	
	function callbackSimulateStage(data){
		window.location.replace("resultCost.action");
		
	}
	
	function callbackInitItemMaster(data){
		
		var statusMessageError      = data.statusMessageError;
		var listItemMasterProductVO = data.listItemMasterProductVO;
		
		var listProductFamilies     = data.listProductFamilies;
//		var listProductTransports   = data.listProductTransports;
		var listProductUnitMeasure  = data.listProductUnitMeasure;
		var listSiteSource          = data.listSiteSource;
		var listProductTypes        = data.listProductTypes;
		var listProductTrade        = data.listProductTrade;
		var listProductPresentation = data.listProductPresentation;
		var listProductTypeLoad     = data.listProductTypeLoad;
		var listCustomDuty          = data.listCustomDuty;
		var listLocalRoute          = data.listLocalRoute;
		var listRateStorage         = data.listRateStorage;
		
		
		var selectListRateStorage = "";
		for (var i=0; i<listRateStorage.length; i++){
			selectListRateStorage = selectListRateStorage.concat("<option value='"+listRateStorage[i].idGeneralCombo+"'>"+listRateStorage[i].nameGeneralCombo+"</option>");
		}
		
		var selectListProductFamilies = "";
		for (var i=0; i<listProductFamilies.length; i++){
			selectListProductFamilies = selectListProductFamilies.concat("<option value='"+listProductFamilies[i].idGeneralCombo+"'>"+listProductFamilies[i].nameGeneralCombo+"</option>");
		}
		
//		var selectListProductTransports = "";
//		for (var i=0; i<listProductTransports.length; i++){
//			selectListProductTransports = selectListProductTransports.concat("<option value='"+listProductTransports[i].idGeneralCombo+"'>"+listProductTransports[i].nameGeneralCombo+"</option>");
//		}
		
		var selectListProductUnitMeasure = "";
		for (var i=0; i<listProductUnitMeasure.length; i++){
			selectListProductUnitMeasure = selectListProductUnitMeasure.concat("<option value='"+listProductUnitMeasure[i].idGeneralCombo+"'>"+listProductUnitMeasure[i].nameGeneralCombo+"</option>");
		}
		
		var selectListSiteSource = "";
		for (var i=0; i<listSiteSource.length; i++){
			selectListSiteSource = selectListSiteSource.concat("<option value='"+listSiteSource[i].idGeneralCombo+"'>"+listSiteSource[i].nameGeneralCombo+"</option>");
		}
		
		
		var selectListProductTypes = "";
		for (var i=0; i<listProductTypes.length; i++){
			selectListProductTypes = selectListProductTypes.concat("<option value='"+listProductTypes[i].idGeneralCombo+"'>"+listProductTypes[i].nameGeneralCombo+"</option>");
		}
		
		
		var selectListProductTrade = "";
		for (var i=0; i<listProductTrade.length; i++){
			selectListProductTrade = selectListProductTrade.concat("<option value='"+listProductTrade[i].idGeneralCombo+"'>"+listProductTrade[i].nameGeneralCombo+"</option>");
		}
		
		var selectListProductPresentation = "";
		for (var i=0; i<listProductPresentation.length; i++){
			selectListProductPresentation = selectListProductPresentation.concat("<option value='"+listProductPresentation[i].idGeneralCombo+"'>"+listProductPresentation[i].nameGeneralCombo+"</option>");
		}
		
		var selectListProductTypeLoad = "";
		for (var i=0; i<listProductTypeLoad.length; i++){
			selectListProductTypeLoad = selectListProductTypeLoad.concat("<option value='"+listProductTypeLoad[i].idGeneralCombo+"'>"+listProductTypeLoad[i].nameGeneralCombo+"</option>");
		}
		
		
		var selectListCustomDuty = "";
		for (var i=0; i<listCustomDuty.length; i++){
			selectListCustomDuty = selectListCustomDuty.concat("<option value='"+listCustomDuty[i].idGeneralCombo+"'>"+listCustomDuty[i].nameGeneralCombo+"</option>");
		}
		
		var selectListLocalRoute = "";
		for (var i=0; i<listLocalRoute.length; i++){
			selectListLocalRoute = selectListLocalRoute.concat("<option value='"+listLocalRoute[i].idGeneralCombo+"'>"+listLocalRoute[i].nameGeneralCombo+"</option>");
		}
		
		
		
		$("#table-item-master-tbody").empty();
		for (var i=0; i<listItemMasterProductVO.length; i++){
			
			var row = "";
			if (listItemMasterProductVO[i].isReplica == "1"){
				row = row.concat("<tr class='warning'>");
			}else{
				row = row.concat("<tr>");
			}
			
//			row = row.concat("<tr>");
			row = row.concat("<td>" + listItemMasterProductVO[i].localCodeProduct + "</td>");
			row = row.concat("<td>" + listItemMasterProductVO[i].descriptionProduct + "</td>");
			
//			row = row.concat("<td>" + "<select disabled='disabled' id='ITEM_MASTER_TYPE_PRODUCT-"+listItemMasterProductVO[i].localCodeProduct+"'>"+selectListProductTypes+"</select>" + "</td>");
//			row = row.concat("<td>" + "<select id='ITEM_MASTER_FAMILY_PRODUCT-"+listItemMasterProductVO[i].localCodeProduct+"'>"+selectListProductFamilies+"</select>" + "</td>");
//			row = row.concat("<td>" + "<select id='ITEM_MASTER_UMEASURE_PRODUCT-"+listItemMasterProductVO[i].localCodeProduct+"'>"+selectListProductUnitMeasure+"</select>" + "</td>");
			row = row.concat("<td>" + "<input  id='ITEM_MASTER_FOB_PRODUCT-"+listItemMasterProductVO[i].localCodeProduct+"' type='text' class='input-block-level' style='text-align: right; padding-right: 2px; width: 60px;'>" + "</td>");
			row = row.concat("<td>" + "<select id='ITEM_MASTER_SITE_PRODUCT-"+listItemMasterProductVO[i].localCodeProduct+"' style='width:100px;' >"+selectListSiteSource+ " </select>" + "</td>");
			row = row.concat("<td>" + "<input  id='ITEM_MASTER_HIGH_P_PRODUCT-"+listItemMasterProductVO[i].localCodeProduct+"' type='text' class='input-block-level'   style='text-align: right; padding-right: 2px; width: 60px;'>" + "</td>");
			row = row.concat("<td>" + "<input  id='ITEM_MASTER_LARGE_P_PRODUCT-"+listItemMasterProductVO[i].localCodeProduct+"' type='text' class='input-block-level'  style='text-align: right; padding-right: 2px; width: 60px;'>" + "</td>");
			row = row.concat("<td>" + "<input  id='ITEM_MASTER_WIDE_P_PRODUCT-"+listItemMasterProductVO[i].localCodeProduct+"' type='text' class='input-block-level'   style='text-align: right; padding-right: 2px; width: 60px;'>" + "</td>");
			row = row.concat("<td>" + "<input  id='ITEM_MASTER_WEIGHT_P_PRODUCT-"+listItemMasterProductVO[i].localCodeProduct+"' type='text' class='input-block-level' style='text-align: right; padding-right: 2px; width: 60px;'>" + "</td>");
			row = row.concat("<td>" + "<input  id='ITEM_MASTER_UNITS_P_PRODUCT-"+listItemMasterProductVO[i].localCodeProduct+"' type='text' class='input-block-level'  style='text-align: right; padding-right: 2px; width: 60px;'>" + "</td>");
			
//			row = row.concat("<td>" + "<select id='ITEM_MASTER_TRADE_PRODUCT-"+listItemMasterProductVO[i].localCodeProduct+"'>"+selectListProductTrade+"</select>" + "</td>");
			
//			row = row.concat("<td>" + "<select id='ITEM_MASTER_PRESEN_PRODUCT-"+listItemMasterProductVO[i].localCodeProduct+"'>"+selectListProductPresentation+"</select>" + "</td>");
//			row = row.concat("<td>" + "<select id='ITEM_MASTER_TYPE_L_PRODUCT-"+listItemMasterProductVO[i].localCodeProduct+"'>"+selectListProductTypeLoad+"</select>" + "</td>");
			row = row.concat("<td>" + "<select id='ITEM_MASTER_CUSTOM_DUTIES-"+listItemMasterProductVO[i].localCodeProduct+"'>"+selectListCustomDuty+"</select>" + "</td>");
			
			row = row.concat("<td>" + "<select id='ITEM_MASTER_LOCAL_ROUTES-"+listItemMasterProductVO[i].localCodeProduct+"'>"+selectListLocalRoute+"</select>" + "</td>");
			row = row.concat("<td>" + "<select id='ITEM_MASTER_RATE_STORAGE-"+listItemMasterProductVO[i].localCodeProduct+"'>"+selectListRateStorage+"</select>" + "</td>");
			row = row.concat("<td>" + "<select id='ITEM_MASTER_IS_IVA_PRODUCT-"+listItemMasterProductVO[i].localCodeProduct+"'>"+"<option value='Y'>Yes</option><option value='N'>No</option>"+"</select>" + "</td>");
			
			
			row = row.concat("</tr>");
			
			$("#table-item-master").append(row);
			
			
			//Setting selectores & Values Text
			
//			$("#ITEM_MASTER_TYPE_PRODUCT-"+listItemMasterProductVO[i].localCodeProduct).val(listItemMasterProductVO[i].idTypeProduct);
//			$("#ITEM_MASTER_FAMILY_PRODUCT-"+listItemMasterProductVO[i].localCodeProduct).val(listItemMasterProductVO[i].idFamilyProduct);
//			$("#ITEM_MASTER_UMEASURE_PRODUCT-"+listItemMasterProductVO[i].localCodeProduct).val(listItemMasterProductVO[i].idUnitMeasureProduct);
			$("#ITEM_MASTER_FOB_PRODUCT-"+listItemMasterProductVO[i].localCodeProduct).val(listItemMasterProductVO[i].valueFOBProduct);
			$("#ITEM_MASTER_SITE_PRODUCT-"+listItemMasterProductVO[i].localCodeProduct).val(listItemMasterProductVO[i].idSiteSource);
			$("#ITEM_MASTER_HIGH_P_PRODUCT-"+listItemMasterProductVO[i].localCodeProduct).val(listItemMasterProductVO[i].highPalette);
			$("#ITEM_MASTER_LARGE_P_PRODUCT-"+listItemMasterProductVO[i].localCodeProduct).val(listItemMasterProductVO[i].largePalette);
			$("#ITEM_MASTER_WIDE_P_PRODUCT-"+listItemMasterProductVO[i].localCodeProduct).val(listItemMasterProductVO[i].widePalette);
			$("#ITEM_MASTER_WEIGHT_P_PRODUCT-"+listItemMasterProductVO[i].localCodeProduct).val(listItemMasterProductVO[i].weightPalette);
			$("#ITEM_MASTER_UNITS_P_PRODUCT-"+listItemMasterProductVO[i].localCodeProduct).val(listItemMasterProductVO[i].unitsByPalette);
			
//			$("#ITEM_MASTER_PRESEN_PRODUCT-"+listItemMasterProductVO[i].localCodeProduct).val(listItemMasterProductVO[i].idPresentationProduct);
//			$("#ITEM_MASTER_TRADE_PRODUCT-"+listItemMasterProductVO[i].localCodeProduct).val(listItemMasterProductVO[i].idTradeProduct);
//			$("#ITEM_MASTER_TYPE_L_PRODUCT-"+listItemMasterProductVO[i].localCodeProduct).val(listItemMasterProductVO[i].idTypeLoadProduct);
			$("#ITEM_MASTER_CUSTOM_DUTIES-"+listItemMasterProductVO[i].localCodeProduct).val(listItemMasterProductVO[i].idCustomDutyProduct);
			
			$("#ITEM_MASTER_LOCAL_ROUTES-"+listItemMasterProductVO[i].localCodeProduct).val(listItemMasterProductVO[i].idLocalRoute);
			$("#ITEM_MASTER_RATE_STORAGE-"+listItemMasterProductVO[i].localCodeProduct).val(listItemMasterProductVO[i].idRateStorage);
			$("#ITEM_MASTER_IS_IVA_PRODUCT-"+listItemMasterProductVO[i].localCodeProduct).val(listItemMasterProductVO[i].isIVA);
			
			
			
			
			//Eventos para los atributos
			
			$('#ITEM_MASTER_IS_IVA_PRODUCT-'+listItemMasterProductVO[i].localCodeProduct).on('change', function() {
				
				var itemMasterAttribute = "ITEM_MASTER_IS_IVA_PRODUCT-"+  this.id.split("-") [1]+"-"+this.value;
				var params = "itemMasterAttribute="+itemMasterAttribute;
				
				ajaxInvokeMethods.postAjaxCallback("itemMasterUpdateAttributes.action", params, null);
			});
			
			$('#ITEM_MASTER_RATE_STORAGE-'+listItemMasterProductVO[i].localCodeProduct).on('change', function() {
				
				var itemMasterAttribute = "ITEM_MASTER_RATE_STORAGE-"+  this.id.split("-") [1]+"-"+this.value;
				var params = "itemMasterAttribute="+itemMasterAttribute;
				
				ajaxInvokeMethods.postAjaxCallback("itemMasterUpdateAttributes.action", params, null);
			});

			$('#ITEM_MASTER_LOCAL_ROUTES-'+listItemMasterProductVO[i].localCodeProduct).on('change', function() {
				
				var itemMasterAttribute = "ITEM_MASTER_LOCAL_ROUTES-"+  this.id.split("-") [1]+"-"+this.value;
				var params = "itemMasterAttribute="+itemMasterAttribute;
				
				ajaxInvokeMethods.postAjaxCallback("itemMasterUpdateAttributes.action", params, null);
			});
			
			$('#ITEM_MASTER_CUSTOM_DUTIES-'+listItemMasterProductVO[i].localCodeProduct).on('change', function() {
				
				var itemMasterAttribute = "ITEM_MASTER_CUSTOM_DUTIES-"+  this.id.split("-") [1]+"-"+this.value;
				var params = "itemMasterAttribute="+itemMasterAttribute;
				
				ajaxInvokeMethods.postAjaxCallback("itemMasterUpdateAttributes.action", params, null);
			});
			
//			$('#ITEM_MASTER_TYPE_PRODUCT-'+listItemMasterProductVO[i].localCodeProduct).on('change', function() {
//				
//				var itemMasterAttribute = "ITEM_MASTER_TYPE_PRODUCT-"+  this.id.split("-") [1]+"-"+this.value
//				var params = "itemMasterAttribute="+itemMasterAttribute;
//				
//				ajaxInvokeMethods.postAjaxCallback("itemMasterUpdateAttributes.action", params, null);
//			});
//			
//			$('#ITEM_MASTER_FAMILY_PRODUCT-'+listItemMasterProductVO[i].localCodeProduct).on('change', function() {
//				
//				var itemMasterAttribute = "ITEM_MASTER_FAMILY_PRODUCT-"+  this.id.split("-") [1]+"-"+this.value
//				var params = "itemMasterAttribute="+itemMasterAttribute;
//				
//				ajaxInvokeMethods.postAjaxCallback("itemMasterUpdateAttributes.action", params, null);
//			});
			
			
			$('#ITEM_MASTER_UMEASURE_PRODUCT-'+listItemMasterProductVO[i].localCodeProduct).on('change', function() {
				
				var itemMasterAttribute = "ITEM_MASTER_UMEASURE_PRODUCT-"+  this.id.split("-") [1]+"-"+this.value;
				var params = "itemMasterAttribute="+itemMasterAttribute;
				
				ajaxInvokeMethods.postAjaxCallback("itemMasterUpdateAttributes.action", params, null);
			});
			
			$('#ITEM_MASTER_FOB_PRODUCT-'+listItemMasterProductVO[i].localCodeProduct).on('blur', function() {
				
				var itemMasterAttribute = "ITEM_MASTER_FOB_PRODUCT-"+  this.id.split("-") [1]+"-"+this.value
				var params = "itemMasterAttribute="+itemMasterAttribute;
				
				valNumberITMaster(this, params);
				
			});
			
			$('#ITEM_MASTER_SITE_PRODUCT-'+listItemMasterProductVO[i].localCodeProduct).on('change', function() {
				
				var itemMasterAttribute = "ITEM_MASTER_SITE_PRODUCT-"+  this.id.split("-") [1]+"-"+this.value
				var params = "itemMasterAttribute="+itemMasterAttribute;
				
				ajaxInvokeMethods.postAjaxCallback("itemMasterUpdateAttributes.action", params, null);
			});
			
			$('#ITEM_MASTER_HIGH_P_PRODUCT-'+listItemMasterProductVO[i].localCodeProduct).on('blur', function() {
				
				var itemMasterAttribute = "ITEM_MASTER_HIGH_P_PRODUCT-"+  this.id.split("-") [1]+"-"+this.value
				var params = "itemMasterAttribute="+itemMasterAttribute;

				valNumberITMaster(this, params);

			});
			
			$('#ITEM_MASTER_LARGE_P_PRODUCT-'+listItemMasterProductVO[i].localCodeProduct).on('blur', function() {
				
				var itemMasterAttribute = "ITEM_MASTER_LARGE_P_PRODUCT-"+  this.id.split("-") [1]+"-"+this.value
				var params = "itemMasterAttribute="+itemMasterAttribute;
				
				valNumberITMaster(this, params);

			});
			
			$('#ITEM_MASTER_WIDE_P_PRODUCT-'+listItemMasterProductVO[i].localCodeProduct).on('blur', function() {
				
				var itemMasterAttribute = "ITEM_MASTER_WIDE_P_PRODUCT-"+  this.id.split("-") [1]+"-"+this.value
				var params = "itemMasterAttribute="+itemMasterAttribute;
				
				valNumberITMaster(this, params);

			});
			
			
			$('#ITEM_MASTER_WEIGHT_P_PRODUCT-'+listItemMasterProductVO[i].localCodeProduct).on('blur', function() {
				
				var itemMasterAttribute = "ITEM_MASTER_WEIGHT_P_PRODUCT-"+  this.id.split("-") [1]+"-"+this.value
				var params = "itemMasterAttribute="+itemMasterAttribute;
				
				valNumberITMaster(this, params);

			});
			
			
			$('#ITEM_MASTER_UNITS_P_PRODUCT-'+listItemMasterProductVO[i].localCodeProduct).on('blur', function() {
				
				var itemMasterAttribute = "ITEM_MASTER_UNITS_P_PRODUCT-"+  this.id.split("-") [1]+"-"+this.value
				var params = "itemMasterAttribute="+itemMasterAttribute;
				
				valNumberITMaster(this, params);

			});
			
			
//			$('#ITEM_MASTER_TRANS_PRODUCT-'+listItemMasterProductVO[i].localCodeProduct).on('change', function() {
//				
//				var itemMasterAttribute = "ITEM_MASTER_TRANS_PRODUCT-"+  this.id.split("-") [1]+"-"+this.value
//				var params = "itemMasterAttribute="+itemMasterAttribute;
//				
//				ajaxInvokeMethods.postAjaxCallback("itemMasterUpdateAttributes.action", params, null);
//			});	
			
			
//			$('#ITEM_MASTER_PRESEN_PRODUCT-'+listItemMasterProductVO[i].localCodeProduct).on('change', function() {
//				
//				var itemMasterAttribute = "ITEM_MASTER_PRESEN_PRODUCT-"+  this.id.split("-") [1]+"-"+this.value
//				var params = "itemMasterAttribute="+itemMasterAttribute;
//				
//				ajaxInvokeMethods.postAjaxCallback("itemMasterUpdateAttributes.action", params, null);
//			});	
			
//			$('#ITEM_MASTER_TRADE_PRODUCT-'+listItemMasterProductVO[i].localCodeProduct).on('change', function() {
//				
//				var itemMasterAttribute = "ITEM_MASTER_TRADE_PRODUCT-"+  this.id.split("-") [1]+"-"+this.value
//				var params = "itemMasterAttribute="+itemMasterAttribute;
//				
//				ajaxInvokeMethods.postAjaxCallback("itemMasterUpdateAttributes.action", params, null);
//			});	
			
			$('#ITEM_MASTER_TYPE_L_PRODUCT-'+listItemMasterProductVO[i].localCodeProduct).on('change', function() {
				
				var itemMasterAttribute = "ITEM_MASTER_TYPE_L_PRODUCT-"+  this.id.split("-") [1]+"-"+this.value
				var params = "itemMasterAttribute="+itemMasterAttribute;
				
				ajaxInvokeMethods.postAjaxCallback("itemMasterUpdateAttributes.action", params, null);
			});
			
		}
		
		$('#loading-local').hide();
		
		$('.body_page').css('height', 'auto');
		$('.body_page').height($( document ).height() - 172);
		$('.t-f-h-item-master').fixedHeader();
	}
	
	
	
	function valNumberITMaster(obj, params){
//		console.log(obj.value);
		if (!validationsAttributes.isNumber(obj.value)){
			$('#alert-errors').modal('show');
			$('#alert-errors-content').html("Only numbers are allowed");
//			obj.focus();
		}else{
			ajaxInvokeMethods.postAjaxCallback("itemMasterUpdateAttributes.action", params, null);
		}		
	}
	
	function callbackInitImportUnit(data){
		
		var statusMessageError  = data.statusMessageError;
		var listImportUnitVO 		= data.listImportUnitVO;
		
		
		$("#table-import-units-tbody").empty();
		for (var i=0; i<listImportUnitVO.length; i++){
			
			var row = "";
			
			if (listImportUnitVO[i].replica == "1"){
				row = row.concat("<tr class='warning'>");
			}else{
				row = row.concat("<tr>");
			}					
			
			if (listImportUnitVO[i].replica == "0"){
				row = row.concat("<td>" + "<span  id='IMPORT_UNITS_LOCAL_CODE-"+listImportUnitVO[i].localCode+"' style='text-align: right; padding-right: 2px;'>" + "</span></td>");
				row = row.concat("<td>" + '<span  id="IMPORT_UNITS_DESCRIPTION_PRODUCT-'+listImportUnitVO[i].localCode+'" style="text-align: right; padding-right: 2px;">' + "</span></td>");
			}else{
				row = row.concat("<td>" + "<input  id='IMPORT_UNITS_LOCAL_CODE-"+listImportUnitVO[i].localCode+"' type='text' class='input-block-level' style='text-align: left; padding-right: 2px;'></td>");
				row = row.concat("<td>"+ "<input id='IMPORT_UNITS_DESCRIPTION_PRODUCT-"+listImportUnitVO[i].localCode+"' type='text' class='input-block-level' style='text-align: left; padding-right: 2px;'></td>");
			}
			
			row = row.concat("<td>" + "<input id='IMPORT_UNITS_JAN-"+listImportUnitVO[i].localCode+"' type='text' class='input-block-level' style='text-align: right; padding-right: 2px;' maxlength='8'>" + "</td>");
			row = row.concat("<td>" + "<input id='IMPORT_UNITS_FEB-"+listImportUnitVO[i].localCode+"' type='text' class='input-block-level' style='text-align: right; padding-right: 2px;' maxlength='8'>" + "</td>");
			row = row.concat("<td>" + "<input id='IMPORT_UNITS_MAR-"+listImportUnitVO[i].localCode+"' type='text' class='input-block-level' style='text-align: right; padding-right: 2px;' maxlength='8'>" + "</td>");
			row = row.concat("<td>" + "<input id='IMPORT_UNITS_APR-"+listImportUnitVO[i].localCode+"' type='text' class='input-block-level' style='text-align: right; padding-right: 2px;' maxlength='8'>" + "</td>");
			row = row.concat("<td>" + "<input id='IMPORT_UNITS_MAY-"+listImportUnitVO[i].localCode+"' type='text' class='input-block-level' style='text-align: right; padding-right: 2px;' maxlength='8'>" + "</td>");
			row = row.concat("<td>" + "<input id='IMPORT_UNITS_JUN-"+listImportUnitVO[i].localCode+"' type='text' class='input-block-level' style='text-align: right; padding-right: 2px;' maxlength='8'>" + "</td>");
			row = row.concat("<td>" + "<input id='IMPORT_UNITS_JUL-"+listImportUnitVO[i].localCode+"' type='text' class='input-block-level' style='text-align: right; padding-right: 2px;' maxlength='8'>" + "</td>");
			row = row.concat("<td>" + "<input id='IMPORT_UNITS_AUG-"+listImportUnitVO[i].localCode+"' type='text' class='input-block-level' style='text-align: right; padding-right: 2px;' maxlength='8'>" + "</td>");
			row = row.concat("<td>" + "<input id='IMPORT_UNITS_SEP-"+listImportUnitVO[i].localCode+"' type='text' class='input-block-level' style='text-align: right; padding-right: 2px;' maxlength='8'>" + "</td>");
			row = row.concat("<td>" + "<input id='IMPORT_UNITS_OCT-"+listImportUnitVO[i].localCode+"' type='text' class='input-block-level' style='text-align: right; padding-right: 2px;' maxlength='8'>" + "</td>");
			row = row.concat("<td>" + "<input id='IMPORT_UNITS_NOV-"+listImportUnitVO[i].localCode+"' type='text' class='input-block-level' style='text-align: right; padding-right: 2px;' maxlength='8'>" + "</td>");
			row = row.concat("<td>" + "<input id='IMPORT_UNITS_DEC-"+listImportUnitVO[i].localCode+"' type='text' class='input-block-level' style='text-align: right; padding-right: 2px;' maxlength='8'>" + "</td>");
			row = row.concat("<td>" + "<span  id='IMPORT_UNITS_TOTAL-"+listImportUnitVO[i].localCode+"' style='text-align: right; padding-right: 2px;'>" + "</span></td>");
			row = row.concat("<td>" + "<span  id='IMPORT_UNITS_MEDIA-"+listImportUnitVO[i].localCode+"' style='text-align: right; padding-right: 2px;'>" + "</span></td>");
			row = row.concat("<td>" + "<input id='IMPORT_UNITS_AJUSTED-"+listImportUnitVO[i].localCode+"' type='text' class='input-block-level' style='text-align: right; padding-right: 2px;' maxlength='8'>" + "</td>");
			
			row = row.concat("<td><table class=''>");
			row = row.concat("<td style='padding:0px; border:0px;'><a class='btn_table' href='#' title='hide' value='importUnitAttribute="+listImportUnitVO[i].localCode+"' onclick='filterUno(this)'><i class='icon-remove'></i></a></td>");
			if (listImportUnitVO[i].replica == "0"){  
				row = row.concat("<td style='padding:0px; border:0px;'><a class='btn_table' href='#' title='replicar' value='importUnitAttribute="+listImportUnitVO[i].localCode+"' onclick='replicar(this)'><i class='icon-retweet'></i></a></td>");
			}else{
				row = row.concat("<td style='padding:0px; border:0px;'><a class='btn_table' href='#' title='remove' value='importUnitAttribute="+listImportUnitVO[i].localCode+"' onclick='eliminar(this)'><i class='icon-trash'></i></a></td>");
			}
			row = row.concat("</tr></table></td>"); 
			
			row = row.concat("</tr>");
			$("#table-import-units").append(row);
			
			
			//Setting selectores & Values Text
				
			if (listImportUnitVO[i].replica == "0"){
				$("#IMPORT_UNITS_LOCAL_CODE-"+listImportUnitVO[i].localCode).text(listImportUnitVO[i].localCode);
				$("#IMPORT_UNITS_DESCRIPTION_PRODUCT-"+listImportUnitVO[i].localCode).text(listImportUnitVO[i].descriptionProduct);
			}else{
				$("#IMPORT_UNITS_LOCAL_CODE-"+listImportUnitVO[i].localCode).val(listImportUnitVO[i].localCode);
				$("#IMPORT_UNITS_DESCRIPTION_PRODUCT-"+listImportUnitVO[i].localCode).val(listImportUnitVO[i].descriptionProduct);
			}
			
			$("#IMPORT_UNITS_JAN-"+listImportUnitVO[i].localCode).val(listImportUnitVO[i].jan);
			$("#IMPORT_UNITS_FEB-"+listImportUnitVO[i].localCode).val(listImportUnitVO[i].feb);
			$("#IMPORT_UNITS_MAR-"+listImportUnitVO[i].localCode).val(listImportUnitVO[i].mar);
			$("#IMPORT_UNITS_APR-"+listImportUnitVO[i].localCode).val(listImportUnitVO[i].apr);
			$("#IMPORT_UNITS_MAY-"+listImportUnitVO[i].localCode).val(listImportUnitVO[i].may);
			$("#IMPORT_UNITS_JUN-"+listImportUnitVO[i].localCode).val(listImportUnitVO[i].jun);
			$("#IMPORT_UNITS_JUL-"+listImportUnitVO[i].localCode).val(listImportUnitVO[i].jul);
			$("#IMPORT_UNITS_AUG-"+listImportUnitVO[i].localCode).val(listImportUnitVO[i].aug);
			$("#IMPORT_UNITS_SEP-"+listImportUnitVO[i].localCode).val(listImportUnitVO[i].sep);
			$("#IMPORT_UNITS_OCT-"+listImportUnitVO[i].localCode).val(listImportUnitVO[i].oct);
			$("#IMPORT_UNITS_NOV-"+listImportUnitVO[i].localCode).val(listImportUnitVO[i].nov);
			$("#IMPORT_UNITS_DEC-"+listImportUnitVO[i].localCode).val(listImportUnitVO[i].dec);
			$("#IMPORT_UNITS_TOTAL-"+listImportUnitVO[i].localCode).text(listImportUnitVO[i].total);
			$("#IMPORT_UNITS_MEDIA-"+listImportUnitVO[i].localCode).text(listImportUnitVO[i].media);
			$("#IMPORT_UNITS_AJUSTED-"+listImportUnitVO[i].localCode).val(listImportUnitVO[i].ajusted);


			if (listImportUnitVO[i].replica == "1"){
				
				$('#IMPORT_UNITS_LOCAL_CODE-'+listImportUnitVO[i].localCode).on('blur', function() {
					
					
					
					var importUnitAttribute = "IMPORT_UNITS_LOCAL_CODE-"+  this.id.split("-") [1] +"-"+this.value
					var params = "importUnitAttribute="+importUnitAttribute;
					ajaxInvokeMethods.postAjaxCallback("importUnitUpdateAttributes.action", params, function(data){
						
						
						var statusMessageError = data.statusMessageError;
						if (statusMessageError != "")
							$('#alert-errors').modal('show');
							$('#alert-errors-content').html(statusMessageError);
							
						
						
					});

				});

				$('#IMPORT_UNITS_DESCRIPTION_PRODUCT-'+listImportUnitVO[i].localCode).on('blur', function() {
					
					
					
					var importUnitAttribute = "IMPORT_DESRIPTION_PRODUCT-"+  this.id.split("-") [1] + "-" +this.id.split("-") [2]+"-"+this.value
					var params = "importUnitAttribute="+importUnitAttribute;
					ajaxInvokeMethods.postAjaxCallback("importUnitUpdateAttributes.action", params, function(data){
					});

				});
			}

			//Eventos para los atributos
			$('#IMPORT_UNITS_JAN-'+listImportUnitVO[i].localCode).on('blur', function() {
				
				var importUnitAttribute = "IMPORT_UNITS_JAN-"+  this.id.split("-") [1]+"-"+this.value
				var params = "importUnitAttribute="+importUnitAttribute;
				valNumber(this, params );

			});

			$('#IMPORT_UNITS_FEB-'+listImportUnitVO[i].localCode).on('blur', function() {
				
				var importUnitAttribute = "IMPORT_UNITS_FEB-"+  this.id.split("-") [1]+"-"+this.value
				var params = "importUnitAttribute="+importUnitAttribute;
				
				valNumber(this, params);
			});

			$('#IMPORT_UNITS_MAR-'+listImportUnitVO[i].localCode).on('blur', function() {
				
				var importUnitAttribute = "IMPORT_UNITS_MAR-"+  this.id.split("-") [1]+"-"+this.value
				var params = "importUnitAttribute="+importUnitAttribute;
				
				valNumber(this, params);
			});

			$('#IMPORT_UNITS_APR-'+listImportUnitVO[i].localCode).on('blur', function() {
				
				var importUnitAttribute = "IMPORT_UNITS_APR-"+  this.id.split("-") [1]+"-"+this.value
				var params = "importUnitAttribute="+importUnitAttribute;
				
				valNumber(this, params);
			});

			$('#IMPORT_UNITS_MAY-'+listImportUnitVO[i].localCode).on('blur', function() {
				
				var importUnitAttribute = "IMPORT_UNITS_MAY-"+  this.id.split("-") [1]+"-"+this.value
				var params = "importUnitAttribute="+importUnitAttribute;
				
				valNumber(this, params);
			});

			$('#IMPORT_UNITS_JUN-'+listImportUnitVO[i].localCode).on('blur', function() {
				
				var importUnitAttribute = "IMPORT_UNITS_JUN-"+  this.id.split("-") [1]+"-"+this.value
				var params = "importUnitAttribute="+importUnitAttribute;
				
				valNumber(this, params);
			});

			$('#IMPORT_UNITS_JUL-'+listImportUnitVO[i].localCode).on('blur', function() {
				
				var importUnitAttribute = "IMPORT_UNITS_JUL-"+  this.id.split("-") [1]+"-"+this.value
				var params = "importUnitAttribute="+importUnitAttribute;
				
				valNumber(this, params);
			});

			$('#IMPORT_UNITS_AUG-'+listImportUnitVO[i].localCode).on('blur', function() {
				
				var importUnitAttribute = "IMPORT_UNITS_AUG-"+  this.id.split("-") [1]+"-"+this.value
				var params = "importUnitAttribute="+importUnitAttribute;
				
				valNumber(this, params);
			});

			$('#IMPORT_UNITS_SEP-'+listImportUnitVO[i].localCode).on('blur', function() {
				
				var importUnitAttribute = "IMPORT_UNITS_SEP-"+  this.id.split("-") [1]+"-"+this.value
				var params = "importUnitAttribute="+importUnitAttribute;
				
				valNumber(this, params);
			});

			$('#IMPORT_UNITS_OCT-'+listImportUnitVO[i].localCode).on('blur', function() {
				
				var importUnitAttribute = "IMPORT_UNITS_OCT-"+  this.id.split("-") [1]+"-"+this.value
				var params = "importUnitAttribute="+importUnitAttribute;
				
				valNumber(this, params);
			});

			$('#IMPORT_UNITS_NOV-'+listImportUnitVO[i].localCode).on('blur', function() {
				
				var importUnitAttribute = "IMPORT_UNITS_NOV-"+  this.id.split("-") [1]+"-"+this.value
				var params = "importUnitAttribute="+importUnitAttribute;
				
				valNumber(this, params);
			});

			$('#IMPORT_UNITS_DEC-'+listImportUnitVO[i].localCode).on('blur', function() {
				
				var importUnitAttribute = "IMPORT_UNITS_DEC-"+  this.id.split("-") [1]+"-"+this.value
				var params = "importUnitAttribute="+importUnitAttribute;
				
				valNumber(this, params);
			});

			$('#IMPORT_UNITS_AJUSTED-'+listImportUnitVO[i].localCode).on('blur', function() {
				
				var importUnitAttribute = "IMPORT_UNITS_AJUSTED-"+  this.id.split("-") [1]+"-"+this.value
				var params = "importUnitAttribute="+importUnitAttribute;
				
				if (!validationsAttributes.isNumber(this.value)){
					$('#alert-errors').modal('show');
					$('#alert-errors-content').html("Only numbers are allowed");
					this.focus();
				}else{
					ajaxInvokeMethods.postAjaxCallback("importUnitUpdateAttributes.action", params, null);
				}
			});

			
		}
		
		
		$('#loading-local').hide();
		
		$('.body_page').css('height', 'auto');
		$('.body_page').height($( document ).height() - 172);
		
		$('.table-fixed-header').fixedHeader();
	}
	
	function valNumber(obj, params){
		if (!validationsAttributes.isNumber(obj.value)){
			$('#alert-errors').modal('show');
			$('#alert-errors-content').html("Only numbers are allowed");
//			obj.focus();
		}else{
			ajaxInvokeMethods.postAjaxCallback("importUnitUpdateAttributes.action", params, callbackRefreshData);
		}
		
	}
	
	function callbackRefreshData(data){
		
		var importUnitVO = data.importUnitVO;
		
		$("#IMPORT_UNITS_TOTAL-"+importUnitVO.localCode).text(importUnitVO.total);
		$("#IMPORT_UNITS_MEDIA-"+importUnitVO.localCode).text(importUnitVO.media);
		$("#IMPORT_UNITS_AJUSTED-"+importUnitVO.localCode).val(importUnitVO.ajusted);

	}
	
	function callbackInitSetupTempStorage(data){
		var listTempStorageVO = data.listTempStorageVO;
		var listCurrencyVO = data.listCurrency;
		var listTempStorageRange = data.listTempStorageRange;
		
		var optionCurID = "";
		
		$( "#temp-storage-range" ).empty();
		for (var j=0; j<listTempStorageRange.length; j++){
			var optionRow = "<option value='"+listTempStorageRange[j].idGeneralCombo+"'>"+listTempStorageRange[j].nameGeneralCombo+"</option>";
			$("#temp-storage-range").append(optionRow);
		}
		
		$('#temp-storage-range').unbind('change');
		$('#temp-storage-range').on('change', function() {
			var params = "rangeGenericVO.id=" + this.value;
			ajaxInvokeMethods.postAjaxCallback("setupGetTempStorageRange.action", params, callbackGetRangeTempStorage);
			
		});
		
		$('#temp-storage-range').trigger("change");
		
//		var params = "rangeGenericVO.id="+this.value;
		
		$('#temp-storage-range-from').unbind('blur');
		$('#temp-storage-range-up').unbind('blur');
		$('#temp-storage-range-rate').unbind('blur');
		
		$('#temp-storage-range-from').on('blur', function() {
			var setupTempStorage = "SSR_RANGE_FROM-"+this.value + "-" +  $('#temp-storage-range').val();
			var params = "setupTempStorage="+setupTempStorage;
			if (!validationsAttributes.isNumber(this.value)){
				$('#alert-errors').modal('show');
				$('#alert-errors-content').html("Only numbers are allowed");
				this.focus();
			}else{
				ajaxInvokeMethods.postAjaxCallback("setupLocalRangeUpdateAttributes.action", params, null);
				$('#nav-setup a[href="#nav-setup-temp-storage"]').trigger("click");
			}
		});
		
		$('#temp-storage-range-up').on('blur', function() {
			
			var setupTempStorage = "SSR_RANGE_UO-"+this.value + "-" + $('#temp-storage-range').val();
			var params = "setupTempStorage="+setupTempStorage;
			if (!validationsAttributes.isNumber(this.value)){
				$('#alert-errors').modal('show');
				$('#alert-errors-content').html("Only numbers are allowed");
				this.focus();
			}else{
				ajaxInvokeMethods.postAjaxCallback("setupLocalRangeUpdateAttributes.action", params, null);
				$('#nav-setup a[href="#nav-setup-temp-storage"]').trigger("click");
			}
		});
		
		$('#temp-storage-range-rate').on('blur', function() {
			
			var setupTempStorage = "SSR_RANGE_RATE-"+this.value + "-" +  $('#temp-storage-range').val();
			var params = "setupTempStorage="+setupTempStorage;
			if (!validationsAttributes.isNumber(this.value)){
				$('#alert-errors').modal('show');
				$('#alert-errors-content').html("Only numbers are allowed");
				this.focus();
			}else{
				ajaxInvokeMethods.postAjaxCallback("setupLocalRangeUpdateAttributes.action", params, null);
			}
		});

		
		
		$("#table-setup-temp-storage-tbody").empty();

		$("#temp-storage-range-currency").empty();
		for (var i=0; i < listCurrencyVO.length; i++){
			optionCurID = optionCurID.concat("<option value='"+listCurrencyVO[i].idGeneralCombo+"'>"+listCurrencyVO[i].nameGeneralCombo+"</option>");
		}
		$("#temp-storage-range-currency").append(optionCurID);
		

		for (var j=0; j<listTempStorageVO.length; j++){

			//LG
			var row = "";
			row = row.concat("<tr>");
//			row = row.concat("<td>"+listTempStorageVO[j].entry+"</td>");
			row = row.concat("<td><input type='text' id='SST_ENTRY-"+listTempStorageVO[j].sstId+"' class='input-block-level' value='12,00' style='text-align: right;' ></td>");
			row = row.concat("<td><input type='text' id='SST_PERCENT_COST-"+listTempStorageVO[j].sstId+"' class='input-block-level' value='12,00' style='text-align: right;' ></td>");
			row = row.concat("<td><input type='text' id='SST_PROCESSING_FEE-"+listTempStorageVO[j].sstId+"' class='input-block-level' value='50,00' style='text-align: right;'></td>");
			row = row.concat("<td>");
			row = row.concat("	<select id='CUR_ID-"+listTempStorageVO[j].sstId+"'>" + optionCurID + "	</select>");
			row = row.concat("</td>");
			row = row.concat("</tr>");
			
			$("#table-setup-temp-storage-tbody").append(row);
			
			$("#SST_ENTRY-"+listTempStorageVO[j].sstId).val(listTempStorageVO[j].entry);
			$("#SST_PERCENT_COST-"+listTempStorageVO[j].sstId).val(listTempStorageVO[j].percentCost);
			$("#SST_PROCESSING_FEE-"+listTempStorageVO[j].sstId).val(listTempStorageVO[j].processingFee);
			$("#CUR_ID-"+listTempStorageVO[j].sstId).val(listTempStorageVO[j].currencyID);
			
			
			$('#SST_ENTRY-'+listTempStorageVO[j].sstId).on('blur', function() {
				
				var setupTempStorage = "SST_ENTRY-"+this.value + "-" + this.id.split("-")[1];
				var params = "setupTempStorage="+setupTempStorage;
				ajaxInvokeMethods.postAjaxCallback("setupTempStorageUpdateAttributes.action", params, null);
			});
			
			
			$('#SST_PERCENT_COST-'+listTempStorageVO[j].sstId).on('blur', function() {
				
				var setupTempStorage = "SST_PERCENT_COST-"+this.value + "-" + this.id.split("-")[1];
				var params = "setupTempStorage="+setupTempStorage;
				if (!validationsAttributes.isNumber(this.value)){
					$('#alert-errors').modal('show');
					$('#alert-errors-content').html("Only numbers are allowed");
					this.focus();
				}else{
					ajaxInvokeMethods.postAjaxCallback("setupTempStorageUpdateAttributes.action", params, null);
				}
			});
			
			$('#SST_PROCESSING_FEE-'+listTempStorageVO[j].sstId).on('blur', function() {
				
				var setupTempStorage = "SST_PROCESSING_FEE-"+this.value + "-" + this.id.split("-")[1];
				var params = "setupTempStorage="+setupTempStorage;
				if (!validationsAttributes.isNumber(this.value)){
					$('#alert-errors').modal('show');
					$('#alert-errors-content').html("Only numbers are allowed");
					this.focus();
				}else{
					ajaxInvokeMethods.postAjaxCallback("setupTempStorageUpdateAttributes.action", params, null);
				}
			});

			$('#CUR_ID-'+listTempStorageVO[j].sstId).on('change', function() {
				var setupTempStorage = "CUR_ID-"+this.value + "-" + this.id.split("-")[1];
				var params = "setupTempStorage="+setupTempStorage;

				ajaxInvokeMethods.postAjaxCallback("setupTempStorageUpdateAttributes.action", params, callBackUpdateSstProcessinfFee);
				
			});
		}
		
		
		$('.body_page').css('height', 'auto');
		$('.body_page').height($( document ).height() - 172);
	}
	
	function callBackUpdateSstProcessinfFee(data){
		var valor = data.valor;
		$('#SST_PROCESSING_FEE-'+valor.split("-")[1]).val(valor.split("-")[0]);
	}
	
	function callbackGetRangeTempStorage(data){
		
//		var statusMessageError = data.statusMessageError;
		var rangeGenericVO    = data.rangeGenericVO;
		
		$('#temp-storage-range-from').val(rangeGenericVO.from);
		$('#temp-storage-range-up').val(rangeGenericVO.uo);
		$('#temp-storage-range-rate').val(rangeGenericVO.rate);
		
		
		
	}
	
	function callbackInitCustodySetup(data){
		
		var statusMessageError = data.statusMessageError;
		var custodyVO          = data.custodyVO;
		var listCurrency       = data.listCurrency;
		
		
		
		$( "#currency-custody" ).empty();
		for (var j=0; j<listCurrency.length; j++){
			var optionRow = "<option value='"+listCurrency[j].idGeneralCombo+"'>"+listCurrency[j].nameGeneralCombo+"</option>";
			$("#currency-custody").append(optionRow);
		}
		
		
		
		$('#base-line-custody').val(custodyVO.baseLineCustody);
		$('#fee-cost-custody').val(custodyVO.feeCostCustody);
		$('#currency-custody').val(custodyVO.idCurrencyCustody);

		
		
		
		//Generando eventos blur
		$('#base-line-custody').unbind('blur');
		$('#base-line-custody').on('blur', function() {
			
			if (!validationsAttributes.isNumber(this.value)){
				$('#alert-errors').modal('show');
				$('#alert-errors-content').html("Only numbers are allowed");
			}
			else{
				var params = "custodyVO.baseLineCustody="+this.value;
				ajaxInvokeMethods.postAjaxCallback("updateBaseLineCustody.action", params, null);
			}
		});
		
		
		$('#fee-cost-custody').unbind('blur');
		$('#fee-cost-custody').on('blur', function() { 
			
			if (!validationsAttributes.isNumber(this.value)){
				$('#alert-errors').modal('show');
				$('#alert-errors-content').html("Only numbers are allowed");
			}
			else{
				var params = "custodyVO.feeCostCustody="+this.value+"&custodyVO.idCurrencyCustody="+$('#currency-custody').val();
				ajaxInvokeMethods.postAjaxCallback("updateFeeCostCustody.action", params, null);
			}
		});
		
		
		$('#currency-custody').unbind('change');
		$('#currency-custody').on('change', function() { 
			
			var params = "custodyVO.feeCostCustody="+$('#fee-cost-custody').val()+"&custodyVO.idCurrencyCustody="+this.value;
			ajaxInvokeMethods.postAjaxCallback("updateFeeCostCustody.action", params, null);
			
		});
		
		
		$('.body_page').css('height', 'auto');
		$('.body_page').height($( document ).height() - 172);
	}
	
	
	
	
	//Local Transport
	function callbackInitSetupLocalTransport(data){
		var listLocalTransportVO = data.listLocalTransportVO;
		var listCurrencyVO = data.listCurrency;
		var listLocalTransportRange = data.listLocalTransportRange;
		
		var optionCurID = "";
		
		$( "#local-transport-range" ).empty();
		for (var j=0; j<listLocalTransportRange.length; j++){
			var optionRow = "<option value='"+listLocalTransportRange[j].idGeneralCombo+"'>"+listLocalTransportRange[j].nameGeneralCombo+"</option>";
			$("#local-transport-range").append(optionRow);
		}
		
		
		$('#local-transport-range').unbind('change');
		$('#local-transport-range').on('change', function() {
			var params = "rangeGenericVO.id=" + this.value;

			ajaxInvokeMethods.postAjaxCallback("setupGetLocalTransportRange.action", params, callbackGetRangeLocalTransport);
			
		});
	
		$('#local-transport-range').trigger("change");
		
//		var params = "rangeGenericVO.id="+this.value;
		
		$('#local-transport-range-from').unbind('blur');
		$('#local-transport-range-up').unbind('blur');
		$('#local-transport-amount').unbind('blur');
		$('#local-transport-amount').unbind('change');
		
		
		$('#local-transport-currency').on('change', function() {
			var locaTransportStorage = "SLR_RANGE_FROM-"+this.value + "-" +  $('#local-transport-range').val()+"-"+$('#local-transport-currency').val();
			
			var params = "locaTransportStorage="+locaTransportStorage;
			ajaxInvokeMethods.postAjaxCallback("setupLocalTransportRangeUpdateAttributes.action", params, null);
		});
		
		
		
		$('#local-transport-range-from').on('blur', function() {
			var locaTransportStorage = "SLR_RANGE_FROM-"+this.value + "-" +  $('#local-transport-range').val()+"-"+$('#local-transport-currency').val();
			
			var params = "locaTransportStorage="+locaTransportStorage;
			if (!validationsAttributes.isNumber(this.value)){
				$('#alert-errors').modal('show');
				$('#alert-errors-content').html("Only numbers are allowed");
				this.focus();
			}else{
				ajaxInvokeMethods.postAjaxCallback("setupLocalTransportRangeUpdateAttributes.action", params, null);
				$('#nav-setup a[href="#nav-setup-local-transport"]').trigger("click");
			}
		});
		
		$('#local-transport-range-up').on('blur', function() {
			
			var locaTransportStorage = "SLR_RANGE_UP-"+this.value + "-" + $('#local-transport-range').val()+"-"+$('#local-transport-currency').val();
			var params = "locaTransportStorage="+locaTransportStorage;
			if (!validationsAttributes.isNumber(this.value)){
				$('#alert-errors').modal('show');
				$('#alert-errors-content').html("Only numbers are allowed");
				this.focus();
			}else{
				ajaxInvokeMethods.postAjaxCallback("setupLocalTransportRangeUpdateAttributes.action", params, null);
				$('#nav-setup a[href="#nav-setup-local-transport"]').trigger("click");
			}
		});
		
		$('#local-transport-amount').on('blur', function() {
			
			var locaTransportStorage = "SLR_RANGE_RATE-"+this.value + "-" +  $('#local-transport-range').val()+"-"+$('#local-transport-currency').val();
			var params = "locaTransportStorage="+locaTransportStorage;
			if (!validationsAttributes.isNumber(this.value)){
				$('#alert-errors').modal('show');
				$('#alert-errors-content').html("Only numbers are allowed");
				this.focus();
			}else{
				ajaxInvokeMethods.postAjaxCallback("setupLocalTransportRangeUpdateAttributes.action", params, null);
				$('#nav-setup a[href="#nav-setup-local-transport"]').trigger("click");
			}
		});


		
		$("#table-local-transport-storage-tbody").empty();

		$( "#local-transport-currency" ).empty();
		for (var i=0; i < listCurrencyVO.length; i++){
			optionCurID = optionCurID.concat("<option value='"+listCurrencyVO[i].idGeneralCombo+"'>"+listCurrencyVO[i].nameGeneralCombo+"</option>");
		}
		$("#local-transport-currency").append(optionCurID);
		

		for (var j=0; j<listLocalTransportVO.length; j++){

			var row = "";
			row = row.concat("<tr>");
//			row = row.concat("<td>"+listLocalTransportVO[j].entry+"</td>");
			row = row.concat("<td><input type='text' id='SLT_ENTRY-"+listLocalTransportVO[j].sltId+"' class='input-block-level' value='12,00' style='text-align: right;' ></td>");
			row = row.concat("<td><input type='text' id='SLT_PERCENT_COST-"+listLocalTransportVO[j].sltId+"' class='input-block-level' value='12,00' style='text-align: right;' ></td>");
			row = row.concat("<td><input type='text' id='SLT_PROCESSING_FEE-"+listLocalTransportVO[j].sltId+"' class='input-block-level' value='50,00' style='text-align: right;'></td>");
			row = row.concat("<td>");
			row = row.concat("	<select id='CUR_ID-"+listLocalTransportVO[j].sltId+"'>" + optionCurID + "	</select>");
			row = row.concat("</td>");
			row = row.concat("</tr>");
			
			$("#table-local-transport-storage-tbody").append(row);
			
			$("#SLT_ENTRY-"+listLocalTransportVO[j].sltId).val(listLocalTransportVO[j].entry);
			$("#SLT_PERCENT_COST-"+listLocalTransportVO[j].sltId).val(listLocalTransportVO[j].percentCost);
			$("#SLT_PROCESSING_FEE-"+listLocalTransportVO[j].sltId).val(listLocalTransportVO[j].processingFee);			
			$("#CUR_ID-"+listLocalTransportVO[j].sltId).val(listLocalTransportVO[j].currencyID);			
			
			
			$('#SLT_ENTRY-'+listLocalTransportVO[j].sltId).on('blur', function() {
				
				var locaTransportStorage = "SLT_ENTRY-"+this.value + "-" + this.id.split("-")[1];
				var params = "locaTransportStorage="+locaTransportStorage;
				ajaxInvokeMethods.postAjaxCallback("setupLocalTransportUpdateAttributes.action", params, null);
			});
			
			$('#SLT_PERCENT_COST-'+listLocalTransportVO[j].sltId).on('blur', function() {
				
				var locaTransportStorage = "SLT_PERCENT_COST-"+this.value + "-" + this.id.split("-")[1];
				var params = "locaTransportStorage="+locaTransportStorage;
				if (!validationsAttributes.isNumber(this.value)){
					$('#alert-errors').modal('show');
					$('#alert-errors-content').html("Only numbers are allowed");
					this.focus();
				}else{
					ajaxInvokeMethods.postAjaxCallback("setupLocalTransportUpdateAttributes.action", params, null);
				}
			});
			
			$('#SLT_PROCESSING_FEE-'+listLocalTransportVO[j].sltId).on('blur', function() {
				
				var locaTransportStorage = "SLT_PROCESSING_FEE-"+this.value + "-" + this.id.split("-")[1];
				var params = "locaTransportStorage="+locaTransportStorage;
				if (!validationsAttributes.isNumber(this.value)){
					$('#alert-errors').modal('show');
					$('#alert-errors-content').html("Only numbers are allowed");
					this.focus();
				}else{
					ajaxInvokeMethods.postAjaxCallback("setupLocalTransportUpdateAttributes.action", params, null);
				}
			});

			$('#CUR_ID-'+listLocalTransportVO[j].sltId).on('change', function() {
				
				var locaTransportStorage = "CUR_ID-"+this.value + "-" + this.id.split("-")[1];
				var params = "locaTransportStorage="+locaTransportStorage;

				ajaxInvokeMethods.postAjaxCallback("setupLocalTransportUpdateAttributes.action", params, callBackUpdateSltProcessinfFee);
				
			});
		}
		
		
		$('.body_page').css('height', 'auto');
		$('.body_page').height($( document ).height() - 172);
		
	}
	
	function callBackUpdateSltProcessinfFee(data){
		var valor = data.valor;
		$('#SLT_PROCESSING_FEE-'+valor.split("-")[1]).val(valor.split("-")[0]);
	}
	
	function callbackGetRangeLocalTransport(data){
		
//		var statusMessageError = data.statusMessageError;
		var rangeGenericVO    = data.rangeGenericVO;
		
		$('#local-transport-range-from').val(rangeGenericVO.from);
		$('#local-transport-range-up').val(rangeGenericVO.up);
		$('#local-transport-amount').val(rangeGenericVO.rate);
		
		$('#local-transport-currency').val(rangeGenericVO.idCurrency);
	}
	
	function callbacklistRangeFreight(data){
		
		var listFreighAirRangeVO = data.listFreighAirRangeVO;
		
		$("#table-freight-air-route-tbody").empty();
		var rowRoute = "";
		for (var j=0; j<listFreighAirRangeVO.length; j++){
			
			rowRoute = "<tr>";
			
			rowRoute = rowRoute.concat('<td><input type="text" class="input-block-level" id="rangeAirFrom-'+listFreighAirRangeVO[j].idRangeFreighAir+'"  value="'+listFreighAirRangeVO[j].fromFreighAir+'"></td>');
			rowRoute = rowRoute.concat('<td><input type="text" class="input-block-level" id="rangeAirTo-'+listFreighAirRangeVO[j].idRangeFreighAir+'"    value="'+listFreighAirRangeVO[j].toFreighAir+'"></td>');
			rowRoute = rowRoute.concat('<td><input type="text" class="input-block-level" id="rangeAirRange-'+listFreighAirRangeVO[j].idRangeFreighAir+'" value="'+listFreighAirRangeVO[j].rateFreighAir+'"></td>');
			
			rowRoute = rowRoute.concat("<td>" +
					"<a class='btn_table' href='#' title='Replicate Concept' id='replicateRangeFreightAir-"+listFreighAirRangeVO[j].idRangeFreighAir+"-"+listFreighAirRangeVO[j].idFreighAir+"'><i class='icon-retweet'></i></a>" +
					"<a class='btn_table' href='#' title='Delete Concept' id='deleteRangeFreightAir-"+listFreighAirRangeVO[j].idRangeFreighAir+"-"+listFreighAirRangeVO[j].idFreighAir+"'><i class='icon-trash'></i></a>" +
					"</td>");
			
			rowRoute =rowRoute.concat("</tr>");
			
			$("#table-freight-air-route-tbody").append(rowRoute);
			
			
			
			//Eventos para los objetos del rango del flete
			
			$('#replicateRangeFreightAir-'+listFreighAirRangeVO[j].idRangeFreighAir+"-"+listFreighAirRangeVO[j].idFreighAir).on('click', function() {
				var params = "idRangeFreighAir="+this.id.split("-") [1]+"&idFreightAir="+this.id.split("-") [2];
				ajaxInvokeMethods.postAjaxCallback("replicateRangeFraightAir.action", params, function(data){
					
					//refrescando la lista
					var params = "idFreightAir="+data.idFreightAir;
					ajaxInvokeMethods.postAjaxCallback("listRangeFreight.action", params, callbacklistRangeFreight);
					
				});
			});
			
			
			$('#deleteRangeFreightAir-'+listFreighAirRangeVO[j].idRangeFreighAir+"-"+listFreighAirRangeVO[j].idFreighAir).on('click', function() {
				var params = "idRangeFreighAir="+this.id.split("-") [1]+"&idFreightAir="+this.id.split("-") [2];
				ajaxInvokeMethods.postAjaxCallback("deleteRangeFraightAir.action", params, function(data){
					
					//refrescando la lista
					var params = "idFreightAir="+data.idFreightAir;
					ajaxInvokeMethods.postAjaxCallback("listRangeFreight.action", params, callbacklistRangeFreight);
					
				});
			});
			
			
			
			$('#rangeAirFrom-'+listFreighAirRangeVO[j].idRangeFreighAir).on('blur', function() {

				var freightFlyAttributes = "FRR_FROM-" + this.value + "-" + this.id.split("-") [1];
				var params = "freightFlyAttributes="+freightFlyAttributes;

				if (!validationsAttributes.isNumber(this.value)){
					$('#alert-errors').modal('show');
					$('#alert-errors-content').html("Only numbers are allowed");
					this.focus();
				}else{
					ajaxInvokeMethods.postAjaxCallback("freightFlyRangeUpdateAttributes.action", params, null);
				}
			});

			$('#rangeAirTo-'+listFreighAirRangeVO[j].idRangeFreighAir).on('blur', function() {
				var freightFlyAttributes = "FRR_TO-" + this.value + "-" + this.id.split("-") [1];
				var params = "freightFlyAttributes="+freightFlyAttributes;

				if (!validationsAttributes.isNumber(this.value)){
					$('#alert-errors').modal('show');
					$('#alert-errors-content').html("Only numbers are allowed");
					this.focus();
				}else{
					ajaxInvokeMethods.postAjaxCallback("freightFlyRangeUpdateAttributes.action", params, null);
				}
			});

			$('#rangeAirRange-'+listFreighAirRangeVO[j].idRangeFreighAir).on('blur', function() {
				var freightFlyAttributes = "FRR_RATE-" + this.value + "-" + this.id.split("-") [1];
				var params = "freightFlyAttributes="+freightFlyAttributes;

				if (!validationsAttributes.isNumber(this.value)){
					$('#alert-errors').modal('show');
					$('#alert-errors-content').html("Only numbers are allowed");
					this.focus();
				}else{
					ajaxInvokeMethods.postAjaxCallback("freightFlyRangeUpdateAttributes.action", params, null);
				}
			});
			
		}
		
	}
	
	function callbackListConceptOtherFreight(data){
		
		
		var listConceptCostOtherFreightVO = data.listConceptCostOtherFreightVO;
		
		$("#table-other-freight-concepts-tbody").empty();
		var rowRoute = "";
		for (var j=0; j<listConceptCostOtherFreightVO.length; j++){
			
			rowRoute = "<tr>";
			
			rowRoute = rowRoute.concat('<td>'+listConceptCostOtherFreightVO[j].idConceptOtherFreight+'</td>');
			rowRoute = rowRoute.concat('<td><input type="text" class="input-block-level" id="OCP_NAME_CONCEPT-'+listConceptCostOtherFreightVO[j].idConceptOtherFreight+'"    value="'+listConceptCostOtherFreightVO[j].nameConceptOtherFreight+'"></td>');
			rowRoute = rowRoute.concat('<td><input type="text" class="input-block-level" id="OCP_RATE_CONCEPT-'+listConceptCostOtherFreightVO[j].idConceptOtherFreight+'" value="'+listConceptCostOtherFreightVO[j].amountConceptOtherFreight+'"></td>');
			
			rowRoute = rowRoute.concat("<td>" +
					"<a class='btn_table' href='#' title='Replicate Concept' id='replicateConceptOtherFreight-"+listConceptCostOtherFreightVO[j].idConceptOtherFreight+"-"+listConceptCostOtherFreightVO[j].idOtherFreight+"'><i class='icon-retweet'></i></a>" +
					"<a class='btn_table' href='#' title='Delete Concept' id='deleteConceptOtherFreight-"+listConceptCostOtherFreightVO[j].idConceptOtherFreight+"-"+listConceptCostOtherFreightVO[j].idOtherFreight+"'><i class='icon-trash'></i></a>" +
					"</td>");
			
			rowRoute =rowRoute.concat("</tr>");
			
			$("#table-other-freight-concepts-tbody").append(rowRoute);
			
			
			
			$('#OCP_NAME_CONCEPT-'+listConceptCostOtherFreightVO[j].idConceptOtherFreight).on('blur', function() {

				var conceptOtherFreight = this.id.split("-") [0] + "-" + this.value + "-" + this.id.split("-") [1];
				var params = "conceptOtherFreight="+conceptOtherFreight;

				ajaxInvokeMethods.postAjaxCallback("updateConceptOtherFreight.action", params, null);

			});
			
			
			$('#OCP_RATE_CONCEPT-'+listConceptCostOtherFreightVO[j].idConceptOtherFreight).on('blur', function() {

				var conceptOtherFreight = this.id.split("-") [0] + "-" + this.value + "-" + this.id.split("-") [1];
				var params = "conceptOtherFreight="+conceptOtherFreight;

				ajaxInvokeMethods.postAjaxCallback("updateConceptOtherFreight.action", params, null);
			});
			
			
			
			$('#replicateConceptOtherFreight-'+listConceptCostOtherFreightVO[j].idConceptOtherFreight+"-"+listConceptCostOtherFreightVO[j].idOtherFreight).on('click', function() {
				var params = "idConceptOtherFreight="+this.id.split("-") [1]+"&idOtherFreight="+this.id.split("-") [2];
				ajaxInvokeMethods.postAjaxCallback("replicateConceptOtherFreight.action", params, function(data){
					
					var params = "idOtherFreight="+data.idOtherFreight;
					ajaxInvokeMethods.postAjaxCallback("listConceptOtherFreight.action", params, callbackListConceptOtherFreight);
					
				});
			});
			
			
			$('#deleteConceptOtherFreight-'+listConceptCostOtherFreightVO[j].idConceptOtherFreight+"-"+listConceptCostOtherFreightVO[j].idOtherFreight).on('click', function() {
				var params = "idConceptOtherFreight="+this.id.split("-") [1]+"&idOtherFreight="+this.id.split("-") [2];
				ajaxInvokeMethods.postAjaxCallback("deleteConceptOtherFreight.action", params, function(data){
					
					var params = "idOtherFreight="+data.idOtherFreight;
					ajaxInvokeMethods.postAjaxCallback("listConceptOtherFreight.action", params, callbackListConceptOtherFreight);
					
				});
			});
		}
		
		
		$('#modal-other-freight-concepts').modal('show'); 
	}
	
	
	
	
	function callbackImportUnits(data){
		$("#tab-move-cost a[href='#tab-import-units']").trigger("click");	
	}

	
	filterUno = function(obj){
	
	   var params    = obj.getAttribute('value');
	   ajaxInvokeMethods.postAjaxCallback("filterUno.action", params, callbackImportUnitClick);
	};

	replicar = function(obj){
		var params    = obj.getAttribute('value');
		ajaxInvokeMethods.postAjaxCallback("replicar.action", params, callbackImportUnitClick);
	};

	eliminar = function(obj){
		var params    = obj.getAttribute('value');
		ajaxInvokeMethods.postAjaxCallback("remove.action", params, callbackImportUnitClick);
		
	};
	
	

	
	
	
	
	
	function callBackListAPOFreights(data){

		var listActivePackOutFreightVO = data.listActivePackOutFreightVO;
		var listCurrencyFreight        = data.listCurrencyFreight;
		
		
		var optionCurID = "";
		for (var i=0; i < listCurrencyFreight.length; i++){
			optionCurID = optionCurID.concat("<option value='"+listCurrencyFreight[i].idGeneralCombo+"'>"+listCurrencyFreight[i].nameGeneralCombo+"</option>");
		}
		
		
		$("#table-freight-APO-body").empty();
		for (var j=0; j<listActivePackOutFreightVO.length; j++){
			
			var row = "";
			row = row.concat("<tr>");
//			row = row.concat("<td>"+listActivePackOutFreightVO[j].idActivePackOut+"</td>");
			
			row = row.concat("<td>");
			row = row.concat("	<select id='APO_CURRENCY-"+listActivePackOutFreightVO[j].idActivePackOut+"'>" + optionCurID + "	</select>");
			row = row.concat("</td>");
			
			
			row = row.concat("<td><input type='text' id='APO_NAME_FREIGHT-"+listActivePackOutFreightVO[j].idActivePackOut+"' class='input-block-level' style='width:200px;'  ></td>");
			row = row.concat("<td><input type='text' id='APO_INCREASE_FREIGHT-"+listActivePackOutFreightVO[j].idActivePackOut+"' class='input-block-level' style='text-align: right;' ></td>");
			row = row.concat("<td><input type='text' id='APO_AWB_RATE_FREIGHT-"+listActivePackOutFreightVO[j].idActivePackOut+"' class='input-block-level' style='text-align: right;' ></td>");
			row = row.concat("<td><input type='text' id='INC_APO_AWB_RATE_FREIGHT-"+listActivePackOutFreightVO[j].idActivePackOut+"' class='input-block-level' style='text-align: right;' ></td>");
			row = row.concat("<td><input type='text' id='APO_OTHER_FEE_FREIGHT-"+listActivePackOutFreightVO[j].idActivePackOut+"' class='input-block-level' style='text-align: right;' ></td>");
			row = row.concat("<td><input type='text' id='INC_APO_OTHER_FEE_FREIGHT-"+listActivePackOutFreightVO[j].idActivePackOut+"' class='input-block-level' style='text-align: right;' ></td>");
			row = row.concat("<td><input type='text' id='APO_FSC_FREIGHT-"+listActivePackOutFreightVO[j].idActivePackOut+"' class='input-block-level' style='text-align: right;' ></td>");
			row = row.concat("<td><input type='text' id='INC_APO_FSC_FREIGHT-"+listActivePackOutFreightVO[j].idActivePackOut+"' class='input-block-level' style='text-align: right;' ></td>");
			row = row.concat("<td><input type='text' id='APO_SSC_FREIGHT-"+listActivePackOutFreightVO[j].idActivePackOut+"' class='input-block-level' style='text-align: right;' ></td>");
			row = row.concat("<td><input type='text' id='INC_APO_SSC_FREIGHT-"+listActivePackOutFreightVO[j].idActivePackOut+"' class='input-block-level' style='text-align: right;' ></td>");
			row = row.concat("<td><input type='text' id='APO_ORDERS_FREIGHT-"+listActivePackOutFreightVO[j].idActivePackOut+"' class='input-block-level' style='text-align: right;' ></td>");
			row = row.concat("<td><input type='text' id='APO_PALETTES_FREIGHT-"+listActivePackOutFreightVO[j].idActivePackOut+"' class='input-block-level' style='text-align: right;' ></td>");

//			row = row.concat("<td>" +
//					"<a class='btn_table' href='#' title='Replicate Concept' id='replicateRangeFreightAir-"+listActivePackOutFreightVO[j].idActivePackOut+"'><i class='icon-retweet'></i></a>" +
//					"<a class='btn_table' href='#' title='Delete Concept' id='deleteRangeFreightAir-"+listActivePackOutFreightVO[j].idActivePackOut+"'><i class='icon-trash'></i></a>" +
//					"<a class='btn_table' href='#' title='Delete Concept' id='listConceptAPOFreight-"+listActivePackOutFreightVO[j].idActivePackOut+"'><i class='icon-plus'></i></a>" +
//					"</td>");
			
			
			row = row.concat("<td><table class=''>");
			row = row.concat("<td style='padding:0px; border:0px;'><a class='btn_table' href='#' title='Replicate Concept' id='replicateAPOFreight-"+listActivePackOutFreightVO[j].idActivePackOut+"'><i class='icon-retweet'></i></a></td>");
			row = row.concat("<td style='padding:0px; border:0px;'><a class='btn_table' href='#' title='Delete Concept' id='deleteAPOFreight-"+listActivePackOutFreightVO[j].idActivePackOut+"'><i class='icon-trash'></i></a></td>");
			row = row.concat("<td style='padding:0px; border:0px;'><a class='btn_table' href='#' title='Edit Concept' id='listConceptAPOFreight-"+listActivePackOutFreightVO[j].idActivePackOut+"'><i class='icon-plus'></i></a></td>");
			row = row.concat("</tr></table></td>");
			
			row = row.concat("</tr>");
			
			$("#table-freight-APO-body").append(row);
			
			
			//Seteando valores obtenidos desde base de datos en lista de objetos
			$("#APO_CURRENCY-"+listActivePackOutFreightVO[j].idActivePackOut).val(listActivePackOutFreightVO[j].idCurrencyActivePackOut);
			$("#APO_NAME_FREIGHT-"+listActivePackOutFreightVO[j].idActivePackOut).val(listActivePackOutFreightVO[j].nameActivePackOut);
			$("#APO_INCREASE_FREIGHT-"+listActivePackOutFreightVO[j].idActivePackOut).val(listActivePackOutFreightVO[j].increaseActivePackOut);
			$("#APO_AWB_RATE_FREIGHT-"+listActivePackOutFreightVO[j].idActivePackOut).val(listActivePackOutFreightVO[j].awbRateActivePackOut);
			$("#APO_OTHER_FEE_FREIGHT-"+listActivePackOutFreightVO[j].idActivePackOut).val(listActivePackOutFreightVO[j].otherFeeActivePackOut);
			$("#APO_FSC_FREIGHT-"+listActivePackOutFreightVO[j].idActivePackOut).val(listActivePackOutFreightVO[j].fuelSurchargeActivePackOut);
			$("#APO_SSC_FREIGHT-"+listActivePackOutFreightVO[j].idActivePackOut).val(listActivePackOutFreightVO[j].securityChargeActivePackOut);
			$("#APO_ORDERS_FREIGHT-"+listActivePackOutFreightVO[j].idActivePackOut).val(listActivePackOutFreightVO[j].ordersActivePackOut);
			$("#APO_PALETTES_FREIGHT-"+listActivePackOutFreightVO[j].idActivePackOut).val(listActivePackOutFreightVO[j].palettesActivePackOut);
			
			$("#INC_APO_AWB_RATE_FREIGHT-"+listActivePackOutFreightVO[j].idActivePackOut).val(listActivePackOutFreightVO[j].increaseAWBActivePackOut);
			$("#INC_APO_OTHER_FEE_FREIGHT-"+listActivePackOutFreightVO[j].idActivePackOut).val(listActivePackOutFreightVO[j].increaseFFWActivePackOut);
			$("#INC_APO_FSC_FREIGHT-"+listActivePackOutFreightVO[j].idActivePackOut).val(listActivePackOutFreightVO[j].increaseFSCActivePackOut);
			$("#INC_APO_SSC_FREIGHT-"+listActivePackOutFreightVO[j].idActivePackOut).val(listActivePackOutFreightVO[j].increaseSCCActivePackOut);
			
			
			
			//Eventos de botones principales de la lista
			$('#deleteAPOFreight-'+listActivePackOutFreightVO[j].idActivePackOut).on('click', function() {
				var params = "idAPOFreight="+this.id.split("-")[1];
				ajaxInvokeMethods.postAjaxCallback("deleteAPOFreight.action", params, function(data){
					var statusMessageError = data.statusMessageError;
					
					if (statusMessageError != ''){
						$('#alert-errors').modal('show');
						$('#alert-errors-content').html(statusMessageError);
						
						return false;
					}
					var params = "";
					ajaxInvokeMethods.postAjaxCallback("listAPOFreights.action", params, callBackListAPOFreights);
				});
			});
			
			
			$('#replicateAPOFreight-'+listActivePackOutFreightVO[j].idActivePackOut).on('click', function() {
				var params = "idAPOFreight="+this.id.split("-")[1];
				ajaxInvokeMethods.postAjaxCallback("replicateAPOFreight.action", params, function(){
					
					var params = "";
					ajaxInvokeMethods.postAjaxCallback("listAPOFreights.action", params, callBackListAPOFreights);
				});
			});
			
			$('#listConceptAPOFreight-'+listActivePackOutFreightVO[j].idActivePackOut).on('click', function() {
				var params = "idAPOFreight="+this.id.split("-")[1];
				ajaxInvokeMethods.postAjaxCallback("listConceptCostApoFreight.action", params, callBackListConceptCostApoFreight);
			});
			
			//eventos de cajas de textos onchange o onblur
			$('#INC_APO_SSC_FREIGHT-'+listActivePackOutFreightVO[j].idActivePackOut).on('blur', function() {
				var freightOthersAttributes = this.id.split("-") [0]+"-" + this.value + "-" + this.id.split("-") [1];
				var params = "freightOthersAttributes="+freightOthersAttributes;
				if (!validationsAttributes.isNumber(this.value)){
					$('#alert-errors').modal('show');
					$('#alert-errors-content').html("Only numbers are allowed");
					this.focus();
				}else{
					ajaxInvokeMethods.postAjaxCallback("freightAPOUpdateAttributes.action", params, null);
				}
			});
			
			$('#INC_APO_FSC_FREIGHT-'+listActivePackOutFreightVO[j].idActivePackOut).on('blur', function() {
				var freightOthersAttributes = this.id.split("-") [0]+"-" + this.value + "-" + this.id.split("-") [1];
				var params = "freightOthersAttributes="+freightOthersAttributes;
				if (!validationsAttributes.isNumber(this.value)){
					$('#alert-errors').modal('show');
					$('#alert-errors-content').html("Only numbers are allowed");
					this.focus();
				}else{
					ajaxInvokeMethods.postAjaxCallback("freightAPOUpdateAttributes.action", params, null);
				}
			});
			
			$('#INC_APO_OTHER_FEE_FREIGHT-'+listActivePackOutFreightVO[j].idActivePackOut).on('blur', function() {
				var freightOthersAttributes = this.id.split("-") [0]+"-" + this.value + "-" + this.id.split("-") [1];
				var params = "freightOthersAttributes="+freightOthersAttributes;
				if (!validationsAttributes.isNumber(this.value)){
					$('#alert-errors').modal('show');
					$('#alert-errors-content').html("Only numbers are allowed");
					this.focus();
				}else{
					ajaxInvokeMethods.postAjaxCallback("freightAPOUpdateAttributes.action", params, null);
				}
			});
			
			$('#INC_APO_AWB_RATE_FREIGHT-'+listActivePackOutFreightVO[j].idActivePackOut).on('blur', function() {
				var freightOthersAttributes = this.id.split("-") [0]+"-" + this.value + "-" + this.id.split("-") [1];
				var params = "freightOthersAttributes="+freightOthersAttributes;
				if (!validationsAttributes.isNumber(this.value)){
					$('#alert-errors').modal('show');
					$('#alert-errors-content').html("Only numbers are allowed");
					this.focus();
				}else{
					ajaxInvokeMethods.postAjaxCallback("freightAPOUpdateAttributes.action", params, null);
				}
			});
			
			$('#APO_PALETTES_FREIGHT-'+listActivePackOutFreightVO[j].idActivePackOut).on('blur', function() {
				var freightOthersAttributes = this.id.split("-") [0]+"-" + this.value + "-" + this.id.split("-") [1];
				var params = "freightOthersAttributes="+freightOthersAttributes;
				if (!validationsAttributes.isNumber(this.value)){
					$('#alert-errors').modal('show');
					$('#alert-errors-content').html("Only numbers are allowed");
					this.focus();
				}else{
					ajaxInvokeMethods.postAjaxCallback("freightAPOUpdateAttributes.action", params, null);
				}
			});
			
			
			$('#APO_ORDERS_FREIGHT-'+listActivePackOutFreightVO[j].idActivePackOut).on('blur', function() {
				var freightOthersAttributes = this.id.split("-") [0]+"-" + this.value + "-" + this.id.split("-") [1];
				var params = "freightOthersAttributes="+freightOthersAttributes;
				if (!validationsAttributes.isNumber(this.value)){
					$('#alert-errors').modal('show');
					$('#alert-errors-content').html("Only numbers are allowed");
					this.focus();
				}else{
					ajaxInvokeMethods.postAjaxCallback("freightAPOUpdateAttributes.action", params, null);
				}
			});
			
			$('#APO_SSC_FREIGHT-'+listActivePackOutFreightVO[j].idActivePackOut).on('blur', function() {
				var freightOthersAttributes = this.id.split("-") [0]+"-" + this.value + "-" + this.id.split("-") [1];
				var params = "freightOthersAttributes="+freightOthersAttributes;
				if (!validationsAttributes.isNumber(this.value)){
					$('#alert-errors').modal('show');
					$('#alert-errors-content').html("Only numbers are allowed");
					this.focus();
				}else{
					ajaxInvokeMethods.postAjaxCallback("freightAPOUpdateAttributes.action", params, null);
				}
			});
			
			$('#APO_FSC_FREIGHT-'+listActivePackOutFreightVO[j].idActivePackOut).on('blur', function() {
				var freightOthersAttributes = this.id.split("-") [0]+"-" + this.value + "-" + this.id.split("-") [1];
				var params = "freightOthersAttributes="+freightOthersAttributes;
				if (!validationsAttributes.isNumber(this.value)){
					$('#alert-errors').modal('show');
					$('#alert-errors-content').html("Only numbers are allowed");
					this.focus();
				}else{
					ajaxInvokeMethods.postAjaxCallback("freightAPOUpdateAttributes.action", params, null);
				}
			});
			
			$('#APO_OTHER_FEE_FREIGHT-'+listActivePackOutFreightVO[j].idActivePackOut).on('blur', function() {
				var freightOthersAttributes = this.id.split("-") [0]+"-" + this.value + "-" + this.id.split("-") [1];
				var params = "freightOthersAttributes="+freightOthersAttributes;
				if (!validationsAttributes.isNumber(this.value)){
					$('#alert-errors').modal('show');
					$('#alert-errors-content').html("Only numbers are allowed");
					this.focus();
				}else{
					ajaxInvokeMethods.postAjaxCallback("freightAPOUpdateAttributes.action", params, null);
				}
			});
			
			$('#APO_AWB_RATE_FREIGHT-'+listActivePackOutFreightVO[j].idActivePackOut).on('blur', function() {
				var freightOthersAttributes = this.id.split("-") [0]+"-" + this.value + "-" + this.id.split("-") [1];
				var params = "freightOthersAttributes="+freightOthersAttributes;
				if (!validationsAttributes.isNumber(this.value)){
					$('#alert-errors').modal('show');
					$('#alert-errors-content').html("Only numbers are allowed");
					this.focus();
				}else{
					ajaxInvokeMethods.postAjaxCallback("freightAPOUpdateAttributes.action", params, null);
				}
			});
			
			
			$('#APO_CURRENCY-'+listActivePackOutFreightVO[j].idActivePackOut).on('change', function() {
				var freightOthersAttributes = this.id.split("-") [0]+"-" + this.value + "-" + this.id.split("-") [1];
				var params = "freightOthersAttributes="+freightOthersAttributes;
				ajaxInvokeMethods.postAjaxCallback("freightAPOUpdateAttributes.action", params, null);
			});
			
			$('#APO_NAME_FREIGHT-'+listActivePackOutFreightVO[j].idActivePackOut).on('blur', function() {
				var freightOthersAttributes = this.id.split("-") [0]+"-" + this.value + "-" + this.id.split("-") [1];
				var params = "freightOthersAttributes="+freightOthersAttributes;
				ajaxInvokeMethods.postAjaxCallback("freightAPOUpdateAttributes.action", params, null);
			});
			
			$('#APO_INCREASE_FREIGHT-'+listActivePackOutFreightVO[j].idActivePackOut).on('blur', function() {
				var freightOthersAttributes = this.id.split("-") [0]+"-" + this.value + "-" + this.id.split("-") [1];
				var params = "freightOthersAttributes="+freightOthersAttributes;
				if (!validationsAttributes.isNumber(this.value)){
					$('#alert-errors').modal('show');
					$('#alert-errors-content').html("Only numbers are allowed");
					this.focus();
				}else{
					ajaxInvokeMethods.postAjaxCallback("freightAPOUpdateAttributes.action", params, null);
				}
			});
					
		}
		
		$('.body_page').css('height', 'auto');
		$('.body_page').height($( document ).height() - 172);
		$('.t-f-h-freight-APO').fixedHeader();
		
		
	}
	
	
	
	
	
	
	function callBackListConceptCostApoFreight(data){
		
		var listConceptCostAPOFreightVO = data.listConceptCostAPOFreightVO;
		
		
		$("#APO-freight-concepts-tbody").empty();
		for (var j=0; j<listConceptCostAPOFreightVO.length; j++){
			
			var row = "";
			row = row.concat("<tr>");
			
			row = row.concat('<td>'+listConceptCostAPOFreightVO[j].idConceptAPOFreight+'</td>');
			row = row.concat('<td><input type="text" id="APO_RATE_NAME_CONCEPT-'+listConceptCostAPOFreightVO[j].idConceptAPOFreight+'" class="input-block-level" value="'+listConceptCostAPOFreightVO[j].nameConceptAPOFreight+'"></td>');
			row = row.concat('<td><input type="text" id="APO_RATE_VALUE_CONCEPT-'+listConceptCostAPOFreightVO[j].idConceptAPOFreight+'" class="input-block-level" value="'+listConceptCostAPOFreightVO[j].amountConceptAPOFreight+'" style="text-align: right;padding-right: 2px;"></td>');
			
			row = row.concat('<td>');
				row = row.concat("<a class='btn_table' href='#' title='Replicate Operative Activity' id='replicateAPOFreightRate-"+listConceptCostAPOFreightVO[j].idConceptAPOFreight+"-"+listConceptCostAPOFreightVO[j].idAPOFreight+"'><i class='icon-retweet'></i></a>");
				row = row.concat("<a class='btn_table' href='#' title='Replicate Operative Activity' id='deleteAPOFreightRate-"+listConceptCostAPOFreightVO[j].idConceptAPOFreight+"-"+listConceptCostAPOFreightVO[j].idAPOFreight+"'><i class='icon-trash'></i></a>");
			row = row.concat("</td>");
			
			row = row.concat("</tr>");
			
			
		
			$("#APO-freight-concepts-tbody").append(row);
			
			
//			public static final String APO_RATE_NAME_CONCEPT			= "APO_RATE_NAME_CONCEPT";
//			public static final String APO_RATE_VALUE_CONCEPT			= "APO_RATE_VALUE_CONCEPT";
			
			
			
			//Eventos para registro de actualizaciones en base de datos
			$('#APO_RATE_NAME_CONCEPT-'+listConceptCostAPOFreightVO[j].idConceptAPOFreight).on('blur', function() {

				var conceptOtherFreight = this.id.split("-") [0] + "-" + this.value + "-" + this.id.split("-") [1];
				var params = "conceptOtherFreight="+conceptOtherFreight;

				ajaxInvokeMethods.postAjaxCallback("updateConceptAPOFreight.action", params, null);

			});
			
			$('#APO_RATE_VALUE_CONCEPT-'+listConceptCostAPOFreightVO[j].idConceptAPOFreight).on('blur', function() {

				var conceptOtherFreight = this.id.split("-") [0] + "-" + this.value + "-" + this.id.split("-") [1];
				var params = "conceptOtherFreight="+conceptOtherFreight;

				ajaxInvokeMethods.postAjaxCallback("updateConceptAPOFreight.action", params, null);

			});
			
			
			
			//Eventos de negocio para para lista
			$('#replicateAPOFreightRate-'+listConceptCostAPOFreightVO[j].idConceptAPOFreight+"-"+listConceptCostAPOFreightVO[j].idAPOFreight).on('click', function() {
				var params = "idRateAPOFreight="+this.id.split("-") [1]+"&idAPOFreight="+this.id.split("-") [2];
				ajaxInvokeMethods.postAjaxCallback("replicateAPOFreightRate.action", params, function(data){
					
					//Se vuelve a llamar a m�todo para recargar el resultado(lista)
					var params = "idAPOFreight="+data.idAPOFreight;
					ajaxInvokeMethods.postAjaxCallback("listConceptCostApoFreight.action", params, callBackListConceptCostApoFreight);
					
				});
			});
			
			$('#deleteAPOFreightRate-'+listConceptCostAPOFreightVO[j].idConceptAPOFreight+"-"+listConceptCostAPOFreightVO[j].idAPOFreight).on('click', function() {
				var params = "idRateAPOFreight="+this.id.split("-") [1]+"&idAPOFreight="+this.id.split("-") [2];
				ajaxInvokeMethods.postAjaxCallback("deleteAPOFreightRate.action", params, function(data){
					
					//Se vuelve a llamar a m�todo para recargar el resultado(lista)
					var params = "idAPOFreight="+data.idAPOFreight;
					ajaxInvokeMethods.postAjaxCallback("listConceptCostApoFreight.action", params, callBackListConceptCostApoFreight);
					
				});
			});
			
		}
		
		$('#modal-APO-freight-concepts').modal('show');
	}
	
	
	
	function callBackListConceptCostWarehouse(data){
		
		var listTempStorageVO = data.listTempStorageVO;
		var listCurrency      = data.listCurrency;
		
		
		var optionCurID = "";
		for (var i=0; i < listCurrency.length; i++){
			optionCurID = optionCurID.concat("<option value='"+listCurrency[i].idGeneralCombo+"'>"+listCurrency[i].nameGeneralCombo+"</option>");
		}
		
		$("#concept-cost-warehouse").empty();
		for (var j=0; j<listTempStorageVO.length; j++){

			//LG
			var row = "";
			row = row.concat("<tr>");
			row = row.concat("<td>"+listTempStorageVO[j].sstId+"</td>");
			row = row.concat("<td><input type='text' id='SST_ENTRY-"+listTempStorageVO[j].sstId+"' class='input-block-level' value='12,00' style='text-align: right;' ></td>");
			row = row.concat("<td><input type='text' id='SST_PROCESSING_FEE-"+listTempStorageVO[j].sstId+"' class='input-block-level' value='50,00' style='text-align: right;'></td>");
			row = row.concat("<td>");
			row = row.concat("	<select id='CUR_ID-"+listTempStorageVO[j].sstId+"'>" + optionCurID + "	</select>");
			row = row.concat("</td>");
			
			
			row = row.concat("<td>" +
					"<a class='btn_table' href='#' title='Replicate Concept' id='replicateConceptCostWarehouse-"+listTempStorageVO[j].sstId+"'><i class='icon-retweet'></i></a>" +
					"<a class='btn_table' href='#' title='Delete Concept' id='deleteConceptCostWarehouse-"+listTempStorageVO[j].sstId+"'><i class='icon-trash'></i></a>" +
					"</td>");
			row = row.concat("</tr>");
			
			$("#concept-cost-warehouse").append(row);
			
			
			//Setting value list
			$("#SST_ENTRY-"+listTempStorageVO[j].sstId).val(listTempStorageVO[j].entry);
			$("#SST_PROCESSING_FEE-"+listTempStorageVO[j].sstId).val(listTempStorageVO[j].processingFee);
			$("#CUR_ID-"+listTempStorageVO[j].sstId).val(listTempStorageVO[j].currencyID);
			
			
			
			//Eventos de negocio para para lista
			$('#replicateConceptCostWarehouse-'+listTempStorageVO[j].sstId).on('click', function() {
				var params = "idConceptCostWarehouse="+this.id.split("-") [1];
				ajaxInvokeMethods.postAjaxCallback("replicateConceptCostWarehouse.action", params, function(data){
					
					//Se vuelve a llamar a m�todo para recargar el resultado(lista)
					var selIdSites       = $('#selIdSites').val();
					var params = "stageVO.idSiteStage="+selIdSites;
					ajaxInvokeMethods.postAjaxCallback("listConceptCostWarehouse.action", params, callBackListConceptCostWarehouse);
				});
			});
			
			$('#deleteConceptCostWarehouse-'+listTempStorageVO[j].sstId).on('click', function() {
				var params = "idConceptCostWarehouse="+this.id.split("-") [1];
				ajaxInvokeMethods.postAjaxCallback("deleteConceptCostWarehouse.action", params, function(data){
					var statusMessageError = data.statusMessageError;
					if(statusMessageError != ''){
						$('#alert-errors').modal('show');
						$('#alert-errors-content').html(statusMessageError);						
					}else{
					//Se vuelve a llamar a m�todo para recargar el resultado(lista)
					var selIdSites       = $('#selIdSites').val();
					var params = "stageVO.idSiteStage="+selIdSites;
					ajaxInvokeMethods.postAjaxCallback("listConceptCostWarehouse.action", params, callBackListConceptCostWarehouse);
					}
				});
			});
			
			$('#SST_ENTRY-'+listTempStorageVO[j].sstId).on('blur', function() {
				
				var setupTempStorage = "SST_ENTRY-"+this.value + "-" + this.id.split("-")[1];
				var params = "setupTempStorage="+setupTempStorage;
				ajaxInvokeMethods.postAjaxCallback("setupTempStorageUpdateAttributes.action", params, null);
			});
			
			
			$('#SST_PERCENT_COST-'+listTempStorageVO[j].sstId).on('blur', function() {
				
				var setupTempStorage = "SST_PERCENT_COST-"+this.value + "-" + this.id.split("-")[1];
				var params = "setupTempStorage="+setupTempStorage;
				if (!validationsAttributes.isNumber(this.value)){
					$('#alert-errors').modal('show');
					$('#alert-errors-content').html("Only numbers are allowed");
					this.focus();
				}else{
					ajaxInvokeMethods.postAjaxCallback("setupTempStorageUpdateAttributes.action", params, null);
				}
			});
			
			$('#SST_PROCESSING_FEE-'+listTempStorageVO[j].sstId).on('blur', function() {
				
				var setupTempStorage = "SST_PROCESSING_FEE-"+this.value + "-" + this.id.split("-")[1];
				var params = "setupTempStorage="+setupTempStorage;
				if (!validationsAttributes.isNumber(this.value)){
					$('#alert-errors').modal('show');
					$('#alert-errors-content').html("Only numbers are allowed");
					this.focus();
				}else{
					ajaxInvokeMethods.postAjaxCallback("setupTempStorageUpdateAttributes.action", params, null);
				}
			});

			$('#CUR_ID-'+listTempStorageVO[j].sstId).on('change', function() {
				var setupTempStorage = "CUR_ID-"+this.value + "-" + this.id.split("-")[1];
				var params = "setupTempStorage="+setupTempStorage;

				ajaxInvokeMethods.postAjaxCallback("setupTempStorageUpdateAttributes.action", params, callBackUpdateSstProcessinfFee);
				
			});
		}	
		
		$('.body_page').css('height', 'auto');
		$('.body_page').height($( document ).height() - 172);
		$('.t-f-h-warehouse-concepts').fixedHeader();
		
	}
	
	
	
	function callbackListRangeWarehouse(data){
		
		
		var listRangeWarehouseVO = data.listRangeWarehouseVO;
		
		var listCurrencyVO	   = data.listCurrency;
		var optionCurID		   = "";
		
		
		for (var i=0; i < listCurrencyVO.length; i++){
			optionCurID = optionCurID.concat("<option value='"+listCurrencyVO[i].idGeneralCombo+"'>"+listCurrencyVO[i].nameGeneralCombo+"</option>");
		}
		
		
		
		$("#ranges-warehouse-tbody").empty();
		var rowRoute = "";
		for (var j=0; j<listRangeWarehouseVO.length; j++){
			
			rowRoute = "<tr>";
			
			rowRoute = rowRoute.concat('<td><input type="text" id="RANGE_WAREHOUSE_FROM-'+listRangeWarehouseVO[j].idRangeWarehouse+'" class="input-block-level" value="'+listRangeWarehouseVO[j].fromRangeWarehouse+'" style="text-align: right;" ></td>');
			rowRoute = rowRoute.concat('<td><input type="text" id="RANGE_WAREHOUSE_TO-'+listRangeWarehouseVO[j].idRangeWarehouse+'"   class="input-block-level" value="'+listRangeWarehouseVO[j].toRangeWarehouse+'"   style="text-align: right;"></td>');
			rowRoute = rowRoute.concat('<td><input type="text" id="RANGE_WAREHOUSE_RATE-'+listRangeWarehouseVO[j].idRangeWarehouse+'" class="input-block-level" value="'+Number(listRangeWarehouseVO[j].rateRangeWarehouse).toFixed(2)+'" style="text-align: right;"></td>');
			
			rowRoute = rowRoute.concat("<td>");
			rowRoute = rowRoute.concat("	<select id='CUR_ID-"+listRangeWarehouseVO[j].idRangeWarehouse+"'>" + optionCurID + "	</select>");
			rowRoute = rowRoute.concat("</td>");
			
			rowRoute = rowRoute.concat("<td>" +
					"<a class='btn_table' href='#' title='Replicate Concept' id='replicateRangeWarehouse-"+listRangeWarehouseVO[j].idRangeWarehouse+"'><i class='icon-retweet'></i></a>" +
					"<a class='btn_table' href='#' title='Delete Concept' id='deleteRangeWarehouse-"+listRangeWarehouseVO[j].idRangeWarehouse+"'><i class='icon-trash'></i></a>" +
					"</td>");
			
			rowRoute =rowRoute.concat("</tr>");
			
			$("#ranges-warehouse-tbody").append(rowRoute);
			
			
			$("#CUR_ID-"+listRangeWarehouseVO[j].idRangeWarehouse).val(listRangeWarehouseVO[j].currencyRangeWarehouse);
			
			$('#replicateRangeWarehouse-'+listRangeWarehouseVO[j].idRangeWarehouse).on('click', function() {
				
				var params = "idRangeStorage="+this.id.split("-")[1];
				ajaxInvokeMethods.postAjaxCallback("replicateRangeWarehouse.action", params, function(){
					
					var selIdSites  = $('#selIdSites').val();
					var params = "stageVO.idSiteStage="+selIdSites;
					ajaxInvokeMethods.postAjaxCallback("listRangeWarehouse.action", params, callbackListRangeWarehouse);
					
				});
			});	
			
			$('#deleteRangeWarehouse-'+listRangeWarehouseVO[j].idRangeWarehouse).on('click', function() {
				
				var params = "idRangeStorage="+this.id.split("-")[1];
				ajaxInvokeMethods.postAjaxCallback("deleteRangeWarehouse.action", params, function(data){
					var statusMessageError = data.statusMessageError;
					if(statusMessageError != ''){
						$('#alert-errors').modal('show');
						$('#alert-errors-content').html(statusMessageError);						
					}else{
					var selIdSites  = $('#selIdSites').val();
					var params = "stageVO.idSiteStage="+selIdSites;
					ajaxInvokeMethods.postAjaxCallback("listRangeWarehouse.action", params, callbackListRangeWarehouse);
					}
				});
			});	
			
			
			$('#CUR_ID-'+listRangeWarehouseVO[j].idRangeWarehouse).on('change', function() {
				
				var setupTempStorage = this.id.split("-")[0]+"-"+this.value + "-" + this.id.split("-")[1];
				var params = "setupTempStorage="+setupTempStorage;
				ajaxInvokeMethods.postAjaxCallback("updateRangesWareHouse.action", params, null);
			});
			
			$('#RANGE_WAREHOUSE_FROM-'+listRangeWarehouseVO[j].idRangeWarehouse).on('blur', function() {
				
				var setupTempStorage = this.id.split("-")[0]+"-"+this.value + "-" + this.id.split("-")[1];
				var params = "setupTempStorage="+setupTempStorage;
				if (!validationsAttributes.isNumber(this.value)){
					$('#alert-errors').modal('show');
					$('#alert-errors-content').html("Only numbers are allowed");
				}else{
					ajaxInvokeMethods.postAjaxCallback("updateRangesWareHouse.action", params, null);
				}
			});
			
			$('#RANGE_WAREHOUSE_TO-'+listRangeWarehouseVO[j].idRangeWarehouse).on('blur', function() {
				
				var setupTempStorage = this.id.split("-")[0]+"-"+this.value + "-" + this.id.split("-")[1];
				var params = "setupTempStorage="+setupTempStorage;
				if (!validationsAttributes.isNumber(this.value)){
					$('#alert-errors').modal('show');
					$('#alert-errors-content').html("Only numbers are allowed");
				}else{
					ajaxInvokeMethods.postAjaxCallback("updateRangesWareHouse.action", params, null);
				}
			});
			
			$('#RANGE_WAREHOUSE_RATE-'+listRangeWarehouseVO[j].idRangeWarehouse).on('blur', function() {
				
				var setupTempStorage = this.id.split("-")[0]+"-"+this.value + "-" + this.id.split("-")[1];
				var params = "setupTempStorage="+setupTempStorage;
				if (!validationsAttributes.isNumber(this.value)){
					$('#alert-errors').modal('show');
					$('#alert-errors-content').html("Only numbers are allowed");
				}else{
					ajaxInvokeMethods.postAjaxCallback("updateRangesWareHouse.action", params, null);
				}
			});
			
		}
		
		$('.body_page').css('height', 'auto');
		$('.body_page').height($( document ).height() - 172);
		$('.t-f-h-warehouse-ranges').fixedHeader();
	}
	
	
	function callbackListRateWarehouse(data){
		
		var listRateWarehouseVO = data.listRateWarehouseVO;
		
		$("#rate-warehouse-tbody").empty();
		var rowRoute = "";
		for (var j=0; j<listRateWarehouseVO.length; j++){
			
			rowRoute = "<tr>";
			
			rowRoute = rowRoute.concat('<td>'+listRateWarehouseVO[j].idRateWarehouse+'</td>');
			rowRoute = rowRoute.concat('<td><input type="text" id="RATE_WAREHOUSE_NAME-'+listRateWarehouseVO[j].idRateWarehouse+'" class="input-block-level" value="'+listRateWarehouseVO[j].nameRateWarehouse+'"  ></td>');
			rowRoute = rowRoute.concat('<td><input type="text" id="RATE_WAREHOUSE_RATE-'+listRateWarehouseVO[j].idRateWarehouse+'" class="input-block-level" value="'+listRateWarehouseVO[j].amountRateWarehouse+'"  style="text-align: right; padding-right: 2px;"></td>');
			
			rowRoute = rowRoute.concat("<td>" +
					"<a class='btn_table' href='#' title='Replicate Concept' id='replicateRateStorage-"+listRateWarehouseVO[j].idRateWarehouse+"'><i class='icon-retweet'></i></a>" +
					"<a class='btn_table' href='#' title='Delete Concept' id='deleteRateStorage-"+listRateWarehouseVO[j].idRateWarehouse+"'><i class='icon-trash'></i></a>" +
					"</td>");
			
			rowRoute =rowRoute.concat("</tr>");
			
			$("#rate-warehouse-tbody").append(rowRoute);
			
			
			$('#replicateRateStorage-'+listRateWarehouseVO[j].idRateWarehouse).on('click', function() {
				
				var params = "idRateStorage="+this.id.split("-")[1];
				ajaxInvokeMethods.postAjaxCallback("replicateRateWarehouse.action", params, function(){
					
					var params = "";
					ajaxInvokeMethods.postAjaxCallback("listRateWarehouse.action", params, callbackListRateWarehouse);
					
				});
			});
			
			$('#deleteRateStorage-'+listRateWarehouseVO[j].idRateWarehouse).on('click', function() {
				
				var params = "idRateStorage="+this.id.split("-")[1];
				ajaxInvokeMethods.postAjaxCallback("deleteRateWarehouse.action", params, function(data){
					var statusMessageError = data.statusMessageError;
					if(statusMessageError != ''){
						$('#alert-errors').modal('show');
						$('#alert-errors-content').html(statusMessageError);						
					}else{						
						var params = "";
					ajaxInvokeMethods.postAjaxCallback("listRateWarehouse.action", params, callbackListRateWarehouse);
					}
				});
			});
			
			
			
			$('#RATE_WAREHOUSE_NAME-'+listRateWarehouseVO[j].idRateWarehouse).on('blur', function() {
				
				var setupTempStorage = this.id.split("-")[0]+"-"+this.value + "-" + this.id.split("-")[1];
				var params = "setupTempStorage="+setupTempStorage;
		
				ajaxInvokeMethods.postAjaxCallback("updateRatesWareHouse.action", params, null);
			});
			
			$('#RATE_WAREHOUSE_RATE-'+listRateWarehouseVO[j].idRateWarehouse).on('blur', function() {
				
				var setupTempStorage = this.id.split("-")[0]+"-"+this.value + "-" + this.id.split("-")[1];
				var params = "setupTempStorage="+setupTempStorage;
				if (!validationsAttributes.isNumber(this.value)){
					$('#alert-errors').modal('show');
					$('#alert-errors-content').html("Only numbers are allowed");
				}else{
					ajaxInvokeMethods.postAjaxCallback("updateRatesWareHouse.action", params, null);
				}
			});
		}
		
		$('.body_page').css('height', 'auto');
		$('.body_page').height($( document ).height() - 172);
		$('.t-f-h-warehouse-rates').fixedHeader();
	}
	
	
	function callbackListRoutesLocalTransport(data){
		
		var listRouteLocalTransportVO = data.listRouteLocalTransportVO;
		
		$("#route-localtransport-tbody").empty();
		
		var rowRoute = "";
		for (var j=0; j<listRouteLocalTransportVO.length; j++){
			
			rowRoute = "<tr>";
			
			rowRoute = rowRoute.concat('<td>'+listRouteLocalTransportVO[j].idRouteLocalTransport+'</td>');
			rowRoute = rowRoute.concat('<td><input type="text" id="ROUTE_LOCALTRANS_NAME-'+listRouteLocalTransportVO[j].idRouteLocalTransport+'" class="input-block-level" value="'+listRouteLocalTransportVO[j].nameRouteLocalTransport+'" ></td>');
			rowRoute = rowRoute.concat('<td><input type="text" id="ROUTE_LOCALTRANS_RATE-'+listRouteLocalTransportVO[j].idRouteLocalTransport+'" class="input-block-level" value="'+listRouteLocalTransportVO[j].rateRouteLocalTransport+'" style="text-align: right; padding-right: 2px;"></td>');
			
			rowRoute = rowRoute.concat("<td>" +
					"<a class='btn_table' href='#' title='Replicate Concept' id='replicateRouteLocalTransport-"+listRouteLocalTransportVO[j].idRouteLocalTransport+"'><i class='icon-retweet'></i></a>" +
					"<a class='btn_table' href='#' title='Delete Concept' id='deleteRouteLocalTransport-"+listRouteLocalTransportVO[j].idRouteLocalTransport+"'><i class='icon-trash'></i></a>" +
					"</td>");
			
			rowRoute =rowRoute.concat("</tr>");
			
			$("#route-localtransport-tbody").append(rowRoute);
			
			
			
			
			//Eventos lista
			$('#replicateRouteLocalTransport-'+listRouteLocalTransportVO[j].idRouteLocalTransport).on('click', function() {
				var params = "idRouteLocalTransport="+this.id.split("-") [1];
				ajaxInvokeMethods.postAjaxCallback("replicateRouteLocalTransport.action", params, function(data){
					
					//Se vuelve a llamar a m�todo para recargar el resultado(lista)
					var params = "";
					ajaxInvokeMethods.postAjaxCallback("listRoutesLocalTransport.action", params, callbackListRoutesLocalTransport);
				});
			});
			
			$('#deleteRouteLocalTransport-'+listRouteLocalTransportVO[j].idRouteLocalTransport).on('click', function() {
				var params = "idRouteLocalTransport="+this.id.split("-") [1];
				ajaxInvokeMethods.postAjaxCallback("deleteRouteLocalTransport.action", params, function(data){
					var statusMessageError = data.statusMessageError;
					if(statusMessageError != ''){
						$('#alert-errors').modal('show');
						$('#alert-errors-content').html(statusMessageError);						
					}else{
					//Se vuelve a llamar a m�todo para recargar el resultado(lista)
					var params = "";
					ajaxInvokeMethods.postAjaxCallback("listRoutesLocalTransport.action", params, callbackListRoutesLocalTransport);
					}
				});
			});
			
			
			
			$('#ROUTE_LOCALTRANS_NAME-'+listRouteLocalTransportVO[j].idRouteLocalTransport).on('blur', function() {
				var setupTempStorage = this.id.split("-")[0]+"-"+this.value + "-" + this.id.split("-")[1];
				var params = "setupTempStorage="+setupTempStorage;
		
				ajaxInvokeMethods.postAjaxCallback("updateRoutesLocalTransport.action", params, null);
			});
			
			$('#ROUTE_LOCALTRANS_RATE-'+listRouteLocalTransportVO[j].idRouteLocalTransport).on('blur', function() {
				
				var setupTempStorage = this.id.split("-")[0]+"-"+this.value + "-" + this.id.split("-")[1];
				var params = "setupTempStorage="+setupTempStorage;
				if (!validationsAttributes.isNumber(this.value)){
					$('#alert-errors').modal('show');
					$('#alert-errors-content').html("Only numbers are allowed");
				}else{
					ajaxInvokeMethods.postAjaxCallback("updateRoutesLocalTransport.action", params, null);
				}
			});
			
		}
		
		$('.body_page').css('height', 'auto');
		$('.body_page').height($( document ).height() - 172);
		$('.t-f-h-localtransport-routes').fixedHeader();
		
	}
	
		
	function callbackListRangesLocalTransport(data){
		
		
		var listLocalTransportRange = data.listLocalTransportRange;
		
		var listCurrencyVO	   = data.listCurrency;
		var optionCurID		   = "";
		
		
		for (var i=0; i < listCurrencyVO.length; i++){
			optionCurID = optionCurID.concat("<option value='"+listCurrencyVO[i].idGeneralCombo+"'>"+listCurrencyVO[i].nameGeneralCombo+"</option>");
		}
		
		
		$("#ranges-localtransport-tbody").empty();
		var rowRoute = "";
		for (var j=0; j<listLocalTransportRange.length; j++){
			
			rowRoute = "<tr>";
			
			rowRoute = rowRoute.concat('<td><input type="text" id="RANGE_LOCALTRANS_FROM-'+listLocalTransportRange[j].idRangeLocalTransport+'" class="input-block-level" value="'+listLocalTransportRange[j].fromRangeLocalTransport+'" style="text-align:right;" ></td>');
			rowRoute = rowRoute.concat('<td><input type="text" id="RANGE_LOCALTRANS_TO-'+listLocalTransportRange[j].idRangeLocalTransport+'" class="input-block-level" value="'+listLocalTransportRange[j].toRangeLocalTransport+'" style="text-align:right;"></td>');
			rowRoute = rowRoute.concat('<td><input type="text" id="RANGE_LOCALTRANS_RATE-'+listLocalTransportRange[j].idRangeLocalTransport+'" class="input-block-level" value="'+Number(listLocalTransportRange[j].rateRangeLocalTransport).toFixed(2)+'" style="text-align:right;"></td>');
			
			
			rowRoute = rowRoute.concat("<td>");
			rowRoute = rowRoute.concat("	<select id='CUR_ID-"+listLocalTransportRange[j].idRangeLocalTransport+"'>" + optionCurID + "	</select>");
			rowRoute = rowRoute.concat("</td>");
			
			rowRoute = rowRoute.concat("<td>" +
					"<a class='btn_table' href='#' title='Replicate Concept' id='replicateRangeLocalTransport-"+listLocalTransportRange[j].idRangeLocalTransport+"'><i class='icon-retweet'></i></a>" +
					"<a class='btn_table' href='#' title='Delete Concept' id='deleteRangeLocalTransport-"+listLocalTransportRange[j].idRangeLocalTransport+"'><i class='icon-trash'></i></a>" +
					"</td>");
			
			rowRoute =rowRoute.concat("</tr>");
			
			$("#ranges-localtransport-tbody").append(rowRoute);
			
			
			//Setting values para objetos de la tabla (lista)
			$("#CUR_ID-"+listLocalTransportRange[j].idRangeLocalTransport).val(listLocalTransportRange[j].currencyRangeLocalTransport);
			
			
			//Creando eventos para lista 
			
			$('#replicateRangeLocalTransport-'+listLocalTransportRange[j].idRangeLocalTransport).on('click', function() {
				var params = "idRangeLocalTransport="+this.id.split("-") [1];
				ajaxInvokeMethods.postAjaxCallback("replicateRangeLocalTransport.action", params, function(data){
					
					//Se vuelve a llamar a m�todo para recargar el resultado(lista)
					var selIdSites  = $('#selIdSites').val();
					var params = "stageVO.idSiteStage="+selIdSites;
					ajaxInvokeMethods.postAjaxCallback("listRangesLocalTransport.action", params, callbackListRangesLocalTransport);
				});
			});
			
			$('#deleteRangeLocalTransport-'+listLocalTransportRange[j].idRangeLocalTransport).on('click', function() {
				var params = "idRangeLocalTransport="+this.id.split("-") [1];
				ajaxInvokeMethods.postAjaxCallback("deleteRangeLocalTransport.action", params, function(data){
					var statusMessageError = data.statusMessageError;
					if(statusMessageError != ''){
						$('#alert-errors').modal('show');
						$('#alert-errors-content').html(statusMessageError);						
					}else{
					//Se vuelve a llamar a m�todo para recargar el resultado(lista)
					var selIdSites  = $('#selIdSites').val();
					var params = "stageVO.idSiteStage="+selIdSites;
					ajaxInvokeMethods.postAjaxCallback("listRangesLocalTransport.action", params, callbackListRangesLocalTransport);
					}
				});
			});
			
			$('#CUR_ID-'+listLocalTransportRange[j].idRangeLocalTransport).on('change', function() {
				
				var setupTempStorage = this.id.split("-")[0]+"-"+this.value + "-" + this.id.split("-")[1];
				var params = "setupTempStorage="+setupTempStorage;
				ajaxInvokeMethods.postAjaxCallback("updateRangesLocalTransport.action", params, null);
				
			});
			
			
			$('#RANGE_LOCALTRANS_FROM-'+listLocalTransportRange[j].idRangeLocalTransport).on('blur', function() {
				
				var setupTempStorage = this.id.split("-")[0]+"-"+this.value + "-" + this.id.split("-")[1];
				var params = "setupTempStorage="+setupTempStorage;
				if (!validationsAttributes.isNumber(this.value)){
					$('#alert-errors').modal('show');
					$('#alert-errors-content').html("Only numbers are allowed");
				}else{
					ajaxInvokeMethods.postAjaxCallback("updateRangesLocalTransport.action", params, null);
				}
			});
			
			
			$('#RANGE_LOCALTRANS_TO-'+listLocalTransportRange[j].idRangeLocalTransport).on('blur', function() {
				
				var setupTempStorage = this.id.split("-")[0]+"-"+this.value + "-" + this.id.split("-")[1];
				var params = "setupTempStorage="+setupTempStorage;
				if (!validationsAttributes.isNumber(this.value)){
					$('#alert-errors').modal('show');
					$('#alert-errors-content').html("Only numbers are allowed");
				}else{
					ajaxInvokeMethods.postAjaxCallback("updateRangesLocalTransport.action", params, null);
				}
			});
			
			
			$('#RANGE_LOCALTRANS_RATE-'+listLocalTransportRange[j].idRangeLocalTransport).on('blur', function() {
				
				var setupTempStorage = this.id.split("-")[0]+"-"+this.value + "-" + this.id.split("-")[1];
				var params = "setupTempStorage="+setupTempStorage;
				if (!validationsAttributes.isNumber(this.value)){
					$('#alert-errors').modal('show');
					$('#alert-errors-content').html("Only numbers are allowed");
				}else{
					ajaxInvokeMethods.postAjaxCallback("updateRangesLocalTransport.action", params, null);
				}
			});
			
		}
		
		$('.body_page').css('height', 'auto');
		$('.body_page').height($( document ).height() - 172);
		$('.t-f-h-localtransport-ranges').fixedHeader();
		
	}
	
	function callbackListConceptCostLocalTransport(data){
		
		var listLocalTransportVO = data.listLocalTransportVO;
		var listCurrencyVO       = data.listCurrency;
		
		var optionCurID = "";
		for (var i=0; i < listCurrencyVO.length; i++){
			optionCurID = optionCurID.concat("<option value='"+listCurrencyVO[i].idGeneralCombo+"'>"+listCurrencyVO[i].nameGeneralCombo+"</option>");
		}
		
		$("#concept-cost-localtransport").empty();
		for (var j=0; j<listLocalTransportVO.length; j++){

			var row = "";
			row = row.concat("<tr>");
			row = row.concat("<td>"+listLocalTransportVO[j].sltId+"</td>");
			row = row.concat("<td><input type='text' id='SLT_ENTRY-"+listLocalTransportVO[j].sltId+"' class='input-block-level' value='12,00' style='text-align: right;' ></td>");
			row = row.concat("<td><input type='text' id='SLT_PROCESSING_FEE-"+listLocalTransportVO[j].sltId+"' class='input-block-level' value='50,00' style='text-align: right;'></td>");
			row = row.concat("<td>");
			row = row.concat("	<select id='CUR_ID-"+listLocalTransportVO[j].sltId+"'>" + optionCurID + "	</select>");
			row = row.concat("</td>");
			
			row = row.concat("<td>" +
					"<a class='btn_table' href='#' title='Replicate Concept' id='replicateConceptCostLocalTransport-"+listLocalTransportVO[j].sltId+"'><i class='icon-retweet'></i></a>" +
					"<a class='btn_table' href='#' title='Delete Concept' id='deleteConceptCostLocalTransport-"+listLocalTransportVO[j].sltId+"'><i class='icon-trash'></i></a>" +
					"</td>");
			
			row = row.concat("</tr>");
			
			$("#concept-cost-localtransport").append(row);
			
			$("#SLT_ENTRY-"+listLocalTransportVO[j].sltId).val(listLocalTransportVO[j].entry);
			$("#SLT_PROCESSING_FEE-"+listLocalTransportVO[j].sltId).val(listLocalTransportVO[j].processingFee);			
			$("#CUR_ID-"+listLocalTransportVO[j].sltId).val(listLocalTransportVO[j].currencyID);			
			

			
			//Generando eventos para la lista 
			$('#replicateConceptCostLocalTransport-'+listLocalTransportVO[j].sltId).on('click', function() {
				var params = "idConceptCostLocalTransport="+this.id.split("-") [1];
				ajaxInvokeMethods.postAjaxCallback("replicateConceptCostLocalTransport.action", params, function(data){
					
					//Se vuelve a llamar a m�todo para recargar el resultado(lista)
					var selIdSites       = $('#selIdSites').val();
					var params = "stageVO.idSiteStage="+selIdSites;
					ajaxInvokeMethods.postAjaxCallback("listConceptCostLocalTransport.action", params, callbackListConceptCostLocalTransport);
				});
			});
			
			$('#deleteConceptCostLocalTransport-'+listLocalTransportVO[j].sltId).on('click', function() {
				var params = "idConceptCostLocalTransport="+this.id.split("-") [1];
				ajaxInvokeMethods.postAjaxCallback("deleteConceptCostLocalTransport.action", params, function(data){
					var statusMessageError = data.statusMessageError;
					if(statusMessageError != ''){
						$('#alert-errors').modal('show');
						$('#alert-errors-content').html(statusMessageError);						
					}else{
					//Se vuelve a llamar a m�todo para recargar el resultado(lista)
					var selIdSites       = $('#selIdSites').val();
					var params = "stageVO.idSiteStage="+selIdSites;
					ajaxInvokeMethods.postAjaxCallback("listConceptCostLocalTransport.action", params, callbackListConceptCostLocalTransport);
					}
				});
			});

			
			$('#SLT_ENTRY-'+listLocalTransportVO[j].sltId).on('blur', function() {
				
				var locaTransportStorage = "SLT_ENTRY-"+this.value + "-" + this.id.split("-")[1];
				var params = "locaTransportStorage="+locaTransportStorage;
				ajaxInvokeMethods.postAjaxCallback("setupLocalTransportUpdateAttributes.action", params, null);
			});
			
			
			$('#SLT_PROCESSING_FEE-'+listLocalTransportVO[j].sltId).on('blur', function() {
				
				var locaTransportStorage = "SLT_PROCESSING_FEE-"+this.value + "-" + this.id.split("-")[1];
				var params = "locaTransportStorage="+locaTransportStorage;
				if (!validationsAttributes.isNumber(this.value)){
					$('#alert-errors').modal('show');
					$('#alert-errors-content').html("Only numbers are allowed");
					this.focus();
				}else{
					ajaxInvokeMethods.postAjaxCallback("setupLocalTransportUpdateAttributes.action", params, null);
				}
			});

			$('#CUR_ID-'+listLocalTransportVO[j].sltId).on('change', function() {
				
				var locaTransportStorage = "CUR_ID-"+this.value + "-" + this.id.split("-")[1];
				var params = "locaTransportStorage="+locaTransportStorage;

				ajaxInvokeMethods.postAjaxCallback("setupLocalTransportUpdateAttributes.action", params, callBackUpdateSltProcessinfFee);
			});
		}
		
		$('.body_page').css('height', 'auto');
		$('.body_page').height($( document ).height() - 172);
		$('.t-f-h-localtransport-concepts').fixedHeader();
	}
	
	
	
	function callbackImportUnitClick(data){
		$("#tab-move-cost a[href='#tab-import-units']").trigger("click");
	}
	
	function callbackUpdateFilter(data){
		$('#filterTransport').val('0');
		$('#filterSiteSource').val('0');
		$('#filterPresentation').val('0');
		$('#filterTrade').val('0');
		$('#filterFamily').val('0');
		$('#filterType').val('0');
		$('#filterTypeLoad').val('0');
		$('#filterCustomDuty').val('0');
		$('#filterProduct').val('');
		$("#tab-move-cost a[href='#tab-import-units']").trigger("click");
	}

//	function callbackFlyUpdate(){
//		$('#tab-move-cost a[href="#tab-freight"]').trigger("click");
//	}
	
});