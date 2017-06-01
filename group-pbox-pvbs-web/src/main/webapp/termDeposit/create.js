$(document).ready(function() {
	getPrimaryCode();
	getTermPeriod();
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
											accountNumber : {
												group: '.group',
												validators : {
													notEmpty : {
														message : 'please input debit amount!'
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
														message : 'please input account number'
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
									create(e.target);
								}

							});
				});
function create(e) {
	$('#tdForm').find('.alert-success').hide();
	$('#tdForm').find('.alert-warning').hide();
	var transAccountNum = $("#transAccountNum").val();
	var ccy = $("#ccy").val();
	var depositAmount = $("#depositAmount").val();
	var termPeriod = $("#termPeriod").val();
	var accountNumber = $("#accountNumber").val();

	var data = {
		'transAccountNum' : transAccountNum,
		'ccy' : ccy,
		'depositAmount' : depositAmount,
		'termPeriod' : termPeriod,
		'accountNumber' : accountNumber,
		'operationCode' : 'TC'
	};
	$.ajax({
		url : contextPath+"/service/termDeposit/termDepositDepatcher",
		type : "post",
		contentType : "application/json",
		dataType : "json",
		data : JSON.stringify(data),
		success : function(response) {
			if (response.result == 00000) {
				$('#tdForm').find('.alert-success').html('Term deposit created successfully !').show();
			} else {
				if (response.errorCode[0] == "10021")
				{
					location.href=contextPath+"/login.html";
				}
				$('#tdForm').find('.alert-warning').html('Term deposit created error ! '+$.errorHandler.prop(response.errorCode[0])).show();
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
function getTermPeriod(){
	var data = {
			"operationCode":"FTR"		
	}
	$.ajax({
		url : contextPath+"/service/termDeposit/termDepositRate",
		type : "post",
		contentType : "application/json",
		dataType : "json",
		data : JSON.stringify(data),
		success : function(response) {
			if (response.result == 00000) {			
				for(var i = 0;i<response.listData.length;i++){
					$("#termPeriod").append("<option value='"+response.listData[i].termDeposiPeriod+"'>"+response.listData[i].termDeposiPeriod+" Months - "+response.listData[i].termDeposiInterestRate+""+"</option>");
				}
			} 
		}
	})
}