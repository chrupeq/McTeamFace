var getCustomQueryBar = function(){
	var customQueryBar = "";

	if(getTheCookieYouNeed('job_title') == 'NME'){
		
		customQueryBar = '<a href="#" id="imsiWithDates" class="list-group-item">List of'
		+ 'IMSI\'s for given dates</a> <a href="#" id="allFailuresForModel"'
		+ 'class="list-group-item">List of failure details for given Model</a>'
	+ '<a href="#" id="imsiStats" class="list-group-item">View IMSI'
		+ 'Statistics</a>'
		+ '<a href="#" id="allImsiFailuresBetweenDates"'
		+ 'class="list-group-item">List of all IMSI failures between given dates</a>'
		+ '<a href="#" id="imsiEventSelectIdCauseCode"'
		+ 'class="list-group-item">Event ID & Cause Code for any given IMSI</a>';
		
	}else if(getTheCookieYouNeed('job_title') == 'CSR'){
		
		customQueryBar = '<a href="#" id="imsiEventSelectIdCauseCode"'
			+ 'class="list-group-item">Event ID & Cause Code for any given IMSI</a>';
			
	}else if(getTheCookieYouNeed('job_title') == 'SE'){
		
		customQueryBar = '<a href="#" id="imsiWithDates" class="list-group-item">List of'
			+ 'IMSI\'s for given dates</a> <a href="#" id="allFailuresForModel"'
			+ 'class="list-group-item">List of failure details for given Model</a>';
		
	}
	
	$('#queryList').html(customQueryBar);
	
	$("#selectByModel").select2({dropdownCssClass : 'bigdrop'}); 
	$('#imsiWithDates').on("click", function(){
		$('#dateQueryModal').removeClass('animated bounceOut');
		$('#dateQueryModal').addClass('animated bounceIn');
	});
	
	$('#imsiStats').on("click", function(){
		$('#imsiStatsModal').removeClass('animated bounceOut');
		$('#imsiStatsModal').addClass('animated bounceIn');
		$('#imsiStatsModal').modal('show');
	});
	
	$('#allImsiFailuresBetweenDates').on('click', function(){
		$('#simgleImsiModal').modal('show');
	})
	
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
		$('#modalTitle2').html('Models Query');
		$('#selectByModel').on('change', function() {
			$('#queryHeader').addClass('animated fadeOutUp');
			$('#queryHeader2').addClass('animated fadeInDown');
			  var value = $(this).val();
			  $('#modelFailuresModal').modal('hide');
			});
	})
	
		$('#imsiEventSelectIdCauseCode').on('click', function(){
		$('#imsisEventIdAndCauseCode').modal('show');
	})
}