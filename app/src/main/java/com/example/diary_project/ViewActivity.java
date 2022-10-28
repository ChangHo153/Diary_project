package com.example.diary_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ViewActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private RecyclerView mRv_todo;
    private ArrayList<TodoItem> mTodoItems;
    private DBHepler mDBHelper;
    private CustomAdpter mAdaptr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        setInit();

        bottomNavigationView = findViewById(R.id.rv_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.action_view:
                        startActivity(new Intent(ViewActivity.this, MainActivity.class));
                        return true;
                    case R.id.action_calendar:
                        startActivity(new Intent(ViewActivity.this, MainActivity.class));
                        return true;
                    case R.id.action_plus:
                        startActivity(new Intent(ViewActivity.this, MainActivity.class));
                        return true;
                    case R.id.action_graph:
                        startActivity(new Intent(ViewActivity.this, MainActivity.class));
                        //overridePendingTransition(0, 0); 애니메이션 효과 추가
                        return true;
                }
                return false;
            }
        });
    }

    private void setInit() {
        mDBHelper = new DBHepler(this);//this
        mRv_todo = findViewById(R.id.rv_view);
        bottomNavigationView = findViewById(R.id.action_plus);
        mTodoItems = new ArrayList<>();

        loadRecentDB();

        bottomNavigationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //팝업창 띄우기

                Dialog dialog = new Dialog(ViewActivity.this, android.R.style.Theme_Material_Light_Dialog);
                dialog.setContentView(R.layout.dialog_edit);
                EditText et_title = dialog.findViewById(R.id.et_title);
                EditText et_content = dialog.findViewById(R.id.et_content);
                Button btn_ok = dialog.findViewById(R.id.btn_ok);

                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //프로그램상에 있는 현재 시간과 날짜를 가져오는 함수
                        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());//현재 시간 월일시 받아오기

                        mDBHelper.InsertTodo(et_title.getText().toString(), et_content.getText().toString(),currentTime);
                        // Insert UI
                        TodoItem item = new TodoItem();
                        item.setTitle(et_title.getText().toString());
                        item.setContent(et_content.getText().toString());
                        item.setWriteDate(currentTime);

                        mAdaptr.addItem(item);
                        mRv_todo.smoothScrollToPosition(0);   //작성이 쌓을때마나 스크롤도 같이 올라가는
                        dialog.dismiss();
                        Toast.makeText(ViewActivity.this, "할일 목록에 추가 되었습니다", Toast.LENGTH_SHORT).show();  //MainActivity.this
                    }
                });

                dialog.show();
            }
        });
    }

    private void loadRecentDB() {
        // 저장되어있던 DB를 가져온다
        mTodoItems = mDBHelper.getTodoList();
        if(mAdaptr ==null){
            mAdaptr= new CustomAdpter(mTodoItems,ViewActivity.this);
            mRv_todo.setHasFixedSize(true);   //리사이트뷰 성능강와 뭔진 모른다함
            mRv_todo.setAdapter(mAdaptr);
        }
    }

}