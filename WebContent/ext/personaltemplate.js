/*!
个人模板js
1、对树的处理
2、新建模板
3、保存模板
4、切换模板
5、删除模板
 */
var tree;

function ChangeNode(node, flag){
	if(node.hasChildNodes()){
		$.each(node.childNodes, function(idx, item){
			item.attributes.checked=flag;
			item.ui.checkbox.checked=flag; 
			ChangeNode(item, flag);
		});
	}
}

function ChangePNode(node, flag){
	if (flag){
		var pNode=node.parentNode;  
	    pNode.ui.checkbox.checked=flag;  
	    pNode.attributes.checked=flag;
	    ChangePNode(pNode, flag);
	}
}

function NewList(){
	var store = [];
	var id = getUrlParam('id');
	var version = getUrlParam('version');
	var user = getUrlParam('user');
	
//	//update the views
	$.getJSON("/imweb/PerTemServlet?arg=list" + "&id=" + id 
			+ "&version=" + version + "&user=" + user, function(data){
		$.each(data, function(idx, item){
			store.push([item, item]);
		});
	});
	
	var combo1 = new Ext.form.ComboBox({
		id: 'combo-tree',
	    store: store,
	    mode: 'loacl',
	    valueField: 'value',
	    width: 150,
	    typeAhead : true,  
        triggerAction : 'all',  
        lazyRender : true, 
        editable : false,  
	    displayField: 'text',
//	    renderTo : api.tbar,
	    listeners: {
	        select: function(combo, record, index){
	        	var data;
	        	var id = getUrlParam('id');
            	var version = getUrlParam('version');
            	var user = getUrlParam('user');
                $.getJSON("/imweb/PerTemServlet?arg=get" + "&name="
                		+ stringToBytes(combo.getValue())
                		+ "&id=" + id + "&version=" + version + "&user=" + user, function(data1){
            		data = data1;
            		DocsTem.classData.children = data;
                    tree.setTitle(combo.getValue());
                    tree.getRootNode().reload();
                    tree.getRootNode().expand(true);
            	});
                
	        }
	    }
	});
	return combo1;
}

function SaveTem(){
	
}

function ShowPersonTemplate(){
	tree = new Ext.tree.TreePanel({
        id:'template-tree',
        title: '个人数据模板',
//        height: 360,
//        width: 300,
//        height:400,
        useArrows:true,
        autoScroll:true,
        animate:true,
        enableDD:true,
        containerScroll: true,
        rootVisible: false,
        frame: true,
        loader: new Ext.tree.TreeLoader({
			preloadChildren: true,
			clearOnLoad: false
		}),
//		dataUrl: 'check-nodes.json',
		
        root: new Ext.tree.AsyncTreeNode({
            text:'Ext JS',
            id:'root1',
            expanded:true,
            children:[DocsTem.classData]
         }),
         
        listeners: {
        	'checkchange': function(node, flag){  
        		ChangeNode(node, flag);
        		ChangePNode(node, flag);
        	}
        }
    });

//    tree.getRootNode().expand(true);
    
    var win = new Ext.Window({
        title: '个人数据模板管理',
        closable:true,
        width:350,
        height:430,
        //border:false,
        modal: true,
        resizable:true,
        constrain:true,
        layout: 'fit',
//        plain:true,
//        layout: 'border',

        items: [tree]
    	,
    	buttons: [
        NewList(),
        {
            text: '新建',
            width: 50,
            handler: function(){
            	var data;
            	var id = getUrlParam('id');
            	var version = getUrlParam('version');
            	var user = getUrlParam('user');
            	var pwd = getUrlParam('pwd');
                           
                Ext.MessageBox.prompt('模板名字', '请输入模板名字:', function (btn, txt) {
                    if (btn == "ok"){
                    	tree.setTitle(txt);
                    	$.getJSON("/imweb/PerTemServlet?arg=new" + "&id=" + id
    	                		+ "&version=" + version + "&user=" + user
    	                		+ "&pwd=" + pwd, function(data1){
    	            		data = data1;
    	            		DocsTem.classData.children = data;
    		                tree.getRootNode().reload();
    		                tree.getRootNode().expand(true);
    	            	});
                    }
                
                });      
            }
        },
        {
            text: '保存',
            width: 50,
            handler: function(){
                var msg = '', selNodes = tree.getChecked();
                Ext.each(selNodes, function(node){
                    if(msg.length > 0){
                        msg += ',';
                    }
                    msg += node.id;
                });
                var name = stringToBytes(tree.title);
                var id = getUrlParam('id');
            	var version = getUrlParam('version');
            	var user = getUrlParam('user');
            	var pwd = getUrlParam('pwd');
            	
                $.post("/imweb/PerTemServlet?name=" + name + "&id=" + id
                		+ "&version=" + version + "&user=" + user
                		+ "&pwd=" + pwd, msg, function(){
                	alert("保存成功");
                	var store = [];
//                	//update the views
                	$.getJSON("/imweb/PerTemServlet?arg=list" + "&id=" + id
	                		+ "&version=" + version + "&user=" + user, function(data){
                		$.each(data, function(idx, item){
                			store.push([item, item]);
                		});
                		var yourCombo = Ext.getCmp('combo-tree');
                		yourCombo.bindStore(store);
                	});
                	
                	var store = [["-1","无视图"]];
                	var cid = getUrlParam('cid');
                	cid = decodeURIComponent(cid);
                	cid = cid.split("_")[0];
                	var sid = getUrlParam('sid');
                	
                	cid = stringToBytes(cid);
//                	//update the views
                	$.getJSON("/imweb/DataPacket?arg=view&cid=" + cid + "&sid_702=" + sid, function(data){
                		$.each(data.viewItems, function(idx, item){
                			store.push([item.sid, item.name]);
                		});
                		// 采用 _pertem作为标识
	                	if (id == sid){
	                		$.getJSON("/imweb/PerTemServlet?arg=list&id=" + id + "&user=" + user, function(data){
	                			$.each(data, function(idx, item){
	                				store.push([item + "_pertem", item]);
	                			});
	                			var yourCombo = Ext.getCmp('view-combo');
		                		yourCombo.bindStore(store);
	                		});
	                	}
                	});
                });
            }
        },
        {
            text: '删除',
            width: 50,
            handler: function(){
            	var name = stringToBytes(tree.title);
            	var id = getUrlParam('id');
            	var version = getUrlParam('version');
            	var user = getUrlParam('user');
            	var pwd = getUrlParam('pwd');
            	
            	$.get("/imweb/PerTemServlet?arg=del" + "&name=" + name + "&id=" + id
                		+ "&version=" + version + "&user=" + user, function(){
            		alert("删除成功");
            		var store = [];
//                	//update the views
                	$.getJSON("/imweb/PerTemServlet?arg=list"+ "&id=" + id
	                		+ "&version=" + version + "&user=" + user, function(data){
                		$.each(data, function(idx, item){
                			store.push([item, item]);
                		});
                		var yourCombo = Ext.getCmp('combo-tree');
                		yourCombo.bindStore(store);
                	});
            	});
            }
        }]
    });

    win.show(this);
    
//    var data;
//	var id = getUrlParam('id');
//	var version = getUrlParam('version');
//	var user = getUrlParam('user');
//	var pwd = getUrlParam('pwd');
//	
//    $.getJSON("/imweb/PerTemServlet?arg=new" + "&id=" + id
//    		+ "&version=" + version + "&user=" + user
//    		+ "&pwd=" + pwd, function(data1){
//		data = data1;
//	});
//	
//    DocsTem.classData.children = data;
//    tree.getRootNode().expand(true);
};