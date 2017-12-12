package com.daixu.dagger.demo.bean;

import java.util.List;

public class BannerResp extends BaseResp {

    public List<DataBean> data;

    public static class DataBean {
        public String id;
        public String areaId;
        public String name;
        public String image;
        public String jumpUrl;
        public Object goodsId;
        public int serialnumber;
        public int type;
        public Object status;
        public Object description;
        public long createTime;
        public long updateTime;
        public String inputer;
        public AreaBean area;
        public GoodsBean goods;

        public static class AreaBean {
            public String id;
            public String name;
        }

        public static class GoodsBean {
            public String id;
            public String name;
            public String areaId;
        }
    }
}