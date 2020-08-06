package com.web.curation.model.alert;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Alert {
    // 고유번호
    private String ano;

    // 유저번호
    private int uno;
    // 글 타입(1: 피드, 2: 피드댓글, 3: 프로그램댓글, 4: 에피소드댓글)
    private int ctype;
    // 타겟 별 고유번호
    private int cno;
    // 알림 타입(1: 좋아요, 2: 댓글, 3: 언급)
    private int atype;
    private String column;
    private String picture;
    private String subject_name;
    private int subject_no;
    // 확인 유무
    private boolean read;
    private LocalDateTime time;

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public int getUno() {
        return uno;
    }

    public void setUno(int uno) {
        this.uno = uno;
    }


    public int getCno() {
        return cno;
    }

    public void setCno(int cno) {
        this.cno = cno;
    }

    public int getAtype() {
        return atype;
    }

    public void setAtype(int atype) {
        this.atype = atype;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public int getSubject_no() {
        return subject_no;
    }

    public void setSubject_no(int subject_no) {
        this.subject_no = subject_no;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public int getCtype() {
        return ctype;
    }

    public void setCtype(int ctype) {
        this.ctype = ctype;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }


}