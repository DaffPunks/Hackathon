package com.kekaton.hackathon.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kekaton.hackathon.API.VKApiCall;
import com.kekaton.hackathon.Activity.LoginActivity;
import com.kekaton.hackathon.Adapters.ProfilePhotosAdapter;
import com.kekaton.hackathon.MainActivity;
import com.kekaton.hackathon.Model.Photo;
import com.kekaton.hackathon.R;
import com.kekaton.hackathon.Util.CircleTransform;
import com.squareup.picasso.Picasso;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
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
    @Bind(R.id.photoProfile) ImageView photo;
    @Bind(R.id.profile_card)    CardView profile_card;
    @Bind(R.id.vk_auth)    CardView vkauth;
    @Bind(R.id.vk_no_auth) CardView vknoauth;
    @Bind(R.id.textView)   TextView nameView;
    @Bind(R.id.name2)   TextView nameView2;

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

        mToolbar.setTitle("Профиль");

        if(VKSdk.isLoggedIn()){
            ((ViewGroup) vkauth.getParent()).removeView(vkauth);

            SharedPreferences sharedPreferences = getMainActivity().getSharedPreferences("mysettings", MODE_PRIVATE);
            String firstName = sharedPreferences.getString("first_name", "mo");
            String lastName = sharedPreferences.getString("last_name", "noe");
            String photoMax = sharedPreferences.getString("photoMax", "");

            nameView.setText(firstName + " " + lastName);
            nameView2.setText(firstName + " " + lastName);

            Picasso.with(getContext()).load(photoMax).fit().transform(new CircleTransform()).into(photo);

            view.findViewById(R.id.vkLogout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    VKSdk.logout();
                    if (!VKSdk.isLoggedIn()) {
                        startActivity(new Intent(getMainActivity(), MainActivity.class));
                    } else {
                        Toast.makeText(getMainActivity(), "Error", Toast.LENGTH_LONG).show();
                    }
                }
            });

        } else {
            ((ViewGroup) profile_card.getParent()).removeView(profile_card);
            ((ViewGroup) vknoauth.getParent()).removeView(vknoauth);

            view.findViewById(R.id.vkLogin).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    VKSdk.login(getActivity(), sMyScope);
                }
            });

        }

        setupToolbarForFragment(mToolbar);

        return view;
    }
}
