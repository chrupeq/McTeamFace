$(document).ready(function() {
	$('#imsiWithDates').on("click", function(){
		 $('#datetimepicker1').datetimepicker({
             viewMode: 'years',
			 format: 'DD/MM/YYYY HH:mm'
         });
		 $('#datetimepicker2').datetimepicker({
			 viewMode: 'years',
			 format: 'DD/MM/YYYY HH:mm'
        });
		loadDateModal();
	});
	
	$('#allFailuresForModel').on('click', function(){
		$('#modelFailuresModal').modal('show');
		$('#modalTitle2').html('Count failures for a given model with unique event ID and cause code');
		$('#selectByModel').on('change', function() {
			$('#queryHeader').addClass('animated fadeOutUp');
			$('#queryHeader2').addClass('animated fadeInDown');
			  var value = $(this).val();
			  $('#modelFailuresModal').modal('hide');
			});
	})
	
});

var loadDateModal = function(){
	$('#modalTitle').html('All IMSI failures between dates');
	$('#dateQueryModal').modal('show');
}

