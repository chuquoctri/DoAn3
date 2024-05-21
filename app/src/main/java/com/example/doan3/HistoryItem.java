package com.example.doan3;

public class HistoryItem {
    private String question; // Nội dung câu hỏi
    private boolean isCorrect; // Biến kiểm tra xem câu hỏi có đúng hay không

    public HistoryItem() {
    }
    public HistoryItem(String question, boolean isCorrect) {
        this.question = question;
        this.isCorrect = isCorrect;
    }

    public String getQuestion() {
        return question;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}
