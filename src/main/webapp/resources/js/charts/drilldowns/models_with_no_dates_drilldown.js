var drilldown1Url = 'http://localhost:8080/GroupProject2016/rest/drill_down/failure_event_desc?';
var dataArray = [];
var getModelInfoForDrilldown = function(eventId, causeCode, tacNumber){
	$('#queryprogress').css('width', '0%');
 	$('#queryprogress').text('0%');
	$('#queryprogressouter').removeClass('animated fadeOutUp');
	$('#queryprogressouter').addClass('animated fadeInDown');
	$.ajax({
		type:'GET',
		url: drilldown1Url + 'eventId=' + eventId + '&causeCode=' + causeCode + '&tacNumber=' + tacNumber,
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
	var table = '<table class="display table table-striped table-hover table-condensed animated fadeInDown" id="drilldownTable">'
	+ '<thead>'
	+ '<tr>'
	+ '<th align="left">Date/Time</th>'
	+ '<th align="left" class="col-sm-2">Report ID</th>'
	+ '<th align="left" class="col-sm-2">Event Cause Description</th>'
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
	                    	   "targets": [1],
	                           "visible": false,
	                           "searchable": false,
	                    	   }
	                       
	                     ],
	        columns: [

	            { data: "date_time",
	            	render : function(data) {
	            		
	            		var date = new Date(data);
						return date.getUTCDate() + '/' + (date.getUTCMonth() + 1)+ '/' + date.getFullYear() + ' ' + date.getHours() + ':' + date.getMinutes();
					}
	            },
	            
	            { data: "report_id" },
	            
	            { data: "event_cause.description" },
	            
	            { data: "failure_class.description" },
	            
	            { data: "mcc_mnc.country" },
	            
	            { data: "mcc_mnc.operator" },
	            
	        ],
	        	        
})

$('#drilldownTable tbody').on( 'click', 'tr', function () {
	var reportId = drilldownTable.row(this).data().report_id;
	showMoreInfoModal(reportId);
	});
}

var showMoreInfoModal = function(reportId){
	var stuffToGoIntoModalBody = "";
	for(var i = 0; i < dataArray.length; i ++){
		if(dataArray[i].report_id == reportId){
			$('#drilldownmodaltitle').html('<h2>More information for ' + dataArray[i].user_equipment.manufacturer + ' ' + dataArray[i].user_equipment.marketing_name);
			stuffToGoIntoModalBody += '<h4>Handset Manufacturer: </h4><p>' + dataArray[i].user_equipment.manufacturer + '</p>'
									+ '<h4>Handset Model: </h4><p>' + dataArray[i].user_equipment.marketing_name + '</p>'
									+ '<h4>Country: </h4><p>' + dataArray[i].mcc_mnc.country + '</p>'
									+ '<h4>Handset Model: </h4><p>' + dataArray[i].mcc_mnc.operator + '</p>';
									
		}
	}
	$('#drilldownmodalcenter').html(stuffToGoIntoModalBody);
	$('#drilldownmodal').modal('show');
}
