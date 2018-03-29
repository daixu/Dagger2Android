package com.daixu.dagger.demo.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.daixu.dagger.demo.R;
import com.daixu.dagger.demo.bean.OrderCommodityBean;
import com.daixu.dagger.demo.utils.NumberFormatUtils;

import java.util.List;

/**
 * Created by 32422 on 2018/3/27.
 */

public class MyOrderAdapter extends BaseQuickAdapter<OrderCommodityBean, BaseViewHolder> {

    public MyOrderAdapter(int layoutResId, @Nullable List<OrderCommodityBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final OrderCommodityBean item) {
        helper.setText(R.id.tv_order_number, "订单号：" + item.orderNo);
        helper.setText(R.id.tv_order_sum, mContext.getString(R.string.commodity_sum, item.productCount.intValue()));

        String deposit = NumberFormatUtils.getInstance().format(item.cashFee.doubleValue());
        helper.setText(R.id.tv_deposit, mContext.getString(R.string.commodity_deposit, deposit));
    }
}
