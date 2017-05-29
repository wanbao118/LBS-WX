$(document).ready(function() {
	getPrimaryCode();
	getSupportCode();
					$('#transferForm').bootstrapValidator(
									{
										message : 'This value is not valid',

										feedbackIcons : {
											valid : 'glyphicon glyphicon-ok',
											invalid : 'glyphicon glyphicon-remove',
											validating : 'glyphicon glyphicon-refresh'
										},
										fields : {
											amount : {
												group: '.group',
												validators : {
													notEmpty : {
														message : 'please input Transfer Amount!'
													},

													regexp : {
														regexp : '^[0-9]+(\\.[0-9]+)?$',
														message : 'Please input Transfer Amount'
													}
												/*
												 * remote: { url:
												 * paths+'/service/employee/checkErExists',
												 * message: '用戶名不存在', delay :
												 * 2000,//per 2s send a request
												 * type: 'POST' }
												 */}
											},

											sourceAccountNumber: {
												validators : {
													notEmpty : {
														message : 'Please input Account Number!'
													},
													regexp : {
														regexp : '^[0-9]{12}$',
														message : 'Please input 12 numbers!'
													},
												}
											},
											targetAccountNumber: {
												validators : {
													notEmpty : {
														message : 'Please input Account Number!'
													},
													regexp : {
														regexp : '^[0-9]{12}$',
														message : 'Please input 12 numbers!'
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
				});

function creation(e) {
	
	var sourceAccountNumber=$("#sourceAccountNumber").val();
	var targetAccountNumber=$("#targetAccountNumber").val();
	var amount=$("#amount").val();
	var currency=$("#currency").val();
	$('#transferForm').find('.alert-success').hide();
	$('#transferForm').find('.alert-warning').hide();
	var acct={
			'accountNumber':sourceAccountNumber,
			'amount':amount,
			'currency':currency,
			'operationCode' : 'T',
			'params':{
				'targetAccountNum':targetAccountNumber
			}
	};
	
	$.ajax({
		url : contextPath+"/service/accountbalance/transaction",
		type : "post",
		contentType : "application/json",
		dataType : "json",
		data : JSON.stringify(acct),
		success : function(response) {
			if (response.result == 00000) {
				
				$('#transferForm').find('.alert-success').html('Transfer successfully!').show();
			} else {
				if (response.errorCode[0] == "10021")
				{
					location.href=contextPath+"/login.html";
				}
				$('#transferForm').find('.alert-warning').html('Transfer fail ! '+$.errorHandler.prop(response.errorCode[0])).show();
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
