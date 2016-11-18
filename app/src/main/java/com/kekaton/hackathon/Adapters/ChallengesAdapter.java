package com.kekaton.hackathon.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kekaton.hackathon.API.VKApiCall;
import com.kekaton.hackathon.Model.Challenge;
import com.kekaton.hackathon.R;
import com.kekaton.hackathon.Util.CircleTransform;
import com.squareup.picasso.Picasso;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.StreamHandler;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ChallengesAdapter extends RecyclerView.Adapter<ChallengesAdapter.PersonViewHolder> {
    private List<Challenge> list;
    private Context context;

    public ChallengesAdapter(List<Challenge> list, Context context){
        this.list = list;
        this.context = context;

    }


    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.challenge_view, parent, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int position) {

        holder.name.setText(list.get(position).getFName() + " " + list.get(position).getLName());
        holder.descText.setText(list.get(position).getDescription());

        //holder.progressText.setText(list.get(position).getProgress() + "/" + list.get(position).getGoal());

        holder.progressBar.setMax(list.get(position).getGoal());
        holder.progressBar.setProgress(list.get(position).getProgress());

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        Picasso.with(context).load(list.get(position).getProfilePhoto()).fit().transform(new CircleTransform()).into(holder.profileImg);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.card_view)    CardView    card;
        //@Bind(R.id.textView4)    TextView    progressText;
        @Bind(R.id.description)  TextView    descText;
        @Bind(R.id.progressBar4) ProgressBar progressBar;
        @Bind(R.id.profileImg)   ImageView   profileImg;
        @Bind(R.id.textView6)   TextView    name;

        PersonViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
