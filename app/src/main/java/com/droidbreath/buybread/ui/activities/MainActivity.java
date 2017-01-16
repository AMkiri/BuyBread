package com.droidbreath.buybread.ui.activities;

import android.app.Dialog;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.droidbreath.buybread.R;
import com.droidbreath.buybread.data.DBHelper;
import com.droidbreath.buybread.data.ItemToBuy;
import com.droidbreath.buybread.ui.adapters.ItemAdapter;
import com.droidbreath.buybread.utils.ConstantManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Dialog mDialog;
    private EditText mNewItemName;

    private RecyclerView mRecyclerView;
    private ItemAdapter mItemAdapter;

    private DBHelper mDBHelper;
    private List<ItemToBuy> mItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.shopping_list);

        mDBHelper = new DBHelper(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        List<ItemToBuy> items = loadItems();
        mItemAdapter = new ItemAdapter(items);
        mRecyclerView.setAdapter(mItemAdapter);

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

        if(savedInstanceState != null){
            if (savedInstanceState.getBoolean(ConstantManager.NEW_ITEM_DIALOG_STATE)){
                mNewItemName.setText(savedInstanceState.getString(ConstantManager.NEW_ITEM_DIALOG_STRING));
                mDialog.show();
            }
        }

    }

    /**
     * Loads data to initialize shopping list
     * @return list with elements for RecycleView
     */
    private List<ItemToBuy> loadItems() {
        mItems = mDBHelper.getAllItems();
        return mItems;
    }

    /**
     * Save data to database when activity paused
     */
    @Override
    protected void onPause() {
        super.onPause();

        List<ItemToBuy> newItems = mItemAdapter.getItems();
        for(ItemToBuy item : newItems){
            if(mItems.contains(item)){
                mDBHelper.updateItem(item);
            } else {
                mDBHelper.addItem(item);
            }
        }

        for (ItemToBuy item : mItems){
            if(!newItems.contains(item)){
                mDBHelper.deleteItem(item);
            }
        }

        mItems = newItems;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(ConstantManager.NEW_ITEM_DIALOG_STATE, mDialog.isShowing());
        outState.putString(ConstantManager.NEW_ITEM_DIALOG_STRING, mNewItemName.getText().toString());
    }
}
