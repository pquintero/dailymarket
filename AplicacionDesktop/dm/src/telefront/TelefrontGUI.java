package telefront;

import java.awt.Component;
import java.awt.Container;
import java.awt.Window;
import java.io.InputStream;
import java.util.HashMap;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;

import logging.Logger;

import org.dom4j.Document;


import dailymarket.model.JComboBoxItem;
import dailymarket.model.JRadioButtonExtended;
import dailymarket.model.Text;
import dailymarket.swing.ui.Configuration;



/**
 * Extension de telefront especifica para interfases tipo Swing.
 * Enmascara errores y los despliega como JOptionPane
 *
 * @author POLETTII
 * @version 1.0, 06/04/2006
 * @see
 */
public class TelefrontGUI extends Telefront {
	private Window parent;

	private static String staticSession;

	private TelefrontExceptionListener exceptionListener = new ErrorMessageListener();

	/**
	 * Construye un objeto TelefrontGUI, es privado porque es un singleton
	 *
	 * @param parent Componente padre contenedor
	 */
	private TelefrontGUI(Window parent) {
		super(getTelefrontDispatcher());
		this.parent = parent;
		this.setXmlConnectionTimeout(Configuration.getInstance().getXmlConnectionTimeout());
		this.setXmlReadTimeout(Configuration.getInstance().getXmlReadTimeout());
		this.setFileReadTimeout(Configuration.getInstance().getFileReadTimeout());

		super.setSessionId(staticSession);
	}

	/**
	 * Devuelve la ubicación del dispatcher.
	 * @return URLString del dispatcher. P.e. https://server-principal:8443/telefront/Dispatcher
	 */
	private static String getTelefrontDispatcher() {
		return Configuration.getInstance().getTelefrontDispatcher();
	}

	/**
	 * Obtiene la instancia del singleton TelefrontGUI
	 *
	 * @return La instancia del singleton
	 */
	public static synchronized TelefrontGUI getInstance() {
		return new TelefrontGUI(null);
	}

	public static synchronized TelefrontGUI getInstance(Window parent) {
		TelefrontGUI telef = getInstance();
		telef.parent = parent;
		return telef;
	}

	@Override
	public Document executeMethod(String className, String methodName, Object... parameters) {
		Logger.getLogger911().debug("class " + className + " - method " + methodName + " -  params " + this.getStringForParameters(parameters));
		try {
			normalizeParameters(parameters);
			Document doc = super.executeMethod(className, methodName, parameters);
			Logger.getLogger911().debug(doc != null ? doc.asXML() : "null");
			return doc;
		} catch (ConnectionException e) {
			logError(e);
		} catch (BusinessException e) {
			logError(e);
		}
		return null;
	}

	private static void normalizeParameters(Object[] parameters) {
		for (int i = 0; i < parameters.length; i++) {
			if(parameters[i] != null && Container.class.isAssignableFrom(parameters[i].getClass())) {
				parameters[i] = generateParameter((Container) parameters[i], null);
			}
		}

	}
	/**
	 * Ejecuta un metodo remoto de Telefront en forma asíncrona, llamando un método establecido por una interfaz listener
	 *
	 * @param className  El nombre de la clase que contiene el metodo
	 * @param methodName El nombre del metodo
	 * @param parameters Lista de parámetros
	 * @param listener   El método a llamar una vez concluida la ejecución
	 */
	@Override
	public void executeMethodAsync(final String className, final String methodName, final Object parameters[], final TelefrontCallbackListener listener) {
		Logger.getLogger911().debug("class " + className + " - method " + methodName + " -  params " + this.getStringForParameters(parameters));
		normalizeParameters(parameters);
		super.executeMethodAsync(className, methodName, parameters, new TelefrontCallbackListener() {
			public void callback(TelefrontCallbackEvent event) {
				Logger.getLogger911().debug(event.getDocument() != null ? event.getDocument().asXML() : "null");

				if (event.getException() == null)
					listener.callback(event);
				else
					logError(event.getException());
			}
		});
	}

