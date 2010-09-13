package ar.com.tsoluciones.emergencies.server.gui.core.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ar.com.tsoluciones.arcom.logging.Log;
import ar.com.tsoluciones.arcom.security.User;
import ar.com.tsoluciones.arcom.security.services.factory.UserServiceFactory;
import ar.com.tsoluciones.arcom.security.services.proxyinterface.UserServiceInterface;
import ar.com.tsoluciones.emergencies.server.gui.core.configuration.Configuration;

/**
 * Filtro de seguridad, bloquea a los usuarios que no tengan sesion, y controla los permisos de las paginas
 *
 * @author Ipoletti
 * @version 1.0, 16/11/2004
 * @see Filter
 */
public class SecurityFilter implements Filter {
	private static final String CURRENT_USER = "CURRENTUSER";

	private static final String URL_NOT_ENOUGH_PERMISSIONS = "/util/notEnoughPermissions.jsp";

	private static final String URL_SESSION_EXPIRED = "/util/sessionExpired.jsp";

	private static final String URL_LOGIN_PAGE = "/util/login.jsp";

	public void init(FilterConfig config) throws ServletException {
		// No hago nada
	}

	public void destroy() {
		//
	}

	/**
	 * Obtiene el nombre de la aplicacion del archivo de config
	 * @return nombre de app
	 */
	private String getApplicationName() {
		return Configuration.getInstance().getApplicationName();
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		if (Configuration.getInstance().isSessionDebugEnabled()) {
			log("MODO DEBUG: Tiene permiso siempre, seteando en sesion usuario " + Configuration.getInstance().getSessionDebugUsername());

			User presentSessionUser = (User) ((HttpServletRequest) req).getSession(true).getAttribute(CURRENT_USER);

			// Si no hay usuario en la sesion, o hay usuario pero es distinto al pedido, guardar el user
			if (presentSessionUser == null
					|| (presentSessionUser.getUsername().equalsIgnoreCase(Configuration.getInstance().getSessionDebugUsername()) == false)) {
				UserServiceInterface userServiceInterface = (UserServiceInterface) new UserServiceFactory().newInstance();
				User newSessionUser = userServiceInterface.getUserByUserName(Configuration.getInstance().getSessionDebugUsername());
				((HttpServletRequest) req).getSession(true).setAttribute(CURRENT_USER, newSessionUser);
			}

			chain.doFilter(req, res);
			return;
		}

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession(true);

		// Verifico que el usuario esté logueado...
		User user = (User) session.getAttribute(CURRENT_USER);
		if (user == null && !mustIgnoreSessionFilter(request)) {
			// No esta logueado
			log("No esta logueado");
			sessionExpired(request, response);
			return;
		}

		// Verifico los permisos de la pagina... TODO
		/*
		 MenuItem menuItem = null;
		 String url = ".." + request.getRequestURI().substring(getApplicationName().length() + 1);
		 log("Buscando " + url);
		 try
		 {
		 if (!chequearRequest(request.getRequestURI()))
		 {
		 // no es de abms/arcom o sea que se tiene que manejar su propia transaccion.
		 MenuItemServiceInterface menuItemService = (MenuItemServiceInterface) new MenuItemServiceFactory().newInstance();
		 menuItem = menuItemService.getMenuItemByUrl(url);
		 }
		 else
		 {
		 // como es de arcom el manejo de session y transaccion lo hace el listener....
		 menuItem = new MenuItemService().getMenuItemByUrl(url);
		 }
		 }
		 catch (HibernateException e)
		 {
		 e.printStackTrace();
		 }

		 if (menuItem == null)
		 {
		 // No necesita ningun permiso especial
		 log("No necesita ningun permiso especial");
		 chain.doFilter(req, res);
		 return;
		 }

		 if (!isAccessAllowed(user, menuItem))
		 {
		 // No tiene permisos
		 log("No tiene permisos");
		 notEnoughPermissions(request, response);
		 chain.doFilter(req, res);
		 return;
		 }
		 */

		// Tiene permiso
		log("Tiene permiso");
		chain.doFilter(req, res);
	}

	protected void sessionExpired(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		String base = "/" + getApplicationName() + (getApplicationName().length() == 0 ? "" : "/");
		req.setAttribute("base", base);
		req.getRequestDispatcher(URL_SESSION_EXPIRED).forward(req, res);
	}

	protected void notEnoughPermissions(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		String base = "/" + getApplicationName() + (getApplicationName().length() == 0 ? "" : "/");
		req.setAttribute("base", base);
		req.getRequestDispatcher(URL_NOT_ENOUGH_PERMISSIONS).forward(req, res);
	}

	private void log(String strString) {
		Log.getLogger(this.getClass()).debug(strString);
	}

	/**
	 * Determina si la pagina referenciada es especial. En ese caso, el filtro no debería hacer chequeo de sesion.
	 *
	 * @param req Objeto request
	 * @return true si debe ignorarse el filtro, false si no
	 */
	private boolean mustIgnoreSessionFilter(HttpServletRequest req) {
		String baseName = "";
		if (!getApplicationName().trim().equals(""))
			baseName = "/" + getApplicationName();

		// Pagina de login directa
		String loginPage = baseName + URL_LOGIN_PAGE;
		// Index, debe redireccionar la aplicacion
		String indexPage = baseName + "index.jsp";
		// Url base, debe redireccionar la aplicacion
		String baseUrl = getApplicationName();
		// Url limpia con barra al final
		String baseUrlSlash = getApplicationName() + "/";

		String uri = req.getRequestURI();
		if (uri.endsWith(loginPage) || uri.endsWith(indexPage) || uri.endsWith(baseUrl) || uri.endsWith(baseUrlSlash))
			return true;

		log(uri + " va a ser filtrada porque no hay sesion y no esta dentro de las paginas excluidas");

		return false;
	}
}
