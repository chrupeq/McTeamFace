var numOfIMSIsBetweenTwoDates = "http://localhost:8080/GroupProject2016/rest/imsi/imsi_count_between_dates?";
var IMSI = "";
var closeButton = '<button id="backToHomeButton" type="button" class="btn btn-secondary">Back To Home</button>';
$(document).ready(function(){
	$.getJSON("http://localhost:8080/GroupProject2016/rest/imsi/get_unique", function(result) {
	    var options = $('#IMSIs');
	    $.each(result, function(index, item) {
	        options.append('<option value="'+item+'">'+item+'</option>');
	    });
	});
	
	$('#IMSIs').on('change', function(){
		IMSI = this.value;
		})
	
	$('#singleImsiSearch').on('click', function(){
		var fifthDate = $("#datetimepicker5").find("input").val();
		var sixthDate = $("#datetimepicker6").find("input").val();
		$('#searchParams').html('You are searching for a failure count for IMSI ' + IMSI + ' between ' + fifthDate + ' and ' + sixthDate);
		
		loadSingleImsiTable(fifthDate, sixthDate, IMSI);
	});
	
});	

var loadSingleImsiTable = function(date1, date2, imsi){

		$.ajax({
			type:'GET',
			url: numOfIMSIsBetweenTwoDates + 'imsi=' + imsi + '&dateOne=' + date1 + '&dateTwo=' + date2,
			dataType:'json',
			success:loadSingleImsis
		});
		
}

	


	var loadSingleImsis = function(data){
		
		var table = '<table class="display table table-striped table-hover table-condensed animated fadeInDown" id="querysTable">'
		+ '<thead>'
		+ '<tr>'
		+ '<th align="left">IMSI</th>'
		+ '<th align="left" class="col-sm-2">Number of Failures</th>'
		+ '</tr>'
		+ '</thead>'
		+ '</table>';
		
		$('#tableDiv').html(table);
		
	
		 querysTable = $('#querysTable').DataTable( {
			 pagingType: "full_numbers",
			 
		        data: data,
		        
		        columns: [

		            { data: "imsi" },
		            
		            { data: "numberOfFailures" }
		      
		        ],
		        
		        
		    } );
		 showTable();
			
			$('#backToHomeButton').on('click', function(){
				hideTable();
			});
		};
	
	