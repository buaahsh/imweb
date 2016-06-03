package cn.edu.buaa.im.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import cn.edu.buaa.im.data.SQLiteCRUD;
import cn.edu.buaa.im.data.SQLiteConn;
import cn.edu.buaa.im.model.ViewItem;

/**
 * 负责数据模板提取的相关接口，
 * 主要完成两个任务。
 * 1、找到模板提取id列表 
 * select * from SchemasDefinition as A where A.[Type] = 6 
 * and A.[Remark] = "测试主模型20160302/气动/集中式气动特性/结构化数据"
 * 2、通过数据模板提取获取数据，这个过程跟视图的过程类似
 * select * from FieldsDefinition as A where 
 * A.[Sid] = "测试主模型20160302/气动/集中式气动特性/气动力数据提取模板01" 
 * ORDER BY A.[Level], A.[Order];
 * @author Shaohan
 *
 */
public class DataExtractService {
	public class DataExtractInfo{
		public String id;
		public String name;
	}
	public static List<ViewItem> geDataExtractInfos(String cid, String sid_702) {
		List<ViewItem> viewItems = new ArrayList<>();
		String s = Utility.getSQLite(sid_702);
		SQLiteConn sqLiteConn = new SQLiteConn(s);
		try {
			// 针对数据库，只显示数据提取模板，其中type=6
			
			SQLiteCRUD sqLiteCRUD = new SQLiteCRUD(sqLiteConn.getConnection());
			String table = "SchemasDefinition";
			String sql = String.format("select * from SchemasDefinition where Remark='%s' and Type = 6;", cid);
			Vector<Vector<Object>> vectors = sqLiteCRUD.selectVector(sql);
			List<String> names = sqLiteCRUD.getFields(table);
			
			// 先找到结构化数据的sid，根据结构化数据的sid去找视图的sid
			int sidIdx = getIdx(names, "id");
			int nameIdx = getIdx(names, "name");
			
			for (Vector<Object> vector : vectors) {
				ViewItem viewItem = new ViewItem();
				viewItem.sid = String.valueOf(vector.get(sidIdx));
				viewItem.name = String.valueOf(vector.get(nameIdx));
				viewItems.add(viewItem);	
			}
			sqLiteCRUD.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return viewItems;
	}
	
	private static int getIdx(List<String> names, String name) {
		for (int i = 0; i < names.size(); i++) {
			if (names.get(i).equalsIgnoreCase(name))
				return i;
		}
		return -1;
	}
}
