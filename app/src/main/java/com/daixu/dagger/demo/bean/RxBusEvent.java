package com.daixu.dagger.demo.bean;

public class RxBusEvent {
    public String type;
    public int position;

    public interface RxBusType {
        String REFRESH = "refresh";
        String FINISH = "finish";
    }
}
