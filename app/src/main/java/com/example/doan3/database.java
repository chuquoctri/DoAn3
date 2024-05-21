package com.example.doan3;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.doan3.model.Category;
import com.example.doan3.model.Question;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;

public class database extends SQLiteOpenHelper {

    private static  final String DATABASE_NAME ="Question.db";
    private  static final int VERTION =1;
    private SQLiteDatabase db;

    public database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERTION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        // biến bảng chuyên mục
        final String CATEGORIES_TABLE = "CREATE TABLE " +
                Table.CategoriesTable.TABLE_NAME + "("+
                Table.CategoriesTable._ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                Table.CategoriesTable.COLUMN_NAME + " TEXT "+   // Thêm một khoảng trắng sau "TEXT"
                ")";
//biên bảng question
        final String QUESTIONS_TABLE= "CREATE TABLE " +
                Table.QuestionsTable.TABLE_NAME +"("+
                Table.QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                Table.QuestionsTable.COLUMN_QUESTION+" TEXT,"+
                Table.QuestionsTable.COLUMN_OPTION1+" TEXT,"+
                Table.QuestionsTable.COLUMN_OPTION2 +" TEXT,"+
                Table.QuestionsTable.COLUMN_OPTION3 + " TEXT,"+
                Table.QuestionsTable.COLUMN_OPTION4 + " TEXT,"+
                Table.QuestionsTable.COLUMN_ANSWER + " INTEGER,"+
                Table.QuestionsTable.COLUMN_CATEGORY_ID + " INTEGER,"+
                "FOREIGN KEY(" + Table.QuestionsTable.COLUMN_CATEGORY_ID + ") REFERENCES "+
                Table.CategoriesTable.TABLE_NAME + "(" + Table.CategoriesTable._ID + ")" + "ON DELETE CASCADE" +
                ")";
        //biên bảng question
        final String QUESTIONS_LichSu= "CREATE TABLE " +
                Table.LichSu.TABLE_NAME +"("+
                Table.LichSu._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                Table.LichSu.COLUMN_QUESTION+" TEXT,"+
                Table.LichSu.COLUMN_OPTION1+" TEXT,"+
                Table.LichSu.COLUMN_OPTION2 +" TEXT,"+
                Table.LichSu.COLUMN_OPTION3 + " TEXT,"+
                Table.LichSu.COLUMN_OPTION4 + " TEXT,"+
                Table.LichSu.COLUMN_ANSWER + " INTEGER,"+
                Table.LichSu.COLUMN_CORRECT + " INTEGER,"+
                Table.LichSu.COLUMN_CATEGORY_ID + " INTEGER,"+
                "FOREIGN KEY(" + Table.LichSu.COLUMN_CATEGORY_ID + ") REFERENCES "+
                Table.CategoriesTable.TABLE_NAME + "(" + Table.CategoriesTable._ID + ")" +
                ")";

        // bang score
        final String SCORES_TABLE = "CREATE TABLE " +
                Table.ScoresTable.TABLE_NAME + "(" +
                Table.ScoresTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Table.ScoresTable.COLUMN_SCORE + " INTEGER" +
                ")";


        // Tạo bảng
        db.execSQL(SCORES_TABLE);

        db.execSQL(CATEGORIES_TABLE);

        db.execSQL(QUESTIONS_LichSu);

        db.execSQL(QUESTIONS_TABLE);


