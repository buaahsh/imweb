package cn.edu.buaa.im.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.dom4j.Attribute;
import org.dom4j.Element;

import cn.edu.buaa.im.data.SQLiteCRUD;
import cn.edu.buaa.im.data.SQLiteConn;
import cn.edu.buaa.im.model.DPVersion;
//import cn.edu.buaa.im.service.RelationService.MMResponse;
//import cn.edu.buaa.im.service.RelationService.RelationItem;
import cn.edu.buaa.im.wsdl.WSDLClient;

public class VersionService {
	private String sid;
	private String cid;
	
	List<DPVersion> versions;		
	
	public VersionService(String sid, String cid){
		this.sid = sid;
		this.cid = cid;
		versions = new ArrayList<DPVersion>();	
		init();
	}
	
	public VersionService(String userId, String passWord, String nodeId){
		versions = new ArrayList<DPVersion>();	
		init(userId, passWord, nodeId);
	}
	
	@SuppressWarnings("unchecked")
	private void init(String userId, String passWord, String nodeId) {
		WSDLClient w = new WSDLClient();
		
		String method = "getNodeHistory";
		String[] arg = new String[]{userId, passWord, nodeId};
		String xml = w.getS(method, arg);
		Element root = Utility.getElementFromXml(xml);
		if (root == null)
			return;
		List<Element> elements = root.elements("history");
		for (Element element : elements) {
			DPVersion version = new DPVersion();
			List<Attribute> list = element.attributes();
			for (Attribute attribute : list) {
				if (attribute.getName().equals("version"))
				{
					version.setId(attribute.getValue());
					version.setName(attribute.getValue());
				}
				else if (attribute.getName().equals("versionRemark"))
					version.setAbs(attribute.getValue());
				else if (attribute.getName().equals("submitDate"))
					version.setDate(attribute.getValue());
				else if (attribute.getName().equals("submitUser"))
					version.setPerson(attribute.getValue());
			}
			versions.add(version);
		}
        
	}
	
	private void init() {
		String s = Utility.getSQLite(null);
		SQLiteConn sqLiteConn = new SQLiteConn(s);
		try {
			SQLiteCRUD sqLiteCRUD = new SQLiteCRUD(sqLiteConn.getConnection());
			String table = "CasesDefinition";
			String sql = String.format("select * from %s as A where A.[Sid] = %s and A.[id] != %s ORDER BY A.[id];", table,
					this.sid, this.cid);
			Vector<Vector<Object>> vectors = sqLiteCRUD.selectVector(sql);
			
			List<String> names = sqLiteCRUD.getFields(table);

			int nameIdx = getIdx(names, "Name");
			int fidIdx = getIdx(names, "ID");
			for (Vector<Object> vector : vectors) {
				String name = String.valueOf(vector.get(nameIdx));
				String fid = String.valueOf(vector.get(fidIdx));
				
				DPVersion version = new DPVersion();
				version.setId(fid);
				version.setAbs(name);
				version.setName(name);
				version.setDate("");
				version.setPerson("");
				
				versions.add(version);
			}
			sqLiteCRUD.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<DPVersion> getVersions() {
		return this.versions;
	}
	
	private int getIdx(List<String> names, String name) {
		for (int i = 0; i < names.size(); i++) {
			if (names.get(i).equalsIgnoreCase(name))
				return i;
		}
		return -1;
	}
	
	public static void updateBigVersion(List<DPVersion> versions, String user, String uid, String pwd, String nodeId) {
		for (DPVersion dpVersion : versions) {
			String version = dpVersion.id;
			
			WSDLClient w = new WSDLClient();
			
			String method = "getNodeDetail";
			String[] arg = new String[]{uid, pwd, nodeId, version};
			String xml = w.getS(method, arg);
			
			if (xml.contains("allDpNewest=\"1\""))
				dpVersion.bigversion = 1;
				
//			String version = dpVersion.id;
//			RelationService relationService = new RelationService(user, uid, pwd, nodeId, version);
//			if (checkBigVersion(relationService.mmResponse))
//				dpVersion.bigversion = 1;
		}
	}
	
//	private static boolean checkBigVersion(MMResponse mResponse) {
//		for (RelationItem relaItem : mResponse.dataPacks) {
//			if (relaItem.techStatus.equals("已完成") == false)
//				return false;
//		}
//		return true;
//	}
}
