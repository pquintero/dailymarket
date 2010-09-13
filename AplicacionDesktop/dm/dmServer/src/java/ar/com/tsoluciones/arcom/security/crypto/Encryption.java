package ar.com.tsoluciones.arcom.security.crypto;

import ar.com.tsoluciones.arcom.logging.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Copyright (c) Telefonica Soluciones
 * Todos los derechos reservados
 * <p/>
 * Class description....
 * 
 * @author Andres Herrera - aherrera@artech-consulting.com
 * @version 1.0 - 21/04/2004 15:12:24
 * @see 
 * @since 0.2,
 */
public class Encryption {
    public static String MD5 (String plain) {
        return digest (plain, "MD5");
    }

    public static String SHA (String plain) {
        return digest (plain, "SHA");
    }

    public static String digest (String plain, String algorithm) {
        byte [] buffer = plain.getBytes();
        StringBuffer sb = new StringBuffer();
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            buffer = digest.digest(buffer);
            for (int i = 0; i < buffer.length; i++)
                sb.append(buffer[i]);
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            Log.util.fatal ("No such algorithm" + e);
            throw new RuntimeException (e.getMessage());
        }
    }

    public static void main (String args[]) {
        System.out.println ("avaz --->" + MD5("avaz"));//operador
        System.out.println ("csanchez --->" + MD5("csanchez"));//supervisor
        System.out.println ("sysadmin --->" + MD5("sysadmin"));
    }

}
