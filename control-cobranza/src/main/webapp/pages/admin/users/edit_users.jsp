<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
<jsp:directive.include file="/pages/templates/header_html.jsp" />
</head>
<script>
	function cargar(){
		$("#idProfileUser").val('<s:property value="userVO.idProfileUser"/>');
	}
	
</script>
<body onload="cargar()">
	<script src="js/actions/admin/users/edit_users.js"></script>


	<div id="wrapper">

		<jsp:directive.include file="/pages/templates/head_body.jsp" />

		<div class="body_page">
			<div class="body_content">

				<form action="home.action" class="form_general"
					onsubmit="return false;">

					<fieldset>

						<legend>Edit User</legend>

						<div class="column_form">

							<label class="control-label">User ID</label> <input id="isidUser"
								type="text" class="input-block-level" readonly="readonly"
								value="<s:property value="userVO.isidUser"/>"> <label
								class="control-label">User Name</label> <input id="nameUser"
								type="text" class="input-block-level"
								value="<s:property value="userVO.nameUser"/>"> <label
								class="control-label">E-Mail</label> <input id="emailUser"
								type="text" class="input-block-level"
								value="<s:property value="userVO.emailUser"/>"> <label
								class="control-label">Profile</label> <select id="idProfileUser"
								name="idProfileUser" onchange="">
								<option value="">Select Profile</option>
								<s:iterator value="listProfile">
									<option value="<s:property value="idProfile"/>">
										<s:property value="nameProfile" /></option>
								</s:iterator>
							</select>
						</div>


						<div class="column_form">
							<label class="control-label">Choice Sites</label>
							<s:iterator value="listSiteVO">
								<span style="margin-right: 50px; margin-bottom: 40px;"> <input
									class="select" id="chk" value="<s:property value="idSite"/>"
									type="checkbox" <s:property value="checkedSite"/>> <img
									src="img/icons/sites/<s:property value="flagSite"/>"
									alt="<s:property value="idSite"/>"
									title="<s:property value="nameSite"/>" />


								</span>
								<br />
							</s:iterator>
						</div>

						<div style="clear: both;"></div>
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