var instantChart = function(data) {
//	$('#chartdiv').removeClass('hiddenbycostanza');
//	$('#chartdiv').removeClass('fadeOutUp');
//	$('#chartdiv').addClass('fadeInDown');
//	var countryCodes = [];
//	
//	data.map(function(item){
//		if(item.country == 'Denmark'){
//			countryCodes.push('DK');
//		}else if(item.country == "Sweden"){
//			countryCodes.push('SE');
//		}else if(item.country == "Canada"){
//			countryCodes.push('CA');
//		}else if(item.country == "United States of America"){
//			countryCodes.push('US');
//		}else if(item.country == "Guadeloupe-France"){
//			countryCodes.push('GLP');
//		}else if(item.country == "Antigua and Barbuda"){
//			countryCodes.push('AG');
//		}else if(item.country == "India"){
//			countryCodes.push('IN');
//		}else if(item.country == "Japan"){
//			countryCodes.push('JP');
//		}else if(item.country == "Australia"){
//			countryCodes.push('AU');
//		}
//		
//		
//		
//	})
//	
//	var dataSets = [[]];
//		for(var i = 0; i < countryCodes.length; i ++){
//		dataSets[0].push({ id : "" + countryCodes[i] + ""});
//		}
//	window.map = AmCharts.makeChart( "chartdiv", {
//
//		  "type": "map",
//		  colorSteps: 10,
//		  "theme": "none",
//		  "projection":"miller",
//
//		  "dataProvider": {
//		    "map": "worldHigh",
//		    "areas": dataSets[0]
//		  },
//		  "areasSettings": {
//		    "autoZoom": true,
//		    "selectedColor": "#CC0000",
//		    "selectable": true
//		  },
//		  "smallMap": {},
//		  "export": {
//		    "enabled": true,
//		    "position": "bottom-right"
//		  }
//		} );
//	
//	map.addListener("clickMapObject", function (event) {
//	    alert(event.mapObject.title);
//	});
	
	
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
		   
		       
		        var label = myPie.data.labels[0];
		       
		   
		        

		        //get value by index      
		        var value = myPie.data.datasets[0].data[0];
		    
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