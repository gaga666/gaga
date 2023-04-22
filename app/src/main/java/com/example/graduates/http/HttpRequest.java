package com.example.graduates.http;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.graduates.model.Cost;
import com.example.graduates.model.Result;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpRequest {

    //注册请求
    private static final String REGISTER = "register";
    private static final String EMAIL = "email";
    private static final String LOGIN = "login";


    public int register(String username,String password,String email,String code){
        int[] codes = new int[1];
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(HTTPHead.HTTPUSERHead + "/register");
                    OkHttpClient okHttpClient = new OkHttpClient();
                    FormBody formBody = new FormBody.Builder()
                            .add("username",username)
                            .add("password",password)
                            .add("code" ,code)
                            .add("email",email)
                            .build();
                    Request request = new Request.Builder()
                            .url(url)
                            .post(formBody)
                            .build();
                    Call call = okHttpClient.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            Log.i(REGISTER,"ERROR");
                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                            ObjectMapper objectMapper = new ObjectMapper();
                            String json = response.body().string().toString();
                            Result result = objectMapper.readValue(json,Result.class);
                            codes[0] = result.getCode();
                            Log.i(REGISTER,Integer.toString(codes[0]));
                        }
                    });
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return codes[0];
    }

    //发送邮件请求
    public int sendEmail(String email){
        int[] code = new int[1];
        Object obj = new Object();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(HTTPHead.HTTPUSERHead + "/sendEmail");
                    OkHttpClient okHttpClient = new OkHttpClient();
                    FormBody formBody = new FormBody.Builder()
                            .add("email",email)
                            .build();
                    Request request = new Request.Builder()
                            .url(url)
                            .post(formBody)
                            .build();
                    Call call = okHttpClient.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            Log.i(EMAIL,"ERROR");
                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            ObjectMapper objectMapper = new ObjectMapper();
                            String json = response.body().string().toString();
                            Result result = objectMapper.readValue(json,Result.class);
                            code[0] = result.getCode();
                        }
                    });
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Log.i(EMAIL,Integer.toString(code[0]));
        return code[0];
    }

    public int login(String username,String password){
        int[] code = new int[1];
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(HTTPHead.HTTPUSERHead + "/login");
                    OkHttpClient okHttpClient = new OkHttpClient();
                    FormBody formBody = new FormBody.Builder()
                            .add("username",username)
                            .add("password",password)
                            .build();
                    Request request = new Request.Builder()
                            .url(url)
                            .post(formBody)
                            .build();
                    Call call = okHttpClient.newCall(request);

                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {

                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            ObjectMapper objectMapper = new ObjectMapper();
                            String json = response.body().string().toString();
                            Result result = objectMapper.readValue(json,Result.class);
                            code[0] = result.getCode();
                        }
                    });
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

            }
        }).start();
        return code[0];
    }

    public void change(String email ,String code,String password){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(HTTPHead.HTTPUSERHead+"/change");
                    OkHttpClient okHttpClient = new OkHttpClient();
                    FormBody formBody = new FormBody.Builder()
                            .add("email",email)
                            .add("code",code)
                            .add("password",password)
                            .build();
                    Request request = new Request.Builder()
                            .url(url)
                            .post(formBody)
                            .build();
                    Call call = okHttpClient.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {

                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                        }
                    });
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void query(String email){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient = new OkHttpClient();
                    URL url = new URL(HTTPHead.HTTPCOSTHEAD + "/query");
                    FormBody formBody = new FormBody.Builder()
                            .add("email",email)
                            .build();
                    Request request = new Request.Builder()
                            .url(url)
                            .post(formBody)
                            .build();
                    Call call = okHttpClient.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {

                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            ObjectMapper objectMapper = new ObjectMapper();
                            String json = response.body().string().toString();
                            Cost cost = new Cost();

                        }
                    });
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    public void insert(String in,String out,String input,String output,String email){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient = new OkHttpClient();
                    URL url = new URL(HTTPHead.HTTPCOSTHEAD + "/insert");
                    HashMap<String,Object> hashMap = new HashMap<>();

                    FormBody formBody = new FormBody.Builder()
                            .add("email",email)
                            .add("in",in)
                            .add("out",out)
                            .add("input",input)
                            .add("output",output)
                            .build();
                    Request request = new Request.Builder()
                            .url(url)
                            .post(formBody)
                            .build();
                    Call call = okHttpClient.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {

                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            ObjectMapper objectMapper = new ObjectMapper();
                            String json = response.body().string().toString();
                            Result result = new Result();

                        }
                    });
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }


    public void queryCost(String username){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient = new OkHttpClient();
                    URL url = new URL(HTTPHead.HTTPCOSTHEAD + "/query");
                    FormBody formBody = new FormBody.Builder()
                            .add("email",username)
                            .build();
                    Request request = new Request.Builder()
                            .url(url)
                            .post(formBody)
                            .build();
                    Call call = okHttpClient.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {

                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            ObjectMapper objectMapper = new ObjectMapper();
                            String json = response.body().string().toString();
                            Cost cost = new Cost();

                        }
                    });
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
}
