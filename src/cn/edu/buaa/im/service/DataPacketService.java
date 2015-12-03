package cn.edu.buaa.im.service;

import java.util.List;
import java.util.Vector;

import com.google.gson.Gson;

import cn.edu.buaa.im.data.SQLiteCRUD;
import cn.edu.buaa.im.data.SQLiteConn;
import cn.edu.buaa.im.model.DataPacketAbs;
import cn.edu.buaa.im.model.TreeNode;

public class DataPacketService {
	private String cid;
	private String pcid;
	private String psid;
	
	DataPacketAbs dataPacket = new DataPacketAbs();
	
	public DataPacketService(String cid){
		this.cid = cid;
		
		init();
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
				
				if (treeNode.text.equals("数据包承担人")){
					dataPacket.setKeyword(value);
				}
			}
			dataPacket.setAbs("结合最新版本的三维原理，图V3.0版本重新进行了集中式气动特性的计算，其中XX数值变化较大");
			dataPacket.setCat("方案设计阶段");
			dataPacket.setDeadline("根据任务状态自动判断");
			dataPacket.setModel("第二轮");
			dataPacket.setSub("动力学");
			sqLiteCRUD.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public DataPacketAbs getDataPacketAbs() {
		return this.dataPacket;
	}
	
	private class DPRemark{
		public String sid;
		public String cid;
	}
}
