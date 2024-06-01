package com.example.auuthenticationsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class InspirationsActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private ImageView imageViewSelfie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspirations);

        //imageViewSelfie = findViewById(R.id.imageViewSelfie);

       /* Button btnCaptureSelfie = findViewById(R.id.btnCaptureSelfie);
        btnCaptureSelfie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        */
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageViewSelfie.setImageBitmap(imageBitmap);
            // Process the captured image (e.g., verify it against DL)
            processSelfie(imageBitmap);
        }
    }

    private void processSelfie(Bitmap imageBitmap) {
        // Implement your selfie processing logic here
        // This could include facial recognition, image quality checks, etc.
        // Compare with the user's DL photo and verify if they match
    }
}