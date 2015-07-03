<!DOCTYPE html>
<html lang="en">
<head>
<jsp:directive.include file="/pages/templates/header_html.jsp" />

<script src="js/jquery/highcharts/highcharts.js"></script>
<script src="js/jquery/highcharts/exporting.js"></script>
<script src="js/jquery/highcharts/highcharts-more.js"></script>

<style>
.chart-wrapper {
	position: relative;
	/*     padding-bottom: 40%; */
}

.chart-inner {
	/* 	position: absolute; */
	width: 100%;
	height: 100%;
}
</style>

<script>

function onloadStageExist(){
	
	var idBaseStage = '<%=request.getAttribute("idBaseStage")%>';
	var idNewStage  = '<%=request.getAttribute("idNewStage")%>';
	
	if (idBaseStage != 'null'){
		$('#result-base-stage-id').val(idBaseStage);
	}

	if (idNewStage != 'null'){
		$('#result-new-stage-id').val(idNewStage);
	}
	
	$('#tab-result-cost a[href="#tab-std-detail"]').trigger("click");
}

</script>
</head>
<body onload="onloadStageExist();">
	<script src="js/actions/cost/result_cost.js"></script>

	<div id="wrapper">

		<jsp:directive.include file="/pages/templates/head_body.jsp" />

		<div class="body_page">
			<div class="body_content">
				<form action="home.action" class="form_general">

					<fieldset>

						<legend>Simulator Outputs</legend>

						<div class="column_form" style="width: 250px; margin-right: 10px;">

							<label class="control-label">New Stage (<strong
								id="strong-label-stage-new"><s:property
										value="labelStatusStageNew" /></strong>):
							</label> <select id="result-new-stage-id">
								<s:iterator value="listStageVO">
									<option value="<s:property value="idStage"/>"><s:property
											value="nameStage" /></option>
								</s:iterator>
							</select>


							<!-- 							<label class="control-label">Month:</label> -->

							<!-- 							<select> -->
							<!-- 								<option>Choice Month:</option> -->
							<!-- 								<option>Month 1</option> -->
							<!-- 								<option>Month 2</option> -->
							<!-- 								<option>Month 3</option> -->
							<!-- 								<option>Month 4</option> -->
							<!-- 							</select> -->

							<label class="control-label">Actions Filter:</label> <a
								id="result-stage-filters-refresh" class="btn_table"
								title="Refresh filter"><i class="icon-refresh"></i></a> <a
								id="result-stage-filters" class="btn_table"
								title="Products Filter"><i id="id-filter-btn"
								class="icon-filter"></i></a> <a id="result-stage-download"
								class="btn_table" title="Excel export"><i
								class="icon-download-alt"></i></a> <a id="result-stage-history"
								class="btn_table" title="Comments History"><i
								class="icon-align-justify"></i></a>
						</div>

						<div class="column_form" style="width: 10px; margin-right: 20px;">
							<label class="control-label">VS</label>
						</div>

						<div class="column_form" style="width: 250px; margin-right: 50px;">

							<label class="control-label">Base Stage (<strong
								id="strong-label-stage-base"><s:property
										value="labelStatusStageBase" /></strong>):
							</label> <select id="result-base-stage-id">
								<s:iterator value="listStageVO">
									<option value="<s:property value="idStage"/>"><s:property
											value="nameStage" /></option>
								</s:iterator>
							</select>

							<!-- 							<label class="radio"> -->
							<!-- 								<input type="radio" name="optionsRadios" id="optionsRadios1" value="option1" checked>Month -->
							<!-- 							</label> -->

							<!-- 							<label class="radio"> -->
							<!-- 								<input type="radio" name="optionsRadios" id="optionsRadios1" value="option1" checked>Year to Date -->
							<!-- 							</label> -->
						</div>

						<div style="clear: both;"></div>
					</fieldset>

				</form>

				<div class="well">
					<div class="buttoms" id="divButtoms">

						<s:if test="idStatusStageBase == 2">

							<button class="btn" type="button" id="btnRetry">Retry</button>
							<button class="btn btn-success" type="button" id="btnForecast">Forecast</button>
							<button class="btn btn-success" type="button" id="btnBudget">Budget</button>
						</s:if>


						<!-- 					Forecast En Proceso -->
						<s:if test="idStatusStageBase == 4">

							<button class='btn' type='button' id='btnRetry'>Retry</button>
							<button class='btn btn-success' type='button'
								id='btnToApproveForecast'>To Approve</button>
						</s:if>

						<!-- 					Budget -->
						<s:if test="idStatusStageBase == 7">

							<button class='btn' type='button' id='btnRetry'>Retry</button>
							<button class='btn btn-success' type='button'
								id='btnToApproveBudget'>To Approve</button>
						</s:if>

						<!-- 					Forecast en proceso de aprobación -->
						<s:if test="idStatusStageBase == 9">
							<button class='btn btn-danger' type='button'
								id='btnRejectForecast'>Reject</button>
							<button class='btn btn-success' type='button'
								id='btnApproveForecast'>Approve</button>
						</s:if>

						<!-- 					Budget en proceso de aprobación -->
						<s:if test="idStatusStageBase == 10">
							<button class='btn btn-danger' type='button' id='btnRejectBudget'>Reject</button>
							<button class='btn btn-success' type='button'
								id='btnApproveBudget'>Approve</button>
						</s:if>

					</div>
				</div>

				<ul class="nav nav-tabs" id="tab-result-cost">
					<li><a href="#tab-std-detail">STD Detail</a></li>
					<!-- 					<li><a href="#tab-impact-analysis">Impact Analysis</a></li> -->
					<li><a href="#tab-productivity">Productivity</a></li>
					<li><a href="#tab-summary">Summary</a></li>
					<li><a href="#tab-monthly-data">Monthly Data</a></li>
					<li><a href="#tab-unit-cost">Unit Cost</a></li>

					<li id="loading-local"
						style="text-align: right; width: auto; float: right; display: none;"><div
							style="width: 30px; height: 30px;">
							<img alt="" src="img/loading.gif">
						</div></li>
				</ul>

				<div class="tab-content">

					<div class="tab-pane" id="tab-summary">



						<ul class="nav nav-tabs" id="tab-summary-index">
							<li class="active"><a href="#tab-units">Units</a></li>
							<li><a href="#tab-fob">Trn. Price</a></li>
							<li><a href="#tab-duties">Duties</a></li>
							<li><a href="#tab-freights">Freight & Others</a></li>
						</ul>


						<div class="tab-content">

							<div class="tab-pane active" id="tab-units">

								<!-- 								<div class="column_form"  style="width: 40%;" > -->

								<table class="" style="width: 100%">
									<tr>
										<td style="width: 50%;">
											<table class="table table-hover t-f-h-units"
												id="table-summary-units">
												<thead class="header">
													<tr>
														<th>Month</th>
														<th style="text-align: right;">New Stage</th>
														<th style="text-align: right;">Base Stage</th>
														<th style="text-align: right;">Variance</th>
														<th style="text-align: right;">Year To Date</th>
														<th style="text-align: right;">%</th>
													</tr>
												</thead>
												<tbody id="table-summary-units-tbody"></tbody>

												<tfoot id="table-summary-units-tfoot"></tfoot>
											</table>
										</td>

										<td><div id="container-summary-units"
												style="height: 540px;"></div></td>
									</tr>
								</table>

								<!-- 								</div> -->

								<!-- 								<div  class="column_form" style="width: 45%; overflow: hidden;"> -->
								<!-- 									<div id="container-summary-units" style="height: 540px; "></div>  -->
								<!-- 								</div> -->
							</div>

							<div class="tab-pane" id="tab-fob">
								<!-- 								<div class="column_form"  style="width: 40%;" > -->

								<table class="" style="width: 100%">
									<tr>
										<td style="width: 50%;">

											<table class="table table-hover t-f-h-fob">
												<thead class="header">
													<tr>
														<th>Month</th>
														<th style="text-align: right;">New Stage</th>
														<th style="text-align: right;">Base Stage</th>
														<th style="text-align: right;">Variance</th>
														<th style="text-align: right;">Year To Date</th>
														<th style="text-align: right;">%</th>
													</tr>
												</thead>
												<tbody id="table-summary-fobs-tbody"></tbody>

												<tfoot></tfoot>
											</table>
										</td>

										<td><div id="container-summary-fobs"
												style="height: 540px;"></div></td>
									</tr>
								</table>
								<!-- 								</div> -->

								<!-- 								<div  class="column_form" style="width: 45%; overflow: hidden;"> -->
								<!-- 									<div id="container-summary-fobs" style="height: 540px; "></div>  -->
								<!-- 								</div> -->

							</div>

							<div class="tab-pane" id="tab-duties">
								<!-- 								<div class="column_form"  style="width: 40%;" > -->

								<table class="" style="width: 100%">
									<tr>
										<td style="width: 50%;">
											<table class="table table-hover t-f-h-duties">
												<thead class="header">
													<tr>
														<th>Month</th>
														<th style="text-align: right;">New Stage</th>
														<th style="text-align: right;">Base Stage</th>
														<th style="text-align: right;">Variance</th>
														<th style="text-align: right;">Year To Date</th>
														<th style="text-align: right;">DTE</th>
														<th style="text-align: right;">EX Exchange</th>
														<th style="text-align: right;">%</th>
													</tr>
												</thead>
												<tbody id="table-summary-duties-tbody"></tbody>

												<tfoot></tfoot>
											</table>
										</td>
										<td><div id="container-summary-duties"
												style="height: 540px;"></div></td>
									</tr>
								</table>
								<!-- 								</div> -->

								<!-- 								<div  class="column_form" style="width: 45%; overflow: hidden;"> -->
								<!-- 									<div id="container-summary-duties" style="height: 540px; width: 50% "></div>  -->
								<!-- 								</div> -->

							</div>

							<div class="tab-pane" id="tab-freights">

								<!-- 								<div class="column_form"  style="width: 40%;" > -->
								<table class="" style="width: 100%">
									<tr>
										<td style="width: 50%;">

											<table class="table table-hover t-f-h-freights">
												<thead class="header">
													<tr>
														<th>Month</th>
														<th style="text-align: right;">New Stage</th>
														<th style="text-align: right;">Base Stage</th>
														<th style="text-align: right;">Variance</th>
														<th style="text-align: right;">Year To Date</th>
														<th style="text-align: right;">DTE</th>
														<th style="text-align: right;">EX Exchange</th>
														<th style="text-align: right;">%</th>
													</tr>
												</thead>
												<tbody id="table-summary-freight-tbody"></tbody>

												<tfoot></tfoot>
											</table>
										</td>

										<td><div id="container-summary-freights"
												style="height: 540px;"></div></td>
									</tr>
								</table>

								<!-- 								</div> -->

								<!-- 								<div  class="column_form" style="width: 45%; overflow: hidden;"> -->
								<!-- 									<div id="container-summary-freights" style="height: 540px; width: 50% "></div>  -->
								<!-- 								</div> -->
							</div>

						</div>
					</div>

					<div class="tab-pane" id="tab-impact-analysis">

						<table class="table table-hover">
							<thead>
								<tr>
									<th>Cost Type</th>
									<th>Cost Sub Type</th>
									<th>Cost Detail</th>
									<th style="text-align: right;">New Stage</th>
									<th style="text-align: right;">Base Stage</th>
									<th style="text-align: right;">Impact</th>
								</tr>
							</thead>

							<tbody id="table-impact-analisys-tbody"></tbody>

						</table>
					</div>

					<div class="tab-pane" id="tab-productivity">

						<table class="table table-hover t-f-h-productivity">
							<thead class="header">
								<tr>
									<th colspan="4"></th>
									<th colspan="2"
										style="background-color: #F5F5F5; border: 1px solid rgb(221, 221, 221); text-align: center">New
										Stage (LC)</th>
									<th colspan="2"
										style="background-color: #F5F5F5; border: 1px solid rgb(221, 221, 221); text-align: center">Base
										Stage (LC)</th>
									<th colspan="2"></th>
									<th colspan="3"
										style="background-color: #F5F5F5; border: 1px solid rgb(221, 221, 221); text-align: center">Productivity
										in (US)</th>
								</tr>
								<tr>
									<th>Local Code</th>
									<th>Description</th>
									<th style="text-align: right;">Units New Stage</th>
									<th style="text-align: right;">Units Base Stage</th>
									<th style="text-align: right;">Duties</th>
									<th style="text-align: right;">Freight & Others</th>
									<th style="text-align: right;">Duties</th>
									<th style="text-align: right;">Freight & Others</th>

									<th style="text-align: right;">Inflation</th>
									<th style="text-align: right;">Exchange</th>

									<th style="text-align: right;">Total Duties</th>
									<th style="text-align: right;">Total Freight & Others</th>
									<th style="text-align: right;">Total Local Add</th>
								</tr>
							</thead>
							<tbody id="table-tbody-productivity"></tbody>
						</table>
					</div>

					<div class="tab-pane" id="tab-monthly-data">

						<div class="well"></div>

						<table class="table table table-hover t-f-h-monthly-data"
							id="monthly-data-table">
							<thead class="header">
								<tr>
									<th colspan="2"></th>
									<th colspan="3"
										style="background-color: #F5F5F5; border: 1px solid rgb(221, 221, 221); text-align: center">UNITS</th>
									<th colspan="3"
										style="background-color: #F5F5F5; border: 1px solid rgb(221, 221, 221); text-align: center">TRN.
										PRICE</th>
									<th colspan="6"
										style="background-color: #F5F5F5; border: 1px solid rgb(221, 221, 221); text-align: center">DUTIES</th>
									<th colspan="6"
										style="background-color: #F5F5F5; border: 1px solid rgb(221, 221, 221); text-align: center">FREIGHT
										& OTHERS</th>
								</tr>
								<tr>
									<th>Local Code</th>
									<th>Description</th>

									<th style="text-align: right;">New Stage</th>
									<th style="text-align: right;">Base Stage</th>
									<th style="text-align: right;">Variance</th>

									<th style="text-align: right;">New Stage</th>
									<th style="text-align: right;">Base Stage</th>
									<th style="text-align: right;">Variance</th>

									<th style="text-align: right;">New Stage</th>
									<th style="text-align: right;">Base Stage</th>
									<th style="text-align: right;">Variance</th>
									<th style="text-align: right;">DTE</th>
									<th style="text-align: right;">Ex Exchange</th>
									<th style="text-align: right;">%</th>

									<th style="text-align: right;">New Stage</th>
									<th style="text-align: right;">Base Stage</th>
									<th style="text-align: right;">Variance</th>
									<th style="text-align: right;">DTE</th>
									<th style="text-align: right;">Ex Exchange</th>
									<th style="text-align: right;">%</th>

								</tr>
							</thead>

							<tbody id="monthly-data-table-tbody"></tbody>
						</table>
					</div>




					<div class="tab-pane" id="tab-unit-cost">

						<div class="well"></div>

						<table class="table table-hover t-f-h-unit-cost"
							id="monthly-data-table">
							<thead class="header">
								<tr>
									<th colspan="10"></th>
									<th colspan="4"
										style="background-color: #F5F5F5; border: 1px solid rgb(221, 221, 221); text-align: center">UNITS
										COST 2015 USD</th>
									<th colspan="4"
										style="background-color: #F5F5F5; border: 1px solid rgb(221, 221, 221); text-align: center">UNITS
										COST 2015 LC</th>

								</tr>
								<tr>

									<th>Local Code</th>
									<th>Hub Code</th>
									<th>Product Description</th>

									<th>Local Transport</th>
									<th>Temporal Warehousing</th>

									<th>IVA Imports 1=yes</th>
									<th>Presentation</th>
									<th>Busines</th>
									<th>Shipment Type</th>
									<%--				<th>Item Class</th>				--%>
									<th>Transp Mode</th>



									<th style="text-align: right;">Fob</th>
									<th style="text-align: right;">Duties</th>
									<th style="text-align: right;">Freight</th>
									<th style="text-align: right;">Other</th>

									<th style="text-align: right;">Fob</th>
									<th style="text-align: right;">Duties</th>
									<th style="text-align: right;">Freight</th>
									<th style="text-align: right;">Other</th>
								</tr>
							</thead>

							<tbody id="unit-cost-tbody"></tbody>
						</table>
					</div>



					<div class="tab-pane" id="tab-std-detail">

						<!-- 						<div class="column_form" style="width: 100%; "  > -->



						<!-- 							<div id="container-std-typeCost"    style="height: 500px; width:50%; float: left;"></div> -->
						<!-- 							<div id="container-std-subTypeCost" style="height: 500px; width:50%; float: right;"></div> -->

						<!-- 							<div id="container-std-detail" style="height: 500px; width:100%;"></div> -->


						<table class="" style="width: 100%;">


							<tr>
								<td colspan="2">

									<table class="table table-hover t-f-h-std-detail">
										<thead class="header">
											<tr>
												<th>Cost Type</th>
												<th>Cost Sub Type</th>
												<th>Cost Detail</th>
												<th style="text-align: right;">New Stage</th>
												<th style="text-align: right;">Base Stage</th>
												<th style="text-align: right;">Var Nominal</th>
												<th style="text-align: right;">DTE</th>
												<th style="text-align: right;">EX-EXCHANGE</th>
												<th style="text-align: right;">%</th>
											</tr>
										</thead>
										<tbody id="table-std-detail-tbody"></tbody>
									</table>

								</td>
							</tr>

							<tr>
								<td style="width: 50%;">
									<div id="container-std-forecast-percent" style="height: 500px;"></div>
								</td>
								<td style="width: 50%;">
									<div id="container-std-actual-percent" style="height: 500px;"></div>
								</td>
							</tr>

							<tr>
								<td style="width: 50%;">
									<div id="container-std-typeCost" style="height: 500px;"></div>
								</td>
								<td style="width: 50%;">
									<div id="container-std-subTypeCost" style="height: 500px;"></div>
								</td>
							</tr>

							<tr>
								<td colspan="2">
									<div id="container-std-detail" style="height: 500px;"></div>
								</td>
							</tr>

						</table>

					</div>

				</div>
			</div>

		</div>

		<jsp:directive.include file="/pages/templates/footer_body.jsp" />

	</div>



	<div id="modal-comments-history" class="modal hide fade" tabindex="-1"
		style="width: 850px">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h3 id="myModalLabel">Comments History</h3>
		</div>
		<div class="modal-body">
			<!-- 			<p id="alert-errors-conten"><strong>Enter comment:</strong></p> -->

			<form action="home.action" class="form_general">

				<fieldset>

					<div class="column_form">

						<label class="control-label">Comment Stage:</label>
						<textarea rows="0" cols="" id="commentStage"
							style="height: 55px; width: 600px;"></textarea>

					</div>

					<div style="clear: both;"></div>
				</fieldset>

			</form>

			<div class="well">
				<div class="buttoms" id="divButtoms">
					<button class="btn btn-success" type="button" id="btnAddComment">Add
						Comment</button>
				</div>
			</div>


			<table class="table table-hover">
				<thead>
					<tr>
						<th>User ISID</th>
						<th>Date Comment</th>
						<th>Comment</th>
					</tr>
				</thead>

				<tbody id="table-comments-tbody">

				</tbody>
			</table>
		</div>
		<!-- 		<div class="modal-footer"> -->
		<!-- 			<button id="filter-product-btn" class="btn btn-success" data-dismiss="modal">Filter</button> -->
		<!-- 		</div> -->
	</div>


	<div id="modal-search-products" class="modal hide fade" tabindex="-1"
		style="width: 850px">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h3 id="myModalLabel">Filter Product Import Units</h3>
		</div>
		<div class="modal-body">
			<p id="alert-errors-conten">
				<strong>Select the follow products filters:</strong>
			</p>

			<form action="home.action" class="form_general">

				<fieldset>

					<div class="column_form">

						<!-- 							<label class="control-label">Transport:</label> -->
						<!-- 							<select id="filter-product-id-transport"> -->
						<!-- 							  <option value="">All Transport</option> -->
						<%-- 							<s:iterator value="#session.listProductTransportsSession"> --%>
						<%--       							<option value="<s:property value="idGeneralCombo"/>"><s:property value="nameGeneralCombo"/></option> --%>
						<%-- 							</s:iterator> --%>
						<!-- 							</select> -->

						<label class="control-label">Int. Route:</label> <select
							id="filter-product-id-siteSource">
							<option value="">All Int. Route</option>
							<s:iterator value="#session.listSiteSourceSession">
								<option value="<s:property value="idGeneralCombo"/>"><s:property
										value="nameGeneralCombo" /></option>
							</s:iterator>
						</select> <label class="control-label">Presentation:</label> <select
							id="filter-product-id-presentation">
							<option value="">All Presentation</option>
							<s:iterator value="#session.listProductPresentationSession">
								<option value="<s:property value="idGeneralCombo"/>"><s:property
										value="nameGeneralCombo" /></option>
							</s:iterator>
						</select> <label class="control-label">Trade:</label> <select
							id="filter-product-id-trade">
							<option value="">All Trade</option>
							<s:iterator value="#session.listProductTradeSession">
								<option value="<s:property value="idGeneralCombo"/>"><s:property
										value="nameGeneralCombo" /></option>
							</s:iterator>
						</select>

					</div>

					<div class="column_form">

						<label class="control-label">Family:</label> <select
							id="filter-product-id-family">
							<option value="">All Family:</option>
							<s:iterator value="#session.listProductFamiliesSession">
								<option value="<s:property value="idGeneralCombo"/>"><s:property
										value="nameGeneralCombo" /></option>
							</s:iterator>
						</select> <label class="control-label">Type:</label> <select
							id="filter-product-id-type">

							<option value="">All Type:</option>
							<s:iterator value="#session.listProductTypesSession">
								<option value="<s:property value="idGeneralCombo"/>"><s:property
										value="nameGeneralCombo" /></option>
							</s:iterator>
						</select> <label class="control-label">Type Load:</label> <select
							id="filter-product-id-typeLoad">
							<option value="">All Type Load</option>
							<s:iterator value="#session.listProductTypeLoadSession">
								<option value="<s:property value="idGeneralCombo"/>"><s:property
										value="nameGeneralCombo" /></option>
							</s:iterator>
						</select> <label class="control-label">Custom Duty:</label> <select
							id="filter-product-id-customDuty">
							<option value="">All Custom Duty</option>
							<s:iterator value="listDuties">
								<option value="<s:property value="idGeneralCombo"/>"><s:property
										value="nameGeneralCombo" /></option>
							</s:iterator>

						</select>
					</div>

					<div class="column_form_center" style="width: 100%">
						<label class="control-label">Product Name</label> <input
							type="text" class="input-block-level" id="filter-product-name"
							name="filter-product-name">
					</div>

					<div style="clear: both;"></div>
				</fieldset>
			</form>
		</div>
		<div class="modal-footer">
			<button id="filter-product-btn" class="btn btn-success"
				data-dismiss="modal">Filter</button>
		</div>
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

</body>
</html>