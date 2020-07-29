package com.web.curation.model.program;

import com.web.curation.model.reply.Reply;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Program {
    // 프로그램 고유번호는 꼭 필요함
    private int pno;

    /////////////////////////////////////////////
    // API 로 받아온 프로그램 정보와 매핑될 변수들 만드는 영역

    /////////////////////////////////////////////

    // 프로그램 정보와 함께 넘길 정보들(좋아요 수, 좋아요 여부, 댓글 수, 대표 댓글 한 개)
    private int like_num;
    private int reply_num;
    private boolean press_like;
    private String reply_content;

    public int getPno() {
        return this.pno;
    }

    public void setPno(int pno) {
        this.pno = pno;
    }

    public int getLike_num() {
        return this.like_num;
    }

    public void setLike_num(int like_num) {
        this.like_num = like_num;
    }

    public int getReply_num() {
        return this.reply_num;
    }

    public void setReply_num(int reply_num) {
        this.reply_num = reply_num;
    }

    public boolean isPress_like() {
        return this.press_like;
    }

    public void setPress_like(boolean press_like) {
        this.press_like = press_like;
    }

    public String getReply_content() {
        return this.reply_content;
    }

    public void setReply_content(String reply_content) {
        this.reply_content = reply_content;
    }

    public void setLikeReplyInfo(Program res) {
        like_num = res.like_num;
        press_like = res.press_like;
        reply_num = res.reply_num;
        reply_content = res.reply_content;
    }

}

