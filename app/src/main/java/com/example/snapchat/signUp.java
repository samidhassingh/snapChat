package com.example.snapchat;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class signUp extends AppCompatActivity {
    private FirebaseAuth x;
    EditText emailEditText;
    EditText passwordEditText;
    Button signButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        x = FirebaseAuth.getInstance();
        emailEditText=(EditText)findViewById(R.id.emailEditText);
        passwordEditText=(EditText)findViewById(R.id.passwordEditText);
        signButton=(Button) findViewById(R.id.signButton);

        signButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email=emailEditText.getText().toString().trim();
                String password=passwordEditText.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(signUp.this, "Please enter email", Toast.LENGTH_SHORT).show();
                  return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(signUp.this, "Please enter password", Toast.LENGTH_SHORT).show();
                    return;
                }


                    x.createUserWithEmailAndPassword(email, password).addOnCompleteListener(signUp.this, new OnCompleteListener<AuthResult>() {

                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {

                                        FirebaseDatabase.getInstance().getReference().child("users").child(task.getResult().getUser().getUid()).child("email").setValue(email);
                                        startActivity(new Intent(getApplicationContext(), logIn.class));
                                        Toast.makeText(signUp.this, "Sucess", Toast.LENGTH_SHORT).show();
                                    } else {
                                        String reas=((FirebaseAuthWeakPasswordException)task.getException()).getReason();
                                        Toast.makeText(signUp.this, reas, Toast.LENGTH_SHORT).show();

                                    }



                                }
                            });

            }
        });

    }



}
