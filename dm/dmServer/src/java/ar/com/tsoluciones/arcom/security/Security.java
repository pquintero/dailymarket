package ar.com.tsoluciones.arcom.security;

/**
 * <p>
 * Representa un password de usuario
 * </p>
 *
 * @author Ariel Clocchiatti
 * @version 1.0
 */

public class Security {

  private String username;
  private String password;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

}