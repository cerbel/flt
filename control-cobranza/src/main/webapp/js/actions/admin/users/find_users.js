$(document).ready(function(){
	
	
   delUser = function(obj){
	   var params    = obj.getAttribute('value');
	   ajaxInvokeMethods.normalAjaxCallback("deleteUser.action", params, callbackReloadData);
   }
	
   
	$('#btnFilter').on('click', function() {

		var isidUser = $( "#isidUser" ).val();
		var params   = "userVO.isidUser="+isidUser+"&numberPage=1";
		var errors_rows = '';

		ajaxInvokeMethods.normalAjaxCallback("listUser.action",params,callbackGetUser);
		
	});
	
	
	
	//*************************CALLBACK FUNCTIONS
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
		$('.t-f-h-find-users').fixedHeader();
	}
	
	function callbackReloadData(){
	   $('#btnFilter').trigger("click");
	}
	$('.t-f-h-find-users').fixedHeader();
});