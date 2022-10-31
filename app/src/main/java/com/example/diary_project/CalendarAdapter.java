package com.example.diary_project;

import static com.example.diary_project.MainActivity.selectedDate;

import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>{

    ArrayList<Date> dayList;

    public CalendarAdapter(ArrayList<Date> dayList){

        this.dayList = dayList;
    }

    @NonNull
    @Override
    public CalendarAdapter.CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_item_day, parent,false);
        return new CalendarViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull CalendarAdapter.CalendarViewHolder holder, int position) {
        //날짜 변수에 담기
        Date monthDate = dayList.get(position);

        //달력 초기화
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(monthDate);

        //현재 년월
        int currentDay = selectedDate.get(Calendar.DAY_OF_MONTH);
        int currentMonth = selectedDate.get(Calendar.MONTH)+1;
        int currenYear = selectedDate.get(Calendar.YEAR);

        //넘어온 데이터
        int displayDay = dateCalendar.get(Calendar.DAY_OF_MONTH);
        int displayMonth = dateCalendar.get(Calendar.MONTH)+1;
        int displayear = dateCalendar.get(Calendar.YEAR);

        //비교 해서 년, 월 같으면 진한색 아니면 연한색으로 변경
        if(displayMonth == currentMonth && displayear == currenYear){
            //holder.parentView.setBackgroundColor(Color.parseColor("#D5D5D5"));
            //holder.parentView.setBackgroundColor(Color.BLACK);

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

        //날짜 클릭 이벤트
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                int iYear = day.getYear(); //년
//                int iMonth = day.getMonthValue(); //월
//                int iDay = day.getDayOfMonth(); //일
//
//                String yearMonDay = iYear + "-" + iMonth + "-" + iDay;
//                Toast.makeText(holder.itemView.getContext(),yearMonDay,Toast.LENGTH_SHORT).show();
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
        public CalendarViewHolder(@NonNull View itemView) {
            super(itemView);

            dayText = itemView.findViewById(R.id.dayText);

            parentView=itemView.findViewById(R.id.parentView);
        }
    }
}
