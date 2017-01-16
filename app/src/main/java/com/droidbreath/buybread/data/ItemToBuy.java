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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemToBuy itemToBuy = (ItemToBuy) o;

        return mName.equals(itemToBuy.mName);

    }

    @Override
    public int hashCode() {
        return mName.hashCode();
    }
}
