package cn.edu.buaa.im.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import cn.edu.buaa.im.model.BaseData;
import cn.edu.buaa.im.model.DataItem;
import cn.edu.buaa.im.model.BaseData.FileDataItem;
import cn.edu.buaa.im.model.BaseData.FloatDataItem;
import cn.edu.buaa.im.model.BaseData.ImageDataItem;
import cn.edu.buaa.im.model.BaseData.TextDataItem;
import cn.edu.buaa.im.model.BaseData.UrlDataItem;

public class DataItemServlet extends BaseServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws  IOException {
		List<DataItem> dataItems = new ArrayList<DataItem>();
		
		String title = "dataitem0";
		String id = "dataitem0";
		ImageDataItem di0 =  BaseData.getInstanceBaseData().new ImageDataItem();
		di0.urls = new ArrayList<String>();
		di0.urls.add("./img/ex_1.jpg");
		di0.urls.add("./img/ex_2.jpg");
		di0.urls.add("./img/ex_3.jpg");
		di0.urls.add("./img/ex_4.jpg");		
		DataItem dataItem = new DataItem(title, id, di0);
		dataItems.add(dataItem);
		
		title = "dataitem1";
		id = "dataitem1";
		ImageDataItem di1 =  BaseData.getInstanceBaseData().new ImageDataItem();
		di1.urls = new ArrayList<String>();
		di1.urls.add("./img/ex_1.jpg");
		di1.urls.add("./img/ex_2.jpg");
		di1.urls.add("./img/ex_3.jpg");
		di1.urls.add("./img/ex_4.jpg");		
		dataItem = new DataItem(title, id, di1);
		dataItems.add(dataItem);
		
		title = "dataitem2";
		id = "dataitem2";
		FileDataItem di2 =  BaseData.getInstanceBaseData().new FileDataItem();
		di2.filePaths = new ArrayList<String>();
		di2.filePaths.add("./img/ex_1.jpg");
		di2.filePaths.add("./img/ex_2.jpg");
		dataItem = new DataItem(title, id, di2);
		dataItems.add(dataItem);
		
		title = "dataitem3";
		id = "dataitem3";
		FileDataItem di3 =  BaseData.getInstanceBaseData().new FileDataItem();
		di3.filePaths = new ArrayList<String>();
		di3.filePaths.add("./img/ex_1.jpg");
		di3.filePaths.add("./img/ex_2.jpg");
		dataItem = new DataItem(title, id, di3);
		dataItems.add(dataItem);
		
		title = "dataitem4";
		id = "dataitem4";
		TextDataItem di4 =  BaseData.getInstanceBaseData().new TextDataItem();
		di4.text = new ArrayList<String>();
		di4.text.add("推进剂又称推进药，有规律地燃烧释放出能量，产生气体，推送火箭和导弹的火药。");
		di4.text.add("推进剂具有下列特性：①比冲量高；②密度大；③燃烧产物的气体（或蒸气）分子量小，离解度小，无毒、无烟、无腐蚀性，不含有凝聚态物质；④火焰温度不应过高，以免烧蚀喷管；⑤应有较宽的温度适应范围；");
		dataItem = new DataItem(title, id, di4);
		dataItems.add(dataItem);
		
		title = "dataitem5";
		id = "dataitem5";
		TextDataItem di5 =  BaseData.getInstanceBaseData().new TextDataItem();
		di5.text = new ArrayList<String>();
		di5.text.add("推进剂又称推进药，有规律地燃烧释放出能量，产生气体，推送火箭和导弹的火药。");
		di5.text.add("推进剂具有下列特性：①比冲量高；②密度大；③燃烧产物的气体（或蒸气）分子量小，离解度小，无毒、无烟、无腐蚀性，不含有凝聚态物质；④火焰温度不应过高，以免烧蚀喷管；⑤应有较宽的温度适应范围；");
		dataItem = new DataItem(title, id, di5);
		dataItems.add(dataItem);
		
		title = "dataitem6";
		id = "dataitem6";
		FloatDataItem di6 =  BaseData.getInstanceBaseData().new FloatDataItem();
		di6.unit = "MPa";
		di6.value = 2000;
		dataItem = new DataItem(title, id, di6);
		dataItems.add(dataItem);
		
		title = "dataitem7";
		id = "dataitem7";
		FloatDataItem di7 =  BaseData.getInstanceBaseData().new FloatDataItem();
		di7.unit = "MPa";
		di7.value = 2000;
		dataItem = new DataItem(title, id, di7);
		dataItems.add(dataItem);
		
		title = "dataitem8";
		id = "dataitem8";
		UrlDataItem di8 =  BaseData.getInstanceBaseData().new UrlDataItem();
		di8.links = new ArrayList<String>();
		di8.links.add("http://baidu.com");
		di8.links.add("http://baidu.com");
		dataItem = new DataItem(title, id, di8);
		dataItems.add(dataItem);
		
		Gson gson = new Gson();
		responseString(response, gson.toJson(dataItems));
		//String arg = request.getParameter("arg");
	}
}
