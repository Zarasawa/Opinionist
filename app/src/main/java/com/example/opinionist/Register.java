package com.example.opinionist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

import org.w3c.dom.Text;

public class Register extends AppCompatActivity {
    EditText mEmail, mPassword;
    Button btnRegister;
    TextView mLogin;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEmail = findViewById(R.id.editRegisterEmail);
        mPassword = findViewById(R.id.editRegisterPassword);
        btnRegister = findViewById(R.id.buttonRegister);
        mLogin = findViewById(R.id.textViewLogin);

        mAuth = FirebaseAuth.getInstance();

        if( mAuth.getCurrentUser() != null ) {
            // MOVE TO COMMENT PAGE HERE
            startActivity( new Intent(getApplicationContext(), MainActivity.class) );
            finish();
        }

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if( TextUtils.isEmpty(email) ) {
                    mEmail.setError("Email is Required");
                    return;
                }
                if( TextUtils.isEmpty(password) ) {
                    mPassword.setError("Password is Required");
                    return;
                }

                if( password.length() < 6) {
                    mPassword.setError("Password length must be >= 6");
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if( task.isSuccessful() ) {
                            Toast.makeText(Register.this, "User Created!", Toast.LENGTH_LONG).show();
                            // MOVE TO COMMENT PAGE HERE
                            startActivity( new Intent(getApplicationContext(), MainActivity.class) );
                        } else {
                            Toast.makeText(Register.this, "Account Creation Failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });


    }
}