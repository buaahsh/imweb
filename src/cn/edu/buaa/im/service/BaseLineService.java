package cn.edu.buaa.im.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import cn.edu.buaa.im.wsdl.HttpClientUtils;
import cn.edu.buaa.im.wsdl.WSDLHttpClient;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;

public class BaseLineService {
	HttpClientUtils httpClientUtils = new HttpClientUtils();
	Gson gson = new Gson();
	String nodeId;
	String userId;

	public BaseLineService(String nodeId, String userId){
		this.nodeId = nodeId;
		this.userId = userId;
	}
	
	public List<HashMap<String, String>> getBaseLines(String nodeId,String version) {

		String baseURL = Utility.getParameter("httpclienturl");
		String url = baseURL + "/genericFile/downloadFile.mm?nodeId=" + nodeId+"&type=baseLine";
		
		String jsonString = httpClientUtils.getDoGetURL(url, "utf8");

		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		
		if(jsonString != null && jsonString.length() > 0){
			
			Object obj = JSON.parse(jsonString);
			
			if(obj instanceof JSONObject){
			    JSONObject json = (JSONObject)obj;
			    
			    HashMap<String, String> map = new HashMap<String, String>();
			    
				map.put("version", json.getString("version"));
				map.put("基线标题", json.getString("title"));
				map.put("基线说明", json.getString("desc"));

				list.add(map);
				

			}else if (obj instanceof JSONArray){
			    JSONArray jsonArray = (JSONArray)obj;
			    
				if (jsonArray.size() > 0) {

					for (int i = 0; i < jsonArray.size(); i++) {
						
						JSONObject json = jsonArray.getJSONObject(i);

						HashMap<String, String> map = new HashMap<String, String>();
						
						map.put("version", json.getString("version"));
						map.put("基线标题", json.getString("title"));
						map.put("基线说明", json.getString("desc"));

						list.add(map);
						
					}
				}
			}
		}
		return list;
	}

	
	public void createBaseLine(String id_702, String v_702,String username, String password,String title,String desc){
		
		WSDLHttpClient client = new WSDLHttpClient();
		
		client.login(username, password);
		
		HashMap<String, String> map = client.getVersionDataItems(id_702, v_702);
		
		if (map != null){
			map.put("version", v_702);
			map.put("title", title);
			map.put("desc", desc);
			
			postBaseLine(map,v_702);
		}

	}
	
	
	public void editBaseLine(String id_702, String v_702,String username, String password,String title,String desc){
		
		WSDLHttpClient client = new WSDLHttpClient();
		
		client.login(username, password);
		
		HashMap<String, String> map = client.getVersionDataItems(id_702, v_702);
		
		String id = "";
		
		if(map != null && map.size() > 0){
			id = map.get("id");
		}

		List<HashMap<String, String>> list = getBaseLines2(id_702,v_702);
		
		JSONArray jsonArray = JSONArray.parseArray(gson.toJson(list));
		
		
		for (int i = 0; i < jsonArray.size(); i++) {
			
			JSONObject json = jsonArray.getJSONObject(i);
			
			if(id.equals(json.getString("id"))){
				json.put("title", title);
				json.put("desc", desc);
			}
		}

		editBaseLine(jsonArray);
	}
	
	
	public void delBaseLine(String id_702, String v_702,String username, String password){
		
		WSDLHttpClient client = new WSDLHttpClient();
		
		client.login(username, password);
		
		HashMap<String, String> map = client.getVersionDataItems(id_702, v_702);
		
		String id = "";
		
		if(map != null && map.size() > 0){
			id = map.get("id");
		}

		List<HashMap<String, String>> list = getBaseLines2(id_702,v_702);
		
		JSONArray jsonArray = JSONArray.parseArray(gson.toJson(list));
		
		
		for (int i = 0; i < jsonArray.size(); i++) {
			
			JSONObject json = jsonArray.getJSONObject(i);
			
			if(id.equals(json.getString("id"))){
				jsonArray.remove(json);
			}
		}

		editBaseLine(jsonArray);
	}

