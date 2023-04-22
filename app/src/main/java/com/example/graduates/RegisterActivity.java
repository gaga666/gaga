package com.example.graduates;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;


import com.example.graduates.databinding.ActivityRegisterBinding;

import com.example.graduates.http.HTTPHead;
import com.example.graduates.http.HttpRequest;

import com.example.graduates.model.Result;
import com.example.graduates.login.MainActivity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class RegisterActivity extends AppCompatActivity {

    private final String TAG = "registeractivity";
    private static final String REGISTER = "register";

    private ActivityRegisterBinding binding;
    private String username;
    private String pwd_1;
    private String pwd_2;
    private String email;
    private String code;
    private int codes;

    public  int getCodes() {
        return codes;
    }

    public void setCodes(int codes) {
        this.codes = codes;
    }

    public static RegisterActivity registerActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        HttpRequest httpRequest = new HttpRequest();
        String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

        binding.mailSubmit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                username = binding.userName.getText().toString();
                pwd_1 = binding.userPasswdFirst.getText().toString();
                pwd_2 = binding.userPasswdSecond.getText().toString();
                code = binding.userCode.getText().toString();
                email = binding.userMail.getText().toString();

                Pattern pattern = Pattern.compile(check);
                Matcher matcher = pattern.matcher(email);
                boolean ismatched = matcher.matches();
                if (!ismatched){
                    Snackbar.make(RegisterActivity.this,view,"请输入正确邮箱",Snackbar.LENGTH_SHORT).show();
                }else {
                    int code = httpRequest.sendEmail(email);
                    Snackbar.make(RegisterActivity.this, view, "发送成功", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        binding.register.setOnClickListener(new OnClickListener() {

            /**
             * 注册
             */
            @Override
            public void onClick(View view) {
                username = binding.userName.getText().toString();
                pwd_1 = binding.userPasswdFirst.getText().toString();
                pwd_2 = binding.userPasswdSecond.getText().toString();
                code = binding.userCode.getText().toString();
                email = binding.userMail.getText().toString();
                if (!pwd_1.equals(pwd_2)){
                    Snackbar.make(view,"确认密码与密码不一致",Snackbar.LENGTH_SHORT).show();
                }else {
                    register(username, pwd_1, email, code);
                }
            }
        });


    }


    public void register(String username,String password,String email,String code){
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
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ObjectMapper objectMapper = new ObjectMapper();
                                    String json = null;
                                    try {
                                        json = response.body().string().toString();
                                        Result result = objectMapper.readValue(json,Result.class);
                                        codes[0] = result.getCode();
                                        String msg = result.getMsg();
                                        Log.i(REGISTER,msg);
                                        if(codes[0] == 301){
                                            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                            startActivity(intent);
                                        }else{
                                            Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
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
    }

}