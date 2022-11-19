package com.example.diary_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView; //바텀 네비게이션 뷰
    private RecyclerView mRv_todo;
    private ArrayList<TodoItem> mTodoItems;
    private DBHepler mDBHelper;
    private ViewCustomAdpter mAdaptr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        setInit();


        bottomNavigationView = findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.action_calendar:
                        startActivity(new Intent(ViewActivity.this, MainActivity.class));
                        finish();
                        return true;
                    case R.id.action_view:
                        break;
                    case R.id.action_graph:
                        startActivity(new Intent(ViewActivity.this, GraphActivity.class));
                        finish();
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
        mTodoItems = new ArrayList<>();

        loadRecentDB();

    }

    private void loadRecentDB() {
        // 저장되어있던 DB를 가져온다
        mTodoItems = mDBHelper.getTodoList();
        if(mAdaptr ==null){
            mAdaptr= new ViewCustomAdpter(mTodoItems, ViewActivity.this);
            mRv_todo.setHasFixedSize(true);   //리사이트뷰 성능강와 뭔진 모른다함
            mRv_todo.setAdapter(mAdaptr);
        }
    }

}