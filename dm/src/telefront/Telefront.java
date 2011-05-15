package telefront;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.SSLException;

import logging.Logger;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.QName;
import org.dom4j.io.SAXReader;

import dailymarket.model.Context;

/**
 * Clase que implementa las funcionalidades de comunicación de telefront del
 * lado cliente
 *
 * @author Ipoletti, despada
 * @version 1.0, 17/06/2005
 * @see
 */
public class Telefront {
    private static final String CHARSET = "ISO-8859-1";
	private static final String COMPRESS_METHOD = "gzip";
	private static final String NAME = "name";
    private static final String PROPERTY = "property";
    private static final String DTO = "dto";
    private static final String EXECUTE = "execute";
    private static final String CLASS_NAME = "className";
    private static final String METHOD_NAME = "methodName";
    private static final String PARAMETERS = "parameters";
    private static final String PARAMETER = "parameter";
    private static final String CLASS = "class";
    private static final String TYPE = "type";
    private static final String COLLECTION = "collection";
    private static final String SIMPLE = "simple";

    protected URL dispatcherURL;
	protected static final SAXReader reader = new SAXReader();
	protected boolean enqueueAsyncCalls;
    private String sessionId = null;
    private int xmlReadTimeout = 10000;
    private int xmlConnectionTimeout = 10000;
    private int fileReadTimeout = 60000;
    private DocumentFactory factory = DocumentFactory.getInstance();


    /**
     * Crea un objeto Telefront
     *
     * @param url url del dispatcher
     */
    public Telefront(String url) {
        try {
            if (url == null)
                throw new RuntimeException("dispatcherURL no puede ser null");
            this.dispatcherURL = new URL(url);
        } catch (MalformedURLException e) {
        	Logger.getLogger911().error("URL incorrecta (" + url + ")", e);
        }
    }

    /**
     * Metodo que ejecuta el metodo de la clase indicada con sus respectivos
     * parametros. Si no se producen errores el metodo retorna el XML devuelto
     * por el servidor telefront, de lo contrario levantará una excepción
     * TelefrontClientException para errores locales o alguna de sus clases
     * hijas como BusinessException en caso de haberse producido un error del
     * lado servidor por cuestiones de negocio, o tambien ConnectionException en
     * caso de haber devuelto una respuesta no esperada del servidor.
     *
     * @param className
     * @param methodName
     * @param xml
     * @return El string con el XML del metodo
     */
    protected Document executeMethod(String className, String methodName, String xml) throws ConnectionException,
                    BusinessException {
        HttpURLConnection httpURLConnection = null;
        Long start = System.currentTimeMillis();
        try {
            URL withPathInfo = new URL(this.dispatcherURL.toString() + "/" + methodName);
            httpURLConnection = (HttpURLConnection) withPathInfo.openConnection();
            if (sessionId != null)
                httpURLConnection.addRequestProperty("Cookie", sessionId);
            httpURLConnection.addRequestProperty("Accept-Encoding", COMPRESS_METHOD);
            httpURLConnection.setConnectTimeout(xmlConnectionTimeout);
            httpURLConnection.setReadTimeout(xmlReadTimeout);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            OutputStream out = httpURLConnection.getOutputStream();
            out.write(xml.getBytes());
            out.flush();
            out.close();

            Document doc = proccessResponse(httpURLConnection); 
            
            Long end = System.currentTimeMillis();
            Double time = (end - start)/1000d;
            
            if (Context.getInstance().isProfilingEnabled()) {
            	org.apache.log4j.Logger.getLogger("PROFILING").info(methodName+" "+time+" seg.");	
            }
            return doc;
        } catch (SSLException ssle) {
        	throw new ConnectionException("Error al intentar establecer conexión ssl con " + dispatcherURL
					+ ". VERIFIQUE HABER ACEPTADO EL CERTIFICADO", ssle);
        } catch (SocketException e) {
        	throw new ServerUnreachableException("El servidor por el momento no puede ser alcanzado. Veririque el estado de la red", e);
        } catch (ProtocolException e) {
            throw new ConnectionException("Error configurando conexión http", e);
        } catch (IOException e) {
            throw new ConnectionException("Error al ejecutar metodo " + methodName + " de clase "
                            + className.replaceAll("\\.", " "), e);
        } finally {
            if (httpURLConnection != null)
                httpURLConnection.disconnect();
        }
    }

