<%@ taglib prefix="s" uri="/struts-tags" %>

<div id="header_document">
	<div class="header_page">

		<div class="header_container_logo">
			<img src="img/html/logo.gif" alt="aasas" height="44" width="120"
				style="width: 120px; height: 44px;" />
		</div>

		<p class="header_container_subtitle">Ferreteria Los Troncos II</p>

		<s:iterator value="#session.userSessionVO.listSiteVO">
			<div class="header_container_sites">

				<s:if test="%{activeImage==\"Y\"}">
					<img src="img/icons/sites/<s:property value="flagSite"/>"
						alt="<s:property value="idSite"/>"
						title="<s:property value="nameSite"/>" />
				</s:if>


				<s:else>
					<img src="img/icons/sites/sitesGrey/<s:property value="flagSite"/>"
						alt="<s:property value="idSite"/>"
						title="<s:property value="nameSite"/>" />
				</s:else>

			</div>
		</s:iterator>

	     <%-- <div class="header_container_profile">
			<div style="float: left; margin-right: 25px; margin-top: 25px;">
				<strong><s:property value="#session.userSessionVO.nameUser" /></strong>,
				último acceso: <strong><s:property
						value="#session.userSessionVO.LastAccessDate" /></strong>
			</div> 

			<a href="sites.action" title="Sites"
				style="float: left; margin-right: 15px;"><img
				src="img/icons/menu/ico_menu_sites.png" alt="Sites" /></a> <a
				href="logout.action" title="Salir" style="float: left;"><img
				src="img/icons/menu/ico_menu_logout.png" alt="Salir" /></a>
		</div> --%>

	</div>

	<div class="header_menu">
		
		<%-- <div class="header_menu_options">
			  <a href="home.action" title="Home"><img src="img/icons/menu/ico_menu_home_selected.png" alt="Home" style="width: 24px; height: 24px;" /><span>Home</span></a>


			<s:if test="%{#session.menuSelectedOption=='home.action'}">
				<a href="selectMenuAction.action?principalAction=home.action"
					title="Home"><img
					src="img/icons/menu/ico_menu_home_selected.png" alt="Home"
					style="width: 24px; height: 24px;" /><span><font
						color="#333333">Home</font></span></a>
			</s:if>

			<s:if test="%{#session.menuSelectedOption!='home.action'}">
				<a href="selectMenuAction.action?principalAction=home.action"
					title="Home"><img src="img/icons/menu/ico_menu_home.png"
					alt="Home" style="width: 24px; height: 24px;" /><span>Home</span></a>
			</s:if>

		</div> --%>


