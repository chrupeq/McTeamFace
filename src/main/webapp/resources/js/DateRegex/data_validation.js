var checkDates = function(date1, date2){
	alert("checking dates...");
	var dateRegex = '\\d{2,2}/\\d{2,2}/\\d{4,4}\\s\\d{2,2}:\\d{2,2}$';
	if(date1.match(dateRegex)){
		alert("true");
	}else{
		alert("false");
	}
	var dateOne = new Date(Date.parse(date1));
	var dateTwo = new Date(Date.parse(date2));
	
	var currentDate = new Date();
	
	if(dateTwo < dateOne){
		return "second date after first date";
	}
	
	if(dateTwo > currentDate.getTime() || dateOne > currentDate.getTime()){
		return "date in the future";
	}
	
}
