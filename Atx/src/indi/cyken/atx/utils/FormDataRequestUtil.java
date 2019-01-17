package indi.cyken.atx.utils;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

public class FormDataRequestUtil {
	
	public Map<String, String> getParams(HttpServletRequest request) {
		DiskFileItemFactory factory = new DiskFileItemFactory(); 
		ServletFileUpload upload = new ServletFileUpload(factory); 
		upload.setHeaderEncoding("UTF-8");
		List<FileItem> items;
		Map<String, String>  param = null;
        RequestContext context=new ServletRequestContext(request);
		try {
			items = upload.parseRequest(context);
			param = new HashMap<String, String> (); 
			for(FileItem fileItem:items){
				if (fileItem.isFormField()) { 
					param.put(fileItem.getFieldName(), fileItem.getString("utf-8"));//如果你页面编码是utf-8的 
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return param;
	}

}
