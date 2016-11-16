package com.kekaton.hackathon.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.kekaton.hackathon.R;

/**
 * Created by User on 15.11.2016.
 */
public class YoutubeFragment extends Fragment {
    private static final String API_KEY = "AIzaSyDtGSwjhnBEodc5HQBYuALvuHXBeDTKMbc";

    private static String VIDEO_ID = "GVFQrYNJMdM";

    YouTubePlayerSupportFragment youTubePlayerFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.you_tube_api, container, false);

        youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.youtube_layout, youTubePlayerFragment).commit();

        Button button = (Button) rootView.findViewById(R.id.button2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                youTubePlayerFragment.initialize(API_KEY, new OnInitializedListener() {

                    @Override
                    public void onInitializationSuccess(Provider provider, final YouTubePlayer player, boolean wasRestored) {
                        if (!wasRestored) {
                            player.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
                                @Override
                                public void onLoading() {

                                }

                                @Override
                                public void onLoaded(String s) {
                                    player.pause();
                                }

                                @Override
                                public void onAdStarted() {

                                }

                                @Override
                                public void onVideoStarted() {

                                }

                                @Override
                                public void onVideoEnded() {

                                }

                                @Override
                                public void onError(YouTubePlayer.ErrorReason errorReason) {

                                }
                            });
                            player.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
                            player.loadVideo(VIDEO_ID);
                        }
                    }

                    @Override
                    public void onInitializationFailure(Provider provider, YouTubeInitializationResult error) {
                        // YouTube error
                        String errorMessage = error.toString();
                        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
                        Log.d("errorMessage:", errorMessage);
                    }
                });
            }
        });

        return rootView;
    }
}
