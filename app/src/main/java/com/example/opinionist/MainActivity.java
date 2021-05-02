package com.example.opinionist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    Button btnRegister, btnComments, btnLogin;
    EditText mEmail, mPassword;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnRegister = findViewById(R.id.buttonMainRegister);
        btnComments = findViewById(R.id.buttonMainComments);
        btnLogin = findViewById(R.id.buttonMainLogin);
        mEmail = findViewById(R.id.editTextMainEmail);
        mPassword = findViewById(R.id.editTextMainPassword);
        mAuth = FirebaseAuth.getInstance();

        // check if user is already logged in. Send to comment page if logged in
        if( mAuth.getCurrentUser() != null ) {
            startActivity(new Intent(getApplicationContext(), Comments.class));
            finish();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                // check user input
                if(TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is required");
                }
                if(TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is required");
                }

                // authenticate user
                mAuth.signInWithEmailAndPassword(email, password).
                        addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Login successful",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Comments.class));
                        } else {
                            Toast.makeText(MainActivity.this, "Login failed! " +
                                            task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });



        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity( new Intent(getApplicationContext(), Register.class) );
            }
        });

        // DELETE THIS WHEN TESTING IS DONE
        btnComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(getApplicationContext(), Comments.class) );
            }
        });




    }
}


