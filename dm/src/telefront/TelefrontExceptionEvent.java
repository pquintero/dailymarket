package telefront;

/**
 * @author POLETTII
 * @version 1.0, 12/04/2006
 * @see
 */
public class TelefrontExceptionEvent {

    private Exception exception;
    private String className;
    private String method;
    private Object parameters[];
    private TelefrontGUI telefront;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    public TelefrontGUI getTelefront() {
        return telefront;
    }

    public void setTelefront(TelefrontGUI telefront) {
        this.telefront = telefront;
    }
}
