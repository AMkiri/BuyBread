package com.droidbreath.buybread.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "LIST_DB";
    private static final String TABLE_ITEMS = "ITEMS";

    private static final String KEY_ID = "_ID";
    private static final String KEY_NAME = "NAME";
    private static final String KEY_STATE = "STATE";

    private SQLiteDatabase mDatabaseWrite;
    private SQLiteDatabase mDatabaseRead;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mDatabaseWrite = this.getWritableDatabase();
        mDatabaseRead = this.getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_ITEMS + " ("
                    + KEY_ID + " integer primary key, "
                    + KEY_NAME + " text, "
                    + KEY_STATE + " integer"
                    + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_ITEMS);

        onCreate(db);
    }

    public List<ItemToBuy> getAllItems(){
        Cursor cursor = mDatabaseRead.query(TABLE_ITEMS, null, null, null, null, null, null);
        if(cursor == null) return null;

        List<ItemToBuy> result = null;

        if (cursor.moveToFirst()) {
            result = new ArrayList<>();
            int nameColumnIndex = cursor.getColumnIndex(KEY_NAME);
            int stateColumnIndex = cursor.getColumnIndex(KEY_STATE);

            do {
                ItemToBuy temp = new ItemToBuy(cursor.getString(nameColumnIndex));
                temp.setDone(cursor.getInt(stateColumnIndex) == 1);
                result.add(temp);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return result;
    }

    public void deleteItem(ItemToBuy item) {
        mDatabaseWrite.delete(TABLE_ITEMS, KEY_NAME + "=?", new String[]{item.getName()});
    }

    public void addItem(ItemToBuy item) {
        mDatabaseWrite.insert(TABLE_ITEMS, null, parseValues(item));
    }

    public void updateItem(ItemToBuy item) {
        mDatabaseWrite.update(TABLE_ITEMS,parseValues(item),KEY_NAME + "=?", new String[]{item.getName()});
    }

    private ContentValues parseValues(ItemToBuy item){
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, item.getName());
        values.put(KEY_STATE, (item.isDone() ? 1 : 0));
        return values;
    }
}
