function ExtDataItemProc(dataItem)
{
	//var html = getLittletitle(dataItem.id, dataItem.title);
	var html = "<tr class=\"config-row \" id= 'docs-" + dataItem.id + 
		"'>" +
		"<td class=\"micon\"><a href=\"#expand\" class=\"exi\">&nbsp;</a></td><td class=\"sig\"><a id=\"Ext.grid.GridPanel-columnLines\"></a><b>"
		+ dataItem.title
		+ "</b> :";
	switch(dataItem.type){ 
		case "TitleDataItem":    
			//return getTitle(dataItem.id, dataItem.title)
    	break; 
	    case "SubtitleDataItem":    
	    	//return getSubtitle(dataItem.id, dataItem.title)
	    	break; 
	    case "ImageDataItem":
	    	//html += ImageDataItemProc(dataItem.id, dataItem.data);
	    	break;
	    case "FileDataItem":
	    	//html += FileDataItemProc(dataItem.id, dataItem.data);
	    	break;
	    case "TextDataItem":
	    	html += "文本<div class=\"mdesc\">";
	    	html += TextDataItemProc(dataItem.id, dataItem.data);
	    	break;
	    case "FloatDataItem":
	    	html += "浮点数<div class=\"mdesc\">";
	    	html += FloatDataItemProc(dataItem.id, dataItem.data);
	    	break;
	    case "RadioDataItem":
	    	//html += RadioDataItemProc(dataItem.id, dataItem.data);
	    	break;
	    case "UrlDataItem":
	    	//html += UrlDataItemProc(dataItem.id, dataItem.data);
	    	break;
	    case "CurveDataItem":
	    	//html += CurveDataItemProc(dataItem.id, dataItem.data);
	    	break;
	    case "D3DataItem":
	    	//html += D3DataItemProc(dataItem.id, dataItem.data);
	    	break;
	    case "TableDataItem":
	    	html += "数据表<div class=\"mdesc\">";
	    	//html += TableDataItemProc(dataItem.id, dataItem.data);
	    	break;	
	    default:
	    	break;
	}
	html += "</div></td></tr>";
	return html;
}

function getTitle(id, title){
	return "<h1 id=\""+id+"\"><span>"+title+"</span></h1>";
}

function getSubtitle(id, title){
	return "<h2 id=\""+id+"\">"+title+"</h2>";
}

function getLittletitle(id, title){
	return "<h3 id=\""+id+"\">"+title+"</h3>";
}

function ImageSilde(){
	$.each($(".imgslides"), function(idx, item){
		$(item).slidesjs({
		      width: 940,
		      height: 528
		      //navigation: false
	    });
	});
}

function CurveDataItemProc(id, data)
{
	var tableid = id + "_table";
	var plotid = id + "_plot";
	var thead = "<tr>";
	$.each(data.table[0], function(idx, item){
		thead += "<th><span>"+item
		+"</span><label><input onclick='RadioClick(this)' type=\"radio\" class='table_radio' name=\"radio_"+tableid+"\"> X轴</label>"
		+"</th>";
	});
	thead += "</tr>";
	
	var tbody = "";
	$.each(data.table, function(idx, item){
		if (idx != 0){
			tbody += "<tr>";
			$.each(item, function(i, tem){
				tbody += "<td>"+tem+"</td>";
			});
			tbody += "</tr>";
		}
	});
	
	var html = 
		"<ul class=\"nav nav-tabs\">"
		+"   <li class=\"active\">"
		+"      <a href=\"#"+tableid+"\" data-toggle=\"tab\">"
		+"         数据"
		+"      </a>"
		+"   </li>"
		+"   <li><a href=\"#"+plotid+"\" data-toggle=\"tab\">曲线</a></li>"
		+"</ul>"
		+"<div  class=\"tab-content\">"
		+"   <div class=\"tab-pane fade in active\" id=\""+tableid+"\">"
		+"      <table class=\"table table-bordered\">"
		+"          <thead>"
		+ thead
		+"          </thead>"
		+"          <tbody>"
		+ tbody
		+"          </tbody>"
		+"        </table>"
		+"   </div>"
		+"   <div class=\"tab-pane fade\" id=\""+plotid+"\">"
		+"        <div id=\""+plotid+"_container\" class='plot_container' style=\"min-width:1000px; height:400px\"></div>   "
		+"   </div>"
		+"</div>";
	//min-width:700px;
	return html;
}

