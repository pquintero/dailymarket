package dailymarket.model;

/**
 * <p>
 * Singleton para acceder a el contexto de sesión de un usuario logueado en el cliente.
 * </p>
 */
public class Context {
	private static Context instance;

	private Context() {
		currentUser = null;
		profilingEnabled = false;
	}

	public static synchronized Context getInstance() {
		if (instance == null)
			instance = new Context();
		return instance;
	}

	private Empleado currentUser;
	private boolean profilingEnabled;

	public void setCurrentUser(Empleado user) {
		currentUser = user;
	}

	public Empleado getCurrentUser() {
		return currentUser;
	}

	/**
	 * Nulifico la instancia del Singleton.
	 */
	public static void reset() {
		instance = null;
	}

	public boolean isProfilingEnabled() {
		return profilingEnabled;
	}

	public void setProfilingEnabled(boolean profilingEnabled) {
		this.profilingEnabled = profilingEnabled;
	}
}
