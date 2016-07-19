var checkDates = function(date1, date2){
	var dateRegex = '\\d{2,2}/\\d{2,2}/\\d{4,4}\\s\\d{2,2}:\\d{2,2}$';
	
	if(date1 == "" && date2 == ""){
		showErrors('bothBlank');
		return 'false';
	}
	
	if(date1 == ""){
		showErrors('firstBlank');
		return 'false';
	}
	
	if(date2 == ""){
		showErrors('secondBlank');
		return 'false';
	}
	
	if(date1.match(dateRegex)){
		
	}else{
		
	}
	
	var dateArray1 = date1.split('/');
	var dateArray2 = date2.split('/');
	var parsedDateOne = dateArray1[1] + '/' + dateArray1[0] + '/' + dateArray1[2];
	var parsedDateTwo = dateArray2[1] + '/' + dateArray2[0] + '/' + dateArray2[2];

	var dateOne = new Date(Date.parse(parsedDateOne));
	var dateTwo = new Date(Date.parse(parsedDateTwo));
	
	var currentDate = new Date();
	
	
	if(dateTwo < dateOne){
		showErrors('secondBefore');
		return 'false';
	}
	
	if(dateOne > currentDate.getTime() || dateOne > currentDate.getTime()){
		showErrors('firstInFuture');
		return 'false';
	}
	
}

var showErrors = function(errorToShow){
	$('.modalerrordiv').remove();
	
	if(errorToShow == 'secondBefore'){
		$('.modal-footer').prepend('<div class="modalerrordiv" id="errordiv" style="float: left; color: red;">Second date is before first date</div>');
	}
	
	if(errorToShow == 'firstInFuture'){
		$('.modal-footer').prepend('<div class="modalerrordiv" id="errordiv" style="float: left; color: red;">First date is in the future</div>');
	}
	
	if(errorToShow == 'bothBlank'){
		$('.modal-footer').prepend('<div class="modalerrordiv" id="errordiv" style="float: left; color: red;">Both fields cannot be blank</div>');
	}
	
	if(errorToShow == 'firstBlank'){
		$('.modal-footer').prepend('<div class="modalerrordiv" id="errordiv" style="float: left; color: red;">From field cannot be blank</div>');
	}
	
	if(errorToShow == 'secondBlank'){
		$('.modal-footer').prepend('<div class="modalerrordiv" id="errordiv" style="float: left; color: red;">To field cannot be blank</div>');
	}
}