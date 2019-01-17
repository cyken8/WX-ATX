package indi.cyken.atx.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.oreilly.servlet.multipart.FileRenamePolicy;

import indi.cyken.atx.ocr.baidu.GeneralOcr;

/**
 * Servlet implementation class BaiduOcrServlet
 */
@WebServlet("/BaiduOcrServlet")
public class BaiduOcrServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BaiduOcrServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("调用servlet");
		response.setContentType("text/html;charset=UTF-8");
		String userId=null;
		String requestip=request.getRemoteAddr();
		String saveDirectory="G:\\code\\atx\\upload\\checkPic";
		File savedir=new File(saveDirectory);
		if(!savedir.exists()) {
			savedir.mkdir();
		}
		int maxPostSize=5*1024*1024;
		FileRenamePolicy policy=(FileRenamePolicy)new DefaultFileRenamePolicy();
		MultipartRequest multi;
		multi=new MultipartRequest(request,saveDirectory,maxPostSize,"UTF-8",policy);
		Enumeration<String> files=multi.getFileNames();
		String name=files.nextElement();
		Enumeration params=multi.getParameterNames(); 
		while (params.hasMoreElements()) { 
			String s=(String)params.nextElement(); 
			System.out.println(s); 
			String[] str=multi.getParameterValues(s); 
			for (int i=0;i<str.length;i++){ 
				System.out.println(str[i]);
				if(s.equals("userId")) {
					userId=str[i];
				}
			}
		}
		File f=multi.getFile(name);
		if(f!=null) {
			String fileName=f.getName();
			//“\\”与fileName之间必须有其他字符阻隔，否则无法写入图片
			String saveFileName=saveDirectory+"\\"+userId+"-"+fileName;
			File sServerFile=new File(saveFileName);
			if(sServerFile.exists()) {
				sServerFile.delete();
			}
			f.renameTo(sServerFile);
			System.out.println("文件上传成功!文件名为： "+saveFileName);
			//上传识别
			GeneralOcr generalOcr=new GeneralOcr();
			String result=generalOcr.getOcrRessult(saveFileName);
			System.out.println("打印识别结果："+result);
			
			//解析json
/*			Gson gson=new Gson();
			OcrResult ocrResult=gson.fromJson(result,OcrResult.class);
			System.out.println("words_result_num: " + ocrResult.getWords_result_num());
			List<Word> words=ocrResult.getWords_result();
			for(int i=0;i<words.size();i++) {
				System.out.println(words.get(i).getWords());
			}*/
			
			//写回到微信小程序
			PrintWriter writer = response.getWriter();
			writer.write(result);
			writer.flush();
			writer.close();
			
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
