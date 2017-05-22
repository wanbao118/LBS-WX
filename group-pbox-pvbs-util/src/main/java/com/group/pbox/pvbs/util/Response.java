package com.group.pbox.pvbs.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Response
{

    private String result = ErrorCode.RESPONSE_SUCCESS;
    private List<String> errorCode = new ArrayList<String>();
    private Map<String, String> params = new HashMap<String, String>();
    private List listData;

    public List<String> getErrorCode()
    {
        return errorCode;
    }

    public void setErrorCode(List<String> errorCode)
    {
        this.errorCode = errorCode;
    }

    public Map<String, String> getParams()
    {
        return params;
    }

    public void setParams(Map<String, String> params)
    {
        this.params = params;
    }

    public List<Object> getListData()
    {
        return listData;
    }

    public void setListData(List<Object> listData)
    {
        this.listData = listData;
    }

    public String getResult()
    {
        return result;
    }

    public void setResult(String result)
    {
        this.result = result;
    }

}
