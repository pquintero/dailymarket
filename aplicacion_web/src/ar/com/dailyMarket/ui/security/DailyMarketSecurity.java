package ar.com.dailyMarket.ui.security;

import java.security.Principal;
import java.util.Hashtable;
import java.util.Map;

import org.apache.log4j.Logger;
import org.securityfilter.realm.SimpleSecurityRealmBase;

public class DailyMarketSecurity extends SimpleSecurityRealmBase {
	
	private static Map roleCache = new Hashtable(); 
	
    private Logger log = Logger.getLogger(getClass().getName());

    public boolean booleanAuthenticate(String arg0, String arg1) {
        /*if (arg0 == null || arg0.trim().equals("")) {
            log.info("booleanAuthenticate: usuario no ingresado");
            return false;
        }

        try {
            UserService service = (UserService) ServiceLocator.getInstance().getService("user");
            User user = service.getUser(arg0);

            if (user == null || user.getPassword() == null || !user.getPassword().equals(arg1) || user.getDateRemoved() != null) {
                log.info("booleanAuthenticate: usuario inválido");
                return false;
            }

            log.info("booleanAuthenticate: usuario logueado: " + user.getUser());
        } catch (Exception e) {
            log.info("booleanAuthenticate: " + e.toString());
            return false;
        }*/
        return true;
    }
    
    public boolean isUserInRole(Principal arg0, String arg1) {
        /*try {
            User user = (User) roleCache.get(arg0);
            if (user == null) {
               	
            	UserService service = (UserService) ServiceLocator.getInstance().getService("user");
               	user = service.getUser(arg0.getName());
               	roleCache.put(arg0,user);
            } 
            if (user.getGroup() != null && user.getGroup().hasRole(arg1)) {
            		return true;       	
            }
        } catch (Exception e) {
            log.info("isUserInRole: " + e.toString());
            return false;
        }
	
        return false;*/
    	return true;
    }
}
