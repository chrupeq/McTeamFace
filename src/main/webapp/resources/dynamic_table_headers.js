$(document).ready(function() {
$('#thisbutton').on("click", function(){
	tableFunction();
});
});

var tableFunction = function(){
	
	var table = 'table class="table table-striped table-hover table-condensed"'
		+ 'id="querysTable">'
		+ '<thead>'
						+'<tr>'
						+	'<th align="left">ID No.</th>'
						+	'<th align="left" class="col-sm-2">Title</th>'
						+	'<th align="left">Director</th>'
						+	'<th align="left">Year Made</th>'
						+	'<th align="left">Picture</th>'
						+	'<th align="left">More Info</th>'
					+	'</tr>'
				+	'</thead>'
				+'</table>';
	alert(table);
	$('#tableDiv').html(table);
}
