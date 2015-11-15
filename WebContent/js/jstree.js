$(function () {
	$('#tree')
		.jstree({
			'core' : {
				'themes' : {
					'responsive' : false,
					'variant' : 'small',
					'stripes' : true
				},
				'data' : [
			          { "id" : "ajson1", "parent" : "#", "text" : "储箱容积", a_attr : { 'href' : '#cxrj'} },
			          { "id" : "ajson2", "parent" : "#", "text" : "气体工作量", a_attr : { 'href': '#qtgzl'} },
			          { "id" : "ajson3", "parent" : "ajson2", "text" : "推进剂"  , icon:'file' , a_attr : { 'href': '#qtgzl_tjj'} },
			          { "id" : "ajson4", "parent" : "ajson2", "text" : "起飞前气体" , icon:'file' , a_attr : { 'href': '#qtgzl_qfqqt'}},
			          { "id" : "ajson5", "parent" : "ajson2", "text" : "气瓶充气量" , icon:'file' , a_attr : { 'href': '#qtgzl_qpcql'}},
			          { "id" : "ajson6", "parent" : "ajson2", "text" : "增压气体质量" , icon:'file' , a_attr : { 'href': '#qtgzl_zyqtzl'}},
			          { "id" : "ajson7", "parent" : "ajson2", "text" : "停火点气体质量" , icon:'file' , a_attr : { 'href': '#qtgzl_thdqtzl'}},
			       ]
				
			},
			'types' : {
				'default' : { 'icon' : 'folder' },
				'file' : { 'valid_children' : [], 'icon' : 'file' }
			},
			'plugins' : ['state','dnd','types','contextmenu']
		})
		.bind("select_node.jstree", function (event, data) {
            //alert(data.node.a_attr.href);
            location.href= data.node.a_attr.href
        })
});
