package com.example.diary_project;
/*
 * 사용자 계정 정보 모델 클래스
 *  여기서 닉네임, 프로필 이미지 URL 유저 메시지 같은것도 저장할 수 있다
 */

public class UserAccount {
    private String idTokern;  //Firebase Uid(고유 토큰정보) 키값
    private String emailId;   //이메일 아이디
    private String password;  // 비번

    public UserAccount() {}

    public String getIdTokern() {
        return idTokern;
    }

    public void setIdTokern(String idTokern) {
        this.idTokern = idTokern;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
