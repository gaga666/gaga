package com.example.graduates;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.graduates.databinding.ActivityChangeEmailBinding;
import com.example.graduates.databinding.ActivityChangePasswordBinding;
import com.example.graduates.http.HTTPHead;
import com.example.graduates.http.HttpRequest;
import com.example.graduates.login.MainActivity;
import com.example.graduates.tools.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ChangePassword extends AppCompatActivity {

    private String email;
    private String newPw_1;
    private String newPw_2;
    private String code;
    private String TAG = "changePassword";
    private ActivityChangePasswordBinding binding;
    private HttpRequest httpRequest = new HttpRequest();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.changeMailSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = binding.changeMail.getText().toString();
                httpRequest.sendEmail(email);
                Snackbar.make(ChangePassword.this, view, "发送成功", Snackbar.LENGTH_SHORT).show();
            }
        });

        binding.change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = binding.changeMail.getText().toString();
                newPw_1 = binding.changePasswordFirst.getText().toString();
                newPw_2 = binding.changePasswordSecond.getText().toString();
                code = binding.changeCode.getText().toString();
                if (!newPw_1.equals(newPw_2)){
                    Snackbar.make(ChangePassword.this,view,"确认密码与密码不同",Snackbar.LENGTH_SHORT);
                }else {
                    change(email,code,newPw_1);
                }
            }
        });

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
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    try {
                                        ObjectMapper objectMapper = new ObjectMapper();
                                        String json = response.body().string().toString();
                                        Log.i(TAG,json);
                                        Result result = objectMapper.readValue(json,Result.class);
                                        int code = result.getCode();
                                        String msg = result.getMsg();
                                        Log.i(TAG,msg);
                                        if(code == 401){
                                            Intent intent = new Intent(ChangePassword.this, MainActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            intent.setClass(ChangePassword.this, MainActivity.class);
                                            startActivity(intent);
                                        }else {
                                            Toast.makeText(ChangePassword.this, msg, Toast.LENGTH_SHORT).show();
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