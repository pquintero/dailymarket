package ar.com.tsoluciones.util;

import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

/**
 * Clase utilitaria para hacer casteos que de otra manera producirían "unchecked warnings".
 */
public class Cast {

    /**
     * Castea una colección. Si las assertions están habilitadas, hace el chequeo iterando
     */
    @SuppressWarnings("unchecked")
    public static <T> Collection<T> castCollection(Class<T> clase, Collection<?> col) {
        if (assertsEnabled())
            chequear(clase, col);

        return (Collection<T>) col;
    }

    /**
     * Castea una colección. Si las assertions están habilitadas, hace el chequeo iterando
     */
    @SuppressWarnings("unchecked")
    public static <T> Enumeration<T> castEnumeration(Class<T> clase, Enumeration<?> col) {
        if (assertsEnabled())
            chequear(clase, col);

        return (Enumeration<T>) col;
    }


    /**
     * Castea una lista. Si las assertions están habilitadas, hace el chequeo iterando
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> castList(Class<T> clase, List<?> col) {
        if (assertsEnabled())
            chequear(clase, col);

        return (List<T>) col;
    }

    /**
     * Castea un mapa. Si las assertions están habilitadas, hace el chequeo iterando
     */
    @SuppressWarnings("unchecked")
    public static <T,R> Map<T,R> castMap(Class<T> claseT, Class<R> claseR, Map<?,?> col) {
        if (assertsEnabled())
            chequear(claseT, claseR, col);

        return (Map<T,R>) col;
    }

    private static <T> void chequear(Class<T> clase, Enumeration<?> e) {
        while (e.hasMoreElements()) {
            Object obj = e.nextElement();
            if (!clase.isAssignableFrom(obj.getClass()))
                throw new AssertionError(getErrorCasteo(clase, obj.getClass()));
        }
    }

    /**
     * Hace un chequeo del tipo real de los elementos de una colección recorriéndola
     * @param clase Supuesta clase de los elementos de la colección
     * @throws AssertionError En caso de que algún elemento no sea del tipo esperado
     */
    private static <T> void chequear(Class<T> clase, Collection<?> col) {
        for (Object obj : col) {
            if (!clase.isAssignableFrom(obj.getClass()))
                throw new AssertionError(getErrorCasteo(clase, obj.getClass()));
        }
    }

    /**
     * Hace un chequeo del tipo real de los elementos de un mapa recorriéndolo
     * @param claseT Clase correspondiente a la clave del Mapa
     * @param claseR Clase correspondiente al valor del Mapa
     * @param map Elementos del mapa
     * @throws AssertionError En caso de que algún elemento no sea del tipo esperado
     */
    private static <T,R> void chequear(Class<T> claseT, Class<R> claseR, Map<?,?> map) {
        for (Map.Entry<?,?> entry : map.entrySet()) {
            if (!claseT.isAssignableFrom(entry.getKey().getClass()))
                throw new AssertionError(getErrorCasteo(claseT, entry.getKey().getClass()));

            if (!claseR.isAssignableFrom(entry.getKey().getClass()))
                throw new AssertionError(getErrorCasteo(claseR, entry.getValue().getClass()));
        }
    }

    private static String getErrorCasteo(Class<?> deseada, Class<?> real) {
        return String.format("No se puede castear de %s a %s", real.getName(), deseada.getName());
    }

    /**
     * Informa si las aserciones están habilitadas
     * Si están habilitadas, se realiza la asignación, sino, esa línea
     * se salta, y rta se retorna en false 
     */
    private static boolean assertsEnabled() {
        boolean rta = false;
        assert rta = true;
        return rta;
    }

    /**
     * Genera una instancia de un enum a partir de la clase del enum y de una
     * cadena. Este método delega la ejecución en java.lang.Enum.valueOf,
     * excepto que no produce unchecked warning y acepta valores nulos
     */
    @SuppressWarnings("unchecked")
    public static Enum getValorEnum(Class clase, String valor) {
        return valor != null ? Enum.valueOf(clase, valor) : null;
    }

    /**
     * Genera una instancia de un enum a partir de la clase parametrizada del
     * enum y de una cadena. Este método delega la ejecución en java.lang.Enum.valueOf,
     * excepto que acepta valores nulos
     */
    public static <T extends Enum<T>> T getEnum(Class<T> clase, String valor) {
        return valor != null ? Enum.valueOf(clase, valor) : null;
    }
}
