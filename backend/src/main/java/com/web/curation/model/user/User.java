// 하단 DB 설정 부분은 Sub PJT II에서 데이터베이스를 구성한 이후에 주석을 해제하여 사용.

package com.web.curation.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    // uid 는 DBMS 가 자동으로 생성해주는 값이네요
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uid;

    @JsonIgnore
    private String password;
    private String email;

    @Column(insertable = false, updatable = false)
    private LocalDateTime createDate;

    // Vue 에는 회원가입시 닉네임 입력하는 칸이 있는데
    // 왜 DB 에는 닉네임 컬럼이 없나요?
    @Column
    private String nickname;

    public User() {}

    public User(int uid, String email, LocalDateTime createDate, String nickname) {
        this.uid = uid;
        this.email = email;
        this.createDate = createDate;
        this.nickname = nickname;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
    public int getUid() {
        return uid;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getNickname() {
        return nickname;
    }
}
