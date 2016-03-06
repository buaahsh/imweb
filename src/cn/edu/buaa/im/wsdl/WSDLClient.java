package cn.edu.buaa.im.wsdl;

import java.util.HashMap;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.dom4j.Attribute;
import org.dom4j.Element;

import com.cssrc.webservice.service.Exception_Exception;
import com.cssrc.webservice.service.GetNodeDetail;
import com.cssrc.webservice.service.IDataService;
import com.cssrc.webservice.service.impl.DataServiceService;

import cn.edu.buaa.im.service.Utility;

public class WSDLClient {
	IDataService dataService;
	
	public WSDLClient() {
		DataServiceService dataServiceService = new DataServiceService();
		dataService = dataServiceService.getDataServicePort();
	}

	public String getS(String methodName, String[] args) {
		try {
//			String url = Utility.getParameter("wsdl_url");

//			String url = "http://202.112.140.210/MainModel/services/IDataService?wsdl";
			if (methodName.equals("getNodeDetail"))
				return dataService.getNodeDetail(args[0], args[1], args[2], args[3]);
			if (methodName.equals("getNodeHistory"))
				return dataService.getNodeHistory(args[0], args[1], args[2]);
			if (methodName.equals("getRelation"))
				return dataService.getNodeDependRelation(args[0], args[1]);
			if (methodName.equals("getDataAreas"))
				return dataService.getDataAreas(args[0], args[1]);
			if (methodName.equals("getSonnodes"))
				return dataService.getSonnodes(args[0], args[1], args[2], args[3], args[4]);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public HashMap<String, String> getFilePaths(String userid, String pwd, String mmid, String mmversion, List<String> nodeIds){
		String[] argsStrings = new String[]{userid, pwd, mmid, mmversion, "-1"};
		String result = this.getS("getSonnodes", argsStrings);
		Element root = Utility.getElementFromXml(result);
		
		HashMap<String, String> reHashMap = new HashMap<String, String>();
		
		String nowPath = "";
		BuildHashMap(root, reHashMap, nowPath);
		return reHashMap;
	}
	
	private void BuildHashMap(Element root, HashMap<String, String> reHashMap, String nowPath) {
		
		List<Element> elements = root.elements("node");
		for (Element element : elements) {	
			List<Attribute> list = element.attributes();
			boolean isDP = false;
			String name = "";
			String id = "";
			for (Attribute attribute : list) {
				if (attribute.getName().equals("type") && attribute.getValue().equals("dataPack"))
					isDP = true;
				if (attribute.getName().equals("name"))
					name = attribute.getValue();
				if (attribute.getName().equals("id"))
					id = attribute.getValue();
			}
			if(isDP)
				reHashMap.put(id, nowPath + name + "/结构化数据");
			else{
				BuildHashMap(element, reHashMap, nowPath + name + "/");
			}
		}
	}
	
	public static void main(String[] args) {
		WSDLClient wsdlClient = new WSDLClient();
//		String[] argsStrings = new String[]{"pdd", "123456", "1822"};
		String[] argsStrings = new String[]{"222", "123456", "2083", "6", "-1"};
//		System.out.println(wsdlClient.getS("getSonnodes", argsStrings));
		wsdlClient.getFilePaths("222", "123456", "2083", "6", null);
	}

}