	private List<HashMap<String, String>> getBaseLines2(String nodeId,String version) {
		
		String baseURL = Utility.getParameter("httpclienturl");
		String url = baseURL + "/genericFile/downloadFile.mm?nodeId=" + nodeId+"&type=baseLine";
		
		String jsonString = httpClientUtils.getDoGetURL(url, "utf8");
		
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		
		if(jsonString != null && jsonString.length() > 0){
			
			Object obj = JSON.parse(jsonString);
			
			if(obj instanceof JSONObject){
			    JSONObject json = (JSONObject)obj;
			    
			    HashMap<String, String> map = new HashMap<String, String>();
			    
			    map.put("id", json.getString("id"));
				map.put("rootid", json.getString("rootid"));
				map.put("version", json.getString("version"));
				map.put("remark", json.getString("remark"));
				map.put("baseLine", json.getString("baseLine"));
				map.put("versionFlag", json.getString("versionFlag"));
				map.put("title", json.getString("desc"));
				map.put("desc", json.getString("title"));
				
				list.add(map);
				

			}else if (obj instanceof JSONArray){
			    JSONArray jsonArray = (JSONArray)obj;
			    
				if (jsonArray.size() > 0) {

					for (int i = 0; i < jsonArray.size(); i++) {
						
						JSONObject json = jsonArray.getJSONObject(i);

						HashMap<String, String> map = new HashMap<String, String>();
						
						map.put("id", json.getString("id"));
						map.put("rootid", json.getString("rootid"));
						map.put("version", json.getString("version"));
						map.put("remark", json.getString("remark"));
						map.put("baseLine", json.getString("baseLine"));
						map.put("versionFlag", json.getString("versionFlag"));
						map.put("title", json.getString("title"));
						map.put("desc", json.getString("desc"));
						
						list.add(map);
						
					}
				}
			    

			}
			
		}
		
		return list;
	}
	
	
	private void editBaseLine(JSONArray jsonArray) {
		
		String baseURL = Utility.getParameter("httpclienturl");
		String url = baseURL +  "/genericFile/uploadFile.mm?nodeId=" + nodeId+"&type=baseLine";
		String fileName = nodeId + "_"+ UUID.randomUUID() + ".baseLine";
		try {
	        File file=new File(fileName);
	        if(!file.exists())
	            file.createNewFile();
	        FileOutputStream out=new FileOutputStream(file,false); //如果追加方式用true        
	        StringBuffer sb=new StringBuffer();

	        sb.append(jsonArray.toJSONString());

	        out.write(sb.toString().getBytes("utf-8"));//注意需要转换对应的字符集
	        out.close();
        }
        catch(IOException ex) {
            System.out.println(ex.getStackTrace());
        }
		
		HttpClientUtils httpClientUtils = new HttpClientUtils();
		httpClientUtils.postFile(url, fileName);	
	}

	private void postBaseLine(HashMap<String, String> map,String version) {
		
		List<HashMap<String, String>> lll  = getBaseLines2(nodeId,version);
		
		JSONArray jsonArray = JSONArray.parseArray(gson.toJson(lll));

		String json = gson.toJson(map);
		
		JSONObject jsonObject = JSONObject.parseObject(json);
		
		jsonArray.add(jsonObject);
		
		String baseURL = Utility.getParameter("httpclienturl");
		String url = baseURL +  "/genericFile/uploadFile.mm?nodeId=" + nodeId+"&type=baseLine";
		String fileName = nodeId + "_"+ version + ".baseLine";
		try {
	        File file=new File(fileName);
	        if(!file.exists())
	            file.createNewFile();
	        FileOutputStream out=new FileOutputStream(file,false); //如果追加方式用true        
	        StringBuffer sb=new StringBuffer();

	        sb.append(jsonArray.toJSONString());

	        out.write(sb.toString().getBytes("utf-8"));//注意需要转换对应的字符集
	        out.close();
        }
        catch(IOException ex) {
            System.out.println(ex.getStackTrace());
        }
		
		HttpClientUtils httpClientUtils = new HttpClientUtils();
		httpClientUtils.postFile(url, fileName);	
	}

}
