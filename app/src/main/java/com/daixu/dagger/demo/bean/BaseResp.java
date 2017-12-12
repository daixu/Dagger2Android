package com.daixu.dagger.demo.bean;

public class BaseResp {

    public int result = -1;
    public String msg;

    public boolean isOk() {
        return result == 0;
    }

    public boolean isDisabled() {
        return result == 7;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BaseResp{");
        sb.append("result=").append(result);
        sb.append(",msg=").append(msg).append("\'");
        sb.append("}");
        return sb.toString();
    }
}