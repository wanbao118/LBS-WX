$(document).ready(function() {
    $('#userAddForm').bootstrapValidator({
		message: 'This value is not valid',

        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	userId: {
        		group: '.group',
        		validators: {
                    notEmpty: {
                        message: 'please input user id'
                    },
                    regexp: {
                        regexp: /^[0-9]{4}$/,
                        message: 'the length should be 4.'
                    },
                    /* regexp: {
                        regexp: /^([\u4E00-\u9FA5]|\w)*$/,
                        message: '请勿包含特殊字符'
                    }, */
                    /* stringLength: {
                        min: 1,
                        max: 12,
                        message: '请输入长度在1到12位之间的ER号'
                    }, 
                    remote: {//ajax  server result:{"valid",true or false}   json
                        url: path+'/service/employee/checkErExists',
                        message: 'ER号已存在',
                        delay :  2000,//per 2s send a request
                        type: 'POST'
                    }*/
                }
            },

            userName: {
            	group: '.group',
            	validators: {
                    notEmpty: {
                        message: 'please input user name.'
                    },
                    stringLength: {
                        max: 20,
                        message: 'the length should be 20.'
                    }
                }
            },

            userPosition: {
                group: '.group',
				validators: {
                    notEmpty: {
                        message: 'please choose user position.'
                    }
                }
            },
            transactionLimit: {
                group: '.group',
				validators: {
                    notEmpty: {
                        message: 'please input transaction limit.'
                    },
                    regexp: {
                        regexp: /^(\+\d+|\d+|\d+\.\d+|\+\d+\.\d+)$/,
                        message: 'transaction limit should be a number.'
                    }
                }
            },
            termDepositeLimit: {
                group: '.group',
				validators: {
                    notEmpty: {
                        message: 'please input termDeposite limit.'
                    },
                    regexp: {
                        regexp: /^(\+\d+|\d+|\d+\.\d+|\+\d+\.\d+)$/,
                        message: 'termDeposite limit should be a number.'
                    }
                }
            },
            exchangeRateLimit: {
                group: '.group',
				validators: {
                    notEmpty: {
                        message: 'please input exchange rate limit.'
                    },
                    regexp: {
                        regexp: /^(\+\d+|\d+|\d+\.\d+|\+\d+\.\d+)$/,
                        message: 'exchange rate limit should be a number.'
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
            	addUser(e.target);
            }

        });
});
function addUser(e){
	$('#userAddForm').find('.alert-success').hide();
	$('#userAddForm').find('.alert-warning').hide();
	var userId = $("#userId").val();
	var userName = $("#userName").val();
	var userPosition = $("#userPosition").val();
	var transactionLimit = $("#transactionLimit").val();
	var termDepositeLimit = $("#termDepositeLimit").val();
	var exchangeRateLimit = $("#exchangeRateLimit").val();
	var json = {'operation':'A','userId' : userId, 'userName' : userName,'userPosition' : userPosition,
			'transactionLimit' : transactionLimit,'termDepositeLimit' : termDepositeLimit,'exchangeRateLimit' : exchangeRateLimit};
	$.ajax({
			url : contextPath+"/service/user/userMaintain",
			type : "post",
			contentType: "application/json",
			dataType : "json",
			data : JSON.stringify(json),
			success : function(response) {
				if (response.result=="00000") {
					$('#userAddForm').find('.alert-success').html('add user success.').show();
				}
				else {
					if (response.errorCode[0] == "10021")
					{
						location.href=contextPath+"/login.html";
					}
					$('#userAddForm').find('.alert').html('add user error.'+$.errorHandler.prop(response.errorCode[0])).show();
				}
			}
		});
}