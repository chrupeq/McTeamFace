var labels = [];
var failureData = [];
var colours = [];
var instantChart = function(data) {
	var randomScalingFactor = function(data) {
		return 1;
	};
	var randomColorFactor = function() {
		return 1;
	};
	var randomColour = function() {
		return 'rgba(' + (Math.floor(Math.random() * 256)) + ','
				+ (Math.floor(Math.random() * 256)) + ','
				+ (Math.floor(Math.random() * 256)) + ','
				+ (Math.floor(Math.random() * 256)) + ')';
	};

	data.map(function(item) {
		labels.push(item.operatorDescription);
	});

	data.map(function(item) {
		failureData.push(item.failureCount);
	});
	
	for(var i = 0; i < labels.length; i ++){
		alert("here");
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
			responsive : true
		}
	};

	var ctx = document.getElementById("skills").getContext("2d");
	window.myPie = new Chart(ctx, config);
    alert(colours[0]);
	alert(failureData);
	$('#randomizeData').click(function() {
		$.each(config.data.datasets, function(i, piece) {
			$.each(piece.data, function(j, value) {
				config.data.datasets[i].data[j] = randomScalingFactor();
				//config.data.datasets.backgroundColor[i] = 'rgba(' + randomColorFactor() + ',' + randomColorFactor() + ',' + randomColorFactor() + ',.7)';
			});
		});
		window.myPie.update();
	});
	$('#addDataset').click(
			function() {
				var newDataset = {
					backgroundColor : [ randomColor(0.7), randomColor(0.7),
							randomColor(0.7), randomColor(0.7),
							randomColor(0.7) ],
					data : [ randomScalingFactor(), randomScalingFactor(),
							randomScalingFactor(), randomScalingFactor(),
							randomScalingFactor() ]
				};
				config.data.datasets.push(newDataset);
				window.myPie.update();
			});
	$('#removeDataset').click(function() {
		config.data.datasets.splice(0, 1);
		window.myPie.update();
	});
}