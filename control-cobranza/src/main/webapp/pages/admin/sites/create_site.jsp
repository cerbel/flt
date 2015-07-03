<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
<jsp:directive.include file="/pages/templates/header_html.jsp" />
</head>

<body>
	<script src="js/actions/admin/sites/create_site.js"></script>

	<div id="wrapper">

		<jsp:directive.include file="/pages/templates/head_body.jsp" />

		<div class="body_page">
			<div class="body_content">

				<form action="home.action" class="form_general"
					onsubmit="return false;" id="formSite">

					<fieldset>

						<legend>Create Site</legend>

						<div class="column_form">

							<label class="control-label">Site Name</label> <input
								id="nameSite" type="text" class="input-block-level"> <label
								class="control-label">Local Currency</label> <select
								id="idCurrencySite" name="idCurrencySite" onchange="">

								<option value="">Select local currency</option>
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

							<input type="radio" value="abkhazia-icon.png" name="flag"
								id="flag" /><img alt="" src="img/icons/sites/abkhazia-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="afghanistan-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/afghanistan-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="aland-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/aland-icon.png" style="margin-right: 20px;">
							<input type="radio" value="albania-icon.png" name="flag"
								id="flag" /><img alt="" src="img/icons/sites/albania-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="algeria-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/algeria-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="americanSamoa-icon.png" name="flag" id="flag" /><img
								alt="" src="img/icons/sites/americanSamoa-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="andorra-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/andorra-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="angola-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/angola-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="anguilla-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/anguilla-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="antarctica-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/antarctica-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="antiguaAndBarbuda-icon.png" name="flag" id="flag" /><img
								alt="" src="img/icons/sites/antiguaAndBarbuda-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="armenia-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/armenia-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="aruba-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/aruba-icon.png" style="margin-right: 20px;">
							<input type="radio" value="australia-icon.png" name="flag"
								id="flag" /><img alt="" src="img/icons/sites/australia-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="austria-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/austria-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="azerbaijan-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/azerbaijan-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="bahamas-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/bahamas-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="bahrain-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/bahrain-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="bangladesh-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/bangladesh-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="barbados-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/barbados-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="basqueCountry-icon.png" name="flag" id="flag" /><img
								alt="" src="img/icons/sites/basqueCountry-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="belarus-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/belarus-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="belgium-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/belgium-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="belize-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/belize-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="benin-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/benin-icon.png" style="margin-right: 20px;">
							<input type="radio" value="bermuda-icon.png" name="flag"
								id="flag" /><img alt="" src="img/icons/sites/bermuda-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="bhutan-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/bhutan-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="bolivia.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/bolivia.png" style="margin-right: 20px;">
							<input type="radio" value="bosniaAndHerzegovina-icon.png"
								name="flag" id="flag" /><img alt=""
								src="img/icons/sites/bosniaAndHerzegovina-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="botswana-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/botswana-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="britishAntarcticTerritory-icon.png" name="flag" id="flag" /><img
								alt="" src="img/icons/sites/britishAntarcticTerritory-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="britishVirginIslands-icon.png" name="flag" id="flag" /><img
								alt="" src="img/icons/sites/britishVirginIslands-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="brunei-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/brunei-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="bulgaria-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/bulgaria-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="burkinaFaso-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/burkinaFaso-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="burundi-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/burundi-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="cambodia-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/cambodia-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="cameroon-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/cameroon-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="canada-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/canada-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="canaryIslands-icon.png" name="flag" id="flag" /><img
								alt="" src="img/icons/sites/canaryIslands-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="capeVerde-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/capeVerde-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="caymanIslands-icon.png" name="flag" id="flag" /><img
								alt="" src="img/icons/sites/caymanIslands-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="centralAfricanRepublic-icon.png" name="flag" id="flag" /><img
								alt="" src="img/icons/sites/centralAfricanRepublic-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="chad-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/chad-icon.png" style="margin-right: 20px;">
							<input type="radio" value="china-icon.png" name="flag" id="flag" /><img
								alt="" src="img/icons/sites/china-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="christmasIsland-icon.png" name="flag" id="flag" /><img
								alt="" src="img/icons/sites/christmasIsland-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="cocosKeelingIslands-icon.png" name="flag" id="flag" /><img
								alt="" src="img/icons/sites/cocosKeelingIslands-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="commonwealth-icon.png" name="flag" id="flag" /><img
								alt="" src="img/icons/sites/commonwealth-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="comoros-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/comoros-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="cookIslands-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/cookIslands-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="costaRica-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/costaRica-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="coteDIvoire-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/coteDIvoire-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="croatia-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/croatia-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="cuba-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/cuba-icon.png" style="margin-right: 20px;">
							<input type="radio" value="curacao-icon.png" name="flag"
								id="flag" /><img alt="" src="img/icons/sites/curacao-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="cyprus-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/cyprus-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="czechRepublic-icon.png" name="flag" id="flag" /><img
								alt="" src="img/icons/sites/czechRepublic-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="democraticRepublicOfTheCongo-icon.png" name="flag"
								id="flag" /><img alt=""
								src="img/icons/sites/democraticRepublicOfTheCongo-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="denmark-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/denmark-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="djibouti-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/djibouti-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="dominica-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/dominica-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="dominicanRepublic-icon.png" name="flag" id="flag" /><img
								alt="" src="img/icons/sites/dominicanRepublic-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="eastTimor-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/eastTimor-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="egypt-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/egypt-icon.png" style="margin-right: 20px;">
							<input type="radio" value="elSalvador-icon.png" name="flag"
								id="flag" /><img alt=""
								src="img/icons/sites/elSalvador-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="england-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/england-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="equatorialGuinea-icon.png" name="flag" id="flag" /><img
								alt="" src="img/icons/sites/equatorialGuinea-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="eritrea-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/eritrea-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="estonia-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/estonia-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="ethiopia-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/ethiopia-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="europeanUnion-icon.png" name="flag" id="flag" /><img
								alt="" src="img/icons/sites/europeanUnion-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="falklandIslands-icon.png" name="flag" id="flag" /><img
								alt="" src="img/icons/sites/falklandIslands-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="faroes-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/faroes-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="fiji-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/fiji-icon.png" style="margin-right: 20px;">
							<input type="radio" value="finland-icon.png" name="flag"
								id="flag" /><img alt="" src="img/icons/sites/finland-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="france-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/france-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="frenchPolynesia-icon.png" name="flag" id="flag" /><img
								alt="" src="img/icons/sites/frenchPolynesia-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="frenchSouthernTerritories-icon.png" name="flag" id="flag" /><img
								alt="" src="img/icons/sites/frenchSouthernTerritories-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="gabon-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/gabon-icon.png" style="margin-right: 20px;">
							<input type="radio" value="gambia-icon.png" name="flag" id="flag" /><img
								alt="" src="img/icons/sites/gambia-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="georgia-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/georgia-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="germany-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/germany-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="ghana-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/ghana-icon.png" style="margin-right: 20px;">
							<input type="radio" value="gibraltar-icon.png" name="flag"
								id="flag" /><img alt="" src="img/icons/sites/gibraltar-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="goSquared-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/goSquared-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="greece-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/greece-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="greenland-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/greenland-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="grenada-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/grenada-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="guam-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/guam-icon.png" style="margin-right: 20px;">
							<input type="radio" value="guatemala-icon.png" name="flag"
								id="flag" /><img alt="" src="img/icons/sites/guatemala-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="guernsey-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/guernsey-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="guineaBissau-icon.png" name="flag" id="flag" /><img
								alt="" src="img/icons/sites/guineaBissau-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="guinea-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/guinea-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="guyana-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/guyana-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="haiti-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/haiti-icon.png" style="margin-right: 20px;">
							<input type="radio" value="honduras-icon.png" name="flag"
								id="flag" /><img alt="" src="img/icons/sites/honduras-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="hongKong-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/hongKong-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="hungary-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/hungary-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="iceland-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/iceland-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="india-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/india-icon.png" style="margin-right: 20px;">
							<input type="radio" value="indonesia-icon.png" name="flag"
								id="flag" /><img alt="" src="img/icons/sites/indonesia-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="iran-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/iran-icon.png" style="margin-right: 20px;">
							<input type="radio" value="iraq-icon.png" name="flag" id="flag" /><img
								alt="" src="img/icons/sites/iraq-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="ireland-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/ireland-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="isleOfMan-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/isleOfMan-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="israel-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/israel-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="italy-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/italy-icon.png" style="margin-right: 20px;">
							<input type="radio" value="jamaica-icon.png" name="flag"
								id="flag" /><img alt="" src="img/icons/sites/jamaica-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="japan-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/japan-icon.png" style="margin-right: 20px;">
							<input type="radio" value="jersey-icon.png" name="flag" id="flag" /><img
								alt="" src="img/icons/sites/jersey-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="jordan-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/jordan-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="kazakhstan-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/kazakhstan-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="kenya-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/kenya-icon.png" style="margin-right: 20px;">
							<input type="radio" value="kiribati-icon.png" name="flag"
								id="flag" /><img alt="" src="img/icons/sites/kiribati-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="kosovo-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/kosovo-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="kuwait-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/kuwait-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="kyrgyzstan-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/kyrgyzstan-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="laos-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/laos-icon.png" style="margin-right: 20px;">
							<input type="radio" value="latvia-icon.png" name="flag" id="flag" /><img
								alt="" src="img/icons/sites/latvia-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="lebanon-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/lebanon-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="lesotho-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/lesotho-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="liberia-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/liberia-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="libya-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/libya-icon.png" style="margin-right: 20px;">
							<input type="radio" value="liechtenstein-icon.png" name="flag"
								id="flag" /><img alt=""
								src="img/icons/sites/liechtenstein-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="lithuania-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/lithuania-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="luxembourg-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/luxembourg-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="macau-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/macau-icon.png" style="margin-right: 20px;">
							<input type="radio" value="macedonia-icon.png" name="flag"
								id="flag" /><img alt="" src="img/icons/sites/macedonia-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="madagascar-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/madagascar-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="malawi-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/malawi-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="malaysia-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/malaysia-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="maldives-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/maldives-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="mali-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/mali-icon.png" style="margin-right: 20px;">
							<input type="radio" value="malta-icon.png" name="flag" id="flag" /><img
								alt="" src="img/icons/sites/malta-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="marshallIslands-icon.png" name="flag" id="flag" /><img
								alt="" src="img/icons/sites/marshallIslands-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="mars-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/mars-icon.png" style="margin-right: 20px;">
							<input type="radio" value="martinique-icon.png" name="flag"
								id="flag" /><img alt=""
								src="img/icons/sites/martinique-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="mauritania-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/mauritania-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="mauritius-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/mauritius-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="mayotte-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/mayotte-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="micronesia-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/micronesia-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="moldova-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/moldova-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="monaco-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/monaco-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="mongolia-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/mongolia-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="montenegro-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/montenegro-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="montserrat-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/montserrat-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="morocco-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/morocco-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="mozambique-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/mozambique-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="myanmar-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/myanmar-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="nagornoKarabakh-icon.png" name="flag" id="flag" /><img
								alt="" src="img/icons/sites/nagornoKarabakh-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="namibia-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/namibia-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="nato-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/nato-icon.png" style="margin-right: 20px;">
							<input type="radio" value="nauru-icon.png" name="flag" id="flag" /><img
								alt="" src="img/icons/sites/nauru-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="nepal-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/nepal-icon.png" style="margin-right: 20px;">
							<input type="radio" value="netherlandsAntilles-icon.png"
								name="flag" id="flag" /><img alt=""
								src="img/icons/sites/netherlandsAntilles-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="netherlands-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/netherlands-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="newCaledonia-icon.png" name="flag" id="flag" /><img
								alt="" src="img/icons/sites/newCaledonia-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="newZealand-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/newZealand-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="nicaragua-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/nicaragua-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="nigeria-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/nigeria-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="niger-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/niger-icon.png" style="margin-right: 20px;">
							<input type="radio" value="niue-icon.png" name="flag" id="flag" /><img
								alt="" src="img/icons/sites/niue-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="norfolkIsland-icon.png" name="flag" id="flag" /><img
								alt="" src="img/icons/sites/norfolkIsland-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="northernCyprus-icon.png" name="flag" id="flag" /><img
								alt="" src="img/icons/sites/northernCyprus-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="northernMarianaIslands-icon.png" name="flag" id="flag" /><img
								alt="" src="img/icons/sites/northernMarianaIslands-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="northKorea-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/northKorea-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="norway-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/norway-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="olympics-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/olympics-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="oman-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/oman-icon.png" style="margin-right: 20px;">
							<input type="radio" value="pakistan-icon.png" name="flag"
								id="flag" /><img alt="" src="img/icons/sites/pakistan-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="palau-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/palau-icon.png" style="margin-right: 20px;">
							<input type="radio" value="palestine-icon.png" name="flag"
								id="flag" /><img alt="" src="img/icons/sites/palestine-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="papuaNewGuinea-icon.png" name="flag" id="flag" /><img
								alt="" src="img/icons/sites/papuaNewGuinea-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="paraguay-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/paraguay-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="philippines-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/philippines-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="pitcairnIslands-icon.png" name="flag" id="flag" /><img
								alt="" src="img/icons/sites/pitcairnIslands-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="poland-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/poland-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="portugal-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/portugal-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="puertoRico-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/puertoRico-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="qatar-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/qatar-icon.png" style="margin-right: 20px;">
							<input type="radio" value="redCross-icon.png" name="flag"
								id="flag" /><img alt="" src="img/icons/sites/redCross-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="republicOfTheCongo-icon.png" name="flag" id="flag" /><img
								alt="" src="img/icons/sites/republicOfTheCongo-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="romania-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/romania-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="russia-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/russia-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="rwanda-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/rwanda-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="saintBarthelemy-icon.png" name="flag" id="flag" /><img
								alt="" src="img/icons/sites/saintBarthelemy-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="saintHelena-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/saintHelena-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="saintKittsAndNevis-icon.png" name="flag" id="flag" /><img
								alt="" src="img/icons/sites/saintKittsAndNevis-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="saintLucia-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/saintLucia-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="saintMartin-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/saintMartin-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="saintVincentAndTheGrenadines-icon.png" name="flag"
								id="flag" /><img alt=""
								src="img/icons/sites/saintVincentAndTheGrenadines-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="samoa-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/samoa-icon.png" style="margin-right: 20px;">
							<input type="radio" value="sanMarino-icon.png" name="flag"
								id="flag" /><img alt="" src="img/icons/sites/sanMarino-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="saoTomeAndPrincipe-icon.png" name="flag" id="flag" /><img
								alt="" src="img/icons/sites/saoTomeAndPrincipe-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="saudiArabia-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/saudiArabia-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="scotland-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/scotland-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="senegal-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/senegal-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="serbia-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/serbia-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="seychelles-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/seychelles-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="sierraLeone-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/sierraLeone-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="singapore-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/singapore-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="slovakia-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/slovakia-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="slovenia-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/slovenia-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="solomonIslands-icon.png" name="flag" id="flag" /><img
								alt="" src="img/icons/sites/solomonIslands-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="somalia-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/somalia-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="somaliland-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/somaliland-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="southAfrica-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/southAfrica-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="southGeorgiaAndTheSouthSandwichIslands-icon.png"
								name="flag" id="flag" /><img alt=""
								src="img/icons/sites/southGeorgiaAndTheSouthSandwichIslands-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="southKorea-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/southKorea-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="southOssetia-icon.png" name="flag" id="flag" /><img
								alt="" src="img/icons/sites/southOssetia-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="southSudan-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/southSudan-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="spain-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/spain-icon.png" style="margin-right: 20px;">
							<input type="radio" value="sriLanka-icon.png" name="flag"
								id="flag" /><img alt="" src="img/icons/sites/sriLanka-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="sudan-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/sudan-icon.png" style="margin-right: 20px;">
							<input type="radio" value="suriname-icon.png" name="flag"
								id="flag" /><img alt="" src="img/icons/sites/suriname-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="swaziland-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/swaziland-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="sweden-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/sweden-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="switzerland-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/switzerland-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="syria-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/syria-icon.png" style="margin-right: 20px;">
							<input type="radio" value="taiwan-icon.png" name="flag" id="flag" /><img
								alt="" src="img/icons/sites/taiwan-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="tajikistan-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/tajikistan-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="tanzania-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/tanzania-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="thailand-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/thailand-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="togo-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/togo-icon.png" style="margin-right: 20px;">
							<input type="radio" value="tokelau-icon.png" name="flag"
								id="flag" /><img alt="" src="img/icons/sites/tokelau-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="tonga-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/tonga-icon.png" style="margin-right: 20px;">
							<input type="radio" value="trinidadAndTobago-icon.png"
								name="flag" id="flag" /><img alt=""
								src="img/icons/sites/trinidadAndTobago-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="tunisia-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/tunisia-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="turkey-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/turkey-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="turkmenistan-icon.png" name="flag" id="flag" /><img
								alt="" src="img/icons/sites/turkmenistan-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="turksAndCaicosIslands-icon.png" name="flag" id="flag" /><img
								alt="" src="img/icons/sites/turksAndCaicosIslands-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="tuvalu-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/tuvalu-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="uganda-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/uganda-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="ukraine-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/ukraine-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="unitedArabEmirates-icon.png" name="flag" id="flag" /><img
								alt="" src="img/icons/sites/unitedArabEmirates-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="unitedKingdom-icon.png" name="flag" id="flag" /><img
								alt="" src="img/icons/sites/unitedKingdom-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="unitedNations-icon.png" name="flag" id="flag" /><img
								alt="" src="img/icons/sites/unitedNations-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="unitedStates-icon.png" name="flag" id="flag" /><img
								alt="" src="img/icons/sites/unitedStates-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="uruguay-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/uruguay-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="usVirginIslands-icon.png" name="flag" id="flag" /><img
								alt="" src="img/icons/sites/usVirginIslands-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="uzbekistan-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/uzbekistan-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="vanuatu-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/vanuatu-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="vaticanCity-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/vaticanCity-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="vietnam-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/vietnam-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="wales-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/wales-icon.png" style="margin-right: 20px;">
							<input type="radio" value="wallisAndFutuna-icon.png" name="flag"
								id="flag" /><img alt=""
								src="img/icons/sites/wallisAndFutuna-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="westernSahara-icon.png" name="flag" id="flag" /><img
								alt="" src="img/icons/sites/westernSahara-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="yemen-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/yemen-icon.png" style="margin-right: 20px;">
							<input type="radio" value="zambia-icon.png" name="flag" id="flag" /><img
								alt="" src="img/icons/sites/zambia-icon.png"
								style="margin-right: 20px;"> <input type="radio"
								value="zimbabwe-icon.png" name="flag" id="flag" /><img alt=""
								src="img/icons/sites/zimbabwe-icon.png"
								style="margin-right: 20px;">



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