<!--COMPARATIVA DE VENTAS POR CAJERO MENSUAL CHART-->
<%@ page language="java" %>

<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.util.Date" %>

<bean:define id="month" property="month" name="IndicadoresForm" type="java.lang.String"/>
<bean:define id="year" property="year" name="IndicadoresForm" type="java.lang.String"/>
<bean:define id="cajeroId" property="cajeroId" name="IndicadoresForm" type="java.lang.Long"/>
<bean:define id="bandaHorariaId" property="bandaHorariaId" name="IndicadoresForm" type="java.lang.Long"/>

<TABLE class="form"  border="0" cellpadding="0" cellspacing="0">	
	<tr>
		<td>&nbsp;</td>
	</tr>

	<tr>
		<td >
			<table width="100%" align="left" cellpadding="0"  cellspacing="0" border="0">			
				<tr align="center">
					<TD>&nbsp;</TD>
					<TD align="center">
						<% 
						String url = "indicadores.do?" +
								"VirtualDispatchName=getCVPCMChart" + 
								"&month="+ URLEncoder.encode(month) + 
								"&year="+ URLEncoder.encode(year) +
								"&bandaHorariaId=" + bandaHorariaId;
						%>
						<embed src="charts/MSLine.swf"
		 					flashVars="dataURL=<%=URLEncoder.encode(url)%>"
		 					allowscriptaccess="always"
		 					quality="high"
		 					width="900"
		 					height="400"
							name="Line"
		 					type="application/x-shockwave-flash"
		 					pluginspage="http://www.macromedia.com/go/getflashplayer"
		 				/>
					</TD>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
</TABLE>