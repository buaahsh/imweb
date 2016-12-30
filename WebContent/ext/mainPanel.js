/*
	构造右边4个模型，包括：概要、谱系、历史版本和数据内容
	@author: Shaohan
*/

function addPanel(){
	var dataItems = new Ext.Panel({
		id : 'docs-data',
        title: "数据列表",
//        html : initwelcome(),
        html: '',
        autoScroll: true
	});
	
	var dataext = getUrlParam('dataext');
	if (dataext == 1)
		return [dataItems];
	
	//return [addHistory()];
	return [addAbs(), addPuxi(), addHistory(), dataItems];
	
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
	        fields: ['id', 'date', 'person', 'abs','title','desc', 'bigversion', 'baseLineVersion'],
	    });
	});
    //定义列
	
	var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});

	var column = new Ext.grid.ColumnModel({
    	id : "versionCol",
        columns: [
             sm,
             { header: '版本', dataIndex: 'id', sortable: true,
            	 renderer: function (val, meta, record) {
            		 tcid = encodeURIComponent(cid);
            		 tvid = encodeURIComponent(vid);
            		 var href = "/imweb/ie.html?cid=" + tcid + "&id=" + id + "&version=" + val
     				+ "&user=" + user + "&pwd=" + pwd + "&sid=" + sid  + "&uid=" + uid + "&vid=" + tvid;
            		 return "<span style='cursor:hand; color:blue;' onclick='clickHistory(\""+href+"\")'>"+val+"</span>";
                 },
                 width: 80
             },
             { header: '完成日期	', dataIndex: 'date', sortable: true,width: 150  },
             { header: '完成人', dataIndex: 'person', sortable: true, width: 100 },
             { header: '版本说明', dataIndex: 'abs', sortable: true, width: 300 },
             { header: '基线标题', dataIndex: 'title', sortable: true, width: 300 },
             { header: '基线说明', dataIndex: 'desc', sortable: true, width: 300 },
             //{ header: '大版本', hidden: true , dataIndex: 'bigversion'},
             { header: '基线版本', hidden: true , dataIndex: 'baseLineVersion'}
        ],
        autoHeight : true,
        viewConfig: {
        	  forceFit: true
        	}
    });
    //列表
    var grid = new Ext.grid.GridPanel({
    	frame: true,
    	sm:sm,
    	id : "docs-history",
    	style : "margin: 10px",
    	height: 300,
        title: '历史版本 <span style="cursor:pointer;float:right;" title="选择版本分析" onclick="openAnalysisAndMonitoring('+id+','+sid+');">版本分析</span>',
        store: jsonstore,
        autoHeight : true,
        colModel: column
    });
    
    return grid;
}


function loadProperties(){  
    jQuery.i18n.properties({// 加载properties文件  
    name:'url', // properties文件名称  
    path:'/imweb/resources/', // properties文件路径  
    mode:'map', // 用 Map 的方式使用资源文件中的值  
    callback: function() {// 加载成功后设置显示内容  
        $.i18n.prop("analysisAndMonitoring_url");//其中isp_index为properties文件中需要查找到的数据的key值  
    }  
    });  
}  

function openAnalysisAndMonitoring(id,sid){

	loadProperties();
	
	var selectionModel = Ext.getCmp('docs-history').getSelectionModel();
	
	var ids = '';
	
	if (selectionModel.hasSelection()){
		var rows = selectionModel.getSelections(); //获取所有选中行

		var n = rows.length - 1;
		
		for(var i=0;i<rows.length;i++){

			if(i == n){
				ids = ids + rows[i].get('id');
			}else{
				ids = ids + rows[i].get('id') + ',';
			}
		}
	
	//待汪逵那边功能实现
	//url = $.i18n.prop("analysisAndMonitoring_url")+"mainModel&id="+sid+"&ids="+ids;

	if(id == sid){
		url = $.i18n.prop("analysisAndMonitoring_url")+"mainModel&id="+id;
	}else{
		url = $.i18n.prop("analysisAndMonitoring_url")+"package&id="+id;
	}

	window.open (url,'newwindow');
		
	}else{
		alert('请选择要分析的版本！');
	}

}

function clickHistory(obj){
	location.href = obj;
}

