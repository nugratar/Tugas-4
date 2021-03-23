package com.example.loginsignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    EditText email, pwd;
    Button signup;
    TextView signin;
    FirebaseAuth mFirebaseAuth;
    private CheckBox showPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.emailAddress);
        pwd = findViewById(R.id.password);
        showPwd = findViewById(R.id.show_pass);
        signup = findViewById(R.id.btn_signup);
        signin = findViewById(R.id.btnSignin);

        showPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showPwd.isChecked()) {
                    pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveToSignin = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(moveToSignin);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputEmail = email.getText().toString();
                String inputPwd = pwd.getText().toString();

                if (inputEmail.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter email address!", Toast.LENGTH_SHORT).show();
                } else if (inputPwd.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter password!", Toast.LENGTH_SHORT).show();
                } else if (inputEmail.isEmpty() && inputPwd.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter email address and password!", Toast.LENGTH_SHORT).show();
                } else if (!(inputEmail.isEmpty() && inputPwd.isEmpty())) {
                    mFirebaseAuth.createUserWithEmailAndPassword(inputEmail,inputPwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Registration failed, try again!", Toast.LENGTH_SHORT).show();
                            } else {
                                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                            }
                        }
                    });
                } else {
                    Toast.makeText(MainActivity.this, "There's an error.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}