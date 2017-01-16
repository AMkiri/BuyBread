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

    List<ItemToBuy> items;

    ItemViewHolder.CheckBoxClickListener mCheckBoxClickListener;
    ItemViewHolder.DeleteButtonClickListener mDeleteButtonClickListener;

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

    public List<ItemToBuy> getItems() {
        return items;
    }

    public void addItem(ItemToBuy item){
        if (!items.contains(item)) {
            items.add(0, item);
            notifyItemInserted(0);
        }
    }

    @Override
    public ItemAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shopping_list, parent, false);
        return new ItemViewHolder(convertView, mCheckBoxClickListener, mDeleteButtonClickListener);
    }

    @Override
    public void onBindViewHolder(ItemAdapter.ItemViewHolder holder, int position) {
        ItemToBuy current = items.get(position);
        holder.mItemName.setText(current.getName());
        holder.mCheckBox.setActivated(current.isDone());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected TextView mItemName;
        protected CheckBox mCheckBox;
        protected ImageButton mDeleteButton;

        private CheckBoxClickListener mCheckBoxClickListener;
        private DeleteButtonClickListener mDeleteButtonClickListener;

        public ItemViewHolder(View itemView,
                              CheckBoxClickListener checkBoxClickListener,
                              DeleteButtonClickListener deleteButtonClickListener) {
            super(itemView);
            mCheckBoxClickListener = checkBoxClickListener;
            mDeleteButtonClickListener = deleteButtonClickListener;

            mItemName = (TextView) itemView.findViewById(R.id.item_name);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.check_box);
            mDeleteButton = (ImageButton)itemView.findViewById(R.id.button_delete_item);

            mCheckBox.setOnClickListener(this);
            mDeleteButton.setOnClickListener(this);
        }

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

        /**
         * Actions when item state inverts
         */
        public interface CheckBoxClickListener{
            void onCheckBoxClick(int position);
        }

        /**
         * Actions when item deletes
         */
        public interface DeleteButtonClickListener{
            void onDeleteButtonClick(int position);
        }

    }
}
