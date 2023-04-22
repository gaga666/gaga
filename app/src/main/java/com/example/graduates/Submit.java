package com.example.graduates;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import com.example.graduates.databinding.ActivitySubmitBinding;
import com.example.graduates.http.HTTPHead;
import com.example.graduates.model.Result;
import com.example.graduates.model.Username;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Submit extends AppCompatActivity {

    private ActivitySubmitBinding binding;
    private String in;
    private String out;
    private String input;
    private String output;
    private final static  String TAG = "submit";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySubmitBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.in.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        binding.out.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        binding.input.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        binding.output.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        binding.submit.setOnClickListener(view -> {
            in = binding.in.getText().toString();
            out = binding.out.getText().toString();
            input = binding.input.getText().toString();
            output = binding.output.getText().toString();
            insert(in,out,input,output, Username.username);
        });
    }

    public void insert(String in,String out,String input,String output,String username){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient = new OkHttpClient();
                    URL url = new URL(HTTPHead.HTTPCOSTHEAD + "/update");

                    FormBody formBody = new FormBody.Builder()
                            .add("email",username)
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
                            Log.i(TAG,"ERROR");
                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ObjectMapper objectMapper = new ObjectMapper();
                                    try {
                                        String json = response.body().string().toString();
                                        Result result = objectMapper.readValue(json,Result.class);
                                        int code = result.getCode();
                                        String msg = result.getMsg();
                                        if(code == 1) {
                                            Toast.makeText(Submit.this, msg, Toast.LENGTH_SHORT).show();
                                            finish();
                                        }else {
                                            Toast.makeText(Submit.this,"更新失败！",Toast.LENGTH_SHORT).show();
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