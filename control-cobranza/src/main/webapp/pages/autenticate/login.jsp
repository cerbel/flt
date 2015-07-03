<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
<jsp:directive.include file="/pages/templates/header_html.jsp" />
</head>

<body>
	<script src="js/actions/autenticate/login.js"></script>


	<div id="header_document">
		<div class="header_page">

			<div class="header_container_logo">
				<img src="img/html/logo.gif" alt="aasas" height="44" width="120"
					style="width: 120px; height: 44px;" />
			</div>

			<p class="header_container_subtitle">Standard Cost Calculation</p>

		</div>
	</div>


	<div class="body_page">

		<div class="body_content" style="height: 500px;">

			<div class="body_content_login">
				<form class="form_sign" onsubmit="return false;">

					<div id="sign_ccs">
						<img alt="" src="img/html/logo_login.gif">
						<p class="rpn_name">Standard Cost Calculation...</p>

					</div>

					<h2>Please sign in</h2>

					<input name="isidUser" id="isidUser" type="text"
						class="input-block-level" placeholder="Username"> <input
						name="passwdUser" id="passwdUser" type="password"
						class="input-block-level" placeholder="Password">
					<div style="text-align: right;">
						<button id="btnLogin" class="btn btn-success"
							style="margin-right: 0px;" type="button">Login</button>
					</div>
				</form>
			</div>

		</div>
	</div>

	<jsp:directive.include file="/pages/templates/footer_body.jsp" />


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
