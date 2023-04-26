package edu.uga.cs.roommateshopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Navigation extends AppCompatActivity {
    private static final String DEBUG_TAG = "NavigationActivity";
    private TextView signedInTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Log.d(DEBUG_TAG, "NavigationActivity.onCreate()");

        signedInTextView = findViewById(R.id.textView4);

        // Setup a listener for a change in the sign in status (authentication status change)
        // when it is invoked, check if a user is signed in and update the UI text view string,
        // as needed.
        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                if (currentUser != null) {
                    // User is signed in
                    Log.d(DEBUG_TAG, "onAuthStateChanged:signed_in:" + currentUser.getUid());
                    String userEmail = currentUser.getEmail();
                    signedInTextView.setText("Signed in as: " + userEmail);
                } else {
                    // User is signed out
                    Log.d(DEBUG_TAG, "onAuthStateChanged:signed_out");
                    signedInTextView.setText("Signed in as: not signed in");
                }
            }
        });


        Button addItemButton = findViewById(R.id.addItem);
        Button personalListButton = findViewById(R.id.personalList);
        Button cartButton = findViewById(R.id.cart);
        Button settleCostButton = findViewById(R.id.settleCost);
        Button logoutButton = findViewById(R.id.logout);

        addItemButton.setOnClickListener(new AddItemButtonClickListener());
        personalListButton.setOnClickListener(new PersonalListButtonClickListener());
        cartButton.setOnClickListener(new CartButtonClickListener());
        settleCostButton.setOnClickListener(new SettleCostButtonClickListener());
        logoutButton.setOnClickListener(new LogoutButtonClickListener());

    }
    private class AddItemButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), AddItemActivity.class);
            view.getContext().startActivity( intent );
        }
    }

    private class PersonalListButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), PersonalShoppingList.class);
            view.getContext().startActivity( intent );
        }
    }

    private class CartButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
//            Intent intent = new Intent(view.getContext(), CartActivity.class);
//            view.getContext().startActivity( intent );
        }
    }

    private class SettleCostButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
//            Intent intent = new Intent(view.getContext(), SettleCostActivity.class);
//            view.getContext().startActivity( intent );
        }
    }

    private class LogoutButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), MainActivity.class);
            FirebaseAuth.getInstance().signOut();
            view.getContext().startActivity(intent);
        }
    }
}