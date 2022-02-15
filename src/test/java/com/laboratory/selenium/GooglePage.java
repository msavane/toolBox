package com.laboratory.selenium;

import org.openqa.selenium.By;

public class GooglePage extends Page {

    private static final String SEARCH_RESULTS = "//*[@id=\"rso\"]/div[1]/div/div[1]/div/div/div[1]/div/a/h3";

    public int getSearchResult() {
        return driver.findElements(By.xpath(SEARCH_RESULTS)).size();
    }
}
