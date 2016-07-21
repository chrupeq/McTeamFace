var drilldown2Url = 'http://localhost:8080/GroupProject2016/rest/drill_down/imsi_desc?';
var dataArray = [];

var labels = [];
var failureDuration = [];
var colours = [];

var getImsiStatsInformationForDrilldown = function(imsi){
	$('#queryprogress').css('width', '0%');
 	$('#queryprogress').text('0%');
	$('#queryprogressouter').removeClass('animated fadeOutUp');
	$('#queryprogressouter').addClass('animated fadeInDown');
	$.ajax({
		type:'GET',
		url: drilldown2Url + 'imsi=' + imsi,
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
			imsiIndividualFailingsAndDurations(data);
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

var imsiIndividualFailingsAndDurations = function(data){
	
	var randomColour = function() {
		return 'rgba(' + (Math.floor(Math.random() * 256)) + ','
				+ (Math.floor(Math.random() * 256)) + ','
				+ (Math.floor(Math.random() * 256)) + ','
				+ (Math.floor(Math.random() * 256)) + ')';
	};

	percentageArray = getPercentagesOfFailures(data);
	
	data.map(function(item) {
		labels.push(item.date_time);
	});
	
	data.map(function(item) {
		failureDuration.push(item.duration);
	});
		
	for(var i = 0; i < labels.length; i ++){
		colours.push(randomColour());
	}
	
	$('#canvasdiv2').html('<h3 id="chartTitle2"></h3><canvas id="skills2" width="300" height="300"></canvas>');
	
	var singleFailures = {
			type : 'line',
			data : {
				datasets : [ {
					data : failureDuration,
					backgroundColor : colours,

				} ],
				labels :  labels 
			},
			options : {
				responsive : true,
				legend : {
					position : 'bottom'
				},
				
				
				scales: {
				    yAxes: [{
				      scaleLabel: {
				        display: true,
				        labelString: 'Total Failures In Milliseconds'
				      }
				    }],
				    xAxes: [{
					      scaleLabel: {
					        display: true,
					        labelString: 'Date/Time'
					      }
					    }],
			},
		}
		}
	
	$('#chartTitle2').html('Individual failures and Durations for ' + data[0].imsi);
	Chart.defaults.global.legend.display = false;
		var ctx = document.getElementById("skills2").getContext("2d");
		window.failuresChart = new Chart(ctx, singleFailures);
}
//var modelDrillDownTable = function(data){
//dataArray = data;
//	var table = '<h3 style="text-align: center;">Individual failure records and associated information</h3>'
//	+ '<table class="display table table-striped table-hover table-condensed animated fadeInDown" id="drilldownTable">'
//	+ '<thead>'
//	+ '<tr>'
//	+ '<th align="left">Date/Time</th>'
//	+ '<th align="left" class="col-sm-2">Report ID</th>'
//	+ '<th align="left" class="col-sm-2">Event Cause Description</th>'
//	+ '<th align="left" class="col-sm-2">Failure Description</th>'
//	+ '<th align="left" class="col-sm-2">Country</th>'
//	+ '<th align="left" class="col-sm-2">Operator</th>'
//	+ '</tr>'
//	+ '</thead>'
//	+ '</table>';
//	
//	$('#canvasdiv2').html(table);
//
//	 drilldownTable = $('#drilldownTable').DataTable( {
//		 
//	        data: data,
//	        
//	        rowId: "report_id",
//	        
//	        "pageLength": 5,
//	        
//	        "columnDefs": [
//	                       { "width": "20%", "targets": 0,
//	                    	   "targets": [1],
//	                           "visible": false,
//	                           "searchable": false,
//	                    	   }
//	                       
//	                     ],
//	        columns: [
//
//	            { data: "date_time",
//	            	render : function(data) {
//	            		
//	            		var date = new Date(data);
//						return date.getUTCDate() + '/' + (date.getUTCMonth() + 1)+ '/' + date.getFullYear() + ' ' + date.getHours() + ':' + date.getMinutes();
//					}
//	            },
//	            
//	            { data: "report_id" },
//	            
//	            { data: "event_cause.description" },
//	            
//	            { data: "failure_class.description" },
//	            
//	            { data: "mcc_mnc.country" },
//	            
//	            { data: "mcc_mnc.operator" },
//	            
//	        ],
//	        	        
//})
//
//$('#drilldownTable tbody').on( 'click', 'tr', function () {
//	var reportId = drilldownTable.row(this).data().report_id;
//	showMoreInfoModal(reportId);
//	});
//}

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
