package ar.com.tsoluciones.telefront.client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

/**
 * Clase que implementa las funcionalidades de comunicación de telefront del lado cliente
 *
 * @author Ipoletti
 * @version 1.0, 17/06/2005
 * @see
 */
public class TelefrontClient {
	protected URL dispatcherURL;

	/**
	 * Crea el objeto teniendo el parametro como base del contexto del servidor, a dicha base le concatena el dispatcher
	 * telefront por defecto.<br/>
	 * Si se pasa por ejemplo: http://localhost:8080/contexto/ la obtiene como http://localhost:8080/contexto/telefront/Dispatcher
	 *
	 * @param baseURL dirección base del contexto servidor
	 * @throws TelefrontClientException excepcion en la direccion especificada
	 */
	public TelefrontClient(String baseURL) throws TelefrontClientException {
		this(baseURL, true);
	}

	/**
	 * Crea el objeto en base a la url especificada, si el parametro booleano es "true" se comporta como el constructor
	 * TelefrontClient(String baseURL), de lo contrario toma el parametro URL sin concatenarle informacion adicional
	 *
	 * @param url                     url
	 * @param concatDefaultDispatcher true si se desea concatenar el dispatcher telefront por defecto
	 * @throws TelefrontClientException excepcion en la direccion especificada
	 */
	public TelefrontClient(String url, boolean concatDefaultDispatcher) throws TelefrontClientException {
		if (concatDefaultDispatcher) {
			if (!url.endsWith("/")) {
				url += "/";
			}
			url += "telefront/Dispatcher";
		}
		try {
			this.dispatcherURL = new URL(url);
		} catch (MalformedURLException e) {
			throw new TelefrontClientException("La URL especificada es incorrecta", e);
		}
	}

	/**
	 * Crea la instancia en base a la URL especificada, la url debe contener la ruta completa al dispatcher telefront
	 *
	 * @param dispatcherURL direccion del dispatcher
	 */
	public TelefrontClient(URL dispatcherURL) {
		this.dispatcherURL = dispatcherURL;
	}

	/**
	 * Retorna el XML en base a los parametros
	 *
	 * @param className
	 * @param methodName
	 * @param parametersArray
	 * @return xml
	 * @throws TelefrontClientException
	 */
	protected String getParametersAsXml(String className, String methodName, Object parametersArray[]) throws TelefrontClientException {
		if (className == null) {
			throw new TelefrontClientException("className no puede ser null");
		}

		if (methodName == null) {
			throw new TelefrontClientException("methodName no puede ser null");
		}

		Document document = null;
		try {
			document = DocumentHelper.parseText("<execute></execute>");
			Element rootElement = document.getRootElement();

			Element element = DocumentHelper.createElement("className");
			element.setText(className);
			rootElement.add(element);

			element = DocumentHelper.createElement("methodName");
			element.setText(methodName);
			rootElement.add(element);

			element = DocumentHelper.createElement("parameters");
			rootElement.add(element);

			for (int i = 0; i < parametersArray.length; i++) {
				Object param = parametersArray[i];
				Element paramElement = DocumentHelper.createElement("parameter");
				paramElement.addAttribute("type", "simple");
				if (param != null) {
					paramElement.setText(param.toString());
				}
				element.add(paramElement);
			}
			return document.asXML();
		} catch (DocumentException e) {
			assert false : "El xml cargado como base de telefront es invalido";
		}

		return "";
	}

	/**
	 * Metodo que ejecuta el metodo de la clase indicada con sus respectivos parametros.
	 * Si no se producen errores el metodo retorna el XML devuelto por el servidor telefront,
	 * de lo contrario levantará una excepción TelefrontClientException para errores locales o
	 * alguna de sus clases hijas como TelefrontBusinessException en caso de haberse producido un
	 * error del lado servidor por cuestiones de negocio, o tambien TelefrontConnectionException en
	 * caso de haber devuelto una respuesta no esperada del servidor.
	 *
	 * @param className
	 * @param methodName
	 * @param parameters
	 * @return El string con el XML del metodo
	 * @throws TelefrontClientException
	 */
	public String executeMethod(String className, String methodName, Object parameters[]) throws TelefrontClientException {
		URLConnection connection = null;
		try {
			connection = this.dispatcherURL.openConnection();
			String xml = this.getParametersAsXml(className, methodName, parameters);
			doConnection(connection, xml);
			return proccessResponse(connection);
		} catch (IOException e) {
			throw new TelefrontClientException("No se pudo abrir la conexión", e);
		} finally {
			if (connection != null) {
				closeConnection(connection);
			}
		}
	}

