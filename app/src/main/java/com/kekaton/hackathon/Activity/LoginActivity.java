package com.kekaton.hackathon.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.kekaton.hackathon.API.VKApiCall;
import com.kekaton.hackathon.MainActivity;
import com.kekaton.hackathon.R;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;


public class LoginActivity extends AppCompatActivity {

    private static final String[] sMyScope = new String[]{
            VKScope.FRIENDS,
            VKScope.WALL,
            VKScope.PHOTOS,
            VKScope.NOHTTPS,
            VKScope.MESSAGES,
            VKScope.DOCS
    };

    AppCompatActivity activity;
    SharedPreferences sPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        activity = this;

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VKSdk.login(activity, sMyScope);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {

                VKApiCall.getUser(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        String first_name = "No";
                        String last_name = "Name";
                        String sex = "Sex";
                        String photoMax = "";
                        try {
                            JSONArray jsonResponse = response.json.getJSONArray("response");
                            first_name = jsonResponse.optJSONObject(0).getString("first_name");
                            last_name = jsonResponse.optJSONObject(0).getString("last_name");
                            sex = jsonResponse.optJSONObject(0).getString("sex");
                            photoMax = jsonResponse.optJSONObject(0).getString("photo_max");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        sPref = getSharedPreferences("mysettings", MODE_PRIVATE);
                        SharedPreferences.Editor ed = sPref.edit();
                        ed.putString("first_name", first_name);
                        ed.putString("last_name", last_name);
                        ed.putString("sex", sex);
                        ed.putString("photoMax", photoMax);
                        ed.apply();

                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                });


            }

            @Override
            public void onError(VKError error) {
                //Error login
            }

        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
