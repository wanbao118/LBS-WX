$(document).ready(function() {
	getPrimaryCode();
					$('#tdForm').bootstrapValidator(
									{
										message : 'This value is not valid',

										feedbackIcons : {
											valid : 'glyphicon glyphicon-ok',
											invalid : 'glyphicon glyphicon-remove',
											validating : 'glyphicon glyphicon-refresh'
										},
										fields : {
											transAccountNum : {
												group: '.group',
												validators : {
													notEmpty : {
														message : 'please input transaction account number!'
													},

													regexp : {
														regexp : '^[0-9]{12}$',
														message : 'Please input 12 numbers!'
													}
												}
											},
											depositNumber : {
												group: '.group',
												validators : {
													notEmpty : {
														message : 'please input term deposit number!'
													}
												},
												regexp : {
													regexp : '^[0-9]{5}$',
													message : 'Please input 12 numbers!'
												}
											},
											accountNumber : {
												group: '.group',
												validators : {
													notEmpty : {
														message : 'please input credit account number!'
													},

													regexp : {
														regexp : '^[0-9]{12}$',
														message : 'Please input 12 numbers!'
													}
												
												 }
											},
											depositAmount : {
												group: '.group',
												validators : {
													notEmpty : {
														message : 'please input amount!'
													},
													regexp : {
														regexp : '^[0-9]+(\\.[0-9]+)?$',
														message : 'Please input number!'
													}
												
												 }
											}
										}
									}).on('success.form.bv', function(e) {
								e.preventDefault();

								var $form = $(e.target);
								validator = $form.data('bootstrapValidator');
								if (validator) {
									drawDown(e.target);
								}

							});
				});
function drawDown(e) {
	$('#tdForm').find('.alert-success').hide();
	$('#tdForm').find('.alert-warning').hide();
	var transAccountNum = $("#transAccountNum").val();
	var ccy = $("#ccy").val();
	var depositAmount = $("#depositAmount").val();
	var depositNumber = $("#depositNumber").val();
	var accountNumber = $("#accountNumber").val();

	var data = {
		'transAccountNum' : transAccountNum,
		'ccy' : ccy,
		'depositAmount' : depositAmount,
		'depositNumber' : depositNumber,
		'accountNumber' : accountNumber,
		'operationCode' : 'TD'
	};
	$.ajax({
		url : contextPath+"/service/termDeposit/termDepositDepatcher",
		type : "post",
		contentType : "application/json",
		dataType : "json",
		data : JSON.stringify(data),
		success : function(response) {
			if (response.result == 00000) {
				$('#tdForm').find('.alert-success').html('Term deposit drawdown  successfully !').show();
			} else {
				if (response.errorCode[0] == "10021")
				{
					location.href=contextPath+"/login.html";
				}
				$('#tdForm').find('.alert-warning').html('Term deposit drawdown error ! '+$.errorHandler.prop(response.errorCode[0])).show();
			}
			
		}
	});
}
function getPrimaryCode(){
	var data = {
			"item":"Primary_Ccy_Code"		
	}
	$.ajax({
		url : contextPath+"/service/sysconf/getSysConfList",
		type : "post",
		contentType : "application/json",
		dataType : "json",
		data : JSON.stringify(data),
		success : function(response) {
			if (response.result == 00000) {			
				for(var i = 0;i<response.listData.length;i++){
					$("#ccy").append("<option value='"+response.listData[i].value+"'>"+response.listData[i].value+"</option>");
				}
			} 
		}
	})
}
