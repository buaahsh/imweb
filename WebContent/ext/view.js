function changeView(sid){
	var cid = getUrlParam('cid');
	var id = getUrlParam('id');
	var version = getUrlParam('version');
	var user = getUrlParam('user');
	var pwd = getUrlParam('pwd');
	var sid_702 = getUrlParam('sid');
	
	$("#tree").remove();
	$("#dataItems").empty();
	$("#sidebar-wrapper").append("<div id='tree'></div>")
	
	if (sid == -1){
		if (sid_702 == id){
			$.getJSON("/imweb/TreeNode?arg=mm&id=" + id + "&version=" + version
					+ "&user=" + user + "&pwd=" + pwd, function(data){
//				Docs.classData.children = data.TreeNode;
				
//				$.each(data.DataItem, function(idx, item){
//					$("#dataItems").append(DataItemProc(item));
//				});
//				
//				ImageSilde();
//				
//				PlotContainer();
			});
		}
		else{
			$.getJSON("/imweb/TreeNode?arg=all&id=" + id + "&version=" + version
					+ "&user=" + user + "&pwd=" + pwd, function(data){
				Docs.classData.children = data.TreeNode;
				
//				$.each(data.DataItem, function(idx, item){
//					$("#dataItems").append(DataItemProc(item));
//				});
//				
//				ImageSilde();
//				
//				PlotContainer();
			});
		}
		
	}
	else{
		sid = stringToBytes(sid);
		if (sid_702 == id){
			$.getJSON("/imweb/TreeNode?arg=mmview&id=" + id + "&version=" + version
					+ "&user=" + user + "&pwd=" + pwd + "&sid=" + sid  + "&sid_702=" + sid_702, function(data){
				Docs.classData.children = data.TreeNode;
				
//				$.each(data.DataItem, function(idx, item){
//					$("#dataItems").append(DataItemProc(item));
//				});
//				
//				ImageSilde();
//				
//				PlotContainer();
			});
		}
		else{
		$.getJSON("/imweb/TreeNode?arg=view&id=" + id + "&version=" + version
				+ "&user=" + user + "&pwd=" + pwd + "&sid=" + sid  + "&sid_702=" + sid_702, function(data){
			Docs.classData.children = data.TreeNode;
			
//			$.each(data.DataItem, function(idx, item){
//				$("#dataItems").append(DataItemProc(item));
//			});
//			
//			ImageSilde();
//			
//			PlotContainer();
		});
		}
	}
};