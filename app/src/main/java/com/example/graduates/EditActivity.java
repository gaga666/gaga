package com.example.graduates;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.graduates.databinding.ActivityEditBinding;
import com.example.graduates.fragment.SecondFragment;
import com.example.graduates.http.HTTPHead;
import com.example.graduates.model.Result;
import com.example.graduates.model.Username;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.necer.painter.CalendarPainter;
import com.necer.painter.InnerPainter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EditActivity extends AppCompatActivity {
    private ActivityEditBinding binding;
    private static final int msgKey1 = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_edit);
        binding = ActivityEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        new TimeThread().start();
        binding.editSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = binding.editTitle.getText().toString();
                String text = binding.editText.getText().toString();
                String date = binding.editDate.getText().toString();
                insert(title,text,date, Username.username);
            }
        });
    }



    public void insert(String title ,String text,String date,String username){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient = new OkHttpClient();
                    URL url = new URL(HTTPHead.HTTPLISTHEAD + "/insert");
                    HashMap<String,Object> hashMap = new HashMap<>();

                    FormBody formBody = new FormBody.Builder()
                            .add("email",username)
                            .add("title",title)
                            .add("text",text)
                            .add("date",date)
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
                            int code = result.getCode();
                            String msg = result.getMsg();
                            if(code == 1){
                                Toast.makeText(EditActivity.this, msg, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(EditActivity.this, SecondFragment.class);
                                startActivity(intent);
                            }else {
                                Toast.makeText(EditActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    public class TimeThread extends Thread {
        @Override
        public void run () {
            do {
                try {
                    Thread.sleep(1000);
                    Message msg = new Message();
                    msg.what = msgKey1;
                    mHandler.sendMessage(msg);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while(true);
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage (Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case msgKey1:
                    binding.tvResult.setText(getTime());
                    break;
                default:
                    break;
            }
        }
    };
    //时间戳转化为时间
    String getTime(){
        long time=System.currentTimeMillis();//long now = android.os.SystemClock.uptimeMillis();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1=new Date(time);
        String t1=format.format(d1);
        return t1;
//        Log.e("msg", t1);
    }
}

