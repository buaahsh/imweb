function addPanel(){
	var p2 = new Ext.Panel({
//		height: 400,
		id : 'docs-data',
        title: "数据列表",
        html : initwelcome(),
//        renderTo : Ext.get('doc-body'),
        autoScroll: true
	});
	
	return [addAbs(), addHistory(), p2]
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
	
	
	 var jsonstore;
	 
	$.getJSON("/imweb/MainModel?arg=version&id=" + id 
			+ "&user=" + user + "&pwd=" + pwd , function(data){
		jsonstore = new Ext.data.JsonStore({
	        data: data,
	        fields: ['id', 'date', 'person', 'abs']
	    });
	});
    //定义列
    var column = new Ext.grid.ColumnModel({
        columns: [
             { header: '数据包版本', dataIndex: 'id', sortable: true },
             { header: '完成日期	', dataIndex: 'date', sortable: true  },
             { header: '完成人', dataIndex: 'person', sortable: true },
             { header: '版本说明', dataIndex: 'abs', sortable: true }
        ]
    });
    //列表
    var grid = new Ext.grid.GridPanel({
    	frame: true,
    	id : "docs-history",
    	style : "margin: 10px",
    	height: 100,
        title: '历史版本',
        store: jsonstore,
        colModel: column
//        viewConfig:{
//        	autoFill: true, // 注意不要用autoFill:true,那样设置的话当GridPanel的大小变化（比如你resize了它）时不会自动调整column的宽度
//        	 scrollOffset: 0 //不加这个的话，会在grid的最右边有个空白，留作滚动条的位置
//        	  }
    });
    
    return grid;
}
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
		        width: 140,
		        maxLength: 20,
		        name: 'username',
		        fieldLabel: item.name
		    });
			txtusername.setValue(item.value);
			items.push(txtusername);
//			if (item.name=="数据包名称")
//				$("#title_a").text(item.value);
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
	    typeAhead : true,  
        triggerAction : 'all',  
        lazyRender : true, 
        editable : false,  
	    displayField: 'text',
	    renderTo : api.tbar,
	    listeners: {
	        select: function(combo, record, index){
	        	location.href = window.location.href.split('&vid=')[0] + "&vid=" + combo.getValue();
	        }
	    }
	});
	var vid = getUrlParam('vid');
	if (vid == null)
		combo1.setValue('-1');
	else
		combo1.setValue(decodeURIComponent(vid));
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