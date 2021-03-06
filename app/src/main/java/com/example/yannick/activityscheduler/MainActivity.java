package com.example.yannick.activityscheduler;

import android.app.Activity;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.View;
import android.widget.Toast;

import com.example.yannick.activityscheduler.adapter.RvMainAdapter;
import com.example.yannick.activityscheduler.model.Card;
import com.example.yannick.activityscheduler.model.CustomActivity;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final int ADD_DIALOG_RC = 1;
    private RecyclerView recList;
    private LinearLayoutManager layoutManager = null;
    private RvMainAdapter cardAdapter = null;
    private ArrayList<Card> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        recList = findViewById(R.id.rv_main);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(layoutManager);

        items = createList(1);
        cardAdapter = new RvMainAdapter(items);
        recList.setAdapter(cardAdapter);
    }

    private ArrayList<Card> createList(int size) {
        ArrayList<Card> result = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            final Card c = new Card();
            c.setTitle("Titel " + i);
            c.setActivated(true);
            c.addActivity(new CustomActivity(ActivityType.WIFI));
            c.addActivity(new CustomActivity(ActivityType.BLUETOOTH));
            c.addActivity(new CustomActivity(ActivityType.AIRPLANE));
            c.addActivity(new CustomActivity(ActivityType.RINGTONE));

            result.add(c);
        }

        return result;
    }

    public void fab_add_click(View view) {
        Intent intent = new Intent(this, AddDialog.class);
        startActivityForResult(intent, ADD_DIALOG_RC);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ADD_DIALOG_RC: {
                if (resultCode == Activity.RESULT_OK) {
                    Serializable object = data.getSerializableExtra(getString(R.string.card_extra));
                    if (object instanceof Card) {
                        Card card = (Card) object;
                        items.add(card);
                        cardAdapter.notifyDataSetChanged();
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
