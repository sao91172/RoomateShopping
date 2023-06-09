package edu.uga.cs.roommateshopping;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class EditItemDialogFragment extends DialogFragment {

    // indicate the type of an edit
    public static final int SAVE = 1;   // update an existing job lead
    public static final int DELETE = 2; // delete an existing job lead

    private EditText itemNameView;
    private EditText priceView;

    private Button shoppingButton;
    int position;     // the position of the edited JobLead on the list of job leads
    String key;
    String name;
    Double price;

    // A callback listener interface to finish up the editing of a JobLead.
    // ReviewJobLeadsActivity implements this listener interface, as it will
    // need to update the list of JobLeads and also update the RecyclerAdapter to reflect the
    // changes.
    public interface EditItemDialogListener {
        void updateItem(int position, Item item, int action);
    }

    public static EditItemDialogFragment newInstance(int position, String key, String name, Double price) {
        EditItemDialogFragment dialog = new EditItemDialogFragment();

        // Supply job lead values as an argument.
        Bundle args = new Bundle();
        args.putString( "key", key );
        args.putInt( "position", position );
        args.putString("name", name);
        args.putDouble("price", price);
        dialog.setArguments(args);

        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState ) {

        key = getArguments().getString( "key" );
        position = getArguments().getInt( "position" );
        name = getArguments().getString( "name" );
        price = getArguments().getDouble( "price" );


        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate( R.layout.fragment_edit_item_dialog, getActivity().findViewById( R.id.root ) );

        itemNameView = layout.findViewById( R.id.editText1 );
        priceView = layout.findViewById( R.id.editText2 );
        shoppingButton = layout.findViewById(R.id.addShopping);

        shoppingButton.setEnabled(true);

        if (getActivity() instanceof CartActivity) {
            shoppingButton.setText("Move back to \"all items\" list");
            shoppingButton.setOnClickListener(new MoveBackListener());
        } else if (getActivity() instanceof SettleCostActivity) {
            shoppingButton.setText("Move back to basket");
            shoppingButton.setOnClickListener(new RemoveClickListener());
        }else {
            shoppingButton.setOnClickListener(new AddShopListener());
        }


        // Pre-fill the edit texts with the current values for this job lead.
        // The user will be able to modify them.
        itemNameView.setText( name );
        priceView.setText(price.toString());

//        AlertDialog.Builder builder = new AlertDialog.Builder( getActivity(), R.style.AlertDialogStyle );
        AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
        builder.setView(layout);

        // Set the title of the AlertDialog
        builder.setTitle( "Edit Item" );

        // The Cancel button handler
        builder.setNegativeButton( android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                // close the dialog
                dialog.dismiss();
            }
        });

        // The Save button handler
        builder.setPositiveButton( "SAVE", new SaveButtonClickListener() );

        // The Delete button handler
        builder.setNeutralButton( "DELETE", new DeleteButtonClickListener() );

        // Create the AlertDialog and show it
        return builder.create();
    }

    private class SaveButtonClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            String itemName = itemNameView.getText().toString();
            String price = priceView.getText().toString();
            Item item = new Item( itemName, Double.parseDouble(price) );
            item.setKey( key );

            // get the Activity's listener to add the new job lead
            EditItemDialogListener listener = (EditItemDialogFragment.EditItemDialogListener) getActivity();
            // add the new job lead
            listener.updateItem( position, item, SAVE );

            // close the dialog
            dismiss();
        }
    }

    private class DeleteButtonClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick( DialogInterface dialog, int which ) {

            Item item = new Item( name, price );
            item.setKey( key );

            // get the Activity's listener to add the new job lead
            EditItemDialogFragment.EditItemDialogListener listener = (EditItemDialogFragment.EditItemDialogListener) getActivity();            // add the new job lead
            listener.updateItem( position, item, DELETE );
            // close the dialog
            dismiss();
        }
    }

    private class AddShopListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String name = itemNameView.getText().toString();
            double price = Double.parseDouble(priceView.getText().toString());
            Item item = new Item(name, price);

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("cart");

            myRef.push().setValue( item )
                    .addOnSuccessListener( new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Clear the EditTexts for next use.
                            itemNameView.setText("");
                            priceView.setText("");
                        }
                    })
                    .addOnFailureListener( new OnFailureListener() {
                        @Override
                        public void onFailure( @NonNull Exception e ) {
                            // do nothing
                        }
                    });

            item.setKey(key);
            // deleting the item from the FireBase database
            EditItemDialogFragment.EditItemDialogListener listener = (EditItemDialogFragment.EditItemDialogListener) getActivity();
            listener.updateItem( position, item, DELETE );
            shoppingButton.setEnabled(false);
        }
    } // AddShopListener


    private class MoveBackListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String name = itemNameView.getText().toString();
            double price = Double.parseDouble(priceView.getText().toString());
            final Item item = new Item(name, price);

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("items");

            myRef.push().setValue( item )
                    .addOnSuccessListener( new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Clear the EditTexts for next use.
                            itemNameView.setText("");
                            priceView.setText("");
                        }
                    })
                    .addOnFailureListener( new OnFailureListener() {
                        @Override
                        public void onFailure( @NonNull Exception e ) {
                            // do nothing
                        }
                    });

            item.setKey(key);
            // deleting the item from the FireBase database
            EditItemDialogFragment.EditItemDialogListener listener = (EditItemDialogFragment.EditItemDialogListener) getActivity();
            listener.updateItem( position, item, DELETE );
            shoppingButton.setEnabled(false);
        }

    } //MoveBackListener


    private class RemoveClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String name = itemNameView.getText().toString();
            double price = Double.parseDouble(priceView.getText().toString());
            final Item item = new Item(name, price);

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("cart");

            myRef.push().setValue( item )
                    .addOnSuccessListener( new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Clear the EditTexts for next use.
                            itemNameView.setText("");
                            priceView.setText("");
                        }
                    })
                    .addOnFailureListener( new OnFailureListener() {
                        @Override
                        public void onFailure( @NonNull Exception e ) {
                            // do nothing
                        }
                    });

            item.setKey(key);
            // deleting the item from the FireBase database
            EditItemDialogFragment.EditItemDialogListener listener = (EditItemDialogFragment.EditItemDialogListener) getActivity();
            listener.updateItem( position, item, DELETE );
            shoppingButton.setEnabled(false);
        }

    } //MoveBackListener

}