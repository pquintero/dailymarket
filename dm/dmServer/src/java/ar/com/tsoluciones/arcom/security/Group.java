package ar.com.tsoluciones.arcom.security;

import java.util.Set;
import java.util.HashSet;

/**
 * Copyright (c) Telefonica Soluciones
 * Todos los derechos reservados
 * <p/>
 * Grupo de usuarios
 *
 * @author Andres Herrera - aherrera@artech-consulting.com
 * @version 1.0 - 17/05/2004 16:59:20
 * @since 1.0, 17/05/2004
 */
public class Group {
  private Long id;
  private String name;
  private Set<User> users;
  public static final Group EVERYBODY = new Group();

  /**
   * <p> Constructor por defecto </p>
   * @author Andres Herrera - aherrera@artech-consulting.com
   * @version 1.0 - May 18, 2004
   */
  public Group() {
    this.users = new HashSet<User>();
  }

  /**
   * <p> Devuelve el ID del grupo </p>
   * @author Andres Herrera - aherrera@artech-consulting.com
   * @version 1.0 - May 18, 2004
   * @return Long - ID - el ID correspondiente al Grupo
   */
  public Long getId() {
    return id;
  }

  /**
   * <p> Setea el ID del grupo </p>
   * @author Andres Herrera - aherrera@artech-consulting.com
   * @version 1.0 - May 18, 2004
   * @param Long - ID - el ID que vamos a setear al Grupo
   */
  @SuppressWarnings("unused")
  private void setId(Long id) {
    this.id = id;
  }

  /**
   * <p> Devuelve el Nombre del grupo </p>
   * @author Andres Herrera - aherrera@artech-consulting.com
   * @version 1.0 - May 18, 2004
   * @return String - name - el Nombre correspondiente al Grupo
   */
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
   * <p> Devuelve conjunto de usuarios deel grgupo </p>
   * @author Andres Herrera - aherrera@artech-consulting.com
   * @version 1.0 - May 18, 2004
   * @return Set - conjunto de usuarios miembros del grupo
   */
  public Set<User> getUsers() {
    return users;
  }

  /**
   * <p> Setea el conjunto de usuarios del grupo </p>
   * @author Andres Herrera - aherrera@artech-consulting.com
   * @version 1.0 - May 18, 2004
   * @param Set - users- el conjunto de usuarios a setear al Grupo
   */
  public void setUsers(Set<User> users) {
    this.users = users;
  }

  /**
   * <p> Agrega un usuario al conjunto de usuarios ya existentes</p>
   * @author Andres Herrera - aherrera@artech-consulting.com
   * @version 1.0 - May 18, 2004
   * @param Set - user- el usuario que se agregara al Grupo
   */
  public void addUsers(User user) {
    this.users.add(user);
  }

  /**
   * <p> Quita un usuario del conjunto de usuarios ya existentes</p>
   * @author Andres Herrera - aherrera@artech-consulting.com
   * @version 1.0 - May 18, 2004
   * @param User - user- el usuario que se quitara del Grupo
   */
  public void removeUsers(User user) {
    this.users.remove(user);
  }

  public void removeAllUsers() {
    this.users.clear();
  }

    @Override
	public boolean equals(Object obj) {
        if (!(obj instanceof Group))
            return false;
        return getName().equals(((Group) obj).getName());
    }


    @Override
	public int hashCode() {
        return getName().hashCode();
    }
}
