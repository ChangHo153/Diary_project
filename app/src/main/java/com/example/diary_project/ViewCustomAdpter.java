package com.example.diary_project;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ViewCustomAdpter extends RecyclerView.Adapter<ViewCustomAdpter.ViewHolder> {

    private ArrayList<TodoItem> mTodoItems;
    private Context mContext;
    private DBHepler mDBHelper;


    public ViewCustomAdpter(ArrayList<TodoItem> mTodoItems, Context mContext) {
        this.mTodoItems = mTodoItems;
        this.mContext = mContext;
        mDBHelper = new DBHepler(mContext);
    }

    @NonNull
    @Override
    public ViewCustomAdpter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //뷰연결
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_list,parent,false);
        return new ViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewCustomAdpter.ViewHolder holder, int position) {
        holder.tv_title.setText(mTodoItems.get(position).getTitle());
        holder.tv_content.setText(mTodoItems.get(position).getContent());
        holder.tv_writeDate.setText(mTodoItems.get(position).getWriteDate());
    }

    @Override
    public int getItemCount() {
        return mTodoItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_title;
        private TextView tv_content;
        private TextView tv_writeDate;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            tv_title = itemView.findViewById(R.id.tv_title);
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_writeDate = itemView.findViewById(R.id.tv_date);

            //하나의 아이템뷰를 터치시 수정가능
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int curPos = getAdapterPosition();      //현재 선택한 리스트 아이템 위치
                    TodoItem todoItem = mTodoItems.get(curPos);  // 선택한 아이템 값들

                    String[] strChoiceItems= {"수정하기","삭제하기"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("원하는 작업을 선택해 주세요");
                    builder.setItems(strChoiceItems, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int position) {
                            if (position == 0){
                                //수정하기
                                Dialog dialog = new Dialog(mContext, android.R.style.Theme_Material_Light_Dialog);
                                dialog.setContentView(R.layout.dialog_edit);
                                EditText et_title = dialog.findViewById(R.id.et_title);
                                EditText et_content = dialog.findViewById(R.id.et_content);
                                Button btn_ok = dialog.findViewById(R.id.btn_ok);
                                btn_ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        // update database
                                        String title = et_title.getText().toString();
                                        String content = et_content.getText().toString();
                                        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());//현재 시간 월일시 받아오기
                                        String beforeTime = todoItem.getWriteDate();  //이전에 작성한 시간

                                        mDBHelper.UpdateTodo(title, content, currentTime, beforeTime);

                                        // update UI
                                        todoItem.setTitle(title);
                                        todoItem.setContent(content);
                                        todoItem.setWriteDate(currentTime);
                                        notifyItemChanged(curPos, todoItem);
                                        dialog.dismiss();
                                        Toast.makeText(mContext, "목록 수정이 완료 되었습니다", Toast.LENGTH_SHORT).show();

                                    }
                                });

                                dialog.show();
                            }
                            else if(position == 1){
                                //delete table
                                String beforeTime = todoItem.getWriteDate();  //이전에 작성한 시간
                                mDBHelper.deleteTodo(beforeTime);

                                //delete UI
                                mTodoItems.remove(curPos);
                                notifyItemRemoved(curPos);
                                Toast.makeText(mContext, "목록이 제거 되었습니다", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                    builder.show();
                }
            });
        }
    }
    // 액티비티에서 호출되는 함수이며, 현재 어댑터에 새로운 게시글 아이템을 전달받아 추가하는 목적
    public void addItem(TodoItem _item){
        mTodoItems.add(0, _item);  //최신 데이터가 위로 올라오게
        notifyItemInserted(0);    //새로고침
    }


}
