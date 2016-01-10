package cn.edu.buaa.im.model;

import java.util.List;

public class DataPacketJson {
	public String name;
	public int type;
	public int pid;
	public String sid;
	public int order;
	public int level;
	public int childcount;
	public String remark;
	public int closed;
	public String created_datetime;
	public String update_datetime;
	public List<DataItemJson> data;

}
