<%@ page language="java" %>

<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/displaytag" prefix="ds" %>

<TABLE class="form" cellSpacing="0" cellPadding="0" border="0">
	<TR> 
		<TD colspan="3"> 
			<html:errors/>
		</TD>
	</TR> 
	<TR> 
		<TD class="title" colspan="3"><bean:message key="ConfigrationForm.timerAlarm"/></TD>		
	</TR>
	<tr><TD colspan="3">&nbsp;</TD></tr>
	<TR> 
		<TH><bean:message key="ConfigurationForm.period"/></TH>
		<TD colspan="2"><html:text property="timer" size="30"  maxlength="20"/></TD>	
	</TR>
	<tr><TD colspan="3">&nbsp;</TD></tr>
	<tr><TD colspan="3">&nbsp;</TD></tr>
	<TR> 
		<TD class="title" colspan="3"><bean:message key="ConfigurationForm.sendNotifications"/></TD>
	</TR>
	<tr><TD colspan="3">&nbsp;</TD></tr>	
	<TR>
		<TH><bean:message key="UserForm.email"/></TH>
		<TD>
			<html:select property="userId">	
				<OPTION VALUE="">Seleccione</OPTION>					
				<html:options collection="users" property="id" labelProperty="descUser" />
			</html:select>
		</TD>
		<TD align="left">
			<TABLE align="left" class="buttons" border="0" cellspacing="0" cellpadding="0"> 
				<TR>						
			        <TD  align="left"  width="50px" >        				
			                    <input width="50px" class="btn" value="Agregar"
									onclick="forms[0].VirtualDispatchName.value='addSendNotification';forms[0].submit();">
			        </TD>
			    </TR>
		    </TABLE>
		</TD>
	</TR>	
	<tr><TD colspan="3">&nbsp;</TD></tr>
</TABLE>	

<ds:table name="items" sort="list"  prop="formDisplaytag" export="false" id="row" pagesize="40" class="list"  cellspacing="0" cellpadding="3">	
<ds:column titleKey="UserForm.user" headerClass="listTitle" sortable="true" property="user"/>
<ds:column titleKey="UserForm.name" headerClass="listTitle" sortable="true" property="name"/>
<ds:column titleKey="UserForm.lastName" headerClass="listTitle" sortable="true" property="lastName"/>
<ds:column titleKey="UserForm.groupUser" headerClass="listTitle" sortable="true" property="groupUser.name"/>
<ds:column titleKey="UserForm.email" headerClass="listTitle" sortable="true" property="email"/>		
</ds:table>
