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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kekaton.hackathon.API.LoginApi;
import com.kekaton.hackathon.API.NewChallengesApi;
import com.kekaton.hackathon.MainActivity;
import com.kekaton.hackathon.R;

import java.io.IOException;
import java.util.HashMap;
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

    private final String URL = "http://138.68.101.176/";

    private Gson gson = new GsonBuilder().create();
    private Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(URL)
            .build();

    private NewChallengesApi intf = retrofit.create(NewChallengesApi.class);

    private static Integer GOAL_TYPE = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_challenge_view);
        ButterKnife.bind(this);

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
                mapJson.put("token", sPref.getString("token", "null"));
                /*mapJson.put("description", description.getText().toString());
                mapJson.put("goal_type", String.valueOf(GOAL_TYPE));
                mapJson.put("goal_number", quantity.getText().toString());
                mapJson.put("goal_vk_uuid", "29286202");*/
                Call<Object> call = intf.newChallenge(mapJson);

                call.enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        Toast.makeText(getApplicationContext(), response.body().toString(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {

                    }
                });
            }
        };
        confirmBtn.setOnClickListener(oclBtnOk);
    }
}
