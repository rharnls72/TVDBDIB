package com.web.curation.model.following;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgramFollowing {
    // program_follow Attribute
    private int uno;
    private int pno;

    // user attribute

    // program attribute

    // Setter, Getter
    public void setUno(int uno) {
        this.uno = uno;
    }
    public int getUno() {
        return uno;
    }

    public void setPno(int pno) {
        this.pno = pno;
    }
    public int getPno() {
        return pno;
    }
}