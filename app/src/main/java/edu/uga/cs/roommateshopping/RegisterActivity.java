package edu.uga.cs.roommateshopping;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class RegisterActivity extends AppCompatActivity {

    public static final String TAG = "SuperApp";
    TextView text;
    EditText emailEditText;
    EditText passwordEditText;
    Button registerButton;
    public FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        text = findViewById(R.id.textView3);
        registerButton = findViewById(R.id.registerButton);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.pwEditText);


        mAuth = FirebaseAuth.getInstance();



//        String email = "dawg@mail.com";
//        String password = "Password";

        registerButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);
                                    startActivity(new Intent(RegisterActivity.this,Navigation.class));

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.d(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    updateUI(null);
                                    if(task.getException() instanceof FirebaseAuthUserCollisionException) {
                                        Toast.makeText(RegisterActivity.this, "User already exists.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
            }
                });


//        mAuth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            Toast.makeText(getApplicationContext(),
//                                    "Registered user: " + email,
//                                    Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(getApplicationContext(),
//                                    "Error: " + email,
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
    }//bundleSavedInstance
    public void updateUI(FirebaseUser account) {
        if (account != null) {
            Toast.makeText(this, "Signed in successfully", Toast.LENGTH_LONG).cancel();
            //startActivity(new Intent(this, MainActivity.class));
    } else {
            Toast.makeText(this,"Please sign in", Toast.LENGTH_LONG).show();
        }






        DatabaseReference myRef = database.getReference( "message" );
       // DatabaseReference myRef = database;


        // Read from the database value for ”message”
        myRef.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String message = dataSnapshot.getValue(String.class);
                text.setText(message);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w( TAG, "Failed to read value.", error.toException() );
            }
        });

    }

}