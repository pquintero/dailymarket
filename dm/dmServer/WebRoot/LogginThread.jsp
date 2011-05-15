<%@ page import="ar.com.tsoluciones.arcom.util.LogginThread" %>

<%  
//Solo modificar alguno de estos 3 campos:
	String archivo = "C:/Pool_De_Conexiones.log";
	
	int segundos = 30; //Mínimo 10 Segundos. Máximo 60. Cualquier otro valor por fuera no funcionará.
	
	int max_dias = 4;  // Tratar que la multiplicación de segundos*minutos*Dias no supere las 30.000 impresiones (Ejecuciones).

//No tocar aquí.
	LogginThread tred = new LogginThread(archivo, segundos, max_dias);
	
	tred.start();
	
	out.println("Listo, mandé a ejecutar.");
	out.println("<BR>");
	out.println("<BR>");
	
	out.println("Se ejecutará cada " + segundos + " segundos y durante " + max_dias + " dias.");
	
	out.println("<BR>");
	out.println("<BR>");
	
	out.println("Archivo de log escrito en: "+archivo);
	
	if(true)
		return;
%>