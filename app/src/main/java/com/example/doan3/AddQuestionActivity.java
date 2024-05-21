package com.example.doan3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.doan3.model.Category;
import com.example.doan3.model.Question;

import java.util.ArrayList;
import java.util.List;

public class AddQuestionActivity extends AppCompatActivity {
    private EditText editTextQuestion, editTextOption1, editTextOption2, editTextOption3, editTextOption4, editTextCorrectAnswer;
    private Spinner spinnerCategory;
    private Button buttonSaveQuestion;
    private ListView listViewQuestions;
    private ArrayAdapter<Question> questionAdapter;
    private ArrayList<Question> questionList;
    private database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);


        db = new database(this);

        editTextQuestion = findViewById(R.id.editTextQuestion);
        editTextOption1 = findViewById(R.id.editTextOption1);
        editTextOption2 = findViewById(R.id.editTextOption2);
        editTextOption3 = findViewById(R.id.editTextOption3);
        editTextOption4 = findViewById(R.id.editTextOption4);
        editTextCorrectAnswer = findViewById(R.id.editTextCorrectAnswer);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        buttonSaveQuestion = findViewById(R.id.buttonSaveQuestion);
        listViewQuestions = findViewById(R.id.listViewQuestions);

        loadCategories();

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadQuestions();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        buttonSaveQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveQuestion();
            }
        });
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Gọi loadQuestions() khi một mục được chọn trong Spinner
                loadQuestions();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Không cần thực hiện gì khi không chọn gì cả
            }
        });

    }

    private void loadCategories() {
        List<Category> categoryList = db.getDataCategories();
        ArrayAdapter<Category> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);
    }

    private void loadQuestions() {
        Category selectedCategory = (Category) spinnerCategory.getSelectedItem();
        if (selectedCategory != null) {
            questionList = db.getQuestionsByCategory(selectedCategory.getId());
            questionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, questionList);
            listViewQuestions.setAdapter(questionAdapter);
        }
    }

    private void saveQuestion() {
        String questionText = editTextQuestion.getText().toString().trim();
        String option1 = editTextOption1.getText().toString().trim();
        String option2 = editTextOption2.getText().toString().trim();
        String option3 = editTextOption3.getText().toString().trim();
        String option4 = editTextOption4.getText().toString().trim();
        String correctAnswerStr = editTextCorrectAnswer.getText().toString().trim();
        Category selectedCategory = (Category) spinnerCategory.getSelectedItem();

        if (questionText.isEmpty() || option1.isEmpty() || option2.isEmpty() || option3.isEmpty() || option4.isEmpty() || correctAnswerStr.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        int correctAnswer;
        try {
            correctAnswer = Integer.parseInt(correctAnswerStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Đáp án đúng phải là số từ 1 đến 4", Toast.LENGTH_SHORT).show();
            return;
        }

        if (correctAnswer < 1 || correctAnswer > 4) {
            Toast.makeText(this, "Đáp án đúng phải là số từ 1 đến 4", Toast.LENGTH_SHORT).show();
            return;
        }

        Question newQuestion = new Question();
        newQuestion.setQuestion(questionText);
        newQuestion.setOption1(option1);
        newQuestion.setOption2(option2);
        newQuestion.setOption3(option3);
        newQuestion.setOption4(option4);
        newQuestion.setAnsewr(correctAnswer);
        newQuestion.setCategoryID(selectedCategory.getId());

        db.addQuestion(newQuestion);
        loadQuestions(); // Tải lại câu hỏi sau khi thêm câu hỏi mới

        Toast.makeText(this, "Câu hỏi đã được thêm", Toast.LENGTH_SHORT).show();
        clearInputFields(); // Xóa dữ liệu trong các trường nhập
    }

    private void clearInputFields() {
        editTextQuestion.setText("");
        editTextOption1.setText("");
        editTextOption2.setText("");
        editTextOption3.setText("");
        editTextOption4.setText("");
        editTextCorrectAnswer.setText("");
    }

}