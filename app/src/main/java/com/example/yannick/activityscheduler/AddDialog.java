package com.example.yannick.activityscheduler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.yannick.activityscheduler.adapter.RvAddDialogAdapter;
import com.example.yannick.activityscheduler.model.Card;
import com.example.yannick.activityscheduler.model.CustomActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.UUID;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AddDialog extends AppCompatActivity {
    private Toolbar toolbar;
    private Card card;
    private RecyclerView rv_activities;
    private RvAddDialogAdapter rv_add_dialog_adapter;
    private LinearLayoutManager rv_layout_manager;
    private TextInputEditText titleInputField;
    private Resources resources;
    private FloatingActionButton airplaneButton;
    private FloatingActionButton bluetoothButton;
    private FloatingActionButton ringtoneButton;
    private FloatingActionButton wifiButton;
    private ArrayList<FloatingActionButton> fabs;
    private boolean fab_menu_opened = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_dialog);
        setTitle(getString(R.string.add_dialog_title));

        resources = AddDialog.this.getResources();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //FAB-INIT
        airplaneButton = findViewById(R.id.fab_add_airplane);
        bluetoothButton = findViewById(R.id.fab_add_bluetooth);
        ringtoneButton = findViewById(R.id.fab_add_ringtone);
        wifiButton = findViewById(R.id.fab_add_wifi);

        //Add fabs to arraylist
        fabs = new ArrayList<>();
        fabs.add(airplaneButton);
        fabs.add(bluetoothButton);
        fabs.add(ringtoneButton);
        fabs.add(wifiButton);

        toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        titleInputField = findViewById(R.id.ti_activity_title);

        card = new Card("", true, null);

        rv_activities = findViewById(R.id.rv_activities);
        rv_layout_manager = new LinearLayoutManager(AddDialog.this);
        rv_add_dialog_adapter = new RvAddDialogAdapter(card.getActivities());

        rv_activities.setLayoutManager(rv_layout_manager);
        rv_activities.setAdapter(rv_add_dialog_adapter);


        //Swipe to delete
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction == ItemTouchHelper.LEFT) {
                    final CustomActivity selectedItem = rv_add_dialog_adapter.getItem(viewHolder.getAdapterPosition());
                    Resources resources = getResources();
                    String[] types = resources.getStringArray(R.array.custom_activity_types);

                    AlertDialog deleteDialog = new AlertDialog.Builder(AddDialog.this)
                            .setMessage(getString(R.string.delete_card, types[selectedItem.getType()]))
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    card.removeActivity(selectedItem);
                                    rv_add_dialog_adapter.notifyDataSetChanged();

                                    FloatingActionButton fab_to_insert = findViewById(getIdFromType(selectedItem.getType()));
                                    fabs.add(fab_to_insert);
                                    updateFabs();
                                }
                            })
                            .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    rv_add_dialog_adapter.notifyDataSetChanged();
                                }
                            })
                            .create();

                    deleteDialog.show();

                }

            }
        };
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(rv_activities);

    }

    private int getTypeFromId(int id) {
        int type = 0;

        switch (id) {
            case R.id.fab_add_wifi: {

                type = 3;
                break;
            }
            case R.id.fab_add_airplane: {
                type = 1;
                break;
            }
            case R.id.fab_add_ringtone: {
                type = 2;
                break;
            }
        }

        return type;
    }

    private int getIdFromType(int type) {
        int id = R.id.fab_add_bluetooth;
        switch (type) {
            case 3: {

                id = R.id.fab_add_wifi;
                break;
            }
            case 1: {
                id = R.id.fab_add_airplane;
                break;
            }
            case 2: {
                id = R.id.fab_add_ringtone;
                break;
            }
        }

        return id;
    }

    private void updateFabs(){
        for (FloatingActionButton fab : fabs) {
            if (fab_menu_opened) {
                fab.setVisibility(View.GONE);

            } else {
                fab.setVisibility(View.VISIBLE);
            }
        }
    }

    public void fab_activity_click(View view) {
        FloatingActionButton fab = (FloatingActionButton) view;

        int type = getTypeFromId(fab.getId());

        fabs.remove(fab);
        card.addActivity(new CustomActivity(type, null));
        rv_add_dialog_adapter.notifyDataSetChanged();
        fab.setVisibility(View.GONE);
    }

    public void fab_add_activity_click(View view) {
        FloatingActionButton fab_add_activity = (FloatingActionButton) view;

        updateFabs();

        if (fab_menu_opened) {
            fab_add_activity.setImageDrawable(resources.getDrawable(R.drawable.ic_event_white_24dp));
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
        switch (item.getItemId()) {
            case R.id.action_save: {
                Intent intent = new Intent(AddDialog.this, MainActivity.class);

                //Get Cardinformation
                String title = titleInputField.getText().toString();
                card.setTitle(title);

                intent.putExtra(getString(R.string.card_extra), card);

                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
