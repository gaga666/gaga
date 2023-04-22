package com.example.graduates;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.example.graduates.databinding.ActivityAnalysisBinding;
import com.example.graduates.model.Cost;
import com.example.graduates.http.HTTPHead;
import com.example.graduates.model.Username;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Analysis extends AppCompatActivity {

    private ActivityAnalysisBinding binding;
    private String username;
    private final String TAG = "analysis";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAnalysisBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        Log.i(TAG,"username  "+username);
        queryCost(Username.username);
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
                            runOnUiThread(new Runnable() {
                                String json = null;
                                @Override
                                public void run() {
                                    ObjectMapper objectMapper = new ObjectMapper();
                                    try {
                                        json = response.body().string().toString();
                                        Cost cost = objectMapper.readValue(json,Cost.class);
                                        double in = cost.getIn();
                                        double out = cost.getOut();
                                        double input = cost.getInput();
                                        double output = cost.getOutput();
                                        getData_1((float) in,(float) input);
                                        getData_2((float) in,(float) output);
                                        getData_3((float) out,(float) input);
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
    public void getData_1(float in ,float input){
        binding.inputIn.setUsePercentValues(true);
        binding.inputIn.getDescription().setEnabled(false);
        binding.inputIn.setDrawHoleEnabled(false);

        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry((float) (in-input),"其他收入"));
        entries.add(new PieEntry((float) input,"投入"));
        PieDataSet dataSet = new PieDataSet(entries,null);
        dataSet.setSliceSpace(3f);

        ArrayList<Integer> colors = new ArrayList<>();
        for (int c:
                ColorTemplate.PASTEL_COLORS){
            colors.add(c);
        }
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        binding.inputIn.setData(data);
        binding.inputIn.invalidate();
    }
    public void getData_2(float in ,float output){
        binding.outputIn.setUsePercentValues(true);
        binding.outputIn.getDescription().setEnabled(false);
        binding.outputIn.setDrawHoleEnabled(false);

        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry((float) (in-output),"其他收入"));
        entries.add(new PieEntry((float) output,"产出"));
        PieDataSet dataSet = new PieDataSet(entries,null);
        dataSet.setSliceSpace(3f);

        ArrayList<Integer> colors = new ArrayList<>();
        for (int c:
                ColorTemplate.PASTEL_COLORS){
            colors.add(c);
        }
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        binding.outputIn.setData(data);
        binding.outputIn.invalidate();
    }
    public void getData_3(float out ,float input){
        binding.inputOut.setUsePercentValues(true);
        binding.inputOut.getDescription().setEnabled(false);
        binding.inputOut.setDrawHoleEnabled(false);

        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry((float) (out-input),"其它支出"));
        entries.add(new PieEntry((float) input,"投入"));
        PieDataSet dataSet = new PieDataSet(entries,null);
        dataSet.setSliceSpace(3f);

        ArrayList<Integer> colors = new ArrayList<>();
        for (int c:
                ColorTemplate.PASTEL_COLORS){
            colors.add(c);
        }
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        binding.inputOut.setData(data);
        binding.inputOut.invalidate();
    }
}
