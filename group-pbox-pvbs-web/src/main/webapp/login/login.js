$(document).ready(function() {
    $('#loginForm').bootstrapValidator({
		message: 'This value is not valid',

        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	userId: {
				validators: {
                    notEmpty: {
                        message: 'Please input username!'
                    },

                    regexp: {
                        regexp: /^([\u4E00-\u9FA5]|\w)*$/,
                        message: 'Please do not include special characters!'
                    },
                    stringLength: {
                        min: 1,
                        max: 20,
                        message: 'Please input username between one and twenty!'
                    },
/*                     remote: {
                        url: paths+'/service/employee/checkErExists',
                        message: '用戶名不存在',
                        delay :  2000,//per 2s send a request
                        type: 'POST'
                    }
 */                }
            },

            password: {
                validators: {
                    notEmpty: {
                        message: 'Please input password!'
                    },
                    stringLength: {
                        min: 1,
                        max: 15,
                        message: 'Please input password between one and fifteen!'
                    },
                }
            }
        }
    }) .on('success.form.bv', function(e) {
        // Prevent submit form
        e.preventDefault();

        var $form     = $(e.target);
            validator = $form.data('bootstrapValidator');
        if(validator){
        	login(e.target);
        }

    }) ;
});
function login(e) {
	var userId = $("#userId").val();
	var password = $("#password").val();
	var json = {'userId' : userId, 'password' : password};
	$.ajax({
			url : "/vbs/service/user/loginCheck",
			type : "post",
			contentType: "application/json",
			dataType : "json",
			data : JSON.stringify(json),
			success : function(response) {
				if (response.result==00000) {
					 window.location.href = getContextPath()+"index.html";
				}
				else {
					//$("#loginAlert").width(500);
					$("#loginAlert").html('Username or Password Fail!');
					$("#loginAlert").css({color:"red"});
				}
			}
		});
}
function getContextPath(){ 
	var pathName = document.location.pathname; 
//	var index = pathName.substr(1).indexOf("/"); 
//	var result = pathName.substr(0,index+1); 
	return pathName; 
	} 
