<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
<jsp:directive.include file="/pages/templates/header_html.jsp" />
</head>
<script>
	function cargar(){
		$("#idCurrencySite").val('<s:property value="siteVO.localCurrency.idCurrency"/>');
		
		$('input:radio[name="flag"][value="<s:property value="siteVO.flagSite"/>"]').attr('checked',true);
	}
	
</script>
<body onload="cargar()">
	<script src="js/actions/admin/sites/edit_sites.js"></script>


	<div id="wrapper">

		<jsp:directive.include file="/pages/templates/head_body.jsp" />

		<div class="body_page">
			<div class="body_content">

				<form action="home.action" class="form_general"
					onsubmit="return false;" id="formSite">

					<fieldset>

						<legend>Edit Site</legend>

						<div class="column_form">

							<label class="control-label">Site ID</label> <input id="idSite"
								type="text" class="input-block-level" readonly="readonly"
								value="<s:property value="siteVO.idSite"/>"> <label
								class="control-label">Site Name</label> <input id="nameSite"
								type="text" class="input-block-level"
								value="<s:property value="siteVO.nameSite"/>"> <label
								class="control-label">Local Currency</label> <select
								id="idCurrencySite" name="idCurrencySite" onchange="">
								<s:iterator value="#session.listCurrencySession">
									<option value="<s:property value="idGeneralCombo"/>">
										<s:property value="nameGeneralCombo" /></option>
								</s:iterator>
							</select>

						</div>

						<div style="clear: both;"></div>

						<div class="column_form_center" style="width: auto; float: left;">

							<label class="control-label">Site Flag</label> <input
								type="radio" value="argentina-icon.png" name="flag" id="flag" /><img
								alt="" src="img/icons/sites/argentina-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="brasil-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/brasil-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="chile-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/chile-icon.png" style="margin-right: 20px;">
							<input type="radio" value="colombia-icon.png" name="flag"
								id="flag" /><img alt="" src="img/icons/sites/colombia-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="ecuador-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/ecuador-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="mexico-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/mexico-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="panama-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/panama-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="peru-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/peru-icon.png" style="margin-right: 20px;">
						</div>


					</fieldset>

					<div class="buttoms"></div>
				</form>

				<div class="well">
					<div class="buttoms">
						<button id="btnBack" class="btn" type="button">Back</button>
						<button id="btnSave" class="btn btn-success" type="button">Save</button>
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