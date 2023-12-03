package com.example.ex6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner intentSpinner = findViewById(R.id.intentSpinner);
        Button startIntentButton = findViewById(R.id.startIntentButton);

        startIntentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedIntent = intentSpinner.getSelectedItem().toString();
                startImplicitIntent(selectedIntent);
            }
        });
    }

    private void startImplicitIntent(String selectedIntent) {
        Intent intent=null;
        switch (selectedIntent) {
            case "Ouvrir le navigateur":
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                break;
            case "Composer un numéro de téléphone":
                intent = new Intent(Intent.ACTION_DIAL);
                break;
            case "Afficher la carte":

                double latitude = 33.892166;
                double longitude = 9.561555;
                String label = "Location Label"; // Replace with a desired label

                Uri gmmIntentUri = Uri.parse("geo:" + latitude + "," + longitude + "?q=" + latitude + "," + longitude + "(" + label + ")");
                intent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                intent.setPackage("com.google.android.apps.maps");

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Google Maps app is not installed.", Toast.LENGTH_SHORT).show();
                }    break;
            case "Prendre une photo":

                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                } else {
                    Toast.makeText(this, "Aucune application de caméra disponible.", Toast.LENGTH_SHORT).show();
                }                break;
            case "Afficher les contacts":
                intent = new Intent(Intent.ACTION_VIEW, ContactsContract.Contacts.CONTENT_URI);
                break;
            case "Éditer le premier contact":
                intent = new Intent(Intent.ACTION_EDIT);
                intent.setData(ContactsContract.Contacts.CONTENT_URI);
                break;
            default:
                break;
        }

        if (intent != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "Aucune application pour gérer cet Intent.", Toast.LENGTH_SHORT).show();
        }
    }
}
