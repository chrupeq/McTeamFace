var imsiStatsURL = "http://localhost:8080/GroupProject2016/rest/imsi/get_stats";
var imsiEventIDAndCauseCodeURL = "http://localhost:8080/GroupProject2016/rest/imsi/imsi_event_id/"

$(document).on('change', 'select', function(){findByIMSI(this.value)})
function findByIMSI(imsi) {
	console.log('inside the findByIMSI function');
	console.log('the value chosen is:' + imsi);
	$('#tableHeader').empty();
	$('#imsiTable tbody').empty();
	$.ajax({
		type:'GET',
		url: imsiEventIDAndCauseCodeURL + imsi,
		dataType:'json',
		success:buildIMSIEventIDTable
	});	
}
var buildIMSIEventIDTable = function(data) {
	console.log('inside the buildIMSIEventIDTable function');
	var imsiTable = $('#imsiTable');
	$('#tableName').text('IMSI Codes With EventIDs and Cause Codes');
	var tableHeader = $('#tableHeader');
	tableHeader = '<tr><th>IMSI</th><th>Event ID</th>' +
	'<th>Cause Code</th><th>Failure Class<tr>';
	$('thead').append(tableHeader);
	var imsiEventIDTableBody = $('#imsiTable tbody');
	$.each(data, function(index, imsiEventID) {
		var row = '<tr><td>'+imsiEventID.imsi+'<td>'+imsiEventID.event_id+'</td><td>'
		+imsiEventID.cause_code+'</td><td>'+imsiEventID.failure_class+'</td></tr>';
		imsiEventIDTableBody.append(row);
	});
//	$('#imsiTable').DataTable();
	$('#imsiTable').show(); 
};


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
	var imsiStatsTable = $('#imsiTable');
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
	$('#imsiTable').DataTable();
	$('#imsiTable').show();
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
		$('#imsiEventCauseButton').hide();
		$('#select_box').show();
		$.getJSON("http://localhost:8080/GroupProject2016/rest/imsi/get_unique", function(result) {
		    var options = $('select');
		    $.each(result, function(index, item) {
		        options.append('<option value="'+item+'">'+item+'</option>');
		    });
		});
	});
	
});



