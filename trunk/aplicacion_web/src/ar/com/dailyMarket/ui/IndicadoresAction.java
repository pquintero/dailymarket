package ar.com.dailyMarket.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import ar.com.dailyMarket.charts.LineChart;
import ar.com.dailyMarket.charts.MSLine;
import ar.com.dailyMarket.services.HourlyBandService;
import ar.com.dailyMarket.services.IndicadoresService;
import ar.com.dailyMarket.services.UserService;

/** FIXME no harcodear fechas? se puede tomar el año de la primer venta? y asi hasta el actual?
 * Ver cache de los graficos, buscar como hacer que la peticion no sea igual, sino pasar un new date como parametro tal como cajeroid y month
 * **/
public class IndicadoresAction extends BaseAction {

    public ActionForward initAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	return mapping.findForward("showIndicadoresHome");
    }
    
/******		VentasPorCajeroMensual		******/
    
    public ActionForward doIndicadoresVentasPorCajeroMensual(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	UserService us = new UserService();
    	HourlyBandService hbs = new HourlyBandService();
    	
    	request.setAttribute("aniosList", StaticData.anios);
    	request.setAttribute("mesesList", StaticData.meses);
    	request.setAttribute("cajerosList", us.getCajeros());
    	request.setAttribute("bandaList", hbs.getAllHourlyBands());
    	return mapping.findForward("showIndicadoresVentasPorCajeroMensualFilter");
    }
    
    //Redirecciona a un jsp con el chart y un volver hacia el filtro
    public ActionForward executeVentasPorCajeroMensual(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	return mapping.findForward("showIndicadoresVentasPorCajeroMensualChart");
    }
    
    public ActionForward getVPCMChart(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		IndicadoresService is = new IndicadoresService();
        LineChart line = is.getVPCMChart((DynaActionForm)form);
        return getXML(line, mapping, form, request, response);
    }
    
/******		VentasPorCajeroAnual		******/
    public ActionForward doIndicadoresVentasPorCajeroAnual(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	UserService us = new UserService();
    	HourlyBandService hbs = new HourlyBandService();
    	
    	request.setAttribute("aniosList", StaticData.anios);
    	request.setAttribute("cajerosList", us.getCajeros());
    	request.setAttribute("bandaList", hbs.getAllHourlyBands());
    	return mapping.findForward("showIndicadoresVentasPorCajeroAnualFilter");
    }
    
    //Redirecciona a un jsp con el chart y un volver hacia el filtro
    public ActionForward executeVentasPorCajeroAnual(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	return mapping.findForward("showIndicadoresVentasPorCajeroAnualChart");
    }
    
    public ActionForward getVPCAChart(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		IndicadoresService is = new IndicadoresService();
        LineChart line = is.getVPCAChart((DynaActionForm)form);
        return getXML(line, mapping, form, request, response);
    }

/******		ComparativaDeVentasPorCajeroMensual		******/
    public ActionForward doIndicadoresComparativaDeVentasPorCajeroMensual(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	UserService us = new UserService();
    	HourlyBandService hbs = new HourlyBandService();
    	
    	request.setAttribute("aniosList", StaticData.anios);
    	request.setAttribute("mesesList", StaticData.meses);
    	request.setAttribute("bandaList", hbs.getAllHourlyBands());
    	
    	((DynaActionForm)form).set("cajerosList",  us.getCajeros());
    	return mapping.findForward("showIndicadoresComparativaDeVentasPorCajeroMensualFilter");
    }
    
    //Redireccionar a un jsp con el chart y un volver hacia el filtro
    public ActionForward executeComparativaDeVentasPorCajeroMensual(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	if(((String[])((DynaActionForm)form).get("cajerosArray")).length < 2) {
    		ActionErrors errors = new ActionErrors();
            errors.add("", new ActionError("errors.cajerosComparativa"));
            saveErrors(request, errors);
            return doIndicadoresComparativaDeVentasPorCajeroMensual(mapping, form, request, response);
    	}
    	request.getSession().setAttribute("cajerosComparar", (String[])((DynaActionForm)form).get("cajerosArray"));
    	return mapping.findForward("showIndicadoresComparativaDeVentasPorCajeroMensualChart");
    }
    
    public ActionForward getCVPCMChart(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		IndicadoresService is = new IndicadoresService();
		String[] vec = (String[])request.getSession().getAttribute("cajerosComparar");
		request.getSession().setAttribute("cajerosComparar", null);
        MSLine msline = is.getCVPCMChart((DynaActionForm)form, vec);
        return getXML(msline, mapping, form, request, response);
    }
    
/******		ComparativaDeVentasPorCajeroAnual		******/
    public ActionForward doIndicadoresComparativaDeVentasPorCajeroAnual(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	UserService us = new UserService();
    	HourlyBandService hbs = new HourlyBandService();
    	
    	request.setAttribute("aniosList", StaticData.anios);
    	request.setAttribute("bandaList", hbs.getAllHourlyBands());
    	((DynaActionForm)form).set("cajerosList",  us.getCajeros());
    	return mapping.findForward("showIndicadoresComparativaDeVentasPorCajeroAnualFilter");
    }
    
    //Redireccionar a un jsp con el chart y un volver hacia el filtro
    public ActionForward executeComparativaDeVentasPorCajeroAnual(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	if(((String[])((DynaActionForm)form).get("cajerosArray")).length < 2) {
    		ActionErrors errors = new ActionErrors();
            errors.add("", new ActionError("errors.cajerosComparativa"));
            saveErrors(request, errors);
            return doIndicadoresComparativaDeVentasPorCajeroAnual(mapping, form, request, response);
    	}
    	request.getSession().setAttribute("cajerosComparar", (String[])((DynaActionForm)form).get("cajerosArray"));
    	return mapping.findForward("showIndicadoresComparativaDeVentasPorCajeroAnualChart");
    }
    
    public ActionForward getCVPCAChart(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		IndicadoresService is = new IndicadoresService();
		String[] vec = (String[])request.getSession().getAttribute("cajerosComparar");
		request.getSession().setAttribute("cajerosComparar", null);
		MSLine msline = is.getCVPCAChart((DynaActionForm)form, vec);
        return getXML(msline, mapping, form, request, response);
    }

}