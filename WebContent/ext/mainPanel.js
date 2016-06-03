/*
	构造右边4个模型，包括：概要、谱系、历史版本和数据内容
	@author: Shaohan
*/

function addPanel(){
	var p2 = new Ext.Panel({
		id : 'docs-data',
        title: "数据列表",
        html : initwelcome(),
        autoScroll: true
	});
	
	return [addAbs(), addPuxi(), addHistory(), p2]
//	return [addPuxi()]
	//Ext.get('welcome-panel').add(p);
}

function addHistory()
{
	$.ajaxSetup({
		async : false
	});
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
	
	var jsonstore;
	 
	$.getJSON("/imweb/MainModel?arg=version&id=" + id 
			+ "&user=" + user + "&pwd=" + pwd + "&uid=" + uid + "&sid=" + sid , function(data){
		jsonstore = new Ext.data.JsonStore({
	        data: data,
	        fields: ['id', 'date', 'person', 'abs', 'bigversion'],
	    });
	});
    //定义列
    var column = new Ext.grid.ColumnModel({
    	id : "versionCol",
        columns: [
             { header: '版本', dataIndex: 'id', sortable: true,
            	 renderer: function (val, meta, record) {
            		 tcid = encodeURIComponent(cid);
            		 tvid = encodeURIComponent(vid);
            		 var href = "/imweb/ie.html?cid=" + tcid + "&id=" + id + "&version=" + val
     				+ "&user=" + user + "&pwd=" + pwd + "&sid=" + sid  + "&uid=" + uid + "&vid=" + tvid;
            		 return "<span style='cursor:hand; color:blue;' onclick='clickHistory(\""+href+"\")'>"+val+"</span>";
                 },
                 width: 120
             },
             { header: '完成日期	', dataIndex: 'date', sortable: true,width: 150  },
             { header: '完成人', dataIndex: 'person', sortable: true, width: 150 },
             { header: '版本说明', dataIndex: 'abs', sortable: true, width: 300 },
             { header: '大版本', hidden: true , dataIndex: 'bigversion'}
        ],
        autoHeight : true,
        viewConfig: {
        	  forceFit: true
        	}
    });
    //列表
    var grid = new Ext.grid.GridPanel({
    	frame: true,
    	id : "docs-history",
    	style : "margin: 10px",
    	height: 300,
        title: '历史版本',
        store: jsonstore,
        autoHeight : true,
        colModel: column
    });
    
    return grid;
}

function clickHistory(obj){
	location.href = obj;
}

//返回概要panel
function addAbs(){
	$.ajaxSetup({
		async : false
	});
	var cid = getUrlParam('cid');
	cid = decodeURIComponent(cid);
	cid = cid.split("_")[0];
	
	var id = getUrlParam('id');
	var version = getUrlParam('version');
	var user = getUrlParam('user');
	var pwd = getUrlParam('pwd');
	
	var uid = getUrlParam('uid');
	var sid = getUrlParam('sid');
	
	var items = new Array();
	//update the abstraction
	$.getJSON("/imweb/MainModel?arg=abs&id=" + id + "&version=" + version
			+ "&user=" + user + "&pwd=" + pwd + "&uid=" + uid, function(data){
		$.each(data, function(idx, item){
			var txtusername = new Ext.form.TextField({
				cls : "abs-item", 
		        width: 150,
		        maxLength: 20,
		        name: 'username',
		        fieldLabel: item.name,
		        readOnly: true
		    });
			if (item.name == "技术状态"){
				if (item.value == "3")
					item.value = "已完成";
				if (item.value == "2")
					item.value = "待完成";
				if (item.value == "1")
					item.value = "等待中";
			}
				
			txtusername.setValue(item.value);
			items.push(txtusername);
			
			if (item.name=="名称")
				$("#doc-body span.icon-docs").text(item.value);
		});		
	}); 

	var form = new Ext.form.FormPanel({
		id : 'docs-abs',
        frame: true,
        title: '概要',
        style: 'margin:10px',
        items: items
    });
	return form;
}

