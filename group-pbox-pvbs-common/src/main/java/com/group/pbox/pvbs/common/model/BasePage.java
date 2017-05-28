package com.group.pbox.pvbs.common.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BasePage
{
    private int currentPage = 1;

    private int currentPageRecords = 0;

    private int totalPages = 0;

    private int pageRecorders = 10;

    private int totalRows = 0;

    private int pageStartRow = 0;

    private boolean hasNextPage = false;

    private boolean hasPreviousPage = false;

    public void setQueryPageParams(int currentPage, int pageRecords)
    {
        this.setCurrentPage(currentPage);
        this.setPageRecorders(pageRecords);
        int start = (this.getCurrentPage() - 1) * this.getPageRecorders();
        this.setPageStartRow(start);
    }

    public Map<String, String> setRespPageParams(List listData, List listCount)
    {
        Map<String, String> map = new HashMap<String, String>();
        map.put("currentPage", String.valueOf(this.getCurrentPage()));
        this.setTotalRows(listCount.size());
        map.put("totalRows", String.valueOf(this.getTotalRows()));
        this.setCurrentPageRecords(listData.size());
        map.put("currentPageRecords", String.valueOf(listData.size()));
        if (this.getCurrentPage() > 1)
        {
            this.setHasPreviousPage(true);
            map.put("hasPreviousPage", "true");
        }
        else
        {
            map.put("hasPreviousPage", "false");
        }

        int mins = listCount.size() % this.getPageRecorders();
        if (mins > 0)
        {
            this.setTotalPages(listCount.size() / this.getPageRecorders() + 1);
        }
        else
        {
            this.setTotalPages(listCount.size() / this.getPageRecorders());
        }
        map.put("totalPages", String.valueOf(this.getTotalPages()));
        if (this.getTotalPages() > this.getCurrentPage())
        {
            this.setHasNextPage(true);
            map.put("hasNextPage", "true");
        }
        else
        {
            map.put("hasNextPage", "false");
        }
        return map;
    }

    public int getCurrentPage()
    {
        return currentPage;
    }

    public void setCurrentPage(int currentPage)
    {
        this.currentPage = currentPage;
    }

    public int getTotalPages()
    {
        return totalPages;
    }

    public void setTotalPages(int totalPages)
    {
        this.totalPages = totalPages;
    }

    public int getPageRecorders()
    {
        return pageRecorders;
    }

    public void setPageRecorders(int pageRecorders)
    {
        this.pageRecorders = pageRecorders;
    }

    public int getTotalRows()
    {
        return totalRows;
    }

    public void setTotalRows(int totalRows)
    {
        this.totalRows = totalRows;
    }

    public int getPageStartRow()
    {
        return pageStartRow;
    }

    public void setPageStartRow(int pageStartRow)
    {
        this.pageStartRow = pageStartRow;
    }

    public boolean isHasNextPage()
    {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage)
    {
        this.hasNextPage = hasNextPage;
    }

    public boolean isHasPreviousPage()
    {
        return hasPreviousPage;
    }

    public void setHasPreviousPage(boolean hasPreviousPage)
    {
        this.hasPreviousPage = hasPreviousPage;
    }

    public int getCurrentPageRecords()
    {
        return currentPageRecords;
    }

    public void setCurrentPageRecords(int currentPageRecords)
    {
        this.currentPageRecords = currentPageRecords;
    }

}