    private Document parseText(String s) throws DocumentException {
        Document doc;
        try {
            StringReader stringReader = new StringReader(s);
            doc = reader.read(stringReader);
            stringReader.close();
            if (doc == null)
                throw new DocumentException();
        } catch (DocumentException e) {
            // Lo reintento para ver si es un error de concurrencia del
            // SAXReader
            try {
                Thread.sleep((long) (Math.random() * 100));
            } catch (InterruptedException e1) {
            	Logger.getLogger911().error("La respuesta telefront no fue parseada correctamente");
            	throw new DocumentException("La respuesta telefront no fue parseada correctamente", e1);
            }
            doc = new SAXReader().read(new StringReader(s));
            Logger.getLogger911().warn("La respuesta telefront fue parseada correctamente en el segundo intento");
        }

        return doc;
    }

    /**
     * Permite invocar un metodo telefront que devuelva cualquier stream de
     * datos
     *
     * @param className
     * @param methodName
     * @param parameters
     * @return
     * @throws ConnectionException
     * @throws BusinessException
     */
    public byte[] executeMethodBinary(String className, String methodName, Object... parameters)
                    throws ConnectionException, BusinessException {
        String xml = this.getParametersAsXml(className, methodName, parameters);
        return executeMethodBinary(className, methodName, xml);
    }

    public Thread executeMethodBinaryAsync(final String className, final String methodName, final Object parameters[],
                    final TelefrontCallbackListener listener) {
        Runnable runnable = new Runnable() {
            public void run() {
                TelefrontCallbackEvent eventDetail = new TelefrontCallbackEvent();
                try {
                    byte response[] = executeMethodBinary(className, methodName, parameters);
                    eventDetail.setBinaryContent(response);
                    executeCallbackListener(listener, eventDetail);
                } catch (ConnectionException e) {
                    e.printStackTrace();
                    eventDetail.setException(e);
                    executeCallbackListener(listener, eventDetail);
                } catch (BusinessException e) {
                    e.printStackTrace();
                    eventDetail.setException(e);
                    executeCallbackListener(listener, eventDetail);
                }
            }
        };

        return startCallbackThread(runnable);
    }

    protected byte[] executeMethodBinary(String className, String methodName, String xml) throws ConnectionException,
                    BusinessException {
        HttpURLConnection httpURLConnection = null;

        try {
            httpURLConnection = (HttpURLConnection) this.dispatcherURL.openConnection();
            if (sessionId != null)
                httpURLConnection.addRequestProperty("Cookie", sessionId);
            httpURLConnection.setConnectTimeout(xmlConnectionTimeout);
            // Timeout mayor por si la generacion del archivo demora mas tiempo
            httpURLConnection.setReadTimeout(fileReadTimeout);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            OutputStream out = httpURLConnection.getOutputStream();
            out.write(xml.getBytes());
            out.flush();
            out.close();

            int responseCode = httpURLConnection.getResponseCode();

            switch (responseCode) {
				case 404:
					throw new ConnectionException(
							"El servidor no ha podido encontrar el dispatcher, verifique que el sitio web este en alta (404): "
									+ httpURLConnection.getResponseMessage());
				case 500:
					throw new ConnectionException("El servidor ha reportado un error interno (500): "
							+ httpURLConnection.getResponseMessage());
				default:
					if (responseCode != 200)
						throw new ConnectionException("El servidor ha devuelto un estado no esperado: "
								+ httpURLConnection.getResponseMessage());
			}

            // Tomar respuesta
            int contentLength = httpURLConnection.getContentLength();
            byte[] responseByteArray = new byte[contentLength];
            int readedBytes = 0;
            while (readedBytes != responseByteArray.length)
                readedBytes += httpURLConnection.getInputStream().read(responseByteArray, readedBytes,
                                responseByteArray.length - readedBytes);

            if (readedBytes != responseByteArray.length)
                System.out.println("ERROR: Se leyeron menos datos que los disponibles");

            httpURLConnection.getInputStream().close();

            // Me fijo si es un xml evitando parsearlo por completo
            if (new String(responseByteArray, 0, 5).equalsIgnoreCase("<?xml")) {
                String stringResponse = new String(responseByteArray);
                Document responseDocument = null;
                try {
                    responseDocument = parseText(stringResponse);
                    Node errorNode = responseDocument.selectSingleNode("/serviceException/message");
                    if (errorNode != null)
                        throw new BusinessException(errorNode.getText());
                } catch (DocumentException e) {
                    // no es un XML, sigo.
                }
            }
            return responseByteArray;
        } catch (SocketTimeoutException socketTimeout) {
            throw new ConnectionException("Se supero el tiempo de espera, presione Aceptar para intentar nuevamente",
                            socketTimeout);
        } catch (ProtocolException e) {
            throw new ConnectionException("Error configurando conexión http", e);
        } catch (IOException e) {
            throw new ConnectionException("Error de E/S al ejecutar método " + methodName + " de clase "
                            + className.replaceAll("\\.", " "), e);
        } finally {
            if (httpURLConnection != null)
                httpURLConnection.disconnect();
        }
    }

