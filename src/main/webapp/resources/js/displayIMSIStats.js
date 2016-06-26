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
	var tableHeader = '<tr><th>IMSI</th><th>Number of failures</th>' +
		'<th>Total duration of failure (in msc)</th><tr>';
	imsiStatsTable.append(tableHeader);
	$.each(data, function(index, imsiStat) {
		var row = '<tr><td>' + imsiStat.imsi + '</td>'
					+ '<td>' + imsiStat.failureDuration + '</td>'
					+ '<td>' + imsiStat.numberOfFailures + '</td></tr>';
		imsiStatsTable.append(row);	
	});
};
$(document).ready(function(){
	console.log("imsi stats js script ready!");
	$('#imsiStatsButton').click(function(){
		console.log("the IMSI stats button has been pressed.");
		getIMSIDataForTable();
	});
	
});

