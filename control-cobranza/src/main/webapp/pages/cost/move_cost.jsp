<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
<jsp:directive.include file="/pages/templates/header_html.jsp" />


<script>

function onloadStageExist(){
	
	var idStage = <%=request.getAttribute("stageVO.idStage")%>
	var nameStage = '<%=request.getAttribute("stageVO.nameStage")%>'
	var descriptionStage = '<%=request.getAttribute("stageVO.descriptionStage")%>'
	var idSiteStage = '<%=request.getAttribute("stageVO.idSiteStage")%>'
	
	if (idStage != null){
		$('#selIdSites').val(idSiteStage);
		$('#nameStage').val(nameStage);
		$('#descriptionStage').val(descriptionStage);
		
		//Se desactiva selector de paises
		$('#selIdSites').prop("disabled", true); 
		
		$("#form-content-inputs").show();
// 		$("#div-summary-cost").show();
		$("#div-buttom-stage").show();
		
		$("#tab-move-cost a[href='#tab-import-units']").trigger("click");
	}
}

</script>

</head>
<body onload="onloadStageExist();">
	<script src="js/actions/cost/move_cost.js"></script>

	<div id="wrapper">

		<jsp:directive.include file="/pages/templates/head_body.jsp" />

		<div class="body_page">
			<div class="body_content">
				<form action="home.action" class="form_general">

					<fieldset>

						<legend>Simulator Inputs</legend>


						<div class="column_form">

							<label class="control-label">Name Stage:</label> <input
								type="text" id="nameStage" class="input-block-level">
						</div>

						<div class="column_form">
							<label class="control-label">Description Stage:</label>
							<textarea rows="0" cols="" id="descriptionStage"
								style="height: 20px;"></textarea>
						</div>


						<div class="column_form">
							<label class="control-label">Site:</label> <select
								id="selIdSites">
								<option value="">Choice Site</option>

								<s:iterator value="#session.userSessionVO.listSiteVO">
									<option value="<s:property value="idSite"/>"><s:property
											value="nameSite" /></option>
								</s:iterator>
							</select>
						</div>

						<!-- 						<div class="column_form"> -->
						<!-- 							<label class="control-label">Description Stage:</label> -->
						<!-- 							<textarea rows="0" cols="" id="descriptionStage" style="height : 35px;"></textarea> -->
						<!-- 						</div> -->

						<div style="clear: both;"></div>

					</fieldset>

				</form>

				<div class="well">

					<div class="buttoms" id="div-buttom-stage" style="display: none;">
						<button class="btn" type="button" id="btnNewStage">New
							Stage</button>
						<button class="btn btn-success" type="button" id="btnSimulate">Save
							Stage</button>
					</div>
				</div>

				<form action="home.action" class="form_general"
					id="form-content-inputs" style="display: none;">

					<fieldset>
						<ul class="nav nav-tabs" id="tab-move-cost">
							<li class="active"><a href="#tab-import-units">Import
									Units</a></li>
							<li><a href="#tab-item-master">Item Master</a></li>
							<li><a href="#tab-setup">Setup</a></li>
							<li><a href="#tab-freight">Freight</a></li>
							<li id="loading-local"
								style="text-align: right; width: auto; float: right; display: none;"><div
									style="width: 30px; height: 30px;">
									<img alt="" src="img/loading.gif">
								</div></li>
						</ul>

						<div class="tab-content">
							<div class="tab-pane active" id="tab-import-units">

								<div class="head_table_icons">
									<a class="btn_table" href="#" id="btnDeleteFilter"
										title="Refresh"><i class="icon-refresh"></i></a> <a
										class="btn_table" href="#" id="btnFindProduts" title="Filter"><i
										class="icon-filter"></i></a>
								</div>

								<table class="table table-hover table-fixed-header"
									id="table-import-units">
									<thead class="header">
										<tr>
											<th>Local Code</th>
											<th>Description</th>
											<th>JAN</th>
											<th>FEB</th>
											<th>MAR</th>
											<th>APR</th>
											<th>MAY</th>
											<th>JUN</th>
											<th>JUL</th>
											<th>AUG</th>
											<th>SEP</th>
											<th>OCT</th>
											<th>NOV</th>
											<th>DIC</th>
											<th>Total</th>
											<th>Media</th>
											<th>Ajusted</th>
											<th colspan="2">Action</th>
										</tr>
									</thead>
									<tbody id="table-import-units-tbody">
									</tbody>
								</table>

							</div>

							<div class="tab-pane" id="tab-item-master">

								<table class="table table-hover t-f-h-item-master"
									id="table-item-master">
									<thead class="header">
										<tr>
											<th>Local Code</th>
											<th>Description</th>
											<!-- 											<th>Type</th> -->
											<!-- 											<th>Family</th> -->
											<!-- 											<th>Unit Measure</th> -->
											<th>Trn. Price</th>
											<th>Route</th>
											<th>Palette Height(cm)</th>
											<th>Palette Lenght(cm)</th>
											<th>Palette Width(cm)</th>
											<th>Palette Weight</th>
											<th>Units Per Palette</th>
											<!-- 											<th>Transport</th> -->
											<!-- 											<th>Business</th> -->
											<!-- 											<th>Presentation</th> -->
											<!-- 											<th>Cargo Type</th> -->
											<th>Duty Type</th>

											<th>Local Transport Route</th>
											<th>Warehousing Rate</th>
											<th>VAT Import</th>
										</tr>
									</thead>
									<tbody id="table-item-master-tbody"></tbody>

								</table>
							</div>


							<div class="tab-pane" id="tab-setup">

								<ul class="nav nav-tabs" id="nav-setup">
									<li class="active"><a href="#nav-setup-exchange">Macroeconomic</a></li>
									<li><a href="#nav-setup-local-custom-duties">Local
											Custum Duties</a></li>
									<li><a href="#nav-setup-operative-management">Operative
											Activities</a></li>
									<li><a href="#nav-setup-temp-storage">Temporal
											Warehousing</a></li>
									<li><a href="#nav-setup-local-transport">Local
											Transport</a></li>
									<li><a href="#nav-setup-custody">Security</a></li>
								</ul>


								<div class="tab-content">
									<div class="tab-pane active" id="nav-setup-exchange">

										<ul class="nav nav-tabs" id="nav-setup-exchange-currency">
											<li class="active"><a href="#nav-setup-exchange-cur">Exchange
													Currency</a></li>
											<li><a href="#nav-setup-exchange-macro-vars">Macro
													vars</a></li>
										</ul>


										<div class="tab-content">
											<div class="tab-pane active" id="nav-setup-exchange-cur">


												<div class="column_form" id="setup-exchange-column"></div>

												<table class="table table-hover t-f-h-exchange-currency"
													id="table-setup-exchange-column">
													<thead class="header">
														<tr>
															<th>Id Currency</th>
															<th>Description Currency</th>
															<th>Acronym Currency</th>
															<th>Rate Currency</th>
															<th>Action</th>
														</tr>
													</thead>
													<tbody id="table-setup-exchange-column-tbody"></tbody>
												</table>

											</div>


											<div class="tab-pane" id="nav-setup-exchange-macro-vars">

												<div class="column_form" id="setup-macrovar-column">
													<label class="control-label">Exchange Rate:</label> <input
														type="text" class="input-block-level"
														style="text-align: right; padding-right: 2px;">
												</div>

											</div>
										</div>



									</div>

									<div class="tab-pane" id="nav-setup-local-custom-duties">

										<fieldset>

											<div class="column_form">

												<label class="control-label">Other Taxes 2(%):</label> <input
													type="text" class="input-block-level"
													style="text-align: right; padding-right: 2px;"
													id="custom-duties-other-taxes">

											</div>


											<div class="column_form">
												<label class="control-label">Foreign Money Transfer
													Tax (%):</label> <input type="text" class="input-block-level"
													style="text-align: right; padding-right: 2px;"
													id="custom-duties-sal-capitales">
											</div>

											<div class="column_form">
												<label class="control-label">VAT Imports(%):</label> <input
													type="text" class="input-block-level"
													style="text-align: right; padding-right: 2px;"
													id="custom-duties-vat-import">
											</div>
										</fieldset>

										<br>
										<div class="well"></div>


										<table class="table table-hover t-f-h-custom-duties"
											id="table-setup-custom-duties">
											<thead class="header">
												<tr>
													<th>Duty Code</th>
													<th>Duty Type</th>
													<th>Duty Rate(%)</th>
													<th>Other Taxes 1(%)</th>
													<th>Action</th>
												</tr>
											</thead>
											<tbody id="table-setup-custom-duties-tbody"></tbody>
										</table>

									</div>

									<div class="tab-pane" id="nav-setup-operative-management">

										<table class="table table-hover t-f-h-operative-management"
											id="table-setup-ope-manage">
											<thead class="header">
												<tr>
													<th>Description</th>
													<th>Currency</th>
													<th>Operative fee</th>
													<th>Kg Fee</th>
													<th>Comission CIF %</th>
													<th>Action</th>
												</tr>
											</thead>
											<tbody id="table-setup-ope-manage-tbody">
											</tbody>
										</table>

									</div>

									<div class="tab-pane" id="nav-setup-temp-storage">

										<!-- 										<fieldset > -->

										<!-- 											<div class="column_form"> -->
										<!-- 												<label class="control-label">Weight range:</label> -->
										<%-- 												<select id="temp-storage-range"> --%>
										<%-- 												</select> --%>
										<!-- 											</div> -->


										<!-- 											<div class="column_form"> -->

										<!-- 												<label class="control-label">Range from:</label> -->
										<!-- 												<input type="text" id="temp-storage-range-from" class="input-block-level" value="0" style="text-align: right; padding-right: 2px;"> -->

										<!-- 												<label class="control-label">Range up:</label> -->
										<!-- 												<input type="text" id="temp-storage-range-up" class="input-block-level" value="799" style="text-align: right; padding-right: 2px;"> -->

										<!-- 												<label class="control-label">Kg. rate:</label> -->
										<!-- 												<input type="text" id="temp-storage-range-rate" class="input-block-level" value="0,22" style="text-align: right; padding-right: 2px;"> -->

										<!-- 												<label class="control-label">Currency Kg. Rate:</label> -->
										<%-- 												<select id="temp-storage-range-currency"></select> --%>

										<!-- 											</div> -->



										<!-- 											<label class="control-label">Actions Warehousing:</label> -->

										<!-- 											<blockquote> -->
										<!-- 												<p>Concept Cost Warehousing:</p> -->
										<!-- 												<a id="concept-cost-warehouse" class="btn_table" title="Concepts Cost Warehousing"><i class="icon-align-justify"></i></a>  -->
										<!-- 												<a id="ranges-warehousing"     class="btn_table" title="Concepts Cost Warehousing"><i class="icon-plus"></i></a>  -->
										<!-- 												<a id="rates-warehousing"      class="btn_table" title="Concepts Cost Warehousing"><i class="icon-plus-sign"></i></a> -->
										<!-- 											</blockquote> -->


										<!-- 										</fieldset> -->

										<!-- 											<ul class="nav nav-tabs" id="nav-setup"> -->
										<!-- 												<li class="active"><a href="#nav-warehouse-rates">Warehouse Rates</a></li> -->
										<!-- 												<li><a href="#nav-warehouse-ranges">Warehouse Ranges</a></li> -->
										<!-- 												<li><a href="#nav-warehouse-concepts">Warehouse Concepts Cost</a></li> -->
										<!-- 											</ul> -->
















										<!-- 								<ul class="nav nav-tabs" id="freights-tab"> -->
										<!-- 									<li class="active"><a href="#freight-aerial">Air Freight Routes</a></li> -->
										<!-- 									<li><a href="#freight-marine">Other Freight Routes</a></li> -->
										<!-- 								</ul> -->

										<!-- 				<li class="active"><a href="#tab-import-units">Import Units</a></li> -->
										<!-- 							<li><a href="#tab-item-master">Item Master</a></li> -->
										<!-- 							<li><a href="#tab-setup">Setup</a></li> -->
										<!-- 							<li><a href="#tab-freight">Freight Routes</a></li> -->



										<ul class="nav nav-tabs" id="nav-warehousing">
											<li class="active"><a href="#tab-warehouse-rates">Percentual
													rates</a></li>
											<li><a href="#tab-warehouse-ranges">Weight Ranges</a></li>
											<li><a href="#tab-warehouse-concepts">Warehousing
													fees</a></li>
										</ul>


										<div class="tab-content">

											<div class="tab-pane active" id="tab-warehouse-rates">

												<table class="table table-hover t-f-h-warehouse-rates">
													<thead class="header">
														<tr>
															<th>ID Rate</th>
															<th>Description Rate</th>
															<th style="text-align: right;">Rate CIF(%)</th>
															<th>Action</th>
														</tr>
													</thead>
													<tbody id="rate-warehouse-tbody">

														<!-- 												<tr> -->
														<!-- 													<td>1</td> -->
														<!-- 													<td><input type="text" id="temp-storage-range-rate1" class="input-block-level" value="TARIFA 01.lkjsadlk asjdkl j" ></td> -->
														<!-- 													<td><input type="text" id="temp-storage-range-rate1" class="input-block-level" value="0,22" style="text-align: right; padding-right: 2px;"></td> -->
														<!-- 													<td> -->
														<!-- 														<a class='btn_table' href='#' title='Delete Route' id='replicateFreight-"+1+"'><i class='icon-retweet'></i></a> -->
														<!-- 														<a class='btn_table' href='#' title='Delete Route' id='replicateFreight-"+1+"'><i class='icon-trash'></i></a> -->
														<!-- 													</td> -->

														<!-- 												</tr> -->

														<!-- 												<tr> -->
														<!-- 													<td>2</td> -->
														<!-- 													<td><input type="text" id="temp-storage-range-rate1" class="input-block-level" value="TARIFA 02 ksajdlkasjd lkasj dlksajd" ></td> -->
														<!-- 													<td><input type="text" id="temp-storage-range-rate1" class="input-block-level" value="0,22" style="text-align: right; padding-right: 2px;"></td> -->
														<!-- 													<td> -->
														<!-- 														<a class='btn_table' href='#' title='Delete Route' id='replicateFreight-"+1+"'><i class='icon-retweet'></i></a> -->
														<!-- 														<a class='btn_table' href='#' title='Delete Route' id='replicateFreight-"+1+"'><i class='icon-trash'></i></a> -->
														<!-- 													</td> -->

														<!-- 												</tr> -->

														<!-- 												<tr> -->
														<!-- 													<td>3</td> -->
														<!-- 													<td><input type="text" id="temp-storage-range-rate1" class="input-block-level" value="TARIFA 034 ksjdlkasj dlksajd " ></td> -->
														<!-- 													<td><input type="text" id="temp-storage-range-rate1" class="input-block-level" value="0,22" style="text-align: right; padding-right: 2px;"></td> -->
														<!-- 													<td> -->
														<!-- 														<a class='btn_table' href='#' title='Delete Route' id='replicateFreight-"+1+"'><i class='icon-retweet'></i></a> -->
														<!-- 														<a class='btn_table' href='#' title='Delete Route' id='replicateFreight-"+1+"'><i class='icon-trash'></i></a> -->
														<!-- 													</td> -->

														<!-- 												</tr> -->
													</tbody>
												</table>

											</div>

											<div class="tab-pane" id="tab-warehouse-ranges">


												<table class="table table-hover t-f-h-warehouse-ranges">
													<thead class="header">
														<tr>
															<th>Kg. Range from</th>
															<th>Kg. Range to</th>
															<th>KG. Fee</th>
															<th>Currency</th>
															<th>Action</th>
														</tr>
													</thead>

													<tbody id="ranges-warehouse-tbody">
														<!-- 												<tr> -->

														<!-- 													<td><input type="text" class="input-block-level" id="freight-others-destination" value="1" style="text-align: right; padding-right: 2px;"></td> -->
														<!-- 													<td><input type="text" class="input-block-level" id="freight-others-destination" value="150" style="text-align: right; padding-right: 2px;"></td> -->
														<!-- 													<td><input type="text" class="input-block-level" id="freight-others-destination" value="1.225" style="text-align: right; padding-right: 2px;"></td> -->

														<!-- 													<td> -->
														<!-- 														<a class='btn_table' href='#' title='Delete Route' id='replicateFreight-"+1+"'><i class='icon-retweet'></i></a> -->
														<!-- 														<a class='btn_table' href='#' title='Delete Route' id='replicateFreight-"+1+"'><i class='icon-trash'></i></a> -->
														<!-- 													</td> -->
														<!-- 												</tr> -->

														<!-- 												<tr> -->
														<!-- 													<td><input type="text" class="input-block-level" id="freight-others-destination" value="151" style="text-align: right; padding-right: 2px;"></td> -->
														<!-- 													<td><input type="text" class="input-block-level" id="freight-others-destination" value="300" style="text-align: right; padding-right: 2px;"></td> -->
														<!-- 													<td><input type="text" class="input-block-level" id="freight-others-destination" value="1.225" style="text-align: right; padding-right: 2px;"></td> -->

														<!-- 													<td> -->
														<!-- 														<a class='btn_table' href='#' title='Delete Route' id='replicateFreight-"+1+"'><i class='icon-retweet'></i></a> -->
														<!-- 														<a class='btn_table' href='#' title='Delete Route' id='replicateFreight-"+1+"'><i class='icon-trash'></i></a> -->
														<!-- 													</td> -->
														<!-- 												</tr> -->

														<!-- 												<tr> -->
														<!-- 													<td><input type="text" class="input-block-level" id="freight-others-destination" value="301" style="text-align: right; padding-right: 2px;"></td> -->
														<!-- 													<td><input type="text" class="input-block-level" id="freight-others-destination" value="450" style="text-align: right; padding-right: 2px;"></td> -->
														<!-- 													<td><input type="text" class="input-block-level" id="freight-others-destination" value="1.225" style="text-align: right; padding-right: 2px;"></td> -->

														<!-- 													<td> -->
														<!-- 														<a class='btn_table' href='#' title='Delete Route' id='replicateFreight-"+1+"'><i class='icon-retweet'></i></a> -->
														<!-- 														<a class='btn_table' href='#' title='Delete Route' id='replicateFreight-"+1+"'><i class='icon-trash'></i></a> -->
														<!-- 													</td> -->
														<!-- 												</tr> -->

														<!-- 												<tr> -->

														<!-- 													<td><input type="text" class="input-block-level" id="freight-others-destination" value="451" style="text-align: right; padding-right: 2px;"></td> -->
														<!-- 													<td><input type="text" class="input-block-level" id="freight-others-destination" value="700" style="text-align: right; padding-right: 2px;"></td> -->
														<!-- 													<td><input type="text" class="input-block-level" id="freight-others-destination" value="1.225" style="text-align: right; padding-right: 2px;"></td> -->

														<!-- 													<td> -->
														<!-- 														<a class='btn_table' href='#' title='Delete Route' id='replicateFreight-"+1+"'><i class='icon-retweet'></i></a> -->
														<!-- 														<a class='btn_table' href='#' title='Delete Route' id='replicateFreight-"+1+"'><i class='icon-trash'></i></a> -->
														<!-- 													</td> -->
														<!-- 												</tr> -->

													</tbody>
												</table>

											</div>

											<div class="tab-pane" id="tab-warehouse-concepts">

												<table class="table table-hover t-f-h-warehouse-concepts">
													<thead class="header">
														<tr>
															<th>Id Concept</th>
															<th>Description</th>
															<th>Amount</th>
															<th>Currency</th>
															<th>Action</th>
														</tr>
													</thead>

													<tbody id="concept-cost-warehouse">

														<!-- 											<tr> -->
														<!-- 												<td>1</td> -->
														<!-- 												<td><input type="text" class="input-block-level" id="freight-others-destination" value="Salvaguarda moneda local"></td> -->
														<!-- 												<td><input type="text" class="input-block-level" id="freight-others-destination" style="text-align: right; padding-right: 2px;"></td> -->

														<!-- 												<td> -->
														<!-- 													<a class='btn_table' href='#' title='Delete Route' id='replicateFreight-"+1+"'><i class='icon-retweet'></i></a> -->
														<!-- 													<a class='btn_table' href='#' title='Delete Route' id='replicateFreight-"+1+"'><i class='icon-trash'></i></a> -->
														<!-- 												</td> -->
														<!-- 											</tr> -->

														<!-- 											<tr> -->
														<!-- 												<td>2</td> -->
														<!-- 												<td><input type="text" class="input-block-level" id="freight-others-destination" value="Manipuleo moneda local"></td> -->
														<!-- 												<td><input type="text" class="input-block-level" id="freight-others-destination" style="text-align: right; padding-right: 2px;"></td> -->

														<!-- 												<td> -->
														<!-- 													<a class='btn_table' href='#' title='Delete Route' id='replicateFreight-"+1+"'><i class='icon-retweet'></i></a> -->
														<!-- 													<a class='btn_table' href='#' title='Delete Route' id='replicateFreight-"+1+"'><i class='icon-trash'></i></a> -->
														<!-- 												</td> -->
														<!-- 											</tr> -->

														<!-- 											<tr> -->
														<!-- 												<td>3</td> -->
														<!-- 												<td><input type="text" class="input-block-level" id="freight-others-destination" value="Documentación moneda local"></td> -->
														<!-- 												<td><input type="text" class="input-block-level" id="freight-others-destination" style="text-align: right; padding-right: 2px;"></td> -->

														<!-- 												<td> -->
														<!-- 													<a class='btn_table' href='#' title='Delete Route' id='replicateFreight-"+1+"'><i class='icon-retweet'></i></a> -->
														<!-- 													<a class='btn_table' href='#' title='Delete Route' id='replicateFreight-"+1+"'><i class='icon-trash'></i></a> -->
														<!-- 												</td> -->
														<!-- 											</tr> -->

														<!-- 											<tr> -->
														<!-- 												<td>4</td> -->
														<!-- 												<td><input type="text" class="input-block-level" id="freight-others-destination" value="Movilización"></td> -->
														<!-- 												<td><input type="text" class="input-block-level" id="freight-others-destination" style="text-align: right; padding-right: 2px;"></td> -->

														<!-- 												<td> -->
														<!-- 													<a class='btn_table' href='#' title='Delete Route' id='replicateFreight-"+1+"'><i class='icon-retweet'></i></a> -->
														<!-- 													<a class='btn_table' href='#' title='Delete Route' id='replicateFreight-"+1+"'><i class='icon-trash'></i></a> -->
														<!-- 												</td> -->
														<!-- 											</tr> -->
													</tbody>
												</table>

											</div>
										</div>

										<!-- 										<table class="table table-hover" id="table-setup-temp-storage"> -->
										<!-- 											<thead> -->
										<!-- 												<tr> -->
										<!-- 													<th>Entry</th> -->
										<!-- 													<th>Amount the cost %</th> -->
										<!-- 													<th>Processing fee</th> -->
										<!-- 												</tr> -->
										<!-- 											</thead> -->
										<!-- 											<tbody  id="table-setup-temp-storage-tbody"> -->
										<!-- 											</tbody> -->
										<!-- 										</table> -->

									</div>







									<div class="tab-pane" id="nav-setup-local-transport">

										<ul class="nav nav-tabs" id="nav-localtransport">
											<li class="active"><a href="#tab-localtransport-routes">Percentual
													rates</a></li>
											<li><a href="#tab-localtransport-ranges">Weight
													Ranges</a></li>
											<li><a href="#tab-localtransport-concepts">Local
													Transport fees</a></li>
										</ul>

										<div class="tab-content">

											<div class="tab-pane active" id="tab-localtransport-routes">


												<table class="table table-hover t-f-h-localtransport-routes">
													<thead class="header">
														<tr>
															<th>ID Route</th>
															<th>Description Route</th>
															<th>Rate CIF (%)</th>
															<th>Action</th>
														</tr>
													</thead>
													<tbody id="route-localtransport-tbody">

														<!-- 														<tr> -->
														<!-- 															<td>1</td> -->
														<!-- 															<td><input type="text" id="temp-storage-range-rate1" class="input-block-level" value="Route 01" ></td> -->
														<!-- 															<td><input type="text" id="temp-storage-range-rate1" class="input-block-level" value="0,22" style="text-align: right; padding-right: 2px;"></td> -->
														<!-- 															<td> -->
														<!-- 																<a class='btn_table' href='#' title='Delete Route' id='replicateFreight-"+1+"'><i class='icon-retweet'></i></a> -->
														<!-- 																<a class='btn_table' href='#' title='Delete Route' id='replicateFreight-"+1+"'><i class='icon-trash'></i></a> -->
														<!-- 															</td> -->

														<!-- 														</tr> -->

														<!-- 														<tr> -->
														<!-- 															<td>2</td> -->
														<!-- 															<td><input type="text" id="temp-storage-range-rate1" class="input-block-level" value="Route 02" ></td> -->
														<!-- 															<td><input type="text" id="temp-storage-range-rate1" class="input-block-level" value="0,22" style="text-align: right; padding-right: 2px;"></td> -->
														<!-- 															<td> -->
														<!-- 																<a class='btn_table' href='#' title='Delete Route' id='replicateFreight-"+1+"'><i class='icon-retweet'></i></a> -->
														<!-- 																<a class='btn_table' href='#' title='Delete Route' id='replicateFreight-"+1+"'><i class='icon-trash'></i></a> -->
														<!-- 															</td> -->

														<!-- 														</tr> -->

														<!-- 														<tr> -->
														<!-- 															<td>3</td> -->
														<!-- 															<td><input type="text" id="temp-storage-range-rate1" class="input-block-level" value="Route 03 " ></td> -->
														<!-- 															<td><input type="text" id="temp-storage-range-rate1" class="input-block-level" value="0,22" style="text-align: right; padding-right: 2px;"></td> -->
														<!-- 															<td> -->
														<!-- 																<a class='btn_table' href='#' title='Delete Route' id='replicateFreight-"+1+"'><i class='icon-retweet'></i></a> -->
														<!-- 																<a class='btn_table' href='#' title='Delete Route' id='replicateFreight-"+1+"'><i class='icon-trash'></i></a> -->
														<!-- 															</td> -->

														<!-- 														</tr> -->
													</tbody>
												</table>
											</div>

											<div class="tab-pane" id="tab-localtransport-ranges">

												<table class="table table-hover t-f-h-localtransport-ranges">
													<thead class="header">
														<tr>
															<th>Kg. Range from</th>
															<th>Kg. Range to</th>
															<th>Kg. Fee</th>
															<th>Currency</th>
															<th>Action</th>
														</tr>
													</thead>

													<tbody id="ranges-localtransport-tbody">
														<!-- 														<tr> -->
														<!-- 															<td><input type="text" class="input-block-level" id="freight-others-destination" value="1" style="text-align: right; padding-right: 2px;"></td> -->
														<!-- 															<td><input type="text" class="input-block-level" id="freight-others-destination" value="150" style="text-align: right; padding-right: 2px;"></td> -->
														<!-- 															<td><input type="text" class="input-block-level" id="freight-others-destination" value="1.225" style="text-align: right; padding-right: 2px;"></td> -->

														<!-- 															<td> -->
														<!-- 																<a class='btn_table' href='#' title='Delete Route' id='replicateFreight-"+1+"'><i class='icon-retweet'></i></a> -->
														<!-- 																<a class='btn_table' href='#' title='Delete Route' id='replicateFreight-"+1+"'><i class='icon-trash'></i></a> -->
														<!-- 															</td> -->
														<!-- 														</tr> -->

														<!-- 														<tr> -->
														<!-- 															<td><input type="text" class="input-block-level" id="freight-others-destination" value="151" style="text-align: right; padding-right: 2px;"></td> -->
														<!-- 															<td><input type="text" class="input-block-level" id="freight-others-destination" value="300" style="text-align: right; padding-right: 2px;"></td> -->
														<!-- 															<td><input type="text" class="input-block-level" id="freight-others-destination" value="1.225" style="text-align: right; padding-right: 2px;"></td> -->

														<!-- 															<td> -->
														<!-- 																<a class='btn_table' href='#' title='Delete Route' id='replicateFreight-"+1+"'><i class='icon-retweet'></i></a> -->
														<!-- 																<a class='btn_table' href='#' title='Delete Route' id='replicateFreight-"+1+"'><i class='icon-trash'></i></a> -->
														<!-- 															</td> -->
														<!-- 														</tr> -->

														<!-- 														<tr> -->
														<!-- 															<td><input type="text" class="input-block-level" id="freight-others-destination" value="301" style="text-align: right; padding-right: 2px;"></td> -->
														<!-- 															<td><input type="text" class="input-block-level" id="freight-others-destination" value="450" style="text-align: right; padding-right: 2px;"></td> -->
														<!-- 															<td><input type="text" class="input-block-level" id="freight-others-destination" value="1.225" style="text-align: right; padding-right: 2px;"></td> -->

														<!-- 															<td> -->
														<!-- 																<a class='btn_table' href='#' title='Delete Route' id='replicateFreight-"+1+"'><i class='icon-retweet'></i></a> -->
														<!-- 																<a class='btn_table' href='#' title='Delete Route' id='replicateFreight-"+1+"'><i class='icon-trash'></i></a> -->
														<!-- 															</td> -->
														<!-- 														</tr> -->

														<!-- 														<tr> -->
														<!-- 															<td><input type="text" class="input-block-level" id="freight-others-destination" value="451" style="text-align: right; padding-right: 2px;"></td> -->
														<!-- 															<td><input type="text" class="input-block-level" id="freight-others-destination" value="700" style="text-align: right; padding-right: 2px;"></td> -->
														<!-- 															<td><input type="text" class="input-block-level" id="freight-others-destination" value="1.225" style="text-align: right; padding-right: 2px;"></td> -->

														<!-- 															<td> -->
														<!-- 																<a class='btn_table' href='#' title='Delete Route' id='replicateFreight-"+1+"'><i class='icon-retweet'></i></a> -->
														<!-- 																<a class='btn_table' href='#' title='Delete Route' id='replicateFreight-"+1+"'><i class='icon-trash'></i></a> -->
														<!-- 															</td> -->
														<!-- 														</tr> -->

													</tbody>
												</table>

											</div>

											<div class="tab-pane" id="tab-localtransport-concepts">

												<table
													class="table table-hover t-f-h-localtransport-concepts">
													<thead class="header">
														<tr>
															<th>Id Concept</th>
															<th>Description</th>
															<th>Amount</th>
															<th>Currency</th>
															<th>Action</th>
														</tr>
													</thead>

													<tbody id="concept-cost-localtransport">

														<!-- 														<tr> -->
														<!-- 															<td>1</td> -->
														<!-- 															<td><input type="text" class="input-block-level" id="freight-others-destination" value="Salvaguarda moneda local"></td> -->
														<!-- 															<td><input type="text" class="input-block-level" id="freight-others-destination" style="text-align: right; padding-right: 2px;"></td> -->
														<%-- 															<td><select><option>Choice currency</option></select></td> --%>

														<!-- 															<td> -->
														<!-- 																<a class='btn_table' href='#' title='Delete Route' id='replicateFreight-"+1+"'><i class='icon-retweet'></i></a> -->
														<!-- 																<a class='btn_table' href='#' title='Delete Route' id='replicateFreight-"+1+"'><i class='icon-trash'></i></a> -->
														<!-- 															</td> -->
														<!-- 														</tr> -->

														<!-- 														<tr> -->
														<!-- 															<td>2</td> -->
														<!-- 															<td><input type="text" class="input-block-level" id="freight-others-destination" value="Manipuleo moneda local"></td> -->
														<!-- 															<td><input type="text" class="input-block-level" id="freight-others-destination" style="text-align: right; padding-right: 2px;"></td> -->
														<%-- 															<td><select><option>Choice currency</option></select></td> --%>

														<!-- 															<td> -->
														<!-- 																<a class='btn_table' href='#' title='Delete Route' id='replicateFreight-"+1+"'><i class='icon-retweet'></i></a> -->
														<!-- 																<a class='btn_table' href='#' title='Delete Route' id='replicateFreight-"+1+"'><i class='icon-trash'></i></a> -->
														<!-- 															</td> -->
														<!-- 														</tr> -->

														<!-- 														<tr> -->
														<!-- 															<td>3</td> -->
														<!-- 															<td><input type="text" class="input-block-level" id="freight-others-destination" value="Documentación moneda local"></td> -->
														<!-- 															<td><input type="text" class="input-block-level" id="freight-others-destination" style="text-align: right; padding-right: 2px;"></td> -->
														<%-- 															<td><select><option>Choice currency</option></select></td> --%>

														<!-- 															<td> -->
														<!-- 																<a class='btn_table' href='#' title='Delete Route' id='replicateFreight-"+1+"'><i class='icon-retweet'></i></a> -->
														<!-- 																<a class='btn_table' href='#' title='Delete Route' id='replicateFreight-"+1+"'><i class='icon-trash'></i></a> -->
														<!-- 															</td> -->
														<!-- 														</tr> -->

														<!-- 														<tr> -->
														<!-- 															<td>4</td> -->
														<!-- 															<td><input type="text" class="input-block-level" id="freight-others-destination" value="Movilización"></td> -->
														<!-- 															<td><input type="text" class="input-block-level" id="freight-others-destination" style="text-align: right; padding-right: 2px;"></td> -->
														<%-- 															<td><select><option>Choice currency</option></select></td> --%>

														<!-- 															<td> -->
														<!-- 																<a class='btn_table' href='#' title='Delete Route' id='replicateFreight-"+1+"'><i class='icon-retweet'></i></a> -->
														<!-- 																<a class='btn_table' href='#' title='Delete Route' id='replicateFreight-"+1+"'><i class='icon-trash'></i></a> -->
														<!-- 															</td> -->
														<!-- 														</tr> -->
													</tbody>
												</table>

											</div>

										</div>


										<!-- 										<fieldset > -->
										<!-- 											<div class="column_form"> -->
										<!-- 												<label class="control-label">Weight range:</label> -->
										<%-- 												<select id="local-transport-range"> --%>
										<%-- 												</select> --%>
										<!-- 											</div> -->
										<!-- 											<div class="column_form"> -->

										<!-- 												<label class="control-label">Range from:</label> -->
										<!-- 												<input type="text" id="local-transport-range-from" class="input-block-level" value="0" style="text-align: right; padding-right: 2px;"> -->

										<!-- 												<label class="control-label">Range up:</label> -->
										<!-- 												<input type="text" id="local-transport-range-up" class="input-block-level" value="799" style="text-align: right;padding-right: 2px;"> -->

										<!-- 												<label class="control-label">Kg. Rate:</label> -->
										<!-- 												<input type="text" id="local-transport-amount" class="input-block-level" value="0,22" style="text-align: right;padding-right: 2px;"> -->

										<!-- 												<label class="control-label">Currency Kg. Rate:</label> -->
										<%-- 												<select id="local-transport-currency"></select> --%>
										<!-- 											</div> -->

										<!-- <!-- 											<div style="clear: both;"></div> -->
										<!-- 										</fieldset> -->

										<!-- 										<br> -->
										<!-- 										<div class="well"></div> -->


										<!-- 										<table class="table table-hover" id="table-setup-duties"> -->
										<!-- 											<thead> -->
										<!-- 												<tr> -->
										<!-- 													<th>Entry</th> -->
										<!-- 													<th>Amount the cost %</th> -->
										<!-- 													<th>Processing fee</th> -->
										<!-- 												</tr> -->
										<!-- 											</thead> -->
										<!-- 											<tbody  id="table-local-transport-storage-tbody"> -->
										<!-- 											</tbody> -->
										<!-- 										</table> -->

									</div>

									<div class="tab-pane" id="nav-setup-custody">
										<fieldset>

											<div class="column_form">

												<label class="control-label">Base Line Amount(USD):</label>
												<input type="text" id="base-line-custody"
													class="input-block-level" value=""
													style="text-align: right; padding-right: 2px;"> <label
													class="control-label">Security Escort Service fee:</label>
												<input type="text" id="fee-cost-custody"
													class="input-block-level" value=""
													style="text-align: right; padding-right: 2px;"> <label
													class="control-label">Currency:</label> <select
													id="currency-custody"></select>

											</div>

										</fieldset>

										<br>
										<div class="well"></div>

									</div>



								</div>

							</div>


							<div class="tab-pane" id="tab-freight">
								<ul class="nav nav-tabs" id="freights-tab">
									<li class="active"><a href="#freight-aerial">Airfreight</a></li>
									<li><a href="#freight-marine">Containerized freight</a></li>
									<li><a href="#freight-APO">Active Pack Out Freight</a></li>
								</ul>


								<div class="tab-content">
									<div class="tab-pane active" id="freight-aerial">

										<table class="table table-hover t-f-h-freight-aerial"
											id="table-freight-air">
											<thead class="header">
												<tr>
													<th>Currency</th>
													<th>Description</th>
													<th>Intl. Freignt <br>%Increase
													</th>
													<th>AWB Kg. Rates</th>
													<th>AWB kg fee<br>%Increase
													</th>
													<th>Other FFW fees</th>
													<th>Other FFW fees<br>%Increase
													</th>
													<th>Fuel Surcharge (FSC)</th>
													<th>FSC<br>%Increase
													</th>
													<th>Security Charge (SCC)</th>
													<th>SCC <br>%Increase
													</th>
													<th>Consolidation Orders by Shipment</th>
													<th>Action</th>
												</tr>
											</thead>
											<tbody id="table-freight-air-body">

											</tbody>

										</table>

									</div>


									<div class="tab-pane" id="freight-marine">

										<table class="table table-hover t-f-h-freight-marine">
											<thead class="header">
												<tr>
													<th>id Route Freigh</th>
													<th>Type Freigh</th>
													<th>Increase %</th>
													<th>Description Freight</th>
													<th>Currency Freight</th>
													<th>Palettes</th>
													<th>Consolidation Orders Number</th>
													<th style="text-align: right;">Amount Concepts</th>
													<th>Action</th>
												</tr>
											</thead>
											<tbody id="table-freight-others-body"></tbody>
										</table>

									</div>

									<div class="tab-pane" id="freight-APO">

										<table class="table table-hover t-f-h-freight-APO">
											<thead class="header">
												<tr>
													<!-- 													<th>ID Freight</th> -->
													<th>Currency</th>
													<th>Description</th>
													<th>Intl. Freignt <br>%Increase
													</th>
													<th>AWB Kg. Rates</th>
													<th>AWB kg fee<br>%Increase
													</th>
													<th>Other FFW fees</th>
													<th>Other FFW fees<br>%Increase
													</th>
													<th>Fuel Surcharge (FSC)</th>
													<th>FSC<br>%Increase
													</th>
													<th>Security Charge (SCC)</th>
													<th>SCC <br>%Increase
													</th>
													<th>Consolidation Orders by Shipment</th>
													<th>Palettes</th>
													<th>Action</th>
												</tr>
											</thead>
											<tbody id="table-freight-APO-body">

												<!-- 												<tr> -->
												<!-- 													<td>1</td> -->
												<%-- 													<td><select id="FOT_ID_CURRENCY-1"><option value="1">EUR</option><option value="2">SIN</option><option value="3">CAN</option><option value="4">GBP</option><option value="5">AUS</option><option value="6">USD</option></select></td> --%>
												<!-- 													<td><input type="text" id="local-transport-amount" class="input-block-level" value="Air Freight Container BR"></td> -->
												<!-- 													<td><input type="text" id="local-transport-amount" class="input-block-level" value="0,22" style="text-align: right;padding-right: 2px;"></td> -->
												<!-- 													<td><input type="text" id="local-transport-amount" class="input-block-level" value="0,22" style="text-align: right;padding-right: 2px;"></td> -->
												<!-- 													<td><input type="text" id="local-transport-amount" class="input-block-level" value="0,22" style="text-align: right;padding-right: 2px;"></td> -->
												<!-- 													<td><input type="text" id="local-transport-amount" class="input-block-level" value="0,22" style="text-align: right;padding-right: 2px;"></td> -->
												<!-- 													<td><input type="text" id="local-transport-amount" class="input-block-level" value="0,22" style="text-align: right;padding-right: 2px;"></td> -->
												<!-- 													<td><input type="text" id="local-transport-amount" class="input-block-level" value="0,22" style="text-align: right;padding-right: 2px;"></td> -->
												<!-- 													<td><input type="text" id="local-transport-amount" class="input-block-level" value="0,22" style="text-align: right;padding-right: 2px;"></td> -->
												<!-- 													<td> -->
												<!-- 														<a class='btn_table' href='#' title='Replicate Operative Activity' id='replicateOperativeAct-"+listOpeManageVO[j].somID+"'><i class='icon-retweet'></i></a> -->
												<!-- 														<a class='btn_table' href='#' title='Delete Operative Activity'    id='deleteOperativeAct-"+listOpeManageVO[j].somID+"'><i class='icon-trash'></i></a> -->
												<!-- 														<a class='btn_table' href='#' title='Delete Operative Activity'    id='btnAPOFreightConcepts'><i class='icon-plus'></i></a> -->
												<!-- 													</td> -->
												<!-- 												</tr> -->


												<!-- 												<tr> -->
												<!-- 													<td>2</td> -->
												<%-- 													<td><select id="FOT_ID_CURRENCY-1"><option value="1">EUR</option><option value="2">SIN</option><option value="3">CAN</option><option value="4">GBP</option><option value="5">AUS</option><option value="6">USD</option></select></td> --%>
												<!-- 													<td><input type="text" id="local-transport-amount" class="input-block-level" value="Air Freight Container CO"></td> -->
												<!-- 													<td><input type="text" id="local-transport-amount" class="input-block-level" value="0,22" style="text-align: right;padding-right: 2px;"></td> -->
												<!-- 													<td><input type="text" id="local-transport-amount" class="input-block-level" value="0,22" style="text-align: right;padding-right: 2px;"></td> -->
												<!-- 													<td><input type="text" id="local-transport-amount" class="input-block-level" value="0,22" style="text-align: right;padding-right: 2px;"></td> -->
												<!-- 													<td><input type="text" id="local-transport-amount" class="input-block-level" value="0,22" style="text-align: right;padding-right: 2px;"></td> -->
												<!-- 													<td><input type="text" id="local-transport-amount" class="input-block-level" value="0,22" style="text-align: right;padding-right: 2px;"></td> -->
												<!-- 													<td><input type="text" id="local-transport-amount" class="input-block-level" value="0,22" style="text-align: right;padding-right: 2px;"></td> -->
												<!-- 													<td><input type="text" id="local-transport-amount" class="input-block-level" value="0,22" style="text-align: right;padding-right: 2px;"></td> -->
												<!-- 													<td> -->
												<!-- 														<a class='btn_table' href='#' title='Replicate Operative Activity' id='replicateOperativeAct-"+listOpeManageVO[j].somID+"'><i class='icon-retweet'></i></a> -->
												<!-- 														<a class='btn_table' href='#' title='Delete Operative Activity'    id='deleteOperativeAct-"+listOpeManageVO[j].somID+"'><i class='icon-trash'></i></a> -->
												<!-- 														<a class='btn_table' href='#' title='Delete Operative Activity'    id='btnAPOFreightConcepts'><i class='icon-plus'></i></a> -->
												<!-- 													</td> -->
												<!-- 												</tr> -->


												<!-- 												<tr> -->
												<!-- 													<td>3</td> -->
												<%-- 													<td><select id="FOT_ID_CURRENCY-1"><option value="1">EUR</option><option value="2">SIN</option><option value="3">CAN</option><option value="4">GBP</option><option value="5">AUS</option><option value="6">USD</option></select></td> --%>
												<!-- 													<td><input type="text" id="local-transport-amount" class="input-block-level" value="Air Freight Container CL"></td> -->
												<!-- 													<td><input type="text" id="local-transport-amount" class="input-block-level" value="0,22" style="text-align: right;padding-right: 2px;"></td> -->
												<!-- 													<td><input type="text" id="local-transport-amount" class="input-block-level" value="0,22" style="text-align: right;padding-right: 2px;"></td> -->
												<!-- 													<td><input type="text" id="local-transport-amount" class="input-block-level" value="0,22" style="text-align: right;padding-right: 2px;"></td> -->
												<!-- 													<td><input type="text" id="local-transport-amount" class="input-block-level" value="0,22" style="text-align: right;padding-right: 2px;"></td> -->
												<!-- 													<td><input type="text" id="local-transport-amount" class="input-block-level" value="0,22" style="text-align: right;padding-right: 2px;"></td> -->
												<!-- 													<td><input type="text" id="local-transport-amount" class="input-block-level" value="0,22" style="text-align: right;padding-right: 2px;"></td> -->
												<!-- 													<td><input type="text" id="local-transport-amount" class="input-block-level" value="0,22" style="text-align: right;padding-right: 2px;"></td> -->
												<!-- 													<td> -->
												<!-- 														<a class='btn_table' href='#' title='Replicate Operative Activity' id='replicateOperativeAct-"+listOpeManageVO[j].somID+"'><i class='icon-retweet'></i></a> -->
												<!-- 														<a class='btn_table' href='#' title='Delete Operative Activity'    id='deleteOperativeAct-"+listOpeManageVO[j].somID+"'><i class='icon-trash'></i></a> -->
												<!-- 														<a class='btn_table' href='#' title='Delete Operative Activity'    id='btnAPOFreightConcepts'><i class='icon-plus'></i></a> -->
												<!-- 													</td> -->
												<!-- 												</tr> -->


											</tbody>
										</table>

									</div>

								</div>

							</div>
						</div>
					</fieldset>
				</form>
			</div>

		</div>

		<jsp:directive.include file="/pages/templates/footer_body.jsp" />
	</div>


	<!-- Dialog Confirm -->
	<div id="alert-confirm" class="modal hide fade" tabindex="-1"
		style="left: 50%">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h3 id="myModalLabel">Action Confirm</h3>
		</div>
		<div class="modal-body">
			<p id="alert-confirm-content"></p>
		</div>
		<div class="modal-footer">
			<button class="btn" data-dismiss="modal">Cancel</button>
			<button id="alert-confirm-acept" class="btn btn-success">Confirm</button>
		</div>
	</div>


	<div id="alert-errors" class="modal hide fade" tabindex="-1"
		style="left: 50%">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h3 id="myModalLabel">Error reporting</h3>
		</div>
		<div class="modal-body">
			<p id="alert-errors-conten">
				<strong>You have the follow errors:</strong>
			</p>
			<p id="alert-errors-content"></p>
		</div>
		<div class="modal-footer">
			<button id="alert-errors-acept" class="btn btn-success"
				data-dismiss="modal">OK</button>
		</div>
	</div>


	<div id="alert-message" class="modal hide fade" tabindex="-1"
		style="left: 50%">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h3 id="myModalLabel">Success Response</h3>
		</div>
		<div class="modal-body">
			<p id="alert-message-conten">
				<strong>You have the follow notifiction:</strong>
			</p>
			<p id="alert-message-content"></p>
		</div>
		<div class="modal-footer">
			<button id="alert-message-acept" class="btn btn-success"
				data-dismiss="modal">OK</button>
		</div>
	</div>



	<div class="alert alert-success " id="alert-success"
		style="width: 400px; display: none; margin: 0 auto;">
		<button type="button" class="close" data-dismiss="alert">×</button>
		<h4>Success!</h4>
		<p id="alert-success-content"></p>
	</div>


	<!-- Loading DIV -->
	<div id="modal-loading" class="modal hide fade" tabindex="999999"
		style="width: 100px; height: 110px; margin: 0 auto; top: 40%; left: 47%;">

		<div class="modal-header" style="height: 20px;">
			<h2 id="myModalLabel" style="font-size: 15px; line-height: 0px;">Loading...</h2>
		</div>
		<div class="modal-body" style="text-align: center;">
			<img alt="" src="img/loading.gif" style="width: 40px; height: 40px;">
		</div>
	</div>



	<!-- 	<div id="modal-freight-others-costs" class="modal hide fade" tabindex="-1"  style="width: 850px"> -->
	<!-- 		<div class="modal-header"> -->
	<!-- 			<button type="button" class="close" data-dismiss="modal">×</button> -->
	<!-- 			<h3 id="myModalLabel">Add/Remove Cost</h3> -->
	<!-- 		</div> -->
	<!-- 		<div class="modal-body"> -->
	<%-- 			<p id="alert-errors-conten"><strong>Add Or Remove Cost to route:</strong></p> --%>

	<!-- 				<form action="home.action" class="form_general" > -->


	<!-- 					<a class='btn_table' href='#' title='Others Costs' id='aaa'><i class='icon-plus'></i></a> -->
	<!-- 					<fieldset > -->

	<!-- 						<table class="table table-hover"  id="table-import-units" > -->
	<!-- 							<thead> -->
	<!-- 								<tr> -->
	<!-- 									<th>Cost Description</th> -->
	<!-- 									<th>Amount</th> -->
	<!-- 									<th>Currency</th> -->
	<!-- 									<th>Action</th> -->
	<!-- 								</tr> -->

	<!-- 								<tr> -->
	<!-- 									<td><input type="text" value="Active Container Lease"></td> -->
	<!-- 									<td><input type="text" value="1500"></td> -->
	<!-- 									<td> -->
	<%-- 										<select id="currency-freight-other-costs"> --%>
	<!-- 					  						<option value="0">USD</option> -->
	<!-- 					  						<option value="1">CLP</option> -->
	<%-- 					  					</select> --%>
	<!-- 									</td> -->
	<!-- 									<td><a class='btn_table' href='#' title='Others Costs' id='aaa'><i class='icon-remove'></i></a></td> -->
	<!-- 								</tr> -->


	<!-- 								<tr> -->
	<!-- 									<td><input type="text" value="Origin Inland Transport to Origin Airport"></td> -->
	<!-- 									<td><input type="text" value="223"></td> -->
	<!-- 									<td> -->
	<%-- 										<select id="currency-freight-other-costs"> --%>
	<!-- 					  						<option value="0">USD</option> -->
	<!-- 					  						<option value="1">CLP</option> -->
	<%-- 					  					</select> --%>
	<!-- 									</td> -->
	<!-- 									<td><a class='btn_table' href='#' title='Others Costs' id='aaa'><i class='icon-remove'></i></a></td> -->
	<!-- 								</tr> -->


	<!-- 								<tr> -->
	<!-- 									<td><input type="text" value="Export Customs (per shipment) "></td> -->
	<!-- 									<td><input type="text" value="258"></td> -->
	<!-- 									<td> -->
	<%-- 										<select id="currency-freight-other-costs"> --%>
	<!-- 					  						<option value="0">USD</option> -->
	<!-- 					  						<option value="1" selected="selected">CLP</option> -->
	<%-- 					  					</select> --%>
	<!-- 									</td> -->
	<!-- 									<td><a class='btn_table' href='#' title='Others Costs' id='aaa'><i class='icon-remove'></i></a></td> -->
	<!-- 								</tr> -->
	<!-- 							</thead> -->
	<!-- 							<tbody id="table-import-units-tbody"> -->
	<!-- 							</tbody> -->
	<!-- 						</table> -->

	<!-- 					</fieldset> -->
	<!-- 				</form> -->
	<!-- 		</div> -->
	<!-- 		<div class="modal-footer"> -->
	<!-- 			<button id="btnImportUnitFilter" class="btn btn-success" data-dismiss="modal">Filter</button> -->
	<!-- 		</div> -->
	<!-- 	</div> -->



	<div id="modal-search-products" class="modal hide fade" tabindex="-1"
		style="width: 850px">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h3 id="myModalLabel">Filter Product Import Units</h3>
		</div>
		<div class="modal-body">
			<p id="alert-errors-conten">
				<strong>Select the follow products and add:</strong>
			</p>

			<form action="home.action" class="form_general">

				<fieldset>

					<div class="column_form">

						<!-- 							<label class="control-label">Transport:</label> -->
						<%-- 							<select id="filterTransport"> --%>
						<!-- 							  <option value="0">All Transport</option> -->
						<%-- 							<s:iterator value="#session.listProductTransportsSession"> --%>
						<%--       							<option value="<s:property value="idGeneralCombo"/>"><s:property value="nameGeneralCombo"/></option> --%>
						<%-- 							</s:iterator> --%>
						<%-- 							</select> --%>

						<label class="control-label">Int. Route:</label> <select
							id="filterSiteSource">
							<option value="0">All Site Source</option>
							<s:iterator value="#session.listSiteSourceSession">
								<option value="<s:property value="idGeneralCombo"/>"><s:property
										value="nameGeneralCombo" /></option>
							</s:iterator>
						</select> <label class="control-label">Presentation:</label> <select
							id="filterPresentation">

							<option value="0">All Presentation</option>
							<s:iterator value="#session.listProductPresentationSession">
								<option value="<s:property value="idGeneralCombo"/>"><s:property
										value="nameGeneralCombo" /></option>
							</s:iterator>
						</select> <label class="control-label">Presentation:</label> <select
							id="filterTrade">

							<option value="0">All Trade</option>
							<s:iterator value="#session.listProductTradeSession">
								<option value="<s:property value="idGeneralCombo"/>"><s:property
										value="nameGeneralCombo" /></option>
							</s:iterator>
						</select>

					</div>

					<div class="column_form">

						<label class="control-label">Family:</label> <select
							id="filterFamily">

							<option value="0">All Family:</option>
							<s:iterator value="#session.listProductFamiliesSession">
								<option value="<s:property value="idGeneralCombo"/>"><s:property
										value="nameGeneralCombo" /></option>
							</s:iterator>
						</select> <label class="control-label">Type:</label> <select
							id="filterType">

							<option value="0">All Type:</option>
							<s:iterator value="#session.listProductTypesSession">
								<option value="<s:property value="idGeneralCombo"/>"><s:property
										value="nameGeneralCombo" /></option>
							</s:iterator>
						</select> <label class="control-label">Type Load:</label> <select
							id="filterTypeLoad">

							<option value="0">All Type Load</option>
							<s:iterator value="#session.listProductTypeLoadSession">
								<option value="<s:property value="idGeneralCombo"/>"><s:property
										value="nameGeneralCombo" /></option>
							</s:iterator>
						</select> <label class="control-label">Custom Duty:</label> <select
							id="filterCustomDuty">
							<option value='0'>All Custom Duty</option>
							<s:iterator value="listCustomDuty">
								<option value="<s:property value="idGeneralCombo"/>"><s:property
										value="nameGeneralCombo" /></option>
							</s:iterator>
						</select>
					</div>


					<div class="column_form_center" style="width: 100%">
						<label class="control-label">Product Name</label> <input
							type="text" class="input-block-level" id="filterProduct"
							name="filterProduct">
					</div>

					<div style="clear: both;"></div>
				</fieldset>
			</form>
		</div>
		<div class="modal-footer">
			<button id="btnImportUnitFilter" class="btn btn-success"
				data-dismiss="modal">Filter</button>
		</div>
	</div>



	<!-- 							<div class="column_form"> -->

	<!-- 												<label class="control-label">Container 40' RFER:</label> -->
	<!-- 												<input type="text" class="input-block-level" id="freight-others-container" style="text-align: right; padding-right: 2px;"> -->

	<!-- 												<label class="control-label">flete interno origen:</label> -->
	<!-- 												<input type="text" class="input-block-level" id="freight-others-internal-freight" style="text-align: right; padding-right: 2px;"> -->

	<!-- 												<label class="control-label">Otros:</label> -->
	<!-- 												<input type="text" class="input-block-level" id="freight-others-others-cost" style="text-align: right; padding-right: 2px;"> -->

	<!-- 												<label class="control-label">TCH + cargos destino:</label> -->
	<!-- 												<input type="text" class="input-block-level" id="freight-others-destination" style="text-align: right; padding-right: 2px;"> -->
	<!-- 											</div> -->


	<div id="modal-other-freight-concepts" class="modal hide fade"
		tabindex="-1" style="width: 850px">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h3 id="myModalLabel">Concept Cost Other Freight</h3>
		</div>
		<div class="modal-body">
			<p id="alert-errors-conten">
				<strong>Change, add or delete concepts:</strong>
			</p>

			<form action="home.action" class="form_general">

				<fieldset>

					<table class="table table-hover" id="table-other-freight-concepts">
						<thead>
							<tr>
								<th>Id Concept</th>
								<th>Description</th>
								<th>Amount</th>
								<th>Action</th>
							</tr>
						</thead>

						<tbody id="table-other-freight-concepts-tbody"></tbody>
					</table>



					<div style="clear: both;"></div>
				</fieldset>
			</form>
		</div>
		<div class="modal-footer">
			<button id="concept-other-freight-ok" class="btn btn-success"
				data-dismiss="modal">Ok</button>
		</div>
	</div>


	<div id="modal-other-freight-air-ranges" class="modal hide fade"
		tabindex="-1" style="width: 850px">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h3 id="myModalLabel">Ranges Route Freight</h3>
		</div>
		<div class="modal-body">
			<p id="alert-errors-conten">
				<strong>Add, delete and alter ranges to the route:</strong>
			</p>

			<form action="home.action" class="form_general">

				<fieldset>

					<table class="table table-hover" id="table-freight-air-route">
						<thead>
							<tr>
								<th>Range From</th>
								<th>Range To</th>
								<th>KG. Rate</th>
								<th>Action</th>
							</tr>
						</thead>

						<tbody id="table-freight-air-route-tbody"></tbody>
					</table>



					<div style="clear: both;"></div>
				</fieldset>
			</form>
		</div>
		<div class="modal-footer">
			<button class="btn btn-success" data-dismiss="modal">Ok</button>
		</div>
	</div>


	<div id="modal-APO-freight-concepts" class="modal hide fade"
		tabindex="-1" style="width: 850px">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h3 id="myModalLabel">Concept Cost Active Pack Out Freight</h3>
		</div>
		<div class="modal-body">
			<p id="alert-errors-conten">
				<strong>Change, add or delete concepts:</strong>
			</p>

			<form action="home.action" class="form_general">

				<fieldset>

					<table class="table table-hover">
						<thead>
							<tr>
								<th>Id Concept</th>
								<th>Description</th>
								<th>Amount</th>
								<th>Action</th>
							</tr>
						</thead>

						<tbody id="APO-freight-concepts-tbody">
							<!-- 								<tr> -->
							<!-- 									<td>1</td> -->
							<!-- 									<td><input type="text" id="local-transport-amount" class="input-block-level" value="Container 42 RFER" style="text-align: right;padding-right: 2px;"></td> -->
							<!-- 									<td><input type="text" id="local-transport-amount" class="input-block-level" value="0,22" style="text-align: right;padding-right: 2px;"></td> -->
							<!-- 									<td> -->
							<!-- 										<a class='btn_table' href='#' title='Replicate Operative Activity' id='replicateOperativeAct-"+listOpeManageVO[j].somID+"'><i class='icon-retweet'></i></a> -->
							<!-- 										<a class='btn_table' href='#' title='Delete Operative Activity'    id='deleteOperativeAct-"+listOpeManageVO[j].somID+"'><i class='icon-trash'></i></a> -->
							<!-- 									</td> -->
							<!-- 								</tr> -->

							<!-- 								<tr> -->
							<!-- 									<td>2</td> -->
							<!-- 									<td><input type="text" id="local-transport-amount" class="input-block-level" value="Pick Up" style="text-align: right;padding-right: 2px;"></td> -->
							<!-- 									<td><input type="text" id="local-transport-amount" class="input-block-level" value="0,22" style="text-align: right;padding-right: 2px;"></td> -->
							<!-- 									<td> -->
							<!-- 										<a class='btn_table' href='#' title='Replicate Operative Activity' id='replicateOperativeAct-"+listOpeManageVO[j].somID+"'><i class='icon-retweet'></i></a> -->
							<!-- 										<a class='btn_table' href='#' title='Delete Operative Activity'    id='deleteOperativeAct-"+listOpeManageVO[j].somID+"'><i class='icon-trash'></i></a> -->
							<!-- 									</td> -->
							<!-- 								</tr> -->

							<!-- 								<tr> -->
							<!-- 									<td>3</td> -->
							<!-- 									<td><input type="text" id="local-transport-amount" class="input-block-level" value="Documentation" style="text-align: right;padding-right: 2px;"></td> -->
							<!-- 									<td><input type="text" id="local-transport-amount" class="input-block-level" value="0,22" style="text-align: right;padding-right: 2px;"></td> -->
							<!-- 									<td> -->
							<!-- 										<a class='btn_table' href='#' title='Replicate Operative Activity' id='replicateOperativeAct-"+listOpeManageVO[j].somID+"'><i class='icon-retweet'></i></a> -->
							<!-- 										<a class='btn_table' href='#' title='Delete Operative Activity'    id='deleteOperativeAct-"+listOpeManageVO[j].somID+"'><i class='icon-trash'></i></a> -->
							<!-- 									</td> -->
							<!-- 								</tr> -->

						</tbody>
					</table>



					<div style="clear: both;"></div>
				</fieldset>
			</form>
		</div>
		<div class="modal-footer">
			<button class="btn btn-success" data-dismiss="modal">Ok</button>
		</div>
	</div>

</body>
</html>