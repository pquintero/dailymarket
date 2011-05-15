package ar.com.tsoluciones.arcom.cor;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * Representa un error no esperado en la aplicaci�n. Cualguier error que no sea de
 * negocio deber�a ser relanzado como una instancia de esta excepci�n dado que �sta
 * agrega un identificador de logueo �nico, estado contextual de donde se produjo el problema
 * e informacion del request que dio origen a la traza de ejecuci�n que produjo la excepci�n.
 */
public class InternalErrorException extends RuntimeException {

    private String id;
    private String request;
    private Object[] params;

    public InternalErrorException(String msg) {
        super(msg);
        id = generarId();
    }

    public InternalErrorException(String msg, Throwable t) {
        super(msg, t);
        id = generarId();
    }

    public InternalErrorException(String msg, Throwable t, Object... params) {
        super(msg, t);
        this.params = params;
        id = generarId();
    }

    public InternalErrorException(String msg, Object... params) {
        super(msg);
        this.params = params;
        id = generarId();
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public static String generarId() {
        String host;
        try {
            host = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
        	host = "localhost";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss-SSS");
        return host + "-" + sdf.format(new Date());
    }

    @Override
    public void printStackTrace(PrintStream s) {
    	synchronized (s) {
            s.println(String.format("\nIdentificaci�n �nico del error %s", id));
            if (params != null && params.length > 0) {
                s.println(" - Estado local al m�todo invocado");
                for(int i=0; i < params.length; i++)
                    s.println(String.format("\tPar�metro #%d: %s", i, params[i]));
            }
            if (request != null && !"".equals(request))
                s.println(" - RequestHTTP origen: " + request);
            s.println(" - Pila");
            super.printStackTrace(s);
        }
    }

    @Override
    public void printStackTrace(PrintWriter s) {
    	synchronized (s) {
            s.println(String.format("\nIdentificaci�n �nico del error %s", id));
            if (params != null && params.length > 0) {
                s.println(" - Estado local al m�todo invocado");
                for(int i=0; i < params.length; i++)
                    s.println(String.format("\tPar�metro #%d: %s", i, params[i]));
            }
            if (request != null && !"".equals(request))
                s.println(" - RequestHTTP origen: " + request);
            s.println(" - Pila");
            super.printStackTrace(s);
        }
    }

    public Document toXml() {
    	Document doc = DocumentHelper.createDocument();
    	Element root = DocumentHelper.createElement("error");
    	doc.setRootElement(root);
    	Element internal = root.addElement("internal");
    	internal.addElement("id").setText(String.valueOf(id));
    	if (getMessage() != null)
    		internal.addElement("message").setText(getMessage());
    	else
    		internal.addElement("message").setText("Sin mensaje");
    	return doc;
    }

	public String getId() {
		return id;
	}

}
