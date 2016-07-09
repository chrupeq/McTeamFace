$(document).ready(function(){
	getCustomQueryBar();
	
})

var getCustomQueryBar = function(){
	var customQueryBar = "";

	if(getTheCookieYouNeed('job_title') == ""){
		$('#queryList').html(customQueryBar);
	}
	
	else if(getTheCookieYouNeed('job_title') == 'NME'){
		customQueryBar ='<a href="#" id="imsiWithDates" class="list-group-item">List of '
			+ 'IMSI failures for given dates</a> <a href="#" id="allFailuresForModel"'
			+ 'class="list-group-item">List of failure details for given Model</a>'
			+ '<a href="#" id="imsiStats" class="list-group-item">View IMSI'
		+ ' Statistics</a>'
		+ '<a href="#" id="allImsiFailuresBetweenDates"'
		+ 'class="list-group-item">Failure count for a given IMSI between two dates</a>'
		+ '<a href="#" id="imsiEventSelectIdCauseCode"'
		+ 'class="list-group-item">Unique Event ID and Cause Codes for a given IMSI</a>'
		+ '<a href="#" id="modelsWithDatesLink"'
			+ 'class="list-group-item">Failure count for given equipment model between two dates</a>';
		
	}else if(getTheCookieYouNeed('job_title') == 'CSR'){
		
		customQueryBar = '<a href="#" id="imsiEventSelectIdCauseCode"'
			+ 'class="list-group-item">Unique Event ID and Cause Codes for a given IMSI</a>'
			+ '<a href="#" id="allImsiFailuresBetweenDates"'
			+ 'class="list-group-item">Failure count for a given IMSI between two dates</a>';
			
	}else if(getTheCookieYouNeed('job_title') == 'SE'){
		
		customQueryBar = '<a href="#" id="imsiWithDates" class="list-group-item">List of '
			+ 'IMSI failures for given dates</a>'
			+ '<a href="#" id="imsiEventSelectIdCauseCode"'
			+ 'class="list-group-item">Unique Event ID and Cause Codes for a given IMSI</a>'
			+ '<a href="#" id="allImsiFailuresBetweenDates"'
			+ 'class="list-group-item">Failure count for a given IMSI between two dates</a>'
			+ '<a href="#" id="modelsWithDatesLink"'
			+ 'class="list-group-item">Failure count for given equipment model between two dates</a>';	
	}
	$('#queryTitle').text();
	$('#queryList').html('<div id="queries"><h2 id="queryTitle">' + getTheCookieYouNeed('name') + '\'s Queries</h2>' + customQueryBar + '</div>');
	
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
		$("#imsiEventIdCauseCode").select2();
		$('#singleImsiModal').modal('show');
	})
	
	$('#imsiWithDates').on("click", function(){
		$('#datetimepicker1').find('input').val("");
		$('#datetimepicker2').find('input').val("");
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
	
	$('#imsiEventSelectIdCauseCode').on('click', function(){
		$.getJSON("http://localhost:8080/GroupProject2016/rest/imsi/get_unique", function(result) {
		    $.each(result, function(index, item) {
		    	$('#imsiEventIdCauseCode').append($("<option></option>")
	                    .attr("value",item)
	                    .text(item));
		    });
		})
			
		$('#imsisEventIdAndCauseCode').modal('show');
	
	})
	
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
	
	$('#dateSearch').on('click', function(){
		var firstDate = $("#datetimepicker1").find("input").val();
		var secondDate = $("#datetimepicker2").find("input").val();
		$('#searchParams').html('You are searching for all IMSI failures between ' + firstDate + ' and ' + secondDate);
		$('#queryHeader').addClass('animated fadeOutUp');
		$('#queryHeader2').removeClass('animated fadeOutUp');
		$('#queryHeader2').addClass('animated fadeInDown');
		checkDates(firstDate, secondDate);
		findAllImsisForDates(firstDate, secondDate);
		$('#querysTable').addClass('animated fadeOutUp');
		changeContainerCSS('imsiDates');
	});
	
	$('#modelsWithDatesLink').on('click', function(){
		$('#modelsWithDatesModal').modal('show');
	})
}