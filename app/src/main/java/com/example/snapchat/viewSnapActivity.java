package com.example.snapchat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class viewSnapActivity extends AppCompatActivity {
    private FirebaseAuth x=FirebaseAuth.getInstance();
    TextView messageTextView;
    ImageView snapImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_snap);

        messageTextView=findViewById(R.id.messageTextView);

        snapImageView=findViewById(R.id.snapImageView);
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images").child(getIntent().getStringExtra("imageName"));
        Glide.with(this /* context */)
                .load(storageReference)
                .into(snapImageView);
        messageTextView.setText(getIntent().getStringExtra("message"));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FirebaseDatabase.getInstance().getReference().child("users").child(x.getCurrentUser().getUid()).child("snaps").child(getIntent().getStringExtra("key")).removeValue();
        FirebaseStorage.getInstance().getReference().child("images").child(getIntent().getStringExtra("imageName")).delete();
    }
}
