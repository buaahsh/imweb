package cn.edu.buaa.im.wsdl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;

public class HttpClientUtils extends HttpClient {

	/**
	 * <p>
	 * httpClient的get请求方式
	 * </p>
	 * 
	 * @param url
	 *            = "http://www.baidu.com/";
	 * @param charset
	 *            = "utf-8";
	 * @return
	 * @throws Exception
	 */
	public String getDoGetURL(String url, String charset) {

		url = toUtf8String(url);
		GetMethod method = new GetMethod(url);

		method.setRequestHeader("Cookie", Convert2Str(this.getState().getCookies()));
		// 设置请求的编码方式
		try {
			printHeader(method);
			int statusCode = this.executeMethod(method);

			if (statusCode != HttpStatus.SC_OK) { // 打印服务器返回的状态
				// return null;
			}
			// 返回响应消息
			byte[] responseBody = method.getResponseBodyAsString().getBytes(method.getResponseCharSet());
			// 在返回响应消息使用编码(utf-8或gb2312)
			String response = new String(responseBody, "utf-8");
			return response;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			method.releaseConnection();
		}

	}

	/**
	 * <p>
	 * httpClient的get请求方式
	 * </p>
	 * 
	 * @param url
	 *            = "http://www.baidu.com/";
	 * @param charset
	 *            = "utf-8";
	 * @return
	 * @throws Exception
	 */
	public String getDoGetURL(String url, String charset, String format) {

		url = toUtf8String(url);
		GetMethod method = new GetMethod(url);

		method.setRequestHeader("Cookie", Convert2Str(this.getState().getCookies()));
		method.setRequestHeader("Accept", format);
		// 设置请求的编码方式
		try {
			printHeader(method);
			int statusCode = this.executeMethod(method);

			if (statusCode != HttpStatus.SC_OK) { // 打印服务器返回的状态
				// return null;
			}
			// 返回响应消息
			byte[] responseBody = method.getResponseBodyAsString().getBytes(method.getResponseCharSet());
			// 在返回响应消息使用编码(utf-8或gb2312)
			String response = new String(responseBody, "utf-8");
			return response;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			method.releaseConnection();
		}

	}

	public byte[] getByteDoGetURL(String url, String format) {
		url = toUtf8String(url);
		GetMethod method = new GetMethod(url);

		method.setRequestHeader("Cookie", Convert2Str(this.getState().getCookies()));
		method.setRequestHeader("Accept", format);
		try {
			int statusCode = this.executeMethod(method);

			if (statusCode != HttpStatus.SC_OK) { // 打印服务器返回的状态
				return null;
			}
			// 返回响应消息
			byte[] responseBody = method.getResponseBodyAsString().getBytes(method.getResponseCharSet());
			// 在返回响应消息使用编码(utf-8或gb2312)
			return responseBody;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			method.releaseConnection();
		}
	}

	public byte[] getDoGetURL(String url) {
		url = toUtf8String(url);
		GetMethod method = new GetMethod(url);

		method.setRequestHeader("Cookie", Convert2Str(this.getState().getCookies()));

		try {
			int statusCode = this.executeMethod(method);

			if (statusCode != HttpStatus.SC_OK) { // 打印服务器返回的状态
				return null;
			}
			// 返回响应消息
			byte[] responseBody = method.getResponseBody();
			// 在返回响应消息使用编码(utf-8或gb2312)
			return responseBody;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			method.releaseConnection();
		}
	}

