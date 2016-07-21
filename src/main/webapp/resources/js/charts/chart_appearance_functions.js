$(document).ready(function() {
	$('#clickforchart').on('click', function() {
		$('chartdiv').hide();
		showChartDiv();
		$('html, body').animate({
			scrollTop : $("#scrolltome").offset().top
		}, 2000);

	})
	
	$('#clickforchart2').on('click', function() {
		showChartDiv2();
		$('html, body').animate({
			scrollTop : $("#scrolltome").offset().top
		}, 2000);

	})
})

var showChartDiv = function() {
	$('#canvasdiv').removeClass('hiddenbycostanza');
	$('#canvasdiv').removeClass('fadeOutUp');
	$('#canvasdiv').addClass('fadeInDown');
}

var appendRevealButton = function() {
	$('#clickforchart').removeClass('hiddenbycostanza');
	$('#clickforchart').removeClass('fadeOutUp');
	$('#clickforchart').addClass('fadeInDown');
}

var appendRevealButton2 = function() {
	$('#clickforchart2').removeClass('hiddenbycostanza');
	$('#clickforchart2').removeClass('fadeOutUp');
	$('#clickforchart2').addClass('fadeInDown');
}

var hideRevealButton = function() {
	$('#clickforchart').removeClass('fadeInDown');
	$('#clickforchart').addClass('fadeOutUp');
	$('#clickforchart').addClass('hiddenbysconstanza');
}

var showChartDiv2 = function() {
	$('chartdiv').show();
	$('#canvasdiv').removeClass('hiddenbycostanza');
	$('#canvasdiv').removeClass('fadeOutUp');
	$('#canvasdiv').addClass('fadeInDown');
	$('#canvasDivOuterPart').hide();
	
}