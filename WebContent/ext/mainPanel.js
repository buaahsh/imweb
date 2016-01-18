function addPanel(mainPanel){
	var p = new Ext.Panel({
        title: "概要",
        height: 200,
        html : "<h1><font color='red'>面板主区域</font></h1>",
        renderTo : Ext.get('doc-body')
	});
	
	var p = new Ext.Panel({
		height: 400,
		id : 'docs-data',
        title: "数据列表",
        html : initwelcome(),
        renderTo : Ext.get('doc-body'),
        autoScroll: true
	});
	
	//Ext.get('welcome-panel').add(p);
}