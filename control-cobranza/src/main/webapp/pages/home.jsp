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
</head>

<body>
	<script src="js/actions/home.js"></script>

	<div id="wrapper">
		<jsp:directive.include file="/pages/templates/head_body.jsp" />

		<div class="body_page">
			<div class="body_content">

				<!-- 				<form action="home.action" class="form_general" > -->

				<fieldset>

					<legend>Summary Import Cost Charts</legend>
				</fieldset>


				<br>

				<ul class="nav nav-tabs" id="tab-charts">
					<s:if test="%{#session.isNotDataFound!='true'}">
						<li class="active"><a href="#tab-detail-charts">STD
								Detail Consolidated </a></li>
					</s:if>


					<s:if test="%{#session.isNotDataFound!='true'}">
						<li><a href="#tab-summary-charts">Summary Consolidated </a></li>
					</s:if>

					<li><a href="#tab-home-charts">Home Consolidated Charts</a></li>


					<li id="loading-local"
						style="text-align: right; width: auto; float: right; display: none;"><div
							style="width: 30px; height: 30px;">
							<img alt="" src="img/loading.gif">
						</div></li>
				</ul>

				<div class="tab-content" style="overflow: hidden;">
					<div class="tab-pane" id="tab-home-charts">


						<table class="" style="width: 100%">
							<tr>
								<td style="width: 50%" align="center">
									<table class="">
										<tr>
											<td>
												<h4 style="text-align: center;">
													Actual VS Forecast <br>UNITS
												</h4>
												<div id="container-home-units" style="min-height: 400px;"></div>
											</td>
										</tr>

										<tr>
											<td>
												<table class="table table-hover">
													<thead>
														<tr>
															<th>Family</th>
															<th>Variation</th>
															<th>Tilt</th>
														</tr>
													</thead>
													<tbody id="table-summary-unit-tbody"></tbody>
												</table>
											</td>
										</tr>
									</table>
								</td>

								<td style="width: 50%" align="center">
									<table class="">
										<tr>
											<td>
												<h4 style="text-align: center;">
													Actual VS Forecast <br>Trn. Price
												</h4>
												<div id="container-home-fobs" style="min-height: 400px;"></div>
											</td>
										</tr>

										<tr>
											<td>
												<table class="table table-hover">
													<thead>
														<tr>
															<th>Family</th>
															<th>Variation</th>
															<th>Tilt</th>
														</tr>
													</thead>
													<tbody id="table-summary-fob-tbody"></tbody>
												</table>
											</td>
										</tr>
									</table>
								</td>
							</tr>

							<tr>
								<td colspan="2"><div class="well"></div></td>
							</tr>


							<tr>
								<td style="width: 50%" align="center">
									<table class="">
										<tr>
											<td>
												<h4 style="text-align: center;">
													Actual VS Forecast <br>DUTIES
												</h4>
												<div id="container-home-duties" style="min-height: 400px;"></div>
											</td>
										</tr>

										<tr>
											<td>
												<table class="table table-hover">
													<thead>
														<tr>
															<th>Family</th>
															<th>Variation</th>
															<th>Tilt</th>
														</tr>
													</thead>
													<tbody id="table-summary-duty-tbody"></tbody>
												</table>
											</td>
										</tr>
									</table>
								</td>

								<td style="width: 50%;" align="center">
									<table class="">
										<tr>
											<td>
												<h4 style="text-align: center;">
													Actual VS Forecast <br>Freight & Others
												</h4>
												<div id="container-home-freights" style="min-height: 400px;"></div>
											</td>
										</tr>

										<tr>
											<td>
												<table class="table table-hover">
													<thead>
														<tr>
															<th>Family</th>
															<th>Variation</th>
															<th>Tilt</th>
														</tr>
													</thead>
													<tbody id="table-summary-freight-tbody"></tbody>
												</table>
											</td>
										</tr>
									</table>
								</td>
							</tr>








							<!-- 									<tr> -->
							<!-- 										<td style="width: 50%"><h4 style="text-align: center;" >Actual VS Forecast <br>DUTIES</h4><div id="container-home-duties" style="height: 300px;"></div></td> -->
							<!-- 										<td style="width: 50%"><h4 style="text-align: center;" >Actual VS Forecast <br>Freight & Others</h4><div id="container-home-freights" style="height: 300px;"></div></td>										 -->
							<!-- 									</tr> -->

						</table>

						<!-- 								<div class="column_chart"> -->

						<!-- 									<h4 style="text-align: center;" >Actual VS Forecast <br>UNITS</h4> -->
						<!-- 									<div id="container-home-units" style="height: 300px; width: 100%;"></div> -->

						<!-- 									<table class="table table-hover"> -->
						<!-- 										<thead> -->
						<!-- 											<tr> -->
						<!-- 												<th>Family</th> -->
						<!-- 												<th>Variation</th> -->
						<!-- 												<th>Tilt</th> -->
						<!-- 											</tr> -->
						<!-- 										</thead> -->
						<!-- 										<tbody id="table-summary-unit-tbody"></tbody> -->
						<!-- 									</table> -->




						<!-- 								</div> -->

						<!-- 								<div class="column_chart"> -->
						<!-- 									<h4 style="text-align: center;" >Actual VS Forecast <br>Trn. Price</h4> -->
						<!-- 									<div id="container-home-fobs" style="height: 300px; width: 100%;"></div> -->


						<!-- 									<table class="table table-hover"> -->
						<!-- 										<thead> -->
						<!-- 											<tr> -->
						<!-- 												<th>Family</th> -->
						<!-- 												<th>Variation</th> -->
						<!-- 												<th>Tilt</th> -->
						<!-- 											</tr> -->
						<!-- 										</thead> -->
						<!-- 										<tbody id="table-summary-fob-tbody"></tbody> -->
						<!-- 									</table> -->
						<!-- 								</div> -->

						<!-- 								<div class="column_chart"> -->
						<!-- 									<h4 style="text-align: center;" >Actual VS Forecast <br>DUTIES</h4> -->
						<!-- 									<div id="container-home-duties" style="height: 300px; width: 100%;"></div> -->

						<!-- 									<table class="table table-hover"> -->
						<!-- 										<thead> -->
						<!-- 											<tr> -->
						<!-- 												<th>Family</th> -->
						<!-- 												<th>Variation</th> -->
						<!-- 												<th>Tilt</th> -->
						<!-- 											</tr> -->
						<!-- 										</thead> -->
						<!-- 										<tbody id="table-summary-duty-tbody"></tbody> -->
						<!-- 									</table> -->
						<!-- 								</div> -->

						<!-- 								<div class="column_chart"> -->
						<!-- 									<h4 style="text-align: center;" >Actual VS Forecast <br>Freight & Others</h4> -->
						<!-- 									<div id="container-home-freights" style="height: 300px; width: 100%;"></div> -->

						<!-- 									<table class="table table-hover"> -->
						<!-- 										<thead> -->
						<!-- 											<tr> -->
						<!-- 												<th>Family</th> -->
						<!-- 												<th>Variation</th> -->
						<!-- 												<th>Tilt</th> -->
						<!-- 											</tr> -->
						<!-- 										</thead> -->
						<!-- 										<tbody id="table-summary-freight-tbody"></tbody> -->
						<!-- 									</table> -->
						<!-- 								</div> -->
					</div>


					<div class="tab-pane" id="tab-summary-charts">

						<ul class="nav nav-tabs" id="tab-summary-index">
							<li class="active"><a href="#tab-units">Units</a></li>
							<li><a href="#tab-fob">Trn. Price</a></li>
							<li><a href="#tab-duties">Duties</a></li>
							<li><a href="#tab-freights">Freight & Others</a></li>
						</ul>


						<div class="tab-content">
							<div class="tab-pane active" id="tab-units">

								<!-- 										<div class="column_form"  style="width: 40%;" > -->


								<table class="" style="width: 100%">
									<tr>
										<td style="width: 50%;">
											<table class="table table-hover t-f-h-summary-units"
												id="table-summary-units">
												<thead class="header">
													<tr>
														<th>Month</th>
														<th style="text-align: right;">Actual</th>
														<th style="text-align: right;">Forecast</th>
														<th style="text-align: right;">By Month</th>
														<th style="text-align: right;">Year To Date</th>
														<th style="text-align: right;">%</th>
													</tr>

												</thead>
												<tbody id="table-summary-units-tbody"></tbody>

												<tfoot id="table-summary-units-tfoot"></tfoot>
											</table>
										</td>
										<td style="width: 50%;">
											<div id="container-summary-units" style="height: 540px;"></div>
										</td>
									</tr>
								</table>





								<!-- 										</div> -->

								<!-- 										<div  class="column_form" style="width: 45%; overflow: hidden;"> -->
								<!-- 											<div id="container-summary-units" style="width:45%; height: 540px; "></div>  -->
								<!-- 										</div> -->

							</div>

							<div class="tab-pane" id="tab-fob">

								<!-- 										<div class="column_form"  style="width: 40%;" > -->

								<table class="" style="width: 100%">
									<tr>
										<td style="width: 50%;">
											<table class="table table-hover t-f-h-summary-fobs"
												id="table-summary-fobs">
												<thead class="header">
													<tr>
														<th>Month</th>
														<th style="text-align: right;">Actual(K)</th>
														<th style="text-align: right;">Forecast(K)</th>
														<th style="text-align: right;">By Month(K)</th>
														<th style="text-align: right;">Year To Date(K)</th>
														<th style="text-align: right;">%</th>
													</tr>
												</thead>

												<tbody id="table-summary-fobs-tbody"></tbody>

												<tfoot id="table-summary-fobs-tfoot"></tfoot>
											</table>
										</td>

										<td>
											<div id="container-summary-fobs" style="height: 540px;"></div>
										</td>
									</tr>
								</table>


								<!-- 										</div> -->

								<!-- 										<div  class="column_form" style="width: 45%; overflow: hidden;"> -->
								<!-- 											<div id="container-summary-fobs" style="width:45%; height: 540px; "></div>  -->
								<!-- 										</div> -->
							</div>

							<div class="tab-pane" id="tab-duties">

								<!-- 										<div class="column_form"  style="width: 40%;" > -->

								<table class="" style="width: 100%">
									<tr>
										<td style="width: 50%;">

											<table class="table table-hover t-f-h-summary-duties"
												id="table-summary-duties">
												<thead class="header">
													<tr>
														<th>Month</th>
														<th style="text-align: right;">Actual(K)</th>
														<th style="text-align: right;">Forecast(K)</th>
														<th style="text-align: right;">By Month(K)</th>
														<th style="text-align: right;">Year To Date(K)</th>
														<th style="text-align: right;">DTE(K)</th>
														<th style="text-align: right;">Exchange(K)</th>
														<th style="text-align: right;">%</th>
													</tr>
												</thead>
												<tbody id="table-summary-duties-tbody"></tbody>

												<tfoot id="table-summary-duties-tfoot"></tfoot>
											</table>
										</td>

										<td style="width: 50%;">
											<div id="container-summary-duties" style="height: 540px;"></div>
										</td>
									</tr>

								</table>

								<!-- 										</div> -->

								<!-- 										<div  class="column_form" style="width: 45%; overflow: hidden;"> -->
								<!-- 											<div id="container-summary-duties" style="width:45%; height: 540px; "></div>  -->
								<!-- 										</div> -->
							</div>

							<div class="tab-pane" id="tab-freights">
								<!-- 										<div class="column_form"  style="width: 40%;" > -->


								<table class="" style="width: 100%">
									<tr>
										<td style="width: 50%;">

											<table class="table table-hover t-f-h-summary-freights"
												id="table-summary-freights">
												<thead class="header">
													<tr>
														<th>Month</th>
														<th style="text-align: right;">Actual(K)</th>
														<th style="text-align: right;">Forecast(K)</th>
														<th style="text-align: right;">By Month(K)</th>
														<th style="text-align: right;">Year To Date(K)</th>
														<th style="text-align: right;">DTE(K)</th>
														<th style="text-align: right;">Exchange(K)</th>
														<th style="text-align: right;">%</th>
													</tr>
												</thead>
												<tbody id="table-summary-freights-tbody"></tbody>

												<tfoot id="table-summary-freights-tfoot"></tfoot>
											</table>
										</td>

										<td style="width: 50%;"><div
												id="container-summary-freights" style="height: 540px;"></div></td>
									</tr>
								</table>

								<!-- 										</div> -->

								<!-- 										<div  class="column_form" style="width: 45%; overflow: hidden;"> -->
								<!-- 											<div id="container-summary-freights" style="width:45%; height: 540px;"></div>  -->
								<!-- 										</div> -->
							</div>
						</div>
					</div>

					<div class="tab-pane active" id="tab-detail-charts">

						<table class="" style="width: 100%;">


							<tr>
								<td style="width: 50%;">
									<div id="container-std-actual-percent"
										style="min-height: 500px;"></div>
								</td>
								<td style="width: 50%;">
									<div id="container-std-forecast-percent"
										style="min-height: 500px;"></div>
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


							<tr>
								<td colspan="2">

									<table class="table table-hover t-f-h-std-detail">
										<thead class="header">
											<tr>
												<th>Cost Type</th>
												<th>Cost Sub Type</th>
												<th>Cost Detail</th>
												<th style="text-align: right;">Actual(K)</th>
												<th style="text-align: right;">Forecast(K)</th>
												<th style="text-align: right;">Var Nominal(K)</th>
												<th style="text-align: right;">DTE(K)</th>
												<th style="text-align: right;">EX-EXCHANGE(K)</th>
												<th style="text-align: right;">%</th>
											</tr>
										</thead>
										<tbody id="table-std-detail-tbody"></tbody>
									</table>


								</td>
							</tr>

						</table>

						<!-- 								<div class="chart-wrapper"> -->
						<!-- 								    <div class="chart-inner"> -->
						<!-- 								    	<div id="container-std-typeCost" style="height: 500px; width: 50%; float: left;"></div> -->
						<!-- 								    	<div id="container-std-subTypeCost" style="height: 500px; width: 50%; float: right; "></div> -->

						<!-- 								    	<div id="container-std-detail" style="height: 500px; width:100%; margin: 0 auto; display: table; "></div> -->
						<!-- 								    </div> -->
						<!-- 								</div> -->

						<!-- 								<div class="column_form" style="width: 100%; "  > -->

						<!-- 									<table class="table table-hover"> -->
						<!-- 										<thead> -->
						<!-- 											<tr> -->
						<!-- 												<th>Cost Type</th> -->
						<!-- 												<th>Cost Sub Type</th> -->
						<!-- 												<th>Cost Detail</th> -->
						<!-- 												<th>Actual(K)</th> -->
						<!-- 												<th>Forecast(K)</th> -->
						<!-- 												<th>Var Nominal(K)</th> -->
						<!-- 												<th>DTE(K)</th> -->
						<!-- 												<th>EX-EXCHANGE(K)</th> -->
						<!-- 												<th>%</th> -->
						<!-- 											</tr> -->
						<!-- 										</thead> -->
						<!-- 										<tbody id="table-std-detail-tbody"></tbody> -->
						<!-- 									</table> -->
						<!-- 								</div> -->




					</div>
				</div>

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

</body>
</html>