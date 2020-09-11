package com.dresscasepic.controller;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import com.dresscase.model.*;
import com.dresscasepic.model.*;
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class DressPicServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		res.setCharacterEncoding("UTF-8");
		res.setContentType("image/jpg");
		ServletOutputStream out = res.getOutputStream();

		List<String> errorMsgs = new LinkedList<String>();
		req.setAttribute("errorMsgs", errorMsgs);
		
		try {
//			抓drcase_id
			String drcase_id = req.getParameter("drcase_id").trim();

//			讓dressPicService去尋找具有drcase_id的VO，呼叫其getDrpic方法，再用byteArayInputStream轉換
			DressPicService dpSvc= new DressPicService();
			List<DressPicVO> list= dpSvc.findPics(drcase_id);
			
			if(!list.isEmpty()) {
			for(DressPicVO dpVO:list) {
				ByteArrayInputStream bais = new ByteArrayInputStream(dpVO.getDrpic());
				BufferedInputStream bis = new BufferedInputStream(bais);
				byte[] buf = new byte[4*1024];
				int len;
				while((len = bis.read(buf))!= -1) {
					out.write(buf,0,len);
				}
				bis.close();
				bais.close();
				System.out.println("讀取成功");
			}
			}
			else {
				InputStream in = getServletContext().getResourceAsStream("/front-end/dresscase/images/暫無圖片2.png");
				byte[] b= new byte[in.available()];
				in.read(b);
				out.write(b);
				in.close();
				}
		} catch (Exception e) {
			//System.out.println(e);
			errorMsgs.add("無法取得圖片"+e.getMessage());
			InputStream in = getServletContext().getResourceAsStream("/front-end/dresscase/images/暫無圖片2.png");
			byte[] b = new byte[in.available()];
			in.read(b);
			out.write(b);
			in.close();
			System.out.println("讀取失敗");
//			req.getRequestDispatcher("/front-end/dresscase/updateDressCaseInput.jsp").forward(req, res);
		}
		doPost(req,res);
	}
}