package com.greenstar.mecwheel.crb.controller;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.greenstar.mecwheel.R;
import com.greenstar.mecwheel.crb.model.User;
import com.greenstar.mecwheel.crb.utils.HttpUtils;
import com.greenstar.mecwheel.crb.utils.Util;
import com.greenstar.mecwheel.crb.utils.WebserviceResponse;

public class SignUp extends AppCompatActivity implements View.OnClickListener, WebserviceResponse {
    EditText etName;
    EditText etEmail;
    EditText etPhoneNumber;

    Button btnSignUp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_form_activity);

     //   getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initializeVariables();
    }

    private void initializeVariables() {
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);

        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==btnSignUp.getId()){
            if(isValid()) {
                User user = new User();
                user.setEmail(etEmail.getText().toString());
                user.setPhoneNumber(etPhoneNumber.getText().toString());
                user.setName(etName.getText().toString());
                new Util().signUp(this, user, this);
            }
        }
    }

    private boolean isValid() {
        boolean isValid = false;
        if(!etEmail.getText().toString().equals("") &&
            !etName.getText().toString().equals("") &&
            !etPhoneNumber.getText().toString().equals("")){

            isValid = true;
        }
        return isValid;
    }

    @Override
    public void responseAlert(String response) {
        if(response.equals(Codes.TIMEOUT)){
            Toast.makeText(this, "Timeout Session - Could not connect to server. Please contact Admin",Toast.LENGTH_LONG).show();
        }else if(response.equals(Codes.SOMETHINGWENTWRONG)){
            Toast.makeText(this, "Something went wrong. Please contact Admin",Toast.LENGTH_LONG).show();
        }else if(response.equals(Codes.ALREADY_EXISTS)){
            Toast.makeText(this, "User already exists",Toast.LENGTH_LONG).show();
        }else if(response.equals(Codes.SOMETHINGWENTWRONG2)){
            Toast.makeText(this, "Something went wrong. Please contact Admin",Toast.LENGTH_LONG).show();
        }else if(response.equals(Codes.ALL_OK)){

            Toast.makeText(this, "User registered successfully",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MedIQ.class);
            startActivity(intent);
            finish();
        }
    }
}
