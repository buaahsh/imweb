$(document).ready(function(){
	serSizes();
});


$(window).resize(function() {serSizes();});

function serSizes()
{
	$("#tree").css("height", $(window).height() * 0.95 - 200);
}