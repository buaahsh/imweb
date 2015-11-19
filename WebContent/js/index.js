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
	
	CreateTree();
	
	// update the data items
	$.getJSON("/imweb/DataItem", function(data){
		$.each(data, function(idx, item){
			$("#dataItems").append(DataItemProc(item));
		});
		
		ImageSilde();
		
		PlotContainer();
	});
 });

$(document).ready(function(){
	$("input.table_radio").click(function(){
		
	});
});

function RadioClick(obj){
	var tokens = $(obj)[0].name.split("_")
	var ContainerId = tokens[1] + "_" + tokens[2] + "_plot_container";
	var x = GetXAxis(ContainerId);
	PlotOneContainer(ContainerId, x);
}

function GetXAxis(ContainerId)
{
	var tokens = ContainerId.split("_")
	var tableid = tokens[0] + "_" + tokens[1] + "_table";
	
	var r = 0;
	$.each($("#" + tableid + " thead th input"), function(idx, item){
		 if($(item)[0].checked)
			 r = idx;
	});
	return r;
}
    