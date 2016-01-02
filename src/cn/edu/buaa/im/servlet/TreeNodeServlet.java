package cn.edu.buaa.im.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import javax.servlet.ServletException;

import cn.edu.buaa.im.data.TreeNodeReader;
import cn.edu.buaa.im.model.DataItem;
import cn.edu.buaa.im.model.TreeNode;
import cn.edu.buaa.im.model.TreeNode.A_attr;
import cn.edu.buaa.im.service.DataItemService;
import cn.edu.buaa.im.service.TreeNodeService;
import cn.edu.buaa.im.wsdl.WSDLFile;
import sun.org.mozilla.javascript.internal.ast.NewExpression;

public class TreeNodeServlet extends BaseServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 得到左边的treenode
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws  IOException {
		List<TreeNode> treeNodes;
	
		String arg = request.getParameter("arg");
		if (arg == null)
		{
			String sid = request.getParameter("sid");
			if (sid == null || sid.equals("undefined"))
				treeNodes = TreeNodeReader.ReadTreeNodes();
			else{
				TreeNodeService treeNodeService = new TreeNodeService(sid);
				treeNodes = treeNodeService.geTreeNodes();
			}
			Gson gson = new Gson();
		}
		else if (arg.endsWith("all")) {
//			String sid = request.getParameter("sid");
//			String cid = request.getParameter("cid");

			WSDLFile wsdlFile = new WSDLFile();
			
			HashMap<String, Object> hashMap = wsdlFile.test();
			
			Gson gson = new Gson();
			responseString(response, gson.toJson(hashMap));			
		}

	}
}
