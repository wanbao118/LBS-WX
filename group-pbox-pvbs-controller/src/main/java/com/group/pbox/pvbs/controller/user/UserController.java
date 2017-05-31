package com.group.pbox.pvbs.controller.user;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.group.pbox.pvbs.clientmodel.user.UserReqModel;
import com.group.pbox.pvbs.clientmodel.user.UserRespData;
import com.group.pbox.pvbs.clientmodel.user.UserRespModel;
import com.group.pbox.pvbs.model.user.User;
import com.group.pbox.pvbs.user.IUserService;
import com.group.pbox.pvbs.util.ErrorCode;
import com.group.pbox.pvbs.util.OperationCode;

@Controller
@RequestMapping("/user")
public class UserController {
	private static final Logger sysLogger = Logger.getLogger("customer");

	@Resource
	IUserService userService;

	@RequestMapping(value = "/loginCheck", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public Object loginCheck(final HttpServletRequest request, final HttpServletResponse response,
			@RequestBody UserReqModel userRequest) {

		UserRespModel userRespModel = new UserRespModel();

		try {
			userRespModel = userService.accountValid(userRequest);
			if (userRespModel.getListData().size() == 1) {
				UserRespData userRespData = userRespModel.getListData().get(0);
				request.getSession().setAttribute("userId", userRespData.getUserId());
			}
		} catch (Exception e) {
			userRespModel.setResult(ErrorCode.RESPONSE_ERROR);
			List<String> errorList = new ArrayList<String>();
			errorList.add(ErrorCode.SYSTEM_OPERATION_ERROR);
			userRespModel.setErrorCode(errorList);

		}

		return userRespModel;
	}

	@RequestMapping(value = "/userMaintain", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public Object userMaintain(final HttpServletRequest request, final HttpServletResponse response,
			@RequestBody UserReqModel userRequest) throws Exception {
		UserRespModel userRespModel = new UserRespModel();
		// String userId = (String) request.getSession().getAttribute("userId");

		switch (userRequest.getOperationCode()) {
		case OperationCode.ACCT_CREATION:
			userRespModel = addUser(userRequest);
			break;
		case OperationCode.FETCH_BY_USER_ID:
			userRespModel = fetchUserDetlByUserId(userRequest);
			break;
		case OperationCode.UPDATE_USER:
			userRespModel = updateUser(userRequest);
			
			
			break;
		case OperationCode.DEL_USER:
			userRespModel = deleteUser(userRequest);
		}
		return userRespModel;
	}

	private UserRespModel addUser(@RequestBody UserReqModel userRequest) {
		return userService.addUser(userRequest);
	}

	private UserRespModel updateUser(@RequestBody UserReqModel userRequest) {
		return userService.updateUser(userRequest);
	}

	private UserRespModel deleteUser(@RequestBody UserReqModel userRequest) {
		userRequest.setUserStatus("Inactive");
		return userService.updateUser(userRequest);
	}

	private UserRespModel fetchUserDetlByUserId(@RequestBody UserReqModel userRequest) {
		return userService.fetchUserByUserId(userRequest);
	}

	@RequestMapping("/logout")
	public String logout(final HttpServletRequest request, final HttpServletResponse response) {
		request.getSession().removeAttribute("userId");

		return "loginCheck";
	}
}
