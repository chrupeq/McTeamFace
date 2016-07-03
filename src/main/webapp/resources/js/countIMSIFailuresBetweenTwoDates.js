var numOfIMSIsBetweenTwoDates = "http://localhost:8080/GroupProject2016/rest/imsi/imsi_count_between_dates?"

$(document).ready(function(){
	console.log('js script ready!');
	$('h2').text('Find The number of call failures for a given IMSI in a given time period');
	$.getJSON("http://localhost:8080/GroupProject2016/rest/imsi/get_unique", function(result) {
	    var options = $('select');
	    $.each(result, function(index, item) {
	        options.append('<option value="'+item+'">'+item+'</option>');
	    });
	});
	$('#select_box').show();
	
	$('form').on('submit', function(e){
		e.preventDefault();
		$('h2').hide();
		console.log('the form has been submitted');
		var formContent = $('form').serialize();
		console.log(formContent);
		$.getJSON(numOfIMSIsBetweenTwoDates+formContent, function(result){
			$('form').empty();
			var imsiStatsTable = $('#imsiFailureCountTable');
			$('#tableName').text('Number of call failures for the chosen IMSI code');
			var tableHeader = '<tr><th>IMSI</th><th>Number of failures</th><tr>';
			$('#tableHeader').append(tableHeader);
			var imsiStatsTableBody = $('#imsiFailureCountTable tbody');
			$.each(result, function(index, imsiStat) {
				console.log('Current value: ' + imsiStat.imsi);
				console.log('Current value: ' + imsiStat.numberOfFailures);
				var row = '<tr><td>' + imsiStat.imsi + '</td>'
							+ '<td>' + imsiStat.numberOfFailures + '</td></tr>';
				imsiStatsTableBody.append(row);	
			});
//			$('#imsiFailureCountTable').DataTable();
			$('#imsiFailureCountTable').show();
		});
	});
});
