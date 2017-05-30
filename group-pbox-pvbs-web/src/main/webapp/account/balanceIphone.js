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
	var tipbox = document.getElementById("tipbox");
	
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
			if(response.result == "10008"){
				$('#balanceForm').find('.alert').html('Account not exist!').show();
				tableDiv.style.display = "none";
			}else if(response.result == "00011"){
				$('#balanceForm').find('.alert').html('Balance enquiry failed!').show();
				tableDiv.style.display = "none";
			}else{
				tipbox.style.display = "none";
				tableDiv.style.display = "block";
				var rec = "<div class=\"alert alert-info alert-dismissible alert-info-new\" role=\"alert\">";
				rec = rec+"<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\"><span aria-hidden=\"true\">&times;</span></button>";
				rec = rec+"<i class=\"fa fa-info-circle\"></i>";
				
				
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
					rec = rec+ " " + realAccountNumber +" ";
					rec = rec+ " " + currentBalance.balance + " " + currentBalance.currencyCode +" ";
					rec = rec+ " at " +showTime + " ";
				}
				
				rec = rec+ "</div>";
				
				$("#data").append(rec);
						
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