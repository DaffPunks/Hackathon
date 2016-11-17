package com.kekaton.hackathon.Fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kekaton.hackathon.Adapters.ChallengesAdapter;
import com.kekaton.hackathon.Model.Challenge;
import com.kekaton.hackathon.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ChallengesFragment extends BaseFragment {
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    boolean isCompleted;

    List<Challenge> list;

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

        list = new ArrayList<>();
        list.add(new Challenge(150, 30, "Въебу лыжами Раисе хуисе, дырявой преподавательнице физры КемГУ", 7634482));
        list.add(new Challenge(10, 4, "Кину гавнецоу", 7634482));
        list.add(new Challenge(121, 33, "Кину гавнецоу", 7634482));

        ChallengesAdapter adapter = new ChallengesAdapter(list, getActivity());
        recyclerView.setAdapter(adapter);

        return view;
    }


}

