package com.web.curation.model.vote;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vote {
    private int vno;
    private int fno;
    private int uno;
    private int pos;

    private int count;
    private int user_pos;

    public int getVno() {
        return this.vno;
    }

    public void setVno(int vno) {
        this.vno = vno;
    }

    public int getFno() {
        return this.fno;
    }

    public void setFno(int fno) {
        this.fno = fno;
    }

    public int getUno() {
        return this.uno;
    }

    public void setUno(int uno) {
        this.uno = uno;
    }

    public int getPos() {
        return this.pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getUser_pos() {
        return this.user_pos;
    }

    public void setUser_pos(int user_pos) {
        this.user_pos = user_pos;
    }
}