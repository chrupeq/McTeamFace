imsisTopTenUrl = 'http://localhost:8080/GroupProject2016/rest/imsi/top10_IMSI';

$(document).ready(function(){
	$('#topTenImsiSearch').on('click', function(){
		hideTable();
		var eleventh = $("#datetimepicker11").find("input").val();
		var twelfth = $("#datetimepicker12").find("input").val();
		if(checkDates(eleventh, twelfth) == 'false'){
			
		}else{
			$('#topTenImsiModal').modal('hide');
			$('.modalerrordiv').remove();
		
		$('#searchParams').html('You are searching for the top ten IMSI\'s with failures between ' + eleventh + ' and ' + twelfth);
		loadTopTenImsiTable(eleventh, twelfth);
		}
		})
})
	
	

var loadTopTenImsiTable = function(eleventh, twelfth){
	$('#queryprogress').css('width', '0%');
 	$('#queryprogress').text('0%');
	$('#queryprogressouter').removeClass('animated fadeOutUp');
	$('#queryprogressouter').addClass('animated fadeInDown');
	$.ajax({
		type:'GET',
		url: imsisTopTenUrl + "/?dateOne=" + eleventh + "&dateTwo=" + twelfth,
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
			loadTopTenImsis(data);
			if(getTheCookieYouNeed('job_title') == 'NME'){
			topTenImsiChart(data);
			}
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

var loadTopTenImsis = function(data){
	
	var table = '<table class="display table table-striped table-hover table-condensed animated fadeInDown" id="querysTable">'
	+ '<thead>'
	+ '<tr>'
	+ '<th align="left" id="clickMe" class="col-sm-2">Number of Failures</th>'
	+ '<th align="left" class="col-sm-2">IMSI</th>'
	+ '</tr>'
	+ '</thead>'
	+ '</table>';
	
	$('#tableDiv').html(table);
	 querysTable = $('#querysTable').DataTable( {
		 pagingType: "full_numbers",
		 
	        data: data,
	        
	        columns: [
	            
	            { data: "failureCount" },
	            
	            { data: "imsi" }
	      
	        ],
	        
	        
	    } );
	
	 showTable();
	 $('#clickMe').click();
	 
	
	$('#backToHomeButton').on('click', function(){
		hideTable();
	});
	};