package com.droidbreath.buybread.ui.activities;

/*
* Comments show how to use RecycleView
* Add to dependencies in build.gradle:
* compile 'com.android.support:recyclerview-v7:23.4.0'
 */

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView; //* Add library to use it */
import android.view.View;
import android.widget.EditText;

import com.droidbreath.buybread.R;
import com.droidbreath.buybread.data.ItemToBuy; //* Create class to hold data of one element */
import com.droidbreath.buybread.ui.adapters.ItemAdapter; //* Create adapter to connect data and element view */

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Dialog mDialog;
    private EditText mNewItemName;

    //* Declare variables to get access to needed data and classes: */
    private RecyclerView mRecyclerView;
    private ItemAdapter mItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Add RecycleView tag to main screen:
        mRecyclerView = (RecyclerView) findViewById(R.id.shopping_list);

        // Give it layout manager, so it can position elements correctly:
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        // Load data and set adapter:
        List<ItemToBuy> items = loadItems();
        mItemAdapter = new ItemAdapter(items);
        mRecyclerView.setAdapter(mItemAdapter);

        // Some additional things to create new item (make it as u want, if needed):
        mDialog = new Dialog(MainActivity.this);
        mDialog.setTitle("To buy:");
        mDialog.setContentView(R.layout.dialog_add_item);
        mNewItemName = (EditText) mDialog.findViewById(R.id.dialog_item_name);

        mDialog.findViewById(R.id.dialog_button_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                mNewItemName.setText("");
            }
        });

        mDialog.findViewById(R.id.dialog_button_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                mItemAdapter.addItem(new ItemToBuy(mNewItemName.getText().toString()));
                mNewItemName.setText("");
            }
        });

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.show();
            }
        });

    }

    /**
     * Loads data to initialize shopping list
     * @return list with elements for RecycleView
     */
    private List<ItemToBuy> loadItems() {
        List<ItemToBuy> items = new ArrayList<>();
        for (int i = 1; i < 7; i++){
            items.add(new ItemToBuy("Task #" + i));
        }
        return items;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
