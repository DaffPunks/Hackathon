package com.kekaton.hackathon.Fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kekaton.hackathon.Adapters.RVAdapter;
import com.kekaton.hackathon.R;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ChallengesFragment extends BaseFragment {
    @Bind(R.id.textView2)
    TextView textView;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    boolean isCompleted;

    List<String> list;

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

        VKRequest request = VKApi.users().get(VKParameters.from(VKApiConst.FIELDS,
                "id,first_name,last_name,sex,bdate,city,country,photo_50,photo_100," +
                        "photo_200_orig,photo_200,photo_400_orig,photo_max,photo_max_orig,online," +
                        "online_mobile,lists,domain,has_mobile,contacts,connections,site,education," +
                        "universities,schools,can_post,can_see_all_posts,can_see_audio,can_write_private_message," +
                        "status,last_seen,common_count,relation,relatives,counters"));
        request.secure = false;
        request.useSystemLanguage = false;

        Long requestId = request.registerObject();

        VKRequest requested = null;

        requested = VKRequest.getRegisteredRequest(requestId);
        requested.executeWithListener(mRequestListener);

        //if(isCompleted){
        //    textView.setText("Тут выполненные");
        //} else {
        //    textView.setText("Тут текущие");
        //}

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llm);

        list = new ArrayList<>();
        list.add("Rofl 1");
        list.add("Rofl 2");
        list.add("Rofl 3");

        RVAdapter adapter = new RVAdapter(list);
        recyclerView.setAdapter(adapter);

        return view;
    }

    VKRequest.VKRequestListener mRequestListener = new VKRequest.VKRequestListener() {
        @Override
        public void onComplete(VKResponse response) {

            String first_name = "No";
            String last_name = "Name";
            try {
                JSONObject jsonResponse = response.json.getJSONObject("response");
                first_name = jsonResponse.getString("first_name");
                last_name = jsonResponse.getString("last_name");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            textView.setText("Welcome " + first_name + " " + last_name);

        }

        @Override
        public void onError(VKError error) {
            textView.setText(error.toString());
        }

        @Override
        public void onProgress(VKRequest.VKProgressType progressType, long bytesLoaded,
                               long bytesTotal) {
            // you can show progress of the request if you want
        }

        @Override
        public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
        }
    };

}

