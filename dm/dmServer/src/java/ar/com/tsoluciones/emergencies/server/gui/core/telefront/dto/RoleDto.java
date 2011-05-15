package ar.com.tsoluciones.emergencies.server.gui.core.telefront.dto;

/**
 * Created by IntelliJ IDEA.
 * User: fvaleriani
 * Date: 30/08/2005
 * Time: 16:58:30
 * To change this template use File | Settings | File Templates.
 */

import ar.com.tsoluciones.arcom.cor.ServiceException;
import ar.com.tsoluciones.arcom.security.Role;
import ar.com.tsoluciones.arcom.struts.MappingException;
import ar.com.tsoluciones.arcom.struts.MappingHelper;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * Representa un permiso en telefront
 */
public class RoleDto {
    private String idText;
    private String nombreText;
    private String descripcionText;
    private String deletedCheckbox;


    public String getIdText() {
        return idText;
    }

    public void setIdText(String idText) {
        this.idText = idText;
    }

    public String getNombreText() {
        return nombreText;
    }

    public void setNombreText(String nombreText) {
        this.nombreText = nombreText;
    }

    public String getDescripcionText() {
        return descripcionText;
    }

    public void setDescripcionText(String descripcionText) {
        this.descripcionText = descripcionText;
    }


    public String getDeletedCheckbox() {
        return deletedCheckbox;
    }

    public void setDeletedCheckbox(String deletedCheckbox) {
        this.deletedCheckbox = deletedCheckbox;
    }


    /**
     * Mapea este DTO a un usuario
     *
     * @return Usuario
     * @throws ar.com.tsoluciones.arcom.cor.ServiceException Cuando hay un error
     */
    public Role toRole(Role role) throws ServiceException {
        role.setId(MappingHelper.parseLong(this.getIdText(), false));
        role.setName(MappingHelper.parseString(this.getNombreText(), true));
        role.setDescription(MappingHelper.parseString(this.getDescripcionText(), true));
        role.setDeleted(MappingHelper.parseBoolean(this.getDeletedCheckbox()));


        return role;
    }

    /**
     * Crea un DTO a partir de un usuario
     *
     * @return DTO
     * @throws MappingException Cuando hay un error al mapear
     */
    public static RoleDto fromRole(Role role) throws MappingException {
        RoleDto roleDto = new RoleDto();

        roleDto.setIdText(MappingHelper.formatLong(role.getId(), false));
        roleDto.setNombreText(MappingHelper.formatString(role.getName(), true));
        roleDto.setDescripcionText(MappingHelper.formatString(role.getDescription(), true));
        roleDto.setDeletedCheckbox(MappingHelper.formatBoolean(role.isDeleted()));


        return roleDto;
    }

    /**
     * Serializa a un XML de Form Telefront
     *
     * @return XML con los datos
     */
    public String toTelefrontForm() {
        try {
            Document document = DocumentHelper.parseText("<form></form>");

            Element rootElement = document.getRootElement();

            rootElement.addElement("control").addAttribute("name", "idText").setText(this.getIdText());
            rootElement.addElement("control").addAttribute("name", "nombreText").setText(this.getNombreText());
            rootElement.addElement("control").addAttribute("name", "descripcionText").setText(this.getDescripcionText());
            rootElement.addElement("control").addAttribute("name", "deletedCheckbox").setText(this.getDeletedCheckbox());

            return document.asXML();
        } catch (DocumentException e) {
            throw new RuntimeException("Error al intentar crear documento base");
        }

    }
}
