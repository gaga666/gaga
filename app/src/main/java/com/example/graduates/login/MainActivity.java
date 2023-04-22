package com.example.graduates.login;

import android.content.Intent;
import android.os.Bundle;

import com.example.graduates.ChangePassword;
import com.example.graduates.RegisterActivity;
import com.example.graduates.SecondActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.graduates.databinding.ActivityMainBinding;
import com.example.graduates.http.HTTPHead;
import com.example.graduates.http.HttpRequest;
import com.example.graduates.model.Result;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private HttpRequest httpRequest = new HttpRequest();
    private String username;
    private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.login.setOnClickListener(new View.OnClickListener() {


            /**
             * 用户名密码校验
             * @param view
             */
            @Override
            public void onClick(View view) {
                username = binding.userPasswd.getText().toString();
                password = binding.userName.getText().toString();
                login(username,password);
            }
        });
        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setClass(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        binding.changePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ChangePassword.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setClass(MainActivity.this, ChangePassword.class);
                startActivity(intent);
            }
        });
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
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ObjectMapper objectMapper = new ObjectMapper();
                                    String json = null;
                                    String msg = null;
                                    try {
                                        json = response.body().string().toString();
                                        Result result = objectMapper.readValue(json,Result.class);
                                        code[0] = result.getCode();
                                        msg = result.getMsg();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    Log.i("login",Integer.toString(code[0]));
                                    Log.i("login",username);
                                    Log.i("login",password);
                                    if(code[0] == 1){
                                        Toast.makeText(MainActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                                        intent.putExtra("username",username);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.setClass(MainActivity.this, SecondActivity.class);
                                        startActivity(intent);
                                    }else {
                                        Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

            }
        }).start();
        return code[0];
    }
}