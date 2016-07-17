$(document).ready(function(){
	$('#clickforchart').on('click', function(){
		showChartDiv();
	})
})

var showChartDiv = function(){
	$('#canvasdiv').removeClass('hiddenbycostanza');
	$('#canvasdiv').removeClass('fadeOutUp');
	$('#canvasdiv').addClass('fadeInDown');
}

var appendRevealButton = function(){
	$('#clickforchart').removeClass('hiddenbycostanza');
	$('#clickforchart').removeClass('fadeOutUp');
	$('#clickforchart').addClass('fadeInDown');
}

var hideRevealButton = function(){
	$('#clickforchart').removeClass('fadeInDown');
	$('#clickforchart').addClass('fadeOutUp');
	$('#clickforchart').addClass('hiddenbysconstanza');
}