package com.example.yannick.activityscheduler.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.yannick.activityscheduler.ActivityTypes;
import com.example.yannick.activityscheduler.R;
import com.example.yannick.activityscheduler.model.CustomActivity;

import java.util.ArrayList;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class RvAddDialogAdapter extends RecyclerView.Adapter<RvAddDialogAdapter.RvAddDialogViewHolder> {

    private ArrayList<CustomActivity> activities;
    private String[] activityTypes;
    private boolean isExpanded = false;

    public RvAddDialogAdapter(ArrayList<CustomActivity> activities) {
        this.activities = activities;
    }

    @Override
    public RvAddDialogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_add_dialog_item, parent, false);
        return new RvAddDialogViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return activities.size();
    }

    @Override
    public void onBindViewHolder(final RvAddDialogViewHolder holder, int position) {
        final CustomActivity activity = activities.get(position);
        Context context = holder.itemView.getContext();
        Resources resources = context.getResources();
        activityTypes = resources.getStringArray(R.array.custom_activity_types);

        holder.tv_type.setText(activityTypes[activity.getType()]);

        holder.tv_status.setText(context.getString(R.string.status_text, activityTypes[activity.getType()]));

        holder.cl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isExpanded = !isExpanded;

                if (isExpanded) {
                    if (activity.getType() == ActivityTypes.RINGTONE) {
                        holder.cl_detail_ringtone.setVisibility(View.VISIBLE);

                    } else {
                        holder.cl_detail_normal.setVisibility(View.VISIBLE);
                    }

                    holder.ib_arrow.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                } else {
                    holder.cl_detail_normal.setVisibility(View.GONE);
                    holder.cl_detail_ringtone.setVisibility(View.GONE);
                    holder.ib_arrow.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                }
            }
        });

        switch (activity.getType()) {
            //Bluetooth
            case ActivityTypes.BLUETOOTH: {
                holder.tv_activity_icon.setImageDrawable(resources.getDrawable(R.drawable.ic_bluetooth_gray_24dp));
                break;
            }
            //Airplane
            case ActivityTypes.AIRPLANE: {
                holder.tv_activity_icon.setImageDrawable(resources.getDrawable(R.drawable.ic_airplanemode_active_gray_24dp));
                break;
            }
            //Ringtone
            case ActivityTypes.RINGTONE: {
                holder.tv_activity_icon.setImageDrawable(resources.getDrawable(R.drawable.ic_volume_up_gray_24dp));
                break;
            }
            //WIFI
            case ActivityTypes.WIFI: {
                holder.tv_activity_icon.setImageDrawable(resources.getDrawable(R.drawable.ic_network_wifi_gray_24dp));
                break;
            }
        }
    }

    public CustomActivity getItem(int position) {
        return activities.get(position);
    }

    class RvAddDialogViewHolder extends RecyclerView.ViewHolder {
        TextView tv_type, tv_status;
        ImageView tv_activity_icon, ib_arrow;
        Switch sw_status;
        ConstraintLayout cl_detail_normal, cl_detail_ringtone, cl_item, cl_detail;

        public RvAddDialogViewHolder(View itemView) {
            super(itemView);

            tv_type = itemView.findViewById(R.id.tv_type);
            tv_activity_icon = itemView.findViewById(R.id.iv_activity_icon);
            tv_status = itemView.findViewById(R.id.tv_status);
            sw_status = itemView.findViewById(R.id.sw_status);
            ib_arrow = itemView.findViewById(R.id.ib_arrow);
            cl_detail_normal = itemView.findViewById(R.id.cl_detail);
            cl_detail_ringtone = itemView.findViewById(R.id.cl_detail_ringtone);
            cl_item = itemView.findViewById(R.id.cl_item);
            cl_detail = itemView.findViewById(R.id.cl_detail);
        }
    }
}
