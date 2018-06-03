package com.example.yannick.activityscheduler;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.yannick.activityscheduler.adapter.RvAddDialogAdapter;
import com.example.yannick.activityscheduler.model.Card;
import com.example.yannick.activityscheduler.model.CustomActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AddDialog extends AppCompatActivity {
    private Toolbar toolbar;
    private ConstraintLayout constraintLayout, cl_time, cl_date;
    private Card card;
    private RecyclerView rv_activities;
    private RvAddDialogAdapter rv_add_dialog_adapter;
    private LinearLayoutManager rv_layout_manager;
    private EditText titleInputField;
    private Resources resources;
    private FloatingActionButton airplaneButton, bluetoothButton, ringtoneButton, wifiButton, fabAddActivity;
    private HashMap<FloatingActionButton, Boolean> fabCollection;
    private String[] activityTypes;
    private Snackbar undoSnackbar;
    private boolean fab_menu_opened = false;
    private TextView tv_date, tv_time;
    private Switch sw_daily;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_dialog);
        setTitle(getString(R.string.add_dialog_title));

        resources = AddDialog.this.getResources();

        //Get ActivityType
        activityTypes = resources.getStringArray(R.array.custom_activity_types);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Init
        fabAddActivity = findViewById(R.id.fab_add_activity);

        //Set fabAddActivity OnClickListener
        fabAddActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (undoSnackbar != null)
                    undoSnackbar.dismiss();
                fabMenuToggle();
            }
        });

        //ConstraintLayout-INIT and OnClick
        constraintLayout = findViewById(R.id.cl_dialog);

        //FAB-INIT
        airplaneButton = findViewById(R.id.fab_add_airplane);
        bluetoothButton = findViewById(R.id.fab_add_bluetooth);
        ringtoneButton = findViewById(R.id.fab_add_ringtone);
        wifiButton = findViewById(R.id.fab_add_wifi);

        //Add visibleFabs to arraylist
        fabCollection = new HashMap<>();
        fabCollection.put(airplaneButton, true);
        fabCollection.put(bluetoothButton, true);
        fabCollection.put(ringtoneButton, true);
        fabCollection.put(wifiButton, true);

        toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        titleInputField = findViewById(R.id.et_activity_title);

        card = new Card("", true);

        rv_activities = findViewById(R.id.rv_activities);
        rv_layout_manager = new LinearLayoutManager(AddDialog.this);
        rv_add_dialog_adapter = new RvAddDialogAdapter(card.getActivities());

        rv_activities.setLayoutManager(rv_layout_manager);
        rv_activities.setAdapter(rv_add_dialog_adapter);

        //TextViews for time and date
        tv_date = findViewById(R.id.tv_date);
        tv_time = findViewById(R.id.tv_time);
        cl_date = findViewById(R.id.cl_date);
        cl_time = findViewById(R.id.cl_time);

        Locale locale = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = resources.getConfiguration().getLocales().get(0);

        } else {
            locale = resources.getConfiguration().locale;
        }

        final Calendar calendar = card.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("E, u. MMMM y", locale);
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", locale);
        tv_date.setText(dateFormat.format(calendar.getTime()));
        tv_time.setText(timeFormat.format(calendar.getTime()));

        cl_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddDialog.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        card.setDate(year, month, day);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();
            }
        });

        cl_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddDialog.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        card.setTime(hour, minute);
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
                timePickerDialog.show();
            }
        });

        //Daily switch
        sw_daily = findViewById(R.id.sw_daily);
        sw_daily.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                card.toggleDaily();
            }
        });

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
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction == ItemTouchHelper.LEFT) {
                    final CustomActivity selectedItem = rv_add_dialog_adapter.getItem(viewHolder.getAdapterPosition());

                    card.removeActivity(selectedItem);

                    rv_add_dialog_adapter.notifyDataSetChanged();

                    final FloatingActionButton fab_to_insert = findViewById(selectedItem.getType().getFabId());
                    fabCollection.put(fab_to_insert, true);

                    //Close Menu Fab and update other Fabs
                    fab_menu_opened = true;
                    fabMenuToggle();
                    updateFabs();

                    undoSnackbar = Snackbar.make(findViewById(R.id.cl_dialog),
                            getString(R.string.activity_deleted, activityTypes[selectedItem.getType().getId()]),
                            Snackbar.LENGTH_SHORT);
                    undoSnackbar.setAction(getString(R.string.undo), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            fabCollection.put(fab_to_insert, false);

                            card.addActivity(selectedItem);
                            rv_add_dialog_adapter.notifyDataSetChanged();
                            updateFabs();
                        }
                    });
                    undoSnackbar.show();
                }
            }
        };
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(rv_activities);
    }

    private void updateFabs() {
        for (HashMap.Entry<FloatingActionButton, Boolean> entry : fabCollection.entrySet()) {
            if (entry.getValue() && fab_menu_opened)
                entry.getKey().setVisibility(View.VISIBLE);
            else
                entry.getKey().setVisibility(View.GONE);
        }
    }

    public void fab_activity_click(View view) {
        FloatingActionButton fab = (FloatingActionButton) view;

        ActivityType type = ActivityType.valueOfFabId(fab.getId());

        fabCollection.put(fab, false);

        card.addActivity(new CustomActivity(type));
        rv_add_dialog_adapter.notifyDataSetChanged();
        fab.setVisibility(View.GONE);
    }

    private void fabMenuToggle() {
        fab_menu_opened = !fab_menu_opened;

        updateFabs();

        if (!fab_menu_opened) {
            fabAddActivity.setImageDrawable(resources.getDrawable(R.drawable.ic_event_white_24dp));
        } else {
            fabAddActivity.setImageDrawable(resources.getDrawable(R.drawable.ic_close_white_24dp));
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
                ArrayList<CustomActivity> activities = card.getActivities();

                if (activities != null && activities.size() > 0 && titleInputField.getText().length() > 0) {
                    Intent intent = new Intent(AddDialog.this, MainActivity.class);

                    //Get Cardinformation
                    String title = titleInputField.getText().toString();
                    card.setTitle(title);

                    intent.putExtra(getString(R.string.card_extra), card);

                    setResult(Activity.RESULT_OK, intent);
                    finish();

                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(AddDialog.this)
                            .setMessage(R.string.add_card_error).setPositiveButton(R.string.ok, null)
                            .create();

                    alertDialog.show();
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
