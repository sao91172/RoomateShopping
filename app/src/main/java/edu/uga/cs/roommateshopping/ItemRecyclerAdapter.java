package edu.uga.cs.roommateshopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * This is an adapter class for the RecyclerView to show all items.
 */
public class ItemRecyclerAdapter extends RecyclerView.Adapter<ItemRecyclerAdapter.ItemHolder> {
    public static final String DEBUG_TAG = "ItemRecyclerAdapter";

    private List<Item> itemList;
    private Context context;

    public ItemRecyclerAdapter( List<Item> itemList, Context context ) {
        this.itemList = itemList;
        this.context = context;
    }

    // The adapter must have a ViewHolder class to "hold" one item to show.
    class ItemHolder extends RecyclerView.ViewHolder {

        TextView itemName;
        TextView itemPrice;

        public ItemHolder(View itemView ) {
            super(itemView);
            itemName = itemView.findViewById( R.id.itemname );
            itemPrice = itemView.findViewById( R.id.itemprice );
        }
    }
    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType ) {
        View view = LayoutInflater.from( parent.getContext()).inflate( R.layout.item, parent, false );
        return new ItemHolder( view );
    }

    // This method fills in the values of the Views to show an item
    @Override
    public void onBindViewHolder( ItemHolder holder, int position ) {
        Item item = itemList.get( position + 1 );

        Log.d( DEBUG_TAG, "onBindViewHolder: " + item );

        String key = item.getKey();
        String name = item.getName();
        double price = item.getPrice();


        holder.itemName.setText( item.getName());
        holder.itemPrice.setText( String.valueOf(item.getPrice()));

        // We can attach an OnClickListener to the itemView of the holder;
        // itemView is a public field in the Holder class.
        // It will be called when the user taps/clicks on the whole item, i.e., one of
        // the items shown.
        // This will indicate that the user wishes to edit (modify or delete) this item.
        // We create and show an EditItemDialogFragment.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d( TAG, "onBindViewHolder: getItemId: " + holder.getItemId() );
                //Log.d( TAG, "onBindViewHolder: getAdapterPosition: " + holder.getAdapterPosition() );
                EditItemDialogFragment editItemFragment =
                        EditItemDialogFragment.newInstance( holder.getAdapterPosition(), key, name, price);
                editItemFragment.show( ((AppCompatActivity)context).getSupportFragmentManager(), null);
            }
        });
    }
    @Override
    public int getItemCount() {
        return itemList.size();
    }
}