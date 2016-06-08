package cn.edu.buaa.im.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import cn.edu.buaa.im.data.SQLiteCRUD;
import cn.edu.buaa.im.data.SQLiteConn;
import cn.edu.buaa.im.model.ViewItem;

public class ViewService {
	public String cid;
	public List<ViewItem> viewItems;
	
	public ViewService(String cid, String sid_702) {
		this.cid = cid;
		this.viewItems = new ArrayList<>();
		init(sid_702);
	}
	
	private void init(String sid_702) {
		String s = Utility.getSQLite(sid_702);
		SQLiteConn sqLiteConn = new SQLiteConn(s);
		try {
//			SQLiteCRUD sqLiteCRUD = new SQLiteCRUD(sqLiteConn.getConnection());
//			String table = "SchemasDefinition";
//			String key = "Remark";
//			Vector<Vector<Object>> vectors = sqLiteCRUD.selectVector(table, key, this.cid);
//			List<String> names = sqLiteCRUD.getFields(table);
//			
//			// 先找到结构化数据的sid，根据结构化数据的sid去找视图的sid
//			int sidIdx = getIdx(names, "id");
//			
//			List<String> structureSid = new ArrayList<>();
//			
//			for (Vector<Object> vector : vectors) {
//				structureSid.add(String.valueOf(vector.get(sidIdx)));
//			}
//			
//			//根据结构化数据的sid去找视图的sid
//			table = "SchemasDefinition";
//			key = "Remark";
//			names = sqLiteCRUD.getFields(table);
//			sidIdx = getIdx(names, "id");
//			int nameIdx = getIdx(names, "name");
//			
//				
//			for (String string : structureSid) {
//				vectors = sqLiteCRUD.selectVector(table, key, string);
//				
//				for (Vector<Object> vector : vectors) {
//					ViewItem viewItem = new ViewItem();
//					viewItem.sid = String.valueOf(vector.get(sidIdx));
//					viewItem.name = String.valueOf(vector.get(nameIdx));
//					this.viewItems.add(viewItem);				
//				}
//			}
			
			
			// 针对数据库，只显示视图，其中type=5
			
			SQLiteCRUD sqLiteCRUD = new SQLiteCRUD(sqLiteConn.getConnection());
			String table = "SchemasDefinition";
//			String key = "Remark";
			String sql = String.format("select * from SchemasDefinition where Remark='%s' and Type = 5;", this.cid);
			Vector<Vector<Object>> vectors = sqLiteCRUD.selectVector(sql);
//			Vector<Vector<Object>> vectors = sqLiteCRUD.selectVector(table, key, this.cid);
			List<String> names = sqLiteCRUD.getFields(table);
			
			// 先找到结构化数据的sid，根据结构化数据的sid去找视图的sid
			int sidIdx = getIdx(names, "id");
			int nameIdx = getIdx(names, "name");
			
//			List<String> structureSid = new ArrayList<>();
			
			for (Vector<Object> vector : vectors) {
				ViewItem viewItem = new ViewItem();
				viewItem.sid = String.valueOf(vector.get(sidIdx));
				viewItem.name = String.valueOf(vector.get(nameIdx));
				this.viewItems.add(viewItem);	
			}
			sqLiteCRUD.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private int getIdx(List<String> names, String name) {
		for (int i = 0; i < names.size(); i++) {
			if (names.get(i).equalsIgnoreCase(name))
				return i;
		}
		return -1;
	}
}
