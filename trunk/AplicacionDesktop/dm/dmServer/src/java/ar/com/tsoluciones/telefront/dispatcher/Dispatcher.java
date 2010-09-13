package ar.com.tsoluciones.telefront.dispatcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import ar.com.tsoluciones.arcom.cor.InternalErrorException;
import ar.com.tsoluciones.arcom.cor.ServiceException;
import ar.com.tsoluciones.arcom.cor.WrappedInternalErrorException;
import ar.com.tsoluciones.arcom.logging.Log;
import ar.com.tsoluciones.arcom.logging.TSLogger;
import ar.com.tsoluciones.arcom.serviceproxy.ServiceFactory;
import ar.com.tsoluciones.telefront.configuration.Configuration;
import ar.com.tsoluciones.telefront.dispatcher.remotecall.Parameter;
import ar.com.tsoluciones.telefront.dispatcher.remotecall.RemoteCall;
import ar.com.tsoluciones.telefront.serializer.SerializerFactory;
import ar.com.tsoluciones.telefront.servicefactory.TelefrontServiceFactory;

/**
 * Copyright (c) Telefónica Soluciones
 * Todos los derechos reservados.
 */

/**
 * <p/> Ejecuta un método por reflection para telefront. El método a ejecutar
 * debe pertenecer a un Service, y se ubica instanciando el Service por un
 * ServiceFactory o TelefrontServiceFactory.
 * </p>
 * <p/> El dispatcher espera un post con el siguiente formato:
 * </p>
 *
 * <pre>
 *  	&lt;execute&gt;
 *  		&lt;className&gt;ar.com.tsoluciones.emergencies.service.CallApprovableService&lt;/className&gt;
 *  		&lt;methodName&gt;get&lt;/methodName&gt;
 *  		&lt;parameters&gt;
 *  			&lt;parameter type=&quot;simple&quot;&gt;1&lt;/parameter&gt;
 *  			&lt;parameter type=&quot;dto&quot;&gt;
 *  				&lt;property name=&quot;first&quot;&gt;Value&lt;/property&gt;
 *  			&lt;/dto&gt;
 *  		&lt;/parameters&gt;
 *  	&lt;/execute&gt;
 * </pre>
 *
 * <br />
 * <p/> ADVERTENCIA: El dispatcher en su presente edición no soporta la llamada
 * de métodos sobrecargados. Llamara al primer metodo de las sobrecargas.
 * </p>
 * <p/> El Charset acordado es ISO-8859-1
 * </p>
 * <br />
 * <p/> Se proporciona tanto la posibilidad de utilizar un ServiceFactory o un
 * TelefrontServiceFactory. Si se utiliza un método de ServiceFactory, sus
 * parámetros deben tener un constructor por String. La práctica recomendada es
 * implementar actions heredando de la clase TelefrontServiceFactory, que
 * realicen el mapeo cual acciones Struts. La posibilidad del ServiceFactory
 * solo se proporciona para evitar la innecesaria duplicación de codigo.
 * </p>
 * <br />
 * <p/> Al implementar los metodos en un ServiceFactory, al devolver resultados
 * se tienen dos opciones:
 * </p>
 * <p/> a) Un objeto que implementa la interfaz XmlSerializable: se toma el xml
 * de este objeto y se devuelve b) Un objeto cualquiera: se serializa a XML y se
 * devuelve el string serializado, que debe ser parseado en JavaScript
 * </p>
 * <br />
 * <p/> Pueden seguirse dos estrategias diferentes al momento de generar metodos
 * telefront:
 * </p>
 * <p/> a) Generar una interfaz con objetos basicos de Java. Esta estrategia de
 * diseño es la recomendada para metodos que tomen pocos parametros (dos como
 * maximo):
 *
 * <pre>
 *  	public void metodo(Long parametroUno, String parametroDos)
 *
 * </pre>
 *
 * Todos los parametros deben poseer un constructor que tome un String (o sea,
 * todos estos parametros deben ser serializables a un string). Cuando genere el
 * xml de invocation, debe proporcionar un elemento "parameter" dentro de
 * "parameters" con el attributo type seteado en "simple" (opcional, ya que es
 * el tipo de valor por defecto)
 * </p>
 * <p/> b) Emplear un DTO (Data Transfer Object). Dentro de este DTO deben
 * definirse propiedades con sus getters y setters. Al ser un DTO, NO DEBE tener
 * inteligencia alguna, tan solo servir de transporte. Esta es la estrategia
 * recomendada para metodos que tomen varios parametros (tres o mas)
 *
 * <pre>
 *  	public void metodo(DataTransferObject dataTransferObject)
 * </pre>
 *
 * Cuando genere el string de invocation, debe proporcionar un elemento
 * "parameter" dentro de "parameters" con el attributo type seteado en "dto".
 * Dentro del mismo, deben incluirse una serie de tags "property" con el nombre
 * de la propiedad y el valor (Ver ejemplo)
 * </p>
 *
 * @author despada
 * @version 1.0, Nov 16, 2004, 11:22:21 AM
 * @see XmlSerializable
 * @since 1.0
 */
