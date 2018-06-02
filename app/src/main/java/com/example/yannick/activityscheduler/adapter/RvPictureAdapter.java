package com.example.yannick.activityscheduler.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.yannick.activityscheduler.R;
import com.example.yannick.activityscheduler.model.CustomActivity;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class RvPictureAdapter extends RecyclerView.Adapter<RvPictureAdapter.RvCvPictureViewHolder> {

    private ArrayList<CustomActivity> activities;

    public RvPictureAdapter(ArrayList<CustomActivity> activities) {
        this.activities = activities;
    }

    @Override
    public RvCvPictureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_picture_item, parent, false);

        return new RvCvPictureViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RvCvPictureViewHolder holder, int position) {
        CustomActivity customActivity = activities.get(position);
        holder.iv_picture.setImageResource(customActivity.getType().getIconId());
    }

    @Override
    public int getItemCount() {
        return activities.size();
    }

    class RvCvPictureViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_picture;

        public RvCvPictureViewHolder(View itemView) {
            super(itemView);

            iv_picture = itemView.findViewById(R.id.iv_picture);
        }
    }
}
