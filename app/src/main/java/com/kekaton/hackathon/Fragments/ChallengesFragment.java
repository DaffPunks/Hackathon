package com.kekaton.hackathon.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kekaton.hackathon.R;

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

        if(isCompleted){
            textView.setText("Тут выполненные");
        } else {
            textView.setText("Тут текущие");
        }

        return view;
    }
}
