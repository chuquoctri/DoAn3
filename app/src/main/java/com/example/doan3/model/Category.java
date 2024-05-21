package com.example.doan3.model;

public class Category {
    private int id; // ID của loại câu hỏi
    private String name; // Tên của loại câu hỏi

    // Constructor mặc định
    public Category() {}

    // Constructor với tham số name
    public Category(String name) {
        this.name = name;
    }

    // Getter cho id
    public int getId() {
        return id;
    }

    // Setter cho id
    public void setId(int id) {
        this.id = id;
    }

    // Getter cho name
    public String getName() {
        return name;
    }

    // Setter cho name
    public void setName(String name) {
        this.name = name;
    }

    // Override phương thức toString để hiển thị tên của loại câu hỏi
    @Override
    public String toString() {
        return getName();
    }
}
