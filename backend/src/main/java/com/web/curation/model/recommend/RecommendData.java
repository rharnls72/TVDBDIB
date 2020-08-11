package com.web.curation.model.recommend;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendData {
    private int pno;
    private LocalDateTime create_date;

    public int getPno() {
        return this.pno;
    }

    public void setPno(int pno) {
        this.pno = pno;
    }

    public LocalDateTime getCreate_date() {
        return this.create_date;
    }

    public void setCreate_date(LocalDateTime create_date) {
        this.create_date = create_date;
    }

}