package com.laboratory.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class TrafficControler {


    WebDriver driver;

    @BeforeMethod
    public void setupApplication(){

        Reporter.log("=====Browser Session Started=====",true);

        driver   = new ChromeDriver();
        driver.manage().window().fullscreen();
        driver.get("http://google.com");

        Reporter.log("=====Application Started=====",true);
    }

    @AfterMethod
    public void closeApplication() throws InterruptedException {
        Thread.sleep(5000);
        driver.quit();
        Reporter.log("=====Browser Session Ended=====",true);

    }
}
