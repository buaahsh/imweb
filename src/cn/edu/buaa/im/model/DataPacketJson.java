package cn.edu.buaa.im.model;

import java.util.List;

public class DataPacketJson {
	public String name;
	public int type;
	public int pid;
	public int sid;
	public int order;
	public int level;
	public int childcount;
	public String remark;
	public int closed;
	public long created_datetime;
	public long update_datetime;
	public List<DataItemJson> data;

}
