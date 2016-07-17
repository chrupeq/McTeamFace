var failuresLineChart = function(data) {
	var labels = [];
	var failureData = [];
	var colours = [];
	var numberOfFailures = [];
;	$('#skills').replaceWith('<canvas class="collapse" id="skills" width="300" height="300"></canvas>');
	config = null;
	
	var randomColour = function() {
		return 'rgba(' + (Math.floor(Math.random() * 256)) + ','
				+ (Math.floor(Math.random() * 256)) + ','
				+ (Math.floor(Math.random() * 256)) + ','
				+ (Math.floor(Math.random() * 256)) + ')';
	};

	percentageArray = getPercentagesOfFailures(data);
	
	data.map(function(item) {
		labels.push(item.imsi);
	});
	
	data.map(function(item) {
		numberOfFailures.push(item.numberOfFailures);
	});
	
	data.map(function(item) {
		failureData.push(item.failureDuration);
	});
		
	for(var i = 0; i < labels.length; i ++){
		colours.push(randomColour());
	}

	var config = {
		type : 'bar',
		data : {
			datasets : [ {
				label : numberOfFailures,
				data : failureData,
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
				        labelString: 'IMSI Number'
				      }
				    }],
		},
	}
	}
	appendRevealButton();
$('#chartTitle').html("IMSI Failures With Durations");
	var ctx = document.getElementById("skills").getContext("2d");
	window.myPie = new Chart(ctx, config);
}