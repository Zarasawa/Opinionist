package com.example.opinionist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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

class LikesComparator implements Comparator<Comment> {
    @Override
    public int compare(Comment c1, Comment c2) {
        return c2.getLikes().compareTo(c1.getLikes());
    }
}

interface CommentInterface {
    public void upvote(Integer id, Integer upvotes);
    public void create_topic(String comment);
}

public class Comments extends AppCompatActivity implements CommentInterface {
    EditText subComment, retComment;
    DatabaseReference reff;
    FirebaseAuth mAuth;
    Comment newComment;
    Button btnLogout, btnLogoutBypass, buttonAdd;
    int maxid = 0;

    RecyclerView commentRecycler;
    Adapter adapter;
    ArrayList<Comment> topics;
    ArrayList<Comment> comments;
    ArrayList<Comment> swap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        topics = new ArrayList<Comment>();
        comments = new ArrayList<Comment>();

        commentRecycler = findViewById(R.id.commentRecycler);
        commentRecycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this,this,topics);
        commentRecycler.setAdapter(adapter);


        commentRecycler = findViewById(R.id.commentRecycler);

        /* SEND COMMENT TO SERVER */
        // check connection to firebase server
        //Toast.makeText(Comments.this, "Firebase connection success!", Toast.LENGTH_LONG).show();

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
                topics.clear();
                maxid = (int) snapshot.getChildrenCount();
                for (DataSnapshot child : snapshot.getChildren()) {
                    Comment comment = child.getValue(Comment.class);
                    if(comment.getParentid() < 0) {
                        topics.add(comment);
                    } else {
                        comments.add(comment);
                    }
                    Collections.sort(topics, new LikesComparator());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // main menu logout bypass
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText input = (EditText) findViewById(R.id.editTopic);
                create_topic(String.valueOf(input.getText()));

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

    @Override
    public void upvote(Integer id, Integer upvotes) {
        reff.child(String.valueOf(id)).child("likes").setValue(upvotes);
    }

    @Override
    public void create_topic(String comment) {

        if(comment.length() < 3) {
            Toast.makeText(Comments.this, "Topic too short", Toast.LENGTH_SHORT).show();
            return;
        }

        if(comment.length() > 32) {
            Toast.makeText(Comments.this, "Topic too long", Toast.LENGTH_SHORT).show();
            return;
        }

        Comment topic = new Comment();
        topic.setComment(comment);
        topic.setLikes(0);
        topic.setID(maxid+1);
        topic.setParentid(-1);

        reff.child(String.valueOf(topic.getID())).setValue(topic);

    }
}