public class Dispatcher extends HttpServlet {

    private static int requestsAnswer;
    private static int totalAnswer;


    /**
     * Evita que el cliente utilice gets
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
	protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
                    throws ServletException, IOException {
        throw new ServletException("El Dispatcher solo acepta requests en modo POST");
    }

    /**
	 * Ejecuta el pedido de ejecución
	 * @param httpServletRequest El request enviado con el pedido
	 * @param httpServletResponse El response enviado para el pedido
	 * @throws ServletException Cuando algo sale mal
	 * @throws IOException Cuando algo sale mal
	 */
    @Override
	protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
                    throws ServletException, IOException {
    	String request="none";
        Object serviceObject = null;
        try {
            // Tomar llamada remota
            request = readRequest(httpServletRequest);
            Log.getLogger(this.getClass()).debug("Request recibido: " + request);

            Document document = DocumentHelper.parseText(request);
            RemoteCall remoteCall = new RemoteCall(document);

            // Controlar sesion
            if (isSessionExpired(httpServletRequest, httpServletResponse, remoteCall)) {
                Document answer = DocumentHelper.createDocument();
                Element rootElement = DocumentHelper.createElement("sessionExpired");
                answer.setRootElement(rootElement);
                emitXmlResponse(httpServletResponse, answer.asXML().getBytes());
                return;
            }

            // Instanciar objeto factory
            Class<?> serviceFactoryClass = Class.forName(remoteCall.getClassName());
            serviceObject = createObject(serviceFactoryClass, httpServletRequest);

            // Tomar método y castear parámetros
            Method[] methodArray = serviceObject.getClass().getMethods();
            Method method = getMethod(methodArray, remoteCall.getMethodName());
            Object[] parametersArray = getParameters(method, remoteCall);

            // Ejecutar método
            Log.getLogger(this.getClass()).debug(
                            "Dispatcher invocando metodo " + method.getName() + " de clase "
                                            + serviceObject.getClass().getName());
            Calendar beginCalendar = Calendar.getInstance();

            if (serviceObject instanceof TelefrontServiceFactory)
                ((TelefrontServiceFactory)serviceObject).beforeExecution();

            Object returnedObject = method.invoke(serviceObject, parametersArray);

            byte[] xmlBytes = serializeToXml(returnedObject);

            emitXmlResponse(httpServletResponse, xmlBytes);

            long executionTime = Calendar.getInstance().getTimeInMillis() - beginCalendar.getTimeInMillis();
            Log.getLogger(this.getClass()).debug(
                            "Dispatcher devolvio respuesta para metodo " + method.getName() + " en " + executionTime
                                            + " milisegundos");
        } catch (InvocationTargetException e) {
            TSLogger logger = Log.getLogger(Dispatcher.class);
            Throwable causeException = e.getCause();

            // El metodo lanzo una excepcion, si es una ServiceException
            // enviarla al cliente como parte del flujo de negocio
            if (causeException instanceof ServiceException) {
                // Logear excepcion de negocio en debug
                logger.debug("Excepcion de negocio despachada por Telefront", causeException);

                throwBusinessException(httpServletResponse, causeException.getMessage());
            } else if (causeException instanceof InternalErrorException) {
                sendError(request, httpServletResponse, (InternalErrorException)causeException);
            } else {
                sendError(request, httpServletResponse, new WrappedInternalErrorException(causeException));
            }
        } catch (ClassNotFoundException e) {
            sendError(request, httpServletResponse, new WrappedInternalErrorException(e));
        } catch (IllegalAccessException e) {
            sendError(request, httpServletResponse, new WrappedInternalErrorException(e));
        } catch (DocumentException e) {
            sendError(request, httpServletResponse, new WrappedInternalErrorException(e));
        } catch (InternalErrorException iee) {
            sendError(request, httpServletResponse, iee);
        } catch (Throwable t) {
            sendError(request, httpServletResponse, new WrappedInternalErrorException(t));
        } finally {
            if (serviceObject != null && serviceObject instanceof TelefrontServiceFactory) {
                ((TelefrontServiceFactory)serviceObject).afterExecution();
            }
        }
    }

