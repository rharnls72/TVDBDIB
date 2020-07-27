package com.web.curation.model.program;

import java.time.LocalDate;
import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Program2 {
    private int id;
    private String name;
    private int season_num;
    private String season_name;
    private ArrayList<String> genres;
    private ArrayList<String> networks;
    private String homepage;
    private String poster_path;
    private LocalDate first_air_date;
    private float rating;

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