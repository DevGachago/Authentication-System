package com.example.auuthenticationsystem.applications;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.auuthenticationsystem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ApplicationActivity extends AppCompatActivity {
    private EditText usernameEditText, firstNameEditText, surnameEditText, idNumberEditText, billingAddressEditText;
    private Button submitApplicationButton;

    private DatabaseReference applicationsRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application);

        // Initialize Firebase
        applicationsRef = FirebaseDatabase.getInstance().getReference().child("applications");
        mAuth = FirebaseAuth.getInstance();

        // Initialize UI elements
        usernameEditText = findViewById(R.id.usernameEditText);
        firstNameEditText = findViewById(R.id.firstNameEditText);
        surnameEditText = findViewById(R.id.surnameEditText);
        idNumberEditText = findViewById(R.id.idNumberEditText);
        billingAddressEditText = findViewById(R.id.billingAddressEditText);
        submitApplicationButton = findViewById(R.id.submitApplicationButton);

        // Set click listener for the submit button
        submitApplicationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitApplication();
            }
        });
    }

    private void submitApplication() {
        String username = usernameEditText.getText().toString().trim();
        String firstName = firstNameEditText.getText().toString().trim();
        String surname = surnameEditText.getText().toString().trim();
        String idNumber = idNumberEditText.getText().toString().trim();
        String billingAddress = billingAddressEditText.getText().toString().trim();

        // Check if any field is empty
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(firstName) || TextUtils.isEmpty(surname)
                || TextUtils.isEmpty(idNumber) || TextUtils.isEmpty(billingAddress)) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        // Store the application in the database
        String userId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        DatabaseReference currentUserApplicationsRef = applicationsRef.child(userId);

        // Check if the applications node exists, if not, create it
        currentUserApplicationsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    currentUserApplicationsRef.setValue(true); // Create the applications node
                }

                // Generate a unique key for the new application
                String applicationId = currentUserApplicationsRef.push().getKey();
                if (applicationId == null) {
                    Toast.makeText(ApplicationActivity.this, "Failed to generate application ID", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create an instance of the Applications class with the application details
                Applications application = new Applications(applicationId, username, firstName, surname, idNumber, billingAddress);

                // Set the value of the new application under the generated key in the database
                currentUserApplicationsRef.child(applicationId).setValue(application)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // Display success message
                                    Toast.makeText(ApplicationActivity.this, "Application submitted successfully", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    // Display failure message
                                    Toast.makeText(ApplicationActivity.this, "Failed to submit application", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
                Toast.makeText(ApplicationActivity.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}