    /**
	 * Obtiene el método buscado
	 * @param methodArray Lista de métodos de una clase
	 * @param methodName Nombre del método
	 * @return El método buscado
	 */
    private Method getMethod(Method[] methodArray, String methodName) {
        for (int i = 0; i < methodArray.length; i++) {
            Method method = methodArray[i];

            if (method.getName().equalsIgnoreCase(methodName)) {
                return method;
            }
        }

        throw new InternalErrorException("No se pudo encontrar un método " + methodName);
    }

    /**
	 * Toma una clase serviceFactory e instancia el objeto que cumple con la interfaz de servicios.
	 * @param serviceFactoryClass Clase serviceFactory que construye el objeto necesario. Si no es una ServiceFactory, lanza una excepción.
	 * @param httpServletRequest request HTTP
	 * @return El objeto creado
	 * @throws ServletException Cuando algo sale mal
	 */
    private Object createObject(Class<?> serviceFactoryClass, HttpServletRequest httpServletRequest) {
        try {
            Constructor<?> constructor = serviceFactoryClass.getConstructor(new Class[0]);
            Object classObject = constructor.newInstance(new Object[0]);

            if (classObject instanceof ServiceFactory == false)
                throw new InternalErrorException("La clase enviada debe ser una especificación de ServiceFactory");

            // Si es instancia especifica de TelefrontServiceFactory, setear los
            // datos adicionales
            if (classObject instanceof TelefrontServiceFactory) {
                TelefrontServiceFactory telefrontServiceFactory = (TelefrontServiceFactory) classObject;
                telefrontServiceFactory.setHttpServletRequest(httpServletRequest);
                return telefrontServiceFactory;
            }

            ServiceFactory serviceFactoryObject = (ServiceFactory) classObject;
            return serviceFactoryObject.newInstance();
        } catch (Exception e) {
            throw new InternalErrorException("Hubo un error al intentar construir el objeto", e);
        }
    }

    /**
	 * Traduce una lista de parametros en nodos a la lista de objetos para invocar un método por reflection
	 * @param method Metodo a llamar
	 * @param remoteCall objeto llamada remota
	 * @return lista de objetos para parámetros
	 */
    private Object[] getParameters(Method method, RemoteCall remoteCall) {
        Parameter[] parameterArray = remoteCall.getParameterArray();

        List<Object> list = new ArrayList<Object>();
        Class<?>[] parameterTypeArray = method.getParameterTypes();

        if (parameterTypeArray.length != parameterArray.length)
            throw new InternalErrorException("En llamada al metodo " + method.getName() + " se recibieron "
                            + parameterArray.length + " parametros pero el metodo utilizado tiene "
                            + parameterTypeArray.length);

        for (int i = 0; i < parameterArray.length; i++) {
            Parameter parameter = parameterArray[i];
            Class<?> parameterType = parameterTypeArray[i];

            list.add(parameter.getValue(parameterType));
        }

        return list.toArray(new Object[0]);
    }

    /**
	 * Serializa un objeto a XML
	 * @param object Objeto XML
	 * @return objeto serializado en un string XML
	 */
    private byte[] serializeToXml(Object object) {
        // Si la referencia es nula, entonces se devuelve un tag nulo, como
        // hacia XStream
        if (object == null)
            return "<null />".getBytes();

        Log.getLogger(this.getClass()).debug("Objeto obtenido: " + object.getClass().toString());

        // Si el objeto es un XmlSerializable, entonces tomar el XML
        // directamente de alli, no volver a serializar
        if (object instanceof XmlSerializable)
            return ((XmlSerializable) object).getBytes();

        // Serializar
        return SerializerFactory.newInstance().serialize(object).getBytes();
    }

