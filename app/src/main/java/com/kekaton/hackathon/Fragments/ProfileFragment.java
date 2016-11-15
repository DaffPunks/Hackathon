package com.kekaton.hackathon.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kekaton.hackathon.BibaRetrofit;
import com.kekaton.hackathon.R;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileFragment extends BaseFragment {
    private EditText text;
    private TextView translated;
    private Button translateBtn;
    private final String URL = "https://translate.yandex.ru";
    private final String KEY = "trnsl.1.1.20161113T050255Z.5ee1ca7d5dbbd970.b82f8c250d226be570b710628b3c273006fc862f";

    private Gson gson = new GsonBuilder().create();
    private Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(URL)
            .build();

    private BibaRetrofit intf = retrofit.create(BibaRetrofit.class);

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        text = (EditText) view.findViewById(R.id.text);
        translated = (TextView) view.findViewById(R.id.translated);
        translateBtn = (Button) view.findViewById(R.id.button);
        translateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> mapJson = new HashMap<>();
                mapJson.put("key", KEY);
                mapJson.put("text", text.toString());
                mapJson.put("lang", "en-ru");
                Call<Object> call = intf.translate(mapJson);
                try {
                    Response<Object> response = call.enqueue(new Callback<Object>() {
                        @Override
                        public void onResponse(Call<Object> call, Response<Object> response) {

                        }

                        @Override
                        public void onFailure(Call<Object> call, Throwable t) {

                        }
                    });

                    Map<String, String> map = gson.fromJson(response.body().toString(), Map.class);

                    for(Map.Entry e : map.entrySet()) {
                        if(e.getKey().equals("text"))
                            translated.setText(e.getValue().toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
