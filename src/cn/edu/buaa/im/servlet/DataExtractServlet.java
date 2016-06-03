package cn.edu.buaa.im.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import cn.edu.buaa.im.service.DataExtractService;

public class DataExtractServlet extends BaseServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws  IOException {
		String arg = request.getParameter("arg");
		if (arg.equals("view")){
			String sid_702 = request.getParameter("sid_702");
			String cid = request.getParameter("cid");
			cid = Util.byte2str(cid);
			
			Gson gson = new Gson();
			responseString(response, gson.toJson(DataExtractService.geDataExtractInfos(cid, sid_702)));
		}
	}
}
