package ar.com.tsoluciones.arcom.security;

import ar.com.tsoluciones.arcom.hibernate.LogicDelete;

import java.io.Serializable;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * <p>Esta clase representa a un Permiso del sistema.</p>
 * @author Andres Herrera &lt;aherrera@artech-consulting.com&gt;
 * @version 2.0, 14/05/2004, Hibernate version including Permissions per Role
 * @see ar.com.tsoluciones.arcom.security.User User
 * @see ar.com.tsoluciones.arcom.security.Permission Permission
 * @since 0.1 tsoluciones
 * Change Log
 * version 1.0, 02/02/2004, Initial Version developed by AH
 * version 2.0, 14/05/2004, Hibernate version including Permissions per Role
 * version 3.0, 02/08/2004, Bean without Lifecycle implement,(+LogicDelete) IAP.
 */
@XmlAccessorType(XmlAccessType.NONE)
public class Permission implements Comparable<Permission>, LogicDelete, Serializable {
	private Long id;

    @XmlAttribute @XmlID
	private String name;
    @XmlAttribute
	private String description;

	private boolean deleted;

	private Set<Role> roles;

	private Set<User> users;

	/**
	 * <p> Constructor por defecto de Permission </p>
	 */
	public Permission() {
		//
	}

	/**
	 * <p> Devuelve el ID del Permiso  </p>
	 *
	 * @return Long ID - El ID correspondiente
	 */
	public Long getId() {
		return id;
	}

	/**
	 * <p> Setea el id del permiso </p>
	 *
	 * @param id - El id a ser seteado en el permiso
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * <p> Devuelve el Nombre del Permiso  </p>
	 *
	 * @return name - El nombre correspondiente
	 */
	public String getName() {
		return name;
	}

	/**
	 * <p> Setea el nombre del permiso </p>
	 *
	 * @param name - El nombre a ser seteado en el permiso
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * <p> Devuelve la Descripcion del Permiso  </p>
	 *
	 * @return String description - La dedscripcion correspondiente
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * <p> Setea la dedscripcion del permiso </p>
	 *
	 * @param description - La descripcion a ser seteada en el permiso
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * <p> Informa se el permiso esta borrado loogicamente </p>
	 *
	 * @return boolean deleted -
	 *         true  = El permiso se encuentra borrado logicamente
	 *         false = El permiso NO se encuentra borrado logicamente
	 */
	public boolean isDeleted() {
		return deleted;
	}

	/**
	 * <p> Setea el borrado logico del permiso </p>
	 * @param deleted - true  = Borrado logicamente, false = No borrado logicamente
	 */
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	/**
	 * <p> Compara un objeto Permission con otro determinado</p>
	 *
	 * @return 0 = equals <0 si el argumento es mayor lexicograficamente que el id >0 si el argumento es menor lexicograficamente que el id
	 */
	public int compareTo(Permission obj) {
		return id.compareTo(obj.id);
	}

	/**
	 * <p> Compara el ID del objeto en cuestion con el ID del objeto que se recibe
	 * Should override hashcode() to make it really usable as a key in Hash collections</p>
	 * @return boolean true = equals; false diferentes
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Permission))
			return false;
		return getName().equals(((Permission) obj).getName());
	}

	/**
	 * <p> HashCode del id del Permiso.
	 * Should override hashcode() to make it really usable as a key in Hash collections</p>
	 * @return int
	 */
	@Override
	public int hashCode() {
		return getName().hashCode();
	}

	/**
	 * Emite el nombre de este permiso
	 * @return Nombre del permiso
	 */
	@Override
	public String toString() {
		return name;
	}

	/**
	 * Roles a los cuales se les asigno este permiso. Es el lado inverso de la relacion.
	 */
	public Set<Role> getRoles() {
		return roles;
	}

	/**
	 * Roles a los cuales se les asigno este permiso. Es el lado inverso de la relacion.
	 */
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	/**
	 * Usuarios asignados a este permiso
	 * @return Usuarios asignados a este permiso
	 */
	public Set<User> getUsers() {
		return users;
	}

	/**
	 * Usuarios asignados a este permiso
	 * @param users Usuarios asignados a este permiso
	 */
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	public Document toXml() {

		Document doc = DocumentHelper.createDocument();
		doc.setRootElement(DocumentHelper.createElement("permission"));
		Element root = doc.getRootElement();
		root.addElement("id").setText(getId().toString());
		root.addElement("name").setText(getName());
		root.addElement("description").setText(getDescription());
		
		return doc;
	}

}
