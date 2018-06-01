package com.example.yannick.activityscheduler.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.example.yannick.activityscheduler.ActivityType;
import com.example.yannick.activityscheduler.R;
import com.example.yannick.activityscheduler.model.CustomActivity;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class RvAddDialogAdapter extends RecyclerView.Adapter<RvAddDialogAdapter.HeaderViewHolder> {

    private ArrayList<CustomActivity> activities;
    private String[] activityTypes;

    private enum ViewType {
        NORMAL(0), RINGTONE(1);

        private int value;

        ViewType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public RvAddDialogAdapter(ArrayList<CustomActivity> activities) {
        this.activities = activities;
    }

    @Override
    public HeaderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        HeaderViewHolder viewHolder = null;

        if (viewType == ViewType.NORMAL.getValue()) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.rv_add_dialog_item, parent, false);
            viewHolder = new SimpleViewHolder(itemView);

        } else if (viewType == ViewType.RINGTONE.getValue()) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.rv_add_dialog_item_ringtone, parent, false);
            viewHolder = new RingtoneViewHolder(itemView);
        }

        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        ViewType ret = ViewType.NORMAL;

        CustomActivity activity = activities.get(position);

        if (activity.getType() == ActivityType.RINGTONE) {
            ret = ViewType.RINGTONE;
        }

        return ret.getValue();
    }

    @Override
    public int getItemCount() {
        return activities.size();
    }

    @Override
    public void onBindViewHolder(final HeaderViewHolder holder, int position) {
        CustomActivity activity = activities.get(position);
        Context context = holder.itemView.getContext();
        Resources resources = context.getResources();
        ActivityType activityType = activity.getType();

        int viewType = getItemViewType(position);

        activityTypes = resources.getStringArray(R.array.custom_activity_types);

        holder.iv_activity_icon.setImageResource(activityType.getIconId());
        holder.tv_type.setText(activityTypes[activityType.getId()]);

        if (viewType == ViewType.NORMAL.getValue()) {

            SimpleViewHolder simpleViewHolder = (SimpleViewHolder) holder;

        } else if (viewType == ViewType.RINGTONE.getValue()) {

            RingtoneViewHolder ringtoneViewHolder = (RingtoneViewHolder) holder;

        }

        //holder.update(activity);

        /*holder.cl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.setExpanded(!activity.isExpanded());

                if (activity.isExpanded()) {
                    if (activity.getIntFromType() == ActivityTypes1.RINGTONE) {
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
        });*/


    }

    public CustomActivity getItem(int position) {
        return activities.get(position);
    }


    class HeaderViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_type;
        private ImageView iv_activity_icon, ib_arrow;

        public HeaderViewHolder(View itemView) {
            super(itemView);

            tv_type = itemView.findViewById(R.id.tv_type);
            iv_activity_icon = itemView.findViewById(R.id.iv_activity_icon);
            ib_arrow = itemView.findViewById(R.id.ib_arrow);
        }

        /*public void update(CustomActivity activity) {
            ActivityType type = activity.getType();
            tv_type.setText(activityTypes[type.getId()]);
            iv_activity_icon.setImageResource(type.getIconId());
        }*/
    }

    class SimpleViewHolder extends HeaderViewHolder {
        private TextView tv_status;
        private Switch sw_status;

        public SimpleViewHolder(View itemView) {
            super(itemView);

            tv_status = itemView.findViewById(R.id.tv_status);
            sw_status = itemView.findViewById(R.id.sw_status);
        }

        /*public void update(CustomActivity activity) {
            super.update(activity);
            ActivityType type = activity.getType();
            tv_status.setText(itemView.getContext().getString(R.string.status_text, activityTypes[type.getId()]));
            // TODO sw_status.setChecked(activity);
        }*/
    }

    class RingtoneViewHolder extends HeaderViewHolder {
        private SeekBar ring, media, alarm;

        public RingtoneViewHolder(View itemView) {
            super(itemView);

            ring = itemView.findViewById(R.id.ringtoneBar);
            media = itemView.findViewById(R.id.mediaBar);
            alarm = itemView.findViewById(R.id.alarmBar);
        }

        /*@Override
        public void update(CustomActivity activity) {
            super.update(activity);
            // TODO set seek bars
        }*/
    }
}
