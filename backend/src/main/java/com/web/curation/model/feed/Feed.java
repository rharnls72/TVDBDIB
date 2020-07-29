package com.web.curation.model.feed;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feed {
    // Feed Attribute
    private int fno;
    private int uno;
    private int pno;
    private int eno;
    private LocalDateTime create_date;
    private String content;
    private int ctype;
    private String thumbnail;
    private String tag;

    // Additional attribute
    private String nick_name;
    private String profile_pic;
    private int reply_num;
    private String reply_content;
    private int like_num;
    private boolean press_like;
    private int dibs_num;
    private boolean press_dibs;

    public void setFno(int fno) {
        this.fno = fno;
    }
    public int getFno() {
        return fno;
    }

    public void setUno(int uno) {
        this.uno = uno;
    }
    public int getUno() {
        return uno;
    }

    public void setPno(int pno) {
        this.pno = pno;
    }
    public int getPno() {
        return pno;
    }

    public void setEno(int eno) {
        this.eno = eno;
    }
    public int getEno() {
        return eno;
    }

    public void setCreate_date(LocalDateTime create_date) {
        this.create_date = create_date;
    }
    public LocalDateTime getCreate_date() {
        return create_date;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getContent() {
        return content;
    }

    public void setCtype(int ctype) {
        this.ctype = ctype;
    }
    public int getCtype() {
        return ctype;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
    public String getThumbnail() {
        return thumbnail;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
    public String getTag() {
        return tag;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }
    public String getNick_name() {
        return nick_name;
    }
    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }
    public String getProfile_pic() {
        return profile_pic;
    }
    public void setReply_num(int reply_num) {
        this.reply_num = reply_num;
    }
    public int getReply_num() {
        return reply_num;
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

    public String getReply_content() {
        return this.reply_content;
    }

    public void setReply_content(String reply_content) {
        this.reply_content = reply_content;
    }

    public int getDibs_num() {
        return this.dibs_num;
    }

    public void setDibs_num(int dibs_num) {
        this.dibs_num = dibs_num;
    }

    public boolean isPress_dibs() {
        return this.press_dibs;
    }

    public void setPress_dibs(boolean press_dibs) {
        this.press_dibs = press_dibs;
    }
}