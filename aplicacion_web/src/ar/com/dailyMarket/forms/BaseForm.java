package ar.com.dailyMarket.forms;

import java.util.List;

import org.apache.struts.action.ActionForm;

public class BaseForm extends ActionForm{

	private static final long serialVersionUID = 1L;
	
	public String VirtualDispatchName;
	protected List listPage;
	protected String dsTable;
	protected String[] selected;
	protected String[] dsTableArray;
	protected String collectionName = "";

	public String getVirtualDispatchName() {
		return VirtualDispatchName;
	}
	public void setVirtualDispatchName(String virtualDispatchName) {
		VirtualDispatchName = virtualDispatchName;
	}
	public List getListPage() {
		return listPage;
	}
	public void setListPage(List listPage) {
		this.listPage = listPage;
	}
	public String getDsTable() {
		return dsTable;
	}
	public void setDsTable(String dsTable) {
		this.dsTable = dsTable;
	}
	public String[] getSelected() {
		return selected;
	}
	public void setSelected(String[] selected) {
		this.selected = selected;
	}
	public String[] getDsTableArray() {
		return dsTableArray;
	}
	public void setDsTableArray(String[] dsTableArray) {
		this.dsTableArray = dsTableArray;
	}
	public String getCollectionName() {
		return collectionName;
	}
	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}			
}
