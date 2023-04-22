package com.example.graduates.http;

import android.view.View;

import java.net.URL;

//网络请求模式接口
public interface RequestInterface {

    /**
     * 同步get
     */

    public abstract void get_syn(View view, URL url);

    /**
     * 异步get
     */

    public abstract void get_asy(View view, URL url);

    /**
     * 同步post
     */

    public abstract void post_syn(View view,URL url);

    /**
     * 异步post
     */

    public abstract void post_asy(View view,URL url);
}
