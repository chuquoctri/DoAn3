package com.example.doan3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan3.model.Question;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;

public class question extends AppCompatActivity {
    TextView txtChuDe;
    private TextView textViewQuestion;
    private TextView textViewScore;
    private TextView textViewQuestionCount;
    private TextView textViewCategory;
    private TextView textViewCountDown;
    private TextView txtPlus;
    private RadioGroup rbGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private Button buttonConfirmNext;

    private CountDownTimer countDownTimer;
    private ArrayList<Question> questionsList;
    private long timeLeftInMillis;
    private int questionCounter;
    private int questionSize;

    private int Score;
    private boolean answerred;
    private Question currentQuestion;

    private  int cout=0;

    // Khai báo đối tượng MediaPlayer cho âm thanh "yes" và "no"
    private MediaPlayer mediaPlayerYes;
    private MediaPlayer mediaPlayerNo;
    private database database;
    private boolean helpUsedForCurrentQuestion = false;
    private boolean isCountDownPaused = false;
    private boolean isPauseButtonClicked = false;
    private Button buttonPauseResume;
    private Animation scoreAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);


        // Khởi tạo txtPlus thông qua findViewById()
        txtPlus = findViewById(R.id.txtPlus);

        // Load animation từ tệp XML
        scoreAnim = AnimationUtils.loadAnimation(this, R.anim.score_anim);
        // Thiết lập listener cho animation
        scoreAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                txtPlus.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });


        // Thêm xử lý sự kiện cho nút Trợ giúp
        Button buttonHelp = findViewById(R.id.button_help);
        buttonHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kiểm tra nếu chưa trả lời câu hỏi và chưa sử dụng trợ giúp
                if (!answerred && !helpUsed) {
                    // Xóa bỏ hai phương án sai bất kỳ
                    useHelp();
                    // Đánh dấu là đã sử dụng trợ giúp
                    helpUsed = true;
                    // Thay đổi màu của văn bản trong nút thành màu đỏ
                    buttonHelp.setTextColor(Color.RED);
                } else {
                    // Nếu đã trả lời câu hỏi hoặc đã sử dụng trợ giúp
                    Toast.makeText(question.this, "Bạn đã sử dụng trợ giúp này rồi!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // Thêm xử lý sự kiện cho nút "x2 điểm"
        Button buttonx2diem = findViewById(R.id.button_x2diem);
        buttonx2diem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kiểm tra xem người dùng đã sử dụng trợ giúp x2 điểm chưa
                if (!helpUsedx2diem) {
                    x2diem = true;
                    // Đánh dấu rằng trợ giúp đã được sử dụng
                    helpUsedx2diem = true;
                    // Thay đổi màu của văn bản trong nút thành màu đỏ
                    buttonx2diem.setTextColor(Color.RED);
                    Toast.makeText(question.this, "Bạn đã sử dụng trợ giúp x2 điểm!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(question.this, "Bạn đã sử dụng trợ giúp x2 điểm rồi!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // dung thoi gian
        buttonPauseResume = findViewById(R.id.button_pause_resume);
        buttonPauseResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPauseButtonClicked) {
                    // Nếu nút chưa được nhấn, tạm dừng đếm ngược và cập nhật trạng thái của nút
                    pauseCountDown();
                    buttonPauseResume.setTextColor(Color.RED); // Chuyển màu văn bản thành đỏ
                    isPauseButtonClicked = true;
                }
            }
        });


        // Khởi tạo đối tượng MediaPlayer cho âm thanh "yes" và "no"
        mediaPlayerYes = MediaPlayer.create(this, R.raw.yes);
        mediaPlayerNo = MediaPlayer.create(this, R.raw.no);
        anhxa();

        //Nhận dữ liệu từ activity chủ đề
        txtChuDe = findViewById(R.id.text_view_category);
        Intent intent = getIntent();
        int data = intent.getIntExtra("ChuDe", -1); // key phải trùng với key đã gửi
        if(intent != null) {
            // Xử lý dữ liệu nhận được ở đây
            if(data == 1) {
                Toast.makeText(this, "Bạn đã chọn chủ đề Khoa học", Toast.LENGTH_SHORT).show();
                txtChuDe.setText("Khoa học");
            }
            if(data == 2) {
                Toast.makeText(this, "Bạn đã chọn chủ đề Văn hóa", Toast.LENGTH_SHORT).show();
                txtChuDe.setText("Văn hóa");
            }
            if(data == 3) {
                Toast.makeText(this, "Bạn đã chọn chủ đề Nghệ thuật", Toast.LENGTH_SHORT).show();
                txtChuDe.setText("Nghệ thuật");
            }
            if(data == 4) {
                Toast.makeText(this, "Bạn đã chọn chủ đề Thể thao", Toast.LENGTH_SHORT).show();
                txtChuDe.setText("Thể thao");
            }
        }
        database = new database(this);
        // danh sach list chua cau hoi
        int categoryID = data;
        questionsList = database.getQuestions(categoryID);
        //lay kick co danh sach = tong so cau hoi
        questionSize = questionsList.size();
        Log.e("TAG", "onCreate: " + questionSize);
        // dao vi tri cac phan tu cau hoi
        Collections.shuffle(questionsList);

        // show cau hoi va dap an
        showNextQuestion();
        // button xác nhận câu tiếp theo, hoàn thành
        buttonConfirmNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // nếu chưa trả lời câu hỏi
                if(!answerred){
                    // nếu chọn 1 trong 3 đáp án
                    if(rb1.isChecked()|| rb2.isChecked()|| rb3.isChecked() || rb4.isChecked()){
                        // kiểm tra đáp án
                        checkAnswer();
                    }
                    else {
                        Toast.makeText(question.this, "Hãy chọn đáp án",Toast.LENGTH_SHORT).show();
                    }
                }
                // nếu đã tra lời, thực hiện chuyển câu
                else {
                    showNextQuestion();
                }
            }
        });

    }
    // Phương thức để tạm dừng đếm ngược
    private void pauseCountDown() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            isCountDownPaused = true;
        }
    }

    // Phương thức để kiểm tra và sử dụng trợ giúp
    private void useHelp() {
        if (!helpUsedForCurrentQuestion) {
            // Thực hiện trợ giúp cho câu hỏi hiện tại ở đây
            removeTwoIncorrectOptions();
            // Đánh dấu rằng trợ giúp đã được sử dụng cho câu hỏi hiện tại
            helpUsedForCurrentQuestion = true;
        }
    }

    private boolean helpUsedx2diem = false;
    private boolean x2diem = false;

    // Biến cờ để theo dõi trạng thái của trợ giúp cho mỗi câu hỏi
    private boolean helpUsed = false;

    // Phương thức để loại bỏ hai phương án sai và chỉ được gọi một lần cho mỗi câu hỏi
    private void removeTwoIncorrectOptions() {
        // Chỉ thực hiện nếu trợ giúp chưa được sử dụng cho câu hỏi này
        if (!helpUsed) {
            // Lấy một phương án đúng
            int correctAnswer = currentQuestion.getAnsewr();

            // Tạo một danh sách các RadioButton
            ArrayList<RadioButton> radioButtons = new ArrayList<>();
            radioButtons.add(rb1);
            radioButtons.add(rb2);
            radioButtons.add(rb3);
            radioButtons.add(rb4);

            // Xáo trộn danh sách các RadioButton
            Collections.shuffle(radioButtons);

            // Đếm số RadioButton đã ẩn
            int hiddenCount = 0;

            // Ẩn hai RadioButton tương ứng với hai phương án sai
            for (RadioButton radioButton : radioButtons) {
                // Lấy vị trí của RadioButton trong RadioGroup
                int radioButtonIndex = rbGroup.indexOfChild(radioButton);

                // Lấy phương án của RadioButton
                int option = radioButtonIndex + 1; // Vị trí RadioButton bắt đầu từ 0

                if (option != correctAnswer && hiddenCount < 2) {
                    radioButton.setVisibility(View.INVISIBLE);
                    hiddenCount++;
                }
            }

            // Đánh dấu rằng trợ giúp đã được sử dụng cho câu hỏi này
            helpUsed = true;
        }
    }


    // hien thi cau hoi
    private void showNextQuestion(){
        // set lai mau den cho dap an
        rb1.setTextColor(Color.WHITE);
        rb2.setTextColor(Color.WHITE);
        rb3.setTextColor(Color.WHITE);
        rb4.setTextColor(Color.WHITE);

        //xoa chon
        rbGroup.clearCheck();
        //neu con cau hoi
        if(questionCounter<questionSize){
            // lay du lieu ow vi tri couter
            currentQuestion=questionsList.get(questionCounter);
            helpUsed=false;
            textViewQuestion.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());
            rb4.setText(currentQuestion.getOption4());
            rb1.setVisibility(View.VISIBLE);
            rb2.setVisibility(View.VISIBLE);
            rb3.setVisibility(View.VISIBLE);
            rb4.setVisibility(View.VISIBLE);
            //tang sau moi cau hoi
            questionCounter++;
            //set vi tri cau hoi hiện tại
            textViewQuestionCount.setText(+questionCounter+"/"+questionSize);
            // gia tri false chua tra loi, dang show
            answerred=false;
            buttonConfirmNext.setText("Xác nhận");
            // thoi gian tra loi
            timeLeftInMillis=30000;
            // đếm ngươcj thời gian trải lời
            startCountDown();
        }
        else{
            finishQuestion();
        }
    }


    // thời gian điếm ngược
    private void startCountDown() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000 ) {
            @Override
            public void onTick(long l) {
                timeLeftInMillis = l;
                // update
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                // heest gio
                timeLeftInMillis = 0;
                updateCountDownText();
                //phuong thuc kiem tra dap an
                checkAnswer();
            }
        }.start();
    }

    // kiểm tra đáp án
    private void checkAnswer() {
        // true đã trả lời
        answerred = true;
        // trả về RadioButton được chọn trong rbGroup
        RadioButton rbSelected = findViewById(rbGroup.getCheckedRadioButtonId());
        // vị trí RadioButton đã chọn
        int answer = rbGroup.indexOfChild(rbSelected) + 1;
        // nếu trả lời đúng
        if (answer == currentQuestion.getAnsewr()) {
            if(x2diem) {
                x2diem = false;
                Score = Score + 20;
            }
            else {
                Score = Score + 10;
            }
            // hiển thị điểm
            textViewScore.setText("" + Score);
            // phát âm thanh "yes"
            if (mediaPlayerYes != null) {
                mediaPlayerYes.start();
            }
            //Lưu câu hỏi vào bảng lịch sử
            database.insertLichSu(currentQuestion, 1);
            // hiển thị văn bản "+10" và thực hiện animation
            txtPlus.setText("+10");
            txtPlus.setVisibility(View.VISIBLE);
            txtPlus.startAnimation(scoreAnim);

        } else {
            // phát âm thanh "no"
            if (mediaPlayerNo != null) {
                mediaPlayerNo.start();
            }
            //Lưu câu hỏi vào bảng lịch sử
            database.insertLichSu(currentQuestion, 0);
        }
        showSolution();
    }

    // Phương thức để giải phóng tài nguyên MediaPlayer khi hoàn thành Activity
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Giải phóng tài nguyên MediaPlayer
        if (mediaPlayerYes != null) {
            mediaPlayerYes.release();
        }
        if (mediaPlayerNo != null) {
            mediaPlayerNo.release();
        }
    }


    // phương thức hiển thị đáp án
    private void showSolution() {
        // set mau button dap an
        rb1.setTextColor(Color.RED);
        rb2.setTextColor(Color.RED);
        rb3.setTextColor(Color.RED);
        rb4.setTextColor(Color.RED);

        // kiem tra dap an
        switch (currentQuestion.getAnsewr()){
            case 1:
                rb1.setTextColor(Color.GREEN);
                textViewQuestion.setText("Đáp án là A");
                break;
            case 2:
                rb2.setTextColor(Color.GREEN);
                textViewQuestion.setText("Đáp án là B");
                break;
            case 3:
                rb3.setTextColor(Color.GREEN);
                textViewQuestion.setText("Đáp án là C");
                break;
            case 4:
                rb4.setTextColor(Color.GREEN);
                textViewQuestion.setText("Đáp án là D");
                break;
        }
        // nếu còn câu trả lời thì button sẽ sexttext câu tiêsp theo
        if (questionCounter< questionSize){
            buttonConfirmNext.setText("Câu tiếp");
        }
        // settext hòan thành
        else {
            buttonConfirmNext.setText("Hoàn thành");
        }
        // dừng thời gian lại
        countDownTimer.cancel();
    }


    //up date thoi gian

    private void updateCountDownText() {
        // tính phút
        int minutes=(int )((timeLeftInMillis/1000)/60);
        // tính giây
        int seconds= (int) ((timeLeftInMillis/1000)%60);
        // định dang kiểu time
        String timeFormatted= String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
        // hiển thị lên màn hình
        textViewCountDown.setText(timeFormatted);
        // Kiểm tra nếu dưới 10 giây
        if (timeLeftInMillis < 10000) {
            textViewCountDown.setTextColor(Color.RED);
            // Gọi Animation nhấp nháy khi còn dưới 10 giây
            startBlinkAnimation();
        } else {
            textViewCountDown.setTextColor(Color.GREEN);
            // Dừng Animation nếu đang chạy
            stopBlinkAnimation();
        }
    }
    // Phương thức để bắt đầu Animation nhấp nháy
    private void startBlinkAnimation() {
        Animation blinkAnimation = AnimationUtils.loadAnimation(this, R.anim.blink_anim);
        textViewCountDown.startAnimation(blinkAnimation);
    }

    // Phương thức để dừng Animation nhấp nháy
    private void stopBlinkAnimation() {
        textViewCountDown.clearAnimation();
    }


    //back
    private void anhxa(){
        textViewQuestion=findViewById(R.id.text_view_question);
        textViewScore = findViewById(R.id.text_view_score);
        textViewQuestionCount=findViewById(R.id.text_view_question_count);
        textViewCategory=findViewById(R.id.text_view_category);

        textViewCountDown=findViewById(R.id.text_view_countdown);
        rbGroup=findViewById(R.id.radio_group);
        rb1=findViewById(R.id.radio_button1);
        rb2=findViewById(R.id.radio_button2);
        rb3=findViewById(R.id.radio_button3);
        rb4=findViewById(R.id.radio_button4);

        buttonConfirmNext = findViewById(R.id.button_confim_next);

    }
    private void finishQuestion(){
        Intent resultIntent = new Intent(this, KetQua.class);
        resultIntent.putExtra("score", Score);
        resultIntent.putExtra("totalQuestions", questionSize);
        // Các dòng dưới này sẽ gửi số câu đúng, số câu sai (tính được từ số câu hỏi và số câu đúng)
        int correctAnswers = Score / 10;
        int wrongAnswers = questionSize - correctAnswers;
        resultIntent.putExtra("correctAnswers", correctAnswers);
        resultIntent.putExtra("wrongAnswers", wrongAnswers);
        startActivity(resultIntent);
        finish();
    }



}