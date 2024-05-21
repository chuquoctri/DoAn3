package com.example.doan3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Login_Register extends AppCompatActivity {
    private Button btnLogin, btnRegis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        btnLogin = findViewById(R.id.btnlogin);
        btnRegis = findViewById(R.id.btnregis);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_Register.this, Login.class);
                startActivity(intent);
                overridePendingTransition(R.anim.activity_slide_in_right, R.anim.activity_slide_out_left);
            }
        });

        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_Register.this, Register.class);
                startActivity(intent);
                overridePendingTransition(R.anim.activity_slide_in_right, R.anim.activity_slide_out_left);
            }
        });
    }
}