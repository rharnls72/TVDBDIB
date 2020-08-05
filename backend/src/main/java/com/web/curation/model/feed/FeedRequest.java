package com.web.curation.model.feed;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedRequest {
    private int uno;
    private int num;
    private int target_uno;
    private int is_dibs;

    public void setUno(int uno) {
        this.uno = uno;
    }
    public int getUno() {
        return uno;
    }
    public void setNum(int num) {
        this.num = num;
    }
    public int getNum() {
        return num;
    }

    public int getTarget_uno() {
        return this.target_uno;
    }

    public void setTarget_uno(int target_uno) {
        this.target_uno = target_uno;
    }

    public int getIs_dibs() {
        return this.is_dibs;
    }

    public void setIs_dibs(int is_dibs) {
        this.is_dibs = is_dibs;
    }
}