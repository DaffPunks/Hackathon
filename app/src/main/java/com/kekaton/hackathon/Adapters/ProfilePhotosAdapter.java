package com.kekaton.hackathon.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.kekaton.hackathon.Model.Photo;
import com.kekaton.hackathon.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProfilePhotosAdapter extends RecyclerView.Adapter<ProfilePhotosAdapter.PhotoViewHolder> {
    private List<Photo> list;
    private Context context;

    private int checkedPhoto = -1;

    public ProfilePhotosAdapter(List<Photo> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_photo_view, parent, false);
        PhotoViewHolder pvh = new PhotoViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final PhotoViewHolder holder, final int position) {
        Picasso.with(context).load(list.get(position).getUrl()).resize(300, 300).centerCrop().into(holder.photo);

        holder.photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkedPhoto = position;
                String chosenPhotoUrl = list.get(checkedPhoto).getUrl();
                Picasso.with(context).load(chosenPhotoUrl).resize(350, 350).centerCrop().into(holder.photo);
                Toast.makeText(context, "Position :" + checkedPhoto, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.photo) ImageView photo;

        PhotoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
