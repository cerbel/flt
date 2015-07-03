<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
<jsp:directive.include file="/pages/templates/header_html.jsp" />

</head>

<body>
	<script src="js/actions/admin/users/find_users.js"></script>


	<script type="text/javascript">
	$(function() {
	    $(".pagination").pagination({
	        items: <s:property value="totalRows"/>,
	        itemsOnPage: <s:property value="maxRows"/>,
	        cssStyle: 'light-theme',
	        
	        onPageClick: function(pageNumber, event){
		    	
// 		    	alert(pageNumber);
		    	
		    	
				var isidUser = $( "#isidUser" ).val();
				var params   = "userVO.isidUser="+isidUser+"&numberPage="+pageNumber;
				var errors_rows = '';

				ajaxInvokeMethods.normalAjaxCallback("listUser.action", params, callbackGetUser);
		    	
				
				function callbackGetUser(data){

					var listUserVO = data.listUserVO;
					
					
					$("#table-item-master-tbody").empty();
					for (var i=0; i<listUserVO.length; i++){

						var row = "";
						row = row.concat("<tr>");
						row = row.concat("<td>"+listUserVO[i].isidUser+"</td>");
						row = row.concat("<td>"+listUserVO[i].nameUser+"</td>");
						row = row.concat("<td>"+listUserVO[i].emailUser+"</td>");
						row = row.concat("<td>"+listUserVO[i].nameProfileUser+"</td>");
						row = row.concat("<td><a class='btn_table' id='del' href='#' title='remove' value='userVO.isidUser="+listUserVO[i].isidUser+"' onclick='delUser(this)'><i class='icon-remove'></i></a><a class='btn_table' id='edi' href='editUser.action?isidUser="+listUserVO[i].isidUser+"' title='edit'><i class='icon-edit'></i></a>");
						row = row.concat("</td>");
						row = row.concat("</tr>");
						
						$("#table-item-master").append(row);
					}
					
					
					$('.body_page').css('height', 'auto');
					$('.body_page').height($( document ).height() - 172);
				}
				
		    }
	        
	    });
	});
	
	</script>

	<div id="wrapper">

		<jsp:directive.include file="/pages/templates/head_body.jsp" />

		<div class="body_page">
			<div class="body_content">

				<form action="usersAdmin.action" class="form_general">

					<fieldset>

						<legend>Users Admin</legend>


						<div class="column_form">

							<label class="control-label">ISID</label> <input id="isidUser"
								type="text" class="input-block-level">
						</div>

						<div style="clear: both;"></div>
					</fieldset>


				</form>


				<div class="well">
					<div class="buttoms">
						<button class="btn btn-success" type="button" id="btnFilter">Filter</button>
					</div>
				</div>


				<div class="head_table_icons">
					<a class="btn_table" href="createUser.action" title="Add"><i
						class="icon-plus"></i></a>
				</div>

				<table class="table table-hover t-f-h-find-users"
					id="table-item-master">
					<thead class="header">
						<tr>
							<th>ISID</th>
							<th>User Name</th>
							<th>E-Mail</th>
							<th>Profile</th>
							<th>Action</th>
						</tr>
					</thead>
					<tbody id="table-item-master-tbody">
						<s:iterator value="listUserVO">
							<tr>
								<td><s:property value="isidUser" /></td>
								<td><s:property value="nameUser" /></td>
								<td><s:property value="emailUser" /></td>
								<td><s:property value="nameProfileUser" /></td>
								<td><a class="btn_table" id="del" href="#" title="remove"
									value="userVO.isidUser=<s:property value="isidUser"/>"
									onclick="delUser(this)"><i class="icon-remove"></i></a> <a
									class="btn_table" id="edi"
									href="editUser.action?isidUser=<s:property value="isidUser"/>"
									title="edit"><i class="icon-edit"></i></a></td>
							</tr>
						</s:iterator>

					</tbody>
				</table>

				<div class="pagination" style="margin: 0 auto; display: table;"></div>

			</div>
		</div>




		<jsp:directive.include file="/pages/templates/footer_body.jsp" />
	</div>


</body>
</html>