package com.example.doan3.model;

public class Question {
    private int id;
    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private int ansewr;
    private int categoryID;
    public Question(){}

    // hàm khởi tạo giá trị
    public Question(String question, String option1, String option2, String option3, String option4, int ansewr, int categoryID) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.ansewr = ansewr;
        this.categoryID = categoryID;
    }

    // tạo get set cho các biến
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public int getAnsewr() {
        return ansewr;
    }

    public void setAnsewr(int ansewr) {
        this.ansewr = ansewr;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public int getAnswer() {
        return ansewr;
    }

    private String questionText;
    private int correctAnswer;
    private boolean helpUsed;

    public Question(String questionText, String option1, String option2, String option3, String option4, int correctAnswer) {
        this.questionText = questionText;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.correctAnswer = correctAnswer;
        this.helpUsed = false; // Ban đầu trợ giúp chưa được sử dụng
    }

    // Getter và setter cho trường helpUsed
    public boolean isHelpUsed() {
        return helpUsed;
    }

    public void setHelpUsed(boolean helpUsed) {
        this.helpUsed = helpUsed;
    }

}
