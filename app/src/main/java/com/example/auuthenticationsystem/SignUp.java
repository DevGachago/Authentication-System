package com.example.auuthenticationsystem;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.auuthenticationsystem.applications.ApplicationActivity;
import com.example.auuthenticationsystem.verification.Verification;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignUp extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button buttonSignUp;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");


        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonSignUp = findViewById(R.id.buttonSignUp);

        buttonSignUp.setOnClickListener(v -> signUp());
    }

    private void signUp() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Enter email");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Enter password");
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        String userId = mAuth.getCurrentUser().getUid();
                        DatabaseReference currentUserDb = mDatabase.child(userId);
                        currentUserDb.child("email").setValue(email);
                        currentUserDb.child("password").setValue(password);
                        currentUserDb.child("verified").setValue(false); // Set verification status to false initially

                        Toast.makeText(SignUp.this, "Sign up successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUp.this, Verification.class);
                        startActivity(intent);
                        finish(); // Close the login activity
                    } else {
                        Toast.makeText(SignUp.this, "Sign up failed. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
