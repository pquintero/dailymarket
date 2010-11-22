<%@ page language="java" %>

<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>


<TABLE class="form" cellSpacing="0" height="100" cellPadding="3" border="0" > 
	<TR> 
		<TH style="width:100px;padding-left:40px;"> 
	        <html:hidden property="id"/>
	        <html:hidden property="objectType"/>
	        <html:hidden property="parentId"/>
	        <html:hidden property="parentStr"/>
	        <html:hidden property="uploadPath"/>
		</TH>
		<TD width="100px;"></TD>
		<TD></TD>
	</TR>
	<tr align=center>
		<td style="width:100px;padding-left:40px;">
			<a class="reportesboton" href="reportes.do?VirtualDispatchName=doReporteVentasMensuales">Adjunto</a>
		</td>			
		<td colspan="2"></td>	
	</tr> 
	<TR> 
	    <TH style="width:100px;padding-left:50px;">Archivo</TH>
	    <TD width="100px;">&nbsp;</TD>
	    <TD><html:file property="file" size="50"/></TD>
	</TR>
	
	<TR> 
	    <TH style="width:100px;padding-left:50px;">Descripción</TH>
	    <TD width="100px;">&nbsp;</TD>
	    <TD><html:text property="description" size="40" maxlength="100"/></TD>
	</TR>
	<tr>
		<td colspan="3" height="20"></td>
	</tr>
</TABLE>