function createBaseLine(sid,version,user,pwd){

	var win=new Ext.Window({
	width:600,
	height:300,
	title:"设置基线",
	closable:true,
	layout:'fit',//布局方式
	//maximizable:true,//显示最大化按钮,点击最大化按钮,窗口自动扩展充满整个浏览器,并且窗口右上角的最大化按钮变为回复原状的按钮
	//minimizable:true,//显示最小化按钮,并未对这个按钮做任何处理,可以添加监听事件minimizable或重写minimizable()函数
	closeAction:'close',
	constrainHeader:true,//设置窗口的顶部不会超出浏览器边界
	//constrain:true,//设置整个窗口都不回超出浏览器边界
	defaultButton:0,//默认选中的按钮
	resizable:false,//控制窗口是否可以通过拖拽改变大小
	resizeHandles:'se',//控制拖拽方式,必须是在设置了resizable的情况下,
	modal:true,//弹出窗口后立刻屏蔽掉其他的组件,只有关闭窗口后才能操作其他组件,
	plain:true,//对窗口内部内容惊醒美化,可以看到整齐的边框
	animateTarget:'target',//可以使窗口展示弹并缩回效果的动画
	buttonAlign:'center',
	items:[{
		layout:'form',
		defaultType:'textfield',
		defaults:{width:300},
		style:{
			marginTop:10,
			marginLeft:10
		},
		labelWidth:60,
		labelAlign:'right',
		items:[
			{
				style:{
					marginTop:12,
					marginLeft:12
				},
				id:'title', 
				labelStyle:'margin-Top:10px',
				fieldLabel:'基线标题',
				maxLength:20
			},
			{
				style:{
					marginTop:12,
					marginLeft:12
				},
				id:'desc',
				xtype: "textarea",
				labelStyle:'margin-Top:10px',
				fieldLabel:'基线说明',
				maxLength:100
			}	  
		]
	}],
	buttons:[
		{
			text:'保存',
			style: {
                marginBottom: '11px' //修改自己定义的样式
            },
			handler:function(){
				
				var title = Ext.getCmp('title').getValue();
				var desc = Ext.getCmp('desc').getValue();

				if (title !== null && title !== undefined && title !== ''){
				
					$.post("/imweb/BaseLineServlet?arg=create&sid=" + sid +"&version=" + version + "&user=" + user +"&pwd=" + pwd +"&title=" + title +"&desc=" + desc, function(){
	                	alert("保存成功！");
					});
					
					win.close();
				}else{
					alert("请输入基线标题！");
				}
			}
		},{
			text:'取消',
			style: {
                marginBottom: '11px' //修改自己定义的样式
            },
			handler:function(){
				win.close();	
			}
		}	 
	]
});

    win.show();
}


function editBaseLine(sid,version,user,pwd,title,desc){

	var win=new Ext.Window({
	width:600,
	height:300,
	title:"设置基线",
	closable:true,
	layout:'fit',//布局方式
	//maximizable:true,//显示最大化按钮,点击最大化按钮,窗口自动扩展充满整个浏览器,并且窗口右上角的最大化按钮变为回复原状的按钮
	//minimizable:true,//显示最小化按钮,并未对这个按钮做任何处理,可以添加监听事件minimizable或重写minimizable()函数
	closeAction:'close',
	constrainHeader:true,//设置窗口的顶部不会超出浏览器边界
	//constrain:true,//设置整个窗口都不回超出浏览器边界
	defaultButton:0,//默认选中的按钮
	resizable:false,//控制窗口是否可以通过拖拽改变大小
	resizeHandles:'se',//控制拖拽方式,必须是在设置了resizable的情况下,
	modal:true,//弹出窗口后立刻屏蔽掉其他的组件,只有关闭窗口后才能操作其他组件,
	plain:true,//对窗口内部内容惊醒美化,可以看到整齐的边框
	animateTarget:'target',//可以使窗口展示弹并缩回效果的动画
	buttonAlign:'center',
	items:[{
		layout:'form',
		defaultType:'textfield',
		defaults:{width:300},
		style:{
			marginTop:10,
			marginLeft:10
		},
		labelWidth:60,
		labelAlign:'right',
		items:[
			{
				style:{
					marginTop:12,
					marginLeft:12
				},
				id:'title', 
				labelStyle:'margin-Top:10px',
				fieldLabel:'基线标题',
				maxLength:20,
				value:title
			},
			{
				style:{
					marginTop:12,
					marginLeft:12
				},
				id:'desc',
				xtype: "textarea",
				labelStyle:'margin-Top:10px',
				fieldLabel:'基线说明',
				maxLength:100,
				value:desc
			}	  
		]
	}],
	buttons:[
		{
			text:'修改',
			style: {
                marginBottom: '11px' //修改自己定义的样式
            },
			handler:function(){
				
				var title = Ext.getCmp('title').getValue();
				var desc = Ext.getCmp('desc').getValue();

				if (title !== null && title !== undefined && title !== ''){
				
					$.post("/imweb/BaseLineServlet?arg=edit&sid=" + sid +"&version=" + version + "&user=" + user +"&pwd=" + pwd +"&title=" + title +"&desc=" + desc, function(){
	                	alert("修改成功！");
					});
					
					win.close();
				}else{
					alert("请输入基线标题！");
				}
			}
		},{
			text:'取消',
			style: {
                marginBottom: '11px' //修改自己定义的样式
            },
			handler:function(){
				win.close();	
			}
		}	 
	]
});

    win.show();
}

