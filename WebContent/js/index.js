/*
	填写内容
	@author: Shaohan
*/

$(function(){
	//update the abstraction
	$.getJSON("/imweb/DataPacket?arg=abs", function(data){
		$("#abs_model").text(data.model);
		$("#abs_cat").text(data.cat);
		$("#abs_sub").text(data.sub);
		$("#abs_keyword").text(data.keyword);
		$("#abs_deadline").text(data.deadline);
		$("#abs_abs").text(data.abs);
	});  	
	
	//update the versions
	$.getJSON("/imweb/DataPacket?arg=version", function(data){
		$("#versions").empty();
		$.each(data, function(idx, item){
			var itemStr = "<tr>" 
				+  "<th scope='row'><a href='#'>"+ item.name +"</a></th>"
				+ "<td>" + item.date + "</td>"
				+ "<td>" + item.person + "</td>"
				+ "<td>" + item.abs + "</td></tr>";
			$("#versions").append(itemStr);
		});
	});
	
	// update the data items
	$.getJSON("/imweb/DataItem", function(data){
		$.each(data, function(idx, item){
			$("#dataItems").append(DataItemProc(item));
		});
		
		ImageSilde();
		CreateTree();
	});
 });
    