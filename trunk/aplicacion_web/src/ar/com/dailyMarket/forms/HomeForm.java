package ar.com.dailyMarket.forms;


public class HomeForm extends BaseForm{
	
	private static final long serialVersionUID = 1L;
	
	private String VirtualDispatchName;
	public String collectionName;
	
	public String getVirtualDispatchName() {
		return VirtualDispatchName;
	}

	public void setVirtualDispatchName(String virtualDispatchName) {
		VirtualDispatchName = virtualDispatchName;
	}

	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}			
}
