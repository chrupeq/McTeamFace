var failuresLineChart = function(data, date1, date2) {
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
				legend: {
					position: 'bottom'
				},
			
			}
		};
	appendRevealButton();
$('#chartTitle').html("Highest total durations that occured between these dates");
	var ctx = document.getElementById("skills").getContext("2d");
	myPie = new Chart(ctx, config);

	$("#skills").click( 	
		    function(evt){
		        var activePoints = myPie.getElementsAtEvent(evt);
		        var holder = activePoints[0];
		       
		        var label = myPie.data.labels[0];
		        
		        var string = "foo",
		        substring = "oo";
		    console.log(string.indexOf(substring) > -1);
		    var duration2 = "";
		    if(label.indexOf('>') > -1){
		    	duration2 == 3000000000;
		    }else{
		        var duration = label.toString().substring(2, label.length - 2);
		        
		        
		        if(duration == '1000'){
		        	duration2 = 0;
		        }else if(duration == '2000'){
		        	duration2 = 1000;
		        }else if(duration == '5000'){
		        	duration2 = 2000;
		        }else if(duration == '10000'){
		        	duration2 = 5000;
		        }else if(duration == '20000'){
		        	duration2 = 10000;
		        }
		    }

		    getImsiStatsInformationForDrilldown(date1, date2, duration2, duration);
		    }
		);
}