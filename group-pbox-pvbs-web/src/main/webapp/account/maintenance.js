/**
 * Enquiry acct master info
 */
(function(){
	$('#enquiryForm').bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			realAcctNum : {
//					group: '.group',
				validators : {
					notEmpty : {
						message : 'please input account number!'
					},

					regexp : {
						regexp : '^([0-9]{12})$|^([0-9]{5})$',
						message : 'Please input five or twelve number!'
					}
				
				}
			},
		}
	}).on('success.form.bv', function(e) {
		// Prevent submit form
		e.preventDefault();
	
		var $form = $(e.target);
		validator = $form.data('bootstrapValidator');
		if (validator) {
			enquiryInfo(e.target,'1');
		}

	});
	
	//存储enquiry info返回的数据
	var listData=[];
	function enquiryInfo(e,currentPage){
		var realAcctNum = $("#realAcctNum").val();
		var json = {'realAccountNumber' : realAcctNum, 'operationCode' : 'B','params':{'pageRecorders':pageRecorders,'currentPage':currentPage}};
		$.ajax({
			url : contextPath+"/service/acct/acctMaintenance",
			type : "post",
			contentType: "application/json",
			dataType : "json",
			data : JSON.stringify(json),
			success : function(response) {
				if (response.result==00000) {
					$("#pageInfo").empty();
					listData = response.listData;
					var list = response.listData;
					console.log(listData);
					var len = list.length,html="";
					for(var i = 0;i<len;i++){
						html+="<tr><td>"+list[i].customerName+"</td>"
							+"<td>"+list[i].customerId+"</td>"
							+"<td>"+list[i].dateOfBirth+"</td>"
							+"<td>"+list[i].address+"</td>"
							+"<td>"+list[i].contactAddress+"</td>"
							+"<td>"+list[i].contactNumber+"</td>"
							+"<td>"+list[i].wechatId+"</td>"
							+"<td><a class='btn btn-info' href='#'><i class='glyphicon glyphicon-edit icon-white'></i>Edit</a>&nbsp;&nbsp;"
							+"<a class='btn btn-danger' href='#'><i class='glyphicon glyphicon-trash icon-white'></i>Delete</a></td></tr>";
					}
					$(".infoCon").html(html);
					$(".acctNotExist").hide();
					$(".acctInfo").fadeIn();
				}
				else {
					$(".acctNotExist").fadeIn();
					$(".acctInfo").hide();
					$(".acctNotExist").html('Invalid real account number!');
				}
				handlePageInfo(response.params);
				
			}
		});
	}
	
})();
function handlePageInfo(params){
	var currentPage = new Number(params.currentPage);
	var totalPage = new Number(params.totalPages);
	$("#pageInfo").append("<li><a href=\"#\"  onclick=\"enquiryInfo(,'1')\">First Page</a></li>");
	if(currentPage>1){
		$("#pageInfo").append("<li><a href=\"#\"  onclick=\"enquiryInfo(,'"+(currentPage-1)+"')\">Previous Page</a></li>" );
	}else{
		$("#pageInfo").append("<li><a href=\"#\"  onclick=\"enquiryInfo(,'1')\">Previous Page</a></li>");
	}
	if(totalPage>currentPage){
		$("#pageInfo").append("<li><a href=\"#\"  onclick=\"enquiryInfo(,'"+(currentPage+1)+"')\">Next Page</a></li>");
	}else{
		$("#pageInfo").append("<li><a href=\"#\"  onclick=\"enquiryInfo(,'"+(totalPage)+"')\">Next Page</a></li>");
	}
	$("#pageInfo").append("<li><a href=\"#\"  onclick=\"enquiryInfo(,'"+totalPage+"')\">Last Page</a></li>");
}