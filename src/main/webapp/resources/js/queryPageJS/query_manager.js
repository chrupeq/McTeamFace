$(document).ready(function() {
	 $('#datetimepicker3').datetimepicker({
         viewMode: 'years',
		 format: 'DD/MM/YYYY HH:mm'
     });
	 $('#datetimepicker4').datetimepicker({
		 viewMode: 'years',
		 format: 'DD/MM/YYYY HH:mm'
    });
	 $('#datetimepicker5').datetimepicker({
         viewMode: 'years',
		 format: 'DD/MM/YYYY HH:mm'
     });
	 $('#datetimepicker6').datetimepicker({
		 viewMode: 'years',
		 format: 'DD/MM/YYYY HH:mm'
    });
	 $('#datetimepicker7').datetimepicker({
         viewMode: 'years',
		 format: 'DD/MM/YYYY HH:mm'
     });
	 $('#datetimepicker8').datetimepicker({
		 viewMode: 'years',
		 format: 'DD/MM/YYYY HH:mm'
    });
	 $('#datetimepicker11').datetimepicker({
         viewMode: 'years',
		 format: 'DD/MM/YYYY HH:mm'
     });
	 $('#datetimepicker12').datetimepicker({
		 viewMode: 'years',
		 format: 'DD/MM/YYYY HH:mm'
    });
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
	
	 $('#datetimepicker9').datetimepicker({
         viewMode: 'years',
		 format: 'DD/MM/YYYY HH:mm'
     });
	 $('#datetimepicker10').datetimepicker({
		 viewMode: 'years',
		 format: 'DD/MM/YYYY HH:mm'
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

