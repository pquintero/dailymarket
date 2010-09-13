package ar.com.tsoluciones.util;

/**
 * Clase utiliria con métodos para operar sobre cadenas de texto.
 */
public class Text {

    /**
     * Se escapan los campos de texto que serán destino de filtros por texto libre dado que si se indican
     * apóstrofos y se usan literales en las queries los mismos cierran una expresión y por ende truncan la idea inicial.
     * Si se permite es una vulnerabilidad común que da lugar al concepto de sql injection.
     * @param in Cadena a escapar
     * @return Cadena espada
     */
    public static String escapeApostrophe(String in) {
        return in != null ? in.replace("'", "''"): null;
    }
}
