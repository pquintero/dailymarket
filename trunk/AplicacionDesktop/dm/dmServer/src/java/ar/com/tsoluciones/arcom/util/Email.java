package ar.com.tsoluciones.arcom.util;

/*
 * Email.java
 */

import java.io.Serializable;

/**
 * Representa un email de una persona
 * 
 * @author  Aherrera, 18 de febrero de 2004, 14:55
 */
public class Email implements Serializable
{
    private Long id;
    private String name, email;
    private boolean deleted = false;
    
    /** Creates a new instance of Email */
    public Email() {
    	//
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
