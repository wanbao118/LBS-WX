$(document)
		.ready(
				function() {
					$('#balanceForm').bootstrapValidator(
									{
										message : 'This value is not valid',

										feedbackIcons : {
											valid : 'glyphicon glyphicon-ok',
											invalid : 'glyphicon glyphicon-remove',
											validating : 'glyphicon glyphicon-refresh'
										},
										
										fields : {
											realAccountNumber : {
												group: '.group',
												validators : {
													notEmpty : {
														message : 'please input account number!'
													},

													regexp : {
														regexp : '^[0-9]{12}$',
														message : 'Please input twelve number!'
													}
												}
											},				
										}
									}).on('success.form.bv', function(e) {
								// Prevent submit form
								e.preventDefault();

								var $form = $(e.target);
								validator = $form.data('bootstrapValidator');
								if (validator) {
									balance(e.target);
								}

							});
				});

function balance(e){
	var realAccountNumber = $("#realAccountNumber").val();
	
	var tableDiv = document.getElementById("balanceShow");
	var tipboxwarn = document.getElementById("tipboxwarn");
	
	var acct = {
			'realAccountNumber' : realAccountNumber,
			'operationCode' : 'E'
	}
	
	$.ajax({		
		url : contextPath+"/service/acct/acctMaintenance",
		type : "post",
		contentType : "application/json",
		dataType : "json",
		data : JSON.stringify(acct),
		success: function(response){
			if(response.result != "00000"){
				if(response.errorCode[0] == "10021")
				{
					location.href=contextPath+"/login.html";
				}
				$('#balanceForm').find('.alert-warning').html($.errorHandler.prop(response.errorCode[0])).show();
				tableDiv.style.display = "none";
			}
			else{
				tipboxwarn.style.display = "none";
				tableDiv.style.display = "block";
				var balanceTable = "<table id = 'EmployeeList'" +
						"class='table table-striped table-bordered'>" +
						"<thead><tr><th>Account number</th><th>Balance</th><th>Last Updated time</th></tr></thead>";
				
				var balanceList = response.listData;		
				for(var i=0;i<balanceList.length;i++){
					var currentBalance = balanceList[i];									
					var updatedDate = currentBalance.lastUpatedDate;
					var newDate = new Date(updatedDate);
					var year = newDate.getFullYear();
					var month = newDate.getMonth()+1;
					var date = newDate.getDate();
					var hour = newDate.getHours();
					var min = newDate.getMinutes();
					var second = newDate.getSeconds();
					var showTime = year+"-"+add0(month)+"-"+add0(date)+" "+add0(hour)+":"+add0(min)+":"+add0(second);
					balanceTable += "<tr><td>" + realAccountNumber +"</td>";
					balanceTable += "<td>" + currentBalance.balance + " " + currentBalance.currencyCode +"</td>";
					balanceTable += "<td>" +showTime + "</td></tr>";
				}
				
				balanceTable += "</table>";
				
				$("#balanceShow").html(balanceTable);
						
			}
		},
	
//		error: function(){
//			alert("database error");
//		}
	});	
	
}

function add0(m){
	return m<10?'0'+m:m 
}