package com.kekaton.hackathon.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.kekaton.hackathon.API.LoginApi;
import com.kekaton.hackathon.API.NewChallengesApi;
import com.kekaton.hackathon.Fragments.ProfileFragment;
import com.kekaton.hackathon.MainActivity;
import com.kekaton.hackathon.R;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewChallengeActivity extends AppCompatActivity {
    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.description) EditText description;
    @Bind(R.id.confirmBtn) Button confirmBtn;
    @Bind(R.id.quantity) EditText quantity;
    @Bind(R.id.loadingPanel) RelativeLayout loadingPanel;

    private static Integer GOAL_TYPE = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_challenge_view);
        ButterKnife.bind(this);
        loadingPanel.setVisibility(View.GONE);

        mToolbar.setTitle("Новый челлендж");
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.typeSelection, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                GOAL_TYPE = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        View.OnClickListener oclBtnOk = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DESC", description.getText().toString());
                Log.d("QUANT", quantity.getText().toString());
                Map<String, String> mapJson = new HashMap<>();
                SharedPreferences sPref = getSharedPreferences("mysettings", MODE_PRIVATE);

                JsonObject json = new JsonObject();

                String res = "";
                String URL = "";
                res += "?token=" + sPref.getString("token", "null");
                try {
                    res += "&description=" + URLEncoder.encode(description.getText().toString(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                res += "&goal_type=" + String.valueOf(GOAL_TYPE);
                res += "&goal_number=" + quantity.getText().toString();
                res += "&goal_vk_uuid=29286202";

                URL = res.replaceAll(" ", "%20");



                Ion.with(getApplicationContext())
                        .load("http://138.68.101.176/api/challenge/create"+URL)
                        .setJsonObjectBody(json)
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {

                                if(e==null) {
                                    Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_SHORT).show();
                                    loadingPanel.setVisibility(View.GONE);
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            }
                                else {
                                    Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
                                    loadingPanel.setVisibility(View.GONE);
                                    confirmBtn.setVisibility(View.VISIBLE);
                                }
                            }
                        });
                loadingPanel.setVisibility(View.VISIBLE);
                confirmBtn.setVisibility(View.GONE);
            }
        };
        confirmBtn.setOnClickListener(oclBtnOk);
    }
}
