function CreateTree(sid) {
	$.getJSON("/imweb/TreeNode?sid=" + sid, function(data){
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
	});
}
