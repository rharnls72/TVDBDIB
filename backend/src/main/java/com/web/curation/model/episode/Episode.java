package com.web.curation.model.episode;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Episode {
    private int eno;
    private int pno;
    private int episode;
    private String summary;
    private LocalDateTime broadcast_date;
    private String guest;
    private String thumbnail;
    private String replay_link;
    private int shares;
    private int dibs;
    private int likes;
    private int reply_count;
    private String reply;
    
    public int getEno() {
        return eno;
    }

    public void setEno(int eno) {
        this.eno = eno;
    }

    public int getPno() {
        return pno;
    }

    public void setPno(int pno) {
        this.pno = pno;
    }

    public int getEpisode() {
        return episode;
    }

    public void setEpisode(int episode) {
        this.episode = episode;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public LocalDateTime getBroadcast_date() {
        return broadcast_date;
    }

    public void setBroadcast_date(LocalDateTime broadcast_date) {
        this.broadcast_date = broadcast_date;
    }

    public String getGuest() {
        return guest;
    }

    public void setGuest(String guest) {
        this.guest = guest;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getReplay_link() {
        return replay_link;
    }

    public void setReplay_link(String replay_link) {
        this.replay_link = replay_link;
    }

    public int getDibs() {
        return dibs;
    }

    public void setDibs(int dibs) {
        this.dibs = dibs;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getShares() {
        return shares;
    }

    public void setShares(int shares) {
        this.shares = shares;
    }

    public int getReply_count() {
        return reply_count;
    }

    public void setReply_count(int reply_count) {
        this.reply_count = reply_count;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }
    

}

