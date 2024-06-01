package com.example.auuthenticationsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CheckBox;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WaitList extends AppCompatActivity {

    // Declare variables for checkboxes
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private CheckBox emailVerifiedCheckBox;
    private CheckBox applicationSubmittedCheckBox;
    private CheckBox verificationStageCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_list);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Initialize checkboxes
        emailVerifiedCheckBox = findViewById(R.id.emailVerifiedCheckBox);
        applicationSubmittedCheckBox = findViewById(R.id.applicationSubmittedCheckBox);
        verificationStageCheckBox = findViewById(R.id.verificationStageCheckBox);

        // Retrieve current user's information from the database
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // Check if email is verified
            checkEmailVerification(currentUser);

            // Check if application is submitted
            checkApplicationSubmission(currentUser.getUid());

            // Check verification stage
            checkVerificationStage(currentUser.getUid());
        }
    }

    private void checkEmailVerification(FirebaseUser user) {
        mDatabase.child("users").child(user.getUid()).child("verified").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Boolean isVerified = dataSnapshot.getValue(Boolean.class);
                if (isVerified != null && isVerified) {
                    emailVerifiedCheckBox.setChecked(true);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database error
            }
        });
    }

    private void checkApplicationSubmission(String userId) {
        mDatabase.child("applications").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean hasSubmittedApplication = dataSnapshot.exists();
                applicationSubmittedCheckBox.setChecked(hasSubmittedApplication);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database error
            }
        });
    }

    private void checkVerificationStage(String userId) {
        mDatabase.child("users").child(userId).child("verified").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean isVerified = (boolean) dataSnapshot.getValue();
                verificationStageCheckBox.setChecked(isVerified);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
            }
        });
    }
}
