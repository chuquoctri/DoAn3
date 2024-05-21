package com.example.doan3;

import android.provider.BaseColumns;

import java.security.PublicKey;

public final class Table {
    private Table(){}
    // Bảng chứa thông tin về các loại câu hỏi.
    public static class CategoriesTable implements BaseColumns{
        // Tên của bảng "categories".
        public static final String TABLE_NAME="categories";
        // Tên của cột chứa tên của loại câu hỏi.
        public static final String COLUMN_NAME="name";
    }
    //Dữ liệu bảng categories
    public static class QuestionsTable implements BaseColumns {
        // tên bảng
        public static final String TABLE_NAME ="questions";
        // Tên của cột chứa nội dung câu hỏi
        public static final String COLUMN_QUESTION ="questions";

        // 4 đáp án
        public static final String COLUMN_OPTION1 ="option1";
        public static final String COLUMN_OPTION2 ="option2";
        public static final String COLUMN_OPTION3 ="option3";
        public static final String COLUMN_OPTION4 ="option4";

        // đáp án
        public static final String COLUMN_ANSWER ="answer";

        // id chuyên mục
        public static final String COLUMN_CATEGORY_ID ="id_categories";

    }
    // Các thông tin của bảng Scores
    public static class ScoresTable {
        public static final String TABLE_NAME = "scores";
        public static final String _ID = "_id";
        public static final String COLUMN_SCORE = "score";
    }
    public static class LichSu implements BaseColumns {
        public static final String TABLE_NAME ="history";
        // tên bảng
        public static final String COLUMN_QUESTION ="questions";

        // 4 đáp án
        public static final String COLUMN_OPTION1 ="option1";
        public static final String COLUMN_OPTION2 ="option2";
        public static final String COLUMN_OPTION3 ="option3";
        public static final String COLUMN_OPTION4 ="option4";

        // đáp án
        // Cột chứa câu trả lời của người dùng.
        public static final String COLUMN_ANSWER ="answer";
        // Cột chứa thông tin về việc câu trả lời của người dùng có đúng hay không.
        public static final String COLUMN_CORRECT ="correct";

        // id chủ đề
        public static final String COLUMN_CATEGORY_ID ="id_categories";


    }
}
