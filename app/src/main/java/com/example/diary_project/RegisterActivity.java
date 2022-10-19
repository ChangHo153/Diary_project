package com.example.diary_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;      //파이어베이스 인증처리
    private DatabaseReference mDatabaseRef;   //실시간 데이터 베이스 서버에 연동시키는 객체
    private EditText mEtEmail, mEtPwd;          // 회원가입 입력필드
    private Button mBtnRegister;               //회원가입 버튼


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirebaseAuth = FirebaseAuth.getInstance();  //파이어베이스 어스 사용 준비
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("homedroid");

        mEtEmail = findViewById(R.id.et_email);
        mEtPwd = findViewById(R.id.et_pwd);
        mBtnRegister = findViewById(R.id.btn_register);

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //회원가입 시작
                String strEmail = mEtEmail.getText().toString(); //이메일 입력값을 가져와 문자열로변환
                String strPwd = mEtPwd.getText().toString();

                //Firebase Auth진행
                mFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {   //회원 가입이 성공적으로 이루어 졌을때 처리할것들
                        if(task.isSuccessful()){    // task이름으로 결과값 반환 ,가입이 정상적으로 성공했으면
                            FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();   //현재 유저 가져오기
                            UserAccount account = new UserAccount();
                            account.setIdTokern(firebaseUser.getUid());
                            account.setEmailId(firebaseUser.getEmail());
                            account.setPassword(strPwd);

                            //setValue : database에 insert (삽입) 행위
                            mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);

                            Toast.makeText(RegisterActivity.this,"회원가입에 성공하셨습니다", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(RegisterActivity.this,"회원가입에 실패하셨습니다", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }
}