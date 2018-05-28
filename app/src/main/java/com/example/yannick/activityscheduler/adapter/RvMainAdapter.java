package com.example.yannick.activityscheduler.adapter;

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
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class RvMainAdapter extends RecyclerView.Adapter<RvMainAdapter.CardViewHolder> {
    private ArrayList<Card> cardList;

    public RvMainAdapter(ArrayList<Card> cardList) {
        this.cardList = cardList;
    }

    public void addItem(Card c) {
        this.cardList.add(c);
    }

    public void removeItem(Card c) {
        this.cardList.remove(c);
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    @Override
    public void onBindViewHolder(final CardViewHolder cardViewHolder, final int position) {
        final Context context = cardViewHolder.itemView.getContext();
        final Card c = cardList.get(position);
        cardViewHolder.title.setText(c.getTitle());
        cardViewHolder.activated.setChecked(c.isActivated());
        cardViewHolder.picture.setImageBitmap(c.getPicture());

        cardViewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cardViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(context)
                        .setMessage(context.getString(R.string.delete_card, c.getTitle()))
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                cardList.remove(position);
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton(R.string.no, null)
                        .create();

                alertDialog.show();

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
        protected ImageView picture;
        protected MaterialButton edit;
        protected MaterialButton delete;

        public CardViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.tv_title);
            activated = v.findViewById(R.id.sw_activate);
            picture = v.findViewById(R.id.iv_cvpicture);
            edit = v.findViewById(R.id.bt_edit);
            delete = v.findViewById(R.id.bt_delete);
        }
    }
}
