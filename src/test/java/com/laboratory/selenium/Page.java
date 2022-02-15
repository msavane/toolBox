package com.laboratory.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class Page {
    protected static WebDriver driver;

    private static final String SEARCH_FIELD_CLASS = "q";
    private static final String QUERY_TXT = "cart_icon";

    public void setWebDriver(WebDriver driver) {
        Page.driver = driver;
    }

    public GooglePage search(String query) {
        driver.findElement(By.className(SEARCH_FIELD_CLASS)).sendKeys(query + Keys.ENTER);

        return new GooglePage();
    }

    /*public GooglePage clickSearchLink() {
        driver.findElement(By.className(CART_LINK_CLASS)).click();
        return new GooglePage();
    }*/
}
