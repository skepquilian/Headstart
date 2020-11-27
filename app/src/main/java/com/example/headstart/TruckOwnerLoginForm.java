package com.example.headstart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class TruckOwnerLoginForm extends AppCompatActivity implements View.OnClickListener {

    /**
     * For password validation, before user can sign up successfully, user must provide strong password
     * password which contains the list below,
     **/
    public static final Pattern PASSWORD_PATTERN
            = Pattern.compile(
            "^" +
                    "(?=.*[0-9])" +                 // at least 1 digit
                    "(?=.*[a-z])" +                 // at least 1 lower case letter
                    "(?=.*[A-Z])" +                 // at least 1 upper case letter
                    "(?=.*[@#$%^&+=])" +            // at least 1 special character
                    "(?=\\S+$)" +                   // no white spaces
                    ".{8,}" +                       // at least 8 characters
                    "$"                             //
    );

    private EditText editTextEmail, editTextPassword;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.truck_owner_login_activity);

        //TextView
        //forget password
        TextView textViewForgetPassword = findViewById(R.id.forgetPassword);
        textViewForgetPassword.setOnClickListener(this);

        TextView textViewRegister = findViewById(R.id.registerButton);
        textViewRegister.setOnClickListener(this);

        //EditText
        editTextEmail = findViewById(R.id.emailAddress);
        editTextPassword = findViewById(R.id.userPassword);

        //Login Button
        Button btnLogin = findViewById(R.id.loginButton);
        btnLogin.setOnClickListener(this);
        progressBar = findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();

    }


    //On click listener
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.forgetPassword) {
            startActivity(new Intent(TruckOwnerLoginForm.this, ResetPassword.class));
            return;
        }
        if (v.getId() == R.id.loginButton) {
            loginUser();
            return;
        }
        if (v.getId() == R.id.registerButton) {
            startActivity(new Intent(TruckOwnerLoginForm.this, TruckOwnerRegisterForm.class));
        }

    }


    //Function To Authenticate user
    private void loginUser() {
        //Get User Email and Password before logging in
        String emailAddress = editTextEmail.getText().toString().trim();
        String userPassword = editTextPassword.getText().toString().trim();

        //We Check for Validation and errors ....both email and password
        if (emailAddress.isEmpty()) {
            editTextEmail.setError("Email is Required");
            editTextEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
            editTextEmail.setError("Enter a Valid Email");
            editTextEmail.requestFocus();
            return;
        }
        //For password
        if (userPassword.isEmpty()) {
            editTextPassword.setError("Please Enter Password");
            editTextPassword.requestFocus();
            return;
        }
        if (!PASSWORD_PATTERN.matcher(userPassword).matches()) {
            editTextPassword.setError("Wrong Password'");
            editTextPassword.requestFocus();
            return;
        }

        //Turn progress bar to visible when onclick Login btn..Progress to true when Login button is clicked
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(emailAddress, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    if (mAuth.getCurrentUser().isEmailVerified()) {
                        //Redirect user to Home activity if Login success
                        startActivity(new Intent(TruckOwnerLoginForm.this, HomeActivity.class));
                        //Show success message
                        Toast.makeText(TruckOwnerLoginForm.this, "Login Successful", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(TruckOwnerLoginForm.this, "Please VERIFY your account ", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(TruckOwnerLoginForm.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(TruckOwnerLoginForm.this, MainActivity.class));
        finish();
    }
}