function delBaseLine(sid,version,user,pwd){
	
	var flag = confirm('确定要删除该基线？');
	if(flag){
		$.post("/imweb/BaseLineServlet?arg=del&sid=" + sid +"&version=" + version + "&user=" + user +"&pwd=" + pwd, function(){
        	alert("删除成功！");
		});
	}
	
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
	
	var arritem = new Array();
	//update the abstraction
	$.getJSON("/imweb/MainModel?arg=abs&id=" + id + "&version=" + version + "&user=" + user + "&pwd=" + pwd + "&uid=" + uid, function(data){
		
		var isbaseLine = true;
		
		$.each(data, function(idx, item){
			var txtusername = new Ext.form.TextField({
				cls : "abs-item", 
		        width: 150,
		        maxLength: 20,
		        name: 'username',
		        fieldLabel: item.name,
		        id: "aba-item"+idx,
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
			
			if(item.name == "基线标题"){
				isbaseLine = false;
			}

			txtusername.setValue(item.value);

			arritem.push(txtusername);
			
			if(item.name == "基线说明"){
				
				var baseLineDesc = new Ext.form.TextArea({
					cls : "abs-item", 
			        width: 150,
			        grow:true,
			        preventScrollbars:true,
			        fieldLabel: item.name,
			        id: "aba-item"+idx,
			        allowBlank:true,
			        readOnly: true
			    });

				baseLineDesc.setValue(item.value);
				
				
				arritem.push(baseLineDesc);
				
			}

			if (item.name=="名称")
				$("#doc-body span.icon-docs").text(item.value);
		});	
		
		var createBaseLine_html = {
				html: '<span style="margin:10px;cursor:pointer;color:blue;" title="点击设置基线" onclick="createBaseLine('+id+','+version+',\''+user+'\',\''+pwd+'\');">	基线设置</span>'
				};
		
		

		if(isbaseLine){
			if(id == sid){
				arritem.push(createBaseLine_html);
			}
		}else{
			
			var title = Ext.getCmp('aba-item2').getValue();

			var desc = Ext.getCmp('aba-item3').getValue();
			
			var editBaseLine_html = {
					html: '<span style="margin:10px;cursor:pointer;color:blue;" title="点击修改基线" onclick="editBaseLine('+id+','+version+',\''+user+'\',\''+pwd+'\',\''+title+'\',\''+desc+'\');">基线修改</span>'+
					'<span style="margin:10px;cursor:hand;color:blue;" title="点击删除基线" onclick="delBaseLine('+id+','+version+',\''+user+'\',\''+pwd+'\');">基线删除</span>'
					};
			
			arritem.push(editBaseLine_html);
		}

	}); 
	
	var form = new Ext.form.FormPanel({
		id : 'docs-abs',
        frame: true,
        title: '概要',
        style: 'margin:10px',
        items: arritem
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
	
	// 采用 _pertem作为标识
	if (id == sid)
		$.getJSON("/imweb/PerTemServlet?arg=list&id=" + id + "&user=" + user, function(data){
			$.each(data, function(idx, item){
				store.push([item + "_pertem", item]);
			});
		});
	
	var combo1 = new Ext.form.ComboBox({
		id: 'view-combo',
	    store: store,
	    mode: 'loacl',
	    valueField: 'value',
	    width: 180,
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
	
	// 对数据提取的形式进行判断
	var dataext = getUrlParam('dataext');
	
	if (dataext == 1){
		var fourTbar = new Ext.Toolbar({
			id:'view',
			renderTo : api.tbar,
			items : [{
	            iconCls: 'icon-expand-all',
				tooltip: 'Expand All',
	            handler: function(){ api.root.expand(true); },
	            scope: this
	        }, '-', {
	            iconCls: 'icon-collapse-all',
	            tooltip: 'Collapse All',
	            handler: function(){ api.root.collapse(true); },
	            scope: this
	        }]
		});
	}
	else if (sid != id){
		var fourTbar = new Ext.Toolbar({
			id:'view',
			renderTo : api.tbar,
			items : [combo1,"", "", {
	            iconCls: 'icon-expand-all',
				tooltip: 'Expand All',
	            handler: function(){ api.root.expand(true); },
	            scope: this
	        }, '-', {
	            iconCls: 'icon-collapse-all',
	            tooltip: 'Collapse All',
	            handler: function(){ api.root.collapse(true); },
	            scope: this
	        } ]
		});
	}
	else{
		var fourTbar = new Ext.Toolbar({
			id:'view',
			renderTo : api.tbar,
			items : [combo1,"", {
	            iconCls: 'icon-expand-members',
				tooltip: '个人数据模板',
	            handler: function(){ ShowPersonTemplate(); },
	            scope: this
	        }, '-', {
	            iconCls: 'icon-expand-all',
				tooltip: 'Expand All',
	            handler: function(){ api.root.expand(true); },
	            scope: this
	        }, '-', {
	            iconCls: 'icon-collapse-all',
	            tooltip: 'Collapse All',
	            handler: function(){ api.root.collapse(true); },
	            scope: this
	        }]
		});
	}
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
		$(obj).text("显示基线版本");
	}
	else{
		$(obj).text("显示所有版本");
		$.each($("#docs-history div.x-grid3-body div.x-grid3-row"), function(idx, item){
			if ($(item).find("td.x-grid3-cell-last").text() == "0")
				$(item).hide();
		});
	}
}