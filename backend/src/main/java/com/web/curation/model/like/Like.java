package com.web.curation.model.like;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Like {
    private int lno;
    private int uno;
    private int tno; // eno, erno, fno 등 타겟의 고유 번호

    // Getter Setter
    public int getLno() {
        return lno;
    }
    public void setLno(int lno) {
        this.lno = lno;
    }
    public int getUno() {
        return uno;
    }
    public void setUno(int uno) {
        this.uno = uno;
    }
    public int getTno() {
        return tno;
    }
    public void setTno(int tno) {
        this.tno = tno;
    }
}