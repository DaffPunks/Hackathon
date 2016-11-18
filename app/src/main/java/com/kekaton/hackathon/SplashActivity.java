package com.kekaton.hackathon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.kekaton.hackathon.Activity.LoginActivity;
import com.kekaton.hackathon.Activity.RegisterActivity;
import com.vk.sdk.VKSdk;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sPref = getSharedPreferences("mysettings", MODE_PRIVATE);


        if (sPref.getString("token", "").equals("")) {
            startActivity(new Intent(this, LoginActivity.class));
        } else {
            startActivity(new Intent(this, MainActivity.class));
        }
        finish();
    }
}
