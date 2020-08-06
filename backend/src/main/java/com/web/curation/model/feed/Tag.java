package com.web.curation.model.feed;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    private int tno;
    private String name;

    public int getTno() {
        return this.tno;
    }

    public void setTno(int tno) {
        this.tno = tno;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }



}