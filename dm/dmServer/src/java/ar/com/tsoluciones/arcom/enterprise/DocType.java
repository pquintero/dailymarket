package ar.com.tsoluciones.arcom.enterprise;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * Copyright (c) Telefonica Soluciones
 * Todos los derechos reservados
 * <p/>
 * <p/>
 * Representa el tipo de documento de una persona (Por ejemplo, DNI)
 * </p>
 *
 * @author gblanco, modified by despada on 09 Feb 2005
 * @version 1.0 - 14/06/2004 16:23:27
 * @see
 * @since 1.0
 */
@XmlAccessorType(XmlAccessType.NONE)
public class DocType implements Serializable {
    private Long id;

    @XmlAttribute
    private String name;

    @XmlAttribute
    private String longname;

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

    public String getLongname() {
        return longname;
    }

    public void setLongname(String longname) {
        this.longname = longname;
    }

}
