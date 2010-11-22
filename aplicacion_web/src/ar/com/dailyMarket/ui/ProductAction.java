package ar.com.dailyMarket.ui;

import java.io.IOException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;

import ar.com.dailyMarket.model.Image;
import ar.com.dailyMarket.model.Product;
import ar.com.dailyMarket.services.GroupProductService;
import ar.com.dailyMarket.services.ImageService;
import ar.com.dailyMarket.services.ProductService;
import ar.com.dailyMarket.ui.validator.Validator;

public class ProductAction extends BaseAction {
	
	public ActionForward showFilter (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {    	    
    	initForm((DynaActionForm)form);
    	setGroupUserRequest(request);
    	return mapping.findForward("filter");
    }             
    
    public ActionForward saveAndBackToCheckPoint (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ProductService productService = new ProductService();
    	ActionErrors errors = validateForm((DynaActionForm)form, request);
    	if (!errors.isEmpty()) {
    		saveErrors(request, errors);
    		setGroupUserRequest(request);
    		return super.initAction(mapping, form, request, response);
    	}
    	productService.save(form);
    	((DynaActionForm)form).set("id",productService.getLastProduct().getId());
    	return findByPK(mapping, form, request, response);
    } 
    
    public ActionForward executeFilter (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ProductService productService = new ProductService();
    	request.setAttribute("items", productService.executeFilter((DynaActionForm)form));    	
    	setGroupUserRequest(request);
    	return mapping.findForward("filter");
    }
    
    public ActionForward update (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ProductService productService = new ProductService();
    	ActionErrors errors = validateForm((DynaActionForm)form, request);
    	if (!errors.isEmpty()) {
    		saveErrors(request, errors);
    		setGroupUserRequest(request);
        	return mapping.findForward("showDetail");
    	}
    	productService.update(form,productService.getProductByPK((Long)((DynaActionForm)form).get("id")));
    	return super.stepBack(mapping, form, request, response);
    }
    
    public ActionForward findByPK (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ProductService productService = new ProductService();
    	Product product = productService.getProductByPK((Long)((DynaActionForm)form).get("id"));
    	setFormProperties((DynaActionForm)form,product);
    	setGroupUserRequest(request);
    	if (product.getImage() != null) {
    		((DynaActionForm)form).set("attachId",product.getImage().getId());
    		request.getSession().setAttribute("image", product.getImage());
    	} else {
    		((DynaActionForm)form).set("attachId",new Long(-1));
    		request.getSession().setAttribute("image", null);
    	}
    	return mapping.findForward("showDetail");
    }
    
    private void initForm(DynaActionForm form) {
    	form.set("name", "");
    	form.set("description", "");
    	form.set("code", "");
    	form.set("groupProductId", null);
    }
    
    private void setFormProperties(DynaActionForm form, Product product) {
    	form.set("id", product.getId());
    	form.set("name", product.getName());
    	form.set("actualStock", product.getActualStock());
    	form.set("price", product.getPrice());
    	form.set("sizeOfPurchase", product.getSizeOfPurchase());
    	form.set("code", product.getCode());
    	form.set("description", product.getDescription());
    	form.set("groupProductId", product.getGroupProduct() != null ? product.getGroupProduct().getId() : null);
    	form.set("repositionStock", product.getRepositionStock() != null ? product.getRepositionStock() : null);
    }
    
    private void setGroupUserRequest (HttpServletRequest request) {
    	GroupProductService groupProductService = new GroupProductService();
    	request.setAttribute("groupsProduct", groupProductService.getAllGroupProduct());
    }
    
    @Override
    public ActionForward redirectCreate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	setGroupUserRequest(request);
    	return super.redirectCreate(mapping, form, request, response);
    }   
    
    public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ProductService productService = new ProductService();
    	productService.delete((Long)((DynaActionForm)form).get("id"));
    	return executeFilter(mapping, form, request, response);    	
    }
    
    private ActionErrors validateForm(DynaActionForm form, HttpServletRequest request) {    	
    	ActionErrors errors = new ActionErrors();
    	Validator.isEmpty(form.get("code"), errors, request, getResources(request).getMessage("ProductForm.code"));
    	Validator.isEmpty(form.get("name"), errors, request, getResources(request).getMessage("ProductForm.name"));
    	Validator.isEmpty(form.get("description"), errors, request, getResources(request).getMessage("ProductForm.description"));
    	Validator.isDouble(form.get("price"), errors, request, getResources(request).getMessage("ProductForm.price"), true);
    	Validator.isInteger(form.get("sizeOfPurchase"), errors, request, getResources(request).getMessage("ProductForm.sizeOfPurchase"), true);
    	Validator.isInteger(form.get("actualStock"), errors, request, getResources(request).getMessage("ProductForm.actualStock"), true);
    	Validator.isInteger(form.get("repositionStock"), errors, request, getResources(request).getMessage("ProductForm.repositionStock"), true);
    			
    	return errors;
    }        
    
    public ActionForward initImage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		((DynaActionForm) form).set("description", "");
		return mapping.findForward("showImage");
	}
    
    public ActionForward confirmImage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
        ImageService service = new ImageService();	
        ProductService productService = new ProductService();
        
        Context initCtx;
        Context envCtx;
        String uploadPath = "";
		try {
			initCtx = new InitialContext();
			envCtx = (Context)initCtx.lookup("java:comp/env");
			uploadPath = (String) envCtx.lookup("uploadPath");
		} catch (NamingException e) {			
			e.printStackTrace();			
		}        
		
        ActionErrors errors = new ActionErrors();
        FormFile file = (FormFile) ((DynaActionForm) form).get("file"); 
        if (!file.getContentType().startsWith("image")) {
   			errors.add("file", new ActionError("errors.invalid", "Archivo debe ser una imagen.")); 
            saveErrors(request, errors);
            return findByPK(mapping, form, request, response);
        }
        if (file.getFileSize() == 0 ) {
   			errors.add("file", new ActionError("errors.required", "Archivo")); 
            saveErrors(request, errors);
            return findByPK(mapping, form, request, response);
        }
        
		((DynaActionForm) form).set("uploadPath", uploadPath);
        
		Product product = productService.getProductByPK((Long)((DynaActionForm)form).get("id"));
        Image img = service.saveImage((DynaBean) form);
        if (product.getImage() != null) {
        	Image imgOld = product.getImage();
        	product.setImage(null);
        	productService.save(product);
        	service.deleteImgAndThumbnail(imgOld);
        }
        product.setImage(img);
        productService.save(product);
		return findByPK(mapping, form, request, response);
	}	
    
    public ActionForward deleteImage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
    	ProductService productService = new ProductService();
    	Product product = productService.getProductByPK((Long)((DynaActionForm)form).get("id"));
    	Image img = product.getImage();
    	product.setImage(null);
    	productService.save(product);
    	
    	ImageService productImageService = new ImageService();
    	productImageService.deleteImgAndThumbnail(img);
    	return findByPK(mapping, form, request, response);
    } 
}
