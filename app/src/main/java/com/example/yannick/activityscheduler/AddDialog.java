package com.example.yannick.activityscheduler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.yannick.activityscheduler.adapter.RvAddDialogAdapter;
import com.example.yannick.activityscheduler.model.Card;
import com.example.yannick.activityscheduler.model.CustomActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AddDialog extends AppCompatActivity {
    private Toolbar toolbar;
    private ConstraintLayout constraintLayout;
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
    private ArrayList<FloatingActionButton> visibleFabs;
    private ArrayList<FloatingActionButton> allFabs;
    private String[] activityTypes;
    private boolean fab_menu_opened = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_dialog);
        setTitle(getString(R.string.add_dialog_title));

        resources = AddDialog.this.getResources();

        //Get ActivityTypes
        activityTypes = resources.getStringArray(R.array.custom_activity_types);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //ConstraintLayout-INIT and OnClick
        constraintLayout = findViewById(R.id.cl_dialog);
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab_menu_opened = false;
                updateFabsMenuHiddenVisible();
            }
        });

        //FAB-INIT
        airplaneButton = findViewById(R.id.fab_add_airplane);
        bluetoothButton = findViewById(R.id.fab_add_bluetooth);
        ringtoneButton = findViewById(R.id.fab_add_ringtone);
        wifiButton = findViewById(R.id.fab_add_wifi);

        //Add visibleFabs to arraylist
        visibleFabs = new ArrayList<>();
        visibleFabs.add(airplaneButton);
        visibleFabs.add(bluetoothButton);
        visibleFabs.add(ringtoneButton);
        visibleFabs.add(wifiButton);

        allFabs = visibleFabs;

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
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                // Draw the red delete background
                View itemView = viewHolder.itemView;
                float itemHeight = itemView.getBottom() - itemView.getTop();
                boolean isCanceled = dX == 0f && !isCurrentlyActive;
                Drawable icon = ContextCompat.getDrawable(AddDialog.this, R.drawable.ic_delete_white_24dp);
                int intrinsicHeight = icon.getIntrinsicHeight();
                int intrinsicWidth = icon.getIntrinsicWidth();

                if (isCanceled) {
                    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                    paint.setColor(Color.TRANSPARENT);
                    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
                    paint.setAntiAlias(true);

                    c.drawRect(itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(),
                            (float) itemView.getBottom(), paint);
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }


                ColorDrawable background = new ColorDrawable();
                background.setColor(resources.getColor(R.color.red));
                background.setBounds(itemView.getRight() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
                background.draw(c);

                // Calculate position of delete icon
                int deleteIconTop = (int) (itemView.getTop() + (itemHeight - intrinsicHeight) / 2);
                int deleteIconMargin = (int) ((itemHeight - intrinsicHeight) / 2);
                int deleteIconLeft = itemView.getRight() - deleteIconMargin - intrinsicWidth;
                int deleteIconRight = itemView.getRight() - deleteIconMargin;
                int deleteIconBottom = deleteIconTop + intrinsicHeight;

                // Draw the delete icon
                icon.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom);
                icon.draw(c);

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

            @Override
            public int convertToAbsoluteDirection(int flags, int layoutDirection) {
                return super.convertToAbsoluteDirection(flags, layoutDirection);
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction == ItemTouchHelper.LEFT) {
                    final CustomActivity selectedItem = rv_add_dialog_adapter.getItem(viewHolder.getAdapterPosition());
                    card.removeActivity(selectedItem);
                    rv_add_dialog_adapter.notifyDataSetChanged();

                    final FloatingActionButton fab_to_insert = findViewById(getIdFromType(selectedItem.getType()));
                    visibleFabs.add(fab_to_insert);
                    updateFabs();

                    Snackbar snackbar = Snackbar.make(findViewById(R.id.cl_dialog), getString(R.string.activity_deleted, activityTypes[selectedItem.getType()]), Snackbar.LENGTH_SHORT);
                    snackbar.setAction(getString(R.string.undo), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            visibleFabs.remove(fab_to_insert);
                            card.addActivity(selectedItem);
                            rv_add_dialog_adapter.notifyDataSetChanged();
                            updateFabsMenuHiddenVisible();
                        }
                    });
                    snackbar.show();
                }
            }
        };
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(rv_activities);

    }

    private int getTypeFromId(int id) {
        int type = ActivityTypes.BLUETOOTH;

        switch (id) {
            case R.id.fab_add_wifi: {

                type = ActivityTypes.WIFI;
                break;
            }
            case R.id.fab_add_airplane: {
                type = ActivityTypes.AIRPLANE;
                break;
            }
            case R.id.fab_add_ringtone: {
                type = ActivityTypes.RINGTONE;
                break;
            }
        }

        return type;
    }

    private int getIdFromType(int type) {
        int id = R.id.fab_add_bluetooth;
        switch (type) {
            case ActivityTypes.WIFI: {
                id = R.id.fab_add_wifi;
                break;
            }
            case ActivityTypes.AIRPLANE: {
                id = R.id.fab_add_airplane;
                break;
            }
            case ActivityTypes.RINGTONE: {
                id = R.id.fab_add_ringtone;
                break;
            }
        }

        return id;
    }

    private void updateFabs() {
        for (FloatingActionButton fab : allFabs) {
            fab.setVisibility(View.GONE);
        }

        for (FloatingActionButton fab : visibleFabs) {
            fab.setVisibility(View.VISIBLE);
        }
    }

    public void fab_activity_click(View view) {
        FloatingActionButton fab = (FloatingActionButton) view;

        int type = getTypeFromId(fab.getId());

        visibleFabs.remove(fab);
        card.addActivity(new CustomActivity(type, null));
        rv_add_dialog_adapter.notifyDataSetChanged();
        fab.setVisibility(View.GONE);
    }

    public void fab_add_activity_click(View view) {
        FloatingActionButton fab_add_activity = (FloatingActionButton) view;

        updateFabsMenuHiddenVisible();

        if (fab_menu_opened) {
            fab_add_activity.setImageDrawable(resources.getDrawable(R.drawable.ic_event_white_24dp));
        } else {
            fab_add_activity.setImageDrawable(resources.getDrawable(R.drawable.ic_close_white_24dp));
        }

        fab_menu_opened = !fab_menu_opened;
    }

    private void updateFabsMenuHiddenVisible() {
        for (FloatingActionButton fab : visibleFabs) {
            if (fab_menu_opened) {
                fab.setVisibility(View.GONE);

            } else {
                fab.setVisibility(View.VISIBLE);
            }
        }
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
