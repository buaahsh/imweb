package cn.edu.buaa.im.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import cn.edu.buaa.im.data.SQLiteCRUD;
import cn.edu.buaa.im.data.SQLiteConn;
import cn.edu.buaa.im.model.TreeNode;

/**
 * 通过一个sid，从数据库FieldsDefinition中找到相关的Fields，构建出整个树
 * @author Shaohan
 *
 */
public class TreeNodeService {
	
	private String sid;
	private List<TreeNode> treeNodes;
	
	public TreeNodeService(String sid){
		this.sid = sid;
		treeNodes = new ArrayList<>();
		init();
	}
	
	private void init() {
		String s = Utility.getSQLite();
		SQLiteConn sqLiteConn = new SQLiteConn(s);
		try {
			SQLiteCRUD sqLiteCRUD = new SQLiteCRUD(sqLiteConn.getConnection());
			String table = "FieldsDefinition";
			String key = "Sid";
			Vector<Vector<Object>> vectors = sqLiteCRUD.selectVector(table, key, this.sid);
			List<String> names = sqLiteCRUD.getFields(table);
			
			int nameIdx = getIdx(names, "Name");
			int fidIdx = getIdx(names, "ID");
			int typeIdx = getIdx(names, "Type");
			int pidIdx = getIdx(names, "PID");
			
			for (Vector<Object> vector : vectors) {
				String name = String.valueOf(vector.get(nameIdx));
				String fid = "fid" + String.valueOf(vector.get(fidIdx));
				String type = String.valueOf(vector.get(typeIdx));
				String pid = "fid" + String.valueOf(vector.get(pidIdx));
				String parent = GetParent(pid);
				String icon = GetIcon(type);
				
				TreeNode treeNode = new TreeNode(fid, parent, name, icon);
				treeNode.type = type;
				treeNodes.add(treeNode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<TreeNode> geTreeNodes() {
		return this.treeNodes;
	}
	
	private int getIdx(List<String> names, String name) {
		for (int i = 0; i < names.size(); i++) {
			if (names.get(i).equalsIgnoreCase(name))
				return i;
		}
		return -1;
	}
	
	private String GetParent(String pid) {
		if (pid.equals("fid0"))
			return "#";
		return pid;
	}
	
	private String GetIcon(String type) {
		if (type.equals("16"))
			return "folder";
		return "file";
	}
}
