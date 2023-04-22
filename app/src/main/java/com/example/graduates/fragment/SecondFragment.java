package com.example.graduates.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.graduates.R;
import com.example.graduates.SecondActivity;
import com.example.graduates.databinding.FragmentSecondBinding;
import com.example.graduates.http.HTTPHead;
import com.example.graduates.model.ListView;
import com.example.graduates.model.Lists;
import com.example.graduates.model.Username;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SecondFragment extends BaseFragment {

    private FragmentSecondBinding binding;
    private SecondActivity mActivity;
    private android.widget.ListView msgView;
    private final String TAG = "sFragment";
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof Activity){
            mActivity = (SecondActivity) context;
        }
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        msgView = getView().findViewById(R.id.msg_list);
        query(Username.username);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void query(String username){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient = new OkHttpClient();
                    URL url = new URL(HTTPHead.HTTPLISTHEAD + "/query");
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
                            mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ObjectMapper objectMapper = new ObjectMapper();
                                    String json = null;
                                    try {
                                        json = response.body().string().toString();
                                        JSONArray jsonArray = new JSONArray(json);
                                        String[] titleList = new String[jsonArray.length()];
                                        String[] dateList = new String[jsonArray.length()];
                                        String[] textList = new String[jsonArray.length()];

                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject jsonObject = jsonArray.optJSONObject(i);
                                            ListView list = objectMapper.readValue(jsonObject.toString(),ListView.class);
                                            titleList[i] = list.getTitle();
                                            dateList[i] = list.getDate();
                                            textList[i]  = list.getText();
                                        }
                                        Log.i(TAG, Arrays.toString(titleList));
                                        List<Map<String ,Object>> msg = new ArrayList<>();
                                        for (int i = 0; i < titleList.length; i++) {
                                            HashMap map = new HashMap();
                                            map.put("title",titleList[i]);
                                            map.put("date",dateList[i]);
                                            msg.add(map);
                                        }
                                        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(),msg,R.layout.msg_item,new String[]{"title","date"},new int[]{R.id.msg_title,R.id.msg_date});
                                        msgView.setAdapter(simpleAdapter);
                                        msgView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                Toast.makeText(mActivity, textList[i], Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        msgView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                            @Override
                                            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                return false;
                                            }
                                        });
                                    } catch (IOException | JSONException e) {
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
