package cn.edu.buaa.im.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.buaa.im.service.BaseLineService;

@WebServlet("/BaseLineServlet")
public class BaseLineServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		doPost(request, response);
	}
       
	/**
	 * 处理保存
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) {

		String arg = request.getParameter("arg");
		
		if (arg.equals("create")){
			
			String id_702 = request.getParameter("sid");
			String v_702 = request.getParameter("version");
			
			String username = request.getParameter("user");
			String password = request.getParameter("pwd");
			
			String title = request.getParameter("title");
			String desc = request.getParameter("desc");

			BaseLineService baseLineService = new BaseLineService(id_702, username);
			baseLineService.createBaseLine(id_702, v_702, username, password,title,desc);
			
		}else if (arg.equals("edit")){
			
			String id_702 = request.getParameter("sid");
			String v_702 = request.getParameter("version");
			
			String username = request.getParameter("user");
			String password = request.getParameter("pwd");
			
			String title = request.getParameter("title");
			String desc = request.getParameter("desc");

			BaseLineService baseLineService = new BaseLineService(id_702, username);
			baseLineService.editBaseLine(id_702, v_702, username, password,title,desc);
			
		}else if (arg.equals("del")){
			
			String id_702 = request.getParameter("sid");
			String v_702 = request.getParameter("version");
			
			String username = request.getParameter("user");
			String password = request.getParameter("pwd");

			BaseLineService baseLineService = new BaseLineService(id_702, username);
			baseLineService.delBaseLine(id_702, v_702, username, password);
			
		}
		
		
	}
	
}
