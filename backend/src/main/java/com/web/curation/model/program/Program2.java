package com.web.curation.model.program;

import java.time.LocalDate;
import java.util.ArrayList;

import com.web.curation.model.episode.Episode2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Program2 {
    private int pno;
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
    private ArrayList<Episode2> episode_list;

	public ArrayList<Episode2> getEpisode_list() {
		return this.episode_list;
	}

	public void setEpisode_list(ArrayList<Episode2> episode_list) {
		this.episode_list = episode_list;
    }
    
    
	public String getPname() {
        return this.pname;
    }
    
    public void setPname(String pname) {
        this.pname = pname;
    }

	public int getPno() {
		return this.pno;
	}

	public void setPno(int pno) {
		this.pno = pno;
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


}