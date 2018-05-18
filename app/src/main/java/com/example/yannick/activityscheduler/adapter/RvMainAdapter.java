package com.example.yannick.activityscheduler.adapter;

import android.support.v7.widget.RecyclerView;
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

import java.util.ArrayList;

public class RvMainAdapter extends RecyclerView.Adapter<RvMainAdapter.CardViewHolder> {
    private ArrayList<Card> cardList;

    public RvMainAdapter(ArrayList<Card> cardList) {
        this.cardList = cardList;
    }

    public void addItem(Card c) {
        this.cardList.add(c);
    }

    public void removeItem(Card c){
        this.cardList.remove(c);
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    @Override
    public void onBindViewHolder(final CardViewHolder cardViewHolder, int i) {
        final Card c = cardList.get(i);
        cardViewHolder.title.setText(c.title);
        cardViewHolder.activated.setChecked(c.activated);
        cardViewHolder.picture.setImageBitmap(c.picture);
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
        protected ImageButton edit;
        protected ImageButton delete;

        public CardViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.tv_title);
            activated = (Switch) v.findViewById(R.id.sw_activate);
            picture = (ImageView) v.findViewById(R.id.iv_cvpicture);
            edit = (ImageButton) v.findViewById(R.id.ib_edit);
            delete = (ImageButton) v.findViewById(R.id.ib_delete);
        }
    }
}
