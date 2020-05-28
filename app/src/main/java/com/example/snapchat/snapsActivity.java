package com.example.snapchat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.UUID;

public class snapsActivity extends AppCompatActivity {
    private FirebaseAuth x;
   ListView snapsListView;
   ArrayList<String> emailss;
    ArrayList<DataSnapshot> snaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snaps);
        x=FirebaseAuth.getInstance();
        emailss=new ArrayList<String>();
        snapsListView=(ListView) findViewById(R.id.snapsListView);
        snaps=new ArrayList<DataSnapshot>();
        final ArrayAdapter<String > arrayAdap=new ArrayAdapter<String>(snapsActivity.this, android.R.layout.simple_list_item_1,emailss);
        snapsListView.setAdapter(arrayAdap);
        FirebaseDatabase.getInstance().getReference().child("users").child(x.getCurrentUser().getUid()).child("snaps").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
              emailss.add(dataSnapshot.child("from").getValue().toString());
              snaps.add(dataSnapshot);
              arrayAdap.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                int index=0;
                for(DataSnapshot snappy: snaps){
                  if(snappy.getKey()== dataSnapshot.getKey()){
                     snaps.remove(index);
                     emailss.remove(index);
                     break;
                  }
                  index++;
                }
                arrayAdap.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });


       snapsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Intent intent=new Intent(getApplicationContext(),viewSnapActivity.class);
               intent.putExtra("imageName",snaps.get(position).child("imageName").getValue().toString());
               intent.putExtra("message",snaps.get(position).child("message").getValue().toString());
               intent.putExtra("key",snaps.get(position).getKey());
              startActivity(intent);
           }
       });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.snaps,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.createSnap){
          startActivity(new Intent(getApplicationContext(),createSnapActivity.class));

        }else if(item.getItemId()==R.id.logout){
            x.signOut();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        x.signOut();
        //finish();
    }
}
