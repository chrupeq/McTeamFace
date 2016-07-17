var modelsBarChart = function(data) {
	var labels = [];
	var failureData = [];
	var colours = [];
;	$('#skills').replaceWith('<canvas class="collapse" id="skills" width="300" height="300"></canvas>');
	config = null;
	
	var randomColour = function() {
		return 'rgba(' + (Math.floor(Math.random() * 256)) + ','
				+ (Math.floor(Math.random() * 256)) + ','
				+ (Math.floor(Math.random() * 256)) + ','
				+ (Math.floor(Math.random() * 256)) + ')';
	};
	
	data.map(function(item) {
		labels.push(item.event_cause.event_id + ' ' + item.event_cause.cause_code);
	});
	
	data.map(function(item) {
		failureData.push(item.hier3_id);
	});
		
	for(var i = 0; i < labels.length; i ++){
		colours.push(randomColour());
	}

	var config = {
		type : 'bar',
		data : {
			datasets : [ {
				label: 'Failure Count: ',
				data : failureData,
				backgroundColor : colours,

			} ],
			labels :  labels 
		},
		options : {
			responsive : true,
			
			scales: {
			    yAxes: [{
			      scaleLabel: {
			        display: true,
			        labelString: 'Total Number of Failures'
			      }
			    }],
			    xAxes: [{
				      scaleLabel: {
				        display: true,
				        labelString: data[0].user_equipment.manufacturer + ' ' + data[0].user_equipment.marketing_name + '\\'
				        + 'unique Event ID and Cause Code combinations'
				      }
				    }],
		},
	}
	}
	appendRevealButton();
$('#chartTitle').html("IMSI Failures With Durations");
Chart.defaults.global.legend.display = false;
	var ctx = document.getElementById("skills").getContext("2d");
	window.myPie = new Chart(ctx, config);
}