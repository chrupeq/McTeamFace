imsiEventUrl = 'http://localhost:8080/GroupProject2016/rest/imsi/imsi_event_id/';

$(document).ready(function(){
	
	$('#imsiEventSelectIdCauseCode').on('click', function(){
		$('#imsisEventIdAndCauseCode').modal('show');
	})
	
	$('#imsiEventIdCauseCode').on('change', function(){
		hideTable();
		IMSI2 = this.value;
		$('#imsisEventIdAndCauseCode').removeClass('animated bounceIn');
		$('#imsisEventIdAndCauseCode').addClass('animated bounceOut');
		
		$('#imsisEventIdAndCauseCode').removeClass('animated bounceOut');
		$('#imsisEventIdAndCauseCode').modal('hide');
		$('#searchParams').html('You are searching for unique event ID and Cause Code combinations for ' + IMSI2);
		loadEventImsiTable(IMSI2);
		})
	
//	$('#imsiEventSearch').on('click', function(){
//		loadEventImsiTable(IMSI2);
//	});
	
	
});	

var getUniqueImsis = function(){
	$.getJSON("http://localhost:8080/GroupProject2016/rest/imsi/get_unique", function(result) {
	    var IMSI2 = "";
	    $.each(result, function(index, item) {
	    	$('#imsiEventIdCauseCode').append($("<option></option>")
                    .attr("value",item)
                    .text(item));
	    });
	});
}

var loadImsisForSelect = function(){
	$('#imsiEventIdCauseCode').empty();
	$.getJSON("http://localhost:8080/GroupProject2016/rest/imsi/get_unique", function(result) {
	    var IMSI2 = "";
	    $.each(result, function(index, item) {
	    	$('#imsiEventIdCauseCode').append($("<option></option>")
                    .attr("value",item)
                    .text(item));
	    });
	});
}
var loadEventImsiTable = function(IMSI2){
	$('#queryprogress').css('width', '0%');
 	$('#queryprogress').text('0%');
	$('#queryprogressouter').removeClass('animated fadeOutUp');
	$('#queryprogressouter').addClass('animated fadeInDown');
	$.ajax({
		type:'GET',
		url: imsiEventUrl + IMSI2,
		dataType:'json',
		progress: function(e) {
	        if(e.lengthComputable) {
	            var pct = (e.loaded / e.total) * 100;
	            $('#queryprogress').css('width', pct.toPrecision(3) + '%');
	        	$('#queryprogress').text(pct.toPrecision(3) + '%');
	        }
	        else {
	            console.warn('Content Length not reported!');
	        }
	    },
		success: function(data){
			loadImsiEventIdCauseCodeTable(data);
			$('#queryprogressouter').mousemove(function(){
				$('#queryprogressouter').removeClass('animated fadeInDown');
				$('#queryprogressouter').addClass('animated fadeOutUp');
			});
		},
		error: $('#tableDiv').html('<h3 id="noDataMessage">no data to display for this query<h3>')
	});
}

var loadImsiEventIdCauseCodeTable = function(data){
	var table = '<table class="display table table-striped table-hover table-condensed animated fadeInDown" id="querysTable">'
	+ '<thead>'
	+ '<tr>'
	+ '<th align="left">Event ID</th>'
	+ '<th align="left" class="col-sm-2">Event Cause Description</th>'
	+ '<th align="left" class="col-sm-2">Cause Code</th>'
	+ '<th align="left" class="col-sm-2">Failure Class</th>'
	+ '<th align="left" class="col-sm-2">Failure Description</th>'
	+ '</tr>'
	+ '</thead>'
	+ '</table>';
	
	$('#tableDiv').html(table);

	 querysTable = $('#querysTable').DataTable( {
		 pagingType: "full_numbers",
		 
	        data: data,
	        
	        columns: [

	            { data: "event_id" },
	            
	            { data: "eventIdCauseCodeDescription" },
	      
	            { data: "cause_code" },
	            
	            { data: "failure_class" },
	            
	            { data: "failureDescription" }
	            
	        ],
	        
	        
	    } );
	
	 showTable();
	
	$('#backToHomeButton').on('click', function(){
		hideTable();
	});
	};