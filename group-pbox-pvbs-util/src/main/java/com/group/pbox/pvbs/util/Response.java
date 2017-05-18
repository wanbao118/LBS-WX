package com.group.pbox.pvbs.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Response
{
    /**
     * 200 success 203 error
     */
    private int result;
    private List<String> errorCode;
    private Map<String, String> params = new HashMap<String, String>();
    private List<Object> listData;

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

    public int getResult()
    {
        return result;
    }

    public void setResult(int result)
    {
        this.result = result;
    }
}
