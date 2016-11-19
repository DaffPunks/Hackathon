package com.kekaton.hackathon.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kekaton.hackathon.API.VKApiCall;
import com.kekaton.hackathon.Activity.NewChallengeActivity;
import com.kekaton.hackathon.Adapters.ChallengesAdapter;
import com.kekaton.hackathon.MainActivity;
import com.kekaton.hackathon.Model.Challenge;
import com.kekaton.hackathon.R;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

public class ChallengesFragment extends BaseFragment {
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    boolean isCompleted;

    List<Challenge> list;
    List<Integer> kostyil;

    public ChallengesFragment() {
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
        ButterKnife.bind(this, view);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llm);

        VKApiCall vkApi = new VKApiCall(getContext());

        //http://138.68.101.176/api/challenges/completed?token=
        String url;
        if(isCompleted) {
            url = "http://138.68.101.176/api/challenges/completed?token=";
        } else {
            url = "http://138.68.101.176/api/challenges/current?token=";
        }

        SharedPreferences sPref = getMainActivity().getSharedPreferences("mysettings", MODE_PRIVATE);

        Log.d("FINALSRASF", url + sPref.getString("token", "null"));

        list = new ArrayList<>();

        Ion.with(getContext())
                .load(url + sPref.getString("token", "null"))
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        VKApiCall vkApiCall = new VKApiCall(getContext());
                        int followersCount;

                        int i;
                        if(result != null) {
                            try {
                                JSONArray arr = new JSONArray(result.toString());
                                for(i = 0; i < arr.length(); i++){

                                    list.add(new Challenge(
                                            arr.getJSONObject(i).getInt("goal.number"),
                                            20,
                                            arr.getJSONObject(i).getString("challenge.description"),
                                            arr.getJSONObject(i).getString("user.name"),
                                            arr.getJSONObject(i).getString("user.surname"),
                                            arr.getJSONObject(i).getString("goal_vk_uuid"),
                                            isCompleted
                                    ));
                                }
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                            ChallengesAdapter adapter = new ChallengesAdapter(list, getActivity());
                            recyclerView.setAdapter(adapter);
                        }
                    }
                });


        //list.add(new Challenge(150, 30, "Въебу лыжами Раисе хуисе, дырявой преподавательнице физры КемГУ", "Егор", "Катков", "https://pp.vk.me/c631523/v631523715/37af7/ZtT0R3h-0fE.jpg"));
        //list.add(new Challenge(10, 4, "Кину гавнецоу", "Артём", "Смаль", "https://pp.vk.me/c638819/v638819367/f6ce/yFYi1IKxB2Q.jpg"));
        //list.add(new Challenge(121, 33, "Кину гавнецооо", "Артём", "Смаль", "https://pp.vk.me/c638819/v638819367/f6ce/yFYi1IKxB2Q.jpg"));



        return view;
    }


}

