package com.example.sunrinton;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.sunrinton.Interface.CustomRetrofit;
import com.example.sunrinton.Interface.CustomRetrofitService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginBtn = findViewById(R.id.login_loginBtn);
        Button regBtn = findViewById(R.id.login_RegisterBtn);
        EditText idField = findViewById(R.id.login_id);
        EditText pwField = findViewById(R.id.login_pw);

        loginBtn.setOnClickListener((view) -> {
            String id, pw;
            if((id = idField.getText().toString().trim()).isEmpty() || (pw = pwField.getText().toString()).trim().isEmpty()) {
                // 에러 메세지 띄워야함
                return;
            }

            CustomRetrofitService RetrofitAPI = CustomRetrofit.getInstance(this).getCustomService();
            Call<ResponseBody> CallResponse = RetrofitAPI.UserLogin(id,pw);

            CallResponse.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    String respone = null;
                    try {
                        respone = response.body().string();
                        JSONObject json = new JSONObject(respone);

                        System.out.println("코드 : " + json.getInt("code"));
                        if(json.getInt("code") == 200) {
                            System.out.println("로그인 성공");
                        }

                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                }
            });


        });

        regBtn.setOnClickListener((view) -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });


    }

}