package cn.edu.buaa.im.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;

import cn.edu.buaa.im.model.PersonalTreeNode;
import cn.edu.buaa.im.wsdl.HttpClientUtils;

public class UserTemplateService {
	HttpClientUtils httpClientUtils = new HttpClientUtils();
	Gson gson = new Gson();
	String nodeId;
	String userId;
	
	static HashMap<String, List<PersonalTreeNode>> pHashMap = new HashMap<String, List<PersonalTreeNode>>();
	
	public UserTemplateService(String nodeId, String userId){
		this.nodeId = nodeId;
		this.userId = userId;
	}
	
	public void DelTemplate(String name) {
		HashMap<String, List<PersonalTreeNode>> pHashMap = GetTemplates();
		pHashMap.remove(name);
		PostTemplates(pHashMap);
	}
	
	public void SaveTemplate(String json, String name) {
		HashMap<String, List<PersonalTreeNode>> pHashMap = GetTemplates();
		List<PersonalTreeNode> personalTreeNodes = this.NewTemplate();
		String[] tokens = json.split(",");
		HashSet<String> idSet = new HashSet<String>();
		for (String string : tokens) {
			idSet.add(string);
		}
		AddChecked(personalTreeNodes, idSet);
		pHashMap.put(name, personalTreeNodes);
		PostTemplates(pHashMap);
	}
	
	private void AddChecked(List<PersonalTreeNode> personalTreeNodes, HashSet<String> idSet) {
		for (PersonalTreeNode item : personalTreeNodes) {
			if (idSet.contains(item.id))
				item.checked = true;
			if (item.children != null)
				AddChecked(item.children, idSet);
		}
	}
	
	public List<PersonalTreeNode> GetTemplate(String name){
		HashMap<String, List<PersonalTreeNode>> pHashMap = GetTemplates();
		if (pHashMap.containsKey(name))
			return pHashMap.get(name);
		return null;
	}
	
	public List<PersonalTreeNode> NewTemplate(){
		List<PersonalTreeNode> treeNodes = new ArrayList<PersonalTreeNode>();
		PersonalTreeNode treeNode = new PersonalTreeNode("1", "head");
		treeNodes.add(treeNode);
		PersonalTreeNode treeNode1 = new PersonalTreeNode("2", "head1");
		treeNode.Add(treeNode1);
		PersonalTreeNode treeNode2 = new PersonalTreeNode("3", "head1");
		treeNode.Add(treeNode2);
		return treeNodes;
	}
	
	public Set<String> GetTemList() {
		HashMap<String, List<PersonalTreeNode>> pHashMap = GetTemplates();
		return pHashMap.keySet();
	}
	
	@SuppressWarnings({ "null", "unchecked" })
	private HashMap<String, List<PersonalTreeNode>> GetTemplates() {
		return this.pHashMap;
//		HashMap<String, List<PersonalTreeNode>> pHashMap = null;
//		String baseURL = Utility.getParameter("httpclienturl");
//		String url = baseURL + "/genericFile/downloadFile.mm?nodeId=" + nodeId
//				+"&type=usertem&userId=" + userId;
//		String jsonString = httpClientUtils.getDoGetURL(url, "utf8");
//		pHashMap = gson.fromJson(jsonString, pHashMap.getClass());
//		return pHashMap;
	}
	
	private void PostTemplates(HashMap<String, List<PersonalTreeNode>> pHashMap) {
		this.pHashMap = pHashMap;
//		String json = gson.toJson(pHashMap);
//		String baseURL = Utility.getParameter("httpclienturl");
//		String url = baseURL +  "/genericFile/uploadFile.mm?nodeId=" + nodeId
//				+"&type=usertem&userId=" + userId;
//		String fileName = nodeId + "_" + userId + ".usertem";
//		try
//        {
//	        File file=new File(fileName);
//	        if(!file.exists())
//	            file.createNewFile();
//	        FileOutputStream out=new FileOutputStream(file,false); //如果追加方式用true        
//	        StringBuffer sb=new StringBuffer();
//	        sb.append(json);
//	        out.write(sb.toString().getBytes("utf-8"));//注意需要转换对应的字符集
//	        out.close();
//        }
//        catch(IOException ex)
//        {
//            System.out.println(ex.getStackTrace());
//        }
//		
//		HttpClientUtils httpClientUtils = new HttpClientUtils();
//		httpClientUtils.postFile(url, fileName);	
	}
}
