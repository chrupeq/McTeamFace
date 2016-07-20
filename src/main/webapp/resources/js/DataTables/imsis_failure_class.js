//getByFailureClassModal - modal name
//failureClassSelect - select box name
//imsiByCauseCode - search button

imsisForFailureClass = 'http://localhost:8080/GroupProject2016/rest/imsi/get_per_failure_class';

$(document).ready(function(){
	
	$('#failureClassSelect').on('change', function(){
		hideTable();
		$('#getByFailureClassModal').modal('hide');
		var failureClass = $('#failureClassSelect option:selected').val();
			$('.modalerrordiv').remove();
		
		$('#searchParams').html('You are searching for IMSI failures with with failures of class ' + $('#failureClassSelect option:selected').text());
		loadFailureClassIMSITable(failureClass);
		
		})
})
	
	var getMoreUniqueImsis = function(){
	$.getJSON("http://localhost:8080/GroupProject2016/rest/imsi/unique_failure_class", function(result) {
	    $.each(result, function(index, item) {
	    	
	    	$('#failureClassSelect').append($("<option></option>")
                    .attr("value",item.failure_class)
                    .text(item.failure_class + ': ' + item.description));
	    });
	});
}

var loadFailureClassIMSITable = function(failureClass){
	$('#queryprogress').css('width', '0%');
 	$('#queryprogress').text('0%');
	$('#queryprogressouter').removeClass('animated fadeOutUp');
	$('#queryprogressouter').addClass('animated fadeInDown');
	$.ajax({
		type:'GET',
		url: imsisForFailureClass + "/?failure_class=" + failureClass,
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
			loadFailureClassIMSITablePart2(data);
			$('#queryprogressouter').mousemove(function(){
				$('#queryprogressouter').removeClass('animated fadeInDown');
				$('#queryprogressouter').addClass('animated fadeOutUp');
			});
		},
		error: function(){
			$('#tableDiv').html('<h3 id="noDataMessage">no data to display for this query<h3>');
			showTable();
			$('#backToHomeButton').on('click', function(){
				hideTable();
			});
			$('#queryprogressouter').mousemove(function(){
				$('#queryprogressouter').removeClass('animated fadeInDown');
				$('#queryprogressouter').addClass('animated fadeOutUp');
			});
		}
	});
}

var loadFailureClassIMSITablePart2 = function(data){
	
	var table = '<div class="tableClass"><table class="display table table-striped table-hover table-condensed animated fadeInDown" id="querysTable">'
	+ '<thead>'
	+ '<tr>'
	+ '<th align="left" class="col-sm-2">IMSI</th>'
	+ '</tr>'
	+ '</thead>'
	+ '</table></div>';
	
	
	$('#tableDiv').html(table);
	 querysTable = $('#querysTable').DataTable( {
		 pagingType: "full_numbers",
		 
	        data: data,
	        
	        columns: [
	            
	            { data: "imsi"}
	      
	        ],
	        
	        
	    } );
	
	 showTable();
	 $('#querysTable_filter').hide();
	 $('#querysTable').css('width: 10%;');
	
	$('#backToHomeButton').on('click', function(){
		hideTable();
	});
	};