package com.example.yannick.activityscheduler.adapter;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yannick.activityscheduler.ActivityType;
import com.example.yannick.activityscheduler.R;
import com.example.yannick.activityscheduler.model.Card;
import com.example.yannick.activityscheduler.model.CustomActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class RvMainAdapter extends RecyclerView.Adapter<RvMainAdapter.CardViewHolder> {
    private ArrayList<Card> cardList;

    public RvMainAdapter(ArrayList<Card> cardList) {
        this.cardList = cardList;
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    @Override
    public void onBindViewHolder(final CardViewHolder cardViewHolder, final int position) {
        final Context context = cardViewHolder.itemView.getContext();
        final Card c = cardList.get(position);
        ArrayList<CustomActivity> activities = cardList.get(position).getActivities();

        cardViewHolder.title.setText(c.getTitle());
        cardViewHolder.activated.setChecked(c.isActivated());

        cardViewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        for(ActivityType activityType : ActivityType.values()){
            cardViewHolder.itemView.findViewById(activityType.getChipId()).setVisibility(View.GONE);
        }


        for(CustomActivity activity : activities){
            Chip chip = cardViewHolder.itemView.findViewById(activity.getType().getChipId());

            if(activity.isOn()){
                chip.setChipBackgroundColorResource(R.color.colorPrimary);
            }

            chip.setVisibility(View.VISIBLE);
        }

        cardViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardList.remove(position);
                notifyDataSetChanged();

                Snackbar snackbar = Snackbar.make(cardViewHolder.itemView, context.getString(R.string.card_deleted, c.getTitle()), Snackbar.LENGTH_SHORT);
                snackbar.setAction(context.getString(R.string.undo), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cardList.add(c);
                        notifyDataSetChanged();
                    }
                });
                snackbar.show();
            }
        });
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.cv_main_item, viewGroup, false);

        return new CardViewHolder(itemView);
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        Switch activated;
        MaterialButton edit;
        MaterialButton delete;
        Chip ch_bluetooth, ch_airplane, ch_ringtone, ch_wifi;

        public CardViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.tv_title);
            activated = v.findViewById(R.id.sw_activate);
            edit = v.findViewById(R.id.bt_edit);
            delete = v.findViewById(R.id.bt_delete);

            ch_bluetooth = v.findViewById(R.id.ch_bluetooth);
            ch_airplane = v.findViewById(R.id.ch_airplane);
            ch_ringtone = v.findViewById(R.id.ch_ringtone);
            ch_wifi = v.findViewById(R.id.ch_wifi);

        }
    }
}
