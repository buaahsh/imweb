/**
 * hsh
 * 数据包的谱系图
 * @param data
 */

function CreateJsplumb(data) {
	AddHtml(data);
	
	jsPlumb.ready(function() {
						
		// setup some defaults for jsPlumb.	
		var instance = jsPlumb.getInstance({
			Endpoint : ["Dot", {radius:2}],
			
			HoverPaintStyle : {strokeStyle:"#1e8151", lineWidth:2 },
			ConnectionOverlays : [
				[ "Arrow", { 
					location:1,
					id:"arrow",
                    length:14,
                    foldback:0.8
				} ],
                [ "Label", { label:"", id:"label", cssClass:"aLabel" }]
			],
			Container:"statemachine-demo"
		});

		var windows = jsPlumb.getSelector(".statemachine-demo .w");

        // initialise draggable elements.  
		instance.draggable(windows);

		// suspend drawing and initialise.
		instance.doWhileSuspended(function() {
										
			// make each ".ep" div a source and give it some parameters to work with.  here we tell it
			// to use a Continuous anchor and the StateMachine connectors, and also we give it the
			// connector's paint style.  note that in this demo the strokeStyle is dynamically generated,
			// which prevents us from just setting a jsPlumb.Defaults.PaintStyle.  but that is what i
			// would recommend you do. Note also here that we use the 'filter' option to tell jsPlumb
			// which parts of the element should actually respond to a drag start.
			instance.makeSource(windows, {
				filter:".ep",				// only supported by jquery
				//anchor:"Continuous",
				//connector:[ "StateMachine", { curviness:20 } ],
				anchor:["Continuous", {faces:["left", "right"]}],
				connector:[ "Flowchart", { stub:[40, 60], gap:10, cornerRadius:5, alwaysRespectStubs:true } ],
				connectorStyle:{ strokeStyle:"#5c96bc", lineWidth:2, outlineColor:"transparent", outlineWidth:4 },
				maxConnections:10,
				onMaxConnections:function(info, e) {
					alert("Maximum connections (" + info.maxConnections + ") reached");
				}
			});						

			// initialise all '.w' elements as connection targets.
	        instance.makeTarget(windows, {
				dropOptions:{ hoverClass:"dragHover" },
				//anchor:"Continuous"
				anchor:["Continuous", {faces:["left"]}]
			});
			
			// and finally, make a couple of connections
	        
		});
		
		ConncetJs(data, instance);
		
		$.each($("div._jsPlumb_overlay"), function(idx, item){
			$(item).hide();
		});
		
		$.each($("div.ep"), function(idx, item){
			$(item).hide();
		});
	});
}

function AddHtml(data){
	var t1 = "<div class=\"w\" id=\"";
	var t2 = "\">";
	var t3 = "<div class=\"ep\"></div></div>";
	var html = "";
	var id = 1;
	var self = 0;
	$.each(data.upper, function(idx, item){
		html += t1 + "token" + id + t2 + ConvertItem2Html(item, data.self.name, 1) + t3;
		id += 1;
	});
	html += t1 + "token" + id + t2 + ConvertItem2Html(data.self, data.self.name, 0) + t3;
	self = id;
	id += 1;
	$.each(data.down, function(idx, item){
		html += t1 + "token" + id + t2 + ConvertItem2Html(item, data.self.name, 2) + t3;
		id += 1;
	});
	
	$("#statemachine-demo").html(html);
	
	// css
	id = 1;
	var top = 3;
	$.each(data.upper, function(idx, item){
		$("#token" + id).css("left", "4em");
		$("#token" + id).css("top",  top + "em");
		id += 1;
		top += 10;
	});
	
	//画自己
	top = 3;
	$("#token" + id).css("left", "32em");
	$("#token" + id).css("top",  top + "em");
	$("#token" + id).css("background-color",  "#b5d1f3");
	
	id += 1;
	$.each(data.down, function(idx, item){
		$("#token" + id).css("left", "64em");
		$("#token" + id).css("top",  top + "em");
		id += 1;
		top += 10;
	});
	$("#statemachine-demo th").css("font-size", 13);
}

/**
 * 生成每一个item的html
 * @param item
 * @param oldName
 * @returns {String}
 */
