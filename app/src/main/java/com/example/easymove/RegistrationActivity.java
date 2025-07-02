package com.example.easymove;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class RegistrationActivity extends AppCompatActivity {

    private EditText mName;
    private EditText mEmail;
    private EditText mPass;
    private EditText mConfirmPass;
    private Button btnReg;
    private TextView mSignin;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();
        registration();
    }

    private void registration() {
        mName = findViewById(R.id.name_reg);
        mEmail = findViewById(R.id.email_reg);
        mPass = findViewById(R.id.password_reg);
        mConfirmPass = findViewById(R.id.confirm_password_reg);
        btnReg = findViewById(R.id.btn_reg);
        mSignin = findViewById(R.id.sign_in);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mName.getText().toString().trim();
                String email = mEmail.getText().toString().trim();
                String pass = mPass.getText().toString().trim();
                String confirmPass = mConfirmPass.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    mName.setError("Name Required");
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email Required");
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    mPass.setError("Password Required");
                    return;
                }
                if (!pass.equals(confirmPass)) {
                    mConfirmPass.setError("Passwords do not match");
                    return;
                }

                // Register the user with Firebase
                mAuth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Registration successful
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    // Optionally, you can send the user to the home screen
                                    startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                                    finish();
                                } else {
                                    // If registration fails, display a message to the user.
                                    mEmail.setError("Registration Failed: " + task.getException().getMessage());
                                }
                            }
                        });
            }
        });

        mSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();  // Optionally close the RegistrationActivity
            }
        });
    }
}
