package cn.edu.buaa.im.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.util.ServerInfo;

import com.google.gson.Gson;

import cn.edu.buaa.im.model.DataItem;
import cn.edu.buaa.im.model.ExtTreeNode;
import cn.edu.buaa.im.model.PersonalTreeNode;
import cn.edu.buaa.im.model.TreeNode;
import cn.edu.buaa.im.service.UserTemplateService;
import cn.edu.buaa.im.wsdl.WSDLHttpClient;

/**
 * Servlet implementation class PerTemServlet
 */
@WebServlet("/PerTemServlet")
public class PerTemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PerTemServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Gson gson = new Gson();
		
		String arg = request.getParameter("arg");
		String nodeId = request.getParameter("id");
		String userId = request.getParameter("user");
		
		UserTemplateService userTem = new UserTemplateService(nodeId, userId);
		
		// 新建一个模板
		if (arg.equals("new")){
			String id_702 = request.getParameter("id");
			String v_702 = request.getParameter("version");
			String username = request.getParameter("user");
			String password = request.getParameter("pwd");
			List<PersonalTreeNode> treeNodes = userTem.NewTemplate(id_702, v_702, username, password);
			responseString(response, gson.toJson(treeNodes));
		}
		// 获取模板列表
		else if (arg.equals("list")){
			Set<String> list = userTem.GetTemList();
			responseString(response, gson.toJson(list));
		}
		//删除模板
		else if (arg.equals("del")){
			String name = request.getParameter("name");
			name = Util.byte2str(name);
			userTem.DelTemplate(name);
		}
		//获取模板
		else if (arg.equals("get")){
			String name = request.getParameter("name");
			name = Util.byte2str(name);
			List<PersonalTreeNode> treeNodes = userTem.GetTemplate(name);
			responseString(response, gson.toJson(treeNodes));
		}
	}

	/**
	 * 处理保存模板
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader reader = request.getReader();
		String line = reader.readLine();
		String name = request.getParameter("name");
		name = Util.byte2str(name);
		
		String id_702 = request.getParameter("id");
		String v_702 = request.getParameter("version");
		String username = request.getParameter("user");
		String password = request.getParameter("pwd");
		
		UserTemplateService userTem = new UserTemplateService(id_702, username);
		userTem.SaveTemplate(line, name, id_702, v_702, username, password);
	}

	public void responseString(HttpServletResponse response, String json){
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type","text/html;charset=UTF-8");
		try {
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
