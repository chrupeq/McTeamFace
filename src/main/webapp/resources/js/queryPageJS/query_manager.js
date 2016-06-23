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
	
	$('#allFailuresForImsi').on('click', function(){
		$('#imsiFailuresModal').modal('show');
	})
});

var loadDateModal = function(){
	$('#modalTitle').html('Dates Query');
	$('#dateQueryModal').modal('show');
}