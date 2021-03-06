package com.web.curation.model.reply;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reply {
    private int no;
    private int parent_reply;
    private String content;
    private LocalDateTime write_date;
    private int writer_uno;
    private String writer_nick_name;
    private String writer_profile_pic;
    private int like_num;
    private int reply_num;
    private boolean press_like;
    private String re_content;
    private boolean hide;

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
    public void setReply_num(int reply_num) {
        this.reply_num = reply_num;
    }
    public int getReply_num() {
        return reply_num;
    }
    public boolean isPress_like() {
        return this.press_like;
    }

    public void setPress_like(boolean press_like) {
        this.press_like = press_like;
    }

    public String getRe_content() {
        return this.re_content;
    }

    public void setRe_content(String re_content) {
        this.re_content = re_content;
    }

    public boolean isHide() {
        return this.hide;
    }

    public void setHide(boolean hide) {
        this.hide = hide;
    }
}