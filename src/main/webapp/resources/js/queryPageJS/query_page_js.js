$(document).ready(function() {
	$(document).on("click", "a", function(){
		$('#dateQueryModal').removeClass('animated bounceOut');
		$('#dateQueryModal').addClass('animated bounceIn');
	});
	$('#modalClose').on("click", function(){
		$('#dateQueryModal').addClass('animated bounceOut');
	});
});