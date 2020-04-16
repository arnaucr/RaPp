package com.olivacebollada.rapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText usernames, firstnames, lastnames;
    Button guardar;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        db = FirebaseFirestore.getInstance();
        usernames = findViewById(R.id.username);
        firstnames = findViewById(R.id.firstName);
        lastnames = findViewById(R.id.lastName);
        guardar = findViewById(R.id.guarda);
        guardar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int ID = v.getId();
        if (ID == R.id.guarda) {
            String un, fn, ln, idname;

            un = usernames.getText().toString();
            fn = firstnames.getText().toString();
            ln = lastnames.getText().toString();

            Map<String, Object> user = new HashMap<>();
            user.put("first", fn);
            user.put("last", ln);
            user.put("username", un);

            db.collection("users")
                    .add(user)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("test", "DocumentSnapshot added with ID: " + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("test", "Error adding document", e);
                        }
                    });


            Intent intent = new Intent(this, Profile.class);
            startActivity(intent);




        }
    }
}
