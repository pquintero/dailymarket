package logging;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.PropertyConfigurator;

import dailymarket.model.Context;

/**
 * Proporciona acciones de logueo para consola y archivo de salida
 */
public class Logger {
    static {
        initLog4j();
    }

    public static void registerKeyForLevel() {
        Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
            private boolean controlPressed;
            private boolean shiftPressed;
            private boolean f5Pressed;
            private boolean f6Pressed;
            private boolean f7Pressed;
            private boolean f8Pressed;
            private boolean f9Pressed;
            private boolean f10Pressed;

            public void eventDispatched(AWTEvent event) {
                KeyEvent ev = (KeyEvent) event;
                switch (ev.getKeyCode()) {
                case KeyEvent.VK_CONTROL:
                    controlPressed = ev.getID() == 401;
                    break;
                case KeyEvent.VK_SHIFT:
                    shiftPressed = ev.getID() == 401;
                    break;
                case KeyEvent.VK_F5:
                    f5Pressed = ev.getID() == 401;
                    break;
                case KeyEvent.VK_F6:
                    f6Pressed = ev.getID() == 401;
                    break;
                case KeyEvent.VK_F7:
                    f7Pressed = ev.getID() == 401;
                    break;
                case KeyEvent.VK_F8:
                    f8Pressed = ev.getID() == 401;
                    break;
                case KeyEvent.VK_F9:
                	f9Pressed = ev.getID() == 401;
                	break;
                case KeyEvent.VK_F10: 
                	f10Pressed = ev.getID() == 401;
                }

                if (controlPressed && shiftPressed) {
                    if (f5Pressed) {
                        getLogger911().setLevel(Level.WARN);
                        getLogger911().warn("Logueo configurado en nivel WARNING");
                        getLoggerCTI().setLevel(Level.WARN);
                        getLoggerCTI().warn("Logueo configurado en nivel WARNING");
                    } else if (f6Pressed) {
                        getLogger911().setLevel(Level.INFO);
                        getLogger911().info("Logueo configurado en nivel INFO");
                        getLoggerCTI().setLevel(Level.INFO);
                        getLoggerCTI().info("Logueo configurado en nivel INFO");
                    } else if (f7Pressed) {
                        getLogger911().setLevel(Level.DEBUG);
                        getLogger911().info("Logueo configurado en nivel DEBUG");
                        getLoggerCTI().setLevel(Level.DEBUG);
                        getLoggerCTI().info("Logueo configurado en nivel DEBUG");
                    } else if (f8Pressed) {
                        getLogger911().setLevel(Level.ALL);
                        getLogger911().info("Logueo configurado en nivel ALL");
                        getLoggerCTI().setLevel(Level.ALL);
                        getLoggerCTI().info("Logueo configurado en nivel ALL");
                    } else if (f9Pressed) {
                    	Context.getInstance().setProfilingEnabled(true);
                    } else if (f10Pressed) {
                    	Context.getInstance().setProfilingEnabled(false);
                    }
                }
            }
        }, AWTEvent.KEY_EVENT_MASK);
    }

    public static org.apache.log4j.Logger getLoggerCTI() {
        return org.apache.log4j.Logger.getLogger("CTI");
    }

    public static org.apache.log4j.Logger getLogger911() {
        return org.apache.log4j.Logger.getLogger("F911");
    }

    private static void initLog4j() {
        String path = System.getProperty("user.home");
        boolean created = false;
        File dir = new File(path + File.separator + "F911" + File.separator + "logs");
        if (!dir.exists())
            created = dir.mkdirs();
        else
            created = true;

        if (created) {
            System.setProperty("CTI", path + File.separator + "F911" + File.separator + "logs" + File.separator
                            + "cti.log");
            System.setProperty("F911", path + File.separator + "F911" + File.separator + "logs" + File.separator
                            + "F911.log");
            System.setProperty("PROFILING", path + File.separator + "F911" + File.separator + "logs" + File.separator
                    + "PROFILING.log");            
        } else
            throw new RuntimeException("No se pudo crear el directorio " + path + File.separator + "F911"
                            + File.separator + "logs" + File.separator);

        InputStream is = Logger.class.getClassLoader().getResourceAsStream("log4j.properties");
        Properties ps = new Properties();
        try {
            ps.load(is);
            PropertyConfigurator.configure(ps);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
