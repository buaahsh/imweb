package cn.edu.buaa.im.model;

import java.util.List;

public class DataItem {
	public String title;
	public String type;
	public String id;
	public BaseData data;
	public List<String> parents;
	
	public DataItem(String title, String id, BaseData baseData){
		this.title = title;
		this.id = id.replace("#", "");
		this.data = baseData;
		this.type = baseData.getClass().getSimpleName();
	}
}

