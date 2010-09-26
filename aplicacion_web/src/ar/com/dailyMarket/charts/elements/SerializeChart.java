package ar.com.dailyMarket.charts.elements;

import java.lang.reflect.Method;

import org.dom4j.Element;

public class SerializeChart {

	public static void serializeThat(Element root, Object obj) {
		for (int i = 0; i < obj.getClass().getMethods().length; i++) {            
            Method method =  obj.getClass().getMethods()[i];
            if(method.getName().startsWith("get")){

            	if(method.getReturnType() == String.class || method.getReturnType() == Integer.class || method.getReturnType() == Double.class) {
	            	Object[] params = {};
	                Object s;
	                try {
                        s = method.invoke(obj, params);
    	                if(s != null){
    	                	String attributeName = method.getName().substring(3, 4).toLowerCase().concat(method.getName().substring(4));
    	                    root.addAttribute(attributeName, s.toString());
    	                }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
	            } 
            }
        }
	}

}
