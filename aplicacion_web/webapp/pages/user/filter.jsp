<%@ page language="java" %>

<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>

<TABLE class="searchform" cellSpacing="0" cellPadding="6" border="0">
	<TR> 
		<TD> 
			<html:errors/>
		</TD>
	</TR> 
	<TR> 
	   <td>
	   	<TABLE class="searchform" cellSpacing="0" cellPadding="6" border="0">
	    	<tr>
	    		<TH><bean:message key="UserForm.id"/></TH>
	            <TD width="150px"><html:text property="idStr"/></TD>
	        </tr>
	        <tr>
	    		<TH><bean:message key="UserForm.user"/></TH>
	            <TD width="150px"><html:text property="user"/></TD>
	        </tr>
	        <tr>
	    		<TH><bean:message key="UserForm.dni"/></TH>
	            <TD width="150px"><html:text property="dni"/></TD>
	        </tr>
	        <tr>
	    		<TH><bean:message key="UserForm.lastName"/></TH>
	            <TD width="150px"><html:text property="lastName"/></TD>
	        </tr>
	    	<tr>
	    		<TH><bean:message key="UserForm.name"/></TH>
	            <TD width="150px"><html:text property="name"/></TD>
	        </tr>
	        <TR>
				<TH><bean:message key="UserForm.groupUser"/></TH>
				<TD>
					<html:select property="groupUserId">	
						<OPTION VALUE="-1">Seleccione</OPTION>					
						<html:options collection="groupsUsers" property="id" labelProperty="name" />
					</html:select>
				</TD>
			</TR>
		</table>
		</td>
	</TR>
</TABLE>

