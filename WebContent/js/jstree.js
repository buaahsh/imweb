function CreateView(data){
	var html = "<option value=-1>无视图</option>";
	$.each(data.viewItems, function(idx, item){
		html += "<option value=" + item.sid + ">" 
		+ item.name + "</option>"
	});
	$("#select_view").html(html);
}

function CreateTree(data) {
	$('#tree')
		.jstree({
			'core' : {
				'themes' : {
					'responsive' : false,
					'variant' : 'small',
					'stripes' : true
				},
				'data' : data,		
			},
			'types' : {
				'default' : { 'icon' : 'folder' },
				'file' : { 'valid_children' : [], 'icon' : 'file' }
			},
			'plugins' : ['state','dnd','types','contextmenu']
		})
		.bind("select_node.jstree", function (event, data) {
            location.href= data.node.a_attr.href
//				$("#page-content-wrapper").scrollTo(document.getElementById(data.node.a_attr.href), 800);
        });
		//window.location.hash = "index_pos";
}
