package ar.com.tsoluciones.arcom.security.handlers;

import ar.com.tsoluciones.arcom.hibernate.AliasToResultRowTransformer;
import ar.com.tsoluciones.arcom.hibernate.CustomNamingStrategy;
import ar.com.tsoluciones.arcom.hibernate.ResultRow;
import ar.com.tsoluciones.arcom.hibernate.SessionContext;
import ar.com.tsoluciones.arcom.hibernate.Transactional;
import ar.com.tsoluciones.arcom.logging.Log;
import ar.com.tsoluciones.arcom.security.Security;
import ar.com.tsoluciones.arcom.security.crypto.Encryption;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import java.io.Serializable;

/**
 * <p>
 * Implementacion de control de login (acceso) contra base de datos
 * </p>
 */
public class SecurityHandlerDatabaseImpl implements SecurityHandler, Serializable {
	private static String tabla = "";

	static {
		tabla = CustomNamingStrategy.INSTANCE.classToTableName(Security.class.getName());
	}

	/**
	 * Creates a new instance of SecurityHandlerDatabaseImpl
	 */
	public SecurityHandlerDatabaseImpl() {
		super();
	}

	@Transactional
	public boolean authenticate(String username, String password) {
		boolean valid = false;
		try {
			SQLQuery q = SessionContext.currentSession().createSQLQuery("SELECT password FROM " + tabla + " WHERE username = :username");
			q.setParameter("username", username);
			q.setResultTransformer(new AliasToResultRowTransformer());
			ResultRow res = (ResultRow)q.uniqueResult();
			if(res != null) {
				valid = Encryption.MD5(password).equals(res.getString("password"));
			}
		} catch (HibernateException he) {
			Log.security.error(String.format("Hubo un error al intentar autenticar el usuario %s, MSG: %s", username,
							he.getMessage()), he);
		}
		return valid;
	}

	/**
	 * Setea el password del usuario. El seteo del password es una operación
	 * atomica, confirma la transaccion en contexto
	 *
	 * @param username
	 *            Nombre del usuario
	 * @param password
	 *            Password
	 * @return -1 si no se pudo hacer el update, <> -1 si se pudo
	 */
	@Transactional
	public int setPassword(String username, String password) {
		int updated = -1;
		Session currentSession = SessionContext.currentSession();
		try {
			SQLQuery q = currentSession.createSQLQuery("SELECT count(*) FROM " + tabla + " WHERE username = :username");
			q.setParameter("username", username);
			if (q.list().size() > 0) {
				long userCount = ((Number)q.uniqueResult()).longValue();
				StringBuilder sql  = new StringBuilder();
				if (userCount > 0)
					sql.append("UPDATE ").append(tabla).append(" SET password = :password WHERE username = :username");
				else
					sql.append("INSERT INTO ").append(tabla).append( " (password, username) VALUES (:password, :username)");
				SQLQuery insertOrUpdate = currentSession.createSQLQuery(sql.toString());
				insertOrUpdate.setParameter("username", username);
				insertOrUpdate.setParameter("password", Encryption.MD5(password));
				updated = insertOrUpdate.executeUpdate();
			}
		} catch (HibernateException he) {
		    rollback(currentSession, he);
		}
		return updated;
	}

	@Transactional
	public int delete(String username) {
		int updated = -1;
        Session currentSession = SessionContext.currentSession();
		try {
		    SQLQuery q = currentSession.createSQLQuery("DELETE FROM " + tabla + " WHERE username = :username");
            q.setParameter("username", username);
            updated = q.executeUpdate();
        } catch (HibernateException he) {
            rollback(currentSession, he);
        }
		return updated;
	}

    private void rollback(Session currentSession, HibernateException he) {
        try {
            Log.security.error("Error al realizar operación", he);
            /*
             * Tengo que hacer esto porque la semántica del método es no lanzar las excepciones si no retornar -1...
             */
            if (currentSession.isOpen() && currentSession.getTransaction().isActive())
                currentSession.getTransaction().rollback();
        } catch (HibernateException e) {
            Log.security.error("No se pudo hacer rollback de la transacción", e);
        }
    }
}
