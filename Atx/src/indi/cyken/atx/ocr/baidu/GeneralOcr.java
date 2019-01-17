package indi.cyken.atx.ocr.baidu;

import java.net.URLEncoder;

import com.baidu.aip.util.Base64Util;

import indi.cyken.atx.utils.FileUtil;
import indi.cyken.atx.utils.HttpUtil;



public class GeneralOcr {
	
	
	/**
	 * 
	 * @param filePath :要识别的图片路径
	 */
	public String getOcrRessult(String filePath) {
		if(filePath==null&&filePath.equals("")) {
			return null;
		}
	     // 通用识别url
        String otherHost = "https://aip.baidubce.com/rest/2.0/ocr/v1/general";
        try {
            byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);
            String params = URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(imgStr, "UTF-8");
            /**
             * 线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
             */
            AuthService autherService= new AuthService();
            autherService.getAuth();
            String accessToken = autherService.getAuth();
            String result = HttpUtil.post(otherHost, accessToken, params);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
		
	}

}
