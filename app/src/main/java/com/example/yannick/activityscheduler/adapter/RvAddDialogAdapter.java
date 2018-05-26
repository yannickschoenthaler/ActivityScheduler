package com.example.yannick.activityscheduler.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yannick.activityscheduler.R;
import com.example.yannick.activityscheduler.model.Card;
import com.example.yannick.activityscheduler.model.CustomActivity;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class RvAddDialogAdapter extends RecyclerView.Adapter<RvAddDialogAdapter.RvAddDialogViewHolder> {

    private ArrayList<CustomActivity> activities;

    public RvAddDialogAdapter(ArrayList<CustomActivity> activities) {
        this.activities = activities;
    }

    @Override
    public RvAddDialogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_add_dialog_item, parent,false);
        return new RvAddDialogViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return activities.size();
    }

    @Override
    public void onBindViewHolder(RvAddDialogViewHolder holder, int position) {
        CustomActivity activity = activities.get(position);
        Context context = holder.itemView.getContext();
        Resources resources = context.getResources();
        String[] types = resources.getStringArray(R.array.custom_activitie_types);

        holder.tv_type.setText(types[activity.getType()]);

    }

    class RvAddDialogViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_type;

        public RvAddDialogViewHolder(View itemView) {
            super(itemView);

            tv_type = itemView.findViewById(R.id.tv_type);
        }
    }
}
