package ar.com.dailyMarket.ui;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.com.dailyMarket.model.Attach;
import ar.com.dailyMarket.services.ImageService;

public class ImageAction extends BaseAction {         
    
	public ActionForward getImage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long imageId = (Long) ((DynaBean) form).get("imageId");
		Long isImage = (Long) ((DynaBean) form).get("isImage");
		
		ImageService service = new ImageService();
		
		Attach img = null;
		
		if (isImage.longValue() == 1)
			img = service.getImage(imageId);
		else
			img = service.getThumbnail(imageId);
		
		
		String path = img.getPath();
        File f = new File(path);
        
        response.setContentLength(new Long(f.length()).intValue());

        BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));

        response.setContentType(img.getContentType());
        response.setHeader("Content-Disposition","attachment; filename=" + f.getName() + "\"");

        try {
            int len = 0;
            for (byte []buffer = new byte[1024]; (len = bis.read(buffer, 0, 1024)) != -1; ) {
                bos.write(buffer, 0, len);               
            }
            response.flushBuffer();
            bos.flush();
            bos.close();

        } catch (IOException e){
            log.info("Se cancelo el download del archivio: " + f.getName());
        } finally {
            bis.close();
        }
        return null;
    }
}