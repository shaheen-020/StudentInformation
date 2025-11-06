package com.example;

import java.util.List;

public class SearchContext {

    private SearchStrategy strategy;

    public SearchContext(SearchStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(SearchStrategy strategy) {
        this.strategy = strategy;
    }

    public List<String[]> executeSearch(String query) {
        return strategy.search(query);
    }
}
