package com.example.sunrinton.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sunrinton.Date.UserDate;
import com.example.sunrinton.MainActivity;
import com.example.sunrinton.R;

public class Fragment2 extends Fragment {
    ViewGroup viewGroup;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup =(ViewGroup) inflater.inflate(R.layout.fragment2, container, false);
        TextView tv = viewGroup.findViewById(R.id.f2_name);
        tv.setText(UserDate.user.getName());


        Button logout = viewGroup.findViewById(R.id.logoutBtn);

        logout.setOnClickListener(view -> {

            SharedPreferences sf = viewGroup.getContext().getSharedPreferences(getString(R.string.myaccount_sf), viewGroup.getContext().MODE_PRIVATE);
            SharedPreferences.Editor edit = sf.edit();
            edit.clear();
            edit.commit();
            startActivity(new Intent(viewGroup.getContext(), MainActivity.class));

        });
        return viewGroup;

    }
}
