package ar.com.dailyMarket.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import ar.com.dailyMarket.model.HourlyBand;
import ar.com.dailyMarket.services.HourlyBandService;
import ar.com.dailyMarket.ui.validator.Validator;

public class HourlyBandAction extends BaseAction {
	
	public ActionForward showFilter (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {    	    
    	initForm((DynaActionForm)form);
    	setHourlyBandRequest(request);
    	return mapping.findForward("filter");
    }             
    
    public ActionForward saveAndBackToCheckPoint (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	HourlyBandService hourlyBandService = new HourlyBandService();
    	ActionErrors errors = validateForm((DynaActionForm)form, request);
    	if (!errors.isEmpty()) {
    		saveErrors(request, errors);
    		setHourlyBandRequest(request);
    		return super.initAction(mapping, form, request, response);
    	}
    	hourlyBandService.save(form);
    	return super.stepBack(mapping, form, request, response);
    } 
    
    public ActionForward executeFilter (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	HourlyBandService hourlyBandService = new HourlyBandService();
    	request.setAttribute("items", hourlyBandService.executeFilter((DynaActionForm)form));    	
    	setHourlyBandRequest(request);
    	return mapping.findForward("filter");
    }
    
    public ActionForward update (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	HourlyBandService hourlyBandService = new HourlyBandService();
    	ActionErrors errors = validateForm((DynaActionForm)form, request);
    	if (!errors.isEmpty()) {
    		saveErrors(request, errors);
    		return mapping.findForward("showDetail");
    	}
    	hourlyBandService.update(form,hourlyBandService.getHourlyBandByPK((Long)((DynaActionForm)form).get("id")));
    	return super.stepBack(mapping, form, request, response);
    }
    
    public ActionForward findByPK (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	HourlyBandService hourlyBandService = new HourlyBandService();
    	setFormProperties((DynaActionForm)form,hourlyBandService.getHourlyBandByPK((Long)((DynaActionForm)form).get("id")));
    	return mapping.findForward("showDetail");
    }
    
    private void initForm(DynaActionForm form) {
    	form.set("name", "");    	
    }
    
    private void setFormProperties(DynaActionForm form, HourlyBand hourlyBand) {
    	form.set("id", hourlyBand.getId());
    	form.set("description", hourlyBand.getDescription());
    	form.set("name", hourlyBand.getName());
    	form.set("initBand", hourlyBand.getInitBand());
    	form.set("endBand", hourlyBand.getEndBand());
    }
    
    private void setHourlyBandRequest (HttpServletRequest request) {
    	HourlyBandService hourlyBandService = new HourlyBandService();
    	request.setAttribute("hourlyBands", hourlyBandService.getAllHourlyBands());
    }
    
    @Override
    public ActionForward redirectCreate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	setHourlyBandRequest(request);
    	return super.redirectCreate(mapping, form, request, response);
    }
    
    public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	HourlyBandService hourlyBandService = new HourlyBandService();
    	hourlyBandService.delete((Long)((DynaActionForm)form).get("id"));
    	((DynaActionForm)form).set("name", "");
    	((DynaActionForm)form).set("id", new Long(-1));
    	return executeFilter(mapping, form, request, response);
    }
    
    private ActionErrors validateForm(DynaActionForm form, HttpServletRequest request) {    	
    	ActionErrors errors = new ActionErrors();
    	Validator.isEmpty(form.get("name"), errors, request, "Nombre");
    	Validator.isEmpty(form.get("description"), errors, request, "Descripción");
    	if ((Integer.parseInt((String)form.get("initBand"))) >= (Integer.parseInt((String)form.get("endBand")))) {
    		errors.add("initBand", new ActionError("errors.incorrectBand"));
    	}
    	return errors;
    }
}
