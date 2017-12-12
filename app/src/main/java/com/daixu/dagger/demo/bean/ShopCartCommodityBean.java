package com.daixu.dagger.demo.bean;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by 32422 on 2017/10/31.
 */

public class ShopCartCommodityBean {

    public String userId;
    public String goodsId;
    public String goodsName;
    public String goodsPictureUrl;
    public BigDecimal goodsPrice;
    public BigDecimal goodsAmount;
    public int collect;
    public BigDecimal originalprice;
    public String unit;
    public String categoryId;
    public boolean isClick = true;
    public String customerRemarks;
    public int repertoryIsFull;
    public int status = 1;
    public long stock;
    public long num;
    public List<ShopCartDepositObj> depositObj;
}
