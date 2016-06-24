$(document).ready(function() {
	$('#imsiWithDates').on("click", function(){
		 $('#datetimepicker1').datetimepicker({
             viewMode: 'years'
         });
		 $('#datetimepicker2').datetimepicker({
             viewMode: 'years'
         });
		loadDateModal();
	});
	
	$('#allFailuresForModel').on('click', function(){
		$('#modelFailuresModal').modal('show');
		$('#modalTitle2').html('Models Query');
	})
	
});

var loadDateModal = function(){
	$('#modalTitle').html('Dates Query');
	$('#dateQueryModal').modal('show');
}

