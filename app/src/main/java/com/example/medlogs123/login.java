package com.example.medlogs123;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.medlogs123.eventsmadel.database;




public class login extends AppCompatActivity {
    TextView txtE,txtR;
    Button btlogin;
    EditText phone,edtPass;
    CheckBox ckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SharedPreferences sp;
        final SharedPreferences.Editor edit;

        sp=getSharedPreferences("App name",MODE_PRIVATE);
        edit=sp.edit();

        txtE=findViewById(R.id.txtView);
        txtR=findViewById(R.id.txtReg);

        phone=findViewById(R.id.editphone);
        edtPass=findViewById(R.id.txtPassword);

        ckbox=findViewById(R.id.chkbox);

        btlogin=findViewById(R.id.btnlogin);

        btlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneno=phone.getText().toString().trim();
                String pass=edtPass.getText().toString().trim();
                if (phoneno.equals("")||pass.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Fill All The Fields",Toast.LENGTH_SHORT).show();

                    if(phoneno.equals(""))
                    {
                        phone.setError("enter phone number");
                    }
                    if(pass.equals(""))
                    {
                        edtPass.setError("pass is Empty");
                    }
                }
                else
                {
                    eventsmadel.getInstance(getApplicationContext());
                    eventsmadel.open();
                    Cursor cursor=database.rawQuery("SELECT * FROM " + keys.TBL_NAME + " WHERE " + keys.Col_PHONE + "=? AND " + keys.Col_PASS + "=?", new String[]{phoneno, pass});
                    if (cursor!=null)
                    {
                        cursor.moveToLast();
                        if (cursor.getCount()>0)
                        {
                            Toast.makeText(getApplicationContext(), "Successfully LoggedIn", Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(login.this,home.class);

                            if (ckbox.isChecked())
                            {
                                edit.putString("phone",phoneno);
                                edit.putString("pass",pass);
                                edit.commit();
                            }
                            else
                            {
                                edit.remove("phone");
                                edit.remove("pass");
                                edit.commit();
                            }
                            startActivity(i);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Invalid Login Credentials",Toast.LENGTH_SHORT).show();
                            edit.remove("phone");
                            edit.remove("pass");
                            edit.commit();
                        }
                    }

                    eventsmadel.close();
                }

            }

        });

        if (sp.contains("phone"));
        phone.setText(sp.getString("phone",""));

        if (sp.contains("pass"));
        edtPass.setText(sp.getString("pass",""));

        txtR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a=new Intent(login.this,registration.class);
                startActivity(a);
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        Intent i=new Intent(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_HOME);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }




}



