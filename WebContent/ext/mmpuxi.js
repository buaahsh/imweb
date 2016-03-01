/**
 * 主模型的谱系图
 * @param data
 */

function CreateMMJsplumb(data) {
	AddMMHtml(data);
	
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
				anchor:["Continuous", {faces:["top", "bottom"]}]
			});
			
			// and finally, make a couple of connections
	        
		});
		
		ConncetMMJs(data, instance);
		
		$.each($("div._jsPlumb_overlay"), function(idx, item){
			$(item).hide();
		});
		$.each($("div.ep"), function(idx, item){
			$(item).hide();
		});
	});
}

function AddMMHtml(data){
	var dps = data.dataPacks;
	dps.sortObjectWith("id", "", "fix");
	var t1 = "<div class=\"w\" id=\"";
	var t2 = "\">";
	var t3 = "<div class=\"ep\"></div></div>";
	var html = "";

	$.each(dps, function(idx, item){
		html += t1 + "token" + item.id 
		+ t2 
		+ ConvertItem2MMHtml(item, item.name) 
		+ t3;
	});
	
	$("#statemachine-demo").html(html);
	
	// css
	var top = 3;
	var left = 3;
	$.each(dps, function(idx, item){
		$("#token" + item.id).css("left", left + "em");
		$("#token" + item.id).css("top",  top + "em");
		top += 10;
		left += 12;
	});
	$("#statemachine-demo").css("height", 110 * dps.length);
	$("#statemachine-demo").parent().css("height", 110 * dps.length + 35);
	$("#statemachine-demo th").css("font-size", 13)
}

function ConvertItem2MMHtml(item, oldName){
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
	
	cid = cid.replace("/" + oldName + "/", "/" + item.name + "/");
	tcid = encodeURIComponent(cid);
	 tvid = encodeURIComponent(vid);
	 
	 var href = "/imweb/ie.html?cid=" + tcid + "&id=" + item.id + "&version=" + item.version
	+ "&user=" + user + "&pwd=" + pwd + "&sid=" + sid  + "&uid=" + uid + "&vid=" + tvid;
	 
	
	var span = "<span style='cursor:hand; color:blue;' onclick='clickHistory(\""+href+"\")'>"+item.name+"</span>";
	var html = "<table class=\"table\"><tr><th colspan=\"2\">" + span;
	html += "</th></tr>";
	html += "<tr><th colspan=\"2\">状态 : " + item.techStatus + "</th></tr>";
	html += "<tr><th colspan=\"2\">版本 : " + item.version + "</th></tr>";
	html += "</table>";
	return html;
}

function ConncetMMJs(data, instance){
	$.each(data.relations, function(idx, item){
		if (item.srcId < item.destId){
			instance.connect({ source:"token" + item.srcId, target:"token" + item.destId , 
				connector : "Straight", anchor:["Continuous", {faces:["right", "top"]}]});
		}
		else{
			instance.connect({ source:"token" + item.srcId, target:"token" + item.destId , 
				connector : "Straight", anchor:["Continuous", {faces:["left", "bottom"]}]});
		
		}
	});
	
}

function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = encodeURI(window.location.search).substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}

Array.prototype.sortObjectWith = function ( key, t, fix){
    if( !this.length ){ return this;}          // 空数组
    t = t ==='desc'? 'desc': 'asc';    // ascending or descending sorting, 默认 升序
    fix = Object.prototype.toString.apply( fix )==='[object Function]'? fix: function(key){ return key; };
    switch( Object.prototype.toString.apply( fix.call({},this[0][key]) ) ){
        case '[object Number]':
            return this.sort( function(a, b){ return t==='asc'?( fix.call({},a[key]) - fix.call({},b[key]) ) :( fix.call({},b[key]) - fix.call({},a[key])); } );
        case '[object String]':
            return this.sort( function(a, b){ return t==='asc'? fix.call({},a[key]).localeCompare( fix.call({},b[key])) : fix.call({},b[key]).localeCompare( fix.call({},a[key])); } );
        default: return this;  // 关键字不是数字也不是字符串, 无法排序
    }
}