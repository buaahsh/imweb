package cn.edu.buaa.im.wsdl;

import javax.xml.namespace.QName;

import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		
	}

}
