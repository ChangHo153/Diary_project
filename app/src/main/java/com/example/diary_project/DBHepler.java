package com.example.diary_project;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHepler extends SQLiteOpenHelper { // db관리를 도와주는 클래스 생성후  alt+ent 메소스드 와 생성자 생성
    private static final int DB_VERSION =1;
    private static final String DB_NAME = "diary.db";

    public DBHepler(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //데이터 베이스가 생성될 때 호출
        //데이터베이스 -> 테이블 ->컬럼 -> 값
        db.execSQL("CREATE TABLE IF NOT EXISTS TodoList(id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL, content TEXT NOT NULL,iocn INTEGER NOT NULL,writeDate TEXT NOT NULL)");
        //테이블이 없을시 테이블 생성 기본키 생성시마다 아이디가 증가 옵션 추가 그 뒤는 title 변수에 string(text) 값을 넣는데 비면X

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    //SELECT문 (할일 조회)
    public ArrayList<TodoItem> getTodoList(){
        ArrayList<TodoItem> todoItems = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM TodoList ORDER BY writeDate DESC",null);
        if(cursor.getCount() != 0){
            //조회온 데이터가 있을때  내부 수행
            while(cursor.moveToNext()){

                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String content = cursor.getString(cursor.getColumnIndexOrThrow("content"));
                int iocn = cursor.getInt(cursor.getColumnIndexOrThrow("iocn"));
                String writeDate = cursor.getString(cursor.getColumnIndexOrThrow("writeDate"));


                TodoItem todoItem = new TodoItem();
                todoItem.setId(id);
                todoItem.setTitle(title);
                todoItem.setContent(content);
                todoItem.setIcon(iocn);
                todoItem.setWriteDate(writeDate);
                todoItems.add(todoItem);
            }
        }
        cursor.close();

        return todoItems;
    }

    // INSERT문 (할일 목록을 DB에 넣는다.
    public void InsertTodo(String _title,String _content, String _writeDate, int _iocn){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO TodoList(title, content,iocn ,writeDate) VALUES('"+ _title+"', '"+ _content+"',"+_iocn+" ,'"+ _writeDate+"');");
    }

    //UPDATE문 (할일 목록을 수정한다)
    public  void UpdateTodo(String _title,String _content,int _iocn ,String _writeDate, String _beforeDate){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE TodoList SET title='"+_title +"',content='"+_content +"',iocn="+_iocn+" ,writeDate='"+_writeDate +"' where writeDate='"+_beforeDate +"'");
    }

    //DELETE문 (할일 목록을 제거한다)
    public void deleteTodo(String _beforeDate){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM TodoList WHERE writeDate='"+ _beforeDate +"'");
    }

}
