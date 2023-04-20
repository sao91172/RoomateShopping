package edu.uga.cs.roommateshopping;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;




/**
 * This class is an activity to create a new item.
 */
public class AddItemActivity extends AppCompatActivity {
    public static final String DEBUG_TAG = "AddItemActivity";
    private EditText itemNameText;
    private EditText itemPriceText;
    Button saveItemButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        itemNameText = findViewById(R.id.itemName);
        itemPriceText = findViewById(R.id.itemPrice);
        saveItemButton = findViewById(R.id.saveItem);

        saveItemButton.setOnClickListener( new saveItemButtonClickListener()) ;

    }


    private class saveItemButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String name = itemNameText.getText().toString();
            double price = Double.parseDouble(itemPriceText.getText().toString());

            final Item item = new Item(name, price);

            // Add a new element (Item) to the list of items in Firebase.
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("items");

            // First, a call to push() appends a new node to the existing list (one is created
            // if this is done for the first time).  Then, we set the value in the newly created
            // list node to store the new items.
            // This listener will be invoked asynchronously, as no need for an AsyncTask, as in
            // the previous apps to maintain items.
            myRef.push().setValue(item)
                    .addOnSuccessListener( new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Show a quick confirmation
                            Toast.makeText(getApplicationContext(), "Item created for " + item.getName() + " & added to shopping list",
                                    Toast.LENGTH_SHORT).show();

                            // Clear the EditTexts for next use.
                            itemNameText.setText("");
                            itemPriceText.setText("");
                        }
                    })
                    .addOnFailureListener( new OnFailureListener() {
                        @Override
                        public void onFailure( @NonNull Exception e ) {
                            Toast.makeText( getApplicationContext(), "Failed to create a Item for " + item.getName(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
    @Override
    protected void onResume() {
        Log.d( DEBUG_TAG, "AddItemActivity.onResume()" );
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d( DEBUG_TAG, "AddItemActivity.onPause()" );
        super.onPause();
    }

    // The following activity callback methods are not needed and are for educational purposes only
    @Override
    protected void onStart() {
        Log.d( DEBUG_TAG, "AddItemActivity.onStart()" );
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.d( DEBUG_TAG, "AddItemActivity.onStop()" );
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d( DEBUG_TAG, "AddItemActivity.onDestroy()" );
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        Log.d( DEBUG_TAG, "AddItemActivity.onRestart()" );
        super.onRestart();
    }
}//AddItemActivity