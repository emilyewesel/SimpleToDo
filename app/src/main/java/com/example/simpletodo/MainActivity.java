package com.example.simpletodo;

//import android.os.FileUtils;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

public class MainActivity extends AppCompatActivity {

    ArrayList items;
    Button button;
    EditText edit;
    RecyclerView rv;
    ItemsAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        items = new ArrayList<>();
        loadItems();


        button = findViewById(R.id.updateButton);
        edit = findViewById(R.id.newTask);
        rv = findViewById(R.id.listy);

        ItemsAdapter.OnLongClickListener onClickListener = new ItemsAdapter.OnLongClickListener(){
            @Override
            public void onItemLongClicked(int position){

                Toast.makeText(getApplicationContext(), "Removed " + items.get(position), Toast.LENGTH_SHORT).show();


                //Delete the item
                items.remove(position);

                //notify the adapter
                itemsAdapter.notifyItemRemoved(position);
                saveItems();




            }
        };

        itemsAdapter = new ItemsAdapter(items, onClickListener);
        rv.setAdapter(itemsAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newItem = edit.getText().toString();
                //add the item
                items.add(newItem);

                //notify the adapter
                itemsAdapter.notifyItemInserted(items.size()-1);

                Toast.makeText(getApplicationContext(), "Added " + edit.getText().toString(), Toast.LENGTH_SHORT).show();
                edit.setText("");
                saveItems();
            }
        });



    }

    private File getDataFile(){
        return new File(getFilesDir(), "data.txt");

    }
    //This function will load items by reading every line of the data file
    private void loadItems(){
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity", "Error reading items", e);
            items = new ArrayList();
        }
        //.readLines(getDataFile(), Charset.defaultCharset()));

    }

    //saving our changes
    private void saveItems(){
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}