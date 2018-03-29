package com.daixu.dagger.demo.bean;

import java.util.List;

/**
 * Created by 32422 on 2017/11/20.
 */

public class GetUserOrdersResp {

    public int pageNum;
    public int pageSize;
    public int size;
    public int startRow;
    public int endRow;
    public int total;
    public int pages;
    public int prePage;
    public int nextPage;
    public boolean isFirstPage;
    public boolean isLastPage;
    public boolean hasPreviousPage;
    public boolean hasNextPage;
    public int navigatePages;
    public int navigateFirstPage;
    public int navigateLastPage;
    public int firstPage;
    public int lastPage;
    public List<OrderCommodityBean> list;
    public List<Integer> navigatepageNums;
}
