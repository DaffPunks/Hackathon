package com.kekaton.hackathon.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kekaton.hackathon.R;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ChallengesFragment extends BaseFragment {
    @Bind(R.id.textView2) TextView textView;

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

        return view;
    }

    VKRequest.VKRequestListener mRequestListener = new VKRequest.VKRequestListener() {
        @Override
        public void onComplete(VKResponse response) {
            textView.setText(response.json.toString());
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