    /**
     * @param className
     * @param methodName
     * @param parameters
     * @return
     * @throws ConnectionException
     * @throws BusinessException
     */
    public Document executeMethod(String className, String methodName, Object... parameters) throws ConnectionException,
                    BusinessException {
        String xml = this.getParametersAsXml(className, methodName, parameters);
        return executeMethod(className, methodName, xml);
    }

    /**
     * @param className
     * @param methodName
     * @param map
     * @return
     * @throws ConnectionException
     * @throws BusinessException
     */
    public Document executeMethod(String className, String methodName, HashMap<String, String> map) throws ConnectionException,
                    BusinessException {
        String xml = getParametersAsXml(className, methodName, map);
        return executeMethod(className, methodName, xml);
    }

    /**
     * @param className
     * @param methodName
     * @param parameter
     * @param listener
     */
    public void executeMethodAsync(final String className, final String methodName, final Object parameter[],
                    final TelefrontCallbackListener listener) {
        executeMethodAsync(className, methodName, getParametersAsXml(className, methodName, parameter), listener);
    }

    /**
     * @param className
     * @param methodName
     * @param map
     * @param listener
     */
    public void executeMethodAsync(final String className, final String methodName, final HashMap<String, String> map,
                    final TelefrontCallbackListener listener) {
        executeMethodAsync(className, methodName, getParametersAsXml(className, methodName, map), listener);
    }

    /**
     * @param className
     * @param methodName
     * @param xml
     * @param listener
     */
    private void executeMethodAsync(final String className, final String methodName, final String xml,
                    final TelefrontCallbackListener listener) {
        Runnable runnable = new Runnable() {
            public void run() {
                if (enqueueAsyncCalls) {
                    synchronized (reader) {
                        run2();
                    }
                } else {
                    run2();
                }
            }

            private void run2() {
                Logger.getLogger911().debug("Comienzo " + Thread.currentThread().getId());
                TelefrontCallbackEvent eventDetail = new TelefrontCallbackEvent();
                try {
                    Document response = executeMethod(className, methodName, xml);
                    eventDetail.setDocument(response);
                    executeCallbackListener(listener, eventDetail);// listener.callback(eventDetail);
                } catch (ConnectionException e) {
                    eventDetail.setException(e);
                    executeCallbackListener(listener, eventDetail);// listener.callback(eventDetail);
                } catch (BusinessException e) {
                    eventDetail.setException(e);
                    executeCallbackListener(listener, eventDetail);// listener.callback(eventDetail);
                }
                Logger.getLogger911().debug("Finalizo " + Thread.currentThread().getId());
            }
        };

        startCallbackThread(runnable);
    }