	/**
	 * Ejecuta un metodo remoto de Telefront enviando como parametros del request los valores de los controles de un contenedor
	 *
	 * @param className  El nombre de la clase que contiene el metodo
	 * @param methodName El nombre del metodo
	 * @param container  Objeto que contiene los controles
	 * @return El XML resultado de la llamada Telefront
	 */
	public Document executeMethod(String className, String methodName, Container container) {
		Logger.getLogger911().debug("class " + className + " - method " + methodName + " -  container " + container);
		try {
			HashMap<String, String> map = generateParameter(container, null);
			Document doc = super.executeMethod(className, methodName, map);
			Logger.getLogger911().debug(doc != null ? doc.asXML() : "null");
			return doc;
		} catch (ConnectionException e) {
			logError(e);
		} catch (BusinessException e) {
			logError(e);
		}
		return null;
	}

	/**
	 * Ejecuta un metodo remoto de Telefront en forma asíncrona enviando como parametros del request los valores de los controles de un contenedor
	 *
	 * @param className  El nombre de la clase que contiene el metodo
	 * @param methodName El nombre del metodo
	 * @param container  Objeto que contiene los controles
	 */
	public void executeMethodAsync(final String className, final String methodName, final Container container, final TelefrontCallbackListener listener) {
		Logger.getLogger911().debug("class " + className + " - method " + methodName + " -  container " + container);

		super.executeMethodAsync(className, methodName, generateParameter(container, null), new TelefrontCallbackListener() {
			public void callback(TelefrontCallbackEvent event) {
				Logger.getLogger911().debug(event.getDocument() != null ? event.getDocument().asXML() : "null");

				if (event.getException() == null)
					listener.callback(event);
				else
					logError(event.getException());
			}
		});
	}

	/**
	 * Obtiene un archivo de recurso por medio de telefront
	 *
	 * @param file Archivo a obtener
	 * @return Contenido de ese archivo
	 */
	@Override
	public String getFile(String file) {
		try {
			return super.getFile(file);
		} catch (ConnectionException e) {
			logError(e);
		}
		return null;
	}

	/**
	 * Obtiene un archivo de recurso por medio de telefront
	 *
	 * @param file Archivo a obtener
	 * @return Un Stream con el contenido de ese archivo
	 */
	@Override
	public InputStream getFileAsStream(String file) {
		Logger.getLogger911().debug("file " + file);
		try {
			return super.getFileAsStream(file);
		} catch (ConnectionException e) {
			logError(e);
		}
		return null;
	}

	/**
	 * Método privado que genera la lista de parametros a enviar a partir de un container
	 *
	 * @param container Container con los controles
	 * @param map       Opcional, puede ser null. Un map con una lista de parametros predefinidos
	 * @return El map actualizado, si se envio, o uno nuevo, con los datos.
	 */
	private static HashMap<String, String> generateParameter(Container container, HashMap<String, String> map) {
		if (map == null)
			map = new HashMap<String, String>();

		Component components[] = container.getComponents();
		for (int i = 0; i < components.length; i++) {
			Component component = components[i];

			// Si no tiene nombre no se envia
			if (component.getName() != null) {
				if (component instanceof JTextComponent) {
					map.put(component.getName(), Text.trim(((JTextComponent) component).getText()));
				} else if (component instanceof JComboBox && ((JComboBox) component).getSelectedItem() instanceof JComboBoxItem)
					map.put(component.getName(), ((JComboBoxItem) (((JComboBox) component).getSelectedItem())).getId());
				else if (component instanceof JCheckBox && ((JCheckBox) component).isSelected())
					map.put(component.getName(), "on");
				else if (component instanceof JRadioButtonExtended && ((JRadioButton) component).isSelected()) {
					map.put(component.getName(), ((JRadioButtonExtended) component).getId());
				} else if (component instanceof JList) {
					JList list = (JList) component;
					Object objs[] = list.getSelectedValues();
					StringBuffer sb = new StringBuffer();
					for (int j = 0; j < objs.length; j++) {
						Object obj = objs[j];
						if (obj instanceof JComboBoxItem)
							sb.append(((JComboBoxItem) obj).getId()).append(';');
					}
					map.put(component.getName(), sb.length() > 0 ? sb.substring(0, sb.length() - 1) : sb.toString());
				}

				// Loguear el componente encontrado si se pidio
				Object valueObject = map.get(component.getName());
				String value = "<null>";
				if (valueObject != null)
					value = valueObject.toString();

				Logger.getLogger911().debug("Componente " + component.getName() + ": " + value);
			}

			generateParameter((Container) component, map);
		}

		return map;
	}

