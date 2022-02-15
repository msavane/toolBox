package com.laboratory.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTest {
    private static WebDriver webDriver;
    protected static Page basePage;

    private static final String APP_URL = "http://google.com";

    @BeforeClass
    public static void launchApplication(){
        setChromeDriverProperty();
        webDriver = new ChromeDriver();
        webDriver.get(APP_URL);
        basePage = new Page();
        basePage.setWebDriver(webDriver);
    }

    @AfterClass
    public static void closeBrowser(){
        webDriver.quit();
    }

    private static void setChromeDriverProperty(){
        System.setProperty("webdriver.chrome.driver", "resources/chromedriver");
    }
}
