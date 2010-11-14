package ar.com.dailyMarket.ui.validator;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.ValidatorUtil;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.validator.FieldChecks;
import org.apache.struts.validator.Resources;


public class Validator extends FieldChecks {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static boolean validateTwoFields(Object bean, ValidatorAction va, Field field, ActionErrors errors, HttpServletRequest request) {
		String value = ValidatorUtil.getValueAsString(bean, field.getProperty());
		String sProperty2 = field.getVarValue("secondProperty");
		String value2 = ValidatorUtil.getValueAsString(bean, sProperty2);

		if (!GenericValidator.isBlankOrNull(value)) {
			try {
				if (!value.equals(value2)) {
					errors.add(field.getKey(),Resources.getActionError(request, va, field));
					return false;
				}
			} catch (Exception e) {
				errors.add(field.getKey(),
				Resources.getActionError(request, va, field));
				return false;
			}
		}
		return true;
	}
	
	public static void isDouble(Object value, ActionErrors errors, HttpServletRequest request, String propertyName, boolean required) {
		if (required) {
			isEmpty(value, errors, request, propertyName);
		}
		if (!value.toString().equals("")) {
			try {
				Double.parseDouble(value.toString());
			} catch (NumberFormatException e) {
				ActionMessage message = new ActionMessage("El campo " + propertyName + " debe ser un número");
				errors.add("propertyName", message);
			}						
		}
	}
	
	public static void isInteger (Object value, ActionErrors errors, HttpServletRequest request, String propertyName, boolean required) {
		if (required) {
			isEmpty(value, errors, request, propertyName);	
		}
		if (!value.toString().equals("")) {				
			try {
				Integer.parseInt(value.toString());
			} catch (NumberFormatException e) {
				ActionError error = new ActionError("errors.isInteger",propertyName);
				errors.add(propertyName, error);
			}
		}
	}
	
	public static void isEmpty(Object value, ActionErrors errors, HttpServletRequest request, String propertyName) {
		if (value == null || value.toString().trim().equals("")) {			
			errors.add(propertyName, new ActionError("errors.isRequired", propertyName));
		}
	}
}
