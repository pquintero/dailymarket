package ar.com.dailyMarket.services;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.upload.FormFile;
import org.hibernate.Criteria;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;

import ar.com.dailyMarket.model.Image;
import ar.com.dailyMarket.model.Product;
import ar.com.dailyMarket.model.Thumbnail;
import ar.com.dailyMarket.model.User;
import ar.com.dailyMarket.services.util.ThumbnailUtil;

public class ImageService {
	
	public Image saveImage(DynaBean data) {
		Image img = null;
		Transaction tx = null;
		try {
			HibernateHelper.closeSession();
			tx = HibernateHelper.currentSession().beginTransaction();
			
			img = new Image();
			img.setContentType("image/jpeg");
			img.setDescription((String)data.get("description"));
					
			FormFile file = (FormFile) data.get("file");        

	        Long parentId = (Long) data.get("id");
	        String fileName = "IMAGE_" + parentId + "_" + new Date().getTime();
	        String filePath = ((String) data.get("uploadPath")) + fileName;
	        
	        try {
	            BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(file.getFileData()));
	            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
	            
	            int len = 0;
	            for (byte []buffer = new byte[1024]; (len = bis.read(buffer, 0, 1024)) != -1; ) {
	            	bos.write(buffer, 0, len);
	            }
	            
	            bos.flush();
	            bos.close();
	            bis.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        img.setRealName(file.getFileName());
	        img.setName(fileName);
	        img.setPath(filePath);        
	        img.setSize(new Long (file.getFileSize()));        
	        
	        HibernateHelper.currentSession().save(img);
	        
	        saveThumbnail(img, ((String) data.get("uploadPath")));
	        
	        HibernateHelper.currentSession().refresh(img);
			
			tx.commit();
		}
		catch (RuntimeException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		}
		finally {
			tx = null;
			HibernateHelper.closeSession();
		}
        return img;
	}
	
	protected Thumbnail saveThumbnail(Image img, String uploadPath) {
		Thumbnail thumb = new Thumbnail();
		thumb.setContentType("image/jpeg");
		thumb.setDescription("(thumb) " + img.getDescription());
		
		String realName = img.getRealName() + "_thumb";
		String fileName = "THUMBNAIL_" + img.getId() + "_" + new Date().getTime();
        String filePath = uploadPath + fileName;

        try {
			ThumbnailUtil.generateThumbnail(img.getPath(), filePath, 200, 200, 100);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Error(e);
		} 
        
        thumb.setRealName(realName);
        thumb.setName(fileName);
        thumb.setPath(filePath);        
        thumb.setSize(new Long (0));
        
        thumb.setImage(img);
        
        HibernateHelper.currentSession().save(thumb);
        return thumb;
	}

	@SuppressWarnings("unchecked")
	public List<Image> getImages(DynaBean data) {
		Transaction tx = null;
		List<Image> images = new ArrayList<Image>();
		try {
			HibernateHelper.closeSession();
			tx = HibernateHelper.currentSession().beginTransaction();
			
			Long id = (Long) data.get("id");
			Criteria c = HibernateHelper.currentSession().createCriteria(Image.class);
			c.createCriteria("order").add(Expression.eq("id", id));
			images = c.list();
			
			tx.commit();
		}
		catch (RuntimeException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		}
		finally {
			tx = null;
			HibernateHelper.closeSession();
		}
				
		return images;
	}
	
	public Image getImage(Long id) {
		Transaction tx = null;
		Image image = null;
		try {
			HibernateHelper.closeSession();
			tx = HibernateHelper.currentSession().beginTransaction();
			
			image = (Image) HibernateHelper.currentSession().load(Image.class, id);	
			
			tx.commit();
		}
		catch (RuntimeException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		}
		finally {
			tx = null;
			HibernateHelper.closeSession();
		}
		return image;
	}
	
	public Thumbnail getThumbnail(Long id) {
		Transaction tx = null;
		Thumbnail thumb = null;
		try {
			HibernateHelper.closeSession();
			tx = HibernateHelper.currentSession().beginTransaction();
			
			thumb = (Thumbnail) HibernateHelper.currentSession().load(Thumbnail.class, id);
			
			tx.commit();
		}
		catch (RuntimeException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		}
		finally {
			tx = null;
			HibernateHelper.closeSession();
		}
		return thumb;
	}
	
	public void deleteImg(Product product) {
		Transaction tx = null;
		try {
			HibernateHelper.closeSession();
			tx = HibernateHelper.currentSession().beginTransaction();
			
			Image img = product.getImage();
	    	product.setImage(null);
	    	HibernateHelper.currentSession().update(product);
	    	deleteImgAndThumbnail(img);
			
			tx.commit();
		}
		catch (Exception e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		}
		finally {
			tx = null;
			HibernateHelper.closeSession();
		}
	}
	
	public void deleteImg(User user) {
		Transaction tx = null;
		try {
			HibernateHelper.closeSession();
			tx = HibernateHelper.currentSession().beginTransaction();
			
			Image img = user.getImage();
	    	user.setImage(null);
	    	HibernateHelper.currentSession().update(user);
	    	deleteImgAndThumbnail(img);
			
			tx.commit();
		}
		catch (Exception e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		}
		finally {
			tx = null;
			HibernateHelper.closeSession();
		}
	}
	
	public void deleteImgAndThumbnail(Image img) throws IOException {				              
        File file = new File(img.getPath());        
		Thumbnail thumbnail = img.getThumbnail();
		File fileThumbnail = new File(thumbnail.getPath());
		
		HibernateHelper.currentSession().delete(img);
		HibernateHelper.currentSession().delete(thumbnail);
		
		if(!file.delete() || !fileThumbnail.delete()){
            throw new IOException();
        }				
	}
}
