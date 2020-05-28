package com.example.snapchat;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class logIn extends AppCompatActivity {
    EditText email2;
    EditText password2;
    private FirebaseAuth y;
    Button logButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        email2=(EditText) findViewById(R.id.emEditText);
        password2=(EditText) findViewById(R.id.passEditText);
        y=FirebaseAuth.getInstance();
        logButton=(Button)findViewById(R.id.logButton);

        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email3=email2.getText().toString().trim();
                String password3=password2.getText().toString().trim();
                if(TextUtils.isEmpty(email3)){
                    Toast.makeText(logIn.this, "Please enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password3)){
                    Toast.makeText(logIn.this, "Please enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                y.signInWithEmailAndPassword(email3, password3)
                        .addOnCompleteListener(logIn.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    startActivity(new Intent(getApplicationContext(), snapsActivity.class));
                                } else {
                                    Toast.makeText(logIn.this, "Authenthentiation failed", Toast.LENGTH_SHORT).show();
                                }


                            }
                        });


            }
        });

    }

}