<%-- 		<s:if test="%{#session.userSessionVO.idProfileUser==1}">
			<div class="header_menu_options">

				<s:if test="%{#session.menuSelectedOption=='showImport.action'}">
					<a href="selectMenuAction.action?principalAction=showImport.action"
						title="Load Import"><img
						src="img/icons/menu/ico_import_units_selected.png"
						alt="Load Import" style="width: 24px; height: 24px;" /><span><font
							color="#333333">Load Import</font> </span></a>
				</s:if>

				<s:if test="%{#session.menuSelectedOption!='showImport.action'}">
					<a href="selectMenuAction.action?principalAction=showImport.action"
						title="Load Import"><img
						src="img/icons/menu/ico_import_units.png" alt="Load Import"
						style="width: 24px; height: 24px;" /><span>Load Import</span></a>
				</s:if>
			</div>
		</s:if>


		<s:if test="%{#session.userSessionVO.idProfileUser==3}">
			<div class="header_menu_options">

				<s:if test="%{#session.menuSelectedOption=='showImport.action'}">
					<a href="selectMenuAction.action?principalAction=showImport.action"
						title="Load Import"><img
						src="img/icons/menu/ico_import_units_selected.png"
						alt="Load Import" style="width: 24px; height: 24px;" /><span><font
							color="#333333">Load Import</font> </span></a>
				</s:if>

				<s:if test="%{#session.menuSelectedOption!='showImport.action'}">
					<a href="selectMenuAction.action?principalAction=showImport.action"
						title="Load Import"><img
						src="img/icons/menu/ico_import_units.png" alt="Load Import"
						style="width: 24px; height: 24px;" /><span>Load Import</span></a>
				</s:if>

			</div>
		</s:if>


		          <s:if test="%{#session.isNotDataFound!='false'}">
		<div class="header_menu_options">


			<s:if test="%{#session.menuSelectedOption=='moveCost.action'}">
				<a href="selectMenuAction.action?principalAction=moveCost.action"
					title="Move Cost"><img
					src="img/icons/menu/ico_simulation_selected.png" alt="Move Cost"
					style="width: 24px; height: 24px;" /><span><font
						color="#333333">Simulator</font></span></a>
			</s:if>


			<s:if test="%{#session.menuSelectedOption!='moveCost.action'}">
				<a href="selectMenuAction.action?principalAction=moveCost.action"
					title="Move Cost"><img src="img/icons/menu/ico_simulation.png"
					alt="Move Cost" style="width: 24px; height: 24px;" /><span>Simulator</span></a>
			</s:if>
		</div> --%>
		<%--           </s:if> --%>












		<div class="header_menu_options">

			<s:if test="%{#session.menuSelectedOption=='createUser.action'}">
				<a href="selectMenuAction.action?principalAction=createUser.action"
					title="Stages"><img
					src="img/icons/menu/ico_menu_usersadmin_selected.png"
					alt="Cierres" style="width: 24px; height: 24px;" /><span><font
						color="#333333">Ingreso Factura</font></span></a>
			</s:if>

			<s:if test="%{#session.menuSelectedOption!='createUser.action'}">
				<a href="selectMenuAction.action?principalAction=createUser.action"
					title="Stages"><img
					src="img/icons/menu/ico_menu_usersadmin.png" alt="Cierres"
					style="width: 24px; height: 24px;" /><span>Ingreso Factura</span></a>
			</s:if>
		</div>
		
		
		
		
		





		<div class="header_menu_options">

			<s:if test="%{#session.menuSelectedOption=='initStages.action'}">
				<a href="selectMenuAction.action?principalAction=initStages.action"
					title="Stages"><img
					src="img/icons/menu/ico_menu_correctivos_selected.png"
					alt="Cierres" style="width: 24px; height: 24px;" /><span><font
						color="#333333">Facturas</font></span></a>
			</s:if>

			<s:if test="%{#session.menuSelectedOption!='initStages.action'}">
				<a href="selectMenuAction.action?principalAction=initStages.action"
					title="Stages"><img
					src="img/icons/menu/ico_menu_correctivos.png" alt="Cierres"
					style="width: 24px; height: 24px;" /><span>Facturas</span></a>
			</s:if>
		</div>



		<s:if test="%{#session.userSessionVO.idProfileUser==1}">
			<div class="header_menu_options">


				<s:if test="%{#session.menuSelectedOption=='usersAdmin.action'}">
					<a href="selectMenuAction.action?principalAction=adminMenu.action"
						title="Admin"><img
						src="img/icons/menu/ico_menu_operadoradmin.png" alt="Admin Users"
						style="width: 24px; height: 24px;" /><span><font
							color="#333333">Administer</font></span> </a>

					<%--             		<a href="selectMenuAction.action?principalAction=usersAdmin.action" title="Admin Users"><img src="img/icons/menu/ico_menu_operadoradmin.png" alt="Admin Users"style="width: 24px; height: 24px;" /><span><font color="#333333">Admin Users</font></span> </a> --%>


				</s:if>

				<s:if test="%{#session.menuSelectedOption!='usersAdmin.action'}">
					<a href="selectMenuAction.action?principalAction=adminMenu.action"
						title="Admin Users"><img
						src="img/icons/menu/ico_menu_operadoradmin.png" alt="Admin Users"
						style="width: 24px; height: 24px;" /><span>Administer</span> </a>
				</s:if>


			</div>
		</s:if>
	</div>

</div>