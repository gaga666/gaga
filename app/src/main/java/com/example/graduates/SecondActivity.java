package com.example.graduates;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavAction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.graduates.databinding.ActivityMainBinding;
import com.example.graduates.databinding.ActivitySecondBinding;
import com.example.graduates.fragment.FirstFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class SecondActivity extends AppCompatActivity {
    private final String TAG = "second";
    public static String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        FloatingActionButton button = findViewById(R.id.float_button);

        NavController navController = Navigation.findNavController(SecondActivity.this,R.id.nav_host_fragment_content_main);
        BottomNavigationView navigationView = findViewById(androidx.transition.R.id.bottom);
        NavigationUI.setupWithNavController(navigationView,navController);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SecondActivity.this,EditActivity.class);
                startActivity(intent);
            }
        });

    }
    public  String toValue(){
        return username;
    }

}