<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
<jsp:directive.include file="/pages/templates/header_html.jsp" />

<script>

function submitForm(){
	
	var cont = 0;
	$(".checkSites").each(function(){
		if ( $(this).is(':checked') ) {
			cont = cont + 1;
		}	
	});
	
	if (cont > 0)
	 	document.getElementById("formSelected").submit();
	else{
		$('#alert-errors').modal('show');
		$('#alert-errors-content').html("Debe seleccionar a lo menos un país");
	}
}

</script>

</head>

<body>
	<script src="js/actions/autenticate/site_select.js"></script>


	<div id="wrapper">

		<div id="header_document">
			<div class="header_page">

				<div class="header_container_logo">
					<img src="img/html/logo.gif" alt="aasas" height="44" width="120"
						style="width: 120px; height: 44px;" />
				</div>

				<p class="header_container_subtitle">Standard Cost Calculation</p>

				<div class="header_container_profile">
					<div style="float: left; margin-right: 25px; margin-top: 25px;">
						<strong><s:property
								value="#session.userSessionVO.nameUser" /></strong>, último acceso: <strong><s:property
								value="#session.userSessionVO.LastAccessDate" /></strong>
					</div>
					<a href="logout.action" title="Salir" style="float: left;"><img
						src="img/icons/menu/ico_menu_logout.png" alt="Salir" /></a>
				</div>
			</div>
		</div>


		<div class="body_page">
			<div class="body_content">

				<form action="home.action" class="form_general"
					onsubmit="return false;" id="formSelected">

					<fieldset>

						<legend>Site Choice</legend>


						<div class="column_form_center"
							style="width: 650px; text-align: center">



							<s:iterator value="#session.listSitesBaseSession">
								<label class="checkbox"
									style="font-size: 12px; float: left; margin-right: 15px;">
									<input type="checkbox" checked="checked"
									style="margin-top: 10px" value="<s:property value="idSite"/>"
									name="sitesSelected" id="sitesSelected" class="checkSites">
									<img src="img/icons/sites/<s:property value="flagSite"/>"
									alt="<s:property value="idSite"/>"
									title="<s:property value="nameSite"/>" />
								</label>
							</s:iterator>

						</div>



					</fieldset>

				</form>


				<div class="column_form_center"
					style="width: 700px; margin-bottom: 10px;">
					<h3 style="text-align: center;">Flow Stage Approve Proccess</h3>
					<a href="img/cats.jpg" target="_blank"><img src="img/cats.jpg"
						style="width: 700px; height: 450px;" /></a>
				</div>


				<div class="well" style="margin-bottom: 0px;">
					<div class="buttoms">
						<button class="btn btn-success" type="button"
							onclick="submitForm();">Continue</button>
					</div>
				</div>

			</div>

		</div>

		<jsp:directive.include file="/pages/templates/footer_body.jsp" />

	</div>


</body>
</html>