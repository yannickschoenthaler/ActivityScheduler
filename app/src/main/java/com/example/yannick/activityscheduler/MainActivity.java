package com.example.yannick.activityscheduler;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.yannick.activityscheduler.adapter.RvMainAdapter;
import com.example.yannick.activityscheduler.model.Card;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recList = null;
    LinearLayoutManager layoutManager = null;
    RvMainAdapter cardAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        recList = (RecyclerView) findViewById(R.id.rv_main);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(layoutManager);

        cardAdapter = new RvMainAdapter(createList(1));
        recList.setAdapter(cardAdapter);
    }

    private ArrayList<Card> createList(int size) {
        ArrayList<Card> result = new ArrayList<Card>();
        for (int i = 1; i <= size; i++) {
            final Card c = new Card();
            c.title = "Titel " + i;
            c.activated = true;
            c.picture = loadBitmap();

            result.add(c);
        }

        return result;
    }

    private Bitmap loadBitmap() {
        Bitmap bitmap = null;
        try {
            //Getting the Bitmap from Gallery
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.nature1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    public void fab_add_click(View view) {
        cardAdapter.addItem(new Card("Neues bims " + cardAdapter.getItemCount(), false, loadBitmap()));
        cardAdapter.notifyDataSetChanged();
        Toast.makeText(this, "New Card added", Toast.LENGTH_SHORT).show();
    }
}
