package dailymarket.model;

import java.util.Iterator;
import java.util.Vector;

import javax.swing.*;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * Permite alojar un item de JComboBox y agregar este al combo
 */
public class JComboBoxItem {
	protected String id;
	protected String description;
	private Vector<String> additionalInfo;

	/**
	 * Llena un combo con un XML estandar de objetos, agregando un item en blacno al principio
	 *
	 * <tag>
	 *   <tag>
	 *     <id>1</id>
	 *     <description>Descripcion</description>
	 *   </tag>
	 * </tag>
	 *
	 * Usando como campos de id y descripcion en el XML "id" y "description"
	 *
	 * @param comboBox El combo
	 * @param document el XML
	 */
	public static void fillCombo(JComboBox comboBox, Document document) {
		fillCombo(comboBox, document, false);
	}

	/**
	 * Llena un combo con un XML estandar de objetos. El XML tiene la siguiente estructura:
	 *
	 * <tag>
	 *   <tag>
	 *     <id>1</id>
	 *     <description>Descripcion</description>
	 *   </tag>
	 * </tag>
	 *
	 * Usando como campos de id y descripcion en el XML "id" y "description"
	 *
	 * @param comboBox El combo
	 * @param document el XML
	 * @param addBlankItem Si true, se agrega un item en blanco al principio del combo
	 */
	public static void fillCombo(JComboBox comboBox, Document document, boolean addBlankItem) {
		fillCombo(comboBox, document, addBlankItem, "id", "description");
	}

	/**
	 * Llena un combo con un XML estandar de objetos. El XML tiene la siguiente estructura:
	 *
	 * <tag>
	 *   <tag>
	 *     <id>1</id>
	 *     <description>Descripcion</description>
	 *   </tag>
	 * </tag>
	 *
	 * @param comboBox El combo
	 * @param document el XML
	 * @param idField Nombre del tag de id
	 * @param descriptionField Nombre del tag de description
	 * @param addBlankItem Si true, se agrega un item en blanco al principio del combo
	 */
	@SuppressWarnings("unchecked")
	public static void fillCombo(JComboBox comboBox, Document document, boolean addBlankItem, String idField, String descriptionField) {
		if(comboBox == null || document == null) {
			return;
		}

		comboBox.removeAllItems();
		if (addBlankItem)
			comboBox.addItem(new JComboBoxItem("", ""));
		if (document.getRootElement() != null) {
			Iterator<Element> iterator = document.getRootElement().elementIterator();
			while (iterator.hasNext()) {
				Element element = iterator.next();

				String id = element.selectSingleNode("./" + idField).getText();
				String description = element.selectSingleNode("./" + descriptionField).getText();

				comboBox.addItem(new JComboBoxItem(id, description));
			}
		}
	}

	/**
	 * Llena un combo con un XML estandar de objetos. El XML tiene la siguiente estructura:
	 *
	 * <tag>
	 *   <tag>
	 *     <id>1</id>
	 *     <description>Descripcion</description>
	 *   </tag>
	 * </tag>
	 *
	 * @param comboBox El combo
	 * @param document el XML
	 * @param idField Nombre del tag de id
	 * @param descriptionField Nombre del tag de description
	 * @param addBlankItem Si true, se agrega un item en blanco al principio del combo
	 * @param selectedField Nombre del tag que indica que objeto va a seleccionar por Default en el combo.
	 */
	@SuppressWarnings("unchecked")
	public static void fillCombo(JComboBox comboBox, Document document, boolean addBlankItem, String idField, String descriptionField, String selectedField) {
		comboBox.removeAllItems();
		if (addBlankItem)
			comboBox.addItem(new JComboBoxItem("", ""));

		Iterator<Element> iterator = document.getRootElement().elementIterator();
		JComboBoxItem itemSelected = null;
		comboBox.addItem(new JComboBoxItem("", "Seleccione"));
		while (iterator.hasNext()) {
			Element element = iterator.next();

			String id = element.selectSingleNode("./" + idField).getText();
			String description = element.selectSingleNode("./" + descriptionField).getText();

			boolean selected = Boolean.valueOf(element.selectSingleNode("./" + selectedField).getText());
			if (selected)
				itemSelected = new JComboBoxItem(id, description);

			comboBox.addItem(new JComboBoxItem(id, description));
		}
		if (itemSelected != null)
			comboBox.setSelectedItem(itemSelected);
		else
			comboBox.setSelectedIndex(0);
	}

