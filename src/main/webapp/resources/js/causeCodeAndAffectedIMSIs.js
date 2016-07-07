var getUniqueCauseCodesURL = "http://localhost:8080/GroupProject2016/rest/cause_codes/get_unique";
var getAffectedIMSIsURL = "http://localhost:8080/GroupProject2016/rest/cause_codes/get_by_cause_code?cause_code=";
$(document).ready(function(){
	console.log('js script ready!');
	$.getJSON(getUniqueCauseCodesURL, function(result) {
	    var options = $('select');
	    $.each(result, function(index, causeCode) {
	        options.append('<option value="'+causeCode+'">'+causeCode+'</option>');
	    });
	});
	$('#select_box').show();
});

$(document).on('change', '#select_box .causeCodes', function(){findByCauseCode(this.value);});
function findByCauseCode(causeCode){
	console.log('inside the find by cause code function!');
	console.log('the cause code picked: ' + causeCode);
	$('h2').empty();
	$('#select_box').empty();
	$.getJSON(getAffectedIMSIsURL+causeCode, function(result){
		var affectedIMSIsTable = $('#affectedIMSIsTable');
		$('#tableName').text('Affected IMSIs for Cause Code: ' + causeCode);
		var tableHeader = '<tr><th>IMSI</th><tr>';
		$('#tableHeader').append(tableHeader);
		var affectedIMSIsTableBody = $('#affectedIMSIsTable tbody');
		$.each(result, function(index, imsi){
			var row = '<tr><td>' + imsi + '</td></tr>'
			affectedIMSIsTableBody.append(row);	
		});
		$('#affectedIMSIsTable').show();
	})
}