        // insert du lieu
        CreatCateries();
        CreateQuestion();
        insertScore();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS "+ Table.CategoriesTable.COLUMN_NAME);
    db.execSQL("DROP TABLE IF EXISTS "+ Table.QuestionsTable.TABLE_NAME);
    db.execSQL("DROP TABLE IF EXISTS "+ Table.LichSu.TABLE_NAME);
    onCreate(db);
    }
    private void insertCategories(Category category){
        ContentValues values = new ContentValues();
        values.put(Table.CategoriesTable.COLUMN_NAME, category.getName());
        db.insert(Table.CategoriesTable.TABLE_NAME, null,values);
    }

    //CÁC GIÁ TRI INSERT
    private void CreatCateries(){
        Category c1 = new Category("Khoa học");
        Category c2 = new Category("Văn hóa");
        Category c3 = new Category("Nghệ thuật");
        Category c4 = new Category("Thể thao");
        insertCategories(c4);

    }
    private void insertScore(){
        ContentValues values = new ContentValues();
        values.put(Table.ScoresTable.COLUMN_SCORE, 0);

        db.insert(Table.ScoresTable.TABLE_NAME,null,values);
    }
    private void insertQuestion(Question question){
        ContentValues values = new ContentValues();
        values.put(Table.QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        values.put(Table.QuestionsTable.COLUMN_OPTION1, question.getOption1());
        values.put(Table.QuestionsTable.COLUMN_OPTION2, question.getOption2());
        values.put(Table.QuestionsTable.COLUMN_OPTION3, question.getOption3());
        values.put(Table.QuestionsTable.COLUMN_OPTION4, question.getOption4());
        values.put(Table.QuestionsTable.COLUMN_ANSWER, question.getAnsewr());
        values.put(Table.QuestionsTable.COLUMN_CATEGORY_ID,question.getCategoryID());

        db.insert(Table.QuestionsTable.TABLE_NAME,null,values);
    }
    public void insertLichSu(Question question, int correct){
        ContentValues values = new ContentValues();
        values.put(Table.LichSu.COLUMN_QUESTION, question.getQuestion());
        values.put(Table.LichSu.COLUMN_OPTION1, question.getOption1());
        values.put(Table.LichSu.COLUMN_OPTION2, question.getOption2());
        values.put(Table.LichSu.COLUMN_OPTION3, question.getOption3());
        values.put(Table.LichSu.COLUMN_OPTION4, question.getOption4());
        values.put(Table.LichSu.COLUMN_ANSWER, question.getAnsewr());
        values.put(Table.LichSu.COLUMN_CATEGORY_ID,question.getCategoryID());
        values.put(Table.LichSu.COLUMN_CORRECT, correct);

        db.insert(Table.LichSu.TABLE_NAME,null,values);
    }

    // tạo bảng câu hỏi
    private void CreateQuestion(){

        // câu hỏi về khoa học
        Question q1 = new Question("Một người không có bất kỳ sắc tố hoặc màu được gọi là gì?",
                "A. Bạch tạng",
                "B. Indigo",
                "C. Inuit",
                "D. Eskimo ",  1, 1);
        insertQuestion(q1);

        Question q2 = new Question("Có bao nhiêu xương trong cơ thể con người?",
                "A. 360",
                "B. 206",
                "C. 106",
                "D. 260 ",  2, 1);
        insertQuestion(q2);

        Question q3 = new Question("Màu gì bắt mắt đầu tiên?",
                "A. Xanh",
                "B. Đỏ",
                "C. Tím",
                "D. Vàng ",  4, 1);
        insertQuestion(q3);

        Question q4 = new Question("Ở nhiệt độ nào thì độ C và độ F bằng nhau?",
                "A. 0",
                "B. -40",
                "C. 1",
                "D. 40",  2, 1);
        insertQuestion(q4);

        Question q5 = new Question("Hành tinh nào quay nhanh nhất, hoàn thành một vòng quay chỉ trong 10 giờ. Đó là hành tinh nào? ",
                "A. sao Mộc",
                "B. sao Hỏa",
                "C. sao Kim",
                "D. sao Thiên Vương",  2, 1);
        insertQuestion(q5);

        // câu hỏi về văn hóa
        Question q6 = new Question("Tháp nghiêng Pisa là biểu tượng của nước nào? ",
                "A.Đức ",
                "B.Thụy Sĩ ",
                "C.Italy ",
                "D.Romania ",  3, 2);
        insertQuestion(q6);
        Question q7 = new Question("Cây chua me đất là biểu tượng của xứ sở nào? ",
                "A.Bắc Ireland ",
                "B.Xứ Wales ",
                "C.Scotland ",
                "D.Nga ",  1, 2);
        insertQuestion(q7);
        Question q8 = new Question("Chuột túi là biểu tượng của quốc gia nào? ",
                "A.Anh ",
                "B.Úc ",
                "C.Nhật ",
                "D.Hàn ",  2, 2);
        insertQuestion(q8);
        Question q9 = new Question("Loại chim nào dưới đây là biểu tượng của đất nước Đan Mạch? ",
                "A. Đại bàng ",
                "B.Cú mèo ",
                "C.Thiên nga ",
                "D.Bồ câu ",  3, 2);
        insertQuestion(q9);
        Question q10 = new Question("Pháp có ba công trình biểu tượng nổi tiếng, đó là tháp Eiffel, nhà thờ Đức Bà Paris, và: ",
                "A.Nhà hát Opera ",
                "B.Tòa nhà Empire State ",
                "C. Bảo tàng Louvre ",
                "D.Tháp đồng hồ Big Ben ",  3, 2);
        insertQuestion(q10);

        // nghệ thuật
        Question q11 = new Question("Ai đã vẽ Bữa ăn tối cuối cùng trong khoảng thời gian ba năm từ 1495 đến 1498? ",
                "A.michaelangelo ",
                "B.Raphael ",
                "C.Leonardo da Vinci ",
                "D.ThanhPam ",  3, 3);
        insertQuestion(q11);
        Question q12 = new Question("Nghệ sĩ nào nổi tiếng với những miêu tả đầy màu sắc về cuộc sống về đêm ở Paris? ",
                "A.Dubuffet ",
                "B.Maneth ",
                "C.Rất nhiều ",
                "D.Toulouse-Lautrec ",  2, 3);
        insertQuestion(q12);
        Question q13 = new Question("Nghệ sĩ nào đã bọc tòa nhà Reichstag của Berlin bằng vải để thể hiện nghệ thuật của mình vào năm 1995? ",
                "A.Cisco ",
                "B.Crisco ",
                "C.Christo ",
                "D.Chrystal ",  3, 3);
        insertQuestion(q13);
        Question q14 = new Question("Bức tranh \"Người gác đêm\" do họa sĩ nào vẽ? ",
                "A.Rubens ",
                "B.Van Eyck ",
                "C.Gainsborough ",
                "D.Rembrandt ",  4, 3);
        insertQuestion(q14);
        Question q15 = new Question("Họa sĩ nào trong số này không phải là người Ý? ",
                "A.Pablo Picasso ",
                "B.Leonardo da Vinci ",
                "C.Titian ",
                "D.Caravaggio ",  4, 3);
        insertQuestion(q15);

        // thể thao
        Question q16 = new Question("Đội bóng nào vô địch World Cup nhiều nhất? ",
                "A.Đức ",
                "B.Pháp ",
                "C.Brazill ",
                "D.Bồ Đào Nha ",  3, 4);
        insertQuestion(q16);
        Question q17 = new Question("Việt Nam dừng chân tại vòng loại thứ mấy tại World Cup 2022? ",
                "A.Vòng 1 ",
                "B.Vòng 2 ",
                "C.Vòng 3 ",
                "D.không tham dự ",  3, 4);
        insertQuestion(q17);
        Question q18 = new Question("Đội tuyển nào thuộc Lục địa đen đạt được thành tích vào đến tứ kết tại WC cùng với Cameroon?",
                "A.Ghana ",
                "B.Ai cập ",
                "C.Ma Rốc ",
                "D.Nigeria ",  1, 4);
        insertQuestion(q18);
        Question q19 = new Question("Có bao nhiêu quốc gia tham gia Thế vận hội mùa hè 2016 tại Rio de Janeiro, Brasil ?",
                "A.204 ",
                "B.205 ",
                "C.206 ",
                "D.207 ",  4, 4);
        insertQuestion(q19);
        Question q20 = new Question("Thành phố nào đã từng 2 lần tổ chức Thế vận hội mùa đông? ",
                "A.Tokyo, Nhật Bản ",
                "B.Bắc Kinh, Trung Quốc ",
                "C.Pyeongchang, Hàn Quốc ",
                "D.Newyork, Mỹ ",  4, 4);
        insertQuestion(q20);
    }

    @SuppressLint("Range")
    // hàm trả về dữ liệu các chủ đề
    public List<Category> getDataCategories(){
        List<Category> categoryList=new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+Table.CategoriesTable.TABLE_NAME,null);
        if (c.moveToFirst()){
            do{
                Category category= new Category();
                category.setId(c.getInt(c.getColumnIndex(Table.CategoriesTable._ID)));
                category.setName(c.getString(c.getColumnIndex(Table.CategoriesTable.COLUMN_NAME)));
                categoryList.add(category);
            }
            while (c.moveToNext());
        }
        c.close();
        return  categoryList;
    }

    //Lấy dữ liệu câu hỏi và đáp án có id= id_category theo chủ đề chọn
    @SuppressLint("Range")
    public ArrayList<Question> getQuestions(int catgoryID){
        ArrayList<Question> questionArrayList = new ArrayList<>();
        db = getReadableDatabase();
        String selection = Table.QuestionsTable.COLUMN_CATEGORY_ID+"= ?";
        String[] selectionArgs = new String[]{String.valueOf(catgoryID)};
        Cursor c = db.query(Table.QuestionsTable.TABLE_NAME,
                null, selection, selectionArgs,null,null,null);
        if(c.moveToFirst()){
            do{
                Question question = new Question();

                question.setId(c.getInt(c.getColumnIndex(Table.QuestionsTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(Table.QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(Table.QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(Table.QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(Table.QuestionsTable.COLUMN_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndex(Table.QuestionsTable.COLUMN_OPTION4)));
                question.setAnsewr(c.getInt(c.getColumnIndex(Table.QuestionsTable.COLUMN_ANSWER)));
                question.setCategoryID(c.getInt(c.getColumnIndex(Table.QuestionsTable.COLUMN_CATEGORY_ID)));

                questionArrayList.add(question);

            }
            while (c.moveToNext());
        }
        c.close();
        return questionArrayList;
    }

    @SuppressLint("Range")
    public ArrayList<HistoryItem> getHistory(){
        ArrayList<HistoryItem> questionArrayList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.query(Table.LichSu.TABLE_NAME,
                null, null, null,null,null,null);
        if(c.moveToFirst()){
            do{
                HistoryItem historyItem = new HistoryItem();

                historyItem.setQuestion(c.getString(c.getColumnIndex(Table.LichSu.COLUMN_QUESTION)));
                historyItem.setCorrect(c.getInt(c.getColumnIndex(Table.LichSu.COLUMN_CORRECT)) == 1);

                questionArrayList.add(historyItem);

            }
            while (c.moveToNext());
        }
        c.close();
        return questionArrayList;
    }
    // Trong lớp database, thêm phương thức để cộng điểm
    public void addScore(int scoreToAdd) {
        SQLiteDatabase db = getWritableDatabase();

        // Lấy tổng điểm hiện tại từ cơ sở dữ liệu
        int currentScore = getCurrentScore();

        // Cộng điểm mới vào tổng điểm hiện tại
        int newScore = currentScore + scoreToAdd;

        // Cập nhật tổng điểm trong cơ sở dữ liệu khi id = 1
        ContentValues values = new ContentValues();
        values.put(Table.ScoresTable.COLUMN_SCORE, newScore);
        String selection = Table.ScoresTable._ID + " = ?";
        String[] selectionArgs = { "1" }; // Điều kiện id = 1
        db.update(Table.ScoresTable.TABLE_NAME, values, selection, selectionArgs);

    }

    // Phương thức để lấy tổng điểm từ cơ sở dữ liệu
    public int getCurrentScore() {
        SQLiteDatabase db = getReadableDatabase();
        int score = 0;

        Cursor cursor = db.rawQuery("SELECT " + Table.ScoresTable.COLUMN_SCORE +
                " FROM " + Table.ScoresTable.TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            score = cursor.getInt(0);
        }

        cursor.close();
        return score;
    }

    public void addQuestion(Question question) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Table.QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        values.put(Table.QuestionsTable.COLUMN_OPTION1, question.getOption1());
        values.put(Table.QuestionsTable.COLUMN_OPTION2, question.getOption2());
        values.put(Table.QuestionsTable.COLUMN_OPTION3, question.getOption3());
        values.put(Table.QuestionsTable.COLUMN_OPTION4, question.getOption4());
        values.put(Table.QuestionsTable.COLUMN_ANSWER, question.getAnsewr());
        values.put(Table.QuestionsTable.COLUMN_CATEGORY_ID, question.getCategoryID());
        db.insert(Table.QuestionsTable.TABLE_NAME, null, values);
        db.close();
    }
    public ArrayList<Question> getQuestionsByCategory(int categoryId) {
        ArrayList<Question> questionList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + Table.QuestionsTable.TABLE_NAME + " WHERE " + Table.QuestionsTable.COLUMN_CATEGORY_ID + " = ?", new String[]{String.valueOf(categoryId)});
        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(c.getInt(c.getColumnIndexOrThrow(Table.QuestionsTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndexOrThrow(Table.QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndexOrThrow(Table.QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndexOrThrow(Table.QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndexOrThrow(Table.QuestionsTable.COLUMN_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndexOrThrow(Table.QuestionsTable.COLUMN_OPTION4)));
                question.setAnsewr(c.getInt(c.getColumnIndexOrThrow(Table.QuestionsTable.COLUMN_ANSWER)));
                question.setCategoryID(c.getInt(c.getColumnIndexOrThrow(Table.QuestionsTable.COLUMN_CATEGORY_ID)));
                questionList.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }



}
