package com.example.yannick.activityscheduler.adapter;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yannick.activityscheduler.R;
import com.example.yannick.activityscheduler.model.Card;
import com.example.yannick.activityscheduler.model.CustomActivity;
import com.google.android.material.button.MaterialButton;
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
        ArrayList<CustomActivity> activities = c.getActivities();
        int activityCount = activities.size();
        cardViewHolder.title.setText(c.getTitle());
        cardViewHolder.activated.setChecked(c.isActivated());

        cardViewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //Picture RecyclerView
        int columnCount = activityCount;

        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, columnCount);
        RvPictureAdapter rv_picture_adapter = new RvPictureAdapter(activities);
        cardViewHolder.rv_picture.setLayoutManager(gridLayoutManager);
        cardViewHolder.rv_picture.setAdapter(rv_picture_adapter);

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
        protected TextView title;
        protected Switch activated;
        protected MaterialButton edit;
        protected MaterialButton delete;
        protected RecyclerView rv_picture;


        public CardViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.tv_title);
            activated = v.findViewById(R.id.sw_activate);
            edit = v.findViewById(R.id.bt_edit);
            delete = v.findViewById(R.id.bt_delete);
            rv_picture = v.findViewById(R.id.rv_picture);

        }
    }
}
