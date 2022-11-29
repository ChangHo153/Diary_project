package com.example.diary_project;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity{

    TextView monthYearText; //년월 텍스트뷰
    RecyclerView recyclerView;
    public static Calendar selectedDate; //년월 변수
    private ArrayList<TodoItem> mTodoItems;

    private FirebaseAuth mFirebaseAuth;
    private BottomNavigationView bottomNavigationView; //바텀 네비게이션 뷰
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();

        //초기화
        monthYearText = findViewById(R.id.monthYearText);
        ImageButton preBtn = findViewById(R.id.pre_btn);
        ImageButton nextBtn = findViewById(R.id.next_btn);
        recyclerView = findViewById(R.id.recycler_view);

        //현재 날짜
        selectedDate = Calendar.getInstance();

        //화면 설정
        setMonthView();

        //이전 달 버튼 이벤트
        preBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // -1한 월을 넣어준다 (2월 -> 1월)
                selectedDate.add(Calendar.MONTH,-1); //-1
                setMonthView();
            }
        });

        //다음달 버튼 이벤트
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedDate.add(Calendar.MONTH,1);//+1
                setMonthView();
            }
        });

        /*Button btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //로그아웃
                mFirebaseAuth.signOut();


                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
         */
        //하단바 변환
        bottomNavigationView = findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.action_calendar:
                        break;
                    case R.id.action_view:
                        startActivity(new Intent(MainActivity.this, ViewActivity.class));
                        finish();
                        return true;
                    case R.id.action_inquiry:
                        startActivity(new Intent(MainActivity.this, InquiryActivity.class));
                        finish();
                        //overridePendingTransition(0, 0); 애니메이션 효과 추가
                        return true;
                }
                return false;
            }
        });

    }

    //날짜 타입 설정 0000년 0월
    @RequiresApi(api = Build.VERSION_CODES.O)
    private String monthYearFromDate(Calendar calendar){
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;

        String monthYear = year+ " 년 "  + month+ " 월";
        return monthYear;
    }

    //화면 설정
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView(){
        //년월 텍스트뷰 셋팅
        monthYearText.setText(monthYearFromDate(selectedDate));

        //해당 월 날짜 가져오기
        ArrayList<Date> dayList = daysInMonthArray();


        //어뎁터 데이터 적용
        MainAdapter adapter = new MainAdapter(dayList,MainActivity.this);

        //레이아웃 설정 (열 7개)
        RecyclerView.LayoutManager manager = new GridLayoutManager(getApplicationContext(), 7);

        //레이아웃 적용
        recyclerView.setLayoutManager(manager);

        //어뎁터 적용
        recyclerView.setAdapter(adapter);
    }

    //날짜 생성
    @RequiresApi(api = Build.VERSION_CODES.O)
    private ArrayList<Date> daysInMonthArray(){

        ArrayList<Date> dayList = new ArrayList<>();

        //날짜 복사해서 변수 생성
        Calendar monthCalendar = (Calendar)selectedDate.clone();

        //1일로 셋팅 0월 1일
        monthCalendar.set(Calendar.DAY_OF_MONTH,1);

        //요일 가져와서 -1 일요일 : 1, 월요일 :2
        int firstDayOfMonth = monthCalendar.get(Calendar.DAY_OF_WEEK)-1;

        //날짜 셋팅(-5일전)
        monthCalendar.add(Calendar.DAY_OF_MONTH, -firstDayOfMonth);

        //42전까지 반복
        while(dayList.size() < 42 ){
            //리스트에 날짜 등록
            dayList.add(monthCalendar.getTime());

            //1일씩 늘린 날짜로 변경 1일 -> 2일
            monthCalendar.add(Calendar.DAY_OF_MONTH,1);
        }
        return dayList;
    }

//    @Override
//    public void onItemClick() {
//        mDBHelper = new DBHepler(this);//this
//        mRv_todo = findViewById(R.id.rv_view);
//        mTodoItems = new ArrayList<>();
//
//        Dialog dialog = new Dialog(MainActivity.this, android.R.style.Theme_Material_Light_Dialog);
//        dialog.setContentView(R.layout.dialog_edit);
//        EditText et_title = dialog.findViewById(R.id.et_title);
//        EditText et_content = dialog.findViewById(R.id.et_content);
//        Button btn_ok = dialog.findViewById(R.id.btn_ok);
//    }
}