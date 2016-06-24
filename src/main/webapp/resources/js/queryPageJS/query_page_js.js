$(document).ready(function() {
	$('#imsiWithDates').on("click", function(){
		$('#dateQueryModal').removeClass('animated bounceOut');
		$('#dateQueryModal').addClass('animated bounceIn');
	});
	
	$('#allFailuresForModel').on("click", function(){
		$('#modelFailuresModal').removeClass('animated bounceOut');
		$('#modelFailuresModal').addClass('animated bounceIn');
	});
	$('#modalClose').on("click", function(){
		$('#modelFailuresModal').addClass('animated bounceOut');
	});
	$('#modalClose2').on("click", function(){
		$('#modelFailuresModal').addClass('animated bounceOut');
	});
	
	$('#dateSearch').on('click', function(){
		$('#container').addClass('animated fadeOutUp');
		changeContainerCSS('imsiDates');
	});
	
	$('#modelSearch').on('click', function(){
		$('#container').addClass('animated fadeOutUp');
		changeContainerCSS('modelsQuery');
	});
});

var changeContainerCSS = function(whichTable){
	
	$('#container').remove();
	
	
var newDiv = '<h2 id="queryHeader2">Query Selector</h2>';

$('body').append(newDiv);
$('#queryHeader2').hide();
$('#queryHeader2').addClass('animated fadeInDown');
$('#queryHeader2').show();
if(whichTable == 'imsiDates'){
imsiWithDatesQuery();
}
if(whichTable == 'modelsQuery'){
	modelQuery();
}
	
}