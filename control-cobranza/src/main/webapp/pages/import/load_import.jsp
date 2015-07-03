<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
<jsp:directive.include file="/pages/templates/header_html.jsp" />
</head>

<body>
	<script src="js/actions/import/load_import.js"></script>

	<div id="wrapper">

		<jsp:directive.include file="/pages/templates/head_body.jsp" />

		<div class="body_page">
			<div class="body_content">
				<fieldset>
					<legend>Upload Import / Local Units</legend>

					<div class="column_form" style="margin-bottom: 10px">
						<form action="uploadExcel.action" class="form_general"
							enctype="multipart/form-data" id="data" name="data" method="post"
							onsubmit="return false;">
							<h4>Import / Local Plan Upload</h4>

							<label class="control-label">Site:</label> <select id="sitID"
								name="siteID">
								<option value="">Choice Site</option>
								<s:iterator value="#session.userSessionVO.listSiteVO">
									<option value="<s:property value="idSite"/>"><s:property
											value="nameSite" /></option>
								</s:iterator>
							</select> <label class="control-label">Download Last Versión:</label>
							<div class="head_table_icons">
								<a id="hrDownP" class="btn_table" href="#" title="Donwload"><i
									class="icon-download-alt"></i> Download</a>
							</div>

							<label class="control-label">File</label> <input id="fileUploadP"
								name="fileUploadP" type="file" class="input-block-level"
								style="width: 100%"> <label class="control-label">Last
								Update:</label> <strong><span id='lastUploadP'>--</span></strong>
							<div class="buttoms">
								<div class="well">
									<button class="btn btn-success" id="saveData" name="saveData"
										type="submit">Save</button>
								</div>
							</div>
						</form>

					</div>



					<div class="column_form" style="margin-bottom: 10px">
						<form action="uploadExcel.action" class="form_general"
							enctype="multipart/form-data" id="dataF" method="post"
							onsubmit="return false;">

							<h4>Import / Local Forecast Upload</h4>

							<label class="control-label">Site:</label> <select id="siteIDF">
								<option value="">Choice Site</option>
								<s:iterator value="#session.userSessionVO.listSiteVO">
									<option value="<s:property value="idSite"/>"><s:property
											value="nameSite" /></option>
								</s:iterator>
							</select> <label class="control-label">Download Last Versión:</label>
							<div class="head_table_icons">
								<a id="hrDownF" class="btn_table" href="#" title="Donwload"><i
									class="icon-download-alt"></i> Download</a>
							</div>

							<label class="control-label">File</label> <input id="fileUploadF"
								name="fileUploadF" type="file" class="input-block-level"
								style="width: 100%"> <label class="control-label">Last
								Update:</label> <strong><span id='lastUploadF'>--</span></strong>

							<div class="buttoms">
								<div class="well">
									<button class="btn btn-success" id="saveDataF" name="saveDataF"
										type="submit">Save</button>
								</div>
							</div>
						</form>
					</div>

					<div style="clear: both;"></div>
				</fieldset>

				<div id="modal-search-products" class="modal hide fade"
					tabindex="-1" style="width: 850px">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">×</button>
						<h3 id="myModalLabel">List error in the excel import</h3>
					</div>
					<div class="modal-body">
						<div class="well"></div>
						<table class="table table-hover" id="table-item-master">
							<thead>
								<tr>
									<th>Line</th>
									<th>Error</th>
								</tr>
							</thead>
							<tbody id="table-item-master-tbody"></tbody>
						</table>
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