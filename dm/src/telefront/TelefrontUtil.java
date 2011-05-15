package telefront;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.QName;

public class TelefrontUtil {
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

	/**
	 * Retorna el XML en base a los parametros
	 *
	 * @param className
	 * @param methodName
	 * @param parametersArray
	 * @return xml
	 */
	@SuppressWarnings("unchecked")
	public static String getParametersAsXml(String className, String methodName, Object... parametersArray) {
		if (className == null)
			throw new RuntimeException("className no puede ser null");
		if (methodName == null)
			throw new RuntimeException("methodName no puede ser null");
		DocumentFactory factory = DocumentFactory.getInstance();
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

	private static void buildCollection(Element paramElement, Object param) {
		paramElement.addAttribute(TYPE, COLLECTION);
		Collection<?> col = (Collection<?>) param;
		boolean first = false;
		for (Object o : col) {
			Class<?> clazz = o.getClass();
			Element child = paramElement.addElement(Introspector.decapitalize(clazz.getSimpleName()));
			try {
				BeanInfo info = Introspector.getBeanInfo(clazz);
				if (!first) {
					paramElement.addAttribute(new QName(CLASS, null), clazz.getName());
					first = !first;
				}
				for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
					if (!CLASS.equals(pd.getName())) {
						Method getter = pd.getReadMethod();
						Element property = child.addElement(new QName(pd.getName()));
						Object value = getter.invoke(o, new Object[0]);
						if (value != null)
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
	public static String getParametersAsXml(String className, String methodName, HashMap<String, String> map) {
		if (className == null)
			throw new RuntimeException("className no puede ser null");
		if (methodName == null)
			throw new RuntimeException("methodName no puede ser null");
		DocumentFactory factory = DocumentFactory.getInstance();
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

	private static void buildMap(Element dtoElement, Map<String, String> map) {
		dtoElement.addAttribute(TYPE, DTO);

		for (Map.Entry<String, String> entry : map.entrySet()) {
			Element paramElement = DocumentHelper.createElement(PROPERTY);
			paramElement.addAttribute(NAME, entry.getKey());
			paramElement.setText(entry.getValue());
			dtoElement.add(paramElement);
		}
	}

}
