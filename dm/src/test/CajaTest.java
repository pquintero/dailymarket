package test;

import org.dom4j.Document;

import telefront.TelefrontGUI;
import dailymarket.lectorDeHuellas.UtilLectorHuellasSingleton;

public class CajaTest {

	 private static final String CONTROLLER_CLASS = "ar.com.tsoluciones.emergencies.server.gui.core.telefront.action.AperturaCajaManagerService";
	
	 public static void main(String[] args) {
		abrir();
	}
	 
	 public static void abrir(){
		 
		  Object params[] = new String[] { "apallich", "", "", "" };
          Document doc = null;
		 doc = TelefrontGUI.getInstance().executeMethod(CONTROLLER_CLASS, "abrirCaja", params);
		 UtilLectorHuellasSingleton.setCurrentUser(doc);
	 }
}
