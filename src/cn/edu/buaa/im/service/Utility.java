package cn.edu.buaa.im.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import cn.edu.buaa.im.wsdl.HttpClientUtils;

public class Utility {

	public static String getSQLite(String sid) {
		
		if (sid == null)
			return getParameter("sqlile_url");
		
		String filePath = sid + ".db";
		
		String ROOTString = null;
		try {
			ROOTString = Utility.class.getClassLoader().getResource("")
					.toURI().getPath();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		filePath = ROOTString + filePath;
		
		File file = new File(filePath);
		
		if (file.isFile())
			return filePath;
	
		//Get SQLite, 对db文件进行下载
		String url = getParameter("db_url");
		
		HttpClientUtils client = new HttpClientUtils();
		byte[] bs = client.getDoGetURL(url + sid);

		if (bs != null){
			FileOutputStream fos;
			try {
				fos = new FileOutputStream(file);
				fos.write(bs);
				fos.close();
			} catch (IOException e) {
				file.delete();
				e.printStackTrace();
			}
			return filePath;
		}
		else{
			file.delete();
			return null;
		}
	}
	
	/**
	 * 从配置文件读取相应的参数
	 * @param key
	 * @return
	 */
	public static String getParameter(String key) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		Properties properties = new Properties();
		try {
			properties.load(classLoader.getResourceAsStream("/parameter.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String p = properties.getProperty(key);
		return p;
	}
	
	/**
	 * 打印log
	 * @param info
	 */
	public static void Log(String info) {
		Date date = new Date();
	    String str=null;  
	    str=String.format("Time:%s Info:%s", date.toString(), info); 
		System.out.println(str);
	}
	
	@SuppressWarnings("unchecked")
	public static Element getElementFromXml(String xml) {
		try {
	        Document document = DocumentHelper.parseText(xml);
			Element root = document.getRootElement();  
			List<Attribute> list = root.attributes();
			for (Attribute attribute : list) {
				if (attribute.getName().equals("result")
						&& attribute.getValue().equals("false"))
					return null;
			}
			return root;
		} catch (DocumentException e) {
			e.printStackTrace();
		}  
		return null;
	}
	
}
