package cn.edu.buaa.im.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import cn.edu.buaa.im.data.SQLiteCRUD;
import cn.edu.buaa.im.data.SQLiteConn;
import cn.edu.buaa.im.model.BaseData;
import cn.edu.buaa.im.model.DataItem;
import cn.edu.buaa.im.model.TreeNode;
import cn.edu.buaa.im.model.BaseData.CurveDataItem;
import cn.edu.buaa.im.model.BaseData.D3DataItem;
import cn.edu.buaa.im.model.BaseData.FloatDataItem;
import cn.edu.buaa.im.model.BaseData.SubtitleDataItem;
import cn.edu.buaa.im.model.BaseData.TextDataItem;
import cn.edu.buaa.im.model.BaseData.TitleDataItem;

public class DataItemService {
	private String sid;
	private String cid;
	
	private List<DataItem> dataItems;
	
	public DataItemService(String sid, String cid){
		this.sid = sid;
		this.cid = cid;
		dataItems = new ArrayList<DataItem>();
		init();
	}
	
	private void init() {
		TreeNodeService treeNodeService = new TreeNodeService("68");
		List<TreeNode> treeNodes = treeNodeService.geTreeNodes();
		buildDataItems(treeNodes);
	}
	
	private void buildDataItems(List<TreeNode> treeNodes) {
		String s = Utility.getSQLite();
		SQLiteConn sqLiteConn = new SQLiteConn(s);
		try {
			SQLiteCRUD sqLiteCRUD = new SQLiteCRUD(sqLiteConn.getConnection());
			String table = "table" + this.sid;
			
			for (TreeNode treeNode : treeNodes) {
				String fid = treeNode.id.substring(3);
				String sql = String.format("select value from %s where Cid = %s and Fid = %s;", table,
						this.cid, fid);
				Vector<Vector<Object>> vectors = sqLiteCRUD.selectVector(sql);
				
				String value = "";
				
				if (vectors.size() > 0){
					value = String.valueOf(vectors.get(0).get(0));
				}
				
				DataItem dataItem = Convert2DataItem(treeNode, value);
				
				this.dataItems.add(dataItem);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<DataItem> getDataItems() {
		return this.dataItems;
	}
	
	private DataItem Convert2DataItem(TreeNode treeNode, String value) {
		DataItem dataitem = null;
		BaseData baseData = null;
		if (treeNode.parent.equals("#")){
			baseData = BaseData.getInstanceBaseData().new TitleDataItem();
			dataitem = new DataItem(treeNode.text, treeNode.a_attr.href, baseData);
		}
		else if (treeNode.type.equals("标题")){
			baseData = BaseData.getInstanceBaseData().new SubtitleDataItem();
			dataitem = new DataItem(treeNode.text, treeNode.a_attr.href, baseData);
		}
		else if (treeNode.type.equals("文本")){
			TextDataItem textDataItem = BaseData.getInstanceBaseData().new TextDataItem();
			textDataItem.text = new ArrayList<String>();
			textDataItem.text.add("推进剂又称推进药，有规律地燃烧释放出能量，产生气体，推送火箭和导弹的火药。");
			textDataItem.text.add("推进剂具有下列特性：①比冲量高；②密度大；③燃烧产物的气体（或蒸气）分子量小，离解度小，无毒、无烟、无腐蚀性，不含有凝聚态物质；④火焰温度不应过高，以免烧蚀喷管；⑤应有较宽的温度适应范围；");
			dataitem = new DataItem(treeNode.text, treeNode.a_attr.href, textDataItem);
		}
		else if (treeNode.type.equals("浮点数")){
			Random r = new Random();
			FloatDataItem floatDataItem =  BaseData.getInstanceBaseData().new FloatDataItem();
			floatDataItem.unit = treeNode.unit;
			floatDataItem.value = r.nextFloat() * 100;
			dataitem = new DataItem(treeNode.text, treeNode.a_attr.href, floatDataItem);
		}
		else if (treeNode.type.equals("曲线")){
			CurveDataItem curveDataItem =  BaseData.getInstanceBaseData().new CurveDataItem();
			curveDataItem.table = null;
			dataitem = new DataItem(treeNode.text, treeNode.a_attr.href, curveDataItem);
		}
		else if (treeNode.type.equals("三维模型")){
			D3DataItem d3 =  BaseData.getInstanceBaseData().new D3DataItem();
			d3.link = "/imweb/DataItem?arg=123";
			dataitem = new DataItem(treeNode.text, treeNode.a_attr.href, d3);
		}
		else{
			TextDataItem textDataItem = BaseData.getInstanceBaseData().new TextDataItem();
			textDataItem.text = new ArrayList<String>();
			textDataItem.text.add(value);
			dataitem = new DataItem(treeNode.text, treeNode.a_attr.href, textDataItem);
		}
		return dataitem;
	}
}
