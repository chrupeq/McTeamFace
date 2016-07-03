var imsiStatsURL = "http://localhost:8080/GroupProject2016/rest/imsi/get_stats";
$(document).ready(function() {
	var accessLevel = getTheCookieYouNeed('job_title');
	if(accessLevel == "SE"){
		$('#imsiStats').hide();
	}
	if(accessLevel == "NME"){
		$('#allFailuresForModel').hide();
		$('#imsiWithDates').hide();
	}
	$('#selectByModel').on('change', function() {
		  var value = $(this).val();
	//	  $('#container').addClass('animated fadeOutUp');
		  $('#modelFailuresModal').modal('hide');
		});
});

var table = "";
var imsiWithDatesQuery = function(){
	
	table = '<table class="table table-striped table-hover table-condensed animated fadeInDown"'
		+ 'id="querysTable">'
		+ '<thead>'
						+'<tr>'
						+	'<th align="left">Date/Time</th>'
						+	'<th align="left" class="col-sm-2">IMSI</th>'
					+	'</tr>'
				+	'</thead>'
				+'</table>';
	$('#tableDiv').html(table);
	$('#tableDiv').hide();
	$('#tableDiv').addClass('animated fadeInDown');
	$('#tableDiv').show();
}

var modelQuery = function(){
	renderModelQueryTable();
}
	TableView = Backbone.View.extend({
		render:function(){
			var template = _.template($('#model_data_table').html(), {});
			$(this.el).html(template);
			return this;
		}
	});


var renderModelQueryTable = function(){
	var tableView = new TableView();
	$('#tableDiv').html(tableView.render().el);
}

var renderMainContainer = function(){
	var mainView = new MainView();
	$('#homeDiv').html(mainView.render().el);
}

var replaceContainer = function(){
	//querysTable.destroy();
	renderMainContainer();
	$('#queryHeader2').addClass('animated fadeOutUp');
	//$('#querysTable').addClass('animated fadeOutUp');
	$('#tableDiv').addClass('animated fadeOutUp');
	$('#infoDiv').addClass('animated fadeOutUp');
	$('#buttonDiv').addClass('animated fadeOutUp');
	$('#container').removeClass('animated fadeOutUp');
	$('#queryHeader').removeClass('animated fadeOutUp');
	$('#queryHeader').addClass('animated fadeInDown');
}
	MainView = Backbone.View.extend({
		initialize: function () {
			$(".js-example-basic-multiple").select2();
			$('#selectByModel').on('change', function() {
				  var value = $(this).val();
				//  $('#container').addClass('animated fadeOutUp');
				  $('#modelFailuresModal').modal('hide');
				  findAllUniqueModelFailures(value);
				});
			
			$('#imsiStats').on('click', function(){
				changeContainerCSS('imsiStats');
			})
		},
		
		events: {
			"click #imsiWithDates":function(){
				$('#dateQueryModal').modal('show');
				 $('#datetimepicker1').datetimepicker({
		             viewMode: 'years',
					 format: 'DD/MM/YYYY HH:mm'
		         });
				 $('#datetimepicker2').datetimepicker({
					 viewMode: 'years',
					 format: 'DD/MM/YYYY HH:mm'
		        });
			},
			
			"click #imsiStats":function(){
					changeContainerCSS('imsiStats');
				},
			
			
	"click #allFailuresForModel":function(){
	
		alert("here");
		$('#modelFailuresModal').removeClass('animated bounceOut');
		$('#modelFailuresModal').addClass('animated bounceIn');
		$('#modelFailuresModal').modal('show');
		$('#selectByModel').empty();
		findAllUniqueModels();
		
	},
			"click #modalClose":function(){
				$('#modelFailuresModal').addClass('animated bounceOut');
			},
	"click #modalClose2":function(){
		$('#modelFailuresModal').addClass('animated bounceOut');
	},
			"click #dateSearch":function(){
				changeContainerCSS('imsiDates');
			}
		},
		
		render:function(){
			var template = _.template($('#main_page_div').html(), {});
			$(this.el).html(template);
			this.delegateEvents();
			return this;
		}
	});
	

		var buildIMSIStatsTable = function(data) {
			imsiTable = '<table class="table table-striped table-hover table-condensed animated fadeInDown"'
				+ 'id="querysTable">'
				+ '<thead>'
								+'<tr>'
								+	'<th align="left">IMSI</th>'
								+	'<th align="left">Number of Failures</th>'
								+	'<th align="left" class="col-sm-2">Total duration of failure (in msc)</th>'
							+	'</tr>'
						+	'</thead>'
						+'</table>';
			$('#tableDiv').html(imsiTable);
			querysTable = $('#querysTable2').DataTable( {
				 pagingType: "full_numbers",
				 
			        data: data,
			        
			        columns: [
			            
			            { data: "imsi" },
			            
			            { data: "numberOfFailures" },

			            { data: "failureDuration" }
			           
			        ],
			        

			    } );
		};
		
		
		