	/**
	 * <p>
	 * 执行一个HTTP Put请求，返回请求响应的HTML
	 * </p>
	 * 
	 * @param url
	 *            请求的URL地址
	 * @param params
	 *            请求的查询参数,Json String
	 * @return 返回请求响应的HTML
	 */
	public String getDoPutResponseDataByURL(String url, String params) {
		StringBuffer response = new StringBuffer();
		PutMethod putMethod = new PutMethod(url);

		putMethod.setRequestHeader("Cookie", Convert2Str(this.getState().getCookies()));

		try {
			StringRequestEntity requestEntity = new StringRequestEntity(params,"application/json", "UTF-8");
			putMethod.setRequestEntity(requestEntity);
			this.executeMethod(putMethod);
			// 读取为 InputStream，在网页内容数据量大时候推荐使用

			BufferedReader reader = new BufferedReader(new InputStreamReader(putMethod.getResponseBodyAsStream(), "utf-8"));
			String line;
			while ((line = reader.readLine()) != null) {
				response.append(line);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			putMethod.releaseConnection();
		}
		return response.toString();
	}

	/**
	 * <p>
	 * 执行一个HTTP Delete请求，返回请求响应的HTML
	 * </p>
	 * 
	 * @param url
	 *            请求的URL地址
	 * @param params
	 *            请求的查询参数,Json String
	 * @return 返回请求响应的HTML
	 */
	public String getDoDeleteResponseDataByURL(String url) {
		DeleteMethod method = new DeleteMethod(url);

		method.setRequestHeader("Cookie", Convert2Str(this.getState().getCookies()));
		method.addRequestHeader("Content-Type","application/x-www-form-urlencoded; charset=" + "utf-8");
		try {
			int statusCode = this.executeMethod(method);

			if (statusCode != HttpStatus.SC_OK) { // 打印服务器返回的状态
				return null;
			}
			// 返回响应消息
			byte[] responseBody = method.getResponseBodyAsString().getBytes(method.getResponseCharSet());
			// 在返回响应消息使用编码(utf-8或gb2312)
			String response = new String(responseBody, "utf-8");
			return response;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			method.releaseConnection();
		}
	}

	/**
	 * <p>
	 * 执行一个HTTP POST请求，返回请求响应的HTML
	 * </p>
	 * 
	 * @param url
	 *            请求的URL地址
	 * @param params
	 *            请求的查询参数,json String
	 * @return 返回请求响应的HTML
	 */
	public String getDoPostResponseDataByURL(String url, String params) {
		StringBuffer response = new StringBuffer();
		PostMethod postMethod = new PostMethod(url);

		postMethod.setRequestHeader("Cookie", Convert2Str(this.getState().getCookies()));
		try {
			StringRequestEntity requestEntity = new StringRequestEntity(params,"application/json", "UTF-8");
			postMethod.setRequestEntity(requestEntity);
			this.executeMethod(postMethod);
			if (postMethod.getStatusCode() == HttpStatus.SC_OK) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream(), "utf-8"));
				String line;
				while ((line = reader.readLine()) != null) {
					response.append(line);
				}
				reader.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			postMethod.releaseConnection();
		}
		return response.toString();
	}

	public String postFile(String url, String filePath) {
		try {
			PostMethod postMethod = new PostMethod(url);

			postMethod.setRequestHeader("Cookie", Convert2Str(this.getState().getCookies()));

			File file = new File(filePath);
			FilePart fp = new FilePart("eml1", file);
			Part[] parts = { fp };

			// 对于MIME类型的请求，httpclient建议全用MulitPartRequestEntity进行包装
			MultipartRequestEntity mre = new MultipartRequestEntity(parts,postMethod.getParams());

			postMethod.setRequestEntity(mre);

			this.executeMethod(postMethod);
			
			//int statusCode = postMethod.getStatusCode();

			StringBuffer response = new StringBuffer();
			BufferedReader reader = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream(), "utf-8"));
			String line;
			while ((line = reader.readLine()) != null) {
				response.append(line);
			}
			reader.close();

			System.out.println(response.toString());
			return response.toString();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
			} catch (Exception ignore) {

			}
		}

		return null;
	}

	/**
	 * <p>
	 * 执行一个HTTP POST请求，返回请求响应的HTML
	 * </p>
	 * 
	 * @param url
	 *            请求的URL地址
	 * @param params
	 *            请求的查询参数,可以为null
	 * @param charset
	 *            字符集
	 * @param pretty
	 *            是否美化
	 * @return 返回请求响应的HTML
	 */
	public String getDoPostResponseDataByURL(String url,Map<String, String> params, String charset, boolean pretty) {
		StringBuffer response = new StringBuffer();
		PostMethod method = new PostMethod(url);
		//System.out.println(url);
		if (params != null) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				method.addParameter(entry.getKey(), entry.getValue());
			}
		}
		method.setRequestHeader("Cookie", Convert2Str(this.getState().getCookies()));
		try {
			this.executeMethod(method);
			//System.out.println(method.getStatusCode());
			Header[] headers = method.getResponseHeaders();
			AddCookie(headers);
			// if (method.getStatusCode() == HttpStatus.SC_OK) {
			// 读取为 InputStream，在网页内容数据量大时候推荐使用
			BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), charset));

			String line = "";
			while ((line = reader.readLine()) != null) {
				if (pretty){
					response.append(line).append(System.getProperty("line.separator"));
				}else{
					response.append(line);
				}
			}
			reader.close();
			return response.toString();
			// }
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			method.releaseConnection();
		}
	}

	public String toUtf8String(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 0 && c <= 255) {
				sb.append(c);
			} else {
				byte[] b;
				try {
					b = String.valueOf(c).getBytes("utf-8");
				} catch (Exception ex) {
					System.out.println(ex);
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0)
						k += 256;
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}

	private String Convert2Str(Cookie[] cookies) {
		if (cookies != null) {
			String result = "";
			for (int i = 0; i < cookies.length; i++) {
				result += cookies[i].getName() + "=" + cookies[i].getValue() + ";";
			}
			return result;
		}
		return "";
	}

	private void AddCookie(Header[] headers) {
		if (this.getState().getCookies().length < 1) {
			HttpState state = new HttpState();
			for (int i = 0; i < headers.length; i++) {
				if (headers[i].getName().equals("Set-Cookie")) {
					String name = headers[i].getValue().split(";")[0].split("=")[0];
					String value = headers[i].getValue().split(";")[0].split("=")[1];
					Cookie cookie = new Cookie();
					cookie.setName(name);
					cookie.setValue(value);
					state.addCookie(cookie);
				}
			}
			this.setState(state);
		}
	}

	private void printHeader(GetMethod method) {
		Header[] temp = method.getRequestHeaders();
		for (Header header : temp) {
			System.err.println(header.getName() + "   " + header.getValue());
		}
	}
	
	public static void main(String[] args) {
		String url = "http://202.112.140.210/MainModel/genericFile/uploadFile.mm?nodeId=2382&type=usertem&userId=285";
		String fileName = "C:\\Users\\Shaohan\\Desktop\\user.usertem";
		HttpClientUtils httpClientUtils = new HttpClientUtils();
		httpClientUtils.postFile(url, fileName);
		url = "http://202.112.140.210/MainModel/genericFile/downloadFile.mm?nodeId=2382&type=usertem&userId=285";
		System.out.println(httpClientUtils.getDoGetURL(url, "utf8"));
	}
}
