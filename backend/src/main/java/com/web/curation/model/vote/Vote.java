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
}