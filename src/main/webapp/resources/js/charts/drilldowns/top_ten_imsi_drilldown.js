var drilldown4Url = 'http://localhost:8080/GroupProject2016/rest/drill_down/drilldown_4?imsi=';
var dataArray = [];
var getMoreInfoDrilldown4 = function(imsi){
	$('#queryprogress').css('width', '0%');
 	$('#queryprogress').text('0%');
	$('#queryprogressouter').removeClass('animated fadeOutUp');
	$('#queryprogressouter').addClass('animated fadeInDown');
	$.ajax({
		type:'GET',
		url: drilldown4Url + imsi,
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
			modelDrillDownTable(data);
			$('#canvasdiv2').removeClass('hiddenbycostanza');
			$('#canvasdiv2').removeClass('animated fadeOutUp');
			$('#canvasdiv2').addClass('animated fadeInDown');
			$('#queryprogressouter').mousemove(function(){
				$('#queryprogressouter').removeClass('animated fadeInDown');
				$('#queryprogressouter').addClass('animated fadeOutUp');
			});
		},
	});
}

var modelDrillDownTable = function(data){
dataArray = data;
	var table = '<h3 style="text-align: center;">More information for </h3>'
	+ '<table class="display table table-striped table-hover table-condensed animated fadeInDown" id="drilldownTable">'
	+ '<thead>'
	+ '<tr>'
	+ '<th align="left" class="col-sm-2">Event ID</th>'
	+ '<th align="left" class="col-sm-2">Event Description</th>'
	+ '<th align="left" class="col-sm-2">Cause Code</th>'
	+ '<th align="left" class="col-sm-2">Failure Description</th>'
	+ '<th align="left" class="col-sm-2">Country</th>'
	+ '<th align="left" class="col-sm-2">Operator</th>'
	+ '</tr>'
	+ '</thead>'
	+ '</table>';
	
	$('#canvasdiv2').html(table);

	 drilldownTable = $('#drilldownTable').DataTable( {
		 
	        data: data,
	        
	        rowId: "report_id",
	        
	        "pageLength": 5,
	        
	        "columnDefs": [
	                       { "width": "20%", "targets": 0,

	                    	   }
	                       
	                     ],
	        columns: [

	          
	            { data: "eventId" },
	            
	            { data: "eventDescription" },
	            
	            { data: "causeCode" },
	            
	            { data: "failureDescription" },
	            
	            { data: "operator" },
	            
	            { data: "country" },
	            
	        ],
	        	        
})


}
