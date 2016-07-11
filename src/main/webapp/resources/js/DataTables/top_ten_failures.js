var topTebUrl = "http://localhost:8080/GroupProject2016/rest/imsi/top10_market_operator_cellIdCombinations?";
var IMSI = "";
var closeButton = '<button id="backToHomeButton" type="button" class="btn btn-secondary">Back To Home</button>';
$(document).ready(function(){
	
});	

var findTopTenFailures = function(date1, date2){
	alert(date1 + " " + date2 + "test");
	$('#queryprogress').css('width', '0%');
 	$('#queryprogress').text('0%');
	$('#queryprogressouter').removeClass('animated fadeOutUp');
	$('#queryprogressouter').addClass('animated fadeInDown');
		$.ajax({
			type:'GET',
			url: topTebUrl + 'dateOne=' + date1 + '&dateTwo=' + date2,
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
				loadTopTenTable(data);
				instantChart(data);
				$('#queryprogressouter').mousemove(function(){
					$('#queryprogressouter').removeClass('animated fadeInDown');
					$('#queryprogressouter').addClass('animated fadeOutUp');
				});
			},
			error: $('#tableDiv').html('<h3 id="noDataMessage">no data to display for this query<h3>')
		});
		
}

	


	var loadTopTenTable = function(data){
		
		var table = '<table class="display table table-striped table-hover table-condensed animated fadeInDown" id="querysTable">'
		+ '<thead>'
		+ '<tr>'
		+ '<th align="left">Failure Count</th>'
		+ '<th align="left" class="col-sm-2">Market</th>'
		+ '<th align="left" class="col-sm-2">Operator</th>'
		+ '<th align="left" class="col-sm-2">Description</th>'
		+ '<th align="left" class="col-sm-2">Country</th>'
		+ '<th align="left" class="col-sm-2">Cell ID</th>'
		+ '</tr>'
		+ '</thead>'
		+ '</table>';
		
		$('#tableDiv').html(table);
		
	
		 querysTable = $('#querysTable').DataTable( {
			 pagingType: "full_numbers",
			 
		        data: data,
		        
		        columns: [

		            { data: "failureCount" },
		            
		            { data: "marketMCC" },
		            
		            { data: "operatorMNC" },
		            
		            { data: "operatorDescription" },
		            
		            { data: "country" },
		            
		            { data: "cell_id" },
		      
		        ],
		        
		        
		    } );
		 showTable();
			
			$('#backToHomeButton').on('click', function(){
				hideTable();
			});
			
		};
	
	