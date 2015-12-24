package cn.edu.buaa.im.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Utility {

	public static String getSQLite() {
		return getParameter("sqlile_url");
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
