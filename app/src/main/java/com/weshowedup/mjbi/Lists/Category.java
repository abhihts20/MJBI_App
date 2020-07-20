package com.weshowedup.mjbi.Lists;

public class Category {
    private String category;
    private String index;

    public Category(String category, String index) {
        this.category = category;
        this.index = index;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return category;
    }
}