function ImageDataItemProc(id, data)
{
	if (data.flag == 1){
		var html = "<div class='imgslides'>";
		$.each(data.urls, function(idx, item){
			html += "<img src=\"\DataItem?arg=file&file="+item+"\">";
		});
	    return html;
	}
	else{
		var html = "<div class='imgslides'>";
		$.each(data.urls, function(idx, item){
			html += "<img src=\""+item+"\">";
		});
	    return html;
	}
}

function FileDataItemProc(id, data){
	var html = "";
	$.each(data.filePaths, function(idx, item){
		html += "<p><span class=\"glyphicon glyphicon-file\" aria-hidden=\"true\"></span><a>"
			+item+"</a></p>";
	});
  	return html;
}

function TextDataItemProc(id, data){
	var html = "";
	$.each(data.text, function(idx, item){
		html += "<p>"+item+"</p>";
	});
  	return html;
}

function FloatDataItemProc(id, data){
	var html = "";
	html = "<p class=\"float\"><span>"+data.value+"</span>"+data.unit+"</p>";
  	return html;
}

function RadioDataItemProc(id, data){
	var html = "";
	$.each(data.filePaths, function(idx, item){
		html += "<p><span class=\"glyphicon glyphicon-music\" aria-hidden=\"true\"></span><a>"
			+item+"</a></p>";
	});
  	return html;
}

function UrlDataItemProc(id, data){
	var html = "";
	$.each(data.links, function(idx, item){
		html += "<p><a>"+item+"</a></p>";
	});
  	return html;
}

function D3DataItemProc(id, data){
	var html = "<div style='text-align: center;'>"
		+ "<embed src=\"\DataItem?arg=file&file="+data.link+"\" width=\"80%\" height=\"400\" "
		+" type=\"application/x-cortona\"   pluginspage=\"http://www.cortona3d.com/cortona\"   vrml_splashscreen=\"false\" "
		+" vrml_dashboard=\"false\"   vrml_background_color=\"#f7f7f9\"   contextmenu=\"false\" ></div>"
  	return html;
}

function TableDataItemProc(id, data){
	data = $.parseJSON(data.value)
	var tableid = id + "_table";
	var plotid = id + "_plot";
	var thead = "<tr>";
	$.each(data.header, function(idx, item){
		thead += "<th><span>"+item
		+"</span><label><input onclick='RadioClick(this)' type=\"radio\" class='table_radio' name=\"radio_"+tableid+"\"> X轴</label>"
		+"</th>";
	});
	thead += "</tr>";
	
	var tbody = "";
	$.each(data.body, function(idx, item){
		tbody += "<tr>";
		$.each(item, function(i, tem){
			tbody += "<td>"+tem+"</td>";
		});
		tbody += "</tr>";
	});
	
	var html = 
		"<ul class=\"nav nav-tabs\">"
		+"   <li class=\"active\">"
		+"      <a href=\"#"+tableid+"\" data-toggle=\"tab\">"
		+"         数据"
		+"      </a>"
		+"   </li>"
		+"   <li><a href=\"#"+plotid+"\" data-toggle=\"tab\">曲线</a></li>"
		+"</ul>"
		+"<div  class=\"tab-content\">"
		+"   <div class=\"tab-pane fade in active\" id=\""+tableid+"\">"
		+"      <table class=\"table table-bordered\"> " 
		+"          <thead>"
		+ thead
		+"          </thead>"
		+"          <tbody>"
		+ tbody
		+"          </tbody>"
		+"        </table>"
		+"   </div>"
		+"   <div class=\"tab-pane fade\" id=\""+plotid+"\">"
		+"        <div id=\""+plotid+"_container\" class='plot_container' style=\"min-width:1000px; height:400px\"></div>   "
		+"   </div>"
		+"</div>";
	//min-width:700px;
	return html;
}
