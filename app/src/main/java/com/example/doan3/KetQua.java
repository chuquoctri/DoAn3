package com.example.doan3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class KetQua extends AppCompatActivity {
    private database database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ket_qua);
        // Nhận dữ liệu từ Intent và hiển thị trên giao diện
        Intent intent = getIntent();
        if (intent != null) {
            int score = intent.getIntExtra("score", 0);
            int totalQuestions = intent.getIntExtra("totalQuestions", 0);
            int correctAnswers = intent.getIntExtra("correctAnswers", 0);
            int wrongAnswers = intent.getIntExtra("wrongAnswers", 0);

            TextView txtScore = findViewById(R.id.txtScore);
            TextView txtTotalQuestions = findViewById(R.id.txtTotalQuestions);
            TextView txtCorrectAnswers = findViewById(R.id.txtCorrectAnswers);
            TextView txtWrongAnswers = findViewById(R.id.txtWrongAnswers);

            txtScore.setText("Điểm số: " + score);
            txtTotalQuestions.setText("Tổng số câu hỏi: " + totalQuestions);
            txtCorrectAnswers.setText("Số câu trả lời đúng: " + correctAnswers);
            txtWrongAnswers.setText("Số câu trả lời sai: " + wrongAnswers);

            database = new database(this);
            database.addScore(score);
            Toast.makeText(this, "" + score, Toast.LENGTH_SHORT).show();
        }

        // Thêm sự kiện OnClickListener cho button quay lại trang chính
        Button btnBackToMain = findViewById(R.id.choilai);
        btnBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gọi phương thức để quay lại trang chính
                goBackToMain();
            }
        });
    }

    // Phương thức để quay lại trang chính
    public void goBackToMain() {
        // Tạo Intent để chuyển đến MainActivity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        // Đóng Activity hiện tại để ngăn người dùng quay lại từ Activity trang kết quả
        finish();
    }
}
