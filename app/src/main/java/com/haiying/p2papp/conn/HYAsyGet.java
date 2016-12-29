package com.haiying.p2papp.conn;

import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.http.AsyGet;
import com.zcx.helper.http.note.HttpServer;

/**
 * Created by Administrator on 2015/10/14.
 */
@HttpServer("http://vhost119.zihaistar.com/api/")
public class HYAsyGet<T> extends AsyGet<T> {

    public static String TOAST = "网络连接异常";

    public HYAsyGet(AsyCallBack<T> asyCallBack) {
        super(asyCallBack);
    }
}
