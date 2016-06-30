var imsiStatsURL = "http://localhost:8080/GroupProject2016/rest/imsi/get_stats";

function getIMSIDataForTable() {
	console.log("inside the getIMSIData function");
	$.ajax({
		type:'GET',
		url: imsiStatsURL,
		dataType:'json',
		success:buildIMSIStatsTable
	});
}
var buildIMSIStatsTable = function(data) {
	console.log('inside the build imsi table function');
	$('#imsiStatsButton').hide();
	var imsiStatsTable = $('#imsiStatsTable');
	$('#tableHeader').text('IMSI Statistics');
	var tableHeader = '<thead><tr><th>IMSI</th><th>Number of failures</th>' +
		'<th>Total duration of failure (in msc)</th><tr></thead>';
	imsiStatsTable.append(tableHeader);
	var imsiStatsTableBody = $('#imsiStatsTable tbody');
	$.each(data, function(index, imsiStat) {
		var row = '<tr><td>' + imsiStat.imsi + '</td>'
					+ '<td>' + imsiStat.numberOfFailures + '</td>'
					+ '<td>' + imsiStat.failureDuration + '</td></tr>';
		imsiStatsTableBody.append(row);	
	});
	$('#imsiStatsTable').DataTable();
	$('#imsiStatsTable').show();
};
$(document).ready(function(){
	console.log("imsi stats js script ready!");
	$('#select_box').hide();
	$('#imsiStatsButton').click(function(){
		console.log("the IMSI stats button has been pressed.");
		getIMSIDataForTable();
	});
	$('#imsiEventCauseButton').click(function() {
		console.log('imsi event cause button pressed')
		$('#imsiStatsButton').hide();
		$('#select_box').show();
		$.getJSON("http://localhost:8080/GroupProject2016/rest/imsi/get_unique", function(result) {
		    var options = $('select');
		    //don't forget error handling!
		    $.each(result, function(index, item) {
		        options.append('<option value="'+item+'">'+item+'</option>');
		    });
		});
	});
	
});



