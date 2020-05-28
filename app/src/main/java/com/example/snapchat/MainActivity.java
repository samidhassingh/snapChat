package com.example.snapchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
   Button logInButton;
   Button signUpButton;
    private FirebaseAuth x;

    public void logInClicked(View view){

        Intent intent1=new Intent(getApplicationContext(),logIn.class);
        startActivity(intent1);
    }
    public void signUpClicked(View view){
        Intent intent2=new Intent(getApplicationContext(),signUp.class);
        startActivity(intent2);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logInButton=findViewById(R.id.logInButton);
        signUpButton= findViewById(R.id.signUpButton);
        x = FirebaseAuth.getInstance();
        if(x.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(), snapsActivity.class));
        }

    }



}
