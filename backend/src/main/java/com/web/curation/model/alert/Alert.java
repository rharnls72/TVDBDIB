package com.web.curation.model.alert;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Alert {
    // 고유번호
    private int ano;
    // 유저번호
    private int uno;
    // 글 타입(1: 에피소드, 2: 피드, 3: 프로그램)
    private int ctype;
    // 타겟 별 고유번호
    private int cno;
    // 알림 타입(1: 좋아요, 2: 댓글, 3: 언급, 4: 팔로우 승인, 5: 새글)
    private int atype;
    // 확인 유무
    private boolean read;

    // Setter, Getter
    public void setAno(int ano) {
        this.ano = ano;
    }
    public int getAno() {
        return ano;
    }
    public void setUno(int uno) {
        this.uno = uno;
    }
    public int getUno() {
        return uno;
    }
    public void setCtype(int ctype) {
        this.ctype = ctype;
    }
    public int getCtype() {
        return ctype;
    }
    public void setCno(int cno) {
        this.cno = cno;
    }
    public int getCno() {
        return cno;
    }public void setAtype(int atype) {
        this.atype = atype;
    }
    public int getAtype() {
        return atype;
    }
    public void setRead(boolean read) {
        this.read = read;
    }
    public boolean getRead() {
        return read;
    }
}