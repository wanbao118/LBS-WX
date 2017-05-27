$(document).ready(function() {
	getPrimaryCode();
	getSupportCode();
					$('#depositForm').bootstrapValidator(
									{
										message : 'This value is not valid',
										feedbackIcons : {
											valid : 'glyphicon glyphicon-ok',
											invalid : 'glyphicon glyphicon-remove',
											validating : 'glyphicon glyphicon-refresh'
										},
										fields : {
											accountNumber : {
												group: '.group',
												validators : {
													notEmpty : {
														message : 'please input Account Number!'
													},

													regexp : {
														regexp : '^[0-9]{12}$',
														message : 'Please input twelve number!'
													}
											}
											},	
											amount : {
												validators : {
													notEmpty : {
														message : 'Please input deposit amount!'
													},
													
												}
											}
										}
									}).on('success.form.bv', function(e) {
								// Prevent submit form
								e.preventDefault();
								var $form = $(e.target);
								validator = $form.data('bootstrapValidator');
								if (validator) {									
									creation(e.target);
								}
							});
				
})			

function creation(e) {		
	var accountNumber = $("#accountNumber").val();
	var amount= $("#amount").val();	
	var AdultObj = document.getElementById("currency");
	var currency = AdultObj.options[AdultObj.selectedIndex].value;		
	$('#depositForm').find('.alert-success').hide();
	$('#depositForm').find('.alert-warning').hide();
	var acct = {
		'accountNumber' : accountNumber,
		'currency' : currency,
		'amount' :amount,
		'operationCode' : 'D'
	};	

	$.ajax({		
		url : contextPath+"/service/accountbalance/transaction",		
		type : "post",
		contentType : "application/json",
		dataType : "json",
	    data : JSON.stringify(acct),
		success : function(response) {				
		 if (response.result == 00000) {				
			$('#depositForm').find('.alert-success').html('Deposit successfully!').show();
			} else {
				$('#depositForm').find('.alert-warning').html('Deposit Failed ! '+$.errorHandler.prop(response.errorCode[0])).show();
			}
	}
	});
		
}
function getPrimaryCode(){
	var acct = {
			"item":"Primary_Ccy_Code"		
	}
	$.ajax({
		url : contextPath+"/service/sysconf/getSysConfList",
		type : "post",
		contentType : "application/json",
		dataType : "json",
		data : JSON.stringify(acct),
		success : function(response) {
			if (response.result == 00000) {			
				for(var i = 0;i<response.listData.length;i++){
					$("#currency").append("<option value='"+response.listData[i].value+"'>"+response.listData[i].value+"</option>");
				}
			} 
		}
	})
}


function getSupportCode(){
	var acct = {
			"item":"Support_Ccy"		
	}
	$.ajax({
		url : contextPath+"/service/sysconf/getSysConfList",
		type : "post",
		contentType : "application/json",
		dataType : "json",
		data : JSON.stringify(acct),
		success : function(response) {
			if (response.result == 00000) {			
				for(var i = 0;i<response.listData.length;i++){
					$("#currency").append("<option value='"+response.listData[i].value+"'>"+response.listData[i].value+"</option>");
				}
			} 
		}
	})
}