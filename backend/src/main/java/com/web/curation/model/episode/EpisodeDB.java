package com.web.curation.model.episode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EpisodeDB {
    private int eno;
    private int pno;
    private int season;
    private int episode;
    private int share_num;

    public int getEno() {
        return this.eno;
    }

    public void setEno(int eno) {
        this.eno = eno;
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

    public int getEpisode() {
        return this.episode;
    }

    public void setEpisode(int episode) {
        this.episode = episode;
    }

    public int getShare_num() {
        return this.share_num;
    }

    public void setShare_num(int share_num) {
        this.share_num = share_num;
    }

}

