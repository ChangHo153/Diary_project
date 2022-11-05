package com.example.diary_project;

import static com.example.diary_project.MainActivity.selectedDate;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.CalendarViewHolder> {

    private View view;
    private ArrayList<Date> dayList;
    private DBHepler mDBHelper;
    private Context mContext;

    public MainAdapter(ArrayList<Date> dayList,Context mContext){
        this.dayList = dayList;
        this.mContext= mContext;
    }

    @NonNull
    @Override
    public MainAdapter.CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_item_day, parent,false);
        return new CalendarViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull MainAdapter.CalendarViewHolder holder, int position) {


        //날짜 변수에 담기
        Date monthDate = dayList.get(position);

        //달력 초기화
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(monthDate);

        //현재 년월
        int currentDay = selectedDate.get(Calendar.DAY_OF_MONTH);  //일
        int currentMonth = selectedDate.get(Calendar.MONTH)+1;
        int currenYear = selectedDate.get(Calendar.YEAR);

        //넘어온 데이터
        int displayDay = dateCalendar.get(Calendar.DAY_OF_MONTH);
        int displayMonth = dateCalendar.get(Calendar.MONTH)+1;  //월
        int displayear = dateCalendar.get(Calendar.YEAR);    //년

        //비교 해서 년, 월 같으면 진한색 아니면 연한색으로 변경
        if(displayMonth == currentMonth && displayear == currenYear){


            //날짜까지 맞으면 색상 표시
            holder.itemView.setBackgroundColor(Color.WHITE);
        }else{
            holder.parentView.setBackgroundColor(Color.parseColor("#D5D5D5"));
        }

        //날짜 변수에 담기
        int dayNo = dateCalendar.get(Calendar.DAY_OF_MONTH);
        holder.dayText.setText(String.valueOf(dayNo));

        if((position +1 )% 7 ==0){//토욜
            holder.dayText.setTextColor(Color.BLUE);
        }else if(position==0 || position % 7 ==0 ) {//일욜
            holder.dayText.setTextColor(Color.RED);
        }

        ArrayList<TodoItem> mTodoItems = new ArrayList<>();
        mDBHelper = new DBHepler(mContext);//this
        //날짜 클릭 이벤트
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(currentDay == displayDay){
                    Dialog dialog = new Dialog(mContext, android.R.style.Theme_Material_Light_Dialog);
                    dialog.setContentView(R.layout.dialog_edit);
                    EditText et_title = dialog.findViewById(R.id.et_title);
                    EditText et_content = dialog.findViewById(R.id.et_content);
                    Button btn_ok = dialog.findViewById(R.id.btn_ok);

                    btn_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //프로그램상에 있는 현재 시간과 날짜를 가져오는 함수
                            //Insert Database
                            String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());//현재 시간 월일시 받아오기
                            mDBHelper.InsertTodo(et_title.getText().toString(), et_content.getText().toString(),currentTime);

                            // Insert UI
                            TodoItem item = new TodoItem();
                            item.setTitle(et_title.getText().toString());
                            item.setContent(et_content.getText().toString());
                            item.setWriteDate(currentTime);

                            mTodoItems.add(item);
                            dialog.dismiss();
                            Toast.makeText(mContext, "할일 목록에 추가 되었습니다", Toast.LENGTH_SHORT).show();  //MainActivity.this
                        }
                    });
                    dialog.show();

                }else {
                    Toast.makeText(mContext, "오늘은 해당 요일이 아닙니다", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return dayList.size();
    }

    public class CalendarViewHolder extends RecyclerView.ViewHolder {
        TextView dayText;
        View parentView;
        RecyclerView mRv_todo;
        TextView tv_title;
        TextView tv_content;
        TextView tv_writeDate;

        public CalendarViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_writeDate = itemView.findViewById(R.id.tv_date);

            mRv_todo = itemView.findViewById(R.id.rv_view);

            dayText = itemView.findViewById(R.id.dayText);

            parentView=itemView.findViewById(R.id.parentView);
        }
    }
}
