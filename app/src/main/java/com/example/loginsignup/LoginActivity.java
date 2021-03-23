package com.example.loginsignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText email, pwd;
    Button signin;
    TextView signup, resetPass;
    FirebaseAuth mFirebaseAuth;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    CheckBox showPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.emailAddress);
        pwd = findViewById(R.id.password);
        showPwd = findViewById(R.id.showPass);
        resetPass = findViewById(R.id.resetPassword);
        signin = findViewById(R.id.btn_signin);
        signup = findViewById(R.id.btn_signup);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if(mFirebaseUser != null) {
                    Toast.makeText(LoginActivity.this, "Login successfully!", Toast.LENGTH_SHORT).show();
                    Intent intoHome = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intoHome);
                } else {
                    Toast.makeText(LoginActivity.this, "Please login first.", Toast.LENGTH_SHORT).show();
                }
            }
        };
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
                String inputEmail = email.getText().toString();
                String inputPwd = pwd.getText().toString();

                if (inputEmail.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Enter email address!", Toast.LENGTH_SHORT).show();
                } else if (inputPwd.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Enter password!", Toast.LENGTH_SHORT).show();
                } else if (inputEmail.isEmpty() && inputPwd.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Enter email address and password!", Toast.LENGTH_SHORT).show();
                } else if (!(inputEmail.isEmpty() && inputPwd.isEmpty())) {
                    mFirebaseAuth.signInWithEmailAndPassword(inputEmail,inputPwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Login error, please login again.", Toast.LENGTH_SHORT).show();
                            } else {
                                Intent intoHome = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intoHome);
                            }
                        }
                    });
                } else {
                    Toast.makeText(LoginActivity.this, "There's an error.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intoSignup = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intoSignup);
            }
        });
        resetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intoReset = new Intent(LoginActivity.this, ResetPasswordActivity.class);
                startActivity(intoReset);
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}