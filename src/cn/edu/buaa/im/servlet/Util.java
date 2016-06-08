package cn.edu.buaa.im.servlet;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import cn.edu.buaa.im.model.BaseData.FloatDataItem;
import cn.edu.buaa.im.model.BaseData.TableDataItem;
import cn.edu.buaa.im.model.BaseData.TextDataItem;
import cn.edu.buaa.im.model.DataItem;
import cn.edu.buaa.im.model.ExtTreeNode;
import cn.edu.buaa.im.model.TreeNode;

public class Util {
	public static byte[] toByteArray(String filename) throws IOException {
		File f = new File(filename);
		if (!f.exists()) {
			throw new FileNotFoundException(filename);
		}

		ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
		BufferedInputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(f));
			int buf_size = 1024;
			byte[] buffer = new byte[buf_size];
			int len = 0;
			while (-1 != (len = in.read(buffer, 0, buf_size))) {
				bos.write(buffer, 0, len);
			}
			return bos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			bos.close();
		}
	}

	public static String readToString(String fileName) {
	        String encoding = "GBK";  
	        File file = new File(fileName);  
	        Long filelength = file.length();  
	        byte[] filecontent = new byte[filelength.intValue()];  
	        try {  
	            FileInputStream in = new FileInputStream(file);  
	            in.read(filecontent);  
	            in.close();  
	        } catch (FileNotFoundException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	        try {  
	            return new String(filecontent, encoding);  
	        } catch (UnsupportedEncodingException e) {  
	            System.err.println("The OS does not support " + encoding);  
	            e.printStackTrace();  
	            return null;  
	        }  
	    }

	public static String recover(String str) {        
		try {
			return new String(str.getBytes("GBK"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}    
		return "";
	}
	
	public static String byte2str(String hexStr) {
		// String hexStr = "e68891e698afe6b58be8af95313233e696b0e5b9b4e5a5bd";
		if (hexStr == null)
			return "";
		int length = hexStr.length();
		if (length % 2 != 0) {
			System.out.println("hex error,长度必须是偶数");
		}
		byte[] bytes = new byte[length / 2];
		for (int i = 0, j = 0; i < length; i += 2, j++) {
			String elementHex = String.format("%c%c", hexStr.charAt(i),
					hexStr.charAt(i + 1));
			int value = Integer.parseInt(elementHex, 16);
//			System.out.println(elementHex + ":" + value);
			bytes[j] = (byte) (value & 0xFF);
		}
		try {
			String str = new String(bytes, "utf-8");
//			System.out.println(str);
			return str;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static byte[] dataItems2byte(List<DataItem> dataItems){
		StringBuffer sb = new StringBuffer();
		Gson gson = new Gson();
		try {
			for (DataItem dataItem : dataItems) {
				sb.append(dataItem.title + "\r\n");
				if (dataItem.data.toString().contains("TableDataItem"))
				{
					TableDataItem tableDataItem = (TableDataItem) dataItem.data;
					TableJson tableJson = gson.fromJson(tableDataItem.value, TableJson.class);
					sb.append(join(tableJson.header) + "\r\n");
					for(List<String> strings : tableJson.body){
						sb.append(join(strings) + "\r\n");
					}
				}
				else if (dataItem.data.toString().contains("TextDataItem"))
				{
					TextDataItem textDataItem = (TextDataItem) dataItem.data;
					for(String s: textDataItem.text)
						sb.append(s + "\r\n");
				}
				else if (dataItem.data.toString().contains("FloatDataItem"))
				{
					FloatDataItem floatDataItem = (FloatDataItem) dataItem.data;
					sb.append(floatDataItem.value + "\t" + floatDataItem.unit + "\r\n");
				}
			}
			byte[] midbytes=sb.toString().getBytes("UTF8");
			return midbytes;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static String join(List<String> strings) {
		StringBuffer sb = new StringBuffer();
		for (String string : strings) {
			sb.append(string + "\t");
		}
		return sb.toString();
	}
	
	public class TableJson{
		public List<List<String>> body;
		public List<String> header; 
	}
	
	public static List<ExtTreeNode> Convert2Ext(List<TreeNode> treeNodes){
		List<ExtTreeNode> extTreeNodes = new ArrayList<>();
		
		for (TreeNode treeNode : treeNodes) {
			if (treeNode.parent.equals("#")){
				ExtTreeNode extTreeNode = new ExtTreeNode(treeNode.id, treeNode.text);
				extTreeNodes.add(extTreeNode);
			}
			else{
				Insert(extTreeNodes, treeNode);
			}
		}
		return extTreeNodes;
	}
	
	public static boolean Insert(List<ExtTreeNode> extTreeNodes, TreeNode treeNode){
		for (ExtTreeNode extTreeNoede : extTreeNodes) {
			if (treeNode.parent.equals(extTreeNoede.id)){
				ExtTreeNode node = new ExtTreeNode(treeNode.id, treeNode.text);
				extTreeNoede.Add(node);
				return true;
			}
			if (extTreeNoede.children != null)
				if (Insert(extTreeNoede.children, treeNode)) {
					return true;
				}
		}
		return false;
	}
	
	public static void AddParents(List<TreeNode> nodes, List<DataItem> dataItems)
	{
		for (DataItem dataitem : dataItems) {
			if (dataitem.type.equals("SubtitleDataItem") )
			{
				
				List<String> parents = new ArrayList<String>();
				getParentList(nodes, dataitem.id.split("_")[0], parents);
				dataitem.parents = parents;
			}
		}
	}
	
	private static void getParentList(List<TreeNode> nodes, String id, List<String> parents) {
		for (TreeNode node : nodes) {
			if (node.id.equals(id)){
				String pid = node.parent;
				for (TreeNode n : nodes) {
					if (n.id.equals(pid))
					{
						parents.add(n.text);
						if (n.parent.equals("#") == false)
							getParentList(nodes, pid, parents);
						break;
					}
					
				}
			}
				
		}
	}
}
