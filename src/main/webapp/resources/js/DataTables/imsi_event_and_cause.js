imsiEventUrl = 'http://localhost:8080/GroupProject2016/rest/imsi/imsi_event_id/';

$(document).ready(function(){
	$.getJSON("http://localhost:8080/GroupProject2016/rest/imsi/get_unique", function(result) {
	    var IMSI2 = "";
	    $.each(result, function(index, item) {
	    	$('#imsiEventIdCauseCode').append($("<option></option>")
                    .attr("value",item)
                    .text(item));
	    });
	});
	
	$('#imsiEventIdCauseCode').on('change', function(){
		IMSI2 = this.value;
		loadEventImsiTable(IMSI2);
		})
	
//	$('#imsiEventSearch').on('click', function(){
//		loadEventImsiTable(IMSI2);
//	});
	
	$('#imsiEventSelectIdCauseCode').on('click', function(){
		$('#imsisEventIdAndCauseCode').modal('show');
	})
});	

var loadEventImsiTable = function(IMSI2){
	$.ajax({
		type:'GET',
		url: imsiEventUrl + IMSI2,
		dataType:'json',
		success:loadImsiEventIdCauseCodeTable
	});
}

var loadImsiEventIdCauseCodeTable = function(data){
	
	var table = '<table class="display table table-striped table-hover table-condensed animated fadeInDown" id="querysTable">'
	+ '<thead>'
	+ '<tr>'
	+ '<th align="left">Event ID</th>'
	+ '<th align="left" class="col-sm-2">Cause Code</th>'
	+ '<th align="left" class="col-sm-2">Failure Class</th>'
	+ '<th align="left" class="col-sm-2">IMSI</th>'
	+ '</tr>'
	+ '</thead>'
	+ '</table>';
	
	$('#tableDiv').html(table);
	

	 querysTable = $('#querysTable').DataTable( {
		 pagingType: "full_numbers",
		 
	        data: data,
	        
	        columns: [

	            { data: "event_id" },
	            
	            { data: "cause_code" },
	            
	            { data: "failure_class" },
	            
	            { data: "imsi" },
	      
	        ],
	        
	        
	    } );
	
	 showTable();
	
	$('#backToHomeButton').on('click', function(){
		hideTable();
	});
	};