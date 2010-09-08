<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-menu" prefix="menu" %>

<tiles:useAttribute name="tab" classname="java.lang.String"/>

<menu:useMenuDisplayer name="DCCTabbedMenu" config="TabsDisplayerStrings" bundle="org.apache.struts.action.MESSAGE" permissions="rolesAdapter">
    <menu:displayMenu name="<%=tab%>"/>
</menu:useMenuDisplayer>
