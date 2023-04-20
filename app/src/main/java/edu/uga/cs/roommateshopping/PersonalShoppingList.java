package edu.uga.cs.roommateshopping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * This is an activity class for listing the current items.
 * The current items are listed as a RecyclerView.
 */
public class PersonalShoppingList extends AppCompatActivity
        implements AddJobLeadDialogFragment.AddJobLeadDialogListener,
        EditJobLeadDialogFragment.EditJobLeadDialogListene{

    public static final String DEBUG_TAG = "PersonalShoppingList";

    private RecyclerView recyclerView;
    private ItemRecyclerAdapter recyclerAdapter;

    private List<Item> itemList;

    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d( DEBUG_TAG, "PersonalShoppingList: onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_shopping_list);

        recyclerView = findViewById(R.id.recyclerView );

        FloatingActionButton floatingButton = findViewById(R.id.floatingActionButton);
        floatingButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new AddJobLeadDialogFragment();
                newFragment.show( getSupportFragmentManager(), null);
            }
        });
        // initialize the Job Lead list
        jobLeadsList = new ArrayList<JobLead>();

        // use a linear layout manager for the recycler view
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // the recycler adapter with job leads is empty at first; it will be updated later
        recyclerAdapter = new JobLeadRecyclerAdapter( jobLeadsList, ReviewJobLeadsActivity.this );
        recyclerView.setAdapter( recyclerAdapter );

        // get a Firebase DB instance reference
        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("jobleads");
    }
}