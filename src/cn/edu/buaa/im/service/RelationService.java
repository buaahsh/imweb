package cn.edu.buaa.im.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import cn.edu.buaa.im.wsdl.WSDLHttpClient;

public class RelationService {

	public Pedigree pedigree;
	public String jsonString;
	
	public RelationService(String username, String password, String id, String version){
		pedigree = new Pedigree();
		WSDLHttpClient client = new WSDLHttpClient();
		client.login(username, password);
		getMMRelation(client, id, version);
	}
	
	public RelationService(String username, String password, String id, String version, String mmid){
		pedigree = new Pedigree();
		WSDLHttpClient client = new WSDLHttpClient();
		
		client.login(username, password);
		
		init(client, id, version, mmid);
	}
	
	private void init(WSDLHttpClient client, String id, String version, String mmid) {
		Gson gson = new Gson();
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", id);
		params.put("version", version);
		params.put("mmId", mmid);
		params.put("mmVersion", version);
		String url = "ancestry/getSrcAncestryDPs.mm";
		String json = client.getJsonResult(url, params);
		RelationItem[] relationItems = gson.fromJson(json, RelationItem[].class);
		this.pedigree.upper = relationItems;
		url = "ancestry/getDestAncestryDPs.mm";
		json = client.getJsonResult(url, params);
		relationItems = gson.fromJson(json, RelationItem[].class);
		this.pedigree.down = relationItems;
		String time = String.valueOf(new Date().getTime());
		url = "ancestry/getAncestryDPInfo.mm?id=" + id + "&version=" + version + "&_=" + time; 
		json = client.getJsonResult(url);
		RelationItem relationItem = gson.fromJson(json, RelationItem.class);
		this.pedigree.self = relationItem;
	}
	
	public void getMMRelation(WSDLHttpClient client, String id, String version)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", id);
		params.put("version", version);
		String url = "ancestry/getMainModelDPRelations.mm";
		String json = client.getJsonResult(url, params);
		this.jsonString = json;
	}
	public class Pedigree{
		public RelationItem[] upper;
		public RelationItem self;
		public RelationItem[] down;
	}
	
	public class RelationItem{
		public int id;
		public String name;
		public String techStatus;
		public int version;
		public String writeMajor;
	}
}
