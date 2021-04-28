package com.example.opinionist;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.net.InetAddress;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    EditText comment;
    DatabaseReference reff;
    Comment newComment;
    Button btnsubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // check connection to firebase server
        Toast.makeText(MainActivity.this, "Firebase connection success!", Toast.LENGTH_LONG).show();

        // get comment from comment textbox
        comment = (EditText)findViewById(R.id.editComment);
        newComment = new Comment();

        // create new reference to database. all comment instances will fall under 'comments'
        reff = FirebaseDatabase.getInstance().getReference("comments");


        btnsubmit = (Button)findViewById(R.id.button);
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newComment.setComment(String.valueOf(comment.getText()));
                newComment.setLikes(0);

                reff.push().setValue(newComment);
                Toast.makeText(MainActivity.this, "Comment inserted successfuly!", Toast.LENGTH_LONG).show();
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


