package com.kekaton.hackathon.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kekaton.hackathon.API.BibaRetrofit;
import com.kekaton.hackathon.R;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChallengesFragment extends BaseFragment {
    @Bind(R.id.textView2) TextView textView;
    private EditText tr;
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

    boolean isCompleted;

    public ChallengesFragment(){
        //empty constructor
    }

    public static ChallengesFragment newInstance(boolean isCompleted) {
        ChallengesFragment fragment = new ChallengesFragment();
        Bundle args = new Bundle();
        args.putBoolean("isCompleted", isCompleted);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.isCompleted = getArguments().getBoolean("isCompleted");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.challenges_fragment, container, false);
        verifyStoragePermissions(getMainActivity());
        ButterKnife.bind(this, view);

        if(isCompleted){
            textView.setText("Тут выполненные");
        } else {
            textView.setText("Тут текущие");
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tr = (EditText) view.findViewById(R.id.tr);
        translated = (TextView) view.findViewById(R.id.translated);
        translateBtn = (Button) view.findViewById(R.id.translate);
        translateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> mapJson = new HashMap<>();
                mapJson.put("key", KEY);
                mapJson.put("text", tr.toString());
                mapJson.put("lang", "en-ru");
                Call<Object> call = intf.translate(mapJson);
                call.enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {

                        Map<String, String> map = gson.fromJson(response.body().toString(), Map.class);
                        for(Map.Entry e : map.entrySet()) {
                            if(e.getKey().equals("text"))
                                translated.setText(e.getValue().toString());

                        }
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                    }
                });
            }
        });
    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET
    };

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}
