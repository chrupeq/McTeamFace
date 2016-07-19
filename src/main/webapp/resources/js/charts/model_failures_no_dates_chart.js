var modelsBarChart = function(data) {
	var labels = [];
	var failureData = [];
	var colours = [];
	var label = "";
	var numberOfFailures = "";
	var dataArray = data;
	var tacNumber = "";
	
	$('#skills').replaceWith('<canvas class="collapse" id="skills" width="300" height="300"></canvas>');
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
$('#chartTitle').html('Unique Failure Data For ' + data[0].user_equipment.manufacturer + ' ' + data[0].user_equipment.marketing_name);
Chart.defaults.global.legend.display = false;
	var ctx = document.getElementById("skills").getContext("2d");
	window.myPie = new Chart(ctx, config);
	
	$("#skills").click( 
		    function(evt){
		        var activePoints = myPie.getElementsAtEvent(evt);
		        var holder = activePoints[0];
		        
		        numberOfFailures = config.data.datasets[holder._datasetIndex].data[holder._index];
		       
		        label = myPie.data.labels[holder._index];
		        
		        var splitEventAndCause = label.toString().split(' ');
		        
		        var event_id = splitEventAndCause[0];
		        var cause_code = splitEventAndCause[1];
		        console.log(data);
		       for(var i = 0; i < dataArray.length; i ++){
		    	   if(data[i].event_cause.event_id == event_id && data[i].event_cause.cause_code == cause_code){
		    		   tacNumber = data[i].user_equipment.tac;
		    	   }
		       }

		        //get value by index      
//		        var value = myPie.data.datasets[0].data[0];
//		        alert("value: " + value);
		    }
		);
}