package com.example.doan3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
public class LichSu extends AppCompatActivity {

    private ListView mListViewHistory;
    private HistoryAdapter mAdapter;
    private List<HistoryItem> mHistoryList;
    private database db;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_su);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  // Quay lại Activity trước đó
                overridePendingTransition(R.anim.activity_slide_in_right, R.anim.activity_slide_out_left);
            }
        });


        db = new database(this); // Khởi tạo đối tượng database
        mListViewHistory = findViewById(R.id.list_view_history); // Ánh xạ ListView từ layout XML
        mHistoryList = db.getHistory(); // Lấy danh sách lịch sử câu hỏi từ cơ sở dữ liệu

        mAdapter = new HistoryAdapter(this, mHistoryList); // Khởi tạo Adapter với danh sách lịch sử câu hỏi
        mListViewHistory.setAdapter(mAdapter); // Gán Adapter cho ListView để hiển thị danh sách
    }

}