    /**
     * Inicia el runnable, posible extender
     *
     * @param runnable
     */
    protected Thread startCallbackThread(Runnable runnable) {
        Thread callbackCaller = new Thread(runnable, "TelefrontAsyncCall");
        callbackCaller.start();
        return callbackCaller;
    }

    /**
     * Despacha el evento recibido al listener, permite sobrecargar para
     * operaciones especiales (como swing) para encolar la ejecución.
     *
     * @param listener
     * @param event
     */
    protected void executeCallbackListener(TelefrontCallbackListener listener, TelefrontCallbackEvent event) {
        // implementacion estandar, lo invoca sobre el mismo thread.
        listener.callback(event);
    }

    /**
     * @param file
     * @return
     * @throws ConnectionException
     */
    public String getFile(String file) throws ConnectionException {
        return new String(getFileAsByte(file));
    }

    /**
     * @param file
     * @return
     * @throws ConnectionException
     */
    public InputStream getFileAsStream(String file) throws ConnectionException {
        return new ByteArrayInputStream(getFileAsByte(file));
    }

    /**
     * @param file
     * @return
     * @throws ConnectionException
     */
    private byte[] getFileAsByte(String file) throws ConnectionException {
        HttpURLConnection httpURLConnection = null;
        if (!file.startsWith("http://"))
            file = this.dispatcherURL.toString() + "/../../" + file;

        try {
            httpURLConnection = (HttpURLConnection) new URL(file).openConnection();
            httpURLConnection.setConnectTimeout(xmlConnectionTimeout);
            httpURLConnection.setReadTimeout(fileReadTimeout);

            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            int responseCode = httpURLConnection.getResponseCode();

            switch (responseCode) {
            case 404:
                throw new ConnectionException(
                                "El servidor no ha podido encontrar el dispatcher, verifique que el sitio web este en alta (404): "
                                                + httpURLConnection.getResponseMessage());
            case 500:
                throw new ConnectionException("El servidor ha reportado un error interno (500): "
                                + httpURLConnection.getResponseMessage());
            default:
                if (responseCode != 200)
                    throw new ConnectionException("El servidor ha devuelto un estado no esperado: "
                                    + httpURLConnection.getResponseMessage());
            }

            // Tomar respuesta
            int contentLength = httpURLConnection.getContentLength();
            byte[] responseByteArray = new byte[contentLength];
            int readedBytes = 0;
            while (readedBytes != responseByteArray.length)
                readedBytes += httpURLConnection.getInputStream().read(responseByteArray, readedBytes,
                                responseByteArray.length - readedBytes);

            if (readedBytes != responseByteArray.length)
                System.out.println("ERROR: Se leyeron menos datos que los disponibles");

            httpURLConnection.getInputStream().close();

            // Transformar a string
            return responseByteArray;
        } catch (SocketTimeoutException socketTimeout) {
            throw new ConnectionException("Se supero el tiempo de espera, intente nuevamente", socketTimeout);
        } catch (ProtocolException e) {
            throw new ConnectionException("Error configurando conexión http", e);
        } catch (IOException e) {
            throw new ConnectionException("Error al obtener archivo " + file, e);
        } finally {
            if (httpURLConnection != null)
                httpURLConnection.disconnect();
        }
    }