function addView(api){
	$.ajaxSetup({
		async : false
	});
	var cid = getUrlParam('cid');
	cid = decodeURIComponent(cid);
	cid = cid.split("_")[0];
	
	var id = getUrlParam('id');
	var version = getUrlParam('version');
	var user = getUrlParam('user');
	var pwd = getUrlParam('pwd');
	
	var uid = getUrlParam('uid');
	var sid = getUrlParam('sid');
	
	cid = stringToBytes(cid);
	
	var store = [["-1","无视图"]]
//	//update the views
	$.getJSON("/imweb/DataPacket?arg=view&cid=" + cid + "&sid_702=" + sid, function(data){
		$.each(data.viewItems, function(idx, item){
			store.push([item.sid, item.name]);
		});
	});
	
	var combo1 = new Ext.form.ComboBox({
	    store: store,
	    mode: 'loacl',
	    valueField: 'value',
	    width: 210,
	    typeAhead : true,  
        triggerAction : 'all',  
        lazyRender : true, 
        editable : false,  
	    displayField: 'text',
//	    renderTo : api.tbar,
	    listeners: {
	        select: function(combo, record, index){
	        	
	        	location.href = window.location.href.split('&vid=')[0] + "&vid=" + encodeURIComponent(combo.getValue());
	        }
	    }
	});
	var vid = getUrlParam('vid');
	if (vid == null || vid == 'null')
		combo1.setValue('-1');
	else
		combo1.setValue(decodeURIComponent(vid));
	var fourTbar = new Ext.Toolbar({
		id:'view',
		renderTo : api.tbar,
		items : [combo1,"", "",
		         {
            iconCls: 'icon-expand-all',
			tooltip: 'Expand All',
            handler: function(){ api.root.expand(true); },
            scope: this
        }, '-', {
            iconCls: 'icon-collapse-all',
            tooltip: 'Collapse All',
            handler: function(){ api.root.collapse(true); },
            scope: this
        }
		         ]
	});
}

function addPuxi(){
	var p2 = new Ext.Panel({
		height: 400,
		frame: true,
//		class: 'demo statemachine-demo',
		id : 'docs-puxi',
		style : "margin: 10px",
        title: "数据谱系",
        html : "<div id='statemachine-demo'></div>",
//        renderTo : Ext.get('doc-body'),
        autoScroll: true
	});
	return p2;
}

function addPuxiData(){
	$.ajaxSetup({
		async : false
	});
	var cid = getUrlParam('cid');
	cid = decodeURIComponent(cid);
	cid = cid.split("_")[0];
	
	var id = getUrlParam('id');
	var version = getUrlParam('version');
	var user = getUrlParam('user');
	var pwd = getUrlParam('pwd');
	
	var uid = getUrlParam('uid');
	var sid = getUrlParam('sid');
	
	
	
	$("#statemachine-demo").attr("class", "demo statemachine-demo");
	
	if (id == sid){
		$.getJSON("/imweb/MainModel?arg=relation&id=" + id + "&version=" + version
				+ "&user=" + user + "&pwd=" + pwd + "&uid=" + uid + "&sid=" + sid, function(data){
			CreateMMJsplumb(data);
		});
	}
	else{
		//update the puxi
		$.getJSON("/imweb/MainModel?arg=relation&id=" + id + "&version=" + version
				+ "&user=" + user + "&pwd=" + pwd + "&uid=" + uid + "&sid=" + sid, function(data){
			CreateJsplumb(data);
		});
	}
}

function getTitle(){
	$.ajaxSetup({
		async : false
	});
	var cid = getUrlParam('cid');
	cid = decodeURIComponent(cid);
	cid = cid.split("_")[0];
	
	var id = getUrlParam('id');
	var version = getUrlParam('version');
	var user = getUrlParam('user');
	var pwd = getUrlParam('pwd');
	
	var uid = getUrlParam('uid');
	var sid = getUrlParam('sid');
	
	var items = new Array();
	//update the abstraction
	var value = "数据展示";
		
	$.getJSON("/imweb/MainModel?arg=abs&id=" + id + "&version=" + version
			+ "&user=" + user + "&pwd=" + pwd + "&uid=" + uid, function(data){
		$.each(data, function(idx, item){
			if (item.name=="名称")
				value = item.value;
		});		
	});
	return value;
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

function addBigVersion()
{
	var id = getUrlParam('id');
	var sid = getUrlParam('sid');
	
	if (sid != id)
		return;
	
	$("#docs-history").append("<span style=\"color: blue; cursor:pointer;\" id='moreversion' onclick='showMoreVersion()'>显示所有版本</span>");
	$.each($("#docs-history div.x-grid3-body div.x-grid3-row"), function(idx, item){
		if ($(item).find("td.x-grid3-cell-last").text() == "0")
			$(item).hide();
	});
}

function showMoreVersion()
{
	obj = "#moreversion";
	if ($(obj).text() == "显示所有版本"){
		$.each($("#docs-history div.x-grid3-body div.x-grid3-row"), function(idx, item){
			$(item).show();
		});
		$(obj).text("显示大版本");
	}
	else{
		$(obj).text("显示所有版本");
		$.each($("#docs-history div.x-grid3-body div.x-grid3-row"), function(idx, item){
			if ($(item).find("td.x-grid3-cell-last").text() == "0")
				$(item).hide();
		});
	}
}