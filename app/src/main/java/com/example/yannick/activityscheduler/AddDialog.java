package com.example.yannick.activityscheduler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.yannick.activityscheduler.model.Card;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AddDialog extends AppCompatActivity {
    private Toolbar toolbar;
    private Card card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_dialog);
        setTitle("Add Activity");

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