    /**
     * Metodo que procesa la conexión de tipo HttpURLConnection, sobreescribirla
     * para otros tipos de conexiones. Devuelve el string del xml devuelto, o en
     * caso de error alguna excepción TelefrontClientException para errores
     * locales o alguna de sus clases hijas como BusinessException en caso de
     * haberse producido un error del lado servidor por cuestiones de negocio, o
     * tambien ConnectionException en caso de haber devuelto una respuesta no
     * esperada del servidor.
     *
     * @param httpURLConnection
     *            conexión
     * @return xml devuelto
     */
    protected Document proccessResponse(HttpURLConnection httpURLConnection) throws ConnectionException,
                    BusinessException {
        try {
            String cookie = httpURLConnection.getHeaderField("Set-Cookie");
            this.setSessionId(cookie != null ? cookie : this.sessionId);

            int responseCode = httpURLConnection.getResponseCode();

            switch (responseCode) {
            case 404:
                throw new ConnectionException(
                                "El servidor no ha podido encontrar el dispatcher, verifique que el sitio web este en alta (404): "
                                                + httpURLConnection.getResponseMessage());
            case 500:
                throw new ConnectionException("El servidor ha reportado un error interno (500): "
                                + httpURLConnection.getResponseMessage());
            default:
                if (responseCode != 200)
                    throw new ConnectionException("El servidor ha devuelto un estado no esperado: "
                                    + httpURLConnection.getResponseMessage());
            }

            httpURLConnection.getResponseCode();
            // Tomar respuesta
            
            byte[] responseByteArray = null;
            String contentEncoding = httpURLConnection.getHeaderField("Content-Encoding");
            if (contentEncoding != null && contentEncoding.equals(COMPRESS_METHOD)) {
            	byte buff[] = new byte[1024];
            	int l;
            	GZIPInputStream in = new GZIPInputStream(httpURLConnection.getInputStream());
            	StringBuilder sb = new StringBuilder();
            	while( (l = in.read(buff)) != -1 ) {
            		String s = new String(buff, 0, l, CHARSET);
            		sb.append(s);
            	}
            	responseByteArray = sb.toString().getBytes();
            } else {
            	responseByteArray = new byte[httpURLConnection.getContentLength()];
                int readedBytes = 0;
                while (readedBytes != responseByteArray.length)
                    readedBytes += httpURLConnection.getInputStream().read(responseByteArray, readedBytes,
                                    responseByteArray.length - readedBytes);

                if (readedBytes != responseByteArray.length)
                    System.out.println("ERROR: Se leyeron menos datos que los disponibles");	
            }
            

            httpURLConnection.getInputStream().close();

            // Transformar a string
            String response = new String(responseByteArray).trim();

            // Es un codigo 200
            Document responseDocument = parseText(response);
            Node businessError = responseDocument.selectSingleNode("/serviceException/message");
            if (businessError != null)
                throw new BusinessException(businessError.getText());

            Node internalError = responseDocument.selectSingleNode("/error/internal");
            if (internalError != null) {
                String msg = internalError.selectSingleNode("message").getText();
                String id  = internalError.selectSingleNode("id").getText();
//                Logger.getLogger911().error(
//                                String.format("El servidor reportó un error interno. Código: %s, msg: %s", id, msg));
                throw new BusinessException(String.format("Se produjo un evento inesperado. Si el problema persiste " +
                		"informe este id al administrador: %s", id));
            }

            Node sessionExpired = responseDocument.selectSingleNode("/sessionExpired");
            if(sessionExpired != null) {
//            	Inbox inbox = Inbox.getInstance(false);
//            	if(inbox != null) {
//            		inbox.getBotoneraPanel().closeSessionMenuItem_onClick(false);
//            	}
//            	return null;
            }
            
            return responseDocument;
        } catch (SocketTimeoutException e) {
            throw new ConnectionException("Finalizó un proceso interno, pulse Aceptar para continuar", e);
        } catch (IOException e) {
            throw new ConnectionException("Error al procesar respuesta de metodo remoto Telefront", e);
        } catch (DocumentException e) {
            throw new ConnectionException("La respuesta del metodo remoto pudo ser obtenida, pero ha llegado corrompida", e);
        }
    }

    /**
     * Retorna el XML en base a los parametros
     *
     * @param className
     * @param methodName
     * @param parametersArray
     * @return xml
     */
    @SuppressWarnings("unchecked")
	protected String getParametersAsXml(String className, String methodName, Object... parametersArray) {
        if (className == null)
            throw new RuntimeException("className no puede ser null");
        if (methodName == null)
            throw new RuntimeException("methodName no puede ser null");

        Document document = factory.createDocument();
        document.addElement(EXECUTE);

        Element rootElement = document.getRootElement();

        Element element = DocumentHelper.createElement(CLASS_NAME);
        element.setText(className);
        rootElement.add(element);

        element = DocumentHelper.createElement(METHOD_NAME);
        element.setText(methodName);
        rootElement.add(element);

        element = DocumentHelper.createElement(PARAMETERS);
        rootElement.add(element);

        for (int i = 0; i < parametersArray.length; i++) {
            Object param = parametersArray[i];
            Element paramElement = DocumentHelper.createElement(PARAMETER);
            if (param != null && Collection.class.isAssignableFrom(param.getClass())) {
                buildCollection(paramElement, param);
            } else if (param != null && Map.class.isAssignableFrom(param.getClass())) {
                buildMap(paramElement, (Map<String, String>) param);
            } else {
                paramElement.addAttribute(TYPE, SIMPLE);
                if (param != null) {
                	paramElement.setText(param.toString());
                }
            }
            element.add(paramElement);
        }

        return document.asXML();
    }

