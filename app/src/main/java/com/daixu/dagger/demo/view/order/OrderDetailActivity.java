package com.daixu.dagger.demo.view.order;

import android.os.Bundle;
import android.view.View;

import com.daixu.dagger.demo.R;
import com.daixu.dagger.demo.bean.RxBusEvent;
import com.daixu.dagger.demo.utils.RxBus;
import com.daixu.dagger.demo.view.BaseActivity;

import timber.log.Timber;

public class OrderDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        final int position = getIntent().getIntExtra("position", -1);
        Timber.tag("OrderDetailActivity").e("position=" + position);
        findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxBusEvent event = new RxBusEvent();
                event.type = RxBusEvent.RxBusType.REFRESH;
                event.position = position;
                RxBus.get().post(event);
                finish();
            }
        });
    }
}
