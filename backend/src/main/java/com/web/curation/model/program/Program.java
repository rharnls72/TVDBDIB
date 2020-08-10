package com.web.curation.model.program;

import java.time.LocalDate;
import java.util.ArrayList;

import com.web.curation.model.episode.EpisodeResponse;

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
    // API 로 받아온 프로그램 정보와 매핑될 변수들
    private String pname;
    private int season;
    private String season_name;
    private ArrayList<String> genre;
    private ArrayList<String> broadcaster;
    private String homepage;
    private String description;
    private String thumbnail;
    private LocalDate start_date;
    private float rating;
    private ArrayList<EpisodeResponse> episode_list;

    // Return API row result(JSON)
    private Object programDetail;

    public Object getProgramDetail() {
        return this.programDetail;
    }

    public void setProgramDetail(Object programDetail) {
        this.programDetail = programDetail;
    }

    /////////////////////////////////////////////

    // 프로그램 정보와 함께 넘길 정보들(좋아요 수, 좋아요 여부, 댓글 수, 대표 댓글 한 개)
    private int like_num;
    private int reply_num;
    private boolean press_like;
    private String reply_content;
    private String reply_user_nick;

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
        if(reply_content != null) {
            String[] strs = reply_content.split(":");
            this.reply_user_nick = strs[0];
            this.reply_content = strs[1];
        }
    }

    public String getReply_user_nick() {
        return this.reply_user_nick;
    }

    public void setReply_user_nick(String reply_user_nick) {
        this.reply_user_nick = reply_user_nick;
    }

    public void setLikeReplyInfo(Program res) {
        like_num = res.like_num;
        press_like = res.press_like;
        reply_num = res.reply_num;

        this.setReply_content(res.reply_content);
    }

    public String getPname() {
        return this.pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public int getSeason() {
        return this.season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public String getSeason_name() {
        return this.season_name;
    }

    public void setSeason_name(String season_name) {
        this.season_name = season_name;
    }

    public ArrayList<String> getGenre() {
        return this.genre;
    }

    public void setGenre(ArrayList<String> genre) {
        this.genre = genre;
    }

    public ArrayList<String> getBroadcaster() {
        return this.broadcaster;
    }

    public void setBroadcaster(ArrayList<String> broadcaster) {
        this.broadcaster = broadcaster;
    }

    public String getHomepage() {
        return this.homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail() {
        return this.thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public LocalDate getStart_date() {
        return this.start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public float getRating() {
        return this.rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public ArrayList<EpisodeResponse> getEpisode_list() {
        return this.episode_list;
    }

    public void setEpisode_list(ArrayList<EpisodeResponse> episode_list) {
        this.episode_list = episode_list;
    }
}

