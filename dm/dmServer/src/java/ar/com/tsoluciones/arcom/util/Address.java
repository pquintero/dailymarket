/*
 * Address.java
 *
 * Created on 18 de febrero de 2004, 14:56
 */
package ar.com.tsoluciones.arcom.util;

import java.io.Serializable;

/**
 * Representa una direccion en una ciudad
 */
public class Address implements Serializable
{
    private Long id;
    private String street1;
    private String street2;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private boolean deleted;

    public Address() {
    	//
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet1() {
        return street1;
    }

    public void setStreet1(String street1) {
        this.street1 = street1;
    }

    public String getStreet2() {
        return street2;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}