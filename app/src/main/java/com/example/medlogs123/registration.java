package com.example.medlogs123;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

import static com.example.medlogs123.eventsmadel.database;
import static com.example.medlogs123.eventsmadel.dbhelper1;
import static com.example.medlogs123.keys.Col_EMAIL;
import static com.example.medlogs123.keys.Col_PHONE;
import static com.example.medlogs123.keys.Col_NAME;
import static com.example.medlogs123.keys.TBL_NAME;

public class registration extends AppCompatActivity {
    EditText edtName,edtemail,edtphone,edtpass,edtstreet,edtaddress,edtcity,edtpin;
    Button btnReg;
    String name, email, city, pass, phone,address,street,pin ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setContentView(R.layout.activity_registration);
        edtName=findViewById(R.id.txtName);
        edtemail=findViewById(R.id.txtEmail);
        edtphone=findViewById(R.id.txtPhone);
        edtpass=findViewById(R.id.txtPass);
        edtstreet=findViewById(R.id.txtstreet);
        edtcity=findViewById(R.id.txtcity);
        edtaddress=findViewById(R.id.txtaddress);
        edtpin=findViewById(R.id.txtpin);
        btnReg=findViewById(R.id.btnreg);
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });



    }

    private void register() {
        initialize();
        if (!validate()){
            Toast.makeText(getApplicationContext(), "Signup has failed", Toast.LENGTH_SHORT).show();
        }
        else{
            onSignupSuccess();
        }
    }

    private boolean validate() {
        boolean valid=true;
        eventsmadel.getInstance(getApplicationContext());
        eventsmadel.open();

        if(name.isEmpty()||name.length()>32){
            edtName.setError("Enter valid name");
            valid=false;
        }
        else if ((email.isEmpty()) ){


            edtemail.setError("email is empty");
            valid=false;


        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edtemail.setError("Enter valid Email Address");
            valid=false;


        }
        else if ( (checkUser(phone))){
            edtphone.setError("Email is already registered");
            valid=false;

        }
        else if((phone.isEmpty())){
            edtphone.setError("Not Valid Number");
            valid=false;
        }
        else if ((phone.length()!=10 )){
            edtphone.setError("Enter 10 digit Indian mobile number");
            valid=false;

        }
        else if(!Pattern.matches("^[7-9][0-9]{9}$+",phone)){
            edtphone.setError("Not an Indian Number");
            valid=false;

        }

        else if(pass.isEmpty()){
            edtpass.setError("Password should be of 8 characters ");
            valid=false;
        }


        else if(city.isEmpty()||city.length()>10){
            edtcity.setError("Enter a valid city ");
            valid=false;
        }
        eventsmadel.close();

        return valid;


    }

    private void initialize() {

        address = edtaddress.getText().toString().trim();
        pin = edtpin.getText().toString().trim();
        name = edtName.getText().toString().trim();
        email = edtemail.getText().toString().trim();
        phone = edtphone.getText().toString().trim();
        city = edtcity.getText().toString().trim();
        pass = edtpass.getText().toString().trim();
        street = edtstreet.getText().toString().trim();

    }

    private void onSignupSuccess() {


        eventsmadel.getInstance(getApplicationContext());
        eventsmadel.open();
        eventsmadel.add(name,email,phone,pass,street,address,city,pin);
        Intent i=new Intent(registration.this,login.class);
        Toast.makeText(getApplicationContext(),"Records successfully inserted",Toast.LENGTH_SHORT).show();
        eventsmadel.close();
        finish();

    }
    private boolean checkUser(String phone) {
        eventsmadel.getInstance(getApplicationContext());
        eventsmadel.open();
        String[] columns = {
                Col_PHONE
        };
        database = dbhelper1.getReadableDatabase();
        String selection = Col_PHONE + " = ?";
        String[] selectionArgs = {phone};
        Cursor cursor = database.query(TBL_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        database.close();

        if (cursorCount > 0) {
            return true;
        }
        eventsmadel.close();

        return false;
    }
}

