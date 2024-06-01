package com.example.auuthenticationsystem.verification;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.auuthenticationsystem.InspirationsActivity;
import com.example.auuthenticationsystem.R;
import com.example.auuthenticationsystem.SignUp;
import com.example.auuthenticationsystem.WaitList;
import com.example.auuthenticationsystem.documents.Documents;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.security.SecureRandom;
import java.util.Objects;
import java.util.UUID;

public class Verification extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int STORAGE_PERMISSION_REQUEST_CODE = 100;

    private Button btnSendOTP, btnVerifyOTP, btnChooseDocument, btnSubmitDocument;
    private EditText etOTP;
    private RadioGroup radioGroupDocumentType;
    private RadioButton rbDL, rbID;
    private ImageView ivSelectedDocument;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private StorageReference mStorageRef;

    private String imageFilePath; // Variable to store the file path of the selected image

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        // Initialize views
        btnChooseDocument = findViewById(R.id.btnChooseDocument);
        btnSubmitDocument = findViewById(R.id.btnSubmitDocument);
        radioGroupDocumentType = findViewById(R.id.radioGroupDocumentType);
        rbDL = findViewById(R.id.rbDL);
        rbID = findViewById(R.id.rbID);
        ivSelectedDocument = findViewById(R.id.ivSelectedDocument);


        mAuth = FirebaseAuth.getInstance();

        //btnSendOTP.setOnClickListener(v -> sendVerificationEmail());

        // Check if user is logged in
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // Check if user's email is verified
            checkEmailVerification(currentUser);
        } else {
            // User is not logged in, handle accordingly
            Toast.makeText(Verification.this, "User is not logged in", Toast.LENGTH_SHORT).show();
        }

        // Choose Document button click listener
        btnChooseDocument.setOnClickListener(v -> chooseDocument());

        // Submit Document button click listener
        btnSubmitDocument.setOnClickListener(v -> submitDocument());



    // Check if the permission is not granted
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted, request it
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    STORAGE_PERMISSION_REQUEST_CODE);
        }
    }

    // Method to send verification email
    private void sendVerificationEmail() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Verification.this, "Verification email sent", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Verification.this, "Failed to send verification email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    // Method to check if user's email is verified
    private void checkEmailVerification(FirebaseUser user) {
        if (user.isEmailVerified()) {
            Toast.makeText(Verification.this, "User's email is verified", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(Verification.this, "User's email is not verified", Toast.LENGTH_SHORT).show();
            // Optionally, you can send another verification email here
            sendVerificationEmail();
        }
    }


    private void chooseDocument() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            imageFilePath = getPath(selectedImageUri); // Get the file path from the URI
            // Display the selected image in the ImageView
            ivSelectedDocument.setImageURI(selectedImageUri);
            ivSelectedDocument.setVisibility(View.VISIBLE);
        }
    }

    private void submitDocument() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();

            // Check if the user has already submitted documents
            DatabaseReference userDocumentsRef = mDatabase.child("documents").child(userId);
            userDocumentsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // User has already submitted documents
                        Toast.makeText(Verification.this, "Documents have already been submitted", Toast.LENGTH_SHORT).show();
                        // Proceed with the existing code for checking email verification and proceeding to next step
                        if (user.isEmailVerified()) {
                            // Email is verified, proceed to next step
                            // Open the next page
                            Intent intent = new Intent(Verification.this, WaitList.class);
                            startActivity(intent);
                        } else {
                            // Email is not verified, resend verification email
                            sendVerificationEmail();
                            Toast.makeText(Verification.this, "Please verify your email", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // User has not submitted documents yet
                        // Proceed with uploading documents
                        if (imageFilePath == null) {
                            Toast.makeText(Verification.this, "Please select a document to upload", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        int selectedId = radioGroupDocumentType.getCheckedRadioButtonId();
                        if (selectedId == -1) {
                            Toast.makeText(Verification.this, "Please select a document type", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        RadioButton radioButton = findViewById(selectedId);
                        String documentType = radioButton.getText().toString();

                        // Save the document to Firebase Storage
                        String documentId = UUID.randomUUID().toString(); // Generate a unique ID for the document
                        StorageReference documentRef = mStorageRef.child("documents").child(userId).child(documentId);

                        // Upload the selected image file to Firebase Storage
                        documentRef.putFile(Uri.fromFile(new File(imageFilePath)))
                                .addOnSuccessListener(taskSnapshot -> {
                                    // Get the download URL of the uploaded image
                                    documentRef.getDownloadUrl().addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();

                                        // Create a Documents object with the document details
                                        Documents document = new Documents(userId, documentType, imageUrl, Objects.requireNonNull(uri.getPath()));

                                        // Save the document details to the Firebase Realtime Database
                                        DatabaseReference documentsRef = mDatabase.child("documents").child(userId).child(documentId);
                                        documentsRef.setValue(document)
                                                .addOnCompleteListener(task -> {
                                                    if (task.isSuccessful()) {
                                                        // Document saved successfully
                                                        Toast.makeText(Verification.this, "Document uploaded successfully", Toast.LENGTH_SHORT).show();
                                                        // Proceed with checking email verification and proceeding to next step
                                                        if (user.isEmailVerified()) {
                                                            // Email is verified, proceed to next step
                                                            // Open the next page
                                                            Intent intent = new Intent(Verification.this, WaitList.class);
                                                            startActivity(intent);
                                                        } else {
                                                            // Email is not verified, resend verification email
                                                            sendVerificationEmail();
                                                            Toast.makeText(Verification.this, "Please verify your email", Toast.LENGTH_SHORT).show();
                                                        }
                                                    } else {
                                                        // Error occurred while saving document
                                                        String errorMessage = Objects.requireNonNull(task.getException()).getMessage();
                                                        Toast.makeText(Verification.this, "Failed to upload document: " + errorMessage, Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    });
                                })
                                .addOnFailureListener(e -> {
                                    // Error occurred while uploading document
                                    Toast.makeText(Verification.this, "Failed to upload document", Toast.LENGTH_SHORT).show();
                                });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle database error
                    Toast.makeText(Verification.this, "Failed to check document submission status", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
        }
    }


    // Method to get the file path from the URI
    private String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            cursor.close();
            return path;
        }
        return null;
    }

}
