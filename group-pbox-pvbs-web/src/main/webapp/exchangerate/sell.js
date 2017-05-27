$(document).ready(function() {
    $('#sellForm').bootstrapValidator({
		message: 'This value is not valid',

        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	accountNumber: {
        		group: '.group',
        		validators: {
                    notEmpty: {
                        message: 'please input account numeber'
                    },
                    regexp: {
                        regexp: /^[0-9]{12}$/,
                        message: 'the length should be 12.'
                    },
                  
                }
            },
            
            currency: {
        		group: '.group',
        		validators: {
                    notEmpty: {
                        message: 'please choose a currency'
                    }
                }
            },

  
            amount: {
                group: '.group',
				validators: {
                    notEmpty: {
                        message: 'please input amount.'
                    },
                    regexp: {
                        regexp: /^(\+\d+|\d+|\d+\.\d+|\+\d+\.\d+)$/,
                        message: 'exchange amount should be a number.'
                    }
                }
            }

        }
    }).on('success.form.bv', function(e) {
            // Prevent submit form
            e.preventDefault();
            var $form     = $(e.target);
                validator = $form.data('bootstrapValidator');
            if(validator){
            	sell(e.target);
            }

        });
});
function sell(e){
	$('#sellForm').find('.alert-success').hide();
	$('#sellForm').find('.alert-warning').hide();
	var accountNumber = $("#accountNumber").val();
	var amount = $("#amount").val();
	var currency = $("#currency").val();
	var json = {'operationCode':'S','accountNumber' : accountNumber, 'amount' : amount,'currency' : currency,'userId':'0001'};
	$.ajax({
			url : contextPath+"/service/currencyExchange/exchange",
		//url : "http://172.168.0.139:8080/vbs/"+"service/currencyExchange/exchange",
			type : "post",
			contentType: "application/json",
			dataType : "json",
			data : JSON.stringify(json),
			success : function(response) {
				
				if (response.result=="00000") {
					//$('#sellForm').find('.alert-success').html('sell success.').show();
					//$("#showRate").text("1 "+ response.listData[0].currencyCode + " = " + response.listData[0].exchangeRate + " RMB");
					//$("#showResult").text("success");
					alert("You have sold successfully");
				}
				else {
					//$('#sellForm').find('.alert-warning').html('sell error.').show();
					//$("#showResult").text(response.errorCode);
					alert("Sorry,the operation was failed");
				}
			}
		});
}

function addOption(){
	var currency = {
			'operationCode': 'FAC'
		};
	$.ajax({
		url : contextPath+"/service/ccyExRate/getCcyExRate",
		//url : "http://172.168.0.139:8080/vbs"+"/service/ccyExRate/getCcyExRate",
		type : "post",
		contentType : "application/json",
		dataType : "json",
		data : JSON.stringify(currency),
		success : function(response) {
			if (response.result == 00000) {			
				for(var i = 0;i<response.listData.length;i++){
					$("#currency").append("<option value='"+response.listData[i].currencyCode+"'>"+response.listData[i].currencyCode+"</option>");
				}
			} 
		}
	});
}