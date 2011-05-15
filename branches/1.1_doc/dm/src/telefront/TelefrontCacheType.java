package telefront;
import org.dom4j.Document;

class TelefrontCacheType {
	private String myClass;
	private String myMethod;
	private Object[] myParameters;
	private Document doc;

	public TelefrontCacheType() {
		this.setMyClass(null);
		this.setMyMethod(null);
		this.setMyParameters(null);
		this.setDoc(null);
	}
	public Document getDoc() {
		return doc;
	}
	public void setDoc(Document doc) {
		this.doc = doc;
	}
	public String getMyClass() {
		return myClass;
	}
	public void setMyClass(String myClass) {
		this.myClass = myClass;
	}
	public String getMyMethod() {
		return myMethod;
	}
	public void setMyMethod(String myMethod) {
		this.myMethod = myMethod;
	}
	public Object[] getMyParameters() {
		return myParameters;
	}
	public void setMyParameters(Object[] myParameters) {
		this.myParameters = myParameters;
	}


}
