package com.example.diary_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private RecyclerView mRv_todo;
    private ArrayList<TodoItem> mTodoItems;
    private DBHepler mDBHelper;
    private ViewCustomAdpter mAdaptr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        loadRecentDB();

        bottomNavigationView = findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.action_calendar:
                        startActivity(new Intent(ViewActivity.this, MainActivity.class));
                        return true;
                    case R.id.action_view:
                        break;
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

    // 저장되어있던 DB를 가져온다
    private void loadRecentDB() {
        mDBHelper = new DBHepler(this);
        mRv_todo = findViewById(R.id.rv_view);
        mTodoItems = new ArrayList<>();

        mTodoItems = mDBHelper.getTodoList();
        if(mAdaptr ==null){
            mAdaptr= new ViewCustomAdpter(mTodoItems,ViewActivity.this);
            mRv_todo.setHasFixedSize(true);   //리사이트뷰 성능강화
            mRv_todo.setAdapter(mAdaptr);
        }
    }

}