package cn.edu.buaa.im.wsdl;

import javax.xml.namespace.QName;

import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

public class WSDLClient {

	private static WSDLClient instance = new WSDLClient();

	private WSDLClient() {
	}

	public static WSDLClient getInstance() {
		return instance;
	}

	public void getS() {
		try {
			// axis1 服务端
			// String url =
			// "http://localhost:8080/StockQuote/services/StockQuoteServiceSOAP11port?wsdl";
			// axis2 服务端
			String url = "http://202.112.140.210/MainModel/services/IDataService?wsdl";

			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory
					.newInstance();
			org.apache.cxf.endpoint.Client client = dcf
					.createClient(url);
			// sayHello 为接口中定义的方法名称 张三为传递的参数 返回一个Object数组
			QName opName = new QName("http://service.webservice.cssrc.com/", "getNodeHistory"); 
			Object[] objects = client.invoke(opName, "pdd", "123456", "1444");
			// 输出调用结果
			System.out.println(objects[0].toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		WSDLClient w = WSDLClient.getInstance();
		w.getS();
	}

}
