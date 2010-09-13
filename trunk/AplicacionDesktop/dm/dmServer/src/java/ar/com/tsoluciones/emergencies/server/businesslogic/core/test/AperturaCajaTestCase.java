package ar.com.tsoluciones.emergencies.server.businesslogic.core.test;

/**
 * LoginServiceTestCase.java
 * Copyright (c) Telefónica Soluciones
 * Todos los derechos reservados.
 */
import junit.framework.TestCase;
import ar.com.tsoluciones.emergencies.server.businesslogic.core.service.AperturaCajaService;

/**
 * <p/>
 * Prueba de la clase LoginService
 * </p>
 *
 * @author  atoranzo
 * @version 31/05/2005, 16:35:54
 * @see
 */
public class AperturaCajaTestCase extends TestCase
{
    /**
     * <p/>Prueba el método login()</p>
     * @throws Exception
     */
    public void testLogin() throws Exception
    {
        AperturaCajaService loguinService = new AperturaCajaService();
        boolean resultado = loguinService.abrirCaja("", "", "", "");

        System.out.println("Resultado del login: " + resultado);
    }

}
