package cn.edu.buaa.im.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import cn.edu.buaa.im.data.TreeNodeReader;
import cn.edu.buaa.im.model.DataItem;
import cn.edu.buaa.im.model.TreeNode;
import cn.edu.buaa.im.service.DataExtractService;
import cn.edu.buaa.im.service.TreeNodeService;
import cn.edu.buaa.im.servlet.Util.ExtTreeNode;
import cn.edu.buaa.im.wsdl.WSDLHttpClient;

public class TreeNodeServlet extends BaseServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 得到左边的treenode
	 */
	@SuppressWarnings("unchecked")
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
		else if (arg.equals("all")) {
			String id_702 = request.getParameter("id");
			String v_702 = request.getParameter("version");
			String username = request.getParameter("user");
			String password = request.getParameter("pwd");
			String ext = request.getParameter("ext");
			
			WSDLHttpClient client = new WSDLHttpClient();
			
			client.login(username, password);
			
			HashMap<String, Object> hashMap = client.getDataItems(id_702, v_702);
			
			//加上获取文件
			if (returnFile(request, response, (List<DataItem>) hashMap.get("DataItem")))
				return;
			
			if (ext != null && hashMap != null)
			{
				List<TreeNode> tnodes = (List<TreeNode>) hashMap.get("TreeNode");
				List<ExtTreeNode> treeNodes2 = Util.Convert2Ext(tnodes);
				hashMap.put("TreeNode", treeNodes2);
			}
			
			Gson gson = new Gson();
			responseString(response, gson.toJson(hashMap));
		}
		else if (arg.equals("view")) {
			String cid = request.getParameter("cid");
			String dataext = request.getParameter("dataext");
			String id_702 = request.getParameter("id");
			String v_702 = request.getParameter("version");
			String username = request.getParameter("user");
			String password = request.getParameter("pwd");
			String sid = request.getParameter("sid");
			
			String sid_702 = request.getParameter("sid_702");
			
			sid = Util.byte2str(sid);
			cid = Util.byte2str(cid);
			
			WSDLHttpClient client = new WSDLHttpClient();
			
			client.login(username, password);
			
			HashMap<String, Object> hashMap = client.getDataItems(id_702, v_702);
			
			TreeNodeService treeNodeService = new TreeNodeService(sid, sid_702);
			treeNodes = treeNodeService.geTreeNodes();
			
			if (dataext != null && dataext.equals("1"))
				treeNodes = DataExtractService.filterTreeNodes(cid, treeNodes);
			
			List<DataItem> dataItems = client.buildDataItems(treeNodes, (List<DataItem>)hashMap.get("DataItem"));
			
			HashMap<String, Object> result = new HashMap<String, Object>();
			result.put("TreeNode", treeNodes);
			result.put("DataItem", dataItems);
			
			//加上获取文件
			if (returnFile(request, response, dataItems))
				return;
			
			String ext = request.getParameter("ext");
			if (ext != null && result != null)
			{
				List<TreeNode> tnodes = (List<TreeNode>) result.get("TreeNode");
				List<ExtTreeNode> treeNodes2 = Util.Convert2Ext(tnodes);
				result.put("TreeNode", treeNodes2);
			}
			
			Gson gson = new Gson();
			responseString(response, gson.toJson(result));
		}
		else if (arg.equals("mm")) {
			String id_702 = request.getParameter("id");
			String v_702 = request.getParameter("version");
			String username = request.getParameter("user");
			String password = request.getParameter("pwd");
			
			WSDLHttpClient client = new WSDLHttpClient();
			
			client.login(username, password);
			
			HashMap<String, Object> hashMap = client.getMMDataItems(id_702, v_702);
			
			//加上获取文件
			if (returnFile(request, response, (List<DataItem>) hashMap.get("DataItem")))
				return;
			
			String ext = request.getParameter("ext");
			if (ext != null && hashMap != null)
			{
				List<TreeNode> tnodes = (List<TreeNode>) hashMap.get("TreeNode");
				List<ExtTreeNode> treeNodes2 = Util.Convert2Ext(tnodes);
				hashMap.put("TreeNode", treeNodes2);
			}
			
			Gson gson = new Gson();
			responseString(response, gson.toJson(hashMap));
		}
		else if (arg.equals("mmview")) {
			String id_702 = request.getParameter("id");
			String v_702 = request.getParameter("version");
			String username = request.getParameter("user");
			String password = request.getParameter("pwd");
			
			String sid = request.getParameter("sid");
			
			String sid_702 = request.getParameter("sid_702");
			
			sid = Util.byte2str(sid);
			
			WSDLHttpClient client = new WSDLHttpClient();
			
			client.login(username, password);
			
			HashMap<String, Object> hashMap = client.getMMDataItems(id_702, v_702);
			
			TreeNodeService treeNodeService = new TreeNodeService(sid, sid_702);
			treeNodes = treeNodeService.geTreeNodes();

			List<DataItem> dataItems = client.buildDataItems(treeNodes, (List<DataItem>)hashMap.get("DataItem"));
			
			HashMap<String, Object> result = new HashMap<String, Object>();
			result.put("TreeNode", treeNodes);
			result.put("DataItem", dataItems);
			
			//加上获取文件
			if (returnFile(request, response, dataItems))
				return;
			
			String ext = request.getParameter("ext");
			if (ext != null)
			{
				List<TreeNode> tnodes = (List<TreeNode>) result.get("TreeNode");
				List<ExtTreeNode> treeNodes2 = Util.Convert2Ext(tnodes);
				result.put("TreeNode", treeNodes2);
			}
			
			Gson gson = new Gson();
			responseString(response, gson.toJson(result));
		}
	}
	
	private boolean returnFile(HttpServletRequest request,
			HttpServletResponse response, List<DataItem> dataItems) {
		String fileName = "Data.txt";
		String isFile = request.getParameter("file");
		
		if (isFile == null)
			return false;

		response.reset();
		// 设置response的Header
		response.setContentType("application/x-cortona");

		byte[] result;
		try {
			result = Util.dataItems2byte(dataItems);
			response.addHeader("Content-Disposition", "attachment;filename="
					+ fileName);
			response.addHeader("Content-Length", "" + result.length);
			OutputStream outputStream = null;
			outputStream = response.getOutputStream();
			outputStream.write(result, 0, result.length);
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return true;
	}
}
