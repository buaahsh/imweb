package cn.edu.buaa.im.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import cn.edu.buaa.im.model.DataPacketAbs;
import cn.edu.buaa.im.service.CaseService;
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
			DataPacketAbs dataPacket = new DataPacketAbs();
			dataPacket.setAbs("结合最新版本的三维原理，图V3.0版本重新进行了集中式气动特性的计算，其中XX数值变化较大");
			dataPacket.setCat("方案设计阶段");
			dataPacket.setDeadline("根据任务状态自动判断");
			dataPacket.setKeyword("方案阶段、总体");
			dataPacket.setModel("第二轮");
			dataPacket.setSub("动力学");
			Gson gson = new Gson();
			responseString(response, gson.toJson(dataPacket));
		}
		else if (arg.equals("version")){
			List<DPVersion> versions = new ArrayList<DPVersion>();		
			for (int i = 1; i < 6; i ++)
			{
				DPVersion version = new DPVersion();
				version.setAbs("对气动特征进行了重新计算");
				version.setDate("2014-11-20");
				version.setPerson("王恩泽");
				version.setName("集中式气动特征 v" + String.valueOf(i));
				
				versions.add(version);
			}
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
