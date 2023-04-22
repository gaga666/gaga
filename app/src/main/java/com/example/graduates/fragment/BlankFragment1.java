package com.example.graduates.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.graduates.ChangeMail;
import com.example.graduates.ChangePassword;
import com.example.graduates.R;
import com.example.graduates.SecondActivity;
import com.example.graduates.databinding.FragmentBlank1Binding;
import com.example.graduates.login.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment1 extends BaseFragment {

    private static final String TAG = "mvvmList";


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentBlank1Binding binding;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public BlankFragment1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment1.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragment1 newInstance(String param1, String param2) {
        BlankFragment1 fragment = new BlankFragment1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBlank1Binding.inflate(inflater,container,false);
        SimpleAdapter simpleAdapter = new SimpleAdapter(this.getContext(),getData(),R.layout.list_item,new String[]{"image","text"},new int[]{R.id.list_image,R.id.list_text});
        binding.listHome.setAdapter(simpleAdapter);
        binding.listHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        //修改密码页面
                        Intent intent = new Intent(getActivity(), ChangePassword.class);
                        startActivity(intent);
                        break;
                    case 1:
                        //修改邮箱界面
                        Intent intent2 = new Intent(getActivity(), ChangeMail.class);
                        startActivity(intent2);
                        break;
                    case 2:
                        //退出登录界面
                        Intent intent1 = new Intent(getActivity(), MainActivity.class);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent1.setClass(getActivity(), MainActivity.class);
                        startActivity(intent1);
                        break;
                    case 3:
                        //退出登录界面
                        Toast.makeText(getActivity(), "这里什么也没有", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        return binding.getRoot();
    }

    private List<Map<String, Object>> getData() {
         String[] text={"修改密码","修改邮箱","退出登录","关于……"};
         int[] image={R.drawable.passwd,R.drawable.mail,R.drawable.exit,R.drawable.avatar};
         List<Map<String ,Object>> list = new ArrayList<>();
        for (int i = 0; i < text.length; i++) {
            HashMap map = new HashMap();
            map.put("image",image[i]);
            map.put("text",text[i]);
            list.add(map);
        }
        return list;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}