package com.danieldk.brewu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class Login extends AppCompatActivity {

    Button btn_login;
    EditText text_loginEmail;
    EditText text_loginPassword;
    ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = findViewById(R.id.btn_login);
        text_loginEmail = findViewById(R.id.text_loginEmail);
        text_loginPassword = findViewById(R.id.text_loginPassword);
        imgView = (ImageView) findViewById(R.id.imgView_login);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });

    }

    private Boolean Login(){
        Intent intent = new Intent(this, MyBrews.class);
        startActivity(intent);
        return true;
    }
}
