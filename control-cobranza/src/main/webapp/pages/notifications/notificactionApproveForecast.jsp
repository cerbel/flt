<%@ taglib prefix="s" uri="/struts-tags"%>

<HTML>
<HEAD>
<META http-equiv='Content-Type' content='text/html; charset=iso-8859-1'>

<TITLE>MSD Cost Calculation</TITLE>
<style type='text/css'>
font-family: Verdana, Arial, Helvetica, sans-serif ; <!--body {
	background-color: #f4f5f7;
}

.parrafo_normal {
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 10px;
	font-style: normal;
	font-weight: normal;
	color: #000000;
	text-align: justify;
	padding-top: 10px;
	padding-right: 20px;
	padding-bottom: 0px;
	padding-left: 20px;
}

.texto_normal {
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 10px;
	font-style: normal;
	font-weight: normal;
	color: #000000;
	text-align: left;
	padding-top: 3px;
	padding-right: 3px;
	padding-bottom: 3px;
	padding-left: 3px;
}

.atte {
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 10px;
	font-style: normal;
	font-weight: normal;
	color: #000000;
	text-align: justify;
	padding-top: 5px;
	padding-right: 55px;
	padding-bottom: 0px;
	padding-left: 35px;
}

.negrita {
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 10px;
	font-style: normal;
	font-weight: bold;
	color: #000000;
}

.linke {
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 10px;
	font-style: normal;
	font-weight: normal;
	color: #FF0000;
	text-decoration: underline;
}

.negrita_rojo {
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 11px;
	font-style: normal;
	font-weight: bold;
	color: #FF0000;
}

.legales {
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 9px;
	font-style: normal;
	font-weight: normal;
	color: #666666;
	text-align: center;
	padding-top: 25px;
}

.linke_legales {
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 9px;
	font-style: normal;
	font-weight: normal;
	color: #666666;
}

.direccion {
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 9px;
	font-weight: normal;
	color: #FF0000;
}

.nota {
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 9px;
	font-weight: normal;
	color: #000000;
}

.click {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 11px;
	font-weight: normal;
	color: #000000;
	text-decoration: underline;
}

.click:hover {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 11px;
	font-weight: normal;
	color: #CCCCCC;
	text-decoration: none;
}

.pie {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 9px;
	font-weight: normal;
	color: #666666;
	text-align: justify;
}

.contacto {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 16px;
	font-style: normal;
	font-weight: bold;
	color: #FF0000;
}
-->
</style>
</HEAD>
<BODY>
	<TABLE width='553' border='5' align='center' cellpadding='0'
		cellspacing='0' bordercolor='#CCCCCC'>
		<TR>
			<TD><TABLE width='545' border='0' align='center' cellpadding='0'
					cellspacing='0' bgcolor='#FFFFFF'>
					<TR bgcolor='#F5F5F5'>
						<TD height='47' colspan='2'><IMG
							src='<s:property value="urlContextApp"/>/img/html/logo.gif'></TD>
					</TR>



					<TR>
						<TD colspan='2' class='parrafo_normal'>Estimado(a) <s:property
								value="notificationVO.nameToUserNotification" />: <BR> <BR>Le
							informamos que hoy, <s:property
								value="notificationVO.dateCurrentNotification" />, se ha
							reportado una notificación de <SPAN class='negrita'><s:property
									value="notificationVO.nameFromUserNotification" /></SPAN> con
							propósito de dar por aprobado el forecast:
						</TD>
					</TR>
					<TR>
						<TD colspan='2'>
							<TABLE width='545' border='0' cellspacing='22' cellpadding='0'>
								<TR>
									<TD valign='top' class='parrafo_normal'>
										<TABLE width='325' border='0' cellspacing='1' cellpadding='1'
											class='texto_normal'>
											<TR>
												<TD width='10'>&nbsp;</TD>
												<TD width='139'><IMG
													src='<s:property value="urlContextApp"/>/img/flecha.gif'
													width='7' height='8'>Stage ID</TD>
												<TD width='176' nowrap>: <B><s:property
															value="notificationVO.idStageNotification" /></B></TD>
											</TR>
											<TR>
												<TD width='10'>&nbsp;</TD>
												<TD width='139'><IMG
													src='<s:property value="urlContextApp"/>/img/flecha.gif'
													width='7' height='8'>Stage Nombre</TD>
												<TD width='176' nowrap>: <B><s:property
															value="notificationVO.nameStageNotification" /></B></TD>
											</TR>
											<TR>
												<TD width='10'>&nbsp;</TD>
												<TD width='139'><IMG
													src='<s:property value="urlContextApp"/>/img/flecha.gif'
													width='7' height='8'>Stage Estado</TD>
												<TD width='176'>: <B><s:property
															value="notificationVO.statusStageNotification" /></B></TD>
											</TR>

											<TR>
												<TD width='10'>&nbsp;</TD>
												<TD width='139' nowrap><IMG
													src='<s:property value="urlContextApp"/>/img/flecha.gif'
													width='7' height='8'>Site</TD>
												<TD width='176' nowrap>: <B><s:property
															value="notificationVO.siteStageNotification" /></B></TD>
											</TR>
											<TR>
												<TD width='10' rowspan='2'></TD>
												<TD width='139'></TD>
												<TD width='176'></TD>
											</TR>
											<TR>
												<TD colspan='2' height='7' align='left' valign='top'><BR>
												<IMG
													src='<s:property value="urlContextApp"/>/img/flecha.gif'
													width='7' height='8'>Comment user:&nbsp;<BR> <BR>
												<s:property value="notificationVO.commentNotificacion" /></TD>
											</TR>
										</TABLE>
									</TD>
								</TR>
							</TABLE>
						</TD>
					</TR>

					<TR>
						<TD colspan='2' class='parrafo_normal'>Atte. <BR>
						<SPAN class='negrita'>MSD Finanzas</SPAN></TD>
					</TR>

					<TR>
						<TD height='110' colspan='2' align='center'><TABLE
								width='100%' border='0' cellspacing='0' cellpadding='0'>
								<TR>
									<TD width='32' height='77'>&nbsp;</TD>
									<TD width='495' align='center' valign='middle'
										background='<s:property value="urlContextApp"/>/img/bk.gif'><A
										href='<s:property value="urlContextApp"/>/login.action'
										target='_blank'><span class="click">Presione aquí
												para ingresar al sistema de aprobación</span> </A></TD>
									<TD height='88' valign='bottom'>&nbsp;</TD>
								</TR>
							</TABLE></TD>
					<TR>
						<TD height='1' colspan='2' align='center' valign='bottom'>

							<TABLE width='500' border='0' cellspacing='0' cellpadding='0'>
								<TR>
									<TD height='30' class='direccion'><DIV align='left'>
											<SPAN class='nota'>Nota: Los comentarios aqu&iacute;
												ingresados son exclusiva responsabilidad de quien lo emite,
												y no involucran en absoluto a MSD. <br>Este email es
												generado en forma autom&aacute;tica, por favor no responda a
												este mensaje.<BR>
										</DIV></TD>
								</TR>
							</TABLE>
						</TD>
					</TR>
					<TR>
						<TD colspan='2' valign='bottom'>&nbsp;</TD>
					</TR>
				</TABLE></TD>
		</TR>
	</TABLE>

	<P class='legales'>Copyright © 2014-2020 Merck Sharp & Dhome. All
		rights reserved</P>
</BODY>
</HTML>
