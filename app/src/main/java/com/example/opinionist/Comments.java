package com.example.opinionist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Comments extends AppCompatActivity {
    EditText subComment, retComment;
    DatabaseReference reff;
    FirebaseAuth mAuth;
    Comment newComment;
    Button btnSubmit, btnretrieve, btndelete, btnLogout, btnLogoutBypass;
    TextView viewComment;
    long maxid = 0;

    RecyclerView commentRecycler;
    Adapter adapter;

    ArrayList<String> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        items = new ArrayList<>();
        items.add("First");
        items.add("Second");
        items.add("Third");

        commentRecycler = findViewById(R.id.commentRecycler);
        commentRecycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this,items);
        commentRecycler.setAdapter(adapter);




        commentRecycler = findViewById(R.id.commentRecycler);

        /* SEND COMMENT TO SERVER */
        // check connection to firebase server
        Toast.makeText(Comments.this, "Firebase connection success!", Toast.LENGTH_LONG).show();

        // get comment from comment textbox
        //subComment = (EditText) findViewById(R.id.editComment);
        newComment = new Comment();

        // create new reference to database. all comment instances will fall under 'comments'
        // must be done for submission and retrieval
        reff = FirebaseDatabase.getInstance().getReference("comments");

        // get number of comments in database and set it to maxid
        // so that next comment can be maxid+1
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    maxid = snapshot.getChildrenCount();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        // Sign out user
        btnLogout = findViewById(R.id.buttonMainLogout);
        mAuth = FirebaseAuth.getInstance();
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( mAuth.getCurrentUser() != null ) {
                    mAuth.signOut();
                    startActivity( new Intent(getApplicationContext(), MainActivity.class) );
                    Toast.makeText(Comments.this, "Logout Successful!",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(Comments.this, "Error: Not logged in", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // main menu logout bypass
        btnLogoutBypass = findViewById(R.id.buttonCommentsLogoutBypass);
        btnLogoutBypass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(getApplicationContext(), MainActivity.class) );
            }
        });
    }

}