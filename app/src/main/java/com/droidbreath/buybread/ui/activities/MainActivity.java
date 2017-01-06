package com.droidbreath.buybread.ui.activities;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.droidbreath.buybread.R;
import com.droidbreath.buybread.data.ItemToBuy;
import com.droidbreath.buybread.ui.adapters.ItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<ItemToBuy> mItems;
    private ItemAdapter mItemAdapter;
    private Dialog mDialog;
    private EditText mNewItemName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.shopping_list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        loadItems();

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

    private void loadItems() {
        //TODO: load data
        mItems = new ArrayList<>();
        for (int i = 1; i < 7; i++){
            mItems.add(new ItemToBuy("Task #" + i));
        }

        mItemAdapter = new ItemAdapter(mItems);
        mRecyclerView.setAdapter(mItemAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //TODO: save data
    }
}
