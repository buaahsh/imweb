package cn.edu.buaa.im.service;

import java.util.ArrayList;
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
	
	public DataPacketService(String cid){
		this.cid = cid;
		init();
	}
	
	public DataPacketService(String id, String version, String user, String pwd){
		init(id, version, user, pwd);
	}
	
	private void init() {
		String s = Utility.getSQLite();
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
			}
		}
	}
	
	
	public List<DataPacketAbs> getDataPacketAbs() {
		return this.dataPacket;
	}
	
	private class DPRemark{
		public String sid;
		public String cid;
	}
}
