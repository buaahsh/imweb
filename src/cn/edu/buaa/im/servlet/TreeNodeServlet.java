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
import cn.edu.buaa.im.model.DataItemJson;
import cn.edu.buaa.im.model.TreeNode;
import cn.edu.buaa.im.model.TreeNode.A_attr;
import cn.edu.buaa.im.service.DataItemService;
import cn.edu.buaa.im.service.TreeNodeService;
import cn.edu.buaa.im.wsdl.WSDLFile;
import cn.edu.buaa.im.wsdl.WSDLHttpClient;
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
			responseString(response, gson.toJson(treeNodes));
		}
		else if (arg.endsWith("all")) {
			String id_702 = request.getParameter("id");
			String v_702 = request.getParameter("version");
			String username = request.getParameter("user");
			String password = request.getParameter("pwd");
			
			WSDLHttpClient client = new WSDLHttpClient();
			
			client.login(username, password);
			
			HashMap<String, Object> hashMap = client.getDataItems(id_702, v_702);
			
			Gson gson = new Gson();
			responseString(response, gson.toJson(hashMap));
		}
		else if (arg.endsWith("view")) {
			String id_702 = request.getParameter("id");
			String v_702 = request.getParameter("version");
			String username = request.getParameter("user");
			String password = request.getParameter("pwd");
			String sid = request.getParameter("sid");
			
			String sid_702 = request.getParameter("sid_702");
			
			sid = Util.byte2str(sid);
			
			WSDLHttpClient client = new WSDLHttpClient();
			
			client.login(username, password);
			
			HashMap<String, Object> hashMap = client.getDataItems(id_702, v_702);
			
			TreeNodeService treeNodeService = new TreeNodeService(sid, sid_702);
			treeNodes = treeNodeService.geTreeNodes();

			List<DataItem> dataItems = client.buildDataItems(treeNodes, (List<DataItem>)hashMap.get("DataItem"));
			
			HashMap<String, Object> result = new HashMap<String, Object>();
			result.put("TreeNode", treeNodes);
			result.put("DataItem", dataItems);
			
			Gson gson = new Gson();
			responseString(response, gson.toJson(result));
		}
		else if (arg.endsWith("mm")) {
			String id_702 = request.getParameter("id");
//			String v_702 = request.getParameter("version");
			String username = request.getParameter("user");
			String password = request.getParameter("pwd");
			
			WSDLHttpClient client = new WSDLHttpClient();
			
			client.login(username, password);
			
			HashMap<String, Object> hashMap = client.getMMDataItems(id_702);
			
			Gson gson = new Gson();
			responseString(response, gson.toJson(hashMap));
		}
		else if (arg.endsWith("mmview")) {
			String id_702 = request.getParameter("id");
			String username = request.getParameter("user");
			String password = request.getParameter("pwd");
			
			String sid = request.getParameter("sid");
			
			String sid_702 = request.getParameter("sid_702");
			
			sid = Util.byte2str(sid);
			
			WSDLHttpClient client = new WSDLHttpClient();
			
			client.login(username, password);
			
			HashMap<String, Object> hashMap = client.getMMDataItems(id_702);
			
			TreeNodeService treeNodeService = new TreeNodeService(sid, sid_702);
			treeNodes = treeNodeService.geTreeNodes();

			List<DataItem> dataItems = client.buildDataItems(treeNodes, (List<DataItem>)hashMap.get("DataItem"));
			
			HashMap<String, Object> result = new HashMap<String, Object>();
			result.put("TreeNode", treeNodes);
			result.put("DataItem", dataItems);
			
			Gson gson = new Gson();
			responseString(response, gson.toJson(result));
		}
	}
}
