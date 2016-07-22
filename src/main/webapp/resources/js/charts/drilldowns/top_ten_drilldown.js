var drilldown3Url = 'http://localhost:8080/GroupProject2016/rest/drill_down/drilldown_3?country=';
var countryName = "";
var showDrillDownMap = function(data, country){	
	countryName = country;
$('#chartdiv').removeClass('hiddenbycostanza');
	$('#chartdiv').removeClass('fadeOutUp');
	$('#chartdiv').addClass('fadeInDown');
	var countryCodes = [];
	
		if(country == 'Denmark'){
			countryCodes.push('DK');
		}else if(country == "Sweden"){
			countryCodes.push('SE');
		}else if(country == "Canada"){
			countryCodes.push('CA');
		}else if(country == "United States of America"){
			countryCodes.push('US');
		}else if(country == "Guadeloupe-France"){
			countryCodes.push('GLP');
		}else if(country == "Antigua and Barbuda"){
			countryCodes.push('AG');
		}else if(country == "India"){
			countryCodes.push('IN');
		}else if(country == "Japan"){
			countryCodes.push('JP');
		}else if(country == "Australia"){
			countryCodes.push('AU');
		}
		
	
	var dataSets = [[]];
		for(var i = 0; i < countryCodes.length; i ++){
		dataSets[0].push({ id : "" + countryCodes[i] + ""});
		}
		
	window.map = AmCharts.makeChart( "chartdiv", {

		  "type": "map",
		  colorSteps: 10,
		  "theme": "none",
		  "projection":"miller",

		  "dataProvider": {
		    "map": "worldHigh",
		    "areas": dataSets[0]
			
		  },
		  "areasSettings": {
		    "autoZoom": true,
		    "selectedColor": "#CC0000",
		    "selectable": true
		  },
		  "smallMap": {},
		  "export": {
		    "enabled": true,
		    "position": "bottom-right"
		  }
		} );
	
	map.addListener("clickMapObject", function (event) {
	    getInfoFromDatabase(country);
	});
}

var getInfoFromDatabase = function(country){
	$.ajax({
		type:'GET',
		url: drilldown3Url + country,
		dataType:'json',
		success: loadCountryTable
		
		
	});
}

var loadCountryTable = function(data){
	var table = '<h3 style="text-align: center;">Test</h3>'
		+ '<table class="display table table-striped table-hover table-condensed animated fadeInDown" id="drilldownTable">'
		+ '<thead>'
		+ '<tr>'
		+ '<th align="left" class="col-sm-2">Event ID</th>'
		+ '<th align="left" class="col-sm-2">Event Description</th>'
		+ '<th align="left" class="col-sm-2">Failure Description</th>'
		+ '<th align="left" class="col-sm-2">Manufacturer</th>'
		+ '<th align="left" class="col-sm-2">Model</th>'
		+ '</tr>'
		+ '</thead>'
		+ '</table>';
		
		$('#canvasdiv2').html(table);
		
	 drilldownTable = $('#drilldownTable').DataTable( {
		 
	        data: data,
	        
	        "pageLength": 5,
	        
	        "columnDefs": [
	                       { "width": "20%", "targets": 0,
	                    	   }
	                       
	                     ],
	        columns: [
	            
	            { data: "event_cause.event_id" },
	            
	            { data: "event_cause.description" },
	            
	            { data: "failure_class.description" },
	            
	            { data: "user_equipment.manufacturer" },
	            
	            { data: "user_equipment.marketing_name" },
	            
	        ],
	        	        
})
		
}