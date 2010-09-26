/*
 * Created on 12/09/2006 
 */
package ar.com.dailyMarket.displaytag;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.Globals;
import org.displaytag.localization.LocaleResolver;

/**
 * @author igarcia - DCC Soft
 */
public class StrutsLocaleResolver implements LocaleResolver {

	/* (non-Javadoc)
	 * @see org.displaytag.localization.LocaleResolver#resolveLocale(javax.servlet.http.HttpServletRequest)
	 */
	public Locale resolveLocale(HttpServletRequest arg0) {
		Locale loc = (Locale) arg0.getAttribute(Globals.LOCALE_KEY);
		if (loc == null)
			loc = arg0.getLocale();
		return loc;
	}

}
