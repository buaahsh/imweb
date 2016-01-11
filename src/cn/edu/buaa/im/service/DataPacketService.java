package cn.edu.buaa.im.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.dom4j.Attribute;
import org.dom4j.Element;

import com.google.gson.Gson;

import cn.edu.buaa.im.data.SQLiteCRUD;
import cn.edu.buaa.im.data.SQLiteConn;
import cn.edu.buaa.im.model.DPVersion;
import cn.edu.buaa.im.model.DataPacketAbs;
import cn.edu.buaa.im.model.TreeNode;
import cn.edu.buaa.im.wsdl.WSDLClient;

public class DataPacketService {
	private String cid;
	private String pcid;
	private String psid;
	
	List<DataPacketAbs> dataPacket = new ArrayList<DataPacketAbs>();
	Pedigree pedigree = new Pedigree();
	
	public DataPacketService(String cid){
		this.cid = cid;
		init();
	}
	
	public DataPacketService(String id, String version, String user, String pwd){
		init(id, version, user, pwd);
	}
	
	private void init() {
		String s = Utility.getSQLite(null);
		SQLiteConn sqLiteConn = new SQLiteConn(s);
		try {
			SQLiteCRUD sqLiteCRUD = new SQLiteCRUD(sqLiteConn.getConnection());
			String table = "CasesDefinition";
			String sql = String.format("select Remark from %s as A where A.[id] == %s;", table, this.cid);
			
			Vector<Vector<Object>> vectors = sqLiteCRUD.selectVector(sql);
			
			String remark = String.valueOf(vectors.get(0).get(0));
			
			Gson gson = new Gson();
			DPRemark dpRemark = gson.fromJson(remark, DPRemark.class);
			
			this.pcid = dpRemark.cid;
			this.psid = dpRemark.sid;
			
			TreeNodeService treeNodeService = new TreeNodeService(this.psid);
			List<TreeNode> treeNodes = treeNodeService.geTreeNodes();
			
			
			table = "table" + this.psid;
			
			for (TreeNode treeNode : treeNodes) {
				
				String fid = treeNode.id.substring(3);
				sql = String.format("select value from %s where Cid = %s and Fid = %s;", table,
						this.pcid, fid);
				vectors = sqLiteCRUD.selectVector(sql);
				
				String value = "";
				
				if (vectors.size() > 0){
					value = String.valueOf(vectors.get(0).get(0));
				}
				
				DataPacketAbs item = new DataPacketAbs();
				item.name = treeNode.text;
				item.value = value;
				
				dataPacket.add(item);
				
			}

			sqLiteCRUD.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void init(String id, String version, String user, String pwd) {
		WSDLClient w = WSDLClient.getInstance();
		
		String method = "getNodeDetail";
		String[] arg = new String[]{user, pwd, id, version};
		String xml = w.getS(method, arg);
		Element root = Utility.getElementFromXml(xml);
		if (root == null)
			return;
		List<Element> elements = root.elements("node");
		for (Element element : elements) {	
			List<Attribute> list = element.attributes();
			for (Attribute attribute : list) {
				DataPacketAbs item = new DataPacketAbs();
				if (attribute.getName().equals("name"))
				{
					item.name = "数据包名称";
					item.value = attribute.getValue();
					dataPacket.add(item);
					this.pedigree.self = attribute.getValue();
				}
				else if (attribute.getName().equals("version"))
				{
					item.name = "数据包版本";
					item.value = attribute.getValue();
					dataPacket.add(item);
				}
				
			}
			List<Element> subelements = element.elements("extra");
			for (Element e : subelements) {	
				list = e.attributes();
				for (Attribute attribute : list) {
					DataPacketAbs item = new DataPacketAbs();
					if (attribute.getName().equals("techStatus"))
					{
						item.name = "技术状态";
						item.value = attribute.getValue();
						dataPacket.add(item);
					}
					else if (attribute.getName().equals("principal"))
					{
						item.name = "负责人";
						item.value = attribute.getValue();
						dataPacket.add(item);
					}
				}
				
				// 解析上下游
				Element pedigreeelements = e.element("pedigree");
				Element upper = pedigreeelements.element("upper");
				List<String> upperList = new ArrayList<String>();
				List<Element> dataPacks = upper.elements("dataPack");
				for (Element element2 : dataPacks) {
					list = element2.attributes();
					for (Attribute attribute : list) {
						if (attribute.getName().equals("name"))
						{
							upperList.add(attribute.getValue());
						}
					}
				}
				Element down = pedigreeelements.element("down");
				List<String> downList = new ArrayList<String>();
				dataPacks = down.elements("dataPack");
				for (Element element2 : dataPacks) {
					list = element2.attributes();
					for (Attribute attribute : list) {
						if (attribute.getName().equals("name"))
						{
							downList.add(attribute.getValue());
						}
					}
				}
				this.pedigree.upper = upperList;
				this.pedigree.down = downList;
			}
		}
	}
	
	
	public List<DataPacketAbs> getDataPacketAbs() {
		return this.dataPacket;
	}
	
	public Pedigree getPedigree() {
		//去重
		
		List<String> temp = new ArrayList<>();
		Iterator<String> sIterator = this.pedigree.down.iterator();
		while (sIterator.hasNext()) {
			String string = (String) sIterator.next();
			if (temp.contains(string))
			{
				sIterator.remove();
			}
			else {
				temp.add(string);
			}
		}
		sIterator = this.pedigree.upper.iterator();
		while (sIterator.hasNext()) {
			String string = (String) sIterator.next();
			if (temp.contains(string))
			{
				sIterator.remove();
			}
			else {
				temp.add(string);
			}
		}
		return this.pedigree;
	}
	
	private class DPRemark{
		public String sid;
		public String cid;
	}
	
	public class Pedigree{
		public List<String> upper;
		public String self;
		public List<String> down;
	}
}
