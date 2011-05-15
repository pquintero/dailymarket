package ar.com.tsoluciones.arcom.security;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import ar.com.tsoluciones.arcom.hibernate.LogicDelete;

/**
 * <p>
 * Roles o perfiles que pueden tomar los usuarios del sistema. Puede ser util
 * por ejemplo, para identificar diferentes tipos de usuarios dentro el sistema
 * y tambien es util para asignar un conjunto de permisos
 * </p>
 *
 * @author Andres Herrera &lt;aherrera@artech-consulting.com&gt;
 * @version 2.0, 14/05/2004, Hibernate version including Permissions per Role
 * @see ar.com.tsoluciones.arcom.security.User User
 * @see ar.com.tsoluciones.arcom.security.Permission Permission
 * @since 0.1 tsoluciones
 */

@XmlAccessorType(XmlAccessType.NONE)
public class Role implements Comparable<Role>, LogicDelete, Serializable {
    private Long id;

    @XmlAttribute @XmlID
    private String name;
    @XmlAttribute
    private String description;
    @XmlElementWrapper(name = "permissions")
    @XmlElement(name = "permission") @XmlIDREF
    private Set<Permission> permissions;

    private Set<User> users;

    private boolean deleted;

    /**
     * <p>
     * Constructor por defecto de Roles
     * </p>
     *
     * @author
     * @version 2.0
     */
    public Role() {
        this.permissions = new HashSet<Permission>();
    }

    /**
     * <p>
     * Metodo get del ID
     * </p>
     *
     * @return Long ID
     * @author
     * @version 2.0
     */
    public Long getId() {
        return id;
    }

    /**
     * <p>
     * Metodo set del ID
     * </p>
     *
     * @author
     * @version 2.0
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * <p>
     * Metodo get del Nombre
     * </p>
     *
     * @return String Name
     * @author
     * @version 2.0
     */
    public String getName() {
        return name;
    }

    /**
     * <p>
     * Metodo set del Nombre
     * </p>
     *
     * @param String
     *            name
     * @author
     * @version 2.0
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>
     * Returns the set of permission either directly associated with this user
     * or associated with the roles associated with the user.
     * </p>
     *
     * @param Set
     *            permission
     * @author
     * @version 2.0
     */
    public Set<Permission> getPermissions() {
        return permissions;
    }

    /**
     * <p>
     * Setea los permisos correspondientes al Role
     * </p>
     *
     * @param Set
     *            permission
     * @author
     * @version 2.0
     */
    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    /**
     * <p>
     * Metodo que informa si el role tiene o no un determinado permiso
     * </p>
     *
     * @param Permission
     *            permission
     * @return boolean true = existe el permiso false = no existe el permiso
     * @author
     * @version 2.0
     */
    public boolean hasPermission(Permission permission) {
        Set<Permission> perm = getPermissions(); //
        if (perm.contains(permission))
            return true;

        // The role do not contain permissions;
        return false;
    }

    /**
     * <p>
     * Agrega uun permiso a ll conjunto de permisos existentes
     * </p>
     *
     * @param Permission
     *            permission
     * @author
     * @version 2.0
     */
    public void addPermission(Permission permission) {
        permissions.add(permission);
    }

    /**
     * <p>
     * Quita uun permiso de los existentes en el conjunto
     * </p>
     *
     * @param Set
     *            permission
     * @author
     * @version 2.0
     */
    public void removePermission(Permission permission) {
        permissions.remove(permission);
    }

    /**
     * <p>
     * Devuelve la descripcion del rol
     * </p>
     *
     * @return String description
     * @author
     * @version 2.0
     */
    public String getDescription() {
        return description;
    }

    /**
     * <p>
     * Setea la descripcion del rol
     * </p>
     *
     * @param String
     *            description
     * @author
     * @version 2.0
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * <p>
     * Devuelve el conjuno de usuarios
     * </p>
     *
     * @return Set users
     * @author
     * @version 2.0
     */
    public Set<User> getUsers() {
        return users;
    }

    /**
     * <p>
     * Setea los usuarios del rol
     * </p>
     *
     * @param Set
     *            users
     * @author
     * @version 2.0
     */
    public void setUsers(Set<User> users) {
        this.users = users;
    }

    /**
     * <p>
     * Informa si el Rol esta borrado o no
     * </p>
     *
     * @return boolean true = Se encuentra borrado false = No se encuentra
     *         borrado
     * @author
     * @version 2.0
     */
    public boolean isDeleted() {
        return deleted;
    }

    /**
     * <p>
     * Marca como borrado o no un rol, es decir lo borra o no logicamente
     * </p>
     *
     * @param boolean
     *            deleted
     * @author
     * @version 2.0
     */
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * <p>
     * Compara dos roles
     * </p>
     *
     * @param Object
     *            El objeto a ser comparado
     * @return int 0 = equals <0 si el argumento es mayor lexicograficamente que
     *         el id >0 si el argumento es menor lexicograficamente que el id
     * @author
     * @version 2.0
     */
    public int compareTo(Role obj) {
        return this.id.compareTo(obj.getId());
    }

    /**
     * <p>
     * HashCode del username del usuario. Should override hashcode() to make it
     * really usable as a key in Hash collections
     * </p>
     *
     * @return boolean
     * @author
     * @version 2.0
     */
    @Override
	public boolean equals(Object obj) {
        if (!(obj instanceof Role))
            return false;
        return getName().equals(((Role) obj).getName());
    }

    /**
     * <p>
     * HashCode del username del usuario. Should override hashcode() to make it
     * really usable as a key in Hash collections
     * </p>
     *
     * @return int
     * @author
     * @version 2.0
     */
    @Override
	public int hashCode() {
        return getName().hashCode();
    }
    
    
	public Document toXml() {

		Document doc = DocumentHelper.createDocument();
		doc.setRootElement(DocumentHelper.createElement("role"));
		Element root = doc.getRootElement();
		root.addElement("id").setText(getId().toString());
		root.addElement("name").setText(getName());
		root.addElement("description").setText(getDescription());
		
		return doc;
	}
}
