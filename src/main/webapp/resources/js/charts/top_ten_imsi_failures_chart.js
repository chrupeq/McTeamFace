var topTenImsiChart = function(data) {
	var labels = [];
	var failureData = [];
	var colours = [];
	var percentageArray = [];
	$('#skills').replaceWith('<canvas id="skills" width="300" height="300"></canvas>');
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
		failureData.push(item.failureCount);
	});
	
	for(var i = 0; i < percentageArray.length; i ++){
		labels[i] += ' (' + percentageArray[i] + '%)';
	}
		
	for(var i = 0; i < labels.length; i ++){
		colours.push(randomColour());
	}

	var config = {
		type : 'pie',
		data : {
			datasets : [ {

				data : failureData,
				backgroundColor : colours,

			} ],
			labels :  labels 
		},
		options : {
			responsive : true,
			legend : {
				position : 'bottom'
			}
		}
	};
	appendRevealButton();
$('#chartTitle').html("Top ten IMSI failures");
	var ctx = document.getElementById("skills").getContext("2d");
	window.myPie = new Chart(ctx, config);
}

var getPercentagesOfFailures = function(data){
	var totalFailures = 0;
	var onePercent = 0;
	var percentageArray = [];
	var failureArray = [];

	data.map(function(item) {
		totalFailures += parseInt(item.failureCount);
	});
	
	onePercent = totalFailures / 100;
	
	data.map(function(item) {
		percentageArray.push(parseInt(item.failureCount) / onePercent);
	});
	
	return percentageArray;
}