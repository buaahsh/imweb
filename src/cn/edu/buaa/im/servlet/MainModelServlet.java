package cn.edu.buaa.im.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.buaa.im.model.DPVersion;
import cn.edu.buaa.im.model.DataPacketAbs;
import cn.edu.buaa.im.service.DataPacketService;
import cn.edu.buaa.im.service.RelationService;
import cn.edu.buaa.im.service.VersionService;

import com.google.gson.Gson;

public class MainModelServlet extends BaseServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws  IOException {
		
		String arg = request.getParameter("arg");
		if (arg.equals("abs")){
			String nodeId = request.getParameter("id");
			String version = request.getParameter("version");
			String user = request.getParameter("user");
			String pwd = request.getParameter("pwd");
			String uid = request.getParameter("uid");
			
			DataPacketService dataPacketService = new DataPacketService(nodeId, version, user, pwd, uid);
			
			List<DataPacketAbs> dataPacket = dataPacketService.getDataPacketAbs();
			
			Gson gson = new Gson();
			responseString(response, gson.toJson(dataPacket));
		}
		else if (arg.equals("version")){
			String nodeId = request.getParameter("id");
			String user = request.getParameter("user");
			String pwd = request.getParameter("pwd");
			VersionService version = new VersionService(user, pwd, nodeId);
			List<DPVersion> versions = version.getVersions();
			
			String uid = request.getParameter("uid");
			String sid = request.getParameter("sid");
			//如果uid不为空，表示为主模型的版本，需要考虑大版本的问题
			if (uid != null && sid != null && sid.equals(nodeId)){
				VersionService.updateBigVersion(versions, user, uid, pwd, nodeId);
			}
			
			Gson gson = new Gson();
			responseString(response, gson.toJson(versions));
		}else if (arg.equals("relation")){
			String nodeId = request.getParameter("id");
			String version = request.getParameter("version");
			String user = request.getParameter("user");
			String pwd = request.getParameter("pwd");
			String uid = request.getParameter("uid");
			String sid = request.getParameter("sid");
			
			if (sid.equals(nodeId))
			{
				RelationService relationService = new RelationService(user, uid, pwd, nodeId, version);
				responseString(response, relationService.jsonString);
			}
			else{
				RelationService relationService = new RelationService(user, uid, pwd, nodeId, version, sid);
				Gson gson = new Gson();
				responseString(response, gson.toJson(relationService.pedigree));
			}
			
		}
		else if (arg.equals("debug")){

		}
	}
}
