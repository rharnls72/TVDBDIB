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
    private int program_id; // 프로그램 고유번호
    private String program_name; // 프로그램 이름
    private int season_num; // 시즌 번호
    private String season_name; // 시즌 이름 (보통 Season 1 이런 식이긴 함)
    
    private int episode_id; // 에피소드 고유번호 (TMDB가 갖고있는 ID)
    private int num; // 에피소드 번호 (1화, 2화...)
    private String image_path; // 에피소드별 스틸컷 이미지
    private LocalDate air_date; // 방송날짜
    private String overview; // 내용요약
    private ArrayList<String> crews; // 제작진 (리스트)
    private ArrayList<String> guest_stars; // 게스트 (리스트)


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