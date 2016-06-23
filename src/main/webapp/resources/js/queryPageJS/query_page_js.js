$(document).ready(function() {
	$('#imsiWithDates').on("click", function(){
		$('#dateQueryModal').removeClass('animated bounceOut');
		$('#dateQueryModal').addClass('animated bounceIn');
	});
	
	$('#allFailuresForImsi').on("click", function(){
		$('#imsiFailuresModal').removeClass('animated bounceOut');
		$('#imsiFailuresModal').addClass('animated bounceIn');
	});
	$('#modalClose').on("click", function(){
		$('#imsiFailuresModal').addClass('animated bounceOut');
	});
	$('#modalClose2').on("click", function(){
		$('#imsiFailuresModal').addClass('animated bounceOut');
	});
	
	$('.searchButton').on('click', function(){
		$('#container').addClass('animated fadeOutUp');
		changeContainerCSS();
	});
});

var changeContainerCSS = function(){
	
	$('#container').remove();
	
	
var newDiv = '<h2 id="queryHeader2">Query Selector</h2>';

$('body').append(newDiv);
$('#queryHeader2').hide();
$('#queryHeader2').addClass('animated fadeInDown');
$('#queryHeader2').show();
tableFunction();
	
}