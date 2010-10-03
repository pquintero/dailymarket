package ar.com.dailyMarket.ui;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import ar.com.dailyMarket.charts.LineChart;
import ar.com.dailyMarket.charts.MSLine;
import ar.com.dailyMarket.services.HourlyBandService;
import ar.com.dailyMarket.services.IndicadoresService;
import ar.com.dailyMarket.services.UserService;

public class IndicadoresAction extends BaseAction {

    public ActionForward initAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	return mapping.findForward("showIndicadoresHome");
    }
    
/******		VentasPorCajeroMensual		******/
    
    public ActionForward doIndicadoresVentasPorCajeroMensual(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	ArrayList<String> anios = new ArrayList();
    	anios.add("2010");
    	anios.add("2009");
    	anios.add("2008");
    	
    	ArrayList<String> meses = new ArrayList();
    	meses.add("Enero");
    	meses.add("Febrero");
    	meses.add("Marzo");
    	meses.add("Abril");
    	meses.add("Mayo");
    	meses.add("Junio");
    	meses.add("Julio");
    	meses.add("Agosto");
    	meses.add("Septiembre");
    	meses.add("Octubre");
    	meses.add("Noviembre");
    	meses.add("Diciembre");
    	
    	UserService us = new UserService();
    	HourlyBandService hbs = new HourlyBandService();
    	
    	request.setAttribute("aniosList", anios);
    	request.setAttribute("mesesList", meses);
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
    	ArrayList<String> anios = new ArrayList();
    	anios.add("2010");
    	anios.add("2009");
    	anios.add("2008");
    	UserService us = new UserService();
    	HourlyBandService hbs = new HourlyBandService();
    	
    	request.setAttribute("aniosList", anios);
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
    	ArrayList<String> anios = new ArrayList();
    	anios.add("2010");
    	anios.add("2009");
    	anios.add("2008");
    	
    	ArrayList<String> meses = new ArrayList();
    	meses.add("Enero");
    	meses.add("Febrero");
    	meses.add("Marzo");
    	meses.add("Abril");
    	meses.add("Mayo");
    	meses.add("Junio");
    	meses.add("Julio");
    	meses.add("Agosto");
    	meses.add("Septiembre");
    	meses.add("Octubre");
    	meses.add("Noviembre");
    	meses.add("Diciembre");
    	
    	UserService us = new UserService();
    	HourlyBandService hbs = new HourlyBandService();
    	
    	request.setAttribute("aniosList", anios);
    	request.setAttribute("mesesList", meses);
//    	request.setAttribute("cajerosList", us.getCajeros());
    	request.setAttribute("bandaList", hbs.getAllHourlyBands());
    	
    	((DynaActionForm)form).set("cajerosList",  us.getCajeros());
    	String[] cajerosArray = {"",""};
    	((DynaActionForm)form).set("cajerosArray", cajerosArray);
    	return mapping.findForward("showIndicadoresComparativaDeVentasPorCajeroMensualFilter");
    }
    
    public ActionForward executeComparativaDeVentasPorCajeroMensual(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	//Redireccionar a un jsp con el chart y un volver hacia el filtro
    	return mapping.findForward("showIndicadoresComparativaDeVentasPorCajeroMensualChart");
    }
    
    public ActionForward getCVPCMChart(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		IndicadoresService is = new IndicadoresService();
        MSLine msline = is.getCVPCMChart((DynaActionForm)form);
        return getXML(msline, mapping, form, request, response);
    }
    
/******		ComparativaDeVentasPorCajeroAnual		******/
    public ActionForward doIndicadoresComparativaDeVentasPorCajeroAnual(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	ArrayList<String> anios = new ArrayList();
    	anios.add("2010");
    	anios.add("2009");
    	anios.add("2008");
    	UserService us = new UserService();
    	HourlyBandService hbs = new HourlyBandService();
    	
    	request.setAttribute("aniosList", anios);
    	request.setAttribute("cajerosList", us.getCajeros());
    	request.setAttribute("bandaList", hbs.getAllHourlyBands());
    	return mapping.findForward("showIndicadoresComparativaDeVentasPorCajeroAnualFilter");
    }
    
    public ActionForward executeComparativaDeVentasPorCajeroAnual(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	//Redireccionar a un jsp con el chart y un volver hacia el filtro
    	return mapping.findForward("showIndicadoresComparativaDeVentasPorCajeroAnualChart");
    }
    
    public ActionForward getCVPCAChart(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		IndicadoresService is = new IndicadoresService();
        MSLine msline = is.getCVPCAChart((DynaActionForm)form);
        return getXML(msline, mapping, form, request, response);
    }

}