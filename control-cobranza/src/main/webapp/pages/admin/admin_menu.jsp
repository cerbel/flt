<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
<jsp:directive.include file="/pages/templates/header_html.jsp" />
</head>

<body>
	<div id="wrapper">

		<jsp:directive.include file="/pages/templates/head_body.jsp" />

		<div class="body_page">
			<div class="body_content">

				<form action="home.action" class="form_general">
					<fieldset>
						<legend>Admin Menu</legend>

						<div class="column_form_center">
							<div
								style="margin-right: 30px; float: left; width: 80px; text-align: center;">
								<a
									href="selectMenuAction.action?principalAction=usersAdmin.action"
									title="Admin Users" style="width: 80px;"><img
									alt="Admin Users" src="img/icons/menu/ico-admin-users.png"></a>
								<strong>Admin User</strong>
							</div>

							<div
								style="margin-right: 30px; float: left; width: 80px; text-align: center;">
								<a
									href="selectMenuAction.action?principalAction=listSites.action"
									title="Admin Sites"><img alt="Admin Sites"
									src="img/icons/menu/ico-admin-sites.png"></a> <strong>Admin
									Sites</strong>
							</div>
						</div>

						<div style="clear: both;"></div>
					</fieldset>
				</form>

				<div class="well"></div>
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



</body>
</html>