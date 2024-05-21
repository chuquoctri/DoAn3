package com.example.doan3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private EditText emailedit, passedit;
    private Button btnlogin, btnregis;
    private ImageButton btnBack;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        // Ánh xạ các thành phần UI
        emailedit = findViewById(R.id.email);
        passedit = findViewById(R.id.password);
        btnlogin = findViewById(R.id.btnlogin);
        btnregis = findViewById(R.id.btnregis);
        btnBack = findViewById(R.id.btnBack);

        // Sự kiện nút login
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        // Sự kiện nút register
        btnregis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  // Quay lại Activity trước đó
                overridePendingTransition(R.anim.activity_slide_in_right, R.anim.activity_slide_out_left);
            }
        });
    }

    private void registerUser() {
        Intent i = new Intent(Login.this, Register.class);
        startActivity(i);
    }

    private void loginUser() {
        String email = emailedit.getText().toString();
        String pass = passedit.getText().toString();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "Vui lòng nhập Email và Password!", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            startActivity(intent);
                            finish(); // Kết thúc activity đăng nhập sau khi đăng nhập thành công
                        } else {
                            Toast.makeText(getApplicationContext(), "Đăng nhập không thành công! Vui lòng kiểm tra lại Email và Password.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
