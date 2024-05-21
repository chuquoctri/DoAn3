package com.example.doan3;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import com.example.doan3.model.Category;
import java.util.List;
import android.media.MediaPlayer;
import android.widget.ImageButton;

public class ChuDe extends AppCompatActivity {
    private Button btnKhoaHoc, btnVanHoa, btnTheThao, btnNgheThuat;
    private static final int REQUEST_CODE_QUESTION = 1;
    private MediaPlayer mediaPlayer;
    private ImageButton btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chu_de);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  // Quay lại Activity trước đó
                overridePendingTransition(R.anim.activity_slide_in_right, R.anim.activity_slide_out_left);
            }
        });

        AnhXa();

        btnKhoaHoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChuDe.this, question.class);
                intent.putExtra("ChuDe", 1);
                startActivity(intent);
            }
        });

        btnVanHoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChuDe.this, question.class);
                intent.putExtra("ChuDe", 2);
                startActivity(intent);
            }
        });

        btnNgheThuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChuDe.this, question.class);
                intent.putExtra("ChuDe", 3);
                startActivity(intent);
            }
        });

        btnTheThao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChuDe.this, question.class);
                intent.putExtra("ChuDe", 4);
                startActivity(intent);
            }
        });
    }
    private void AnhXa() {
        btnKhoaHoc = findViewById(R.id.btnKhoaHoc);
        btnVanHoa = findViewById(R.id.btnVanHoa);
        btnNgheThuat = findViewById(R.id.btnNgheThuat);
        btnTheThao = findViewById(R.id.btnTheThao);
    }
}