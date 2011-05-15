<%@ page import="ar.com.tsoluciones.emergencies.server.businesslogic.gis.db.AlovConnection" %>
<%@ page import="ar.com.tsoluciones.emergencies.server.businesslogic.gis.db.GeoConnection" %>
<%@ page import="java.io.ByteArrayOutputStream" %>
<%@ page import="java.io.PrintStream" %>
<%@ page import="ar.com.tsoluciones.emergencies.server.hibernate.C3P0ConnectionProvider"%>

<pre>
<%
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        PrintStream o = new PrintStream(bout);
        GeoConnection.getInstance().printReport(o);

        StringBuffer sb = new StringBuffer();
        sb.append(new java.util.Date()+"\n");
        sb.append("-----CONEXIONES GEO-------\n");
        sb.append(new String(bout.toByteArray()));
        sb.append("-----CONEXIONES ALOV------\n");

        bout.reset();
        AlovConnection.getInstance().printReport(o);
        sb.append(new String(bout.toByteArray()));

        bout.reset();
        C3P0ConnectionProvider.printReport(o);
        sb.append(new String(bout.toByteArray()));

        out.print(sb.toString());
        System.out.println(sb.toString());
%>

Esta informacion tambien se registra en el log local
</pre>
<script type="text/javascript">
    setTimeout ('window.location.reload( true )', 5000);
</script>
