package com.example.sunrinton;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sunrinton.Date.UserDate;
import com.example.sunrinton.Interface.CustomRetrofit;
import com.example.sunrinton.Interface.CustomRetrofitService;
import com.example.sunrinton.Util.Quiz;
import com.example.sunrinton.Util.User;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static List<Quiz> quizlist = new ArrayList<>();
    public static int quizIndex = 0;
    public static int CorrectCount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sf = getSharedPreferences(getString(R.string.myaccount_sf), MODE_PRIVATE);
        String s;
        if((s = sf.getString("date", "none")).equals("none") == false) {

            Gson gson = new Gson();
            User user = gson.fromJson(s, User.class);
            UserDate.user = user;
            // 메인화면으로 넘어가기
            System.out.println(user.getName() + "님으로 자동 로그인됩니다.");
            nextActivity();
            return;
        }

        Button loginBtn = findViewById(R.id.login_loginBtn);
        TextView regBtn = findViewById(R.id.login_RegisterBtn);
        EditText idField = findViewById(R.id.login_id);
        EditText pwField = findViewById(R.id.login_pw);

        loginBtn.setOnClickListener((view) -> {
            String id, pw;
            if((id = idField.getText().toString().trim()).isEmpty() || (pw = pwField.getText().toString()).trim().isEmpty()) {
                Toast.makeText(this, "모두 입력해주세요", Toast.LENGTH_SHORT).show();
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
                        System.out.println("메세지 : " + respone);
                        System.out.println("코드 : " + json.getInt("code"));
                        if(json.getInt("code") == 200) {
                            System.out.println("로그인 성공");

                            String userjson = json.getJSONObject("data").toString();

                            Gson gson = new Gson();
                            User user = gson.fromJson(userjson, User.class);
                            UserDate.user = user;
                            SharedPreferences sf = getSharedPreferences(getString(R.string.myaccount_sf), MODE_PRIVATE);
                            SharedPreferences.Editor edit = sf.edit();
                            edit.putString("date", userjson);
                            edit.commit();
                            nextActivity();
                        } else {

                            Toast.makeText(getBaseContext(), "계정 정봍가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                        }

                    } catch (IOException | JSONException e) {
                        Toast.makeText(getBaseContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
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

    private void nextActivity() {
        Intent intent = new Intent(this, MainFormActivity.class);
        startActivity(intent);
        finish();
    }

}