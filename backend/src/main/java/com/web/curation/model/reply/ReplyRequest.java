package com.web.curation.model.reply;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplyRequest {
    private int uno;
    private int no;
    private int num;

    // Setter Getter
    public void setUno(int uno) {
        this.uno = uno;
    }
    public int getUno() {
        return uno;
    }
    public void setNo(int no) {
        this.no = no;
    }
    public int getNo() {
        return no;
    }
    public void setNum(int num) {
        this.num = num;
    }
    public int getNum() {
        return num;
    }
}