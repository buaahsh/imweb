package cn.edu.buaa.im.model;

import java.util.ArrayList;
import java.util.List;

public class PersonalTreeNode{
	
	public List<PersonalTreeNode> children;
	public boolean checked;
	
	public String id;
	public String iconCls;
	public String cls;
	public String text;
	
	public boolean leaf;
	public boolean singleClickExpand;
	 
	public PersonalTreeNode(String id, String text) {
		this.id = id;
		this.text = text;
		singleClickExpand = true;
		leaf = true;
		this.checked = false;
	 }
	 
	public void Add(PersonalTreeNode extTreeNoede){
		if (children == null)
			children = new ArrayList<>();
		children.add(extTreeNoede);
		leaf = false;
	}

	public boolean isChecked() {
		return checked;
	}
	
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}
