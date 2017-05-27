$(document).ready(function() {
    $('#buyForm').bootstrapValidator({
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
                        message: 'please input account number'
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
            	buy(e.target);
            }

        });
});
function buy(e){
	$('#buyForm').find('.alert-success').hide();
	$('#buyForm').find('.alert-warning').hide();

	var accountNumber = $("#accountNumber").val();
	var amount = $("#amount").val();
	var currency = $("#currency").val();
	var json = {'operationCode':'B','acctNumber': accountNumber, 'changeAmount' :amount,'currencyCode' : currency,'userId':'0001'};

	$.ajax({
			url : contextPath+"/service/ccyExRate/getCcyExRate",
			type : "post",
			contentType: "application/json",
			dataType : "json",
			data : JSON.stringify(json),
			success : function(response) {
				if (response.result=="00000") {
					//$('#buyForm').find('.alert-success').html('buy success.').show();
					$('#buyForm').find('.alert-success').html('You have bought successfully').show();
				}
				else {
					//$('#buyForm').find('.alert-warning').html('buy error.').show();
					$('#buyForm').find('.alert-warning').html('add user error.').show();
					//$("#showResult").text(response.errorCode);
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
