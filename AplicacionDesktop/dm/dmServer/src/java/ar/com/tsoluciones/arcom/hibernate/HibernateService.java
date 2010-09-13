package ar.com.tsoluciones.arcom.hibernate;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.type.Type;

import ar.com.tsoluciones.arcom.logging.Log;
import ar.com.tsoluciones.util.Cast;

/**
 * <p>Clase que proporciona metodos estaticos de ayuda para la persistencia y manejo de objetos
 * con Hibernate</p>
 * <p>Incluye metodos de busqueda, actualizacion y grabacion de objetos genericos.</p>
 * <p>Los objetos utilizados deben estar mapeados para ser aceptados por Hibernate</p>
 *
 * @author Javier Porporatto,Ignacio Poletti
 * @see
 */
public class HibernateService {

    public static final String DEFAULT_CATEGORY_LOG = "org.hibernate";
    public static final String SQL_CATEGORY_LOG = "org.hibernate.SQL";

    /**
     * Busca todos los objetos persistidos de un clase.
     *
     * @param clase tipo de clases a buscar
     * @return lista de objetos
     * @throws HibernateException error al buscar
     */
    public static <T> List<T> findAll(Class<T> clazz) throws HibernateException {
        try {
            return findByCriteria(clazz, CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        } catch (HibernateException he) {
            Log.hibernate.error("No se pudo encontrar la clase " + clazz, he);
            throw he;
        }
    }

    /**
     * Busca todos los objetos persistidos (solo los que no estan borrados logicamente)
     *
     * @param clazz     tipo de clases a buscar
     * @param recursive indica si la condicion de retornar los objetos no borrados se
     *                  aplica a la clase indicada o tambien a sus subobjetos (recursivamente)
     * @return lista de objetos
     * @throws HibernateException error al buscar
     */
    public static <T> List<T> findAllExludeDeleted(Class<T> clazz, boolean recursive) throws HibernateException {
        List<T> all = findAll(clazz);
        for (int i = 0; i < all.size(); i++) {
            Object obj = all.get(i);
            if (deleteInactive(obj, recursive)) {
                all.remove(obj);
                i--;
            }
        }
        return all;
    }

    /**
     * Busca el objeto de la clase especificada y filtra por el campo indicado.
     * Solo retorna el primer valor encontrado (en el caso de ser el campo Unique, retorna el unico objeto posible).
     *
     * @param type  clase a buscar
     * @param field campo a filtrar
     * @param value valor a filtrar
     * @return primer objeto encontrado de la lista, o null si no se encuentra.
     * @throws HibernateException error al buscar
     */
    public static <T> T findByFilterUnique(Class<T> type, String field, String value) throws HibernateException {
        List<T> list = findByFilter(type, field, value);
        if (list == null || list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    /**
     *
     * @param type
     * @param field
     * @param value
     * @return
     * @throws HibernateException
     */
    public static <T> List<T> findByFilterLike(Class<T> type, String field, String value) throws HibernateException {
        try {
            return findByCriteria(type, CriteriaSpecification.DISTINCT_ROOT_ENTITY, Restrictions.ilike(field, value));
        } catch (HibernateException he) {
            Log.hibernate.error("Could not find " + type + " where " + field + " like " + value, he);
            throw he;
        }
    }

    /**
     * Busca el objeto de la clase especificada y filtra por el campo indicado.
     *
     * @param type  clase a buscar
     * @param field campo a filtrar
     * @param value valor a filtrar
     * @return Lista de objetos que concuerdan con la condicion
     * @throws HibernateException error al buscar
     */
    public static <T> List<T> findByFilter(Class<T> type, String field, String value) throws HibernateException {
        try {
            return findByCriteria(type, CriteriaSpecification.DISTINCT_ROOT_ENTITY, Restrictions.eq(field, value));
        } catch (HibernateException he) {
            Log.hibernate.error("Could not find " + type + " where " + field + " = " + value, he);
            throw he;
        }
    }

    /**
     * Busca el objeto de la clase especificada y filtra por el campo de tipo long especificado
     *
     * @param type  clase a buscar
     * @param field campo a filtrar
     * @param value valor a filtrar
     * @return Lista de objetos que concuerdan con la condicion
     * @throws HibernateException error al buscar
     */
    public static <T> List<T> findByFilter(Class<T> type, String field, Long value) throws HibernateException {
        try {
            return findByCriteria(type, CriteriaSpecification.DISTINCT_ROOT_ENTITY, Restrictions.eq(field, value));
        } catch (HibernateException he) {
            Log.hibernate.error("Could not find " + type + " where " + field + " = " + value, he);
            throw he;
        }
    }

    /**
     *
     * @param obj
     * @throws HibernateException
     */
    public static void deleteObject(Object obj) throws HibernateException {
        try {
            Session sess = SessionContext.currentSession();
            sess.delete(obj);
        } catch (HibernateException he) {
            Log.hibernate.error("Could not delete " + obj, he);
            throw he;
        }
    }
    /**
     * Borra lo que filtra la query.
     * @param query Tipo: "DELETE ar.com.package.MiClase As o Where o.id > 67 And o.name != 'pepito' ".
     * @throws HibernateException Si hubo error.
     */
    public static void delete(String query) throws HibernateException {
        try {
            Session sess = SessionContext.currentSession();
            Query q = sess.createQuery(query);
            q.executeUpdate();
        } catch (HibernateException he) {
            Log.hibernate.error("Could not delete " + query, he);
            throw he;
        }
    }

    /**
     *
     * @param obj
     * @throws HibernateException
     */
    public static void updateObject(Object obj) throws HibernateException {
        try {
            Session sess = SessionContext.currentSession();
            sess.saveOrUpdate(obj);
        } catch (HibernateException he) {
            Log.hibernate.error("Could not save or update " + obj, he);
            throw he;
        }
    }

    /**
     * <p> Graba en la BD los objetos que recibe  </p>
     *
     * @param obj - objeto a ser persistido
     */
    public static void saveObject(Object obj) throws HibernateException {
        try {
            Session sess = SessionContext.currentSession();
            sess.save(obj);
        } catch (HibernateException he) {
            Log.hibernate.error("Could not save " + obj, he);
            throw he;
        }
    }

    /**
     * <p> Devuelve un objeto de la Base ded Datos  </p>
     *
     * @param oClass - clase a ser persistido
     * @param id     id - pk dedl objeto
     * @param mode   . Tipo dee lockeo del objeto
     * @return Object - Objeto del tipo de lka clase que se recivio
     */
    public static <T, ID extends Serializable> T getObject(Class<T> clazz, ID id, LockMode mode) throws HibernateException {
        T obj = null;
        try {
            Session sess = SessionContext.currentSession();
            if (mode != null)
                obj = clazz.cast(sess.get(clazz, id, mode));
            else
                obj = clazz.cast(sess.get(clazz, id));
            Hibernate.initialize(obj);
        } catch (HibernateException he) {
            Log.hibernate.error("Could not get " + clazz, he);
            throw he;
        }
        return obj; // null if no matching row exists in the DB
    }

    public static <T, ID extends Serializable> T getObject(Class<T> clazz, ID id) throws HibernateException {
        return getObject(clazz, id, null);
    }

    /**
     * Analiza el arbol de objetos (analizando los atributos y objetos contenidos en las clases Set).
     * De encontrar dentro de un Set un objeto borrado logicamente lo excluye generando
     * un nuevo Set con el resto de los objetos no borrados.
     * En caso de encontrar un atributo del paquete ar.com.tsoluciones.* que se encuentre
     * borrado, lo setea como null.
     *
     * @param obj objeto a analizar recursivamente
     * @return true si el objeto pasado esta borrado logicamente
     */
    public static boolean deleteInactive(Object obj, boolean recursive) {
        return deleteInactive(obj, recursive, new HashSet<Object>());
    }

    /**
     * MOdificado para que no se produzca un stack over flow cuando hay referencia cruzadas entre
     * objetos
     * @param obj
     * @param recursive
     * @param listaObjetos
     */
    @SuppressWarnings("unchecked")
	private static boolean deleteInactive(Object obj, boolean recursive, HashSet<Object> listaObjetos) {
        if (obj == null) {
            return false;
        }
        // para evitar stack over flow
        if (listaObjetos.contains(obj))
            return false;

        listaObjetos.add(obj);

        if (obj instanceof LogicDelete) {
            // chequeo el bean ingresado
            LogicDelete bean = (LogicDelete) obj;

            // si esta borrado retorno
            if (bean.isDeleted()) {
                return true;
            }
        }

        if (!recursive) {
            return false;
        }

        // El bean actual no esta borrado, veo si tiene attribuos de tipo Set

        Method metodos[] = obj.getClass().getMethods();

        //recorro los metodos del objeto
        for (int i = 0; i < metodos.length; i++) {
            Method metodo = metodos[i];

            if (metodo.getName().startsWith("get") &&
                    metodo.getParameterTypes().length == 0) {
                // TODO: Mejorar la busqueda de atributos de tipo "Set"
                if (metodo.getReturnType().getName().indexOf("Set") != -1) {
                    // encontre un metodo que devuelve un Set de objetos

                    HashSet <Object>nuevoSet = new HashSet<Object>();
                    try {
                        // Obtengo el Set original
                        Set set = (Set) metodo.invoke(obj, new Object[0]);

                        if (set != null) {
                            Object[] objetos = set.toArray();

                            // recorro los objetos que tiene el Set
                            for (int j = 0; j < objetos.length; j++) {
                                Object objeto = objetos[j];
                                // me fijo si el objeto contenido en el Set esta borrado logicamente (recursivo)
                                //evito recursividad
                                if (objeto == obj || deleteInactive(objeto, recursive, listaObjetos) == false) {
                                    // el objeto no esta borrado logicamente
                                    // lo agrego al nuevo Set
                                    nuevoSet.add(objeto);
                                }
                            }

                            String nombreMetodoGet = metodo.getName();
                            String nombreMetodoSet = "set" + nombreMetodoGet.substring(3);

                            // ejecuto el metodo setXXX(nuevoSet) cargando el nuevo grupo de objetos
                            Method metodoSet = obj.getClass().getMethod(nombreMetodoSet,
                                    new Class[]{Set.class});
                            metodoSet.invoke(obj, new Object[]{nuevoSet});
                        }

                    } catch (IllegalAccessException e) {
                        e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.
                    } catch (InvocationTargetException e) {
                        e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.
                    }
                } else if (metodo.getReturnType().getPackage() != null) {
                    if (metodo.getReturnType().getPackage().getName().startsWith("ar.com.tsoluciones")) {
                        // encontre un metodo que devuelve una clase de ar.com
                        try {
                            Object objAttribute = metodo.invoke(obj, new Object[0]);
                            // veo si este objeto encontrado esta borrado o no
                            if (objAttribute != obj && deleteInactive(objAttribute, recursive, listaObjetos)) {
                                // esta borrado -> lo seteo en null

                                String nombreMetodoGet = metodo.getName();
                                String nombreMetodoSet = "set" + nombreMetodoGet.substring(3);

                                // ejecuto el metodo setXXX(nuevoSet) seteando en null
                                Method metodoSet = null;
                                try {
                                    metodoSet = obj.getClass().getMethod(nombreMetodoSet,
                                            new Class[]{metodo.getReturnType()});
                                    metodoSet.invoke(obj, new Object[]{null});
                                } catch (NoSuchMethodException e) {
                                    e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.
                                }

                            }

                        } catch (IllegalAccessException e) {
                            e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.
                        } catch (InvocationTargetException e) {
                            e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     *
     * @param clase
     * @param field
     * @param value
     * @param tipo
     * @return
     * @throws HibernateException
     */
    public static <T> List<T> findByFilter(Class<T> clazz, String field, Object value, Type tipo) throws HibernateException {
        try {
            Session sess = SessionContext.currentSession();
            Query q = sess.createQuery("from " + clazz.getName() + " as o where o." + field + " = :filtro");
            q.setParameter("filtro", value, tipo);
            return Cast.castList(clazz, q.list());
        } catch (HibernateException he) {
            Log.hibernate.error("Could not find " + clazz + " where " + field + " = " + value + " for " + tipo, he);
            throw he;
        }
    }

    /**
     *
     * @param clase
     * @param field
     * @param value
     * @param tipo
     * @return
     * @throws HibernateException
     */
    public static <T> List<T> findByFilter(Class<T> clase, String[] field, Object[] value, Type[] tipo) throws HibernateException {
        StringBuilder sql = new StringBuilder();
        Parameters params = new Parameters();
        sql.append("From " + clase.getName() + " As o Where");
        for (int i = 0; i < field.length; i++) {
            sql.append(" o." + field[i] + " = :"+ field[i] +" And");
            params.put(field[i], value[i], tipo[i]);
        }
        sql.delete(sql.length() - 4, sql.length()); //Borra el 'And' último.
        return Cast.castList(clase, findByFilters(sql.toString(), params));
    }

    public static <T> List<T> findByFilters(String hqlArmado, Parameters parameters) throws HibernateException { 
    	return findByFilters(hqlArmado, parameters, null);
    }
    
    /**
    *
    * @param hqlArmado
    * @param value
    * @param tipo
    * @return
    * @throws HibernateException
    */
   @SuppressWarnings("unchecked")
	public static <T> List<T> findByFilters(String hqlArmado, Parameters parameters, ResultTransformer transformer) throws HibernateException {
       try {
           Session sess = SessionContext.currentSession();
           Query q = sess.createQuery(hqlArmado);
           if (transformer != null) 
        	   q.setResultTransformer(transformer);
           if(parameters != null) {
           	for (String key : parameters.keySet()) {
           		if(parameters.getType(key) != null) {
           			q.setParameter(key, parameters.getValue(key), parameters.getType(key));
           		} else {
           			q.setParameter(key, parameters.getValue(key));
           		}
           	}
           }
           return q.list();
       } catch (HibernateException he) {
           Log.hibernate.error("Could not find " + hqlArmado, he);
           throw he;
       }
   }

   /**
   * Retorna una lista de objetos de la clase, que respondan a los parámetros definidos
   *
   * @param clazz
   * @param parameters
   *
   * @return Lista de objetos de la clase clazz
   */
  public static <T> List<T> findByFilters(Class<T> clazz, Parameters parameters) throws HibernateException {
	  StringBuilder query = new StringBuilder("from " + clazz.getName() + " c ");
	  if(!parameters.keySet().isEmpty()) {
		  query.append("where ");
		  for(String key : parameters.keySet()) {
			  query.append("c." + key + " = :" + key + " and ");
		  }
	  }
	  //Le saco el último and antes de pasarlo
      return findByFilters(query.substring(0, query.length() - 4), parameters);
  }

    /**
     *
     * @param hqlArmado
     * @return
     * @throws HibernateException
     */
    public static <T> List<T> findByFilters(String hqlArmado) throws HibernateException {
        return findByFilters(hqlArmado, null);
	}

    /**
     * <p>
     *  Hace una búsqueda por criterios aplicados a una clase persistente. Si no se indica ningún criterio retorna
     *  todas las instancias que existen de esa clase.
     * </p>
     * @param <T> Tipo parametrizado usado para deducir el tipo de retorno de cada elemento en la lista.
     * @param clazz Clase de cada objeto retornado en la lista.
     * @param criterion Parámetros variables de criterios a aplicarle a la consulta.
     * @return List<T> Lista de objetos persistentes que cumplen con las condiciones pasadas.
     * @throws HibernateException si falla la ejecución de la consulta.
     */
    public static <T> List<T> findByCriteria(Class<T> clazz, ResultTransformer transformer, Criterion... criterion) {
        Criteria crit = getCriteria(clazz, criterion);
        crit.setResultTransformer(transformer);
        return Cast.castList(clazz, crit.list());
    }

    /**
     * Hace una búsqueda por criterios en la que se espera que se retorne un solo valor.
     * @param <T> Tipo parametrizado usado para deducir el tipo de retorno de cada elemento en la lista.
     * @param clazz Clase del objeto retornado.
     * @param criterion Parámetros variables de criterios a aplicarle a la consulta.
     * @return  Un objeto del tipo deducido T
     * @throws HibernateException si falla la ejecución de la consulta.
     *         NonUniqueResultException si hay más de un objeto de que cumple con los criterios de la consulta.
     */
    public static <T> T findOneByCriteria(Class<T> clazz, Criterion... criterion) {
        Criteria crit = getCriteria(clazz, criterion);
        return clazz.cast(crit.uniqueResult());
    }

    private static Criteria getCriteria(Class<?> clazz, Criterion... criterion) {
        Criteria crit = SessionContext.currentSession().createCriteria(clazz);
        for (Criterion c : criterion) {
            crit.add(c);
        }
        return crit;
    }
}
