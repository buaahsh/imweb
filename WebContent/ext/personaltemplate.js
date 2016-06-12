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
//	//update the views
	$.getJSON("/imweb/PerTemServlet?arg=list", function(data){
		$.each(data, function(idx, item){
			store.push([item, item]);
		});
	});
	
	var combo1 = new Ext.form.ComboBox({
		id: 'combo-tree',
	    store: store,
	    mode: 'loacl',
	    valueField: 'value',
	    width: 100,
	    typeAhead : true,  
        triggerAction : 'all',  
        lazyRender : true, 
        editable : false,  
	    displayField: 'text',
//	    renderTo : api.tbar,
	    listeners: {
	        select: function(combo, record, index){
	        	var data;
                $.getJSON("/imweb/PerTemServlet?arg=get" + "&name="
                		+ stringToBytes(combo.getValue()), function(data1){
            		data = data1;
            	});
                DocsTem.classData.children = data;
                tree.setTitle(combo.getValue());
                tree.getRootNode().reload();
	        }
	    }
	});
	return combo1;
}

function SaveTem(){
	
}

Ext.onReady(function(){
	tree = new Ext.tree.TreePanel({
        id:'template-tree',
        title: '个人数据模板',
        height: 360,
        width: 300,
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
        },
        buttons: [
            NewList(),
	        {
	            text: '新建',
	            width: 50,
	            handler: function(){
	            	var data;
	                
	                $.getJSON("/imweb/PerTemServlet?arg=new", function(data1){
	            		data = data1;
	            	});
	                Ext.MessageBox.prompt('模板名字', '请输入模板名字:', function (btn, txt) {
	                    if (btn == "ok")
	                    	tree.setTitle(txt);
	                });
	                
	                DocsTem.classData.children = data;
	                tree.getRootNode().reload();
	                //TODO: 输入模板名字
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
	                $.post("/imweb/PerTemServlet?name=" + name, msg, function(){
	                	alert("保存成功");
	                	var store = [];
//	                	//update the views
	                	$.getJSON("/imweb/PerTemServlet?arg=list", function(data){
	                		$.each(data, function(idx, item){
	                			store.push([item, item]);
	                		});
	                		var yourCombo = Ext.getCmp('combo-tree');
	                		yourCombo.bindStore(store);
	                	});
	                	
	                });
	            }
	        },
	        {
	            text: '删除',
	            width: 50,
	            handler: function(){
	            	var name = stringToBytes(tree.title);
	            	$.get("/imweb/PerTemServlet?arg=del" + "&name=" + name, function(){
	            		alert("删除成功");
	            		var store = [];
//	                	//update the views
	                	$.getJSON("/imweb/PerTemServlet?arg=list", function(data){
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

//    tree.getRootNode().expand(true);
    
    var win = new Ext.Window({
        title: '个人数据模板管理',
        closable:true,
        width:320,
        height:400,
        //border:false,
        plain:true,
//        layout: 'border',

        items: [tree]
    });

    win.show(this);
    
    var data;
    
    $.getJSON("/imweb/PerTemServlet?arg=new", function(data1){
		data = data1;
	});
	
    DocsTem.classData.children = data;
});