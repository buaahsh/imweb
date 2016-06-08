package cn.edu.buaa.im.model;

import java.util.ArrayList;
import java.util.List;

public class ExtTreeNode{
	 public String id;
	 public String iconCls;
	 public String cls;
	 public String text;

	 public List<ExtTreeNode> children;
	 public boolean leaf;
	 public boolean singleClickExpand;
	 
	 public ExtTreeNode(String id, String text) {
		this.id = id;
		this.text = text;
		singleClickExpand = true;
		leaf = true;
	 }
	 
	 public void Add(ExtTreeNode extTreeNoede){
		 if (children == null)
			 children = new ArrayList<>();
		 children.add(extTreeNoede);
		 leaf = false;
	 }
}