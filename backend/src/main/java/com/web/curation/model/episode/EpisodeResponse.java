package com.web.curation.model.episode;

import java.time.LocalDate;
import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EpisodeResponse {

    private int pno;
    private String pname; 
    private int season; 
    private String season_name;
    private int episode;
    private String episode_name; 
    private String thumbnail; // 그 에피소드의 스틸컷 이미지.
    private ArrayList<String> genre;
	private String poster; // 프로필사진으로 쓰일 프로그램 포스터.

    private LocalDate broadcast_date;
    private String summary;
    private ArrayList<String> crew;
	private ArrayList<String> guest;
	
	// Additional attribute
	private int uno;
	private int eno;
	private int like_num;
	private boolean press_like;
	private int reply_num;
	private String reply_content;
	private String reply_user_nick;

	private int share_num;
	private int dibs_num;

	public int getPno() {
		return this.pno;
	}

	public void setPno(int pno) {
		this.pno = pno;
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

	public int getEpisode() {
		return this.episode;
	}

	public void setEpisode(int episode) {
		this.episode = episode;
	}

	public String getEpisode_name() {
		return this.episode_name;
	}

	public void setEpisode_name(String episode_name) {
		this.episode_name = episode_name;
	}

	public String getThumbnail() {
		return this.thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public ArrayList<String> getGenre() {
		return this.genre;
	}

	public void setGenre(ArrayList<String> genre) {
		this.genre = genre;
	}

	public String getPoster() {
		return this.poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public LocalDate getBroadcast_date() {
		return this.broadcast_date;
	}

	public void setBroadcast_date(LocalDate broadcast_date) {
		this.broadcast_date = broadcast_date;
	}

	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public ArrayList<String> getCrew() {
		return this.crew;
	}

	public void setCrew(ArrayList<String> crew) {
		this.crew = crew;
	}

	public ArrayList<String> getGuest() {
		return this.guest;
	}

	public void setGuest(ArrayList<String> guest) {
		this.guest = guest;
	}


	public int getUno() {
		return this.uno;
	}

	public void setUno(int uno) {
		this.uno = uno;
	}

	public int getEno() {
		return this.eno;
	}

	public void setEno(int eno) {
		this.eno = eno;
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

	public int getReply_num() {
		return this.reply_num;
	}

	public void setReply_num(int reply_num) {
		this.reply_num = reply_num;
	}

	public String getReply_content() {
		return this.reply_content;
	}

	public void setReply_content(String reply_content) {
		String[] strs = reply_content.split(":");
        this.reply_user_nick = strs[0];
        this.reply_content = strs[1];
	}

	public String getReply_user_nick() {
		return this.reply_user_nick;
	}

	public void setReply_user_nick(String reply_user_nick) {
		this.reply_user_nick = reply_user_nick;
	}

	public int getShare_num() {
		return this.share_num;
	}

	public void setShare_num(int share_num) {
		this.share_num = share_num;
	}

	public int getDibs_num() {
		return this.dibs_num;
	}

	public void setDibs_num(int dibs_num) {
		this.dibs_num = dibs_num;
	}

	public void setAdditionalData(EpisodeDB episode) {
		eno = episode.getEno();
		like_num = episode.getLike_num();
		press_like = episode.isPress_like();
		reply_num = episode.getReply_num();
		reply_content = episode.getReply_content();
		share_num = episode.getShare_num();
		dibs_num = episode.getDibs_num();
	}
}