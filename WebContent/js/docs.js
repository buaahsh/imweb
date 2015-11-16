$(function () {
    $('#container').highcharts({
        title: {
            text: 'Monthly Average Temperature',
            x: -20 //center
        },
        subtitle: {
            text: 'Source: WorldClimate.com',
            x: -20
        },
        xAxis: {
            categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun','Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
        },
        yAxis: {
            title: {
                text: 'Temperature (°C)'
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        tooltip: {
            valueSuffix: '°C'
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0
        },
        series: [{
            name: 'Tokyo',
            data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]
        }, {
            name: 'New York',
            data: [-0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5]
        }, {
            name: 'Berlin',
            data: [-0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3, 9.0, 3.9, 1.0]
        }, {
            name: 'London',
            data: [3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8]
        }]
    });
});
				

$(function () {
    $('#container1').highcharts({
        chart: {
            type: 'column'
        },
        title: {
            text: 'Monthly Average Rainfall'
        },
        subtitle: {
            text: 'Source: WorldClimate.com'
        },
        xAxis: {
            categories: [
                'Jan',
                'Feb',
                'Mar',
                'Apr',
                'May',
                'Jun',
                'Jul',
                'Aug',
                'Sep',
                'Oct',
                'Nov',
                'Dec'
            ]
        },
        yAxis: {
            min: 0,
            title: {
                text: 'Rainfall (mm)'
            }
        },
        tooltip: {
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                '<td style="padding:0"><b>{point.y:.1f} mm</b></td></tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
        },
        plotOptions: {
            column: {
                pointPadding: 0.2,
                borderWidth: 0
            }
        },
        series: [{
            name: 'Tokyo',
            data: [49.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4]

        }, {
            name: 'New York',
            data: [83.6, 78.8, 98.5, 93.4, 106.0, 84.5, 105.0, 104.3, 91.2, 83.5, 106.6, 92.3]

        }, {
            name: 'London',
            data: [48.9, 38.8, 39.3, 41.4, 47.0, 48.3, 59.0, 59.6, 52.4, 65.2, 59.3, 51.2]

        }, {
            name: 'Berlin',
            data: [42.4, 33.2, 34.5, 39.7, 52.6, 75.5, 57.4, 60.4, 47.6, 39.1, 46.8, 51.1]

        }]
    });
});				

$(function(){
    $("#slides").slidesjs({
      width: 940,
      height: 528,
      navigation: false
    });
  });


function DataItemProc(dataItem)
{
	var html = getLittletitle(dataItem.id, dataItem.title);
	html += "<div class=\"highlight\">";
	switch(dataItem.type){ 
		case "TitleDataItem":    
			return getTitle(dataItem.id, dataItem.title)
    	break; 
	    case "SubtitleDataItem":    
	    	return getSubtitle(dataItem.id, dataItem.title)
	    	break; 
	    case "ImageDataItem":
	    	html += ImageDataItemProc(dataItem.id, dataItem.data);
	    	break;
	    case "FileDataItem":
	    	html += FileDataItemProc(dataItem.id, dataItem.data);
	    	break;
	    case "TextDataItem":
	    	html += TextDataItemProc(dataItem.id, dataItem.data);
	    	break;
	    case "FloatDataItem":
	    	html += FloatDataItemProc(dataItem.id, dataItem.data);
	    	break;
	    case "RadioDataItem":
	    	html += RadioDataItemProc(dataItem.id, dataItem.data);
	    	break;
	    case "UrlDataItem":
	    	html += UrlDataItemProc(dataItem.id, dataItem.data);
	    	break;
	    case "CurveDataItem":
	    	html += CurveDataItemProc(dataItem.id, dataItem.data);
	    	break;
	    default:
	    	break;
	}
	html += "</div>";
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
		      height: 528,
		      navigation: false
	    });
	});
}

function CurveDataItemProc(id, data)
{
	var tableid = id + "_table";
	var plotid = id + "_plot";
	var thead = "<tr>";
	$.each(data.table[0], function(idx, item){
		thead += "<th>"+item+"</th>";
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
		+"        <div id=\""+plotid+"_container\" class='plot_container' style=\"min-width:700px;height:400px\"></div>   "
		+"   </div>"
		+"</div>";
	return html;
}

function ImageDataItemProc(id, data)
{
	var html = "<div class='imgslides'>";
	$.each(data.urls, function(idx, item){
		html += "<img src=\""+item+"\">";
	});
    html += "<a href=\"#\" class=\"slidesjs-previous slidesjs-navigation\">"
    	+"<i class=\"icon-chevron-left icon-large\"></i></a>"
    	+"<a href=\"#\" class=\"slidesjs-next slidesjs-navigation\">"
    	+"<i class=\"icon-chevron-right icon-large\"></i></a></div>";
    return html;
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