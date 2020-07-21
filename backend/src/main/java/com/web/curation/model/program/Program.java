package com.web.curation.model.program;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Program {
    private int pno;
    private String name;
    private String broadcaster;
    private String genre;
    private int season;
    private String alias;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private String director;
    private String cast;
    private String description;
    private String broadcast_time;
    private String thumbnail;

    public void setPno(int pno) {
        this.pno = pno;
    }
    public int getPno() {
        return pno;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setBroadcaster(String broadcaster) {
        this.broadcaster = broadcaster;
    }
    public String getBroadcaster() {
        return broadcaster;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public String getGenre() {
        return genre;
    }
    public void setSeason(int season) {
        this.season = season;
    }
    public int getSeason() {
        return season;
    }
    public void setAlias(String alias) {
        this.alias = alias;
    }
    public String getAlias() {
        return alias;
    }
    public void setStart_date(LocalDateTime start_date) {
        this.start_date = start_date;
    }
    public LocalDateTime getStart_date() {
        return start_date;
    }
    public void setEnd_date(LocalDateTime end_date) {
        this.end_date = end_date;
    }
    public LocalDateTime getEnd_date() {
        return end_date;
    }
    public void setDirector(String director) {
        this.director = director;
    }
    public String getDirector() {
        return director;
    }
    public void setCast(String cast) {
        this.cast = cast;
    }
    public String getCast() {
        return cast;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
    public void setBroadcast_time(String broadcast_time) {
        this.broadcast_time = broadcast_time;
    }
    public String getBroadcast_time() {
        return broadcast_time;
    }
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
    public String getThumbnail() {
        return thumbnail;
    }
}

