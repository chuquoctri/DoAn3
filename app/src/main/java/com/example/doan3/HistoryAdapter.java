package com.example.doan3;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;

public class HistoryAdapter extends ArrayAdapter<HistoryItem> { // Lớp HistoryAdapter kế thừa từ ArrayAdapter để tạo ra giao diện cho danh sách lịch sử câu hỏi

    private Context mContext;
    private List<HistoryItem> mHistoryList;

    public HistoryAdapter(@NonNull Context context, @NonNull List<HistoryItem> historyList) {
        super(context, 0, historyList);
        mContext = context;
        mHistoryList = historyList;
    }
    // Phương thức getView để tạo và hiển thị giao diện cho từng mục trong danh sách
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            // Nếu convertView là null, inflate layout từ file XML chi_tiet_lich_cau_hoi
            listItemView = LayoutInflater.from(mContext).inflate(
                    R.layout.chi_tiet_lich_cau_hoi, parent, false);
        }
        // Lấy ra mục lịch sử hiện tại từ danh sách
        HistoryItem currentItem = mHistoryList.get(position);

        // Tìm TextView để hiển thị nội dung câu hỏi
        TextView questionTextView = listItemView.findViewById(R.id.text_view_question);
        questionTextView.setText(currentItem.getQuestion());

        // Tìm TextView để hiển thị kết quả (Đúng/Sai)
        TextView resultTextView = listItemView.findViewById(R.id.text_view_result);
        String resultText = currentItem.isCorrect() ? "Đúng" : "Sai";
        resultTextView.setText(resultText);

        // Đặt màu cho chữ "Đúng" và "Sai"
        if (resultText.equals("Đúng")) {
            resultTextView.setTextColor(mContext.getResources().getColor(android.R.color.holo_green_dark));
        } else {
            resultTextView.setTextColor(mContext.getResources().getColor(android.R.color.holo_red_dark));
        }

        return listItemView;  // Trả về View đã được tạo để hiển thị mục lịch sử câu hỏi
    }
}

