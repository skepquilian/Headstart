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
import com.google.firebase.database.FirebaseDatabase;

public class TruckOwnerRegisterForm extends AppCompatActivity implements View.OnClickListener {

    private EditText    editTextOrganizationName, editTextEmail,
                        editTextPhone, editTextPassword, editTextPasswordConfirm;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    public TruckOwnerRegisterForm() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.truck_owner_register_activity);

        //TextView
        TextView textViewAppName = findViewById(R.id.appName);
        textViewAppName.setOnClickListener(this);

        TextView textViewLogin   = findViewById(R.id.loginUser);
        textViewLogin.setOnClickListener(this);

        //EditText
        editTextOrganizationName = findViewById(R.id.organizationName);
        editTextEmail            = findViewById(R.id.emailAddress);
        editTextPhone            = findViewById(R.id.phoneNumber);
        editTextPassword         = findViewById(R.id.userPassword);
        editTextPasswordConfirm  = findViewById(R.id.passwordConfirm);

        //register Button
        Button btnRegister = findViewById(R.id.registerUser);
        btnRegister.setOnClickListener(this);

        progressBar = findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();

    }

    //Onclick Listener
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.appName || v.getId() == R.id.loginUser){
            startActivity(new Intent(this, TruckOwnerLoginForm.class));
            return;
        }
        if (v.getId() == R.id.registerUser){
            registerUser();
        }
    }

    //Register User Function....
    private void registerUser() {
        String organizationName = editTextOrganizationName.getText().toString().trim();
        String emailAddress     = editTextEmail.getText().toString().trim();
        String phoneNumber      = editTextPhone.getText().toString().trim();
        String userPassword     = editTextPassword.getText().toString().trim();
        String passwordConfirm  = editTextPasswordConfirm.getText().toString().trim();

        //Now we Need If Statements to validate these Inputs..Error Handling
        if (organizationName.isEmpty()){
            editTextOrganizationName.setError("Name is required");
            editTextOrganizationName.requestFocus();
            return;

        }
        if (emailAddress.isEmpty()){
            editTextEmail.setError("Email Field Required");
            editTextEmail.requestFocus();
            return;
        }
        //Check if Email is Valid
        if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()){
            editTextEmail.setError("Please provide valid email");
            editTextEmail.requestFocus();
            return;
        }
        if (phoneNumber.isEmpty() || (phoneNumber.length() < 10)){
            editTextPhone.setError("Enter a valid Phone Number");
            editTextPhone.requestFocus();
            return;
        }

        if (userPassword.isEmpty()){
            editTextPassword.setError("Provide password");
            editTextPassword.requestFocus();
            return;
        }
        if (passwordConfirm.isEmpty()){
            editTextPasswordConfirm.setError("Provide password confirmation");
            editTextPasswordConfirm.requestFocus();
            return;
        }
        if (!userPassword.equals(passwordConfirm)){
            editTextPassword.setError("Password did not match! Re-enter");
            editTextPassword.requestFocus();
            return;
        }
        if (userPassword.length() < 8){
            editTextPassword.setError("Password must be at least 8 characters");
            editTextPassword.requestFocus();
        }

        //set progress Bar to visible when
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(emailAddress,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    //Create Users info with these below
                    User user = new User(organizationName, emailAddress, phoneNumber);

                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){
                                //Redirect to Login Page
                                startActivity(new Intent(TruckOwnerRegisterForm.this, TruckOwnerLoginForm.class));
                                Toast.makeText(TruckOwnerRegisterForm.this, "Registration successful...Login Now", Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(TruckOwnerRegisterForm.this, "Registration Failed...Login Now", Toast.LENGTH_LONG).show();
                            }
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }else {
                    Toast.makeText(TruckOwnerRegisterForm.this, "Registration Failed. TRY AGAIN", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}