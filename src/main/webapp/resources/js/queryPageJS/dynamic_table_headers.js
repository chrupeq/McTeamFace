$(document).ready(function() {
	
});

var table = "";
var imsiWithDatesQuery = function(){
	
	table = 'table class="table table-striped table-hover table-condensed"'
		+ 'id="querysTable">'
		+ '<thead>'
						+'<tr>'
						+	'<th align="left">Report ID.</th>'
						+	'<th align="left" class="col-sm-2">IMSI</th>'
					+	'</tr>'
				+	'</thead>'
				+'</table>';
	$('#tableDiv').html(table);
	$('#tableDiv').hide();
	$('#tableDiv').addClass('animated fadeInDown');
	$('#tableDiv').show();
}

var modelQuery = function(){
	table = '<table class="table table-striped table-hover table-condensed animated fadeInDown"'
		+ 'id="querysTable">'
		+ '<thead>'
						+'<tr>'
						+	'<th align="left">Date/Time</th>'
						+	'<th align="left" class="col-sm-2">Cause Code</th>'
						+	'<th align="left" class="col-sm-2">Event ID</th>'
						+	'<th align="left" class="col-sm-2">Description</th>'
						+	'<th align="left" class="col-sm-2">Failure Class</th>'
						+	'<th align="left" class="col-sm-2">Failure Description</th>'
						+	'<th align="left" class="col-sm-2">Manufacturer</th>'
						+	'<th align="left" class="col-sm-2">Model</th>'
						+	'<th align="left" class="col-sm-2">Access Capability</th>'
						+	'<th align="left" class="col-sm-2">OS</th>'
						+	'<th align="left" class="col-sm-2">Country</th>'
						+	'<th align="left" class="col-sm-2">Operator</th>'
						+	'<th align="left" class="col-sm-2">Cell ID</th>'
						+	'<th align="left" class="col-sm-2">NE Version</th>'
					+	'</tr>'
				+	'</thead>'
				+'</table>';
	$('#tableDiv').html(table);
	$('#tableDiv').hide();
	$('#tableDiv').addClass('animated fadeInDown');
	$('#tableDiv').show();
}