    /**
	 * Emite la respuesta xml al cliente
	 * @param httpServletResponse Objeto response
	 * @param xmlBytes Xml a responder
	 */
    private void emitXmlResponse(HttpServletResponse httpServletResponse, byte xmlBytes[]) throws IOException {
        // Cambiar el encoding, si lo posee
        if (xmlBytes.length > 5) {
            String firstBytes = new String(xmlBytes, 0, 5);
            if (firstBytes.equals("<?xml")) {
                String xml = new String(xmlBytes);
                int finalPosition = xml.indexOf("?>");
                if (finalPosition >= 0) {
                    xml = xml.substring(finalPosition + 2, xml.length());
                    xml = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>" + xml;
                    xmlBytes = xml.getBytes();
                }
            }
        }

        // Escribir el xml de la respuesta para el cliente
        httpServletResponse.setContentType("text/xml");
        httpServletResponse.setContentLength(xmlBytes.length);
        httpServletResponse.setCharacterEncoding("ISO-8859-1");
        httpServletResponse.getOutputStream().write(xmlBytes);
        httpServletResponse.getOutputStream().close();

        String xml = new String(xmlBytes);
        if (Configuration.getInstance().isLogResponsesInDebug()) {
            Log.getLogger(this.getClass()).debug("Respuesta emitida:\n" + xml);
            Log.getLogger(this.getClass()).debug("Tamaño de la respuesta (KB):" + (xml.length() / 1024));
        }

        if (Configuration.getInstance().isBandwithTesterEnabled()) {
            totalAnswer += xml.length() / 1024;
            requestsAnswer++;
            Log.getLogger(this.getClass()).debug("Respuesta promedio (KB):" + totalAnswer / requestsAnswer);
        }
    }

    /**
	 * Lee el request enviado en un string
	 * @param httpServletRequest Objeto Request
	 * @return String con los datos
	 */
    private String readRequest(HttpServletRequest httpServletRequest) throws IOException {
        // Leer
    	StringBuffer stringBuffer = new StringBuffer();
    	BufferedReader bufferedReader = httpServletRequest.getReader();

        String string = bufferedReader.readLine();
        while (string != null) {
            stringBuffer.append(string);
            stringBuffer.append("\n");
            string = bufferedReader.readLine();
        }

        return stringBuffer.toString();
    }

    /**
	 * Lanza una excepcion de negocios por telefront. Las excepciones de negocio se muestran al usuario
	 * @param httpServletResponse objeto para utilizar emitXmlResponse
	 * @param message Mensaje a emitir en la excepcion
	 * @throws ServletException Cuando algo sale mal y no puede ser recuperado
	 */
    private void throwBusinessException(HttpServletResponse httpServletResponse, String message) {
        try {
            Document document = DocumentHelper.createDocument();
            Element rootElement = DocumentHelper.createElement("serviceException");
            document.setRootElement(rootElement);
            Element messageElement = rootElement.addElement("message");
            messageElement.setText(message);

            emitXmlResponse(httpServletResponse, document.asXML().getBytes());
        } catch (IOException e) {
            throw new InternalErrorException("Error IO al intentar emitir respuesta", e);
        }
    }

    /**
     * Método para enviar un error interno a la parte cliente.
     * @param requestXML
     * @param response
     * @param gfe
     * @throws IOException
     */
    private void sendError(String requestXML, HttpServletResponse response, InternalErrorException gfe) throws IOException {
        TSLogger logger = Log.getLogger(Dispatcher.class);
        gfe.setRequest(requestXML);
        logger.error("Error interno", gfe);
		emitXmlResponse(response, gfe.toXml().asXML().getBytes());
    }
    /**
	 * Controla que la sesion tenga valores cargados, si no los tiene, emite un mensaje al cliente avisando, y devuelve true
	 * @param httpServletRequest Request
	 * @param httpServletResponse Response
	 * @return true si expiro la sesion, false si no
	 * @throws ServletException Cuando algo sale mal
	 */
    private boolean isSessionExpired(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                    RemoteCall remoteCall) {
        // Ver si corresponde chequear la sesion
        if (Configuration.getInstance().isSessionCheck() == false)
            return false;

        // Debo chequear la sesion, ver si esta la variable requerida
        if (httpServletRequest.getSession().getAttribute(Configuration.getInstance().getVariable()) == null) {
            // No esta -> chequear que el metodo en particular no se encuentre en la lista de exclusion
            if (Configuration.getInstance().isExcluded(remoteCall.getClassName(), remoteCall.getMethodName())) {
                Log.getLogger(this.getClass()).debug(
						"La llamada remota esta especificamente excluida del control de sesion");
                return false;
            }

            Log.getLogger(Dispatcher.class).warn("Operación que llega con sessionId inválido: " + remoteCall.getClassName() + " " + remoteCall.getMethodName());
            // No se encuentra en la lista y no hay sesion -> informar
            return true;
        }

        return false;
    }
}