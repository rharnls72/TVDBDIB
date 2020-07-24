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
}