	private void buildCollection(Element paramElement, Object param) {
        paramElement.addAttribute(TYPE, COLLECTION);
        Collection<?> col = (Collection<?>)param;
        boolean first = false;
        for(Object o : col) {
            Class<?> clazz = o.getClass();
            Element child = paramElement.addElement(Introspector.decapitalize(clazz.getSimpleName()));
            try {
                BeanInfo info = Introspector.getBeanInfo(clazz);
                if (!first) {
                    paramElement.addAttribute(new QName(CLASS, null), clazz.getName());
                    first = !first;
                }
                for(PropertyDescriptor pd : info.getPropertyDescriptors()) {
                    if (!CLASS.equals(pd.getName())) {
                        Method getter = pd.getReadMethod();
                        Element property = child.addElement(new QName(pd.getName()));
                        Object value = getter.invoke(o, new Object[0]);
                        if(value != null)
                        	property.addText(value.toString());
                    }
                }
            } catch (IntrospectionException e) {
                throw new RuntimeException("Error al hacer instrospección de la clase " + clazz, e);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Retorna el XML en base a los parametros
     *
     * @param className
     * @param methodName
     * @param map
     * @return xml
     */
    protected String getParametersAsXml(String className, String methodName, HashMap<String, String> map) {
        if (className == null)
            throw new RuntimeException("className no puede ser null");
        if (methodName == null)
            throw new RuntimeException("methodName no puede ser null");

        Document document = null;
        document = factory.createDocument();
        document.addElement(EXECUTE);

        Element rootElement = document.getRootElement();

        Element element = DocumentHelper.createElement(CLASS_NAME);
        element.setText(className);
        rootElement.add(element);

        element = DocumentHelper.createElement(METHOD_NAME);
        element.setText(methodName);
        rootElement.add(element);

        element = DocumentHelper.createElement(PARAMETERS);
        rootElement.add(element);

        Element dtoElement = DocumentHelper.createElement(PARAMETER);
        element.add(dtoElement);
        buildMap(dtoElement, map);

        return document.asXML();
    }

    private void buildMap(Element dtoElement, Map<String, String> map) {
        dtoElement.addAttribute(TYPE, DTO);

        for (Map.Entry<String, String> entry : map.entrySet()) {
            Element paramElement = DocumentHelper.createElement(PROPERTY);
            paramElement.addAttribute(NAME, entry.getKey());
            paramElement.setText(entry.getValue());
            dtoElement.add(paramElement);
        }
	}


    public int getFileReadTimeout() {
        return fileReadTimeout;
    }

    public void setFileReadTimeout(int fileReadTimeout) {
        this.fileReadTimeout = fileReadTimeout;
    }

    public int getXmlConnectionTimeout() {
        return xmlConnectionTimeout;
    }

    public void setXmlConnectionTimeout(int xmlConnectionTimeout) {
        this.xmlConnectionTimeout = xmlConnectionTimeout;
    }

    public int getXmlReadTimeout() {
        return xmlReadTimeout;
    }

    public void setXmlReadTimeout(int xmlReadTimeout) {
        this.xmlReadTimeout = xmlReadTimeout;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public boolean isEnqueueAsyncCalls() {
        return enqueueAsyncCalls;
    }

    public void setEnqueueAsyncCalls(boolean enqueueAsyncCalls) {
        this.enqueueAsyncCalls = enqueueAsyncCalls;
    }
}