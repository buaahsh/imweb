package cn.edu.buaa.im.wsdl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.buaa.im.model.DataItem;
import cn.edu.buaa.im.model.TreeNode;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;

import cn.edu.buaa.im.model.BaseData;
import cn.edu.buaa.im.service.Utility;


public class WSDLHttpClient {
	public boolean loginFlag;
	private String baseURL;
	private HttpClientUtils client;
	
	public WSDLHttpClient(){
		baseURL = Utility.getParameter("httpclienturl");
		
		client = new HttpClientUtils();
	}
	
	public HashMap<String, Object> getMMDataItems(String nodeId, String version){
		
		int start = 0;
		int limit = 25;
		String resultString = getJsonStr(nodeId, start, limit, Integer.valueOf(version));
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
	
	public HashMap<String, String> getVersionDataItems(String nodeId, String version){
		
		int start = 0;
		int limit = 25;

		//hisVersion/getHisVersion.mm?start=0&limit=10&nodeId=2956
		String urlstr = String.format("%s/hisVersion/getHisVersion.mm",baseURL);
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("start", String.valueOf(start));
		params.put("limit", String.valueOf(limit));
		params.put("nodeId", String.valueOf(nodeId));

		String resultString = client.getDoPostResponseDataByURL(urlstr, params,"utf-8", false);
		
		JSONObject json = JSONObject.parseObject(resultString);

		JSONArray results = json.getJSONArray("results");
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		for (int i = 0; i < results.size(); i++) {

			JSONObject fileJson = results.getJSONObject(i);

			String resultVersion = fileJson.getString("version");
			
			if(resultVersion.equals(version)){
				map.put("id", fileJson.getString("id"));
				map.put("rootid", fileJson.getString("rootid"));
				map.put("version", fileJson.getString("version"));
				map.put("remark", fileJson.getString("remark"));
				map.put("baseLine", fileJson.getString("baseLine"));
				map.put("versionFlag", fileJson.getString("versionFlag"));
				
				break;
			}
		}
		
		return map;
	}

	
	@SuppressWarnings("unchecked")
	private void getOneNode(List<TreeNode> treeNodes, 
			List<DataItem> dataItems, String pid, WSDLNode wsdlnode) {
		// 如果为数据包
		if (wsdlnode.type.equals("dataPack"))
		{
			HashMap<String, Object> result =  getDataItems(String.valueOf(wsdlnode.id), 
					String.valueOf(wsdlnode.version));
			if (result != null){
				List<TreeNode> treeNodes2 =(List<TreeNode>) result.get("TreeNode");
				for (TreeNode tr : treeNodes2) {
					if (tr.parent.equals("#"))
						tr.parent = pid;
				}
				treeNodes.addAll(treeNodes2);
				dataItems.addAll((List<DataItem>) result.get("DataItem"));
			}
		}
		else{ //如果不是数据包
			String name = wsdlnode.text;
			String fid = "fid" + String.valueOf(wsdlnode.id);
			String type = "";
			String icon = "folder";
			String unit = "";
			
			TreeNode treeNode = new TreeNode(fid, pid, name, icon);
			treeNode.type = type;
			treeNode.unit = unit;
			DataItem dataitem = null;
			BaseData baseData = null;
			baseData = BaseData.getInstanceBaseData().new TitleDataItem();
			dataitem = new DataItem(treeNode.text, treeNode.a_attr.href, "", baseData);
			
			treeNodes.add(treeNode);
			dataItems.add(dataitem);
			for (WSDLNode node : wsdlnode.children) {
//				name = node.text;
//				fid = "fid" + String.valueOf(node.id);
//				type = "";
//				icon = "folder";
//				unit = "";
//				
//				treeNode = new TreeNode(fid, "fid" + String.valueOf(wsdlnode.id), name, icon);
//				treeNode.type = type;
//				treeNode.unit = unit;
//				dataitem = null;
//				baseData = null;
//				baseData = BaseData.getInstanceBaseData().new TitleDataItem();
//				dataitem = new DataItem(treeNode.text, treeNode.a_attr.href, "", baseData);
//				
//				treeNodes.add(treeNode);
//				dataItems.add(dataitem);
				
				getOneNode(treeNodes, dataItems, fid, node);
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
		if (json == null)
			return null;
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
		//System.out.println(urlstr);
		String resultString = client.getDoPostResponseDataByURL(urlstr, params,"utf-8", false);
		System.out.println(resultString);
		loginFlag = true;
	}
	
	public String getJsonResult(String url, Map<String, String> params) {
		String urlstr = String.format("%s/%s", 
				baseURL, url);
		String resultString = client.getDoPostResponseDataByURL(urlstr, params,
				"utf-8", false);
		return resultString;
	}
	
	public String getJsonResult(String url) {
		String urlstr = String.format("%s/%s", 
				baseURL, url);
		String resultString = client.getDoGetURL(urlstr, "utf-8");
		return resultString;
	}
	
	public String download(String nodeId, String version) {
		String urlstr = String.format("%s/document/download.mm?nodeId=%s&version=%s", 
				baseURL, nodeId, version);
		byte[] bytes = client.getDoGetURL(urlstr);
		if (bytes == null)
			return null;
		try {
			String s = new String(bytes,"GBK");  
			
			return s;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private String getJsonStr(String nodeId, int start, int limit, int version) {
		String urlstr = String.format("%s/hisNode/loadHisNodeGrid.mm",baseURL);
		Map<String, String> params = new HashMap<String, String>();
		params.put("start", String.valueOf(start));
		params.put("limit", String.valueOf(limit));
		params.put("pid", String.valueOf(nodeId));
		params.put("version", String.valueOf(version));
		
		String resultString = client.getDoPostResponseDataByURL(urlstr, params,"utf-8", false);
		Gson gson = new Gson();
		WSDLNodes wNodes = gson.fromJson(resultString, WSDLNodes.class);
		// 首先下载history，如果没有查找最新
		
		if(wNodes == null)
			return null;
		
		if (wNodes.totalProperty == 0){
			urlstr = String.format("%s/node/loadNodeGrid.mm",baseURL);
			resultString = client.getDoPostResponseDataByURL(urlstr, params,"utf-8", false);
		}
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
			
			String urlstr = String.format("%s/hisNode/loadHisNodeGrid.mm",
					baseURL);
			Map<String, String> params = new HashMap<String, String>();
			params.put("start", String.valueOf(start));
			params.put("limit", String.valueOf(limit));
			params.put("pid", String.valueOf(nodeId));
			params.put("version", String.valueOf(version));

			
			String resultString = client.getDoPostResponseDataByURL(urlstr, params,
					"utf-8", false);
			Gson gson = new Gson();
			WSDLNodes wNodes = gson.fromJson(resultString, WSDLNodes.class);
			// 首先下载history，如果没有查找最新
			
			if(wNodes == null)
				return null;
			
			if (wNodes.totalProperty == 0){
				urlstr = String.format("%s/node/loadNodeGrid.mm",
					baseURL);
				resultString = client.getDoPostResponseDataByURL(urlstr, params,
					"utf-8", false);
				wNodes = gson.fromJson(resultString, WSDLNodes.class);
			}
			
			if(wNodes == null || wNodes.results == null)
				return null;
			
			for (WSDLNode node : wNodes.results) {
				if (node.text.equals("json.txt"))
				{
					if (tempversion == -1 && node.version <= version)
					{
						tempversion = node.version;
						tempid = node.id;
					}
					else if (node.version <= version && node.version > tempversion) {
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
	
	/**
	 * 根据新的treenode，和 oldDataItems 把新的DataItems提取
	 * @param treeNodes
	 * @param oldDataItems
	 * @return
	 */
	public List<DataItem> buildDataItems(List<TreeNode> treeNodes, List<DataItem> oldDataItems) {
		List<DataItem> dataItems = new ArrayList<>();
		try {
			for (TreeNode treeNode : treeNodes) {
				String fid = treeNode.id;
				
				DataItem dataitem = null;
				BaseData baseData = null;
				if (treeNode.parent.equals("#") && treeNode.type.equals("16")){	
					baseData = BaseData.getInstanceBaseData().new TitleDataItem();
					dataitem = new DataItem(treeNode.text, treeNode.a_attr.href, "", baseData);
					dataItems.add(dataitem);
					continue;
				}
				else if (treeNode.type.equals("16")){
					baseData = BaseData.getInstanceBaseData().new SubtitleDataItem();
					dataitem = new DataItem(treeNode.text, treeNode.a_attr.href,  "", baseData);
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
		public String type;
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
//		WSDLHttpClient w = new WSDLHttpClient();
		
//		String method = "getNodeHistory";
////		String[] arg = new String[]{"pdd", "123456", "1444"};
////		w.getS(method, arg);
////		
//		w.login("pdd", "123456");
//		
////		w.download("1502", "76");
//		w.getDataItems("1445", "75");
	}
	
}
