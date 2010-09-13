/*
 * Phone.java
 *
 * Created on 18 de febrero de 2004, 14:56
 */

package ar.com.tsoluciones.arcom.util;

import java.io.Serializable;

/**
 * Representa un telefono de una persona
 * 
 * @author  Aherrera
 */
public class Phone implements Serializable
{
    private Long id;
    private String countryCode;
    private String areaCode;
    private String prefix;
    private String number;
    private String extension;
    private boolean deleted;

    public Phone() {
    	//
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
