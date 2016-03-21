package cn.edu.buaa.im.wsdl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;

import cn.edu.buaa.im.model.BaseData;
import cn.edu.buaa.im.model.DataItem;
import cn.edu.buaa.im.model.DataItemJson;
import cn.edu.buaa.im.model.DataPacketJson;
import cn.edu.buaa.im.model.TreeNode;
import cn.edu.buaa.im.model.BaseData.CurveDataItem;
import cn.edu.buaa.im.model.BaseData.FloatDataItem;
import cn.edu.buaa.im.model.BaseData.SubtitleDataItem;
import cn.edu.buaa.im.model.BaseData.TableDataItem;
import cn.edu.buaa.im.model.BaseData.TextDataItem;
import cn.edu.buaa.im.model.BaseData.TitleDataItem;
import cn.edu.buaa.im.service.Utility;
import cn.edu.buaa.im.servlet.Util;

public class WSDLFile {
	
	public HashMap<String, Object> GetHashMap(String json) {		
		Gson gson = new Gson();
		DataPacketJson dataPacketJson = gson.fromJson(json, DataPacketJson.class);
		
		List<TreeNode> treeNodes = new ArrayList<TreeNode>();
		List<DataItem> dataItems = new ArrayList<DataItem>();
		
		for (DataItemJson dataItem : dataPacketJson.data) {
			GenDataItem(treeNodes, dataItems, dataItem, "#");
		}
		
		HashMap<String, Object> result = new HashMap<String, Object>();
		Util.AddParents(treeNodes, dataItems);
		result.put("TreeNode", treeNodes);
		result.put("DataItem", dataItems);
		
		return result;
	}
	
	public void GenDataItem(List<TreeNode> treeNodes, List<DataItem> dataItems, 
			DataItemJson dataItemJson, String pid) {
		
		String name = dataItemJson.name;
		String fid = "fid" + String.valueOf(dataItemJson.fid);
		String type = String.valueOf(dataItemJson.type);
		String icon = GetIcon(type);
		String unit = dataItemJson.remark;
		String remark = dataItemJson.remark3;
		
		TreeNode treeNode = new TreeNode(fid, pid, name, icon);
		treeNode.type = type;
		treeNode.unit = unit;
		
		treeNodes.add(treeNode);
		
		DataItem dataItem = Convert2DataItem(treeNode, dataItemJson.value, remark);
		
		dataItems.add(dataItem);
		
		if (dataItemJson.data != null){
			for (DataItemJson dataItemJson2 : dataItemJson.data) {
				GenDataItem(treeNodes, dataItems, dataItemJson2, fid);
			}
		}
	}

	private String GetIcon(String type) {
		// 对应小白的type进行调整
		if (type.equals("4"))
			return "folder";
		return "file";
	}
	
	public DataItem Convert2DataItem(TreeNode treeNode, String value, String remark) {
		DataItem dataitem = null;
		BaseData baseData = null;
		//if (treeNode.parent.equals("#") && treeNode.type.equals("4")){
		if (treeNode.parent.equals("#")){
			baseData = BaseData.getInstanceBaseData().new TitleDataItem();
			dataitem = new DataItem(treeNode.text, treeNode.a_attr.href, remark, baseData);
		}
		if (treeNode.type.equals("4")){ //分类
			baseData = BaseData.getInstanceBaseData().new SubtitleDataItem();
			dataitem = new DataItem(treeNode.text, treeNode.a_attr.href, remark, baseData);
		}
		else if (treeNode.type.equals("8")){ //浮点数
			FloatDataItem floatDataItem =  BaseData.getInstanceBaseData().new FloatDataItem();
			floatDataItem.unit = treeNode.unit;
			floatDataItem.value = value;
			dataitem = new DataItem(treeNode.text, treeNode.a_attr.href, remark, floatDataItem);
		}
		else if (treeNode.type.equals("19")){ //文本 
			TextDataItem textDataItem = BaseData.getInstanceBaseData().new TextDataItem();
			textDataItem.text = new ArrayList<String>();
			textDataItem.text.add(value);
			dataitem = new DataItem(treeNode.text, treeNode.a_attr.href, remark, textDataItem);
		}
		else if (treeNode.type.equals("19")){
			CurveDataItem curveDataItem =  BaseData.getInstanceBaseData().new CurveDataItem();
			curveDataItem.table = null;
			dataitem = new DataItem(treeNode.text, treeNode.a_attr.href, remark, curveDataItem);
		}
		else if (treeNode.type.equals("20")){ // figure
		}
		else if (treeNode.type.equals("21")){ //model
		}
		// TODO : 浮点数选项
		else if (treeNode.type.equals("22")){
			TextDataItem textDataItem = BaseData.getInstanceBaseData().new TextDataItem();
			textDataItem.text = new ArrayList<String>();
			textDataItem.text.add(value);
			dataitem = new DataItem(treeNode.text, treeNode.a_attr.href, remark, textDataItem);
		}
		// TODO : 曲线簇模型
		else if (treeNode.type.equals("23")){
			TextDataItem textDataItem = BaseData.getInstanceBaseData().new TextDataItem();
			textDataItem.text = new ArrayList<String>();
			textDataItem.text.add(value);
			dataitem = new DataItem(treeNode.text, treeNode.a_attr.href, remark, textDataItem);
		}
		else if (treeNode.type.equals("24")){
			TextDataItem textDataItem = BaseData.getInstanceBaseData().new TextDataItem();
			textDataItem.text = new ArrayList<String>();
			textDataItem.text.add(value);
			dataitem = new DataItem(treeNode.text, treeNode.a_attr.href, remark, textDataItem);
		}
		// TODO : 实例链接
		else if (treeNode.type.equals("25")){
			TextDataItem textDataItem = BaseData.getInstanceBaseData().new TextDataItem();
			textDataItem.text = new ArrayList<String>();
			textDataItem.text.add(value);
			dataitem = new DataItem(treeNode.text, treeNode.a_attr.href, remark, textDataItem);
		}
		else if (treeNode.type.equals("10")){ //二维表
			TableDataItem tableItem = BaseData.getInstanceBaseData().new TableDataItem();
			// 根据小白的设计，value可能为空，表头放在remark里面
			tableItem.value = value;
			if (value == null || value.isEmpty())
				tableItem.value = treeNode.unit;
			dataitem = new DataItem(treeNode.text, treeNode.a_attr.href, remark, tableItem);
		}
		else{
			TextDataItem textDataItem = BaseData.getInstanceBaseData().new TextDataItem();
			textDataItem.text = new ArrayList<String>();
			textDataItem.text.add(value);
			dataitem = new DataItem(treeNode.text, treeNode.a_attr.href, remark, textDataItem);
		}
		return dataitem;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		test();
		String s = Util.readToString("/Users/hsh/Desktop/json.txt");
		WSDLFile wsdlFile = new WSDLFile();
		wsdlFile.GetHashMap(s);
	}

}
