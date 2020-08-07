package com.example.sunrinton.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.sunrinton.Interface.CustomRetrofit;
import com.example.sunrinton.Interface.CustomRetrofitService;
import com.example.sunrinton.MainActivity;
import com.example.sunrinton.QuizActivity;
import com.example.sunrinton.R;
import com.example.sunrinton.Util.Quiz;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment1 extends Fragment implements View.OnClickListener {
    ViewGroup viewGroup;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup =(ViewGroup) inflater.inflate(R.layout.fragment1, container, false);


        TextView tv1 = viewGroup.findViewById(R.id.f1_Word);
        TextView tv2 = viewGroup.findViewById(R.id.f1_WordMean);
        TextView tv3 = viewGroup.findViewById(R.id.f1_WordDetail);

        TextView label = viewGroup.findViewById(R.id.f1_label_today);

        CardView repeatquiz = viewGroup.findViewById(R.id.f1_RepeatWord);
        CardView myquiz = viewGroup.findViewById(R.id.f1_MyQuizBtn);
        CardView randomquiz = viewGroup.findViewById(R.id.f1_RandomQuizBtn);
        randomquiz.setOnClickListener(this);
        myquiz.setOnClickListener(this);
        repeatquiz.setOnClickListener(this);

        EditText et = viewGroup.findViewById(R.id.f1_search);
        et.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    CustomRetrofitService RetrofitAPI = CustomRetrofit.getInstance(viewGroup.getContext()).getCustomService();
                    Call<ResponseBody> CallResponse = RetrofitAPI.getOneWord(et.getText().toString());
                    CallResponse.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                tv1.setText(jsonObject.getJSONObject("data").getString("word"));
                                tv2.setText(jsonObject.getJSONObject("data").getString("mean"));
                                tv3.setText(jsonObject.getJSONObject("data").getString("sentence"));
                                label.setText(et.getText().toString() + " 검색 결과");
                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }

                        }


                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                            System.out.println("MSG : " + t.getMessage());
                        }
                    });

                    return true;
                }
                return false;
            }
        });

        CustomRetrofitService RetrofitAPI = CustomRetrofit.getInstance(viewGroup.getContext()).getCustomService();
        Call<ResponseBody> CallResponse = RetrofitAPI.getOneWord();
        CallResponse.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    tv1.setText(jsonObject.getString("word"));
                    tv2.setText(jsonObject.getString("mean"));
                    tv3.setText(jsonObject.getString("sentence"));
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }

            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                System.out.println("MSG : " + t.getMessage());
            }
        });

        return viewGroup;

    }

    @Override
    public void onClick(View view) {
        CustomRetrofitService RetrofitAPI = CustomRetrofit.getInstance(viewGroup.getContext()).getCustomService();
        Call<ResponseBody> CallResponse = RetrofitAPI.getWords();
        CallResponse.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    MainActivity.quizlist.clear();
                    MainActivity.quizIndex=0;
                    MainActivity.CorrectCount=0;
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONArray array = jsonObject.getJSONArray("data");
                    Gson gson = new Gson();
                    System.out.println(array.length());
                    for(int i = 0; i < array.length(); i++) {
                        Quiz q = gson.fromJson(array.getJSONObject(i).toString(), Quiz.class);
                        MainActivity.quizlist.add(q);
                    }
                    startActivity(new Intent(view.getContext(), QuizActivity.class));
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
