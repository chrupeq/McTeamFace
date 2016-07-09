var imsiStatsURL = "http://localhost:8080/GroupProject2016/rest/imsi/get_stats";
$(document).ready(function() {
	var accessLevel = getTheCookieYouNeed('job_title');
	if(accessLevel == "SE"){
		$('#imsiStats').hide();
	}
	if(accessLevel == "NME"){
		$('#allFailuresForModel').hide();
		$('#imsiWithDates').hide();
	}
	$('#selectByModel').on('change', function() {
		  var value = $(this).val();
		  $('#modelFailuresModal').modal('hide');
		});
});

var table = "";
var imsiWithDatesQuery = function(){
	
	table = '<table class="table table-striped table-hover table-condensed animated fadeInDown"'
		+ 'id="querysTable">'
		+ '<thead>'
						+'<tr>'
						+	'<th align="left">Date/Time</th>'
						+	'<th align="left" class="col-sm-2">IMSI</th>'
					+	'</tr>'
				+	'</thead>'
				+'</table>';
	$('#tableDiv').html(table);
	showTable();
	$('#backToHomeButton').on('click', function(){
		hideTable();
	})
}

var modelQuery = function(){
	table = '<table class="table table-striped table-hover table-condensed animated fadeInDown"'
		+ 'id="querysTable">'
		+ '<thead>'
						+'<tr>'
						+	'<th align="left">Number of Failures</th>'
						+	'<th align="left" class="col-sm-2">Manufacturer</th>'
						+	'<th align="left" class="col-sm-2">Marketing Name</th>'
						+	'<th align="left" class="col-sm-2">Event ID</th>'
						+	'<th align="left" class="col-sm-2">Cause Code</th>'
					+	'</tr>'
				+	'</thead>'
				+'</table>';
	$('#tableDiv').html(table);
	showTable();
	$('#backToHomeButton').on('click', function(){
		hideTable();
	})

}
	
		var buildIMSIStatsTable = function(data) {
			imsiTable = '<table class="table table-striped table-hover table-condensed animated fadeInDown"'
				+ 'id="querysTable">'
				+ '<thead>'
								+'<tr>'
								+	'<th align="left">IMSI</th>'
								+	'<th align="left">Number of Failures</th>'
								+	'<th align="left" class="col-sm-2">Total duration of failure (in msc)</th>'
							+	'</tr>'
						+	'</thead>'
						+'</table>';
			$('#tableDiv').html(imsiTable);
			showTable();
			$('#backToHomeButton').on('click', function(){
				hideTable();
			})
			
			querysTable = $('#querysTable').DataTable( {
				 pagingType: "full_numbers",
				 
			        data: data,
			        
			        columns: [
			            
			            { data: "imsi" },
			            
			            { data: "numberOfFailures" },

			            { data: "failureDuration" }
			           
			        ],
			        

			    } );
			
			showTable();
			$('#backToHomeButton').on('click', function(){
				hideTable();
			})
		};
		
		
		