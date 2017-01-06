package com.droidbreath.buybread.data;

import java.io.Serializable;

public class ItemToBuy implements Serializable{
    private final String mName;
    private boolean isDone;

    public ItemToBuy(String name) {
        mName = name;
        isDone = false;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String getName() {
        return mName;
    }
}
