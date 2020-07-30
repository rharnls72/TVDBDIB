package com.web.curation.model.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {
    private int uno;
    private int search_uno;

    public int getUno() {
        return this.uno;
    }

    public void setUno(int uno) {
        this.uno = uno;
    }

    public int getSearch_uno() {
        return this.search_uno;
    }

    public void setSearch_uno(int search_uno) {
        this.search_uno = search_uno;
    }

}