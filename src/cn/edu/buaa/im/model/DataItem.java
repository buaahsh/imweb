package cn.edu.buaa.im.model;

public class DataItem {
	public String title;
	public String type;
	public String id;
	public BaseData data;
	
	public DataItem(String title, String id, BaseData baseData){
		this.title = title;
		this.id = id;
		this.data = baseData;
		if (baseData == null)
			this.type = "subtitle";
		else
			this.type = baseData.getClass().getSimpleName();
	}
}