function ConvertItem2Html(item, oldName, dataext){
	var cid = getUrlParam('cid');
	cid = decodeURIComponent(cid);
	cid = cid.split("_")[0];
	
	var id = getUrlParam('id');
	var version = getUrlParam('version');
	var user = getUrlParam('user');
	var pwd = getUrlParam('pwd');
	
	var uid = getUrlParam('uid');
	var sid = getUrlParam('sid');
	
	var vid = getUrlParam('vid');
	vid = decodeURIComponent(vid);
	
//	cid = cid.replace("/" + oldName + "/", "/" + item.name + "/");
	tcid = encodeURIComponent(item.path);
	tvid = "";
//	tvid = encodeURIComponent(vid);
	
	var dataext_html = "";
	var span = "<span>"+item.name+"</span>";
	
	var html = "<table class=\"table\"><tr><th colspan=\"2\" style=\"font-weight: bold;\">" + span;
	html += "</th></tr>";
	
	html += "<tr><th colspan=\"2\">完成人: " + item.updateUser + "</th></tr>";
	
	if (item.version == "-1" ){
		html += "<tr><th colspan=\"2\">版本 : 无</th></tr>";
	}
	else{
		html += "<tr><th colspan=\"2\">版本 : ";
		$.each(item.version.split(","), function(idx, ite){
			if (ite != ""){
				var href = "/imweb/ie.html?cid=" + tcid + "&id=" + item.id + "&version=" + ite
				+ "&user=" + user + "&pwd=" + pwd + "&sid=" + sid  + "&uid=" + uid;
				if (idx != 0)
					html += ", <span style='cursor:hand; color:blue;' onclick='clickHistory(\""+href+"\")'>"+ite+"</span>";
				else
					html += "<span style='cursor:hand; color:blue;' onclick='clickHistory(\""+href+"\")'>"+ite+"</span>";
			}
		});
		if (dataext == 1){
			$.each(item.version.split(","), function(idx, ite){
				if (ite != ""){
					var data_e = tcid + "_" + item.id + "_" + ite;
//					+ "&user=" + user + "&pwd=" + pwd + "&sid=" + sid  + "&uid=" + uid;
					dataext_html = "<span style='float: right;cursor:hand; color:blue;' onclick='addDataExtract(\""+data_e+"\")'>浏览数据</span>"; 
				}
			});
		}
		if (dataext == 2){
			$.each(item.version.split(","), function(idx, ite){
				if (ite != ""){
					var data_e = tcid + "_" + item.id + "_" + ite;
//					+ "&user=" + user + "&pwd=" + pwd + "&sid=" + sid  + "&uid=" + uid;
					dataext_html = "<span style='cursor:hand; color:blue;' onclick='addDownDataExtract(\""+data_e+"\")'>浏览数据</span>"; 
				}
			});
		}
		html += "</th></tr>";
	}
	
	html += "<tr><th colspan=\"2\">时间: " + item.updateDate + "</th></tr>";
	
	if (dataext == 1){
		html += "<tr><th colspan=\"2\">" + dataext_html + "</th></tr>";
	}
	if (dataext == 2){
		html += "<tr><th colspan=\"2\">" + dataext_html + "</th></tr>";
	}
	
	html += "</table>";
	
	return html;
}

function ConncetJs(data, instance){
	var id = 1;
	var self = 0;
	$.each(data.upper, function(idx, item){
		id += 1;
	});
	self = id;
	
	// css
	id = 1;
	$.each(data.upper, function(idx, item){
		instance.connect({ source:"token" + id, target:"token" + self , 
			connector : "Straight", anchor:["Continuous", {faces:["left", "right"]}]});
		id += 1;
	});
	id += 1;
	$.each(data.down, function(idx, item){
		instance.connect({ source:"token" + self , target:"token" + id, 
			connector : "Straight", anchor:["Continuous", {faces:["right", "left"]}]});
		id += 1;
	});
}


function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = encodeURI(window.location.search).substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}

/**
 * 针对上游的数据提取模板的浏览
 * @param data_e
 */
function addDataExtract(data_e){
	var tokens = data_e.split("_");
	var cid = tokens[0];
	cid = decodeURIComponent(cid);
//	cid = cid.split("_")[0];
	
	var id = tokens[1];
	var version = tokens[2];
	var user = getUrlParam('user');
	var pwd = getUrlParam('pwd');
	
	var uid = getUrlParam('uid');
	var sid = getUrlParam('sid');
	
	cidsb = stringToBytes(cid);
	
	// raw_cid 是当前浏览的cid
	var raw_cid = getUrlParam('cid');
	raw_cid = decodeURIComponent(raw_cid);
	raw_cid = raw_cid.split("_")[0];
	raw_cid = stringToBytes(raw_cid);
	
	var html = "";
//	//update the views
	$.getJSON("/imweb/DataExtract?arg=view&cid=" + raw_cid + "&sid_702=" + sid, function(data){
		$.each(data, function(idx, item){
			var href = "/imweb/ie.html?cid=" + cid + "&id=" + id + "&version=" + version
			+ "&user=" + user + "&pwd=" + pwd + "&sid=" + sid  + "&uid=" + uid + "&vid=" + item.sid + "&dataext=1";
			html += "<p>" 
					+ "<a target=\"_blank\" href='"+href+"'>" + item.name + "</a>"
					+ "</p>";
		});
		Ext.MessageBox.show({
            title: '数据列表',
            msg: html,
            width: 200 ,
            buttons: Ext.MessageBox.OK
        });                
	});
}

/**
 * 针对下游的数据提取模板的浏览
 * @param data_e
 */
function addDownDataExtract(data_e){
	var tokens = data_e.split("_");
	var cid = tokens[0];
	cid = decodeURIComponent(cid);
//	cid = cid.split("_")[0];
	
//	var id = tokens[1];
//	var version = tokens[2];
	var user = getUrlParam('user');
	var pwd = getUrlParam('pwd');
	
	var uid = getUrlParam('uid');
	var sid = getUrlParam('sid');
	
	cidsb = stringToBytes(cid);
	
	
	var version = getUrlParam('version');
	var id = getUrlParam('id');
	
	// raw_cid 是当前浏览的cid
	var raw_cid = getUrlParam('cid');
	raw_cid = decodeURIComponent(raw_cid);
	raw_cid = raw_cid.split("_")[0];
//	raw_cid = stringToBytes(raw_cid);
	
	var html = "";
//	//update the views
	$.getJSON("/imweb/DataExtract?arg=view&cid=" + cidsb + "&sid_702=" + sid, function(data){
		$.each(data, function(idx, item){
			var href = "/imweb/ie.html?cid=" + raw_cid + "&id=" + id + "&version=" + version
			+ "&user=" + user + "&pwd=" + pwd + "&sid=" + sid  + "&uid=" + uid + "&vid=" + item.sid + "&dataext=1";
			html += "<p>" 
					+ "<a target=\"_blank\" href='"+href+"'>" + item.name + "</a>"
					+ "</p>";
		});
		Ext.MessageBox.show({
            title: '数据列表',
            msg: html,
            width: 200 ,
            buttons: Ext.MessageBox.OK
        });                
	});
}