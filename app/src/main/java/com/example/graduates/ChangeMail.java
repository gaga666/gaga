package com.example.graduates;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.graduates.databinding.ActivityChangeEmailBinding;
import com.example.graduates.databinding.ActivityChangeMailBinding;
import com.example.graduates.http.HTTPHead;
import com.example.graduates.http.HttpRequest;
import com.example.graduates.login.MainActivity;
import com.example.graduates.tools.Result;
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

public class ChangeMail extends AppCompatActivity {

    private ActivityChangeMailBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_change_mail);
        binding = ActivityChangeMailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        HttpRequest httpRequest = new HttpRequest();

        binding.changeMailSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String email = binding.changeMail.getText().toString();
                httpRequest.sendEmail(email);
                Snackbar.make(ChangeMail.this, view, "发送成功", Snackbar.LENGTH_SHORT).show();
            }
        });

        String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

        binding.change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String  newMail_1 = binding.changeMailFirst.getText().toString();
               String newMail_2 = binding.changeMailSecond.getText().toString();
               String code = binding.changeCode.getText().toString();
                Pattern pattern = Pattern.compile(check);
                Matcher matcher = pattern.matcher(newMail_1);
                boolean ismatched = matcher.matches();
                if (!ismatched){
                    Snackbar.make(ChangeMail.this,view,"请输入正确邮箱",Snackbar.LENGTH_SHORT).show();
                }else {
                    changeMail(newMail_1,newMail_2,code);
                }

            }
        });
    }


    public void changeMail(String email_1 ,String email_2,String code){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(HTTPHead.HTTPUSERHead+"/changeemail");
                    OkHttpClient okHttpClient = new OkHttpClient();
                    FormBody formBody = new FormBody.Builder()
                            .add("email_1",email_1)
                            .add("email_2",email_2)
                            .add("code",code)
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
                                        Result result = objectMapper.readValue(json,Result.class);
                                        int code = result.getCode();
                                        String msg = result.getMsg();
                                        if(code == 501){
                                            Intent intent = new Intent(ChangeMail.this, MainActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            intent.setClass(ChangeMail.this, MainActivity.class);
                                            startActivity(intent);
                                        }else {
                                            Toast.makeText(ChangeMail.this, msg, Toast.LENGTH_SHORT).show();
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