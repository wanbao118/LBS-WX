$(document).ready(function() {
	getPrimaryCode();
	getSupportCode();
					$('#withDrawForm').bootstrapValidator(
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
														message : 'please input Withdraw Amount!'
													},

													regexp : {
														regexp : '^[0-9]+(\\.[0-9]+)?$',
														message : 'please input Withdraw Amount!'
													}
												}
											},

											accountNumber: {
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
									withDraw(e.target);
								}

							});
				});


function withDraw(e) {
	var accountNumber=$("#accountNumber").val();
	var amount=$("#amount").val();
	var currency=$("#currency").val();
	var acct={
			'accountNumber':accountNumber,
			'amount':amount,
			'currency':currency,
			'operationCode' : 'W'
	};
	$('#withDrawForm').find('.alert-success').hide();
	$('#withDrawForm').find('.alert-warning').hide();
	$.ajax({
		url : contextPath+"/service/accountbalance/transaction?date"+new Date(),
		type : "post",
		contentType : "application/json",
		dataType : "json",
		data : JSON.stringify(acct),
		success : function(response) {
			if (response.result == 00000) {
				$('#withDrawForm').find('.alert-success').html('Withdraw successfully!').show();
			} else {
				if (response.errorCode[0] == "10021")
				{
					location.href=contextPath+"/login.html";
				}
				$('#withDrawForm').find('.alert-warning').html('Withdraw fail ! '+$.errorHandler.prop(response.errorCode[0])).show();
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