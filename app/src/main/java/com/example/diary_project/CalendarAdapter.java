package com.example.diary_project;

import static com.example.diary_project.MainActivity.selectedDate;

import android.content.Context;
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

    private View view;
    private ViewCustomAdpter mAdaptr;
    private ArrayList<Date> dayList;
    private RecyclerView mRv_todo;
    private ArrayList<TodoItem> mTodoItems;
    private DBHepler mDBHelper;
    private Context mContext;

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
//        setInit();

        //날짜 클릭 이벤트
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //프로그램상에 있는 현재 시간과 날짜를 가져오는 함수
//                String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());//현재 시간 월일시 받아오기
//
//                mDBHelper.InsertTodo(et_title.getText().toString(), et_content.getText().toString(),currentTime);
//                // Insert UI
//                TodoItem item = new TodoItem();
//                item.setTitle(et_title.getText().toString());
//                item.setContent(et_content.getText().toString());
//                item.setWriteDate(currentTime);
//
//                mAdaptr.addItem(item);
//                mRv_todo.smoothScrollToPosition(0);   //작성이 쌓을때마나 스크롤도 같이 올라가는
//                dialog.dismiss();
//                //Toast.makeText(getActivity(), "할일 목록에 추가 되었습니다", Toast.LENGTH_SHORT).show();  //MainActivity.this
//            }
//
//        });
//        dialog.show();
        mDBHelper = new DBHepler(mContext);//this
        //mRv_todo = view.findViewById(R.id.rv_view);
        //bottomNavigationView = view.findViewById(R.id.action_view);
        mTodoItems = new ArrayList<>();

//z
    }

//    private void setInit() {
//        mDBHelper = new DBHepler(mContext);//this
//        //mRv_todo = view.findViewById(R.id.rv_view);
//        //bottomNavigationView = view.findViewById(R.id.action_view);
//        mTodoItems = new ArrayList<>();
//
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //팝업창 띄우기
//
//                Dialog dialog = new Dialog(mContext, android.R.style.Theme_Material_Light_Dialog);
//                dialog.setContentView(R.layout.dialog_edit);
//                EditText et_title = dialog.findViewById(R.id.et_title);
//                EditText et_content = dialog.findViewById(R.id.et_content);
//                Button btn_ok = dialog.findViewById(R.id.btn_ok);
//
//                btn_ok.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        //프로그램상에 있는 현재 시간과 날짜를 가져오는 함수
//                        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());//현재 시간 월일시 받아오기
//
//                        mDBHelper.InsertTodo(et_title.getText().toString(), et_content.getText().toString(),currentTime);
//                        // Insert UI
//                        TodoItem item = new TodoItem();
//                        item.setTitle(et_title.getText().toString());
//                        item.setContent(et_content.getText().toString());
//                        item.setWriteDate(currentTime);
//
//                        mAdaptr.addItem(item);
//                        mRv_todo.smoothScrollToPosition(0);   //작성이 쌓을때마나 스크롤도 같이 올라가는
//                        dialog.dismiss();
//                        //Toast.makeText(getActivity(), "할일 목록에 추가 되었습니다", Toast.LENGTH_SHORT).show();  //MainActivity.this
//                    }
//                });
//
//                dialog.show();
//            }
//        });
//    }
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
