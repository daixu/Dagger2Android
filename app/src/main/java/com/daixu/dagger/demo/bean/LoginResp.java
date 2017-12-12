package com.daixu.dagger.demo.bean;

/**
 * Created by 32422 on 2017/10/26.
 */

public class LoginResp {
    public int result = -1;
    public String msg;

    public boolean isOk() {
        return result == 0;
    }

    public DataBean data;

    public static class DataBean {

        public String id;
        public String userId;
        public String userType;
        public long createTime;
        public long lastLogintime;
        public int terminalType;
        public String token;
        public String nickname;
        public String cityId;
        public String sex;
        public float freeCash;
        public String merchantName;
        public String icon;
    }
}
