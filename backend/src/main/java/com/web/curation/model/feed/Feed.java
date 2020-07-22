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

    // User Attribute
    private String nick_name;
    
    // Program Attribute


    // Episode Attribute

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
}