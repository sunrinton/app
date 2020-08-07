package com.example.sunrinton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sunrinton.Interface.CustomRetrofit;
import com.example.sunrinton.Interface.CustomRetrofitService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    int year=0, month=0, day=0;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_register);


        Button regBtn = findViewById(R.id.login_RegisterBtn);
        EditText nameField = findViewById(R.id.register_name);
        EditText idField = findViewById(R.id.register_id);
        EditText pwField = findViewById(R.id.register_pw);
        EditText pw2Field = findViewById(R.id.register_pw2);
        Button dateBtn = findViewById(R.id.register_date);

        dateBtn.setOnClickListener(view -> {
            DatePickerDialog dialog = new DatePickerDialog(this, listener, 2020, 7, 8);
            dialog.show();
        });

        regBtn.setOnClickListener((view) -> {

            String id, pw, pw2, name;
            if((id = idField.getText().toString().trim()).isEmpty() || (pw = pwField.getText().toString()).trim().isEmpty() || (pw2 = pw2Field.getText().toString()).trim().isEmpty() || (name = nameField.getText().toString()).trim().isEmpty()) {

                Toast.makeText(this, "모두 입력해주세요", Toast.LENGTH_SHORT).show();
                // 에러 메세지 띄워야함
                return;
            }

            if(pw.equals(pw2) == false) {
                Toast.makeText(this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                // 비밀번호 동일하지 않음
                return;
            }

            if(year == 0 || month == 0 || day == 0) {
                Toast.makeText(this, "생일을 기입해주세요", Toast.LENGTH_SHORT).show();
                // 날짜를 설정하시오
                return;
            }

            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_MONTH, day);
            cal.set(Calendar.MONTH, month);
            cal.set(Calendar.YEAR, year);

            CustomRetrofitService RetrofitAPI = CustomRetrofit.getInstance(this).getCustomService();
            Call<ResponseBody> CallResponse = RetrofitAPI.UserRegister(name, id, pw, cal.getTimeInMillis());

            CallResponse.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    String respone = null;
                    try {
                        respone = response.body().string();
                        JSONObject json = new JSONObject(respone);
                        System.out.println(respone);
                        System.out.println("코드 : " + json.getInt("code"));
                        if(json.getInt("code") == 200) {
                            System.out.println("가입 성공");
                            finish();
                        }

                    } catch (IOException | JSONException e) {
                        Toast.makeText(getBaseContext(), "가입 실패", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                }
            });


        });
    }

    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {

        @Override

        public void onDateSet(DatePicker view, int _year, int monthOfYear, int dayOfMonth) {
            year = _year;
            month = monthOfYear+1;
            day = dayOfMonth;
            System.out.println(year +" "+month+" "+day);
        }

    };



}