	/**
	 * Llena un combo con una Lista de Nodos de un XML de objetos.
	 * Esto posibilitar tener un solo XML con diferentes tipos de Nodos
	 * Osea utilizar en un mismo XML nodos Tipo Agencia, nodos Tipo Roles, etc
	 *  El XML tiene la siguiente estructura:
	 *
	 * <tag>
	 *   <tag>
	 *     <id>1</id>
	 *     <description>Descripcion</description>
	 *   </tag>
	 * </tag>
	 *
	 * @param comboBox El combo
	 * @param nodeList Lista elementos correspondientes a nodos de un XML
	 * @param indicadorSeleccion Indicador de mostrar el la etiqueta  'Seleccione'
	 * @param idField Nombre del tag de id
	 * @param descriptionField Nombre del tag de description o name
	 */
	public static void fillCombo(JComboBox comboBox, java.util.List<Element> nodeList, boolean indicadorSeleccion, String idField, String descriptionField) {
		comboBox.removeAllItems();
		if (indicadorSeleccion)
			comboBox.addItem(new JComboBoxItem("", "Seleccione"));
		if (nodeList.size() > 0)
			for (int x = 0; x < nodeList.size(); x++) {
				Element element = nodeList.get(x);
				String id = element.selectSingleNode("./" + idField).getText();
				String description = element.selectSingleNode("./" + descriptionField).getText();
				comboBox.addItem(new JComboBoxItem(id, description));
			}
	}


	/**
	 * Construye un item sin descripcion, tipicamente para items blancos o para busquedas por id
	 * @param id El id
	 */
	public JComboBoxItem(String id) {
		this.id = id;
		this.description = "";
	}

	/**
	 * Construye un item
	 * @param id El id
	 * @param description La descripcion
	 */
	public JComboBoxItem(String id, String description) {
		this.id = id;
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Vector<String> getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(Vector<String> additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	@Override
	public int hashCode() {
		if (this.getId() == null)
			return 0;
		return this.getId().hashCode();
	}

	@Override
	public boolean equals(Object object) {
		if (object == null || object instanceof JComboBoxItem == false)
			return false;

		JComboBoxItem comboBoxItem = (JComboBoxItem) object;
		if (comboBoxItem.getId() == null)
			return false;
		if (this.getId() == null)
			return false;

		boolean equals = (comboBoxItem.getId().equals(this.getId()));

		// Tomar en cuenta el problema clasico de swing cuando se comparan objetos "distintos" en contenido pero con equals = true
		if (equals) {
			if (this.getDescription() == null || this.getDescription().equals(""))
				this.setDescription(comboBoxItem.getDescription());
			if (comboBoxItem.getDescription() == null || comboBoxItem.getDescription().equals(""))
				comboBoxItem.setDescription(this.getDescription());
		}

		return equals;
	}

	@Override
	public String toString() {
		return description.trim();
	}

	@SuppressWarnings("unchecked")
	public static void fillList(JList list, Document document, String idField, String descriptionField) {

		Vector<JComboBoxItem> datos = new Vector<JComboBoxItem>();
		int tito = 0;
		int selected = 0;
		if (document.getRootElement() != null) {
			Iterator<Element> iterator = document.getRootElement().elementIterator();
			while (iterator.hasNext()) {
				Element element = iterator.next();

				String id = element.selectSingleNode("./" + idField).getText();
				String description = element.selectSingleNode("./" + descriptionField).getText();
				String mio = element.selectSingleNode("./mia").getText();
				if(mio.equalsIgnoreCase("Si"))
					selected = tito;
				datos.add(new JComboBoxItem(id, description));
				tito++;
			}
		}
		list.setListData(datos);
		list.setSelectedIndex(selected);
	}

}
