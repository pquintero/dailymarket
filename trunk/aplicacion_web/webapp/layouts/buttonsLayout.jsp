<%@ page contentType="text/html" language="java" %>

<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<%@ page import="org.apache.struts.tiles.beans.MenuItem" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ResourceBundle" %>


<tiles:useAttribute name="buttonList" classname="List"/>

<TABLE align="right" class="buttons" border="0" cellspacing="0" cellpadding="3">
<%
	ResourceBundle resources = ResourceBundle.getBundle("ApplicationResources", (Locale)request.getSession().getAttribute("org.apache.struts.action.LOCALE"));	
%>
<TR>	
	<td width="100%">&nbsp;</td>
    <logic:iterate id="button" name="buttonList" type="MenuItem">
        
        <%
        	String retVal = null;
        	try {
	        	String s = button.getValue();				  		
				retVal = resources.getString(s);	
			} catch (Exception e) {
				retVal = button.getValue();
			}
        %>
        <TD align="left"  width="<%= retVal.length() * 10 %>px" >        				
                    <input readonly="true" width="<%= retVal.length() * 10 %>px" class="btn" value="<%=retVal%>"
						onclick="forms[0].VirtualDispatchName.value='<%=button.getLink()%>'; 
						forms[0].submit();">
        </TD>

        
    </logic:iterate>
	
</TR>
</TABLE>
