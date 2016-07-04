var numOfIMSIsBetweenTwoDates = "http://localhost:8080/GroupProject2016/rest/imsi/imsi_count_between_dates?";
var IMSI = "";
var closeButton = '<button id="backToHomeButton" type="button" class="btn btn-secondary">Back To Home</button>';
$(document).ready(function(){
	$.getJSON("http://localhost:8080/GroupProject2016/rest/imsi/get_unique", function(result) {
	    var options = $('select');
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
		
		loadSingleImsiTable(fifthDate, sixthDate, IMSI);
	});
	
});	

var loadSingleImsiTable = function(date1, date2){

		$.ajax({
			type:'GET',
			url: numOfIMSIsBetweenTwoDates + 'imsi=' + IMSI + '&dateOne=' + date1 + '&dateTwo=' + date2,
			dataType:'json',
			success:loadSingleImsis
		});
		console.log(numOfIMSIsBetweenTwoDates + 'imsi=' + IMSI + '&dateOne=' + date1 + '&dateTwo=' + date2);
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
		
		$('#querysTable').removeClass('animated fadeOutUp');
		$('#querysTable').addClass('animated fadeInDown');
		$('#buttonDiv').removeClass('animated fadeOutUp');
		$('#buttonDiv').html(closeButton);
		$('#buttonDiv').addClass('animated fadeInDown');
		$('#selectorDiv').removeClass('animated fadeInDown');
		$('#selectorDiv').addClass('animated fadeOutUp');
		$('#tableDiv').removeClass('animated fadeOutUp');
		$('#tableDiv').addClass('animated fadeInDown');
		
		$('#backToHomeButton').on('click', function(){
			$('#queryHeader').removeClass('animated fadeOutUp');
			$('#queryHeader').addClass('animated fadeInDown');
			$('#buttonDiv').removeClass('animated fadeInDown');
			$('#infoDiv').removeClass('animated fadeInDown');
			$('#buttonDiv').addClass('animated fadeOutUp');
			$('#infoDiv').addClass('animated fadeOutUp');
			$('#tableDiv').removeClass('animated fadeInDown');
			$('#tableDiv').addClass('animated fadeOutUp');
			
		});
		};
	
	