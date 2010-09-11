package telefront;

import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import logging.Logger;

import org.dom4j.Document;
import org.dom4j.Element;

import dailymarket.model.Cast;
import dailymarket.model.JComboBoxItem;
import dailymarket.model.JRadioButtonExtended;

/**
 * Decorator de un container, que permite llenar los controles del mismo
 * con la devolucion de una query telefront
 */
/**
 * @author avantarioj
 * 
 */
public class TelefrontForm {
	private final Component[] componentArray;

	/**
	 * Construye el decorator
	 * 
	 * @param container
	 *            Container decorado por este TelefrontForm
	 */
	public TelefrontForm(Container container) {
		this.componentArray = getPlainComponentArray(container).toArray(new Component[0]);
		Arrays.sort(componentArray, new ComponentComparator());
	}

	/**
	 * "Aplana" un container, pasando todos sus componentes (incluidos aquelos en los contenedores incluidos) a un array
	 * plano, con el objetivo de poder buscarlos mas facilmente
	 * 
	 * @param container
	 *            Container con los controles y otros containers
	 * @return Lista de components
	 */
	private List<Component> getPlainComponentArray(Container container) {
		Component[] componentArray = container.getComponents();

		List<Component> list = new ArrayList<Component>();

		for (int i = 0; i < componentArray.length; i++) {
			if (componentArray[i].getName() != null)
				list.add(componentArray[i]);

			List<Component> componentList = getPlainComponentArray((Container) componentArray[i]);
			list.addAll(componentList);
		}

		return list;
	}

	/**
	 * Llena los controles de un container con el resultado de una llamada telefront
	 * 
	 * @param container
	 *            Container a rellenar
	 * @param document
	 *            XML con los datos
	 * @deprecated Utilice la sobrecarga sin container
	 */
	@Deprecated
	public void fill(Container container, Document document) {
		Element[] elementArray = Cast.castList(Element.class, document.getRootElement().elements("control")).toArray(
				new Element[0]);

		for (int i = 0; i < elementArray.length; i++) {
			String name = elementArray[i].attributeValue("name");
			String value = elementArray[i].getText();

			Component component = getComponentByName(name);
			if (component == null)
				continue;
			
			if (component instanceof JTextComponent)
				((JTextComponent) component).setText(value);
			if (component instanceof JLabel)
				((JLabel) component).setText(value);
			if (component instanceof JComboBox) {
				JComboBox comboBox = (JComboBox) component;
				comboBox.setSelectedItem(new JComboBoxItem(value));
			}
			if (component instanceof JCheckBox) {
				JCheckBox checkBox = (JCheckBox) component;
				if (value.equalsIgnoreCase("on"))
					checkBox.setSelected(true);
				else
					checkBox.setSelected(false);
			}
			if (component instanceof JRadioButtonExtended) {
				JRadioButtonExtended radioButton = (JRadioButtonExtended) component;
				radioButton.setId(value);
			}
		}
	}

	/**
	 * Llena los controles de un container con el resultado de una llamada telefront
	 * 
	 * @param document
	 *            XML con los datos
	 */
	public void fill(Document document) {
		Element[] elementArray = Cast.castList(Element.class, document.getRootElement().elements()).toArray(
				new Element[0]);

		for (int i = 0; i < elementArray.length; i++) {
			String name = elementArray[i].getQName().getName();
			String value = elementArray[i].getText();

			Component component = getComponentByName(name);
			if (component == null)
				continue;

			if (component instanceof JTextComponent)
				((JTextComponent) component).setText(value);
			if (component instanceof JLabel)
				((JLabel) component).setText(value);
			if (component instanceof JComboBox) {
				JComboBox comboBox = (JComboBox) component;
				comboBox.setSelectedItem(new JComboBoxItem(value));
			}
			if (component instanceof JCheckBox) {
				JCheckBox checkBox = (JCheckBox) component;
				if (value.equalsIgnoreCase("on"))
					checkBox.setSelected(true);
				else
					checkBox.setSelected(false);
			}
			if (component instanceof JRadioButtonExtended) {
				JRadioButtonExtended radioButton = (JRadioButtonExtended) component;
				radioButton.setId(value);
			}
		}
	}

	/**
	 * Habilita y deshabilita un container y todos sus hijos.
	 * 
	 * @param enabled
	 *            Si true, el control y sus hijos se habilitan
	 * @param components
	 *            Componentes que se deben pasar por alto. Compara por nombre
	 */
	public void setEnabled(boolean enabled, JComponent... components) {
		for (int i = 0; i < componentArray.length; i++) {
			if (containsComponent(components, componentArray[i].getName()))
				continue;

			componentArray[i].setEnabled(enabled);
		}
	}

	/**
	 * 
	 * @param enabled
	 * @param components
	 */
	public void setEditable(boolean enabled, JComponent... components) {
		for (Component comp : componentArray) {
			if (containsComponent(components, comp.getName()))
				continue;
			if (comp instanceof JTextField) {
				((JTextField) comp).setEditable(enabled);

			} else {
				comp.setEnabled(enabled);
			}

		}
	}

	/**
	 * Si en el array pasado por parámetro hay un componente con el mismo nombre que componentName, devuelve true
	 * 
	 * @param components
	 *            array de componentes.
	 * @param componentName
	 *            nombre de componente a buscar.
	 * @return
	 */
	private boolean containsComponent(JComponent[] components, String componentName) {
		for (JComponent component : components) {
			if (component != null && componentName.equals(component.getName()))
				return true;
		}
		return false;
	}

	/**
	 * Obtiene un component por su nombre
	 * 
	 * @param name
	 *            Nombre del component
	 * @return Component pedido, o null si no existe un component con el nombre recibido
	 */
	public Component getComponentByName(String name) {
		Container dummyContainer = new Container();
		dummyContainer.setName(name);

		int pos = Arrays.binarySearch(componentArray, dummyContainer, new ComponentComparator());
		if (pos < 0) {
			Logger.getLogger911().debug("Control no encontrado: " + name);
			return null;
		}

		return componentArray[pos];
	}

	/**
	 * Clase que permite buscar un component por su name
	 */
	protected static class ComponentComparator implements Comparator<Component> {
		/**
		 * Compara dos objetos Component
		 * 
		 * @param one
		 *            Objeto Component
		 * @param two
		 *            Objeto Component
		 * @return Ver documentación interfaz Comparator
		 */
		public int compare(Component one, Component two) {
			Component componentOne = one;
			Component componentTwo = two;

			return componentOne.getName().compareToIgnoreCase(componentTwo.getName());
		}
	}
}
