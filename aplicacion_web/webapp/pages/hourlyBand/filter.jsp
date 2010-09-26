<%@ page language="java" %>

<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>

<TABLE class="searchform" cellSpacing="0" cellPadding="6" border="0">
	<TR> 
		<TD colspan="3"> 
			<html:errors/>
		</TD>
	</TR> 
	<TR> 
	   <td>
	   	<TABLE class="searchform" cellSpacing="0" cellPadding="6" border="0">	    	
	        <TR>
				<TH><bean:message key="GroupProductForm.name"/></TH>
				<TD>
					<html:select property="id">	
						<OPTION VALUE="-1">Seleccione</OPTION>					
						<html:options collection="hourlyBands" property="id" labelProperty="detail" />
					</html:select>
				</TD>
			</TR>
		</table>
		</td>
	</TR>
</TABLE>

