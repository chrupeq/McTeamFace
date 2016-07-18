var injectNavBar = function(userType){
var headers = "";
	if(userType == 'SA'){
	
	}
	
	if(userType == 'CSR'){
	headers = '<li id="querytab" class="hiddenbycostanza"><a data-toggle="tab" href="#queryBody" id="queryTabClick">Queries</a></li>'
		+ '<li id="contacttab" class="hiddenbycostanza"><a data-toggle="tab" href="#contactus" id="contact">Contact Us</a></li>'
		+ '<li id="abouttab" class="hiddenbycostanza"><a data-toggle="tab" href="#aboutus" id="about">About Us</a></li>';
	$('.nav-tabs').html(headers);
	}
	
	if(userType == 'NME'){
	headers = '<li id="querytab" class="hiddenbycostanza"> <a href="#menu-toggle" class="btn btn-default" id="menu-toggle">Toggle Menu</a></li>'
		+ '<li id="importtab" class="hiddenbycostanza"><a data-toggle="tab" href="#importDataset" id="importDatasetTab">Import Datasets</a></li>';
		+ '<li id="contacttab" class="hiddenbycostanza"><a data-toggle="tab" href="#contactus" id="contact">Contact Us</a></li>'
		+ '<li id="abouttab" class="hiddenbycostanza"><a data-toggle="tab" href="#aboutus" id="about">About Us</a></li>';
		$('.nav-tabs').html(headers);
	}
	
	if(userType == 'SE'){
			headers = '<li id="querytab" class="hiddenbycostanza"><a data-toggle="tab" href="#queryBody" id="queryTabClick">Queries</a></li>'
				+ '<li id="contacttab" class="hiddenbycostanza"><a data-toggle="tab" href="#contactus" id="contact">Contact Us</a></li>'
				+ '<li id="abouttab" class="hiddenbycostanza"><a data-toggle="tab" href="#aboutus" id="about">About Us</a></li>';
				$('.nav-tabs').html(headers);
		}
	
	if(userType == 'logged out'){
		headers = '<li class="hiddenbycostanza" id="welcometab"><a data-toggle="tab" href="#home" id="wilkommen">Welcome</a></li>';
		$('#home').removeClass('hiddenbycostanza');
		$('#welcometab').removeClass('hiddenbycostanza');
		$('#wilkommen').removeClass('hiddenbycostanza');
		$('.nav-tabs').html(headers);
		$('#wikommen').click();
	}
	
    $("#menu-toggle").click(function(e) {
        e.preventDefault();
        $("#wrapper").toggleClass("toggled");
    });
	}