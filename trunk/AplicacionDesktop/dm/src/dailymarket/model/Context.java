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

	private Cajero currentUser;
	private boolean profilingEnabled;

	public void setCurrentUser(Cajero user) {
		currentUser = user;
	}

	public Cajero getCurrentUser() {
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
