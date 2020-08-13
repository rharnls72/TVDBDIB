package com.web.curation.model.feed;

import java.io.File;
import java.io.FileInputStream;
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
    private String reply_user_nick;

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

        // 파일 경로로 된 profile_pic 이 들어왔다면
        // 이미지 데이터로 바꿔주기
        if(profile_pic != null && profile_pic.charAt(0) == '/') {
            try {
                String pre_path = "/tvility";
                String full_path = pre_path + profile_pic;
                File file = new File(full_path);

                FileInputStream fis = new FileInputStream(file);
                byte[] bytes = fis.readAllBytes();
                fis.close();

                this.profile_pic = new String(bytes);
            } catch (Exception e) {
                System.out.println("유저 프로필 데이터 로딩 실패: " + e.getMessage());
            }
        }
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
    public String getReply_user_nick() {
        return this.reply_user_nick;
    }

    public void setReply_user_nick(String reply_user_nick) {
        this.reply_user_nick = reply_user_nick;
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
        if(reply_content != null) {
            String[] strs = reply_content.split(":");
            this.reply_user_nick = strs[0];
            this.reply_content = strs[1];
        }
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