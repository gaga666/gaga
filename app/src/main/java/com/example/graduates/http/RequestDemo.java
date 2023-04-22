package com.example.graduates.http;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.graduates.R;

import java.io.IOException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RequestDemo implements RequestInterface {

    private final String TAG = "HTTPrequest";

    /**
     * 同步get
     * 子线程内网络请求
     */
    @Override
    public void get_syn(View view, URL url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient httpClient = new OkHttpClient();
                okhttp3.Request request = new okhttp3.Request.Builder().url(url).build();
                Call call = httpClient.newCall(request);
                try {
                    Response response = call.execute();
                    Log.i(TAG,"同步get");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 异步get
     */
    @Override
    public void get_asy(View view, URL url) {
        OkHttpClient httpClient = new OkHttpClient();
        okhttp3.Request request = new okhttp3.Request.Builder().url(url).build();
        Call call = httpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.i(TAG,"get异步");
            }
        });
    }

    /**
     *
     * post请求规定参数放在请求体中
     */

    /**
     * 同步post
     *子线程内网络请求
     */
    @Override
    public void post_syn(View view, URL url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient httpClient = new OkHttpClient();
                FormBody formBody = new FormBody.Builder()
                        .add("1","1")
                        .add("1","1")
                        .build();
                okhttp3.Request request = new okhttp3.Request.Builder().url(url)
                        .post(formBody)
                        .build();
                Call call = httpClient.newCall(request);
                try {
                    Response response = call.execute();
                    Log.i(TAG,"同步post");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 异步post
     */
    @Override
    public void post_asy(View view, URL url) {
        OkHttpClient httpClient = new OkHttpClient();
        FormBody formBody = new FormBody.Builder()
                .add("2","2")
                .add("2","2")
                .build();
        okhttp3.Request request = new okhttp3.Request.Builder().url(url)
                .post(formBody)
                .build();
        Call call = httpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.i(TAG,"post异步");
            }
        });
    }
}
