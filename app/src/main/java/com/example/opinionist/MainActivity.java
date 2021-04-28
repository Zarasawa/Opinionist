package com.example.opinionist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    EditText subComment, retComment;
    DatabaseReference reff;
    Comments newComments;
    Button btnSubmit, btnretrieve, btndelete;
    TextView viewComment;
    long maxid = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* SEND COMMENT TO SERVER */
        // check connection to firebase server
        Toast.makeText(MainActivity.this, "Firebase connection success!", Toast.LENGTH_LONG).show();

        // get comment from comment textbox
        subComment = (EditText)findViewById(R.id.editComment);
        newComments = new Comments();

        // create new reference to database. all comment instances will fall under 'comments'
        // must be done for submission and retrieval
        reff = FirebaseDatabase.getInstance().getReference("comments");

        // get number of comments in database and set it to maxid
        // so that next comment can be maxid+1
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    maxid=snapshot.getChildrenCount();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // send comment to firebase server when button is clicked
        btnSubmit = (Button)findViewById(R.id.button);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newComments.setComment(String.valueOf(subComment.getText()));
                newComments.setLikes(0);

                reff.child(String.valueOf(maxid+1)).setValue(newComments);
                Toast.makeText(MainActivity.this, "Comment inserted successfuly!", Toast.LENGTH_LONG).show();
            }
        });



        /* RETRIEVE DATA */
        // get xml items
        retComment = (EditText)findViewById(R.id.editRetrieveComment);
        btnretrieve = (Button)findViewById(R.id.button2);
        btndelete = (Button)findViewById(R.id.button3);
        viewComment = (TextView)findViewById(R.id.textViewComment);

        // check when retrieve button is clicked
        btnretrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // grab a snapshot of comments array in database
                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if( snapshot.child(String.valueOf(retComment.getText())).exists() ) { // comment ID exists
                            viewComment.setText(snapshot.child(String.valueOf(retComment.getText())).getValue().toString());
                        } else { // comment ID does not exists
                            viewComment.setText("Comment ID does not exist");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });


        /* DELETE AN ITEM */
        /*  ISSUE:  comment ID can get confusing when deleting a comment from DB. For example,
                    if you delete comment ID 1 but comment ID 2 exists, we need to find a way
                    to refill ID 1. Maybe create an array that stores every ID that is empty and check that array when
                    inserting a new comment before incrementing maxID
         */
        // check when retrieve button is clicked
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // grab a snapshot of comments array in database
                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if( snapshot.child(String.valueOf(retComment.getText())).exists() ) { // comment ID exists
                            snapshot.child(String.valueOf(retComment.getText())).getRef().removeValue();
                            viewComment.setText("Comment removed");
                        } else { // comment ID does not exists
                            viewComment.setText("Comment ID does not exist");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });





    }

//    public void doInc(View v)
//    {
//        TextView msg=(TextView) findViewById(R.id.textView);
//        msg.setText("jfdshfjfhkaflhhfajlfhakfh");
//
//        NetThread nt=new NetThread();
//        nt.saveTV(msg);
//        nt.start();
//
//    }
}


