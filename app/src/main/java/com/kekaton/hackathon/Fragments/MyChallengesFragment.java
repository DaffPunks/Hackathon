package com.kekaton.hackathon.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kekaton.hackathon.Adapters.MyChallengesAdapter;
import com.kekaton.hackathon.MainActivity;
import com.kekaton.hackathon.Model.Challenge;
import com.kekaton.hackathon.R;
import com.kekaton.hackathon.Util.CircleTransform;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.R.id.list;
import static android.content.Context.MODE_PRIVATE;
import static com.vk.sdk.VKUIHelper.getApplicationContext;

/**
 * Created by User on 19.11.2016.
 */

public class MyChallengesFragment extends BaseFragment {

    private static final String[] sMyScope = new String[]{
            VKScope.FRIENDS,
            VKScope.WALL,
            VKScope.PHOTOS,
            VKScope.NOHTTPS,
            VKScope.MESSAGES,
            VKScope.DOCS
    };

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    public static MyChallengesFragment newInstance() {
        MyChallengesFragment fragment = new MyChallengesFragment();
        return fragment;
    }

    List<Challenge> list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mychallengesfragment, container, false);
        ButterKnife.bind(this, view);

        mToolbar.setTitle("Мои челленджи");
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llm);

        //http://138.68.101.176/api/my_challenges?token=

        SharedPreferences sPref = getMainActivity().getSharedPreferences("mysettings", MODE_PRIVATE);

        Log.d("ASD", sPref.getString("token", "null"));

        Ion.with(getContext())
                .load("http://138.68.101.176/api/my_challenges?token=" + sPref.getString("token", "null"))
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        list = new ArrayList<Challenge>();
                        JSONArray arr = null;
                        try {
                            arr = new JSONArray(result.toString());
                            for(int i = 0; i < arr.length(); i++){

                                list.add(new Challenge(
                                        arr.getJSONObject(i).getInt("challenge.id"),
                                        20,
                                        arr.getJSONObject(i).getString("challenge.description"),
                                        "null",
                                        "null",
                                        "null",
                                        false
                                ));
                                MyChallengesAdapter adapter = new MyChallengesAdapter(list, getActivity());
                                recyclerView.setAdapter(adapter);
                            }

                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }

                    }
                });

        setupToolbarForFragment(mToolbar);

        return view;
    }
}