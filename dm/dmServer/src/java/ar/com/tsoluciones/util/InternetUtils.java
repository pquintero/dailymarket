package ar.com.tsoluciones.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.NTCredentials;
import org.apache.commons.httpclient.ProxyHost;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.log4j.Logger;

import ar.com.tsoluciones.emergencies.server.gui.core.configuration.Configuration;
import ar.com.tsoluciones.emergencies.server.gui.core.configuration.Configuration.Internet;
import ar.com.tsoluciones.emergencies.server.gui.core.configuration.Configuration.ProxyType;

/**
 * Clase con métodos útiles para las conexiones a internet
 *
 */
public class InternetUtils {

	protected static Logger log = Logger.getLogger(InternetUtils.class);

	 /**
     * Retorna la instancia de HttpClient que va a usar XFire. Se necesitó hacer esto porque seteándole propiedades a XFire
     * no se encontró la forma de hacerlo pasar por un proxy que autentique por NTLM.
     */
    public static HttpClient getHttpClientInstance() {
        Internet config = Configuration.getInstance().getInternet();
        HttpClient httpClient = new HttpClient();
        HttpState state = new HttpState();
        if (config.getUseProxy()) {
            UsernamePasswordCredentials credentials = null;
            if (ProxyType.NTLM.equals(config.getProxyType())) {
                try {
                    credentials = new NTCredentials(config.getProxyUser(), config.getProxyPass(),
                            InetAddress.getLocalHost().getHostName(), config.getProxyDomain());
                } catch (UnknownHostException e) {
                    log.warn("No se pudo determinar el IP del host. Se usará Localhost. Controle que su equipo resuelva apropiadamente localhost.");
                    credentials = new NTCredentials(config.getProxyUser(), config.getProxyPass(),
                            "localhost", config.getProxyDomain());
                }
            } else if (config.getProxyUser() != null) {
                credentials = new UsernamePasswordCredentials(config.getProxyUser(), config.getProxyPass());
            }
            state.setProxyCredentials(AuthScope.ANY, credentials);
            ProxyHost ph = new ProxyHost(config.getProxyHost(), Integer.valueOf(config.getProxyPort()));
            httpClient.getHostConfiguration().setProxyHost(ph);
        }
        httpClient.setState(state);
        return httpClient;
    }
}
