/*
	填写内容
	针对ext进行初始化
	@author: Shaohan
*/

function initwelcome(){
	$.ajaxSetup({
		async : false
	});
	$("#welcome-panel").empty();
	var cid = getUrlParam('cid');
	cid = decodeURIComponent(cid);
	cid = cid.split("_")[0];
	
	var id = getUrlParam('id');
	var version = getUrlParam('version');
	var user = getUrlParam('user');
	var pwd = getUrlParam('pwd');
	
	var uid = getUrlParam('uid');
	var sid = getUrlParam('sid');
	
	var html = "";
	$.getJSON("/imweb/TreeNode?ext=1&arg=all&id=" + id + "&version=" + version
			+ "&user=" + user + "&pwd=" + pwd, function(data){
		//CreateTree(data.TreeNode);
		
		Docs.classData.children = data.TreeNode;
		
		html = "<div xmlns:ext='http://www.extjs.com' class='body-wrap'>" +
				"<table cellspacing=\"0\" class=\"member-table\"><tbody>";
		$.each(data.DataItem, function(idx, item){
			html += ExtDataItemProc(item);
		});
		html += "</tbody></table></div>";
		
//		$("#welcome-panel").append(html);
		
		
		//PlotContainer();
	});
	
//	//update the versions
//	$.getJSON("/imweb/MainModel?arg=version&id=" + id 
//			+ "&user=" + user + "&pwd=" + pwd , function(data){
//		$("#versions").empty();
//		$.each(data, function(idx, item){
//			var href = "/imweb/?cid=" + cid + "&id=" + id + "&version=" + item.id
//				+ "&user=" + user + "&pwd=" + pwd + "&sid=" + sid  + "&uid=" + uid;
//			var itemStr = "<tr>" 
//				+  "<th scope='row'><a href=\""+ href
//				+"\">"+ item.name +"</a></th>"
//				+ "<td>" + item.date + "</td>"
//				+ "<td>" + item.person + "</td>"
//				+ "<td>" + item.abs + "</td></tr>";
//			$("#versions").append(itemStr);
//		});
//		
//		cid = stringToBytes(cid);
//		//update the views
//		$.getJSON("/imweb/DataPacket?arg=view&cid=" + cid + "&sid_702=" + sid, function(data){
//			CreateView(data);
//		});
//	});


//	//update the abstraction
//	$.getJSON("/imweb/MainModel?arg=abs&id=" + id + "&version=" + version
//			+ "&user=" + user + "&pwd=" + pwd + "&uid=" + uid, function(data){
//		var html = "<tr>";
//		$.each(data, function(idx, item){
//			if (idx > 0 && idx % 2 == 0){
//				html += "</tr><tr>";
//			}
//			html += "<th class=\"col-md-3\">"+item.name+":</th>"
//				+ "<td class=\"col-md-3\">"+item.value+"</td>";	
//			if (item.name=="数据包名称")
//				$("#title_a").text(item.value);
//		});
//		html += "</tr>";
//		
//		$("#abs_table").html(html);
//	}); 
	
	//update the abstraction
//	$.getJSON("/imweb/MainModel?arg=relation&id=" + id + "&version=" + version
//			+ "&user=" + user + "&pwd=" + pwd + "&uid=" + uid, function(data){
//		CreateJsplumb(data);
//	});
	
	return html;
	
 }

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
    var r = encodeURI(window.location.search).substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}

function stringToBytes ( str ) {
	//Google Closure library, convert to/from UTF-8 and byte arrays
	//just copy some codes to covert UTF-8 to byte arrays
	    var out = [], p = 0;
	   for (var i = 0; i < str.length; i++) {
	     var c = str.charCodeAt(i);
	     if (c < 128) {
	       out[p++] = c;
	     } else if (c < 2048) {
	       out[p++] = (c >> 6) | 192;
	       out[p++] = (c & 63) | 128;
	     } else {
	       out[p++] = (c >> 12) | 224;
	       out[p++] = ((c >> 6) & 63) | 128;
	       out[p++] = (c & 63) | 128;
	     }
	   }
	   //return out;

	var hex_str=""
	   for(var i=0;i<out.length;i++){
	     var num_10=out[i]
	     var hex_one=num_10.toString(16);
	     if (hex_one.length==1){
	         hex_one='0'+hex_one;
	     }
	     hex_str+=hex_one
	}
 return hex_str;
}