var primaryCcyCode;

function enqueryExRate() {
	var currExRateReq = {
		'operationCode': 'FAC'
	};
	$.ajax({
		url : contextPath+"/service/ccyExRate/getCcyExRate",
		type : "post",
		contentType : "application/json",
		dataType : "json",
		data : JSON.stringify(currExRateReq),
		success : function(response) {
			if (response.result == 00000) {			
				for(var i = 0;i<response.listData.length;i++){
					$("#selectCurrency").append("<option value='"+response.listData[i].currencyCode+"'>"+response.listData[i].currencyCode+"</option>");
				}
				$("#selectCurrency option[value=" + primaryCcyCode + "]").remove();
			} else {
				$('#rateForm').find('.alert-warning').html($.errorHandler.prop(response.errorCode[0])).show();
			}
		}
	});
}

$(function(){
	getPrimaryCcyCode();
	enqueryExRate();
});

function getPrimaryCcyCode() {
	var sysConfReq = {
		'item' : 'Primary_Ccy_Code'
	}

	$.ajax({
		url : contextPath+"/service/sysconf/getSysConfList",
		type : "post",
		contentType : "application/json",
		dataType : "json",
		data : JSON.stringify(sysConfReq),
		success : function(response) {
			if (response.result == 00000) {
				primaryCcyCode = response.listData[0].value;
			} else {
				
			}
		}
	});

}
function getExRate(){
	var currencyCode = $('#selectCurrency option:selected') .val();
	var currExRateReq = {
			'operationCode': 'FBC',
			'currencyCode': currencyCode
		};
	$.ajax({
		url : contextPath+"/service/ccyExRate/getCcyExRate",
		type : "post",
		contentType : "application/json",
		dataType : "json",
		data : JSON.stringify(currExRateReq),
		success : function(response) {
			if (response.result == 00000) {
					$("#showRate").text("1 "+ primaryCcyCode + " = " + response.listData[0].exchangeRate + " " + response.listData[0].currencyCode);
			} else {
					
			}
		}
	});
}