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
	
//	$('.searchButton').on("click", function()){
//		
//	}
});