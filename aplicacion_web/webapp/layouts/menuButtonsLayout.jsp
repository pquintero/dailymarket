<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-menu" prefix="menu" %>

<tiles:useAttribute name="menu" classname="java.lang.String"/>

<menu:useMenuDisplayer name="DCCButtons" config="ButtonMenuDisplayerStrings" bundle="org.apache.struts.action.MESSAGE" permissions="rolesAdapter">
			<menu:displayMenu name="<%=menu%>"/>			
	    </menu:useMenuDisplayer>
