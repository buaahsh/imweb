package cn.edu.buaa.im.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import javax.servlet.ServletException;

import cn.edu.buaa.im.data.TreeNodeReader;
import cn.edu.buaa.im.model.TreeNode;
import cn.edu.buaa.im.model.TreeNode.A_attr;
import cn.edu.buaa.im.service.TreeNodeService;
import sun.org.mozilla.javascript.internal.ast.NewExpression;

public class TreeNodeServlet extends BaseServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws  IOException {
//		List<TreeNode> treeNodes = TreeNodeReader.ReadTreeNodes();
	
		String sid = request.getParameter("sid");
		if (sid == null)
			return;
		TreeNodeService treeNodeService = new TreeNodeService(sid);
		List<TreeNode> treeNodes = treeNodeService.geTreeNodes();
		Gson gson = new Gson();
		responseString(response, gson.toJson(treeNodes));
	}
}
