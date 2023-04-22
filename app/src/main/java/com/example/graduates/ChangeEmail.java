package com.example.graduates;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.graduates.databinding.ActivityChangeEmailBinding;
import com.example.graduates.http.HttpRequest;

public class ChangeEmail extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email);

    }
}