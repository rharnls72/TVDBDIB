package com.web.curation.model.dibs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dibs {
    private int uno;
    private int tno;

    public int getUno() {
        return this.uno;
    }

    public void setUno(int uno) {
        this.uno = uno;
    }

    public int getTno() {
        return this.tno;
    }

    public void setTno(int tno) {
        this.tno = tno;
    }

}