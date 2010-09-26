package ar.com.dailyMarket.ui.security;

import java.security.Principal;
import java.util.Hashtable;
import java.util.Map;

import org.apache.log4j.Logger;
import org.securityfilter.realm.SimpleSecurityRealmBase;

import ar.com.dailyMarket.model.GroupUser;
import ar.com.dailyMarket.model.User;
import ar.com.dailyMarket.services.UserService;

public class DailyMarketSecurity extends SimpleSecurityRealmBase {
	
	private static Map roleCache = new Hashtable(); 
	
    private Logger log = Logger.getLogger(getClass().getName());

    public boolean booleanAuthenticate(String arg0, String arg1) {
        if (arg0 == null || arg0.trim().equals("")) {
            log.info("booleanAuthenticate: usuario no ingresado");
            return false;
        }

        try {
            UserService service = new UserService();
            User user = service.getUser((String)arg0);

            if (user == null || user.getPassword() == null || !user.getPassword().equals(arg1)) {
                log.info("booleanAuthenticate: usuario inválido");
                return false;
            }

            log.info("booleanAuthenticate: usuario logueado: " + user.getUser());
        } catch (Exception e) {
            log.info("booleanAuthenticate: " + e.toString());
            return false;
        }
        return true;
    }
    
    public boolean isUserInRole(Principal arg0, String arg1) {
        try {
            User user = (User) roleCache.get(arg0);
            if (user == null) {
               	
            	UserService service = new UserService();
               	user = service.getUser(arg0.getName());
               	roleCache.put(arg0,user);
            } 
            GroupUser groupUser = user.getGroupUser();            
            if (groupUser != null && (groupUser.getName().equals("Admin") || groupUser.getName().equals("Manager"))) {            	
            	return true;       	
            }
        } catch (Exception e) {
            log.info("isUserInRole: " + e.toString());
            return false;
        }
	
        return false;
    }
}
