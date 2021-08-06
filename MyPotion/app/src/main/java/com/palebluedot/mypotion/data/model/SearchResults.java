package com.palebluedot.mypotion.data.model;

import androidx.annotation.NonNull;

import java.util.List;

public class SearchResults {
    private List<PotionItem> results;
    private int pageNo;
    private int maxPageNo;
    private int total;

    public SearchResults(@NonNull List<PotionItem> results, int pageNo, int maxPageNo, int total) {
        this.results = results;
        this.pageNo = pageNo;
        this.maxPageNo = maxPageNo;
        this.total = total;
    }

    public List<PotionItem> getResults() {
        return results;
    }

    public void setResults(List<PotionItem> results) {
        this.results = results;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getMaxPageNo() {
        return maxPageNo;
    }

    public void setMaxPageNo(int maxPageNo) {
        this.maxPageNo = maxPageNo;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "SearchResults{" +
                "results=" + results +
                ", pageNo=" + pageNo +
                ", maxPageNo=" + maxPageNo +
                ", total=" + total +
                '}';
    }

}
