package com.web.curation.model.reply;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplyResponse {
    private int no;
    private int parent_reply;
    private String content = "";
    private LocalDateTime write_date;
    private int writer_uno;
    private String writer_nick_name;
    private String writer_profile_pic;
    
    // 좋아요 수
    private int like_num;
    // 좋아요 눌렀는지 유무
    private boolean press_like;
    // 좋아요 누른사람 닉네임 둘
    private String liker_nick_name;

    // 하위 댓글 수
    private int reply_num;

    // Getter Setter
    public void setNo(int no) {
        this.no = no;
    }
    public int getNo() {
        return no;
    }
    public void setParent_reply(int parent_reply) {
        this.parent_reply = parent_reply;
    }
    public int getParent_reply() {
        return parent_reply;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getContent() {
        return content;
    }
    public void setWrite_date(LocalDateTime write_date) {
        this.write_date = write_date;
    }
    public LocalDateTime getWrite_date() {
        return write_date;
    }
    public void setWriter_uno(int writer_uno) {
        this.writer_uno = writer_uno;
    }
    public int getWriter_uno() {
        return writer_uno;
    }
    public void setWriter_nick_name(String writer_nick_name) {
        this.writer_nick_name = writer_nick_name;
    }
    public String getWriter_nick_name() {
        return writer_nick_name;
    }
    public void setWriter_profile_pic(String writer_profile_pic) {
        this.writer_profile_pic = writer_profile_pic;
    }
    public String getWriter_profile_pic() {
        return writer_profile_pic;
    }

    public void setLike_num(int like_num) {
        this.like_num = like_num;
    }
    public int getLike_num() {
        return like_num;
    }
    public void setPress_like(boolean press_like) {
        this.press_like = press_like;
    }
    public boolean getPress_like() {
        return press_like;
    }
    public void setLiker_nick_name(String liker_nick_name) {
        this.liker_nick_name = liker_nick_name;
    }
    public String getLiker_nick_name() {
        return liker_nick_name;
    }
    public void setReply_num(int reply_num) {
        this.reply_num = reply_num;
    }
    public int getReply_num() {
        return reply_num;
    }

    // 좋아요 누른 사람 닉네임을 설정하는 함수
    public void appendNickName(String str) {
        if(liker_nick_name == null)
            liker_nick_name = "";
        liker_nick_name += str;
        liker_nick_name += ",";
    }

    // DB 에서 가져온 댓글 정보를 설정하는 함수
    public void setReply(Reply reply) {
        no = reply.getNo();
        parent_reply = reply.getParent_reply();
        content = reply.getContent();
        write_date = reply.getWrite_date();
        writer_uno = reply.getWriter_uno();
        writer_nick_name = reply.getWriter_nick_name();
        writer_profile_pic = reply.getWriter_profile_pic();
        like_num = reply.getLike_num();
        reply_num = reply.getReply_num();
    }
}