	/**
	 * Metodo que procesa la conexión de tipo HttpURLConnection, sobreescribirla para otros tipos de
	 * conexiones.
	 *
	 * @param connection
	 * @param xml
	 * @throws TelefrontClientException
	 */
	protected void doConnection(URLConnection connection, String xml) throws TelefrontClientException {
		if (connection instanceof HttpURLConnection) {
			HttpURLConnection httpConnection = (HttpURLConnection) connection;
			try {
				httpConnection.setRequestMethod("POST");
				httpConnection.setDoInput(true);
				httpConnection.setDoOutput(true);
				OutputStream out = httpConnection.getOutputStream();
				out.write(xml.getBytes());
				out.flush();
				out.close();
			} catch (ProtocolException e) {
				throw new TelefrontClientException("Error configurando conexión http", e);
			} catch (IOException e) {
				throw new TelefrontClientException("Error escribiendo xml en conexión http", e);
			}
		} else {
			throw new TelefrontClientException("Tipo de conexión no soportada " + connection.getClass());
		}
	}

	/**
	 * Metodo que procesa la conexión de tipo HttpURLConnection, sobreescribirla para otros tipos de
	 * conexiones.
	 * Devuelve el string del xml devuelto, o en caso de error alguna excepción TelefrontClientException para errores
	 * locales o alguna de sus clases hijas como TelefrontBusinessException en caso de haberse producido un
	 * error del lado servidor por cuestiones de negocio, o tambien TelefrontConnectionException en
	 * caso de haber devuelto una respuesta no esperada del servidor.
	 *
	 * @param connection conexión
	 * @return xml devuelto
	 * @throws TelefrontClientException TelefrontClientException o alguna de sus hijas
	 */
	protected String proccessResponse(URLConnection connection) throws TelefrontClientException {
		if (connection instanceof HttpURLConnection) {
			HttpURLConnection httpConnection = (HttpURLConnection) connection;

			try {
				int responseCode = httpConnection.getResponseCode();

				switch (responseCode) {
					case 404:
						throw new TelefrontConnectionException(
								"El servidor no ha podido encontrar el dispatcher, verifique que el sitio web este en alta (404): "
										+ httpConnection.getResponseMessage(), responseCode);
					case 500:
						throw new TelefrontConnectionException("El servidor ha reportado un error interno (500): " + httpConnection.getResponseMessage(),
								responseCode);
					default:
						if (responseCode != 200) {
							throw new TelefrontConnectionException("El servidor ha devuelto un estado no esperado: " + httpConnection.getResponseMessage(),
									responseCode);
						}
				}

				// Tomar respuesta
				byte[] responseByteArray = new byte[999999];
				httpConnection.getInputStream().read(responseByteArray);
				httpConnection.getInputStream().close();

				// Transformar a string
				String response = new String(responseByteArray).trim();

				// Es un codigo 200
				Document responseDocument = DocumentHelper.parseText(response);
				Node errorNode = responseDocument.selectSingleNode("/serviceException/message");
				if (errorNode != null) {
					throw new TelefrontBusinessException(errorNode.getText());
				}

				return responseDocument.asXML();
			} catch (IOException e) {
				throw new TelefrontClientException("No se pudo obtener la respuesta http", e);
			} catch (DocumentException e) {
				throw new TelefrontClientException("Hubo un error al parsear el xml devuelto por el Dispatcher", e);
			}
		}
		throw new TelefrontClientException("Tipo de conexión no soportada " + connection.getClass());
	}

	/**
	 * Metodo que procesa la conexión de tipo HttpURLConnection y la cierra, sobreescribirla para otros tipos de
	 * conexiones.
	 *
	 * @param connection conexión
	 * @throws TelefrontClientException error al cerrar conexión
	 */
	protected void closeConnection(URLConnection connection) throws TelefrontClientException {
		if (connection instanceof HttpURLConnection) {
			((HttpURLConnection) connection).disconnect();
		} else {
			throw new TelefrontClientException("Tipo de conexión no soportada " + connection.getClass());
		}
	}

	public static void main(String[] args) throws Exception {
		TelefrontClient client = new TelefrontClient("http://wmoper/model");
		String xml = client.executeMethod("ar.com.tsoluciones.emergencies.server.gui.core.telefront.action.CallApprovableManagerService",
				"getCodificatedQuestions", new Object[] { Long.valueOf(5) });

		//System.out.println(client.getParametersAsXml("ar.com.tsoluciones.Test", "findCalls", new Object[]{"Mitre", new Integer(3), new Date()}));
		System.out.println(client.dispatcherURL);
		System.out.println(xml);
	}

}