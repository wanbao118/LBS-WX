package com.group.pbox.pvbs.user;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.group.pbox.pvbs.clientmodel.user.UserReqModel;
import com.group.pbox.pvbs.clientmodel.user.UserRespData;
import com.group.pbox.pvbs.clientmodel.user.UserRespModel;
import com.group.pbox.pvbs.model.user.User;
import com.group.pbox.pvbs.persist.user.UserMapper;
import com.group.pbox.pvbs.util.ErrorCode;
import com.group.pbox.pvbs.util.Utils;

@Service
public class UserServiceImpl implements IUserService {
	@Resource
	UserMapper userMapper;

	public int accountValid(User user) {
		return userMapper.accountValid(user);
	}

	public UserRespModel addUser(UserReqModel userReqModel) {
		UserRespModel userResponseModel = new UserRespModel();
		User user = new User();

		user.setId(Utils.getUUID());
		user.setUserId(userReqModel.getUserId());
		user.setUserName(userReqModel.getUserName());
		user.setUserPosition(userReqModel.getUserPosition());
		user.setTransactionLimit(userReqModel.getTransactionLimit());
		user.setTermDepositeLimit(userReqModel.getTermDepositeLimit());
		user.setExchangeRateLimit(userReqModel.getExchangeRateLimit());
		user.setUserPassword("1");
		user.setUserStatus("Active");

		if (userMapper.addUser(user)) {
			userResponseModel.setResult(ErrorCode.RESPONSE_SUCCESS);
		} else {
			userResponseModel.setResult(ErrorCode.RESPONSE_ERROR);
		}
		return userResponseModel;
	}

	public UserRespModel updateUser(UserReqModel userReqModel) {
		// TODO Auto-generated method stub
		UserRespModel userResponseModel = new UserRespModel();
		User user = userMapper.fetchUserDetlByUserId(userReqModel.getUserId());

		if (user != null) {
			/*update user*/
			user.setUserName(userReqModel.getUserName());
			int recordNum = userMapper.updateUser(user);

			if (recordNum > 0) {
				userResponseModel.setResult(ErrorCode.RESPONSE_SUCCESS);
			}
			else {
				userResponseModel.setResult(ErrorCode.RESPONSE_ERROR);
			}
		}
		else {
			List<String> errorList = new ArrayList<String>();
			errorList.add(ErrorCode.RECORD_NOT_FOUND);
			userResponseModel.setErrorCode(errorList);
		}

		return userResponseModel;
	}

	public User queryUserDetail(String userId) {
		return userMapper.fetchUserDetlByUserId(userId);
	}

	public List<User> fetchAllUser() {
		// TODO Auto-generated method stub
		return null;
	}

	public int deleteUser(String userId) {
		// TODO Auto-generated method stub
		return 0;
	}

	public UserRespModel fetchUserByUserId(UserReqModel userReqModel) {
		// TODO Auto-generated method stub
		UserRespModel userResponseModel = new UserRespModel();
		User user = userMapper.fetchUserDetlByUserId(userReqModel.getUserId());
		UserRespData userRespData = new UserRespData();
		List<UserRespData> userDataList = new ArrayList<UserRespData>();

		if (user != null) {
			userRespData.setId(user.getId());
			userRespData.setUserId(user.getUserId());
			userRespData.setUserName(user.getUserName());
			userRespData.setUserPassword(user.getUserPassword());
			userRespData.setUserPosition(user.getUserPosition());
			userRespData.setExchangeRateLimit(user.getExchangeRateLimit());
			userRespData.setTransactionLimit(user.getTransactionLimit());
			userRespData.setTermDepositeLimit(user.getTermDepositeLimit());
			userRespData.setUserStatus(user.getUserStatus());

			userDataList.add(userRespData);

			userResponseModel.setResult(ErrorCode.RESPONSE_SUCCESS);
			userResponseModel.setListData(userDataList);
		}
		else {
			userResponseModel.setResult(ErrorCode.RESPONSE_ERROR);
		}

		return userResponseModel;
	}

}
