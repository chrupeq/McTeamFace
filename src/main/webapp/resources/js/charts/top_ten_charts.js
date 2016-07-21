var instantChart = function(data) {
	console.log(data);
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
		labels.push(item.operatorDescription);
	});
	
	data.map(function(item) {
		failureData.push(item.failureCount);
	});
	
	for(var i = 0; i < percentageArray.length; i ++){
		labels[i] += ' (' + percentageArray[i].toFixed(2) + '%)';
	}
		
	for(var i = 0; i < labels.length; i ++){
		colours.push(randomColour());
	}

	var config = {
		type : 'doughnut',
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
			},
		}
	};
showCharts();
		
	appendRevealButton();
$('#chartTitle').html("Top ten failures");
	var ctx = document.getElementById("skills").getContext("2d");
	myPie = new Chart(ctx, config);
	
	$("#skills").click( 
		    function(evt){
		        var activePoints = myPie.getElementsAtEvent(evt);
		       
		        var holder = activePoints[0];
		   
		       
		        var label = myPie.data.labels[holder._index];
		        
		      
		       
		   
		        

		        //get value by index      
		        var value = myPie.data.datasets[0].data[0];
		  
		       var indexForSub = label.indexOf('(');
		       var dataForMap = label.substring(0, indexForSub - 1);
		       
		       for(var i = 0; i < data.length; i ++){
		    	   if(data[i].operatorDescription == dataForMap){
		    		   showDrillDownMap(data, data[i].country);
		    		   break;
		    	   }
		    	   }
		       }
		);
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