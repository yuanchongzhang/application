package com.haiying.p2papp.conn;

import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.http.AsyPost;
import com.zcx.helper.http.note.HttpServer;

/**
 * Created by Administrator on 4/28/2016.
 */
@HttpServer("http://vhost119.zihaistar.com/api/")
public class HYAsyPost<T>  extends AsyPost<T> {

    public static String TOAST = "网络连接异常";

    public HYAsyPost(AsyCallBack<T> asyCallBack) {
        super(asyCallBack);
    }
}
