package com.example.graduates.fragment;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {
    private Activity activity;
    public Context getContext(){
        if(activity == null){
            return MyApplication.getInstance();
        }
        return activity;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = getActivity();
    }
}
