package telefront;

import org.dom4j.Document;

/**
 *
 * @author POLETTII
 * @version 1.0, 06/04/2006
 * @see
 */
public class TelefrontCallbackEvent {
    private Document document;
    private Exception exception;
    private byte[] binaryContent;
    private long threadExecutionId;

    public TelefrontCallbackEvent(Exception exception) {
        this.exception = exception;
    }

    public TelefrontCallbackEvent() {
    	super();
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public byte[] getBinaryContent() {
        return binaryContent;
    }

    public void setBinaryContent(byte[] binaryContent) {
        this.binaryContent = binaryContent;
    }

    public long getThreadExecutionId() {
        return threadExecutionId;
    }

    public void setThreadExecutionId(long threadExecutionId) {
        this.threadExecutionId = threadExecutionId;
    }
}
