package com.daixu.dagger.demo.bean;

import java.math.BigDecimal;
import java.util.List;

public class OrderCommodityBean {

    public int id;
    public String orderNo;
    public int orderStatus;
    public BigDecimal productCount;
    public BigDecimal productAmountTotal;
    public BigDecimal orderAmountTotal;
    public BigDecimal logisticsFee;
    public int evaluateStatus;
    public int invoiceId;
    public int addressId;
    public int payType;
    public String outTradeNo;
    public String createTime;
    public String payTime;
    public int orderSettlementStatus;
    public String deliveryTime;
    public String userId;
    public String orderSettlementTime;
    public BigDecimal cashPledge;
    public int compensationAmount;
    public int discountMount;
    public int isOneselfTake;
    public String storehouseId;
    public BigDecimal proTotalFee;
    public BigDecimal orderTotalFee;
    public BigDecimal logisticsTotalFee;
    public BigDecimal compensationFee;
    public BigDecimal cashFee;
    public BigDecimal actualPaymentFee;
    public BigDecimal meiPayTotalFee;
    public List<OrderDetailBean> orderDetails;

    public DeliveryAddress deliveryAddress;
}