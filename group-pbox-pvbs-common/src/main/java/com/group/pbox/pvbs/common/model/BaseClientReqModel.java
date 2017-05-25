package com.group.pbox.pvbs.common.model;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseClientReqModel
{
    private String operationCode;

    private Map<String, String> params = new HashMap<String, String>();

    public String getOperationCode()
    {
        return operationCode;
    }

    public void setOperationCode(String operationCode)
    {
        this.operationCode = operationCode;
    }

    public Map<String, String> getParams()
    {
        return params;
    }

    public void setParams(Map<String, String> params)
    {
        this.params = params;
    }

}
