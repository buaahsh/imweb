package cn.edu.buaa.im.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import cn.edu.buaa.im.model.DataPacketAbs;
import cn.edu.buaa.im.service.CaseService;
import cn.edu.buaa.im.service.DataPacketService;
import cn.edu.buaa.im.service.VersionService;
import cn.edu.buaa.im.model.DPVersion;

public class DataPacketServlet extends BaseServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws  IOException {
		String arg = request.getParameter("arg");
		if (arg.equals("abs")){
			String cid = request.getParameter("cid");
			DataPacketService dataPacketService = new DataPacketService(cid);
			
			List<DataPacketAbs> dataPacket = dataPacketService.getDataPacketAbs();
			
			Gson gson = new Gson();
			responseString(response, gson.toJson(dataPacket));
		}
		else if (arg.equals("version")){
			String cid = request.getParameter("cid");
			String sid = request.getParameter("sid");
			VersionService version = new VersionService(sid, cid);
			List<DPVersion> versions = version.getVersions();
			Gson gson = new Gson();
			responseString(response, gson.toJson(versions));
		}
		else if (arg.equals("sid")){
			String cid = request.getParameter("cid");
			CaseService caseService = new CaseService(cid);
			Gson gson = new Gson();
			responseString(response, gson.toJson(caseService));
		}
	}
}
