package com.web.curation.model.program;

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
public class Program2 {
    private int id; // 프로그램 고유번호
    private String name; // 프로그램 이름
    private int season_num; // 시즌 번호
    private String season_name; // 시즌 이름
    private ArrayList<String> genres; // 장르 (리스트)
    private ArrayList<String> networks; // 방송사 (리스트)
    private String homepage; // 홈페이지 URL
    private String poster_path; // (그 시즌의) 포스터
    private LocalDate first_air_date; // (그 시즌의) 첫 방송일
    private float rating; // (그 시즌에 대한) 평점

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeason_num() {
        return this.season_num;
    }

    public void setSeason_num(int season_num) {
        this.season_num = season_num;
    }

    public String getSeason_name() {
        return this.season_name;
    }

    public void setSeason_name(String season_name) {
        this.season_name = season_name;
    }

    public ArrayList<String> getGenres() {
        return this.genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public ArrayList<String> getNetworks() {
        return this.networks;
    }

    public void setNetworks(ArrayList<String> networks) {
        this.networks = networks;
    }

    public String getHomepage() {
        return this.homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getPoster_path() {
        return this.poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public LocalDate getFirst_air_date() {
        return this.first_air_date;
    }

    public void setFirst_air_date(LocalDate first_air_date) {
        this.first_air_date = first_air_date;
    }

    public float getRating() {
        return this.rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

}