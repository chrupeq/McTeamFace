var eventCauseFailuresURL = "http://localhost:8080/GroupProject2016/rest/event_cause_failures/count_for_tac?tac=";

$(document).ready(function(){
	console.log('js is ready!');
	$(document).on('click', '#seeEventCauseFailures', function(){
		console.log('the button has been pressed!');
		$('#seeEventCauseFailures').hide();
		var tacCode = 33000253;
//		var tacCode = 33000153;
		getEventCauseFailuresForATacCode(tacCode);
	});
	
	
});

function getEventCauseFailuresForATacCode(tacCode){
	console.log('inside the get event cause failures function.');
	$.getJSON(eventCauseFailuresURL+tacCode, function(result){
			var eventCauseFailuresTable = $('#eventCauseFailuresTable');
			$('#tableName').text('Number of Event Cause Failures Per User Equipment Model');
			var tableHeader = '<tr><th>Event ID</th><th>Cause Code</th><th>Number Of Failures</th><tr>';
			$('#tableHeader').append(tableHeader);
			var imsiStatsTableBody = $('#eventCauseFailuresTable tbody');
			$.each(result, function(index, causeCodeFailures){
			
				var row = '<tr><td>' + causeCodeFailures.event_id + '</td>'
							+ '<td>' + causeCodeFailures.cause_code + '</td>'
							+ '<td>' + causeCodeFailures.numberOfFailures + '</td></tr>';
				imsiStatsTableBody.append(row);	
			});
			$('#imsiFailureCountTable').show();
		});
	}
	
