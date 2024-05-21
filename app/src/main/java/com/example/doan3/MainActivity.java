package com.example.doan3;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private ImageView hopqua;
    private Button btnPlay;
    private Button btnLichSu;
    private TextView textScore;
    private database database;
    private TextView textTenNguoiChoi;
    private MediaPlayer mediaPlayer;
    private SeekBar seekBarVolume;
    private Button ThemCauHoi;

    //setting
    ImageButton settingsIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ThemCauHoi = findViewById(R.id.ThemCauHoi);
        ThemCauHoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang màn hình Chủ Đề khi nhấn nút "Chơi Ngay"
                Intent intent = new Intent(MainActivity.this, AddQuestionActivity.class);
                startActivity(intent);
            }
        });
        // setting
        settingsIcon = findViewById(R.id.settingsIcon);
        settingsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show settings dialog when settings icon is clicked
                showSettingsDialog();
            }
        });

        // Khởi tạo và cấu hình MediaPlayer
        mediaPlayer = MediaPlayer.create(this, R.raw.backgorund);
        mediaPlayer.setLooping(true); // Lặp lại vô hạn
        mediaPlayer.start(); // Bắt đầu phát nhạc

        // Ánh xạ TextView textTenNguoiChoi từ layout
        textTenNguoiChoi = findViewById(R.id.textTenNguoiChoi);

        // Lấy thông tin người dùng đã đăng nhập từ Firebase
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            // Nếu người dùng đã đăng nhập, lấy tên người dùng và hiển thị lên TextView
            String displayName = user.getDisplayName();
            textTenNguoiChoi.setText(displayName);
        } else {
            // Nếu không có người dùng đăng nhập, hiển thị một giá trị mặc định hoặc thông báo khác
            textTenNguoiChoi.setText("Khách");
        }

        btnLichSu =findViewById(R.id.button_lichsu);
        btnLichSu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang màn hình lịch sử khi nhấn vào textScore
                Intent intent = new Intent(MainActivity.this, LichSu.class);
                startActivity(intent);
            }
        });


        // Khởi tạo database
        database = new database(this);

        // Ánh xạ các thành phần trong layout
        hopqua = findViewById(R.id.img_hopqua);
        btnPlay = findViewById(R.id.btnPlay);
        textScore = findViewById(R.id.textScore);

        // Thiết lập sự kiện click cho nút "Chơi Ngay"
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang màn hình Chủ Đề khi nhấn nút "Chơi Ngay"
                Intent intent = new Intent(MainActivity.this, ChuDe.class);
                startActivity(intent);
            }
        });

        // Load animation và áp dụng cho ImageView hopqua
        Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotate);
        hopqua.startAnimation(animation);

        // Hiển thị tổng điểm lên TextView textScore
        int totalScore = database.getCurrentScore(); // Lấy tổng điểm từ database

        textScore.setText(String.valueOf(totalScore)); // Hiển thị tổng điểm
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Cập nhật tổng điểm mỗi khi MainActivity được resumed
        int totalScore = database.getCurrentScore();
        textScore.setText(String.valueOf(totalScore));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Giải phóng tài nguyên MediaPlayer khi Activity bị hủy
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
    // Khởi tạo SeekBar điều chỉnh âm lượng
    private void initializeVolumeSeekBar() {
        // Thiết lập giá trị tối đa cho SeekBar là 100 (đại diện cho 100%)
        seekBarVolume.setMax(100);

        // Thiết lập giá trị hiện tại cho SeekBar là 50% âm lượng
        seekBarVolume.setProgress(50);

        // Thiết lập sự kiện thay đổi giá trị của SeekBar
        seekBarVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    // Cập nhật âm lượng của MediaPlayer khi người dùng thay đổi giá trị SeekBar
                    float volume = progress / 100f;
                    mediaPlayer.setVolume(volume, volume);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }
    //setting
    private void showSettingsDialog() {
        // Create a BottomSheetDialog
        BottomSheetDialog settingsDialog = new BottomSheetDialog(this);
        View settingsView = getLayoutInflater().inflate(R.layout.activity_cai_dat, null);
        // Ánh xạ SeekBar từ layout
        seekBarVolume = settingsView.findViewById(R.id.seekBarVolume);
        if (seekBarVolume == null) {
            Toast.makeText(this, "Không thể tìm thấy SeekBar", Toast.LENGTH_SHORT).show();
            return;
        }


        // Khởi tạo SeekBar điều chỉnh âm lượng
        initializeVolumeSeekBar();
        // Ánh xạ các thành phần trong XML cài đặt
        ImageButton closeButton = settingsView.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingsDialog.dismiss(); // Đóng dialog khi nút được nhấn
            }
        });

        // Hiển thị dialog ở góc phải màn hình bên trên
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.gravity = Gravity.TOP | Gravity.END; // Thiết lập vị trí của dialog
        settingsDialog.getWindow().setAttributes(layoutParams);

        settingsDialog.setContentView(settingsView);
        settingsDialog.show();
        

    }
}
