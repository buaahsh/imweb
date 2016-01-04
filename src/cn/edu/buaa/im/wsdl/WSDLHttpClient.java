package cn.edu.buaa.im.wsdl;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;


public class WSDLHttpClient {
	public boolean loginFlag;
	private String baseURL;
	private HttpClientUtils client;
	
	public WSDLHttpClient(){
		baseURL = "http://202.112.140.210/MainModel";
		
		client = new HttpClientUtils();
	}
	
	public HashMap<String, Object> getDataItems(String nodeId, String version){
		int start = 0;
		int limit = 25;
		String[] results = getJsonId(nodeId, start, limit);
		String json = "";
		if (results != null)
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
			return new String(bytes,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private String[] getJsonId(String nodeId, int start, int limit) {
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
				return new String[]{String.valueOf(node.id), 
						String.valueOf(node.version)};
		}
		if (wNodes.totalProperty < limit)
			return getJsonId(nodeId, start + limit, limit);
		
		return null;
	}
	
	public class WSDLNodes{
		public int totalProperty;
		public List<WSDLNode> results;
	}
	
	public class WSDLNode{
		public int id;
		public String text;
		public int version;
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
