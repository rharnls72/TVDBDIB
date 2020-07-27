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
    private int program_id;
    private String program_name;
    private int season_num;
    private String season_name;
    private int num;

    private String image_path;
    private LocalDate air_date;
    private String overview;
    private ArrayList<String> crews;
    private ArrayList<String> guest_stars;


    public int getNum() {
        return this.num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getProgram_id() {
        return this.program_id;
    }

    public void setProgram_id(int program_id) {
        this.program_id = program_id;
    }

    public String getProgram_name() {
        return this.program_name;
    }

    public void setProgram_name(String program_name) {
        this.program_name = program_name;
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

    public String getImage_path() {
        return this.image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public LocalDate getAir_date() {
        return this.air_date;
    }

    public void setAir_date(LocalDate air_date) {
        this.air_date = air_date;
    }

    public String getOverview() {
        return this.overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public ArrayList<String> getCrews() {
        return this.crews;
    }

    public void setCrews(ArrayList<String> crews) {
        this.crews = crews;
    }

    public ArrayList<String> getGuest_stars() {
        return this.guest_stars;
    }

    public void setGuest_stars(ArrayList<String> guest_stars) {
        this.guest_stars = guest_stars;
    }

}