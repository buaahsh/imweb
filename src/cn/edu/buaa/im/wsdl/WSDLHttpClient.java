package cn.edu.buaa.im.wsdl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import cn.edu.buaa.im.model.DataItem;
import cn.edu.buaa.im.model.TreeNode;
import cn.edu.buaa.im.servlet.Util;

import com.google.gson.Gson;

import cn.edu.buaa.im.data.SQLiteCRUD;
import cn.edu.buaa.im.data.SQLiteConn;
import cn.edu.buaa.im.model.BaseData;
import cn.edu.buaa.im.model.DataItem;
import cn.edu.buaa.im.model.TreeNode;
import cn.edu.buaa.im.model.BaseData.SubtitleDataItem;
import cn.edu.buaa.im.model.BaseData.TitleDataItem;
import cn.edu.buaa.im.service.Utility;


public class WSDLHttpClient {
	public boolean loginFlag;
	private String baseURL;
	private HttpClientUtils client;
	
	public WSDLHttpClient(){
		baseURL = Utility.getParameter("httpclienturl");;
		
		client = new HttpClientUtils();
	}
	
	public HashMap<String, Object> getMMDataItems(String nodeId){
		
		int start = 0;
		int limit = 25;
		String resultString = getJsonStr(nodeId, start, limit);
		Gson gson = new Gson();
		List<TreeNode> treeNodes = new ArrayList<TreeNode>();
		List<DataItem> dataItems = new ArrayList<DataItem>();
		WSDLNodes wNodes = gson.fromJson(resultString, WSDLNodes.class);
		for (WSDLNode wsdlnode : wNodes.results) {
			getOneNode(treeNodes, dataItems, "#", wsdlnode);
		}
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("TreeNode", treeNodes);
		result.put("DataItem", dataItems);
		
		return result;
	}
	
	private void getOneNode(List<TreeNode> treeNodes, 
			List<DataItem> dataItems, String pid, WSDLNode wsdlnode) {
		// 如果为数据包
		if (wsdlnode.dataPack != null)
		{
			HashMap<String, Object> result =  getDataItems(String.valueOf(wsdlnode.id), 
					String.valueOf(wsdlnode.version));
			treeNodes.addAll((List<TreeNode>) result.get("TreeNode"));
			dataItems.addAll((List<DataItem>) result.get("DataItem"));
		}
		else{ //如果不是数据包
			for (WSDLNode node : wsdlnode.children) {
				getOneNode(treeNodes, dataItems, "#", node);
			}
		}
	}
	
	public HashMap<String, Object> getDataItems(String nodeId, String version){
		int start = 0;
		int limit = 50;
		String[] results = getJsonId(nodeId, start, limit, Integer.parseInt(version));
		String json = "";
		if (results == null)
			return null;
		json = download(results[0], results[1]);
		WSDLFile wsdlFile = new WSDLFile();
		HashMap<String, Object> result = wsdlFile.GetHashMap(json);
		return result;
	}
	
	public void login(String username, String password)
	{
		String urlstr = String.format("%s/doLogin.mm", baseURL);

		Map<String, String> params = new HashMap<String, String>();
		params.put("userName", username);
		params.put("password", password);
		System.out.println(urlstr);
		String resultString = client.getDoPostResponseDataByURL(urlstr, params,
				"utf-8", false);
		System.out.println(resultString);
		loginFlag = true;
	}
	
	public String download(String nodeId, String version) {
		String urlstr = String.format("%s/file/download.mm?nodeId=%s&version=%s", 
				baseURL, nodeId, version);
		byte[] bytes = client.getDoGetURL(urlstr);
		try {
			String s = new String(bytes,"GBK");  
			
			return s;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private String getJsonStr(String nodeId, int start, int limit) {
		String urlstr = String.format("%s/node/loadNodeGrid.mm",
				baseURL);
		Map<String, String> params = new HashMap<String, String>();
		params.put("start", String.valueOf(start));
		params.put("limit", String.valueOf(limit));
		params.put("pid", String.valueOf(nodeId));

		String resultString = client.getDoPostResponseDataByURL(urlstr, params,
				"utf-8", false);
		return resultString;
	}
	
	private String[] getJsonId(String nodeId, int start, int limit, int version) {
		int n = 0;
		int tempid = -1;
		int tempversion = -1;
				
		while (true) {
			n += 1;
			if (n >= 100)
				break;
			
			String urlstr = String.format("%s/node/loadNodeGrid.mm",
					baseURL);
			Map<String, String> params = new HashMap<String, String>();
			params.put("start", String.valueOf(start));
			params.put("limit", String.valueOf(limit));
			params.put("pid", String.valueOf(nodeId));

			String resultString = client.getDoPostResponseDataByURL(urlstr, params,
					"utf-8", false);
			Gson gson = new Gson();
			WSDLNodes wNodes = gson.fromJson(resultString, WSDLNodes.class);
			for (WSDLNode node : wNodes.results) {
				if (node.text.equals("json.txt"))
				{
					if (tempversion == -1 && node.version >= version)
					{
						tempversion = node.version;
						tempid = node.id;
					}
					else if (node.version >= version && node.version < tempversion) {
						tempversion = node.version;
						tempid = node.id;
					}
				}
			}
			if (wNodes.totalProperty == 0)
				break;
			if (wNodes.totalProperty < limit)
				break;
			start = start + limit;
		}
		return new String[]{String.valueOf(tempid), 
				String.valueOf(tempversion)};
	}
	
	public List<DataItem> buildDataItems(List<TreeNode> treeNodes, List<DataItem> oldDataItems) {
		List<DataItem> dataItems = new ArrayList<>();
		try {
			for (TreeNode treeNode : treeNodes) {
				String fid = treeNode.id;
				
				DataItem dataitem = null;
				BaseData baseData = null;
				if (treeNode.parent.equals("#")){	
					baseData = BaseData.getInstanceBaseData().new TitleDataItem();
					dataitem = new DataItem(treeNode.text, treeNode.a_attr.href, baseData);
					dataItems.add(dataitem);
					continue;
				}
				else if (treeNode.type.equals("16")){
					baseData = BaseData.getInstanceBaseData().new SubtitleDataItem();
					dataitem = new DataItem(treeNode.text, treeNode.a_attr.href, baseData);
					dataItems.add(dataitem);
					continue;
				}
				
				for (DataItem dataItem : oldDataItems) {
					if (dataItem.id.split("_")[0].equals(fid))
					{
						dataItems.add(dataItem);
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataItems;
	}
	
	public class WSDLNodes{
		public int totalProperty;
		public List<WSDLNode> results;
	}
	
	public class WSDLNode{
		public int id;
		public String text;
		public int version;
		public List<WSDLNode> children;
		public WSDLDataPack dataPack;
	}
	
	public class WSDLDataPack{
		public int id;
		public String name;
	}
	
	public static void main(String[] args) {
		WSDLHttpClient w = new WSDLHttpClient();
		
//		String method = "getNodeHistory";
//		String[] arg = new String[]{"pdd", "123456", "1444"};
//		w.getS(method, arg);
//		
		w.login("pdd", "123456");
		
//		w.download("1502", "76");
		w.getDataItems("1445", "75");
	}
	
}
