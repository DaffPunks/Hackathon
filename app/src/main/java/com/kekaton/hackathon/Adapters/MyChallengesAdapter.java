package com.kekaton.hackathon.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kekaton.hackathon.Model.Challenge;
import com.kekaton.hackathon.R;
import com.kekaton.hackathon.Util.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyChallengesAdapter extends RecyclerView.Adapter<MyChallengesAdapter.PersonViewHolder> {
    private List<Challenge> list;
    private Context context;

    public MyChallengesAdapter(List<Challenge> list, Context context){
        this.list = list;
        this.context = context;

    }


    @Override
    public MyChallengesAdapter.PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mychallengeview, parent, false);
        MyChallengesAdapter.PersonViewHolder pvh = new MyChallengesAdapter.PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final MyChallengesAdapter.PersonViewHolder holder, final int position) {

        holder.descText.setText(list.get(position).getDescription());

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.get(position).getGoal();
            }
        });
        /*holder.name.setText(list.get(position).getFName() + " " + list.get(position).getLName());


        //holder.progressText.setText(list.get(position).getProgress() + "/" + list.get(position).getGoal());

        holder.progressBar.setMax(list.get(position).getGoal());
        holder.progressBar.setProgress(list.get(position).getProgress());

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        Picasso.with(context).load(list.get(position).getProfilePhoto()).fit().transform(new CircleTransform()).into(holder.profileImg);*/
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.card_view)
        CardView card;
        @Bind(R.id.textView4)
        TextView descText;

        PersonViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}