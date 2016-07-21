var failuresLineChart = function(data) {
	var labels = [];
	var failureData = [];
	var colours = [];
	var numberOfFailures = [];
	
	var counter1 = 0;
	var counter2 = 0;
	var counter3 = 0;
	var counter4 = 0;
	var counter5 = 0;
	var counter6 = 0;
	
	var counterArray = [];
	counterArray.push("< 1000ms");
	counterArray.push("< 2000ms");
	counterArray.push("< 5000ms");
	counterArray.push("< 10000ms");
	counterArray.push("< 20000ms");
	counterArray.push("> 20000ms");
	
;	$('#skills').replaceWith('<canvas class="collapse" id="skills" width="300" height="300"></canvas>');
	config = null;

	var randomColour = function() {
		return 'rgba(' + (Math.floor(Math.random() * 256)) + ','
				+ (Math.floor(Math.random() * 256)) + ','
				+ (Math.floor(Math.random() * 256)) + ','
				+ (Math.floor(Math.random() * 256)) + ')';
	};

	percentageArray = getPercentagesOfFailures(data);
	
//	data.map(function(item) {
//		labels.push(item.imsi);
//	});
	
	
	data.map(function(item) {
		numberOfFailures.push(item.numberOfFailures);
	});
	
	for(var i = 0; i < data.length; i ++){
		if(data[i].failureDuration <= 1000){
			counter1 ++;
		}
		
		if(data[i].failureDuration > 1000 && data[i].failureDuration <= 2000){
			counter2 ++;
		}
		
		if(data[i].failureDuration > 2000 && data[i].failureDuration <= 5000){
			counter3 ++;
		}
		
		if(data[i].failureDuration > 5000 && data[i].failureDuration <= 10000){
			counter4 ++;
		}
		
		if(data[i].failureDuration > 10000 && data[i].failureDuration <= 20000){
			counter5 ++;
		}
		
		if(data[i].failureDuration > 20000){
			counter6 ++;
		}
		
		
	}
	
	failureData.push(counter1);
	failureData.push(counter2);
	failureData.push(counter3);
	failureData.push(counter4);
	failureData.push(counter5);
	failureData.push(counter6);
	colours.push(randomColour());
	colours.push(randomColour());
	colours.push(randomColour());
	colours.push(randomColour());
	colours.push(randomColour());
	colours.push(randomColour());
	
		
//	for(var i = 0; i < labels.length; i ++){
//		colours.push(randomColour());
//		alert(colours.length);
//	}

	var config = {
			type : 'pie',
			data : {
				datasets : [ {

					data : failureData,
					backgroundColor : colours,

				} ],
				labels :  counterArray 
			},
			options : {
				responsive : true,
			
			}
		};
	appendRevealButton();
$('#chartTitle').html("Highest total durations that occured between these dates");
	var ctx = document.getElementById("skills").getContext("2d");
	window.myPie = new Chart(ctx, config);
}