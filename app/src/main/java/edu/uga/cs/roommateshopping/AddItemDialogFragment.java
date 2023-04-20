package edu.uga.cs.roommateshopping.;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

// A DialogFragment class to handle job lead additions from the job lead review activity
// It uses a DialogFragment to allow the input of a new job lead.
public class AddItemDialogFragment extends DialogFragment {

//    private EditText companyNameView;
    private EditText itemNameView;
//    private EditText phoneView;

    private EditText priceView;

    // This interface will be used to obtain the new job lead from an AlertDialog.
    // A class implementing this interface will handle the new job lead, i.e. store it
    // in Firebase and add it to the RecyclerAdapter.
    public interface AddItemDialogListener {
        void addItem(Item item);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Create the AlertDialog view
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.fragment_add_item_dialog,
                getActivity().findViewById(R.id.root));

        // get the view objects in the AlertDialog
        itemNameView = layout.findViewById( R.id.editText1 );
        priceView = layout.findViewById( R.id.editText2 );

        // create a new AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Set its view (inflated above).
        builder.setView(layout);

        // Set the title of the AlertDialog
        builder.setTitle( "New Item" );
        // Provide the negative button listener
        builder.setNegativeButton( android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                // close the dialog
                dialog.dismiss();
            }
        });
        // Provide the positive button listener
        builder.setPositiveButton( android.R.string.ok, new AddItemListener() );

        // Create the AlertDialog and show it
        return builder.create();
    }

    private class AddItemListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            // get the new job lead data from the user
            String itemName = itemNameView.getText().toString();
            String price = priceView.getText().toString();
            double itemPrice = Double.parseDouble(price);

            // create a new JobLead object
            Item item = new Item( itemName, itemPrice);

            // get the Activity's listener to add the new job lead
            AddItemDialogListener listener = (AddItemDialogListener) getActivity();

            // add the new job lead
            listener.addItem( item );

            // close the dialog
            dismiss();
        }
    }
}
