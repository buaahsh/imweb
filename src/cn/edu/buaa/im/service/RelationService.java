package cn.edu.buaa.im.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Element;

import com.google.gson.Gson;

import cn.edu.buaa.im.model.DPVersion;
import cn.edu.buaa.im.model.DataPacketAbs;
import cn.edu.buaa.im.wsdl.WSDLClient;
import cn.edu.buaa.im.wsdl.WSDLHttpClient;

public class RelationService {

	public Pedigree pedigree;
	public String jsonString;
	
	/**
	 * 构建主模型的上下游关系
	 * @param username
	 * @param password
	 * @param id
	 * @param version
	 */
	public RelationService(String username, String password, String id, String version){
		pedigree = new Pedigree();
		WSDLHttpClient client = new WSDLHttpClient();
		client.login(username, password);
		getMMRelation(client, id, version);
	}
	
	/**
	 * 构建数据包的上下游关系
	 * @param username
	 * @param password
	 * @param id
	 * @param version
	 * @param mmid
	 */
	public RelationService(String username, String password, String id, String version, String mmid){
		pedigree = new Pedigree();
		WSDLHttpClient client = new WSDLHttpClient();
		
		client.login(username, password);
		
		init(client, id, version, mmid);
		
		RefineRelation(username, password, id, version);
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
		public String version;
		public String writeMajor;
		
		public RelationItem clone(){
			RelationItem relationItem = new RelationItem();
			relationItem.id = this.id;
			relationItem.name = this.name;
			relationItem.techStatus = this.techStatus;
			relationItem.version = this.version;
			relationItem.writeMajor = this.writeMajor;
			
			return relationItem;
		}
	}
	
	/**
	 * refine 上下游，找到真正属于该节点的上下游
	 * @param id
	 * @param version
	 */
	private void RefineRelation(String username, String password, String id, String version){
		//更新上游
		WSDLClient wsdlClient = new WSDLClient();
		String[] argsStrings = new String[]{id, version};
		String result = wsdlClient.getS("getRelation", argsStrings);
		Element root = Utility.getElementFromXml(result);
		List<Element> elements = root.elements("upper");
		HashMap<String, String> idMap = new HashMap<String, String>();
		for (Element element : elements) {	
			List<Element> subelements = element.elements("node");
			for (Element subelement : subelements) {	
				List<Attribute> list = subelement.attributes();
				String tid = "";
				String tversion = "";
				for (Attribute attribute : list) {
					if (attribute.getName().equals("id"))
						tid = attribute.getValue();
					else if (attribute.getName().equals("version"))
						tversion = attribute.getValue();
					}
				idMap.put(tid, tversion);
			}			
		}
		for (RelationItem item : pedigree.upper) {
			if (idMap.containsKey(String.valueOf(item.id)))
				item.version = idMap.get(String.valueOf(item.id)); 
		}

		//更新下游
		//多个下游节点
		
//		List<RelationItem> downs = new ArrayList<RelationService.RelationItem>();
//		
//		for (RelationItem item : pedigree.down) {
//			VersionService versionService = new VersionService(username, password, String.valueOf(item.id));
//			List<DPVersion> versions = versionService.getVersions();
//			
//			boolean has = false;
//			for (DPVersion dpVersion : versions) {
//				argsStrings = new String[]{String.valueOf(item.id), dpVersion.id};
//				result = wsdlClient.getS("getRelation", argsStrings);
//				String keyString = "<node id=\""+ id +"\" version=\""+ version +"\"/>";
//				if (result.contains(keyString))
//				{
//					has = true;
//					RelationItem newItem = (RelationItem) item.clone();
//					newItem.version = dpVersion.id;
//					downs.add(newItem);
//				}
//			}
//			if (has == false){
//				RelationItem newItem = (RelationItem) item.clone();
//				newItem.version = "-1";
//				downs.add(newItem);
//			}
//		}
//		
//		RelationItem[] newRelationItems = new RelationItem[downs.size()];
//		int i = 0;
//		for (RelationItem relationItem : downs) {
//			newRelationItems[i] = relationItem;
//			i += 1;
//		}
//		pedigree.down = newRelationItems;
		
		
		for (RelationItem item : pedigree.down) {
			VersionService versionService = new VersionService(username, password, String.valueOf(item.id));
			List<DPVersion> versions = versionService.getVersions();
			String versionStr = "";
			boolean has = false;
			for (DPVersion dpVersion : versions) {
				argsStrings = new String[]{String.valueOf(item.id), dpVersion.id};
				result = wsdlClient.getS("getRelation", argsStrings);
				String keyString = "<node id=\""+ id +"\" version=\""+ version +"\"/>";
				if (result.contains(keyString))
				{
					has = true;
					versionStr += dpVersion.id + ",";
				}
			}
			if (has == false){
				item.version = "-1";
			}
			else
				item.version = versionStr;
		}
	}
}