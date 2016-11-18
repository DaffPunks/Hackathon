package com.kekaton.hackathon.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kekaton.hackathon.API.RegisterApi;
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


public class RegisterActivity extends AppCompatActivity {

    @Bind(R.id.button)
    Button button;

    private final String URL = "http://138.68.101.176/";

    private Gson gson = new GsonBuilder().create();
    private Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(URL)
            .build();

    private RegisterApi intf = retrofit.create(RegisterApi.class);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);
        ButterKnife.bind(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> mapJson = new HashMap<>();
                mapJson.put("email", "saw66766assad2@gmail.com");
                mapJson.put("password", "qwerty");
                Call<Object> call = intf.signup(mapJson);
                call.enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        JsonParser parser = new JsonParser();
                        //JsonObject obj = parser.parse(response.body().toString()).getAsJsonObject();
                        //JsonArray pItem = obj.getAsJsonArray("response");
                        //obj = parser.parse(pItem.get(0).toString()).getAsJsonObject();
                        if(response.isSuccessful()){
                            Toast.makeText(getApplicationContext(), response.body().toString() , Toast.LENGTH_SHORT).show();
                        } else{
                            try {
                                Toast.makeText(getApplicationContext(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), call.toString(), Toast.LENGTH_SHORT).show();
                    }

                    //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                });
            }
        });

    }

}
