package com.web.curation.model.like;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Like {
    private int lno;
    private int uno;
    private int tno; // eno, erno, fno 등 타겟의 고유 번호

    // Like 객체 하나로 프로그램과 관련된 서비스도 제공하기위해
    // 프로그램의 좋아요 수 와 로그인한 유저가 좋아요 눌렀는지 여부도
    // 여기에 포함시켰음
    private int like_num;
    private boolean press_like;

    // Getter Setter
    public int getLno() {
        return lno;
    }
    public void setLno(int lno) {
        this.lno = lno;
    }
    public int getUno() {
        return uno;
    }
    public void setUno(int uno) {
        this.uno = uno;
    }
    public int getTno() {
        return tno;
    }
    public void setTno(int tno) {
        this.tno = tno;
    }

    public int getLike_num() {
        return this.like_num;
    }

    public void setLike_num(int like_num) {
        this.like_num = like_num;
    }

    public boolean isPress_like() {
        return this.press_like;
    }

    public void setPress_like(boolean press_like) {
        this.press_like = press_like;
    }
}