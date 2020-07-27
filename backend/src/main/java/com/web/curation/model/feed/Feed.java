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
    private int like_num;
    private int liker_uno;
    private String liker_nick_name;
    private boolean press_like;

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
    public void setLiker_uno(int liker_uno) {
        this.liker_uno = liker_uno;
    }
    public int getLiker_uno() {
        return liker_uno;
    }
    public void setLiker_nick_name(String liker_nick_name) {
        this.liker_nick_name = liker_nick_name;
    }
    public String getLiker_nick_name() {
        return liker_nick_name;
    }
    public void setPress_like(boolean press_like) {
        this.press_like = press_like;
    }
    public boolean getPress_like() {
        return press_like;
    }

    // 좋아요 누른 사람 닉네임을 설정하는 함수
    public void appendNickName(String str) {
        if(liker_nick_name == null)
            liker_nick_name = "";
        liker_nick_name += str;
        liker_nick_name += ",";
    }
}