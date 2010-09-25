package ar.com.tsoluciones.emergencies.server.gui.core.telefront.dto;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import ar.com.tsoluciones.arcom.cor.ServiceException;
import ar.com.tsoluciones.arcom.security.User;
import ar.com.tsoluciones.arcom.struts.MappingException;
import ar.com.tsoluciones.arcom.struts.MappingHelper;

/**
 * Representa un usuario en telefront
 */
public class UserDto
{
	private String idText;
	private String usernameText;
	private String legajoText;
	private String firstnameText;
	private String lastnameText;
	private String titleText;
	private String deletedCheckbox;
	private String sysadminCheckbox;
	private String agentIdText;
	private String agentPasswordText;
    private String agencyId;
    private String agency;
    private String agencyCode;


  public String getIdText()
	{
		return idText;
	}

	public void setIdText(String idText)
	{
		this.idText = idText;
	}

	public String getLegajoText()
	{
		return legajoText;
	}

	public void setLegajoText(String legajoText)
	{
		this.legajoText = legajoText;
	}

	public String getFirstnameText()
	{
		return firstnameText;
	}

	public void setFirstnameText(String firstnameText)
	{
		this.firstnameText = firstnameText;
	}

	public String getLastnameText()
	{
		return lastnameText;
	}

	public void setLastnameText(String lastnameText)
	{
		this.lastnameText = lastnameText;
	}

	public String getTitleText()
	{
		return titleText;
	}

	public void setTitleText(String titleText)
	{
		this.titleText = titleText;
	}

	public String getDeletedCheckbox()
	{
		return deletedCheckbox;
	}

	public void setDeletedCheckbox(String deletedCheckbox)
	{
		this.deletedCheckbox = deletedCheckbox;
	}

	public String getSysadminCheckbox()
	{
		return sysadminCheckbox;
	}

	public void setSysadminCheckbox(String sysadminCheckbox)
	{
		this.sysadminCheckbox = sysadminCheckbox;
	}

	public String getAgentIdText()
	{
		return agentIdText;
	}

	public void setAgentIdText(String agentIdText)
	{
		this.agentIdText = agentIdText;
	}

	public String getAgentPasswordText()
	{
		return agentPasswordText;
	}

	public void setAgentPasswordText(String agentPasswordText)
	{
		this.agentPasswordText = agentPasswordText;
	}

	public String getUsernameText()
	{
		return usernameText;
	}

  public String getAgencyId() {
    return agencyId;
  }

  public String getAgency() {
    return agency;
  }

  public void setUsernameText(String usernameText)
	{
		this.usernameText = usernameText;
	}

  public void setAgencyId(String agencyId) {
    this.agencyId = agencyId;
  }

  public void setAgency(String agency) {
    this.agency = agency;
  }

  public String getAgencyCode() {
	return agencyCode;
  }

  public void setAgencyCode(String agencyCode) {
	this.agencyCode = agencyCode;
  }

/**
	 * Mapea este DTO a un usuario
	 * @return Usuario
	 * @throws ServiceException Cuando hay un error
	 */
	public User toUser(User user) throws ServiceException
	{
//		user.setId(MappingHelper.parseLong(this.getIdText(), false));
//		user.setUsername(MappingHelper.parseString(this.getUsernameText(), true));
//		user.setLegajo(MappingHelper.parseString(this.getLegajoText(), false));
//		user.setFirstName(MappingHelper.parseString(this.getFirstnameText(), false));
//		user.setLastName(MappingHelper.parseString(this.getLastnameText(), false));
//		user.setTitle(MappingHelper.parseString(this.getTitleText(), false));
//		user.setDeleted(MappingHelper.parseBoolean(this.getDeletedCheckbox()));


		return user;
	}

	/**
	 * Crea un DTO a partir de un usuario
	 * @param user Usuario
	 * @return DTO
	 * @throws MappingException Cuando hay un error al mapear
	 */
	public static UserDto fromUser(User user) throws MappingException
	{
		UserDto userDto = new UserDto();

//		userDto.setIdText(MappingHelper.formatLong(user.getId(), false));
//		userDto.setUsernameText(MappingHelper.formatString(user.getUsername(), true));
//		userDto.setLegajoText(MappingHelper.formatString(user.getLegajo(), false));
//		userDto.setFirstnameText(MappingHelper.formatString(user.getFirstName(), false));
//		userDto.setLastnameText(MappingHelper.formatString(user.getLastName(), false));
//		userDto.setTitleText(MappingHelper.formatString(user.getTitle(), false));
//		userDto.setDeletedCheckbox(MappingHelper.formatBoolean(user.isDeleted()));
//		userDto.setSysadminCheckbox(MappingHelper.formatBoolean(user.isSysAdmin()));

		return userDto;
	}

	/**
	 * Serializa a un XML de Form Telefront
	 * @return XML con los datos
	 */
	public String toTelefrontForm()
	{
		try
		{
			Document document = DocumentHelper.parseText("<form></form>");

			Element rootElement = document.getRootElement();

			rootElement.addElement("control").addAttribute("name", "idText").setText(this.getIdText());
			rootElement.addElement("control").addAttribute("name", "legajoText").setText(this.getLegajoText());
			rootElement.addElement("control").addAttribute("name", "usernameText").setText(this.getUsernameText());
			rootElement.addElement("control").addAttribute("name", "firstnameText").setText(this.getFirstnameText());
			rootElement.addElement("control").addAttribute("name", "lastnameText").setText(this.getLastnameText());
			rootElement.addElement("control").addAttribute("name", "titleText").setText(this.getTitleText());
			rootElement.addElement("control").addAttribute("name", "deletedCheckbox").setText(this.getDeletedCheckbox());
			rootElement.addElement("control").addAttribute("name", "sysadminCheckbox").setText(this.getSysadminCheckbox());
			rootElement.addElement("control").addAttribute("name", "agentIdText").setText(this.getAgentIdText());
			rootElement.addElement("control").addAttribute("name", "agentPasswordText").setText(this.getAgentPasswordText());
            rootElement.addElement("control").addAttribute("name", "agencyId").setText(this.getAgencyId());
            rootElement.addElement("control").addAttribute("name", "agency").setText(this.getAgency());
            rootElement.addElement("control").addAttribute("name", "agencyCode").setText(this.getAgencyCode());

			return document.asXML();
		}
		catch(DocumentException e)
		{
			throw new RuntimeException("Error al intentar crear documento base");
		}

	}
}