	/**
	 * Ejecuta una llamada telefront pero obteniendo un resultado binario
	 *
	 * @param className  Nombre de la clase a ejecutar
	 * @param methodName Nombre del metodo
	 * @param parameters Lista de parametros
	 * @return Array binario con los datos
	 */
	@Override
	public byte[] executeMethodBinary(String className, String methodName, Object... parameters) {
		Logger.getLogger911().debug("class " + className + " - method " + methodName + " -  params " + this.getStringForParameters(parameters));
		try {
			return super.executeMethodBinary(className, methodName, parameters);
		} catch (ConnectionException e) {
			logError(e);
		} catch (BusinessException e) {
			logError(e);
		}
		return null;
	}

	/**
	 * Ejecuta una llamada telefront pero obteniendo un resultado binario en forma asincrona.
	 * El evento enviado al listener contendra la informacion en un array de bytes.
	 *
	 * @param className
	 * @param methodName
	 * @param parameters
	 * @param listener
	 */
	@Override
	public Thread executeMethodBinaryAsync(final String className, final String methodName, final Object parameters[], final TelefrontCallbackListener listener) {
		Logger.getLogger911().debug("executeMethodBinaryAsync class " + className + " - method " + methodName);
		return super.executeMethodBinaryAsync(className, methodName, parameters, new TelefrontCallbackListener() {
			public void callback(TelefrontCallbackEvent event) {
				if (event.getException() == null)
					listener.callback(event);
				else
					logError(event.getException());
			}
		});
	}

	/**
	 * @param listener
	 * @param event
	 */
	@Override
	protected void executeCallbackListener(final TelefrontCallbackListener listener, final TelefrontCallbackEvent event) {
		// Hago que el despacho se haga en thread aparte encolado por swing
		event.setThreadExecutionId(Thread.currentThread().getId());
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				listener.callback(event);
			}
		});
	}

	/**
	 * Logea errores de telefront
	 * @param e Excepcion a logear
	 */
	public void logError(Exception e) {
		TelefrontExceptionEvent exceptionEvent = new TelefrontExceptionEvent();
		exceptionEvent.setException(e);
		exceptionEvent.setTelefront(this);
		this.exceptionListener.catchException(exceptionEvent);			
	}

	private String getStringForParameters(Object objs[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < objs.length; i++) {
			sb.append(objs[i]).append(";");
		}
		return sb.toString();
	}

	/**
	 * Devuelve el padre de la ventana de error o null si no fue seteado
	 * @return ventana padre
	 */
	public Window getParent() {
		return parent;
	}

	/**
	 * Establece el padre de la ventana de error
	 * @param parent
	 */
	public void setParent(Window parent) {
		this.parent = parent;
	}

	@Override
	public void setSessionId(String sessionId) {
		super.setSessionId(sessionId);
		staticSession = sessionId;
	}

	public TelefrontExceptionListener getExceptionListener() {
		return exceptionListener;
	}

	public void setExceptionListener(TelefrontExceptionListener exceptionListener) {
		this.exceptionListener = exceptionListener;
	}
}
