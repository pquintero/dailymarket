package ar.com.tsoluciones.telefront.dispatcher.remotecall;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Element;

import ar.com.tsoluciones.util.Cast;


/**
 * <p>
 * Permite procesar colecciones de objetos javabeans cuyos miembros de dato son simples y pueden construirse a partir de un literal.
 * </p>
 */
public class Collection implements Parameter {

    private static final String CLASS_ATT = "class";
    private List<Object> valores;

    public Collection(Element xmlElement) {
        valores = new ArrayList<Object>();

        List<Element> beans = Cast.castList(Element.class, xmlElement.elements());
        if(beans.isEmpty())
        	return;
        
        Class<?> clazz;
        HashMap<String, Method> propertyMethod = new HashMap<String, Method>();
        try {
            clazz = Class.forName(xmlElement.attributeValue(CLASS_ATT));
            BeanInfo info = Introspector.getBeanInfo(clazz);
            for(PropertyDescriptor pd : info.getPropertyDescriptors()) {
                propertyMethod.put(pd.getName(), pd.getWriteMethod());
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IntrospectionException e) {
            throw new RuntimeException(e);
        }

        for(Element bean : beans) {
            try {
                Object o = clazz.newInstance();
                for(Element property : Cast.castList(Element.class, bean.elements())) {
                    String name = property.getName();
                    String value = property.getText();
                    if(!value.equals("")) {
	                    Class<?>[] parameterTypes = propertyMethod.get(name).getParameterTypes();
	                    Object setterParameter = parameterTypes[0].getConstructor(String.class).newInstance(value);
	                    propertyMethod.get(name).invoke(o, setterParameter);
                    }
                }
                valores.add(o);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (SecurityException e) {
            	throw new RuntimeException(e);
			} catch (NoSuchMethodException e) {
				throw new RuntimeException(e);
			}
        }
    }

    public Object getValue(Class<?> parameterType) {
        return valores;
    }
}
