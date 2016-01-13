/*
	填写内容
	为主模型浏览，构建index
	@author: Shaohan
*/

$(function(){
	
	var sid = getUrlParam('sid');
	var id = getUrlParam('id');
	var version = getUrlParam('version');
	var user = getUrlParam('user');
	var pwd = getUrlParam('pwd');
	var uid = getUrlParam('uid');
	
	var cid = getUrlParam('cid');
	cid = decodeURIComponent(cid);
	cid = cid.split("_")[0];
	
	$.getJSON("/imweb/TreeNode?arg=mm&id=" + id + "&version=" + version
			+ "&user=" + user + "&pwd=" + pwd, function(data){
		CreateTree(data.TreeNode);
		
		$.each(data.DataItem, function(idx, item){
			$("#dataItems").append(DataItemProc(item));
		});
		
		ImageSilde();
		
		PlotContainer();
	});
	
	//update the versions
	$.getJSON("/imweb/MainModel?arg=version&id=" + id 
			+ "&user=" + user + "&pwd=" + pwd , function(data){
		$("#versions").empty();
		$.each(data, function(idx, item){
			var href = "/imweb/?cid=" + cid + "&id=" + id + "&version=" + item.id
				+ "&user=" + user + "&pwd=" + pwd;
			var itemStr = "<tr>" 
				+  "<th scope='row'><a target='_blank' href=\""+ href
				+"\">"+ item.name +"</a></th>"
				+ "<td>" + item.date + "</td>"
				+ "<td>" + item.person + "</td>"
				+ "<td>" + item.abs + "</td></tr>";
			$("#versions").append(itemStr);
		});
		
		cid = stringToBytes(cid);
		//update the views
		$.getJSON("/imweb/DataPacket?arg=view&cid=" + cid + "&sid_702=" + sid, function(data){
			CreateView(data);
		});
	});
	
	//update the abstraction
	$.getJSON("/imweb/MainModel?arg=abs&id=" + id + "&version=" + version
			+ "&user=" + user + "&pwd=" + pwd + "&uid=" + uid, function(data){
		var html = "<tr>";
		$.each(data, function(idx, item){
			if (idx > 0 && idx % 2 == 0){
				html += "</tr><tr>";
			}
			html += "<th class=\"col-md-3\">"+item.name+":</th>"
				+ "<td class=\"col-md-3\">"+item.value+"</td>";	
			if (item.name=="数据包名称")
				$("#title_a").text(item.value);
		});
		html += "</tr>";
		
		$("#abs_table").html(html);
	}); 
	
	//update the abstraction
	$.getJSON("/imweb/MainModel?arg=relation&id=" + id + "&version=" + version
			+ "&user=" + user + "&pwd=" + pwd, function(data){
		CreateJsplumb(data);
	});
	
 });

$(document).ready(function(){
	$("input.table_radio").click(function(){
		
	});
});

function RadioClick(obj){
	var tokens = $(obj)[0].name.split("_")
	var ContainerId = tokens[1] + "_" + tokens[2] + "_plot_container";
	var x = GetXAxis(ContainerId);
	PlotOneContainer(ContainerId, x);
}

function GetXAxis(ContainerId)
{
	var tokens = ContainerId.split("_")
	var tableid = tokens[0] + "_" + tokens[1] + "_table";
	
	var r = 0;
	$.each($("#" + tableid + " thead th input"), function(idx, item){
		 if($(item)[0].checked)
			 r = idx;
	});
	return r;
}

function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}