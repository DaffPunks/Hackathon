package com.kekaton.hackathon.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kekaton.hackathon.R;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;

public class ProfileFragment extends BaseFragment {

    private static final String[] sMyScope = new String[]{
            VKScope.FRIENDS,
            VKScope.WALL,
            VKScope.PHOTOS,
            VKScope.NOHTTPS,
            VKScope.MESSAGES,
            VKScope.DOCS
    };

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
        view.findViewById(R.id.ButtonLogin).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                VKSdk.login(getActivity(), sMyScope);
            }
        });
        return view;
    }


}
