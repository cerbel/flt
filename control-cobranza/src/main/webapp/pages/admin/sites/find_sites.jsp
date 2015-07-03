<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
<jsp:directive.include file="/pages/templates/header_html.jsp" />
</head>

<body>
	<script src="js/actions/admin/users/find_users.js"></script>

	<div id="wrapper">

		<jsp:directive.include file="/pages/templates/head_body.jsp" />

		<div class="body_page">
			<div class="body_content">

				<form action="usersAdmin.action" class="form_general">

					<fieldset>

						<legend>Admin Sites</legend>

						<!-- 						<div class="column_form"> -->

						<!-- 							<label class="control-label">ID Site</label> -->
						<!-- 							<input id="isidUser" type="text" class="input-block-level" > -->
						<!-- 						</div> -->

						<div style="clear: both;"></div>
					</fieldset>
				</form>

				<div class="well">
					<!-- 					<div class="buttoms"> -->
					<!-- 						<button class="btn btn-success" type="button" id="btnFilter">Filter</button> -->
					<!-- 					</div> -->
				</div>


				<div class="head_table_icons">
					<a class="btn_table" href="createSite.action" title="Add"><i
						class="icon-plus"></i></a>
				</div>

				<table class="table table-hover" id="table-sites">
					<thead>
						<tr>
							<th>Id Site</th>
							<th>Site Name</th>
							<th>Local Currency</th>
							<th>Flag</th>
							<th>Action</th>
						</tr>
					</thead>

					<!-- 						private String idSite; -->
					<!-- 	private String nameSite; -->
					<!-- 	private String checkedSite; -->

					<!-- 	private String flagSite; -->

					<!-- 	private CurrencyVO localCurrency; -->

					<tbody id="table-sites-tbody">
						<s:iterator value="listSiteVO">
							<tr>
								<td><s:property value="idSite" /></td>
								<td><s:property value="nameSite" /></td>
								<td><s:property value="localCurrency.acronym" /></td>
								<td><img alt=""
									src="img/icons/sites/<s:property value="flagSite"/>"></td>
								<td>
									<%-- 								<a class="btn_table" id="del" href="#" title="remove" value="userVO.isidUser=<s:property value="idSite"/>" onclick="delUser(this)"><i class="icon-remove"></i></a> --%>
									<a class="btn_table" id="edi"
									href="editSite.action?idSite=<s:property value="idSite"/>"
									title="Edit"><i class="icon-edit"></i></a>
								</td>
							</tr>
						</s:iterator>
					</tbody>
				</table>

			</div>

		</div>

		<jsp:directive.include file="/pages/templates/footer_body.jsp" />
	</div>

</body>
</html>