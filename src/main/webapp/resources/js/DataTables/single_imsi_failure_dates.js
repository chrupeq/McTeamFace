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
	$('#queryprogress').css('width', '0%');
 	$('#queryprogress').text('0%');
	$('#queryprogressouter').removeClass('animated fadeOutUp');
	$('#queryprogressouter').addClass('animated fadeInDown');
		$.ajax({
			type:'GET',
			url: numOfIMSIsBetweenTwoDates + 'imsi=' + imsi + '&dateOne=' + date1 + '&dateTwo=' + date2,
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
				loadSingleImsis(data);
				$('#queryprogressouter').mousemove(function(){
					$('#queryprogressouter').removeClass('animated fadeInDown');
					$('#queryprogressouter').addClass('animated fadeOutUp');
				});
			},
			error: $('#tableDiv').html('<h3 id="noDataMessage">no data to display for this query<h3>')
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
	
	