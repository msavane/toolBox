package com.laboratory.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SearchGoogle {

    WebDriver driver;

    @BeforeMethod
    public void setupApplication(){

        Reporter.log("=====Browser Session Started=====",true);

        driver   = new ChromeDriver();
        driver.manage().window().fullscreen();
        driver.get("http://google.com");

        Reporter.log("=====Application Started=====",true);
    }

    @Test
            public void askGoogle() throws InterruptedException {

        WebElement searchbox = driver.findElement(By.name("q"));
        searchbox.click();

        Reporter.log("=====Type in keyword to search=====",true);

        Thread.sleep(5000);


        searchbox.sendKeys("Flabola FSQS");
        searchbox.submit();

        Reporter.log("=====Search Engine results=====",true);

        WebElement l = driver.findElement(By.xpath("//*[@id=\"rso\"]/div[1]/div/div[1]/div/div/div[1]/div/a"));
        // href value from getAttribute()
        String v = l.getAttribute("href");
        assert v.equals("https://www.morysavane.com/");
        System.out.println("Href value of link: "+ v);

    }
    @AfterMethod
    public void closeApplication() throws InterruptedException {
        Thread.sleep(5000);
        driver.quit();
        Reporter.log("=====Browser Session Ended=====",true);

    }
}
