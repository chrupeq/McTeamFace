var showTable = function(){
	$('#querysTable').removeClass('animated fadeOutUp');
	$('#querysTable').addClass('animated fadeInDown');
	$('#buttonDiv').removeClass('animated fadeOutUp');
	$('#buttonDiv').html(closeButton);
	$('#buttonDiv').addClass('animated fadeInDown');
	$('#selectorDiv').removeClass('animated fadeInDown');
	$('#selectorDiv').addClass('animated fadeOutUp');
	$('#tableDiv').removeClass('animated fadeOutUp');
	$('#tableDiv').addClass('animated fadeInDown');
	$('queryHeader2').removeClass('animated fadeOutUp');
	$('queryHeader2').addClass('animated fadeInDown');
	$('#searchParams').removeClass('animated fadeOutUp');
	$('#searchParams').addClass('animated fadeInDown');
	$('#tableDiv').removeClass('hiddenbycostanza');
	$('#searchParams').removeClass('hiddenbycostanza');
	clearDatePickers();
}

var hideTable = function(){
	$('#buttonDiv').removeClass('animated fadeInDown');
	$('#infoDiv').removeClass('animated fadeInDown');
	$('#buttonDiv').addClass('animated fadeOutUp');
	$('#infoDiv').addClass('animated fadeOutUp');
	$('#tableDiv').removeClass('animated fadeInDown');
	$('#tableDiv').addClass('animated fadeOutUp');
	$('queryHeader2').removeClass('animated fadeInDown');
	$('queryHeader2').addClass('animated fadeOutUp');
	$('#homeDiv').removeClass('animated fadeOutUp');
	$('#homeDiv').addClass('animated fadeInDown');
	$('#selectorDiv').removeClass('animated fadeOutUp');
	$('#selectorDiv').addClass('animated fadeInDown');
	$("#queryHeader").css({left: '30%', position:'fixed'});
	$('#queryHeader').removeClass('animated fadeOutUp');
	$('#queryHeader').addClass('animated fadeInDown');
	$('#searchParams').removeClass('animated fadeInDown');
	$('#searchParams').addClass('animated fadeOutUp');
	$('#tableDiv').removeClass('animated fadeInDown');
	$('#tableDiv').addClass('animated fadeOutUp');
	$('#tableDiv').addClass('hiddenbycostanza');
	$('#searchParams').addClass('hiddenbycostanza');
}