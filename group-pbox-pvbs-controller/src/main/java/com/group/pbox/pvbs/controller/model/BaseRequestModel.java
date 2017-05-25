package com.group.pbox.pvbs.controller.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Base request model.
 *
 * @author Fred Mao
 *
 */
public class BaseRequestModel
{
    private String operation;
    private Map<String, String> params = new HashMap<String, String>();

    public String getOperation()
    {
        return operation;
    }

    public void setOperation(String operation)
    {
        this.operation = operation;
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
