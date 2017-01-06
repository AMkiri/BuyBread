package com.droidbreath.buybread.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.droidbreath.buybread.R;
import com.droidbreath.buybread.data.ItemToBuy;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    // Hold list with elements
    List<ItemToBuy> items;

    // Create anonymous classes to handle user click (interfaces declared in inner class)
    ItemViewHolder.CheckBoxClickListener mCheckBoxClickListener;
    ItemViewHolder.DeleteButtonClickListener mDeleteButtonClickListener;

    // Create your own constructor
    // U can put interfaces to parameters, if u need to handle clicks in main activity
    public ItemAdapter(final List<ItemToBuy> items) {
        this.items = items;
        mCheckBoxClickListener = new ItemViewHolder.CheckBoxClickListener() {
            @Override
            public void onCheckBoxClick(int position) {
                ItemToBuy current = items.get(position);
                current.setDone(!current.isDone());
            }
        };

        mDeleteButtonClickListener = new ItemViewHolder.DeleteButtonClickListener() {
            @Override
            public void onDeleteButtonClick(int position) {
                items.remove(position);
                notifyItemRemoved(position);
            }
        };
    }

    // 2 methods to make work easier:

    public List<ItemToBuy> getItems() {
        return items;
    }

    public void addItem(ItemToBuy item){
        items.add(item);
        // U have to notify RecycleView when some data is changed, because RV don't know about it:
        notifyItemInserted(items.size());
    }

    // You have to override 3 methods at least and create 1 inner class:

    // Creates View class for elements just one time:
    @Override
    public ItemAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // load your item xml:
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shopping_list, parent, false);
        // create inner class, which hold it:
        return new ItemViewHolder(convertView, mCheckBoxClickListener, mDeleteButtonClickListener);
    }

    // The most needed part!
    // Each time RV creates or updates element on screen, it calls this function.
    // So put your data to right places and
    // change view parameters if needed (don't forget to change back in other elements! Use if-else)
    @Override
    public void onBindViewHolder(ItemAdapter.ItemViewHolder holder, int position) {
        ItemToBuy current = items.get(position);
        holder.mItemName.setText(current.getName());
        holder.mCheckBox.setActivated(current.isDone());
    }

    // Just count number of elements,
    // couse RV don't know ANYTHING about your data
    @Override
    public int getItemCount() {
        return items.size();
    }

    // Create your inner class that extends ViewHolder
    public static class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // U can read/write to fields from outer class
        protected TextView mItemName;
        protected CheckBox mCheckBox;
        protected ImageButton mDeleteButton;

        // Get listeners from outer class
        private CheckBoxClickListener mCheckBoxClickListener;
        private DeleteButtonClickListener mDeleteButtonClickListener;

        // Create your constructor
        public ItemViewHolder(View itemView,
                              CheckBoxClickListener checkBoxClickListener,
                              DeleteButtonClickListener deleteButtonClickListener) {
            super(itemView);
            mCheckBoxClickListener = checkBoxClickListener;
            mDeleteButtonClickListener = deleteButtonClickListener;

            // find everything to interact with element view
            mItemName = (TextView) itemView.findViewById(R.id.item_name);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.check_box);
            mDeleteButton = (ImageButton)itemView.findViewById(R.id.button_delete_item);

            // Don't forget to set listeners! I've spend 2 hours finding this bug =(
            mCheckBox.setOnClickListener(this);
            mDeleteButton.setOnClickListener(this);
        }

        //Implement OnClickListener if u want to do some staff with elements,
        // don't write there complex logic
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.check_box:
                    if (mCheckBoxClickListener != null){
                        mCheckBoxClickListener.onCheckBoxClick(getAdapterPosition());
                    }
                    break;
                case R.id.button_delete_item:
                    if (mDeleteButtonClickListener != null){
                        mDeleteButtonClickListener.onDeleteButtonClick(getAdapterPosition());
                    }
                    break;
            }
        }

        // And declare interfaces to throw hard logic away

        public interface CheckBoxClickListener{
            void onCheckBoxClick(int position);
        }

        public interface DeleteButtonClickListener{
            void onDeleteButtonClick(int position);
        }

    }
}
