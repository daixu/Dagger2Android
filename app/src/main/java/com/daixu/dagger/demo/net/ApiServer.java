package com.daixu.dagger.demo.net;

import com.daixu.dagger.demo.bean.BannerResp;
import com.daixu.dagger.demo.bean.GetShopCartResp;
import com.daixu.dagger.demo.bean.GetUserTodoReq;
import com.daixu.dagger.demo.bean.GetUserTodoResp;
import com.daixu.dagger.demo.bean.LoginReq;
import com.daixu.dagger.demo.bean.LoginResp;
import com.daixu.dagger.demo.bean.OnlyUserIdReq;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by 32422 on 2017/12/9.
 */

public interface ApiServer {
    String API_SERVER_URL = "http://api.front.jinyoufarm.com/api/";

    /**
     * 获取广告图
     *
     * @return
     */
    @POST("activity/slideShow/listF")
    Flowable<BannerResp> areaSlideShows();

    /**
     * 商户登录
     *
     * @param req
     * @return
     */
    @POST("user/login")
    Flowable<LoginResp> login(@Body LoginReq req);

    /**
     * 加载用户常用清单
     *
     * @param req
     * @return
     */
    @POST("user/commonUserGreensList")
    Flowable<GetUserTodoResp> commonUserGreensList(@Body GetUserTodoReq req);

    /**
     * 用户购物车列表
     *
     * @param req
     * @return
     */
    @POST("user/userShoppingTrolleyList")
    Flowable<GetShopCartResp> userShoppingTrolleyList(@Body OnlyUserIdReq req);
}
