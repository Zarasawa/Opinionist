package com.example.opinionist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
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
import java.util.Collections;
import java.util.Comparator;

public class Replies extends AppCompatActivity implements CommentInterface {
    EditText subComment, retComment;
    DatabaseReference reff;
    FirebaseAuth mAuth;
    Comment newComment;
    Button btnSubmit, btnretrieve, btndelete, btnBack, btnLogoutBypass;
    TextView viewComment;
    int maxid = 0;

    RecyclerView commentRecycler;
    AdapterReply adapter;
    ArrayList<Comment> comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replies);

        comments = new ArrayList<Comment>();

        commentRecycler = findViewById(R.id.commentRecycler);
        commentRecycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdapterReply(this,this,comments);
        commentRecycler.setAdapter(adapter);


        commentRecycler = findViewById(R.id.commentRecycler);

        /* SEND COMMENT TO SERVER */
        // check connection to firebase server
        Toast.makeText(Replies.this, "Firebase connection success!", Toast.LENGTH_LONG).show();

        // get comment from comment textbox
        //subComment = (EditText) findViewById(R.id.editComment);
        newComment = new Comment();

        // create new reference to database. all comment instances will fall under 'comments'
        // must be done for submission and retrieval
        reff = FirebaseDatabase.getInstance().getReference("comments");

        //get topic comments and add them to list of topics.

        reff.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                maxid = 200 + (int) snapshot.getChildrenCount(); // remove 200+ when database is reset
                comments.clear();
                for (DataSnapshot child : snapshot.getChildren()) {
                    Comment comment = child.getValue(Comment.class);
                    if(comment.getParentid() == getIntent().getIntExtra("Topic", -2)) {
                        comments.add(comment);
                    }
                    Collections.sort(comments, new LikesComparator());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // reply
        btnSubmit = findViewById(R.id.buttonReplyAdd);
        EditText editReply = findViewById(R.id.editReply);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int parentID = getIntent().getIntExtra("Topic", -2);
                Toast.makeText(Replies.this, "Par id=" + parentID + " maxid=" + maxid, Toast.LENGTH_LONG).show();
                DatabaseReference reff = FirebaseDatabase.getInstance().getReference("comments");

                Comment topic = new Comment();
                topic.setComment(String.valueOf(editReply.getText()));
                topic.setLikes(0);
                topic.setID(maxid+1);
                topic.setParentid(parentID);

                reff.child(String.valueOf(topic.getID())).setValue(topic);
                //reff.child(String.valueOf(topic.getID())).removeValue();


            }
        });

        // Sign out user
        btnBack = findViewById(R.id.buttonBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(getApplicationContext(), Comments.class) );
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

    @Override
    public void upvote(Integer id, Integer upvotes) {
        reff.child(String.valueOf(id)).child("likes").setValue(upvotes);
        //reff.child(String.valueOf(id)).removeValue();

    }

    @Override
    public void create_topic(String comment) {

    }

}