package com.group.pbox.pvbs.controller.user;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.group.pbox.pvbs.clientmodel.user.UserReqModel;

import com.group.pbox.pvbs.clientmodel.user.UserRespModel;
import com.group.pbox.pvbs.controller.model.BaseResponseModel;
import com.group.pbox.pvbs.model.user.User;
import com.group.pbox.pvbs.user.IUserService;
import com.group.pbox.pvbs.util.ErrorCode;
import com.group.pbox.pvbs.util.OperationCode;


@Controller
@RequestMapping("/user")
public class UserController {
	@Resource
	IUserService userService;

	@RequestMapping(value = "/loginCheck", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public Object loginCheck(final HttpServletRequest request, final HttpServletResponse response,
			@RequestBody User user) {

		BaseResponseModel resp = new BaseResponseModel();

		int result = userService.accountValid(user);

		if (result > 0) {
			resp.setResult(ErrorCode.RESPONSE_SUCCESS);
		} else {
			resp.setResult(ErrorCode.RESPONSE_ERROR);
		}
		return resp;
	}

	@RequestMapping(value = "/userMaintain", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public Object userMaintain(final HttpServletRequest request, final HttpServletResponse response,
			@RequestBody UserReqModel userRequest) throws Exception {
		UserRespModel userRespModel = new UserRespModel();

		switch (userRequest.getOperation()) {
		case OperationCode.ACCT_CREATION:
			userRespModel = addUser(userRequest);
			break;
		case OperationCode.ACCT_MAINT:
			userRespModel = updateUser(userRequest);
			break;
/*		case OperationCode.ACCT_DETL:
			retutn deleteUser(userRequest);
*/		case OperationCode.FETCH_BY_USER_ID:
			userRespModel = fetchUserDetlByUserId(userRequest);
		}
		return userRespModel;
	}

	private UserRespModel addUser(@RequestBody UserReqModel userRequest) {
		return userService.addUser(userRequest);
	}

	private UserRespModel updateUser(@RequestBody UserReqModel userRequest) {
		return userService.updateUser(userRequest);
	}

/*	private Object deleteUser(@RequestBody UserReqModel userRequest, UserRespModel resp) {
		String userId = userRequest.getUserId();

		User user = userService.fetchUserByUserId(userId);
		user.setUserStatus("Inactive");

		int result = userService.updateUser(user);

		if (result > 0) {
			resp.setResult(ErrorCode.RESPONSE_SUCCESS);
		} else {
			resp.setResult(ErrorCode.RESPONSE_ERROR);
		}
		return resp;
	}
*/
	private UserRespModel fetchUserDetlByUserId(@RequestBody UserReqModel userRequest) {
		return userService.fetchUserByUserId(userRequest);
	}
}
