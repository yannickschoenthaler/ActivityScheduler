package com.example.yannick.activityscheduler;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.yannick.activityscheduler.adapter.RvAddDialogAdapter;
import com.example.yannick.activityscheduler.model.Card;
import com.example.yannick.activityscheduler.model.CustomActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AddDialog extends AppCompatActivity {
    private Toolbar toolbar;
    private Card card;
    private RecyclerView rv_activities;
    private RvAddDialogAdapter rv_add_dialog_adapter;
    private LinearLayoutManager rv_layout_manager;
    private Resources resources;
    private boolean fab_menu_opened = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_dialog);
        setTitle(getString(R.string.add_dialog_title));

        resources = AddDialog.this.getResources();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        card = new Card("Test", true, null);

        rv_activities = findViewById(R.id.rv_activities);
        rv_layout_manager = new LinearLayoutManager(AddDialog.this);
        rv_add_dialog_adapter = new RvAddDialogAdapter(card.getActivities());

        rv_activities.setLayoutManager(rv_layout_manager);
        rv_activities.setAdapter(rv_add_dialog_adapter);
    }

    public void fab_add_activity_click(View view){
        ArrayList<FloatingActionButton> fabs = new ArrayList<>();
        fabs.add((FloatingActionButton) findViewById(R.id.fab_add_airplane));
        fabs.add((FloatingActionButton) findViewById(R.id.fab_add_bluetooth));
        fabs.add((FloatingActionButton) findViewById(R.id.fab_add_ringtone));
        fabs.add((FloatingActionButton) findViewById(R.id.fab_add_wifi));

        FloatingActionButton fab_add_activity = (FloatingActionButton)view;

        for(FloatingActionButton fab : fabs){
            if(fab_menu_opened){
                fab.setVisibility(View.GONE);

            }else{
                fab.setVisibility(View.VISIBLE);

            }
        }

        if (fab_menu_opened) {
            fab_add_activity.setImageDrawable(resources.getDrawable(R.drawable.ic_add_white_24dp));
        } else {
            fab_add_activity.setImageDrawable(resources.getDrawable(R.drawable.ic_close_white_24dp));
        }

        fab_menu_opened = !fab_menu_opened;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_dialog_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_save: {
                Intent intent = new Intent(AddDialog.this, MainActivity.class);
                intent.putExtra(getString(R.string.card_extra), card);

                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
