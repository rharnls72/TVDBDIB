package com.web.curation.model.episode;

import java.time.LocalDate;
import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Episode2 {

    private int pno;
    private String pname; 
    private int season; 
    private String season_name;
    private int episode;
    private String episode_name; 
    private String thumbnail;
    private LocalDate broadcast_date;
    private String summary;
    private ArrayList<String> crew;
    private ArrayList<String> guest;

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




}