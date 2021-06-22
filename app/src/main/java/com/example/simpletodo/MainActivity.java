package com.example.simpletodo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    List<String> items;

    Button button;
    EditText edit;
    RecyclerView rv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        items = new ArrayList<>();
        items.add("rsvp for that thing");


        button = findViewById(R.id.updateButton);
        edit = findViewById(R.id.newTask);
        rv = findViewById(R.id.listy);

        ItemsAdapter itemsAdapter = new ItemsAdapter(items);
        rv.setAdapter(itemsAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newItem = edit.getText().toString();
                items.add(newItem);

                itemsAdapter.notifyItemInserted(items.size()-1);


                Toast.makeText(getApplicationContext(), "Added " + edit.getText().toString(), Toast.LENGTH_SHORT).show();
                edit.setText("");
            }
        });


    }
}