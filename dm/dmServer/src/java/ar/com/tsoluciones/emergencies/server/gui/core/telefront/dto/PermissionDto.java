package ar.com.tsoluciones.emergencies.server.gui.core.telefront.dto;

/**
 * Created by IntelliJ IDEA.
 * User: fvaleriani
 * Date: 30/08/2005
 * Time: 16:58:30
 * To change this template use File | Settings | File Templates.
 */

import ar.com.tsoluciones.arcom.cor.ServiceException;
import ar.com.tsoluciones.arcom.security.Permission;
import ar.com.tsoluciones.arcom.struts.MappingException;
import ar.com.tsoluciones.arcom.struts.MappingHelper;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * Representa un permiso en telefront
 */
public class PermissionDto {
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
     * @throws ServiceException Cuando hay un error
     */
    public Permission toPermission(Permission permission) throws ServiceException {
        permission.setId(MappingHelper.parseLong(this.getIdText(), false));
        permission.setName(MappingHelper.parseString(this.getNombreText(), true));
        permission.setDescription(MappingHelper.parseString(this.getDescripcionText(), true));
        permission.setDeleted(MappingHelper.parseBoolean(this.getDeletedCheckbox()));


        return permission;
    }

    /**
     * Crea un DTO a partir de un usuario
     *
     * @return DTO
     * @throws MappingException Cuando hay un error al mapear
     */
    public static PermissionDto fromPermission(Permission permission) throws MappingException {
        PermissionDto permissionDto = new PermissionDto();

        permissionDto.setIdText(MappingHelper.formatLong(permission.getId(), false));
        permissionDto.setNombreText(MappingHelper.formatString(permission.getName(), true));
        permissionDto.setDescripcionText(MappingHelper.formatString(permission.getDescription(), true));
        permissionDto.setDeletedCheckbox(MappingHelper.formatBoolean(permission.isDeleted()));


        return permissionDto;
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
