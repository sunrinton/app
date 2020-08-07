package com.example.sunrinton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.sunrinton.Fragment.Fragment1;
import com.example.sunrinton.Fragment.Fragment2;
import com.example.sunrinton.Fragment.Fragment3;
import com.example.sunrinton.Interface.CustomRetrofit;
import com.example.sunrinton.Interface.CustomRetrofitService;
import com.example.sunrinton.Util.Quiz;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainFormActivity extends AppCompatActivity {
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main_form);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        Fragment1 f1 = new Fragment1();
        Fragment2 f2 = new Fragment2();
        Fragment3 f3 = new Fragment3();


        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,f1).commitAllowingStateLoss();


        
        bottomNavigationView.setOnNavigationItemSelectedListener((menuItem -> {
            switch(menuItem.getItemId()) {
                case R.id.menu_main:
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,f1).commitAllowingStateLoss();
                    return true;
                case R.id.menu_profile:
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,f2).commitAllowingStateLoss();
                    return true;
                case R.id.menu_rank:
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,f3).commitAllowingStateLoss();
                    return true;
                default:
                    return false;
            }
        }));
    }
}