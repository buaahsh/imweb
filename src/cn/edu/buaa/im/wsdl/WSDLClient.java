package cn.edu.buaa.im.wsdl;

import javax.xml.namespace.QName;

import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import cn.edu.buaa.im.service.Utility;

public class WSDLClient {

	private static WSDLClient instance = new WSDLClient();

	private WSDLClient() {
	}

	public static WSDLClient getInstance() {
		return instance;
	}

	public String getS(String methodName, String[] args) {
		try {
			String url = Utility.getParameter("wsdl_url");

//			String url = "http://202.112.140.210/MainModel/services/IDataService?wsdl";
					
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory
					.newInstance();
			org.apache.cxf.endpoint.Client client = dcf
					.createClient(url);
			// sayHello 为接口中定义的方法名称 张三为传递的参数 返回一个Object数组
			QName opName = new QName("http://service.webservice.cssrc.com/", methodName); 
			Object[] objects = client.invoke(opName, args);
			// 输出调用结果
			Utility.Log(objects[0].toString());
			return objects[0].toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		WSDLClient w = WSDLClient.getInstance();
		
//		String method = "getNodeHistory";
//		String[] arg = new String[]{"pdd", "123456", "1444"};
//		w.getS(method, arg);
//		
		String method = "getNodeDetail";
		String[] arg = new String[]{"pdd", "123456", "1445", "77"};
		System.out.println(w.getS(method, arg));
		
	}

}
