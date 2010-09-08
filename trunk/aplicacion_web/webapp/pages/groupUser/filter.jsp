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
	    	<tr>
	    		<TH><bean:message key="GroupUserForm.name"/></TH>
	            <TD width="150px"><html:text property="name"/></TD>
	        </tr>
	        <tr>
	    		<TH><bean:message key="GroupUserForm.description"/></TH>
	            <TD width="150px"><html:text property="description"/></TD>
	        </tr>
		</table>
		</td>
	</TR>
</TABLE>

