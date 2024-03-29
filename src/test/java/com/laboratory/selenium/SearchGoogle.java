package com.laboratory.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class SearchGoogle extends TrafficController {


    WebElement searchbox;

    @Test
    public void askGoogle() throws InterruptedException {

        searchbox = driver.findElement(By.name("q"));
        searchbox.click();

        Reporter.log("=====Type in keyword to search=====", true);

        Thread.sleep(5000);

    }


    public void findInGoogle(String keywords) {

        searchbox.sendKeys(keywords);
        searchbox.submit();

    }

    public void checkResults() {

        WebElement l = driver.findElement(By.xpath("//*[@id=\"rso\"]/div[1]/div/div[1]/div/div/div[1]/div/a"));
        Reporter.log("=====Search Engine results=====", true);

        // href value from getAttribute()
        String v = l.getAttribute("href");
        assert v.equals("https://www.morysavane.com/");
        System.out.println("Href value of link: " + v);

    }

}
