package com.group.pbox.pvbs.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.group.pbox.pvbs.clientmodel.user.UserReqModel;
import com.group.pbox.pvbs.clientmodel.user.UserRespData;
import com.group.pbox.pvbs.clientmodel.user.UserRespModel;
import com.group.pbox.pvbs.model.user.User;
import com.group.pbox.pvbs.persist.user.UserMapper;
import com.group.pbox.pvbs.util.ErrorCode;
import com.group.pbox.pvbs.util.Utils;

@Service
public class UserServiceImpl implements IUserService
{
    @Resource
    UserMapper userMapper;

    private static final String PAGE_RECORDS = "pageRecorders";

    private static final String PAGE_CURRENT = "currentPage";

    public UserRespModel accountValid(UserReqModel userReqModel)
    {
        UserRespModel userResponseModel = new UserRespModel();

        User user = new User();
        user.setUserId(userReqModel.getUserId());
        user.setUserPassword(userReqModel.getUserPassword());
        User returnUser = userMapper.accountValid(user);
        if (returnUser != null)
        {
            List<UserRespData> list = new ArrayList<UserRespData>();
            UserRespData userRespData = new UserRespData();
            BeanUtils.copyProperties(returnUser, userRespData);
            list.add(userRespData);
            userResponseModel.setResult(ErrorCode.RESPONSE_SUCCESS);
            userResponseModel.setListData(list);
        }
        else
        {
            userResponseModel.setResult(ErrorCode.RESPONSE_ERROR);
            List<String> errorList = new ArrayList<String>();
            errorList.add(ErrorCode.RECORD_NOT_FOUND);
            userResponseModel.setErrorCode(errorList);
        }

        return userResponseModel;
    }

    public UserRespModel addUser(UserReqModel userReqModel)
    {
        UserRespModel userResponseModel = new UserRespModel();
        User user = new User();
        BeanUtils.copyProperties(userReqModel, user);
        user.setId(Utils.getUUID());
        user.setUserPassword("1");
        user.setUserStatus("Active");

        if (userMapper.addUser(user))
        {
            userResponseModel.setResult(ErrorCode.RESPONSE_SUCCESS);
        }
        else
        {
            userResponseModel.setResult(ErrorCode.RESPONSE_ERROR);
        }
        return userResponseModel;
    }

    public UserRespModel updateUser(UserReqModel userReqModel)
    {
        // TODO Auto-generated method stub
        UserRespModel userResponseModel = new UserRespModel();
        User user = userMapper.fetchUserDetlByUserId(userReqModel.getUserId());

        if (user != null)
        {
            /* update user */
            user.setUserName(userReqModel.getUserName());
            int recordNum = userMapper.updateUser(user);

            if (recordNum > 0)
            {
                userResponseModel.setResult(ErrorCode.RESPONSE_SUCCESS);
            }
            else
            {
                userResponseModel.setResult(ErrorCode.RESPONSE_ERROR);
            }
        }
        else
        {
            List<String> errorList = new ArrayList<String>();
            errorList.add(ErrorCode.RECORD_NOT_FOUND);
            userResponseModel.setErrorCode(errorList);
        }

        return userResponseModel;
    }

    public User queryUserDetail(String userId)
    {
        return userMapper.fetchUserDetlByUserId(userId);
    }

    public List<User> fetchAllUser()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public int deleteUser(String userId)
    {
        // TODO Auto-generated method stub
        return 0;
    }

    public UserRespModel fetchUserByUserId(UserReqModel userReqModel)
    {
        // TODO Auto-generated method stub
        UserRespModel userResponseModel = new UserRespModel();
        User user = userMapper.fetchUserDetlByUserId(userReqModel.getUserId());
        UserRespData userRespData = new UserRespData();
        List<UserRespData> userDataList = new ArrayList<UserRespData>();

        if (user != null)
        {
            BeanUtils.copyProperties(user, userRespData);

            userDataList.add(userRespData);

            userResponseModel.setResult(ErrorCode.RESPONSE_SUCCESS);
            userResponseModel.setListData(userDataList);
        }
        else
        {
            userResponseModel.setResult(ErrorCode.RESPONSE_ERROR);
            List<String> errorList = new ArrayList<String>();
            errorList.add(ErrorCode.USER_NOT_FOUND);
            userResponseModel.setErrorCode(errorList);
        }

        return userResponseModel;
    }

    @SuppressWarnings("unchecked")
    public UserRespModel queryUserByParams(UserReqModel userReqModel) throws Exception
    {
        UserRespModel userRespModel = new UserRespModel();
        User user = new User();
        BeanUtils.copyProperties(userReqModel, user);

        // get spereate page infor.
        Map<String, String> params = userReqModel.getParams();
        String currentPage = params.get(PAGE_CURRENT);
        String pageRecords = params.get(PAGE_RECORDS);

        // set query page infor.
        if (StringUtils.isNotBlank(currentPage) && StringUtils.isNotBlank(currentPage))
        {
            user.setQueryPageParams(Integer.parseInt(currentPage), Integer.parseInt(pageRecords));
        }

        List<User> listUser = userMapper.queryUserByParams(user);
        List<UserRespData> listUserRespData = new ArrayList<UserRespData>();
        for (User tmpUser : listUser)
        {
            UserRespData targetData = new UserRespData();
            BeanUtils.copyProperties(tmpUser, targetData);
            listUserRespData.add(targetData);
        }
        userRespModel.setListData(listUserRespData);
        List<User> listCount = userMapper.queryUserByParamsCount(user);
        Map<String, String> respParams = user.setRespPageParams(listUser, listCount);
        userRespModel.setParams(respParams);
        userRespModel.setResult(ErrorCode.RESPONSE_SUCCESS);
        return userRespModel;
    }

}
