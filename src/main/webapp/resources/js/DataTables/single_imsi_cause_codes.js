var imsisWithCauseCodes = "http://localhost:8080/GroupProject2016/rest/imsi/unique_causeCode_failureClass_Description?";
var IMSI = "";
var closeButton = '<button id="backToHomeButton" type="button" class="btn btn-secondary">Back To Home</button>';
	
var moreImsis = function(){
	$.getJSON("http://localhost:8080/GroupProject2016/rest/imsi/get_unique", function(result) {
	    var options = $('#IMSIsCauseCodes');
	    $.each(result, function(index, item) {
	        options.append('<option value="'+item+'">'+item+'</option>');
	    });
	});
}

var loadImsiCauseCodesTable = function(imsi){
	$('#queryprogress').css('width', '0%');
 	$('#queryprogress').text('0%');
	$('#queryprogressouter').removeClass('animated fadeOutUp');
	$('#queryprogressouter').addClass('animated fadeInDown');
		$.ajax({
			type:'GET',
			url: imsisWithCauseCodes + 'imsi=' + imsi,
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
				loadImsisWithCauseCodes(data);
				$('#queryprogressouter').mousemove(function(){
					$('#queryprogressouter').removeClass('animated fadeInDown');
					$('#queryprogressouter').addClass('animated fadeOutUp');
				});
			},
			error: $('#tableDiv').html('<h3 id="noDataMessage">no data to display for this query<h3>')
		});
		
}

	


	var loadImsisWithCauseCodes = function(data){
		
		var table = '<table class="display table table-striped table-hover table-condensed animated fadeInDown" id="querysTable">'
		+ '<thead>'
		+ '<tr>'
		+ '<th align="left">Cause Code</th>'
		+ '<th align="left">Failure Class</th>'
		+ '<th align="left" class="col-sm-2">Description</th>'
		+ '</tr>'
		+ '</thead>'
		+ '</table>';
		
		$('#tableDiv').html(table);
		
	
		 querysTable = $('#querysTable').DataTable( {
			 pagingType: "full_numbers",
			 
		        data: data,
		        
		        columns: [

		            { data: "causeCode" },
		            
		            {data: "failureClass"},
		            
		            { data: "description" }
		      
		        ],
		        
		        
		    } );
		 showTable();
			
			$('#backToHomeButton').on('click', function(){
				hideTable();
			});
		};
	
		$(document).ready(function(){
			
			$('#IMSIsCauseCodes').on('change', function(){
				IMSI = this.value;

				$('.modalerrordiv').remove();


					hideTable();
					$('#singleImsiModal').modal('hide');
					$('.modalerrordiv').remove();
				    $('#searchParams').html('You are searching for unique failures for IMSI ' + IMSI);
				
				loadImsiCauseCodesTable(IMSI);
			});
			
		});	