package com.example.sunrinton.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class Fragment1 extends Fragment {
    ViewGroup viewGroup;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup =(ViewGroup) inflater.inflate(R.layout.fragment1, container, false);


        TextView tv1 = viewGroup.findViewById(R.id.f1_Word);
        TextView tv2 = viewGroup.findViewById(R.id.f1_WordMean);
        TextView tv3 = viewGroup.findViewById(R.id.f1_WordDetail);


        CardView randomquiz = viewGroup.findViewById(R.id.f1_RandomQuizBtn);
        randomquiz.setOnClickListener(view -> {
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
}
