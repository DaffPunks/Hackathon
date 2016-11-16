package com.kekaton.hackathon.Fragments;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kekaton.hackathon.R;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProfileFragment extends BaseFragment {

    private static final String[] sMyScope = new String[]{
            VKScope.FRIENDS,
            VKScope.WALL,
            VKScope.PHOTOS,
            VKScope.NOHTTPS,
            VKScope.MESSAGES,
            VKScope.DOCS
    };

    @Bind(R.id.toolbar) Toolbar mToolbar;

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
        ButterKnife.bind(this, view);

        mToolbar.setTitle("Profile");
        setupToolbarForFragment(mToolbar);

        view.findViewById(R.id.ButtonLogin).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                VKSdk.login(getActivity(), sMyScope);
            }
        });



        return view;
    }
}
